package com.ff.rate.configuration.rateConfiguration.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.ratemanagement.operations.rateconfiguration.BARateConfigFixedChargesTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.BARateConfigRTOChargesTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.BARateConfigSlabRateTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.BARateHeaderTO;

public class BARateConfigurationForm extends CGBaseForm {
	private static final long serialVersionUID = 1L;

	public BARateConfigurationForm() {

		BARateHeaderTO baRateHeaderTO = new BARateHeaderTO();
		// For Courier Product
		BARateConfigSlabRateTO baCourierSlabRateTO = new BARateConfigSlabRateTO();
		BARateConfigFixedChargesTO baCourierFixedChargesTO = new BARateConfigFixedChargesTO();
		BARateConfigRTOChargesTO baCourierRTOChargesTO = new BARateConfigRTOChargesTO();
		// For Priority Product
		BARateConfigSlabRateTO baPrioritySlabRateTO = new BARateConfigSlabRateTO();
		BARateConfigFixedChargesTO baPriorityFixedChargesTO = new BARateConfigFixedChargesTO();
		BARateConfigRTOChargesTO baPriorityRTOChargesTO = new BARateConfigRTOChargesTO();

		baRateHeaderTO.setBaCourierSlabRateTO(baCourierSlabRateTO);
		// For Train Product
		baRateHeaderTO.setBaTrainSlabRateTO(new BARateConfigSlabRateTO());
		// For Air-Cargo Product
		baRateHeaderTO.setBaAirCargoSlabRateTO(new BARateConfigSlabRateTO());
		baRateHeaderTO.setBaCourierFixedChargesTO(baCourierFixedChargesTO);
		baRateHeaderTO.setBaCourierRTOChargesTO(baCourierRTOChargesTO);
		baRateHeaderTO.setBaPrioritySlabRateTO(baPrioritySlabRateTO);
		baRateHeaderTO.setBaPriorityFixedChargesTO(baPriorityFixedChargesTO);
		baRateHeaderTO.setBaPriorityRTOChargesTO(baPriorityRTOChargesTO);

		setTo(baRateHeaderTO);
	}
}
