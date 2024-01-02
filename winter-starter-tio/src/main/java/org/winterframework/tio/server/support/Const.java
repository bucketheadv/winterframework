package org.winterframework.tio.server.support;

/**
 * @author qinglin.liu
 * created at 2023/12/20 19:45
 */
public class Const {
    public static final String GROUP_ID = "group-id";

    public static final String WS_MSG_TOPIC_CHANNEL = "WS_MSG_TOPIC_CHANNEL";

    public static final String WS_GROUP_MSG_TOPIC_CHANNEL = "WS_GROUP_MSG_TOPIC_CHANNEL";

    public static final String WS_USER_PREFIX = "WS_USER_PREFIX:";

    public static final String WS_USER_ONLINE = "WS_USER_ONLINE";

    /**
     * 客户端和服务端的交互动作枚举
     */
    public enum Action {
        /**
         * 点对点消息请求
         */
        P2P_MSG_REQ(3),
        /**
         * 点对点消息响应
         */
        P2P_MSG_RESP(33),
        /**
         * 群组消息请求
         */
        GROUP_MSG_REQ(4),
        /**
         * 群组消息响应
         */
        GROUP_MSG_RESP(44),
        /**
         * 心跳信息qingqiu
         */
        HEART_BEAT_REQ(9);


        private final int action;

        Action(int action) {
            this.action = action;
        }

        public int val(){
            return this.action;
        }
    }
}
