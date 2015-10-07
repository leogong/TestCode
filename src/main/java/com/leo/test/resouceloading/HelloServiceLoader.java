package com.leo.test.resouceloading;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class HelloServiceLoader {

    private static final String PREFIX = "META-INF/hellos/";

    @SuppressWarnings("unchecked")
    public static List<String> loadHellos(ClassLoader classLoader) {
        try {
            Enumeration<URL> urlEnumeration = classLoader.getResources(PREFIX + HelloService.class.getName());
            List<String> hellos = new ArrayList<String>();
            while (urlEnumeration.hasMoreElements()) {
                URL url = urlEnumeration.nextElement();
                List<String> lines = IOUtils.readLines(url.openStream());
                if (lines != null) {
                    for (String line : lines) {
                        String l = StringUtils.trim(StringUtils.substringBefore(line, "#"));
                        if (StringUtils.isNotEmpty(l)) {
                            hellos.add(l);
                        }
                    }
                }
            }
            return hellos;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        List<String> strings = loadHellos(HelloServiceLoader.class.getClassLoader());
        for (String string : strings) {
            System.out.println(string);
        }
    }
}
