/**
 * 
 */
package com.ff.to.stockmanagement.stockrequisition;

import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.to.stockmanagement.StockDetailTO;

/**
 * @author mohammes
 *
 */
public class StockRequisitionItemDtlsTO extends StockDetailTO implements Comparable<StockRequisitionItemDtlsTO>{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1878790897L;

	/**   primary key */
	private Long stockRequisitionItemDtlsId;
	private String  isApproved;
	private String procurementType;// for approval
	
	
	

	/**
	 * @return the stockRequisitionItemDtlsId
	 */
	public Long getStockRequisitionItemDtlsId() {
		return stockRequisitionItemDtlsId;
	}

	/**
	 * @param stockRequisitionItemDtlsId the stockRequisitionItemDtlsId to set
	 */
	public void setStockRequisitionItemDtlsId(Long stockRequisitionItemDtlsId) {
		this.stockRequisitionItemDtlsId = stockRequisitionItemDtlsId;
	}

	
	@Override
	public int compareTo(StockRequisitionItemDtlsTO arg0) {
		int result=0;
		if(!StringUtil.isEmptyInteger(rowNumber)&& !StringUtil.isEmptyInteger(arg0.getRowNumber())){
			result = this.rowNumber.compareTo(arg0.rowNumber) ;
		}
		else if(!StringUtil.isEmptyLong(stockRequisitionItemDtlsId)&& !StringUtil.isEmptyLong(arg0.getStockRequisitionItemDtlsId())){
			result = this.stockRequisitionItemDtlsId.compareTo(arg0.stockRequisitionItemDtlsId) ;
		}
		return result;
	}

	/**
	 * @return the isApproved
	 */
	public String getIsApproved() {
		return isApproved;
	}

	/**
	 * @param isApproved the isApproved to set
	 */
	public void setIsApproved(String isApproved) {
		this.isApproved = isApproved;
	}

	/**
	 * @return the procurementType
	 */
	public String getProcurementType() {
		return procurementType;
	}

	/**
	 * @param procurementType the procurementType to set
	 */
	public void setProcurementType(String procurementType) {
		this.procurementType = procurementType;
	}

	

	
	
}
