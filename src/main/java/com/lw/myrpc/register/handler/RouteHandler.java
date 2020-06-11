package com.lw.myrpc.register.handler;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.lw.myrpc.register.DiscoverServer;
import com.lw.myrpc.register.Instance;
import com.lw.myrpc.register.util.HttpUtil;
import io.netty.handler.codec.http.HttpMethod;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

/**
 * @author wliu10
 */
@Getter
public enum RouteHandler {

    DEFAULT_HANDLER(null, null) {
        @Override
        HttpHandleFunction handler() {
            return (ctx, request) -> {
                // do nothing
                HttpUtil.echo(ctx, "out of my duty");
            };
        }
    },

    REGISTER("register", HttpMethod.POST) {
        @Override
        HttpHandleFunction handler() {
            return (ctx, request) -> {
                String requestBody = HttpUtil.getRequestBody(request);
                Instance instance = JSON.parseObject(requestBody, Instance.class);
                Set<Instance> instances = DiscoverServer.data.getOrDefault(instance.getServiceName(), Sets.newHashSet());
                instances.add(instance);
                DiscoverServer.data.put(instance.getServiceName(), instances);
                HttpUtil.echo(ctx, "success");
            };
        }
    },

    GET_INSTANCES("getInstances", HttpMethod.GET) {
        @Override
        HttpHandleFunction handler() {
            return (ctx, request) -> {
                Map<String, String> param = HttpUtil.parseParam(request.uri());
                Set<Instance> instances = DiscoverServer.data.get(param.get("serviceName"));
                HttpUtil.echo(ctx, JSON.toJSONString(instances));
            };
        }
    },
    ;

    private HttpLabel httpLabel;

    RouteHandler(String uri, HttpMethod method) {
        this.httpLabel = new HttpLabel(uri, method);
    }

    abstract HttpHandleFunction handler();
}
