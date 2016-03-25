package com.ff.to.ratemanagement.operations.ratequotation;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.to.ratemanagement.masters.RateProductCategoryTO;
/**
 * @author preegupt
 *
 */
public class RateQuotationProductCategoryHeaderTO extends CGBaseTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6007830768130903639L;
	
	private Integer rateQuotationProductCategoryHeaderId;
	private String extendedFromPreviousQuotation;
	private RateProductCategoryTO rateProductCategoryTO;
	private Integer vobSlab;
	private Integer rateMinChargeableWeight;
	private RateQuotationTO quotationId;
	private List<RateQuotationWeightSlabTO> weightSlabTO;
	private String flatRate;
	/**
	 * @return the rateMinChargeableWeight
	 */
	public Integer getRateMinChargeableWeight() {
		return rateMinChargeableWeight;
	}
	/**
	 * @return the rateProductCategoryTO
	 */
	public RateProductCategoryTO getRateProductCategoryTO() {
		return rateProductCategoryTO;
	}
	/**
	 * @param rateProductCategoryTO the rateProductCategoryTO to set
	 */
	public void setRateProductCategoryTO(RateProductCategoryTO rateProductCategoryTO) {
		this.rateProductCategoryTO = rateProductCategoryTO;
	}
	/**
	 * @return the weightSlabTO
	 */
	public List<RateQuotationWeightSlabTO> getWeightSlabTO() {
		return weightSlabTO;
	}
	/**
	 * @param weightSlabTO the weightSlabTO to set
	 */
	public void setWeightSlabTO(List<RateQuotationWeightSlabTO> weightSlabTO) {
		this.weightSlabTO = weightSlabTO;
	}
	/**
	 * @param rateMinChargeableWeight the rateMinChargeableWeight to set
	 */
	public void setRateMinChargeableWeight(Integer rateMinChargeableWeight) {
		this.rateMinChargeableWeight = rateMinChargeableWeight;
	}
	/**
	 * @return the rateQuotationProductCategoryHeaderId
	 */
	public Integer getRateQuotationProductCategoryHeaderId() {
		return rateQuotationProductCategoryHeaderId;
	}
	/**
	 * @param rateQuotationProductCategoryHeaderId the rateQuotationProductCategoryHeaderId to set
	 */
	public void setRateQuotationProductCategoryHeaderId(
			Integer rateQuotationProductCategoryHeaderId) {
		this.rateQuotationProductCategoryHeaderId = rateQuotationProductCategoryHeaderId;
	}
	/**
	 * @return the extendedFromPreviousQuotation
	 */
	public String getExtendedFromPreviousQuotation() {
		return extendedFromPreviousQuotation;
	}
	/**
	 * @param extendedFromPreviousQuotation the extendedFromPreviousQuotation to set
	 */
	public void setExtendedFromPreviousQuotation(
			String extendedFromPreviousQuotation) {
		this.extendedFromPreviousQuotation = extendedFromPreviousQuotation;
	}
	
	/**
	 * @return the vobSlab
	 */
	public Integer getVobSlab() {
		return vobSlab;
	}
	/**
	 * @param vobSlab the vobSlab to set
	 */
	public void setVobSlab(Integer vobSlab) {
		this.vobSlab = vobSlab;
	}
	
	public RateQuotationTO getQuotationId() {
		return quotationId;
	}
	public void setQuotationId(RateQuotationTO quotationId) {
		this.quotationId = quotationId;
	}
	public String getFlatRate() {
		return flatRate;
	}
	public void setFlatRate(String flatRate) {
		this.flatRate = flatRate;
	}
	

}
