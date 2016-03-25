package com.ff.domain.manifest;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.consignment.ConsignmentDO;


// TODO: Auto-generated Javadoc
/**
 * The Class ConsignmentManifestDO.
 */
public class ConsignmentManifestDO extends CGFactDO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 9040753128698165474L;

	/** The consignment manifest id. */
	private Integer consignmentManifestId;

	/** The consignment. */
	private ConsignmentDO consignment;
	
	/** The manifest. */
	//@JsonBackReference 
	private ManifestDO manifest;
	
	
	/**
	 * Gets the consignment manifest id.
	 *
	 * @return the consignment manifest id
	 */
	public Integer getConsignmentManifestId() {
		return consignmentManifestId;
	}

	/**
	 * Sets the consignment manifest id.
	 *
	 * @param consignmentManifestId the new consignment manifest id
	 */
	public void setConsignmentManifestId(Integer consignmentManifestId) {
		this.consignmentManifestId = consignmentManifestId;
	}

	/**
	 * Gets the consignment.
	 *
	 * @return the consignment
	 */
	public ConsignmentDO getConsignment() {
		return consignment;
	}

	/**
	 * Sets the consignment.
	 *
	 * @param consignment the new consignment
	 */
	public void setConsignment(ConsignmentDO consignment) {
		this.consignment = consignment;
	}

	/**
	 * Gets the manifest.
	 *
	 * @return the manifest
	 */
	public ManifestDO getManifest() {
		return manifest;
	}

	/**
	 * Sets the manifest.
	 *
	 * @param manifest the new manifest
	 */
	public void setManifest(ManifestDO manifest) {
		this.manifest = manifest;
	}

}
