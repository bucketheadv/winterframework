package org.winterframework.tio.server.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.utils.hutool.Snowflake;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.server.handler.IWsMsgHandler;
import org.winterframework.core.tool.JsonTool;
import org.winterframework.tio.server.support.Const;
import org.winterframework.tio.server.entity.Msg;
import org.winterframework.tio.server.entity.User;
import org.winterframework.tio.server.service.MsgService;
import org.winterframework.tio.server.service.UserService;

/**
 * @author qinglin.liu
 * created at 2023/12/26 21:15
 */
@Slf4j
@Component
@AllArgsConstructor
public class ServerWsMsgHandler implements IWsMsgHandler {

    private MsgService msgService;

    private UserService userService;

    private final Snowflake snowflake = new Snowflake(0, 0);

    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        String clientIp = httpRequest.getClientIp();
        String myName = httpRequest.getParam("name");
        Tio.bindUser(channelContext, myName);

        log.debug("收到来自 {} 的握手包\r\n{}", clientIp, httpRequest);
        return httpResponse;
    }

    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        String username = channelContext.userid;
        User user = new User();
        user.setUsername(username);
        user.setNode(channelContext.getServerNode().toString());
        userService.addOnlineUser(user);
        log.info("用户 {} 加入，当前总用户 {}", username, userService.getOnlineUserCount());

        Tio.bindGroup(channelContext, Const.GROUP_ID);
        log.info("用户 {} 加入群组 {}", channelContext.userid, Const.GROUP_ID);
        Msg msg = new Msg();
        msg.setTo(Const.GROUP_ID);
        msgService.sendToGroupChannel(msg);
    }

    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }

    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        String username = channelContext.userid;
        userService.delOnlineUser(username);
        log.info("用户 {} 离开, 当前总用户 {}", username, userService.getOnlineUserCount());
        return null;
    }

    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
        Msg msg = JsonTool.parseObject(text, Msg.class);
        msg.setFrom(channelContext.userid);
        msg.setTimestamp(System.currentTimeMillis());
        if (Const.Action.HEART_BEAT_REQ.val() != msg.getAction()) {
            msg.setId(String.valueOf(snowflake.nextId()));
            msgService.dispatch(msg);
        }
        return null;
    }
}
