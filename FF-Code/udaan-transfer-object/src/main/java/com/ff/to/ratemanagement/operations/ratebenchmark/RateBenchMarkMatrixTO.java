package com.ff.to.ratemanagement.operations.ratebenchmark;
import com.capgemini.lbs.framework.to.CGBaseTO;


public class RateBenchMarkMatrixTO extends CGBaseTO{

	private static final long serialVersionUID = 1L;
	
	private Integer rateBenchMarkMatrixId;
	/*private SectorTO rateOriginSectorTO;
	private SectorTO rateDestinationSectorTO;
	private WeightSlabTO weightSlabTO;*/
	private Integer rateOriginSector;
	private Integer rateDestinationSector;
	private Integer weightSlab;
	private Double rate;
	private Integer vobId;
	private Integer prodCatId;
	private Integer productId;
	private Integer minChrgWtId;
	private Integer rateBenchMarkMatrixHeaderId;
	private RateBenchMarkMatrixHeaderTO rateBenchMarkMatrixHeaderTO;
	
	public Integer getMinChrgWtId() {
		return minChrgWtId;
	}
	public void setMinChrgWtId(Integer minChrgWtId) {
		this.minChrgWtId = minChrgWtId;
	}
	public Integer getRateBenchMarkMatrixId() {
		return rateBenchMarkMatrixId;
	}
	public void setRateBenchMarkMatrixId(Integer rateBenchMarkMatrixId) {
		this.rateBenchMarkMatrixId = rateBenchMarkMatrixId;
	}
	/*public SectorTO getRateOriginSectorTO() {
		return rateOriginSectorTO;
	}
	public void setRateOriginSectorTO(SectorTO rateOriginSectorTO) {
		this.rateOriginSectorTO = rateOriginSectorTO;
	}
	public SectorTO getRateDestinationSectorTO() {
		return rateDestinationSectorTO;
	}
	public void setRateDestinationSectorTO(SectorTO rateDestinationSectorTO) {
		this.rateDestinationSectorTO = rateDestinationSectorTO;
	}
	public WeightSlabTO getWeightSlabTO() {
		return weightSlabTO;
	}
	public void setWeightSlabTO(WeightSlabTO weightSlabTO) {
		this.weightSlabTO = weightSlabTO;
	}*/
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getVobId() {
		return vobId;
	}
	public void setVobId(Integer vobId) {
		this.vobId = vobId;
	}
	public Integer getProdCatId() {
		return prodCatId;
	}
	public void setProdCatId(Integer prodCatId) {
		this.prodCatId = prodCatId;
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
	
	public RateBenchMarkMatrixHeaderTO getRateBenchMarkMatrixHeaderTO() {
		return rateBenchMarkMatrixHeaderTO;
	}
	public void setRateBenchMarkMatrixHeaderTO(
			RateBenchMarkMatrixHeaderTO rateBenchMarkMatrixHeaderTO) {
		this.rateBenchMarkMatrixHeaderTO = rateBenchMarkMatrixHeaderTO;
	}
	public Integer getRateBenchMarkMatrixHeaderId() {
		return rateBenchMarkMatrixHeaderId;
	}
	public void setRateBenchMarkMatrixHeaderId(Integer rateBenchMarkMatrixHeaderId) {
		this.rateBenchMarkMatrixHeaderId = rateBenchMarkMatrixHeaderId;
	}
	
}
