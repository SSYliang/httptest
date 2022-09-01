package com.test.test1.Util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

    private static String addr = "192.168.132.129";
    private static int port = 6379;
    private static int maxActive = 1024;
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值是8。
    private static int maxIdle = 200;
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
    private static int maxWait = 10000;
    //连接超时的时间
    private static int timeOut = 10000;
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean testOnBorrow = true;
    private static JedisPool jedisPool = null;
    /**
     * 初始化连接池
     */
    static {

        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(maxActive);
            config.setMaxIdle(maxIdle);
            config.setMaxWaitMillis(maxWait);
            config.setTestOnBorrow(testOnBorrow);
            jedisPool = new JedisPool(config,addr,port,timeOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取实例
     */
    public synchronized static Jedis getJedis(){
        try {
            if (jedisPool != null){
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放资源
     */
    public static void returnResource(final Jedis jedis){
        if (jedis != null){
            jedisPool.returnResource(jedis);
        }
    }
}
