/**
 * 
 */
package com.capgemini.lbs.centralserver.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class JdbcUtility.
 *
 * @author mohammes
 */
public final class JdbcUtility {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
	.getLogger(JdbcUtility.class);
	
	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the sQL exception
	 */
	public static Connection getConnection() throws ClassNotFoundException,SQLException{
		String  driver = "com.mysql.jdbc.Driver";
		String  url = "jdbc:mysql://10.10.5.15:3306/dtdc_ctbs_plus";
		String  userName = "ctbs";
		String  pwd = "root";
		Class.forName(driver);
		return DriverManager.getConnection(url, userName, pwd);

	}
	
	/**
	 * Close connection.
	 *
	 * @param connection the connection
	 */
	public static void closeConnection(Connection connection){
		logger.debug("JdbcUtility ::closeConnection ::");
		try {
		if(connection != null && !connection.isClosed()){
			connection.close();
		}
		}catch(Exception e){
			logger.error("JdbcUtility ::closeConnection ::Exception"+e.getMessage());
		}
	}
	
	/**
	 * Commit transaction.
	 *
	 * @param connection the connection
	 */
	public static void commitTransaction(Connection connection){
		try {
		if(connection != null && !connection.isClosed()){
			connection.commit();
		}
		}catch(Exception e){
			logger.error("JdbcUtility ::commitTransaction ::Exception"+e.getMessage());
		}
	}
	
	/**
	 * Rollback transaction.
	 *
	 * @param connection the connection
	 */
	public static void rollbackTransaction(Connection connection){
		try {
		if(connection != null && !connection.isClosed()){
			connection.rollback();
		}
		}catch(Exception e){
			logger.error("JdbcUtility ::rollbackTransaction ::Exception"+e.getMessage());
		}
	}
	
}
