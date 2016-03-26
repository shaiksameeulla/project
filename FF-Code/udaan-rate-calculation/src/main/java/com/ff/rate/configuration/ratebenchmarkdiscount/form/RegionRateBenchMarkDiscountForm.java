package com.ff.rate.configuration.ratebenchmarkdiscount.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.ratemanagement.operations.rateBenchmarkDiscount.RegionRateBenchMarkDiscountTO;

/**
 * @author preegupt
 *
 */
public class RegionRateBenchMarkDiscountForm extends CGBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2536005620369565063L;

	public RegionRateBenchMarkDiscountForm() {
		setTo(new RegionRateBenchMarkDiscountTO());
	}
}
