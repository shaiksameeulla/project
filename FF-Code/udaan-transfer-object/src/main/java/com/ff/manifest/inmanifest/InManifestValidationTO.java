package com.ff.manifest.inmanifest;

import java.util.Date;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;

/**
 * @author narmdr
 *
 */
public class InManifestValidationTO extends CGBaseTO {

	private static final long serialVersionUID = -7044873830953099138L;

	private String bplNumber;
	private String manifestNumber;
	private String consgNumber;
	private String manifestType;
	private String processCode;
	private String updateProcessCode;
	private Date manifestDate;
	private Date previousDate;
	
	private InBagManifestDoxTO inBagManifestDoxTO;
	private InBagManifestParcelTO inBagManifestParcelTO;
	private InBagManifestDetailsParcelTO inBagManifestDetailsParcelTO;

	private CityTO cityTO;
	private PincodeTO pincodeTO;
	
	private Integer coMailId;
	
	/**
	 * Outputs
	 */
	private String errorMsg;
	private Boolean isNewManifest;
	private Boolean isOutManifest;
	private Boolean isInManifest = Boolean.FALSE;
	private Boolean isInManifestByReceive;
	
	private String loggedInOfficeName;
	private String loggedInOfficeCity;
	
	/**
	 * @return the manifestNumber
	 */
	public String getManifestNumber() {
		return manifestNumber;
	}
	/**
	 * @param manifestNumber the manifestNumber to set
	 */
	public void setManifestNumber(String manifestNumber) {
		this.manifestNumber = manifestNumber;
	}
	/**
	 * @return the processCode
	 */
	public String getProcessCode() {
		return processCode;
	}
	/**
	 * @param processCode the processCode to set
	 */
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	/**
	 * @return the updateProcessCode
	 */
	public String getUpdateProcessCode() {
		return updateProcessCode;
	}
	/**
	 * @param updateProcessCode the updateProcessCode to set
	 */
	public void setUpdateProcessCode(String updateProcessCode) {
		this.updateProcessCode = updateProcessCode;
	}
	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	/**
	 * @return the isNewManifest
	 */
	public Boolean getIsNewManifest() {
		return isNewManifest;
	}
	/**
	 * @param isNewManifest the isNewManifest to set
	 */
	public void setIsNewManifest(Boolean isNewManifest) {
		this.isNewManifest = isNewManifest;
	}
	/**
	 * @return the isOutManifest
	 */
	public Boolean getIsOutManifest() {
		return isOutManifest;
	}
	/**
	 * @param isOutManifest the isOutManifest to set
	 */
	public void setIsOutManifest(Boolean isOutManifest) {
		this.isOutManifest = isOutManifest;
	}
	/**
	 * @return the manifestType
	 */
	public String getManifestType() {
		return manifestType;
	}
	/**
	 * @param manifestType the manifestType to set
	 */
	public void setManifestType(String manifestType) {
		this.manifestType = manifestType;
	}
	/**
	 * @return the bplNumber
	 */
	public String getBplNumber() {
		return bplNumber;
	}
	/**
	 * @param bplNumber the bplNumber to set
	 */
	public void setBplNumber(String bplNumber) {
		this.bplNumber = bplNumber;
	}
	/**
	 * @return the inBagManifestDoxTO
	 */
	public InBagManifestDoxTO getInBagManifestDoxTO() {
		return inBagManifestDoxTO;
	}
	/**
	 * @param inBagManifestDoxTO the inBagManifestDoxTO to set
	 */
	public void setInBagManifestDoxTO(InBagManifestDoxTO inBagManifestDoxTO) {
		this.inBagManifestDoxTO = inBagManifestDoxTO;
	}
	/**
	 * @return the isInManifest
	 */
	public Boolean getIsInManifest() {
		return isInManifest;
	}
	/**
	 * @param isInManifest the isInManifest to set
	 */
	public void setIsInManifest(Boolean isInManifest) {
		this.isInManifest = isInManifest;
	}
	/**
	 * @return the isInManifestByReceive
	 */
	public Boolean getIsInManifestByReceive() {
		return isInManifestByReceive;
	}
	/**
	 * @param isInManifestByReceive the isInManifestByReceive to set
	 */
	public void setIsInManifestByReceive(Boolean isInManifestByReceive) {
		this.isInManifestByReceive = isInManifestByReceive;
	}
	/**
	 * @return the inBagManifestParcelTO
	 */
	public InBagManifestParcelTO getInBagManifestParcelTO() {
		return inBagManifestParcelTO;
	}
	/**
	 * @param inBagManifestParcelTO the inBagManifestParcelTO to set
	 */
	public void setInBagManifestParcelTO(InBagManifestParcelTO inBagManifestParcelTO) {
		this.inBagManifestParcelTO = inBagManifestParcelTO;
	}
	/**
	 * @return the cityTO
	 */
	public CityTO getCityTO() {
		return cityTO;
	}
	/**
	 * @param cityTO the cityTO to set
	 */
	public void setCityTO(CityTO cityTO) {
		this.cityTO = cityTO;
	}
	/**
	 * @return the pincodeTO
	 */
	public PincodeTO getPincodeTO() {
		return pincodeTO;
	}
	/**
	 * @param pincodeTO the pincodeTO to set
	 */
	public void setPincodeTO(PincodeTO pincodeTO) {
		this.pincodeTO = pincodeTO;
	}
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
	 * @return the inBagManifestDetailsParcelTO
	 */
	public InBagManifestDetailsParcelTO getInBagManifestDetailsParcelTO() {
		return inBagManifestDetailsParcelTO;
	}
	/**
	 * @param inBagManifestDetailsParcelTO the inBagManifestDetailsParcelTO to set
	 */
	public void setInBagManifestDetailsParcelTO(
			InBagManifestDetailsParcelTO inBagManifestDetailsParcelTO) {
		this.inBagManifestDetailsParcelTO = inBagManifestDetailsParcelTO;
	}
	/**
	 * @return the manifestDate
	 */
	public Date getManifestDate() {
		return manifestDate;
	}
	/**
	 * @param manifestDate the manifestDate to set
	 */
	public void setManifestDate(Date manifestDate) {
		this.manifestDate = manifestDate;
	}
	/**
	 * @return the previousDate
	 */
	public Date getPreviousDate() {
		return previousDate;
	}
	/**
	 * @param previousDate the previousDate to set
	 */
	public void setPreviousDate(Date previousDate) {
		this.previousDate = previousDate;
	}
	/**
	 * @return the coMailId
	 */
	public Integer getCoMailId() {
		return coMailId;
	}
	/**
	 * @param coMailId the coMailId to set
	 */
	public void setCoMailId(Integer coMailId) {
		this.coMailId = coMailId;
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
