package com.ff.booking;

/**
 * The Class BookingGridTO.
 */
public class BookingGridTO extends BookingTO {

	/** The count. */
	private int count;
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7580377634174910770L;
	
	/** The consg numbers. */
	private String[] consgNumbers = new String[count];
	
	/** The pincodes. */
	private String[] pincodes = new String[count];
	
	/** The dest cities. */
	private String[] destCities = new String[count];
	
	/** The actual weights. */
	private Double[] actualWeights = new Double[count];
	
	/** The chargeable weights. */
	private Double[] chargeableWeights = new Double[count];
	
	/** The final weights. */
	private Double[] finalWeights = new Double[count];
	
	/** The ref nos. */
	private String[] refNos = new String[count];
	
	/** The pincode ids. */
	private Integer[] pincodeIds = new Integer[count];
	
	/** The city ids. */
	private Integer[] cityIds = new Integer[count];
	
	/** The booking ids. */
	private Integer[] bookingIds = new Integer[count];
	
	/** The vol weights. */
	private Double[] volWeights = new Double[count];
	
	/** The lengths. */
	private Double[] lengths = new Double[count];
	
	/** The heights. */
	private Double[] heights = new Double[count];
	
	/** The breaths. */
	private Double[] breaths = new Double[count];
	
	/** The weight captured modes. */
	private String[] weightCapturedModes = new String[count];
	// Format : finalAmount#Freight Chg#Risk
	// SurChg#AirportHandlings#ServiceTax#Higher Edu Cess #Fuel Chg# Topay
	// Chg#Spl Chg#EduCessChg
	/** The consg pricing dtls. */
	private String[] consgPricingDtls = new String[count];
	// format: cne name # cne address # mobile # phone
	/** The cne address dtls. */
	private String[] cneAddressDtls = new String[count];
	
	/** The cnr address dtls. */
	private String[] cnrAddressDtls = new String[count];
	// format: Child Cn NO # weight
	/** The child cn details. */
	private String[] childCNDetails = new String[count];
	
	/** The other cn contents. */
	private String[] otherCNContents = new String[count];
	
	/** The dlv time map ids. */
	private Integer[] dlvTimeMapIds = new Integer[count];
	
	/** The num of pcs. */
	private Integer[] numOfPcs = new Integer[count];
	
	/** The declared values. */
	private Double[] declaredValues = new Double[count];
	
	private String[] priorityServicedOns = new String[count];
	

	private Double[] baAmts = new Double[count];
	
	private Double[] codAmts = new Double[count];
	
	
	
	/**
	 * @return the codAmts
	 */
	public Double[] getCodAmts() {
		return codAmts;
	}

	/**
	 * @param codAmts the codAmts to set
	 */
	public void setCodAmts(Double[] codAmts) {
		this.codAmts = codAmts;
	}

	/**
	 * @return the baAmts
	 */
	public Double[] getBaAmts() {
		return baAmts;
	}

	/**
	 * @param baAmts the baAmts to set
	 */
	public void setBaAmts(Double[] baAmts) {
		this.baAmts = baAmts;
	}

	/**
	 * @return the priorityServicedOns
	 */
	public String[] getPriorityServicedOns() {
		return priorityServicedOns;
	}

	/**
	 * @param priorityServicedOns the priorityServicedOns to set
	 */
	public void setPriorityServicedOns(String[] priorityServicedOns) {
		this.priorityServicedOns = priorityServicedOns;
	}

	/**
	 * Gets the consg numbers.
	 *
	 * @return the consg numbers
	 */
	public String[] getConsgNumbers() {
		return consgNumbers;
	}

	/**
	 * Sets the consg numbers.
	 *
	 * @param consgNumbers the new consg numbers
	 */
	public void setConsgNumbers(String[] consgNumbers) {
		this.consgNumbers = consgNumbers;
	}

	/**
	 * Gets the pincodes.
	 *
	 * @return the pincodes
	 */
	public String[] getPincodes() {
		return pincodes;
	}

	/**
	 * Sets the pincodes.
	 *
	 * @param pincodes the new pincodes
	 */
	public void setPincodes(String[] pincodes) {
		this.pincodes = pincodes;
	}

	/**
	 * Gets the dest cities.
	 *
	 * @return the dest cities
	 */
	public String[] getDestCities() {
		return destCities;
	}

	/**
	 * Sets the dest cities.
	 *
	 * @param destCities the new dest cities
	 */
	public void setDestCities(String[] destCities) {
		this.destCities = destCities;
	}

	/**
	 * Gets the actual weights.
	 *
	 * @return the actual weights
	 */
	public Double[] getActualWeights() {
		return actualWeights;
	}

	/**
	 * Sets the actual weights.
	 *
	 * @param actualWeights the new actual weights
	 */
	public void setActualWeights(Double[] actualWeights) {
		this.actualWeights = actualWeights;
	}

	/**
	 * Gets the chargeable weights.
	 *
	 * @return the chargeable weights
	 */
	public Double[] getChargeableWeights() {
		return chargeableWeights;
	}

	/**
	 * Sets the chargeable weights.
	 *
	 * @param chargeableWeights the new chargeable weights
	 */
	public void setChargeableWeights(Double[] chargeableWeights) {
		this.chargeableWeights = chargeableWeights;
	}

	/**
	 * Gets the final weights.
	 *
	 * @return the final weights
	 */
	public Double[] getFinalWeights() {
		return finalWeights;
	}

	/**
	 * Sets the final weights.
	 *
	 * @param finalWeights the new final weights
	 */
	public void setFinalWeights(Double[] finalWeights) {
		this.finalWeights = finalWeights;
	}

	/**
	 * Gets the ref nos.
	 *
	 * @return the ref nos
	 */
	public String[] getRefNos() {
		return refNos;
	}

	/**
	 * Sets the ref nos.
	 *
	 * @param refNos the new ref nos
	 */
	public void setRefNos(String[] refNos) {
		this.refNos = refNos;
	}

	/**
	 * Gets the pincode ids.
	 *
	 * @return the pincode ids
	 */
	public Integer[] getPincodeIds() {
		return pincodeIds;
	}

	/**
	 * Sets the pincode ids.
	 *
	 * @param pincodeIds the new pincode ids
	 */
	public void setPincodeIds(Integer[] pincodeIds) {
		this.pincodeIds = pincodeIds;
	}

	/**
	 * Gets the city ids.
	 *
	 * @return the city ids
	 */
	public Integer[] getCityIds() {
		return cityIds;
	}

	/**
	 * Sets the city ids.
	 *
	 * @param cityIds the new city ids
	 */
	public void setCityIds(Integer[] cityIds) {
		this.cityIds = cityIds;
	}

	/**
	 * Gets the booking ids.
	 *
	 * @return the booking ids
	 */
	public Integer[] getBookingIds() {
		return bookingIds;
	}

	/**
	 * Sets the booking ids.
	 *
	 * @param bookingIds the new booking ids
	 */
	public void setBookingIds(Integer[] bookingIds) {
		this.bookingIds = bookingIds;
	}

	/**
	 * Gets the vol weights.
	 *
	 * @return the vol weights
	 */
	public Double[] getVolWeights() {
		return volWeights;
	}

	/**
	 * Sets the vol weights.
	 *
	 * @param volWeights the new vol weights
	 */
	public void setVolWeights(Double[] volWeights) {
		this.volWeights = volWeights;
	}

	/**
	 * Gets the lengths.
	 *
	 * @return the lengths
	 */
	public Double[] getLengths() {
		return lengths;
	}

	/**
	 * Sets the lengths.
	 *
	 * @param lengths the new lengths
	 */
	public void setLengths(Double[] lengths) {
		this.lengths = lengths;
	}

	/**
	 * Gets the heights.
	 *
	 * @return the heights
	 */
	public Double[] getHeights() {
		return heights;
	}

	/**
	 * Sets the heights.
	 *
	 * @param heights the new heights
	 */
	public void setHeights(Double[] heights) {
		this.heights = heights;
	}

	/**
	 * Gets the breaths.
	 *
	 * @return the breaths
	 */
	public Double[] getBreaths() {
		return breaths;
	}

	/**
	 * Sets the breaths.
	 *
	 * @param breaths the new breaths
	 */
	public void setBreaths(Double[] breaths) {
		this.breaths = breaths;
	}

	/**
	 * Gets the weight captured modes.
	 *
	 * @return the weight captured modes
	 */
	public String[] getWeightCapturedModes() {
		return weightCapturedModes;
	}

	/**
	 * Sets the weight captured modes.
	 *
	 * @param weightCapturedModes the new weight captured modes
	 */
	public void setWeightCapturedModes(String[] weightCapturedModes) {
		this.weightCapturedModes = weightCapturedModes;
	}

	/**
	 * Gets the consg pricing dtls.
	 *
	 * @return the consg pricing dtls
	 */
	public String[] getConsgPricingDtls() {
		return consgPricingDtls;
	}

	/**
	 * Sets the consg pricing dtls.
	 *
	 * @param consgPricingDtls the new consg pricing dtls
	 */
	public void setConsgPricingDtls(String[] consgPricingDtls) {
		this.consgPricingDtls = consgPricingDtls;
	}

	/**
	 * Gets the cne address dtls.
	 *
	 * @return the cne address dtls
	 */
	public String[] getCneAddressDtls() {
		return cneAddressDtls;
	}

	/**
	 * Sets the cne address dtls.
	 *
	 * @param cneAddressDtls the new cne address dtls
	 */
	public void setCneAddressDtls(String[] cneAddressDtls) {
		this.cneAddressDtls = cneAddressDtls;
	}

	/**
	 * Gets the cnr address dtls.
	 *
	 * @return the cnr address dtls
	 */
	public String[] getCnrAddressDtls() {
		return cnrAddressDtls;
	}

	/**
	 * Sets the cnr address dtls.
	 *
	 * @param cnrAddressDtls the new cnr address dtls
	 */
	public void setCnrAddressDtls(String[] cnrAddressDtls) {
		this.cnrAddressDtls = cnrAddressDtls;
	}

	/**
	 * Gets the other cn contents.
	 *
	 * @return the otherCNContents
	 */
	public String[] getOtherCNContents() {
		return otherCNContents;
	}

	/**
	 * Sets the other cn contents.
	 *
	 * @param otherCNContents the otherCNContents to set
	 */
	public void setOtherCNContents(String[] otherCNContents) {
		this.otherCNContents = otherCNContents;
	}

	/**
	 * Gets the child cn details.
	 *
	 * @return the child cn details
	 */
	public String[] getChildCNDetails() {
		return childCNDetails;
	}

	/**
	 * Sets the child cn details.
	 *
	 * @param childCNDetails the new child cn details
	 */
	public void setChildCNDetails(String[] childCNDetails) {
		this.childCNDetails = childCNDetails;
	}

	/**
	 * Gets the dlv time map ids.
	 *
	 * @return the dlv time map ids
	 */
	public Integer[] getDlvTimeMapIds() {
		return dlvTimeMapIds;
	}

	/**
	 * Sets the dlv time map ids.
	 *
	 * @param dlvTimeMapIds the new dlv time map ids
	 */
	public void setDlvTimeMapIds(Integer[] dlvTimeMapIds) {
		this.dlvTimeMapIds = dlvTimeMapIds;
	}

	/**
	 * Gets the num of pcs.
	 *
	 * @return the num of pcs
	 */
	public Integer[] getNumOfPcs() {
		return numOfPcs;
	}

	/**
	 * Sets the num of pcs.
	 *
	 * @param numOfPcs the new num of pcs
	 */
	public void setNumOfPcs(Integer[] numOfPcs) {
		this.numOfPcs = numOfPcs;
	}

	/**
	 * Gets the declared values.
	 *
	 * @return the declared values
	 */
	public Double[] getDeclaredValues() {
		return declaredValues;
	}

	/**
	 * Sets the declared values.
	 *
	 * @param declaredValues the new declared values
	 */
	public void setDeclaredValues(Double[] declaredValues) {
		this.declaredValues = declaredValues;
	}

}
