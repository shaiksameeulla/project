package com.ff.complaints;

import java.util.List;

import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

/**
 * @author hkansagr
 */
public class ComplaintDtlsTO {

	// Drop down
	private String serviceType;
	// Text Field
	private String bookingNo;
	private String serviceRequestNo;
	private String linkedServiceReqNo;
	// Check box
	private String isLinkedWith;
	// Text field
	private String callerName;
	private String callerPhone;
	private String callerEmail;
	// Drop down
	private String serviceRelated;
	private String complaintCategory;
	private String customerType;
	private String originCityId;
	private String productId;
	private String pincodeId;
	// Text field
	private String weightKgs;
	private String weightGrm;
	// Drop down
	private String consignmentType;
	private String industryType;
	private String employeeId;
	private String empEmailId;
	// Text field
	private String empPhone;
	private String serviceResult;
	// Drop down
	private String sourceOfQuery;
	private String status;
	// Text field
	private String remark;
	// Check box
	private String smsToConsignor;
	private String smsToConsignee;
	private String emailToCaller;

	// List
	private List<StockStandardTypeTO> serviceTypeList;
	private List<StockStandardTypeTO> industryTypeList;
	private List<StockStandardTypeTO> srcOfQryList;

	
	/**
	 * @return the srcOfQryList
	 */
	public List<StockStandardTypeTO> getSrcOfQryList() {
		return srcOfQryList;
	}

	/**
	 * @param srcOfQryList
	 *            the srcOfQryList to set
	 */
	public void setSrcOfQryList(List<StockStandardTypeTO> srcOfQryList) {
		this.srcOfQryList = srcOfQryList;
	}

	/**
	 * @return the industryTypeList
	 */
	public List<StockStandardTypeTO> getIndustryTypeList() {
		return industryTypeList;
	}

	/**
	 * @param industryTypeList
	 *            the industryTypeList to set
	 */
	public void setIndustryTypeList(List<StockStandardTypeTO> industryTypeList) {
		this.industryTypeList = industryTypeList;
	}

	/**
	 * @return the serviceTypeList
	 */
	public List<StockStandardTypeTO> getServiceTypeList() {
		return serviceTypeList;
	}

	/**
	 * @param serviceTypeList
	 *            the serviceTypeList to set
	 */
	public void setServiceTypeList(List<StockStandardTypeTO> serviceTypeList) {
		this.serviceTypeList = serviceTypeList;
	}

	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * @param serviceType
	 *            the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * @return the bookingNo
	 */
	public String getBookingNo() {
		return bookingNo;
	}

	/**
	 * @param bookingNo
	 *            the bookingNo to set
	 */
	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}

	/**
	 * @return the serviceRequestNo
	 */
	public String getServiceRequestNo() {
		return serviceRequestNo;
	}

	/**
	 * @param serviceRequestNo
	 *            the serviceRequestNo to set
	 */
	public void setServiceRequestNo(String serviceRequestNo) {
		this.serviceRequestNo = serviceRequestNo;
	}

	/**
	 * @return the linkedServiceReqNo
	 */
	public String getLinkedServiceReqNo() {
		return linkedServiceReqNo;
	}

	/**
	 * @param linkedServiceReqNo
	 *            the linkedServiceReqNo to set
	 */
	public void setLinkedServiceReqNo(String linkedServiceReqNo) {
		this.linkedServiceReqNo = linkedServiceReqNo;
	}

	/**
	 * @return the isLinkedWith
	 */
	public String getIsLinkedWith() {
		return isLinkedWith;
	}

	/**
	 * @param isLinkedWith
	 *            the isLinkedWith to set
	 */
	public void setIsLinkedWith(String isLinkedWith) {
		this.isLinkedWith = isLinkedWith;
	}

	/**
	 * @return the callerName
	 */
	public String getCallerName() {
		return callerName;
	}

	/**
	 * @param callerName
	 *            the callerName to set
	 */
	public void setCallerName(String callerName) {
		this.callerName = callerName;
	}

	/**
	 * @return the callerPhone
	 */
	public String getCallerPhone() {
		return callerPhone;
	}

	/**
	 * @param callerPhone
	 *            the callerPhone to set
	 */
	public void setCallerPhone(String callerPhone) {
		this.callerPhone = callerPhone;
	}

	/**
	 * @return the callerEmail
	 */
	public String getCallerEmail() {
		return callerEmail;
	}

	/**
	 * @param callerEmail
	 *            the callerEmail to set
	 */
	public void setCallerEmail(String callerEmail) {
		this.callerEmail = callerEmail;
	}

	/**
	 * @return the serviceRelated
	 */
	public String getServiceRelated() {
		return serviceRelated;
	}

	/**
	 * @param serviceRelated
	 *            the serviceRelated to set
	 */
	public void setServiceRelated(String serviceRelated) {
		this.serviceRelated = serviceRelated;
	}

	/**
	 * @return the complaintCategory
	 */
	public String getComplaintCategory() {
		return complaintCategory;
	}

	/**
	 * @param complaintCategory
	 *            the complaintCategory to set
	 */
	public void setComplaintCategory(String complaintCategory) {
		this.complaintCategory = complaintCategory;
	}

	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * @param customerType
	 *            the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * @return the originCityId
	 */
	public String getOriginCityId() {
		return originCityId;
	}

	/**
	 * @param originCityId
	 *            the originCityId to set
	 */
	public void setOriginCityId(String originCityId) {
		this.originCityId = originCityId;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the pincodeId
	 */
	public String getPincodeId() {
		return pincodeId;
	}

	/**
	 * @param pincodeId
	 *            the pincodeId to set
	 */
	public void setPincodeId(String pincodeId) {
		this.pincodeId = pincodeId;
	}

	/**
	 * @return the weightKgs
	 */
	public String getWeightKgs() {
		return weightKgs;
	}

	/**
	 * @param weightKgs
	 *            the weightKgs to set
	 */
	public void setWeightKgs(String weightKgs) {
		this.weightKgs = weightKgs;
	}

	/**
	 * @return the weightGrm
	 */
	public String getWeightGrm() {
		return weightGrm;
	}

	/**
	 * @param weightGrm
	 *            the weightGrm to set
	 */
	public void setWeightGrm(String weightGrm) {
		this.weightGrm = weightGrm;
	}

	/**
	 * @return the consignmentType
	 */
	public String getConsignmentType() {
		return consignmentType;
	}

	/**
	 * @param consignmentType
	 *            the consignmentType to set
	 */
	public void setConsignmentType(String consignmentType) {
		this.consignmentType = consignmentType;
	}

	/**
	 * @return the industryType
	 */
	public String getIndustryType() {
		return industryType;
	}

	/**
	 * @param industryType
	 *            the industryType to set
	 */
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	/**
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId
	 *            the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the empEmailId
	 */
	public String getEmpEmailId() {
		return empEmailId;
	}

	/**
	 * @param empEmailId
	 *            the empEmailId to set
	 */
	public void setEmpEmailId(String empEmailId) {
		this.empEmailId = empEmailId;
	}

	/**
	 * @return the empPhone
	 */
	public String getEmpPhone() {
		return empPhone;
	}

	/**
	 * @param empPhone
	 *            the empPhone to set
	 */
	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	/**
	 * @return the serviceResult
	 */
	public String getServiceResult() {
		return serviceResult;
	}

	/**
	 * @param serviceResult
	 *            the serviceResult to set
	 */
	public void setServiceResult(String serviceResult) {
		this.serviceResult = serviceResult;
	}

	/**
	 * @return the sourceOfQuery
	 */
	public String getSourceOfQuery() {
		return sourceOfQuery;
	}

	/**
	 * @param sourceOfQuery
	 *            the sourceOfQuery to set
	 */
	public void setSourceOfQuery(String sourceOfQuery) {
		this.sourceOfQuery = sourceOfQuery;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the smsToConsignor
	 */
	public String getSmsToConsignor() {
		return smsToConsignor;
	}

	/**
	 * @param smsToConsignor
	 *            the smsToConsignor to set
	 */
	public void setSmsToConsignor(String smsToConsignor) {
		this.smsToConsignor = smsToConsignor;
	}

	/**
	 * @return the smsToConsignee
	 */
	public String getSmsToConsignee() {
		return smsToConsignee;
	}

	/**
	 * @param smsToConsignee
	 *            the smsToConsignee to set
	 */
	public void setSmsToConsignee(String smsToConsignee) {
		this.smsToConsignee = smsToConsignee;
	}

	/**
	 * @return the emailToCaller
	 */
	public String getEmailToCaller() {
		return emailToCaller;
	}

	/**
	 * @param emailToCaller
	 *            the emailToCaller to set
	 */
	public void setEmailToCaller(String emailToCaller) {
		this.emailToCaller = emailToCaller;
	}

}
