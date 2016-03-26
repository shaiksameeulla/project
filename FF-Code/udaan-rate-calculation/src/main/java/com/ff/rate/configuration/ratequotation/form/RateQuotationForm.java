package com.ff.rate.configuration.ratequotation.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationListViewTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationProposedRatesTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotaionFixedChargesTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotaionRTOChargesTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationTO;


public class RateQuotationForm extends CGBaseForm{
	private static final long serialVersionUID = 1L;
	
	private RateQuotationProposedRatesTO proposedRatesTO;
	private RateQuotaionFixedChargesTO rateQuotationFixedChargesTO;
	private RateQuotaionRTOChargesTO rateQuotationRTOChargesTO;
	private RateQuotationListViewTO rateQuotationListViewTO;

	public RateQuotationForm() {
		setTo(new RateQuotationTO());
		setProposedRatesTO(new RateQuotationProposedRatesTO());
		setRateQuotationFixedChargesTO(new RateQuotaionFixedChargesTO());
		setRateQuotationRTOChargesTO(new RateQuotaionRTOChargesTO());
		setRateQuotationListViewTO(rateQuotationListViewTO);

	}
	public RateQuotaionFixedChargesTO getRateQuotationFixedChargesTO() {
		return rateQuotationFixedChargesTO;
	}

	public void setRateQuotationFixedChargesTO(
			RateQuotaionFixedChargesTO rateQuotationFixedChargesTO) {
		this.rateQuotationFixedChargesTO = rateQuotationFixedChargesTO;
	}

	public RateQuotaionRTOChargesTO getRateQuotationRTOChargesTO() {
		return rateQuotationRTOChargesTO;
	}

	public void setRateQuotationRTOChargesTO(RateQuotaionRTOChargesTO rateQuotationRTOChargesTO) {
		this.rateQuotationRTOChargesTO = rateQuotationRTOChargesTO;
	}
	public RateQuotationProposedRatesTO getProposedRatesTO() {
		return proposedRatesTO;
	}

	public void setProposedRatesTO(RateQuotationProposedRatesTO proposedRatesTO) {
		this.proposedRatesTO = proposedRatesTO;
	}
	/**
	 * @return the rateQuotationListViewTO
	 */
	public RateQuotationListViewTO getRateQuotationListViewTO() {
		return rateQuotationListViewTO;
	}
	/**
	 * @param rateQuotationListViewTO the rateQuotationListViewTO to set
	 */
	public void setRateQuotationListViewTO(
			RateQuotationListViewTO rateQuotationListViewTO) {
		this.rateQuotationListViewTO = rateQuotationListViewTO;
	}
	
	
}
