package com.ff.admin.stockmanagement.stockissue.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.stockmanagement.stockissue.StockIssueTO;

/**
 * The Class StockIssueForm.
 *
 * @author hkansagr
 */

public class StockIssueForm extends CGBaseForm
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The to. */
	private StockIssueTO to;
	
	/**
	 * Instantiates a new stock issue form.
	 */
	public StockIssueForm() {
		super();
		to=new StockIssueTO();
		setTo(to);
		
	}
	
}
