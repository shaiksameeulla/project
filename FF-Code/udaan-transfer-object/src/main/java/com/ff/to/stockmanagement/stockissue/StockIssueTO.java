/*
 * 
 */
package com.ff.to.stockmanagement.stockissue;

import java.util.List;
import java.util.Map;

import com.ff.to.stockmanagement.StockHeaderTO;

/**
 * @author hkansagr
 * 
 */

public class StockIssueTO extends StockHeaderTO {
	private static final long serialVersionUID = 1L;
	
	private Long stockIssueId;
	private String recipientCode;
	private String recipientName;
	private Integer recipientId;
	
	private String issueDateStr;
	private String issueTimeStr;
	private String issuedToType;
	private Integer requisitionOfficeId;
	private Integer issueOfficeId;
	private Integer loggedInOfficeId;
	private String loggedInOfficeName;
	private Integer loggedInUserId;
	private Integer createdByUserId;
	private Integer updatedByUserId;
	
	
	private String address1;
	private String address2;
	private String address3;
	private String pincode;
	
	private String RHOAddress1;
	private String RHOAddress2;
	private String RHOAddress3;
	private String RHOPincode;
	
	private String CORPAddress1;
	private String CORPAddress2;
	private String CORPAddress3;
	private String CORPPincode;
	
	
	
	/** The party type dtls.  IssuedTO Type Dtls in the Screen as Dropdown*/
	private Map<Integer,String> partyTypeDtls;
	
	
	
	
	private Long rowStockIssueItemDtlsId [] = new Long[rowCount];
	
	/** The row rate per unit quantity. */
	private Double rowRatePerUnitQuantity[] = new Double[rowCount];
	
	/** The row total price. */
	private Double rowTotalPrice[] = new Double[rowCount];
	
	
	private String status ;
	private String paymentCash;
	private String paymentDd;
	private String paymentChq;
	
	
	private List<StockIssueItemDtlsTO> issueItemDetls;
	private StockIssuePaymentDetailsTO paymentTO=new StockIssuePaymentDetailsTO();
	
	
	
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

	

	
	public String getLoggedInOfficeName() {
		return loggedInOfficeName;
	}

	public void setLoggedInOfficeName(String loggedInOfficeName) {
		this.loggedInOfficeName = loggedInOfficeName;
	}

	

	public Integer getLoggedInOfficeId() {
		return loggedInOfficeId;
	}

	public void setLoggedInOfficeId(Integer loggedInOfficeId) {
		this.loggedInOfficeId = loggedInOfficeId;
	}

	public Integer getCreatedByUserId() {
		return createdByUserId;
	}

	public void setCreatedByUserId(Integer createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	public Integer getUpdatedByUserId() {
		return updatedByUserId;
	}

	public void setUpdatedByUserId(Integer updatedByUserId) {
		this.updatedByUserId = updatedByUserId;
	}

	

	public String getIssueTimeStr() {
		return issueTimeStr;
	}

	public void setIssueTimeStr(String issueTimeStr) {
		this.issueTimeStr = issueTimeStr;
	}

	
	
	public List<StockIssueItemDtlsTO> getIssueItemDetls() {
		return issueItemDetls;
	}

	public void setIssueItemDetls(List<StockIssueItemDtlsTO> issueItemDetls) {
		this.issueItemDetls = issueItemDetls;
	}

	public Long getStockIssueId() {
		return stockIssueId;
	}

	public void setStockIssueId(Long stockIssueId) {
		this.stockIssueId = stockIssueId;
	}

	

	public String getIssueDateStr() {
		return issueDateStr;
	}

	public void setIssueDateStr(String issueDateStr) {
		this.issueDateStr = issueDateStr;
	}

	
	

	public Integer getRequisitionOfficeId() {
		return requisitionOfficeId;
	}

	public void setRequisitionOfficeId(Integer requisitionOfficeId) {
		this.requisitionOfficeId = requisitionOfficeId;
	}

	public Integer getIssueOfficeId() {
		return issueOfficeId;
	}

	public void setIssueOfficeId(Integer issueOfficeId) {
		this.issueOfficeId = issueOfficeId;
	}

	/**
	 * @return the rowRatePerUnitQuantity
	 */
	public Double[] getRowRatePerUnitQuantity() {
		return rowRatePerUnitQuantity;
	}

	/**
	 * @return the paymentCash
	 */
	public String getPaymentCash() {
		return paymentCash;
	}

	/**
	 * @return the paymentDd
	 */
	public String getPaymentDd() {
		return paymentDd;
	}

	/**
	 * @return the paymentChq
	 */
	public String getPaymentChq() {
		return paymentChq;
	}

	/**
	 * @param paymentCash the paymentCash to set
	 */
	public void setPaymentCash(String paymentCash) {
		this.paymentCash = paymentCash;
	}

	/**
	 * @param paymentDd the paymentDd to set
	 */
	public void setPaymentDd(String paymentDd) {
		this.paymentDd = paymentDd;
	}

	/**
	 * @param paymentChq the paymentChq to set
	 */
	public void setPaymentChq(String paymentChq) {
		this.paymentChq = paymentChq;
	}

	/**
	 * @return the rowTotalPrice
	 */
	public Double[] getRowTotalPrice() {
		return rowTotalPrice;
	}

	/**
	 * @param rowRatePerUnitQuantity the rowRatePerUnitQuantity to set
	 */
	public void setRowRatePerUnitQuantity(Double[] rowRatePerUnitQuantity) {
		this.rowRatePerUnitQuantity = rowRatePerUnitQuantity;
	}

	/**
	 * @param rowTotalPrice the rowTotalPrice to set
	 */
	public void setRowTotalPrice(Double[] rowTotalPrice) {
		this.rowTotalPrice = rowTotalPrice;
	}

	public Integer getLoggedInUserId() {
		return loggedInUserId;
	}

	public void setLoggedInUserId(Integer loggedInUserId) {
		this.loggedInUserId = loggedInUserId;
	}

	public Long[] getRowStockIssueItemDtlsId() {
		return rowStockIssueItemDtlsId;
	}

	public void setRowStockIssueItemDtlsId(Long[] rowStockIssueItemDtlsId) {
		this.rowStockIssueItemDtlsId = rowStockIssueItemDtlsId;
	}

	public String getRecipientCode() {
		return recipientCode;
	}

	public void setRecipientCode(String recipientCode) {
		this.recipientCode = recipientCode;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public Integer getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(Integer recipientId) {
		this.recipientId = recipientId;
	}

	public String getIssuedToType() {
		return issuedToType;
	}

	public void setIssuedToType(String issuedToType) {
		this.issuedToType = issuedToType;
	}

	public StockIssuePaymentDetailsTO getPaymentTO() {
		return paymentTO;
	}

	public void setPaymentTO(StockIssuePaymentDetailsTO paymentTO) {
		this.paymentTO = paymentTO;
	}

	/**
	 * @return the partyTypeDtls
	 */
	public Map<Integer, String> getPartyTypeDtls() {
		return partyTypeDtls;
	}

	/**
	 * @param partyTypeDtls the partyTypeDtls to set
	 */
	public void setPartyTypeDtls(Map<Integer, String> partyTypeDtls) {
		this.partyTypeDtls = partyTypeDtls;
	}

	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @return the address3
	 */
	public String getAddress3() {
		return address3;
	}

	/**
	 * @param address3 the address3 to set
	 */
	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}

	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	/**
	 * @return the rHOAddress1
	 */
	public String getRHOAddress1() {
		return RHOAddress1;
	}

	/**
	 * @param rHOAddress1 the rHOAddress1 to set
	 */
	public void setRHOAddress1(String rHOAddress1) {
		RHOAddress1 = rHOAddress1;
	}

	/**
	 * @return the rHOAddress2
	 */
	public String getRHOAddress2() {
		return RHOAddress2;
	}

	/**
	 * @param rHOAddress2 the rHOAddress2 to set
	 */
	public void setRHOAddress2(String rHOAddress2) {
		RHOAddress2 = rHOAddress2;
	}

	/**
	 * @return the rHOAddress3
	 */
	public String getRHOAddress3() {
		return RHOAddress3;
	}

	/**
	 * @param rHOAddress3 the rHOAddress3 to set
	 */
	public void setRHOAddress3(String rHOAddress3) {
		RHOAddress3 = rHOAddress3;
	}

	/**
	 * @return the rHOPincode
	 */
	public String getRHOPincode() {
		return RHOPincode;
	}

	/**
	 * @param rHOPincode the rHOPincode to set
	 */
	public void setRHOPincode(String rHOPincode) {
		RHOPincode = rHOPincode;
	}

	/**
	 * @return the cORPAddress1
	 */
	public String getCORPAddress1() {
		return CORPAddress1;
	}

	/**
	 * @param cORPAddress1 the cORPAddress1 to set
	 */
	public void setCORPAddress1(String cORPAddress1) {
		CORPAddress1 = cORPAddress1;
	}

	/**
	 * @return the cORPAddress2
	 */
	public String getCORPAddress2() {
		return CORPAddress2;
	}

	/**
	 * @param cORPAddress2 the cORPAddress2 to set
	 */
	public void setCORPAddress2(String cORPAddress2) {
		CORPAddress2 = cORPAddress2;
	}

	/**
	 * @return the cORPAddress3
	 */
	public String getCORPAddress3() {
		return CORPAddress3;
	}

	/**
	 * @param cORPAddress3 the cORPAddress3 to set
	 */
	public void setCORPAddress3(String cORPAddress3) {
		CORPAddress3 = cORPAddress3;
	}

	/**
	 * @return the cORPPincode
	 */
	public String getCORPPincode() {
		return CORPPincode;
	}

	/**
	 * @param cORPPincode the cORPPincode to set
	 */
	public void setCORPPincode(String cORPPincode) {
		CORPPincode = cORPPincode;
	}

	
}
