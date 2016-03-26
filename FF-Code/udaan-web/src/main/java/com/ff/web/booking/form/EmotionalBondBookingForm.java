/**
 * 
 */
package com.ff.web.booking.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.booking.EmotionalBondBookingTO;
import com.ff.business.ConsignorConsigneeTO;

/**
 * The Class EmotionalBondBookingForm.
 *
 * @author uchauhan
 */
public class EmotionalBondBookingForm extends CGBaseForm{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * Instantiates a new emotional bond booking form.
	 */
	public EmotionalBondBookingForm() {
		ConsignorConsigneeTO consigneeId = new ConsignorConsigneeTO();
		ConsignorConsigneeTO consignorId = new ConsignorConsigneeTO();
		CNPricingDetailsTO cnPricingDtls = new CNPricingDetailsTO();
		EmotionalBondBookingTO emotionalBondBookingTO = new EmotionalBondBookingTO();
		emotionalBondBookingTO.setConsignee(consigneeId);
		emotionalBondBookingTO.setConsignor(consignorId);
		emotionalBondBookingTO.setCnPricingDtls(cnPricingDtls);
		setTo(emotionalBondBookingTO);
	}
}

	


