package com.lw.myrpc.register.handler;

import io.netty.handler.codec.http.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName : HttpLabel
 * @Description :
 * @Author : lw.tar.gz
 * @Date: 2020-06-10 10:47
 */
@Data
@AllArgsConstructor
public class HttpLabel {
    private String uri;
    private HttpMethod method;
}
