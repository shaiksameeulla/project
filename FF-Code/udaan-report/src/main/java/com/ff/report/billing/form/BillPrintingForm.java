package com.ff.report.billing.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.billing.BillPrintingTO;
import com.ff.to.billing.InvoiceRunSheetTO;

// TODO: Auto-generated Javadoc
/**
 * The Class BillPrintingForm.
 */
public class BillPrintingForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new bill printing form.
	 */
	public BillPrintingForm() {

		BillPrintingTO billPrintingTO = new BillPrintingTO();
		setTo(billPrintingTO);
	}

}
