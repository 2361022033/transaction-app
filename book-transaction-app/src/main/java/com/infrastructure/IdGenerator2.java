package com.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 基于Twitter的Snowflake算法实现分布式高效有序ID生产黑科技(sequence)——升级版Snowflake
 *
 * <br>
 * SnowFlake的结构如下(每部分用-分开):<br>
 * <br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 000000000000 - 000000000000 <br>
 * <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * <br>
 * <br>
 * 12位的数据机器位<br>
 * <br>
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
 * <br>
 * 64 - 符号位(1) - 机器id(12) - 毫秒内的序列(12) = 39
 * 39位时间截(毫秒级)，
 * <br>
 * 加起来刚好64位，为一个Long型。<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 * <p>
 */
@Component
public class IdGenerator2 {


    private static final Logger log = LoggerFactory.getLogger(IdGenerator2.class);

    /**
     * 12位的机器id
     */
    private final long workerIdBits = 12L;
    /**
     * 每毫秒内产生的id数: 2的12次方个
     */
    private final long sequenceBits = 12L;

    protected final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    private final long workerIdShift = sequenceBits;

    /**
     * 时间戳左移动位
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits;

    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 所属机器id
     */
    private long workerId;
    /**
     * 并发控制序列
     */
    private long sequence = 0L;

    /**
     * 上次生产 ID 时间戳
     */
    private long lastTimestamp = -1L;

    @Resource
    private StringRedisTemplate redisTemplate;

    private final static String KEY_WORKER_ID = "SNOW:FLAKE:WORKER:ID";
    /**
     * redis系统时间
     */
    private final static String KEY_FAKETIMESTAMP_ID = "SNOW:FLAKE:FAKETIMESTAMP:ID";

    private ScheduledExecutorService executorService;
    private AtomicLong fakeTimeStamp = new AtomicLong(1);

    @PostConstruct
    public void init() {
        // 初始化redis伪时间戳
        redisTemplate.opsForValue().setIfAbsent(KEY_FAKETIMESTAMP_ID, "1");

        //workerId
        redisTemplate.opsForValue().setIfAbsent(KEY_WORKER_ID, "1");
        Long workerId = redisTemplate.opsForValue().increment(KEY_WORKER_ID);
        this.workerId = workerId % maxWorkerId;

        //定时任务轮循
        executorService = new ScheduledThreadPoolExecutor(2, r -> {
            Thread thread = new Thread(r, "increaseId-refresher-" + workerId);
            thread.setDaemon(true);
            return thread;
        });

        //jvm自增increaseId
        executorService.scheduleAtFixedRate(() -> {
                    try {
                        fakeTimeStamp.getAndIncrement();
                    } catch (Exception e) {
                        log.error("Autoincrement increaseId failed", e);
                    }
                },
                0, 1, TimeUnit.MILLISECONDS);

        //伪时间戳更新到redis
        executorService.scheduleAtFixedRate(() -> {
                    try {
                        String luaScript = "local increaseId = ARGV[1]\n" +
                                "local currentId = tonumber(redis.call('GET', KEYS[1]))\n" +
                                "if tonumber(increaseId) > currentId then\n" +
                                "    redis.call('SET', KEYS[1], increaseId)\n" +
                                "    return increaseId\n" +
                                "else\n" +
                                "    return currentId\n" +
                                "end";

                        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>(luaScript, String.class);
                        String key = "KEY_FAKETIMESTAMP_ID";
                        List<String> keys = Collections.singletonList(key);
                        String result = redisTemplate.execute(redisScript, keys, fakeTimeStamp.get());
                        if (result != null) {
                            fakeTimeStamp.set(Long.parseLong(result));
                        }
                    } catch (Exception e) {
                        log.error("refresh redis:KEY_FAKETIMESTAMP_ID failed", e);
                    }
                },
                0, 1, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取下一个 ID
     *
     * @return next id
     */
    public synchronized long nextId() {
        long timestamp = workerId;
        // 时间回退
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }
        if (lastTimestamp == timestamp) {
            // 相同毫秒内，序列号自增
            long nextSequence = (sequence + 1) & sequenceMask;
            if (nextSequence == 0) {
                // 同一毫秒的序列数已经达到最大,increaseId自增，并更新redis中的KEY_FAKETIMESTAMP_ID
                Long newIncreaseId = redisTemplate.opsForValue().increment(KEY_FAKETIMESTAMP_ID);
                if (newIncreaseId != null) {
                    fakeTimeStamp.set(newIncreaseId);
                }
            }
            sequence = nextSequence;
        } else {
            // 不同毫秒内，序列号置为 1 - 3 随机数
            sequence = ThreadLocalRandom.current().nextLong(1, 3);
        }
        lastTimestamp = timestamp;
        // 时间戳部分 | 数据中心部分 | 机器标识部分 | 序列号部分
        return (fakeTimeStamp.get() << timestampLeftShift)
                | (workerId << workerIdShift)
                | sequence;
    }


    public static void main(String[] args) {
        IdGenerator2 idGenerator = new IdGenerator2();
        idGenerator.fakeTimeStamp.set(1L);
        idGenerator.workerId = 1;
        for (int i = 0; i < 6000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(idGenerator.nextId() + "===" + idGenerator.lastTimestamp);
                    } catch (Exception e) {
                        System.out.println("----" + e.getMessage());
                    }
                }
            }).start();
        }
    }

}
