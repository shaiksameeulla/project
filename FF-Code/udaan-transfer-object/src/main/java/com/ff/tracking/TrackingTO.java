/**
 * 
 */
package com.ff.tracking;

import com.ff.consignment.ConsignmentTO;
import com.ff.manifest.ManifestBaseTO;

/**
 * @author uchauhan
 *
 */
public class TrackingTO {
	
	private ConsignmentTO consignmentTO;
	private ManifestBaseTO manifestTO;
	private String artifactType;
	/**
	 * @return the consignmentTO
	 */
	public ConsignmentTO getConsignmentTO() {
		return consignmentTO;
	}
	/**
	 * @param consignmentTO the consignmentTO to set
	 */
	public void setConsignmentTO(ConsignmentTO consignmentTO) {
		this.consignmentTO = consignmentTO;
	}
	/**
	 * @return the artifactType
	 */
	public String getArtifactType() {
		return artifactType;
	}
	/**
	 * @param artifactType the artifactType to set
	 */
	public void setArtifactType(String artifactType) {
		this.artifactType = artifactType;
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
