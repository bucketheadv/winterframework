package org.winterframework.gateway.service;

/**
 * @author qinglin.liu
 * created at 2023/11/26 18:35
 */
public interface UserService {
    Long getUserIdByToken(String token);
}
