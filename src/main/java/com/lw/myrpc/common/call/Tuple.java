package com.lw.myrpc.common.call;

/**
 * @ClassName : Tuple
 * @Description :
 * @Author : lw.tar.gz
 * @Date: 2020-06-10 16:38
 */
public class Tuple<K,T> {

    K left;
    T right;

    public Tuple(K left, T right) {
        this.left = left;
        this.right = right;
    }
}
