package com.utils.snowflake;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.ThreadLocalRandom;

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
@Slf4j
public class IdGenerator {

    private static long lastTimestamp = -1L;

    private static long workerID = -1L;

    private static long sequence;

    public static void init(StringRedisTemplate redisTemplate, String applicationName) {
        String keyPrefix = applicationName + SnowflakeConstant.REDIS_KEY_PREFIX;
        WorkerIdManager.init(redisTemplate.getRequiredConnectionFactory(), keyPrefix);
        FakeTimeStampManager.init(redisTemplate, keyPrefix);
        workerID = WorkerIdManager.getWorkerId();
    }

    public static synchronized long nextId() {
        if (-1 == workerID) throw new RuntimeException("The snowflake-id-generator is not enabled, please configure @EnableSnowflakeIdGenerator.");
        long currentTimestamp = FakeTimeStampManager.currentTimestamp();
        if (currentTimestamp > lastTimestamp) {
            sequence = ThreadLocalRandom.current().nextLong(1, 3);
        } else if (currentTimestamp == lastTimestamp) {
            // 相同毫秒内，序列号自增
            long nextSequence = (sequence + 1) & SnowflakeConstant.MAX_SEQUENCE;
            if (nextSequence == 0) {
                currentTimestamp++;
                FakeTimeStampManager.updateTimestamp(currentTimestamp);
            }
            sequence = nextSequence;
        } else {
            throw new RuntimeException("The timestamp has been fallback."); // 理论上不会发生
        }
        lastTimestamp = currentTimestamp;
        // 时间戳部分 | WorkerID部分 | 序列号部分
        return (currentTimestamp << SnowflakeConstant.TIMESTAMP_LEFT_SHIFT)
                | (workerID << SnowflakeConstant.WORKER_ID_LEFT_SHIFT)
                | sequence;
    }

}
