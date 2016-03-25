package com.ff.complaints;


import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.StringUtil;



public class SearchServiceRequestGridTO extends CGBaseTO implements Comparable<SearchServiceRequestGridTO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2571343979205354368L;
	
	
	
	private String linkedServiceReqNo;
	private String serviceRequestNo;
	private String callerPhone;
	private String consignmentNumber;
	private String bookingReferenceNo;
	private String status;
	/**
	 * @return the linkedServiceReqNo
	 */
	public String getLinkedServiceReqNo() {
		return linkedServiceReqNo;
	}
	/**
	 * @return the serviceRequestNo
	 */
	public String getServiceRequestNo() {
		return serviceRequestNo;
	}
	/**
	 * @return the callerPhone
	 */
	public String getCallerPhone() {
		return callerPhone;
	}
	/**
	 * @return the consignmentNumber
	 */
	public String getConsignmentNumber() {
		return consignmentNumber;
	}
	/**
	 * @return the bookingReferenceNo
	 */
	public String getBookingReferenceNo() {
		return bookingReferenceNo;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param linkedServiceReqNo the linkedServiceReqNo to set
	 */
	public void setLinkedServiceReqNo(String linkedServiceReqNo) {
		this.linkedServiceReqNo = linkedServiceReqNo;
	}
	/**
	 * @param serviceRequestNo the serviceRequestNo to set
	 */
	public void setServiceRequestNo(String serviceRequestNo) {
		this.serviceRequestNo = serviceRequestNo;
	}
	/**
	 * @param callerPhone the callerPhone to set
	 */
	public void setCallerPhone(String callerPhone) {
		this.callerPhone = callerPhone;
	}
	/**
	 * @param consignmentNumber the consignmentNumber to set
	 */
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}
	/**
	 * @param bookingReferenceNo the bookingReferenceNo to set
	 */
	public void setBookingReferenceNo(String bookingReferenceNo) {
		this.bookingReferenceNo = bookingReferenceNo;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int compareTo(SearchServiceRequestGridTO arg0) {
		int result=0;
		if(!StringUtil.isStringEmpty(serviceRequestNo) && !StringUtil.isStringEmpty(arg0.getServiceRequestNo())) {
			result = this.serviceRequestNo.compareTo(arg0.serviceRequestNo);
		}
		else if(!StringUtil.isStringEmpty(linkedServiceReqNo) && !StringUtil.isStringEmpty(arg0.getLinkedServiceReqNo())) {
			result = this.linkedServiceReqNo.compareTo(arg0.linkedServiceReqNo);
		}
		return result;
	}
	
}