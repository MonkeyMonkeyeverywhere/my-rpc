package com.lw.myrpc.common.call;

import io.netty.channel.pool.ChannelPoolHandler;

/**
 * @ClassName : ChannelPoolHandlerFactory
 * @Description :
 * @Author : lw.tar.gz
 * @Date: 2020-06-11 11:28
 */
public interface ChannelPoolHandlerFactory<R> {

    ChannelPoolHandler createHandler(NettyPooledClient<R> client);

}
