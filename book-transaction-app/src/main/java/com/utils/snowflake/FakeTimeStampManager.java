package com.utils.snowflake;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FakeTimeStampManager {

    private static long fakeTimestamp;

    private static String key;

    public static void init(StringRedisTemplate redisTemplate, String keyPrefix) {
        key = keyPrefix + "fakeTimestamp";
        initTimestamp(redisTemplate);
        increaseTimestamp();
        syncTimestamp(redisTemplate);
    }

    private static void initTimestamp(StringRedisTemplate redisTemplate) {
        redisTemplate.opsForValue().setIfAbsent(key, "1");
        String value = redisTemplate.opsForValue().get(key);
        assert value != null;
        fakeTimestamp = Long.parseLong(value);
    }

    private static void increaseTimestamp() {
        Executors.newSingleThreadScheduledExecutor(
                r -> new Thread(r, "FakeTimeStampManager.increaseTimestamp" + WorkerIdManager.getWorkerId())
        ).scheduleAtFixedRate(() -> {
            try {
                updateTimestamp(fakeTimestamp + 1);
            } catch (Exception e) {
                log.error("FakeTimeStampManager.increaseTimestamp failed", e);
            }
        }, 0, 1, TimeUnit.MILLISECONDS);
    }

    private static void syncTimestamp(StringRedisTemplate redisTemplate) {
        Executors.newSingleThreadScheduledExecutor(
                r -> new Thread(r, "increaseTimestamp.syncTimestamp" + WorkerIdManager.getWorkerId())
        ).scheduleWithFixedDelay(() -> {
            try {
                String luaScript = "local newValue = ARGV[1]\n" +
                        "local currValue = tonumber(redis.call('GET', KEYS[1]))\n" +
                        "if tonumber(newValue) > currValue then\n" +
                        "    redis.call('SET', KEYS[1], newValue)\n" +
                        "    return tostring(newValue)\n" +
                        "else\n" +
                        "    return tostring(currValue)\n" +
                        "end";
                DefaultRedisScript<String> redisScript = new DefaultRedisScript<>(luaScript, String.class);
                List<String> keys = Collections.singletonList(key);
                String result = redisTemplate.execute(redisScript, keys, String.valueOf(fakeTimestamp));
                if (null != result) updateTimestamp(Long.parseLong(result));
            } catch (Exception e) {
                log.error("increaseTimestamp.syncTimestamp failed", e);
            }
        }, 1, 1, TimeUnit.MILLISECONDS);
    }

    public static synchronized void updateTimestamp(long newValue) {
        if (fakeTimestamp < newValue) fakeTimestamp = newValue;
    }

    public static long currentTimestamp() {
        return fakeTimestamp;
    }

}
