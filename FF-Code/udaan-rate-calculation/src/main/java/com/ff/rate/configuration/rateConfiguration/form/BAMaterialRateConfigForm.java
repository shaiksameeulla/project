package com.ff.rate.configuration.rateConfiguration.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.ratemanagement.operations.rateconfiguration.BAMaterialRateConfigTO;

/**
 * @author hkansagr
 */

public class BAMaterialRateConfigForm extends CGBaseForm {

	private static final long serialVersionUID = 1L;

	public BAMaterialRateConfigForm(){
		setTo(new BAMaterialRateConfigTO());
	}
	
}
