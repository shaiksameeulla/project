
/**
 * 
 */
package com.ff.manifest;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.tracking.ProcessTO;

/**
 * @author nkattung
 * 
 */
public class ManifestBaseTO extends CGBaseTO implements Comparable<ManifestBaseTO>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1396569449517612992L;
	private int count;
	private Double totalManifestWeight;
	private Integer manifestId;
	private String manifestNumber;
	private Double manifestWeight;
	private String manifestType; // 'I','O','R'
	private String manifestDate;
	private ConsignmentTypeTO consignmentTypeTO;
	private CityTO originCityTO;
	private OfficeTO originOfficeTO;
	private OfficeTO destinationOfficeTO;
	private OfficeTypeTO officeTypeTO;
	private String processCode;
	private String lockNum;
	private String updateProcessCode;
	private Integer updateProcessId;
	private String loginOfficeName;
	private String processNo;
	private Integer processId;
	private RegionTO originRegionTO;
	private String manifestDirection;
	private String transMsg;
	private Integer[] consignmentManifestIds = new Integer[count];
	private Integer loggedInOfficeId;
	private String isManifested;
	private ProcessTO processTO;
	private Integer destinationCityId;
	private Integer destinationOfficeId;
	private Integer originOfficeId;
	private Integer manifestEmbeddedIn;
	private Integer position;

	private Boolean isExcludeManifestType = Boolean.FALSE;
	private Boolean isFetchProfileManifestEmbedded = Boolean.FALSE;//enable fetch profle of manifestDOs
	private Boolean isFetchProfileManifestParcel = Boolean.FALSE;//enable fetch profle of consignments
	private Boolean isFetchProfileManifestDox = Boolean.FALSE;//enable fetch profle of consignments, comails
	private Boolean isNoOfElementNotNull = Boolean.FALSE;

	private Boolean isInManifest = Boolean.FALSE;
	
	private CityTO destCityTO;
	/**
	 * Outputs
	 */
	private String errorMsg;
	private String mnfstWeight;
	
	public String getMnfstWeight() {
		return mnfstWeight;
	}

	public void setMnfstWeight(String mnfstWeight) {
		this.mnfstWeight = mnfstWeight;
	}

	/**
	 * @return the isNoOfElementNotNull
	 */
	public Boolean getIsNoOfElementNotNull() {
		return isNoOfElementNotNull;
	}

	/**
	 * @param isNoOfElementNotNull the isNoOfElementNotNull to set
	 */
	public void setIsNoOfElementNotNull(Boolean isNoOfElementNotNull) {
		this.isNoOfElementNotNull = isNoOfElementNotNull;
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
	 * @return the loginOfficeName
	 */
	public String getLoginOfficeName() {
		return loginOfficeName;
	}

	/**
	 * @param loginOfficeName the loginOfficeName to set
	 */
	public void setLoginOfficeName(String loginOfficeName) {
		this.loginOfficeName = loginOfficeName;
	}

	/**
	 * @return the processNo
	 */
	public String getProcessNo() {
		return processNo;
	}

	/**
	 * @param processNo the processNo to set
	 */
	public void setProcessNo(String processNo) {
		this.processNo = processNo;
	}

	/**
	 * @return the processId
	 */
	public Integer getProcessId() {
		return processId;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	

	public Integer getManifestId() {
		return manifestId;
	}

	public void setManifestId(Integer manifestId) {
		this.manifestId = manifestId;
	}

	public String getManifestNumber() {
		return manifestNumber;
	}

	public void setManifestNumber(String manifestNumber) {
		this.manifestNumber = manifestNumber;
	}

	public Double getManifestWeight() {
		return manifestWeight;
	}

	public void setManifestWeight(Double manifestWeight) {
		this.manifestWeight = manifestWeight;
	}

	public String getManifestType() {
		return manifestType;
	}

	/**
	 * Sets the manifest type.
	 *
	 * @param manifestType the new manifest type
	 */
	public void setManifestType(String manifestType) {
		this.manifestType = manifestType;
	}

	/**
	 * Gets the manifest date.
	 *
	 * @return the manifest date
	 */
	public String getManifestDate() {
		return manifestDate;
	}

	/**
	 * Sets the manifest date.
	 *
	 * @param manifestDate the new manifest date
	 */
	public void setManifestDate(String manifestDate) {
		this.manifestDate = manifestDate;
	}

	/**
	 * Gets the consignment type to.
	 *
	 * @return the consignment type to
	 */
	public ConsignmentTypeTO getConsignmentTypeTO() {
		return consignmentTypeTO;
	}

	/**
	 * Sets the consignment type to.
	 *
	 * @param consignmentTypeTO the new consignment type to
	 */
	public void setConsignmentTypeTO(ConsignmentTypeTO consignmentTypeTO) {
		this.consignmentTypeTO = consignmentTypeTO;
	}


	/**
	 * @return the originCityTO
	 */
	/**
	 * Gets the origin city to.
	 *
	 * @return the origin city to
	 */
	public CityTO getOriginCityTO() {
		return originCityTO;
	}

	/**
	 * @param originCityTO the originCityTO to set
	 */
	/**
	 * Sets the origin city to.
	 *
	 * @param originCityTO the new origin city to
	 */
	public void setOriginCityTO(CityTO originCityTO) {
		this.originCityTO = originCityTO;
	}

	/**
	 * @return the originOfficeTO
	 */
	/**
	 * Gets the origin office to.
	 *
	 * @return the origin office to
	 */
	public OfficeTO getOriginOfficeTO() {
		return originOfficeTO;
	}

	/**
	 * @param originOfficeTO the originOfficeTO to set
	 */
	/**
	 * Sets the origin office to.
	 *
	 * @param originOfficeTO the new origin office to
	 */
	public void setOriginOfficeTO(OfficeTO originOfficeTO) {
		this.originOfficeTO = originOfficeTO;
	}

	/**
	 * @return the destinationOfficeTO
	 */
	/**
	 * Gets the destination office to.
	 *
	 * @return the destination office to
	 */
	public OfficeTO getDestinationOfficeTO() {
		return destinationOfficeTO;
	}

	/**
	 * @param destinationOfficeTO the destinationOfficeTO to set
	 */
	/**
	 * Sets the destination office to.
	 *
	 * @param destinationOfficeTO the new destination office to
	 */
	public void setDestinationOfficeTO(OfficeTO destinationOfficeTO) {
		this.destinationOfficeTO = destinationOfficeTO;
	}

	/**
	 * Gets the process code.
	 *
	 * @return the processCode
	 */
	public String getProcessCode() {
		return processCode;
	}

	/**
	 * Sets the process code.
	 *
	 * @param processCode the processCode to set
	 */
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	/**
	 * Gets the lock num.
	 *
	 * @return the lockNum
	 */
	public String getLockNum() {
		return lockNum;
	}

	/**
	 * Sets the lock num.
	 *
	 * @param lockNum the lockNum to set
	 */
	public void setLockNum(String lockNum) {
		this.lockNum = lockNum;
	}

	/**
	 * Gets the update process code.
	 *
	 * @return the updateProcessCode
	 */
	public String getUpdateProcessCode() {
		return updateProcessCode;
	}

	/**
	 * Sets the update process code.
	 *
	 * @param updateProcessCode the updateProcessCode to set
	 */
	public void setUpdateProcessCode(String updateProcessCode) {
		this.updateProcessCode = updateProcessCode;
	}

	/**
	 * @return the updateProcessId
	 */
	public Integer getUpdateProcessId() {
		return updateProcessId;
	}

	/**
	 * @param updateProcessId the updateProcessId to set
	 */
	public void setUpdateProcessId(Integer updateProcessId) {
		this.updateProcessId = updateProcessId;
	}

	/**
	 * @return the officeTypeTO
	 */
	public OfficeTypeTO getOfficeTypeTO() {
		return officeTypeTO;
	}

	/**
	 * @param officeTypeTO the officeTypeTO to set
	 */
	public void setOfficeTypeTO(OfficeTypeTO officeTypeTO) {
		this.officeTypeTO = officeTypeTO;
	}

	/**
	 * @return the originRegionTO
	 */
	public RegionTO getOriginRegionTO() {
		return originRegionTO;
	}

	/**
	 * @param originRegionTO the originRegionTO to set
	 */
	public void setOriginRegionTO(RegionTO originRegionTO) {
		this.originRegionTO = originRegionTO;
	}

	/**
	 * @return the transMsg
	 */
	public String getTransMsg() {
		return transMsg;
	}

	/**
	 * @param transMsg the transMsg to set
	 */
	public void setTransMsg(String transMsg) {
		this.transMsg = transMsg;
	}

	/**
	 * @return the consignmentManifestIds
	 */
	public Integer[] getConsignmentManifestIds() {
		return consignmentManifestIds;
	}

	/**
	 * @param consignmentManifestIds the consignmentManifestIds to set
	 */
	public void setConsignmentManifestIds(Integer[] consignmentManifestIds) {
		this.consignmentManifestIds = consignmentManifestIds;
	}

	/**
	 * @return the totalManifestWeight
	 */
	public Double getTotalManifestWeight() {
		return totalManifestWeight;
	}

	/**
	 * @param totalManifestWeight the totalManifestWeight to set
	 */
	public void setTotalManifestWeight(Double totalManifestWeight) {
		this.totalManifestWeight = totalManifestWeight;
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
	 * @return the isManifested
	 */
	public String getIsManifested() {
		return isManifested;
	}

	/**
	 * @param isManifested the isManifested to set
	 */
	public void setIsManifested(String isManifested) {
		this.isManifested = isManifested;
	}

	/**
	 * @return the processTO
	 */
	public ProcessTO getProcessTO() {
		return processTO;
	}

	/**
	 * @param processTO the processTO to set
	 */
	public void setProcessTO(ProcessTO processTO) {
		this.processTO = processTO;
	}

	/**
	 * @return the destinationCityId
	 */
	public Integer getDestinationCityId() {
		return destinationCityId;
	}

	/**
	 * @param destinationCityId the destinationCityId to set
	 */
	public void setDestinationCityId(Integer destinationCityId) {
		this.destinationCityId = destinationCityId;
	}

	public Integer getDestinationOfficeId() {
		return destinationOfficeId;
	}

	public void setDestinationOfficeId(Integer destinationOfficeId) {
		this.destinationOfficeId = destinationOfficeId;
	}

	public Boolean getIsFetchProfileManifestEmbedded() {
		return isFetchProfileManifestEmbedded;
	}

	public void setIsFetchProfileManifestEmbedded(
			Boolean isFetchProfileManifestEmbedded) {
		this.isFetchProfileManifestEmbedded = isFetchProfileManifestEmbedded;
	}

	public Boolean getIsFetchProfileManifestParcel() {
		return isFetchProfileManifestParcel;
	}

	public void setIsFetchProfileManifestParcel(Boolean isFetchProfileManifestParcel) {
		this.isFetchProfileManifestParcel = isFetchProfileManifestParcel;
	}

	public Boolean getIsFetchProfileManifestDox() {
		return isFetchProfileManifestDox;
	}

	public void setIsFetchProfileManifestDox(Boolean isFetchProfileManifestDox) {
		this.isFetchProfileManifestDox = isFetchProfileManifestDox;
	}

	public Integer getManifestEmbeddedIn() {
		return manifestEmbeddedIn;
	}

	public void setManifestEmbeddedIn(Integer manifestEmbeddedIn) {
		this.manifestEmbeddedIn = manifestEmbeddedIn;
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
	 * @return the originOfficeId
	 */
	public Integer getOriginOfficeId() {
		return originOfficeId;
	}

	/**
	 * @param originOfficeId the originOfficeId to set
	 */
	public void setOriginOfficeId(Integer originOfficeId) {
		this.originOfficeId = originOfficeId;
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
	 * @return the isExcludeManifestType
	 */
	public Boolean getIsExcludeManifestType() {
		return isExcludeManifestType;
	}

	/**
	 * @param isExcludeManifestType the isExcludeManifestType to set
	 */
	public void setIsExcludeManifestType(Boolean isExcludeManifestType) {
		this.isExcludeManifestType = isExcludeManifestType;
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

	public CityTO getDestCityTO() {
		return destCityTO;
	}

	public void setDestCityTO(CityTO destCityTO) {
		this.destCityTO = destCityTO;
	}
	/*The sequencing of embedded elements in OGM / BPL are getting changed in tracking*/
	@Override
	public int compareTo(ManifestBaseTO to) {
		int returnVal = 0;
		if (StringUtils.isNotEmpty(this.manifestNumber)) {
			returnVal = this.manifestNumber.compareTo(to.manifestNumber);
		}
		return returnVal;
	}
}
