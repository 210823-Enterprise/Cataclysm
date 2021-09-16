package com.revature.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConnectionUtil {
	
	// Idea: Maybe you could use this to grab JDBC configuration details 
	// from your Configuration.java class
	
	// The user's job might be to provide those somewhere in the app.
	
	// Another idea: set up connection pool here....
	// Research DATASOURCE in custom ORM's
	

	private static Logger log = Logger.getLogger(ConnectionUtil.class);
	
	private static Connection conn = null;
	
	/**
	 *  we want to make the constructor PRIVATE so it can't be instantiated any other way
	 *  than our getConnection() method
	 */
	private ConnectionUtil() {
		super();
	}
	
	/**
	 *  The following method is designed to return the ONE single instance of this 
	 *  class if it exists, or instantiate it if if doesn't.
	 */
	public static Connection getConnection() {
		
		try {// check if the ConnectionUtil instance exists or is open first...
			if (conn != null && !conn.isClosed()) {
				log.info("returned reused connection");
			}
		} catch (SQLException e) {
			log.error("Failed to reuse a connection.");
			return null;
		}
		
		Properties prop = new Properties();
		
		String url = "";
		String username = "";
		String password = "";
		
		try {
			prop.load(new FileReader("C:\\Users\\Ty\\Desktop\\revature-projects\\project-1-team1\\project-1-team1\\src\\main\\resources\\testProp.properties"));
			url = prop.getProperty("dbUrl");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
		
			
			conn = DriverManager.getConnection(url, username, password);
			log.info("Database Connection Established");
		} catch (IOException e) {
			log.error("Cannot locate application.properties file.");
		} catch (SQLException e) {
			log.error("Cannot establish database connection");
			return null;
		}
		
		return conn;
	}
}
