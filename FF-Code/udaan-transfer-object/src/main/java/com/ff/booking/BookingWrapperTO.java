package com.ff.booking;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ff.to.rate.ConsignmentRateCalculationOutputTO;

public class BookingWrapperTO {

	private List<? extends BookingTO> sucessConsignments;
	private Set<String> failureConsignments;
	private Map<String, ConsignmentRateCalculationOutputTO> consgRateDetails = null;

	/**
	 * @return the sucessConsignments
	 */
	public List<? extends BookingTO> getSucessConsignments() {
		return sucessConsignments;
	}

	/**
	 * @param sucessConsignments
	 *            the sucessConsignments to set
	 */
	public void setSucessConsignments(
			List<? extends BookingTO> sucessConsignments) {
		this.sucessConsignments = sucessConsignments;
	}

	/**
	 * @return the failureConsignments
	 */
	public Set<String> getFailureConsignments() {
		return failureConsignments;
	}

	/**
	 * @param failureConsignments
	 *            the failureConsignments to set
	 */
	public void setFailureConsignments(Set<String> failureConsignments) {
		this.failureConsignments = failureConsignments;
	}

	/**
	 * @return the consgRateDetails
	 */
	public Map<String, ConsignmentRateCalculationOutputTO> getConsgRateDetails() {
		return consgRateDetails;
	}

	/**
	 * @param consgRateDetails the consgRateDetails to set
	 */
	public void setConsgRateDetails(
			Map<String, ConsignmentRateCalculationOutputTO> consgRateDetails) {
		this.consgRateDetails = consgRateDetails;
	}
	

}
