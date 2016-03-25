/**
 * 
 */
package com.capgemini.lbs.centralserver.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.centralserver.constants.CentralCommonConstant;

// TODO: Auto-generated Javadoc
/**
 * The Class JdbcUtility.
 *
 * @author mohammes
 */
public final class JdbcUtility {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
	.getLogger(JdbcUtility.class);
	static ResourceBundle jdbcProperties = null;
	
	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the sQL exception
	 */
	public static Connection getConnection() throws ClassNotFoundException,SQLException{
		if(jdbcProperties==null){
			loadProperties();
		}
		String  driver = jdbcProperties.getString("jdbc.mysql.driverClassName");
		String  url = jdbcProperties.getString("udaan.jdbc.url");
		String  userName = jdbcProperties.getString("jdbc.retail.user");
		String  pwd = jdbcProperties.getString("jdbc.retail.password");
		
		Class.forName(driver);
		return DriverManager.getConnection(url, userName, pwd);

	}
	
	/**
	 * Close connection.
	 *
	 * @param connection the connection
	 */
	public static void closeConnection(Connection connection){
		LOGGER.debug("JdbcUtility ::closeConnection ::");
		try {
		if(connection != null && !connection.isClosed()){
			connection.close();
		}
		}catch(Exception e){
			LOGGER.error("JdbcUtility ::closeConnection ::Exception",e);
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
			LOGGER.error("JdbcUtility ::commitTransaction ::Exception",e);
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
			LOGGER.error("JdbcUtility ::rollbackTransaction ::Exception",e);
		}
	}
	
	

	/**
	 * 
	 */
	private static void loadProperties() {
		jdbcProperties = ResourceBundle
				.getBundle(CentralCommonConstant.JDBC_PROPERTIES);
		LOGGER.debug("JdbcUtility :::loadProperties  ::properties Status ::-->"+jdbcProperties!=null?"Loaded":"not loaded");
	}
	
}
