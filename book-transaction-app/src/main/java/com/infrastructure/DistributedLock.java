package com.infrastructure;

import cn.hutool.extra.spring.SpringUtil;
import lombok.SneakyThrows;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class DistributedLock {
    private static RedissonClient redissonClient = null;
    private static final String KEY_PREFIX = "lock:";
    private static final int LOCK_FAIL_ERROR_CODE = 1001;

    public static RedissonClient getRedissonClient() {
        if (Objects.isNull(redissonClient)) {
            return redissonClient = SpringUtil.getBean(RedissonClient.class);
        }
        return redissonClient;
    }


    /**
     * 尝试获取分布式锁，执行业务逻辑后自动释放锁
     *
     * @param key
     * @param runnable
     */
    public static void lock(String key, Runnable runnable) {
        RLock lock = getRedissonClient().getLock(KEY_PREFIX + key);
        try {
            lock.lock();
            runnable.run();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 尝试获取分布式锁，执行业务逻辑后自动释放锁
     *
     * @param key
     * @param wait     获取锁最大等待时间，单位秒，可以为空
     * @param consumer
     */
    @SneakyThrows
    public static void tryLock(String key, Long wait, Consumer<Boolean> consumer) {
        boolean success = false;
        RLock lock = getRedissonClient().getLock(KEY_PREFIX + key);
        try {
            if (Objects.isNull(wait)) {
                success = lock.tryLock();
            } else {
                success = lock.tryLock(wait, TimeUnit.SECONDS);
            }
            consumer.accept(success);
        } finally {
            if (success) {
                lock.unlock();
            }
        }
    }

    /**
     * 尝试获取分布式锁成功后，执行业务逻辑后自动释放锁
     *
     * @param key
     * @param supplier
     */
    public static <T> T lock(String key, Supplier<T> supplier) {
        RLock lock = getRedissonClient().getLock(KEY_PREFIX + key);
        try {
            lock.lock();
            return supplier.get();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 尝试获取分布式锁成功后，执行业务逻辑后自动释放锁
     *
     * @param key
     * @param wait     获取锁最大等待时间，单位秒，可以为空
     * @param supplier
     * @param failMsg  获取锁失败提示
     */
    @SneakyThrows
    public static <T> T tryLock(String key, Long wait, Supplier<T> supplier, String failMsg) {
        boolean success = false;
        RLock lock = getRedissonClient().getLock(KEY_PREFIX + key);
        try {
            if (Objects.isNull(wait)) {
                success = lock.tryLock();
            } else {
                success = lock.tryLock(wait, TimeUnit.SECONDS);
            }
            if (!success) {
                throw new ServiceExcpetion(LOCK_FAIL_ERROR_CODE, failMsg);
            }
            return supplier.get();
        } finally {
            if (success) {
                lock.unlock();
            }
        }
    }

    /**
     * 尝试获取分布式锁成功后，执行业务逻辑后自动释放锁
     *
     * @param key
     * @param wait    获取锁最大等待时间，单位秒，可以为空
     * @param data
     * @param failMsg 获取锁失败提示
     */
    @SneakyThrows
    public static <T, R> R tryLock(String key, Long wait, T data, Function<T, R> function, String failMsg) {
        boolean success = false;
        RLock lock = getRedissonClient().getLock(KEY_PREFIX + key);
        try {
            if (Objects.isNull(wait)) {
                success = lock.tryLock();
            } else {
                success = lock.tryLock(wait, TimeUnit.SECONDS);
            }
            if (!success) {
                throw new ServiceExcpetion(LOCK_FAIL_ERROR_CODE, failMsg);
            }
            return function.apply(data);
        } finally {
            if (success) {
                lock.unlock();
            }
        }
    }
}
