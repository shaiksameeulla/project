package com.ff.to.ratemanagement.masters;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.serviceOfferring.ProductTO;

public class RateProdAndProdCatMapTO extends CGBaseTO{

	private static final long serialVersionUID = 1L;
	
	private Integer rateProdAndProdCatMap;
	private ProductTO productTO;
	private RateProductCategoryTO rateProductCategoryTO;
	
	public Integer getRateProdAndProdCatMap() {
		return rateProdAndProdCatMap;
	}
	public void setRateProdAndProdCatMap(Integer rateProdAndProdCatMap) {
		this.rateProdAndProdCatMap = rateProdAndProdCatMap;
	}
	public ProductTO getProductTO() {
		return productTO;
	}
	public void setProductTO(ProductTO productTO) {
		this.productTO = productTO;
	}
	public RateProductCategoryTO getRateProductCategoryTO() {
		return rateProductCategoryTO;
	}
	public void setRateProductCategoryTO(RateProductCategoryTO rateProductCategoryTO) {
		this.rateProductCategoryTO = rateProductCategoryTO;
	}
	
	
}
