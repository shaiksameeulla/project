package com.ff.ud.dto;




public class BoPktDTO {
	
	private String despDate;
	private String pktNo;
	private String bplNo;
	private String mbplNo;
	private String mblNo;
	private String loadNo;
	private String conCnt;
	private String csaCd;
	private String type;
	private String transCode;
	private String transDest;
	private String closFlag;
	private String counter;
	private String channel;
	private String mConCd;
	private String machine;
	private String weight;
	private String hubWeight;
	private String direct;
	private String hubLoad;
	private String bagLock;
	private String webupd;
	private String fileName;
	private String selection;
	private String mbplLock;
	private String noOfBag;
	private String fileId;
	private String origin;
	private String insertTime;
	private String dtudaan;
	private String drudaan;
	private String dtdbf;
	
	
	/*public boolean assignValuesToDBFArray(Object[] dataArray,BoPktDTO boPktDTO){
		try{
			dataArray[0]=boPktDTO.getOrigin();	//[0]="ORIGIN,C,3";	
			dataArray[1]=DateUtils.getDateFromString(boPktDTO.getDespDate(), DateUtils.FORMAT_yyyy_MM_dd_hh_mm_ss);	//[1]="DESP_DATE,D";
			
			dataArray[2]=boPktDTO.getCsaCd();	//[2]="CSA_CD,C,5";	
			dataArray[3]=boPktDTO.getPktNo();	//[3]="PKT_NO,C,10";	
			dataArray[4]=OpsmanDbfUtil.getBplNO(boPktDTO.getBplNo());	//[4]="BPL_NO,C,10";	
			dataArray[5]=OpsmanDbfUtil.getBplNO(boPktDTO.getMbplNo());	//[5]="MBPL_NO,C,10";	
			dataArray[6]=boPktDTO.getMblNo();	//[6]="MBL_NO,C,10";	
			dataArray[7]=StringUtils.getDoubleValueFromString(boPktDTO.getLoadNo());	//[7]="LOAD_NO,N,1,0";	
			dataArray[8]=StringUtils.getDoubleValueFromString(boPktDTO.getConCnt());	//[8]="CON_CNT,N,4,0";	
			dataArray[9]=OpsmanDbfUtil.getConsingnmentType(boPktDTO.getType());	//[9]="TYPE,C,1";         
			dataArray[10]=boPktDTO.getTransCode();	//[10]="TRANS_CODE,C,6";	
			dataArray[11]=boPktDTO.getTransDest();	//[11]="TRANS_DEST,C,6";	
			dataArray[12]=OpsmanDbfUtil.getCloseFlag(boPktDTO.getClosFlag());	//[12]="CLOS_FLAG,C,1";	
			dataArray[13]=boPktDTO.getCounter();	//[13]="COUNTER,C,1";	
			dataArray[14]=boPktDTO.getChannel();	//[14]="CHANNEL,C,1";	
			dataArray[15]=boPktDTO.getmConCd();	//[15]="M_CON_CD,C,8";	
			dataArray[16]="A";	//[16]="MACHINE,C,1";
			
			dataArray[17]=StringUtils.getDoubleValueFromString(boPktDTO.getWeight());	//[17]="WEIGHT,N,8,3";
			
			dataArray[18]="D";			//[18]="DATASTATUS,C,1";	
			dataArray[19]="D";			//[19]="BAG_TYPE,C,3";	
			dataArray[20]=StringUtils.getDoubleValueFromString(boPktDTO.getHubWeight());	//[20]="HUB_WEIGHT,N,8,3";
			dataArray[21]=OpsmanDbfUtil.getManifestDirectionForDBF(boPktDTO.getDirect());	//[21]="DIRECT,C,1";	
			dataArray[22]=boPktDTO.getHubLoad();	//[22]="HUB_LOAD,C,4";	
			dataArray[23]=boPktDTO.getBagLock();	//[23]="BAG_LOCK,C,10";	
			dataArray[24]=boPktDTO.getWebupd();	//[24]="WEBUPD,C,1";	
			dataArray[25]=boPktDTO.getFileName();	//[25]="FILENAME,C,50";	
			dataArray[26]=boPktDTO.getSelection();	//[26]="SELECTION,C,1";	
			dataArray[27]=boPktDTO.getMbplLock();	//[27]="MBPL_LOCK,C,10";	
			dataArray[28]=StringUtils.getDoubleValueFromString(boPktDTO.getNoOfBag());    //[28]="NO_OF_BAG,N,5,0";	
			//dataArray[43]=pktDTO.getDtDbf();
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false; 
		}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getDespDate() {
		return despDate;
	}
	public void setDespDate(String despDate) {
		this.despDate = despDate;
	}
	public String getPktNo() {
		return pktNo;
	}
	public void setPktNo(String pktNo) {
		this.pktNo = pktNo;
	}
	public String getBplNo() {
		return bplNo;
	}
	public void setBplNo(String bplNo) {
		this.bplNo = bplNo;
	}
	public String getMbplNo() {
		return mbplNo;
	}
	public void setMbplNo(String mbplNo) {
		this.mbplNo = mbplNo;
	}
	public String getMblNo() {
		return mblNo;
	}
	public void setMblNo(String mblNo) {
		this.mblNo = mblNo;
	}
	public String getLoadNo() {
		return loadNo;
	}
	public void setLoadNo(String loadNo) {
		this.loadNo = loadNo;
	}
	public String getConCnt() {
		return conCnt;
	}
	public void setConCnt(String conCnt) {
		this.conCnt = conCnt;
	}
	public String getCsaCd() {
		return csaCd;
	}
	public void setCsaCd(String csaCd) {
		this.csaCd = csaCd;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getTransDest() {
		return transDest;
	}
	public void setTransDest(String transDest) {
		this.transDest = transDest;
	}
	public String getClosFlag() {
		return closFlag;
	}
	public void setClosFlag(String closFlag) {
		this.closFlag = closFlag;
	}
	public String getCounter() {
		return counter;
	}
	public void setCounter(String counter) {
		this.counter = counter;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getmConCd() {
		return mConCd;
	}
	public void setmConCd(String mConCd) {
		this.mConCd = mConCd;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getHubWeight() {
		return hubWeight;
	}
	public void setHubWeight(String hubWeight) {
		this.hubWeight = hubWeight;
	}
	public String getDirect() {
		return direct;
	}
	public void setDirect(String direct) {
		this.direct = direct;
	}
	public String getHubLoad() {
		return hubLoad;
	}
	public void setHubLoad(String hubLoad) {
		this.hubLoad = hubLoad;
	}
	public String getBagLock() {
		return bagLock;
	}
	public void setBagLock(String bagLock) {
		this.bagLock = bagLock;
	}
	public String getWebupd() {
		return webupd;
	}
	public void setWebupd(String webupd) {
		this.webupd = webupd;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSelection() {
		return selection;
	}
	public void setSelection(String selection) {
		this.selection = selection;
	}
	
	























	























	public String getNoOfBag() {
		return noOfBag;
	}
	public void setNoOfBag(String noOfBag) {
		this.noOfBag = noOfBag;
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
	public String getDtudaan() {
		return dtudaan;
	}
	public void setDtudaan(String dtudaan) {
		this.dtudaan = dtudaan;
	}
	public String getDrudaan() {
		return drudaan;
	}
	public void setDrudaan(String drudaan) {
		this.drudaan = drudaan;
	}
	public String getDtdbf() {
		return dtdbf;
	}
	public void setDtdbf(String dtdbf) {
		this.dtdbf = dtdbf;
	}


	public String getMbplLock() {
		return mbplLock;
	}


	public void setMbplLock(String mbplLock) {
		this.mbplLock = mbplLock;
	}























	@Override
	public String toString() {
		return "BoPktDTO [despDate=" + despDate + ", pktNo=" + pktNo
				+ ", bplNo=" + bplNo + ", mbplNo=" + mbplNo + ", mblNo="
				+ mblNo + ", loadNo=" + loadNo + ", conCnt=" + conCnt
				+ ", csaCd=" + csaCd + ", type=" + type + ", transCode="
				+ transCode + ", transDest=" + transDest + ", closFlag="
				+ closFlag + ", counter=" + counter + ", channel=" + channel
				+ ", mConCd=" + mConCd + ", machine=" + machine + ", weight="
				+ weight + ", hubWeight=" + hubWeight + ", direct=" + direct
				+ ", hubLoad=" + hubLoad + ", bagLock=" + bagLock + ", webupd="
				+ webupd + ", fileName=" + fileName + ", selection="
				+ selection + ", mbplLock=" + mbplLock + ", noOfBag=" + noOfBag
				+ ", fileId=" + fileId + ", origin=" + origin + ", insertTime="
				+ insertTime + ", dtudaan=" + dtudaan + ", drudaan=" + drudaan
				+ ", dtdbf=" + dtdbf + "]";
	}


	




}
