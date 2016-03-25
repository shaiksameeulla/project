/**
 * 
 */
package com.ff.admin.stockmanagement.stockreturn.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.stockmanagement.stockreturn.StockReturnTO;

/**
 * The Class StockReturnForm.
 *
 * @author cbhure
 */
public class StockReturnForm extends CGBaseForm{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7441060034519615058L;
	
	/** The to. */
	private StockReturnTO to;
	
	/**
	 * Instantiates a new stock return form.
	 */
	public StockReturnForm(){
		super();
		to=new StockReturnTO();
		setTo(to);
	}

	

	

}
