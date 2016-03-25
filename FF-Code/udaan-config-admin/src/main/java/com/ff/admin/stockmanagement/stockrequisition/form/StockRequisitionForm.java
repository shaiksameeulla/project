/**
 * 
 */
package com.ff.admin.stockmanagement.stockrequisition.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.stockmanagement.stockrequisition.StockRequisitionTO;

/**
 * The Class StockRequisitionForm.
 *
 * @author mohammes
 */
public class StockRequisitionForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The to. */
	private StockRequisitionTO to;
	
	/**
	 * Instantiates a new stock requisition form.
	 */
	public StockRequisitionForm(){
		super();
		to=new StockRequisitionTO();
		setTo(to);
	}

	

	
}
