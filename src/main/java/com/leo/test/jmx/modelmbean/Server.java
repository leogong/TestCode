package com.leo.test.jmx.modelmbean;

/**
 * Server Created by Leo on 4/17/16.
 */
public class Server {

    private long startTime;

    public Server() {
    }

    public int start() {
        startTime = System.currentTimeMillis();
        return 0;
    }

    public long getUpTime() {
        return System.currentTimeMillis() - startTime;
    }
}
