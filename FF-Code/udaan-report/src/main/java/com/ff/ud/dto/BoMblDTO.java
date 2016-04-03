package com.ff.ud.dto;

import com.ff.ud.utils.DateUtils;
import com.ff.ud.utils.StringUtils;




public class BoMblDTO {
	
	private String hub;
	private String destCode;
	private String despDate;
	private String mblNo;
	private String vehName;
	private String vehNo;
	private String bagWeight;
	private String mblFlag;
	private String despType;
	private String despTo;
	private String selection;
	private String fileId;
	private String origin;
	private String insertTime;
	private String drUdaan;
	private String dtUdaan;
	private String dtDbf;
	
	
	
	public boolean assignValuesToDBFArray(Object[] dataArray,BoMblDTO boMblDto){
		try{
			dataArray[0]= boMblDto.getOrigin();      //columnName[0]="ORIGIN,C,3";	        
			dataArray[1]= boMblDto.getHub();	//columnName[1]="HUB,C,3";		
			dataArray[2]= boMblDto.getDestCode();	//columnName[2]="DEST_CODE,C,3";		
			dataArray[3]= "";			//columnName[3]="DESP_DEST,C,3";		
			dataArray[4]= DateUtils.getDateFromString(boMblDto.getDespDate(), DateUtils.FORMAT_yyyy_MM_dd_hh_mm_ss);       //columnName[4]="DESP_DATE,D";		
			dataArray[5]= "";			//columnName[5]="DESP_TIME,C,5";		
			dataArray[6]= boMblDto.getMblNo();	//columnName[6]="MBL_NO,C,10";		
			dataArray[7]= boMblDto.getVehName();	//columnName[7]="VEH_NAME,C,35";		
			dataArray[8]= "";			//columnName[8]="WEBUPD1,C,1";		
			dataArray[9]= "";			//columnName[9]="FILENAME,C,50";		
			dataArray[10]= "";			//columnName[10]="WEBUPD,C,1";		
			dataArray[11]= boMblDto.getVehNo();	//columnName[11]="VEH_NO,C,20";		
			dataArray[12]= StringUtils.getDoubleValueFromString(boMblDto.getBagWeight());				       //columnName[12]="BAG_WEIGHT,N,8,3";	
			dataArray[13]= boMblDto.getMblFlag();	//columnName[13]="MBL_FLAG,C,1";		
			dataArray[14]= "";			//columnName[14]="CO_LOADER,C,30";	
			dataArray[15]= "";			//columnName[15]="CDRR_NO,C,15";		
			dataArray[16]= StringUtils.getDoubleValueFromString("0");	//columnName[16]="TOT_BAG,N,5,0";		
			dataArray[17]= StringUtils.getDoubleValueFromString("0");	//columnName[17]="TOT_BAG_WT,N,10,3";	
			dataArray[18]= boMblDto.getDespType();	//columnName[18]="DESP_TYPE,C,15";	
			dataArray[19]= boMblDto.getDespTo();	//columnName[19]="DESP_TO,C,15";		
			dataArray[20]= boMblDto.getSelection();	//columnName[20]="SELECTION,C,1";	        
		
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false; 
		}
	
	}
	
	
	
	public String getHub() {
		return hub;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public String getDestCode() {
		return destCode;
	}
	public void setDestCode(String destCode) {
		this.destCode = destCode;
	}
	public String getDespDate() {
		return despDate;
	}
	public void setDespDate(String despDate) {
		this.despDate = despDate;
	}
	public String getMblNo() {
		return mblNo;
	}
	public void setMblNo(String mblNo) {
		this.mblNo = mblNo;
	}
	public String getVehName() {
		return vehName;
	}
	public void setVehName(String vehName) {
		this.vehName = vehName;
	}
	public String getVehNo() {
		return vehNo;
	}
	public void setVehNo(String vehNo) {
		this.vehNo = vehNo;
	}
	public String getBagWeight() {
		return bagWeight;
	}
	public void setBagWeight(String bagWeight) {
		this.bagWeight = bagWeight;
	}
	public String getMblFlag() {
		return mblFlag;
	}
	public void setMblFlag(String mblFlag) {
		this.mblFlag = mblFlag;
	}
	public String getDespType() {
		return despType;
	}
	public void setDespType(String despType) {
		this.despType = despType;
	}
	public String getDespTo() {
		return despTo;
	}
	public void setDespTo(String despTo) {
		this.despTo = despTo;
	}
	public String getSelection() {
		return selection;
	}
	public void setSelection(String selection) {
		this.selection = selection;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getDrUdaan() {
		return drUdaan;
	}
	public void setDrUdaan(String drUdaan) {
		this.drUdaan = drUdaan;
	}
	public String getDtUdaan() {
		return dtUdaan;
	}
	public void setDtUdaan(String dtUdaan) {
		this.dtUdaan = dtUdaan;
	}
	public String getDtDbf() {
		return dtDbf;
	}
	public void setDtDbf(String dtDbf) {
		this.dtDbf = dtDbf;
	}



	@Override
	public String toString() {
		return "BoMblDTO [hub=" + hub + ", destCode=" + destCode
				+ ", despDate=" + despDate + ", mblNo=" + mblNo + ", vehName="
				+ vehName + ", vehNo=" + vehNo + ", bagWeight=" + bagWeight
				+ ", mblFlag=" + mblFlag + ", despType=" + despType
				+ ", despTo=" + despTo + ", selection=" + selection
				+ ", fileId=" + fileId + ", origin=" + origin + ", insertTime="
				+ insertTime + ", drUdaan=" + drUdaan + ", dtUdaan=" + dtUdaan
				+ ", dtDbf=" + dtDbf + "]";
	}


}
