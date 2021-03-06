package com.revature.cataclysm;

import java.sql.Connection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.revature.customStatement.Create;
import com.revature.customStatement.Delete;
import com.revature.customStatement.Insert;
import com.revature.customStatement.Select;
import com.revature.customStatement.Update;
import com.revature.objectMapper.ObjectCache;
import com.revature.objectMapper.ObjectReader;
import com.revature.objectMapper.ObjectRemover;
import com.revature.objectMapper.ObjectSaver;
import com.revature.objectMapper.ObjectUpdater;
import com.revature.objectMapper.Query;
import com.revature.objectMapper.Transaction;

public class Cataclysm {
	private Query q = new Query();
	private ObjectSaver om = new ObjectSaver();
	private ObjectUpdater ou = new ObjectUpdater();
	private ObjectReader oread = new ObjectReader();
	private ObjectRemover or = new ObjectRemover();
	private ObjectCache oc = ObjectCache.getInstance();
	private Transaction tr;
	
	public int insert(Object obj) {
		return om.insert(obj);
	}
	
	// Update this to boolean later
	public boolean update(Object obj) {
		return ou.update(obj);
	}
	
	public HashMap<String, HashSet<Object>> getCache() {
		return oc.getCache();
	}
	
	public <T> Object selectRowWithId(Class clazz, int id) {
		return oread.selectRowWithId(clazz, id);
	}
	
	public <T> List<T> selectAllFromTable(Class clazz) {
		return oread.selectAllFromTable(clazz);
	}
	
	public boolean deleteById(Class clazz, int id) {
		return or.deleteById(clazz, id);
	}
	
	public boolean dropTable(Class clazz) {
		return or.deleteTable(clazz);
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
	
	public boolean customCreate(Create create) {
		String sql = create.toString();
		return q.customCreate(sql);
	}
	
	public int customInsert(Insert insert) {
		String sql = insert.toString();
		return om.customInsert(sql);
	}
	
	public <T> Object customSelect(Select select, Class clazz) {
		String sql = select.toString();
		return oread.customSelect(clazz, sql);
	}
	
	public boolean customUpdate(Update update) {
		String sql = update.toString();
		return ou.customUpdate(sql);
	}
	
	public boolean customDelete(Delete delete) {
		String sql = delete.toString();
		return or.customDelete(sql);
	}
}
