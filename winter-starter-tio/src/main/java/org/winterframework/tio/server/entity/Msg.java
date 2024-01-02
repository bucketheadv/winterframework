package org.winterframework.tio.server.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qinglin.liu
 * created at 2023/12/26 21:09
 */
@Data
public class Msg implements Serializable {
    private String id;

    private int action;

    private String msg;

    private String from;

    private String to;

    private String status;

    private Long timestamp;
}
