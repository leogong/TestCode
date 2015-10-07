package com.leo.test.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 
 * @author Leo
 *
 */
public class AuthProxy implements MethodInterceptor {
	private String name;

	public AuthProxy(String name) {
		this.name = name;
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		if (!"leo".equals(this.name)) {
			System.out.println("AuthProxy:you have no permits to do manger! method:" + method.getName());
			return null;
		}
        return proxy.invokeSuper(obj, args);
	}

	public String getName() {
		return name;
	}

	public void SetName(String name) {
		this.name = name;
	}
}
