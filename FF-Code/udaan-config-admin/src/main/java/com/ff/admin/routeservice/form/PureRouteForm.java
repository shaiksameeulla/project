package com.ff.admin.routeservice.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.routeservice.PureRouteTO;
/**
 * @author mrohini
 *
 */
public class PureRouteForm extends CGBaseForm {

	private static final long serialVersionUID = 1L;

	public PureRouteForm() {
		setTo(new PureRouteTO());
	}
}
