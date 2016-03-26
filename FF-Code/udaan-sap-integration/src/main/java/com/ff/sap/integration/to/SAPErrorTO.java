/**
 * 
 */
package com.ff.sap.integration.to;

/**
 * @author cbhure
 *
 */
public class SAPErrorTO {
	
	private String transactionNo;
	private String errorMessage;
	/**
	 * @return the transactionNo
	 */
	public String getTransactionNo() {
		return transactionNo;
	}
	/**
	 * @param transactionNo the transactionNo to set
	 */
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

}
