/**
 * 
 */
package com.ff.domain.manifest;

import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.consignment.ConsignmentDOXDO;

/**
 * @author prmeher
 *
 */
public class GridItemOrderDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2735344762139706679L;
	
	private String gridPosition;
	private String[] consignments;
	private String[] comails;
	private String[] manifests;
	private Set<ConsignmentDO> consignmentDOs;
	private Set<ManifestDO> manifestDos;
	private Set<ComailDO> comailDOs;
	private Set<ConsignmentDOXDO> consignmentDOXDOs;
	private String isComailOnly = "N";
	
	
	/**
	 * @return the isComailOnly
	 */
	public String getIsComailOnly() {
		return isComailOnly;
	}
	/**
	 * @param isComailOnly the isComailOnly to set
	 */
	public void setIsComailOnly(String isComailOnly) {
		this.isComailOnly = isComailOnly;
	}
	/**
	 * @return the gridPosition
	 */
	public String getGridPosition() {
		return gridPosition;
	}
	/**
	 * @param gridPosition the gridPosition to set
	 */
	public void setGridPosition(String gridPosition) {
		this.gridPosition = gridPosition;
	}
	/**
	 * @return the consignments
	 */
	public String[] getConsignments() {
		return consignments;
	}
	/**
	 * @param consignments the consignments to set
	 */
	public void setConsignments(String[] consignments) {
		this.consignments = consignments;
	}
	/**
	 * @return the comails
	 */
	public String[] getComails() {
		return comails;
	}
	/**
	 * @param comails the comails to set
	 */
	public void setComails(String[] comails) {
		this.comails = comails;
	}
	/**
	 * @return the manifests
	 */
	public String[] getManifests() {
		return manifests;
	}
	/**
	 * @param manifests the manifests to set
	 */
	public void setManifests(String[] manifests) {
		this.manifests = manifests;
	}
	/**
	 * @return the consignmentDOs
	 */
	public Set<ConsignmentDO> getConsignmentDOs() {
		return consignmentDOs;
	}
	/**
	 * @param consignmentDOs the consignmentDOs to set
	 */
	public void setConsignmentDOs(Set<ConsignmentDO> consignmentDOs) {
		this.consignmentDOs = consignmentDOs;
	}
	/**
	 * @return the manifestDos
	 */
	public Set<ManifestDO> getManifestDos() {
		return manifestDos;
	}
	/**
	 * @param manifestDos the manifestDos to set
	 */
	public void setManifestDos(Set<ManifestDO> manifestDos) {
		this.manifestDos = manifestDos;
	}
	/**
	 * @return the comailDOs
	 */
	public Set<ComailDO> getComailDOs() {
		return comailDOs;
	}
	/**
	 * @param comailDOs the comailDOs to set
	 */
	public void setComailDOs(Set<ComailDO> comailDOs) {
		this.comailDOs = comailDOs;
	}
	/**
	 * @return the consignmentDOXDOs
	 */
	public Set<ConsignmentDOXDO> getConsignmentDOXDOs() {
		return consignmentDOXDOs;
	}
	/**
	 * @param consignmentDOXDOs the consignmentDOXDOs to set
	 */
	public void setConsignmentDOXDOs(Set<ConsignmentDOXDO> consignmentDOXDOs) {
		this.consignmentDOXDOs = consignmentDOXDOs;
	}
	
}
