package com.ff.domain.pickup;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BcunReversePickupOrderHeaderDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9212544017407661677L;
	private Integer requestHeaderID;
	private Date requestDate;
	private Integer originatingRegion;
	private Integer originatingOffice;
	private Integer deliveryOffice;
	private Integer originatingHub;
	private Integer customer;
	private Integer numOfOrders;
	
	public Integer getRequestHeaderID() {
		return requestHeaderID;
	}
	public void setRequestHeaderID(Integer requestHeaderID) {
		this.requestHeaderID = requestHeaderID;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public Integer getOriginatingRegion() {
		return originatingRegion;
	}
	public void setOriginatingRegion(Integer originatingRegion) {
		this.originatingRegion = originatingRegion;
	}
	public Integer getOriginatingOffice() {
		return originatingOffice;
	}
	public void setOriginatingOffice(Integer originatingOffice) {
		this.originatingOffice = originatingOffice;
	}
	public Integer getDeliveryOffice() {
		return deliveryOffice;
	}
	public void setDeliveryOffice(Integer deliveryOffice) {
		this.deliveryOffice = deliveryOffice;
	}
	public Integer getOriginatingHub() {
		return originatingHub;
	}
	public void setOriginatingHub(Integer originatingHub) {
		this.originatingHub = originatingHub;
	}
	public Integer getCustomer() {
		return customer;
	}
	public void setCustomer(Integer customer) {
		this.customer = customer;
	}
	public Integer getNumOfOrders() {
		return numOfOrders;
	}
	public void setNumOfOrders(Integer numOfOrders) {
		this.numOfOrders = numOfOrders;
	}
}
