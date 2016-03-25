/**
 * 
 */
package com.ff.manifest.rthrto;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class RthRtoManifestTO.
 *
 * @author narmdr
 */
public class RtoConsignmentReportTO extends CGBaseTO {

	private static final long serialVersionUID = 5310718526651937670L;

	private String consgNumber;
	private String receivedDate;
	private String reasons;//all reasons separated by comma
	private String remarks;//all remarks separated by comma
	
	/**
	 * @return the consgNumber
	 */
	public String getConsgNumber() {
		return consgNumber;
	}
	/**
	 * @param consgNumber the consgNumber to set
	 */
	public void setConsgNumber(String consgNumber) {
		this.consgNumber = consgNumber;
	}
	/**
	 * @return the receivedDate
	 */
	public String getReceivedDate() {
		return receivedDate;
	}
	/**
	 * @param receivedDate the receivedDate to set
	 */
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}
	/**
	 * @return the reasons
	 */
	public String getReasons() {
		return reasons;
	}
	/**
	 * @param reasons the reasons to set
	 */
	public void setReasons(String reasons) {
		this.reasons = reasons;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
