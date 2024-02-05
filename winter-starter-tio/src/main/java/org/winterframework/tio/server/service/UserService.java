package org.winterframework.tio.server.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.winterframework.core.tool.JSONTool;
import org.winterframework.data.redis.core.JedisTemplate;
import org.winterframework.tio.server.support.Const;
import org.winterframework.tio.server.entity.User;

/**
 * @author qinglin.liu
 * created at 2023/12/27 19:07
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private JedisTemplate jedisTemplate;

    public void addOnlineUser(User user) {
        jedisTemplate.doInMasterPipeline(pipeline -> {
            pipeline.set(Const.WS_USER_PREFIX + user.getUsername(), JSONTool.toJSONString(user));
            pipeline.sadd(Const.WS_USER_ONLINE, user.getUsername());
        });
    }

    public long getOnlineUserCount() {
        return jedisTemplate.scard(Const.WS_USER_ONLINE);
    }

    public void delOnlineUser(String username) {
        jedisTemplate.doInMasterPipeline(pipeline -> {
            pipeline.del(Const.WS_USER_PREFIX + username);
            pipeline.srem(Const.WS_USER_ONLINE, username);
        });
    }
}
