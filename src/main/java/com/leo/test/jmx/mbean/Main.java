package com.leo.test.jmx.mbean;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

/**
 * Main Created by Leo on 4/17/16.
 */
public class Main {

    private static ObjectName  objectName;
    private static MBeanServer mBeanServer;

    public static void main(String[] args) throws Exception {
        init();
        manage();
    }

    private static void init() throws Exception {
        ServerImpl serverImpl = new ServerImpl();
        ServerMonitor serverMonitor = new ServerMonitor(serverImpl);
        mBeanServer = MBeanServerFactory.createMBeanServer();
        objectName = new ObjectName("objectName:id=ServerMonitor1");
        mBeanServer.registerMBean(serverMonitor, objectName);
    }

    private static void manage() throws Exception {
        Long upTime = (Long) mBeanServer.getAttribute(objectName, "UpTime");
        System.out.println(upTime);
        Thread.sleep(5*1000);
        upTime = (Long) mBeanServer.getAttribute(objectName, "UpTime");
        System.out.println(upTime);
    }
}
