package com.ff.to.ratemanagement.operations.rateconfiguration;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.to.stockmanagement.masters.ItemTO;

/**
 * @author hkansagr
 */

public class BAMaterialRateDetailsTO extends CGBaseTO implements Comparable<BAMaterialRateDetailsTO>{

	private static final long serialVersionUID = 1L;

	/** The baMaterialRateDtlsId */
	private Long baMaterialRateDtlsId;
	
	/** The baMaterialRateConfigId */
	private Long baMaterialRateConfigId;
	
	/** The itemTO */
	private ItemTO itemTO;
	
	/** The ratePerUnit */
	private Double ratePerUnit;
	
	/** The rowNumber */
	private Integer rowNumber;
	
	
	/**
	 * @return the baMaterialRateConfigId
	 */
	public Long getBaMaterialRateConfigId() {
		return baMaterialRateConfigId;
	}
	/**
	 * @param baMaterialRateConfigId the baMaterialRateConfigId to set
	 */
	public void setBaMaterialRateConfigId(Long baMaterialRateConfigId) {
		this.baMaterialRateConfigId = baMaterialRateConfigId;
	}
	/**
	 * @return the itemTO
	 */
	public ItemTO getItemTO() {
		return itemTO;
	}
	/**
	 * @param itemTO the itemTO to set
	 */
	public void setItemTO(ItemTO itemTO) {
		this.itemTO = itemTO;
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

	@Override
	public int compareTo(BAMaterialRateDetailsTO arg0) {
		int result = 0;
		if(!StringUtil.isEmptyInteger(rowNumber) 
				&& !StringUtil.isEmptyInteger(arg0.getRowNumber())){
			result = this.rowNumber.compareTo(arg0.rowNumber) ;
		} else if(!StringUtil.isEmptyLong(baMaterialRateDtlsId) 
				&& !StringUtil.isEmptyLong(arg0.getBaMaterialRateDtlsId())){
			result = this.baMaterialRateDtlsId.compareTo(arg0.baMaterialRateDtlsId) ;
		}
		return result;
	}

}
