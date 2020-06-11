package com.lw.myrpc.producer.reflection;

import com.lw.myrpc.common.call.Caller;

import java.lang.reflect.Method;

/**
 * @ClassName : CallerReceiver
 * @Description :
 * @Author : lw.tar.gz
 * @Date: 2020-06-11 14:27
 */
public class CallerReceiver {

    public Caller handle(Caller caller) throws Exception {
        String className = caller.getClassName();
        String methodName = caller.getMethodName();
        Object[] params = caller.getParams();
        Class<?>[] paramTypes = caller.getParamTypes();

        Class<?> classType = Class.forName(className);
        Method method = classType.getMethod(methodName, paramTypes);
        Object result = method.invoke(MyRpcServiceScanner.rpcImpMap.get(className), params);
        caller.setResult(result);
        return caller;
    }

}
