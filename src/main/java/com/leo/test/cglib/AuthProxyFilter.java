package com.leo.test.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.CallbackFilter;

public class AuthProxyFilter implements CallbackFilter {
	private static final int AUTH_NEED = 0;
	private static final int AUTH_NOT_NEED = 1;

	@Override
	public int accept(Method method) {
		if ("query".equals(method.getName()))
			return AUTH_NOT_NEED;
		return AUTH_NEED;
	}

}
