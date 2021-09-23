package com.revature.objectMapper;

/**
 * This File is used by a developer to access Database methods
 * @author Ty
 */
public class Database {

	private ObjectSaver om = new ObjectSaver();
	private ObjectRemover or = new ObjectRemover();
	private ObjectUpdater ou = new ObjectUpdater();
	
	public int insert(Object obj) {
		return om.insert(obj);
	}
	
	
	// Update this to boolean later
	public void update(Object obj) {
		ou.update(obj);
	}
	
	public boolean delete(Object obj) {
		// om.delete(obj)
		return false;
	}
	
	public void deleteById(Class clazz, int id) {
		or.deleteById(clazz, id);
	}
	
	public void dropTable(Class clazz) {
		or.deleteTable(clazz);
	}
}
