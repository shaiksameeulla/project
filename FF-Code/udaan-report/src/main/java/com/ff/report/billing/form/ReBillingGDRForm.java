/**
 * 
 */
package com.ff.report.billing.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.billing.ReBillingGDRTO;

/**
 * @author abarudwa
 *
 */
public class ReBillingGDRForm extends CGBaseForm{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReBillingGDRForm() {
		ReBillingGDRTO ReBillingGDRTO = new ReBillingGDRTO();
		setTo(ReBillingGDRTO);
	}
	

}
