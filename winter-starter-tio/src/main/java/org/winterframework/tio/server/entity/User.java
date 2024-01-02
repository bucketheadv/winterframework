package org.winterframework.tio.server.entity;

import lombok.Data;

import java.util.Set;

/**
 * @author qinglin.liu
 * created at 2023/12/26 21:10
 */
@Data
public class User {
    private String username;

    private Set<String> group;

    /**
     * 所在节点
     */
    private String node;
}
