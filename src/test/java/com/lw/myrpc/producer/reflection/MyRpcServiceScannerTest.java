package com.lw.myrpc.producer.reflection;

import com.lw.myrpc.producer.CustomerServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Set;

class MyRpcServiceScannerTest {

    @Test
    public void testScan() {
        String pkgPath = MyRpcServiceScanner.getPkgPath("com.lw.myrpc.producer");
        System.out.println(pkgPath);
        Set<Class<?>> classes = MyRpcServiceScanner.scanClasses("com.lw.myrpc.producer", true);
        classes.forEach(c -> System.out.println(c.getSimpleName()));
    }

    @Test
    public void testGetName() {
        CustomerServiceImpl customerService = new CustomerServiceImpl();
        Class<? extends CustomerServiceImpl> aClass = customerService.getClass();
        System.out.println(aClass.getSimpleName());
    }

}