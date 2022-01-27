package org.winterframework.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.winterframework.data.redis.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author sven
 * Created on 2022/1/16 8:25 下午
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("/getUser")
    public Object getUser() {
        return redisTemplate.get("abc");
    }
}
