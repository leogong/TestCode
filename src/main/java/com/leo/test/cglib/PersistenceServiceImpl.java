package com.leo.test.cglib;

/**
 * 
 * @author Leo
 *
 */
public class PersistenceServiceImpl implements PersistenceService {

	@Override
	public void save(long id, String data) {
		System.out.println(data + " has been saved succesfully ");
	}

	@Override
	public String load(long id) {
		System.out.println("\""+id+"\"  has been loaded successfully");
		return "leo";
	}

}
