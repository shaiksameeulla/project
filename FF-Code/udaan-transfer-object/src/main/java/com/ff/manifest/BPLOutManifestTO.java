package com.ff.manifest;

import java.util.List;

/**
 * The Class BPLOutManifestTO.
 */
public class BPLOutManifestTO extends OutManifestBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	// Specific to MBPL fields
	/** The dest office type. */
	private String destOfficeType;

	/** The bpl manifest type. */
	private String bplManifestType;

	/** The is serviced. */
	private boolean isServiced;

	// Internal use for transferring data from UI to Action
	/** The rowcount. */
	private int rowcount;

	/** The bpl nos. */
	private String[] bplNos = new String[rowcount];

	/** The bag lock nos. */
	private String[] bagLockNos = new String[rowcount];

	// List of Child details of MBPL
	/** The mbpl out manifest details t os list. */
	private List<MBPLOutManifestDetailsTO> mbplOutManifestDetailsTOsList;

	/**
	 * Gets the dest office type.
	 * 
	 * @return the dest office type
	 */
	public String getDestOfficeType() {
		return destOfficeType;
	}

	/**
	 * Sets the dest office type.
	 * 
	 * @param destOfficeType
	 *            the new dest office type
	 */
	public void setDestOfficeType(String destOfficeType) {
		this.destOfficeType = destOfficeType;
	}

	/**
	 * Gets the bpl nos.
	 * 
	 * @return the bpl nos
	 */
	public String[] getBplNos() {
		return bplNos;
	}

	/**
	 * Sets the bpl nos.
	 * 
	 * @param bplNos
	 *            the new bpl nos
	 */
	public void setBplNos(String[] bplNos) {
		this.bplNos = bplNos;
	}

	/**
	 * Gets the bag lock nos.
	 * 
	 * @return the bag lock nos
	 */
	public String[] getBagLockNos() {
		return bagLockNos;
	}

	/**
	 * Sets the bag lock nos.
	 * 
	 * @param bagLockNos
	 *            the new bag lock nos
	 */
	public void setBagLockNos(String[] bagLockNos) {
		this.bagLockNos = bagLockNos;
	}

	/**
	 * Gets the mbpl out manifest details t os list.
	 * 
	 * @return the mbpl out manifest details t os list
	 */
	public List<MBPLOutManifestDetailsTO> getMbplOutManifestDetailsTOsList() {
		return mbplOutManifestDetailsTOsList;
	}

	/**
	 * Sets the mbpl out manifest details t os list.
	 * 
	 * @param mbplOutManifestDetailsTOsList
	 *            the new mbpl out manifest details t os list
	 */
	public void setMbplOutManifestDetailsTOsList(
			List<MBPLOutManifestDetailsTO> mbplOutManifestDetailsTOsList) {
		this.mbplOutManifestDetailsTOsList = mbplOutManifestDetailsTOsList;
	}

	/**
	 * Gets the bpl manifest type.
	 * 
	 * @return the bpl manifest type
	 */
	public String getBplManifestType() {
		return bplManifestType;
	}

	/**
	 * Sets the bpl manifest type.
	 * 
	 * @param bplManifestType
	 *            the new bpl manifest type
	 */
	public void setBplManifestType(String bplManifestType) {
		this.bplManifestType = bplManifestType;
	}

	/**
	 * Checks if is serviced.
	 * 
	 * @return true, if is serviced
	 */
	public boolean isServiced() {
		return isServiced;
	}

	/**
	 * Sets the serviced.
	 * 
	 * @param isServiced
	 *            the new serviced
	 */
	public void setServiced(boolean isServiced) {
		this.isServiced = isServiced;
	}

}
