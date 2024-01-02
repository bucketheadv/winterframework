package org.winterframework.tio.server.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.TioConfig;
import org.tio.core.intf.Packet;
import org.tio.core.stat.IpStat;
import org.tio.core.stat.IpStatListener;

/**
 * @author qinglin.liu
 * created at 2023/12/20 19:36
 */
@Slf4j
@Component
public class WinterIpStatListener implements IpStatListener {
    @Override
    public void onExpired(TioConfig tioConfig, IpStat ipStat) {
    }

    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect, IpStat ipStat) throws Exception {
    }

    @Override
    public void onDecodeError(ChannelContext channelContext, IpStat ipStat) {
    }

    @Override
    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean isSentSuccess, IpStat ipStat) throws Exception {
    }

    @Override
    public void onAfterDecoded(ChannelContext channelContext, Packet packet, int packetSize, IpStat ipStat) throws Exception {
    }

    @Override
    public void onAfterReceivedBytes(ChannelContext channelContext, int receivedBytes, IpStat ipStat) throws Exception {
    }

    @Override
    public void onAfterHandled(ChannelContext channelContext, Packet packet, IpStat ipStat, long cost) throws Exception {
    }
}
