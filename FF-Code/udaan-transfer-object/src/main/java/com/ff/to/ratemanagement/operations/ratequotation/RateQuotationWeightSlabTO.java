package com.ff.to.ratemanagement.operations.ratequotation;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.to.ratemanagement.masters.WeightSlabTO;

public class RateQuotationWeightSlabTO extends CGBaseTO implements Comparable<RateQuotationWeightSlabTO>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer rateQuotationWeightSlabId;
	private WeightSlabTO startWeightTO;
	private WeightSlabTO endWeightTO;
	//private RateQuotationProductCategoryHeaderTO rateQuotationProductCategoryHeader;
	private List<RateQuotationSlabRateTO> slabRateTO;
	private List<RateQuotationSpecialDestinationTO> splDestRateTO;
	private Integer order;
	private String rateConfiguredType;
	/**
	 * @return the rateQuotationWeightSlabId
	 */
	public Integer getRateQuotationWeightSlabId() {
		return rateQuotationWeightSlabId;
	}
	/**
	 * @return the order
	 */
	public Integer getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}
	/**
	 * @return the slabRateTO
	 */
	public List<RateQuotationSlabRateTO> getSlabRateTO() {
		return slabRateTO;
	}
	/**
	 * @param slabRateTO the slabRateTO to set
	 */
	public void setSlabRateTO(List<RateQuotationSlabRateTO> slabRateTO) {
		this.slabRateTO = slabRateTO;
	}
	/**
	 * @return the splDestRateTO
	 */
	public List<RateQuotationSpecialDestinationTO> getSplDestRateTO() {
		return splDestRateTO;
	}
	/**
	 * @param splDestRateTO the splDestRateTO to set
	 */
	public void setSplDestRateTO(
			List<RateQuotationSpecialDestinationTO> splDestRateTO) {
		this.splDestRateTO = splDestRateTO;
	}
	/**
	 * @param rateQuotationWeightSlabId the rateQuotationWeightSlabId to set
	 */
	public void setRateQuotationWeightSlabId(Integer rateQuotationWeightSlabId) {
		this.rateQuotationWeightSlabId = rateQuotationWeightSlabId;
	}
	/**
	 * @return the startWeight
	 */
	public WeightSlabTO getStartWeightTO() {
		return startWeightTO;
	}
	/**
	 * @param startWeight the startWeight to set
	 */
	public void setStartWeightTO(WeightSlabTO startWeight) {
		this.startWeightTO = startWeight;
	}
	/**
	 * @return the endWeight
	 */
	public WeightSlabTO getEndWeightTO() {
		return endWeightTO;
	}
	/**
	 * @param endWeight the endWeight to set
	 */
	public void setEndWeightTO(WeightSlabTO endWeight) {
		this.endWeightTO = endWeight;
	}
	/**
	 * @return the rateQuotationProductCategoryHeader
	 */
	/*public RateQuotationProductCategoryHeaderTO getRateQuotationProductCategoryHeader() {
		return rateQuotationProductCategoryHeader;
	}
	*//**
	 * @param rateQuotationProductCategoryHeader the rateQuotationProductCategoryHeader to set
	 *//*
	public void setRateQuotationProductCategoryHeader(
			RateQuotationProductCategoryHeaderTO rateQuotationProductCategoryHeader) {
		this.rateQuotationProductCategoryHeader = rateQuotationProductCategoryHeader;
	}*/

	@Override
	public int compareTo(RateQuotationWeightSlabTO obj) {
		int result = 0;
		if(!StringUtil.isEmptyInteger(order) 
				&& !StringUtil.isEmptyInteger(obj.getOrder())){
			result = this.order.compareTo(obj.order);
		} 
		return result;
	}
	public String getRateConfiguredType() {
		return rateConfiguredType;
	}
	public void setRateConfiguredType(String rateConfiguredType) {
		this.rateConfiguredType = rateConfiguredType;
	}
	
}
