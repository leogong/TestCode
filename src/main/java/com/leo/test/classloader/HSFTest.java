package com.leo.test.classloader;

import sun.reflect.Reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by leo on 15/1/21.
 */
public class HSFTest {

    public HSFTest() {
        System.out.println("我是HSFTest,我被：" + this.getClass().getClassLoader() + "   加载了！");
    }

    public Object getObject(Class<?> interfaceClass, final Class<?> returnClass) {
        return Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[] { interfaceClass },
                                      new InvocationHandler() {

                                          @Override
                                          public Object invoke(Object proxy, Method method, Object[] args)
                                                                                                          throws Throwable {
                                              Object o = returnClass.newInstance();
                                              returnClass.getDeclaredMethod("setName", String.class).invoke(o,
                                                                                                            "invoke "
                                                                                                                    + method.getName());
                                              return o;
                                          }
                                      });
    }
}
