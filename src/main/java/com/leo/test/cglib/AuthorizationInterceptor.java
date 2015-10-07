package com.leo.test.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 
 * @author Leo
 * 
 */
public class AuthorizationInterceptor implements MethodInterceptor {
	private AuthorizationService authorizationService;

	public AuthorizationInterceptor(AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}
	

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		if(authorizationService!=null){
			authorizationService.authorize(method);
		}
		return proxy.invokeSuper(obj, args);
	}

}
