/**
 * 
 */
package com.ff.admin.leads.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.leads.LeadTO;

/**
 * @author abarudwa
 *
 */
public class SendEmailForm extends CGBaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SendEmailForm() {
		
		LeadTO leadTo = new LeadTO();
		setTo(leadTo);
	}
	
	

}
