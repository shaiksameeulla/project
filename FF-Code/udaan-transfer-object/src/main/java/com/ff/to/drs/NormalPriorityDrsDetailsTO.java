/**
 * 
 */
package com.ff.to.drs;

import com.capgemini.lbs.framework.utils.StringUtil;


/**
 * @author mohammes
 *
 */
public class NormalPriorityDrsDetailsTO extends AbstractDeliveryDetailTO implements Comparable<NormalPriorityDrsDetailsTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4187295493691823311L;

	
	public int compareTo(NormalPriorityDrsDetailsTO arg0) {
		int result=0;
		if(!StringUtil.isEmptyInteger(rowNumber) && !StringUtil.isEmptyInteger(arg0.getRowNumber())) {
			result = this.rowNumber.compareTo(arg0.rowNumber);
		}
		else if(!StringUtil.isEmptyLong(deliveryDetailId) && !StringUtil.isEmptyLong(arg0.getDeliveryDetailId())) {
			result = this.deliveryDetailId.compareTo(arg0.deliveryDetailId);
		}
		return result;
	}
	
}
