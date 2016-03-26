package com.ff.rate.configuration.ratebenchmark.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.ratemanagement.operations.ratebenchmark.RateBenchMarkHeaderTO;

public class RateBenchMarkForm extends CGBaseForm{
	private static final long serialVersionUID = 1L;

	public RateBenchMarkForm() {
		setTo(new RateBenchMarkHeaderTO());
	}
}
