package com.ff.complaints;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.business.CustomerTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.EmployeeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.umc.UserInfoTO;

/**
 * @author sdalli
 *
 */
public class ComplaintsTO extends CGBaseTO {

	private static final long serialVersionUID = 6537915192185600324L;
	private Integer complaintId;
	private String referenceNo;
	private String complaintNo;
	private String complaintType;
	private String complaintStatus;
	private String complaintDate;
	private String complaintTime;
	private CustomerTO customerTO;
	private Integer employeeId;
	private String callerName;
	private String callerPhone;
	private String callerEmail;
	private String callerMobile;
	private CityTO cityTO;
	private ProductTO productTO;
	private PincodeTO pincodeTO;
	private String serviceRelated;
	private String consgTypes;
	private String queryTpye;
	private String searchType;
	private String remark;
	private Integer loginOfficeId;//hidden
	private String loginOfficeCode;//hidden
	private String date;//hidden
	private String dateOfUpdate;//hidden
	private Integer userId;//hidden
	private Integer regionId; //hidden
	private Integer officeTypeId; //hidden
	private UserInfoTO userInfoTO; 
	private EmployeeTO createdBy; 
	private EmployeeTO updatedBy;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	private ConsignmentTO consignmentTO;
	
	public Integer getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId;
	}
	public String getComplaintNo() {
		return complaintNo;
	}
	public void setComplaintNo(String complaintNo) {
		this.complaintNo = complaintNo;
	}
	public String getComplaintType() {
		return complaintType;
	}
	public void setComplaintType(String complaintType) {
		this.complaintType = complaintType;
	}
	public String getComplaintStatus() {
		return complaintStatus;
	}
	public void setComplaintStatus(String complaintStatus) {
		this.complaintStatus = complaintStatus;
	}
	public String getComplaintDate() {
		return complaintDate;
	}
	public void setComplaintDate(String complaintDate) {
		this.complaintDate = complaintDate;
	}
	public String getComplaintTime() {
		return complaintTime;
	}
	public void setComplaintTime(String complaintTime) {
		this.complaintTime = complaintTime;
	}
	public String getCallerName() {
		return callerName;
	}
	public void setCallerName(String callerName) {
		this.callerName = callerName;
	}
	public String getCallerPhone() {
		return callerPhone;
	}
	public void setCallerPhone(String callerPhone) {
		this.callerPhone = callerPhone;
	}
	public String getCallerEmail() {
		return callerEmail;
	}
	public void setCallerEmail(String callerEmail) {
		this.callerEmail = callerEmail;
	}
	public ConsignmentTO getConsignmentTO() {
		return consignmentTO;
	}
	public void setConsignmentTO(ConsignmentTO consignmentTO) {
		this.consignmentTO = consignmentTO;
	}
	public String getCallerMobile() {
		return callerMobile;
	}
	public void setCallerMobile(String callerMobile) {
		this.callerMobile = callerMobile;
	}
	public CityTO getCityTO() {
		return cityTO;
	}
	public void setCityTO(CityTO cityTO) {
		this.cityTO = cityTO;
	}
	public ProductTO getProductTO() {
		return productTO;
	}
	public void setProductTO(ProductTO productTO) {
		this.productTO = productTO;
	}
	public PincodeTO getPincodeTO() {
		return pincodeTO;
	}
	public void setPincodeTO(PincodeTO pincodeTO) {
		this.pincodeTO = pincodeTO;
	}
	public CustomerTO getCustomerTO() {
		return customerTO;
	}
	public void setCustomerTO(CustomerTO customerTO) {
		this.customerTO = customerTO;
	}
	public String getServiceRelated() {
		return serviceRelated;
	}
	public void setServiceRelated(String serviceRelated) {
		this.serviceRelated = serviceRelated;
	}
	public String getQueryTpye() {
		return queryTpye;
	}
	public void setQueryTpye(String queryTpye) {
		this.queryTpye = queryTpye;
	}
	public String getConsgTypes() {
		return consgTypes;
	}
	public void setConsgTypes(String consgTypes) {
		this.consgTypes = consgTypes;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDateOfUpdate() {
		return dateOfUpdate;
	}
	public void setDateOfUpdate(String dateOfUpdate) {
		this.dateOfUpdate = dateOfUpdate;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getRegionId() {
		return regionId;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	public Integer getOfficeTypeId() {
		return officeTypeId;
	}
	public void setOfficeTypeId(Integer officeTypeId) {
		this.officeTypeId = officeTypeId;
	}
	public UserInfoTO getUserInfoTO() {
		return userInfoTO;
	}
	public void setUserInfoTO(UserInfoTO userInfoTO) {
		this.userInfoTO = userInfoTO;
	}
	public EmployeeTO getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(EmployeeTO createdBy) {
		this.createdBy = createdBy;
	}
	public EmployeeTO getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(EmployeeTO updatedBy) {
		this.updatedBy = updatedBy;
	}	
	
}
