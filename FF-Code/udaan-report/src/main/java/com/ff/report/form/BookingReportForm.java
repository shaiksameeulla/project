package com.ff.report.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.report.BookingReportTO;

/**
 * @author khassan
 *
 */
public class BookingReportForm extends CGBaseForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7891708262508243774L;
	private BookingReportTO to;
	
	public BookingReportForm(){
		super();
		to = new BookingReportTO();
		setTo(to);
	}
	

}