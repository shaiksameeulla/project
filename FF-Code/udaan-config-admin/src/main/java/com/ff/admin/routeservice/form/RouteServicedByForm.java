package com.ff.admin.routeservice.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.routeserviced.TripServicedByTO;
/**
 * @author mrohini
 *
 */
public class RouteServicedByForm extends CGBaseForm {

	private static final long serialVersionUID = 1L;

	public RouteServicedByForm() {
		setTo(new TripServicedByTO());
	}
}
