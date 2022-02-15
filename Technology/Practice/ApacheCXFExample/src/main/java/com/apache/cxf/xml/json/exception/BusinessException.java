/**
 * 
 */
package com.apache.cxf.xml.json.exception;

/**
 * @author mohammes
 *
 */
public class BusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4101148147849788323L;
	
	BusinessException(){
		super();
	}

	BusinessException(String errorMsg){
		super(errorMsg);
	}
	BusinessException(String errorMsg,Throwable error){
		super(errorMsg,error);
	}

	

}
