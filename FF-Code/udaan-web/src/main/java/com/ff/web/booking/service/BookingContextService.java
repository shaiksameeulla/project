package com.ff.web.booking.service;

import java.util.Map;

import com.ff.to.rate.ConsignmentRateCalculationOutputTO;

public class BookingContextService {
	Map<String, ConsignmentRateCalculationOutputTO> consgRateDtls = null;

	/**
	 * @return the consgRateDtls
	 */
	public Map<String, ConsignmentRateCalculationOutputTO> getConsgRateDtls() {
		return consgRateDtls;
	}

	/**
	 * @param consgRateDtls
	 *            the consgRateDtls to set
	 */
	public void setConsgRateDtls(
			Map<String, ConsignmentRateCalculationOutputTO> consgRateDtls) {
		this.consgRateDtls = consgRateDtls;
	}

}
