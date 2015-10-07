package com.leo.test.resouceloading;

import java.util.ServiceLoader;

/**
 * Created by leo on 7/28/15.
 */
public class Test {

    public static void main(String[] args) {
        ServiceLoader<HelloService> helloServices = ServiceLoader.load(HelloService.class);
        for (HelloService helloService : helloServices) {
            System.out.println(helloService.sayHello());
        }
    }
}
