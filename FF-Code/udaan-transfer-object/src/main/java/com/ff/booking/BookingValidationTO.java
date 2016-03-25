package com.ff.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.serviceOfferring.ProductTO;

public class BookingValidationTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2376315116905559948L;
	/**
	 * Input parameters
	 */
	private String consgNumber;
	private String custCode;
	private Integer custID;
	private List<String> consgNumbers;
	private Integer pincodeId;
	private String pincode;
	private Integer cityId;
	private String cityName;
	private Integer bookingOfficeId;
	private String bookingType;
	private Double maxDiscount;
	private Double privilegeCardAmt;
	private Double privilegeCardAvalBal;
	private String privilegeCardNo;
	private Integer privilegeCardId;
	private String consgSeries;
	private Double declaredValue;
	private Integer rowCount;
	private String processCode;
	private Integer maxBackBookingDateAllowed;
	private String bookingDate;
	private Double consgWeight;
	private String consgType;
	

	private String isPkupCN = "N";

	// For validating consignee and condignor
	private ConsignorConsigneeTO consignee;
	private ConsignorConsigneeTO consignor;

	// Goods issue validations (Branch /Employee / Business Associate /
	// customer)
	private Integer issuedTOPartyId;// Customer / BA / Fr
	private Integer issuedTOPartyId1;// Branch
	private Integer issuedTOPartyId2;// Employee
	/**
	 * Outputs
	 */
	private String errorMsg;
	private List<String> errorCodes = new ArrayList();
	private String isCustValid = "Y";
	private String isValidPincode = "Y";
	private String isValidCN = "Y";
	private String isConsgExists = "N";
	private String isDiscountExceeded = "N";
	private String isValidPrivilegeCard = "Y";
	private String isValidPriorityPincode = "Y";
	private Map<String, String> consgTransStatus = null;
	// private List<InsuranceConfigTO> insuredByDtls = null;
	private String businessAssociate;
	private Integer businessAssociateId;
	private Integer bookingId;
	private String pickupRunsheetNo;
	private String isValidDeclaredVal;
	private String consgIssuedTO;

	private String isBusinessExceptionReq = "Y";

	private String officeCode;
	private String rhoCode;
	
	private String consigneeFirstName;
	private String consigneeAddr;
	
	private Integer altPincodeId;
	private String altPincode;
	private Integer altCityId;
	private String altCityName;
	
	List<ProductTO> productTOList;
	
	private String consigneePhn;
	private String consigneeMobile;
	private String consignorPhn;
	private String consignorMobile;
	private String consignorFirstName;
	private Boolean isProductServiced;
	private Integer loggedInCityId;//for BA Booking this should not be null
	
	
	
	

	

	/**
	 * @return the consignorFirstName
	 */
	public String getConsignorFirstName() {
		return consignorFirstName;
	}

	/**
	 * @param consignorFirstName the consignorFirstName to set
	 */
	public void setConsignorFirstName(String consignorFirstName) {
		this.consignorFirstName = consignorFirstName;
	}

	/**
	 * @return the isProductServiced
	 */
	public Boolean getIsProductServiced() {
		return isProductServiced;
	}

	/**
	 * @param isProductServiced the isProductServiced to set
	 */
	public void setIsProductServiced(Boolean isProductServiced) {
		this.isProductServiced = isProductServiced;
	}

	/**
	 * @return the consigneePhn
	 */
	public String getConsigneePhn() {
		return consigneePhn;
	}

	/**
	 * @param consigneePhn the consigneePhn to set
	 */
	public void setConsigneePhn(String consigneePhn) {
		this.consigneePhn = consigneePhn;
	}

	/**
	 * @return the consigneeMobile
	 */
	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	/**
	 * @param consigneeMobile the consigneeMobile to set
	 */
	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}

	/**
	 * @return the consignorPhn
	 */
	public String getConsignorPhn() {
		return consignorPhn;
	}

	/**
	 * @param consignorPhn the consignorPhn to set
	 */
	public void setConsignorPhn(String consignorPhn) {
		this.consignorPhn = consignorPhn;
	}

	/**
	 * @return the consignorMobile
	 */
	public String getConsignorMobile() {
		return consignorMobile;
	}

	/**
	 * @param consignorMobile the consignorMobile to set
	 */
	public void setConsignorMobile(String consignorMobile) {
		this.consignorMobile = consignorMobile;
	}

	/**
	 * @return the productTOList
	 */
	public List<ProductTO> getProductTOList() {
		return productTOList;
	}

	/**
	 * @param productTOList the productTOList to set
	 */
	public void setProductTOList(List<ProductTO> productTOList) {
		this.productTOList = productTOList;
	}

	/*
	 * @return the altPincodeId
	 */
	public Integer getAltPincodeId() {
		return altPincodeId;
	}

	/**
	 * @param altPincodeId the altPincodeId to set
	 */
	public void setAltPincodeId(Integer altPincodeId) {
		this.altPincodeId = altPincodeId;
	}

	/**
	 * @return the altPincode
	 */
	public String getAltPincode() {
		return altPincode;
	}

	/**
	 * @param altPincode the altPincode to set
	 */
	public void setAltPincode(String altPincode) {
		this.altPincode = altPincode;
	}

	/**
	 * @return the altCityId
	 */
	public Integer getAltCityId() {
		return altCityId;
	}

	/**
	 * @param altCityId the altCityId to set
	 */
	public void setAltCityId(Integer altCityId) {
		this.altCityId = altCityId;
	}

	/**
	 * @return the altCityName
	 */
	public String getAltCityName() {
		return altCityName;
	}

	/**
	 * @param altCityName the altCityName to set
	 */
	public void setAltCityName(String altCityName) {
		this.altCityName = altCityName;
	}

	public String getConsgNumber() {
		return consgNumber;
	}

	public void setConsgNumber(String consgNumber) {
		this.consgNumber = consgNumber;
	}

	public Integer getPincodeId() {
		return pincodeId;
	}

	public void setPincodeId(Integer pincodeId) {
		this.pincodeId = pincodeId;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getIsValidPincode() {
		return isValidPincode;
	}

	public void setIsValidPincode(String isValidPincode) {
		this.isValidPincode = isValidPincode;
	}

	public String getIsValidCN() {
		return isValidCN;
	}

	public void setIsValidCN(String isValidCN) {
		this.isValidCN = isValidCN;
	}

	public Integer getBookingOfficeId() {
		return bookingOfficeId;
	}

	public void setBookingOfficeId(Integer bookingOfficeId) {
		this.bookingOfficeId = bookingOfficeId;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getIsConsgExists() {
		return isConsgExists;
	}

	public void setIsConsgExists(String isConsgExists) {
		this.isConsgExists = isConsgExists;
	}

	public String getBookingType() {
		return bookingType;
	}

	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}

	public Double getMaxDiscount() {
		return maxDiscount;
	}

	public void setMaxDiscount(Double maxDiscount) {
		this.maxDiscount = maxDiscount;
	}

	public String getIsDiscountExceeded() {
		return isDiscountExceeded;
	}

	public void setIsDiscountExceeded(String isDiscountExceeded) {
		this.isDiscountExceeded = isDiscountExceeded;
	}

	public Double getPrivilegeCardAmt() {
		return privilegeCardAmt;
	}

	public void setPrivilegeCardAmt(Double privilegeCardAmt) {
		this.privilegeCardAmt = privilegeCardAmt;
	}

	public Double getPrivilegeCardAvalBal() {
		return privilegeCardAvalBal;
	}

	public void setPrivilegeCardAvalBal(Double privilegeCardAvalBal) {
		this.privilegeCardAvalBal = privilegeCardAvalBal;
	}

	public String getIsValidPrivilegeCard() {
		return isValidPrivilegeCard;
	}

	public void setIsValidPrivilegeCard(String isValidPrivilegeCard) {
		this.isValidPrivilegeCard = isValidPrivilegeCard;
	}

	public String getPrivilegeCardNo() {
		return privilegeCardNo;
	}

	public void setPrivilegeCardNo(String privilegeCardNo) {
		this.privilegeCardNo = privilegeCardNo;
	}

	public Integer getPrivilegeCardId() {
		return privilegeCardId;
	}

	public void setPrivilegeCardId(Integer privilegeCardId) {
		this.privilegeCardId = privilegeCardId;
	}

	public String getConsgSeries() {
		return consgSeries;
	}

	public void setConsgSeries(String consgSeries) {
		this.consgSeries = consgSeries;
	}

	public String getIsValidPriorityPincode() {
		return isValidPriorityPincode;
	}

	public void setIsValidPriorityPincode(String isValidPriorityPincode) {
		this.isValidPriorityPincode = isValidPriorityPincode;
	}

	public List<String> getConsgNumbers() {
		return consgNumbers;
	}

	public void setConsgNumbers(List<String> consgNumbers) {
		this.consgNumbers = consgNumbers;
	}

	public Map<String, String> getConsgTransStatus() {
		return consgTransStatus;
	}

	public void setConsgTransStatus(Map<String, String> consgTransStatus) {
		this.consgTransStatus = consgTransStatus;
	}

	public Double getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}

	/**
	 * @return the rowCount
	 */
	public Integer getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount
	 *            the rowCount to set
	 */
	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	public String getBusinessAssociate() {
		return businessAssociate;
	}

	public void setBusinessAssociate(String businessAssociate) {
		this.businessAssociate = businessAssociate;
	}

	public Integer getBusinessAssociateId() {
		return businessAssociateId;
	}

	public void setBusinessAssociateId(Integer businessAssociateId) {
		this.businessAssociateId = businessAssociateId;
	}

	/**
	 * @return the isCustValid
	 */
	public String getIsCustValid() {
		return isCustValid;
	}

	/**
	 * @param isCustValid
	 *            the isCustValid to set
	 */
	public void setIsCustValid(String isCustValid) {
		this.isCustValid = isCustValid;
	}

	/**
	 * @return the custCode
	 */
	public String getCustCode() {
		return custCode;
	}

	/**
	 * @param custCode
	 *            the custCode to set
	 */
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	/**
	 * @return the custID
	 */
	public Integer getCustID() {
		return custID;
	}

	/**
	 * @param custID
	 *            the custID to set
	 */
	public void setCustID(Integer custID) {
		this.custID = custID;
	}

	public Integer getIssuedTOPartyId() {
		return issuedTOPartyId;
	}

	public void setIssuedTOPartyId(Integer issuedTOPartyId) {
		this.issuedTOPartyId = issuedTOPartyId;
	}

	public Integer getIssuedTOPartyId1() {
		return issuedTOPartyId1;
	}

	public void setIssuedTOPartyId1(Integer issuedTOPartyId1) {
		this.issuedTOPartyId1 = issuedTOPartyId1;
	}

	public Integer getIssuedTOPartyId2() {
		return issuedTOPartyId2;
	}

	public void setIssuedTOPartyId2(Integer issuedTOPartyId2) {
		this.issuedTOPartyId2 = issuedTOPartyId2;
	}

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public String getPickupRunsheetNo() {
		return pickupRunsheetNo;
	}

	public void setPickupRunsheetNo(String pickupRunsheetNo) {
		this.pickupRunsheetNo = pickupRunsheetNo;
	}

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	/**
	 * @return the isValidDeclaredVal
	 */
	public String getIsValidDeclaredVal() {
		return isValidDeclaredVal;
	}

	/**
	 * @param isValidDeclaredVal
	 *            the isValidDeclaredVal to set
	 */
	public void setIsValidDeclaredVal(String isValidDeclaredVal) {
		this.isValidDeclaredVal = isValidDeclaredVal;
	}

	public String getConsgIssuedTO() {
		return consgIssuedTO;
	}

	public void setConsgIssuedTO(String consgIssuedTO) {
		this.consgIssuedTO = consgIssuedTO;
	}

	public List<String> getErrorCodes() {
		return errorCodes;
	}

	public void setErrorCodes(List<String> errorCodes) {
		this.errorCodes = errorCodes;
	}

	public String getIsBusinessExceptionReq() {
		return isBusinessExceptionReq;
	}

	public void setIsBusinessExceptionReq(String isBusinessExceptionReq) {
		this.isBusinessExceptionReq = isBusinessExceptionReq;
	}

	public ConsignorConsigneeTO getConsignee() {
		return consignee;
	}

	public void setConsignee(ConsignorConsigneeTO consignee) {
		this.consignee = consignee;
	}

	public ConsignorConsigneeTO getConsignor() {
		return consignor;
	}

	public void setConsignor(ConsignorConsigneeTO consignor) {
		this.consignor = consignor;
	}

	public Integer getMaxBackBookingDateAllowed() {
		return maxBackBookingDateAllowed;
	}

	public void setMaxBackBookingDateAllowed(Integer maxBackBookingDateAllowed) {
		this.maxBackBookingDateAllowed = maxBackBookingDateAllowed;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Double getConsgWeight() {
		return consgWeight;
	}

	public void setConsgWeight(Double consgWeight) {
		this.consgWeight = consgWeight;
	}

	public String getConsgType() {
		return consgType;
	}

	public void setConsgType(String consgType) {
		this.consgType = consgType;
	}

	/**
	 * @return the officeCode
	 */
	public String getOfficeCode() {
		return officeCode;
	}

	/**
	 * @param officeCode
	 *            the officeCode to set
	 */
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	/**
	 * @return the rhoCode
	 */
	public String getRhoCode() {
		return rhoCode;
	}

	/**
	 * @param rhoCode
	 *            the rhoCode to set
	 */
	public void setRhoCode(String rhoCode) {
		this.rhoCode = rhoCode;
	}

	/**
	 * @return the isPkupCN
	 */
	public String getIsPkupCN() {
		return isPkupCN;
	}

	/**
	 * @param isPkupCN
	 *            the isPkupCN to set
	 */
	public void setIsPkupCN(String isPkupCN) {
		this.isPkupCN = isPkupCN;
	}

	/**
	 * @return the consigneeFirstName
	 */
	public String getConsigneeFirstName() {
		return consigneeFirstName;
	}

	/**
	 * @param consigneeFirstName the consigneeFirstName to set
	 */
	public void setConsigneeFirstName(String consigneeFirstName) {
		this.consigneeFirstName = consigneeFirstName;
	}

	/**
	 * @return the consigneeAddr
	 */
	public String getConsigneeAddr() {
		return consigneeAddr;
	}

	/**
	 * @param consigneeAddr the consigneeAddr to set
	 */
	public void setConsigneeAddr(String consigneeAddr) {
		this.consigneeAddr = consigneeAddr;
	}

	/**
	 * @return the loggedInCityId
	 */
	public Integer getLoggedInCityId() {
		return loggedInCityId;
	}

	/**
	 * @param loggedInCityId the loggedInCityId to set
	 */
	public void setLoggedInCityId(Integer loggedInCityId) {
		this.loggedInCityId = loggedInCityId;
	}

	
}
