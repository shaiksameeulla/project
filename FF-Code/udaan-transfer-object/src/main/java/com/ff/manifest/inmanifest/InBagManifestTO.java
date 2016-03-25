/**
 * 
 */
package com.ff.manifest.inmanifest;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.ff.geography.RegionTO;

/**
 * The Class InBagManifestTO common class for In BPL Dox and Parcel.
 *
 * @author narmdr
 */
public class InBagManifestTO extends InManifestTO {

	private static final long serialVersionUID = 4183527756860507678L;
	
	private String destinationRegion;
	private String destinationOffice;
	private String originRegion;
	private String originCity;
	private String originOffice;
	private Integer destCityId;
	private Integer consignmentTypeId;
	private Integer gridProcessId;
	private String gridOgmProcessCode;
	private String loggedInOfficeName;
	private String loggedInOfficeCity;
	private String destCityName;
	private String destOfficeName;

	/*private RegionTO originRegionTO;
	//private OfficeTypeTO officeTypeTO;
	private CityTO originCityTO;*/
	
	//private Integer totalBags;
	
	//private Integer outManifestId;
	
	private List<LabelValueBean> originOfficeTypeList;
	private List<RegionTO> originRegionTOs;
	
	// UI specific	
	//int count;

	public String getDestinationRegion() {
		return destinationRegion;
	}
	public void setDestinationRegion(String destinationRegion) {
		this.destinationRegion = destinationRegion;
	}
	public String getDestinationOffice() {
		return destinationOffice;
	}
	public void setDestinationOffice(String destinationOffice) {
		this.destinationOffice = destinationOffice;
	}
	public String getOriginRegion() {
		return originRegion;
	}
	public void setOriginRegion(String originRegion) {
		this.originRegion = originRegion;
	}
	public String getOriginCity() {
		return originCity;
	}
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	public String getOriginOffice() {
		return originOffice;
	}
	public void setOriginOffice(String originOffice) {
		this.originOffice = originOffice;
	}
	public List<LabelValueBean> getOriginOfficeTypeList() {
		return originOfficeTypeList;
	}
	public void setOriginOfficeTypeList(List<LabelValueBean> originOfficeTypeList) {
		this.originOfficeTypeList = originOfficeTypeList;
	}
	/**
	 * @return the originRegionTOs
	 */
	public List<RegionTO> getOriginRegionTOs() {
		return originRegionTOs;
	}
	/**
	 * @param originRegionTOs the originRegionTOs to set
	 */
	public void setOriginRegionTOs(List<RegionTO> originRegionTOs) {
		this.originRegionTOs = originRegionTOs;
	}
	public Integer getDestCityId() {
		return destCityId;
	}
	public void setDestCityId(Integer destCityId) {
		this.destCityId = destCityId;
	}
	/**
	 * @return the gridProcessId
	 */
	public Integer getGridProcessId() {
		return gridProcessId;
	}
	/**
	 * @param gridProcessId the gridProcessId to set
	 */
	public void setGridProcessId(Integer gridProcessId) {
		this.gridProcessId = gridProcessId;
	}
	/**
	 * @return the consignmentTypeId
	 */
	public Integer getConsignmentTypeId() {
		return consignmentTypeId;
	}
	/**
	 * @param consignmentTypeId the consignmentTypeId to set
	 */
	public void setConsignmentTypeId(Integer consignmentTypeId) {
		this.consignmentTypeId = consignmentTypeId;
	}
	/**
	 * @return the gridOgmProcessCode
	 */
	public String getGridOgmProcessCode() {
		return gridOgmProcessCode;
	}
	/**
	 * @param gridOgmProcessCode the gridOgmProcessCode to set
	 */
	public void setGridOgmProcessCode(String gridOgmProcessCode) {
		this.gridOgmProcessCode = gridOgmProcessCode;
	}
	/**
	 * @return the loggedInOfficeName
	 */
	public String getLoggedInOfficeName() {
		return loggedInOfficeName;
	}
	/**
	 * @param loggedInOfficeName the loggedInOfficeName to set
	 */
	public void setLoggedInOfficeName(String loggedInOfficeName) {
		this.loggedInOfficeName = loggedInOfficeName;
	}
	/**
	 * @return the loggedInOfficeCity
	 */
	public String getLoggedInOfficeCity() {
		return loggedInOfficeCity;
	}
	/**
	 * @param loggedInOfficeCity the loggedInOfficeCity to set
	 */
	public void setLoggedInOfficeCity(String loggedInOfficeCity) {
		this.loggedInOfficeCity = loggedInOfficeCity;
	}
	public String getDestCityName() {
		return destCityName;
	}
	public void setDestCityName(String destCityName) {
		this.destCityName = destCityName;
	}
	public String getDestOfficeName() {
		return destOfficeName;
	}
	public void setDestOfficeName(String destOfficeName) {
		this.destOfficeName = destOfficeName;
	}
	
}
