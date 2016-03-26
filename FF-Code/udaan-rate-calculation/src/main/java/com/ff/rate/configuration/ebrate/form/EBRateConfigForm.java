package com.ff.rate.configuration.ebrate.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.ratemanagement.operations.ratequotation.EBRateConfigTO;
import com.ff.to.ratemanagement.operations.ratequotation.EBRatePreferenceTO;

public class EBRateConfigForm extends CGBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1124449807561682594L;
	private EBRatePreferenceTO ratePreferenceTO;

	public EBRateConfigForm() {
		setTo(new EBRateConfigTO());
		setRatePreferenceTO(new EBRatePreferenceTO());

	}

	/**
	 * @return the ratePreferenceTO
	 */
	public EBRatePreferenceTO getRatePreferenceTO() {
		return ratePreferenceTO;
	}

	/**
	 * @param ratePreferenceTO
	 *            the ratePreferenceTO to set
	 */
	public void setRatePreferenceTO(EBRatePreferenceTO ratePreferenceTO) {
		this.ratePreferenceTO = ratePreferenceTO;
	}

}
