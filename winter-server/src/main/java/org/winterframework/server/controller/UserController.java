package org.winterframework.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.winterframework.data.redis.RedisTemplate;

/**
 * @author sven
 * Created on 2022/1/16 8:25 下午
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("/getUser")
    public Object getUser() {
        return redisTemplate.oper().get("abc");
    }
}
