package com.ff.manifest.pod;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class PODConsignmentDtlsTO extends CGBaseTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1296117885456579489L;
	private String consgNumber;
	private String receivedStatus;
	private String missedConsignments;
	private String receivedConsingments;
	private String errorMsg;

	/**
	 * @return the consgNumber
	 */
	public String getConsgNumber() {
		return consgNumber;
	}

	/**
	 * @param consgNumber
	 *            the consgNumber to set
	 */
	public void setConsgNumber(String consgNumber) {
		this.consgNumber = consgNumber;
	}

	/**
	 * @return the receivedStatus
	 */
	public String getReceivedStatus() {
		return receivedStatus;
	}

	/**
	 * @param receivedStatus
	 *            the receivedStatus to set
	 */
	public void setReceivedStatus(String receivedStatus) {
		this.receivedStatus = receivedStatus;
	}

	/**
	 * @return the missedConsignments
	 */
	public String getMissedConsignments() {
		return missedConsignments;
	}

	/**
	 * @param missedConsignments
	 *            the missedConsignments to set
	 */
	public void setMissedConsignments(String missedConsignments) {
		this.missedConsignments = missedConsignments;
	}

	/**
	 * @return the receivedConsingments
	 */
	public String getReceivedConsingments() {
		return receivedConsingments;
	}

	/**
	 * @param receivedConsingments
	 *            the receivedConsingments to set
	 */
	public void setReceivedConsingments(String receivedConsingments) {
		this.receivedConsingments = receivedConsingments;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg
	 *            the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
