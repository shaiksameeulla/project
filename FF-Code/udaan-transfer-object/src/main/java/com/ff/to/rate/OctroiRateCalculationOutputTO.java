/**
 * 
 */
package com.ff.to.rate;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author prmeher
 *
 */
public class OctroiRateCalculationOutputTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7476355232165576772L;
	/** Octroi Amount */
	private Double octroi;
	/** Octroi Service Charge */
	private Double octroiServiceCharge;
	/** service Tax On Octroi Service Charge */
	private Double serviceTaxOnOctroiServiceCharge;
	/** Edu Cess On Octroi Service Charge */
	private Double eduCessOnOctroiServiceCharge;
	/** Higher Edu Cess On Octroi Service Charge */
	private Double higherEduCessOnOctroiServiceCharge;
	/** Octroi stateTaxOnOctroiServiceCharge */
	private Double stateTaxOnOctroiServiceCharge;
	/** Octroi surcharge on state tax on octroi service charge */
	private Double surchargeOnStateTaxOnoctroiServiceCharge;
	/** Octroi Bourne By Consignee or consignor */
	private String octroiBourneBy;
	private List<RateComponentTO> components;

	/**
	 * @return the components
	 */
	public List<RateComponentTO> getComponents() {
		return components;
	}

	/**
	 * @param components the components to set
	 */
	public void setComponents(List<RateComponentTO> components) {
		this.components = components;
	}
	/**
	 * @return the octroi
	 */
	public Double getOctroi() {
		return octroi;
	}
	/**
	 * @param octroi the octroi to set
	 */
	public void setOctroi(Double octroi) {
		this.octroi = octroi;
	}
	/**
	 * @return the octroiServiceCharge
	 */
	public Double getOctroiServiceCharge() {
		return octroiServiceCharge;
	}
	/**
	 * @param octroiServiceCharge the octroiServiceCharge to set
	 */
	public void setOctroiServiceCharge(Double octroiServiceCharge) {
		this.octroiServiceCharge = octroiServiceCharge;
	}
	/**
	 * @return the serviceTaxOnOctroiServiceCharge
	 */
	public Double getServiceTaxOnOctroiServiceCharge() {
		return serviceTaxOnOctroiServiceCharge;
	}
	/**
	 * @param serviceTaxOnOctroiServiceCharge the serviceTaxOnOctroiServiceCharge to set
	 */
	public void setServiceTaxOnOctroiServiceCharge(
			Double serviceTaxOnOctroiServiceCharge) {
		this.serviceTaxOnOctroiServiceCharge = serviceTaxOnOctroiServiceCharge;
	}
	/**
	 * @return the eduCessOnOctroiServiceCharge
	 */
	public Double getEduCessOnOctroiServiceCharge() {
		return eduCessOnOctroiServiceCharge;
	}
	/**
	 * @param eduCessOnOctroiServiceCharge the eduCessOnOctroiServiceCharge to set
	 */
	public void setEduCessOnOctroiServiceCharge(Double eduCessOnOctroiServiceCharge) {
		this.eduCessOnOctroiServiceCharge = eduCessOnOctroiServiceCharge;
	}
	/**
	 * @return the higherEduCessOnOctroiServiceCharge
	 */
	public Double getHigherEduCessOnOctroiServiceCharge() {
		return higherEduCessOnOctroiServiceCharge;
	}
	/**
	 * @param higherEduCessOnOctroiServiceCharge the higherEduCessOnOctroiServiceCharge to set
	 */
	public void setHigherEduCessOnOctroiServiceCharge(
			Double higherEduCessOnOctroiServiceCharge) {
		this.higherEduCessOnOctroiServiceCharge = higherEduCessOnOctroiServiceCharge;
	}
	/**
	 * @return the stateTaxOnOctroiServiceCharge
	 */
	public Double getStateTaxOnOctroiServiceCharge() {
		return stateTaxOnOctroiServiceCharge;
	}
	/**
	 * @param stateTaxOnOctroiServiceCharge the stateTaxOnOctroiServiceCharge to set
	 */
	public void setStateTaxOnOctroiServiceCharge(
			Double stateTaxOnOctroiServiceCharge) {
		this.stateTaxOnOctroiServiceCharge = stateTaxOnOctroiServiceCharge;
	}
	/**
	 * @return the surchargeOnStateTaxOnoctroiServiceCharge
	 */
	public Double getSurchargeOnStateTaxOnoctroiServiceCharge() {
		return surchargeOnStateTaxOnoctroiServiceCharge;
	}
	/**
	 * @param surchargeOnStateTaxOnoctroiServiceCharge the surchargeOnStateTaxOnoctroiServiceCharge to set
	 */
	public void setSurchargeOnStateTaxOnoctroiServiceCharge(
			Double surchargeOnStateTaxOnoctroiServiceCharge) {
		this.surchargeOnStateTaxOnoctroiServiceCharge = surchargeOnStateTaxOnoctroiServiceCharge;
	}
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
	
}
