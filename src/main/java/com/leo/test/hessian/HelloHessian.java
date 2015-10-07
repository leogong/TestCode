package com.leo.test.hessian;

import java.util.List;
import java.util.Map;

/**
 * Created by leo on 14-4-17.
 */

public interface HelloHessian {

    String sayHello(String name);

    MyCar getMyCar();

    List<String> myLoveFruit();

    Map<String, String> myBabays();

}
