package com.ff.admin.mec.liability.form;


import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.mec.LiabilityTO;

/**
 * @author amimehta
 */

public class LiabilityPaymentForm extends CGBaseForm{

	private static final long serialVersionUID = 1L;

	public LiabilityPaymentForm(){
		setTo(new LiabilityTO());
		
	}
}
