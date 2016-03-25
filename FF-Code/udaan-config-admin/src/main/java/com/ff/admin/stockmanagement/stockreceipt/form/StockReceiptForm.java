package com.ff.admin.stockmanagement.stockreceipt.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.stockmanagement.stockreceipt.StockReceiptTO;

/**
 * The Class StockReceiptForm.
 *
 * @author hkansagr
 */

public class StockReceiptForm extends CGBaseForm 
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The to. */
	private StockReceiptTO to;
	
	/**
	 * Instantiates a new stock receipt form.
	 */
	public StockReceiptForm() {
		super();
		to=new StockReceiptTO();
		setTo(to);
	}
	
	

}
