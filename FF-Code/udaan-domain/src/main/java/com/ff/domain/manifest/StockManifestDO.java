package com.ff.domain.manifest;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGBaseDO;

/**
 * The Class ManifestDO.
 *
 * @author narmdr
 */
public class StockManifestDO extends CGBaseDO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The manifest id. */
	private Integer manifestId;
	/** The manifest no. */
	private String manifestNo;
	/** The bag lock no. */
	private String bagLockNo;
	/** The manifest status. */
	private String manifestStatus;
	
	/** The manifest type. */
	private String manifestType;
	/** The manifest date. */
	private Date manifestDate;
	private Integer operatingOffice;
	private Integer originOffice;
	
	private String isStockConsumed;
	private String manifestDirection;

	/**
	 * @return the manifestId
	 */
	public Integer getManifestId() {
		return manifestId;
	}

	/**
	 * @return the manifestNo
	 */
	public String getManifestNo() {
		return manifestNo;
	}

	/**
	 * @return the bagLockNo
	 */
	public String getBagLockNo() {
		return bagLockNo;
	}

	/**
	 * @return the manifestStatus
	 */
	public String getManifestStatus() {
		return manifestStatus;
	}

	/**
	 * @return the manifestType
	 */
	public String getManifestType() {
		return manifestType;
	}

	/**
	 * @return the manifestDate
	 */
	public Date getManifestDate() {
		return manifestDate;
	}

	/**
	 * @return the operatingOffice
	 */
	public Integer getOperatingOffice() {
		return operatingOffice;
	}

	/**
	 * @return the isStockConsumed
	 */
	public String getIsStockConsumed() {
		return isStockConsumed;
	}

	/**
	 * @param manifestId the manifestId to set
	 */
	public void setManifestId(Integer manifestId) {
		this.manifestId = manifestId;
	}

	/**
	 * @param manifestNo the manifestNo to set
	 */
	public void setManifestNo(String manifestNo) {
		this.manifestNo = manifestNo;
	}

	/**
	 * @param bagLockNo the bagLockNo to set
	 */
	public void setBagLockNo(String bagLockNo) {
		this.bagLockNo = bagLockNo;
	}

	/**
	 * @param manifestStatus the manifestStatus to set
	 */
	public void setManifestStatus(String manifestStatus) {
		this.manifestStatus = manifestStatus;
	}

	/**
	 * @param manifestType the manifestType to set
	 */
	public void setManifestType(String manifestType) {
		this.manifestType = manifestType;
	}

	/**
	 * @param manifestDate the manifestDate to set
	 */
	public void setManifestDate(Date manifestDate) {
		this.manifestDate = manifestDate;
	}

	/**
	 * @param operatingOffice the operatingOffice to set
	 */
	public void setOperatingOffice(Integer operatingOffice) {
		this.operatingOffice = operatingOffice;
	}

	/**
	 * @param isStockConsumed the isStockConsumed to set
	 */
	public void setIsStockConsumed(String isStockConsumed) {
		this.isStockConsumed = isStockConsumed;
	}

	/**
	 * @return the originOffice
	 */
	public Integer getOriginOffice() {
		return originOffice;
	}

	/**
	 * @param originOffice the originOffice to set
	 */
	public void setOriginOffice(Integer originOffice) {
		this.originOffice = originOffice;
	}

	/**
	 * @return the manifestDirection
	 */
	public String getManifestDirection() {
		return manifestDirection;
	}

	/**
	 * @param manifestDirection the manifestDirection to set
	 */
	public void setManifestDirection(String manifestDirection) {
		this.manifestDirection = manifestDirection;
	}
	
	
}
