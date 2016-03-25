package com.ff.domain.ratemanagement.operations.ratebenchmark;

import java.util.Set;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunRateBenchMarkMatrixHeaderDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateBenchMarkMatrixHeaderId;
	private Integer rateMinChargeableWeight;
	private Integer vobSlab;
	private BcunRateBenchMarkProductDO rateBenchMarkProductDO;
	private Set<BcunRateBenchMarkMatrixDO> rateBenchMarkMatrixDO;
	
	public Integer getRateBenchMarkMatrixHeaderId() {
		return rateBenchMarkMatrixHeaderId;
	}
	public void setRateBenchMarkMatrixHeaderId(Integer rateBenchMarkMatrixHeaderId) {
		this.rateBenchMarkMatrixHeaderId = rateBenchMarkMatrixHeaderId;
	}
	public Integer getRateMinChargeableWeight() {
		return rateMinChargeableWeight;
	}
	public void setRateMinChargeableWeight(Integer rateMinChargeableWeight) {
		this.rateMinChargeableWeight = rateMinChargeableWeight;
	}
	public Integer getVobSlab() {
		return vobSlab;
	}
	public void setVobSlab(Integer vobSlab) {
		this.vobSlab = vobSlab;
	}
	public Set<BcunRateBenchMarkMatrixDO> getRateBenchMarkMatrixDO() {
		return rateBenchMarkMatrixDO;
	}
	public void setRateBenchMarkMatrixDO(
			Set<BcunRateBenchMarkMatrixDO> rateBenchMarkMatrixDO) {
		this.rateBenchMarkMatrixDO = rateBenchMarkMatrixDO;
	}
	public BcunRateBenchMarkProductDO getRateBenchMarkProductDO() {
		return rateBenchMarkProductDO;
	}
	public void setRateBenchMarkProductDO(
			BcunRateBenchMarkProductDO rateBenchMarkProductDO) {
		this.rateBenchMarkProductDO = rateBenchMarkProductDO;
	}


}
