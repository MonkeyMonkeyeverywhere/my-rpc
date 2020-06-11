package com.lw.myrpc.common.call;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName : Caller
 * @Description :
 * @Author : lw.tar.gz
 * @Date: 2020-06-10 16:35
 */
@Data
public class Caller implements Serializable {

    private String className;
    private String methodName;
    private Class<?>[] paramTypes;
    private Object[] params;

    private Object result;

    public Caller() {
    }

    public Caller(String className, String methodName, Class<?>[] paramTypes, Object[] params) {
        this.className = className;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.params = params;
    }

}
