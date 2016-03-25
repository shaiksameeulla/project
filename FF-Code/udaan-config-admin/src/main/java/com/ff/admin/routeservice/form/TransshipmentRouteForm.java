package com.ff.admin.routeservice.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.routeserviced.TransshipmentRouteTO;
/**
 * @author mrohini
 *
 */
public class TransshipmentRouteForm extends CGBaseForm {

	private static final long serialVersionUID = 1L;

	public TransshipmentRouteForm() {
		setTo(new TransshipmentRouteTO());
	}
}
