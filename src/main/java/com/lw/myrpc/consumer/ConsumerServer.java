package com.lw.myrpc.consumer;

import com.lw.myrpc.api.CustomerService;
import com.lw.myrpc.common.entity.Customer;
import com.lw.myrpc.consumer.proxy.MyRpcInvocationHandler;
import com.lw.myrpc.consumer.proxy.MyRpcServiceProxyFactory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @ClassName : ConsumerServer
 * @Description :
 * @Author : lw.tar.gz
 * @Date: 2020-06-10 16:27
 */
public class ConsumerServer {

    @Setter
    @Getter
    private CustomerService customerService;

    public static void main(String[] args) {
        // get all instances
        // load balance
        // generate caller
        // handle response
        ConsumerServer consumerServer = new ConsumerServer();
        autowire(consumerServer);
        List<Customer> customers = consumerServer.getCustomerService().listAll();
        customers.forEach(System.out::println);

    }

    private static void autowire(ConsumerServer consumerServer) {
        MyRpcInvocationHandler handler = new MyRpcInvocationHandler(CustomerService.class, "localhost", 28080);
        consumerServer.setCustomerService((CustomerService) MyRpcServiceProxyFactory.getProxy(handler));
    }

}
