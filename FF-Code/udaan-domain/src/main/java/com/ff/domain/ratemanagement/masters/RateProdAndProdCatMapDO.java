package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.serviceOffering.ProductDO;

public class RateProdAndProdCatMapDO extends CGFactDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateProdAndProdCatMap;
	private ProductDO productDO;
	private RateProductCategoryDO rateProductCategoryDO;
	
	public Integer getRateProdAndProdCatMap() {
		return rateProdAndProdCatMap;
	}
	public void setRateProdAndProdCatMap(Integer rateProdAndProdCatMap) {
		this.rateProdAndProdCatMap = rateProdAndProdCatMap;
	}
	public ProductDO getProductDO() {
		return productDO;
	}
	public void setProductDO(ProductDO productDO) {
		this.productDO = productDO;
	}
	public RateProductCategoryDO getRateProductCategoryDO() {
		return rateProductCategoryDO;
	}
	public void setRateProductCategoryDO(RateProductCategoryDO rateProductCategoryDO) {
		this.rateProductCategoryDO = rateProductCategoryDO;
	}
}
