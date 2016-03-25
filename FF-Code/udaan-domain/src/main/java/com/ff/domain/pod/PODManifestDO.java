package com.ff.domain.pod;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.organization.OfficeDO;

/**
 * The Class PODManifestDO.
 * 
 * @author prmeher
 */

public class PODManifestDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** primary key for the pod manifest table. */
	private Integer podManifestId;

	/** manifest date. */
	private Date manifestDate;

	/** Manifest Number */
	private String manifestNo;

	/** The Dispatch office. */
	private OfficeDO dispachOffice;

	/** The dest office. */
	private OfficeDO destOffice;

	/** The destination city. */
	private CityDO destinationCity;

	/** manifest status */
	private String manifestStatus;

	/** manifest type */
	private String manifestType;

	private Set<PODManifestDtlsDO> podManifestDtls;

	/**
	 * @return the podManifestId
	 */
	public Integer getPodManifestId() {
		return podManifestId;
	}

	/**
	 * @param podManifestId
	 *            the podManifestId to set
	 */
	public void setPodManifestId(Integer podManifestId) {
		this.podManifestId = podManifestId;
	}

	/**
	 * @return the manifestDate
	 */
	public Date getManifestDate() {
		return manifestDate;
	}

	/**
	 * @param manifestDate
	 *            the manifestDate to set
	 */
	public void setManifestDate(Date manifestDate) {
		this.manifestDate = manifestDate;
	}

	/**
	 * @return the manifestNo
	 */
	public String getManifestNo() {
		return manifestNo;
	}

	/**
	 * @param manifestNo
	 *            the manifestNo to set
	 */
	public void setManifestNo(String manifestNo) {
		this.manifestNo = manifestNo;
	}

	/**
	 * @return the dispachOffice
	 */
	public OfficeDO getDispachOffice() {
		return dispachOffice;
	}

	/**
	 * @param dispachOffice
	 *            the dispachOffice to set
	 */
	public void setDispachOffice(OfficeDO dispachOffice) {
		this.dispachOffice = dispachOffice;
	}

	/**
	 * @return the destOffice
	 */
	public OfficeDO getDestOffice() {
		return destOffice;
	}

	/**
	 * @param destOffice
	 *            the destOffice to set
	 */
	public void setDestOffice(OfficeDO destOffice) {
		this.destOffice = destOffice;
	}

	/**
	 * @return the destinationCity
	 */
	public CityDO getDestinationCity() {
		return destinationCity;
	}

	/**
	 * @param destinationCity
	 *            the destinationCity to set
	 */
	public void setDestinationCity(CityDO destinationCity) {
		this.destinationCity = destinationCity;
	}

	/**
	 * @return the manifestStatus
	 */
	public String getManifestStatus() {
		return manifestStatus;
	}

	/**
	 * @param manifestStatus
	 *            the manifestStatus to set
	 */
	public void setManifestStatus(String manifestStatus) {
		this.manifestStatus = manifestStatus;
	}

	/**
	 * @return the manifestType
	 */
	public String getManifestType() {
		return manifestType;
	}

	/**
	 * @param manifestType
	 *            the manifestType to set
	 */
	public void setManifestType(String manifestType) {
		this.manifestType = manifestType;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the podManifestDtls
	 */
	public Set<PODManifestDtlsDO> getPodManifestDtls() {
		return podManifestDtls;
	}

	/**
	 * @param podManifestDtls
	 *            the podManifestDtls to set
	 */
	public void setPodManifestDtls(Set<PODManifestDtlsDO> podManifestDtls) {
		this.podManifestDtls = podManifestDtls;
	}

}