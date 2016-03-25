/**
 * 
 */
package com.ff.leads;

import java.math.BigDecimal;
import java.util.Date;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.EmployeeTO;

/**
 * @author abarudwa
 *
 */
public class CompetitorListTO extends CGBaseTO
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CompetitorTO competitor;
	
	private ProductTO product;
	
	private BigDecimal potential;
	
	private BigDecimal expectedVolume;
	
	private EmployeeTO createdByEmployeeTO;
	
	private Date createdDate;
	
	private EmployeeTO updatedByEmployeeTO;
	
	private Date updatedDate;
	
	private String date;
	
	private Integer leadId;
	
	private Integer leadCompetitorId;
	
	


	/**
	 * @return the leadCompetitorId
	 */
	public Integer getLeadCompetitorId() {
		return leadCompetitorId;
	}

	/**
	 * @param leadCompetitorId the leadCompetitorId to set
	 */
	public void setLeadCompetitorId(Integer leadCompetitorId) {
		this.leadCompetitorId = leadCompetitorId;
	}

	/**
	 * @return the leadId
	 */
	public Integer getLeadId() {
		return leadId;
	}

	/**
	 * @param leadId the leadId to set
	 */
	public void setLeadId(Integer leadId) {
		this.leadId = leadId;
	}

	/**
	 * @return the competitor
	 */
	public CompetitorTO getCompetitor() {
		return competitor;
	}

	/**
	 * @param competitor the competitor to set
	 */
	public void setCompetitor(CompetitorTO competitor) {
		this.competitor = competitor;
	}

	/**
	 * @return the product
	 */
	public ProductTO getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(ProductTO product) {
		this.product = product;
	}

	/**
	 * @return the createdByEmployeeTO
	 */
	public EmployeeTO getCreatedByEmployeeTO() {
		return createdByEmployeeTO;
	}

	/**
	 * @param createdByEmployeeTO the createdByEmployeeTO to set
	 */
	public void setCreatedByEmployeeTO(EmployeeTO createdByEmployeeTO) {
		this.createdByEmployeeTO = createdByEmployeeTO;
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
	 * @return the updatedByEmployeeTO
	 */
	public EmployeeTO getUpdatedByEmployeeTO() {
		return updatedByEmployeeTO;
	}

	/**
	 * @param updatedByEmployeeTO the updatedByEmployeeTO to set
	 */
	public void setUpdatedByEmployeeTO(EmployeeTO updatedByEmployeeTO) {
		this.updatedByEmployeeTO = updatedByEmployeeTO;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the potential
	 */
	public BigDecimal getPotential() {
		return potential;
	}

	/**
	 * @param potential the potential to set
	 */
	public void setPotential(BigDecimal potential) {
		this.potential = potential;
	}

	/**
	 * @return the expectedVolume
	 */
	public BigDecimal getExpectedVolume() {
		return expectedVolume;
	}

	/**
	 * @param expectedVolume the expectedVolume to set
	 */
	public void setExpectedVolume(BigDecimal expectedVolume) {
		this.expectedVolume = expectedVolume;
	}
	
	
}
