package org.winterframework.tio.server.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.utils.lock.SetWithLock;
import org.tio.websocket.common.WsPacket;
import org.tio.websocket.common.WsResponse;
import org.winterframework.core.tool.CollectionTool;
import org.winterframework.core.tool.JSONTool;
import org.winterframework.data.redis.core.JedisTemplate;
import org.winterframework.tio.server.support.Const;
import org.winterframework.tio.server.configuration.TioInitConfig;
import org.winterframework.tio.server.entity.Msg;

import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author qinglin.liu
 * created at 2023/12/27 10:06
 */
@Slf4j
@Component
@AllArgsConstructor
public class MsgService {
    private JedisTemplate jedisTemplate;

    private RocketMQTemplate rocketMQTemplate;

    public boolean existUser(String userId) {
        SetWithLock<ChannelContext> set = Tio.getByUserid(TioInitConfig.getTioServerConfig(), userId);
        return set != null && set.size() >= 1;
    }

    public void sendToUserChannel(String userId, Msg msg) {
        if (!existUser(userId)) {
            rocketMQTemplate.syncSend(Const.WS_MSG_TOPIC_CHANNEL, JSONTool.toJSONString(msg));
            return;
        }
        sendToUser(userId, msg);
    }

    public void sendToUser(String userId, Msg msg) {
        SetWithLock<ChannelContext> set = Tio.getByUserid(TioInitConfig.getTioServerConfig(), userId);
        if (set == null) {
            return;
        }
        ReentrantReadWriteLock.ReadLock readLock = set.readLock();
        readLock.lock();
        try {
            Set<ChannelContext> channels = set.getObj();
            for (ChannelContext channel : channels) {
                send(channel, msg);
            }
        } finally {
            readLock.unlock();
        }
    }

    public void sendToGroupChannel(Msg msg) {
        rocketMQTemplate.syncSend(Const.WS_GROUP_MSG_TOPIC_CHANNEL, JSONTool.toJSONString(msg));
    }

    public void sendToGroup(String group, Msg msg) {
        if (msg == null) {
            return;
        }
        SetWithLock<ChannelContext> withLockChannels = Tio.getByGroup(TioInitConfig.getTioServerConfig(), group);
        if (withLockChannels == null) {
            return;
        }
        ReentrantReadWriteLock.ReadLock readLock = withLockChannels.readLock();
        readLock.lock();
        try {
            Set<ChannelContext> channels = withLockChannels.getObj();
            if (CollectionTool.isNotEmpty(channels)) {
                for (ChannelContext channel : channels) {
                    sendToUserChannel(channel.userid, msg);
                }
            }
        } finally {
            readLock.unlock();
        }
    }

    public void send(ChannelContext channelContext, Msg msg) {
        if (channelContext == null) {
            return;
        }
        WsResponse response = WsResponse.fromText(JSONTool.toJSONString(msg), WsPacket.CHARSET_NAME);
        Tio.sendToId(channelContext.tioConfig, channelContext.getId(), response);
    }

    public void dispatch(Msg msg) {
        int action = msg.getAction();
        Msg respMsg = new Msg();
        // 响应信息则直接返回给客户端即可
        if (action % 11 == 0) {
            respMsg.setMsg(msg.getMsg());
            respMsg.setAction(msg.getAction());
            respMsg.setStatus(msg.getStatus());
            respMsg.setId(msg.getId());
            respMsg.setFrom(msg.getFrom());
            respMsg.setTo(msg.getTo());
            sendToUserChannel(msg.getTo(), respMsg);
        } else {
            respMsg.setFrom(msg.getFrom());
            respMsg.setTo(msg.getTo());
            respMsg.setStatus("200");
            respMsg.setId(msg.getId());
            respMsg.setMsg(msg.getMsg());
            if (action == Const.Action.P2P_MSG_REQ.val()) {
                respMsg.setAction(Const.Action.P2P_MSG_RESP.val());
                sendToUser(msg.getTo(), msg);
            } else if (action == Const.Action.GROUP_MSG_REQ.val()) {
                respMsg.setAction(Const.Action.GROUP_MSG_RESP.val());
                sendToGroupChannel(msg);
            }
        }
    }
}
