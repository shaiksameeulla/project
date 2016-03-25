package com.ff.domain.ratemanagement.operations.ba;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.stockmanagement.masters.ItemDO;

/**
 * @author hkansagr
 */

public class BAMaterialRateDetailsDO extends CGFactDO {

	private static final long serialVersionUID = 1L;

	/** The baMaterialRateDtlsId */
	private Long baMaterialRateDtlsId;
	
	/** The baMaterialRateConfigDO */
	private BAMaterialRateConfigDO baMaterialRateConfigDO;
	
	/** The itemDO */
	private ItemDO itemDO;
	
	/** The ratePerUnit */
	private Double ratePerUnit;
	
	/** The rowNumber */
	private Integer rowNumber;

	
	/**
	 * @return the baMaterialRateConfigDO
	 */
	public BAMaterialRateConfigDO getBaMaterialRateConfigDO() {
		return baMaterialRateConfigDO;
	}
	/**
	 * @param baMaterialRateConfigDO the baMaterialRateConfigDO to set
	 */
	public void setBaMaterialRateConfigDO(
			BAMaterialRateConfigDO baMaterialRateConfigDO) {
		this.baMaterialRateConfigDO = baMaterialRateConfigDO;
	}
	/**
	 * @return the itemDO
	 */
	public ItemDO getItemDO() {
		return itemDO;
	}
	/**
	 * @param itemDO the itemDO to set
	 */
	public void setItemDO(ItemDO itemDO) {
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
