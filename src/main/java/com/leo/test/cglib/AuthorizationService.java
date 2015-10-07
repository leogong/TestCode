package com.leo.test.cglib;

import java.lang.reflect.Method;

/**
 * 
 * @author Leo
 *
 */
public interface AuthorizationService {
	void authorize(Method method);
}
