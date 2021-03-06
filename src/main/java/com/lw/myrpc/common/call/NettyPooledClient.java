package com.lw.myrpc.common.call;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName : MyHttpClient
 * @Description :
 * @Author : lw.tar.gz
 * @Date: 2020-06-11 10:26
 */
@Slf4j
@Data
public class NettyPooledClient<R> implements AutoCloseable {

    protected Bootstrap bootstrap = new Bootstrap();

    protected AbstractChannelPoolMap<InetSocketAddress, FixedChannelPool> channelPoolMap;

    protected ConcurrentHashMap<InetSocketAddress, AtomicInteger> releasedCounterMap = new ConcurrentHashMap<>();

    protected ConcurrentHashMap<Channel, InetSocketAddress> channelAddressMapping = new ConcurrentHashMap<>();

    protected long timeout;

    protected TimeUnit timeoutUnit;

    public NettyPooledClient(int poolSizePerAddress, ChannelPoolHandlerFactory<R> handlerFactory) {
        this(poolSizePerAddress, handlerFactory, 0, 0, TimeUnit.SECONDS);
    }

    public NettyPooledClient(int poolSizePerAddress, ChannelPoolHandlerFactory<R> handlerFactory, int nThreads, long timeout, TimeUnit timeoutUnit) {
        this.timeout = timeout;
        this.timeoutUnit = timeoutUnit;
        bootstrap.channel(NioSocketChannel.class)
                .group(new NioEventLoopGroup(nThreads))
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true);
        channelPoolMap = new AbstractChannelPoolMap<InetSocketAddress, FixedChannelPool>() {
            @Override
            protected FixedChannelPool newPool(InetSocketAddress key) {
                return new FixedChannelPool(bootstrap.remoteAddress(key),
                        handlerFactory.createHandler(NettyPooledClient.this),
                        poolSizePerAddress);
            }
        };
    }

    public Future<Channel> acquireChannel(InetSocketAddress address) {
        Promise<Channel> promise = newPromise();
        channelPoolMap.get(address).acquire().addListener(future -> {
            if (future.isSuccess()) {
                Channel channel = (Channel) future.get();
                channelAddressMapping.putIfAbsent(channel, address);
                increaseReleaseCount(address);
                promise.trySuccess(channel);
                log.debug("channel[{}] is acquired", channel.id());
            } else {
                promise.tryFailure(future.cause());
                log.error("failed to acquired channel due to ", future.cause());
            }
        });
        return promise;
    }

    public Future<Void> releaseChannel(Channel channel) {
        InetSocketAddress address = channelAddressMapping.remove(channel);
        return channelPoolMap.get(address).release(channel)
                .addListener(future -> {
                    decreaseReleaseCount(address);
                    log.debug("channel[{}] is released", channel.id());
                });
    }

    private void increaseReleaseCount(InetSocketAddress address) {
        if (!releasedCounterMap.containsKey(address)) {
            releasedCounterMap.putIfAbsent(address, new AtomicInteger());
        }
        releasedCounterMap.get(address).incrementAndGet();
    }

    private void decreaseReleaseCount(InetSocketAddress address) {
        if (!releasedCounterMap.containsKey(address)) {
            return;
        }
        releasedCounterMap.get(address).decrementAndGet();
    }

    protected <T> Promise<T> newPromise() {
        return bootstrap.config().group().next().newPromise();
    }

    @Override
    public void close() throws Exception {
        bootstrap.config().group().shutdownGracefully().sync();
        releasedCounterMap.clear();
        channelAddressMapping.clear();
    }

    public int getTotalPoolCnt() {
        return channelPoolMap.size();
    }

    public int getReleasedChannelCount(InetSocketAddress address) {
        if (!releasedCounterMap.containsKey(address)) {
            return 0;
        }
        return releasedCounterMap.get(address).get();
    }
}
