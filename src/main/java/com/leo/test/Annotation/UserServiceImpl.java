package com.leo.test.Annotation;

/**
 * User: Leo
 * Date: 12-8-16
 * Time: 下午10:21
 */
@MyAnnotation(id = 1, description = "userService")
public class UserServiceImpl implements UserService {
    @Override
    public String getUserName() {
        return "leo";
    }
}
