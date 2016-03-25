/**
 * 
 */
package com.ff.to.drs.pending;

import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.to.drs.AbstractDeliveryDetailTO;

/**
 * @author mohammes
 *
 */
public class PendingDrsDetailsTO extends AbstractDeliveryDetailTO  implements Comparable<PendingDrsDetailsTO>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -321970045565604552L;
	private String alreadyAddedRow = "Y";
	@Override
	public int compareTo(PendingDrsDetailsTO arg0) {
		int result=0;
		if(!StringUtil.isEmptyInteger(rowNumber) && !StringUtil.isEmptyInteger(arg0.getRowNumber())) {
			result = this.rowNumber.compareTo(arg0.rowNumber);
		}
		else if(!StringUtil.isEmptyLong(deliveryDetailId) && !StringUtil.isEmptyLong(arg0.getDeliveryDetailId())) {
			result = this.deliveryDetailId.compareTo(arg0.deliveryDetailId);
		}
		return result;
	}
	public String getAlreadyAddedRow() {
		return alreadyAddedRow;
	}
	public void setAlreadyAddedRow(String alreadyAddedRow) {
		this.alreadyAddedRow = alreadyAddedRow;
	}

}
