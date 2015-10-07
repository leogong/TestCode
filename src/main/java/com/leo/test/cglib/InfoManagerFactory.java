package com.leo.test.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

public class InfoManagerFactory {

    public static InfoManager getInstance(AuthProxy auth) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(InfoManager.class);
        enhancer.setCallback(auth);
        return (InfoManager) enhancer.create();
    }

    public static InfoManager getSelectiveAuthInstance(AuthProxy auth) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(InfoManager.class);
        enhancer.setCallbacks(new Callback[]{auth, NoOp.INSTANCE});
        enhancer.setCallbackFilter(new AuthProxyFilter());
        return (InfoManager) enhancer.create();
    }
}
