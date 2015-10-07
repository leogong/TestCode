package com.leo.test.nio.nio2.sample.filecopy;

public class CommonCopy implements Runnable {

    String         fromFile;
    String         toFile;
    public boolean flag = false;

    public CommonCopy(String fromFile, String toFile) {
        this.fromFile = fromFile;
        this.toFile = toFile;
    }

    @Override
    public void run() {
        System.out.println("run");
    }

}
