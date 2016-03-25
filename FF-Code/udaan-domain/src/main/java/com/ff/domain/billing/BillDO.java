package com.ff.domain.billing;


import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.ProductDO;

/**
 * The Class BillDO.
 *
 * @author narmdr
 */
public class BillDO extends CGFactDO {
	
	private static final long serialVersionUID = -4962380791888176321L;
	private Integer invoiceId;//billId
	private String invoiceNumber;//billNumber
	private Date fromDate;
	private Date toDate;
	private Integer noOfPickups;
	private String billGenerated;//'N','Y'
	//private String transferStatus;//'N','Y'

	private Double freight;
	private Double riskSurcharge;
	private Double docHandlingCharge;
	private Double parcelHandlingCharge;
	private Double airportHandlingCharge;
	private Double otherCharges;
	private Double total;
	private Double fuelSurcharge;
	private Double serviceTax;
	private Double educationCess;
	private Double higherEducationCess;
	private Double stateTax;
	private Double surchargeOnStateTax;
	private Double grandTotal;
	private Double grandTotalRoundedOff;
	
	private ProductDO productDO;
	private OfficeDO pickupOfficeDO;
	/*private ContractPaymentBillingLocationDO contractPaymentBillingLocationDO;*/
	private String shipToCode;
	private String billStatus;

	/*private Set<BillingConsignmentDO> billingConsignmentDOs;
	private Set<BillingConsignmentSummaryDO> billingConsignmentSummaryDOs;*/
	
	private CustomerDO customer;
	private FinancialProductDO financialProductDO;
	
	private Double fuelSurchargePercentage;
	private String fuelSurchargePercentageFormaula;
	private Date billGenerationDate;
	private Double lcAmount;
	private Double lcCharge;
	private Double codAmount;
	private Double codCharge;
	private Double rtoCharge;
	private String billType;
	
	/**
	 * @return the invoiceId
	 */
	public Integer getInvoiceId() {
		return invoiceId;
	}
	/**
	 * @param invoiceId the invoiceId to set
	 */
	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}
	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the noOfPickups
	 */
	public Integer getNoOfPickups() {
		return noOfPickups;
	}
	/**
	 * @param noOfPickups the noOfPickups to set
	 */
	public void setNoOfPickups(Integer noOfPickups) {
		this.noOfPickups = noOfPickups;
	}
	/**
	 * @return the billGenerated
	 */
	public String getBillGenerated() {
		return billGenerated;
	}
	/**
	 * @param billGenerated the billGenerated to set
	 */
	public void setBillGenerated(String billGenerated) {
		this.billGenerated = billGenerated;
	}
	/**
	 * @return the pickupOfficeDO
	 */
	public OfficeDO getPickupOfficeDO() {
		return pickupOfficeDO;
	}
	/**
	 * @param pickupOfficeDO the pickupOfficeDO to set
	 */
	public void setPickupOfficeDO(OfficeDO pickupOfficeDO) {
		this.pickupOfficeDO = pickupOfficeDO;
	}
	/**
	 * @return the freight
	 */
	public Double getFreight() {
		return freight;
	}
	/**
	 * @param freight the freight to set
	 */
	public void setFreight(Double freight) {
		this.freight = freight;
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
	 * @return the docHandlingCharge
	 */
	public Double getDocHandlingCharge() {
		return docHandlingCharge;
	}
	/**
	 * @param docHandlingCharge the docHandlingCharge to set
	 */
	public void setDocHandlingCharge(Double docHandlingCharge) {
		this.docHandlingCharge = docHandlingCharge;
	}
	/**
	 * @return the parcelHandlingCharge
	 */
	public Double getParcelHandlingCharge() {
		return parcelHandlingCharge;
	}
	/**
	 * @param parcelHandlingCharge the parcelHandlingCharge to set
	 */
	public void setParcelHandlingCharge(Double parcelHandlingCharge) {
		this.parcelHandlingCharge = parcelHandlingCharge;
	}
	/**
	 * @return the airportHandlingCharge
	 */
	public Double getAirportHandlingCharge() {
		return airportHandlingCharge;
	}
	/**
	 * @param airportHandlingCharge the airportHandlingCharge to set
	 */
	public void setAirportHandlingCharge(Double airportHandlingCharge) {
		this.airportHandlingCharge = airportHandlingCharge;
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
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
	}
	/**
	 * @return the fuelSurcharge
	 */
	public Double getFuelSurcharge() {
		return fuelSurcharge;
	}
	/**
	 * @param fuelSurcharge the fuelSurcharge to set
	 */
	public void setFuelSurcharge(Double fuelSurcharge) {
		this.fuelSurcharge = fuelSurcharge;
	}
	/**
	 * @return the serviceTax
	 */
	public Double getServiceTax() {
		return serviceTax;
	}
	/**
	 * @param serviceTax the serviceTax to set
	 */
	public void setServiceTax(Double serviceTax) {
		this.serviceTax = serviceTax;
	}
	/**
	 * @return the educationCess
	 */
	public Double getEducationCess() {
		return educationCess;
	}
	/**
	 * @param educationCess the educationCess to set
	 */
	public void setEducationCess(Double educationCess) {
		this.educationCess = educationCess;
	}
	/**
	 * @return the higherEducationCess
	 */
	public Double getHigherEducationCess() {
		return higherEducationCess;
	}
	/**
	 * @param higherEducationCess the higherEducationCess to set
	 */
	public void setHigherEducationCess(Double higherEducationCess) {
		this.higherEducationCess = higherEducationCess;
	}
	/**
	 * @return the stateTax
	 */
	public Double getStateTax() {
		return stateTax;
	}
	/**
	 * @param stateTax the stateTax to set
	 */
	public void setStateTax(Double stateTax) {
		this.stateTax = stateTax;
	}
	/**
	 * @return the surchargeOnStateTax
	 */
	public Double getSurchargeOnStateTax() {
		return surchargeOnStateTax;
	}
	/**
	 * @param surchargeOnStateTax the surchargeOnStateTax to set
	 */
	public void setSurchargeOnStateTax(Double surchargeOnStateTax) {
		this.surchargeOnStateTax = surchargeOnStateTax;
	}
	/**
	 * @return the grandTotal
	 */
	public Double getGrandTotal() {
		return grandTotal;
	}
	/**
	 * @param grandTotal the grandTotal to set
	 */
	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}
	/**
	 * @return the grandTotalRoundedOff
	 */
	public Double getGrandTotalRoundedOff() {
		return grandTotalRoundedOff;
	}
	/**
	 * @param grandTotalRoundedOff the grandTotalRoundedOff to set
	 */
	public void setGrandTotalRoundedOff(Double grandTotalRoundedOff) {
		this.grandTotalRoundedOff = grandTotalRoundedOff;
	}
	/**
	 * @return the billingConsignmentDOs
	 */
	/*public Set<BillingConsignmentDO> getBillingConsignmentDOs() {
		return billingConsignmentDOs;
	}
	*//**
	 * @param billingConsignmentDOs the billingConsignmentDOs to set
	 *//*
	public void setBillingConsignmentDOs(
			Set<BillingConsignmentDO> billingConsignmentDOs) {
		this.billingConsignmentDOs = billingConsignmentDOs;
	}
	*//**
	 * @return the billingConsignmentSummaryDOs
	 *//*
	public Set<BillingConsignmentSummaryDO> getBillingConsignmentSummaryDOs() {
		return billingConsignmentSummaryDOs;
	}
	*//**
	 * @param billingConsignmentSummaryDOs the billingConsignmentSummaryDOs to set
	 *//*
	public void setBillingConsignmentSummaryDOs(
			Set<BillingConsignmentSummaryDO> billingConsignmentSummaryDOs) {
		this.billingConsignmentSummaryDOs = billingConsignmentSummaryDOs;
	}*/
	/**
	 * @return the shipToCode
	 */
	public String getShipToCode() {
		return shipToCode;
	}
	/**
	 * @param shipToCode the shipToCode to set
	 */
	public void setShipToCode(String shipToCode) {
		this.shipToCode = shipToCode;
	}
	/**
	 * @return the billStatus
	 */
	public String getBillStatus() {
	    return billStatus;
	}
	/**
	 * @param billStatus the billStatus to set
	 */
	public void setBillStatus(String billStatus) {
	    this.billStatus = billStatus;
	}
	/**
	 * @return the customer
	 */
	public CustomerDO getCustomer() {
	    return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(CustomerDO customer) {
	    this.customer = customer;
	}
	public FinancialProductDO getFinancialProductDO() {
		return financialProductDO;
	}
	public void setFinancialProductDO(FinancialProductDO financialProductDO) {
		this.financialProductDO = financialProductDO;
	}
	public ProductDO getProductDO() {
		return productDO;
	}
	public void setProductDO(ProductDO productDO) {
		this.productDO = productDO;
	}
	public Double getFuelSurchargePercentage() {
		return fuelSurchargePercentage;
	}
	public void setFuelSurchargePercentage(Double fuelSurchargePercentage) {
		this.fuelSurchargePercentage = fuelSurchargePercentage;
	}
	public String getFuelSurchargePercentageFormaula() {
		return fuelSurchargePercentageFormaula;
	}
	public void setFuelSurchargePercentageFormaula(
			String fuelSurchargePercentageFormaula) {
		this.fuelSurchargePercentageFormaula = fuelSurchargePercentageFormaula;
	}
	public Date getBillGenerationDate() {
		return billGenerationDate;
	}
	public void setBillGenerationDate(Date billGenerationDate) {
		this.billGenerationDate = billGenerationDate;
	}
	public Double getLcAmount() {
		return lcAmount;
	}
	public void setLcAmount(Double lcAmount) {
		this.lcAmount = lcAmount;
	}
	public Double getLcCharge() {
		return lcCharge;
	}
	public void setLcCharge(Double lcCharge) {
		this.lcCharge = lcCharge;
	}
	public Double getCodAmount() {
		return codAmount;
	}
	public void setCodAmount(Double codAmount) {
		this.codAmount = codAmount;
	}
	public Double getCodCharge() {
		return codCharge;
	}
	public void setCodCharge(Double codCharge) {
		this.codCharge = codCharge;
	}
	public Double getRtoCharge() {
		return rtoCharge;
	}
	public void setRtoCharge(Double rtoCharge) {
		this.rtoCharge = rtoCharge;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}	

	
	
}
