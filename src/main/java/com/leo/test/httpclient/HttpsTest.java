package com.leo.test.httpclient;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by leo on 8/20/15.
 */
public class HttpsTest {

    public static void main(String[] args) throws IOException {
        HttpClient httpclient = new HttpClient();
        GetMethod httpget = new GetMethod("https://www.tmall.com/");
        try {
            httpclient.executeMethod(httpget);
            BufferedReader in = new BufferedReader(new InputStreamReader(httpget.getResponseBodyAsStream(),
                                                                         "ISO-8859-1"));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } finally {
            httpget.releaseConnection();
        }
    }
}
