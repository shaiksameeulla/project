package com.ff.web.pickup.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.pickup.RunsheetAssignmentTO;

public class CreateRunsheetForm	extends CGBaseForm 
{
	/**
	 *	@author hkansagr
	 *	@date Friday, November 09, 2012
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private RunsheetAssignmentTO to;

	public CreateRunsheetForm() {
	    setTo(new RunsheetAssignmentTO());
	}

	public RunsheetAssignmentTO getTo() {
	    return to;
	}

	public void setTo(RunsheetAssignmentTO to) {
	    this.to = to;
	}

	

}
