package com.leo.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * Test Created by Leo on 3/28/16.
 */
public class Test {
    static <T extends Number> T getObject() {
        return (T)Long.valueOf(1L);
    }

    public static void main(String... args) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(getObject());
    }
}
