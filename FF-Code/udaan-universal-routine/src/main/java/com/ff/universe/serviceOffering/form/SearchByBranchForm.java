package com.ff.universe.serviceOffering.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.serviceability.PincodeBranchServiceabilityTO;


public class SearchByBranchForm extends CGBaseForm {
	
	private static final long serialVersionUID = 1L;

	public SearchByBranchForm(){
		PincodeBranchServiceabilityTO pincodeBranchServiceabilityTO =  new PincodeBranchServiceabilityTO();
		setTo(pincodeBranchServiceabilityTO);
	}

}
