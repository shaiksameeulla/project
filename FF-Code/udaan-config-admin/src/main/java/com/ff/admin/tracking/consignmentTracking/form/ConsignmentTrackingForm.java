/**
 * 
 */
package com.ff.admin.tracking.consignmentTracking.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.booking.BookingParcelTO;
import com.ff.organization.OfficeTO;
import com.ff.tracking.TrackingConsignmentTO;

/**
 * @author uchauhan
 *
 */
public class ConsignmentTrackingForm extends CGBaseForm{

	/**
	 * The Constant serialVersionUID
	 */
	private static final long serialVersionUID = 7632123378076127635L;
	
	private TrackingConsignmentTO trackingConsgTO = new TrackingConsignmentTO();
	private BookingParcelTO bookingTO =  new BookingParcelTO();
	private OfficeTO officeTO = new OfficeTO();
	
	
	/**
	 * Instantiates a new in master bag manifest form.
	 */
	public ConsignmentTrackingForm() {
		trackingConsgTO.setBookingTO(bookingTO);
		trackingConsgTO.setOfficeTO(officeTO);
		
		setTo(trackingConsgTO);
	}

}
