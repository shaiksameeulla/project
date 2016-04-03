package com.ff.ud.dto;

import com.ff.ud.utils.DateUtils;




public class HubToBranchMblDTO implements Cloneable{
	
	private String DESTCODE;
	private String DESPDEST;
	private String DESPDATE;
	private String MBLNO;
	private String VEHNO;
	private String VEHNAME;
	private String BAGWEIGHT;
	private String TOTALBAG;
	private String MBLFLAG;
	private String DESPTYPE;
	private String COLOADER;
	private String DESPTO;
	private String SELECTION;
	private String TOBRLOAD;
	private String MANUALENT;
	private String WEBUPD;
	private String FILENAME;
	
	
	@Override
	public HubToBranchMblDTO clone() throws CloneNotSupportedException {
		return (HubToBranchMblDTO) super.clone();
	}

	public String getDESTCODE() {
		return DESTCODE;
	}


	public void setDESTCODE(String dESTCODE) {
		DESTCODE = dESTCODE;
	}

	public String getDESPDEST() {
		return DESPDEST;
	}

	public void setDESPDEST(String dESPDEST) {
		DESPDEST = dESPDEST;
	}

	public String getDESPDATE() {
		return DESPDATE;
	}

	public void setDESPDATE(String dESPDATE) {
		DESPDATE = dESPDATE;
	}

	public String getMBLNO() {
		return MBLNO;
	}

	public void setMBLNO(String mBLNO) {
		MBLNO = mBLNO;
	}

	public String getVEHNO() {
		return VEHNO;
	}

	public void setVEHNO(String vEHNO) {
		VEHNO = vEHNO;
	}

	public String getVEHNAME() {
		return VEHNAME;
	}

	public void setVEHNAME(String vEHNAME) {
		VEHNAME = vEHNAME;
	}

	public String getBAGWEIGHT() {
		return BAGWEIGHT;
	}

	public void setBAGWEIGHT(String bAGWEIGHT) {
		BAGWEIGHT = bAGWEIGHT;
	}

	public String getTOTALBAG() {
		return TOTALBAG;
	}

	public void setTOTALBAG(String tOTALBAG) {
		TOTALBAG = tOTALBAG;
	}

	public String getMBLFLAG() {
		return MBLFLAG;
	}

	public void setMBLFLAG(String mBLFLAG) {
		MBLFLAG = mBLFLAG;
	}

	public String getDESPTYPE() {
		return DESPTYPE;
	}

	public void setDESPTYPE(String dESPTYPE) {
		DESPTYPE = dESPTYPE;
	}
	public String getCOLOADER() {
		return COLOADER;
	}

	public void setCOLOADER(String cOLOADER) {
		COLOADER = cOLOADER;
	}


	public String getDESPTO() {
		return DESPTO;
	}
	public void setDESPTO(String dESPTO) {
		DESPTO = dESPTO;
	}
	public String getSELECTION() {
		return SELECTION;
	}

	public void setSELECTION(String sELECTION) {
		SELECTION = sELECTION;
	}
	public String getTOBRLOAD() {
		return TOBRLOAD;
	}
	public void setTOBRLOAD(String tOBRLOAD) {
		TOBRLOAD = tOBRLOAD;
	}




	public String getMANUALENT() {
		return MANUALENT;
	}




	public void setMANUALENT(String mANUALENT) {
		MANUALENT = mANUALENT;
	}




	public String getWEBUPD() {
		return WEBUPD;
	}




	public void setWEBUPD(String wEBUPD) {
		WEBUPD = wEBUPD;
	}




	public String getFILENAME() {
		return FILENAME;
	}




	public void setFILENAME(String fILENAME) {
		FILENAME = fILENAME;
	}




	public boolean assignValuesToDBFArray(Object[] dataArray,HubToBranchMblDTO boMblDto){

		try {
			dataArray[0] = boMblDto.getDESTCODE(); // "DEST_CODE,C,3";
			dataArray[1] = boMblDto.getDESPDEST();// "DESP_DEST,C,3";
			// dataArray[2]= boMblDto.getDESPDATE(); // "DESP_DATE,D";
			if (null != boMblDto.getDESPDATE() && "" != boMblDto.getDESPDATE()) {
				dataArray[2] = DateUtils.getDateFromString(
						boMblDto.getDESPDATE(),
						DateUtils.FORMAT_yyyy_MM_dd_hh_mm_ss);
			} else {
				dataArray[2] = null;
			}
			dataArray[3] = boMblDto.getMBLNO(); // "MBL_NO,C,10";
			dataArray[4] = boMblDto.getVEHNO(); // "VEH_NO,C,20";
			dataArray[5] = boMblDto.getVEHNAME(); // "VEH_NAME,C,30";
			// dataArray[6]= boMblDto.getBAGWEIGHT(); // "BAG_WEIGHT,N,8,3";
			// dataArray[7]= boMblDto.getTOTALBAG(); // "TOTAL_BAG,N,6,0";
			if (null != boMblDto.getBAGWEIGHT()
					&& "" != boMblDto.getBAGWEIGHT()) {
				dataArray[6] = Double.valueOf(boMblDto.getBAGWEIGHT());
			} else {
				dataArray[6] = Double.valueOf(0);
			}
			if (null != boMblDto.getTOTALBAG() && "" != boMblDto.getTOTALBAG()) {
				dataArray[7] = Double.valueOf(boMblDto.getTOTALBAG());
			} else {
				dataArray[7] = Double.valueOf(0);
			}
			dataArray[8] = boMblDto.getMBLFLAG(); // "MBL_FLAG,C,1";
			dataArray[9] = boMblDto.getDESPTYPE(); // "DESP_TYPE,C,15";
			dataArray[10] = boMblDto.getCOLOADER(); // "CO_LOADER,C,25";
			dataArray[11] = boMblDto.getDESPTO(); // "DESP_TO,C,15";
			dataArray[12] = boMblDto.getSELECTION(); // "SELECTION,C,1";
			// dataArray[13]=boMblDto.getTOBRLOAD(); // "TO_BR_LOAD,N,1,0";
			if (null != boMblDto.getTOBRLOAD() && "" != boMblDto.getTOBRLOAD()) {
				dataArray[13] = Double.valueOf(boMblDto.getTOBRLOAD());
			} else {
				dataArray[13] = Double.valueOf(0);
			}
			dataArray[14] = boMblDto.getMANUALENT(); // "MANUAL_ENT,C,1";
			dataArray[15] = boMblDto.getWEBUPD(); // "WEBUPD,C,1";
			dataArray[16] = boMblDto.getFILENAME(); // "FILENAME,C,50";

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	
	}


	
}
