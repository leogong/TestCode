package com.leo.test.jmx.mbean;

/**
 * ServerMonitor Created by Leo on 4/17/16.
 */
public class ServerMonitor implements ServerMonitorMBean {

    private final ServerImpl target;

    public ServerMonitor(ServerImpl target) {
        this.target = target;
    }

    public long getUpTime() {
        return System.currentTimeMillis() - target.startTime;
    }
}
