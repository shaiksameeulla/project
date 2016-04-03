package com.ff.report.common.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BirtUtil {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(BirtUtil.class);
	
	public static void addBirtException(IReportContext reportContext , String errorMessage) {
	    addBirtException(reportContext , errorMessage, BirtException.WARNING);
	}

	public static void addBirtException(IReportContext reportContext , String errorMessage, Integer severity) {
	    
	    BirtException be = new BirtException("org.eclipse.birt.report.engine", errorMessage, new Object[]{""} );
	    //org.eclipse.birt.report.engine
	    be.setSeverity(severity);

	    try {
	        // get the protect field 'context' from reportContext
	        Class rciClass = reportContext.getClass();
	        Field fieldFromScript = rciClass.getDeclaredField("context");
	        if (fieldFromScript == null) {
	            return;
	        }

	        // instantiate the ExecutionContext object that
	        // populates the context field
	        fieldFromScript.setAccessible(true);
	        Object execContext = fieldFromScript.get(reportContext);

	        // now get a handle to the addException method on ExecutionObject
	        Class execClass = execContext.getClass();
	        Method addMethod = execClass.getMethod("addException", new Class[] { BirtException.class });

	        // finally invoke the method which will add the BirtException 
	        // to the report
	        addMethod.setAccessible(true);
	        addMethod.invoke(execContext, new Object[] { be });

	        // Lots of ways for this to break...
	    } catch (Exception e) {
	    	LOGGER.warn(e.getMessage());
	    	LOGGER.error("ERROR : BirtUtil.addBirtException", e);
	    } 
	    return;
	}

	
}
