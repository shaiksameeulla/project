package com.ff.routeserviced;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;

/**
 * @author narmdr
 *
 */
public class TransshipmentRouteTO extends CGBaseTO {

	private static final long serialVersionUID = -1521505322504191003L;
	private Integer transshipmentRouteId;
	private Integer transshipmentCityId;
	private Integer servicedCityId;
	private Integer transshipmentRegionId;
	private Integer servicedRegionId;
	
	private List<LabelValueBean> transshipmentRegionList;
	private int rowCount;	
	
	private Integer[] transshipmentNumber = 	new Integer[rowCount];
	private Integer[] servicedRegionIds = new Integer[rowCount];
	private Integer[] servicedCityIds = new Integer[rowCount];
	private List<CityTO> servicedCityList = new ArrayList<CityTO>();
	private String active;
	private String transshipmentIdsArrStr;
	private String pageAction;
	
	
	public String getTransshipmentIdsArrStr() {
		return transshipmentIdsArrStr;
	}
	public void setTransshipmentIdsArrStr(String transshipmentIdsArrStr) {
		this.transshipmentIdsArrStr = transshipmentIdsArrStr;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public List<CityTO> getServicedCityList() {
		return servicedCityList;
	}
	public void setServicedCityList(List<CityTO> servicedCityList) {
		this.servicedCityList = servicedCityList;
	}
	public Integer getTransshipmentRegionId() {
		return transshipmentRegionId;
	}
	public void setTransshipmentRegionId(Integer transshipmentRegionId) {
		this.transshipmentRegionId = transshipmentRegionId;
	}
	public Integer getServicedRegionId() {
		return servicedRegionId;
	}
	public void setServicedRegionId(Integer servicedRegionId) {
		this.servicedRegionId = servicedRegionId;
	}
	public Integer[] getServicedRegionIds() {
		return servicedRegionIds;
	}
	public void setServicedRegionIds(Integer[] servicedRegionIds) {
		this.servicedRegionIds = servicedRegionIds;
	}
	public Integer[] getServicedCityIds() {
		return servicedCityIds;
	}
	public void setServicedCityIds(Integer[] servicedCityIds) {
		this.servicedCityIds = servicedCityIds;
	}
	public List<LabelValueBean> getTransshipmentRegionList() {
		return transshipmentRegionList;
	}
	public void setTransshipmentRegionList(
			List<LabelValueBean> transshipmentRegionList) {
		this.transshipmentRegionList = transshipmentRegionList;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public Integer[] getTransshipmentNumber() {
		return transshipmentNumber;
	}
	public void setTransshipmentNumber(Integer[] transshipmentNumber) {
		this.transshipmentNumber = transshipmentNumber;
	}
	
	public Integer getTransshipmentRouteId() {
		return transshipmentRouteId;
	}
	public void setTransshipmentRouteId(Integer transshipmentRouteId) {
		this.transshipmentRouteId = transshipmentRouteId;
	}
	public Integer getTransshipmentCityId() {
		return transshipmentCityId;
	}
	public void setTransshipmentCityId(Integer transshipmentCityId) {
		this.transshipmentCityId = transshipmentCityId;
	}
	public Integer getServicedCityId() {
		return servicedCityId;
	}
	public void setServicedCityId(Integer servicedCityId) {
		this.servicedCityId = servicedCityId;
	}
	/**
	 * @return the pageAction
	 */
	public String getPageAction() {
		return pageAction;
	}
	/**
	 * @param pageAction the pageAction to set
	 */
	public void setPageAction(String pageAction) {
		this.pageAction = pageAction;
	}	
	
}
