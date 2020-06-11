package com.lw.myrpc.register;

import com.lw.myrpc.register.handler.HttpServerReceiver;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName : discover
 * @Description :
 * @Author : lw.tar.gz
 * @Date: 2020-06-10 09:54
 */
@Slf4j
public class DiscoverServer {

    private int id;
    private int port;
    /**
     * instance data mapping service name and metadata
     */
    public final static Map<String, Set<Instance>> data = new ConcurrentHashMap<>();

    public DiscoverServer() {
        this.port = 18080;
    }

    public DiscoverServer(int port) {
        this.port = port;
    }

    public void start() {
        start(port);
    }

    public void start(int port) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group).channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new HttpServerCodec());
                            ch.pipeline().addLast(new HttpObjectAggregator(512 * 1024));
                            ch.pipeline().addLast(new HttpServerReceiver());
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            log.info("======= DiscoverServer start =======");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("======= DiscoverServer start error =======");
        } finally {
            log.info("======= DiscoverServer shut down =======");
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new DiscoverServer().start();
    }
}
