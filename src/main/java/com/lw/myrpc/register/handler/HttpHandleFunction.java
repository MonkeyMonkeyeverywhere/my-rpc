package com.lw.myrpc.register.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author wliu10
 */
@FunctionalInterface
public interface HttpHandleFunction {

    void execute(ChannelHandlerContext channelHandlerContext, FullHttpRequest request);

}
