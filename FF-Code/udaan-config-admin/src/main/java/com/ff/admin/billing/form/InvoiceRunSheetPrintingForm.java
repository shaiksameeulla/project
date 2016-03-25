/**
 * 
 */
package com.ff.admin.billing.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.billing.InvoiceRunSheetTO;

// TODO: Auto-generated Javadoc
/**
 * The Class InvoiceRunSheetPrintingForm.
 *
 * @author abarudwa
 */
public class InvoiceRunSheetPrintingForm extends CGBaseForm{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new invoice run sheet printing form.
	 */
	public InvoiceRunSheetPrintingForm() 
	{
		InvoiceRunSheetTO invoiceRunSheetTO = new InvoiceRunSheetTO();
		setTo(invoiceRunSheetTO);
	}

}
