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
public class SendSMSForm extends CGBaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//radio button
	public String radio;
	
	
	/**
	 * @return the radio
	 */
	public String getRadio() {
		return radio;
	}


	/**
	 * @param radio the radio to set
	 */
	public void setRadio(String radio) {
		this.radio = radio;
	}


	public SendSMSForm() {
		
		LeadTO leadTo = new LeadTO();
		setTo(leadTo);
	}
	

}
