/**
 * 
 */
package com.ff.to.ratemanagement.operations;

import java.math.BigDecimal;
import java.util.Date;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;

/**
 * @author mohammes
 *
 */
public class StockRateTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7213391401007193152L;
	
	private Integer itemId;
	private Date date;
	private Integer quantity;
	private CityTO cityTO;
	private BigDecimal ratePerUnit;
	
	private Boolean isForPanIndia;
	/** The service tax. */
	private Double serviceTax;
	
	/** The service tax amount. it's Per Rupee*/
	private Double serviceTaxAmount;
	
	/** The edu cess tax. */
	private Double eduCessTax;
	
	/** The edu cess tax amount. it's Per Rupee*/
	private Double eduCessTaxAmount;
	
	/** The hedu cess tax. */
	private Double heduCessTax;
	
	/** The hedu cess tax amount. it's Per Rupee*/
	private Double heduCessTaxAmount;
	
	/** The state tax. StTax applies only For Jammu &Kashmir */
	private Double stateTax; 
	/** The state tax. surcharge on StTax applies only For Jammu &Kashmir */
	private Double surChrgeStateTax;
	/** The state tax. StTax applies only For Jammu &Kashmir it's Per Rupee*/
	private Double stateTaxAmount;
	/** The state tax. surcharge on StTax Amount applies only For Jammu &Kashmir  it's Per Rupee*/
	private Double surChrgeStateTaxAmount;
	
	
	/** The total tax per quantity. it's total applied Tax per Unit Quantity */
	private Double totalTaxPerQuantity;
	/** The total tax per quantity. it's total applied Tax per Unit Quantity per Rupee */
	private Double totalTaxPerQuantityPerRupe;
	
	/** The total amount. After Applying the tax*/
	private Double totalAmount;
	
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}
	/**
	 * @return the ratePerUnit
	 */
	public BigDecimal getRatePerUnit() {
		return ratePerUnit;
	}
	
	
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	/**
	 * @param ratePerUnit the ratePerUnit to set
	 */
	public void setRatePerUnit(BigDecimal ratePerUnit) {
		this.ratePerUnit = ratePerUnit;
	}
	
	
	
	/**
	 * @return the cityTO
	 */
	public CityTO getCityTO() {
		return cityTO;
	}
	/**
	 * @param cityTO the cityTO to set
	 */
	public void setCityTO(CityTO cityTO) {
		this.cityTO = cityTO;
	}
	
	/**
	 * @return the totalTaxPerQuantity
	 */
	public Double getTotalTaxPerQuantity() {
		return totalTaxPerQuantity;
	}
	/**
	 * @return the itemId
	 */
	public Integer getItemId() {
		return itemId;
	}
	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	/**
	 * @param totalTaxPerQuantity the totalTaxPerQuantity to set
	 */
	public void setTotalTaxPerQuantity(Double totalTaxPerQuantity) {
		this.totalTaxPerQuantity = totalTaxPerQuantity;
	}
	
	/**
	 * @return the serviceTax
	 */
	public Double getServiceTax() {
		return serviceTax;
	}
	/**
	 * @return the serviceTaxAmount
	 */
	public Double getServiceTaxAmount() {
		return serviceTaxAmount;
	}
	/**
	 * @return the eduCessTax
	 */
	public Double getEduCessTax() {
		return eduCessTax;
	}
	/**
	 * @return the eduCessTaxAmount
	 */
	public Double getEduCessTaxAmount() {
		return eduCessTaxAmount;
	}
	/**
	 * @return the heduCessTax
	 */
	public Double getHeduCessTax() {
		return heduCessTax;
	}
	/**
	 * @return the heduCessTaxAmount
	 */
	public Double getHeduCessTaxAmount() {
		return heduCessTaxAmount;
	}
	/**
	 * @return the stateTax
	 */
	public Double getStateTax() {
		return stateTax;
	}
	/**
	 * @return the surChrgeStateTax
	 */
	public Double getSurChrgeStateTax() {
		return surChrgeStateTax;
	}
	/**
	 * @return the stateTaxAmount
	 */
	public Double getStateTaxAmount() {
		return stateTaxAmount;
	}
	/**
	 * @return the surChrgeStateTaxAmount
	 */
	public Double getSurChrgeStateTaxAmount() {
		return surChrgeStateTaxAmount;
	}
	
	/**
	 * @param serviceTax the serviceTax to set
	 */
	public void setServiceTax(Double serviceTax) {
		this.serviceTax = serviceTax;
	}
	/**
	 * @param serviceTaxAmount the serviceTaxAmount to set
	 */
	public void setServiceTaxAmount(Double serviceTaxAmount) {
		this.serviceTaxAmount = serviceTaxAmount;
	}
	/**
	 * @param eduCessTax the eduCessTax to set
	 */
	public void setEduCessTax(Double eduCessTax) {
		this.eduCessTax = eduCessTax;
	}
	/**
	 * @param eduCessTaxAmount the eduCessTaxAmount to set
	 */
	public void setEduCessTaxAmount(Double eduCessTaxAmount) {
		this.eduCessTaxAmount = eduCessTaxAmount;
	}
	/**
	 * @param heduCessTax the heduCessTax to set
	 */
	public void setHeduCessTax(Double heduCessTax) {
		this.heduCessTax = heduCessTax;
	}
	/**
	 * @param heduCessTaxAmount the heduCessTaxAmount to set
	 */
	public void setHeduCessTaxAmount(Double heduCessTaxAmount) {
		this.heduCessTaxAmount = heduCessTaxAmount;
	}
	/**
	 * @param stateTax the stateTax to set
	 */
	public void setStateTax(Double stateTax) {
		this.stateTax = stateTax;
	}
	/**
	 * @return the totalTaxPerQuantityPerRupe
	 */
	public Double getTotalTaxPerQuantityPerRupe() {
		return totalTaxPerQuantityPerRupe;
	}
	/**
	 * @return the totalAmount
	 */
	public Double getTotalAmount() {
		return totalAmount;
	}
	/**
	 * @param totalTaxPerQuantityPerRupe the totalTaxPerQuantityPerRupe to set
	 */
	public void setTotalTaxPerQuantityPerRupe(Double totalTaxPerQuantityPerRupe) {
		this.totalTaxPerQuantityPerRupe = totalTaxPerQuantityPerRupe;
	}
	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	/**
	 * @param surChrgeStateTax the surChrgeStateTax to set
	 */
	public void setSurChrgeStateTax(Double surChrgeStateTax) {
		this.surChrgeStateTax = surChrgeStateTax;
	}
	/**
	 * @param stateTaxAmount the stateTaxAmount to set
	 */
	public void setStateTaxAmount(Double stateTaxAmount) {
		this.stateTaxAmount = stateTaxAmount;
	}
	/**
	 * @return the isForPanIndia
	 */
	public Boolean getIsForPanIndia() {
		return isForPanIndia;
	}
	/**
	 * @param isForPanIndia the isForPanIndia to set
	 */
	public void setIsForPanIndia(Boolean isForPanIndia) {
		this.isForPanIndia = isForPanIndia;
	}
	/**
	 * @param surChrgeStateTaxAmount the surChrgeStateTaxAmount to set
	 */
	public void setSurChrgeStateTaxAmount(Double surChrgeStateTaxAmount) {
		this.surChrgeStateTaxAmount = surChrgeStateTaxAmount;
	}

}
