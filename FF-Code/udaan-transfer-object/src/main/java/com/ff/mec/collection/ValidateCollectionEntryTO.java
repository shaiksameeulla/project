package com.ff.mec.collection;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author prmeher
 * 
 */
public class ValidateCollectionEntryTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;

	private String frmDate;
	private String toDate;
	private Integer stationId;
	private Integer officeId;
	private String headerStatus;
	private String headerTransNo;
	private String transMsg;

	int rowCount;
	private String isChecked[] = new String[rowCount];
	private String txns[] = new String[rowCount];

	private List<ValidateCollectionEntryDtlsTO> collectionDtls;
	
	
	/**
	 * @return the transMsg
	 */
	public String getTransMsg() {
		return transMsg;
	}

	/**
	 * @param transMsg
	 *            the transMsg to set
	 */
	public void setTransMsg(String transMsg) {
		this.transMsg = transMsg;
	}

	/**
	 * @return the txns
	 */
	public String[] getTxns() {
		return txns;
	}

	/**
	 * @param txns
	 *            the txns to set
	 */
	public void setTxns(String[] txns) {
		this.txns = txns;
	}

	/**
	 * @return the isChecked
	 */
	public String[] getIsChecked() {
		return isChecked;
	}

	/**
	 * @param isChecked
	 *            the isChecked to set
	 */
	public void setIsChecked(String[] isChecked) {
		this.isChecked = isChecked;
	}

	/**
	 * @return the collectionDtls
	 */
	public List<ValidateCollectionEntryDtlsTO> getCollectionDtls() {
		return collectionDtls;
	}

	/**
	 * @param collectionDtls
	 *            the collectionDtls to set
	 */
	public void setCollectionDtls(
			List<ValidateCollectionEntryDtlsTO> collectionDtls) {
		this.collectionDtls = collectionDtls;
	}

	/**
	 * @return the frmDate
	 */
	public String getFrmDate() {
		return frmDate;
	}

	/**
	 * @param frmDate
	 *            the frmDate to set
	 */
	public void setFrmDate(String frmDate) {
		this.frmDate = frmDate;
	}

	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the stationId
	 */
	public Integer getStationId() {
		return stationId;
	}

	/**
	 * @param stationId
	 *            the stationId to set
	 */
	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	/**
	 * @return the officeId
	 */
	public Integer getOfficeId() {
		return officeId;
	}

	/**
	 * @param officeId
	 *            the officeId to set
	 */
	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}

	/**
	 * @return the headerStatus
	 */
	public String getHeaderStatus() {
		return headerStatus;
	}

	/**
	 * @param headerStatus
	 *            the headerStatus to set
	 */
	public void setHeaderStatus(String headerStatus) {
		this.headerStatus = headerStatus;
	}

	/**
	 * @return the headerTransNo
	 */
	public String getHeaderTransNo() {
		return headerTransNo;
	}

	/**
	 * @param headerTransNo
	 *            the headerTransNo to set
	 */
	public void setHeaderTransNo(String headerTransNo) {
		this.headerTransNo = headerTransNo;
	}

}
