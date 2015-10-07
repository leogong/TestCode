package com.leo.test.Enum;

/**
 * User: Leo
 * Date: 12-10-21
 * Time: 下午8:15
 */
public enum Color {
    Green(1, 2);
    private int a;
    private int b;

    private Color(int a, int b) {
        this.a = a;
        this.b = b;
    }

//    public abstract String getVal();
}
