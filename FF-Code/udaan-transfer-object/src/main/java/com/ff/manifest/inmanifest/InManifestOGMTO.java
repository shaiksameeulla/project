/**
 * 
 */
package com.ff.manifest.inmanifest;

import java.util.List;

import com.capgemini.lbs.framework.constants.CommonConstants;

/**
 * @author uchauhan
 *
 */
public class InManifestOGMTO extends InManifestTO {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2811288893413450145L;
	private String isCoMail = CommonConstants.NO;
	int count;
	private String deletedIds;
	private Boolean isInBplDoxPacket=Boolean.FALSE;

	private Integer consignmentTypeId;
	
	private Integer[] comailIds = new Integer[count];
	//private Integer[] position = new Integer[count];
	private Integer[] comailManifestIds = new Integer[count];
	private String[] isCN = new String[count];
	private List<InManifestOGMDetailTO> inManifestOGMDetailTOs;
	private List inCoMailTOs;
	private String[] coMailTOs = new String[count];
	private String loginRegionOffice;
	
	private Integer destCityId;
	private double totalWt;
	private int rowCount;
	

	private String loggedInOfficeName;
	private String loggedInOfficeCity;
	/**
	 * @return the comailIds
	 */
	public Integer[] getComailIds() {
		return comailIds;
	}
	/**
	 * @param comailIds the comailIds to set
	 */
	public void setComailIds(Integer[] comailIds) {
		this.comailIds = comailIds;
	}
	/**
	 * @return the isCN
	 */
	public String[] getIsCN() {
		return isCN;
	}
	/**
	 * @param isCN the isCN to set
	 */
	public void setIsCN(String[] isCN) {
		this.isCN = isCN;
	}
	
	

	
	/**
	 * @return the inManifestOGMDetailTOs
	 */
	public List<InManifestOGMDetailTO> getInManifestOGMDetailTOs() {
		return inManifestOGMDetailTOs;
	}
	/**
	 * @param inManifestOGMDetailTOs the inManifestOGMDetailTOs to set
	 */
	public void setInManifestOGMDetailTOs(List<InManifestOGMDetailTO> inManifestOGMDetailTOs) {
		this.inManifestOGMDetailTOs = inManifestOGMDetailTOs;
	}

	/**
	 * @return the isCoMail
	 */
	public String getIsCoMail() {
		return isCoMail;
	}
	/**
	 * @param isCoMail the isCoMail to set
	 */
	public void setIsCoMail(String isCoMail) {
		this.isCoMail = isCoMail;
	}
	/**
	 * @return the inCoMailTOs
	 */
	public List getInCoMailTOs() {
		return inCoMailTOs;
	}
	/**
	 * @param inCoMailTOs the inCoMailTOs to set
	 */
	public void setInCoMailTOs(List inCoMailTOs) {
		this.inCoMailTOs = inCoMailTOs;
	}
	/**
	 * @return the coMailTOs
	 */
	public String[] getCoMailTOs() {
		return coMailTOs;
	}
	/**
	 * @param coMailTOs the coMailTOs to set
	 */
	public void setCoMailTOs(String[] coMailTOs) {
		this.coMailTOs = coMailTOs;
	}
	/**
	 * @return the comailManifestIds
	 */
	public Integer[] getComailManifestIds() {
		return comailManifestIds;
	}
	/**
	 * @param comailManifestIds the comailManifestIds to set
	 */
	public void setComailManifestIds(Integer[] comailManifestIds) {
		this.comailManifestIds = comailManifestIds;
	}
	/**
	 * @return the deletedIds
	 */
	public String getDeletedIds() {
		return deletedIds;
	}
	/**
	 * @param deletedIds the deletedIds to set
	 */
	public void setDeletedIds(String deletedIds) {
		this.deletedIds = deletedIds;
	}
	/**
	 * @return the loginRegionOffice
	 */
	public String getLoginRegionOffice() {
		return loginRegionOffice;
	}
	/**
	 * @param loginRegionOffice the loginRegionOffice to set
	 */
	public void setLoginRegionOffice(String loginRegionOffice) {
		this.loginRegionOffice = loginRegionOffice;
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
	 * @return the position
	 */
	/*public Integer[] getPosition() {
		return position;
	}
	*//**
	 * @param position the position to set
	 *//*
	public void setPosition(Integer[] position) {
		this.position = position;
	}*/
	public double getTotalWt() {
		return totalWt;
	}
	public void setTotalWt(double totalWt) {
		this.totalWt = totalWt;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	/**
	 * @return the isInBplDoxPacket
	 */
	public Boolean getIsInBplDoxPacket() {
		return isInBplDoxPacket;
	}
	/**
	 * @param isInBplDoxPacket the isInBplDoxPacket to set
	 */
	public void setIsInBplDoxPacket(Boolean isInBplDoxPacket) {
		this.isInBplDoxPacket = isInBplDoxPacket;
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
	/**
	 * @return the consignmentTypeId
	 */
	public Integer getConsignmentTypeId() {
		return consignmentTypeId;
	}
	/**
	 * @param consignmentTypeId the consignmentTypeId to set
	 */
	public void setConsignmentTypeId(Integer consignmentTypeId) {
		this.consignmentTypeId = consignmentTypeId;
	}
	
}
