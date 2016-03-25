package com.ff.coloading;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class CdAwbModificationDetailsTO.
 * 
 * @author narmdr
 */
public class CdAwbModificationDetailsTO extends CGBaseTO {

	private static final long serialVersionUID = 4529859161480063294L;
	private Integer loadConnectedId;
	private Integer loadMovementId;
	private String tokenNumber;
	private String transportModeCode;
	
	private String dispatchedUsing = CommonConstants.ENUM_DEFAULT_NULL;//CD/AWB/RR/Z
	private String dispatchedType = CommonConstants.ENUM_DEFAULT_NULL;//CC/FF/Z
	private String rateCalculated = CommonConstants.NO;//Y/N
	
	private String dispatchDate;
	private String gatePassNumber;
	private String vendor;
	private String destOffice;
	
	/**
	 * @return the loadConnectedId
	 */
	public Integer getLoadConnectedId() {
		return loadConnectedId;
	}
	/**
	 * @param loadConnectedId the loadConnectedId to set
	 */
	public void setLoadConnectedId(Integer loadConnectedId) {
		this.loadConnectedId = loadConnectedId;
	}
	/**
	 * @return the tokenNumber
	 */
	public String getTokenNumber() {
		return tokenNumber;
	}
	/**
	 * @param tokenNumber the tokenNumber to set
	 */
	public void setTokenNumber(String tokenNumber) {
		this.tokenNumber = tokenNumber;
	}
	/**
	 * @return the dispatchedUsing
	 */
	public String getDispatchedUsing() {
		return dispatchedUsing;
	}
	/**
	 * @param dispatchedUsing the dispatchedUsing to set
	 */
	public void setDispatchedUsing(String dispatchedUsing) {
		this.dispatchedUsing = dispatchedUsing;
	}
	/**
	 * @return the dispatchedType
	 */
	public String getDispatchedType() {
		return dispatchedType;
	}
	/**
	 * @param dispatchedType the dispatchedType to set
	 */
	public void setDispatchedType(String dispatchedType) {
		this.dispatchedType = dispatchedType;
	}
	/**
	 * @return the rateCalculated
	 */
	public String getRateCalculated() {
		return rateCalculated;
	}
	/**
	 * @param rateCalculated the rateCalculated to set
	 */
	public void setRateCalculated(String rateCalculated) {
		this.rateCalculated = rateCalculated;
	}
	/**
	 * @return the loadMovementId
	 */
	public Integer getLoadMovementId() {
		return loadMovementId;
	}
	/**
	 * @param loadMovementId the loadMovementId to set
	 */
	public void setLoadMovementId(Integer loadMovementId) {
		this.loadMovementId = loadMovementId;
	}
	/**
	 * @return the transportModeCode
	 */
	public String getTransportModeCode() {
		return transportModeCode;
	}
	/**
	 * @param transportModeCode the transportModeCode to set
	 */
	public void setTransportModeCode(String transportModeCode) {
		this.transportModeCode = transportModeCode;
	}
	public String getDispatchDate() {
		return dispatchDate;
	}
	public void setDispatchDate(String dispatchDate) {
		this.dispatchDate = dispatchDate;
	}
	public String getGatePassNumber() {
		return gatePassNumber;
	}
	public void setGatePassNumber(String gatePassNumber) {
		this.gatePassNumber = gatePassNumber;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getDestOffice() {
		return destOffice;
	}
	public void setDestOffice(String destOffice) {
		this.destOffice = destOffice;
	}

}
