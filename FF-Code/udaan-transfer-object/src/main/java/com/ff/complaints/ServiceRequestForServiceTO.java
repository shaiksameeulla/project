package com.ff.complaints;

import java.util.List;

import com.ff.organization.EmployeeTO;

public class ServiceRequestForServiceTO extends ServiceRequestTO {

	private static final long serialVersionUID = 6537915192185600324L;
	
	
	private String custCategoryType;
	
	private String originDtls;
	
	private List<EmployeeTO> employeeTOs;
	private Integer paperwork;
	
	
	private Integer productId;
	private Integer consgTypeId;
	private Integer originCityId;
	private Integer pincodeId;
	
	
	/*** complaint status constant*/
	private String complaintStatusResolved;
	private String complaintStatusBackline;
	private String complaintStatusFollowup;
	
	/*** constants complaint Query type/service related for CN Service */
	private String serviceRequestConsgQueryTypeComplaint;
	private String serviceRequestConsgQueryTypeCriticalComplaint;
	private String serviceRequestConsgQueryTypeEscalationComplaint;
	private String serviceRequestConsgQueryTypeFinancialComplaint;
	
	/*** constants complaint Query type/service related for Service */
	private String serviceRequestServiceQueryTypeTariffEnquiry;
	private String serviceRequestServiceQueryTypeServiceCheck;
	private String serviceRequestServiceQueryTypeGeneralInfo;
	private String serviceRequestServiceQueryTypeLeadCall;
	private String serviceRequestServiceQueryTypePickupCall;
	private String serviceRequestServiceQueryTypePaperwork;
	private String serviceRequestServiceQueryTypeEmotionalBond;
	
	
	
	
	
	
	

	public List<EmployeeTO> getEmployeeTOs() {
		return employeeTOs;
	}

	public void setEmployeeTOs(List<EmployeeTO> employeeTOs) {
		this.employeeTOs = employeeTOs;
	}

	/**
	 * @return the originDtls
	 */
	public String getOriginDtls() {
		return originDtls;
	}

	/**
	 * @param originDtls the originDtls to set
	 */
	public void setOriginDtls(String originDtls) {
		this.originDtls = originDtls;
	}

	/**
	 * @return the custCategoryType
	 */
	public String getCustCategoryType() {
		return custCategoryType;
	}

	/**
	 * @param custCategoryType the custCategoryType to set
	 */
	public void setCustCategoryType(String custCategoryType) {
		this.custCategoryType = custCategoryType;
	}

	/**
	 * @return the productId
	 */
	public Integer getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getConsgTypeId() {
		return consgTypeId;
	}

	public void setConsgTypeId(Integer consgTypeId) {
		this.consgTypeId = consgTypeId;
	}

	

	public Integer getOriginCityId() {
		return originCityId;
	}

	public void setOriginCityId(Integer originCityId) {
		this.originCityId = originCityId;
	}

	/**
	 * @return the pincodeId
	 */
	public Integer getPincodeId() {
		return pincodeId;
	}

	/**
	 * @param pincodeId the pincodeId to set
	 */
	public void setPincodeId(Integer pincodeId) {
		this.pincodeId = pincodeId;
	}

	/**
	 * @return the paperwork
	 */
	public Integer getPaperwork() {
		return paperwork;
	}

	/**
	 * @param paperwork the paperwork to set
	 */
	public void setPaperwork(Integer paperwork) {
		this.paperwork = paperwork;
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
	 * @param complaintStatusResolved the complaintStatusResolved to set
	 */
	public void setComplaintStatusResolved(String complaintStatusResolved) {
		this.complaintStatusResolved = complaintStatusResolved;
	}

	/**
	 * @param complaintStatusBackline the complaintStatusBackline to set
	 */
	public void setComplaintStatusBackline(String complaintStatusBackline) {
		this.complaintStatusBackline = complaintStatusBackline;
	}

	/**
	 * @param complaintStatusFollowup the complaintStatusFollowup to set
	 */
	public void setComplaintStatusFollowup(String complaintStatusFollowup) {
		this.complaintStatusFollowup = complaintStatusFollowup;
	}

	/**
	 * @return the serviceRequestConsgQueryTypeComplaint
	 */
	public String getServiceRequestConsgQueryTypeComplaint() {
		return serviceRequestConsgQueryTypeComplaint;
	}

	/**
	 * @return the serviceRequestConsgQueryTypeCriticalComplaint
	 */
	public String getServiceRequestConsgQueryTypeCriticalComplaint() {
		return serviceRequestConsgQueryTypeCriticalComplaint;
	}

	/**
	 * @return the serviceRequestConsgQueryTypeEscalationComplaint
	 */
	public String getServiceRequestConsgQueryTypeEscalationComplaint() {
		return serviceRequestConsgQueryTypeEscalationComplaint;
	}

	/**
	 * @return the serviceRequestConsgQueryTypeFinancialComplaint
	 */
	public String getServiceRequestConsgQueryTypeFinancialComplaint() {
		return serviceRequestConsgQueryTypeFinancialComplaint;
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
	 * @param serviceRequestConsgQueryTypeComplaint the serviceRequestConsgQueryTypeComplaint to set
	 */
	public void setServiceRequestConsgQueryTypeComplaint(
			String serviceRequestConsgQueryTypeComplaint) {
		this.serviceRequestConsgQueryTypeComplaint = serviceRequestConsgQueryTypeComplaint;
	}

	/**
	 * @param serviceRequestConsgQueryTypeCriticalComplaint the serviceRequestConsgQueryTypeCriticalComplaint to set
	 */
	public void setServiceRequestConsgQueryTypeCriticalComplaint(
			String serviceRequestConsgQueryTypeCriticalComplaint) {
		this.serviceRequestConsgQueryTypeCriticalComplaint = serviceRequestConsgQueryTypeCriticalComplaint;
	}

	/**
	 * @param serviceRequestConsgQueryTypeEscalationComplaint the serviceRequestConsgQueryTypeEscalationComplaint to set
	 */
	public void setServiceRequestConsgQueryTypeEscalationComplaint(
			String serviceRequestConsgQueryTypeEscalationComplaint) {
		this.serviceRequestConsgQueryTypeEscalationComplaint = serviceRequestConsgQueryTypeEscalationComplaint;
	}

	/**
	 * @param serviceRequestConsgQueryTypeFinancialComplaint the serviceRequestConsgQueryTypeFinancialComplaint to set
	 */
	public void setServiceRequestConsgQueryTypeFinancialComplaint(
			String serviceRequestConsgQueryTypeFinancialComplaint) {
		this.serviceRequestConsgQueryTypeFinancialComplaint = serviceRequestConsgQueryTypeFinancialComplaint;
	}

	/**
	 * @param serviceRequestServiceQueryTypeTariffEnquiry the serviceRequestServiceQueryTypeTariffEnquiry to set
	 */
	public void setServiceRequestServiceQueryTypeTariffEnquiry(
			String serviceRequestServiceQueryTypeTariffEnquiry) {
		this.serviceRequestServiceQueryTypeTariffEnquiry = serviceRequestServiceQueryTypeTariffEnquiry;
	}

	/**
	 * @param serviceRequestServiceQueryTypeServiceCheck the serviceRequestServiceQueryTypeServiceCheck to set
	 */
	public void setServiceRequestServiceQueryTypeServiceCheck(
			String serviceRequestServiceQueryTypeServiceCheck) {
		this.serviceRequestServiceQueryTypeServiceCheck = serviceRequestServiceQueryTypeServiceCheck;
	}

	/**
	 * @param serviceRequestServiceQueryTypeGeneralInfo the serviceRequestServiceQueryTypeGeneralInfo to set
	 */
	public void setServiceRequestServiceQueryTypeGeneralInfo(
			String serviceRequestServiceQueryTypeGeneralInfo) {
		this.serviceRequestServiceQueryTypeGeneralInfo = serviceRequestServiceQueryTypeGeneralInfo;
	}

	/**
	 * @param serviceRequestServiceQueryTypeLeadCall the serviceRequestServiceQueryTypeLeadCall to set
	 */
	public void setServiceRequestServiceQueryTypeLeadCall(
			String serviceRequestServiceQueryTypeLeadCall) {
		this.serviceRequestServiceQueryTypeLeadCall = serviceRequestServiceQueryTypeLeadCall;
	}

	/**
	 * @param serviceRequestServiceQueryTypePickupCall the serviceRequestServiceQueryTypePickupCall to set
	 */
	public void setServiceRequestServiceQueryTypePickupCall(
			String serviceRequestServiceQueryTypePickupCall) {
		this.serviceRequestServiceQueryTypePickupCall = serviceRequestServiceQueryTypePickupCall;
	}

	/**
	 * @param serviceRequestServiceQueryTypePaperwork the serviceRequestServiceQueryTypePaperwork to set
	 */
	public void setServiceRequestServiceQueryTypePaperwork(
			String serviceRequestServiceQueryTypePaperwork) {
		this.serviceRequestServiceQueryTypePaperwork = serviceRequestServiceQueryTypePaperwork;
	}

	/**
	 * @param serviceRequestServiceQueryTypeEmotionalBond the serviceRequestServiceQueryTypeEmotionalBond to set
	 */
	public void setServiceRequestServiceQueryTypeEmotionalBond(
			String serviceRequestServiceQueryTypeEmotionalBond) {
		this.serviceRequestServiceQueryTypeEmotionalBond = serviceRequestServiceQueryTypeEmotionalBond;
	}
	

}
