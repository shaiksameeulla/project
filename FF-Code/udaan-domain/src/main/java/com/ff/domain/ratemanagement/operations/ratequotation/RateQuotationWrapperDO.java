package com.ff.domain.ratemanagement.operations.ratequotation;


import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;




public class RateQuotationWrapperDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4201356147696366813L;
	

	private Date quotCreatedDate;
	private Integer rateQuotationId;
	private String RateQuotationNo;
	private String regionalName;
	private String cityName;
	private String customerName;
	private String salesOfficeName;
	private String salesPersonName;
	private String status;
	private String rateQuotationType;
	private String groupKey;
	private String rateContractId;
	private String contractNo;
	private String validToDate;
	private String validFromDate;
	private String emailIdTO;
	private String emailIdCC;
	private String customerCode;
	private String businessName;
	private String firstName;
	private String lastName;
	private Integer userEmpId;
	private Integer salesUserEmpId;
	private Integer quotationCreatedBy;
	private String approvalRequired;
	private Integer contractCreatedBy;
	private String rateContractType;
	private String rhoOfcCode;
	private String salesOfcCode;
	private String salesOfcName;
	
	public RateQuotationWrapperDO() {
		super();
	}
	
	public RateQuotationWrapperDO(Date quotCreatedDate, Integer rateQuotationId,
			String rateQuotationNo, String rateQuotationType, String regionalName, String cityName,
			String customerName, String salesOfficeName,
			String salesPersonName, String status) {
		
		this.quotCreatedDate = quotCreatedDate;
		this.rateQuotationId = rateQuotationId;
		this.RateQuotationNo = rateQuotationNo;
		this.rateQuotationType = rateQuotationType; 
		this.regionalName = regionalName;
		this.cityName = cityName;
		this.customerName = customerName;
		this.salesOfficeName = salesOfficeName;
		this.salesPersonName = salesPersonName;
		this.status = status;
		
	}
	
	
	public RateQuotationWrapperDO(Date quotCreatedDate, Integer rateQuotationId,
			String rateQuotationNo, String rateQuotationType, String regionalName, String rhoOfcCode, 
			String salesOfcCode, String salesOfcName, String cityName,
			String customerName, String salesOfficeName,
			String salesPersonName, Integer userEmpId, Integer salesUserEmpId,
			Integer quotationCreatedBy, String approvalRequired, String status) {
		
		this.quotCreatedDate = quotCreatedDate;
		this.rateQuotationId = rateQuotationId;
		this.RateQuotationNo = rateQuotationNo;
		this.rateQuotationType = rateQuotationType; 
		this.regionalName = regionalName;
		this.rhoOfcCode = rhoOfcCode;
		this.salesOfcCode = salesOfcCode;
		this.salesOfcName = salesOfcName;
		this.cityName = cityName;
		this.customerName = customerName;
		this.salesOfficeName = salesOfficeName;
		this.salesPersonName = salesPersonName;
		this.userEmpId = userEmpId;
		this.salesUserEmpId = salesUserEmpId;
		this.quotationCreatedBy = quotationCreatedBy;
		this.approvalRequired = approvalRequired;
		this.status = status;
		
	}
	
	public RateQuotationWrapperDO(Date quotCreatedDate, Integer rateQuotationId,
			String rateQuotationNo, String rateQuotationType, String regionalName, String cityName,
			String customerName, String salesOfficeName,
			String salesPersonName, Integer userEmpId, Integer salesUserEmpId,
			Integer quotationCreatedBy, String approvalRequired, String status) {
		
		this.quotCreatedDate = quotCreatedDate;
		this.rateQuotationId = rateQuotationId;
		this.RateQuotationNo = rateQuotationNo;
		this.rateQuotationType = rateQuotationType; 
		this.regionalName = regionalName;
		this.cityName = cityName;
		this.customerName = customerName;
		this.salesOfficeName = salesOfficeName;
		this.salesPersonName = salesPersonName;
		this.userEmpId = userEmpId;
		this.salesUserEmpId = salesUserEmpId;
		this.quotationCreatedBy = quotationCreatedBy;
		this.approvalRequired = approvalRequired;
		this.status = status;
		
	}
	
	public RateQuotationWrapperDO(Date quotCreatedDate, String contractNo,  String status, String customerName,String regionalName,
			String cityName,String salesOfficeName,String salesPersonName,String groupKey) {
		this.quotCreatedDate = quotCreatedDate;
		this.regionalName = regionalName;
		this.cityName = cityName;
		this.customerName = customerName;
		this.status = status;
		this.contractNo = contractNo;
		this.salesOfficeName = salesOfficeName;
		this.salesPersonName = salesPersonName;
		this.groupKey = groupKey;
	}
	
	
	public RateQuotationWrapperDO(Date quotCreatedDate, Integer rateQuotationId,
			String contractNo, String rateContractType, String regionalName, String rhoOfcCode, 
			String salesOfcCode, String salesOfcName,  String cityName,
			String customerName, String salesOfficeName,
			String salesPersonName, String groupKey,Integer quotationCreatedBy, Integer userEmpId, Integer salesUserEmpId, Integer contractCreatedBy, String status) {
		super();
		this.quotCreatedDate = quotCreatedDate;
		this.rateQuotationId = rateQuotationId;
		this.contractNo = contractNo;
		this.rateContractType = rateContractType;
		this.regionalName = regionalName;
		this.rhoOfcCode = rhoOfcCode;
		this.salesOfcCode = salesOfcCode;
		this.salesOfcName = salesOfcName;
		this.cityName = cityName;
		this.customerName = customerName;
		this.salesOfficeName = salesOfficeName;
		this.salesPersonName = salesPersonName;
		this.groupKey = groupKey;
		this.quotationCreatedBy = quotationCreatedBy;
		this.userEmpId = userEmpId;
		this.salesUserEmpId = salesUserEmpId;
		this.contractCreatedBy = contractCreatedBy;
		this.status = status;
	}

	public RateQuotationWrapperDO(Date quotCreatedDate, Integer rateQuotationId,
			String contractNo, String rateContractType, String regionalName, String cityName,
			String customerName, String salesOfficeName,
			String salesPersonName, String groupKey,Integer quotationCreatedBy, Integer userEmpId, Integer salesUserEmpId, Integer contractCreatedBy, String status) {
		super();
		this.quotCreatedDate = quotCreatedDate;
		this.rateQuotationId = rateQuotationId;
		this.contractNo = contractNo;
		this.rateContractType = rateContractType;
		this.regionalName = regionalName;
		this.cityName = cityName;
		this.customerName = customerName;
		this.salesOfficeName = salesOfficeName;
		this.salesPersonName = salesPersonName;
		this.groupKey = groupKey;
		this.quotationCreatedBy = quotationCreatedBy;
		this.userEmpId = userEmpId;
		this.salesUserEmpId = salesUserEmpId;
		this.contractCreatedBy = contractCreatedBy;
		this.status = status;
	}
	/**
	 * @return the rateQuotationType
	 */
	public String getRateQuotationType() {
		return rateQuotationType;
	}
	/**
	 * @param rateQuotationType the rateQuotationType to set
	 */
	public void setRateQuotationType(String rateQuotationType) {
		this.rateQuotationType = rateQuotationType;
	}
	
	/**
	 * @return the quotCreatedDate
	 */
	public Date getQuotCreatedDate() {
		return quotCreatedDate;
	}

	/**
	 * @param quotCreatedDate the quotCreatedDate to set
	 */
	public void setQuotCreatedDate(Date quotCreatedDate) {
		this.quotCreatedDate = quotCreatedDate;
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
	 * @return the rateQuotationId
	 */
	public Integer getRateQuotationId() {
		return rateQuotationId;
	}
	/**
	 * @param rateQuotationId the rateQuotationId to set
	 */
	public void setRateQuotationId(Integer rateQuotationId) {
		this.rateQuotationId = rateQuotationId;
	}
	/**
	 * @return the rateQuotationNo
	 */
	public String getRateQuotationNo() {
		return RateQuotationNo;
	}
	/**
	 * @param rateQuotationNo the rateQuotationNo to set
	 */
	public void setRateQuotationNo(String rateQuotationNo) {
		RateQuotationNo = rateQuotationNo;
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
	 * @return the regionalName
	 */
	public String getRegionalName() {
		return regionalName;
	}
	/**
	 * @param regionalName the regionalName to set
	 */
	public void setRegionalName(String regionalName) {
		this.regionalName = regionalName;
	}
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @return the salesOfficeName
	 */
	public String getSalesOfficeName() {
		return salesOfficeName;
	}
	/**
	 * @param salesOfficeName the salesOfficeName to set
	 */
	public void setSalesOfficeName(String salesOfficeName) {
		this.salesOfficeName = salesOfficeName;
	}
	/**
	 * @return the salesPersonName
	 */
	public String getSalesPersonName() {
		return salesPersonName;
	}
	/**
	 * @param salesPersonName the salesPersonName to set
	 */
	public void setSalesPersonName(String salesPersonName) {
		this.salesPersonName = salesPersonName;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getGroupKey() {
		return groupKey;
	}
	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}

	/**
	 * @return the emailIdTO
	 */
	public String getEmailIdTO() {
		return emailIdTO;
	}

	/**
	 * @param emailIdTO the emailIdTO to set
	 */
	public void setEmailIdTO(String emailIdTO) {
		this.emailIdTO = emailIdTO;
	}

	/**
	 * @return the rateContractId
	 */
	public String getRateContractId() {
		return rateContractId;
	}

	/**
	 * @param rateContractId the rateContractId to set
	 */
	public void setRateContractId(String rateContractId) {
		this.rateContractId = rateContractId;
	}

	/**
	 * @return the validToDate
	 */
	public String getValidToDate() {
		return validToDate;
	}

	/**
	 * @param validToDate the validToDate to set
	 */
	public void setValidToDate(String validToDate) {
		this.validToDate = validToDate;
	}

	/**
	 * @return the validFromDate
	 */
	public String getValidFromDate() {
		return validFromDate;
	}

	/**
	 * @param validFromDate the validFromDate to set
	 */
	public void setValidFromDate(String validFromDate) {
		this.validFromDate = validFromDate;
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
	 * @return the businessName
	 */
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * @param businessName the businessName to set
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	
	/**
	 * @return the emailIdCC
	 */
	public String getEmailIdCC() {
		return emailIdCC;
	}

	/**
	 * @param emailIdCC the emailIdCC to set
	 */
	public void setEmailIdCC(String emailIdCC) {
		this.emailIdCC = emailIdCC;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public RateQuotationWrapperDO(String rateContractId, String contractNo,
			String validToDate, String validFromDate, String emailIdTO,
			String emailIdCC, String customerCode, String businessName,
			String firstName, String lastName) {
		super();
		this.rateContractId = rateContractId;
		this.contractNo = contractNo;
		this.validToDate = validToDate;
		this.validFromDate = validFromDate;
		this.emailIdTO = emailIdTO;
		this.emailIdCC = emailIdCC;
		this.customerCode = customerCode;
		this.businessName = businessName;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * @return the userEmpId
	 */
	public Integer getUserEmpId() {
		return userEmpId;
	}

	/**
	 * @param userEmpId the userEmpId to set
	 */
	public void setUserEmpId(Integer userEmpId) {
		this.userEmpId = userEmpId;
	}

	/**
	 * @return the salesUserEmpId
	 */
	public Integer getSalesUserEmpId() {
		return salesUserEmpId;
	}

	/**
	 * @param salesUserEmpId the salesUserEmpId to set
	 */
	public void setSalesUserEmpId(Integer salesUserEmpId) {
		this.salesUserEmpId = salesUserEmpId;
	}

	/**
	 * @return the quotationCreatedBy
	 */
	public Integer getQuotationCreatedBy() {
		return quotationCreatedBy;
	}

	/**
	 * @param quotationCreatedBy the quotationCreatedBy to set
	 */
	public void setQuotationCreatedBy(Integer quotationCreatedBy) {
		this.quotationCreatedBy = quotationCreatedBy;
	}

	/**
	 * @return the approvalRequired
	 */
	public String getApprovalRequired() {
		return approvalRequired;
	}

	/**
	 * @param approvalRequired the approvalRequired to set
	 */
	public void setApprovalRequired(String approvalRequired) {
		this.approvalRequired = approvalRequired;
	}

	/**
	 * @return the contractCreatedBy
	 */
	public Integer getContractCreatedBy() {
		return contractCreatedBy;
	}

	/**
	 * @param contractCreatedBy the contractCreatedBy to set
	 */
	public void setContractCreatedBy(Integer contractCreatedBy) {
		this.contractCreatedBy = contractCreatedBy;
	}

	/**
	 * @return the rateContractType
	 */
	public String getRateContractType() {
		return rateContractType;
	}

	/**
	 * @param rateContractType the rateContractType to set
	 */
	public void setRateContractType(String rateContractType) {
		this.rateContractType = rateContractType;
	}

	/**
	 * @return the rhoOfcCode
	 */
	public String getRhoOfcCode() {
		return rhoOfcCode;
	}

	/**
	 * @param rhoOfcCode the rhoOfcCode to set
	 */
	public void setRhoOfcCode(String rhoOfcCode) {
		this.rhoOfcCode = rhoOfcCode;
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
	 * @return the salesOfcName
	 */
	public String getSalesOfcName() {
		return salesOfcName;
	}

	/**
	 * @param salesOfcName the salesOfcName to set
	 */
	public void setSalesOfcName(String salesOfcName) {
		this.salesOfcName = salesOfcName;
	}
	
}
