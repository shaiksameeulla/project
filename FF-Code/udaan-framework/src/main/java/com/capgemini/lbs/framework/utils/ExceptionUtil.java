/*
 * @author mohammes
 */
package com.capgemini.lbs.framework.utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.QueryTimeoutException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.orm.hibernate3.HibernateQueryException;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.constants.GlobalErrorCodeConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.exception.MessageDetail;
import com.capgemini.lbs.framework.exception.MessageType;
import com.capgemini.lbs.framework.exception.MessageWrapper;
import com.capgemini.lbs.framework.exception.ParamInfo;
import com.capgemini.lbs.framework.to.CGBaseTO;
import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * The Class ExceptionUtil.
 */
public abstract class ExceptionUtil {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ExceptionUtil.class);
	
	public static String DATA_SYNC_RE_PROCESS_TYPE="RP";
	public static String DATA_SYNC_ERROR_TYPE="ER";
	
	public static String dataSyncExceptionType(Exception e) {
		String processType=DATA_SYNC_RE_PROCESS_TYPE;
		String errorCode=null;
		if(e instanceof CGBusinessException){
			processType=DATA_SYNC_ERROR_TYPE;
			if(!StringUtil.isStringEmpty(e.getMessage())){
				errorCode=e.getMessage();
			}
			
		}else{
		 errorCode=getSqlVendorCode(e);
		}
		if(!StringUtil.isStringEmpty(errorCode)){
			switch(errorCode){
			case GlobalErrorCodeConstants.DATA_TRUNCATION:
				processType=DATA_SYNC_ERROR_TYPE;
				break;
			case GlobalErrorCodeConstants.DUPLICATE_KEY_VIOLATION:
				processType=DATA_SYNC_ERROR_TYPE;
				break;
			case GlobalErrorCodeConstants.NOT_NULL_CONSTRAINT_VIOLATION:
				processType=DATA_SYNC_ERROR_TYPE;
				break;
			case GlobalErrorCodeConstants.BUSSINESS_VIOLATION_ERROR:
				processType=DATA_SYNC_ERROR_TYPE;
				break;
			case GlobalErrorCodeConstants.BUSSINESS_VIOLATION_RE_PROCESSING:
				processType=DATA_SYNC_RE_PROCESS_TYPE;
				break;
				
			}

		}
		if(!StringUtil.isStringEmpty(processType) && processType.equalsIgnoreCase(DATA_SYNC_ERROR_TYPE)){
			String message=getMessageFromException(e);
			if(!StringUtil.isStringEmpty(message) && message.contains("Duplicate entry") && (message.contains("ff_f_manifest_unq") || message.contains("consignment_no_unq") || message.contains("ff_f_pickup_assignment_header_uniqueindex1"))){
				processType=DATA_SYNC_RE_PROCESS_TYPE;
			}
		}
		return processType;
	}
	/**
	 * Generic method to get the exception from that TO.
	 *
	 * @param baseTo the base to
	 * @param request the request
	 * @return true, if successful
	 */
	public static boolean prepareActionMessage(List<? extends CGBaseTO> baseToList, HttpServletRequest request) {

		boolean exceptionStatus = false;
		// Define the actionmessages for errors
		ActionMessages actionMessages = new ActionMessages();
		// Define actionMessages for warnings
		ActionMessages actionWarnings = new ActionMessages();
		String[] values = null;//new String[CommonConstants.PLACE_HOLDER_MAX_SIZE];
		if (baseToList != null && !baseToList.isEmpty()) {
			for (CGBaseTO baseTo :baseToList) {
				CGBusinessException dtdcException = baseTo.getBusinessException();
				if (dtdcException != null)	{	
					MessageWrapper mesRapper = dtdcException.getBusinessMessage();
					if(mesRapper != null) {
						List<MessageDetail> msgDetailList = mesRapper.getMessageList();
						if (msgDetailList != null && !msgDetailList.isEmpty()) {
							// iterate the message message list from the to
							for (MessageDetail msgDetail : msgDetailList) {
								// Check whether the exception is of type error and then add
								List<ParamInfo> paramInfoList = msgDetail.getParams();
								int tempIndex = 0;
								
								if(paramInfoList!=null && !paramInfoList.isEmpty()) {
									values = new String[paramInfoList.size()];
									for(ParamInfo paramInfo : paramInfoList) {
										if(tempIndex == CommonConstants.PLACE_HOLDER_MAX_SIZE) {
											break;
										}
										values[tempIndex] = paramInfo.getValue();
										tempIndex++;
									}
								}
								
								String propertyName = msgDetail.getPropertyName() != null && !msgDetail.getPropertyName().isEmpty() ? msgDetail.getPropertyName() : ActionMessages.GLOBAL_MESSAGE;
								ActionMessage actionMessage =  new ActionMessage(msgDetail.getMessageKey(), values);
								if (msgDetail.getMessageType().getMessage().equals(MessageType.Error.getMessage()))	{
									actionMessages.add(propertyName, actionMessage);
								}else if (msgDetail.getMessageType().getMessage().equals(MessageType.Warning.getMessage())) {// Check Check whether the exception is of type warning and then add
									actionWarnings.add(propertyName, actionMessage);
								}
							}
							exceptionStatus = true;
						}
					} 
				} 	
			}
			// Set the error and warnings into the request attribute
			request.setAttribute(CommonConstants.ERROR_MESSAGE, actionMessages);
			request.setAttribute(CommonConstants.WARNING_MESSAGE, actionWarnings);
		}
		return exceptionStatus;
	}

	/**
	 * Exception handler.
	 *
	 * @param baseTo the base to
	 * @param request the request
	 * @return true, if successful
	 */
	public static boolean prepareActionMessage(CGBaseTO baseTo,	HttpServletRequest request) {
		boolean exceptionStatus = false;
		List<CGBaseTO> baseToList = new ArrayList<CGBaseTO>();
		if (baseTo != null) {
			baseToList.add(baseTo);
		}
		exceptionStatus = prepareActionMessage(baseToList, request);
		return exceptionStatus;
	}
	
	/**
	 * @param baseTo
	 * @return
	 */
	public static boolean  checkError(CGBaseTO baseTo) {
		boolean status = baseTo != null ? (baseTo.getBusinessException() != null ? true : false) : false;
		return status;
	}
	
	/**
	 * @param ex
	 * @param baseTo
	 * @return
	 */
	public static CGBaseTO mergeExceptions (CGBusinessException ex, CGBaseTO baseTo) {
		boolean isBusinessMessage1 = ex != null ? (ex.getBusinessMessage() != null ? true : false) : false;
		boolean isBusinessMessage2 = baseTo.getBusinessException() != null ? (baseTo.getBusinessException().getBusinessMessage() != null ? true : false) : false;
		
		if(isBusinessMessage1 && isBusinessMessage2) {
			List<MessageDetail> dl1 = ex.getBusinessMessage().getMessageList();
			List<MessageDetail> dl2 = baseTo.getBusinessException().getBusinessMessage().getMessageList();
			dl2.addAll(dl1);
		}
		
		return baseTo;
	}
	/**
	 * This method will create a message wrapper for the BusinessException class.
	 * @param key Error key in the resource bundle.
	 * @param description Message Description for the message defined in the resource bundle.
	 * @param msgType Type of the message Error , Warning, Information.
	 * @return messWrapper
	 */
	
	public static MessageWrapper getMessageWrapper(String key, String message, MessageType msgType) {
		MessageDetail messDetails = new MessageDetail();
		messDetails.setMessageKey(key);
		messDetails.setMessageType(msgType);
		
		MessageWrapper messWrapper = new MessageWrapper();
		messWrapper.addMessageDetail(messDetails);
		
		return messWrapper;
	}
	/**
	 * @param key
	 * @param msgType
	 * @return
	 */
	public static MessageWrapper getMessageWrapper(String key, MessageType msgType) {
		MessageDetail messDetails = new MessageDetail(key, msgType);
		MessageWrapper messWrapper = new MessageWrapper();
		messWrapper.addMessageDetail(messDetails);
		return messWrapper;
	}
	/**
	 * @param key
	 * @param msgType
	 * @param propertyName
	 * @param placeHolder
	 * @return
	 */
	public static MessageWrapper getMessageWrapper(String key, MessageType msgType, String propertyName, String placeHolder) {
		MessageDetail messDetails = new MessageDetail(key, msgType);
		messDetails.setPropertyName(propertyName);
		messDetails.addParamByValue(placeHolder);
		MessageWrapper messWrapper = new MessageWrapper();
		messWrapper.addMessageDetail(messDetails);
		return messWrapper;
	}
	/**
	 * @param key
	 * @param msgType
	 * @param propertyName
	 * @param placeHolders
	 * @return
	 */
	public static MessageWrapper getMessageWrapper(String key, MessageType msgType, String propertyName, String[] placeHolders) {
		MessageDetail messDetails = new MessageDetail(key, msgType);
		messDetails.setPropertyName(propertyName);
		messDetails.addParamByValue(placeHolders);
		MessageWrapper messWrapper = new MessageWrapper();
		messWrapper.addMessageDetail(messDetails);
		return messWrapper;
	}
	
	public static void prepareBusinessException(String key,String[] placeHolders) throws CGBusinessException {
		MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(key, MessageType.Warning,FrameworkConstants.ERROR_MESSAGE,placeHolders);
		throw new CGBusinessException(msgWrapper,key);
	}
	/**
	 * @throws CGBusinessException
	 */
	public static void prepareBusinessException(String key) throws CGBusinessException {
		MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(key, MessageType.Warning);
		throw new CGBusinessException(msgWrapper,key);
	}
	
	/**
	 * this method is responsible for converting the exception stack tract to string
	 * @param aThrowable
	 * @return
	 */
	public static String getExceptionStackTrace(Throwable aThrowable) {
	    //add the class name and any message passed to constructor
	    final StringBuilder result = new StringBuilder( "Exception stack trace: " );
	    result.append(aThrowable.toString());
	    final String NEW_LINE = System.getProperty("line.separator");
	    result.append(NEW_LINE);

	    //add each element of the stack trace
	    for (StackTraceElement element : aThrowable.getStackTrace() ){
	      result.append( element );
	      result.append( NEW_LINE );
	    }
	    return result.toString();
	  }
	public static String getExceptionStackTrace(Exception exception) {
		final StringBuilder result = new StringBuilder( "Exception : " );
		final String message = getMessageFromException(exception);
		result.append(message);
		return result.toString();
	}

	/**
	 * @param exception
	 * @return
	 */
	public static String getMessageFromException(Exception exception) {
		LOGGER.error("ExceptionUtil :: getMessageFromException ::Exception:::START");
		final StringBuilder result = new StringBuilder();
		final String NEW_LINE = System.getProperty("line.separator");
		Throwable trowable=exception;
		if(trowable instanceof CGSystemException){
			trowable=trowable.getCause();
		}
		if(trowable instanceof GenericJDBCException){
			 trowable=trowable.getCause();
		}
		if(trowable instanceof SQLException){
			SQLException cve=(SQLException)trowable;
				result.append(cve.getMessage());
				result.append(NEW_LINE);
				LOGGER.error("ExceptionUtil :: getMessageFromException :: Message:"+cve.getMessage());
		}else if(exception instanceof NullPointerException || trowable instanceof NullPointerException){
			result.append("Accessing or modifying the field of a NULL object");
			result.append(NEW_LINE);
			LOGGER.error("ExceptionUtil :: getMessageFromException :: Message:"+exception.getMessage());
		}else if(trowable instanceof DataException){
			DataException cve=(DataException)trowable;
			result.append(cve.getSQLException()!=null?cve.getSQLException().getMessage():cve.getMessage());
			LOGGER.error("ExceptionUtil :: getMessageFromException :: DataException:"+result);
			result.append(NEW_LINE);
		}else if(trowable instanceof ConstraintViolationException){
			ConstraintViolationException cve=(ConstraintViolationException)trowable;
			result.append(cve.getSQLException()!=null?cve.getSQLException().getMessage():cve.getMessage());
			LOGGER.error("ExceptionUtil :: getMessageFromException :: ConstraintViolationException:"+result);
			result.append(NEW_LINE);
		}else if(trowable instanceof DataIntegrityViolationException ){
			DataIntegrityViolationException cve=(DataIntegrityViolationException)trowable;
			if (cve.getCause() instanceof SQLException) {
				SQLException sqlExcp=(SQLException)cve.getCause();
				result.append(sqlExcp.getMessage());
			}else if(cve.getCause() instanceof ConstraintViolationException){
				ConstraintViolationException sqlExcp=(ConstraintViolationException)cve.getCause();
				if(sqlExcp.getCause() instanceof MySQLIntegrityConstraintViolationException){
					MySQLIntegrityConstraintViolationException mysqlVioExp=(MySQLIntegrityConstraintViolationException)sqlExcp.getCause();
					result.append(mysqlVioExp.getMessage());
				}
			}
		}else if (trowable instanceof SQLGrammarException) {
			SQLGrammarException cve=(SQLGrammarException)trowable;
			result.append(cve.getSQLException()!=null?cve.getSQLException().getMessage():cve.getMessage());
		
		}else if (trowable instanceof QueryTimeoutException) {
			QueryTimeoutException cve=(QueryTimeoutException)trowable;
			result.append(cve.getSQLException()!=null?cve.getSQLException().getMessage():cve.getMessage());
	
		}else if (trowable instanceof JDBCConnectionException) {
			JDBCConnectionException cve=(JDBCConnectionException)trowable;
			result.append(cve.getSQLException()!=null?cve.getSQLException().getMessage():cve.getMessage());
	
		}else if (trowable instanceof InvalidDataAccessResourceUsageException) {
			InvalidDataAccessResourceUsageException cve=(InvalidDataAccessResourceUsageException)trowable;
			if (cve.getCause() instanceof SQLGrammarException) {
				SQLGrammarException grammer=(SQLGrammarException)cve.getCause();
				result.append(grammer.getSQLException()!=null?grammer.getSQLException().getMessage():grammer.getMessage());
			}else{
				result.append(cve.getCause()!=null?cve.getCause().getMessage():cve.getMessage());
			}
		}else {
			if(exception.getCause()!=null){
				result.append(exception.getCause().toString()); 
				result.append(NEW_LINE);
			}else if(exception.getMessage()!=null){
				result.append(exception.getMessage().toString()); 
				result.append(NEW_LINE);
			}
			LOGGER.error("ExceptionUtil :: getMessageFromException ::Exception:"+result);
		}
		LOGGER.error("ExceptionUtil :: getMessageFromException ::Exception:::END with result :"+result);
		return result.toString();
	}
	/**
	 * getExceptionDetails
	 * @param exception
	 * @return
	 */
	public static String getExceptionDetails(Exception exception) {
		return getSystemErrorCode(exception);
	}

	/**
	 * @param exception
	 * @return
	 */
	private static String getSystemErrorCode(Exception exception) {
		LOGGER.error("ExceptionUtil :: getExceptionDetails :: START");
		final StringBuilder result = new StringBuilder();
		Throwable trowable=exception.getCause();
		if(trowable instanceof SQLException){

			if (((SQLException) trowable).getErrorCode()==-911 )  {
				result.append(GlobalErrorCodeConstants.DATABASE_DEAD_LOCK_ERROR);
			}else{
				result.append(GlobalErrorCodeConstants.DATABASE_ACCESS_ERROR);
			}
			
		} else if(exception instanceof NullPointerException){
			result.append(GlobalErrorCodeConstants.NULL_ACCESS_ERROR);
			//FIXME
		}else if(trowable instanceof DataException){
			result.append(GlobalErrorCodeConstants.DATA_ISSUE);
			
		}else if(trowable instanceof DataIntegrityViolationException || exception instanceof DataIntegrityViolationException){
			result.append(getSqlVendorCode(exception));
		}else if(trowable instanceof ConstraintViolationException){
			result.append(GlobalErrorCodeConstants.DUPLICATE_ROW);
		}else if (trowable instanceof UncategorizedSQLException) {
			result.append(GlobalErrorCodeConstants.DB_CONNECTION_ISSUE);
		}else if (trowable instanceof DataAccessResourceFailureException) {
			DataAccessResourceFailureException cve=(DataAccessResourceFailureException)trowable;
			if (cve.getCause() instanceof JDBCConnectionException) {
			result.append(GlobalErrorCodeConstants.DB_CONNECTION_ISSUE);
			}
		}else if (trowable instanceof HibernateQueryException) {
			result.append(GlobalErrorCodeConstants.SQL_GRAMMER_ISSUE);
		}else if (trowable instanceof QueryTimeoutException) {
			result.append(GlobalErrorCodeConstants.QRY_TIME_OUT_ISSUE);
		}else if (trowable instanceof InvalidDataAccessResourceUsageException) {
			
			InvalidDataAccessResourceUsageException cve=(InvalidDataAccessResourceUsageException)trowable;
			if (cve.getCause() instanceof SQLGrammarException) {
				//FIXME
				result.append(GlobalErrorCodeConstants.SQL_GRAMMER_ISSUE);
			}else{
				result.append(GlobalErrorCodeConstants.INVALID_DATA_ACCESS_EXCEPTION);
			}
		}else {
			result.append(GlobalErrorCodeConstants.GLOBAL_ERROR_CODE);
		}
		LOGGER.error("ExceptionUtil :: getExceptionDetails :: ENDS With Code:"+result+" and Exception:",exception);
		if(result!=null && StringUtil.isStringEmpty(result.toString())){
			LOGGER.error("ExceptionUtil :: getExceptionDetails :: ENDS(considering Default error code) With Code:"+result+" and Exception:",exception);
			result.append(GlobalErrorCodeConstants.GLOBAL_ERROR_CODE);
		}
		return result.toString();
	}
	/**
	 * @param result
	 * @param vendorErrorCode
	 * @param trowable
	 */
	public static String getSqlVendorCode(Exception e) {
		int vendorErrorCode=0;
		String  globalErrorCode=null;
		Throwable trowable=e;
		if(e instanceof CGSystemException){
		 trowable=e.getCause();
		}
		if(trowable instanceof DataIntegrityViolationException){
			DataIntegrityViolationException cve=(DataIntegrityViolationException)trowable;
			if (cve.getCause() instanceof SQLException) {
				SQLException sqlExcp=(SQLException)cve.getCause();
				vendorErrorCode=sqlExcp.getErrorCode();
			}else if(cve.getCause() instanceof ConstraintViolationException){
				ConstraintViolationException sqlExcp=(ConstraintViolationException)cve.getCause();
				if(sqlExcp.getCause() instanceof MySQLIntegrityConstraintViolationException){
					MySQLIntegrityConstraintViolationException mysqlVioExp=(MySQLIntegrityConstraintViolationException)sqlExcp.getCause();
					vendorErrorCode=mysqlVioExp.getErrorCode();
				}

			}else if (cve.getCause() instanceof DataException){
				DataException sqlExcp=(DataException)cve.getCause();
				if(sqlExcp.getCause() instanceof MysqlDataTruncation){
					MysqlDataTruncation sqlTruncation=(MysqlDataTruncation)sqlExcp.getCause();
					vendorErrorCode=sqlTruncation.getErrorCode();
					
				}
				
			}
		}
		globalErrorCode=GlobalErrorCodeConstants.GLOBAL_ERROR_CODE;
		switch(vendorErrorCode){
		case FrameworkConstants.DATA_TRUNCATION_ERROR_CODE:
			globalErrorCode=GlobalErrorCodeConstants.DATA_TRUNCATION;
			break;
		case FrameworkConstants.FOREIGN_KEY_ERROR_CODE:
			globalErrorCode=GlobalErrorCodeConstants.FOREIGN_KEY_VIOLATION;
			break;
		case FrameworkConstants.DUPLICATE_CONSTRAINT_ERROR_CODE:
			globalErrorCode=GlobalErrorCodeConstants.DUPLICATE_KEY_VIOLATION;
			break;
		case FrameworkConstants.NOT_NULL_CONSTRAINT_ERROR_CODE:
			globalErrorCode=GlobalErrorCodeConstants.NOT_NULL_CONSTRAINT_VIOLATION;
			break;
		case FrameworkConstants.DATA_TRUNCATION_TOO_LONG_VALUE_ERROR_CODE:
			globalErrorCode=GlobalErrorCodeConstants.DATA_TRUNCATION;
			break;
		case FrameworkConstants.DATA_OUT_OF_RANGE_ERROR_CODE:
			globalErrorCode=GlobalErrorCodeConstants.DATA_OUT_OF_RANGE;
			break;
			
			
		}
		return globalErrorCode;
	}
	

	public static String getGenericException(Exception exception) {
		LOGGER.error("ExceptionUtil :: getGenericException :: Exception:",exception);
		return GlobalErrorCodeConstants.GLOBAL_ERROR_CODE;
	}
	
}
