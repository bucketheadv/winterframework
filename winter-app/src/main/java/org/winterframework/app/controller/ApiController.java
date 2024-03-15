package org.winterframework.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.winterframework.core.support.ApiResponse;
import org.winterframework.data.redis.core.JedisTemplate;

/**
 * @author qinglin.liu
 * created at 2024/3/7 19:16
 */
@RestController
public class ApiController {
    @Autowired
    private JedisTemplate jedisTemplate;

    @GetMapping("/api")
    public ApiResponse<?> api() {
        String value = jedisTemplate.get("api");
        return ApiResponse.builder().data(value).build();
    }
}
