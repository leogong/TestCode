package com.leo.test.cglib;

import java.lang.reflect.Method;

public class AuthorizationServiceImpl implements AuthorizationService {

	@Override
	public void authorize(Method method) {
		if (!"save".equals(method.getName())) {
			throw new RuntimeException("方法不为save");
		}
	}
}
