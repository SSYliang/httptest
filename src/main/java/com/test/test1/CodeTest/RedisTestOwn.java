package com.test.test1.CodeTest;

import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisTestOwn {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.132.129",6379);
//        jedis.auth("123456");

        jedis.lpush("fruits","apple");
        jedis.lpush("fruits","orange");
        jedis.lpush("fruits","banana");

        List<String> fruits = jedis.lrange("fruits",0,2);
        for (String fruit: fruits){
            System.out.println(fruit);
        }
    }
}
