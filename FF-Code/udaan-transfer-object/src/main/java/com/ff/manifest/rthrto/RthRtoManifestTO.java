/**
 * 
 */
package com.ff.manifest.rthrto;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.tracking.ProcessTO;

/**
 * The Class RthRtoManifestTO.
 * 
 * @author narmdr
 */
public class RthRtoManifestTO extends CGBaseTO {

	private static final long serialVersionUID = -5625339616037793154L;

	private Integer manifestId;
	// commented since we no where updating the manifest again once it is saved.
	private Integer manifestProcessId;
	private String manifestNumber;
	private String manifestType; // R-RTO, H-RTH
	private String manifestDate;// rtoRth DateTime
	// Added by Himal
	private String bagLockNo;
	private ConsignmentTypeTO consignmentTypeTO;
	private RegionTO destRegionTO;
	private CityTO destCityTO;
	private OfficeTO destOfficeTO;
	private OfficeTO originOfficeTO;// dispatching office
	private String originCityCode;
	private ProcessTO processTO;
	private ProcessTO updateProcessTO;

	private Double manifestWeight;
	private String errorMsg;
	private String transMsg;

	private Set<Integer> destOffIds;

	// added for 2WayWrite
	private Integer twoWayManifestId;

	// Reusable UI Specific attributes
	private int count;
	private Integer[] consignmentIds = new Integer[count];
	private String[] consgNumbers = new String[count];
	private Integer[] reasonIds = new Integer[count];
	private String[] remarks = new String[count];
	private String[] receivedDate = new String[count];
	private Integer[] consignmentManifestIds = new Integer[count];
	private Double[] cnWeights = new Double[count];
	private Integer[] position = new Integer[count];

	// for grid
	private List<RthRtoDetailsTO> rthRtoDetailsTOs;
	// for print
	private String pincode;

	private String loggedInOfficeName;
	private String loggedInOfficeCity;

	Map<String, ConsignmentRateCalculationOutputTO> rateCompnents;

	private String regionCode;
	private Integer regionId;
	private String manifestDirection;
	private String processCode;
	
	private List<CityTO> cityTOList;

	/**
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * @param regionCode
	 *            the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
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
	 * @return the manifestDirection
	 */
	public String getManifestDirection() {
		return manifestDirection;
	}

	/**
	 * @param manifestDirection
	 *            the manifestDirection to set
	 */
	public void setManifestDirection(String manifestDirection) {
		this.manifestDirection = manifestDirection;
	}

	/**
	 * @return the processCode
	 */
	public String getProcessCode() {
		return processCode;
	}

	/**
	 * @param processCode
	 *            the processCode to set
	 */
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	/**
	 * @return the bagLockNo
	 */
	public String getBagLockNo() {
		return bagLockNo;
	}

	/**
	 * @param bagLockNo
	 *            the bagLockNo to set
	 */
	public void setBagLockNo(String bagLockNo) {
		this.bagLockNo = bagLockNo;
	}

	/**
	 * @return the position
	 */
	public Integer[] getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Integer[] position) {
		this.position = position;
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
	 * @return the manifestNumber
	 */
	public String getManifestNumber() {
		return manifestNumber;
	}

	/**
	 * @param manifestNumber
	 *            the manifestNumber to set
	 */
	public void setManifestNumber(String manifestNumber) {
		this.manifestNumber = manifestNumber;
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
	 * @return the consignmentTypeTO
	 */
	public ConsignmentTypeTO getConsignmentTypeTO() {
		return consignmentTypeTO;
	}

	/**
	 * @param consignmentTypeTO
	 *            the consignmentTypeTO to set
	 */
	public void setConsignmentTypeTO(ConsignmentTypeTO consignmentTypeTO) {
		this.consignmentTypeTO = consignmentTypeTO;
	}

	/**
	 * @return the destRegionTO
	 */
	public RegionTO getDestRegionTO() {
		return destRegionTO;
	}

	/**
	 * @param destRegionTO
	 *            the destRegionTO to set
	 */
	public void setDestRegionTO(RegionTO destRegionTO) {
		this.destRegionTO = destRegionTO;
	}

	/**
	 * @return the destCityTO
	 */
	public CityTO getDestCityTO() {
		return destCityTO;
	}

	/**
	 * @param destCityTO
	 *            the destCityTO to set
	 */
	public void setDestCityTO(CityTO destCityTO) {
		this.destCityTO = destCityTO;
	}

	/**
	 * @return the destOfficeTO
	 */
	public OfficeTO getDestOfficeTO() {
		return destOfficeTO;
	}

	/**
	 * @param destOfficeTO
	 *            the destOfficeTO to set
	 */
	public void setDestOfficeTO(OfficeTO destOfficeTO) {
		this.destOfficeTO = destOfficeTO;
	}

	/**
	 * @return the originOfficeTO
	 */
	public OfficeTO getOriginOfficeTO() {
		return originOfficeTO;
	}

	/**
	 * @param originOfficeTO
	 *            the originOfficeTO to set
	 */
	public void setOriginOfficeTO(OfficeTO originOfficeTO) {
		this.originOfficeTO = originOfficeTO;
	}

	/**
	 * @return the processTO
	 */
	public ProcessTO getProcessTO() {
		return processTO;
	}

	/**
	 * @param processTO
	 *            the processTO to set
	 */
	public void setProcessTO(ProcessTO processTO) {
		this.processTO = processTO;
	}

	/**
	 * @return the updateProcessTO
	 */
	public ProcessTO getUpdateProcessTO() {
		return updateProcessTO;
	}

	/**
	 * @param updateProcessTO
	 *            the updateProcessTO to set
	 */
	public void setUpdateProcessTO(ProcessTO updateProcessTO) {
		this.updateProcessTO = updateProcessTO;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the consignmentIds
	 */
	public Integer[] getConsignmentIds() {
		return consignmentIds;
	}

	/**
	 * @param consignmentIds
	 *            the consignmentIds to set
	 */
	public void setConsignmentIds(Integer[] consignmentIds) {
		this.consignmentIds = consignmentIds;
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
	 * @return the reasonIds
	 */
	public Integer[] getReasonIds() {
		return reasonIds;
	}

	/**
	 * @param reasonIds
	 *            the reasonIds to set
	 */
	public void setReasonIds(Integer[] reasonIds) {
		this.reasonIds = reasonIds;
	}

	/**
	 * @return the remarks
	 */
	public String[] getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the rthRtoDetailsTOs
	 */
	public List<RthRtoDetailsTO> getRthRtoDetailsTOs() {
		return rthRtoDetailsTOs;
	}

	/**
	 * @param rthRtoDetailsTOs
	 *            the rthRtoDetailsTOs to set
	 */
	public void setRthRtoDetailsTOs(List<RthRtoDetailsTO> rthRtoDetailsTOs) {
		this.rthRtoDetailsTOs = rthRtoDetailsTOs;
	}

	/**
	 * @return the receivedDate
	 */
	public String[] getReceivedDate() {
		return receivedDate;
	}

	/**
	 * @param receivedDate
	 *            the receivedDate to set
	 */
	public void setReceivedDate(String[] receivedDate) {
		this.receivedDate = receivedDate;
	}

	/**
	 * @return the consignmentManifestIds
	 */
	public Integer[] getConsignmentManifestIds() {
		return consignmentManifestIds;
	}

	/**
	 * @param consignmentManifestIds
	 *            the consignmentManifestIds to set
	 */
	public void setConsignmentManifestIds(Integer[] consignmentManifestIds) {
		this.consignmentManifestIds = consignmentManifestIds;
	}

	/**
	 * @return the manifestWeight
	 */
	public Double getManifestWeight() {
		return manifestWeight;
	}

	/**
	 * @param manifestWeight
	 *            the manifestWeight to set
	 */
	public void setManifestWeight(Double manifestWeight) {
		this.manifestWeight = manifestWeight;
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
	 * @return the cnWeights
	 */
	public Double[] getCnWeights() {
		return cnWeights;
	}

	/**
	 * @param cnWeights
	 *            the cnWeights to set
	 */
	public void setCnWeights(Double[] cnWeights) {
		this.cnWeights = cnWeights;
	}

	/**
	 * @return the originCityCode
	 */
	public String getOriginCityCode() {
		return originCityCode;
	}

	/**
	 * @param originCityCode
	 *            the originCityCode to set
	 */
	public void setOriginCityCode(String originCityCode) {
		this.originCityCode = originCityCode;
	}

	public Set<Integer> getDestOffIds() {
		return destOffIds;
	}

	public void setDestOffIds(Set<Integer> destOffIds) {
		this.destOffIds = destOffIds;
	}

	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}

	/**
	 * @param pincode
	 *            the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public Map<String, ConsignmentRateCalculationOutputTO> getRateCompnents() {
		return rateCompnents;
	}

	public void setRateCompnents(
			Map<String, ConsignmentRateCalculationOutputTO> rateCompnents) {
		this.rateCompnents = rateCompnents;
	}

	/**
	 * @return the loggedInOfficeName
	 */
	public String getLoggedInOfficeName() {
		return loggedInOfficeName;
	}

	/**
	 * @param loggedInOfficeName
	 *            the loggedInOfficeName to set
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
	 * @param loggedInOfficeCity
	 *            the loggedInOfficeCity to set
	 */
	public void setLoggedInOfficeCity(String loggedInOfficeCity) {
		this.loggedInOfficeCity = loggedInOfficeCity;
	}

	/**
	 * @return the twoWayManifestId
	 */
	public Integer getTwoWayManifestId() {
		return twoWayManifestId;
	}

	/**
	 * @param twoWayManifestId
	 *            the twoWayManifestId to set
	 */
	public void setTwoWayManifestId(Integer twoWayManifestId) {
		this.twoWayManifestId = twoWayManifestId;
	}

	public List<CityTO> getCityTOList() {
		return cityTOList;
	}

	public void setCityTOList(List<CityTO> cityTOList) {
		this.cityTOList = cityTOList;
	}

}
