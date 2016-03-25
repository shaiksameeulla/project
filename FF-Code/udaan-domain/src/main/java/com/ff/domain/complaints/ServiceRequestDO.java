package com.ff.domain.complaints;

import java.math.BigDecimal;
import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.umc.UserDO;

public class ServiceRequestDO extends CGFactDO {

	private static final long serialVersionUID = -3856606657216528391L;
	
	private Integer serviceRequestId;
	private String serviceRequestNo;
	private String linkedServiceReqNo;
	private String callerName ;
	private String callerPhone ;
	private String callerEmail ;
	private ServiceRequestCustTypeDO serviceRequestCustTypeDO;
	private ServiceRequestQueryTypeDO serviceRequestQueryTypeDO;
	private ServiceRequestComplaintTypeDO serviceRequestComplaintTypeDO;
	
	private String remark;
	private String serviceResult;
	private String sourceOfQuery;
	private ServiceRequestStatusDO serviceRequestStatusDO;
	private UserDO createdByUserDO;
	private Date createdDate;
	private UserDO updateByUserDO;
	private Date updateDate;
	private String bookingNo;
	private String bookingNoType;
	private CityDO originCity;
	private ProductDO productType;
	private PincodeDO destPincode;
    private BigDecimal weight;
    @Deprecated
    private String doxType;
    private String consignmentType;
    private Integer complaintPaperType;
    private EmployeeDO assignedTo;
    private String smsToConsignor ;
	private String smsToConsignee ;
	private String emailToCaller ;
	private String serviceRequestType;
	private ServiceRequestComplaintDO serviceRequestComplaintDO;
	private String referenceNo;
	private Integer transactionOfficeId;
	private ServiceRequestTransfertoDO serviceRequestTransferToDO;
	
	private Boolean isNewServiceRequest;
	private String industryType;
	private Integer originBranchId;
	
	private boolean isForTransferFunctionality=false;//non persistance property
	private String fromEmailId;//non persistance property
	
	
	
	/**
	 * @return the serviceRequestTransferToDO
	 */
	public ServiceRequestTransfertoDO getServiceRequestTransferToDO() {
		return serviceRequestTransferToDO;
	}
	/**
	 * @param serviceRequestTransferToDO the serviceRequestTransferToDO to set
	 */
	public void setServiceRequestTransferToDO(
			ServiceRequestTransfertoDO serviceRequestTransferToDO) {
		this.serviceRequestTransferToDO = serviceRequestTransferToDO;
	}
	/**
	 * @return the serviceRequestType
	 */
	public String getServiceRequestType() {
		return serviceRequestType;
	}
	/**
	 * @param serviceRequestType the serviceRequestType to set
	 */
	public void setServiceRequestType(String serviceRequestType) {
		this.serviceRequestType = serviceRequestType;
	}
	/**
	 * @return the linkedServiceReqNo
	 */
	public String getLinkedServiceReqNo() {
		return linkedServiceReqNo;
	}
	/**
	 * @param linkedServiceReqNo the linkedServiceReqNo to set
	 */
	public void setLinkedServiceReqNo(String linkedServiceReqNo) {
		this.linkedServiceReqNo = linkedServiceReqNo;
	}
	public Integer getServiceRequestId() {
		return serviceRequestId;
	}
	public void setServiceRequestId(Integer serviceRequestId) {
		this.serviceRequestId = serviceRequestId;
	}
	public String getServiceRequestNo() {
		return serviceRequestNo;
	}
	public void setServiceRequestNo(String serviceRequestNo) {
		this.serviceRequestNo = StringUtil.isStringEmpty(serviceRequestNo)?null :serviceRequestNo.trim().toUpperCase();
	}
	/**
	 * @return the serviceResult
	 */
	public String getServiceResult() {
		return serviceResult;
	}
	/**
	 * @param serviceResult the serviceResult to set
	 */
	public void setServiceResult(String serviceResult) {
		this.serviceResult = serviceResult;
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
	public ServiceRequestCustTypeDO getServiceRequestCustTypeDO() {
		return serviceRequestCustTypeDO;
	}
	public void setServiceRequestCustTypeDO(
			ServiceRequestCustTypeDO serviceRequestCustTypeDO) {
		this.serviceRequestCustTypeDO = serviceRequestCustTypeDO;
	}
	public ServiceRequestQueryTypeDO getServiceRequestQueryTypeDO() {
		return serviceRequestQueryTypeDO;
	}
	public void setServiceRequestQueryTypeDO(
			ServiceRequestQueryTypeDO serviceRequestQueryTypeDO) {
		this.serviceRequestQueryTypeDO = serviceRequestQueryTypeDO;
	}
	public ServiceRequestComplaintTypeDO getServiceRequestComplaintTypeDO() {
		return serviceRequestComplaintTypeDO;
	}
	public void setServiceRequestComplaintTypeDO(
			ServiceRequestComplaintTypeDO serviceRequestComplaintTypeDO) {
		this.serviceRequestComplaintTypeDO = serviceRequestComplaintTypeDO;
	}
	/**
	 * @return the consignmentType
	 */
	public String getConsignmentType() {
		return consignmentType;
	}
	/**
	 * @param consignmentType the consignmentType to set
	 */
	public void setConsignmentType(String consignmentType) {
		this.consignmentType = consignmentType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public ServiceRequestStatusDO getServiceRequestStatusDO() {
		return serviceRequestStatusDO;
	}
	
	/**
	 * @return the transactionOfficeId
	 */
	public Integer getTransactionOfficeId() {
		return transactionOfficeId;
	}
	/**
	 * @param transactionOfficeId the transactionOfficeId to set
	 */
	public void setTransactionOfficeId(Integer transactionOfficeId) {
		this.transactionOfficeId = transactionOfficeId;
	}
	public void setServiceRequestStatusDO(
			ServiceRequestStatusDO serviceRequestStatusDO) {
		this.serviceRequestStatusDO = serviceRequestStatusDO;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * @return the referenceNo
	 */
	public String getReferenceNo() {
		return referenceNo;
	}
	/**
	 * @param referenceNo the referenceNo to set
	 */
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getBookingNo() {
		return bookingNo;
	}
	public void setBookingNo(String bookingNo) {
		this.bookingNo = StringUtil.isStringEmpty(bookingNo)?null :bookingNo.trim().toUpperCase();
	}
	public String getBookingNoType() {
		return bookingNoType;
	}
	public void setBookingNoType(String bookingNoType) {
		this.bookingNoType = bookingNoType;
	}
	public CityDO getOriginCity() {
		return originCity;
	}
	public void setOriginCity(CityDO originCity) {
		this.originCity = originCity;
	}
	public ProductDO getProductType() {
		return productType;
	}
	public void setProductType(ProductDO productType) {
		this.productType = productType;
	}
	public PincodeDO getDestPincode() {
		return destPincode;
	}
	public void setDestPincode(PincodeDO destPincode) {
		this.destPincode = destPincode;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public String getDoxType() {
		return doxType;
	}
	public void setDoxType(String doxType) {
		this.doxType = doxType;
	}
	public Integer getComplaintPaperType() {
		return complaintPaperType;
	}
	public void setComplaintPaperType(Integer complaintPaperType) {
		this.complaintPaperType = complaintPaperType;
	}
	public EmployeeDO getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(EmployeeDO assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getSmsToConsignor() {
		return smsToConsignor;
	}
	public void setSmsToConsignor(String smsToConsignor) {
		this.smsToConsignor = smsToConsignor;
	}
	/**
	 * @return the isNewServiceRequest
	 */
	public Boolean getIsNewServiceRequest() {
		return isNewServiceRequest;
	}
	/**
	 * @param isNewServiceRequest the isNewServiceRequest to set
	 */
	public void setIsNewServiceRequest(Boolean isNewServiceRequest) {
		this.isNewServiceRequest = isNewServiceRequest;
	}
	public String getSmsToConsignee() {
		return smsToConsignee;
	}
	public void setSmsToConsignee(String smsToConsignee) {
		this.smsToConsignee = smsToConsignee;
	}
	public String getEmailToCaller() {
		return emailToCaller;
	}
	public void setEmailToCaller(String emailToCaller) {
		this.emailToCaller = emailToCaller;
	}
	public UserDO getCreatedByUserDO() {
		return createdByUserDO;
	}
	public void setCreatedByUserDO(UserDO createdByUserDO) {
		this.createdByUserDO = createdByUserDO;
	}
	public UserDO getUpdateByUserDO() {
		return updateByUserDO;
	}
	public void setUpdateByUserDO(UserDO updateByUserDO) {
		this.updateByUserDO = updateByUserDO;
	}
	/**
	 * @return the serviceRequestComplaintDO
	 */
	public ServiceRequestComplaintDO getServiceRequestComplaintDO() {
		return serviceRequestComplaintDO;
	}
	/**
	 * @param serviceRequestComplaintDO the serviceRequestComplaintDO to set
	 */
	public void setServiceRequestComplaintDO(
			ServiceRequestComplaintDO serviceRequestComplaintDO) {
		this.serviceRequestComplaintDO = serviceRequestComplaintDO;
	}
	/**
	 * @return the sourceOfQuery
	 */
	public String getSourceOfQuery() {
		return sourceOfQuery;
	}
	/**
	 * @param sourceOfQuery the sourceOfQuery to set
	 */
	public void setSourceOfQuery(String sourceOfQuery) {
		this.sourceOfQuery = sourceOfQuery;
	}
	/**
	 * @return the industryType
	 */
	public String getIndustryType() {
		return industryType;
	}
	/**
	 * @param industryType the industryType to set
	 */
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	/**
	 * @return the originBranchId
	 */
	public Integer getOriginBranchId() {
		return originBranchId;
	}
	/**
	 * @param originBranchId the originBranchId to set
	 */
	public void setOriginBranchId(Integer originBranchId) {
		this.originBranchId = originBranchId;
	}
	/**
	 * @return the isForTransferFunctionality
	 */
	public boolean isForTransferFunctionality() {
		return isForTransferFunctionality;
	}
	/**
	 * @param isForTransferFunctionality the isForTransferFunctionality to set
	 */
	public void setForTransferFunctionality(boolean isForTransferFunctionality) {
		this.isForTransferFunctionality = isForTransferFunctionality;
	}
	/**
	 * @return the fromEmailId
	 */
	public String getFromEmailId() {
		return fromEmailId;
	}
	/**
	 * @param fromEmailId the fromEmailId to set
	 */
	public void setFromEmailId(String fromEmailId) {
		this.fromEmailId = fromEmailId;
	}
	
	
	
	/*private String serviceRequestType;
	private ConsignmentDO consignmentDO;
	private String customerType ;
	private String serviceRelated ;
	private String source ;
	private String status;
	private String result;
	private EmployeeDO employeeDO;
	private OfficeDO complaintOriginOfficeDO;
	private String custCategory ;
	private UserDO userDO;
	private Integer createdByEmpDO ;
    private ServiceRelatedDetailsDO serviceReqDtlsDO;*/
    
	
	
	
	
}
