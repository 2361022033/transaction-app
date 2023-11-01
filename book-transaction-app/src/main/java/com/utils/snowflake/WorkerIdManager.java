package com.utils.snowflake;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

@Slf4j
public class WorkerIdManager {

    private static long workerId;

    public static void init(RedisConnectionFactory connectionFactory, String keyPrefix) {
        RedisAtomicLong redisAtomicLong = new RedisAtomicLong(keyPrefix + "workerID", connectionFactory);
        long remoteWorkerID = redisAtomicLong.incrementAndGet();
        workerId = remoteWorkerID % SnowflakeConstant.MAX_WORKER_ID;
    }

    public static long getWorkerId() {
        return workerId;
    }

}


