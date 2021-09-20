package com.revature.objectMapper;

/**
 * This File is used by a developer to access Database methods
 * @author Ty
 */
public class Database {

	private ObjectSaver om = new ObjectSaver();
	private ObjectRemover or = new ObjectRemover();
	
	public int insert(Object obj) {
		return om.insert(obj);
	}
	
	
	// Update this to boolean later
	public void update(Object obj) {
		om.update(obj);
	}
	
	public void deleteById(Class clazz, int id) {
		or.deleteById(clazz, id);
	}
}
