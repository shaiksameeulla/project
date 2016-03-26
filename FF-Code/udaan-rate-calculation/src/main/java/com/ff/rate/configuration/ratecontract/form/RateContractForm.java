package com.ff.rate.configuration.ratecontract.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.ratemanagement.masters.RateContractSpocTO;
import com.ff.to.ratemanagement.masters.RateContractTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotaionFixedChargesTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotaionRTOChargesTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationListViewTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationProposedRatesTO;

/**
 * @author hkansagr 
 */

public class RateContractForm extends CGBaseForm {
	
	private static final long serialVersionUID = 1L;
	private RateQuotationProposedRatesTO proposedRatesTO;
	private RateQuotaionFixedChargesTO rateQuotationFixedChargesTO;
	private RateQuotaionRTOChargesTO rateQuotationRTOChargesTO;
	private RateQuotationListViewTO rateQuotationListViewTO;
	private RateContractSpocTO rateContractSpocTO;
	public RateContractForm(){
		setTo(new RateContractTO());
		setProposedRatesTO(new RateQuotationProposedRatesTO());
		setRateQuotationFixedChargesTO(new RateQuotaionFixedChargesTO());
		setRateQuotationRTOChargesTO(new RateQuotaionRTOChargesTO());
		setRateQuotationListViewTO(new RateQuotationListViewTO());
		setRateContractSpocTO(new RateContractSpocTO());
	}

	
	/**
	 * @return the proposedRatesTO
	 */
	public RateQuotationProposedRatesTO getProposedRatesTO() {
		return proposedRatesTO;
	}


	/**
	 * @param proposedRatesTO the proposedRatesTO to set
	 */
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


	/**
	 * @return the rateQuotationFixedChargesTO
	 */
	public RateQuotaionFixedChargesTO getRateQuotationFixedChargesTO() {
		return rateQuotationFixedChargesTO;
	}

	/**
	 * @param rateQuotationFixedChargesTO the rateQuotationFixedChargesTO to set
	 */
	public void setRateQuotationFixedChargesTO(
			RateQuotaionFixedChargesTO rateQuotationFixedChargesTO) {
		this.rateQuotationFixedChargesTO = rateQuotationFixedChargesTO;
	}

	/**
	 * @return the rateQuotationRTOChargesTO
	 */
	public RateQuotaionRTOChargesTO getRateQuotationRTOChargesTO() {
		return rateQuotationRTOChargesTO;
	}

	/**
	 * @param rateQuotationRTOChargesTO the rateQuotationRTOChargesTO to set
	 */
	public void setRateQuotationRTOChargesTO(
			RateQuotaionRTOChargesTO rateQuotationRTOChargesTO) {
		this.rateQuotationRTOChargesTO = rateQuotationRTOChargesTO;
	}


	/**
	 * @return the rateContractSpocTO
	 */
	public RateContractSpocTO getRateContractSpocTO() {
		return rateContractSpocTO;
	}


	/**
	 * @param rateContractSpocTO the rateContractSpocTO to set
	 */
	public void setRateContractSpocTO(RateContractSpocTO rateContractSpocTO) {
		this.rateContractSpocTO = rateContractSpocTO;
	}
}
