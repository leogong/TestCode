package com.leo.test.xstream;

/**
 * User: leo Date: 1/10/13 Time: 2:52 PM
 */
public class RoomInfo {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }

}
