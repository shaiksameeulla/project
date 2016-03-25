package com.ff.admin.stockmanagement.stocktransfer.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.stockmanagement.stocktransfer.StockTransferTO;

/**
 * The Class StockTransferForm.
 *
 * @author hkansagr
 */

public class StockTransferForm extends CGBaseForm{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The to. */
	private StockTransferTO to = null;
	
	/**
	 * Instantiates a new stock transfer form.
	 */
	public StockTransferForm() {
		super();
		to=new StockTransferTO();
		setTo(to);
	}
	
	
}
