/**
 * 
 */
package com.ff.rateCalculator;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author prmeher
 *
 */
public class RateCalculatorTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1715798134636099580L;

	private String productType;
	private String serviceAt;
	private String CNtype;
	private String pincode;
	private String destination;
	private Double weightKg;
	private Double weightGrm;
	private String originOfficeCode;
	private Integer originCityId;
	private String preferences;
	private Double declaredValue;
	private String insuredBy;
	
	public String getPreferences() {
		return preferences;
	}
	public void setPreferences(String preferences) {
		this.preferences = preferences;
	}
	/**
	 * @return the originCityId
	 */
	public Integer getOriginCityId() {
		return originCityId;
	}
	/**
	 * @param originCityId the originCityId to set
	 */
	public void setOriginCityId(Integer originCityId) {
		this.originCityId = originCityId;
	}
	/**
	 * @return the originOfficeCode
	 */
	public String getOriginOfficeCode() {
		return originOfficeCode;
	}
	/**
	 * @param originOfficeCode the originOfficeCode to set
	 */
	public void setOriginOfficeCode(String originOfficeCode) {
		this.originOfficeCode = originOfficeCode;
	}
	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}
	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}
	/**
	 * @return the serviceAt
	 */
	public String getServiceAt() {
		return serviceAt;
	}
	/**
	 * @param serviceAt the serviceAt to set
	 */
	public void setServiceAt(String serviceAt) {
		this.serviceAt = serviceAt;
	}
	/**
	 * @return the cNtype
	 */
	public String getCNtype() {
		return CNtype;
	}
	/**
	 * @param cNtype the cNtype to set
	 */
	public void setCNtype(String cNtype) {
		CNtype = cNtype;
	}
	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}
	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return the weightKg
	 */
	public Double getWeightKg() {
		return weightKg;
	}
	/**
	 * @param weightKg the weightKg to set
	 */
	public void setWeightKg(Double weightKg) {
		this.weightKg = weightKg;
	}
	/**
	 * @return the weightGrm
	 */
	public Double getWeightGrm() {
		return weightGrm;
	}
	/**
	 * @param weightGrm the weightGrm to set
	 */
	public void setWeightGrm(Double weightGrm) {
		this.weightGrm = weightGrm;
	}
	/**
	 * @return the declaredValue
	 */
	public Double getDeclaredValue() {
		return declaredValue;
	}
	/**
	 * @param declaredValue the declaredValue to set
	 */
	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}
	/**
	 * @return the insuredBy
	 */
	public String getInsuredBy() {
		return insuredBy;
	}
	/**
	 * @param insuredBy the insuredBy to set
	 */
	public void setInsuredBy(String insuredBy) {
		this.insuredBy = insuredBy;
	}
	
}
