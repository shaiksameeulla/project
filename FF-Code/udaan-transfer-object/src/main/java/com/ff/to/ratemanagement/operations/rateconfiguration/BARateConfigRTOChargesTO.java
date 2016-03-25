/**
 * 
 */
package com.ff.to.ratemanagement.operations.rateconfiguration;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author prmeher
 *
 */

public class BARateConfigRTOChargesTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4964770408719686713L;
	
	private String rtoChargesApplicablechk;
	private String rtoChargeApplicable;
	private String sameAsSlabRate;
	private Double discountOnSlab;
	/**
	 * @return the rtoChargesApplicablechk
	 */
	public String getRtoChargesApplicablechk() {
		return rtoChargesApplicablechk;
	}
	/**
	 * @param rtoChargesApplicablechk the rtoChargesApplicablechk to set
	 */
	public void setRtoChargesApplicablechk(String rtoChargesApplicablechk) {
		this.rtoChargesApplicablechk = rtoChargesApplicablechk;
	}
	/**
	 * @return the rtoChargeApplicable
	 */
	public String getRtoChargeApplicable() {
		return rtoChargeApplicable;
	}
	/**
	 * @param rtoChargeApplicable the rtoChargeApplicable to set
	 */
	public void setRtoChargeApplicable(String rtoChargeApplicable) {
		this.rtoChargeApplicable = rtoChargeApplicable;
	}
	/**
	 * @return the sameAsSlabRate
	 */
	public String getSameAsSlabRate() {
		return sameAsSlabRate;
	}
	/**
	 * @param sameAsSlabRate the sameAsSlabRate to set
	 */
	public void setSameAsSlabRate(String sameAsSlabRate) {
		this.sameAsSlabRate = sameAsSlabRate;
	}
	/**
	 * @return the discountOnSlab
	 */
	public Double getDiscountOnSlab() {
		return discountOnSlab;
	}
	/**
	 * @param discountOnSlab the discountOnSlab to set
	 */
	public void setDiscountOnSlab(Double discountOnSlab) {
		this.discountOnSlab = discountOnSlab;
	}
	
	

}
