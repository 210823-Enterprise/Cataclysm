package com.revature.objectMapper;

import java.sql.Connection;

/**
 * This File is used by a developer to access Database methods
 * @author Ty
 */
public class Database {

	private ObjectSaver om = new ObjectSaver();
	private ObjectRemover or = new ObjectRemover();
	private ObjectUpdater ou = new ObjectUpdater();
	private ObjectReader ore = new ObjectReader();
	private Transaction tr;
	
	public int insert(Object obj) {
		return om.insert(obj);
	}
	
	public Object selectRowById(Class clazz, int id) {
		return ore.selectRowWithId(clazz, id);
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
	
	public boolean enableAutoCommit(Connection conn) {
		return tr.enableAutoCommit(conn);
	}
	
	public boolean disableAutoCommit(Connection conn) {
		return tr.disableAutoCommit(conn);
	}
	
	public boolean commitChanges(Connection conn) {
		return tr.commit(conn);
	}
	
	public boolean abortChanges(Connection conn) {
		return tr.rollback(conn);
	}
	
	public boolean returnToSavepoint(Connection conn, String name) {
		return tr.rollbackSavepoint(name, conn);
	}
	
	public boolean setSavepoint(Connection conn, String name) {
		return tr.Savepoint(name, conn);
	}
	
	public boolean releaseSavepoint(Connection conn, String name) {
		return tr.ReleaseSavepoint(name, conn);
	}
}
