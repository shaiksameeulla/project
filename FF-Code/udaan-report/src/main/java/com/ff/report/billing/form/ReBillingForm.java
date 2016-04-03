/**
 * 
 */
package com.ff.report.billing.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.billing.ReBillingTO;

/**
 * @author abarudwa
 *
 */
public class ReBillingForm extends CGBaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReBillingForm() {
		ReBillingTO reBillingTO = new ReBillingTO();
		setTo(reBillingTO);
	}
	
	

}
