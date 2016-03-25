/**
 * 
 */
package com.ff.to.billing;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;

/**
 * @author abarudwa
 *
 */
public class ReBillingTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer regionTO;//regionId
	private Integer cityTO;//stationId
	private Integer officeTO;//branchId
	private Integer customerTO;//customerId
	private String startDateStr;
	private String endDateStr;
	private Integer loginOfficeId;//hidden
	private String loginOfficeCode;//hidden
	private Integer createdBy;
	private String rebillingNo;
    private String errorMessage;
	private String successMessage;
	
	public Integer getRegionTO() {
		return regionTO;
	}
	public void setRegionTO(Integer regionTO) {
		this.regionTO = regionTO;
	}
	public Integer getCityTO() {
		return cityTO;
	}
	public void setCityTO(Integer cityTO) {
		this.cityTO = cityTO;
	}
	public Integer getOfficeTO() {
		return officeTO;
	}
	public void setOfficeTO(Integer officeTO) {
		this.officeTO = officeTO;
	}
	public Integer getCustomerTO() {
		return customerTO;
	}
	public void setCustomerTO(Integer customerTO) {
		this.customerTO = customerTO;
	}
	public String getStartDateStr() {
		return startDateStr;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public String getEndDateStr() {
		return endDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	public Integer getLoginOfficeId() {
		return loginOfficeId;
	}
	public void setLoginOfficeId(Integer loginOfficeId) {
		this.loginOfficeId = loginOfficeId;
	}
	public String getLoginOfficeCode() {
		return loginOfficeCode;
	}
	public void setLoginOfficeCode(String loginOfficeCode) {
		this.loginOfficeCode = loginOfficeCode;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public String getRebillingNo() {
		return rebillingNo;
	}
	public void setRebillingNo(String rebillingNo) {
		this.rebillingNo = rebillingNo;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getSuccessMessage() {
		return successMessage;
	}
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	
	
}
