package com.utils.snowflake;

public interface SnowflakeConstant {

    // 时间戳预留43位，2的43次方=8796093022208 / 1000 / 3600 / 24 / 365 = 可用278.9年

    /**
     * 10位的机器id
     */
    long WORKER_ID_BITS = 10L;

    /**
     * 每毫秒内产生的id数: 2的10次方个
     */
    long SEQUENCE_BITS = 10L;

    long MAX_WORKER_ID = ~(-1 << WORKER_ID_BITS);

    long MAX_SEQUENCE = ~(-1 << SEQUENCE_BITS);

    long WORKER_ID_LEFT_SHIFT = SnowflakeConstant.SEQUENCE_BITS;

    long TIMESTAMP_LEFT_SHIFT = SnowflakeConstant.SEQUENCE_BITS + SnowflakeConstant.WORKER_ID_BITS;

    String REDIS_KEY_PREFIX = ":snowflake:";

}
