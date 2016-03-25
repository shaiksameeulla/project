package com.ff.manifest.inmanifest;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;

/**
 * @author nkattung
 * 
 */
public class InManifestDetailsTO extends CGBaseTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5036352281344050371L;
	private int count;
	private Integer manifestId;
	private String manifestNumber;
	private Double manifestWeight;
	private String remarks;
	private PincodeTO destPincode;
	private CityTO destCity;
	private Integer consignmentId;
	private ConsignmentTypeTO consignmentTypeTO;
	private Integer originOfficeId;
	private Integer destinationOfficeId;
	private Integer destinationCityId;

	private Integer updateProcessId;
	private String receivedStatus;
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Double getManifestWeight() {
		return manifestWeight;
	}

	public void setManifestWeight(Double manifestWeight) {
		this.manifestWeight = manifestWeight;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public PincodeTO getDestPincode() {
		return destPincode;
	}

	public void setDestPincode(PincodeTO destPincode) {
		this.destPincode = destPincode;
	}

	public CityTO getDestCity() {
		return destCity;
	}

	public void setDestCity(CityTO destCity) {
		this.destCity = destCity;
	}

	public Integer getManifestId() {
		return manifestId;
	}

	public void setManifestId(Integer manifestId) {
		this.manifestId = manifestId;
	}

	public Integer getConsignmentId() {
		return consignmentId;
	}

	public void setConsignmentId(Integer consignmentId) {
		this.consignmentId = consignmentId;
	}

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

	/**
	 * @return the consignmentTypeTO
	 */
	public ConsignmentTypeTO getConsignmentTypeTO() {
		return consignmentTypeTO;
	}

	/**
	 * @param consignmentTypeTO the consignmentTypeTO to set
	 */
	public void setConsignmentTypeTO(ConsignmentTypeTO consignmentTypeTO) {
		this.consignmentTypeTO = consignmentTypeTO;
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
	 * @return the destinationOfficeId
	 */
	public Integer getDestinationOfficeId() {
		return destinationOfficeId;
	}

	/**
	 * @param destinationOfficeId the destinationOfficeId to set
	 */
	public void setDestinationOfficeId(Integer destinationOfficeId) {
		this.destinationOfficeId = destinationOfficeId;
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

}
