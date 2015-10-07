package com.leo.test.hessian;


import java.net.MalformedURLException;
import java.util.Map;

import com.taobao.hsf.com.caucho.hessian.client.HessianProxyFactory;

/**
 * Created by leo on 14-4-17.
 */
public class HessianClientTest {

    public static void main(String[] args) {
        String url = "http://localhost:8080/HessianService";
        HessianProxyFactory factory = new HessianProxyFactory();
        try {
            HelloHessian hello = (HelloHessian) factory.create(HelloHessian.class, url);
            System.out.println(hello.sayHello("xxx"));

            MyCar car = hello.getMyCar();
            System.out.println(car.toString());

            for (Map.Entry entry : hello.myBabays().entrySet()) {
                System.out.println(entry.getKey() + "   " + entry.getValue());
            }

            for (String str : hello.myLoveFruit()) {
                System.out.println(str);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
