/**
 * 
 */
package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author cbhure
 *
 */
public class SAPContractDO extends CGFactDO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6903317959235319246L;
	
	private Long Id;
	private String contractNo;
	private String customerNo;
	private String salesOfcCode;
	private String salesPersonCode;
	private String industryTypeCode;
	private String customerGroup;
	private String disChannel;
	private String groupKey;
	private String customerName;
	private String address1;
	private String address2;
	private String address3;
	private String city;
	private String pincode;
	private String state;
	private String priTitle;
	private String priPersonName;
	private String priEmail;
	private String priContactNo;
	private String priExt;
	private String priFax;
	private String priMobile;
	private String secTitle;
	private String secPersonName;
	private String secEmail;
	private String secContactNo;
	private String secExt;
	private String secMobile;
	private String secFax;
	private String billingCycle;
	private String paymentTermsCode;
	private String plantCode;
	private String tanNo;
	private String panNo;
	private String status;
	private String salesDistrict;
	private String legacyCustCode;
	private String exception;
	
	
	
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
	/**
	 * @return the id
	 */
	public Long getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		Id = id;
	}
	/**
	 * @return the contractNo
	 */
	public String getContractNo() {
		return contractNo;
	}
	/**
	 * @param contractNo the contractNo to set
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	/**
	 * @return the customerNo
	 */
	public String getCustomerNo() {
		return customerNo;
	}
	/**
	 * @param customerNo the customerNo to set
	 */
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	/**
	 * @return the salesOfcCode
	 */
	public String getSalesOfcCode() {
		return salesOfcCode;
	}
	/**
	 * @param salesOfcCode the salesOfcCode to set
	 */
	public void setSalesOfcCode(String salesOfcCode) {
		this.salesOfcCode = salesOfcCode;
	}
	/**
	 * @return the salesPersonCode
	 */
	public String getSalesPersonCode() {
		return salesPersonCode;
	}
	/**
	 * @param salesPersonCode the salesPersonCode to set
	 */
	public void setSalesPersonCode(String salesPersonCode) {
		this.salesPersonCode = salesPersonCode;
	}
	/**
	 * @return the industryTypeCode
	 */
	public String getIndustryTypeCode() {
		return industryTypeCode;
	}
	/**
	 * @param industryTypeCode the industryTypeCode to set
	 */
	public void setIndustryTypeCode(String industryTypeCode) {
		this.industryTypeCode = industryTypeCode;
	}
	/**
	 * @return the customerGroup
	 */
	public String getCustomerGroup() {
		return customerGroup;
	}
	/**
	 * @param customerGroup the customerGroup to set
	 */
	public void setCustomerGroup(String customerGroup) {
		this.customerGroup = customerGroup;
	}
	/**
	 * @return the disChannel
	 */
	public String getDisChannel() {
		return disChannel;
	}
	/**
	 * @param disChannel the disChannel to set
	 */
	public void setDisChannel(String disChannel) {
		this.disChannel = disChannel;
	}
	/**
	 * @return the groupKey
	 */
	public String getGroupKey() {
		return groupKey;
	}
	/**
	 * @param groupKey the groupKey to set
	 */
	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}
	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}
	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * @return the address3
	 */
	public String getAddress3() {
		return address3;
	}
	/**
	 * @param address3 the address3 to set
	 */
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
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
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the priTitle
	 */
	public String getPriTitle() {
		return priTitle;
	}
	/**
	 * @param priTitle the priTitle to set
	 */
	public void setPriTitle(String priTitle) {
		this.priTitle = priTitle;
	}
	/**
	 * @return the priPersonName
	 */
	public String getPriPersonName() {
		return priPersonName;
	}
	/**
	 * @param priPersonName the priPersonName to set
	 */
	public void setPriPersonName(String priPersonName) {
		this.priPersonName = priPersonName;
	}
	/**
	 * @return the priEmail
	 */
	public String getPriEmail() {
		return priEmail;
	}
	/**
	 * @param priEmail the priEmail to set
	 */
	public void setPriEmail(String priEmail) {
		this.priEmail = priEmail;
	}
	/**
	 * @return the priContactNo
	 */
	public String getPriContactNo() {
		return priContactNo;
	}
	/**
	 * @param priContactNo the priContactNo to set
	 */
	public void setPriContactNo(String priContactNo) {
		this.priContactNo = priContactNo;
	}
	/**
	 * @return the priExt
	 */
	public String getPriExt() {
		return priExt;
	}
	/**
	 * @param priExt the priExt to set
	 */
	public void setPriExt(String priExt) {
		this.priExt = priExt;
	}
	/**
	 * @return the priFax
	 */
	public String getPriFax() {
		return priFax;
	}
	/**
	 * @param priFax the priFax to set
	 */
	public void setPriFax(String priFax) {
		this.priFax = priFax;
	}
	/**
	 * @return the priMobile
	 */
	public String getPriMobile() {
		return priMobile;
	}
	/**
	 * @param priMobile the priMobile to set
	 */
	public void setPriMobile(String priMobile) {
		this.priMobile = priMobile;
	}
	/**
	 * @return the secTitle
	 */
	public String getSecTitle() {
		return secTitle;
	}
	/**
	 * @param secTitle the secTitle to set
	 */
	public void setSecTitle(String secTitle) {
		this.secTitle = secTitle;
	}
	/**
	 * @return the secPersonName
	 */
	public String getSecPersonName() {
		return secPersonName;
	}
	/**
	 * @param secPersonName the secPersonName to set
	 */
	public void setSecPersonName(String secPersonName) {
		this.secPersonName = secPersonName;
	}
	/**
	 * @return the secEmail
	 */
	public String getSecEmail() {
		return secEmail;
	}
	/**
	 * @param secEmail the secEmail to set
	 */
	public void setSecEmail(String secEmail) {
		this.secEmail = secEmail;
	}
	/**
	 * @return the secContactNo
	 */
	public String getSecContactNo() {
		return secContactNo;
	}
	/**
	 * @param secContactNo the secContactNo to set
	 */
	public void setSecContactNo(String secContactNo) {
		this.secContactNo = secContactNo;
	}
	/**
	 * @return the secExt
	 */
	public String getSecExt() {
		return secExt;
	}
	/**
	 * @param secExt the secExt to set
	 */
	public void setSecExt(String secExt) {
		this.secExt = secExt;
	}
	/**
	 * @return the secMobile
	 */
	public String getSecMobile() {
		return secMobile;
	}
	/**
	 * @param secMobile the secMobile to set
	 */
	public void setSecMobile(String secMobile) {
		this.secMobile = secMobile;
	}
	/**
	 * @return the secFax
	 */
	public String getSecFax() {
		return secFax;
	}
	/**
	 * @param secFax the secFax to set
	 */
	public void setSecFax(String secFax) {
		this.secFax = secFax;
	}
	/**
	 * @return the billingCycle
	 */
	public String getBillingCycle() {
		return billingCycle;
	}
	/**
	 * @param billingCycle the billingCycle to set
	 */
	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}
	/**
	 * @return the paymentTermsCode
	 */
	public String getPaymentTermsCode() {
		return paymentTermsCode;
	}
	/**
	 * @param paymentTermsCode the paymentTermsCode to set
	 */
	public void setPaymentTermsCode(String paymentTermsCode) {
		this.paymentTermsCode = paymentTermsCode;
	}
	/**
	 * @return the plantCode
	 */
	public String getPlantCode() {
		return plantCode;
	}
	/**
	 * @param plantCode the plantCode to set
	 */
	public void setPlantCode(String plantCode) {
		this.plantCode = plantCode;
	}
	/**
	 * @return the tanNo
	 */
	public String getTanNo() {
		return tanNo;
	}
	/**
	 * @param tanNo the tanNo to set
	 */
	public void setTanNo(String tanNo) {
		this.tanNo = tanNo;
	}
	/**
	 * @return the panNo
	 */
	public String getPanNo() {
		return panNo;
	}
	/**
	 * @param panNo the panNo to set
	 */
	public void setPanNo(String panNo) {
		this.panNo = panNo;
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
	 * @return the salesDistrict
	 */
	public String getSalesDistrict() {
		return salesDistrict;
	}
	/**
	 * @param salesDistrict the salesDistrict to set
	 */
	public void setSalesDistrict(String salesDistrict) {
		this.salesDistrict = salesDistrict;
	}
	/**
	 * @return the legacyCustCode
	 */
	public String getLegacyCustCode() {
		return legacyCustCode;
	}
	/**
	 * @param legacyCustCode the legacyCustCode to set
	 */
	public void setLegacyCustCode(String legacyCustCode) {
		this.legacyCustCode = legacyCustCode;
	}

	
}
