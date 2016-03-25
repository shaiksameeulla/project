package com.ff.manifest;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

/**
 * The Class ThirdPartyOutManifestDoxTO.
 */
public class ThirdPartyOutManifestDoxTO extends OutManifestBaseTO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 623490601309449701L;
	// Specific to BPL Branch out manifest fields
	//Moved to Parent
	/** The third party type. */
	//private String thirdPartyType;
	
	/** The load list. */
	private List<LabelValueBean> loadList;
	
	/** The load no. */
	private Integer loadNo;
	
	/** The third party type list. */
	private List<LabelValueBean> thirdPartyTypeList;	
	
	/** The third party name list. */
	private List<LabelValueBean> thirdPartyNameList;
	
	/** The third party name. */
	//private String thirdPartyName;
	
	/** The region code. */
	private String regionCode;
	
	private int printRowCount;
	
	/** The third party out manifest dox details to list. */
	private List<ThirdPartyOutManifestDoxDetailsTO> thirdPartyOutManifestDoxDetailsToList;
	
	//for print
	private String vendorCode;
	
	/** The row count. */
	private int rowCount;
	//for thirdparty CR
	private Double[] baAmounts = new Double[rowCount];


	/**
	 * Gets the load list.
	 *
	 * @return the loadList
	 */
	public List<LabelValueBean> getLoadList() {
		return loadList;
	}

	/**
	 * Sets the load list.
	 *
	 * @param loadList the loadList to set
	 */
	public void setLoadList(List<LabelValueBean> loadList) {
		this.loadList = loadList;
	}

	/**
	 * Gets the third party type list.
	 *
	 * @return the thirdPartyTypeList
	 */
	public List<LabelValueBean> getThirdPartyTypeList() {
		return thirdPartyTypeList;
	}

	/**
	 * Sets the third party type list.
	 *
	 * @param thirdPartyTypeList the thirdPartyTypeList to set
	 */
	public void setThirdPartyTypeList(List<LabelValueBean> thirdPartyTypeList) {
		this.thirdPartyTypeList = thirdPartyTypeList;
	}

	/**
	 * Gets the third party name list.
	 *
	 * @return the thirdPartyNameList
	 */
	public List<LabelValueBean> getThirdPartyNameList() {
		return thirdPartyNameList;
	}

	/**
	 * Sets the third party name list.
	 *
	 * @param thirdPartyNameList the thirdPartyNameList to set
	 */
	public void setThirdPartyNameList(List<LabelValueBean> thirdPartyNameList) {
		this.thirdPartyNameList = thirdPartyNameList;
	}

	
	/**
	 * Gets the third party out manifest dox details to list.
	 *
	 * @return the third party out manifest dox details to list
	 */
	public List<ThirdPartyOutManifestDoxDetailsTO> getThirdPartyOutManifestDoxDetailsToList() {
		return thirdPartyOutManifestDoxDetailsToList;
	}

	/**
	 * Sets the third party out manifest dox details to list.
	 *
	 * @param thirdPartyOutManifestDoxDetailsToList the new third party out manifest dox details to list
	 */
	public void setThirdPartyOutManifestDoxDetailsToList(
			List<ThirdPartyOutManifestDoxDetailsTO> thirdPartyOutManifestDoxDetailsToList) {
		this.thirdPartyOutManifestDoxDetailsToList = thirdPartyOutManifestDoxDetailsToList;
	}

	/**
	 * Gets the load no.
	 *
	 * @return the load no
	 */
	public Integer getLoadNo() {
		return loadNo;
	}

	/**
	 * Sets the load no.
	 *
	 * @param loadNo the new load no
	 */
	public void setLoadNo(Integer loadNo) {
		this.loadNo = loadNo;
	}

	/**
	 * Gets the region code.
	 *
	 * @return the region code
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * Sets the region code.
	 *
	 * @param regionCode the new region code
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public int getPrintRowCount() {
		return printRowCount;
	}

	public void setPrintRowCount(int printRowCount) {
		this.printRowCount = printRowCount;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	/**
	 * @return the baAmounts
	 */
	public Double[] getBaAmounts() {
		return baAmounts;
	}

	/**
	 * @param baAmounts the baAmounts to set
	 */
	public void setBaAmounts(Double[] baAmounts) {
		this.baAmounts = baAmounts;
	}
	
	
}
