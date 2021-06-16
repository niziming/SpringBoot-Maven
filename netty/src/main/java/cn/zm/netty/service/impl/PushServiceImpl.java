package cn.zm.netty.service.impl;

import cn.zm.netty.handler.NettyHandler;
import cn.zm.netty.service.PushService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class PushServiceImpl implements PushService {
    @Override
    public void pushMsgToOne(String userId, String msg) {
        ConcurrentHashMap<String, Channel> userChannelMap = NettyHandler.getUserChannelMap();
        Channel channel = userChannelMap.get(userId);
        Assert.notNull(channel, userId+"-用户不存在");
        channel.writeAndFlush(new TextWebSocketFrame(msg));
    }

    @Override
    public void pushMsgToAll(String msg) {
        NettyHandler.getChannelGroup().writeAndFlush(new TextWebSocketFrame(msg));
    }
}
