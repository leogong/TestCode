package com.leo.test.Annotation;


import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * User: Leo
 * Date: 12-8-16
 * Time: 下午9:51
 */
public class ParseAnnotation {
    @MyAnnotation(id = 1, description = "Filed")
    private UserService userService;

    private static HashMap<String, Object> beanMap = new HashMap<>();

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public ParseAnnotation() {

    }

    @MyAnnotation(id = 1, description = "methodOne")
    public void methodOne() {

    }

    @MyAnnotation(id = 2)
    public void methodTwo() {

    }

    @MyAnnotation(id = 3, description = "method three")
    public void methodThree() {
    }

    static {
        Class<UserServiceImpl> userServiceClass = UserServiceImpl.class;
        MyAnnotation annotation = userServiceClass.getAnnotation(MyAnnotation.class);
        System.out.println("@MyAnnotation(id=" + annotation.id() + ",description=" + annotation.description() + ")");
        System.out.println("public class " + userServiceClass.getSimpleName() + " implement " + userServiceClass.getInterfaces()[0]);
        try {
            beanMap.put(annotation.description(), userServiceClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<ParseAnnotation> parseAnnotationClass = ParseAnnotation.class;
        ParseAnnotation parseAnnotation = new ParseAnnotation();
        Method[] methods = parseAnnotationClass.getDeclaredMethods();

        Field[] fields = parseAnnotationClass.getDeclaredFields();
        for (Field field : fields) {
            Object userServiceImpl = beanMap.get(field.getName());
            if (userServiceImpl != null) {
                for (Method method : methods) {
                    if (method.getName().equals("set" + StringUtils.capitalize(field.getName()))) {
                        method.invoke(parseAnnotation, userServiceImpl);
                        break;
                    }
                }
            }
        }
        System.out.println("userName:" + parseAnnotation.getUserService().getUserName());

        for (Method method : methods) {
            boolean hasAnnotation = method.isAnnotationPresent(MyAnnotation.class);
            if (hasAnnotation) {
                MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
                System.out.println("@MyAnnotation(id=" + myAnnotation.id() + ",description=" + myAnnotation.description() + ")");
                System.out.println("public " + method.getReturnType() + " " + method.getName());
            }
        }
    }
}
