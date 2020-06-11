package com.lw.myrpc.consumer;

import lombok.Data;

/**
 * @ClassName : Connector
 * @Description :
 * @Author : lw.tar.gz
 * @Date: 2020-06-11 10:20
 */
@Data
public class Connector {

    private String host;
    private Integer port;

    public Connector(String host, Integer port) {
        this.host = host;
        this.port = port;
    }



}
