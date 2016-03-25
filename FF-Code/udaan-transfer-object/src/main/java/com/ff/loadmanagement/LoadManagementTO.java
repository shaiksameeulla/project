package com.ff.loadmanagement;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.OfficeTO;

/**
 * The Class LoadManagementTO.
 *
 * @author narmdr
 */
public abstract class LoadManagementTO extends CGBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5442621608612082465L;
	
	/** The load movement id. */
	private Integer loadMovementId;
	
	/** The movement direction. */
	private String movementDirection;
	
	/** The regional office. */
	private String regionalOffice;
	
	/** The logged in office code. */
	private String loggedInOfficeCode;
	
	/** The regional office id. */
	private Integer regionalOfficeId;
	
	/** The origin office. */
	private String originOffice;
	
	/** The origin office type. */
	private String originOfficeType;
	
	/** The origin office id. */
	private Integer originOfficeId;
	
	/** The dest office type. */
	private String destOfficeType;
	
	/** The dest office. */
	private String destOffice;

	/** The dest office id. */
	private Integer destOfficeId;
	
	/** The gate pass number. */
	private String gatePassNumber;
	
	/** The vehicle number. */
	private String vehicleNumber;
	
	/** The other vehicle number. */
	private String otherVehicleNumber;
	
	/** The transport mode. */
	private String transportMode;
	
	/** The driver name. */
	private String driverName;	
	
	/** The process id. */
	private Integer processId;

	/** The process code. */
	private String processCode;
	
	/** The process number. */
	private String processNumber;
	
	/** The logged in office to. */
	private OfficeTO loggedInOfficeTO;
	
	/** The error msg. */
	private String errorMsg;	

	/** The vehicle no list. */
	private List<LabelValueBean> vehicleNoList;	

	private Double totalWeight;	
	private Integer totalBags;	

	private String errorMessage;
	
	private String successMessage;
	
	//print
	private Double totalWeightPrint;
	
	private int totalBagPrint;
	
	private OfficeTO originOfficeTO;
	
	//grid
	/** The row count. */
	private int rowCount;
	
	/** The load connected id. */
	private Integer[] loadConnectedId = new Integer[rowCount];
	
	/** The load number. */
	private String[] loadNumber = new String[rowCount];
	
	/** The doc type. */
	private String[] docType = new String[rowCount];
	
	/** The weight. */
	private Double[] weight = new Double[rowCount];
	
	/** The cd weight. */
	private Double[] cdWeight = new Double[rowCount];
	
	/** The lock number. */
	private String[] lockNumber = new String[rowCount];
	
	/** The token number. */
	private String[] tokenNumber = new String[rowCount];
	
	/** The remarks. */
	private String[] remarks = new String[rowCount];
	
	/** The manifest id. */
	private Integer[] manifestId = new Integer[rowCount];
	
	/** The manifest weight. */
	private Double[] manifestWeight = new Double[rowCount];
	
	/** The manifest dest city. */
	private String[] manifestDestCity = new String[rowCount];//cityId~code~name
	
	/** The manifest dest city details. */
	private String[] manifestDestCityDetails = new String[rowCount];
	
	/** The weight tolerance. */
	private String[] weightTolerance = new String[rowCount];

	/** The manifest origin off id. */
	private Integer[] manifestOriginOffId = new Integer[rowCount];
	
	/** The manifest dest off id. */
	private Integer[] manifestDestOffId = new Integer[rowCount];

	private Integer[] consgTypeId = new Integer[rowCount];
	/**
	 * Gets the vehicle no list.
	 *
	 * @return the vehicle no list
	 */
	public List<LabelValueBean> getVehicleNoList() {
		if(this.vehicleNoList==null){
			vehicleNoList = new ArrayList<LabelValueBean>();
		}
		return vehicleNoList;
	}
	
	/**
	 * Sets the vehicle no list.
	 *
	 * @param vehicleNoList the new vehicle no list
	 */
	public void setVehicleNoList(List<LabelValueBean> vehicleNoList) {
		this.vehicleNoList = vehicleNoList;
	}
	
	/**
	 * Gets the load movement id.
	 *
	 * @return the load movement id
	 */
	public Integer getLoadMovementId() {
		return loadMovementId;
	}
	
	/**
	 * Sets the load movement id.
	 *
	 * @param loadMovementId the new load movement id
	 */
	public void setLoadMovementId(Integer loadMovementId) {
		this.loadMovementId = loadMovementId;
	}
	
	/**
	 * Gets the movement direction.
	 *
	 * @return the movement direction
	 */
	public String getMovementDirection() {
		return movementDirection;
	}
	
	/**
	 * Sets the movement direction.
	 *
	 * @param movementDirection the new movement direction
	 */
	public void setMovementDirection(String movementDirection) {
		this.movementDirection = movementDirection;
	}
	
	/**
	 * Gets the regional office.
	 *
	 * @return the regional office
	 */
	public String getRegionalOffice() {
		return regionalOffice;
	}
	
	/**
	 * Sets the regional office.
	 *
	 * @param regionalOffice the new regional office
	 */
	public void setRegionalOffice(String regionalOffice) {
		this.regionalOffice = regionalOffice;
	}
	
	/**
	 * Gets the regional office id.
	 *
	 * @return the regional office id
	 */
	public Integer getRegionalOfficeId() {
		return regionalOfficeId;
	}
	
	/**
	 * Sets the regional office id.
	 *
	 * @param regionalOfficeId the new regional office id
	 */
	public void setRegionalOfficeId(Integer regionalOfficeId) {
		this.regionalOfficeId = regionalOfficeId;
	}
	
	/**
	 * Gets the origin office.
	 *
	 * @return the origin office
	 */
	public String getOriginOffice() {
		return originOffice;
	}
	
	/**
	 * Sets the origin office.
	 *
	 * @param originOffice the new origin office
	 */
	public void setOriginOffice(String originOffice) {
		this.originOffice = originOffice;
	}
	
	/**
	 * Gets the origin office id.
	 *
	 * @return the origin office id
	 */
	public Integer getOriginOfficeId() {
		return originOfficeId;
	}
	
	/**
	 * Sets the origin office id.
	 *
	 * @param originOfficeId the new origin office id
	 */
	public void setOriginOfficeId(Integer originOfficeId) {
		this.originOfficeId = originOfficeId;
	}
	
	/**
	 * Gets the dest office type.
	 *
	 * @return the dest office type
	 */
	public String getDestOfficeType() {
		return destOfficeType;
	}
	
	/**
	 * Sets the dest office type.
	 *
	 * @param destOfficeType the new dest office type
	 */
	public void setDestOfficeType(String destOfficeType) {
		this.destOfficeType = destOfficeType;
	}
	
	/**
	 * Gets the dest office.
	 *
	 * @return the dest office
	 */
	public String getDestOffice() {
		return destOffice;
	}
	
	/**
	 * Sets the dest office.
	 *
	 * @param destOffice the new dest office
	 */
	public void setDestOffice(String destOffice) {
		this.destOffice = destOffice;
	}
	
	/**
	 * Gets the gate pass number.
	 *
	 * @return the gate pass number
	 */
	public String getGatePassNumber() {
		return gatePassNumber;
	}
	
	/**
	 * Sets the gate pass number.
	 *
	 * @param gatePassNumber the new gate pass number
	 */
	public void setGatePassNumber(String gatePassNumber) {
		this.gatePassNumber = gatePassNumber;
	}
	
	/**
	 * Gets the vehicle number.
	 *
	 * @return the vehicle number
	 */
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	
	/**
	 * Sets the vehicle number.
	 *
	 * @param vehicleNumber the new vehicle number
	 */
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	
	/**
	 * Gets the other vehicle number.
	 *
	 * @return the other vehicle number
	 */
	public String getOtherVehicleNumber() {
		return otherVehicleNumber;
	}
	
	/**
	 * Sets the other vehicle number.
	 *
	 * @param otherVehicleNumber the new other vehicle number
	 */
	public void setOtherVehicleNumber(String otherVehicleNumber) {
		this.otherVehicleNumber = otherVehicleNumber;
	}
	
	/**
	 * Gets the driver name.
	 *
	 * @return the driver name
	 */
	public String getDriverName() {
		return driverName;
	}
	
	/**
	 * Sets the driver name.
	 *
	 * @param driverName the new driver name
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	/**
	 * Gets the transport mode.
	 *
	 * @return the transport mode
	 */
	public String getTransportMode() {
		return transportMode;
	}
	
	/**
	 * Sets the transport mode.
	 *
	 * @param transportMode the new transport mode
	 */
	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}
	
	/**
	 * Gets the row count.
	 *
	 * @return the row count
	 */
	public int getRowCount() {
		return rowCount;
	}
	
	/**
	 * Sets the row count.
	 *
	 * @param rowCount the new row count
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	
	/**
	 * Gets the load connected id.
	 *
	 * @return the load connected id
	 */
	public Integer[] getLoadConnectedId() {
		return loadConnectedId;
	}
	
	/**
	 * Sets the load connected id.
	 *
	 * @param loadConnectedId the new load connected id
	 */
	public void setLoadConnectedId(Integer[] loadConnectedId) {
		this.loadConnectedId = loadConnectedId;
	}
	
	/**
	 * Gets the load number.
	 *
	 * @return the load number
	 */
	public String[] getLoadNumber() {
		return loadNumber;
	}
	
	/**
	 * Sets the load number.
	 *
	 * @param loadNumber the new load number
	 */
	public void setLoadNumber(String[] loadNumber) {
		this.loadNumber = loadNumber;
	}
	
	/**
	 * Gets the doc type.
	 *
	 * @return the doc type
	 */
	public String[] getDocType() {
		return docType;
	}
	
	/**
	 * Sets the doc type.
	 *
	 * @param docType the new doc type
	 */
	public void setDocType(String[] docType) {
		this.docType = docType;
	}
	
	/**
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	public Double[] getWeight() {
		return weight;
	}
	
	/**
	 * Sets the weight.
	 *
	 * @param weight the new weight
	 */
	public void setWeight(Double[] weight) {
		this.weight = weight;
	}
	
	/**
	 * Gets the cd weight.
	 *
	 * @return the cd weight
	 */
	public Double[] getCdWeight() {
		return cdWeight;
	}
	
	/**
	 * Sets the cd weight.
	 *
	 * @param cdWeight the new cd weight
	 */
	public void setCdWeight(Double[] cdWeight) {
		this.cdWeight = cdWeight;
	}
	
	/**
	 * Gets the lock number.
	 *
	 * @return the lock number
	 */
	public String[] getLockNumber() {
		return lockNumber;
	}
	
	/**
	 * Sets the lock number.
	 *
	 * @param lockNumber the new lock number
	 */
	public void setLockNumber(String[] lockNumber) {
		this.lockNumber = lockNumber;
	}
	
	/**
	 * Gets the token number.
	 *
	 * @return the token number
	 */
	public String[] getTokenNumber() {
		return tokenNumber;
	}
	
	/**
	 * Sets the token number.
	 *
	 * @param tokenNumber the new token number
	 */
	public void setTokenNumber(String[] tokenNumber) {
		this.tokenNumber = tokenNumber;
	}
	
	/**
	 * Gets the remarks.
	 *
	 * @return the remarks
	 */
	public String[] getRemarks() {
		return remarks;
	}
	
	/**
	 * Sets the remarks.
	 *
	 * @param remarks the new remarks
	 */
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}
	
	/**
	 * Gets the manifest id.
	 *
	 * @return the manifest id
	 */
	public Integer[] getManifestId() {
		return manifestId;
	}
	
	/**
	 * Sets the manifest id.
	 *
	 * @param manifestId the new manifest id
	 */
	public void setManifestId(Integer[] manifestId) {
		this.manifestId = manifestId;
	}
	
	/**
	 * Gets the origin office type.
	 *
	 * @return the origin office type
	 */
	public String getOriginOfficeType() {
		return originOfficeType;
	}
	
	/**
	 * Sets the origin office type.
	 *
	 * @param originOfficeType the new origin office type
	 */
	public void setOriginOfficeType(String originOfficeType) {
		this.originOfficeType = originOfficeType;
	}
	
	/**
	 * Gets the manifest dest city.
	 *
	 * @return the manifest dest city
	 */
	public String[] getManifestDestCity() {
		return manifestDestCity;
	}
	
	/**
	 * Sets the manifest dest city.
	 *
	 * @param manifestDestCity the new manifest dest city
	 */
	public void setManifestDestCity(String[] manifestDestCity) {
		this.manifestDestCity = manifestDestCity;
	}
	
	/**
	 * Gets the manifest dest city details.
	 *
	 * @return the manifest dest city details
	 */
	public String[] getManifestDestCityDetails() {
		return manifestDestCityDetails;
	}
	
	/**
	 * Sets the manifest dest city details.
	 *
	 * @param manifestDestCityDetails the new manifest dest city details
	 */
	public void setManifestDestCityDetails(String[] manifestDestCityDetails) {
		this.manifestDestCityDetails = manifestDestCityDetails;
	}
	
	/**
	 * Gets the manifest weight.
	 *
	 * @return the manifest weight
	 */
	public Double[] getManifestWeight() {
		return manifestWeight;
	}
	
	/**
	 * Sets the manifest weight.
	 *
	 * @param manifestWeight the new manifest weight
	 */
	public void setManifestWeight(Double[] manifestWeight) {
		this.manifestWeight = manifestWeight;
	}
	
	/**
	 * Gets the weight tolerance.
	 *
	 * @return the weight tolerance
	 */
	public String[] getWeightTolerance() {
		return weightTolerance;
	}
	
	/**
	 * Sets the weight tolerance.
	 *
	 * @param weightTolerance the new weight tolerance
	 */
	public void setWeightTolerance(String[] weightTolerance) {
		this.weightTolerance = weightTolerance;
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
	 * Gets the process id.
	 *
	 * @return the process id
	 */
	public Integer getProcessId() {
		return processId;
	}
	
	/**
	 * Sets the process id.
	 *
	 * @param processId the new process id
	 */
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}
	
	public Double getTotalWeightPrint() {
		return totalWeightPrint;
	}

	public void setTotalWeightPrint(Double totalWeightPrint) {
		this.totalWeightPrint = totalWeightPrint;
	}

	public int getTotalBagPrint() {
		return totalBagPrint;
	}

	public void setTotalBagPrint(int totalBagPrint) {
		this.totalBagPrint = totalBagPrint;
	}

	/**
	 * @return the loggedInOfficeCode
	 */
	public String getLoggedInOfficeCode() {
		return loggedInOfficeCode;
	}

	/**
	 * @param loggedInOfficeCode the loggedInOfficeCode to set
	 */
	public void setLoggedInOfficeCode(String loggedInOfficeCode) {
		this.loggedInOfficeCode = loggedInOfficeCode;
	}

	/**
	 * @return the loggedInOfficeTO
	 */
	public OfficeTO getLoggedInOfficeTO() {
		return loggedInOfficeTO;
	}

	/**
	 * @param loggedInOfficeTO the loggedInOfficeTO to set
	 */
	public void setLoggedInOfficeTO(OfficeTO loggedInOfficeTO) {
		this.loggedInOfficeTO = loggedInOfficeTO;
	}

	/**
	 * @return the processNumber
	 */
	public String getProcessNumber() {
		return processNumber;
	}

	/**
	 * @param processNumber the processNumber to set
	 */
	public void setProcessNumber(String processNumber) {
		this.processNumber = processNumber;
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
	 * @return the destOfficeId
	 */
	public Integer getDestOfficeId() {
		return destOfficeId;
	}

	/**
	 * @param destOfficeId the destOfficeId to set
	 */
	public void setDestOfficeId(Integer destOfficeId) {
		this.destOfficeId = destOfficeId;
	}

	/**
	 * @return the originOfficeTO
	 */
	public OfficeTO getOriginOfficeTO() {
		return originOfficeTO;
	}

	/**
	 * @param originOfficeTO the originOfficeTO to set
	 */
	public void setOriginOfficeTO(OfficeTO originOfficeTO) {
		this.originOfficeTO = originOfficeTO;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the successMessage
	 */
	public String getSuccessMessage() {
		return successMessage;
	}

	/**
	 * @param successMessage the successMessage to set
	 */
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	/**
	 * @return the manifestOriginOffId
	 */
	public Integer[] getManifestOriginOffId() {
		return manifestOriginOffId;
	}

	/**
	 * @param manifestOriginOffId the manifestOriginOffId to set
	 */
	public void setManifestOriginOffId(Integer[] manifestOriginOffId) {
		this.manifestOriginOffId = manifestOriginOffId;
	}

	/**
	 * @return the manifestDestOffId
	 */
	public Integer[] getManifestDestOffId() {
		return manifestDestOffId;
	}

	/**
	 * @param manifestDestOffId the manifestDestOffId to set
	 */
	public void setManifestDestOffId(Integer[] manifestDestOffId) {
		this.manifestDestOffId = manifestDestOffId;
	}

	/**
	 * @return the consgTypeId
	 */
	public Integer[] getConsgTypeId() {
		return consgTypeId;
	}

	/**
	 * @param consgTypeId the consgTypeId to set
	 */
	public void setConsgTypeId(Integer[] consgTypeId) {
		this.consgTypeId = consgTypeId;
	}

	/**
	 * @return the totalWeight
	 */
	public Double getTotalWeight() {
		return totalWeight;
	}

	/**
	 * @param totalWeight the totalWeight to set
	 */
	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	/**
	 * @return the totalBags
	 */
	public Integer getTotalBags() {
		return totalBags;
	}

	/**
	 * @param totalBags the totalBags to set
	 */
	public void setTotalBags(Integer totalBags) {
		this.totalBags = totalBags;
	}
}
