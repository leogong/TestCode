package com.leo.test.proxy;


/**
 * 动态代理
 * 
 * @author Leo
 * 
 */
public class Test {

	public static void main(String[] args) {
		ProxyUser proxyuser=new ProxyUser();
		UserInterface user= (UserInterface) proxyuser.bind(new UserImpl());
		System.out.println(user.getNames());
	}

}
