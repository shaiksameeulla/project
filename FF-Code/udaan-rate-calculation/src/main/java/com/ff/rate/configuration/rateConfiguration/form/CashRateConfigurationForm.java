package com.ff.rate.configuration.rateConfiguration.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.ratemanagement.operations.rateconfiguration.CashRateConfigFixedChargesTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.CashRateConfigHeaderTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.CashRateConfigProductTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.CashRateConfigRTOChargesTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.CashRateSlabRateTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.CashRateSpecialDestinationTO;

/**
 * @author hkansagr
 */

public class CashRateConfigurationForm extends CGBaseForm {

	private static final long serialVersionUID = 1L;
	
	public CashRateConfigurationForm() {
		CashRateConfigHeaderTO headerTO = new CashRateConfigHeaderTO();
		//Courier
		headerTO.setCourierSlabRateTO(new CashRateSlabRateTO());
		headerTO.setCourierSplDestTO(new CashRateSpecialDestinationTO());
		headerTO.setCourierProductTO(new CashRateConfigProductTO());
		//Air-Cargo
		headerTO.setAirCargoSlabRateTO(new CashRateSlabRateTO());
		headerTO.setAirCargoSplDestTO(new CashRateSpecialDestinationTO());
		headerTO.setAirCargoProductTO(new CashRateConfigProductTO());
		//Train
		headerTO.setTrainSlabRateTO(new CashRateSlabRateTO());
		headerTO.setTrainSplDestTO(new CashRateSpecialDestinationTO());
		headerTO.setTrainProductTO(new CashRateConfigProductTO());
		headerTO.setFixedChargesTO(new CashRateConfigFixedChargesTO());
		headerTO.setRtoChargesTO(new CashRateConfigRTOChargesTO());
		
		//Priority
		headerTO.setPrioritySlabRateTO(new CashRateSlabRateTO());
		headerTO.setPrioritySplDestTO(new CashRateSpecialDestinationTO());
		headerTO.setPriorityProductTO(new CashRateConfigProductTO());
		headerTO.setPriorityFixedChargesTO(new CashRateConfigFixedChargesTO());
		headerTO.setPriorityRtoChargesTO(new CashRateConfigRTOChargesTO());		
		
		setTo(headerTO);
	}
}
