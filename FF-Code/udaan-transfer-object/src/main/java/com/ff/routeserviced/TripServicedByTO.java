package com.ff.routeserviced;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.transport.TransportModeTO;

/**
 * @author narmdr
 *
 */
public class TripServicedByTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;

	private Integer tripServicedById;
	private ServicedByTO servicedByTO;
	private TripTO tripTO;
	private TransportModeTO transportModeTO;
	private String operationDays;
	private String allDays;
	
	private Integer originRegionId;
	private Integer originStationId;
	private Integer destinationRegionId;
	private Integer destinationStationId;
	private String serviceByType;
	private String transportMode;
	
	private List<LabelValueBean> destinationRegionList;	
	private List<LabelValueBean> originRegionList;
	private List<LabelValueBean> transportModeList;
	private List<LabelValueBean> serviceByTypeList;
	
	private int rowCount;
	private Integer routeId;
	private Integer[] tripServicedByNumber = 	new Integer[rowCount];
	private Integer[] transportNumber = new Integer[rowCount];		
	private Integer[] vendorNumber = new Integer[rowCount];
	private Integer[] servicedByNumber = new Integer[rowCount];
	private String[] vendorName = new String[rowCount];
	private Integer[] tripNumber = 	new Integer[rowCount];
	private String[] operationDaysArr = 	new String[rowCount];
	private String[] allDaysArr = 	new String[rowCount];
	

	private String effectiveFromStr;
	private String effectiveToStr;
	private String active;
	private String tripServicedIdsArrStr;
	private String pageAction;
		
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getTripServicedIdsArrStr() {
		return tripServicedIdsArrStr;
	}
	public void setTripServicedIdsArrStr(String tripServicedIdsArrStr) {
		this.tripServicedIdsArrStr = tripServicedIdsArrStr;
	}
	public String[] getOperationDaysArr() {
		return operationDaysArr;
	}
	public void setOperationDaysArr(String[] operationDaysArr) {
		this.operationDaysArr = operationDaysArr;
	}
	public String[] getAllDaysArr() {
		return allDaysArr;
	}
	public void setAllDaysArr(String[] allDaysArr) {
		this.allDaysArr = allDaysArr;
	}
	public String getOperationDays() {
		return operationDays;
	}
	public void setOperationDays(String operationDays) {
		this.operationDays = operationDays;
	}
	public String getAllDays() {
		return allDays;
	}
	public void setAllDays(String allDays) {
		this.allDays = allDays;
	}
	public String getServiceByType() {
		return serviceByType;
	}
	public void setServiceByType(String serviceByType) {
		this.serviceByType = serviceByType;
	}
	public Integer[] getVendorNumber() {
		return vendorNumber;
	}
	public void setVendorNumber(Integer[] vendorNumber) {
		this.vendorNumber = vendorNumber;
	}
	public Integer[] getServicedByNumber() {
		return servicedByNumber;
	}
	public void setServicedByNumber(Integer[] servicedByNumber) {
		this.servicedByNumber = servicedByNumber;
	}
	public String getTransportMode() {
		return transportMode;
	}
	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}		
	public List<LabelValueBean> getServiceByTypeList() {
		return serviceByTypeList;
	}
	public void setServiceByTypeList(List<LabelValueBean> serviceByTypeList) {
		this.serviceByTypeList = serviceByTypeList;
	}
	public String getEffectiveFromStr() {
		return effectiveFromStr;
	}
	public void setEffectiveFromStr(String effectiveFromStr) {
		this.effectiveFromStr = effectiveFromStr;
	}
	public String getEffectiveToStr() {
		return effectiveToStr;
	}
	public void setEffectiveToStr(String effectiveToStr) {
		this.effectiveToStr = effectiveToStr;
	}
	public Integer getOriginRegionId() {
		return originRegionId;
	}
	public void setOriginRegionId(Integer originRegionId) {
		this.originRegionId = originRegionId;
	}
	public Integer getOriginStationId() {
		return originStationId;
	}
	public void setOriginStationId(Integer originStationId) {
		this.originStationId = originStationId;
	}
	public Integer getDestinationRegionId() {
		return destinationRegionId;
	}
	public void setDestinationRegionId(Integer destinationRegionId) {
		this.destinationRegionId = destinationRegionId;
	}
	public Integer getDestinationStationId() {
		return destinationStationId;
	}
	public void setDestinationStationId(Integer destinationStationId) {
		this.destinationStationId = destinationStationId;
	}
	public List<LabelValueBean> getDestinationRegionList() {
		return destinationRegionList;
	}
	public void setDestinationRegionList(List<LabelValueBean> destinationRegionList) {
		this.destinationRegionList = destinationRegionList;
	}
	public List<LabelValueBean> getOriginRegionList() {
		return originRegionList;
	}
	public void setOriginRegionList(List<LabelValueBean> originRegionList) {
		this.originRegionList = originRegionList;
	}
	public List<LabelValueBean> getTransportModeList() {
		return transportModeList;
	}
	public void setTransportModeList(List<LabelValueBean> transportModeList) {
		this.transportModeList = transportModeList;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public Integer getRouteId() {
		return routeId;
	}
	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}
	public Integer[] getTripServicedByNumber() {
		return tripServicedByNumber;
	}
	public void setTripServicedByNumber(Integer[] tripServicedByNumber) {
		this.tripServicedByNumber = tripServicedByNumber;
	}
	public Integer[] getTransportNumber() {
		return transportNumber;
	}
	public void setTransportNumber(Integer[] transportNumber) {
		this.transportNumber = transportNumber;
	}
	public String[] getVendorName() {
		return vendorName;
	}
	public void setVendorName(String[] vendorName) {
		this.vendorName = vendorName;
	}
	public Integer[] getTripNumber() {
		return tripNumber;
	}
	public void setTripNumber(Integer[] tripNumber) {
		this.tripNumber = tripNumber;
	}
	public Integer getTripServicedById() {
		return tripServicedById;
	}
	public void setTripServicedById(Integer tripServicedById) {
		this.tripServicedById = tripServicedById;
	}
	public ServicedByTO getServicedByTO() {
		return servicedByTO;
	}
	public void setServicedByTO(ServicedByTO servicedByTO) {
		this.servicedByTO = servicedByTO;
	}
	public TripTO getTripTO() {
		return tripTO;
	}
	public void setTripTO(TripTO tripTO) {
		this.tripTO = tripTO;
	}
	public TransportModeTO getTransportModeTO() {
		return transportModeTO;
	}
	public void setTransportModeTO(TransportModeTO transportModeTO) {
		this.transportModeTO = transportModeTO;
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
