package com.leo.test.memory;

/**
 * Created by leo on 2014-11-03.
 */
public class HeapOOM {
    /*
        VM Args: -verbose:gc -Xms20m -Xmx20m -Xmn 10m -XX:HeapDumpOnOutOfMemoryError -XX:+PrintGCDetails -XX:SurvivorRatio=8
     */
    public static void main(String[] args) {

    }
}
