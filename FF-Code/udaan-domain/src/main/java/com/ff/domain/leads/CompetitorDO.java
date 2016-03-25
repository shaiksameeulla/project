/**
 * 
 */
package com.ff.domain.leads;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author abarudwa
 *
 */
public class CompetitorDO extends CGFactDO
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer competitorId;
	
	private String competitorName;

	/**
	 * @return the competitorId
	 */
	public Integer getCompetitorId() {
		return competitorId;
	}

	/**
	 * @param competitorId the competitorId to set
	 */
	public void setCompetitorId(Integer competitorId) {
		this.competitorId = competitorId;
	}

	/**
	 * @return the competitorName
	 */
	public String getCompetitorName() {
		return competitorName;
	}

	/**
	 * @param competitorName the competitorName to set
	 */
	public void setCompetitorName(String competitorName) {
		this.competitorName = competitorName;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
