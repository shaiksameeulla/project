/**
 * 
 */
package com.ff.booking;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class BulkBookingTO.
 *
 * @author nkattung
 */
public class BulkBookingTO extends CGBaseTO{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2654721317979741599L;
	
	/** The bulk booking excel. */
	private FormFile bulkBookingExcel;
	
	/** The consg sticker type. */
	private String consgStickerType;
	
	/** The product code. */
	private String productCode;
	
	/** The product id. */
	private Integer productId;
	
	/** The start consg no. */
	private String startConsgNo;
	
	/** The end consg no. */
	private String endConsgNo;
	
	/** The customer id. */
	private Integer customerId;
	
	/** The cust code. */
	private String custCode;
	
	/** The file upload. */
	private FormFile fileUpload;
	
	/** The error list. */
	List<List> errorList;
	
	/** The state. */
	private String state;
	
	/** The country. */
	private String country;
	
	/** The transaction no. */
	private String transactionNo;
	
	/** The quantity. */
	private Integer quantity;
	
	/** The vendor. */
	private String vendor;
	
	/** The vendor location. */
	private String vendorLocation;
	
	/** The vendor num. */
	private String vendorNum;
	
	/** The payment method. */
	private String paymentMethod;
	
	/** The order date. */
	private String orderDate;
	
	/** The cn count. */
	private Integer cnCount;

	/** The valid bookings. */
	private List<BulkBookingTO> validBookings = new ArrayList();
	
	/** The invalid bookings. */
	private List<BulkBookingTO> invalidBookings = new ArrayList();

	/**
	 * Gets the valid bookings.
	 *
	 * @return the valid bookings
	 */
	public List<BulkBookingTO> getValidBookings() {
		return validBookings;
	}

	/**
	 * Sets the valid bookings.
	 *
	 * @param validBookings the new valid bookings
	 */
	public void setValidBookings(List<BulkBookingTO> validBookings) {
		this.validBookings = validBookings;
	}

	/**
	 * Gets the invalid bookings.
	 *
	 * @return the invalid bookings
	 */
	public List<BulkBookingTO> getInvalidBookings() {
		return invalidBookings;
	}

	/**
	 * Sets the invalid bookings.
	 *
	 * @param invalidBookings the new invalid bookings
	 */
	public void setInvalidBookings(List<BulkBookingTO> invalidBookings) {
		this.invalidBookings = invalidBookings;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the transaction no.
	 *
	 * @return the transactionNo
	 */
	public String getTransactionNo() {
		return transactionNo;
	}

	/**
	 * Sets the transaction no.
	 *
	 * @param transactionNo the transactionNo to set
	 */
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity.
	 *
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets the vendor.
	 *
	 * @return the vendor
	 */
	public String getVendor() {
		return vendor;
	}

	/**
	 * Sets the vendor.
	 *
	 * @param vendor the vendor to set
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	/**
	 * Gets the vendor location.
	 *
	 * @return the vendorLocation
	 */
	public String getVendorLocation() {
		return vendorLocation;
	}

	/**
	 * Sets the vendor location.
	 *
	 * @param vendorLocation the vendorLocation to set
	 */
	public void setVendorLocation(String vendorLocation) {
		this.vendorLocation = vendorLocation;
	}

	/**
	 * Gets the vendor num.
	 *
	 * @return the vendorNum
	 */
	public String getVendorNum() {
		return vendorNum;
	}

	/**
	 * Sets the vendor num.
	 *
	 * @param vendorNum the vendorNum to set
	 */
	public void setVendorNum(String vendorNum) {
		this.vendorNum = vendorNum;
	}

	/**
	 * Gets the payment method.
	 *
	 * @return the paymentMethod
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * Sets the payment method.
	 *
	 * @param paymentMethod the paymentMethod to set
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * Gets the order date.
	 *
	 * @return the orderDate
	 */
	public String getOrderDate() {
		return orderDate;
	}

	/**
	 * Sets the order date.
	 *
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * Gets the error list.
	 *
	 * @return the errorList
	 */
	public List<List> getErrorList() {
		return errorList;
	}

	/**
	 * Sets the error list.
	 *
	 * @param errorList the errorList to set
	 */
	public void setErrorList(List<List> errorList) {
		this.errorList = errorList;
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
	 * @param bulkBookingExcel the new bulk booking excel
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
	 * @param consgStickerType the new consg sticker type
	 */
	public void setConsgStickerType(String consgStickerType) {
		this.consgStickerType = consgStickerType;
	}

	/**
	 * Gets the product code.
	 *
	 * @return the product code
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * Sets the product code.
	 *
	 * @param productCode the new product code
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * Gets the product id.
	 *
	 * @return the product id
	 */
	public Integer getProductId() {
		return productId;
	}

	/**
	 * Sets the product id.
	 *
	 * @param productId the new product id
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	/**
	 * Gets the start consg no.
	 *
	 * @return the start consg no
	 */
	public String getStartConsgNo() {
		return startConsgNo;
	}

	/**
	 * Sets the start consg no.
	 *
	 * @param startConsgNo the new start consg no
	 */
	public void setStartConsgNo(String startConsgNo) {
		this.startConsgNo = startConsgNo;
	}

	/**
	 * Gets the end consg no.
	 *
	 * @return the end consg no
	 */
	public String getEndConsgNo() {
		return endConsgNo;
	}

	/**
	 * Sets the end consg no.
	 *
	 * @param endConsgNo the new end consg no
	 */
	public void setEndConsgNo(String endConsgNo) {
		this.endConsgNo = endConsgNo;
	}

	/**
	 * Gets the customer id.
	 *
	 * @return the customer id
	 */
	public Integer getCustomerId() {
		return customerId;
	}

	/**
	 * Sets the customer id.
	 *
	 * @param customerId the new customer id
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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
	 * @param custCode the new cust code
	 */
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	/**
	 * Gets the file upload.
	 *
	 * @return the fileUpload
	 */
	public FormFile getFileUpload() {
		return fileUpload;
	}

	/**
	 * Sets the file upload.
	 *
	 * @param fileUpload the fileUpload to set
	 */
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}

	/**
	 * Gets the cn count.
	 *
	 * @return the cnCount
	 */
	public Integer getCnCount() {
		return cnCount;
	}

	/**
	 * Sets the cn count.
	 *
	 * @param cnCount the cnCount to set
	 */
	public void setCnCount(Integer cnCount) {
		this.cnCount = cnCount;
	}

}
