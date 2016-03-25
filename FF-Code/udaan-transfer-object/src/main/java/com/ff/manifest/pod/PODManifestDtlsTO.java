package com.ff.manifest.pod;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.StringUtil;

/**
 * @author prmeher
 * 
 */
public class PODManifestDtlsTO extends CGBaseTO implements Comparable<PODManifestDtlsTO>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer manifestDtlsId;
	private String consgNumber;
	private String receivedDate;
	private String dlvDate;
	private String recvNameOrCompSeal;
	private String receivedStatus;
	private Integer consgId;
	private String isValidCN = "Y";
	private String errorMsg;
	private Integer position;
	/*
	 * Used for print
	 */
	private Integer srNo;

	public Integer getSrNo() {
		return srNo;
	}

	public void setSrNo(Integer srNo) {
		this.srNo = srNo;
	}

	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}

	/**
	 * @return the manifestDtlsId
	 */
	public Integer getManifestDtlsId() {
		return manifestDtlsId;
	}

	/**
	 * @param manifestDtlsId
	 *            the manifestDtlsId to set
	 */
	public void setManifestDtlsId(Integer manifestDtlsId) {
		this.manifestDtlsId = manifestDtlsId;
	}

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
	 * @return the receivedDate
	 */
	public String getReceivedDate() {
		return receivedDate;
	}

	/**
	 * @param receivedDate
	 *            the receivedDate to set
	 */
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}

	/**
	 * @return the dlvDate
	 */
	public String getDlvDate() {
		return dlvDate;
	}

	/**
	 * @param dlvDate
	 *            the dlvDate to set
	 */
	public void setDlvDate(String dlvDate) {
		this.dlvDate = dlvDate;
	}

	/**
	 * @return the recvNameOrCompSeal
	 */
	public String getRecvNameOrCompSeal() {
		return recvNameOrCompSeal;
	}

	/**
	 * @param recvNameOrCompSeal
	 *            the recvNameOrCompSeal to set
	 */
	public void setRecvNameOrCompSeal(String recvNameOrCompSeal) {
		this.recvNameOrCompSeal = recvNameOrCompSeal;
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
	 * @return the consgId
	 */
	public Integer getConsgId() {
		return consgId;
	}

	/**
	 * @param consgId
	 *            the consgId to set
	 */
	public void setConsgId(Integer consgId) {
		this.consgId = consgId;
	}

	/**
	 * @return the isValidCN
	 */
	public String getIsValidCN() {
		return isValidCN;
	}

	/**
	 * @param isValidCN
	 *            the isValidCN to set
	 */
	public void setIsValidCN(String isValidCN) {
		this.isValidCN = isValidCN;
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
	
	@Override
	public int compareTo(PODManifestDtlsTO obj) {
		int result = 0;
		if(!StringUtil.isEmptyInteger(position) 
				&& !StringUtil.isEmptyInteger(obj.getPosition())){
			result = this.position.compareTo(obj.position);
		} 
		return result;
	}

}
