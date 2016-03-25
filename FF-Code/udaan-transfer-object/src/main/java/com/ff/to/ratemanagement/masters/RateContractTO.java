package com.ff.to.ratemanagement.masters;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationTO;

/**
 * The Class RateContractTO.
 * @author narmdr
 */
public class RateContractTO extends CGBaseTO {
	
	private static final long serialVersionUID = 6967186622295398004L;
	
	private String custName;
	private Integer rateContractId;
	private String rateContractNo;
	private String validFromDate;
	private String validToDate;
	private String billingContractType;////N - Normal, R - Reverse Logistics
	private String typeOfBilling;//'DBDP','CBCP','DBCP'
	private String modeOfBilling;//H - Hard Copy, S - Soft Copy
	private String billingCycle;//M - Monthly, F - Fortnightly
	private String paymentTerm;//'P001','P002','P003','P004','P005','P006'
	private String octraiBourneBy;//CO - Consigner, CE - Consignee 
	private String contractFor;//L - Local
	private String rateContractType;//N - Normal, E - ECommerce 
	private String contractStatus;//C- Created,S- Submitted,A- Active,I- Inactive,B- Blocked
	private String userType;//normal user or super user
	
	
	private Integer customerId;
	private Integer rateQuotationId;
	
	private CustomerTO customer;
	private RateQuotationTO rateQuotationTO;
	
	private Integer createdBy;
	private Integer updatedBy;
	private String createdDate;
	
	private String panNo;
	private String tanNo;
	private String pickupDlvContractType;//P - Pickup, D - Delivery
	private String customerStatus;//A-Active, I-Inactive
	
	private String isTriggeredFromSearchButton; // Y - yes, N - no
	private String isDeletePickupOrDlvLocations; // Y - yes, N - no
	
	
	/** Pickup details START */
	int rowCount;
	private String[] pincode = new String[rowCount];
	private Integer[] pickupBranch = new Integer[rowCount];
	private String[] pickupBranchCode = new String[rowCount];
	private String[] locationName = new String[rowCount];
	private String[] address1 = new String[rowCount];
	private String[] address2 = new String[rowCount];
	private String[] address3 = new String[rowCount];
	private String[] contactPerson = new String[rowCount];
	private String[] designation = new String[rowCount];
	private String[] mobile = new String[rowCount];
	private String[] email = new String[rowCount];
	private boolean[] billing = new boolean[rowCount];
	private boolean[] payment = new boolean[rowCount];
	private String[] isBillLoc = new String[rowCount];//Y - Yes, N - No (if checked then "Y" else "N")
	private String[] isPayLoc = new String[rowCount];//Y - Yes, N - No (if checked then "Y" else "N")
	private String[] customerCode = new String[rowCount];
	private String[] locCode = new String[rowCount];//Location Code = Plant Code + 4 digit
	private Integer[] locCity = new Integer[rowCount];
	private Integer[] locPincodeId = new Integer[rowCount];
	
	/* PK: Contract Payment Billing Location */
	private Integer[] contractPaymentBillingLocationId = new Integer[rowCount];//hidden
	/* PK: Pickup Delivery Location */
	private Integer[] pickupDlvLocId = new Integer[rowCount];//hidden
	/* PK: Pickup Delivery Contract */
	private Integer[] contractId = new Integer[rowCount];//hidden
	/* PK: Address */
	private Integer[] addressId = new Integer[rowCount];//hidden
	/** Pickup details END */
	
	private List<ContractPaymentBillingLocationTO> contractLocationList;
	private List<Integer> conPayBillLocIdList;
	private List<Integer> pickupDlvLocIdList;
	private List<Integer> contractIdList;
	private List<Integer> addressIdList;
	private String transMsg;
	private Boolean isNew;
	private Integer originatedRateContractId;
	private String oldContractExpDate;
	private String isRenewed;
	private Date oldExpDate;
	private boolean isExistPkUpOrDlvDetails;
	private boolean renewContract;
	private String  soldToCode;
	private String billingSaved = "N";
	private String pkpupSaved = "N";
	private String dlvSaved = "N";
	private String ffContactsSaved = "N";
	private String distributionChannel;
	private String customerSapStatus;
	private String customerDtToBranch;
	private boolean isSaved=Boolean.FALSE;
	private String pickDlvIdsArr;
	private List<OfficeTO> salesOfcList;
	private List<CityTO> salesCityList;
	private List<EmployeeTO> salesPersonList;
	private String fromMailId;
	
	
	
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
	/**
	 * @return the renewContract
	 */
	public boolean isRenewContract() {
		return renewContract;
	}
	/**
	 * @param renewContract the renewContract to set
	 */
	public void setRenewContract(boolean renewContract) {
		this.renewContract = renewContract;
	}
	/**
	 * @return the isExistPkUpOrDlvDetails
	 */
	public boolean getIsExistPkUpOrDlvDetails() {
		return isExistPkUpOrDlvDetails;
	}
	/**
	 * @param isExistPkUpOrDlvDetails the isExistPkUpOrDlvDetails to set
	 */
	public void setIsExistPkUpOrDlvDetails(boolean isExistPkUpOrDlvDetails) {
		this.isExistPkUpOrDlvDetails = isExistPkUpOrDlvDetails;
	}
	
	/**
	 * @return the oldContractExpDate
	 */
	public String getOldContractExpDate() {
		return oldContractExpDate;
	}
	/**
	 * @return the oldExpDate
	 */
	public Date getOldExpDate() {
		return oldExpDate;
	}
	/**
	 * @param oldExpDate the oldExpDate to set
	 */
	public void setOldExpDate(Date oldExpDate) {
		this.oldExpDate = oldExpDate;
	}
	/**
	 * @param oldContractExpDate the oldContractExpDate to set
	 */
	public void setOldContractExpDate(String oldContractExpDate) {
		this.oldContractExpDate = oldContractExpDate;
	}
	/**
	 * @return the isRenewed
	 */
	public String getIsRenewed() {
		return isRenewed;
	}
	/**
	 * @param isRenewed the isRenewed to set
	 */
	public void setIsRenewed(String isRenewed) {
		this.isRenewed = isRenewed;
	}
	/**
	 * @return the originatedRateContractId
	 */
	public Integer getOriginatedRateContractId() {
		return originatedRateContractId;
	}
	/**
	 * @param originatedRateContractId the originatedRateContractId to set
	 */
	public void setOriginatedRateContractId(Integer originatedRateContractId) {
		this.originatedRateContractId = originatedRateContractId;
	}
	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}
	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	/**
	 * @return the locationName
	 */
	public String[] getLocationName() {
		return locationName;
	}
	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String[] locationName) {
		this.locationName = locationName;
	}
	/**
	 * @return the locCode
	 */
	public String[] getLocCode() {
		return locCode;
	}
	/**
	 * @param locCode the locCode to set
	 */
	public void setLocCode(String[] locCode) {
		this.locCode = locCode;
	}
	/**
	 * @return the transMsg
	 */
	public String getTransMsg() {
		return transMsg;
	}
	/**
	 * @param transMsg the transMsg to set
	 */
	public void setTransMsg(String transMsg) {
		this.transMsg = transMsg;
	}
	/**
	 * @return the conPayBillLocIdList
	 */
	public List<Integer> getConPayBillLocIdList() {
		return conPayBillLocIdList;
	}
	/**
	 * @param conPayBillLocIdList the conPayBillLocIdList to set
	 */
	public void setConPayBillLocIdList(List<Integer> conPayBillLocIdList) {
		this.conPayBillLocIdList = conPayBillLocIdList;
	}
	/**
	 * @return the pickupDlvLocIdList
	 */
	public List<Integer> getPickupDlvLocIdList() {
		return pickupDlvLocIdList;
	}
	/**
	 * @param pickupDlvLocIdList the pickupDlvLocIdList to set
	 */
	public void setPickupDlvLocIdList(List<Integer> pickupDlvLocIdList) {
		this.pickupDlvLocIdList = pickupDlvLocIdList;
	}
	/**
	 * @return the contractIdList
	 */
	public List<Integer> getContractIdList() {
		return contractIdList;
	}
	/**
	 * @param contractIdList the contractIdList to set
	 */
	public void setContractIdList(List<Integer> contractIdList) {
		this.contractIdList = contractIdList;
	}
	/**
	 * @return the addressIdList
	 */
	public List<Integer> getAddressIdList() {
		return addressIdList;
	}
	/**
	 * @param addressIdList the addressIdList to set
	 */
	public void setAddressIdList(List<Integer> addressIdList) {
		this.addressIdList = addressIdList;
	}
	/**
	 * @return the customerStatus
	 */
	public String getCustomerStatus() {
		return customerStatus;
	}
	/**
	 * @param customerStatus the customerStatus to set
	 */
	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}
	/**
	 * @return the billing
	 */
	public boolean[] getBilling() {
		return billing;
	}
	/**
	 * @param billing the billing to set
	 */
	public void setBilling(boolean[] billing) {
		this.billing = billing;
	}
	/**
	 * @return the payment
	 */
	public boolean[] getPayment() {
		return payment;
	}
	/**
	 * @param payment the payment to set
	 */
	public void setPayment(boolean[] payment) {
		this.payment = payment;
	}
	/**
	 * @return the isBillLoc
	 */
	public String[] getIsBillLoc() {
		return isBillLoc;
	}
	/**
	 * @param isBillLoc the isBillLoc to set
	 */
	public void setIsBillLoc(String[] isBillLoc) {
		this.isBillLoc = isBillLoc;
	}
	/**
	 * @return the isPayLoc
	 */
	public String[] getIsPayLoc() {
		return isPayLoc;
	}
	/**
	 * @param isPayLoc the isPayLoc to set
	 */
	public void setIsPayLoc(String[] isPayLoc) {
		this.isPayLoc = isPayLoc;
	}
	/**
	 * @return the pickupDlvContractType
	 */
	public String getPickupDlvContractType() {
		return pickupDlvContractType;
	}
	/**
	 * @param pickupDlvContractType the pickupDlvContractType to set
	 */
	public void setPickupDlvContractType(String pickupDlvContractType) {
		this.pickupDlvContractType = pickupDlvContractType;
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
	 * @return the pickupBranchCode
	 */
	public String[] getPickupBranchCode() {
		return pickupBranchCode;
	}
	/**
	 * @param pickupBranchCode the pickupBranchCode to set
	 */
	public void setPickupBranchCode(String[] pickupBranchCode) {
		this.pickupBranchCode = pickupBranchCode;
	}
	/**
	 * @return the billingContractType
	 */
	public String getBillingContractType() {
		return billingContractType;
	}
	/**
	 * @param billingContractType the billingContractType to set
	 */
	public void setBillingContractType(String billingContractType) {
		this.billingContractType = billingContractType;
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
	 * @return the contractStatus
	 */
	public String getContractStatus() {
		return contractStatus;
	}
	/**
	 * @param contractStatus the contractStatus to set
	 */
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
	/**
	 * @return the contractPaymentBillingLocationId
	 */
	public Integer[] getContractPaymentBillingLocationId() {
		return contractPaymentBillingLocationId;
	}
	/**
	 * @param contractPaymentBillingLocationId the contractPaymentBillingLocationId to set
	 */
	public void setContractPaymentBillingLocationId(
			Integer[] contractPaymentBillingLocationId) {
		this.contractPaymentBillingLocationId = contractPaymentBillingLocationId;
	}
	/**
	 * @return the pickupDlvLocId
	 */
	public Integer[] getPickupDlvLocId() {
		return pickupDlvLocId;
	}
	/**
	 * @param pickupDlvLocId the pickupDlvLocId to set
	 */
	public void setPickupDlvLocId(Integer[] pickupDlvLocId) {
		this.pickupDlvLocId = pickupDlvLocId;
	}
	/**
	 * @return the contractId
	 */
	public Integer[] getContractId() {
		return contractId;
	}
	/**
	 * @param contractId the contractId to set
	 */
	public void setContractId(Integer[] contractId) {
		this.contractId = contractId;
	}
	/**
	 * @return the addressId
	 */
	public Integer[] getAddressId() {
		return addressId;
	}
	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(Integer[] addressId) {
		this.addressId = addressId;
	}
	/**
	 * @return the contactPerson
	 */
	public String[] getContactPerson() {
		return contactPerson;
	}
	/**
	 * @param contactPerson the contactPerson to set
	 */
	public void setContactPerson(String[] contactPerson) {
		this.contactPerson = contactPerson;
	}
	/**
	 * @return the designation
	 */
	public String[] getDesignation() {
		return designation;
	}
	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(String[] designation) {
		this.designation = designation;
	}
	/**
	 * @return the mobile
	 */
	public String[] getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String[] mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the email
	 */
	public String[] getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String[] email) {
		this.email = email;
	}
	/**
	 * @return the pincodes
	 */
	public String[] getPincode() {
		return pincode;
	}
	/**
	 * @param pincodes the pincode to set
	 */
	public void setPincode(String[] pincode) {
		this.pincode = pincode;
	}
	/**
	 * @return the pickupBranch
	 */
	public Integer[] getPickupBranch() {
		return pickupBranch;
	}
	/**
	 * @param pickupBranch the pickupBranch to set
	 */
	public void setPickupBranch(Integer[] pickupBranch) {
		this.pickupBranch = pickupBranch;
	}
	/**
	 * @return the address1
	 */
	public String[] getAddress1() {
		return address1;
	}
	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String[] address1) {
		this.address1 = address1;
	}
	/**
	 * @return the address2
	 */
	public String[] getAddress2() {
		return address2;
	}
	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String[] address2) {
		this.address2 = address2;
	}
	/**
	 * @return the address3
	 */
	public String[] getAddress3() {
		return address3;
	}
	/**
	 * @param address3 the address3 to set
	 */
	public void setAddress3(String[] address3) {
		this.address3 = address3;
	}
	/**
	 * @return the customerCode
	 */
	public String[] getCustomerCode() {
		return customerCode;
	}
	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String[] customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * @return the contractLocationList
	 */
	public List<ContractPaymentBillingLocationTO> getContractLocationList() {
		return contractLocationList;
	}
	/**
	 * @param contractLocationList the contractLocationList to set
	 */
	public void setContractLocationList(
			List<ContractPaymentBillingLocationTO> contractLocationList) {
		this.contractLocationList = contractLocationList;
	}
	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	/**
	 * @return the customer
	 */
	public CustomerTO getCustomer() {
		if(customer == null){
			customer = new CustomerTO();
		}
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(CustomerTO customer) {
		this.customer = customer;
	}
	/**
	 * @return the rateQuotationTO
	 */
	public RateQuotationTO getRateQuotationTO() {
		if(rateQuotationTO==null)
			rateQuotationTO=new RateQuotationTO();
		return rateQuotationTO;
	}
	/**
	 * @param rateQuotationTO the rateQuotationTO to set
	 */
	public void setRateQuotationTO(RateQuotationTO rateQuotationTO) {
		this.rateQuotationTO = rateQuotationTO;
	}
	/**
	 * @return the updatedBy
	 */
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
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
	 * @return the rateContractId
	 */
	
	
	public Integer getRateContractId() {
		return rateContractId;
	}
	/**
	 * @return the rateContractNo
	 */
	public String getRateContractNo() {
		return rateContractNo;
	}
	/**
	 * @param rateContractNo the rateContractNo to set
	 */
	public void setRateContractNo(String rateContractNo) {
		this.rateContractNo = rateContractNo;
	}
	/**
	 * @param rateContractId the rateContractId to set
	 */
	public void setRateContractId(Integer rateContractId) {
		this.rateContractId = rateContractId;
	}
	/**
	 * @return the typeOfBilling
	 */
	public String getTypeOfBilling() {
		return typeOfBilling;
	}
	/**
	 * @param typeOfBilling the typeOfBilling to set
	 */
	public void setTypeOfBilling(String typeOfBilling) {
		this.typeOfBilling = typeOfBilling;
	}
	/**
	 * @return the modeOfBilling
	 */
	public String getModeOfBilling() {
		return modeOfBilling;
	}
	/**
	 * @param modeOfBilling the modeOfBilling to set
	 */
	public void setModeOfBilling(String modeOfBilling) {
		this.modeOfBilling = modeOfBilling;
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
	 * @return the paymentTerm
	 */
	public String getPaymentTerm() {
		return paymentTerm;
	}
	/**
	 * @param paymentTerm the paymentTerm to set
	 */
	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}
	/**
	 * @return the octraiBourneBy
	 */
	public String getOctraiBourneBy() {
		return octraiBourneBy;
	}
	/**
	 * @param octraiBourneBy the octraiBourneBy to set
	 */
	public void setOctraiBourneBy(String octraiBourneBy) {
		this.octraiBourneBy = octraiBourneBy;
	}
	/**
	 * @return the contractFor
	 */
	public String getContractFor() {
		return contractFor;
	}
	/**
	 * @param contractFor the contractFor to set
	 */
	public void setContractFor(String contractFor) {
		this.contractFor = contractFor;
	}
	/**
	 * @return the customerId
	 */
	public Integer getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	/**
	 * @return the isNew
	 */
	public Boolean getIsNew() {
		return isNew;
	}
	/**
	 * @param isNew the isNew to set
	 */
	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
	/**
	 * @return the soldToCode
	 */
	public String getSoldToCode() {
		return soldToCode;
	}
	/**
	 * @param soldToCode the soldToCode to set
	 */
	public void setSoldToCode(String soldToCode) {
		this.soldToCode = soldToCode;
	}
	/**
	 * @return the billingSaved
	 */
	public String getBillingSaved() {
		return billingSaved;
	}
	/**
	 * @param billingSaved the billingSaved to set
	 */
	public void setBillingSaved(String billingSaved) {
		this.billingSaved = billingSaved;
	}
	/**
	 * @return the pkpupSaved
	 */
	public String getPkpupSaved() {
		return pkpupSaved;
	}
	/**
	 * @param pkpupSaved the pkpupSaved to set
	 */
	public void setPkpupSaved(String pkpupSaved) {
		this.pkpupSaved = pkpupSaved;
	}
	/**
	 * @return the dlvSaved
	 */
	public String getDlvSaved() {
		return dlvSaved;
	}
	/**
	 * @param dlvSaved the dlvSaved to set
	 */
	public void setDlvSaved(String dlvSaved) {
		this.dlvSaved = dlvSaved;
	}
	/**
	 * @return the distributionChannel
	 */
	public String getDistributionChannel() {
		return distributionChannel;
	}
	/**
	 * @param distributionChannel the distributionChannel to set
	 */
	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}
	/**
	 * @return the customerSapStatus
	 */
	public String getCustomerSapStatus() {
		return customerSapStatus;
	}
	/**
	 * @param customerSapStatus the customerSapStatus to set
	 */
	public void setCustomerSapStatus(String customerSapStatus) {
		this.customerSapStatus = customerSapStatus;
	}
	/**
	 * @return the customerDtToBranch
	 */
	public String getCustomerDtToBranch() {
		return customerDtToBranch;
	}
	/**
	 * @param customerDtToBranch the customerDtToBranch to set
	 */
	public void setCustomerDtToBranch(String customerDtToBranch) {
		this.customerDtToBranch = customerDtToBranch;
	}
	public String getPickDlvIdsArr() {
		return pickDlvIdsArr;
	}
	public void setPickDlvIdsArr(String pickDlvIdsArr) {
		this.pickDlvIdsArr = pickDlvIdsArr;
	}
	/**
	 * @return the ffContactsSaved
	 */
	public String getFfContactsSaved() {
		return ffContactsSaved;
	}
	/**
	 * @param ffContactsSaved the ffContactsSaved to set
	 */
	public void setFfContactsSaved(String ffContactsSaved) {
		this.ffContactsSaved = ffContactsSaved;
	}
	/**
	 * @return the salesOfcList
	 */
	public List<OfficeTO> getSalesOfcList() {
		return salesOfcList;
	}
	/**
	 * @param salesOfcList the salesOfcList to set
	 */
	public void setSalesOfcList(List<OfficeTO> salesOfcList) {
		this.salesOfcList = salesOfcList;
	}
	/**
	 * @return the salesCityList
	 */
	public List<CityTO> getSalesCityList() {
		return salesCityList;
	}
	/**
	 * @param salesCityList the salesCityList to set
	 */
	public void setSalesCityList(List<CityTO> salesCityList) {
		this.salesCityList = salesCityList;
	}
	/**
	 * @return the salesPersonList
	 */
	public List<EmployeeTO> getSalesPersonList() {
		return salesPersonList;
	}
	/**
	 * @param salesPersonList the salesPersonList to set
	 */
	public void setSalesPersonList(List<EmployeeTO> salesPersonList) {
		this.salesPersonList = salesPersonList;
	}
	/**
	 * @return the locCity
	 */
	public Integer[] getLocCity() {
		return locCity;
	}
	/**
	 * @param locCity the locCity to set
	 */
	public void setLocCity(Integer[] locCity) {
		this.locCity = locCity;
	}
	/**
	 * @return the locPincodeId
	 */
	public Integer[] getLocPincodeId() {
		return locPincodeId;
	}
	/**
	 * @param locPincodeId the locPincodeId to set
	 */
	public void setLocPincodeId(Integer[] locPincodeId) {
		this.locPincodeId = locPincodeId;
	}
	/**
	 * @return the fromMailId
	 */
	public String getFromMailId() {
		return fromMailId;
	}
	/**
	 * @param fromMailId the fromMailId to set
	 */
	public void setFromMailId(String fromMailId) {
		this.fromMailId = fromMailId;
	}
	/**
	 * @return value of flag isTriggeredFromSearchButton
	 */
	public String getIsTriggeredFromSearchButton() {
		return isTriggeredFromSearchButton;
	}
	/**
	 * @param isTriggeredFromSearchButton
	 */
	public void setIsTriggeredFromSearchButton(String isTriggeredFromSearchButton) {
		this.isTriggeredFromSearchButton = isTriggeredFromSearchButton;
	}
	
	/**
	 * @return value of the flag isDeletePickupOrDlvLocations
	 */
	public String getIsDeletePickupOrDlvLocations() {
		return isDeletePickupOrDlvLocations;
	}
	/**
	 * @param isDeletePickupOrDlvLocations
	 */
	public void setIsDeletePickupOrDlvLocations(String isDeletePickupOrDlvLocations) {
		this.isDeletePickupOrDlvLocations = isDeletePickupOrDlvLocations;
	}
}
