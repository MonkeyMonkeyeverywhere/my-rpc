package com.lw.myrpc.consumer.proxy;

import com.lw.myrpc.common.call.Caller;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @ClassName : MethodProxy
 * @Description :
 * @Author : lw.tar.gz
 * @Date: 2020-06-10 16:58
 */
public class MyRpcInvocationHandler implements InvocationHandler {

    private Class<?> classType;
    private String host;
    private Integer port;

    public Class<?> getClassType() {
        return classType;
    }
    public MyRpcInvocationHandler(Class<?> classType, String host, Integer port) {
        this.classType = classType;
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            try {
                return method.invoke(this, args);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        } else {
            return doInvoke(method, args);
        }
        return null;
    }

    private Object doInvoke(Method method, Object[] args) {
        // todo netty call
        // todo how to serialize
        Caller call = new Caller(classType.getName(), method.getName(), method.getParameterTypes(), args);

        return null;
    }
}
