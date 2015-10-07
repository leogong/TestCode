package com.leo.test.encode;

import java.io.UnsupportedEncodingException;

/**
 * User: Leo Date: 4/22/13 Time: 9:13 PM
 */
public class Test {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String en = "a";
        String ch = "百";
        printEncodeLength(en,"UTF-8");
        printEncodeLength(en,"GBK");
        printEncodeLength(en,"GB2312");
        printEncodeLength(en,"UNICODE");

        printEncodeLength(ch,"UTF-8");
        printEncodeLength(ch,"GBK");
        printEncodeLength(ch,"GB2312");
        printEncodeLength(ch,"UNICODE");

    }

    public static void printEncodeLength(String s, String encodeName) throws UnsupportedEncodingException {
        System.out.println(s + ":" + s.getBytes(encodeName).length + ":" + encodeName);
    }
}