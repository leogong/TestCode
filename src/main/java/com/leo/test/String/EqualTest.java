package com.leo.test.String;

/**
 * User: Leo
 * Date: 12-10-8
 * Time: 下午10:36
 */
public class EqualTest {

    public static void main(String[] args) {
        String s = "hello";
        String t = "hello";
        char c[] = {'h', 'e', 'l', 'l', 'o'};
        System.out.println();
        System.out.println(Boolean.valueOf(s.equals(t)));
        System.out.println(Boolean.valueOf(t.equals(c)));
        System.out.println(Boolean.valueOf(s == t));
        System.out.println(Boolean.valueOf(t.equals(new String("hello"))));
    }
}
