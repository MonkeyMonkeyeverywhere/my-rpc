package com.lw.myrpc.common.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @ClassName : KryoSerializer
 * @Description :
 * @Author : lw.tar.gz
 * @Date: 2020-06-11 10:06
 */
public class KryoSerializer implements MySerializer {

    @Override
    public byte[] serialize(Object obj) {
        Kryo kryo = kryoLocal.get();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Output output = new Output(bos);
        kryo.writeClassAndObject(output, obj);
        output.close();
        return bos.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] bytes) {
        Kryo kryo = kryoLocal.get();
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Input input = new Input(bis);
        input.close();
        return (T) kryo.readClassAndObject(input);
    }

    private final static ThreadLocal<Kryo> kryoLocal = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            kryo.setReferences(true);// 默认值为 true, 强调作用
            kryo.setRegistrationRequired(false);// 默认值为 false, 强调作用
            return kryo;
        }
    };

}
