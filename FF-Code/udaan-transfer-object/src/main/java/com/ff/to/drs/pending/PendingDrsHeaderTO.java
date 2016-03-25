/**
 * 
 */
package com.ff.to.drs.pending;

import java.util.List;

import com.ff.to.drs.AbstractDeliveryTO;

/**
 * @author mohammes
 *
 */
public class PendingDrsHeaderTO extends AbstractDeliveryTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8692397208283687381L;
	
	private Integer addedRowCount;
	
	private String rowAlreadyAddedRow[] = new String[rowCount];
	
	private List<PendingDrsDetailsTO> drsDetailsTo;
	
	private Integer pendingReasonForBulkCn;
	
	private String prepareYpDrs;
	
	
	/**
	 * @return the drsDetailsTo
	 */
	public final List<PendingDrsDetailsTO> getDrsDetailsTo() {
		return drsDetailsTo;
	}
	/**
	 * @param drsDetailsTo the drsDetailsTo to set
	 */
	public final void setDrsDetailsTo(List<PendingDrsDetailsTO> drsDetailsTo) {
		this.drsDetailsTo = drsDetailsTo;
	}
	public Integer getAddedRowCount() {
		return addedRowCount;
	}
	public void setAddedRowCount(Integer addedRowCount) {
		this.addedRowCount = addedRowCount;
	}
	public String[] getRowAlreadyAddedRow() {
		return rowAlreadyAddedRow;
	}
	public void setRowAlreadyAddedRow(String[] rowAlreadyAddedRow) {
		this.rowAlreadyAddedRow = rowAlreadyAddedRow;
	}
	/**
	 * @return the pendingReasonForBulkCn
	 */
	public Integer getPendingReasonForBulkCn() {
		return pendingReasonForBulkCn;
	}
	/**
	 * @param pendingReasonForBulkCn the pendingReasonForBulkCn to set
	 */
	public void setPendingReasonForBulkCn(Integer pendingReasonForBulkCn) {
		this.pendingReasonForBulkCn = pendingReasonForBulkCn;
	}
	/**
	 * @return the prepareYpDrs
	 */
	public String getPrepareYpDrs() {
		return prepareYpDrs;
	}
	/**
	 * @param prepareYpDrs the prepareYpDrs to set
	 */
	public void setPrepareYpDrs(String prepareYpDrs) {
		this.prepareYpDrs = prepareYpDrs;
	}
	
	}
