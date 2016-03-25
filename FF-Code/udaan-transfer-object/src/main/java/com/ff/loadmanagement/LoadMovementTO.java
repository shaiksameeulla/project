package com.ff.loadmanagement;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.ff.organization.OfficeTO;

/**
 * The Class LoadMovementTO used for Load Dispatch Header.
 *
 * @author narmdr
 */
public class LoadMovementTO extends LoadManagementTO {
	//using for dispatch screen 
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6039367506022400529L;

	/** The service by type. */
	private String serviceByType;
	
	/** The expected departure. */
	private String expectedDeparture;
	
	/** The expected arrival. */
	private String expectedArrival;
	
	/** The off load ids. */
	private String offLoadIds;	
	
	/** The dispatch date time. */
	private String dispatchDateTime;
	
	/** The loading time. */
	private String loadingTime;

	/** The dest city. */
	private String destCity;
	
	/** The load movement vendor. */
	private String loadMovementVendor;
	
	/** The transport number. */
	private String transportNumber;
	
	/** The other transport number. */
	private String otherTransportNumber;

	//Foreign key Ids
	/** The trip serviced by id. */
	private Integer tripServicedById;
	
	/** The route id. */
	private Integer routeId;
	
	/** The origin city id. */
	private Integer originCityId;	
	
	/** The dest office type list. */
	private List<LabelValueBean> destOfficeTypeList;
	
	/** The transport mode list. */
	private List<LabelValueBean> transportModeList;
	
	/** The load dispatch details t os. */
	private List<LoadDispatchDetailsTO> loadDispatchDetailsTOs;
	


	//print
	private String printDestOfficeType;
	private Double totalCdWeightPrint;
	private String transportModeLabelPrint;

	//grid
	/** The row count. */
	private int rowCount;	
	
	/** The send mail. */
	private String[] sendMail = new String[rowCount];
	
	/**
	 * Gets the dest office type list.
	 *
	 * @return the dest office type list
	 */
	
	// email attributes
	private OfficeTO destOfficeTO;
	private String emailDstnOffcType ;
	private String emailDestOffice;
	private String emailTransportMode;
	private String emailServiceByType;
	private String emailVendor;
	private String emailTransportNumber;
	private String emailVehicleNumber;
	private String emailTransportLabel;	
	//private String smsErrorMessage;
	
	
	public List<LabelValueBean> getDestOfficeTypeList() {
		if(this.destOfficeTypeList==null){
			destOfficeTypeList = new ArrayList<LabelValueBean>();
		}
		return destOfficeTypeList;
	}
	
	/**
	 * Sets the dest office type list.
	 *
	 * @param destOfficeTypeList the new dest office type list
	 */
	public void setDestOfficeTypeList(List<LabelValueBean> destOfficeTypeList) {
		this.destOfficeTypeList = destOfficeTypeList;
	}
	
	/**
	 * Gets the transport mode list.
	 *
	 * @return the transport mode list
	 */
	public List<LabelValueBean> getTransportModeList() {
		if(this.transportModeList==null){
			transportModeList = new ArrayList<LabelValueBean>();
		}
		return transportModeList;
	}
	
	/**
	 * Sets the transport mode list.
	 *
	 * @param transportModeList the new transport mode list
	 */
	public void setTransportModeList(List<LabelValueBean> transportModeList) {
		this.transportModeList = transportModeList;
	}
	
	/**
	 * Gets the service by type.
	 *
	 * @return the service by type
	 */
	public String getServiceByType() {
		return serviceByType;
	}
	
	/**
	 * Sets the service by type.
	 *
	 * @param serviceByType the new service by type
	 */
	public void setServiceByType(String serviceByType) {
		this.serviceByType = serviceByType;
	}
	
	/**
	 * Gets the expected departure.
	 *
	 * @return the expected departure
	 */
	public String getExpectedDeparture() {
		return expectedDeparture;
	}
	
	/**
	 * Sets the expected departure.
	 *
	 * @param expectedDeparture the new expected departure
	 */
	public void setExpectedDeparture(String expectedDeparture) {
		this.expectedDeparture = expectedDeparture;
	}
	
	/**
	 * Gets the expected arrival.
	 *
	 * @return the expected arrival
	 */
	public String getExpectedArrival() {
		return expectedArrival;
	}
	
	/**
	 * Sets the expected arrival.
	 *
	 * @param expectedArrival the new expected arrival
	 */
	public void setExpectedArrival(String expectedArrival) {
		this.expectedArrival = expectedArrival;
	}
	
	/**
	 * Gets the off load ids.
	 *
	 * @return the off load ids
	 */
	public String getOffLoadIds() {
		return offLoadIds;
	}
	
	/**
	 * Sets the off load ids.
	 *
	 * @param offLoadIds the new off load ids
	 */
	public void setOffLoadIds(String offLoadIds) {
		this.offLoadIds = offLoadIds;
	}	
	
	/**
	 * Gets the dispatch date time.
	 *
	 * @return the dispatch date time
	 */
	public String getDispatchDateTime() {
		return dispatchDateTime;
	}
	
	/**
	 * Sets the dispatch date time.
	 *
	 * @param dispatchDateTime the new dispatch date time
	 */
	public void setDispatchDateTime(String dispatchDateTime) {
		this.dispatchDateTime = dispatchDateTime;
	}
	
	/**
	 * Gets the loading time.
	 *
	 * @return the loading time
	 */
	public String getLoadingTime() {
		return loadingTime;
	}
	
	/**
	 * Sets the loading time.
	 *
	 * @param loadingTime the new loading time
	 */
	public void setLoadingTime(String loadingTime) {
		this.loadingTime = loadingTime;
	}
	
	/**
	 * Gets the load dispatch details t os.
	 *
	 * @return the load dispatch details t os
	 */
	public List<LoadDispatchDetailsTO> getLoadDispatchDetailsTOs() {
		return loadDispatchDetailsTOs;
	}
	
	/**
	 * Sets the load dispatch details t os.
	 *
	 * @param loadDispatchDetailsTOs the new load dispatch details t os
	 */
	public void setLoadDispatchDetailsTOs(
			List<LoadDispatchDetailsTO> loadDispatchDetailsTOs) {
		this.loadDispatchDetailsTOs = loadDispatchDetailsTOs;
	}
	
	/**
	 * Gets the dest city.
	 *
	 * @return the dest city
	 */
	public String getDestCity() {
		return destCity;
	}
	
	/**
	 * Sets the dest city.
	 *
	 * @param destCity the new dest city
	 */
	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}
	
	/**
	 * Gets the load movement vendor.
	 *
	 * @return the load movement vendor
	 */
	public String getLoadMovementVendor() {
		return loadMovementVendor;
	}
	
	/**
	 * Sets the load movement vendor.
	 *
	 * @param loadMovementVendor the new load movement vendor
	 */
	public void setLoadMovementVendor(String loadMovementVendor) {
		this.loadMovementVendor = loadMovementVendor;
	}
	
	/**
	 * Gets the transport number.
	 *
	 * @return the transport number
	 */
	public String getTransportNumber() {
		return transportNumber;
	}
	
	/**
	 * Sets the transport number.
	 *
	 * @param transportNumber the new transport number
	 */
	public void setTransportNumber(String transportNumber) {
		this.transportNumber = transportNumber;
	}
	
	/**
	 * Gets the other transport number.
	 *
	 * @return the other transport number
	 */
	public String getOtherTransportNumber() {
		return otherTransportNumber;
	}
	
	/**
	 * Sets the other transport number.
	 *
	 * @param otherTransportNumber the new other transport number
	 */
	public void setOtherTransportNumber(String otherTransportNumber) {
		this.otherTransportNumber = otherTransportNumber;
	}
	
	/**
	 * Gets the trip serviced by id.
	 *
	 * @return the trip serviced by id
	 */
	public Integer getTripServicedById() {
		return tripServicedById;
	}
	
	/**
	 * Sets the trip serviced by id.
	 *
	 * @param tripServicedById the new trip serviced by id
	 */
	public void setTripServicedById(Integer tripServicedById) {
		this.tripServicedById = tripServicedById;
	}
	
	/**
	 * Gets the route id.
	 *
	 * @return the route id
	 */
	public Integer getRouteId() {
		return routeId;
	}
	
	/**
	 * Sets the route id.
	 *
	 * @param routeId the new route id
	 */
	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}
	
	/**
	 * Gets the origin city id.
	 *
	 * @return the origin city id
	 */
	public Integer getOriginCityId() {
		return originCityId;
	}
	
	/**
	 * Sets the origin city id.
	 *
	 * @param originCityId the new origin city id
	 */
	public void setOriginCityId(Integer originCityId) {
		this.originCityId = originCityId;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.loadmanagement.LoadManagementTO#getRowCount()
	 */
	public int getRowCount() {
		return rowCount;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.loadmanagement.LoadManagementTO#setRowCount(int)
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	
	/**
	 * Gets the send mail.
	 *
	 * @return the send mail
	 */
	public String[] getSendMail() {
		return sendMail;
	}
	
	/**
	 * Sets the send mail.
	 *
	 * @param sendMail the new send mail
	 */
	public void setSendMail(String[] sendMail) {
		this.sendMail = sendMail;
	}	
	public String getPrintDestOfficeType() {
		return printDestOfficeType;
	}

	public void setPrintDestOfficeType(String printDestOfficeType) {
		this.printDestOfficeType = printDestOfficeType;
	}

	public Double getTotalCdWeightPrint() {
		return totalCdWeightPrint;
	}

	public void setTotalCdWeightPrint(Double totalCdWeightPrint) {
		this.totalCdWeightPrint = totalCdWeightPrint;
	}

	/**
	 * @return the transportModeLabelPrint
	 */
	public String getTransportModeLabelPrint() {
		return transportModeLabelPrint;
	}

	/**
	 * @param transportModeLabelPrint the transportModeLabelPrint to set
	 */
	public void setTransportModeLabelPrint(String transportModeLabelPrint) {
		this.transportModeLabelPrint = transportModeLabelPrint;
	}

	public String getEmailDstnOffcType() {
		return emailDstnOffcType;
	}

	public void setEmailDstnOffcType(String emailDstnOffcType) {
		this.emailDstnOffcType = emailDstnOffcType;
	}

	public String getEmailDestOffice() {
		return emailDestOffice;
	}

	public void setEmailDestOffice(String emailDestOffice) {
		this.emailDestOffice = emailDestOffice;
	}

	public String getEmailTransportMode() {
		return emailTransportMode;
	}

	public void setEmailTransportMode(String emailTransportMode) {
		this.emailTransportMode = emailTransportMode;
	}

	public String getEmailServiceByType() {
		return emailServiceByType;
	}

	public void setEmailServiceByType(String emailServiceByType) {
		this.emailServiceByType = emailServiceByType;
	}

	public String getEmailVendor() {
		return emailVendor;
	}

	public void setEmailVendor(String emailVendor) {
		this.emailVendor = emailVendor;
	}

	public String getEmailTransportNumber() {
		return emailTransportNumber;
	}

	public void setEmailTransportNumber(String emailTransportNumber) {
		this.emailTransportNumber = emailTransportNumber;
	}

	public String getEmailVehicleNumber() {
		return emailVehicleNumber;
	}

	public void setEmailVehicleNumber(String emailVehicleNumber) {
		this.emailVehicleNumber = emailVehicleNumber;
	}

	/**
	 * @return the destOfficeTO
	 */
	public OfficeTO getDestOfficeTO() {
		return destOfficeTO;
	}

	/**
	 * @param destOfficeTO the destOfficeTO to set
	 */
	public void setDestOfficeTO(OfficeTO destOfficeTO) {
		this.destOfficeTO = destOfficeTO;
	}

	/**
	 * @return the emailTransportLabel
	 */
	public String getEmailTransportLabel() {
		return emailTransportLabel;
	}

	/**
	 * @param emailTransportLabel the emailTransportLabel to set
	 */
	public void setEmailTransportLabel(String emailTransportLabel) {
		this.emailTransportLabel = emailTransportLabel;
	}
}
