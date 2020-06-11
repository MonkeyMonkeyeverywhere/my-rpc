package com.lw.myrpc.register.util;

import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.Map;

/**
 * @ClassName : ResponseUtil
 * @Description :
 * @Author : lw.tar.gz
 * @Date: 2020-06-10 13:30
 */
public class HttpUtil {

    public static String getRequestBody(FullHttpRequest request) {
        ByteBuf buf = request.content();
        return buf.toString(CharsetUtil.UTF_8);
    }

    public static void echo(ChannelHandlerContext ctx, String msg) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    public static Map<String, String> parseParam(String uri) {
        Map<String, String> pair = Maps.newHashMap();
        if(!uri.contains("?")){
            return pair;
        }
        String substring = uri.substring(uri.indexOf("?")+1);
        String[] split = substring.split("&");
        for (int i = 0; i < split.length; i++) {
            String[] keyValue = split[i].split("=");
            pair.put(keyValue[0], keyValue[1]);
        }
        return pair;
    }
}
