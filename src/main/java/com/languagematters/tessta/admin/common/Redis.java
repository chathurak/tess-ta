package com.languagematters.tessta.admin.common;

import redis.clients.jedis.Jedis;

public class Redis {
    public static Jedis jedis = new Jedis("localhost");
}
