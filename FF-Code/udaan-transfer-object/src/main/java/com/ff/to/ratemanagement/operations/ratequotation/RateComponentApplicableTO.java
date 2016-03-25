package com.ff.to.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.RateComponentTO;
/**
 * @author preegupt
 *
 */
public class RateComponentApplicableTO extends CGBaseTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8079061054942458656L;
	private Integer rateComponentApplicableId;
	private RateComponentTO rateComponent;
	private ProductTO product;
	private ConsignmentTypeTO consignmentType;
	/**
	 * @return the rateComponentApplicableId
	 */
	public Integer getRateComponentApplicableId() {
		return rateComponentApplicableId;
	}
	/**
	 * @param rateComponentApplicableId the rateComponentApplicableId to set
	 */
	public void setRateComponentApplicableId(Integer rateComponentApplicableId) {
		this.rateComponentApplicableId = rateComponentApplicableId;
	}
	/**
	 * @return the rateComponent
	 */
	public RateComponentTO getRateComponent() {
		return rateComponent;
	}
	/**
	 * @param rateComponent the rateComponent to set
	 */
	public void setRateComponent(RateComponentTO rateComponent) {
		this.rateComponent = rateComponent;
	}
	/**
	 * @return the product
	 */
	public ProductTO getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(ProductTO product) {
		this.product = product;
	}
	/**
	 * @return the consignmentType
	 */
	public ConsignmentTypeTO getConsignmentType() {
		return consignmentType;
	}
	/**
	 * @param consignmentType the consignmentType to set
	 */
	public void setConsignmentType(ConsignmentTypeTO consignmentType) {
		this.consignmentType = consignmentType;
	}


}
