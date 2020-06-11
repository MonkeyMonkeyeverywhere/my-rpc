package com.lw.myrpc.register.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

/**
 * @ClassName : ServerHandler
 * @Description :
 * @Author : lw.tar.gz
 * @Date: 2020-06-10 10:28
 */
public class HttpServerReceiver extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String uri = request.uri();
        HttpMethod method = request.method();
        HttpHandleFunction routeFunc = RouterMap.route(uri, method);
        routeFunc.execute(ctx, request);
    }

}
