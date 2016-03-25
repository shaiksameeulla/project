package com.ff.domain.tracking;

import java.io.Serializable;

public class BulkCnTrackDO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6452967151416915919L;
	private Integer trackRowId;
	private String consgNo;
	private String refNo;
	private String bookingDate;
	private String originCity;
	private String destCity;
	private String cnStatus;
	private String pendingReason;
	private String deliveryDate;
	private String receiverName;
	private String cnWeight;
	private String ogmNo;
	private String ogmDate;
	private String bplNo;
	private String bplDate;
	private String cdNo;
	private String cdDate;
	private String flightNo;
	private String flightDep;
	private String flightArr;
	private String rcvDate;
	private String inMnfstDate;
	
	//Enhancement
	//Add DRS No,FS/Third Party Name ,delivery branch name
	private String drsNo;
	private String thirdPartyName;
	private String dlvBranchName;
	
	public Integer getTrackRowId() {
		return trackRowId;
	}
	public void setTrackRowId(Integer trackRowId) {
		this.trackRowId = trackRowId;
	}
	public String getConsgNo() {
		return consgNo;
	}
	public void setConsgNo(String consgNo) {
		this.consgNo = consgNo;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getOriginCity() {
		return originCity;
	}
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	public String getDestCity() {
		return destCity;
	}
	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}
	public String getCnStatus() {
		return cnStatus;
	}
	public void setCnStatus(String cnStatus) {
		this.cnStatus = cnStatus;
	}
	public String getPendingReason() {
		return pendingReason;
	}
	public void setPendingReason(String pendingReason) {
		this.pendingReason = pendingReason;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getCnWeight() {
		return cnWeight;
	}
	public void setCnWeight(String cnWeight) {
		this.cnWeight = cnWeight;
	}
	public String getOgmNo() {
		return ogmNo;
	}
	public void setOgmNo(String ogmNo) {
		this.ogmNo = ogmNo;
	}
	public String getOgmDate() {
		return ogmDate;
	}
	public void setOgmDate(String ogmDate) {
		this.ogmDate = ogmDate;
	}
	public String getBplNo() {
		return bplNo;
	}
	public void setBplNo(String bplNo) {
		this.bplNo = bplNo;
	}
	public String getBplDate() {
		return bplDate;
	}
	public void setBplDate(String bplDate) {
		this.bplDate = bplDate;
	}
	public String getCdNo() {
		return cdNo;
	}
	public void setCdNo(String cdNo) {
		this.cdNo = cdNo;
	}
	public String getCdDate() {
		return cdDate;
	}
	public void setCdDate(String cdDate) {
		this.cdDate = cdDate;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public String getFlightDep() {
		return flightDep;
	}
	public void setFlightDep(String flightDep) {
		this.flightDep = flightDep;
	}
	public String getFlightArr() {
		return flightArr;
	}
	public void setFlightArr(String flightArr) {
		this.flightArr = flightArr;
	}
	public String getRcvDate() {
		return rcvDate;
	}
	public void setRcvDate(String rcvDate) {
		this.rcvDate = rcvDate;
	}
	public String getInMnfstDate() {
		return inMnfstDate;
	}
	public void setInMnfstDate(String inMnfstDate) {
		this.inMnfstDate = inMnfstDate;
	}
	public String getDrsNo() {
		return drsNo;
	}
	public void setDrsNo(String drsNo) {
		this.drsNo = drsNo;
	}
	public String getThirdPartyName() {
		return thirdPartyName;
	}
	public void setThirdPartyName(String thirdPartyName) {
		this.thirdPartyName = thirdPartyName;
	}
	public String getDlvBranchName() {
		return dlvBranchName;
	}
	public void setDlvBranchName(String dlvBranchName) {
		this.dlvBranchName = dlvBranchName;
	}
		
}
