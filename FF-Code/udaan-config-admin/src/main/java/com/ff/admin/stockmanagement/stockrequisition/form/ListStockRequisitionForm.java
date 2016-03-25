/**
 * 
 */
package com.ff.admin.stockmanagement.stockrequisition.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.stockmanagement.stockrequisition.ListStockRequisitionTO;

/**
 * The Class ListStockRequisitionForm.
 *
 * @author mohammes
 */
public class ListStockRequisitionForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The to. */
	private ListStockRequisitionTO to;
	
	/**
	 * Instantiates a new stock requisition form.
	 */
	public ListStockRequisitionForm(){
		super();
		to=new ListStockRequisitionTO();
		setTo(to);
	}

	

	
}
