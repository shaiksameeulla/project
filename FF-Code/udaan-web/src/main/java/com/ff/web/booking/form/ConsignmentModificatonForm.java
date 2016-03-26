package com.ff.web.booking.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.booking.BookingPaymentTO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.booking.CashBookingDoxTO;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.InsuredByTO;

/**
 * The Class ConsignmentModificatonForm.
 */
public class ConsignmentModificatonForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 611051543675203562L;

	/**
	 * Instantiates a new consignment modificaton form.
	 */
	public ConsignmentModificatonForm(){
		
		ConsignorConsigneeTO consigneeId = new ConsignorConsigneeTO();
		ConsignorConsigneeTO consignorId = new ConsignorConsigneeTO();
		CNPricingDetailsTO cnPricingDtls = new CNPricingDetailsTO();
		BookingPaymentTO bookingPayment = new BookingPaymentTO();
		CNContentTO cnContents = new CNContentTO();
		CNPaperWorksTO cnPaperWorks = new CNPaperWorksTO();
		InsuredByTO insuredBy = new InsuredByTO();
		ConsignmentModificationTO consignmentModificationTO=new ConsignmentModificationTO();
		consignmentModificationTO.setConsignee(consigneeId);
		consignmentModificationTO.setConsignor(consignorId);
		consignmentModificationTO.setCnPricingDtls(cnPricingDtls);
		consignmentModificationTO.setBookingPayment(bookingPayment);
		consignmentModificationTO.setCnContents(cnContents);
		consignmentModificationTO.setCnPaperWorks(cnPaperWorks);
		consignmentModificationTO.setInsuredBy(insuredBy);
		setTo(consignmentModificationTO);
		
	}
	
}
