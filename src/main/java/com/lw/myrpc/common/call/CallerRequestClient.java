package com.lw.myrpc.common.call;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.pool.AbstractChannelPoolHandler;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName : CallerRequestClient
 * @Description :
 * @Author : lw.tar.gz
 * @Date: 2020-06-11 14:11
 */
public class CallerRequestClient extends NettyPooledClient {

    public CallerRequestClient(int poolSizePerAddress) {
        this(poolSizePerAddress, 0, 0, TimeUnit.SECONDS);
    }

    public CallerRequestClient(int poolSizePerAddress, int nThreads, long timeout, TimeUnit timeoutUnit) {
        super(poolSizePerAddress, RpcCallerHandler::new, nThreads, timeout, timeoutUnit);
    }

    public static class RpcCallerHandler extends AbstractChannelPoolHandler {

        private NettyPooledClient<Caller> client;

        public RpcCallerHandler(NettyPooledClient<Caller> client) {
            this.client = client;
        }

        @Override
        public void channelCreated(Channel channel) throws Exception {
            channel.pipeline()
                    .addLast(new SimpleChannelInboundHandler() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
                            // todo handle channel management
                        }
                    });
        }

    }

}
