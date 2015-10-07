package com.leo.test.String;

/**
 * User: Leo
 * Date: 12-10-9
 * Time: 上午12:06
 */
public class ForTest {
    static boolean foo(char s) {
        System.out.print(s);
        return true;
    }
    public static void main(String[] args){
        int i;
        for(foo('A'),i=0;foo('B')&&(i<2);foo('C')){
            ++i;
            foo('D');
        }
    }
}
