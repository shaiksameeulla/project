package com.ff.to.routeservice;

import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.transport.TransportTO;

/**
 * @author rmaladi
 *
 */
public class PureRouteTO extends CGBaseTO {

	private static final long serialVersionUID = -6039367506022400529L;

	private Integer pureRouteId;
	
	
	private Integer originRegionId;
	private String originStation;
	private String originRegion;
	private Integer originStationId;
	private Integer destinationRegionId;
	private String destinationStation;
	private String destinationRegion;
	private Integer destinationStationId;
	
	private TransportTO transportTO;
	
	private List<LabelValueBean> destinationRegionList;
	private List<LabelValueBean> destinationStationList;
	private List<LabelValueBean> originRegionList;
	private List<LabelValueBean> originStationList;
	private List<LabelValueBean> transportModeList;
	private Map<String,String> airlinesMap;
	
	private int rowCount;
	private int routeId;
	private Integer[] tripNumber = 	new Integer[rowCount];
	private String[] airlineCode = 	new String[rowCount];
	private String[] transportNumber = new String[rowCount];
	private String[] transportName = new String[rowCount];	
	private String[] expDepartureTime = new String[rowCount];
	private String[] expArrivalTime = new String[rowCount];
	private Integer[] transportId = new Integer[rowCount];
	private Integer[] transporterId = new Integer[rowCount];
	private String tripIdsArrStr;
	private String pageAction;
	
	
	public String getTripIdsArrStr() {
		return tripIdsArrStr;
	}


	public void setTripIdsArrStr(String tripIdsArrStr) {
		this.tripIdsArrStr = tripIdsArrStr;
	}


	public int getRouteId() {
		return routeId;
	}


	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}


	public Integer[] getTransporterId() {
		return transporterId;
	}


	public void setTransporterId(Integer[] transporterId) {
		this.transporterId = transporterId;
	}


	public TransportTO getTransportTO() {
		return transportTO;
	}


	public Integer[] getTransportId() {
		return transportId;
	}


	public void setTransportId(Integer[] transportId) {
		this.transportId = transportId;
	}


	public void setTransportTO(TransportTO transportTO) {
		this.transportTO = transportTO;
	}

	public Integer[] getTripNumber() {
		return tripNumber;
	}


	public void setTripNumber(Integer[] tripNumber) {
		this.tripNumber = tripNumber;
	}




	public int getRowCount() {
		return rowCount;
	}


	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}


	public String[] getTransportNumber() {
		return transportNumber;
	}


	public void setTransportNumber(String[] transportNumber) {
		this.transportNumber = transportNumber;
	}


	public String[] getTransportName() {
		return transportName;
	}


	public void setTransportName(String[] transportName) {
		this.transportName = transportName;
	}


	public String[] getExpDepartureTime() {
		return expDepartureTime;
	}


	public void setExpDepartureTime(String[] expDepartureTime) {
		this.expDepartureTime = expDepartureTime;
	}


	public String[] getExpArrivalTime() {
		return expArrivalTime;
	}


	public void setExpArrivalTime(String[] expArrivalTime) {
		this.expArrivalTime = expArrivalTime;
	}


	public void setOriginStationId(Integer originStationId) {
		this.originStationId = originStationId;
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


	public List<LabelValueBean> getDestinationStationList() {
		return destinationStationList;
	}


	public void setDestinationStationList(
			List<LabelValueBean> destinationStationList) {
		this.destinationStationList = destinationStationList;
	}


	public List<LabelValueBean> getOriginRegionList() {
		return originRegionList;
	}


	public void setOriginRegionList(List<LabelValueBean> originRegionList) {
		this.originRegionList = originRegionList;
	}


	public List<LabelValueBean> getOriginStationList() {
		return originStationList;
	}


	public void setOriginStationList(List<LabelValueBean> originStationList) {
		this.originStationList = originStationList;
	}


	public List<LabelValueBean> getTransportModeList() {
		return transportModeList;
	}


	public void setTransportModeList(List<LabelValueBean> transportModeList) {
		this.transportModeList = transportModeList;
	}



	
	
	private String transportMode;


	public Integer getPureRouteId() {
		return pureRouteId;
	}


	public void setPureRouteId(Integer pureRouteId) {
		this.pureRouteId = pureRouteId;
	}


	public Integer getOriginRegionId() {
		return originRegionId;
	}


	public void setOriginRegionId(Integer originRegionId) {
		this.originRegionId = originRegionId;
	}


	public String getOriginStation() {
		return originStation;
	}


	public void setOriginStation(String originStation) {
		this.originStation = originStation;
	}


	public String getOriginRegion() {
		return originRegion;
	}


	public void setOriginRegion(String originRegion) {
		this.originRegion = originRegion;
	}


	public Integer getOriginStationId() {
		return originStationId;
	}


	public void setOriginStaionId(Integer originStationId) {
		this.originStationId = originStationId;
	}


	public Integer getDestinationRegionId() {
		return destinationRegionId;
	}


	public void setDestinationRegionId(Integer destinationRegionId) {
		this.destinationRegionId = destinationRegionId;
	}


	public String getDestinationStation() {
		return destinationStation;
	}


	public void setDestinationStation(String destinationStation) {
		this.destinationStation = destinationStation;
	}


	public String getDestinationRegion() {
		return destinationRegion;
	}


	public void setDestinationRegion(String destinationRegion) {
		this.destinationRegion = destinationRegion;
	}


	public Integer getDestinationStationId() {
		return destinationStationId;
	}


	public void setDestinationStaitonId(Integer destinationStationId) {
		this.destinationStationId = destinationStationId;
	}


	public String getTransportMode() {
		return transportMode;
	}


	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
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


	/**
	 * @return the airlineCode
	 */
	public String[] getAirlineCode() {
		return airlineCode;
	}


	/**
	 * @param airlineCode the airlineCode to set
	 */
	public void setAirlineCode(String[] airlineCode) {
		this.airlineCode = airlineCode;
	}


	/**
	 * @return the airlinesMap
	 */
	public Map<String, String> getAirlinesMap() {
		return airlinesMap;
	}


	/**
	 * @param airlinesMap the airlinesMap to set
	 */
	public void setAirlinesMap(Map<String, String> airlinesMap) {
		this.airlinesMap = airlinesMap;
	}


	
	
	}
