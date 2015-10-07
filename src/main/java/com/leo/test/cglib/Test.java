package com.leo.test.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

public class Test {
	public static void main(String[] args) {
		Enhancer enhancer=new Enhancer();
		enhancer.setSuperclass(PersistenceServiceImpl.class);
		CallbackFilter callbackFilterd=new PersistenceServiceCallbackFilter();
		enhancer.setCallbackFilter(callbackFilterd);
		AuthorizationService authorizationService=new AuthorizationServiceImpl();
		Callback saveCallBack=new AuthorizationInterceptor(authorizationService);
		Callback loadCallBack=NoOp.INSTANCE;
		Callback[] callbacks=new Callback[]{saveCallBack,loadCallBack};
		enhancer.setCallbacks(callbacks);
		PersistenceService persistenceService=(PersistenceServiceImpl)enhancer.create();
		persistenceService.load(1);
		persistenceService.save(1, "leo");
	}
}
