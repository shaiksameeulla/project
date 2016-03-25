package com.ff.domain.ratemanagement.operations.ratebenchmark;

import java.util.Set;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class RateBenchMarkMatrixHeaderDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateBenchMarkMatrixHeaderId;
	private Integer rateMinChargeableWeight;
	private Integer vobSlab;
	private RateBenchMarkProductDO rateBenchMarkProductDO;
	private Set<RateBenchMarkMatrixDO> rateBenchMarkMatrixDO;
	
	public Integer getRateBenchMarkMatrixHeaderId() {
		return rateBenchMarkMatrixHeaderId;
	}
	public void setRateBenchMarkMatrixHeaderId(Integer rateBenchMarkMatrixHeaderId) {
		this.rateBenchMarkMatrixHeaderId = rateBenchMarkMatrixHeaderId;
	}
	/*public RateMinChargeableWeightDO getRateMinChargeableWeightDO() {
		return rateMinChargeableWeightDO;
	}
	public void setRateMinChargeableWeightDO(
			RateMinChargeableWeightDO rateMinChargeableWeightDO) {
		this.rateMinChargeableWeightDO = rateMinChargeableWeightDO;
	}
	public VobSlabDO getVobSlabDO() {
		return vobSlabDO;
	}
	public void setVobSlabDO(VobSlabDO vobSlabDO) {
		this.vobSlabDO = vobSlabDO;
	}*/
	
	public RateBenchMarkProductDO getRateBenchMarkProductDO() {
		return rateBenchMarkProductDO;
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
	public void setRateBenchMarkProductDO(
			RateBenchMarkProductDO rateBenchMarkProductDO) {
		this.rateBenchMarkProductDO = rateBenchMarkProductDO;
	}
	public Set<RateBenchMarkMatrixDO> getRateBenchMarkMatrixDO() {
		return rateBenchMarkMatrixDO;
	}
	public void setRateBenchMarkMatrixDO(
			Set<RateBenchMarkMatrixDO> rateBenchMarkMatrixDO) {
		this.rateBenchMarkMatrixDO = rateBenchMarkMatrixDO;
	}

}
