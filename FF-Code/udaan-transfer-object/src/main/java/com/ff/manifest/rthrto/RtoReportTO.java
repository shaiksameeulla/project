/**
 * 
 */
package com.ff.manifest.rthrto;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.OfficeTO;

/**
 * The Class RthRtoManifestTO.
 *
 * @author narmdr
 */
public class RtoReportTO extends CGBaseTO {

	private static final long serialVersionUID = -8550820025459507042L;	

	private OfficeTO originOfficeTO; //consg origin office
	private OfficeTO loggedInOfficeTO;
	private List<RtoConsignmentReportTO> rtoConsignmentReportTOs;
	
	/**
	 * @return the originOfficeTO
	 */
	public OfficeTO getOriginOfficeTO() {
		return originOfficeTO;
	}
	/**
	 * @param originOfficeTO the originOfficeTO to set
	 */
	public void setOriginOfficeTO(OfficeTO originOfficeTO) {
		this.originOfficeTO = originOfficeTO;
	}
	/**
	 * @return the loggedInOfficeTO
	 */
	public OfficeTO getLoggedInOfficeTO() {
		return loggedInOfficeTO;
	}
	/**
	 * @param loggedInOfficeTO the loggedInOfficeTO to set
	 */
	public void setLoggedInOfficeTO(OfficeTO loggedInOfficeTO) {
		this.loggedInOfficeTO = loggedInOfficeTO;
	}
	/**
	 * @return the rtoConsignmentReportTOs
	 */
	public List<RtoConsignmentReportTO> getRtoConsignmentReportTOs() {
		return rtoConsignmentReportTOs;
	}
	/**
	 * @param rtoConsignmentReportTOs the rtoConsignmentReportTOs to set
	 */
	public void setRtoConsignmentReportTOs(
			List<RtoConsignmentReportTO> rtoConsignmentReportTOs) {
		this.rtoConsignmentReportTOs = rtoConsignmentReportTOs;
	}	
}
