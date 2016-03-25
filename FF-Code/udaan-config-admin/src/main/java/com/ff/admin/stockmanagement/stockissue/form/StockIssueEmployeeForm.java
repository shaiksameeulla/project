package com.ff.admin.stockmanagement.stockissue.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.stockmanagement.stockissue.StockIssueEmployeeTO;

/**
 * The Class StockIssueEmployeeForm.
 *
 * @author hkansagr
 */
public class StockIssueEmployeeForm	extends CGBaseForm 
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The to. */
	private StockIssueEmployeeTO to;
	
	/**
	 * Instantiates a new stock issue employee form.
	 */
	public StockIssueEmployeeForm() {
		super();
		to=new StockIssueEmployeeTO();
		setTo(to);
	}
	
	
}
