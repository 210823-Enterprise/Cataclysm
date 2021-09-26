package com.revature.cataclysm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.revature.objectMapper.ObjectCache;
import com.revature.objectMapper.ObjectReader;
import com.revature.objectMapper.ObjectRemover;
import com.revature.objectMapper.ObjectSaver;
import com.revature.objectMapper.ObjectUpdater;

public class Cataclysm {
	private ObjectSaver om = new ObjectSaver();
	private ObjectUpdater ou = new ObjectUpdater();
	private ObjectReader oread = new ObjectReader();
	private ObjectRemover or = new ObjectRemover();
	private ObjectCache oc = ObjectCache.getInstance();
	
	public int insert(Object obj) {
		return om.insert(obj);
	}
	
	// Update this to boolean later
	public void update(Object obj) {
		ou.update(obj);
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
	
	public void deleteById(Class clazz, int id) {
		or.deleteById(clazz, id);
	}
	
	public void dropTable(Class clazz) {
		or.deleteTable(clazz);
	}
}
