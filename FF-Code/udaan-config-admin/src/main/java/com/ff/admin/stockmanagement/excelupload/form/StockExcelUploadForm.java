/**
 * 
 */
package com.ff.admin.stockmanagement.excelupload.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.stockmanagement.StockExcelUploadTO;

/**
 * @author mohammes
 *
 */
public class StockExcelUploadForm extends CGBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2924033985417529447L;
	
	private StockExcelUploadTO to;
	/**
	 * Instantiates a new stock cancellation form.
	 */
	public StockExcelUploadForm(){
		super();
		to= new StockExcelUploadTO();
		setTo(to);
	}

}
