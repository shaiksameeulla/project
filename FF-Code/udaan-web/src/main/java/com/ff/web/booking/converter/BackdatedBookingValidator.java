package com.ff.web.booking.converter;

import java.util.List;

import com.ff.booking.BackdatedBookingTO;
import com.ff.web.booking.validator.BookingValidator;

/**
 * The Class BackdatedBookingValidator.
 */
public class BackdatedBookingValidator extends BookingValidator {
	
	/**
	 * Validate backdated booking.
	 *
	 * @param backdatedBookingTOs the backdated booking t os
	 * @return true, if successful
	 */
	public static boolean validateBackdatedBooking(
			List<BackdatedBookingTO> backdatedBookingTOs){
				return false;
					
			}
}
