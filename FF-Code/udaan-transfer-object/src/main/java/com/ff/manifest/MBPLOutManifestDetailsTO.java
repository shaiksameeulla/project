package com.ff.manifest;

/**
 * The Class MBPLOutManifestDetailsTO.
 *
 * @author preegupt
 */
public class MBPLOutManifestDetailsTO extends OutManifestDetailBaseTO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5078314192702403552L;
	
	/** The bpl no. */
	private String bplNo;
	
	/** The bag lock no. */
	private String bagLockNo;
	
	private Integer noOfConsignment;
	private String  bagType;
	/**
	 * Gets the bpl no.
	 *
	 * @return the bplNo
	 */
	public String getBplNo() {
		return bplNo;
	}
	
	/**
	 * Sets the bpl no.
	 *
	 * @param bplNo the bplNo to set
	 */
	public void setBplNo(String bplNo) {
		this.bplNo = bplNo;
	}
	
	/**
	 * Gets the bag lock no.
	 *
	 * @return the bagLockNo
	 */
	public String getBagLockNo() {
		return bagLockNo;
	}
	
	/**
	 * Sets the bag lock no.
	 *
	 * @param bagLockNo the bagLockNo to set
	 */
	public void setBagLockNo(String bagLockNo) {
		this.bagLockNo = bagLockNo;
	}

	public Integer getNoOfConsignment() {
		return noOfConsignment;
	}

	public void setNoOfConsignment(Integer noOfConsignment) {
		this.noOfConsignment = noOfConsignment;
	}

	/**
	 * @return the bagType
	 */
	public String getBagType() {
		return bagType;
	}

	/**
	 * @param bagType the bagType to set
	 */
	public void setBagType(String bagType) {
		this.bagType = bagType;
	}

	

}
