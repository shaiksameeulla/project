package com.ff.report.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.webaction.CGBaseAction;

public class MissedConsignments extends CGBaseAction {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(MissedConsignments.class);
	

	public List readData(String param1, String param2, String jdbcFilePath) {

		Properties props = new Properties();
		try {
			FileInputStream fis = new FileInputStream(jdbcFilePath);
			// loading properites from properties file
			props.load(fis);
		} catch (FileNotFoundException e) {
			LOGGER.error("MissedConsignments::readData::FileNotFoundException occured:"
					+e.getMessage());
		} catch (IOException e) {
			LOGGER.error("MissedConsignments::readData::IOException occured:"
					+e.getMessage());
		}

		final String DB_URL = props.getProperty("udaan.jdbc.url");

		// Database credentials
		final String USER = props.getProperty("jdbc.retail.user");
		final String PASS = props.getProperty("jdbc.retail.password");

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List rtnV = null;
		String startLeaf, prefix, concatString, endLeaf, consgNumber;
		int startLeafInt = 0;
		int endLeafInt = 0;
		int prevDigitCount = 0;
		int presDigitCount = 0;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			if ((!param1.equalsIgnoreCase(""))
					&& (!param2.equalsIgnoreCase(""))) {
				startLeaf = param1.substring(5, param1.length());
				prefix = param1.substring(0, 5);
				concatString = "";
				if (startLeaf.length() != String.valueOf(
						Integer.parseInt(startLeaf)).length()) {
					concatString = startLeaf
							.substring(
									0,
									(startLeaf.length() - String.valueOf(
											(Integer.parseInt(startLeaf) + 1))
											.length()));
				}
				endLeaf = param2.substring(5, param2.length());
				startLeafInt = Integer.parseInt(startLeaf);
				endLeafInt = Integer.parseInt(endLeaf);				
				rtnV = new ArrayList(endLeafInt-startLeafInt);

				while (startLeafInt <= endLeafInt) {
					if (prevDigitCount < presDigitCount) {
						concatString = concatString.substring(0,
								(concatString.length() - 1));
					}
					consgNumber = prefix + concatString
							+ String.valueOf(startLeafInt);
					String query = "SELECT CONSG_NUMBER FROM ff_f_booking WHERE consg_number IN ('"
							+ consgNumber + "')";
					rs = stmt.executeQuery(query);
					if (rs.next()) {
					} else {
						rtnV.add(consgNumber);
					}
					prevDigitCount = String.valueOf(startLeafInt).length();
					startLeafInt = startLeafInt + 1;
					presDigitCount = String.valueOf(startLeafInt).length();
				}
			}
		}

		catch (SQLException se) {
			LOGGER.error("MissedConsignments::readData::SQLException occured:"
					+se.getMessage());
		} catch (Exception e) {
			LOGGER.error("MissedConsignments::readData::Exception occured:"
					+e.getMessage());
		} finally {
			try {
				if (stmt != null)
					conn.close();
					stmt.close();
					rs.close();
			} catch (SQLException se) {
				LOGGER.error("MissedConsignments::readData::SQLException occured:"
						+se.getMessage());
			}
			try {
				if (conn != null)
					conn.close();
					stmt.close();
					rs.close();
			} catch (SQLException se) {
				LOGGER.error("MissedConsignments::readData::SQLException occured:"
						+se.getMessage());
			}
		}
		return (rtnV);

	}

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		MissedConsignments sc = new MissedConsignments();
		String param1 = "";
		String param2 = "";
		String jdbcFilePath = "";		
		sc.readData(param1, param2, jdbcFilePath);		
	}
}