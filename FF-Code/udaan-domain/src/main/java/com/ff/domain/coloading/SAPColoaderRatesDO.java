package com.ff.domain.coloading;

import java.util.Date;


public class SAPColoaderRatesDO implements java.io.Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7601497009574191741L;
	
	private Long id;
	private Integer transactionNumber;
	private Date dispatchDate;
	private String vendorName;
	private String subRateType;
	private String awbCDRRNumber;
	private String transportRefNumber;
	private String destinationCity;
	private Double basic;
	private Double grossTotal;
	private String serviceType;
	private String uom;
	private Double qty;
	private String offCode;;
	private String tripShitNumber;
	private Double otherCharges;
 	private Double total;
 	private String sapStatus;
 	private Date sapTimestamp;
 	private String exception;
 	
 	
	/**
	 * @return the awbCDRRNumber
	 */
	public String getAwbCDRRNumber() {
		return awbCDRRNumber;
	}
	/**
	 * @param awbCDRRNumber the awbCDRRNumber to set
	 */
	public void setAwbCDRRNumber(String awbCDRRNumber) {
		this.awbCDRRNumber = awbCDRRNumber;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the transactionNumber
	 */
	public Integer getTransactionNumber() {
		return transactionNumber;
	}
	/**
	 * @param transactionNumber the transactionNumber to set
	 */
	public void setTransactionNumber(Integer transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	/**
	 * @return the dispatchDate
	 */
	public Date getDispatchDate() {
		return dispatchDate;
	}
	/**
	 * @param dispatchDate the dispatchDate to set
	 */
	public void setDispatchDate(Date dispatchDate) {
		this.dispatchDate = dispatchDate;
	}
	/**
	 * @return the vendorName
	 */
	public String getVendorName() {
		return vendorName;
	}
	/**
	 * @param vendorName the vendorName to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	/**
	 * @return the subRateType
	 */
	public String getSubRateType() {
		return subRateType;
	}
	/**
	 * @param subRateType the subRateType to set
	 */
	public void setSubRateType(String subRateType) {
		this.subRateType = subRateType;
	}
	/**
	 * @return the transportRefNumber
	 */
	public String getTransportRefNumber() {
		return transportRefNumber;
	}
	/**
	 * @param transportRefNumber the transportRefNumber to set
	 */
	public void setTransportRefNumber(String transportRefNumber) {
		this.transportRefNumber = transportRefNumber;
	}
	/**
	 * @return the destinationCity
	 */
	public String getDestinationCity() {
		return destinationCity;
	}
	/**
	 * @param destinationCity the destinationCity to set
	 */
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}
	/**
	 * @return the basic
	 */
	public Double getBasic() {
		return basic;
	}
	/**
	 * @param basic the basic to set
	 */
	public void setBasic(Double basic) {
		this.basic = basic;
	}
	/**
	 * @return the grossTotal
	 */
	public Double getGrossTotal() {
		return grossTotal;
	}
	/**
	 * @param grossTotal the grossTotal to set
	 */
	public void setGrossTotal(Double grossTotal) {
		this.grossTotal = grossTotal;
	}
	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}
	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	
	/**
	 * @return the uom
	 */
	public String getUom() {
		return uom;
	}
	/**
	 * @param uom the uom to set
	 */
	public void setUom(String uom) {
		this.uom = uom;
	}
	/**
	 * @return the qty
	 */
	public Double getQty() {
		return qty;
	}
	/**
	 * @param qty the qty to set
	 */
	public void setQty(Double qty) {
		this.qty = qty;
	}
	/**
	 * @return the offCode
	 */
	public String getOffCode() {
		return offCode;
	}
	/**
	 * @param offCode the offCode to set
	 */
	public void setOffCode(String offCode) {
		this.offCode = offCode;
	}
	/**
	 * @return the tripShitNumber
	 */
	public String getTripShitNumber() {
		return tripShitNumber;
	}
	/**
	 * @param tripShitNumber the tripShitNumber to set
	 */
	public void setTripShitNumber(String tripShitNumber) {
		this.tripShitNumber = tripShitNumber;
	}
	/**
	 * @return the otherCharges
	 */
	public Double getOtherCharges() {
		return otherCharges;
	}
	/**
	 * @param otherCharges the otherCharges to set
	 */
	public void setOtherCharges(Double otherCharges) {
		this.otherCharges = otherCharges;
	}
	/**
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
	}
	/**
	 * @return the sapStatus
	 */
	public String getSapStatus() {
		return sapStatus;
	}
	/**
	 * @param sapStatus the sapStatus to set
	 */
	public void setSapStatus(String sapStatus) {
		this.sapStatus = sapStatus;
	}
	/**
	 * @return the sapTimestamp
	 */
	public Date getSapTimestamp() {
		return sapTimestamp;
	}
	/**
	 * @param sapTimestamp the sapTimestamp to set
	 */
	public void setSapTimestamp(Date sapTimestamp) {
		this.sapTimestamp = sapTimestamp;
	}
	/**
	 * @return the exception
	 */
	public String getException() {
		return exception;
	}
	/**
	 * @param exception the exception to set
	 */
	public void setException(String exception) {
		this.exception = exception;
	}
 	

}
