/**
 * 
 */
package com.capgemini.lbs.framework.constants;

/**
 * @author mohammes
 *
 */
public interface GlobalErrorCodeConstants {
	
	String GLOBAL_ERROR_CODE="CGS000";
	/** The duplicate row. */
	String DUPLICATE_ROW="CGS001";
	
	/** The data issue. */
	String DATA_ISSUE="CGS002";
	
	/** The database access error. */
	String DATABASE_ACCESS_ERROR ="CGS003";
	
	/** The database dead lock error. */
	String DATABASE_DEAD_LOCK_ERROR ="CGS004";
	
	/** The null access error. */
	String NULL_ACCESS_ERROR ="CGS005";
	
	/** The db connection issue. */
	String DB_CONNECTION_ISSUE ="CGS006";
	
	/** The sql grammer issue. */
	String SQL_GRAMMER_ISSUE ="CGS007";
	
	/** The qry time out issue. */
	String QRY_TIME_OUT_ISSUE ="CGS008";
	
	/** The invalid data access exception. */
	String INVALID_DATA_ACCESS_EXCEPTION ="CGS009";
	
	/** The data truncation. */
	String DATA_TRUNCATION="CG1265";
	/** The data truncation. */
	String DATA_OUT_OF_RANGE="CG1264";
	
	/** The foreign key violation. */
	String FOREIGN_KEY_VIOLATION="CG1452";
	
	/** The duplicate key violation. */
	String DUPLICATE_KEY_VIOLATION="CG1062";
	
	String NOT_NULL_CONSTRAINT_VIOLATION="CG1048";
	
	String BUSSINESS_VIOLATION_RE_PROCESSING="CGB100";
	String BUSSINESS_VIOLATION_ERROR="CGB200";
	String BUSIENSS_VIOLATION_FILE_ERROR="CGB202";
	String BUSINESS_ERROR_INBOUND="CGB201";
	
}
