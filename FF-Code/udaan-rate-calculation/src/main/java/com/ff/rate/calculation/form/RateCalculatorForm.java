package com.ff.rate.calculation.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.rate.RateCalculationInputTO;

public class RateCalculatorForm extends CGBaseForm {
	public RateCalculatorForm () {
		setTo(new RateCalculationInputTO());
	}
}
