package com.ff.ud.dto;




public class RegionPktDTO {
	
	    private String pktNo;
	    private String origin;
		private String despDate;
		private String despTime;
		private String stcode;
		private String destCode;
		private String serveCode;
		private String transCode;
		private String bplNo;
		private String mbplNo;
		private String mblNo;
		private String pktType;
		private String uVersion;
		private String consCode;
		private String noOfCn;
		private String noOfCo;
		private String noOfBag;
		private String weight;
		private String mplNo;
		private String mplGen;
		private String loadNo;
		private String status;
		private String machineNo;
		private String handCarry;
		private String retTraMi;
		private String brManifes;
		private String lclBrLd;
		private String bagNo;
		private String operator;
		private String hubWeight;
		private String parlno;
		private String bagLock;
		private String mbplLock;
		private String bplSel;
		private String mbplSel;
		private String mblSel;
		private String selection;
		private String field;
		private String insertTime;
		private String dtDbf;
		
		
		/*public boolean assignValuesToDBFArray(Object[] dataArray,RegionPktDTO pktDTO){
			try{
				
				//new column type
				
				dataArray[0]=pktDTO.getOrigin();
				if(null !=pktDTO.getDespDate() && ""!=pktDTO.getDespDate()){
					dataArray[1]= DateUtils.getDateFromString(pktDTO.getDespDate(), DateUtils.FORMAT_yyyy_MM_dd_hh_mm_ss);
				}else{
					dataArray[1]=null;
				}
				//dataArray[1]=pktDTO.getDespDate();
				dataArray[2]=pktDTO.getDespTime();
				dataArray[3]=pktDTO.getStcode();
				dataArray[4]=pktDTO.getDestCode();
				dataArray[5]=pktDTO.getServeCode();
				dataArray[6]=pktDTO.getTransCode();
				dataArray[7]=pktDTO.getPktNo();
				dataArray[8]=pktDTO.getBplNo();
				dataArray[9]=pktDTO.getMbplNo();
				dataArray[10]=pktDTO.getMblNo();
				dataArray[11]=pktDTO.getPktType();
				dataArray[12]=pktDTO.getuVersion();
				dataArray[13]=pktDTO.getConsCode();
				
				if(null != pktDTO.getNoOfCn()&& ""!=pktDTO.getNoOfCn()){
					dataArray[14]=Double.valueOf(pktDTO.getNoOfCn());
				}else {
					dataArray[14]=OpsmanDbfUtil.getNoOfElementsIsZero(Double.valueOf(0));
				}
				if(null != pktDTO.getNoOfCo()&& ""!=pktDTO.getNoOfCo()){
					dataArray[15]=Double.valueOf(pktDTO.getNoOfCo());
				}else {
					dataArray[15]=Double.valueOf(0);
				}
				if(null != pktDTO.getNoOfBag()&& ""!=pktDTO.getNoOfBag()){
					dataArray[16]=Double.valueOf(pktDTO.getNoOfBag());
				}else {
					dataArray[16]=Double.valueOf(0);
				}
				if(null != pktDTO.getWeight()&& ""!=pktDTO.getWeight()){
					dataArray[17]=Double.valueOf(pktDTO.getWeight());
				}else {
					dataArray[17]=Double.valueOf(0);
				}
				dataArray[14]=pktDTO.getNoOfCn();		
				dataArray[15]=pktDTO.getNoOfCo();
				dataArray[16]=pktDTO.getNoOfBag();
				dataArray[17]=pktDTO.getWeight();
				dataArray[18]=pktDTO.getMplNo();
				dataArray[19]="TRUE";//pktDTO.getMplGen();	
				dataArray[20]=pktDTO.getLoadNo();
				dataArray[21]=OpsmanDbfUtil.getCloseFlag(pktDTO.getStatus());
				dataArray[22]="";	//WEBUPD
				dataArray[23]="";  //WEBUPD1
				dataArray[24]="";  //FILENAME
				dataArray[25]=pktDTO.getMachineNo();
				dataArray[26]=pktDTO.getHandCarry();
				dataArray[27]=pktDTO.getRetTraMi();
				dataArray[28]=pktDTO.getBrManifes();
				if(null !=pktDTO.getLclBrLd() && ""!=pktDTO.getLclBrLd()){
					dataArray[29]=Double.valueOf(pktDTO.getLclBrLd());
				}else {
					dataArray[29]= Double.valueOf(0);
				}
				//dataArray[29]=pktDTO.getLclBrLd();
				dataArray[30]=pktDTO.getBagNo();
				dataArray[31]=pktDTO.getOperator();
				
				if(null !=pktDTO.getHubWeight() && ""!=pktDTO.getHubWeight()){
					dataArray[32]=Double.valueOf(pktDTO.getHubWeight());
				}else {
					dataArray[32]= Double.valueOf(0);
				}
				if(null !=pktDTO.getParlno() && ""!=pktDTO.getParlno()){
					dataArray[33]=Double.valueOf(pktDTO.getParlno());
				}else {
					dataArray[33]= Double.valueOf(0);
				}
				//dataArray[32]=pktDTO.getHubWeight();
				//dataArray[33]=pktDTO.getParlno();
				dataArray[34]="";//LFLAG
				dataArray[35]=null; //PFLAG
				dataArray[36]=pktDTO.getBagLock();
				dataArray[37]=pktDTO.getMbplLock();
				dataArray[38]=""; //DATASTATUS
				dataArray[39]=pktDTO.getBplSel();
				dataArray[40]=pktDTO.getMbplSel();
				dataArray[41]=pktDTO.getMblSel();
				dataArray[42]=pktDTO.getSelection();
				
				if(null !=pktDTO.getField() && ""!=pktDTO.getField()){
					dataArray[37]=Double.valueOf(pktDTO.getField());
				}else {
					dataArray[37]= Double.valueOf(0);
				}
				//dataArray[37]=pktDTO.getField();
				dataArray[38]=pktDTO.getInsertTime();		
				dataArray[39]=pktDTO.getDtDbf();
				
				return true;
			}catch (Exception e) {
				e.printStackTrace();
				return false; 
			}
		}*/

		
		public String getDtDbf() {
			return dtDbf;
		}

		public void setDtDbf(String dtDbf) {
			this.dtDbf = dtDbf;
		}


		public String getOrigin() {
			return origin;
		}

		public void setOrigin(String origin) {
			this.origin = origin;
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

		public String getStcode() {
			return stcode;
		}

		public void setStcode(String stcode) {
			this.stcode = stcode;
		}

		public String getDestCode() {
			return destCode;
		}

		public void setDestCode(String destCode) {
			this.destCode = destCode;
		}

		public String getServeCode() {
			return serveCode;
		}

		public void setServeCode(String serveCode) {
			this.serveCode = serveCode;
		}

		public String getTransCode() {
			return transCode;
		}

		public void setTransCode(String transCode) {
			this.transCode = transCode;
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

		public String getPktType() {
			return pktType;
		}

		public void setPktType(String pktType) {
			this.pktType = pktType;
		}

		public String getuVersion() {
			return uVersion;
		}

		public void setuVersion(String uVersion) {
			this.uVersion = uVersion;
		}

		public String getConsCode() {
			return consCode;
		}

		public void setConsCode(String consCode) {
			this.consCode = consCode;
		}

		public String getNoOfCn() {
			return noOfCn;
		}

		public void setNoOfCn(String noOfCn) {
			this.noOfCn = noOfCn;
		}

		public String getNoOfCo() {
			return noOfCo;
		}

		public void setNoOfCo(String noOfCo) {
			this.noOfCo = noOfCo;
		}

		public String getNoOfBag() {
			return noOfBag;
		}

		public void setNoOfBag(String noOfBag) {
			this.noOfBag = noOfBag;
		}

		public String getWeight() {
			return weight;
		}

		public void setWeight(String weight) {
			this.weight = weight;
		}

		public String getMplNo() {
			return mplNo;
		}

		public void setMplNo(String mplNo) {
			this.mplNo = mplNo;
		}

		public String getMplGen() {
			return mplGen;
		}

		public void setMplGen(String mplGen) {
			this.mplGen = mplGen;
		}

		public String getLoadNo() {
			return loadNo;
		}

		public void setLoadNo(String loadNo) {
			this.loadNo = loadNo;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getMachineNo() {
			return machineNo;
		}

		public void setMachineNo(String machineNo) {
			this.machineNo = machineNo;
		}

		public String getHandCarry() {
			return handCarry;
		}

		public void setHandCarry(String handCarry) {
			this.handCarry = handCarry;
		}

		public String getRetTraMi() {
			return retTraMi;
		}

		public void setRetTraMi(String retTraMi) {
			this.retTraMi = retTraMi;
		}

		public String getBrManifes() {
			return brManifes;
		}

		public void setBrManifes(String brManifes) {
			this.brManifes = brManifes;
		}

		public String getLclBrLd() {
			return lclBrLd;
		}

		public void setLclBrLd(String lclBrLd) {
			this.lclBrLd = lclBrLd;
		}

		public String getBagNo() {
			return bagNo;
		}

		public void setBagNo(String bagNo) {
			this.bagNo = bagNo;
		}

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		public String getHubWeight() {
			return hubWeight;
		}

		public void setHubWeight(String hubWeight) {
			this.hubWeight = hubWeight;
		}

		public String getParlno() {
			return parlno;
		}

		public void setParlno(String parlno) {
			this.parlno = parlno;
		}

		

		public String getBagLock() {
			return bagLock;
		}

		public void setBagLock(String bagLock) {
			this.bagLock = bagLock;
		}

		public String getMbplLock() {
			return mbplLock;
		}

		public void setMbplLock(String mbplLock) {
			this.mbplLock = mbplLock;
		}


		public String getBplSel() {
			return bplSel;
		}

		public void setBplSel(String bplSel) {
			this.bplSel = bplSel;
		}

		public String getMbplSel() {
			return mbplSel;
		}

		public void setMbplSel(String mbplSel) {
			this.mbplSel = mbplSel;
		}

		public String getMblSel() {
			return mblSel;
		}

		public void setMblSel(String mblSel) {
			this.mblSel = mblSel;
		}

		public String getSelection() {
			return selection;
		}

		public void setSelection(String selection) {
			this.selection = selection;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}
		
		public String getInsertTime() {
			return insertTime;
		}

		public void setInsertTime(String insertTime) {
			this.insertTime = insertTime;
		}
	}
