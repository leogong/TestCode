package com.leo.test.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyClassLoader extends ClassLoader {

    private String       name;
    private String       path;

    private final String classType = ".class";

    public MyClassLoader(String name) {
        super();
        this.name = name;
    }

    public MyClassLoader(ClassLoader parent, String name) {
        super(parent);
        this.name = name;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = this.loadClassData(name);
        return this.defineClass(name, data, 0, data.length);
    }

    private byte[] loadClassData(String name) {
        InputStream is = null;
        byte[] data = null;
        ByteArrayOutputStream baos = null;
        name = name.replace(".", File.separator);
        try {
            is = new FileInputStream(new File(path + name + classType));
            baos = new ByteArrayOutputStream();
            int ch;
            while (-1 != (ch = is.read())) {
                baos.write(ch);
            }
            data = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (baos != null) baos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static void main(String[] args) {
        // 1
        // MyClassLoader loader1 = new MyClassLoader("loader1");
        // loader1.setPath("/Users/leo/test/lib1/");
        // try {
        // Class clazz = loader1.loadClass("Animal");
        // clazz.newInstance();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        // 2
        // MyClassLoader loader1 = new MyClassLoader("loader1");
        // MyClassLoader loader2 = new MyClassLoader("loader2");
        // loader1.setPath("/Users/leo/test/lib1/");
        // loader2.setPath("/Users/leo/test/lib1/");
        // try {
        // System.out.println(loader1.getParent());
        // System.out.println(loader2.getParent());
        // Class<?> clazz = loader1.loadClass("Animal");
        // Class<?> clazz2 = loader2.loadClass("Animal");
        // clazz.newInstance();
        // clazz2.newInstance();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        // 3
        // MyClassLoader loader1 = new MyClassLoader(null, "loader1");// 使用根类加载器做父类，将命名空间隔离
        // loader1.setPath("/Users/leo/test/lib1/");
        // try {
        // System.out.println(loader1.getParent());
        // Class<?> clazz = loader1.loadClass("Animal");
        // System.out.println(clazz.getClassLoader());
        // Object object = clazz.newInstance();
        // Animal animal = (Animal) object;
        // System.out.println(animal.a);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        // 4
        MyClassLoader loader1 = new MyClassLoader(null, "loader1");// 使用根类加载器做父类，将命名空间隔离
        loader1.setPath("/Users/leo/test/lib1/");
        try {
            System.out.println(loader1.getParent());
            Class<?> clazz = loader1.loadClass("java.lang.String");
            System.out.println(clazz.getClassLoader());
            Object object = clazz.newInstance();
            String string = (String) object;
            System.out.println("object:" + string);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 5
        // MyClassLoader loader1 = new MyClassLoader(null, "loader1");// 使用根类加载器做父类，将命名空间隔离
        // loader1.setPath("/Users/leo/test/lib1/");
        // try {
        // System.out.println(loader1.getParent());
        // Class<?> clazz = loader1.loadClass("Cat");
        // System.out.println(clazz.getClassLoader());
        // Object object = clazz.newInstance();
        // Method method = clazz.getMethod("getDog");
        // Dog dog = (Dog) method.invoke(object);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        // 6
        // MyClassLoader loader1 = new MyClassLoader(null, "loader1");// 使用根类加载器做父类，将命名空间隔离
        // loader1.setPath("/Users/leo/test/lib1/");
        // try {
        // System.out.println(loader1.getParent());
        // Class<?> rfqService1 = loader1.loadClass("com.leo.test.classloader.RfqService");
        // com.leo.test.classloader.RfqService rfqService = (com.leo.test.classloader.RfqService) Proxy.newProxyInstance(loader1, new Class[] { com.leo.test.classloader.RfqService.class },
        // new InvocationHandler() {
        //
        // @Override
        // public Object invoke(Object proxy,
        // Method method,
        // Object[] args)¡
        // throws Throwable {
        // return method.getName();
        // }
        // });
        // System.out.println(rfqService.getClass().getClassLoader());
        // System.out.println(rfqService.getName());
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        // 7
        // MyClassLoader loader1 = new MyClassLoader(null, "loader1");// 使用根类加载器做父类，将命名空间隔离
        // loader1.setPath("/Users/leo/test/lib1/");
        // try {
        // System.out.println(loader1.getParent());
        // Class<com.leo.test.classloader.RfqService> rfqServiceClass = com.leo.test.classloader.RfqService.class;// 直接传入接口类，不再模拟HSF获取应用类
        // Class<com.leo.test.classloader.RfqRequestDTO> rfqRequestDTOClass = com.leo.test.classloader.RfqRequestDTO.class;// 直接传入接口类，不再模拟HSF获取应用类
        // Class<?> hsfTest = loader1.loadClass("com.leo.test.classloader.HSFTest");// 应用获取HSF类，不再模拟导出给应用
        // com.leo.test.classloader.RfqService rfqService = (com.leo.test.classloader.RfqService) hsfTest.getDeclaredMethod("getObject",
        // new Class[] { Class.class, Class.class }).invoke(hsfTest.newInstance(),
        // new Class[] {
        // rfqServiceClass,
        // rfqRequestDTOClass });
        //
        // System.out.println(rfqService.getClass().getClassLoader());
        // System.out.println(rfqService.getRfq().getName());
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        // MyClassLoader loader1 = new MyClassLoader("loader1");
        // loader1.setPath("/Users/leo/test/lib1/");
        // try {
        // Class<?> clazz = loader1.loadClass("Animal");
        // Object object = clazz.newInstance();
        // Field field = clazz.getField("a");
        // System.out.println(field.getInt(object));
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        // MyClassLoader loader1 = new MyClassLoader("loader1");
        // loader1.setPath("/Users/leo/test/lib1/");
        // try {
        // Class<?> clazz = loader1.loadClass("Animal");
        // loader1 = null;
        // loader1 = new MyClassLoader("loader1");
        // loader1.setPath("/Users/leo/test/lib1/");
        // Class<?> clazz2 = loader1.loadClass("Animal");
        // System.out.println(clazz == clazz2);
        // System.out.println(clazz.hashCode());
        // System.out.println(clazz2.hashCode());
        // } catch (ClassNotFoundException e) {
        // e.printStackTrace();
        // }

    }
}

