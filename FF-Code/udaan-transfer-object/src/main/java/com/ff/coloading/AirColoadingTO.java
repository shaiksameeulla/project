package com.ff.coloading;

import java.util.Collections;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class AirColoadingTO extends CGBaseTO {

	private static final long serialVersionUID = -1625486300796456617L;

	private Integer origionRegionID;

	private Integer origionCityID;

	private Integer destinationRegionID;

	private Integer destinationCityID;

	private Integer vendorId;

	private String effectiveFrom;

	private String cdType = "CD";

	private FormFile xlsTemplateFile;

	private Integer sspWeightSlab;
	
	private List<AwbTO> awbToList;
	
	private List<CdTO> cdToList;
	
	private String storeStatus;
	
	private String renewFlag;
	
	private Boolean isRenewalAllow = true;
	
	private String coloaderType;

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

	public String getCdType() {
		return cdType;
	}

	public void setCdType(String cdType) {
		this.cdType = cdType;
	}

	public FormFile getXlsTemplateFile() {
		return xlsTemplateFile;
	}

	public void setXlsTemplateFile(FormFile xlsTemplateFile) {
		this.xlsTemplateFile = xlsTemplateFile;
	}

	public Integer getSspWeightSlab() {
		return sspWeightSlab;
	}

	public void setSspWeightSlab(Integer sspWeightSlab) {
		this.sspWeightSlab = sspWeightSlab;
	}

	public List<AwbTO> getAwbToList() {
		if(awbToList != null){
			Collections.sort(awbToList);
		}
		return awbToList;
	}

	public void setAwbToList(List<AwbTO> awbToList) {
		this.awbToList = awbToList;
	}

	public List<CdTO> getCdToList() {
		if(cdToList != null){
			Collections.sort(cdToList);
		}
		return cdToList;
	}

	public void setCdToList(List<CdTO> cdToList) {
		this.cdToList = cdToList;
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
