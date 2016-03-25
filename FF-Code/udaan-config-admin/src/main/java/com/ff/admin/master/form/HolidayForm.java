package com.ff.admin.master.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.master.HolidayTO;

public class HolidayForm extends CGBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public HolidayForm() {
		HolidayTO holidayTO  = new HolidayTO();
		setTo(holidayTO);
	}

}
