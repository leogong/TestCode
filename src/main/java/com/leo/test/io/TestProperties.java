package com.leo.test.io;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class TestProperties {

    public static void main(String[] args) {
        Properties props = System.getProperties();
//        props.list(System.out);
        Set<Map.Entry<Object,Object>> entries = props.entrySet();
        for (Map.Entry<Object,Object> entry:entries) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

    }
}