package com.ff.coloading;

import java.util.List;
import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.RegionTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

/**
 * The Class CdAwbModificationTO.
 * @author narmdr
 */
public class CdAwbModificationTO extends CGBaseTO {

	private static final long serialVersionUID = -5488526869862173192L;
	private String status;
	private String fromDate;
	private String toDate;
	private String defaultNull = CommonConstants.ENUM_DEFAULT_NULL;
	private String transportModeRoadCode = CommonConstants.TRANSPORT_MODE_ROAD_CODE;

	private RegionTO regionTO;
	
	private List<CdAwbModificationDetailsTO> cdAwbModificationDetailsTOs;
	private List<StockStandardTypeTO> standardTypeTOs4Status;
	private List<StockStandardTypeTO> standardTypeTOs4DispUsing;
	private List<StockStandardTypeTO> standardTypeTOs4DispType;
	
	//UI Specific attributes
	private int count;
	private Integer[] loadConnectedIds = new Integer[count];
	private Integer[] loadMovementIds = new Integer[count];
	private String[] tokenNumbers = new String[count];
	private String[] dispatchedUsings = new String[count];
	private String[] dispatchedTypes = new String[count];
	private String[] transportModeCodes = new String[count];
	//private String[] rateCalculateds = new String[count];

	/**
	 * Outputs
	 */
	private String errorMsg;
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the defaultNull
	 */
	public String getDefaultNull() {
		return defaultNull;
	}
	/**
	 * @param defaultNull the defaultNull to set
	 */
	public void setDefaultNull(String defaultNull) {
		this.defaultNull = defaultNull;
	}
	/**
	 * @return the cdAwbModificationDetailsTOs
	 */
	public List<CdAwbModificationDetailsTO> getCdAwbModificationDetailsTOs() {
		return cdAwbModificationDetailsTOs;
	}
	/**
	 * @param cdAwbModificationDetailsTOs the cdAwbModificationDetailsTOs to set
	 */
	public void setCdAwbModificationDetailsTOs(
			List<CdAwbModificationDetailsTO> cdAwbModificationDetailsTOs) {
		this.cdAwbModificationDetailsTOs = cdAwbModificationDetailsTOs;
	}
	/**
	 * @return the regionTO
	 */
	public RegionTO getRegionTO() {
		return regionTO;
	}
	/**
	 * @param regionTO the regionTO to set
	 */
	public void setRegionTO(RegionTO regionTO) {
		this.regionTO = regionTO;
	}
	/**
	 * @return the standardTypeTOs4Status
	 */
	public List<StockStandardTypeTO> getStandardTypeTOs4Status() {
		return standardTypeTOs4Status;
	}
	/**
	 * @param standardTypeTOs4Status the standardTypeTOs4Status to set
	 */
	public void setStandardTypeTOs4Status(List<StockStandardTypeTO> standardTypeTOs4Status) {
		this.standardTypeTOs4Status = standardTypeTOs4Status;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * @return the loadConnectedIds
	 */
	public Integer[] getLoadConnectedIds() {
		return loadConnectedIds;
	}
	/**
	 * @param loadConnectedIds the loadConnectedIds to set
	 */
	public void setLoadConnectedIds(Integer[] loadConnectedIds) {
		this.loadConnectedIds = loadConnectedIds;
	}
	/**
	 * @return the tokenNumbers
	 */
	public String[] getTokenNumbers() {
		return tokenNumbers;
	}
	/**
	 * @param tokenNumbers the tokenNumbers to set
	 */
	public void setTokenNumbers(String[] tokenNumbers) {
		this.tokenNumbers = tokenNumbers;
	}
	/**
	 * @return the dispatchedUsings
	 */
	public String[] getDispatchedUsings() {
		return dispatchedUsings;
	}
	/**
	 * @param dispatchedUsings the dispatchedUsings to set
	 */
	public void setDispatchedUsings(String[] dispatchedUsings) {
		this.dispatchedUsings = dispatchedUsings;
	}
	/**
	 * @return the dispatchedTypes
	 */
	public String[] getDispatchedTypes() {
		return dispatchedTypes;
	}
	/**
	 * @param dispatchedTypes the dispatchedTypes to set
	 */
	public void setDispatchedTypes(String[] dispatchedTypes) {
		this.dispatchedTypes = dispatchedTypes;
	}
	/**
	 * @return the standardTypeTOs4DispUsing
	 */
	public List<StockStandardTypeTO> getStandardTypeTOs4DispUsing() {
		return standardTypeTOs4DispUsing;
	}
	/**
	 * @param standardTypeTOs4DispUsing the standardTypeTOs4DispUsing to set
	 */
	public void setStandardTypeTOs4DispUsing(
			List<StockStandardTypeTO> standardTypeTOs4DispUsing) {
		this.standardTypeTOs4DispUsing = standardTypeTOs4DispUsing;
	}
	/**
	 * @return the standardTypeTOs4DispType
	 */
	public List<StockStandardTypeTO> getStandardTypeTOs4DispType() {
		return standardTypeTOs4DispType;
	}
	/**
	 * @param standardTypeTOs4DispType the standardTypeTOs4DispType to set
	 */
	public void setStandardTypeTOs4DispType(
			List<StockStandardTypeTO> standardTypeTOs4DispType) {
		this.standardTypeTOs4DispType = standardTypeTOs4DispType;
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
	 * @return the loadMovementIds
	 */
	public Integer[] getLoadMovementIds() {
		return loadMovementIds;
	}
	/**
	 * @param loadMovementIds the loadMovementIds to set
	 */
	public void setLoadMovementIds(Integer[] loadMovementIds) {
		this.loadMovementIds = loadMovementIds;
	}
	/**
	 * @return the transportModeCodes
	 */
	public String[] getTransportModeCodes() {
		return transportModeCodes;
	}
	/**
	 * @param transportModeCodes the transportModeCodes to set
	 */
	public void setTransportModeCodes(String[] transportModeCodes) {
		this.transportModeCodes = transportModeCodes;
	}
	/**
	 * @return the transportModeRoadCode
	 */
	public String getTransportModeRoadCode() {
		return transportModeRoadCode;
	}
	/**
	 * @param transportModeRoadCode the transportModeRoadCode to set
	 */
	public void setTransportModeRoadCode(String transportModeRoadCode) {
		this.transportModeRoadCode = transportModeRoadCode;
	}
}
