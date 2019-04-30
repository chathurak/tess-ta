package com.languagematters.tessta.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.HashMap;

@RestController
public class AdminController {

    private Jedis jedis = new Jedis("localhost");

    @RequestMapping(value = "/api/admin/path", method = RequestMethod.GET)
    public HashMap<String, Object> getDirPath(@RequestParam(value = "dir") String dir) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("savedPath", jedis.get(dir));
        return map;
    }

}
