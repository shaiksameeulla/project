package com.ff.manifest;

import java.util.List;

/**
 * The Class ManifestInputs.
 */
public class ManifestInputs {

	/** The manifest number. */
	private String manifestNumber;

	private Integer manifestId;

	/** The manifest process code. */
	private String manifestProcessCode;

	/** The process id. */
	private Integer processId;

	/** The login office id. */
	private Integer loginOfficeId;

	/** The doc type. */
	private String docType;

	/** The manifest type. */
	private String manifestType;

	/** The manifest direction. */
	private String manifestDirection;

	/** The grid manfst process code. */
	private String gridManfstProcessCode;

	private String clkOnSearch;

	private String originOfficeType;

	private List<String> manifestTypeList;

	private Integer manifestDestOfficId;

	/** The Header Manifest No. for embedded in check in grid. */
	private String headerManifestNo;

	private String misrouteType;

	/** The third party name. */
	private Integer thirdPartyName;

	/** The third party type. */
	private String thirdPartyType;

	/**
	 * @return the thirdPartyName
	 */
	public Integer getThirdPartyName() {
		return thirdPartyName;
	}

	/**
	 * @param thirdPartyName
	 *            the thirdPartyName to set
	 */
	public void setThirdPartyName(Integer thirdPartyName) {
		this.thirdPartyName = thirdPartyName;
	}

	/**
	 * @return the thirdPartyType
	 */
	public String getThirdPartyType() {
		return thirdPartyType;
	}

	/**
	 * @param thirdPartyType
	 *            the thirdPartyType to set
	 */
	public void setThirdPartyType(String thirdPartyType) {
		this.thirdPartyType = thirdPartyType;
	}

	/**
	 * @return the headerManifestNo
	 */
	public String getHeaderManifestNo() {
		return headerManifestNo;
	}

	/**
	 * @param headerManifestNo
	 *            the headerManifestNo to set
	 */
	public void setHeaderManifestNo(String headerManifestNo) {
		this.headerManifestNo = headerManifestNo;
	}

	public String getClkOnSearch() {
		return clkOnSearch;
	}

	public void setClkOnSearch(String clkOnSearch) {
		this.clkOnSearch = clkOnSearch;
	}

	/**
	 * Gets the manifest number.
	 * 
	 * @return the manifest number
	 */
	public String getManifestNumber() {
		return manifestNumber;
	}

	/**
	 * Sets the manifest number.
	 * 
	 * @param manifestNumber
	 *            the new manifest number
	 */
	public void setManifestNumber(String manifestNumber) {
		this.manifestNumber = manifestNumber;
	}

	/**
	 * Gets the manifest process code.
	 * 
	 * @return the manifest process code
	 */
	public String getManifestProcessCode() {
		return manifestProcessCode;
	}

	/**
	 * Sets the manifest process code.
	 * 
	 * @param manifestProcessCode
	 *            the new manifest process code
	 */
	public void setManifestProcessCode(String manifestProcessCode) {
		this.manifestProcessCode = manifestProcessCode;
	}

	/**
	 * Gets the login office id.
	 * 
	 * @return the login office id
	 */
	public Integer getLoginOfficeId() {
		return loginOfficeId;
	}

	/**
	 * Sets the login office id.
	 * 
	 * @param loginOfficeId
	 *            the new login office id
	 */
	public void setLoginOfficeId(Integer loginOfficeId) {
		this.loginOfficeId = loginOfficeId;
	}

	/**
	 * Gets the doc type.
	 * 
	 * @return the doc type
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * Sets the doc type.
	 * 
	 * @param docType
	 *            the new doc type
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	/**
	 * Gets the manifest direction.
	 * 
	 * @return the manifest direction
	 */
	public String getManifestDirection() {
		return manifestDirection;
	}

	/**
	 * Sets the manifest direction.
	 * 
	 * @param manifestDirection
	 *            the new manifest direction
	 */
	public void setManifestDirection(String manifestDirection) {
		this.manifestDirection = manifestDirection;
	}

	/**
	 * Gets the grid manfst process code.
	 * 
	 * @return the grid manfst process code
	 */
	public String getGridManfstProcessCode() {
		return gridManfstProcessCode;
	}

	/**
	 * Sets the grid manfst process code.
	 * 
	 * @param gridManfstProcessCode
	 *            the new grid manfst process code
	 */
	public void setGridManfstProcessCode(String gridManfstProcessCode) {
		this.gridManfstProcessCode = gridManfstProcessCode;
	}

	/**
	 * Gets the manifest type.
	 * 
	 * @return the manifest type
	 */
	public String getManifestType() {
		return manifestType;
	}

	/**
	 * Sets the manifest type.
	 * 
	 * @param manifestType
	 *            the new manifest type
	 */
	public void setManifestType(String manifestType) {
		this.manifestType = manifestType;
	}

	/**
	 * Gets the process id.
	 * 
	 * @return the process id
	 */
	public Integer getProcessId() {
		return processId;
	}

	/**
	 * Sets the process id.
	 * 
	 * @param processId
	 *            the new process id
	 */
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public Integer getManifestId() {
		return manifestId;
	}

	public void setManifestId(Integer manifestId) {
		this.manifestId = manifestId;
	}

	public String getOriginOfficeType() {
		return originOfficeType;
	}

	public void setOriginOfficeType(String originOfficeType) {
		this.originOfficeType = originOfficeType;
	}

	/**
	 * @return the manifestTypeList
	 */
	public List<String> getManifestTypeList() {
		return manifestTypeList;
	}

	/**
	 * @param manifestTypeList
	 *            the manifestTypeList to set
	 */
	public void setManifestTypeList(List<String> manifestTypeList) {
		this.manifestTypeList = manifestTypeList;
	}

	public Integer getManifestDestOfficId() {
		return manifestDestOfficId;
	}

	public void setManifestDestOfficId(Integer manifestDestOfficId) {
		this.manifestDestOfficId = manifestDestOfficId;
	}

	public String getMisrouteType() {
		return misrouteType;
	}

	public void setMisrouteType(String misrouteType) {
		this.misrouteType = misrouteType;
	}

}
