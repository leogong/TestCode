package com.leo.test.proxy;

/**
 * Created by leo on 15/1/19.
 */
import java.lang.reflect.*;
import java.util.*;

public class ProxyFunction {

    public static void main(String[] args) throws Exception {
        // 获取代理类的字节码
        Class proxyClass = Proxy.getProxyClass(Collection.class.getClassLoader(), Collection.class);
        // 打印字节码名字
        System.out.println("字节码的名字：" + proxyClass.getName());
        System.out.println("构造函数的列表---------------------------------");
        // 创建一个对象集，从字节码中获取构造函数
        Constructor[] constructors = proxyClass.getConstructors();
        // 遍历对象集，获取构造函数的名字
        for (Constructor constructor : constructors) {
            String name = constructor.getName();
            StringBuilder sBuilder = new StringBuilder(name);
            sBuilder.append('(');

            // 获取构造函数的参数类型
            Class[] params = constructor.getParameterTypes();
            for (Class param : params) {
                sBuilder.append(param.getName()).append(',');
            }
            if (params.length != 0) {
                sBuilder.deleteCharAt(sBuilder.length() - 1);
            }
            sBuilder.append(')');
            System.out.println(sBuilder.toString());
        }

        System.out.println("方法列表----------------------------------------");
        // 创建一个方法数组，获取方法
        Method[] methods = proxyClass.getMethods();
        for (Method method : methods) {
            // 获取方法名称存入缓冲区
            StringBuilder sBuilder = new StringBuilder(method.getName());
            sBuilder.append('(');
            // 将此对象所表示的方法类型封装为一个数组
            Class[] params = method.getParameterTypes();

            // 遍历方法类型数组
            for (Class param : params) {
                sBuilder.append(param.getName()).append(',');
            }
            if (params.length != 0) {
                sBuilder.deleteCharAt(sBuilder.length() - 1);
            }
            sBuilder.append(')');
            System.out.println(sBuilder.toString());
        }
        Constructor constructor = constructors[0];
        //创建动态代理类的三种方式
        //方式一：通过接口的子类创建对象
        Collection proxy = (Collection)constructor.newInstance(new MyInvocationHandler());
        System.out.println(proxy);//null
        System.out.println(proxy.toString());//null
        proxy.clear();     //无异常
        //proxy.size();    //异常

        //方式二：匿名内部类
        Collection proxy2 = (Collection)constructor.newInstance(
                new InvocationHandler(){
                    public Object invoke(Object proxy, Method method,Object[] args)throws Throwable{
                        return null;
                    }
                });

        //方式三：
        //通过代理类的newProxyInstance方法直接创建对象
        Collection proxy3 = (Collection)Proxy.newProxyInstance(
                //定义代理类的类加载器
                Collection.class.getClassLoader(),
                //代理类要实现的接口列表
                new Class[]{Collection.class},
                //指派方法调用的调用处理程序
                new InvocationHandler() {
                    //创建集合，制定一个目标
                    ArrayList target = new ArrayList();
                    public Object invoke(Object proxy, Method method, Object[] args)throws Throwable{
                        //测试程序运行时间
                        long beginTime = System.currentTimeMillis();
                        //调用目标方法，将其从return抽出来，加入代理所需的代码
                        Object retVal = method.invoke(target, args);
                        long endTime = System.currentTimeMillis();
                        //测试
                        System.out.println(method.getName()+" run time of "+(endTime - beginTime));
                        return retVal;
                    }
                }
        );
        //通过代理类调用目标方法，每调用一个目标的方法就会执行代理类的方法
        //当调用一次add方法时，就会找一次InvocationHandler这个参数的invoke方法
        proxy3.add("sdfd");
        proxy3.add("shrt");
        proxy3.add("rtbv");
        System.out.println(proxy3.size());
        System.out.println(proxy3.getClass().getName());
    }
}

class MyInvocationHandler implements InvocationHandler{

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
