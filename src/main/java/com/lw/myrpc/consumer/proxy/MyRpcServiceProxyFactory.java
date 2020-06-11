package com.lw.myrpc.consumer.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @ClassName : Invoker
 * @Description :
 * @Author : lw.tar.gz
 * @Date: 2020-06-10 16:54
 */
public class MyRpcServiceProxyFactory {

    public static Object getProxy(InvocationHandler handler) {
        Class<?> cls = ((MyRpcInvocationHandler) handler).getClassType();
        Object proxyInstance = Proxy.newProxyInstance(
                cls.getClassLoader(),
                new Class[]{cls},
                handler);
        return proxyInstance;
    }

}
