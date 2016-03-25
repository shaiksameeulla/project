package com.ff.domain.coloading;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;


/**
 * @author cbhure
 *
 */
public class SAPCocourierDO extends CGFactDO {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4550390739213769319L;
	
	private Long id;
	private Long deliveryDetailId;
	private Date date;
	private String vendorCode;
	private String status;
	private Integer qty;
	private String productCode;
	private String offCode;
	private String consgType;
	private String serviceType;
	private Double weight;
 	private String sapStatus;
 	private Date sapTimestamp;
 	private String exception;
 	
 	
	/**
	 * @return the deliveryDetailId
	 */
	public Long getDeliveryDetailId() {
		return deliveryDetailId;
	}
	/**
	 * @param deliveryDetailId the deliveryDetailId to set
	 */
	public void setDeliveryDetailId(Long deliveryDetailId) {
		this.deliveryDetailId = deliveryDetailId;
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
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the vendorCode
	 */
	public String getVendorCode() {
		return vendorCode;
	}
	/**
	 * @param vendorCode the vendorCode to set
	 */
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
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
	 * @return the qty
	 */
	public Integer getQty() {
		return qty;
	}
	/**
	 * @param qty the qty to set
	 */
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
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
	 * @return the consgType
	 */
	public String getConsgType() {
		return consgType;
	}
	/**
	 * @param consgType the consgType to set
	 */
	public void setConsgType(String consgType) {
		this.consgType = consgType;
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
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
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
