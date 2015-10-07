package com.leo.test.Annotation;

import java.lang.annotation.*;

/**
 * User: Leo
 * Date: 12-8-16
 * Time: 下午9:47
 */

/*
 * 元注解@Target,@Retention,@Documented,@Inherited
 *
 *     @Target 表示该注解用于什么地方，可能的 ElementType 参数包括：
 *         ElementType.CONSTRUCTOR 构造器声明
 *         ElementType.FIELD 域声明（包括 Enum 实例）
 *         ElementType.LOCAL_VARIABLE 局部变量声明
 *         ElementType.METHOD 方法声明
 *         ElementType.PACKAGE 包声明
 *         ElementType.PARAMETER 参数声明
 *         ElementType.TYPE 类，接口（包括注解类型）或enum声明
 *
 *     @Retention 表示在什么级别保存该注解信息。可选的 RetentionPolicy 参数包括：
 *         RetentionPolicy.SOURCE 注解将被编译器丢弃
 *         RetentionPolicy.CLASS 注解在class文件中可用，但会被VM丢弃
 *         RetentionPolicy.RUNTIME VM将在运行期也保留注释，因此可以通过反射机制读取注解的信息。
 *
 *     @Documented 将此注解包含在 javadoc 中
 *
 *     @Inherited 允许子类继承父类中的注解
 *
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyAnnotation {
    public int id();

    public String description() default "no description";
}
