package com.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @description: RedisUtil
 * @author: zw
 * @date: 2022/4/26 3:36 下午
 */
@Component
public class RedisUtils {

    @Resource(name = "redisTemplateSerializer")
    private RedisTemplate redisTemplate;
    private static double size = Math.pow(2.0D, 32.0D);
    private static final String bloomFilterName = "isVipBloom";

    public RedisUtils() {
    }

    public boolean setBit(String key, long offset, boolean isShow) {
        boolean result = false;

        try {
            ValueOperations<Serializable, Object> operations = this.redisTemplate.opsForValue();
            operations.setBit(key, offset, isShow);
            result = true;
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return result;
    }

    public boolean getBit(String key, long offset) {
        boolean result = false;

        try {
            ValueOperations<Serializable, Object> operations = this.redisTemplate.opsForValue();
            result = operations.getBit(key, offset);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return result;
    }

    public boolean set(String key, Object value) {
        boolean result = false;

        try {
            ValueOperations<Serializable, Object> operations = this.redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return result;
    }

    public boolean set(String key, Object value, Long expireTime) {
        boolean result = false;

        try {
            ValueOperations<Serializable, Object> operations = this.redisTemplate.opsForValue();
            operations.set(key, value);
            this.redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return result;
    }

    public boolean setForHours(String key, Long expireTime, TimeUnit seconds) {
        boolean result = false;

        try {
            this.redisTemplate.expire(key, expireTime, seconds);
            result = true;
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return result;
    }

    public void remove(String... keys) {
        String[] var2 = keys;
        int var3 = keys.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String key = var2[var4];
            this.remove(key);
        }

    }

    public void remove(String key) {
        if (this.exists(key)) {
            this.redisTemplate.delete(key);
        }

    }

    public boolean exists(String key) {
        return this.redisTemplate.hasKey(key);
    }

    public Object get(String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = this.redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    public void hmSet(String key, Object hashKey, Object value, Long expireTime) {
        HashOperations<String, Object, Object> hash = this.redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
        this.redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = this.redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    public Boolean hmExit(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = this.redisTemplate.opsForHash();
        return hash.hasKey(key, hashKey);
    }

    public void lPush(String k, Object v) {
        ListOperations<String, Object> list = this.redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    public List<Object> lPop(String k) {
        ListOperations<String, Object> list = this.redisTemplate.opsForList();
        return (List) list.rightPop(k);
    }

    public List<Object> lRange(String k, long l, long l1) {
        ListOperations<String, Object> list = this.redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    public void add(String key, Object value) {
        SetOperations<String, Object> set = this.redisTemplate.opsForSet();
        set.add(key, new Object[]{value});
    }

    public Set<Object> setMembers(String key) {
        SetOperations<String, Object> set = this.redisTemplate.opsForSet();
        return set.members(key);
    }

    public void zAdd(String key, Object value, double scoure) {
        ZSetOperations<String, Object> zset = this.redisTemplate.opsForZSet();
        zset.add(key, value, scoure);
    }

    public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
        ZSetOperations<String, Object> zset = this.redisTemplate.opsForZSet();
        this.redisTemplate.opsForValue();
        return zset.rangeByScore(key, scoure, scoure1);
    }

    public void saveDataToRedis(String name) {
        double index = Math.abs((double) name.hashCode() % size);
        long indexLong = (new Double(index)).longValue();
        this.setBit("availableUsers", indexLong, true);
    }

    public boolean getDataToRedis(String name) {
        double index = Math.abs((double) name.hashCode() % size);
        long indexLong = (new Double(index)).longValue();
        return this.getBit("availableUsers", indexLong);
    }

    public Long zRank(String key, Object value) {
        ZSetOperations<String, Object> zset = this.redisTemplate.opsForZSet();
        return zset.rank(key, value);
    }

    public Set<ZSetOperations.TypedTuple<Object>> zRankWithScore(String key, long start, long end) {
        ZSetOperations<String, Object> zset = this.redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<Object>> ret = zset.rangeWithScores(key, start, end);
        return ret;
    }

    public Double zSetScore(String key, Object value) {
        ZSetOperations<String, Object> zset = this.redisTemplate.opsForZSet();
        return zset.score(key, value);
    }

    public void incrementScore(String key, Object value, double scoure) {
        ZSetOperations<String, Object> zset = this.redisTemplate.opsForZSet();
        zset.incrementScore(key, value, scoure);
    }

    public Set<ZSetOperations.TypedTuple<Object>> reverseZRankWithScore(String key, long start, long end) {
        ZSetOperations<String, Object> zset = this.redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<Object>> ret = zset.reverseRangeByScoreWithScores(key, (double) start, (double) end);
        return ret;
    }

    public Set<ZSetOperations.TypedTuple<Object>> reverseZRankWithRank(String key, long start, long end) {
        ZSetOperations<String, Object> zset = this.redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<Object>> ret = zset.reverseRangeWithScores(key, start, end);
        return ret;
    }

    public boolean decr(String key, int value) {
        boolean result = false;

        try {
            ValueOperations<Serializable, Object> operations = this.redisTemplate.opsForValue();
            operations.increment(key, (long) (-value));
            result = true;
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return result;
    }

    public boolean incr(String key) {
        boolean result = false;

        try {
            ValueOperations<Serializable, Object> operations = this.redisTemplate.opsForValue();
            operations.increment(key, 1L);
            result = true;
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return result;
    }

    public Boolean bloomFilterAdd(int value) {
        DefaultRedisScript<Boolean> bloomAdd = new DefaultRedisScript();
        bloomAdd.setScriptSource(new ResourceScriptSource(new ClassPathResource("bloomFilterAdd.lua")));
        bloomAdd.setResultType(Boolean.class);
        List<Object> keyList = new ArrayList();
        keyList.add("isVipBloom");
        keyList.add(value + "");
        Boolean result = (Boolean) this.redisTemplate.execute(bloomAdd, keyList, new Object[0]);
        return result;
    }

    public Boolean bloomFilterExists(int value) {
        DefaultRedisScript<Boolean> bloomExists = new DefaultRedisScript();
        bloomExists.setScriptSource(new ResourceScriptSource(new ClassPathResource("bloomFilterExist.lua")));
        bloomExists.setResultType(Boolean.class);
        List<Object> keyList = new ArrayList();
        keyList.add("isVipBloom");
        keyList.add(value + "");
        Boolean result = (Boolean) this.redisTemplate.execute(bloomExists, keyList, new Object[0]);
        return result;
    }

    public Boolean bloomFilterAdd(String filterName, int value) {
        DefaultRedisScript<Boolean> bloomAdd = new DefaultRedisScript();
        bloomAdd.setScriptSource(new ResourceScriptSource(new ClassPathResource("bloomFilterAdd.lua")));
        bloomAdd.setResultType(Boolean.class);
        List<Object> keyList = new ArrayList();
        keyList.add(filterName);
        keyList.add(value + "");
        Boolean result = (Boolean) this.redisTemplate.execute(bloomAdd, keyList, new Object[0]);
        return result;
    }

    public Boolean bloomFilterExists(String filterName, int value) {
        DefaultRedisScript<Boolean> bloomExists = new DefaultRedisScript();
        bloomExists.setScriptSource(new ResourceScriptSource(new ClassPathResource("bloomFilterExist.lua")));
        bloomExists.setResultType(Boolean.class);
        List<Object> keyList = new ArrayList();
        keyList.add(filterName);
        keyList.add(value + "");
        Boolean result = (Boolean) this.redisTemplate.execute(bloomExists, keyList, new Object[0]);
        return result;
    }

    public Boolean getAndIncrLua(String key) {
        DefaultRedisScript<Boolean> bloomExists = new DefaultRedisScript();
        bloomExists.setScriptSource(new ResourceScriptSource(new ClassPathResource("secKillIncr.lua")));
        bloomExists.setResultType(Boolean.class);
        List<Object> keyList = new ArrayList();
        keyList.add(key);
        Boolean result = (Boolean) this.redisTemplate.execute(bloomExists, keyList, new Object[0]);
        return result;
    }
}
