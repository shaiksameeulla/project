package com.ff.to.billing;

import java.util.Date;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class BillTO.
 *
 * @author narmdr
 */
public class BillAliasTO extends CGBaseTO {
	
    /**
     * 
     */
    private static final long serialVersionUID = 8698100788540427702L;
    private Integer invoiceId;
    private String invoiceNumber;
    private Date createdDate;
    private Date fromDate;
    private Date toDate;
    private Integer numberOfPickups;
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
    private String billingOfficeName;
    private String billingOfficePhone;
    private String billingRHOOfficeName;
    private String billingRHOOfficeAddress1;
    private String billingRHOOfficeAddress2;
    private String billingRHOOfficeAddress3;
    private String billingRHOOfficePhone;
    private String billingRHOOfficeEmail;
    private String rhoCityName;
    private Integer customerId;
    private String customerBusinessName;
    private String customerCode;
    private String customerAddress1;
    private String customerAddress2;
    private String customerAddress3;
    private String customerTypeCode;
    private Integer productId;
    private String productSeries;
    private Integer financialProductId;
    private String stateCode;
    private Double fuelSurchargePercentage;
    private String fuelSurchargePercentageFormula;
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
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }
    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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
     * @return the fuelCurcharge
     */
    public Double getFuelSurcharge() {
        return fuelSurcharge;
    }
    /**
     * @param fuelCurcharge the fuelCurcharge to set
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
     * @return the billingOfficeName
     */
    public String getBillingOfficeName() {
        return billingOfficeName;
    }
    /**
     * @param billingOfficeName the billingOfficeName to set
     */
    public void setBillingOfficeName(String billingOfficeName) {
        this.billingOfficeName = billingOfficeName;
    }
    /**
     * @return the billingOfficePhone
     */
    public String getBillingOfficePhone() {
        return billingOfficePhone;
    }
    /**
     * @param billingOfficePhone the billingOfficePhone to set
     */
    public void setBillingOfficePhone(String billingOfficePhone) {
        this.billingOfficePhone = billingOfficePhone;
    }
    /**
     * @return the billingRHOOfficeName
     */
    public String getBillingRHOOfficeName() {
        return billingRHOOfficeName;
    }
    /**
     * @param billingRHOOfficeName the billingRHOOfficeName to set
     */
    public void setBillingRHOOfficeName(String billingRHOOfficeName) {
        this.billingRHOOfficeName = billingRHOOfficeName;
    }
    /**
	 * @return the billingRHOOfficeAddress1
	 */
	public String getBillingRHOOfficeAddress1() {
		return billingRHOOfficeAddress1;
	}
	/**
	 * @param billingRHOOfficeAddress1 the billingRHOOfficeAddress1 to set
	 */
	public void setBillingRHOOfficeAddress1(String billingRHOOfficeAddress1) {
		this.billingRHOOfficeAddress1 = billingRHOOfficeAddress1;
	}
	/**
	 * @return the billingRHOOfficeAddress2
	 */
	public String getBillingRHOOfficeAddress2() {
		return billingRHOOfficeAddress2;
	}
	/**
	 * @param billingRHOOfficeAddress2 the billingRHOOfficeAddress2 to set
	 */
	public void setBillingRHOOfficeAddress2(String billingRHOOfficeAddress2) {
		this.billingRHOOfficeAddress2 = billingRHOOfficeAddress2;
	}
	/**
	 * @return the billingRHOOfficeAddress3
	 */
	public String getBillingRHOOfficeAddress3() {
		return billingRHOOfficeAddress3;
	}
	/**
	 * @param billingRHOOfficeAddress3 the billingRHOOfficeAddress3 to set
	 */
	public void setBillingRHOOfficeAddress3(String billingRHOOfficeAddress3) {
		this.billingRHOOfficeAddress3 = billingRHOOfficeAddress3;
	}
    /**
     * @return the billingRHOOfficePhone
     */
    public String getBillingRHOOfficePhone() {
        return billingRHOOfficePhone;
    }
    /**
     * @param billingRHOOfficePhone the billingRHOOfficePhone to set
     */
    public void setBillingRHOOfficePhone(String billingRHOOfficePhone) {
        this.billingRHOOfficePhone = billingRHOOfficePhone;
    }
    /**
	 * @return the billingRHOOfficeEmail
	 */
	public String getBillingRHOOfficeEmail() {
		return billingRHOOfficeEmail;
	}
	/**
	 * @param billingRHOOfficeEmail the billingRHOOfficeEmail to set
	 */
	public void setBillingRHOOfficeEmail(String billingRHOOfficeEmail) {
		this.billingRHOOfficeEmail = billingRHOOfficeEmail;
	}
	/**
     * @return the rhoCityName
     */
    public String getRhoCityName() {
        return rhoCityName;
    }
    /**
     * @param rhoCityName the rhoCityName to set
     */
    public void setRhoCityName(String rhoCityName) {
        this.rhoCityName = rhoCityName;
    }
    /**
     * @return the customerId
     */
    public Integer getCustomerId() {
        return customerId;
    }
    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    /**
     * @return the customerBusinessName
     */
    public String getCustomerBusinessName() {
        return customerBusinessName;
    }
    /**
     * @param customerBusinessName the customerBusinessName to set
     */
    public void setCustomerBusinessName(String customerBusinessName) {
        this.customerBusinessName = customerBusinessName;
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
     * @return the customerAddress1
     */
    public String getCustomerAddress1() {
        return customerAddress1;
    }
    /**
     * @param customerAddress1 the customerAddress1 to set
     */
    public void setCustomerAddress1(String customerAddress1) {
        this.customerAddress1 = customerAddress1;
    }
    /**
     * @return the customerAddress2
     */
    public String getCustomerAddress2() {
        return customerAddress2;
    }
    /**
     * @param customerAddress2 the customerAddress2 to set
     */
    public void setCustomerAddress2(String customerAddress2) {
        this.customerAddress2 = customerAddress2;
    }
    /**
     * @return the customerAddress3
     */
    public String getCustomerAddress3() {
        return customerAddress3;
    }
    /**
     * @param customerAddress3 the customerAddress3 to set
     */
    public void setCustomerAddress3(String customerAddress3) {
        this.customerAddress3 = customerAddress3;
    }
    /**
     * @return the customerTypeCode
     */
    public String getCustomerTypeCode() {
        return customerTypeCode;
    }
    /**
     * @param customerTypeCode the customerTypeCode to set
     */
    public void setCustomerTypeCode(String customerTypeCode) {
        this.customerTypeCode = customerTypeCode;
    }
    /**
     * @return the productId
     */
    public Integer getProductId() {
        return productId;
    }
    /**
     * @param productId the productId to set
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    /**
     * @return the productSeries
     */
    public String getProductSeries() {
        return productSeries;
    }
    /**
     * @param productSeries the productSeries to set
     */
    public void setProductSeries(String productSeries) {
        this.productSeries = productSeries;
    }
    /**
     * @return the financialProductId
     */
    public Integer getFinancialProductId() {
        return financialProductId;
    }
    /**
     * @param financialProductId the financialProductId to set
     */
    public void setFinancialProductId(Integer financialProductId) {
        this.financialProductId = financialProductId;
    }
    /**
     * @return the numberOfPickups
     */
    public Integer getNumberOfPickups() {
	return numberOfPickups;
    }
    /**
     * @param numberOfPickups the numberOfPickups to set
     */
    public void setNumberOfPickups(Integer numberOfPickups) {
	this.numberOfPickups = numberOfPickups;
    }
    /**
     * @return the stateCode
     */
    public String getStateCode() {
        return stateCode;
    }
    /**
     * @param stateCode the stateCode to set
     */
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }
    /**
     * @return the fuelSurchargePercentage
     */
    public Double getFuelSurchargePercentage() {
        return fuelSurchargePercentage;
    }
    /**
     * @param fuelSurchargePercentage the fuelSurchargePercentage to set
     */
    public void setFuelSurchargePercentage(Double fuelSurchargePercentage) {
        this.fuelSurchargePercentage = fuelSurchargePercentage;
    }
	public String getFuelSurchargePercentageFormula() {
		return fuelSurchargePercentageFormula;
	}
	public void setFuelSurchargePercentageFormula(
			String fuelSurchargePercentageFormula) {
		this.fuelSurchargePercentageFormula = fuelSurchargePercentageFormula;
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
