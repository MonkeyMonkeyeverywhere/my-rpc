package com.lw.myrpc.api;

import com.lw.myrpc.common.entity.Customer;

import java.util.List;

/**
 * @author wliu10
 */
public interface CustomerService {

    List<Customer> listAll();

    List<Customer> getCustomerByName(String name);

}
