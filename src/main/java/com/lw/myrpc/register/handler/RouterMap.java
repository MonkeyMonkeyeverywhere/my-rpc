package com.lw.myrpc.register.handler;

import io.netty.handler.codec.http.HttpMethod;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @ClassName : Router
 * @Description :
 * @Author : lw.tar.gz
 * @Date: 2020-06-10 10:31
 */
@Slf4j
public class RouterMap {

    private static Map<HttpLabel, RouteHandler> handlerMap =
            Arrays.stream(RouteHandler.values())
                    .collect(Collectors.toMap(RouteHandler::getHttpLabel, Function.identity()));

    private static Set<String> whiteList = handlerMap.keySet().stream().map(l -> l.getUri()).collect(Collectors.toSet());

    public static HttpHandleFunction route(String uri, HttpMethod method) {
        String labelUri = uri;
        if (labelUri.startsWith("/")) {
            labelUri = labelUri.substring(1);
        }
        if (labelUri.contains("?")) {
            labelUri = labelUri.substring(0, labelUri.indexOf("?"));
        }
        if (!whiteList.contains(labelUri)) {
            return RouteHandler.DEFAULT_HANDLER.handler();
        }
        return handlerMap.get(new HttpLabel(labelUri, method)).handler();
    }

}
