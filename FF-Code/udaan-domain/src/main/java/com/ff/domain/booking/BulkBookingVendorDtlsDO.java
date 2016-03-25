/**
 * 
 */
package com.ff.domain.booking;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author nkattung
 * 
 */
public class BulkBookingVendorDtlsDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5992130684771994550L;
	private Integer bulkBookingVendorDtlsId;
	private String vendorName;
	private String vendorPickupLoc;
	private String vendorContactNo;
	private String paymentMethodType;
	private String transactionNo;
	private Integer bookingId;

	/**
	 * @return the bulkBookingVendorDtlsId
	 */
	public Integer getBulkBookingVendorDtlsId() {
		return bulkBookingVendorDtlsId;
	}

	/**
	 * @param bulkBookingVendorDtlsId
	 *            the bulkBookingVendorDtlsId to set
	 */
	public void setBulkBookingVendorDtlsId(Integer bulkBookingVendorDtlsId) {
		this.bulkBookingVendorDtlsId = bulkBookingVendorDtlsId;
	}

	/**
	 * @return the vendorName
	 */
	public String getVendorName() {
		return vendorName;
	}

	/**
	 * @param vendorName
	 *            the vendorName to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	/**
	 * @return the vendorPickupLoc
	 */
	public String getVendorPickupLoc() {
		return vendorPickupLoc;
	}

	/**
	 * @param vendorPickupLoc
	 *            the vendorPickupLoc to set
	 */
	public void setVendorPickupLoc(String vendorPickupLoc) {
		this.vendorPickupLoc = vendorPickupLoc;
	}

	/**
	 * @return the vendorContactNo
	 */
	public String getVendorContactNo() {
		return vendorContactNo;
	}

	/**
	 * @param vendorContactNo
	 *            the vendorContactNo to set
	 */
	public void setVendorContactNo(String vendorContactNo) {
		this.vendorContactNo = vendorContactNo;
	}

	/**
	 * @return the paymentMethodType
	 */
	public String getPaymentMethodType() {
		return paymentMethodType;
	}

	/**
	 * @param paymentMethodType
	 *            the paymentMethodType to set
	 */
	public void setPaymentMethodType(String paymentMethodType) {
		this.paymentMethodType = paymentMethodType;
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
	 * @return the bookingId
	 */
	public Integer getBookingId() {
		return bookingId;
	}

	/**
	 * @param bookingId
	 *            the bookingId to set
	 */
	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

}
