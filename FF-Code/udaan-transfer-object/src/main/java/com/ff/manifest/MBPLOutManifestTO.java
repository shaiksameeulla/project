package com.ff.manifest;

import java.util.List;

/**
 * The Class MBPLOutManifestTO.
 * 
 * @author preegupt
 */
public class MBPLOutManifestTO extends OutManifestBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4823443451083398266L;
	// Specific to MBPL fields
	/** The dest office type. */
	private String destOfficeType;

	/** The bpl manifest type. */
	private String bplManifestType;

	/** The is serviced. */
	private boolean isServiced;
	
	private Integer operatingOffice;
	
	private Integer originOfficeId;

	// Internal use for transferring data from UI to Action

	/** The bpl nos. */
	private String[] bplNos = new String[rowCount];

	/** The bag lock nos. */
	private String[] bagLockNos = new String[rowCount];

	private String[] bagTypes = new String[rowCount];

	/** The mbpl out manifest details t os list. */
	private List<MBPLOutManifestDetailsTO> mbplOutManifestDetailsTOsList;

	private String destinationOffcId;

	private int rowcount;
	private Long consigTotal;
	private double consigTotalWt;
	private Long totalComail;
	private String bagType;
	private Integer manifestProcessId;
	/**
	 * @return the destOfficeType
	 */
	public String getDestOfficeType() {
		return destOfficeType;
	}

	/**
	 * @param destOfficeType
	 *            the destOfficeType to set
	 */
	public void setDestOfficeType(String destOfficeType) {
		this.destOfficeType = destOfficeType;
	}

	/**
	 * @return the bplManifestType
	 */
	public String getBplManifestType() {
		return bplManifestType;
	}

	/**
	 * @param bplManifestType
	 *            the bplManifestType to set
	 */
	public void setBplManifestType(String bplManifestType) {
		this.bplManifestType = bplManifestType;
	}

	/**
	 * @return the isServiced
	 */
	public boolean isServiced() {
		return isServiced;
	}

	/**
	 * @param isServiced
	 *            the isServiced to set
	 */
	public void setServiced(boolean isServiced) {
		this.isServiced = isServiced;
	}

	/**
	 * @return the bplNos
	 */
	public String[] getBplNos() {
		return bplNos;
	}

	/**
	 * @param bplNos
	 *            the bplNos to set
	 */
	public void setBplNos(String[] bplNos) {
		this.bplNos = bplNos;
	}

	/**
	 * @return the bagLockNos
	 */
	public String[] getBagLockNos() {
		return bagLockNos;
	}

	/**
	 * @param bagLockNos
	 *            the bagLockNos to set
	 */
	public void setBagLockNos(String[] bagLockNos) {
		this.bagLockNos = bagLockNos;
	}

	/**
	 * @return the bagTypes
	 */
	public String[] getBagTypes() {
		return bagTypes;
	}

	/**
	 * @param bagTypes
	 *            the bagTypes to set
	 */
	public void setBagTypes(String[] bagTypes) {
		this.bagTypes = bagTypes;
	}

	/**
	 * @return the mbplOutManifestDetailsTOsList
	 */
	public List<MBPLOutManifestDetailsTO> getMbplOutManifestDetailsTOsList() {
		return mbplOutManifestDetailsTOsList;
	}

	/**
	 * @param mbplOutManifestDetailsTOsList
	 *            the mbplOutManifestDetailsTOsList to set
	 */
	public void setMbplOutManifestDetailsTOsList(
			List<MBPLOutManifestDetailsTO> mbplOutManifestDetailsTOsList) {
		this.mbplOutManifestDetailsTOsList = mbplOutManifestDetailsTOsList;
	}

	/**
	 * @return the destinationOffcId
	 */
	public String getDestinationOffcId() {
		return destinationOffcId;
	}

	/**
	 * @param destinationOffcId
	 *            the destinationOffcId to set
	 */
	public void setDestinationOffcId(String destinationOffcId) {
		this.destinationOffcId = destinationOffcId;
	}

	/**
	 * @return the rowcount
	 */
	public int getRowcount() {
		return rowcount;
	}

	/**
	 * @param rowcount
	 *            the rowcount to set
	 */
	public void setRowcount(int rowcount) {
		this.rowcount = rowcount;
	}

	/**
	 * @return the consigTotal
	 */
	public Long getConsigTotal() {
		return consigTotal;
	}

	/**
	 * @param consigTotal
	 *            the consigTotal to set
	 */
	public void setConsigTotal(Long consigTotal) {
		this.consigTotal = consigTotal;
	}

	/**
	 * @return the consigTotalWt
	 */
	public double getConsigTotalWt() {
		return consigTotalWt;
	}

	/**
	 * @param consigTotalWt
	 *            the consigTotalWt to set
	 */
	public void setConsigTotalWt(double consigTotalWt) {
		this.consigTotalWt = consigTotalWt;
	}

	/**
	 * @return the totalComail
	 */
	public Long getTotalComail() {
		return totalComail;
	}

	/**
	 * @param totalComail
	 *            the totalComail to set
	 */
	public void setTotalComail(Long totalComail) {
		this.totalComail = totalComail;
	}

	/**
	 * @return the bagType
	 */
	public String getBagType() {
		return bagType;
	}

	/**
	 * @param bagType
	 *            the bagType to set
	 */
	public void setBagType(String bagType) {
		this.bagType = bagType;
	}

	/**
	 * @return the manifestProcessId
	 */
	public Integer getManifestProcessId() {
		return manifestProcessId;
	}

	/**
	 * @param manifestProcessId the manifestProcessId to set
	 */
	public void setManifestProcessId(Integer manifestProcessId) {
		this.manifestProcessId = manifestProcessId;
	}

	public Integer getOperatingOffice() {
		return operatingOffice;
	}

	public void setOperatingOffice(Integer operatingOffice) {
		this.operatingOffice = operatingOffice;
	}

	public Integer getOriginOfficeId() {
		return originOfficeId;
	}

	public void setOriginOfficeId(Integer originOfficeId) {
		this.originOfficeId = originOfficeId;
	}
	
	

	
}
