package com.leo.test.classloader;

/**
 * User: Leo
 * Date: 12-10-8
 * Time: 下午10:43
 */
public class LoadTest {
     public static void main(String[] args){
         new Ab();
     }
}

class B{
    static {
        System.out.println("Load B");
    }
    public B(){
        System.out.println("Create B");
    }
}

class Ab extends B{
    static {
        System.out.println("Load A");
    }
    public Ab(){
        System.out.println("Create A");
    }
}



