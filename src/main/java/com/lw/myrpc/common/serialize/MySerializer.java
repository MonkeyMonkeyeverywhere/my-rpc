package com.lw.myrpc.common.serialize;

public interface MySerializer {

    byte[] serialize(Object obj);

    <T> T deserialize(byte[] bytes);

}
