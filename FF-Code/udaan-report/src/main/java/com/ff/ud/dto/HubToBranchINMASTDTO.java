package com.ff.ud.dto;

import com.ff.ud.utils.DateUtils;





public class HubToBranchINMASTDTO implements Cloneable {
	
	private String ORCD;
	private String ORRCDATE;
	private String CONNO;
	private String PKTNO;
	private String INPKTNO;
	private String BPLNO;
	private String LOAD;//COLUMN NOT AVAILABLE IN INMAST TABLE
	private String TOBRLOAD;
	private String STCODE;
	private String DFSCD;
	private String STATUS;
	private String DSTATUS;
	private String DDATE;
	private String DOK;
	private String DTIME;
	private String DRANGE;
	private String TYPE;
	private String EDATE;
	private String ETIME;
	private String MANFLAG;
	private String OPERATOR;
	private String PODOPCD;
	private String LCTOPAY;
	private String DBRCD;
	private String DUPLICATE;
	private String RECEIVER;
	private String INMPLNO;
	private String REFFLAG;
	private String BYMODEM;
	private String INSTATUS;
	private String NMNR;
	private String AMOUNT; //COLUMN NOT AVAILABLE IN INMAST TABLE
	private String BANKNM; //COLUMN NOT AVAILABLE IN INMAST TABLE
	private String TRESCD; //COLUMN NOT AVAILABLE IN INMAST TABLE
	private String RINCD; //COLUMN NOT AVAILABLE IN INMAST TABLE
	private String TRES; //COLUMN NOT AVAILABLE IN INMAST TABLE
	private String RINRES; //COLUMN NOT AVAILABLE IN INMAST TABLE
	private String TTOBRCD;//COLUMN NOT AVAILABLE IN INMAST TABLE
	private String MASTWT;
	private String BRANWT;
	private String INVOICE;//COLUMN NOT AVAILABLE IN INMAST TABLE
	private String DECLARE;//COLUMN NOT AVAILABLE IN INMAST TABLE
	private String CONTENTS;//COLUMN NOT AVAILABLE IN INMAST TABLE
	private String QTY; //COLUMN NOT AVAILABLE IN INMAST TABLE
	private String MCONTENT;//COLUMN NOT AVAILABLE IN INMAST TABLE
	private String MPIECE;//COLUMN NOT AVAILABLE IN INMAST TABLE
	private String BPIECE; //COLUMN NOT AVAILABLE IN INMAST TABLE
	private String MDEST;
	private String MROGMNO;
	private String MLOADNO;
	private String DESPDT;
	private String UVERSION;
	
	@Override
	public HubToBranchINMASTDTO clone() throws CloneNotSupportedException {
		return (HubToBranchINMASTDTO) super.clone();
	}
	
	public String getORCD() {
		return ORCD;
	}



	public void setORCD(String oRCD) {
		ORCD = oRCD;
	}



	public String getORRCDATE() {
		return ORRCDATE;
	}



	public void setORRCDATE(String oRRCDATE) {
		ORRCDATE = oRRCDATE;
	}



	public String getCONNO() {
		return CONNO;
	}



	public void setCONNO(String cONNO) {
		CONNO = cONNO;
	}



	public String getPKTNO() {
		return PKTNO;
	}



	public void setPKTNO(String pKTNO) {
		PKTNO = pKTNO;
	}



	public String getINPKTNO() {
		return INPKTNO;
	}



	public void setINPKTNO(String iNPKTNO) {
		INPKTNO = iNPKTNO;
	}



	public String getBPLNO() {
		return BPLNO;
	}



	public void setBPLNO(String bPLNO) {
		BPLNO = bPLNO;
	}



	public String getLOAD() {
		return LOAD;
	}



	public void setLOAD(String lOAD) {
		LOAD = lOAD;
	}



	public String getTOBRLOAD() {
		return TOBRLOAD;
	}



	public void setTOBRLOAD(String tOBRLOAD) {
		TOBRLOAD = tOBRLOAD;
	}



	public String getSTCODE() {
		return STCODE;
	}



	public void setSTCODE(String sTCODE) {
		STCODE = sTCODE;
	}



	public String getDFSCD() {
		return DFSCD;
	}



	public void setDFSCD(String dFSCD) {
		DFSCD = dFSCD;
	}



	public String getSTATUS() {
		return STATUS;
	}



	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}



	public String getDSTATUS() {
		return DSTATUS;
	}



	public void setDSTATUS(String dSTATUS) {
		DSTATUS = dSTATUS;
	}



	public String getDDATE() {
		return DDATE;
	}



	public void setDDATE(String dDATE) {
		DDATE = dDATE;
	}



	public String getDOK() {
		return DOK;
	}



	public void setDOK(String dOK) {
		DOK = dOK;
	}



	public String getDTIME() {
		return DTIME;
	}



	public void setDTIME(String dTIME) {
		DTIME = dTIME;
	}



	public String getDRANGE() {
		return DRANGE;
	}



	public void setDRANGE(String dRANGE) {
		DRANGE = dRANGE;
	}



	public String getTYPE() {
		return TYPE;
	}



	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}



	public String getEDATE() {
		return EDATE;
	}



	public void setEDATE(String eDATE) {
		EDATE = eDATE;
	}



	public String getETIME() {
		return ETIME;
	}



	public void setETIME(String eTIME) {
		ETIME = eTIME;
	}



	public String getMANFLAG() {
		return MANFLAG;
	}



	public void setMANFLAG(String mANFLAG) {
		MANFLAG = mANFLAG;
	}



	public String getOPERATOR() {
		return OPERATOR;
	}



	public void setOPERATOR(String oPERATOR) {
		OPERATOR = oPERATOR;
	}



	public String getPODOPCD() {
		return PODOPCD;
	}



	public void setPODOPCD(String pODOPCD) {
		PODOPCD = pODOPCD;
	}



	public String getLCTOPAY() {
		return LCTOPAY;
	}



	public void setLCTOPAY(String lCTOPAY) {
		LCTOPAY = lCTOPAY;
	}



	public String getDBRCD() {
		return DBRCD;
	}



	public void setDBRCD(String dBRCD) {
		DBRCD = dBRCD;
	}



	public String getDUPLICATE() {
		return DUPLICATE;
	}



	public void setDUPLICATE(String dUPLICATE) {
		DUPLICATE = dUPLICATE;
	}



	public String getRECEIVER() {
		return RECEIVER;
	}



	public void setRECEIVER(String rECEIVER) {
		RECEIVER = rECEIVER;
	}



	public String getINMPLNO() {
		return INMPLNO;
	}



	public void setINMPLNO(String iNMPLNO) {
		INMPLNO = iNMPLNO;
	}



	public String getREFFLAG() {
		return REFFLAG;
	}



	public void setREFFLAG(String rEFFLAG) {
		REFFLAG = rEFFLAG;
	}



	public String getBYMODEM() {
		return BYMODEM;
	}



	public void setBYMODEM(String bYMODEM) {
		BYMODEM = bYMODEM;
	}



	public String getINSTATUS() {
		return INSTATUS;
	}



	public void setINSTATUS(String iNSTATUS) {
		INSTATUS = iNSTATUS;
	}



	public String getNMNR() {
		return NMNR;
	}



	public void setNMNR(String nMNR) {
		NMNR = nMNR;
	}



	public String getAMOUNT() {
		return AMOUNT;
	}



	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}



	public String getBANKNM() {
		return BANKNM;
	}



	public void setBANKNM(String bANKNM) {
		BANKNM = bANKNM;
	}



	public String getTRESCD() {
		return TRESCD;
	}



	public void setTRESCD(String tRESCD) {
		TRESCD = tRESCD;
	}



	public String getRINCD() {
		return RINCD;
	}



	public void setRINCD(String rINCD) {
		RINCD = rINCD;
	}



	public String getTRES() {
		return TRES;
	}



	public void setTRES(String tRES) {
		TRES = tRES;
	}



	public String getRINRES() {
		return RINRES;
	}



	public void setRINRES(String rINRES) {
		RINRES = rINRES;
	}



	public String getTTOBRCD() {
		return TTOBRCD;
	}



	public void setTTOBRCD(String tTOBRCD) {
		TTOBRCD = tTOBRCD;
	}



	public String getMASTWT() {
		return MASTWT;
	}



	public void setMASTWT(String mASTWT) {
		MASTWT = mASTWT;
	}



	public String getBRANWT() {
		return BRANWT;
	}



	public void setBRANWT(String bRANWT) {
		BRANWT = bRANWT;
	}



	public String getINVOICE() {
		return INVOICE;
	}



	public void setINVOICE(String iNVOICE) {
		INVOICE = iNVOICE;
	}



	public String getDECLARE() {
		return DECLARE;
	}



	public void setDECLARE(String dECLARE) {
		DECLARE = dECLARE;
	}



	public String getCONTENTS() {
		return CONTENTS;
	}



	public void setCONTENTS(String cONTENTS) {
		CONTENTS = cONTENTS;
	}



	public String getQTY() {
		return QTY;
	}



	public void setQTY(String qTY) {
		QTY = qTY;
	}



	public String getMCONTENT() {
		return MCONTENT;
	}



	public void setMCONTENT(String mCONTENT) {
		MCONTENT = mCONTENT;
	}



	public String getMPIECE() {
		return MPIECE;
	}



	public void setMPIECE(String mPIECE) {
		MPIECE = mPIECE;
	}



	public String getBPIECE() {
		return BPIECE;
	}



	public void setBPIECE(String bPIECE) {
		BPIECE = bPIECE;
	}



	public String getMDEST() {
		return MDEST;
	}



	public void setMDEST(String mDEST) {
		MDEST = mDEST;
	}



	public String getMROGMNO() {
		return MROGMNO;
	}



	public void setMROGMNO(String mROGMNO) {
		MROGMNO = mROGMNO;
	}



	public String getMLOADNO() {
		return MLOADNO;
	}



	public void setMLOADNO(String mLOADNO) {
		MLOADNO = mLOADNO;
	}



	public String getDESPDT() {
		return DESPDT;
	}



	public void setDESPDT(String dESPDT) {
		DESPDT = dESPDT;
	}



	public String getUVERSION() {
		return UVERSION;
	}



	public void setUVERSION(String uVERSION) {
		UVERSION = uVERSION;
	}



	public void assignValuesToDBFArray(Object[] dataArray, HubToBranchINMASTDTO inMastTO)  {
		
		dataArray[0] = inMastTO.getORCD() ;// "OR_CD,C,3";
		//dataArray[1] = inMastTO.getORRCDATE() ;// "OR_RC_DATE,D";
		if(null !=inMastTO.getORRCDATE() && ""!=inMastTO.getORRCDATE()){
			dataArray[1]= DateUtils.getDateFromString(inMastTO.getORRCDATE(), DateUtils.FORMAT_yyyy_MM_dd_hh_mm_ss);
		}else{
			dataArray[1]=null;
		}
		dataArray[2] =  inMastTO.getCONNO();// "CON_NO,C,12";
		dataArray[3] =  inMastTO.getPKTNO(); // "PKT_NO,C,10";
		dataArray[4] =  inMastTO.getINPKTNO();//	 "IN_PKTNO,C,10";
		dataArray[5] =  inMastTO.getBPLNO();//  ;// "BPL_NO,C,10";
		dataArray[6] =  inMastTO.getLOAD();// "LOAD,C,4";
		//dataArray[7] =  inMastTO.getTOBRLOAD();// "TO_BR_LOAD,N,1,0"; 
		if(null != inMastTO.getTOBRLOAD()&& ""!=inMastTO.getTOBRLOAD()){
			dataArray[7]=Double.valueOf(inMastTO.getTOBRLOAD());
		}else {
			dataArray[7]=Double.valueOf(0);
		}
		dataArray[8] =  inMastTO.getSTCODE();// "STCODE,C,3";
		dataArray[9] =  inMastTO.getDFSCD();// "D_FS_CD,C,5";
		dataArray[10] =  inMastTO.getSTATUS();// "STATUS,C,1";
		dataArray[11] =  inMastTO.getDSTATUS();// "D_STATUS,C,1";
		//dataArray[12] =  inMastTO.getDDATE();// "D_DATE,D";
		if(null !=inMastTO.getDDATE() && ""!=inMastTO.getDDATE()){
			dataArray[12]= DateUtils.getDateFromString(inMastTO.getDDATE(), DateUtils.FORMAT_yyyy_MM_dd_hh_mm_ss);
		}else{
			dataArray[12]=null;
		}
		dataArray[13] =  inMastTO.getDOK();// "D_OK,C,1";
		dataArray[14] =  inMastTO.getDTIME();// "D_TIME,C,5";
		dataArray[15] =  inMastTO.getDRANGE();// "D_RANGE,C,1";
		dataArray[16] =  inMastTO.getTYPE();// "TYPE,C,1";
		//dataArray[17] =  inMastTO.getEDATE();// "E_DATE,D";
		if(null !=inMastTO.getEDATE() && ""!=inMastTO.getEDATE()){
			dataArray[17]= DateUtils.getDateFromString(inMastTO.getEDATE(), DateUtils.FORMAT_yyyy_MM_dd_hh_mm_ss);
		}else{
			dataArray[17]=null;
		}
		dataArray[18] =  inMastTO.getETIME();// "E_TIME,C,5";
		dataArray[19] =  inMastTO.getMANFLAG();// "MAN_FLAG,C,1";
		dataArray[20] =  inMastTO.getOPERATOR();// "OPERATOR,C,5";
		dataArray[21] =  inMastTO.getPODOPCD();// "POD_OP_CD,C,5";
		dataArray[22] =  inMastTO.getLCTOPAY();// "LC_TOPAY,C,1";
		dataArray[23] =  inMastTO.getDBRCD();// "D_BR_CD,C,3";
		dataArray[24] =  inMastTO.getDUPLICATE();// "DUPLICATE,C,1";
		dataArray[25] =  inMastTO.getRECEIVER();// "RECEIVER,C,20";
		//dataArray[26] =  inMastTO.getINMPLNO();// "INMPL_NO,N,6,0";
		if(null != inMastTO.getINMPLNO() && ""!=inMastTO.getINMPLNO()){
			dataArray[26]=Double.valueOf(inMastTO.getINMPLNO());
		}else {
			dataArray[26]=Double.valueOf(0);
		}
		dataArray[27] =  inMastTO.getREFFLAG();// "REF_FLAG,C,1";
		dataArray[28] =  inMastTO.getBYMODEM();// "BY_MODEM,C,1";
		dataArray[29] =  inMastTO.getINSTATUS();// "IN_STATUS,C,1";
		dataArray[30] =  inMastTO.getNMNR();//"NM_NR,C,1"; 
		//dataArray[31] =  inMastTO.getAMOUNT();// "AMOUNT,N,7,0";
		if(null != inMastTO.getAMOUNT() && ""!=inMastTO.getAMOUNT()){
			dataArray[31]=Double.valueOf(inMastTO.getAMOUNT());
		}else {
			dataArray[31]=Double.valueOf(0);
		}
		dataArray[32] =  inMastTO.getBANKNM();// "BANK_NM,C,25";
		dataArray[33] =  inMastTO.getTRESCD();// "T_RES_CD,C,2";
		dataArray[34] =  inMastTO.getRINCD();// "R_IN_CD,C,2";
		dataArray[35] =  inMastTO.getTRES();// "T_RES,C,25"; 
		dataArray[36] =  inMastTO.getRINRES();//"R_IN_RES,C,25"; 
		dataArray[37] =  inMastTO.getTTOBRCD();//"T_TO_BR_CD,C,3";
		//dataArray[38] =  inMastTO.getMASTWT();// "MAST_WT,N,8,2";
		//dataArray[39] =  inMastTO.getBRANWT();// "BRAN_WT,N,7,2";
		if(null != inMastTO.getMASTWT() && ""!=inMastTO.getMASTWT()){
			dataArray[38]=Double.valueOf(inMastTO.getMASTWT());
		}else {
			dataArray[38]=Double.valueOf(0);
		}
		if(null != inMastTO.getBRANWT() && ""!=inMastTO.getBRANWT()){
			dataArray[39]=Double.valueOf(inMastTO.getBRANWT());
		}else {
			dataArray[39]=Double.valueOf(0);
		}
		dataArray[40] =  inMastTO.getINVOICE();// "INVOICE,C,10";
		//dataArray[41] =  inMastTO.getDECLARE();// "DECLARE,N,10,0";
		if(null != inMastTO.getDECLARE() && ""!=inMastTO.getDECLARE()){
			dataArray[41]=Double.valueOf(inMastTO.getDECLARE());
		}else {
			dataArray[41]=Double.valueOf(0);
		}
		dataArray[42] =  inMastTO.getCONTENTS();// "CONTENTS,C,30";
		//dataArray[43] =  inMastTO.getQTY();// "QTY,N,4,0";
		if(null != inMastTO.getQTY() && ""!=inMastTO.getQTY()){
			dataArray[43]=Double.valueOf(inMastTO.getQTY());
		}else {
			dataArray[43]=Double.valueOf(0);
		}
		dataArray[44] =  inMastTO.getMCONTENT();// "M_CONTENT,C,30";
		//dataArray[45] =  inMastTO.getMPIECE();//"MPIECE,N,4,0";
		//dataArray[46] =  inMastTO.getBPIECE();//"BPIECE,N,4,0";
		
		if(null != inMastTO.getMPIECE() && ""!=inMastTO.getMPIECE()){
			dataArray[45]=Double.valueOf(inMastTO.getMPIECE());
		}else {
			dataArray[45]=Double.valueOf(0);
		}
		if(null != inMastTO.getBPIECE() && ""!=inMastTO.getBPIECE()){
			dataArray[46]=Double.valueOf(inMastTO.getBPIECE());
		}else {
			dataArray[46]=Double.valueOf(0);
		}
		dataArray[47] =  inMastTO.getMDEST();// "MDEST,C,3";
		dataArray[48] =  inMastTO.getMROGMNO();// "MROGMNO,C,11";
		dataArray[49] =  inMastTO.getMLOADNO();// "MLOADNO,C,4";
		dataArray[50] =  inMastTO.getDESPDT();// "DESPDT,D";
		if(null !=inMastTO.getDESPDT() && ""!=inMastTO.getDESPDT()){
			dataArray[50]= DateUtils.getDateFromString(inMastTO.getDESPDT(), DateUtils.FORMAT_yyyy_MM_dd_hh_mm_ss);
		}else{
			dataArray[50]=null;
		}
		dataArray[51] =  inMastTO.getUVERSION();// "U_VERSION,C,50";
	}
	
	
	/**
	 * Hub To Branch RET FILE dbf column value.
	 * @param dataArray
	 * @return
	 */
	public Object[] assignRETValuesToDBFArray(Object[] dataArray) {
		
		dataArray[0] =  null;
		dataArray[1] =  null;
		dataArray[2] =  null;
		dataArray[3] =  null;
		dataArray[4] =  null;
		
		return dataArray;
		
		
	}
	
	
}
