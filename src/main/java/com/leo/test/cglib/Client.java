package com.leo.test.cglib;

public class Client {
	public static void main(String[] args) {
		Client c = new Client();
//		c.haveNoAuthManager();
//		c.haveAuthManager();
		c.selectiveAuthManager();
	}
	
	public void selectiveAuthManager(){
		System.out.println("the loginer's name is not leo,so have no permites do manager except to query operator...");
		InfoManager authManger=InfoManagerFactory.getSelectiveAuthInstance(new AuthProxy("leo1"));
		doCRUD(authManger);
		separatorLine();
	}
	
	
	public void haveNoAuthManager(){
		System.out.println("the loginer's name is not leo,so have no permits do manager...");
		InfoManager noAuthManager=InfoManagerFactory.getInstance(new AuthProxy("leo1"));
		doCRUD(noAuthManager);
		separatorLine();
	}
	
	public void haveAuthManager(){
		System.out.println("the loginer's name is leo,so have permits do manager...");
		InfoManager noAuthManager=InfoManagerFactory.getInstance(new AuthProxy("leo"));
		doCRUD(noAuthManager);
		separatorLine();
	}

	private void doCRUD(InfoManager manager) {
		manager.create();
		manager.update();
		manager.delete();
		manager.query();
	}

	private void separatorLine() {
		System.out.println("##################################");
	}
}
