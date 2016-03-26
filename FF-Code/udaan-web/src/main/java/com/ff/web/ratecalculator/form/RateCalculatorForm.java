/**
 * 
 */
package com.ff.web.ratecalculator.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.rateCalculator.RateCalculatorTO;

/**
 * @author prmeher
 * 
 */
public class RateCalculatorForm extends CGBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5628857844194318490L;

	public RateCalculatorForm() {
		setTo(new RateCalculatorTO());
	}
}
