package com.ff.domain.ratemanagement.operations.ba;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author hkansagr
 */

public class BcunBAMaterialRateDetailsDO extends CGFactDO {

	private static final long serialVersionUID = 1L;

	/** The baMaterialRateDtlsId */
	private Long baMaterialRateDtlsId;
	
	/** The baMaterialRateConfigDO */
	private BcunBAMaterialRateConfigDO baMaterialRateConfigDO;
	
	/** The itemDO */
	private Integer itemDO;
	
	/** The ratePerUnit */
	private Double ratePerUnit;
	
	/** The rowNumber */
	private Integer rowNumber;

	
	/**
	 * @return the baMaterialRateConfigDO
	 */
	public BcunBAMaterialRateConfigDO getBaMaterialRateConfigDO() {
		return baMaterialRateConfigDO;
	}
	/**
	 * @param baMaterialRateConfigDO the baMaterialRateConfigDO to set
	 */
	public void setBaMaterialRateConfigDO(
			BcunBAMaterialRateConfigDO baMaterialRateConfigDO) {
		this.baMaterialRateConfigDO = baMaterialRateConfigDO;
	}
	/**
	 * @return the itemDO
	 */
	public Integer getItemDO() {
		return itemDO;
	}
	/**
	 * @param itemDO the itemDO to set
	 */
	public void setItemDO(Integer itemDO) {
		this.itemDO = itemDO;
	}
	/**
	 * @return the baMaterialRateDtlsId
	 */
	public Long getBaMaterialRateDtlsId() {
		return baMaterialRateDtlsId;
	}
	/**
	 * @param baMaterialRateDtlsId the baMaterialRateDtlsId to set
	 */
	public void setBaMaterialRateDtlsId(Long baMaterialRateDtlsId) {
		this.baMaterialRateDtlsId = baMaterialRateDtlsId;
	}
	/**
	 * @return the ratePerUnit
	 */
	public Double getRatePerUnit() {
		return ratePerUnit;
	}
	/**
	 * @param ratePerUnit the ratePerUnit to set
	 */
	public void setRatePerUnit(Double ratePerUnit) {
		this.ratePerUnit = ratePerUnit;
	}
	/**
	 * @return the rowNumber
	 */
	public Integer getRowNumber() {
		return rowNumber;
	}
	/**
	 * @param rowNumber the rowNumber to set
	 */
	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

}
