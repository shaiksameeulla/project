/**
 * 
 */
package com.ff.leads;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.OfficeTO;

/**
 * @author abarudwa
 *
 */
public class CompetitorTO extends CGBaseTO implements Comparable<CompetitorTO> 
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

	@Override
	public int compareTo(CompetitorTO obj) {
		// TODO Auto-generated method stub
		int returnVal = 0;
		if (StringUtils.isNotEmpty(this.competitorName)) {
			returnVal = this.competitorName.compareTo(obj.competitorName);
		}
		return returnVal;
	}
	
	

}
