/**
 * 
 */
package com.ff.domain.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author prmeher
 *
 */
public class RateCalculationFixedChargesConfigDO extends CGFactDO {

	/**
	 * @param args
	 */
	private String octroiBourneBy;
	private String insuredby;
	private Double percentile;
	/**
	 * @return the octroiBourneBy
	 */
	public String getOctroiBourneBy() {
		return octroiBourneBy;
	}
	/**
	 * @param octroiBourneBy the octroiBourneBy to set
	 */
	public void setOctroiBourneBy(String octroiBourneBy) {
		this.octroiBourneBy = octroiBourneBy;
	}
	/**
	 * @return the insuredby
	 */
	public String getInsuredby() {
		return insuredby;
	}
	/**
	 * @param insuredby the insuredby to set
	 */
	public void setInsuredby(String insuredby) {
		this.insuredby = insuredby;
	}
	/**
	 * @return the percentile
	 */
	public Double getPercentile() {
		return percentile;
	}
	/**
	 * @param percentile the percentile to set
	 */
	public void setPercentile(Double percentile) {
		this.percentile = percentile;
	}

}
