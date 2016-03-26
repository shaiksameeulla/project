package com.ff.web.locationsearch.form;



import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.utilities.LocationSearchTO;

public class LocationSearchForm extends CGBaseForm{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2008713581823276670L;

	public LocationSearchForm(){
		LocationSearchTO locationSearchTo =  new LocationSearchTO();
		setTo(locationSearchTo);
	}
	
}
