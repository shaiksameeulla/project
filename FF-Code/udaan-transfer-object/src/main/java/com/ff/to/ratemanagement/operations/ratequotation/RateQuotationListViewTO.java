package com.ff.to.ratemanagement.operations.ratequotation;

import java.util.Date;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateUtil;

/**
 * @author preegupt
 *
 */
public class RateQuotationListViewTO extends CGBaseTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date quotCreatedDate;
	private Integer rateQuotationId;
	private String rateQuotationNo;
	private String regionalName;
	private String cityName;
	private String customerName;
	private String salesOfficeName;
	private String salesPersonName;
	private String status;
	private String fromDate;
	private String toDate;
	private Integer regionOfcId;
	private Integer cityId;
	private int rowNumber;
	private String quotationDate;
	private Integer[] city = new Integer[rowNumber];
	private Integer regionDropId;
	private Integer cityRegDropId;
	private Integer cityDropId;
	private Integer userId;
	private String rateQuotationType;
	private String approvalRequird;  
	private String rateContractId;
	private String contractNo;
	private String validToDate;
	private String validFromDate;
	private String emailIdTO;
	private String emailIdCC;
	private String customerCode;
	private String businessName;
	private Integer userEmpId;
	private Integer salesUserEmpId;
	private Integer quotationCreatedBy;
	private String approvalRequired;
	private Integer contractCreatedBy;
	private String rateContractType;
	private String groupKey;
	private String officeType;
	private String rhoOfcCode;
	private String salesOfcCode;
	private String salesOfcName;
	
	/**
	 * @return the officeType
	 */
	public String getOfficeType() {
		return officeType;
	}
	/**
	 * @param officeType the officeType to set
	 */
	public void setOfficeType(String officeType) {
		this.officeType = officeType;
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
	 * @return the approvalRequird
	 */
	public String getApprovalRequird() {
		return approvalRequird;
	}
	/**
	 * @param approvalRequird the approvalRequird to set
	 */
	public void setApprovalRequird(String approvalRequird) {
		this.approvalRequird = approvalRequird;
	}
	/**
	 * @return the quotationDate
	 */
	public String getQuotationDate() {
		return quotationDate;
	}
	/**
	 * @param quotationDate the quotationDate to set
	 */
	public void setQuotationDate(String quotationDate) {
		this.quotationDate = quotationDate;
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
		this.quotationDate = DateUtil.getDateInDDMMYYYYHHMMSlashFormat(quotCreatedDate);
	}

	/**
	 * @return the rowNumber
	 */
	public int getRowNumber() {
		return rowNumber;
	}
	/**
	 * @return the cityRegDropId
	 */
	public Integer getCityRegDropId() {
		return cityRegDropId;
	}
	/**
	 * @param cityRegDropId the cityRegDropId to set
	 */
	public void setCityRegDropId(Integer cityRegDropId) {
		this.cityRegDropId = cityRegDropId;
	}
	/**
	 * @return the regionDropId
	 */
	public Integer getRegionDropId() {
		return regionDropId;
	}
	/**
	 * @param regionDropId the regionDropId to set
	 */
	public void setRegionDropId(Integer regionDropId) {
		this.regionDropId = regionDropId;
	}
	
	/**
	 * @return the cityDropId
	 */
	public Integer getCityDropId() {
		return cityDropId;
	}
	/**
	 * @param cityDropId the cityDropId to set
	 */
	public void setCityDropId(Integer cityDropId) {
		this.cityDropId = cityDropId;
	}
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @param rowNumber the rowNumber to set
	 */
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	/**
	 * @return the city
	 */
	public Integer[] getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(Integer[] city) {
		this.city = city;
	}
	/**
	 * @return the regionOfcId
	 */
	public Integer getRegionOfcId() {
		return regionOfcId;
	}
	/**
	 * @param regionOfcId the regionOfcId to set
	 */
	public void setRegionOfcId(Integer regionOfcId) {
		this.regionOfcId = regionOfcId;
	}
	/**
	 * @return the cityId
	 */
	public Integer getCityId() {
		return cityId;
	}
	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
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
		return rateQuotationNo;
	}
	/**
	 * @param rateQuotationNo the rateQuotationNo to set
	 */
	public void setRateQuotationNo(String rateQuotationNo) {
		this.rateQuotationNo = rateQuotationNo;
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
