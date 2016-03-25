package com.ff.booking;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.ff.business.ConsignorConsigneeTO;
import com.ff.consignment.ConsignmentTO;

// TODO: Auto-generated Javadoc
/**
 * The Class CreditCustomerBookingParcelTO.
 */
public class CreditCustomerBookingParcelTO extends BookingParcelTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3105258325604616245L;

	/** The customer id. */
	 private Integer customerId;

	/** The pickup runsheet no. */
	private String pickupRunsheetNo;

	/** The count. */
	private int count;

	/** The customer code. */
	private String[] customerCode = new String[count];

	/** The pickup runsheet nos. */
	private String[] pickupRunsheetNos = new String[count];

	/** The customer ids. */
	private Integer[] customerIds = new Integer[count];

	/** The ref no. */
	private String refNo;

	// For Bulk Bookings
	/** The bulk booking excel. */
	private FormFile bulkBookingExcel;

	/** The consg sticker type. */
	private String consgStickerType;

	/** The error list. */
	private List<List> errorList;

	private String[] lcBankNames = new String[count];
	private Double[] codOrLCAmts = new Double[count];

	private String lcBankName;
	private Double codOrLCAmt;

	/** The cn count. */
	private Integer cnCount;

	/** The valid bookings. */
	private List<CreditCustomerBookingParcelTO> validBookings = new ArrayList();

	/** The invalid bookings. */
	private List<CreditCustomerBookingParcelTO> invalidBookings = new ArrayList();

	/** The cust code. */
	private String custCode;

	/** The start consg no. */
	private String startConsgNo;

	/** The end consg no. */
	private String endConsgNo;

	/** The transactionNo. */
	private String transactionNo;

	private BulkBookingVendorDtlsTO bulkBookingVendorDtlsTO;
	private String orderDate;
	private List<String> errorCodes = new ArrayList();
	
	private ConsignorConsigneeTO consigneeAlternateAddress;
	
	private String consigneeFirstName;
	private String consigneeAddr;
	private String altPincode;
	private List<ConsignmentTO> validConsignments = new ArrayList();
	
	private ConsignorConsigneeTO customerAddress;
	
	
	

	/**
	 * @return the customerAddress
	 */
	public ConsignorConsigneeTO getCustomerAddress() {
		return customerAddress;
	}

	/**
	 * @param customerAddress the customerAddress to set
	 */
	public void setCustomerAddress(ConsignorConsigneeTO customerAddress) {
		this.customerAddress = customerAddress;
	}

	/**
	 * @return the validConsignments
	 */
	public List<ConsignmentTO> getValidConsignments() {
		return validConsignments;
	}

	/**
	 * @param validConsignments the validConsignments to set
	 */
	public void setValidConsignments(List<ConsignmentTO> validConsignments) {
		this.validConsignments = validConsignments;
	}

	/**
	 * Gets the customer id.
	 * 
	 * @return the customer id
	 */
	/*
	 * public Integer getCustomerId() { return customerId; }
	 *//**
	 * Sets the customer id.
	 * 
	 * @param customerId
	 *            the new customer id
	 */
	/*
	 * public void setCustomerId(Integer customerId) { this.customerId =
	 * customerId; }
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.booking.BookingTO#getPickupRunsheetNo()
	 */
	public String getPickupRunsheetNo() {
		return pickupRunsheetNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.booking.BookingTO#setPickupRunsheetNo(java.lang.String)
	 */
	public void setPickupRunsheetNo(String pickupRunsheetNo) {
		this.pickupRunsheetNo = pickupRunsheetNo;
	}

	/**
	 * Gets the customer code.
	 * 
	 * @return the customer code
	 */
	public String[] getCustomerCode() {
		return customerCode;
	}

	/**
	 * Sets the customer code.
	 * 
	 * @param customerCode
	 *            the new customer code
	 */
	public void setCustomerCode(String[] customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * Gets the pickup runsheet nos.
	 * 
	 * @return the pickup runsheet nos
	 */
	public String[] getPickupRunsheetNos() {
		return pickupRunsheetNos;
	}

	/**
	 * Sets the pickup runsheet nos.
	 * 
	 * @param pickupRunsheetNos
	 *            the new pickup runsheet nos
	 */
	public void setPickupRunsheetNos(String[] pickupRunsheetNos) {
		this.pickupRunsheetNos = pickupRunsheetNos;
	}

	/**
	 * Gets the customer ids.
	 * 
	 * @return the customer ids
	 */
	public Integer[] getCustomerIds() {
		return customerIds;
	}

	/**
	 * Sets the customer ids.
	 * 
	 * @param customerIds
	 *            the new customer ids
	 */
	public void setCustomerIds(Integer[] customerIds) {
		this.customerIds = customerIds;
	}

	/**
	 * Gets the ref no.
	 * 
	 * @return the ref no
	 */
	public String getRefNo() {
		return refNo;
	}

	/**
	 * Sets the ref no.
	 * 
	 * @param refNo
	 *            the new ref no
	 */
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.booking.BookingParcelTO#getCount()
	 */
	public int getCount() {
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.booking.BookingParcelTO#setCount(int)
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * Gets the bulk booking excel.
	 * 
	 * @return the bulk booking excel
	 */
	public FormFile getBulkBookingExcel() {
		return bulkBookingExcel;
	}

	/**
	 * Sets the bulk booking excel.
	 * 
	 * @param bulkBookingExcel
	 *            the new bulk booking excel
	 */
	public void setBulkBookingExcel(FormFile bulkBookingExcel) {
		this.bulkBookingExcel = bulkBookingExcel;
	}

	/**
	 * Gets the consg sticker type.
	 * 
	 * @return the consg sticker type
	 */
	public String getConsgStickerType() {
		return consgStickerType;
	}

	/**
	 * Sets the consg sticker type.
	 * 
	 * @param consgStickerType
	 *            the new consg sticker type
	 */
	public void setConsgStickerType(String consgStickerType) {
		this.consgStickerType = consgStickerType;
	}

	/**
	 * Gets the error list.
	 * 
	 * @return the error list
	 */
	public List<List> getErrorList() {
		return errorList;
	}

	/**
	 * Sets the error list.
	 * 
	 * @param errorList
	 *            the new error list
	 */
	public void setErrorList(List<List> errorList) {
		this.errorList = errorList;
	}

	/**
	 * Gets the cn count.
	 * 
	 * @return the cn count
	 */
	public Integer getCnCount() {
		return cnCount;
	}

	/**
	 * Sets the cn count.
	 * 
	 * @param cnCount
	 *            the new cn count
	 */
	public void setCnCount(Integer cnCount) {
		this.cnCount = cnCount;
	}

	/**
	 * Gets the valid bookings.
	 * 
	 * @return the valid bookings
	 */
	public List<CreditCustomerBookingParcelTO> getValidBookings() {
		return validBookings;
	}

	/**
	 * Sets the valid bookings.
	 * 
	 * @param validBookings
	 *            the new valid bookings
	 */
	public void setValidBookings(
			List<CreditCustomerBookingParcelTO> validBookings) {
		this.validBookings = validBookings;
	}

	/**
	 * Gets the invalid bookings.
	 * 
	 * @return the invalid bookings
	 */
	public List<CreditCustomerBookingParcelTO> getInvalidBookings() {
		return invalidBookings;
	}

	/**
	 * Sets the invalid bookings.
	 * 
	 * @param invalidBookings
	 *            the new invalid bookings
	 */
	public void setInvalidBookings(
			List<CreditCustomerBookingParcelTO> invalidBookings) {
		this.invalidBookings = invalidBookings;
	}

	/**
	 * Gets the cust code.
	 * 
	 * @return the cust code
	 */
	public String getCustCode() {
		return custCode;
	}

	/**
	 * Sets the cust code.
	 * 
	 * @param custCode
	 *            the new cust code
	 */
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	/**
	 * @return the startConsgNo
	 */
	public String getStartConsgNo() {
		return startConsgNo;
	}

	/**
	 * @param startConsgNo
	 *            the startConsgNo to set
	 */
	public void setStartConsgNo(String startConsgNo) {
		this.startConsgNo = startConsgNo;
	}

	/**
	 * @return the endConsgNo
	 */
	public String getEndConsgNo() {
		return endConsgNo;
	}

	/**
	 * @param endConsgNo
	 *            the endConsgNo to set
	 */
	public void setEndConsgNo(String endConsgNo) {
		this.endConsgNo = endConsgNo;
	}

	/**
	 * @return the transactionNo
	 */
	public String getTransactionNo() {
		return transactionNo;
	}

	/**
	 * @param transactionNo
	 *            the transactionNo to set
	 */
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	/**
	 * @return the bulkBookingVendorDtlsTO
	 */
	public BulkBookingVendorDtlsTO getBulkBookingVendorDtlsTO() {
		return bulkBookingVendorDtlsTO;
	}

	/**
	 * @param bulkBookingVendorDtlsTO
	 *            the bulkBookingVendorDtlsTO to set
	 */
	public void setBulkBookingVendorDtlsTO(
			BulkBookingVendorDtlsTO bulkBookingVendorDtlsTO) {
		this.bulkBookingVendorDtlsTO = bulkBookingVendorDtlsTO;
	}

	/**
	 * @return the orderDate
	 */
	public String getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate
	 *            the orderDate to set
	 */
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * @return the errorCodes
	 */
	public List<String> getErrorCodes() {
		return errorCodes;
	}

	/**
	 * @param errorCodes
	 *            the errorCodes to set
	 */
	public void setErrorCodes(List<String> errorCodes) {
		this.errorCodes = errorCodes;
	}

	/**
	 * @return the lcBankNames
	 */
	public String[] getLcBankNames() {
		return lcBankNames;
	}

	/**
	 * @param lcBankNames
	 *            the lcBankNames to set
	 */
	public void setLcBankNames(String[] lcBankNames) {
		this.lcBankNames = lcBankNames;
	}

	/**
	 * @return the codOrLCAmts
	 */
	public Double[] getCodOrLCAmts() {
		return codOrLCAmts;
	}

	/**
	 * @param codOrLCAmts
	 *            the codOrLCAmts to set
	 */
	public void setCodOrLCAmts(Double[] codOrLCAmts) {
		this.codOrLCAmts = codOrLCAmts;
	}

	/**
	 * @return the lcBankName
	 */
	public String getLcBankName() {
		return lcBankName;
	}

	/**
	 * @param lcBankName
	 *            the lcBankName to set
	 */
	public void setLcBankName(String lcBankName) {
		this.lcBankName = lcBankName;
	}

	/**
	 * @return the codOrLCAmt
	 */
	public Double getCodOrLCAmt() {
		return codOrLCAmt;
	}

	/**
	 * @param codOrLCAmt
	 *            the codOrLCAmt to set
	 */
	public void setCodOrLCAmt(Double codOrLCAmt) {
		this.codOrLCAmt = codOrLCAmt;
	}

	public ConsignorConsigneeTO getConsigneeAlternateAddress() {
		return consigneeAlternateAddress;
	}

	public void setConsigneeAlternateAddress(
			ConsignorConsigneeTO consigneeAlternateAddress) {
		this.consigneeAlternateAddress = consigneeAlternateAddress;
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
	
	
	
	
}
