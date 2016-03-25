package com.ff.loadmanagement;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.to.stockmanagement.StockIssueValidationTO;
import com.ff.tracking.ProcessTO;

/**
 * The Class ManifestTO.
 *
 * @author narmdr
 */
public class ManifestTO extends CGBaseTO implements Comparable<ManifestTO>{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5147309210652763461L;
	
	/** The manifest id. */
	private Integer manifestId;
	
	/** The manifest number. */
	private String manifestNumber;
	
	/** The bag lock no. */
	private String bagLockNo;
	
	/** The manifest weight. */
	private Double manifestWeight;
	
	/** The manifest type. */
	private String manifestType; //'I','O','R'
	
	/** The manifest date. */
	private Date manifestDate = Calendar.getInstance().getTime();//today date
	
	/** The consignment type to. */
	private ConsignmentTypeTO consignmentTypeTO;
	
	/** The destination city to. */
	private CityTO destinationCityTO;
	
	/** The origin office to. */
	private OfficeTO originOfficeTO;
	
	/** The destination office to. */
	private OfficeTO destinationOfficeTO;

	/** The login office id. */
	private Integer loginOfficeId;
	
	/** The stock issue validation to. */
	private StockIssueValidationTO stockIssueValidationTO;

	/** Outputs. */
	private String errorMsg; 
	
	/** The is new manifest. */
	private boolean isNewManifest; 
	
	/** The manifest status for tracking */
	private String manifestStatus;
	
	/** The operating office. */
	private Integer operatingOffice;
		
	/** The process id. */
	private Integer processId;
	
	/** The user id. */
	private Integer userId;

	private String loadOriginOfficeType;
	
	private String loadDestOfficeType;	

	private ProcessTO updatingProcessTO;

	/** The no of elements. */
	private Integer noOfElements;
	
	private String remarks;
	
	private String mnfstWeight;
	
	private String receivedStatus;
	
	/**
	 * Gets the manifest id.
	 *
	 * @return the manifest id
	 */
	public Integer getManifestId() {
		return manifestId;
	}
	
	/**
	 * Sets the manifest id.
	 *
	 * @param manifestId the new manifest id
	 */
	public void setManifestId(Integer manifestId) {
		this.manifestId = manifestId;
	}
	
	/**
	 * Gets the manifest number.
	 *
	 * @return the manifest number
	 */
	public String getManifestNumber() {
		return manifestNumber;
	}
	
	/**
	 * Sets the manifest number.
	 *
	 * @param manifestNumber the new manifest number
	 */
	public void setManifestNumber(String manifestNumber) {
		this.manifestNumber = manifestNumber;
	}
	
	/**
	 * Gets the bag lock no.
	 *
	 * @return the bag lock no
	 */
	public String getBagLockNo() {
		return bagLockNo;
	}
	
	/**
	 * Sets the bag lock no.
	 *
	 * @param bagLockNo the new bag lock no
	 */
	public void setBagLockNo(String bagLockNo) {
		this.bagLockNo = bagLockNo;
	}
	
	/**
	 * Gets the manifest weight.
	 *
	 * @return the manifest weight
	 */
	public Double getManifestWeight() {
		return manifestWeight;
	}
	
	/**
	 * Sets the manifest weight.
	 *
	 * @param manifestWeight the new manifest weight
	 */
	public void setManifestWeight(Double manifestWeight) {
		this.manifestWeight = manifestWeight;
	}
	
	/**
	 * Gets the manifest type.
	 *
	 * @return the manifest type
	 */
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
	 * Gets the destination city to.
	 *
	 * @return the destination city to
	 */
	public CityTO getDestinationCityTO() {
		return destinationCityTO;
	}
	
	/**
	 * Sets the destination city to.
	 *
	 * @param destinationCityTO the new destination city to
	 */
	public void setDestinationCityTO(CityTO destinationCityTO) {
		this.destinationCityTO = destinationCityTO;
	}
	
	/**
	 * Gets the origin office to.
	 *
	 * @return the origin office to
	 */
	public OfficeTO getOriginOfficeTO() {
		return originOfficeTO;
	}
	
	/**
	 * Sets the origin office to.
	 *
	 * @param originOfficeTO the new origin office to
	 */
	public void setOriginOfficeTO(OfficeTO originOfficeTO) {
		this.originOfficeTO = originOfficeTO;
	}
	
	/**
	 * Gets the destination office to.
	 *
	 * @return the destination office to
	 */
	public OfficeTO getDestinationOfficeTO() {
		return destinationOfficeTO;
	}
	
	/**
	 * Sets the destination office to.
	 *
	 * @param destinationOfficeTO the new destination office to
	 */
	public void setDestinationOfficeTO(OfficeTO destinationOfficeTO) {
		this.destinationOfficeTO = destinationOfficeTO;
	}
	
	/**
	 * Gets the manifest date.
	 *
	 * @return the manifest date
	 */
	public Date getManifestDate() {
		return manifestDate;
	}
	
	/**
	 * Sets the manifest date.
	 *
	 * @param manifestDate the new manifest date
	 */
	public void setManifestDate(Date manifestDate) {
		this.manifestDate = manifestDate;
	}
	
	/**
	 * Gets the error msg.
	 *
	 * @return the error msg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	
	/**
	 * Sets the error msg.
	 *
	 * @param errorMsg the new error msg
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	/**
	 * Gets the checks if is new manifest.
	 *
	 * @return the checks if is new manifest
	 */
	public boolean getIsNewManifest() {
		return isNewManifest;
	}
	
	/**
	 * Sets the checks if is new manifest.
	 *
	 * @param isNewManifest the new checks if is new manifest
	 */
	public void setIsNewManifest(boolean isNewManifest) {
		this.isNewManifest = isNewManifest;
	}

	/**
	 * @return the stockIssueValidationTO
	 */
	public StockIssueValidationTO getStockIssueValidationTO() {
		return stockIssueValidationTO;
	}

	/**
	 * @param stockIssueValidationTO the stockIssueValidationTO to set
	 */
	public void setStockIssueValidationTO(StockIssueValidationTO stockIssueValidationTO) {
		this.stockIssueValidationTO = stockIssueValidationTO;
	}

	/**
	 * @return the manifestStatus
	 */
	public String getManifestStatus() {
		return manifestStatus;
	}

	/**
	 * @param manifestStatus the manifestStatus to set
	 */
	public void setManifestStatus(String manifestStatus) {
		this.manifestStatus = manifestStatus;
	}

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

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the loginOfficeId
	 */
	public Integer getLoginOfficeId() {
		return loginOfficeId;
	}

	/**
	 * @param loginOfficeId the loginOfficeId to set
	 */
	public void setLoginOfficeId(Integer loginOfficeId) {
		this.loginOfficeId = loginOfficeId;
	}

	/**
	 * @return the loadOriginOfficeType
	 */
	public String getLoadOriginOfficeType() {
		return loadOriginOfficeType;
	}

	/**
	 * @param loadOriginOfficeType the loadOriginOfficeType to set
	 */
	public void setLoadOriginOfficeType(String loadOriginOfficeType) {
		this.loadOriginOfficeType = loadOriginOfficeType;
	}

	/**
	 * @return the loadDestOfficeType
	 */
	public String getLoadDestOfficeType() {
		return loadDestOfficeType;
	}

	/**
	 * @param loadDestOfficeType the loadDestOfficeType to set
	 */
	public void setLoadDestOfficeType(String loadDestOfficeType) {
		this.loadDestOfficeType = loadDestOfficeType;
	}

	/**
	 * @return the updatingProcessTO
	 */
	public ProcessTO getUpdatingProcessTO() {
		return updatingProcessTO;
	}

	/**
	 * @param updatingProcessTO the updatingProcessTO to set
	 */
	public void setUpdatingProcessTO(ProcessTO updatingProcessTO) {
		this.updatingProcessTO = updatingProcessTO;
	}

	/**
	 * @return the noOfElements
	 */
	public Integer getNoOfElements() {
		return noOfElements;
	}

	/**
	 * @param noOfElements the noOfElements to set
	 */
	public void setNoOfElements(Integer noOfElements) {
		this.noOfElements = noOfElements;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getMnfstWeight() {
		return mnfstWeight;
	}

	public void setMnfstWeight(String mnfstWeight) {
		this.mnfstWeight = mnfstWeight;
	}

	@Override
	public int compareTo(ManifestTO arg0) {
		int returnVal = 0;
		if (StringUtils.isNotEmpty(this.manifestNumber)) {
			returnVal = this.manifestNumber.compareTo(arg0.manifestNumber);
		}
		return returnVal;
	}

	/**
	 * @return the receivedStatus
	 */
	public String getReceivedStatus() {
		return receivedStatus;
	}

	/**
	 * @param receivedStatus the receivedStatus to set
	 */
	public void setReceivedStatus(String receivedStatus) {
		this.receivedStatus = receivedStatus;
	}

}
