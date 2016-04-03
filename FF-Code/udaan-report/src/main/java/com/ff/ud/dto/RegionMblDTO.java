package com.ff.ud.dto;

import com.ff.ud.utils.DateUtils;




public class RegionMblDTO implements Cloneable{
	
	private String despDate;
	private String mblNo;
	private String vehNo;
	private String vehName;
	private String bagWeight;
	private String totalBag;
	private String mblFlag;
	private String despType;
	private String coLoader;
	private String cdrrNo;
	private String despTo;
	private String selection;
	private String origin;
	private String fileId;
	private String insertTime;
	private String dtUdaan;
	private String dtDbf;
	private String despDest;
	private String destCode;
	
	public String getDestCode() {
		return destCode;
	}

	public void setDestCode(String destCode) {
		this.destCode = destCode;
	}
	
	public String getDespDest() {
		return despDest;
	}

	public void setDespDest(String despDest) {
		this.despDest = despDest;
	}

	@Override
	public RegionMblDTO clone() throws CloneNotSupportedException {
		return (RegionMblDTO) super.clone();
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



	public String getVehNo() {
		return vehNo;
	}



	public void setVehNo(String vehNo) {
		this.vehNo = vehNo;
	}



	public String getVehName() {
		return vehName;
	}



	public void setVehName(String vehName) {
		this.vehName = vehName;
	}



	public String getBagWeight() {
		return bagWeight;
	}



	public void setBagWeight(String bagWeight) {
		this.bagWeight = bagWeight;
	}



	public String getTotalBag() {
		return totalBag;
	}



	public void setTotalBag(String totalBag) {
		this.totalBag = totalBag;
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



	public String getCoLoader() {
		return coLoader;
	}



	public void setCoLoader(String coLoader) {
		this.coLoader = coLoader;
	}



	public String getCdrrNo() {
		return cdrrNo;
	}



	public void setCdrrNo(String cdrrNo) {
		this.cdrrNo = cdrrNo;
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



	public String getOrigin() {
		return origin;
	}



	public void setOrigin(String origin) {
		this.origin = origin;
	}



	public String getFileId() {
		return fileId;
	}



	public void setFileId(String fileId) {
		this.fileId = fileId;
	}



	public String getInsertTime() {
		return insertTime;
	}



	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
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



	public boolean assignValuesToDBFArray(Object[] dataArray,RegionMblDTO boMblDto){
		try{
			/*dataArray[0]= boMblDto.getMblNo();
			dataArray[1]= boMblDto.getDespDate();
			dataArray[2]= boMblDto.getVehNo();
			dataArray[3]= boMblDto.getVehName();
			dataArray[4]= boMblDto.getBagWeight();
			dataArray[5]= boMblDto.getTotalBag();
			dataArray[6]= boMblDto.getMblFlag();
			dataArray[7]= boMblDto.getDespType();
			dataArray[8]= boMblDto.getCoLoader();
			dataArray[9]= boMblDto.getCdrrNo();
			dataArray[10]= boMblDto.getDespTo();
			dataArray[11]= boMblDto.getSelection();
			dataArray[12]= boMblDto.getOrigin();
			dataArray[13]= boMblDto.getFileId();
			dataArray[14]= boMblDto.getInsertTime();
			dataArray[15]= boMblDto.getDtUdaan();
			dataArray[16]= boMblDto.getDtDbf();
			*/
			
			//NEW Column order
			dataArray[0]= boMblDto.getOrigin();
			//dataArray[1]= boMblDto.getDespDate();
			if(null !=boMblDto.getDespDate() && ""!=boMblDto.getDespDate()){
				dataArray[1]= DateUtils.getDateFromString(boMblDto.getDespDate(), DateUtils.FORMAT_yyyy_MM_dd_hh_mm_ss);
			}else{
				dataArray[1]=null;
			}
			
			dataArray[2]=""; //desp time
			dataArray[3]= boMblDto.getMblNo();
			dataArray[4]=boMblDto.getDestCode(); //dest code
			dataArray[5]=boMblDto.getDespDest(); //DESP_DEST
			dataArray[6]=boMblDto.getVehNo();
			dataArray[7]=boMblDto.getVehName();
			dataArray[8]=""; //VIA_FLAG
			//dataArray[9]=boMblDto.getBagWeight();
			if(null != boMblDto.getBagWeight()&& ""!=boMblDto.getBagWeight()){
				dataArray[9]=Double.valueOf(boMblDto.getBagWeight());
			}else {
				dataArray[9]=null;//Double.valueOf(0);
			}
			
			dataArray[10]=""; //WEBUPD
			dataArray[11]=""; //WEBUPD1
			dataArray[12]=""; //FILENAME
			dataArray[13]=null; //TOTAL_BAG N,6,0
			dataArray[14]="Y";//boMblDto.getMblFlag();
			dataArray[15]=boMblDto.getDespType();
			dataArray[16]=boMblDto.getCoLoader();
			dataArray[17]=boMblDto.getCdrrNo();
			//dataArray[18]=boMblDto.getTotalBag();
			if(null != boMblDto.getTotalBag()&& ""!=boMblDto.getTotalBag()){
				dataArray[18]=Double.valueOf(boMblDto.getTotalBag());
			}else {
				dataArray[18]=Double.valueOf(0);
			}
			dataArray[19]=null; //TOT_BAG_WT,N,10,3
			dataArray[20]="destination";
			dataArray[21]=boMblDto.getSelection();
			
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false; 
		}
	
	}


	
}
