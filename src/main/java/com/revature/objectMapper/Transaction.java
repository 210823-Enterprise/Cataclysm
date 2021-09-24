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
	
	
	public void enableAutoCommit(Connection conn) {
		try {
			conn.setAutoCommit(true);
			log.info("Auto-commit enabled.");
		} catch (SQLException e) {
			log.error("Auto-commit was not able to be set to true.");
		}
	}
	
	public void disableAutoCommit(Connection conn) {
		try {
			conn.setAutoCommit(false);
			log.info("Auto-commit disabled.");
		} catch (SQLException e) {
			log.error("Auto-commit was not able to be set to false.");
		}
	}
	
	public void commit(Connection conn) {
		try {
			conn.commit();
			log.info("Committed transaction.");
		} catch (SQLException e) {
			log.error("Committing transaction was not able to be done.");
		}
	}
	
	public void rollack(Connection conn) {
		try {
			conn.rollback();
			log.info("Rollback committed.");
		} catch (SQLException e) {
			log.error("Rollback failed.");
		}
	}
	
	public void Savepoint(String name, Connection conn) {
		try {
			Savepoint save = conn.setSavepoint(name);
			savepoints.put(name, save);
			log.info("Initiated save point for " + save);
		} catch (SQLException e) {
			log.error("Savepoint initialization failed.");
		}
	}

	

}
