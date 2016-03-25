package com.ff.universe.serviceOffering.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.serviceability.PincodeBranchServiceabilityTO;

public class SearchByPincodeForm extends CGBaseForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SearchByPincodeForm(){
		PincodeBranchServiceabilityTO pincodeBranchServiceabilityTO =  new PincodeBranchServiceabilityTO();
		setTo(pincodeBranchServiceabilityTO);
	}

}
