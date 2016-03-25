package com.ff.complaints;

public class ServiceRequestForConsignmentTO extends ServiceRequestTO {

	private static final long serialVersionUID = 3702916038357221304L;
	
	private String cosnsigNo;
	private String sentTo;
	private String consignor;


	public String getCosnsigNo() {
		return cosnsigNo;
	}

	public void setCosnsigNo(String cosnsigNo) {
		this.cosnsigNo = cosnsigNo;
	}
	/**
	 * @return the sentTo
	 */
	public String getSentTo() {
		return sentTo;
	}

	/**
	 * @param sentTo the sentTo to set
	 */
	public void setSentTo(String sentTo) {
		this.sentTo = sentTo;
	}

	/**
	 * @return the consignor
	 */
	public String getConsignor() {
		return consignor;
	}


}
