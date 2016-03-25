package com.ff.coloading;

import java.util.Collections;
import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class TrainColoadingTO extends CGBaseTO {

	private static final long serialVersionUID = -1625486300796456617L;

	private Integer origionRegionID;

	private Integer origionCityID;

	private Integer destinationRegionID;

	private Integer destinationCityID;

	private Integer vendorId;

	private String effectiveFrom;
	
	private List<TrainDetailsTO> trainDetailsList; 

	private String storeStatus;
	
	private String coloaderType;

	private String renewFlag;
	
	private Boolean isRenewalAllow = true;

	public Integer getOrigionRegionID() {
		return origionRegionID;
	}

	public void setOrigionRegionID(Integer origionRegionID) {
		this.origionRegionID = origionRegionID;
	}

	public Integer getOrigionCityID() {
		return origionCityID;
	}

	public void setOrigionCityID(Integer origionCityID) {
		this.origionCityID = origionCityID;
	}

	public Integer getDestinationRegionID() {
		return destinationRegionID;
	}

	public void setDestinationRegionID(Integer destinationRegionID) {
		this.destinationRegionID = destinationRegionID;
	}

	public Integer getDestinationCityID() {
		return destinationCityID;
	}

	public void setDestinationCityID(Integer destinationCityID) {
		this.destinationCityID = destinationCityID;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getEffectiveFrom() {
		return effectiveFrom;
	}

	public void setEffectiveFrom(String effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}

	public List<TrainDetailsTO> getTrainDetailsList() {
		if(trainDetailsList != null){
			Collections.sort(trainDetailsList);
		}
		return trainDetailsList;
	}

	public void setTrainDetailsList(List<TrainDetailsTO> trainDetailsList) {
		this.trainDetailsList = trainDetailsList;
	}

	public String getStoreStatus() {
		return storeStatus;
	}

	public void setStoreStatus(String storeStatus) {
		this.storeStatus = storeStatus;
	}

	public String getRenewFlag() {
		return renewFlag;
	}

	public void setRenewFlag(String renewFlag) {
		this.renewFlag = renewFlag;
	}

	public Boolean getIsRenewalAllow() {
		return isRenewalAllow;
	}

	public void setIsRenewalAllow(Boolean isRenewalAllow) {
		this.isRenewalAllow = isRenewalAllow;
	}
	
	public String getColoaderType() {
		return coloaderType;
	}

	public void setColoaderType(String coloaderType) {
		this.coloaderType = coloaderType;
	}	
	
}
