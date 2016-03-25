/**
 * 
 */
package com.ff.to.rate;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author prmeher
 *
 */
public class ProductToBeValidatedInputTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8182321781099328263L;
	private String productCode;
	private String customerCode;
	private String originCityCode;
	private String rateType;
	private String calculationRequestDate;
	private String ebPreference;
	
	
	/**
	 * @return the ebPreference
	 */
	public String getEbPreference() {
		return ebPreference;
	}
	/**
	 * @param ebPreference the ebPreference to set
	 */
	public void setEbPreference(String ebPreference) {
		this.ebPreference = ebPreference;
	}
	/**
	 * @return the calculationRequestDate
	 */
	public String getCalculationRequestDate() {
		return calculationRequestDate;
	}
	/**
	 * @param calculationRequestDate the calculationRequestDate to set
	 */
	public void setCalculationRequestDate(String calculationRequestDate) {
		this.calculationRequestDate = calculationRequestDate;
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
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * @return the originCityCode
	 */
	public String getOriginCityCode() {
		return originCityCode;
	}
	/**
	 * @param originCityCode the originCityCode to set
	 */
	public void setOriginCityCode(String originCityCode) {
		this.originCityCode = originCityCode;
	}
	/**
	 * @return the rateType
	 */
	public String getRateType() {
		return rateType;
	}
	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	
	
}
