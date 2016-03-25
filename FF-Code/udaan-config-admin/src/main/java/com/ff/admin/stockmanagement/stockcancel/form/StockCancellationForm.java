package com.ff.admin.stockmanagement.stockcancel.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.stockmanagement.stockcancel.StockCancellationTO;

/**
 * The Class StockCancellationForm.
 */
public class StockCancellationForm extends CGBaseForm{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4115396049286042443L;
	
	
	/** The to. */
	private StockCancellationTO to;
	
	/**
	 * Instantiates a new stock cancellation form.
	 */
	public StockCancellationForm(){
		super();
		to= new StockCancellationTO();
		setTo(to);
	}

	
	

}
