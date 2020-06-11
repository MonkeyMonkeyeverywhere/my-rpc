package com.lw.myrpc.common.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName : Customer
 * @Description :
 * @Author : lw.tar.gz
 * @Date: 2020-06-09 17:39
 */
@Data
public class Customer {

    private int id;
    private String name;
    private String label;
    private Date birthday;

}
