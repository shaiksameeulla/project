package com.ff.mec.collection;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.business.CustomerTO;
import com.ff.geography.RegionTO;

public class BulkCollectionValidationTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;
	
	private String fromDate;
	private String toDate;
	private List<RegionTO> regionToList;
	private List<CustomerTO> customerToList;
	private String customerName;
	private String customerCode;
	private String collectionDate;
	private String bookingDate;
	private String transactionNo;
	private String consignmentNo;
	private String modeOfPayment;
	private Double amount;
	private String transactionStatus;
	private Integer regionId;
	private Integer customerId;
	private List<BulkCollectionValidationWrapperTO> bulkCollectionDetails;
	int rowCount;
	private String[] isChecked = new String[rowCount];
	private String[] transactionNumbers = new String[rowCount];
	private Integer numberOfPages;
	private Integer currentPageNumber;
	private Integer firstResult;
	private String navigationLabel;
	private Integer numberOfRecordsPerPage;
	private Double totalSelectedAmount;
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public List<RegionTO> getRegionToList() {
		return regionToList;
	}
	public void setRegionToList(List<RegionTO> regionToList) {
		this.regionToList = regionToList;
	}
	public List<CustomerTO> getCustomerToList() {
		return customerToList;
	}
	public void setCustomerToList(List<CustomerTO> customerToList) {
		this.customerToList = customerToList;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCollectionDate() {
		return collectionDate;
	}
	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}
	public String getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	public String getConsignmentNo() {
		return consignmentNo;
	}
	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}
	public String getModeOfPayment() {
		return modeOfPayment;
	}
	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Integer getRegionId() {
		return regionId;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public List<BulkCollectionValidationWrapperTO> getBulkCollectionDetails() {
		return bulkCollectionDetails;
	}
	public void setBulkCollectionDetails(
			List<BulkCollectionValidationWrapperTO> bulkCollectionDetails) {
		this.bulkCollectionDetails = bulkCollectionDetails;
	}
	public String[] getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String[] isChecked) {
		this.isChecked = isChecked;
	}
	public String[] getTransactionNumbers() {
		return transactionNumbers;
	}
	public void setTransactionNumbers(String[] transactionNumbers) {
		this.transactionNumbers = transactionNumbers;
	}
	public Integer getNumberOfPages() {
		return numberOfPages;
	}
	public void setNumberOfPages(Integer numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
	public Integer getCurrentPageNumber() {
		return currentPageNumber;
	}
	public void setCurrentPageNumber(Integer currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}
	public Integer getFirstResult() {
		return firstResult;
	}
	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}
	public String getNavigationLabel() {
		return navigationLabel;
	}
	public void setNavigationLabel(String navigationLabel) {
		this.navigationLabel = navigationLabel;
	}
	public Integer getNumberOfRecordsPerPage() {
		return numberOfRecordsPerPage;
	}
	public void setNumberOfRecordsPerPage(Integer numberOfRecordsPerPage) {
		this.numberOfRecordsPerPage = numberOfRecordsPerPage;
	}
	public Double getTotalSelectedAmount() {
		return totalSelectedAmount;
	}
	public void setTotalSelectedAmount(Double totalSelectedAmount) {
		this.totalSelectedAmount = totalSelectedAmount;
	}
	
}
