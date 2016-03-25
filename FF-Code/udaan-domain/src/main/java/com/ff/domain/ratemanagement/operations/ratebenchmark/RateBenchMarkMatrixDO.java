package com.ff.domain.ratemanagement.operations.ratebenchmark;

import com.capgemini.lbs.framework.domain.CGMasterDO;


public class RateBenchMarkMatrixDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateBenchMarkMatrixId;
	private Integer rateOriginSector;
	private Integer rateDestinationSector;
	private Integer weightSlab;
	private Double rate;
	private RateBenchMarkMatrixHeaderDO rateBenchMarkMatrixHeaderDO;
	
	public Integer getRateBenchMarkMatrixId() {
		return rateBenchMarkMatrixId;
	}
	public void setRateBenchMarkMatrixId(Integer rateBenchMarkMatrixId) {
		this.rateBenchMarkMatrixId = rateBenchMarkMatrixId;
	}
	/*public SectorDO getRateOriginSectorDO() {
		return rateOriginSectorDO;
	}
	public void setRateOriginSectorDO(SectorDO rateOriginSectorDO) {
		this.rateOriginSectorDO = rateOriginSectorDO;
	}
	public SectorDO getRateDestinationSectorDO() {
		return rateDestinationSectorDO;
	}
	public void setRateDestinationSectorDO(SectorDO rateDestinationSectorDO) {
		this.rateDestinationSectorDO = rateDestinationSectorDO;
	}
	public WeightSlabDO getWeightSlabDO() {
		return weightSlabDO;
	}
	public void setWeightSlabDO(WeightSlabDO weightSlabDO) {
		this.weightSlabDO = weightSlabDO;
	}*/
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public RateBenchMarkMatrixHeaderDO getRateBenchMarkMatrixHeaderDO() {
		return rateBenchMarkMatrixHeaderDO;
	}
	public Integer getRateOriginSector() {
		return rateOriginSector;
	}
	public void setRateOriginSector(Integer rateOriginSector) {
		this.rateOriginSector = rateOriginSector;
	}
	public Integer getRateDestinationSector() {
		return rateDestinationSector;
	}
	public void setRateDestinationSector(Integer rateDestinationSector) {
		this.rateDestinationSector = rateDestinationSector;
	}
	public Integer getWeightSlab() {
		return weightSlab;
	}
	public void setWeightSlab(Integer weightSlab) {
		this.weightSlab = weightSlab;
	}
	public void setRateBenchMarkMatrixHeaderDO(
			RateBenchMarkMatrixHeaderDO rateBenchMarkMatrixHeaderDO) {
		this.rateBenchMarkMatrixHeaderDO = rateBenchMarkMatrixHeaderDO;
	}
	
}
