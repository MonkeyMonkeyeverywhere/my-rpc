package com.lw.myrpc.common.annotation;

import java.lang.annotation.*;

/**
 * @ClassName : MyRpcService
 * @Description :
 * @Author : lw.tar.gz
 * @Date: 2020-06-10 16:21
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyRpcService {

    String value() default "";

}
