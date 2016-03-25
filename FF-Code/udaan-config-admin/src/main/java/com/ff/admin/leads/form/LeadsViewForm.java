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
public class LeadsViewForm extends CGBaseForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//radio button property
	public String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LeadsViewForm() 
	{
		LeadTO leadTo = new LeadTO();
		setTo(leadTo);
	}

}
