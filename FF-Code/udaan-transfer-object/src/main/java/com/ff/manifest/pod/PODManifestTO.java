package com.ff.manifest.pod;

import java.util.Set;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.OfficeTO;
import com.ff.tracking.ProcessTO;

/**
 * @author prmeher
 * 
 */
public class PODManifestTO extends CGBaseTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer manifestId;
	private Integer manifestProcessId;
	private String manifestDate;
	private String manifestNo;
	private OfficeTO dispachOfficeTO;
	private Integer regionId;
	private String manifestStatus;
	private String manifestType;
	private Integer destCityId;
	private Integer destOffId;
	private String officeType;
	private ProcessTO process;
	private OfficeTO destOfficeTO;
	private String manifestProcessNumber;
	private String loggedInofficeTypeCode; 
	private Integer operatingOffice;
	
	// for UI Specific
	private int rowCount;
	private String[] consgNumbers = new String[rowCount];
	private Integer[] consgIds = new Integer[rowCount];
	private String[] receivedDates = new String[rowCount];
	private String[] dlvDates = new String[rowCount];;
	private String[] receiverNames = new String[rowCount];
	private String[] receiveStatus = new String[rowCount];
	private Integer[] position = new Integer[rowCount];
	
	private String destOffice;
	private String destCity;
	private String destRegion;

	private Set<PODManifestDtlsTO> manifestDtls;

	// for validation
	private String isManifestExist = "Y";
	private String errorMsg;
	private String manifestDirection;
	private Integer logingUserId;

	
	
	
	/**
	 * @return the operatingOffice
	 */
	public Integer getOperatingOffice() {
		return operatingOffice;
	}

	/**
	 * @param operatingOffice the operatingOffice to set
	 */
	public void setOperatingOffice(Integer operatingOffice) {
		this.operatingOffice = operatingOffice;
	}

	/**
	 * @return the loggedInofficeTypeCode
	 */
	public String getLoggedInofficeTypeCode() {
		return loggedInofficeTypeCode;
	}

	/**
	 * @param loggedInofficeTypeCode the loggedInofficeTypeCode to set
	 */
	public void setLoggedInofficeTypeCode(String loggedInofficeTypeCode) {
		this.loggedInofficeTypeCode = loggedInofficeTypeCode;
	}

	/**
	 * @return the manifestProcessNumber
	 */
	public String getManifestProcessNumber() {
		return manifestProcessNumber;
	}

	/**
	 * @param manifestProcessNumber the manifestProcessNumber to set
	 */
	public void setManifestProcessNumber(String manifestProcessNumber) {
		this.manifestProcessNumber = manifestProcessNumber;
	}

	/**
	 * @return the logingUserId
	 */
	public Integer getLogingUserId() {
		return logingUserId;
	}

	/**
	 * @param logingUserId the logingUserId to set
	 */
	public void setLogingUserId(Integer logingUserId) {
		this.logingUserId = logingUserId;
	}

	/**
	 * @return the position
	 */
	public Integer[] getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer[] position) {
		this.position = position;
	}

	/**
	 * @return the destOfficeTO
	 */
	public OfficeTO getDestOfficeTO() {
		return destOfficeTO;
	}

	/**
	 * @param destOfficeTO the destOfficeTO to set
	 */
	public void setDestOfficeTO(OfficeTO destOfficeTO) {
		this.destOfficeTO = destOfficeTO;
	}

	/**
	 * @return the manifestDirection
	 */
	public String getManifestDirection() {
		return manifestDirection;
	}

	/**
	 * @param manifestDirection the manifestDirection to set
	 */
	public void setManifestDirection(String manifestDirection) {
		this.manifestDirection = manifestDirection;
	}


	/**
	 * @return the manifestId
	 */
	public Integer getManifestId() {
		return manifestId;
	}

	/**
	 * @param manifestId
	 *            the manifestId to set
	 */
	public void setManifestId(Integer manifestId) {
		this.manifestId = manifestId;
	}

	/**
	 * @return the manifestDate
	 */
	public String getManifestDate() {
		return manifestDate;
	}

	/**
	 * @param manifestDate
	 *            the manifestDate to set
	 */
	public void setManifestDate(String manifestDate) {
		this.manifestDate = manifestDate;
	}

	/**
	 * @return the manifestNo
	 */
	public String getManifestNo() {
		return manifestNo;
	}

	/**
	 * @param manifestNo
	 *            the manifestNo to set
	 */
	public void setManifestNo(String manifestNo) {
		this.manifestNo = manifestNo;
	}

	/**
	 * @return the dispachOfficeTO
	 */
	public OfficeTO getDispachOfficeTO() {
		return dispachOfficeTO;
	}

	/**
	 * @param dispachOfficeTO
	 *            the dispachOfficeTO to set
	 */
	public void setDispachOfficeTO(OfficeTO dispachOfficeTO) {
		this.dispachOfficeTO = dispachOfficeTO;
	}

	/**
	 * @return the destCityId
	 */
	public Integer getDestCityId() {
		return destCityId;
	}

	/**
	 * @param destCityId
	 *            the destCityId to set
	 */
	public void setDestCityId(Integer destCityId) {
		this.destCityId = destCityId;
	}

	/**
	 * @return the destOffId
	 */
	public Integer getDestOffId() {
		return destOffId;
	}

	/**
	 * @param destOffId
	 *            the destOffId to set
	 */
	public void setDestOffId(Integer destOffId) {
		this.destOffId = destOffId;
	}

	/**
	 * @return the regionId
	 */
	public Integer getRegionId() {
		return regionId;
	}

	/**
	 * @param regionId
	 *            the regionId to set
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	/**
	 * @return the manifestStatus
	 */
	public String getManifestStatus() {
		return manifestStatus;
	}

	/**
	 * @param manifestStatus
	 *            the manifestStatus to set
	 */
	public void setManifestStatus(String manifestStatus) {
		this.manifestStatus = manifestStatus;
	}

	/**
	 * @return the manifestType
	 */
	public String getManifestType() {
		return manifestType;
	}

	/**
	 * @param manifestType
	 *            the manifestType to set
	 */
	public void setManifestType(String manifestType) {
		this.manifestType = manifestType;
	}

	/**
	 * @return the manifestDtls
	 */
	public Set<PODManifestDtlsTO> getManifestDtls() {
		return manifestDtls;
	}

	/**
	 * @param manifestDtls
	 *            the manifestDtls to set
	 */
	public void setManifestDtls(Set<PODManifestDtlsTO> manifestDtls) {
		this.manifestDtls = manifestDtls;
	}

	/**
	 * @return the consgNumbers
	 */
	public String[] getConsgNumbers() {
		return consgNumbers;
	}

	/**
	 * @param consgNumbers
	 *            the consgNumbers to set
	 */
	public void setConsgNumbers(String[] consgNumbers) {
		this.consgNumbers = consgNumbers;
	}

	/**
	 * @return the receivedDates
	 */
	public String[] getReceivedDates() {
		return receivedDates;
	}

	/**
	 * @param receivedDates
	 *            the receivedDates to set
	 */
	public void setReceivedDates(String[] receivedDates) {
		this.receivedDates = receivedDates;
	}

	/**
	 * @return the dlvDates
	 */
	public String[] getDlvDates() {
		return dlvDates;
	}

	/**
	 * @param dlvDates
	 *            the dlvDates to set
	 */
	public void setDlvDates(String[] dlvDates) {
		this.dlvDates = dlvDates;
	}

	/**
	 * @return the receiverNames
	 */
	public String[] getReceiverNames() {
		return receiverNames;
	}

	/**
	 * @param receiverNames
	 *            the receiverNames to set
	 */
	public void setReceiverNames(String[] receiverNames) {
		this.receiverNames = receiverNames;
	}

	/**
	 * @return the consgIds
	 */
	public Integer[] getConsgIds() {
		return consgIds;
	}

	/**
	 * @param consgIds
	 *            the consgIds to set
	 */
	public void setConsgIds(Integer[] consgIds) {
		this.consgIds = consgIds;
	}

	/**
	 * @return the process
	 */
	public ProcessTO getProcess() {
		return process;
	}

	/**
	 * @param process
	 *            the process to set
	 */
	public void setProcess(ProcessTO process) {
		this.process = process;
	}

	/**
	 * @return the manifestProcessId
	 */
	public Integer getManifestProcessId() {
		return manifestProcessId;
	}

	/**
	 * @param manifestProcessId
	 *            the manifestProcessId to set
	 */
	public void setManifestProcessId(Integer manifestProcessId) {
		this.manifestProcessId = manifestProcessId;
	}

	/**
	 * @return the isManifestExist
	 */
	public String getIsManifestExist() {
		return isManifestExist;
	}

	/**
	 * @param isManifestExist
	 *            the isManifestExist to set
	 */
	public void setIsManifestExist(String isManifestExist) {
		this.isManifestExist = isManifestExist;
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

	/**
	 * @return the destOffice
	 */
	public String getDestOffice() {
		return destOffice;
	}

	/**
	 * @param destOffice
	 *            the destOffice to set
	 */
	public void setDestOffice(String destOffice) {
		this.destOffice = destOffice;
	}

	/**
	 * @return the destCity
	 */
	public String getDestCity() {
		return destCity;
	}

	/**
	 * @param destCity
	 *            the destCity to set
	 */
	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}

	/**
	 * @return the destRegion
	 */
	public String getDestRegion() {
		return destRegion;
	}

	/**
	 * @param destRegion
	 *            the destRegion to set
	 */
	public void setDestRegion(String destRegion) {
		this.destRegion = destRegion;
	}

	/**
	 * @return the receiveStatus
	 */
	public String[] getReceiveStatus() {
		return receiveStatus;
	}

	/**
	 * @param receiveStatus
	 *            the receiveStatus to set
	 */
	public void setReceiveStatus(String[] receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	/**
	 * @return the officeType
	 */
	public String getOfficeType() {
		return officeType;
	}

	/**
	 * @param officeType the officeType to set
	 */
	public void setOfficeType(String officeType) {
		this.officeType = officeType;
	}

	
	
}
