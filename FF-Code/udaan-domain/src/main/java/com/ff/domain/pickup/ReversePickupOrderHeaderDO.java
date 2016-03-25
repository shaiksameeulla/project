package com.ff.domain.pickup;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class ReversePickupOrderHeaderDO extends CGFactDO
{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3083816854476310324L;
	private Integer requestHeaderID;
	  private Date requestDate;
	  private Integer originatingRegion;
	  private Integer originatingOffice;
	  private Integer deliveryOffice;
	  private Integer originatingHub; 
	  private Integer customer;
	  private Integer numOfOrders;
	  private String dtUpdateToCentral;
	  @JsonManagedReference private Set<ReversePickupOrderDetailDO> detailsDO;
	  
	
	  
  /**
	 * @return the dtUpdateToCentral
	 */
	public String getDtUpdateToCentral() {
		return dtUpdateToCentral;
	}

	/**
	 * @param dtUpdateToCentral the dtUpdateToCentral to set
	 */
	public void setDtUpdateToCentral(String dtUpdateToCentral) {
		this.dtUpdateToCentral = dtUpdateToCentral;
	}

/**
	 * @return the requestHeaderID
	 */
	public Integer getRequestHeaderID() {
		return requestHeaderID;
	}
	
	/**
	 * @param requestHeaderID the requestHeaderID to set
	 */
	public void setRequestHeaderID(Integer requestHeaderID) {
		this.requestHeaderID = requestHeaderID;
	}
	/**
	 * @return the requestDate
	 */
	
	
	/**
	 * @return the customer
	 */
	public Integer getCustomer() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Integer customer) {
		this.customer = customer;
	}
	/**
	 * @return the numOfOrders
	 */
	public Integer getNumOfOrders() {
		return numOfOrders;
	}
	/**
	 * @param numOfOrders the numOfOrders to set
	 */
	public void setNumOfOrders(Integer numOfOrders) {
		this.numOfOrders = numOfOrders;
	}
	/**
	 * @return the originatingHub
	 */
	public Integer getOriginatingHub() {
		return originatingHub;
	}
	/**
	 * @param originatingHub the originatingHub to set
	 */
	public void setOriginatingHub(Integer originatingHub) {
		this.originatingHub = originatingHub;
	}
	/**
	 * @return the originatingRegion
	 */
	public Integer getOriginatingRegion() {
		return originatingRegion;
	}
	/**
	 * @param originatingRegion the originatingRegion to set
	 */
	public void setOriginatingRegion( Integer originatingRegion) {
		this.originatingRegion = originatingRegion;
	}
	/**
	 * @return the detailsDO
	 */
	public Set<ReversePickupOrderDetailDO> getDetailsDO() {
		return detailsDO;
	}
	/**
	 * @param detailsDO the detailsDO to set
	 */
	public void setDetailsDO(Set<ReversePickupOrderDetailDO> detailsDO) {
		this.detailsDO = detailsDO;
	}
	/**
	 * @return the requestDate
	 */
	public Date getRequestDate() {
		return requestDate;
	}
	/**
	 * @param requestDate the requestDate to set
	 */
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	/**
	 * @return the originatingOffice
	 */
	public Integer getOriginatingOffice() {
		return originatingOffice;
	}

	/**
	 * @param originatingOffice the originatingOffice to set
	 */
	public void setOriginatingOffice(Integer originatingOffice) {
		this.originatingOffice = originatingOffice;
	}

	/**
	 * @return the deliveryOffice
	 */
	public Integer getDeliveryOffice() {
		return deliveryOffice;
	}

	/**
	 * @param deliveryOffice the deliveryOffice to set
	 */
	public void setDeliveryOffice(Integer deliveryOffice) {
		this.deliveryOffice = deliveryOffice;
	}
	
	

}
