package com.ff.to.ratemanagement.operations.ratebenchmark;

import java.util.Set;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.to.ratemanagement.masters.RateProductCategoryTO;

public class RateBenchMarkProductTO extends CGBaseTO{

	private static final long serialVersionUID = 1L;
	
	private Integer rateBenchMarkProductId;
	private RateProductCategoryTO rateProductCategoryTO;
	private RateBenchMarkHeaderTO rateBenchMarkHeaderTO;
	private Set<RateBenchMarkMatrixHeaderTO> rateBenchMarkMatrixHeaderTO;
	
	public Integer getRateBenchMarkProductId() {
		return rateBenchMarkProductId;
	}
	public void setRateBenchMarkProductId(Integer rateBenchMarkProductId) {
		this.rateBenchMarkProductId = rateBenchMarkProductId;
	}
	public RateProductCategoryTO getRateProductCategoryTO() {
		return rateProductCategoryTO;
	}
	public void setRateProductCategoryTO(RateProductCategoryTO rateProductCategoryTO) {
		this.rateProductCategoryTO = rateProductCategoryTO;
	}
	public RateBenchMarkHeaderTO getRateBenchMarkHeaderTO() {
		return rateBenchMarkHeaderTO;
	}
	public void setRateBenchMarkHeaderTO(RateBenchMarkHeaderTO rateBenchMarkHeaderTO) {
		this.rateBenchMarkHeaderTO = rateBenchMarkHeaderTO;
	}
	public Set<RateBenchMarkMatrixHeaderTO> getRateBenchMarkMatrixHeaderTO() {
		return rateBenchMarkMatrixHeaderTO;
	}
	public void setRateBenchMarkMatrixHeaderTO(
			Set<RateBenchMarkMatrixHeaderTO> rateBenchMarkMatrixHeaderTO) {
		this.rateBenchMarkMatrixHeaderTO = rateBenchMarkMatrixHeaderTO;
	}
	

}
