package com.ff.domain.ratemanagement.operations.ratebenchmark;

import java.util.Set;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.ratemanagement.masters.BcunRateProductCategoryDO;

public class BcunRateBenchMarkProductDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateBenchMarkProductId;
	private BcunRateProductCategoryDO rateProductCategoryDO;
	private BcunRateBenchMarkHeaderDO rateBenchMarkHeaderDO;
	private Set<BcunRateBenchMarkMatrixHeaderDO> rateBenchMarkMatrixHeaderDO;
	
	public Integer getRateBenchMarkProductId() {
		return rateBenchMarkProductId;
	}
	public void setRateBenchMarkProductId(Integer rateBenchMarkProductId) {
		this.rateBenchMarkProductId = rateBenchMarkProductId;
	}
	public Set<BcunRateBenchMarkMatrixHeaderDO> getRateBenchMarkMatrixHeaderDO() {
		return rateBenchMarkMatrixHeaderDO;
	}
	public void setRateBenchMarkMatrixHeaderDO(
			Set<BcunRateBenchMarkMatrixHeaderDO> rateBenchMarkMatrixHeaderDO) {
		this.rateBenchMarkMatrixHeaderDO = rateBenchMarkMatrixHeaderDO;
	}
	public BcunRateProductCategoryDO getRateProductCategoryDO() {
		return rateProductCategoryDO;
	}
	public void setRateProductCategoryDO(BcunRateProductCategoryDO rateProductCategoryDO) {
		this.rateProductCategoryDO = rateProductCategoryDO;
	}
	public BcunRateBenchMarkHeaderDO getRateBenchMarkHeaderDO() {
		return rateBenchMarkHeaderDO;
	}
	public void setRateBenchMarkHeaderDO(BcunRateBenchMarkHeaderDO rateBenchMarkHeaderDO) {
		this.rateBenchMarkHeaderDO = rateBenchMarkHeaderDO;
	}


}
