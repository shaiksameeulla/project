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
public class CreateLeadForm extends CGBaseForm
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CreateLeadForm() 
	{
		LeadTO leadTo = new LeadTO();
		setTo(leadTo);
	}
	
	
	

}
