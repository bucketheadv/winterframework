package org.winterframework.tio.server.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.winterframework.core.tool.JsonTool;
import org.winterframework.tio.server.support.Const;
import org.winterframework.tio.server.entity.Msg;
import org.winterframework.tio.server.service.MsgService;

import java.nio.charset.StandardCharsets;

/**
 * 单聊消息MQ
 * @author qinglin.liu
 * created at 2023/12/30 16:09
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = Const.WS_MSG_TOPIC_CHANNEL,
        consumerGroup = "user_channel_msg_group",
        messageModel = MessageModel.BROADCASTING // 使用广播模式，使每个consumer都会消费一次
)
public class UserChannelMessageConsumer implements RocketMQListener<MessageExt> {
    @Autowired
    private MsgService msgService;

    @Override
    public void onMessage(MessageExt messageExt) {
        String body = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        log.info("消费到跨服user channel消息: {}", body);
        Msg msg = JsonTool.parseObject(body, Msg.class);
        msgService.sendToUser(msg.getTo(), msg);
    }
}
