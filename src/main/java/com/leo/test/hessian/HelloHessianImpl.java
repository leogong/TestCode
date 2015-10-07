package com.leo.test.hessian;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.taobao.hsf.com.caucho.hessian.server.HessianServlet;

/**
 * Created by leo on 14-4-17.
 */
public class HelloHessianImpl extends HessianServlet implements HelloHessian {

    public MyCar getMyCar() {
        MyCar car = new MyCar();
        car.setCarName("阿斯顿·马丁");
        car.setCarModel("One-77");
        return car;
    }

    public Map<String, String> myBabays() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("son", "孙吴空");
        map.put("daughter", "孙小美");
        return map;
    }

    public List<String> myLoveFruit() {
        List<String> list = new ArrayList<String>();
        list.add("apple");
        list.add("kiwi");
        list.add("orange");
        return list;
    }

    public String sayHello(String name) {
        return "welcom to Hessian" + name;
    }

}
