package com.ff.domain.ratemanagement.operations.ratebenchmark;

import com.capgemini.lbs.framework.domain.CGMasterDO;


public class BcunRateBenchMarkMatrixDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateBenchMarkMatrixId;
	private Integer rateOriginSector;
	private Integer rateDestinationSector;
	private Integer weightSlab;
	private Double rate;
	private Integer rateBenchMarkMatrixHeaderId;
	
	public Integer getRateBenchMarkMatrixId() {
		return rateBenchMarkMatrixId;
	}
	public void setRateBenchMarkMatrixId(Integer rateBenchMarkMatrixId) {
		this.rateBenchMarkMatrixId = rateBenchMarkMatrixId;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
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
	/**
	 * @return the rateBenchMarkMatrixHeaderId
	 */
	public Integer getRateBenchMarkMatrixHeaderId() {
		return rateBenchMarkMatrixHeaderId;
	}
	/**
	 * @param rateBenchMarkMatrixHeaderId the rateBenchMarkMatrixHeaderId to set
	 */
	public void setRateBenchMarkMatrixHeaderId(Integer rateBenchMarkMatrixHeaderId) {
		this.rateBenchMarkMatrixHeaderId = rateBenchMarkMatrixHeaderId;
	}
	
	
}
