package com.ff.manifest.inmanifest;

import java.util.List;

/**
 * @author nkattung
 * 
 */
public class InMasterBagManifestTO extends InManifestTO {

	private static final long serialVersionUID = -2257250703731550581L;

	
	private String mbplNumber;
	private String mbplLockNo;
	
	private String officeName;
	private String docType;
	private Integer loggedInOfficeId;
	private String loginOffName;
	
	private Integer destCityId;
	private boolean empty;
	private int rowCount;
	private double totalBplWt;
	
	//for ptint
	private Long totalComail;
	private Long totalConsg;
	
	// UI Specific
	private int count;
	private String[] bplNumbers = new String[count];
	private String[] bagLockNumbers = new String[count];
	private String[] bplProcessCode = new String[count];
	private Integer[] bplProcessId = new Integer[count];
	
	private String loggedInOfficeName;
	private String loggedInOfficeCity;

	// List of master bag details tos
	private List<InMasterBagManifestDetailsTO> inMasterBagManifestDtlsTOs;		

	public String getMbplNumber() {
		return mbplNumber;
	}

	public void setMbplNumber(String mbplNumber) {
		this.mbplNumber = mbplNumber;
	}

	public String getMbplLockNo() {
		return mbplLockNo;
	}

	public void setMbplLockNo(String mbplLockNo) {
		this.mbplLockNo = mbplLockNo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String[] getBplNumbers() {
		return bplNumbers;
	}

	public void setBplNumbers(String[] bplNumbers) {
		this.bplNumbers = bplNumbers;
	}

	public String[] getBagLockNumbers() {
		return bagLockNumbers;
	}

	public void setBagLockNumbers(String[] bagLockNumbers) {
		this.bagLockNumbers = bagLockNumbers;
	}

	public List<InMasterBagManifestDetailsTO> getInMasterBagManifestDtlsTOs() {
		return inMasterBagManifestDtlsTOs;
	}

	public void setInMasterBagManifestDtlsTOs(
			List<InMasterBagManifestDetailsTO> inMasterBagManifestDtlsTOs) {
		this.inMasterBagManifestDtlsTOs = inMasterBagManifestDtlsTOs;
	}

	/**
	 * @return the officeName
	 */
	public String getOfficeName() {
		return officeName;
	}

	/**
	 * @param officeName the officeName to set
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	


	/**
	 * @return the bplProcessCode
	 */
	public String[] getBplProcessCode() {
		return bplProcessCode;
	}

	/**
	 * @param bplProcessCode the bplProcessCode to set
	 */
	public void setBplProcessCode(String[] bplProcessCode) {
		this.bplProcessCode = bplProcessCode;
	}

	/**
	 * @return the bplProcessId
	 */
	public Integer[] getBplProcessId() {
		return bplProcessId;
	}

	/**
	 * @param bplProcessId the bplProcessId to set
	 */
	public void setBplProcessId(Integer[] bplProcessId) {
		this.bplProcessId = bplProcessId;
	}

	/**
	 * @return the docType
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * @param docType the docType to set
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	/**
	 * @return the loggedInOfficeId
	 */
	public Integer getLoggedInOfficeId() {
		return loggedInOfficeId;
	}

	/**
	 * @param loggedInOfficeId the loggedInOfficeId to set
	 */
	public void setLoggedInOfficeId(Integer loggedInOfficeId) {
		this.loggedInOfficeId = loggedInOfficeId;
	}

	/**
	 * @return the loginOffName
	 */
	public String getLoginOffName() {
		return loginOffName;
	}

	/**
	 * @param loginOffName the loginOffName to set
	 */
	public void setLoginOffName(String loginOffName) {
		this.loginOffName = loginOffName;
	}

	/**
	 * @return the destCityId
	 */
	public Integer getDestCityId() {
		return destCityId;
	}

	/**
	 * @param destCityId the destCityId to set
	 */
	public void setDestCityId(Integer destCityId) {
		this.destCityId = destCityId;
	}

	/**
	 * @return the empty
	 */
	public boolean isEmpty() {
		return empty;
	}

	/**
	 * @param empty the empty to set
	 */
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public double getTotalBplWt() {
		return totalBplWt;
	}

	public void setTotalBplWt(double totalBplWt) {
		this.totalBplWt = totalBplWt;
	}

	/**
	 * @return the totalComail
	 */
	public Long getTotalComail() {
		return totalComail;
	}

	/**
	 * @param totalComail the totalComail to set
	 */
	public void setTotalComail(Long totalComail) {
		this.totalComail = totalComail;
	}

	/**
	 * @return the totalConsg
	 */
	public Long getTotalConsg() {
		return totalConsg;
	}

	/**
	 * @param totalConsg the totalConsg to set
	 */
	public void setTotalConsg(Long totalConsg) {
		this.totalConsg = totalConsg;
	}

	/**
	 * @return the loggedInOfficeName
	 */
	public String getLoggedInOfficeName() {
		return loggedInOfficeName;
	}

	/**
	 * @param loggedInOfficeName the loggedInOfficeName to set
	 */
	public void setLoggedInOfficeName(String loggedInOfficeName) {
		this.loggedInOfficeName = loggedInOfficeName;
	}

	/**
	 * @return the loggedInOfficeCity
	 */
	public String getLoggedInOfficeCity() {
		return loggedInOfficeCity;
	}

	/**
	 * @param loggedInOfficeCity the loggedInOfficeCity to set
	 */
	public void setLoggedInOfficeCity(String loggedInOfficeCity) {
		this.loggedInOfficeCity = loggedInOfficeCity;
	}

	

}
