package com.ff.to.ratemanagement.operations.ratebenchmark;

import java.util.Set;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class RateBenchMarkMatrixHeaderTO extends CGBaseTO{

	private static final long serialVersionUID = 1L;
	
	private Integer rateBenchMarkMatrixHeaderId;
	private Integer rateMinChargeableWeight;
	private Integer vobSlab;
	private RateBenchMarkProductTO rateBenchMarkProductTO;
	private Set<RateBenchMarkMatrixTO> rateBenchMarkMatrixTO;
	
	public Integer getRateBenchMarkMatrixHeaderId() {
		return rateBenchMarkMatrixHeaderId;
	}
	public void setRateBenchMarkMatrixHeaderId(Integer rateBenchMarkMatrixHeaderId) {
		this.rateBenchMarkMatrixHeaderId = rateBenchMarkMatrixHeaderId;
	}
	/*public RateMinChargeableWeightTO getRateMinChargeableWeightTO() {
		return rateMinChargeableWeightTO;
	}
	public void setRateMinChargeableWeightTO(
			RateMinChargeableWeightTO rateMinChargeableWeightTO) {
		this.rateMinChargeableWeightTO = rateMinChargeableWeightTO;
	}
	public VobSlabTO getVobSlabTO() {
		return vobSlabTO;
	}
	public void setVobSlabTO(VobSlabTO vobSlabTO) {
		this.vobSlabTO = vobSlabTO;
	}*/
	
	public RateBenchMarkProductTO getRateBenchMarkProductTO() {
		return rateBenchMarkProductTO;
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
	public void setRateBenchMarkProductTO(
			RateBenchMarkProductTO rateBenchMarkProductTO) {
		this.rateBenchMarkProductTO = rateBenchMarkProductTO;
	}
	public Set<RateBenchMarkMatrixTO> getRateBenchMarkMatrixTO() {
		return rateBenchMarkMatrixTO;
	}
	public void setRateBenchMarkMatrixTO(
			Set<RateBenchMarkMatrixTO> rateBenchMarkMatrixTO) {
		this.rateBenchMarkMatrixTO = rateBenchMarkMatrixTO;
	}
	
}
