package com.ff.complaints;

import java.util.Map;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;

public class ServiceRequestTO extends CGBaseTO {

	private static final long serialVersionUID = -2571343979205354368L;

	private Integer serviceRequestId;
	private String serviceRequestNo;
	private String callerName;
	private String callerPhone;
	private String callerEmail;
	private ServiceRequestCustTypeTO serviceRequestCustTypeTO;
	private ServiceRequestQueryTypeTO serviceRequestQueryTypeTO;
	private ServiceRequestComplaintTypeTO serviceRequestComplaintTypeTO;
	private Integer serviceRequestDetailsId;
	private String remark;
	private ServiceRequestStatusTO serviceRequestStatusTO;
	private UserTO createdByUserTO;
	private String createdDateStr;
	private UserTO updateByUserTO;
	private String updateDateStr;
	private String bookingNo;
	private String bookingNoType;
	private CityTO originCityTO;
	private ProductTO productTypeTO;
	private PincodeTO destPincodeTO;
	private String weightKgs;
	private String weightGrm;
	private String doxType;
	private Integer complaintPaperType;
	private EmployeeTO assignedToEmpTO;
	private String smsToConsignor;
	private String smsToConsignee;
	private String emailToCaller;
	private String serviceRequestType;
	private String deliveryStatus;
	private String referenceNo;

	private Integer serviceRelatedId;
	private String linkedServiceReqNo;
	private ConsignmentTO consignmentTO;
	private String customerType;
	private ConsignmentTypeTO consgTypeTO;
	private CNPaperWorksTO paperworkTO;
	private String serviceRelated;
	private String sourceOfQuery;
	private OfficeTO originTO;
	private ProductTO productTO;
	private PincodeTO pincodeTO;
	private String status;
	private String serviceResult;
	private OfficeTO complaintOriginOfficeTO;
	private String custCategory;
	private UserTO userTO;
	private CityTO cityTO;
	private String empEmailId;

	private String backlineExecutiveRole;
	private String salesCoordinatorRole;
	private String csmRole;
	private String ccsmRole;

	private String isLinkedWith;

	private String empPhone;
	private String employeeDtls;
	private String number;
	private String searchType;
	private String serviceType;

	private ServiceRequestTransfertoTO serviceRequestTranferTO;
	private String transMsg;
	private String isLinkEnabled = "N";

	// Common Hidden Attributes
	private String complaintCategory;
	private String serviceRequestTypeForService;
	private String serviceRequestTypeForConsg;
	private String serviceRequestTypeForBref;
	private Integer loginOfficeId;// hidden
	private String loginOfficeCode;// hidden
	private String date;// hidden
	private String dateOfUpdate;// hidden
	private Integer userId;// hidden
	private Integer regionId; // hidden
	private Integer officeTypeId; // hidden
	private UserInfoTO userInfoTO; // Common
	private Integer logginUserId; // Common
	private Integer employeeId; // hidden

	private String consignmentType;
	public Map<Integer, String> emplyoeeMap;
	public Map<Integer, String> pincodeMap;

	/*** complaint status constant */
	private String complaintStatusResolved;
	private String complaintStatusBackline;
	private String complaintStatusFollowup;

	private String complaintSourceOfQueryPhone;

	/*** constants complaint Query type/service related for CN Service */
	private String serviceRequestConsgQueryTypeComplaint;
	private String serviceRequestConsgQueryTypePodStatus;
	// private String serviceRequestConsgQueryTypeCriticalComplaint;
	// private String serviceRequestConsgQueryTypeEscalationComplaint;
	// private String serviceRequestConsgQueryTypeFinancialComplaint;

	/*** constants complaint Query type/service related for Service */
	private String serviceRequestServiceQueryTypeTariffEnquiry;
	private String serviceRequestServiceQueryTypeServiceCheck;
	private String serviceRequestServiceQueryTypeGeneralInfo;
	private String serviceRequestServiceQueryTypeLeadCall;
	private String serviceRequestServiceQueryTypePickupCall;
	private String serviceRequestServiceQueryTypePaperwork;
	private String serviceRequestServiceQueryTypeEmotionalBond;

	private Integer productId;
	private Integer consgTypeId;
	private Integer originCityId;
	private Integer pincodeId;

	private String consignmentTypeDox;
	private String transactionRoleType;

	private String industryType;

	private boolean isOriginBranchRequireToLoad = false;
	private ServiceTransferTO transferTO;
	private String fromEmailId;

	private String consgDeliveryDate;

	private String frontlineExecName;

	/** The complaint details tab in follow up screen drop down label name. */
	private String originLabel;
	private String productLabel;

	
	
	/**
	 * @return the originLabel
	 */
	public String getOriginLabel() {
		return originLabel;
	}

	/**
	 * @param originLabel
	 *            the originLabel to set
	 */
	public void setOriginLabel(String originLabel) {
		this.originLabel = originLabel;
	}

	/**
	 * @return the productLabel
	 */
	public String getProductLabel() {
		return productLabel;
	}

	/**
	 * @param productLabel
	 *            the productLabel to set
	 */
	public void setProductLabel(String productLabel) {
		this.productLabel = productLabel;
	}

	/**
	 * @return the frontlineExecName
	 */
	public String getFrontlineExecName() {
		return frontlineExecName;
	}

	/**
	 * @param frontlineExecName
	 *            the frontlineExecName to set
	 */
	public void setFrontlineExecName(String frontlineExecName) {
		this.frontlineExecName = frontlineExecName;
	}

	/**
	 * @return the consgDeliveryDate
	 */
	public String getConsgDeliveryDate() {
		return consgDeliveryDate;
	}

	/**
	 * @param consgDeliveryDate
	 *            the consgDeliveryDate to set
	 */
	public void setConsgDeliveryDate(String consgDeliveryDate) {
		this.consgDeliveryDate = consgDeliveryDate;
	}

	/**
	 * @return the serviceRequestTranferTO
	 */
	public ServiceRequestTransfertoTO getServiceRequestTranferTO() {
		if (serviceRequestTranferTO == null)
			serviceRequestTranferTO = new ServiceRequestTransfertoTO();
		return serviceRequestTranferTO;
	}

	/**
	 * @param serviceRequestTranferTO
	 *            the serviceRequestTranferTO to set
	 */
	public void setServiceRequestTranferTO(
			ServiceRequestTransfertoTO serviceRequestTranferTO) {
		this.serviceRequestTranferTO = serviceRequestTranferTO;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
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

	/**
	 * @return the serviceRequestTypeForBref
	 */
	public String getServiceRequestTypeForBref() {
		return serviceRequestTypeForBref;
	}

	/**
	 * @param serviceRequestTypeForBref
	 *            the serviceRequestTypeForBref to set
	 */
	public void setServiceRequestTypeForBref(String serviceRequestTypeForBref) {
		this.serviceRequestTypeForBref = serviceRequestTypeForBref;
	}

	public UserInfoTO getUserInfoTO() {
		return userInfoTO;
	}

	public void setUserInfoTO(UserInfoTO userInfoTO) {
		this.userInfoTO = userInfoTO;
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
		this.serviceRequestNo = serviceRequestNo;
	}

	public String getCallerName() {
		return callerName;
	}

	/**
	 * @return the transactionRoleType
	 */
	public String getTransactionRoleType() {
		return transactionRoleType;
	}

	/**
	 * @param transactionRoleType
	 *            the transactionRoleType to set
	 */
	public void setTransactionRoleType(String transactionRoleType) {
		this.transactionRoleType = transactionRoleType;
	}

	/**
	 * @return the consignmentTypeDox
	 */
	public String getConsignmentTypeDox() {
		return consignmentTypeDox;
	}

	/**
	 * @param consignmentTypeDox
	 *            the consignmentTypeDox to set
	 */
	public void setConsignmentTypeDox(String consignmentTypeDox) {
		this.consignmentTypeDox = consignmentTypeDox;
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

	/**
	 * @return the emplyoeeMap
	 */
	public Map<Integer, String> getEmplyoeeMap() {
		return emplyoeeMap;
	}

	/**
	 * @return the pincodeMap
	 */
	public Map<Integer, String> getPincodeMap() {
		return pincodeMap;
	}

	/**
	 * @param pincodeMap
	 *            the pincodeMap to set
	 */
	public void setPincodeMap(Map<Integer, String> pincodeMap) {
		this.pincodeMap = pincodeMap;
	}

	/**
	 * @param emplyoeeMap
	 *            the emplyoeeMap to set
	 */
	public void setEmplyoeeMap(Map<Integer, String> emplyoeeMap) {
		this.emplyoeeMap = emplyoeeMap;
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

	public String getCallerEmail() {
		return callerEmail;
	}

	public void setCallerEmail(String callerEmail) {
		this.callerEmail = callerEmail;
	}

	public ServiceRequestCustTypeTO getServiceRequestCustTypeTO() {
		if (serviceRequestCustTypeTO == null)
			serviceRequestCustTypeTO = new ServiceRequestCustTypeTO();
		return serviceRequestCustTypeTO;
	}

	public void setServiceRequestCustTypeTO(
			ServiceRequestCustTypeTO serviceRequestCustTypeTO) {
		this.serviceRequestCustTypeTO = serviceRequestCustTypeTO;
	}

	public ServiceRequestQueryTypeTO getServiceRequestQueryTypeTO() {
		if (serviceRequestQueryTypeTO == null)
			serviceRequestQueryTypeTO = new ServiceRequestQueryTypeTO();
		return serviceRequestQueryTypeTO;
	}

	public void setServiceRequestQueryTypeTO(
			ServiceRequestQueryTypeTO serviceRequestQueryTypeTO) {
		this.serviceRequestQueryTypeTO = serviceRequestQueryTypeTO;
	}

	public ServiceRequestComplaintTypeTO getServiceRequestComplaintTypeTO() {
		if (serviceRequestComplaintTypeTO == null)
			serviceRequestComplaintTypeTO = new ServiceRequestComplaintTypeTO();
		return serviceRequestComplaintTypeTO;
	}

	public void setServiceRequestComplaintTypeTO(
			ServiceRequestComplaintTypeTO serviceRequestComplaintTypeTO) {
		this.serviceRequestComplaintTypeTO = serviceRequestComplaintTypeTO;
	}

	/**
	 * @return the serviceRequestConsgQueryTypePodStatus
	 */
	public String getServiceRequestConsgQueryTypePodStatus() {
		return serviceRequestConsgQueryTypePodStatus;
	}

	/**
	 * @param serviceRequestConsgQueryTypePodStatus
	 *            the serviceRequestConsgQueryTypePodStatus to set
	 */
	public void setServiceRequestConsgQueryTypePodStatus(
			String serviceRequestConsgQueryTypePodStatus) {
		this.serviceRequestConsgQueryTypePodStatus = serviceRequestConsgQueryTypePodStatus;
	}

	public Integer getServiceRequestDetailsId() {
		return serviceRequestDetailsId;
	}

	public void setServiceRequestDetailsId(Integer serviceRequestDetailsId) {
		this.serviceRequestDetailsId = serviceRequestDetailsId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ServiceRequestStatusTO getServiceRequestStatusTO() {
		if (serviceRequestStatusTO == null)
			serviceRequestStatusTO = new ServiceRequestStatusTO();
		return serviceRequestStatusTO;
	}

	public void setServiceRequestStatusTO(
			ServiceRequestStatusTO serviceRequestStatusTO) {
		this.serviceRequestStatusTO = serviceRequestStatusTO;
	}

	public UserTO getCreatedByUserTO() {
		if (createdByUserTO == null)
			createdByUserTO = new UserTO();
		return createdByUserTO;
	}

	public void setCreatedByUserTO(UserTO createdByUserTO) {
		this.createdByUserTO = createdByUserTO;
	}

	public String getCreatedDateStr() {
		return createdDateStr;
	}

	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}

	public UserTO getUpdateByUserTO() {
		if (updateByUserTO == null)
			updateByUserTO = new UserTO();
		return updateByUserTO;
	}

	public void setUpdateByUserTO(UserTO updateByUserTO) {
		this.updateByUserTO = updateByUserTO;
	}

	public String getUpdateDateStr() {
		return updateDateStr;
	}

	public void setUpdateDateStr(String updateDateStr) {
		this.updateDateStr = updateDateStr;
	}

	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}

	public String getBookingNoType() {
		return bookingNoType;
	}

	public void setBookingNoType(String bookingNoType) {
		this.bookingNoType = bookingNoType;
	}

	public CityTO getOriginCityTO() {
		if (originCityTO == null)
			originCityTO = new CityTO();
		return originCityTO;
	}

	public void setOriginCityTO(CityTO originCityTO) {
		this.originCityTO = originCityTO;
	}

	public ProductTO getProductTypeTO() {
		if (productTypeTO == null)
			productTypeTO = new ProductTO();
		return productTypeTO;
	}

	public void setProductTypeTO(ProductTO productTypeTO) {
		this.productTypeTO = productTypeTO;
	}

	public PincodeTO getDestPincodeTO() {
		if (destPincodeTO == null)
			destPincodeTO = new PincodeTO();
		return destPincodeTO;
	}

	public void setDestPincodeTO(PincodeTO destPincodeTO) {
		this.destPincodeTO = destPincodeTO;
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

	public EmployeeTO getAssignedToEmpTO() {
		if (assignedToEmpTO == null)
			assignedToEmpTO = new EmployeeTO();
		return assignedToEmpTO;
	}

	public void setAssignedToEmpTO(EmployeeTO assignedToEmpTO) {
		this.assignedToEmpTO = assignedToEmpTO;
	}

	public String getSmsToConsignor() {
		return smsToConsignor;
	}

	public void setSmsToConsignor(String smsToConsignor) {
		this.smsToConsignor = smsToConsignor;
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

	public String getServiceRequestType() {
		return serviceRequestType;
	}

	public void setServiceRequestType(String serviceRequestType) {
		this.serviceRequestType = serviceRequestType;
	}

	public Integer getLogginUserId() {
		return logginUserId;
	}

	public void setLogginUserId(Integer logginUserId) {
		this.logginUserId = logginUserId;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public Integer getServiceRelatedId() {
		return serviceRelatedId;
	}

	public void setServiceRelatedId(Integer serviceRelatedId) {
		this.serviceRelatedId = serviceRelatedId;
	}

	public ConsignmentTO getConsignmentTO() {
		return consignmentTO;
	}

	public void setConsignmentTO(ConsignmentTO consignmentTO) {
		this.consignmentTO = consignmentTO;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public ConsignmentTypeTO getConsgTypeTO() {
		return consgTypeTO;
	}

	public void setConsgTypeTO(ConsignmentTypeTO consgTypeTO) {
		this.consgTypeTO = consgTypeTO;
	}

	public CNPaperWorksTO getPaperworkTO() {
		return paperworkTO;
	}

	public void setPaperworkTO(CNPaperWorksTO paperworkTO) {
		this.paperworkTO = paperworkTO;
	}

	public String getServiceRelated() {
		return serviceRelated;
	}

	public void setServiceRelated(String serviceRelated) {
		this.serviceRelated = serviceRelated;
	}

	public OfficeTO getOriginTO() {
		return originTO;
	}

	public void setOriginTO(OfficeTO originTO) {
		this.originTO = originTO;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public OfficeTO getComplaintOriginOfficeTO() {
		return complaintOriginOfficeTO;
	}

	public void setComplaintOriginOfficeTO(OfficeTO complaintOriginOfficeTO) {
		this.complaintOriginOfficeTO = complaintOriginOfficeTO;
	}

	public String getCustCategory() {
		return custCategory;
	}

	public void setCustCategory(String custCategory) {
		this.custCategory = custCategory;
	}

	public UserTO getUserTO() {
		return userTO;
	}

	public void setUserTO(UserTO userTO) {
		this.userTO = userTO;
	}

	public CityTO getCityTO() {
		return cityTO;
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

	public void setCityTO(CityTO cityTO) {
		this.cityTO = cityTO;
	}

	public String getEmpEmailId() {
		return empEmailId;
	}

	public void setEmpEmailId(String empEmailId) {
		this.empEmailId = empEmailId;
	}

	public String getBacklineExecutiveRole() {
		return backlineExecutiveRole;
	}

	public void setBacklineExecutiveRole(String backlineExecutiveRole) {
		this.backlineExecutiveRole = backlineExecutiveRole;
	}

	public String getSalesCoordinatorRole() {
		return salesCoordinatorRole;
	}

	public void setSalesCoordinatorRole(String salesCoordinatorRole) {
		this.salesCoordinatorRole = salesCoordinatorRole;
	}

	public String getEmpPhone() {
		return empPhone;
	}

	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	public String getEmployeeDtls() {
		return employeeDtls;
	}

	public void setEmployeeDtls(String employeeDtls) {
		this.employeeDtls = employeeDtls;
	}

	public String getNumber() {
		return number;
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

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getComplaintCategory() {
		return complaintCategory;
	}

	public void setComplaintCategory(String complaintCategory) {
		this.complaintCategory = complaintCategory;
	}

	public String getServiceRequestTypeForService() {
		return serviceRequestTypeForService;
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

	public void setServiceRequestTypeForService(
			String serviceRequestTypeForService) {
		this.serviceRequestTypeForService = serviceRequestTypeForService;
	}

	public String getServiceRequestTypeForConsg() {
		return serviceRequestTypeForConsg;
	}

	public void setServiceRequestTypeForConsg(String serviceRequestTypeForConsg) {
		this.serviceRequestTypeForConsg = serviceRequestTypeForConsg;
	}

	/**
	 * @return the complaintStatusResolved
	 */
	public String getComplaintStatusResolved() {
		return complaintStatusResolved;
	}

	/**
	 * @return the complaintStatusBackline
	 */
	public String getComplaintStatusBackline() {
		return complaintStatusBackline;
	}

	/**
	 * @return the complaintStatusFollowup
	 */
	public String getComplaintStatusFollowup() {
		return complaintStatusFollowup;
	}

	/**
	 * @return the serviceRequestConsgQueryTypeComplaint
	 */
	public String getServiceRequestConsgQueryTypeComplaint() {
		return serviceRequestConsgQueryTypeComplaint;
	}

	/**
	 * @return the serviceRequestServiceQueryTypeTariffEnquiry
	 */
	public String getServiceRequestServiceQueryTypeTariffEnquiry() {
		return serviceRequestServiceQueryTypeTariffEnquiry;
	}

	/**
	 * @return the serviceRequestServiceQueryTypeServiceCheck
	 */
	public String getServiceRequestServiceQueryTypeServiceCheck() {
		return serviceRequestServiceQueryTypeServiceCheck;
	}

	/**
	 * @return the serviceRequestServiceQueryTypeGeneralInfo
	 */
	public String getServiceRequestServiceQueryTypeGeneralInfo() {
		return serviceRequestServiceQueryTypeGeneralInfo;
	}

	/**
	 * @return the serviceRequestServiceQueryTypeLeadCall
	 */
	public String getServiceRequestServiceQueryTypeLeadCall() {
		return serviceRequestServiceQueryTypeLeadCall;
	}

	/**
	 * @return the serviceRequestServiceQueryTypePickupCall
	 */
	public String getServiceRequestServiceQueryTypePickupCall() {
		return serviceRequestServiceQueryTypePickupCall;
	}

	/**
	 * @return the serviceRequestServiceQueryTypePaperwork
	 */
	public String getServiceRequestServiceQueryTypePaperwork() {
		return serviceRequestServiceQueryTypePaperwork;
	}

	/**
	 * @return the serviceRequestServiceQueryTypeEmotionalBond
	 */
	public String getServiceRequestServiceQueryTypeEmotionalBond() {
		return serviceRequestServiceQueryTypeEmotionalBond;
	}

	/**
	 * @return the productId
	 */
	public Integer getProductId() {
		return productId;
	}

	/**
	 * @return the consgTypeId
	 */
	public Integer getConsgTypeId() {
		return consgTypeId;
	}

	/**
	 * @return the originCityId
	 */
	public Integer getOriginCityId() {
		return originCityId;
	}

	/**
	 * @return the pincodeId
	 */
	public Integer getPincodeId() {
		return pincodeId;
	}

	/**
	 * @param complaintStatusResolved
	 *            the complaintStatusResolved to set
	 */
	public void setComplaintStatusResolved(String complaintStatusResolved) {
		this.complaintStatusResolved = complaintStatusResolved;
	}

	/**
	 * @param complaintStatusBackline
	 *            the complaintStatusBackline to set
	 */
	public void setComplaintStatusBackline(String complaintStatusBackline) {
		this.complaintStatusBackline = complaintStatusBackline;
	}

	/**
	 * @param complaintStatusFollowup
	 *            the complaintStatusFollowup to set
	 */
	public void setComplaintStatusFollowup(String complaintStatusFollowup) {
		this.complaintStatusFollowup = complaintStatusFollowup;
	}

	/**
	 * @param serviceRequestConsgQueryTypeComplaint
	 *            the serviceRequestConsgQueryTypeComplaint to set
	 */
	public void setServiceRequestConsgQueryTypeComplaint(
			String serviceRequestConsgQueryTypeComplaint) {
		this.serviceRequestConsgQueryTypeComplaint = serviceRequestConsgQueryTypeComplaint;
	}

	/**
	 * @param serviceRequestServiceQueryTypeTariffEnquiry
	 *            the serviceRequestServiceQueryTypeTariffEnquiry to set
	 */
	public void setServiceRequestServiceQueryTypeTariffEnquiry(
			String serviceRequestServiceQueryTypeTariffEnquiry) {
		this.serviceRequestServiceQueryTypeTariffEnquiry = serviceRequestServiceQueryTypeTariffEnquiry;
	}

	/**
	 * @param serviceRequestServiceQueryTypeServiceCheck
	 *            the serviceRequestServiceQueryTypeServiceCheck to set
	 */
	public void setServiceRequestServiceQueryTypeServiceCheck(
			String serviceRequestServiceQueryTypeServiceCheck) {
		this.serviceRequestServiceQueryTypeServiceCheck = serviceRequestServiceQueryTypeServiceCheck;
	}

	/**
	 * @param serviceRequestServiceQueryTypeGeneralInfo
	 *            the serviceRequestServiceQueryTypeGeneralInfo to set
	 */
	public void setServiceRequestServiceQueryTypeGeneralInfo(
			String serviceRequestServiceQueryTypeGeneralInfo) {
		this.serviceRequestServiceQueryTypeGeneralInfo = serviceRequestServiceQueryTypeGeneralInfo;
	}

	/**
	 * @param serviceRequestServiceQueryTypeLeadCall
	 *            the serviceRequestServiceQueryTypeLeadCall to set
	 */
	public void setServiceRequestServiceQueryTypeLeadCall(
			String serviceRequestServiceQueryTypeLeadCall) {
		this.serviceRequestServiceQueryTypeLeadCall = serviceRequestServiceQueryTypeLeadCall;
	}

	/**
	 * @param serviceRequestServiceQueryTypePickupCall
	 *            the serviceRequestServiceQueryTypePickupCall to set
	 */
	public void setServiceRequestServiceQueryTypePickupCall(
			String serviceRequestServiceQueryTypePickupCall) {
		this.serviceRequestServiceQueryTypePickupCall = serviceRequestServiceQueryTypePickupCall;
	}

	/**
	 * @param serviceRequestServiceQueryTypePaperwork
	 *            the serviceRequestServiceQueryTypePaperwork to set
	 */
	public void setServiceRequestServiceQueryTypePaperwork(
			String serviceRequestServiceQueryTypePaperwork) {
		this.serviceRequestServiceQueryTypePaperwork = serviceRequestServiceQueryTypePaperwork;
	}

	/**
	 * @param serviceRequestServiceQueryTypeEmotionalBond
	 *            the serviceRequestServiceQueryTypeEmotionalBond to set
	 */
	public void setServiceRequestServiceQueryTypeEmotionalBond(
			String serviceRequestServiceQueryTypeEmotionalBond) {
		this.serviceRequestServiceQueryTypeEmotionalBond = serviceRequestServiceQueryTypeEmotionalBond;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	/**
	 * @param consgTypeId
	 *            the consgTypeId to set
	 */
	public void setConsgTypeId(Integer consgTypeId) {
		this.consgTypeId = consgTypeId;
	}

	/**
	 * @param originCityId
	 *            the originCityId to set
	 */
	public void setOriginCityId(Integer originCityId) {
		this.originCityId = originCityId;
	}

	/**
	 * @param pincodeId
	 *            the pincodeId to set
	 */
	public void setPincodeId(Integer pincodeId) {
		this.pincodeId = pincodeId;
	}

	/**
	 * @return the transMsg
	 */
	public String getTransMsg() {
		return transMsg;
	}

	/**
	 * @param transMsg
	 *            the transMsg to set
	 */
	public void setTransMsg(String transMsg) {
		this.transMsg = transMsg;
	}

	/**
	 * @return the complaintSourceOfQueryPhone
	 */
	public String getComplaintSourceOfQueryPhone() {
		return complaintSourceOfQueryPhone;
	}

	/**
	 * @param complaintSourceOfQueryPhone
	 *            the complaintSourceOfQueryPhone to set
	 */
	public void setComplaintSourceOfQueryPhone(
			String complaintSourceOfQueryPhone) {
		this.complaintSourceOfQueryPhone = complaintSourceOfQueryPhone;
	}

	/**
	 * @return the isLinkEnabled
	 */
	public String getIsLinkEnabled() {
		return isLinkEnabled;
	}

	/**
	 * @param isLinkEnabled
	 *            the isLinkEnabled to set
	 */
	public void setIsLinkEnabled(String isLinkEnabled) {
		this.isLinkEnabled = isLinkEnabled;
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
	 * @return the isOriginBranchRequireToLoad
	 */
	public boolean isOriginBranchRequireToLoad() {
		return isOriginBranchRequireToLoad;
	}

	/**
	 * @param isOriginBranchRequireToLoad
	 *            the isOriginBranchRequireToLoad to set
	 */
	public void setOriginBranchRequireToLoad(boolean isOriginBranchRequireToLoad) {
		this.isOriginBranchRequireToLoad = isOriginBranchRequireToLoad;
	}

	/**
	 * @return the csmRole
	 */
	public String getCsmRole() {
		return csmRole;
	}

	/**
	 * @return the ccsmRole
	 */
	public String getCcsmRole() {
		return ccsmRole;
	}

	/**
	 * @param csmRole
	 *            the csmRole to set
	 */
	public void setCsmRole(String csmRole) {
		this.csmRole = csmRole;
	}

	/**
	 * @param ccsmRole
	 *            the ccsmRole to set
	 */
	public void setCcsmRole(String ccsmRole) {
		this.ccsmRole = ccsmRole;
	}

	/**
	 * @return the transferTO
	 */
	public ServiceTransferTO getTransferTO() {
		return transferTO;
	}

	/**
	 * @param transferTO
	 *            the transferTO to set
	 */
	public void setTransferTO(ServiceTransferTO transferTO) {
		this.transferTO = transferTO;
	}

	/**
	 * @return the weightKgs
	 */
	public String getWeightKgs() {
		return weightKgs;
	}

	/**
	 * @return the weightGrm
	 */
	public String getWeightGrm() {
		return weightGrm;
	}

	/**
	 * @param weightKgs
	 *            the weightKgs to set
	 */
	public void setWeightKgs(String weightKgs) {
		this.weightKgs = weightKgs;
	}

	/**
	 * @param weightGrm
	 *            the weightGrm to set
	 */
	public void setWeightGrm(String weightGrm) {
		this.weightGrm = weightGrm;
	}

	/**
	 * @return the fromEmailId
	 */
	public String getFromEmailId() {
		return fromEmailId;
	}

	/**
	 * @param fromEmailId
	 *            the fromEmailId to set
	 */
	public void setFromEmailId(String fromEmailId) {
		this.fromEmailId = fromEmailId;
	}

}
