package org.winterframework.gateway.service.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.winterframework.core.tool.StringTool;
import org.winterframework.data.redis.core.JedisTemplate;
import org.winterframework.gateway.service.UserService;

/**
 * @author qinglin.liu
 * created at 2023/11/26 18:36
 */
@Slf4j
@Setter
@Service
public class UserServiceImpl implements UserService {

    private JedisTemplate jedisTemplate;

    @Override
    public Long getUserIdByToken(String token) {
        String key = "user:info:" + token;
        String val = jedisTemplate.get(key);
        if (StringTool.isBlank(val)) {
            return null;
        }
        return Long.parseLong(val);
    }
}
