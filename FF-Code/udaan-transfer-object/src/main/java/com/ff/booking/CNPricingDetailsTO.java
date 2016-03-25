package com.ff.booking;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.to.rate.RateCalculationInputTO;

/**
 * The Class CNPricingDetailsTO.
 */
public class CNPricingDetailsTO extends CGBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1010370464899921017L;

	/** The price id. */
	private Integer priceId;

	/** The discount. */
	private Double discount;

	/** The final price. */
	private Double finalPrice;

	/** The fuel chg. */
	private Double fuelChg;

	/** The service tax. */
	private Double serviceTax;

	/** The risk sur chg. */
	private Double riskSurChg;

	/** The cod chg. */
	private Double codChg;

	/** The topay chg. */
	private Double topayChg;

	/** The airport handling chg. */
	private Double airportHandlingChg;

	/** The spl chg. */
	private Double splChg;

	/** The consignment id. */
	private Integer consignmentId;

	/** The edu cess chg. */
	private Double eduCessChg;

	/** The higher edu cess chg. */
	private Double higherEduCessChg;

	/** The freight chg. */
	private Double freightChg;

	/** The rate calc inputs. */
	private RateCalculationInputTO rateCalcInputs;

	/** The declaredvalue. */
	private Double declaredvalue;

	/** The cod amt. */
	private Double codAmt;

	/** The lc amount. */
	private Double lcAmount;

	/** The bank name. */
	private String bankName;

	/** The is re calc rate req. */
	private boolean isReCalcRateReq;

	private String servicesOn;
	private String rateType;
	private String ebPreferencesCodes;

	private String message;
	/** The spl chg. */
	private Double inputSplChg;
	
	private Double baAmt;


	
	
	/**
	 * @return the baAmt
	 */
	public Double getBaAmt() {
		return baAmt;
	}

	/**
	 * @param baAmt the baAmt to set
	 */
	public void setBaAmt(Double baAmt) {
		this.baAmt = baAmt;
	}

	/**
	 * @return the inputSplChg
	 */
	public Double getInputSplChg() {
		return inputSplChg;
	}

	/**
	 * @param inputSplChg the inputSplChg to set
	 */
	public void setInputSplChg(Double inputSplChg) {
		this.inputSplChg = inputSplChg;
	}

	/**
	 * Gets the freight chg.
	 * 
	 * @return the freight chg
	 */
	public Double getFreightChg() {
		return freightChg;
	}

	/**
	 * Sets the freight chg.
	 * 
	 * @param freightChg
	 *            the new freight chg
	 */
	public void setFreightChg(Double freightChg) {
		this.freightChg = freightChg;
	}

	/**
	 * Gets the price id.
	 * 
	 * @return the price id
	 */
	public Integer getPriceId() {
		return priceId;
	}

	/**
	 * Sets the price id.
	 * 
	 * @param priceId
	 *            the new price id
	 */
	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}

	/**
	 * Gets the discount.
	 * 
	 * @return the discount
	 */
	public Double getDiscount() {
		return discount;
	}

	/**
	 * Sets the discount.
	 * 
	 * @param discount
	 *            the new discount
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	/**
	 * Gets the final price.
	 * 
	 * @return the final price
	 */
	public Double getFinalPrice() {
		return finalPrice;
	}

	/**
	 * Sets the final price.
	 * 
	 * @param finalPrice
	 *            the new final price
	 */
	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}

	/**
	 * Gets the fuel chg.
	 * 
	 * @return the fuel chg
	 */
	public Double getFuelChg() {
		return fuelChg;
	}

	/**
	 * Sets the fuel chg.
	 * 
	 * @param fuelChg
	 *            the new fuel chg
	 */
	public void setFuelChg(Double fuelChg) {
		this.fuelChg = fuelChg;
	}

	/**
	 * Gets the service tax.
	 * 
	 * @return the service tax
	 */
	public Double getServiceTax() {
		return serviceTax;
	}

	/**
	 * Sets the service tax.
	 * 
	 * @param serviceTax
	 *            the new service tax
	 */
	public void setServiceTax(Double serviceTax) {
		this.serviceTax = serviceTax;
	}

	/**
	 * Gets the risk sur chg.
	 * 
	 * @return the risk sur chg
	 */
	public Double getRiskSurChg() {
		return riskSurChg;
	}

	/**
	 * Sets the risk sur chg.
	 * 
	 * @param riskSurChg
	 *            the new risk sur chg
	 */
	public void setRiskSurChg(Double riskSurChg) {
		this.riskSurChg = riskSurChg;
	}

	/**
	 * Gets the cod chg.
	 * 
	 * @return the cod chg
	 */
	public Double getCodChg() {
		return codChg;
	}

	/**
	 * Sets the cod chg.
	 * 
	 * @param codChg
	 *            the new cod chg
	 */
	public void setCodChg(Double codChg) {
		this.codChg = codChg;
	}

	/**
	 * Gets the topay chg.
	 * 
	 * @return the topay chg
	 */
	public Double getTopayChg() {
		return topayChg;
	}

	/**
	 * Sets the topay chg.
	 * 
	 * @param topayChg
	 *            the new topay chg
	 */
	public void setTopayChg(Double topayChg) {
		this.topayChg = topayChg;
	}

	/**
	 * Gets the airport handling chg.
	 * 
	 * @return the airport handling chg
	 */
	public Double getAirportHandlingChg() {
		return airportHandlingChg;
	}

	/**
	 * Sets the airport handling chg.
	 * 
	 * @param airportHandlingChg
	 *            the new airport handling chg
	 */
	public void setAirportHandlingChg(Double airportHandlingChg) {
		this.airportHandlingChg = airportHandlingChg;
	}

	/**
	 * Gets the spl chg.
	 * 
	 * @return the spl chg
	 */
	public Double getSplChg() {
		return splChg;
	}

	/**
	 * Sets the spl chg.
	 * 
	 * @param splChg
	 *            the new spl chg
	 */
	public void setSplChg(Double splChg) {
		this.splChg = splChg;
	}

	/**
	 * Gets the consignment id.
	 * 
	 * @return the consignment id
	 */
	public Integer getConsignmentId() {
		return consignmentId;
	}

	/**
	 * Sets the consignment id.
	 * 
	 * @param consignmentId
	 *            the new consignment id
	 */
	public void setConsignmentId(Integer consignmentId) {
		this.consignmentId = consignmentId;
	}

	/**
	 * Gets the edu cess chg.
	 * 
	 * @return the edu cess chg
	 */
	public Double getEduCessChg() {
		return eduCessChg;
	}

	/**
	 * Sets the edu cess chg.
	 * 
	 * @param eduCessChg
	 *            the new edu cess chg
	 */
	public void setEduCessChg(Double eduCessChg) {
		this.eduCessChg = eduCessChg;
	}

	/**
	 * Gets the higher edu cess chg.
	 * 
	 * @return the higher edu cess chg
	 */
	public Double getHigherEduCessChg() {
		return higherEduCessChg;
	}

	/**
	 * Sets the higher edu cess chg.
	 * 
	 * @param higherEduCessChg
	 *            the new higher edu cess chg
	 */
	public void setHigherEduCessChg(Double higherEduCessChg) {
		this.higherEduCessChg = higherEduCessChg;
	}

	/**
	 * Gets the rate calc inputs.
	 * 
	 * @return the rate calc inputs
	 */
	public RateCalculationInputTO getRateCalcInputs() {
		return rateCalcInputs;
	}

	/**
	 * Sets the rate calc inputs.
	 * 
	 * @param rateCalcInputs
	 *            the new rate calc inputs
	 */
	public void setRateCalcInputs(RateCalculationInputTO rateCalcInputs) {
		this.rateCalcInputs = rateCalcInputs;
	}

	/**
	 * Gets the declaredvalue.
	 * 
	 * @return the declaredvalue
	 */
	public Double getDeclaredvalue() {
		return declaredvalue;
	}

	/**
	 * Sets the declaredvalue.
	 * 
	 * @param declaredvalue
	 *            the new declaredvalue
	 */
	public void setDeclaredvalue(Double declaredvalue) {
		this.declaredvalue = declaredvalue;
	}

	/**
	 * Gets the cod amt.
	 * 
	 * @return the cod amt
	 */
	public Double getCodAmt() {
		return codAmt;
	}

	/**
	 * Sets the cod amt.
	 * 
	 * @param codAmt
	 *            the new cod amt
	 */
	public void setCodAmt(Double codAmt) {
		this.codAmt = codAmt;
	}

	/**
	 * Checks if is re calc rate req.
	 * 
	 * @return true, if is re calc rate req
	 */
	public boolean isReCalcRateReq() {
		return isReCalcRateReq;
	}

	/**
	 * Sets the re calc rate req.
	 * 
	 * @param isReCalcRateReq
	 *            the new re calc rate req
	 */
	public void setReCalcRateReq(boolean isReCalcRateReq) {
		this.isReCalcRateReq = isReCalcRateReq;
	}

	/**
	 * Gets the lc amount.
	 * 
	 * @return the lc amount
	 */
	public Double getLcAmount() {
		return lcAmount;
	}

	/**
	 * Sets the lc amount.
	 * 
	 * @param lcAmount
	 *            the new lc amount
	 */
	public void setLcAmount(Double lcAmount) {
		this.lcAmount = lcAmount;
	}

	/**
	 * Gets the bank name.
	 * 
	 * @return the bank name
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * Sets the bank name.
	 * 
	 * @param bankName
	 *            the new bank name
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the servicesOn
	 */
	public String getServicesOn() {
		return servicesOn;
	}

	/**
	 * @param servicesOn
	 *            the servicesOn to set
	 */
	public void setServicesOn(String servicesOn) {
		this.servicesOn = servicesOn;
	}

	/**
	 * @return the rateType
	 */
	public String getRateType() {
		return rateType;
	}

	/**
	 * @param rateType
	 *            the rateType to set
	 */
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	/**
	 * @return the ebPreferencesCodes
	 */
	public String getEbPreferencesCodes() {
		return ebPreferencesCodes;
	}

	/**
	 * @param ebPreferencesCodes
	 *            the ebPreferencesCodes to set
	 */
	public void setEbPreferencesCodes(String ebPreferencesCodes) {
		this.ebPreferencesCodes = ebPreferencesCodes;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
