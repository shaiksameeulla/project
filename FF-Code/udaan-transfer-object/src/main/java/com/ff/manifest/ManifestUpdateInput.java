package com.ff.manifest;

import java.io.Serializable;
import java.util.List;

/**
 * The Class ManifestUpdateInput.
 */
public class ManifestUpdateInput implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8808933876773223476L;

	/** The manifest id. */
	private Integer manifestId;
	
	/** The manifest embedded in. */
	private Integer manifestEmbeddedIn;
	
	/** The updated process id. */
	private Integer updatedProcessId;
	
	/** The manifest weight. */
	private Double manifestWeight;
	
	/** The manifest ids. */
	private List<Integer> manifestIds;

	/** The positions */
	private List<Integer> positions;
	
	/** The manifest wt. */
	private List<Double> manifestWt;
	
	/** The manifest type. */
	private String manifestType;
		
	/**
	 * @return the manifestWt
	 */
	public List<Double> getManifestWt() {
		return manifestWt;
	}

	/**
	 * @param manifestWt the manifestWt to set
	 */
	public void setManifestWt(List<Double> manifestWt) {
		this.manifestWt = manifestWt;
	}

	/**
	 * @return the positions
	 */
	public List<Integer> getPositions() {
		return positions;
	}

	/**
	 * @param positions the positions to set
	 */
	public void setPositions(List<Integer> positions) {
		this.positions = positions;
	}

	/**
	 * Gets the manifest id.
	 *
	 * @return the manifest id
	 */
	public Integer getManifestId() {
		return manifestId;
	}

	/**
	 * Sets the manifest id.
	 *
	 * @param manifestId the new manifest id
	 */
	public void setManifestId(Integer manifestId) {
		this.manifestId = manifestId;
	}

	/**
	 * Gets the manifest embedded in.
	 *
	 * @return the manifest embedded in
	 */
	public Integer getManifestEmbeddedIn() {
		return manifestEmbeddedIn;
	}

	/**
	 * Sets the manifest embedded in.
	 *
	 * @param manifestEmbeddedIn the new manifest embedded in
	 */
	public void setManifestEmbeddedIn(Integer manifestEmbeddedIn) {
		this.manifestEmbeddedIn = manifestEmbeddedIn;
	}

	/**
	 * Gets the manifest weight.
	 *
	 * @return the manifest weight
	 */
	public Double getManifestWeight() {
		return manifestWeight;
	}

	/**
	 * Sets the manifest weight.
	 *
	 * @param manifestWeight the new manifest weight
	 */
	public void setManifestWeight(Double manifestWeight) {
		this.manifestWeight = manifestWeight;
	}

	/**
	 * Gets the updated process id.
	 *
	 * @return the updated process id
	 */
	public Integer getUpdatedProcessId() {
		return updatedProcessId;
	}

	/**
	 * Sets the updated process id.
	 *
	 * @param updatedProcessId the new updated process id
	 */
	public void setUpdatedProcessId(Integer updatedProcessId) {
		this.updatedProcessId = updatedProcessId;
	}

	/**
	 * Gets the manifest ids.
	 *
	 * @return the manifest ids
	 */
	public List<Integer> getManifestIds() {
		return manifestIds;
	}

	/**
	 * Sets the manifest ids.
	 *
	 * @param manifestIds the new manifest ids
	 */
	public void setManifestIds(List<Integer> manifestIds) {
		this.manifestIds = manifestIds;
	}

	/**
	 * @return the manifestType
	 */
	public String getManifestType() {
		return manifestType;
	}

	/**
	 * @param manifestType the manifestType to set
	 */
	public void setManifestType(String manifestType) {
		this.manifestType = manifestType;
	}

	
}
