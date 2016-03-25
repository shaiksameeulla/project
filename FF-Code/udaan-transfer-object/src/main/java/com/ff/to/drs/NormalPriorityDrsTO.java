/**
 * 
 */
package com.ff.to.drs;

import java.util.List;


/**
 * @author mohammes
 *
 */
public class NormalPriorityDrsTO extends AbstractDeliveryTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6835020080921259485L;

	private Double rowAmount[]= new Double[rowCount];
	
	private List<NormalPriorityDrsDetailsTO> detailsToList;

	/**
	 * @return the detailsToList
	 */
	public List<NormalPriorityDrsDetailsTO> getDetailsToList() {
		return detailsToList;
	}

	/**
	 * @param detailsToList the detailsToList to set
	 */
	public void setDetailsToList(List<NormalPriorityDrsDetailsTO> detailsToList) {
		this.detailsToList = detailsToList;
	}

	/**
	 * @return the rowAmount
	 */
	public Double[] getRowAmount() {
		return rowAmount;
	}

	/**
	 * @param rowAmount the rowAmount to set
	 */
	public void setRowAmount(Double[] rowAmount) {
		this.rowAmount = rowAmount;
	}

	
}
