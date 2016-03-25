/**
 * 
 */
package com.ff.tracking;

import com.ff.consignment.ConsignmentTO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.organization.OfficeTO;

/**
 * @author uchauhan
 *
 */
public class TrackingProcessTO {
	
	private ProcessMapTO processMapTO;
	private OfficeTO officeTO;//Logged in office ID
	private ConsignmentTO consgTO;//Need Consg NUmber,Origin Office Id,Dest City id
	private ManifestBaseTO manifestTO;
	
	
	/**
	 * @return the processMapTO
	 */
	public ProcessMapTO getProcessMapTO() {
		return processMapTO;
	}

	/**
	 * @param processMapTO the processMapTO to set
	 */
	public void setProcessMapTO(ProcessMapTO processMapTO) {
		this.processMapTO = processMapTO;
	}
	/**
	 * @return the officeTO
	 */
	public OfficeTO getOfficeTO() {
		return officeTO;
	}
	/**
	 * @param officeTO the officeTO to set
	 */
	public void setOfficeTO(OfficeTO officeTO) {
		this.officeTO = officeTO;
	}
	/**
	 * @return the consgTO
	 */
	public ConsignmentTO getConsgTO() {
		return consgTO;
	}
	/**
	 * @param consgTO the consgTO to set
	 */
	public void setConsgTO(ConsignmentTO consgTO) {
		this.consgTO = consgTO;
	}
	/**
	 * @return the manifestTO
	 */
	public ManifestBaseTO getManifestTO() {
		return manifestTO;
	}
	/**
	 * @param manifestTO the manifestTO to set
	 */
	public void setManifestTO(ManifestBaseTO manifestTO) {
		this.manifestTO = manifestTO;
	}


}
