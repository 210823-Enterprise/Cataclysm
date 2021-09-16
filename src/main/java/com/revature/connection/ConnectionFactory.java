package com.revature.connection;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * class which represents a connection to the database
 * only  a single instance of this class is available during the use of the application
 *
 */

public class ConnectionFactory {
	
	private BasicDataSource ds;
	private static final ConnectionFactory connection_factory = new ConnectionFactory();

//	static { // static initializer loads before the main method
//		try {
//			Class.forName("org.postgressql.Driver");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}

	private ConnectionFactory () {
		
		try {
			Properties props = new Properties();
			props.load(new FileReader("src/main/resources/application.properties"));
			ds = new BasicDataSource();
			ds.setUrl(props.getProperty("dbUrl"));
			ds.setUsername(props.getProperty("username"));
			ds.setPassword(props.getProperty("password"));
			ds.setMinIdle(5);
			ds.setDefaultAutoCommit(true);
			ds.setMaxOpenPreparedStatements(10);
			
		} catch (IOException e) {
			// log that the file can't be found
			System.out.println("cant find prop file");
			// maybe research creating a custom loggers using a BufferedReader
		}
	}
	
	/**
	 * This is a method to return a current static instance of the ConnectionFactory
	 * @return
	 */
	public static ConnectionFactory getInstance() {
		return connection_factory;
	}
	
	
	/**
	 * Method to retrieve a connection to database
	 * @return
	 */
	public Connection getConnection() {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}