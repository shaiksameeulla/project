/**
 * 
 */
package com.ff.domain.leads;

import java.math.BigDecimal;
import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.organization.EmployeeDO;

/**
 * @author abarudwa
 *
 */
public class LeadCompetitorDO extends CGFactDO
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer leadCompetitorId;
	
	private LeadDO leadDO;
	
	private String leadProductCode;
	
	private CompetitorDO competitorDO;
	
	private BigDecimal potential = BigDecimal.ZERO;
	
	private BigDecimal expectedVolume = BigDecimal.ZERO;
	
	private EmployeeDO createdByEmployeeDO;
	
	private Date createdDate;
	
	private EmployeeDO updatedByEmployeeDO;
	
	private Date updatedDate;

	public String getLeadProductCode() {
		return leadProductCode;
	}

	public void setLeadProductCode(String leadProductCode) {
		this.leadProductCode = leadProductCode;
	}

	public CompetitorDO getCompetitorDO() {
		return competitorDO;
	}

	public void setCompetitorDO(CompetitorDO competitorDO) {
		this.competitorDO = competitorDO;
	}

	public EmployeeDO getCreatedByEmployeeDO() {
		return createdByEmployeeDO;
	}

	public void setCreatedByEmployeeDO(EmployeeDO createdByEmployeeDO) {
		this.createdByEmployeeDO = createdByEmployeeDO;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public EmployeeDO getUpdatedByEmployeeDO() {
		return updatedByEmployeeDO;
	}

	public void setUpdatedByEmployeeDO(EmployeeDO updatedByEmployeeDO) {
		this.updatedByEmployeeDO = updatedByEmployeeDO;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

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



	public LeadDO getLeadDO() {
		return leadDO;
	}

	public void setLeadDO(LeadDO leadDO) {
		this.leadDO = leadDO;
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

/*	public LeadProductDO getLeadProductDO() {
		return leadProductDO;
	}

	public void setLeadProductDO(LeadProductDO leadProductDO) {
		this.leadProductDO = leadProductDO;
	}

	*//**
	 * @return the competitor
	 *//*
	public CompetitorDO getCompetitor() {
		return competitor;
	}

	*//**
	 * @param competitor the competitor to set
	 *//*
	public void setCompetitor(CompetitorDO competitor) {
		this.competitor = competitor;
	}*/

	

}
