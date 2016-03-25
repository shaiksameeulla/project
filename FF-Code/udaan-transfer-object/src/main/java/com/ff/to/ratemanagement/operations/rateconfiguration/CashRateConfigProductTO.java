package com.ff.to.ratemanagement.operations.rateconfiguration;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author hkansagr
 */

public class CashRateConfigProductTO extends CGBaseTO{

	private static final long serialVersionUID = 1L;
	
	private Integer headerProductMapId;//The headerProductMapId
	private Integer headerId;//The headerId
	private Integer productId;//The productId
	private String productCode;//The productCode
	private Integer minChargeableWeight;
	
	private List<CashRateSlabRateTO> slabRateTOList;
	private List<CashRateSpecialDestinationTO> specialDestTOList;
	
	
	
	/**
	 * @return the minChargeableWeight
	 */
	public Integer getMinChargeableWeight() {
		return minChargeableWeight;
	}
	/**
	 * @param minChargeableWeight the minChargeableWeight to set
	 */
	public void setMinChargeableWeight(Integer minChargeableWeight) {
		this.minChargeableWeight = minChargeableWeight;
	}
	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	/**
	 * @return the slabRateTOList
	 */
	public List<CashRateSlabRateTO> getSlabRateTOList() {
		return slabRateTOList;
	}
	/**
	 * @param slabRateTOList the slabRateTOList to set
	 */
	public void setSlabRateTOList(List<CashRateSlabRateTO> slabRateTOList) {
		this.slabRateTOList = slabRateTOList;
	}
	/**
	 * @return the specialDestTOList
	 */
	public List<CashRateSpecialDestinationTO> getSpecialDestTOList() {
		return specialDestTOList;
	}
	/**
	 * @param specialDestTOList the specialDestTOList to set
	 */
	public void setSpecialDestTOList(
			List<CashRateSpecialDestinationTO> specialDestTOList) {
		this.specialDestTOList = specialDestTOList;
	}
	/**
	 * @return the headerProductMapId
	 */
	public Integer getHeaderProductMapId() {
		return headerProductMapId;
	}
	/**
	 * @param headerProductMapId the headerProductMapId to set
	 */
	public void setHeaderProductMapId(Integer headerProductMapId) {
		this.headerProductMapId = headerProductMapId;
	}
	/**
	 * @return the headerId
	 */
	public Integer getHeaderId() {
		return headerId;
	}
	/**
	 * @param headerId the headerId to set
	 */
	public void setHeaderId(Integer headerId) {
		this.headerId = headerId;
	}
	/**
	 * @return the productId
	 */
	public Integer getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
}
