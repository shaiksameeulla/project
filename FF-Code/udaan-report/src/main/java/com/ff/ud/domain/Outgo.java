package com.ff.ud.domain;



public class Outgo {
	
	private String U_BR_CD;
	private String BKING_DATE;
	private String BOOK_TIME;
	private String DESP_DATE;
	private String CON_NO;
	private String TRAN_CODE;
	private String DEST_CODE;
	private String USERID;
	private String U_EMP_NO;
	private String BR_PKT_NO;
	private String MPL_NO;
	private String STCODE;
	private String PKT_NO;
	private String LOAD_NO;
	private String LCL_BR_LD;
	private String CON_CODE;
	private String CON_TYPE;
	private String MPL_FLAG;
	private String FORMS;
	private String MAN_FLAG;
	private String MISRTE_RET;
	private String LCT;
	private String LCT_AMT;
	private String HUB_WEIGHT;
	private String PIECES;
	private String RET_CD;
	private String DECLARE;
	private String VALUE;
	private String LENGTH;
	private String BREADTH;
	private String HEIGHT;
	private String U_VERSION;
	private String INSURED;
	private String FORM;
	private String TOP_AMT;
	private String EXPO_NET;
	private String WEBUPD;
	private String WEBUPD1;
	private String LD_DESP;
	private String FILENAME;
	private String COD_AMT;
	private String LLOAD;
	private String FLAG;
	private String M_CON_CD;
	private String CONTENTS;
	private String INVOICE;
	private String PINCODE;
	private String DIRECT;
	private String CLIENTID;
	private String U_CON_CD;
	private String CLIENTNO;
	private String CONSNAME;
	private String VENDORID;
	private String VENDORNAME;
	private String FILEID;
	private String ORIGIN;
	private String INSERTTIME;
	private String DT_UDAAN;
	private String DT_DBF;
	
	
	/**
	 * Returns true if successfully able to add the value to the array
	 * @param dataArray : <code>Object</code> having data to be add into the DBF file
	 * @param outgo : Object having data from DB
	 * @return
	 */
	
	/*public boolean assignValuesToDBFArray(Object[] dataArray,Outgo outgo){
		try{
			dataArray[0]=outgo.getORIGIN();               //[0]="ORIGIN,C,3";
			dataArray[1]=outgo.getU_BR_CD();              //[1]="U_BR_CD,C,4";
			dataArray[2]=DateUtils.getDateFromString(outgo.getBKING_DATE(), DateUtils.FORMAT_yyyy_MM_dd_hh_mm_ss);;             //[2]="BKING_DATE,D";
			dataArray[3]=outgo.getBOOK_TIME();            //[3]="BOOK_TIME,C,5";
			dataArray[4]=DateUtils.getDateFromString(outgo.getDESP_DATE(), DateUtils.FORMAT_yyyy_MM_dd_hh_mm_ss);              //[4]="DESP_DATE,D";
			dataArray[5]=outgo.getCON_NO();              //[5]="CON_NO,C,12";
			dataArray[6]=outgo.getTRAN_CODE();            //[6]="TRAN_CODE,C,6";
			dataArray[7]=outgo.getDEST_CODE();            //[7]="DEST_CODE,C,3";
			dataArray[8]=StringUtils.getDoubleValueFromString(outgo.getUSERID());             //[8]="USERID,N,3,0";
			dataArray[9]="";             //[9]="U_EMP_NO,C,9";
			dataArray[10]=outgo.getBR_PKT_NO();          //[10]="BR_PKT_NO,C,10";
			dataArray[11]=outgo.getMPL_NO();             //[11]="MPL_NO,C,10";
			dataArray[12]=outgo.getSTCODE();              //[12]="STCODE,C,3";
			dataArray[13]=outgo.getPKT_NO();             //[13]="PKT_NO,C,10";
			dataArray[14]=outgo.getLOAD_NO();             //[14]="LOAD_NO,C,4";
			dataArray[15]=StringUtils.getDoubleValueFromString(outgo.getLCL_BR_LD());         //[15]="LCL_BR_LD,N,1,0";
			dataArray[16]=OpsmanDbfUtil.getConCode(outgo.getCON_CODE());            //[16]="CON_CODE,C,1";
			dataArray[17]=OpsmanDbfUtil.getConsingnmentType(outgo.getCON_TYPE());            //[17]="CON_TYPE,C,1";
			dataArray[18]=outgo.getMPL_FLAG();            //[18]="MPL_FLAG,C,1";
			dataArray[19]=outgo.getFORMS();              //[19]="FORMS,C,10";
			dataArray[20]=outgo.getMAN_FLAG();            //[20]="MAN_FLAG,C,1";
			dataArray[21]=outgo.getMISRTE_RET();          //[21]="MISRTE_RET,C,1";
			dataArray[22]=outgo.getLCT();                 //[22]="LCT,C,1";
			dataArray[23]=StringUtils.getDoubleValueFromString(outgo.getLCT_AMT());           //[23]="LCT_AMT,N,8,2";
			dataArray[24]=StringUtils.getDoubleValueFromString(outgo.getHUB_WEIGHT());        //[24]="HUB_WEIGHT,N,8,3";
			dataArray[25]=StringUtils.getDoubleValueFromString(outgo.getPIECES());            //[25]="PIECES,N,3,0";
			dataArray[26]=StringUtils.getDoubleValueFromString(outgo.getRET_CD());            //[26]="RET_CD,N,2,0";
			dataArray[27]=StringUtils.getDoubleValueFromString(outgo.getDECLARE());          //[27]="DECLARE,N,10,2";
			dataArray[28]=StringUtils.getDoubleValueFromString(outgo.getVALUE());            //[28]="VALUE,N,10,2";
			dataArray[29]=StringUtils.getDoubleValueFromString(outgo.getLENGTH());            //[29]="LENGTH,N,6,2";
			dataArray[30]=StringUtils.getDoubleValueFromString(outgo.getBREADTH());           //[30]="BREADTH,N,6,2";
			dataArray[31]=StringUtils.getDoubleValueFromString(outgo.getHEIGHT());            //[31]="HEIGHT,N,6,2";
			dataArray[32]=outgo.getU_VERSION();          //[32]="U_VERSION,C,50";
			dataArray[33]=outgo.getINSURED();             //[33]="INSURED,C,1";
			dataArray[34]=outgo.getFORM();               //[34]="FORM,C,10";
			dataArray[35]=StringUtils.getDoubleValueFromString(outgo.getTOP_AMT());           //[35]="TOP_AMT,N,8,2";
			dataArray[36]=outgo.getEXPO_NET();            //[36]="EXPO_NET,C,1";
			dataArray[37]=outgo.getWEBUPD();              //[37]="WEBUPD,C,1";
			dataArray[38]=outgo.getWEBUPD1();             //[38]="WEBUPD1,C,1";
			dataArray[39]=outgo.getLD_DESP();             //[39]="LD_DESP,C,1";
			dataArray[40]=outgo.getFILENAME();           //[40]="FILENAME,C,50";
			dataArray[41]=StringUtils.getDoubleValueFromString(outgo.getCOD_AMT());           //[41]="COD_AMT,N,8,2";
			dataArray[42]=outgo.getLLOAD();               //[42]="LLOAD,C,2";
			dataArray[43]=outgo.getFLAG();                //[43]="FLAG,C,1";
			dataArray[44]=outgo.getM_CON_CD();            //[44]="M_CON_CD,C,8";
			dataArray[45]=outgo.getCONTENTS();          //[45]="CONTENTS,C,100";
			dataArray[46]=outgo.getINVOICE();           //[46]="INVOICE,C,100";
			dataArray[47]=StringUtils.getDoubleValueFromString(outgo.getPINCODE());           //[47]="PINCODE,N,6,0";
			dataArray[48]=OpsmanDbfUtil.getManifestDirectionForDBF(outgo.getDIRECT());              //[48]="DIRECT,C,1";
			dataArray[49]=outgo.getCLIENTID();            //[49]="CLIENTID,C,8";
			dataArray[50]=StringUtils.getDoubleValueFromString(outgo.getU_CON_CD());         //[50]="U_CON_CD,N,10,0";
			dataArray[51]=outgo.getCLIENTNO();           //[51]="CLIENTNO,C,25";
			dataArray[52]=outgo.getCONSNAME();           //[52]="CONSNAME,C,40";
			dataArray[53]=outgo.getVENDORID();            //[53]="VENDORID,C,8";
			dataArray[54]=outgo.getVENDORNAME();         //[54]="VENDORNAME,C,45";
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}*/

	
	
	
	
	
	public String getDT_UDAAN() {
		return DT_UDAAN;
	}
	public void setDT_UDAAN(String dT_UDAAN) {
		DT_UDAAN = dT_UDAAN;
	}
	public String getDT_DBF() {
		return DT_DBF;
	}
	public void setDT_DBF(String dT_DBF) {
		DT_DBF = dT_DBF;
	}
	public String getU_BR_CD() {
		return U_BR_CD;
	}
	public void setU_BR_CD(String u_BR_CD) {
		U_BR_CD = u_BR_CD;
	}
	public String getBKING_DATE() {
		return BKING_DATE;
	}
	public void setBKING_DATE(String bKING_DATE) {
		BKING_DATE = bKING_DATE;
	}
	public String getBOOK_TIME() {
		return BOOK_TIME;
	}
	public void setBOOK_TIME(String bOOK_TIME) {
		BOOK_TIME = bOOK_TIME;
	}
	public String getDESP_DATE() {
		return DESP_DATE;
	}
	public void setDESP_DATE(String dESP_DATE) {
		DESP_DATE = dESP_DATE;
	}
	public String getCON_NO() {
		return CON_NO;
	}
	public void setCON_NO(String cON_NO) {
		CON_NO = cON_NO;
	}
	public String getTRAN_CODE() {
		return TRAN_CODE;
	}
	public void setTRAN_CODE(String tRAN_CODE) {
		TRAN_CODE = tRAN_CODE;
	}
	public String getDEST_CODE() {
		return DEST_CODE;
	}
	public void setDEST_CODE(String dEST_CODE) {
		DEST_CODE = dEST_CODE;
	}
	public String getUSERID() {
		return USERID;
	}
	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}
	public String getU_EMP_NO() {
		return U_EMP_NO;
	}
	public void setU_EMP_NO(String u_EMP_NO) {
		U_EMP_NO = u_EMP_NO;
	}
	public String getBR_PKT_NO() {
		return BR_PKT_NO;
	}
	public void setBR_PKT_NO(String bR_PKT_NO) {
		BR_PKT_NO = bR_PKT_NO;
	}
	public String getMPL_NO() {
		return MPL_NO;
	}
	public void setMPL_NO(String mPL_NO) {
		MPL_NO = mPL_NO;
	}
	public String getSTCODE() {
		return STCODE;
	}
	public void setSTCODE(String sTCODE) {
		STCODE = sTCODE;
	}
	public String getPKT_NO() {
		return PKT_NO;
	}
	public void setPKT_NO(String pKT_NO) {
		PKT_NO = pKT_NO;
	}
	public String getLOAD_NO() {
		return LOAD_NO;
	}
	public void setLOAD_NO(String lOAD_NO) {
		LOAD_NO = lOAD_NO;
	}
	public String getLCL_BR_LD() {
		return LCL_BR_LD;
	}
	public void setLCL_BR_LD(String lCL_BR_LD) {
		LCL_BR_LD = lCL_BR_LD;
	}
	public String getCON_CODE() {
		return CON_CODE;
	}
	public void setCON_CODE(String cON_CODE) {
		CON_CODE = cON_CODE;
	}
	public String getCON_TYPE() {
		return CON_TYPE;
	}
	public void setCON_TYPE(String cON_TYPE) {
		CON_TYPE = cON_TYPE;
	}
	public String getMPL_FLAG() {
		return MPL_FLAG;
	}
	public void setMPL_FLAG(String mPL_FLAG) {
		MPL_FLAG = mPL_FLAG;
	}
	public String getFORMS() {
		return FORMS;
	}
	public void setFORMS(String fORMS) {
		FORMS = fORMS;
	}
	public String getMAN_FLAG() {
		return MAN_FLAG;
	}
	public void setMAN_FLAG(String mAN_FLAG) {
		MAN_FLAG = mAN_FLAG;
	}
	public String getMISRTE_RET() {
		return MISRTE_RET;
	}
	public void setMISRTE_RET(String mISRTE_RET) {
		MISRTE_RET = mISRTE_RET;
	}
	public String getLCT() {
		return LCT;
	}
	public void setLCT(String lCT) {
		LCT = lCT;
	}
	public String getLCT_AMT() {
		return LCT_AMT;
	}
	public void setLCT_AMT(String lCT_AMT) {
		LCT_AMT = lCT_AMT;
	}
	public String getHUB_WEIGHT() {
		return HUB_WEIGHT;
	}
	public void setHUB_WEIGHT(String hUB_WEIGHT) {
		HUB_WEIGHT = hUB_WEIGHT;
	}
	public String getPIECES() {
		return PIECES;
	}
	public void setPIECES(String pIECES) {
		PIECES = pIECES;
	}
	public String getRET_CD() {
		return RET_CD;
	}
	public void setRET_CD(String rET_CD) {
		RET_CD = rET_CD;
	}
	public String getDECLARE() {
		return DECLARE;
	}
	public void setDECLARE(String dECLARE) {
		DECLARE = dECLARE;
	}
	public String getVALUE() {
		return VALUE;
	}
	public void setVALUE(String vALUE) {
		VALUE = vALUE;
	}
	public String getLENGTH() {
		return LENGTH;
	}
	public void setLENGTH(String lENGTH) {
		LENGTH = lENGTH;
	}
	public String getBREADTH() {
		return BREADTH;
	}
	public void setBREADTH(String bREADTH) {
		BREADTH = bREADTH;
	}
	public String getHEIGHT() {
		return HEIGHT;
	}
	public void setHEIGHT(String hEIGHT) {
		HEIGHT = hEIGHT;
	}
	public String getU_VERSION() {
		return U_VERSION;
	}
	public void setU_VERSION(String u_VERSION) {
		U_VERSION = u_VERSION;
	}
	public String getINSURED() {
		return INSURED;
	}
	public void setINSURED(String iNSURED) {
		INSURED = iNSURED;
	}
	public String getFORM() {
		return FORM;
	}
	public void setFORM(String fORM) {
		FORM = fORM;
	}
	public String getTOP_AMT() {
		return TOP_AMT;
	}
	public void setTOP_AMT(String tOP_AMT) {
		TOP_AMT = tOP_AMT;
	}
	public String getEXPO_NET() {
		return EXPO_NET;
	}
	public void setEXPO_NET(String eXPO_NET) {
		EXPO_NET = eXPO_NET;
	}
	public String getWEBUPD() {
		return WEBUPD;
	}
	public void setWEBUPD(String wEBUPD) {
		WEBUPD = wEBUPD;
	}
	public String getWEBUPD1() {
		return WEBUPD1;
	}
	public void setWEBUPD1(String wEBUPD1) {
		WEBUPD1 = wEBUPD1;
	}
	public String getLD_DESP() {
		return LD_DESP;
	}
	public void setLD_DESP(String lD_DESP) {
		LD_DESP = lD_DESP;
	}
	public String getFILENAME() {
		return FILENAME;
	}
	public void setFILENAME(String fILENAME) {
		FILENAME = fILENAME;
	}
	public String getCOD_AMT() {
		return COD_AMT;
	}
	public void setCOD_AMT(String cOD_AMT) {
		COD_AMT = cOD_AMT;
	}
	public String getLLOAD() {
		return LLOAD;
	}
	public void setLLOAD(String lLOAD) {
		LLOAD = lLOAD;
	}
	public String getFLAG() {
		return FLAG;
	}
	public void setFLAG(String fLAG) {
		FLAG = fLAG;
	}
	public String getM_CON_CD() {
		return M_CON_CD;
	}
	public void setM_CON_CD(String m_CON_CD) {
		M_CON_CD = m_CON_CD;
	}
	public String getCONTENTS() {
		return CONTENTS;
	}
	public void setCONTENTS(String cONTENTS) {
		CONTENTS = cONTENTS;
	}
	public String getINVOICE() {
		return INVOICE;
	}
	public void setINVOICE(String iNVOICE) {
		INVOICE = iNVOICE;
	}
	public String getPINCODE() {
		return PINCODE;
	}
	public void setPINCODE(String pINCODE) {
		PINCODE = pINCODE;
	}
	public String getDIRECT() {
		return DIRECT;
	}
	public void setDIRECT(String dIRECT) {
		DIRECT = dIRECT;
	}
	public String getCLIENTID() {
		return CLIENTID;
	}
	public void setCLIENTID(String cLIENTID) {
		CLIENTID = cLIENTID;
	}
	public String getU_CON_CD() {
		return U_CON_CD;
	}
	public void setU_CON_CD(String u_CON_CD) {
		U_CON_CD = u_CON_CD;
	}
	public String getCLIENTNO() {
		return CLIENTNO;
	}
	public void setCLIENTNO(String cLIENTNO) {
		CLIENTNO = cLIENTNO;
	}
	public String getCONSNAME() {
		return CONSNAME;
	}
	public void setCONSNAME(String cONSNAME) {
		CONSNAME = cONSNAME;
	}
	public String getVENDORID() {
		return VENDORID;
	}
	public void setVENDORID(String vENDORID) {
		VENDORID = vENDORID;
	}
	public String getVENDORNAME() {
		return VENDORNAME;
	}
	public void setVENDORNAME(String vENDORNAME) {
		VENDORNAME = vENDORNAME;
	}
	public String getFILEID() {
		return FILEID;
	}
	public void setFILEID(String fILEID) {
		FILEID = fILEID;
	}
	public String getORIGIN() {
		return ORIGIN;
	}
	public void setORIGIN(String oRIGIN) {
		ORIGIN = oRIGIN;
	}
	public String getINSERTTIME() {
		return INSERTTIME;
	}
	public void setINSERTTIME(String iNSERTTIME) {
		INSERTTIME = iNSERTTIME;
	}
	@Override
	public String toString() {
		return "Outgo [U_BR_CD=" + U_BR_CD + ", BKING_DATE=" + BKING_DATE
				+ ", BOOK_TIME=" + BOOK_TIME + ", DESP_DATE=" + DESP_DATE
				+ ", CON_NO=" + CON_NO + ", TRAN_CODE=" + TRAN_CODE
				+ ", DEST_CODE=" + DEST_CODE + ", USERID=" + USERID
				+ ", U_EMP_NO=" + U_EMP_NO + ", BR_PKT_NO=" + BR_PKT_NO
				+ ", MPL_NO=" + MPL_NO + ", STCODE=" + STCODE + ", PKT_NO="
				+ PKT_NO + ", LOAD_NO=" + LOAD_NO + ", LCL_BR_LD=" + LCL_BR_LD
				+ ", CON_CODE=" + CON_CODE + ", CON_TYPE=" + CON_TYPE
				+ ", MPL_FLAG=" + MPL_FLAG + ", FORMS=" + FORMS + ", MAN_FLAG="
				+ MAN_FLAG + ", MISRTE_RET=" + MISRTE_RET + ", LCT=" + LCT
				+ ", LCT_AMT=" + LCT_AMT + ", HUB_WEIGHT=" + HUB_WEIGHT
				+ ", PIECES=" + PIECES + ", RET_CD=" + RET_CD + ", DECLARE="
				+ DECLARE + ", VALUE=" + VALUE + ", LENGTH=" + LENGTH
				+ ", BREADTH=" + BREADTH + ", HEIGHT=" + HEIGHT
				+ ", U_VERSION=" + U_VERSION + ", INSURED=" + INSURED
				+ ", FORM=" + FORM + ", TOP_AMT=" + TOP_AMT + ", EXPO_NET="
				+ EXPO_NET + ", WEBUPD=" + WEBUPD + ", WEBUPD1=" + WEBUPD1
				+ ", LD_DESP=" + LD_DESP + ", FILENAME=" + FILENAME
				+ ", COD_AMT=" + COD_AMT + ", LLOAD=" + LLOAD + ", FLAG="
				+ FLAG + ", M_CON_CD=" + M_CON_CD + ", CONTENTS=" + CONTENTS
				+ ", INVOICE=" + INVOICE + ", PINCODE=" + PINCODE + ", DIRECT="
				+ DIRECT + ", CLIENTID=" + CLIENTID + ", U_CON_CD=" + U_CON_CD
				+ ", CLIENTNO=" + CLIENTNO + ", CONSNAME=" + CONSNAME
				+ ", VENDORID=" + VENDORID + ", VENDORNAME=" + VENDORNAME
				+ ", FILEID=" + FILEID + ", ORIGIN=" + ORIGIN + ", INSERTTIME="
				+ INSERTTIME + ", DT_UDAAN=" + DT_UDAAN + ", DT_DBF=" + DT_DBF
				+ "]";
	}
	
	
	
	

}
