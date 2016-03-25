package com.ff.to.rate;

import java.util.List;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;
import com.ff.to.ratemanagement.operations.ratequotation.EBRateConfigTO;

/**
 * @author prmeher May 24, 2013
 * 
 */
public class RateCalculationInputTO extends CGBaseTO {

	private static final long serialVersionUID = 7315612294276168606L;

	/**
     * Input for rate Calculation
     */
    /**
     * FR- franchisee, CC-credit customer, BA-business associate, CH- cash
     */
     private String rateType="";
     /** consignment Type */
     private String consignmentType="";
     /** Product Code */
     private String productCode="";
     /** Destination pincode */
     private String destinationPincode="";
     /** consignment weight */
     private Double weight=0.0;
     /** declaredValue */
     private Double declaredValue=0.0;
     /** other charges */
     private Double otherCharges=0.0;
     /** Discount */
     private Double discount=0.0;
     /** RTO value */
     private String isRTO="";
     /** Customer code */
     private String customerCode="";
     /** insured by */
     private String insuredBy="";
     /** origin city code */
     private String originCityCode="";
     /** LC Amount */
     private Double lcAmount=0.0;
     /** Serviced On */
     private String serviceOn="";
     /** calculation date */
     private String calculationRequestDate="";
     /** COD Amount */
     private Double codAmount=0.0;
     /** EB preferences */
     private String ebPreference="";
     /** To pay Charge */
     private Double toPayCharge=0.0;
     
     /** Octroi Amounts */
     private List<Double> octroiAmountList;
     
     /** Octroi States */
     private List<Integer> octroiStateList;
     
     /** Octroi Amount */
     private Double octroiAmount;
     
     /** Octroi State */
     private Integer octroiState;
     
     private Integer cnLimit;
     
     
     //For internal use of Rate Calculation
     private Double riskSurcharge=0.0;
     private Integer rateQuotationProductCategoryHeader=0;
     private String rateProductCategory;
     private CityTO originCityTO;
     private CityTO destCityTO;
     public EBRateConfigTO ebRateConfigTO;
     private String octroiBourneBy;
     private String rateCalculationFOR;
     
     private String IsAirportHandlingChrgApplicable = CommonConstants.NO;     
     
     
     /**
	 * @return the isAirportHandlingChrgApplicable
	 */
	public String getIsAirportHandlingChrgApplicable() {
		return IsAirportHandlingChrgApplicable;
	}
	/**
	 * @param isAirportHandlingChrgApplicable the isAirportHandlingChrgApplicable to set
	 */
	public void setIsAirportHandlingChrgApplicable(
			String isAirportHandlingChrgApplicable) {
		IsAirportHandlingChrgApplicable = isAirportHandlingChrgApplicable;
	}
	/**
	 * @return the cnLimit
	 */
	public Integer getCnLimit() {
		return cnLimit;
	}
	/**
	 * @param cnLimit the cnLimit to set
	 */
	public void setCnLimit(Integer cnLimit) {
		this.cnLimit = cnLimit;
	}
	/**
	 * @return the rateCalculationFOR
	 */
	public String getRateCalculationFOR() {
		return rateCalculationFOR;
	}
	/**
	 * @param rateCalculationFOR the rateCalculationFOR to set
	 */
	public void setRateCalculationFOR(String rateCalculationFOR) {
		this.rateCalculationFOR = rateCalculationFOR;
	}
	/**
	 * @return the rateType
	 */
	public String getRateType() {
		return rateType;
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
	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	/**
	 * @return the consignmentType
	 */
	public String getConsignmentType() {
		return consignmentType;
	}
	/**
	 * @param consignmentType the consignmentType to set
	 */
	public void setConsignmentType(String consignmentType) {
		this.consignmentType = consignmentType;
	}
	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	/**
	 * @return the destinationPincode
	 */
	public String getDestinationPincode() {
		return destinationPincode;
	}
	/**
	 * @param destinationPincode the destinationPincode to set
	 */
	public void setDestinationPincode(String destinationPincode) {
		this.destinationPincode = destinationPincode;
	}
	/**
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	/**
	 * @return the declaredValue
	 */
	public Double getDeclaredValue() {
		return declaredValue;
	}
	/**
	 * @param declaredValue the declaredValue to set
	 */
	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}
	/**
	 * @return the otherCharges
	 */
	public Double getOtherCharges() {
		return otherCharges;
	}
	/**
	 * @param otherCharges the otherCharges to set
	 */
	public void setOtherCharges(Double otherCharges) {
		this.otherCharges = otherCharges;
	}
	/**
	 * @return the discount
	 */
	public Double getDiscount() {
		return discount;
	}
	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	/**
	 * @return the isRTO
	 */
	public String getIsRTO() {
		return isRTO;
	}
	/**
	 * @param isRTO the isRTO to set
	 */
	public void setIsRTO(String isRTO) {
		this.isRTO = isRTO;
	}
	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * @return the insuredBy
	 */
	public String getInsuredBy() {
		return insuredBy;
	}
	/**
	 * @param insuredBy the insuredBy to set
	 */
	public void setInsuredBy(String insuredBy) {
		this.insuredBy = insuredBy;
	}
	/**
	 * @return the originCityCode
	 */
	public String getOriginCityCode() {
		return originCityCode;
	}
	/**
	 * @param originCityCode the originCityCode to set
	 */
	public void setOriginCityCode(String originCityCode) {
		this.originCityCode = originCityCode;
	}
	/**
	 * @return the lcAmount
	 */
	public Double getLcAmount() {
		return lcAmount;
	}
	/**
	 * @param lcAmount the lcAmount to set
	 */
	public void setLcAmount(Double lcAmount) {
		this.lcAmount = lcAmount;
	}
	/**
	 * @return the serviceOn
	 */
	public String getServiceOn() {
		return serviceOn;
	}
	/**
	 * @param serviceOn the serviceOn to set
	 */
	public void setServiceOn(String serviceOn) {
		this.serviceOn = serviceOn;
	}
	/**
	 * @return the calculationRequestDate
	 */
	public String getCalculationRequestDate() {
		return calculationRequestDate;
	}
	/**
	 * @param calculationRequestDate the calculationRequestDate to set
	 */
	public void setCalculationRequestDate(String calculationRequestDate) {
		this.calculationRequestDate = calculationRequestDate;
	}
	/**
	 * @return the codAmount
	 */
	public Double getCodAmount() {
		return codAmount;
	}
	/**
	 * @param codAmount the codAmount to set
	 */
	public void setCodAmount(Double codAmount) {
		this.codAmount = codAmount;
	}
	/**
	 * @return the ebPreference
	 */
	public String getEbPreference() {
		return ebPreference;
	}
	/**
	 * @param ebPreference the ebPreference to set
	 */
	public void setEbPreference(String ebPreference) {
		this.ebPreference = ebPreference;
	}
	/**
	 * @return the toPayCharge
	 */
	public Double getToPayCharge() {
		return toPayCharge;
	}
	/**
	 * @param toPayCharge the toPayCharge to set
	 */
	public void setToPayCharge(Double toPayCharge) {
		this.toPayCharge = toPayCharge;
	}
	/**
	 * @return the riskSurcharge
	 */
	public Double getRiskSurcharge() {
		return riskSurcharge;
	}
	/**
	 * @param riskSurcharge the riskSurcharge to set
	 */
	public void setRiskSurcharge(Double riskSurcharge) {
		this.riskSurcharge = riskSurcharge;
	}
	/**
	 * @return the rateQuotationProductCategoryHeader
	 */
	public Integer getRateQuotationProductCategoryHeader() {
		return rateQuotationProductCategoryHeader;
	}
	/**
	 * @param rateQuotationProductCategoryHeader the rateQuotationProductCategoryHeader to set
	 */
	public void setRateQuotationProductCategoryHeader(
			Integer rateQuotationProductCategoryHeader) {
		this.rateQuotationProductCategoryHeader = rateQuotationProductCategoryHeader;
	}
	/**
	 * @return the rateProductCategory
	 */
	public String getRateProductCategory() {
		return rateProductCategory;
	}
	/**
	 * @param rateProductCategory the rateProductCategory to set
	 */
	public void setRateProductCategory(String rateProductCategory) {
		this.rateProductCategory = rateProductCategory;
	}
	/**
	 * @return the originCityTO
	 */
	public CityTO getOriginCityTO() {
		return originCityTO;
	}
	/**
	 * @param originCityTO the originCityTO to set
	 */
	public void setOriginCityTO(CityTO originCityTO) {
		this.originCityTO = originCityTO;
	}
	/**
	 * @return the destCityTO
	 */
	public CityTO getDestCityTO() {
		return destCityTO;
	}
	/**
	 * @param destCityTO the destCityTO to set
	 */
	public void setDestCityTO(CityTO destCityTO) {
		this.destCityTO = destCityTO;
	}
	/**
	 * @return the ebRateConfigTO
	 */
	public EBRateConfigTO getEbRateConfigTO() {
		return ebRateConfigTO;
	}
	/**
	 * @param ebRateConfigTO the ebRateConfigTO to set
	 */
	public void setEbRateConfigTO(EBRateConfigTO ebRateConfigTO) {
		this.ebRateConfigTO = ebRateConfigTO;
	}
	/**
	 * @return the octroiAmountList
	 */
	public List<Double> getOctroiAmountList() {
		return octroiAmountList;
	}
	/**
	 * @param octroiAmountList the octroiAmountList to set
	 */
	public void setOctroiAmountList(List<Double> octroiAmountList) {
		this.octroiAmountList = octroiAmountList;
	}
	/**
	 * @return the octroiStateList
	 */
	public List<Integer> getOctroiStateList() {
		return octroiStateList;
	}
	/**
	 * @param octroiStateList the octroiStateList to set
	 */
	public void setOctroiStateList(List<Integer> octroiStateList) {
		this.octroiStateList = octroiStateList;
	}
	/**
	 * @return the octroiAmount
	 */
	public Double getOctroiAmount() {
		return octroiAmount;
	}
	/**
	 * @param octroiAmount the octroiAmount to set
	 */
	public void setOctroiAmount(Double octroiAmount) {
		this.octroiAmount = octroiAmount;
	}
	
	/**
	 * @return the octroiState
	 */
	public Integer getOctroiState() {
		return octroiState;
	}
	/**
	 * @param octroiState the octroiState to set
	 */
	public void setOctroiState(Integer octroiState) {
		this.octroiState = octroiState;
	}
	
}
