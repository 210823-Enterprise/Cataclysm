package com.revature.objectMapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class Transaction {
	
	private static Logger log = Logger.getLogger(Transaction.class);
	
	private static final Transaction tr = new Transaction();
	
	private final HashMap<String, Savepoint> savepoints;
	
	private Transaction() {
		super();
		savepoints = new HashMap<>();
	}
	
	public Transaction getTransaction() {
		return tr;
	}
	
	
	public boolean enableAutoCommit(Connection conn) {
		try {
			conn.setAutoCommit(true);
			log.info("Auto-commit enabled.");
			return true;
		} catch (SQLException e) {
			log.error("Auto-commit was not able to be set to true.");
			return false;
		}
	}
	
	public boolean disableAutoCommit(Connection conn) {
		try {
			conn.setAutoCommit(false);
			log.info("Auto-commit disabled.");
			return true;
		} catch (SQLException e) {
			log.error("Auto-commit was not able to be set to false.");
			return false;
		}
	}
	
	public boolean commit(Connection conn) {
		try {
			conn.commit();
			log.info("Committed transaction.");
			return true;
		} catch (SQLException e) {
			log.error("Committing transaction was not able to be done.");
			return false;
		}
	}
	
	public boolean rollback(Connection conn) {
		try {
			conn.rollback();
			log.info("Rollback committed.");
			return true;
		} catch (SQLException e) {
			log.error("Rollback failed.");
			return false;
		}
	}
	
	public boolean Savepoint(String name, Connection conn) {
		try {
			Savepoint save = conn.setSavepoint(name);
			savepoints.put(name, save);
			log.info("Initiated save point for " + save);
			return true;
		} catch (SQLException e) {
			log.error("Savepoint initialization failed.");
			return false;
		}
	}
	
	public boolean ReleaseSavepoint(final String name, final Connection conn) {
		try {
			if (savepoints.containsKey(name)) {
				conn.releaseSavepoint(savepoints.get(name));
				log.info("Save point " + name + " removed.");
				return true;
			} else {
				log.warn("Trying to remove a non-existent savepoint");
				return false;
			}
		} catch (SQLException e) {
			log.error("Failed to remove save point: ", e);
			return false;
		}
	}
	
	public boolean rollbackSavepoint(final String name, final Connection conn) {
		try {
			if (savepoints.containsKey(name)) {
				conn.rollback(savepoints.get(name));
				log.info("Rolled back to your savepoint.");
				return true;
			} else {
				log.warn("Savepoint does not exist.");
				return false;
			}
		} catch (SQLException e) {
			log.error("Error in rolling back transaction: ",e);
			return false;
		}
	}

	

}
