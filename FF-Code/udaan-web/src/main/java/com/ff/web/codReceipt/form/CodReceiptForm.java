package com.ff.web.codReceipt.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.codreceipt.CodReceiptFormTO;

public class CodReceiptForm extends CGBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4626170534910838529L;

	public CodReceiptForm(){
		CodReceiptFormTO codReceiptFormTO = new CodReceiptFormTO();
		setTo(codReceiptFormTO);
	}
}
