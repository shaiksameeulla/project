package com.ff.ud.dto;

public class MblDTO {
	
	private String origin;
	private String hub;
	private String destCode;
	private String despDest;
	private String despDate;
	private String despTime;
	private String mblNo;
	private String vehName;
	private String webupd1;
	private String filename;
	private String webupd;
	private String vehNo;
	private String bagWeight;
	private String mblFlag;
	private String coLoader;
	private String cdrrNo;
	private String totalBag;
	private String totBagWt;
	private String despType;
	private String despTo;
	private String selection;
	private String dtdbf;
	
	public boolean assignValuesToDBFArray(Object[] dataArray,MblDTO mblDTO){
		try{
			dataArray[0]= mblDTO.getOrigin();
			dataArray[1]= "";
			dataArray[2]= "";
			dataArray[3]= "";
			dataArray[4]= mblDTO.getDespDate();
			dataArray[5]= "";
			dataArray[6]= mblDTO.getMblNo();
			dataArray[7]= mblDTO.getVehName();
			dataArray[8]= "";
			dataArray[9]= "";
			dataArray[10]= "";
			dataArray[11]= mblDTO.getVehNo();
			dataArray[12]= mblDTO.getBagWeight();
			dataArray[13]= mblDTO.getMblFlag();
			dataArray[14]= mblDTO.getCoLoader();
			dataArray[15]= mblDTO.getCdrrNo();
			dataArray[16]= mblDTO.getTotalBag();
			dataArray[17]= "";
			dataArray[18]= mblDTO.getDespType();
			dataArray[19]= mblDTO.getDespTo();
			dataArray[20]= mblDTO.getSelection();
			dataArray[21]=mblDTO.getDtdbf();
			
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false; 
		}
	
	}
	
	
	

	public String getDtdbf() {
		return dtdbf;
	}


	public void setDtdbf(String dtdbf) {
		this.dtdbf = dtdbf;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
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

	public String getDespDest() {
		return despDest;
	}

	public void setDespDest(String despDest) {
		this.despDest = despDest;
	}

	public String getDespDate() {
		return despDate;
	}

	public void setDespDate(String despDate) {
		this.despDate = despDate;
	}

	public String getDespTime() {
		return despTime;
	}

	public void setDespTime(String despTime) {
		this.despTime = despTime;
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

	public String getWebupd1() {
		return webupd1;
	}

	public void setWebupd1(String webupd1) {
		this.webupd1 = webupd1;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getWebupd() {
		return webupd;
	}

	public void setWebupd(String webupd) {
		this.webupd = webupd;
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

	public String getTotalBag() {
		return totalBag;
	}

	public void setTotalBag(String totalBag) {
		this.totalBag = totalBag;
	}

	public String getTotBagWt() {
		return totBagWt;
	}

	public void setTotBagWt(String totBagWt) {
		this.totBagWt = totBagWt;
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
	
	
	

}
