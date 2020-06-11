package com.lw.myrpc.producer;

import com.google.common.collect.Lists;
import com.lw.myrpc.api.CustomerService;
import com.lw.myrpc.common.annotation.MyRpcService;
import com.lw.myrpc.common.entity.Customer;

import java.util.List;

/**
 * @ClassName : CustomerServiceImpl
 * @Description :
 * @Author : lw.tar.gz
 * @Date: 2020-06-09 17:42
 */
@MyRpcService
public class CustomerServiceImpl implements CustomerService {

    @Override
    public List<Customer> listAll() {
        return Lists.newArrayList(new Customer());
    }

    @Override
    public List<Customer> getCustomerByName(String name) {
        return Lists.newArrayList(new Customer());
    }

}
