package com.example.blog.utils;

import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private final Logger log = LoggerFactory.getLogger(RedisUtil.class);

    private final String DEFAULT_KEY_PREFIX = "__NOTIFICATION__";
//    private final int EXPIRE_TIME = 7;
    private final TimeUnit EXPIRE_TIME_TYPE = TimeUnit.DAYS;

    public <K,V> void lPush(K key,V value){
        try{
            if(value!=null){
//                redisTemplate.opsForValue().set(DEFAULT_KEY_PREFIX+key, JSON.toJSONString(value));
                redisTemplate.opsForList().leftPush(DEFAULT_KEY_PREFIX+key,JSON.toJSONString(value));
            }
        }catch (Exception e){
            log.error(e.getMessage());
//            throw new Exception("Push value to redis failed");
        }
    }

    public <K,V> void lPush(K key,V value,long timeout){
        try{
            if(value!=null){
//                redisTemplate.opsForValue().set(DEFAULT_KEY_PREFIX+key, JSON.toJSONString(value),timeout,EXPIRE_TIME_TYPE);
                redisTemplate.opsForList().leftPush(DEFAULT_KEY_PREFIX+key,JSON.toJSONString(value));
                redisTemplate.expire(DEFAULT_KEY_PREFIX+key,timeout,EXPIRE_TIME_TYPE);
            }
        }catch (Exception e){
            log.error(e.getMessage());
//            throw new Exception("Push value to redis failed");
        }
    }

    public <K,V> List<V> lGet(K key,Class<V> vClass){
        try{
            List<String> list = redisTemplate.opsForList().range(DEFAULT_KEY_PREFIX+key,0,-1);
            List<V> ans = new ArrayList<>();
            for(String s:list){
                ans.add(JSONObject.parseObject(s,vClass));
            }
//            lRemove(key);
            return ans;
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    public <K,V> List<V> lGet(K key,int count,Class<V> vClass){
        try{
            List<String> list = redisTemplate.opsForList().range(DEFAULT_KEY_PREFIX+key,0,count);
            List<V> ans = new ArrayList<>();
            for(String s:list){
                ans.add(JSONObject.parseObject(s,vClass));
            }
            return ans;
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    public <K> int lCount(K key){
        try{
            return redisTemplate.opsForList().range(DEFAULT_KEY_PREFIX+key,0,-1).size();
        }catch (Exception e){
            log.error(e.getMessage());
            return -1;
        }
    }

    public <K> void lRemove(K key){
        redisTemplate.delete(DEFAULT_KEY_PREFIX+key);
    }

}
