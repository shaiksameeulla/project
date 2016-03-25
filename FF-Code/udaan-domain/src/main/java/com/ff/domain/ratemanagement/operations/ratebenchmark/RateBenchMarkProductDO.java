package com.ff.domain.ratemanagement.operations.ratebenchmark;

import java.util.Set;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.ratemanagement.masters.RateProductCategoryDO;

public class RateBenchMarkProductDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateBenchMarkProductId;
	private RateProductCategoryDO rateProductCategoryDO;
	private RateBenchMarkHeaderDO rateBenchMarkHeaderDO;
	private Set<RateBenchMarkMatrixHeaderDO> rateBenchMarkMatrixHeaderDO;
	
	public Integer getRateBenchMarkProductId() {
		return rateBenchMarkProductId;
	}
	public void setRateBenchMarkProductId(Integer rateBenchMarkProductId) {
		this.rateBenchMarkProductId = rateBenchMarkProductId;
	}
	public RateProductCategoryDO getRateProductCategoryDO() {
		return rateProductCategoryDO;
	}
	public void setRateProductCategoryDO(RateProductCategoryDO rateProductCategoryDO) {
		this.rateProductCategoryDO = rateProductCategoryDO;
	}
	public RateBenchMarkHeaderDO getRateBenchMarkHeaderDO() {
		return rateBenchMarkHeaderDO;
	}
	public void setRateBenchMarkHeaderDO(RateBenchMarkHeaderDO rateBenchMarkHeaderDO) {
		this.rateBenchMarkHeaderDO = rateBenchMarkHeaderDO;
	}
	public Set<RateBenchMarkMatrixHeaderDO> getRateBenchMarkMatrixHeaderDO() {
		return rateBenchMarkMatrixHeaderDO;
	}
	public void setRateBenchMarkMatrixHeaderDO(
			Set<RateBenchMarkMatrixHeaderDO> rateBenchMarkMatrixHeaderDO) {
		this.rateBenchMarkMatrixHeaderDO = rateBenchMarkMatrixHeaderDO;
	}

}
