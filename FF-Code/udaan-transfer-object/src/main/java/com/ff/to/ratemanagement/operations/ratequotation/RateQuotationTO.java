package com.ff.to.ratemanagement.operations.ratequotation;

import java.util.List;

import org.apache.struts.upload.FormFile;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.to.ratemanagement.masters.RateCustomerCategoryTO;
/**
 * @author preegupt
 *
 */
public class RateQuotationTO extends CGBaseTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer rateQuotationId;
	private String rateQuotationNo;
	private String status;
	private CustomerTO customer;
	private String rateQuotationType;
	private String rateQuotationOriginatedfromType;
	private Integer rateOriginatedFrom;
	private String createdDate;
	private String loginOfficeCode;
	private String approversRemarks;
	private String excecutiveRemarks;
	private String fromDate;
	private String toDate;
	List<RateCustomerCategoryTO> customerCategoryTOList;
	private Integer createdBy;
	private String approvalRequired;
	private String approvedAt;
	private Integer userId;
	private boolean proposedRates = Boolean.FALSE;
	private boolean fixedChrgs = Boolean.FALSE;
	private String quotationUsedFor;
	private boolean contractCreated;
	private String approver;
	private String indCatCode;
	private Integer vwDenominator;
	private String lcCode;
	private String transMsg;
	private Integer updatedBy;
	private boolean isSaved = Boolean.FALSE;
	private String module;
	private String errorMsg;
	private Integer rhoOfcId;
	private String empOfcType;
	private List<CityTO> cityTOList;
	private String rhoOfcName ;
	private FormFile quotationUploadFile;
	private String quotationCreatedFrom;
	private String proposedRatesP = "N";
	private String proposedRatesD = "N";
	private String proposedRatesB = "N";
	private String proposedRatesCO = "N";
	/**
	 * @return the isSaved
	 */
	public boolean isSaved() {
		return isSaved;
	}
	/**
	 * @param isSaved the isSaved to set
	 */
	public void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}
	public String getTransMsg() {
		return transMsg;
	}
	public void setTransMsg(String transMsg) {
		this.transMsg = transMsg;
	}
	public String getLcCode() {
		return lcCode;
	}
	public void setLcCode(String lcCode) {
		this.lcCode = lcCode;
	}
	public Integer getVwDenominator() {
		return vwDenominator;
	}
	public void setVwDenominator(Integer vwDenominator) {
		this.vwDenominator = vwDenominator;
	}
	/**
	 * @return the approver
	 */
	public String getApprover() {
		return approver;
	}
	/**
	 * @param approver the approver to set
	 */
	public void setApprover(String approver) {
		this.approver = approver;
	}
	/**
	 * @return the contractCreated
	 */
	public boolean isContractCreated() {
		return contractCreated;
	}
	/**
	 * @param contractCreated the contractCreated to set
	 */
	public void setContractCreated(boolean contractCreated) {
		this.contractCreated = contractCreated;
	}
	/**
	 * @return the quotationUsedFor
	 */
	public String getQuotationUsedFor() {
		return quotationUsedFor;
	}
	/**
	 * @param quotationUsedFor the quotationUsedFor to set
	 */
	public void setQuotationUsedFor(String quotationUsedFor) {
		this.quotationUsedFor = quotationUsedFor;
	}
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @return the proposedRates
	 */
	public boolean isProposedRates() {
		return proposedRates;
	}
	/**
	 * @param proposedRates the proposedRates to set
	 */
	public void setProposedRates(boolean proposedRates) {
		this.proposedRates = proposedRates;
	}
	/**
	 * @return the fixedChrgs
	 */
	public boolean isFixedChrgs() {
		return fixedChrgs;
	}
	/**
	 * @param fixedChrgs the fixedChrgs to set
	 */
	public void setFixedChrgs(boolean fixedChrgs) {
		this.fixedChrgs = fixedChrgs;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	 * @return the approvedAt
	 */
	public String getApprovedAt() {
		return approvedAt;
	}
	/**
	 * @param approvedAt the approvedAt to set
	 */
	public void setApprovedAt(String approvedAt) {
		this.approvedAt = approvedAt;
	}
	/**
	 * @return the excecutiveRemarks
	 */
	public String getExcecutiveRemarks() {
		return excecutiveRemarks;
	}
	/**
	 * @param excecutiveRemarks the excecutiveRemarks to set
	 */
	public void setExcecutiveRemarks(String excecutiveRemarks) {
		this.excecutiveRemarks = excecutiveRemarks;
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
	 * @return the customer
	 */

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
	 * @return the rateQuotationOriginatedfromType
	 */
	public String getRateQuotationOriginatedfromType() {
		return rateQuotationOriginatedfromType;
	}
	/**
	 * @param rateQuotationOriginatedfromType the rateQuotationOriginatedfromType to set
	 */
	public void setRateQuotationOriginatedfromType(
			String rateQuotationOriginatedfromType) {
		this.rateQuotationOriginatedfromType = rateQuotationOriginatedfromType;
	}
	
	
	

	/**
	 * @return the customer
	 */
	public CustomerTO getCustomer() {
		if(customer==null)
			customer=new CustomerTO();
			
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(CustomerTO customer) {
		this.customer = customer;
	}
	public String getRateQuotationNo() {
		return rateQuotationNo;
	}
	public void setRateQuotationNo(String rateQuotationNo) {
		this.rateQuotationNo = rateQuotationNo;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getLoginOfficeCode() {
		return loginOfficeCode;
	}
	public void setLoginOfficeCode(String loginOfficeCode) {
		this.loginOfficeCode = loginOfficeCode;
	}
	
	public String getApproversRemarks() {
		return approversRemarks;
	}
	public void setApproversRemarks(String approversRemarks) {
		this.approversRemarks = approversRemarks;
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
	

	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the rateOriginatedFrom
	 */
	public Integer getRateOriginatedFrom() {
		return rateOriginatedFrom;
	}
	/**
	 * @param rateOriginatedFrom the rateOriginatedFrom to set
	 */
	public void setRateOriginatedFrom(Integer rateOriginatedFrom) {
		this.rateOriginatedFrom = rateOriginatedFrom;
	}
	/**
	 * @return the indCatCode
	 */
	public String getIndCatCode() {
		return indCatCode;
	}
	/**
	 * @param indCatCode the indCatCode to set
	 */
	public void setIndCatCode(String indCatCode) {
		this.indCatCode = indCatCode;
	}
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	/**
	 * @return the rhoOfcId
	 */
	public Integer getRhoOfcId() {
		return rhoOfcId;
	}
	/**
	 * @param rhoOfcId the rhoOfcId to set
	 */
	public void setRhoOfcId(Integer rhoOfcId) {
		this.rhoOfcId = rhoOfcId;
	}
	/**
	 * @return the empOfcType
	 */
	public String getEmpOfcType() {
		return empOfcType;
	}
	/**
	 * @param empOfcType the empOfcType to set
	 */
	public void setEmpOfcType(String empOfcType) {
		this.empOfcType = empOfcType;
	}
	/**
	 * @return the cityTOList
	 */
	public List<CityTO> getCityTOList() {
		return cityTOList;
	}
	/**
	 * @param cityTOList the cityTOList to set
	 */
	public void setCityTOList(List<CityTO> cityTOList) {
		this.cityTOList = cityTOList;
	}
	/**
	 * @return the rhoOfcName
	 */
	public String getRhoOfcName() {
		return rhoOfcName;
	}
	/**
	 * @param rhoOfcName the rhoOfcName to set
	 */
	public void setRhoOfcName(String rhoOfcName) {
		this.rhoOfcName = rhoOfcName;
	}
	public FormFile getQuotationUploadFile() {
		return quotationUploadFile;
	}
	public void setQuotationUploadFile(FormFile quotationUploadFile) {
		this.quotationUploadFile = quotationUploadFile;
	}
	public String getQuotationCreatedFrom() {
		return quotationCreatedFrom;
	}
	public void setQuotationCreatedFrom(String quotationCreatedFrom) {
		this.quotationCreatedFrom = quotationCreatedFrom;
	}
	public String getProposedRatesP() {
		return proposedRatesP;
	}
	public void setProposedRatesP(String proposedRatesP) {
		this.proposedRatesP = proposedRatesP;
	}
	public String getProposedRatesD() {
		return proposedRatesD;
	}
	public void setProposedRatesD(String proposedRatesD) {
		this.proposedRatesD = proposedRatesD;
	}
	public String getProposedRatesB() {
		return proposedRatesB;
	}
	public void setProposedRatesB(String proposedRatesB) {
		this.proposedRatesB = proposedRatesB;
	}
	public String getProposedRatesCO() {
		return proposedRatesCO;
	}
	public void setProposedRatesCO(String proposedRatesCO) {
		this.proposedRatesCO = proposedRatesCO;
	}
	
	
}
