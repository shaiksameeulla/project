package com.ff.ud.dto;



public class HubToBranchLCTDTO {
	
	private String CONNO;
	private String LCTOPAY;
	private String DESPDATE;
	private String AMOUNT;
	private String BANKNM;
	private String LC;
	private String TOPAY;
	private String COD;
	private String OCTROI;
	
	
	public String getCONNO() {
		return CONNO;
	}


	public void setCONNO(String cONNO) {
		CONNO = cONNO;
	}


	public String getLCTOPAY() {
		return LCTOPAY;
	}


	public void setLCTOPAY(String lCTOPAY) {
		LCTOPAY = lCTOPAY;
	}


	public String getDESPDATE() {
		return DESPDATE;
	}


	public void setDESPDATE(String dESPDATE) {
		DESPDATE = dESPDATE;
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


	public String getLC() {
		return LC;
	}


	public void setLC(String lC) {
		LC = lC;
	}


	public String getTOPAY() {
		return TOPAY;
	}


	public void setTOPAY(String tOPAY) {
		TOPAY = tOPAY;
	}


	public String getCOD() {
		return COD;
	}


	public void setCOD(String cOD) {
		COD = cOD;
	}


	public String getOCTROI() {
		return OCTROI;
	}


	public void setOCTROI(String oCTROI) {
		OCTROI = oCTROI;
	}


	public boolean assignValuesToDBFArray(Object[] dataArray,HubToBranchLCTDTO lctDto){

		try {
			dataArray[0] =lctDto.getCONNO();// "CON_NO,C,12";
			dataArray[1] =lctDto.getLCTOPAY(); // "LC_TOPAY,C,1";
			//dataArray[2] =lctDto.getAMOUNT(); // "AMOUNT,N,10,2";
			if(null != lctDto.getAMOUNT()&& ""!=lctDto.getAMOUNT()){
				dataArray[2]=Double.valueOf(lctDto.getAMOUNT());
			}else {
				dataArray[2]=Double.valueOf(0);
			}
			
			dataArray[3] =lctDto.getBANKNM(); // "BANK_NM,C,25";
			//dataArray[4] =lctDto.getLC(); // "LC,N,10,2";
			//dataArray[5] =lctDto.getTOPAY();// "TO_PAY,N,10,2";
			//dataArray[6] =lctDto.getCOD(); // "COD,N,10,2";
			//dataArray[7] =lctDto.getOCTROI(); // "OCTROI,N,10,2";
			
			if(null != lctDto.getLC()&& ""!=lctDto.getLC()){
				dataArray[4]=Double.valueOf(lctDto.getLC());
			}else {
				dataArray[4]=Double.valueOf(0);
			}
			if(null != lctDto.getTOPAY()&& ""!=lctDto.getTOPAY()){
				dataArray[5]=Double.valueOf(lctDto.getTOPAY());
			}else {
				dataArray[5]=Double.valueOf(0);
			}
			if(null != lctDto.getCOD()&& ""!=lctDto.getCOD()){
				dataArray[6]=Double.valueOf(lctDto.getCOD());
			}else {
				dataArray[6]=Double.valueOf(0);
			}
			if(null != lctDto.getOCTROI()&& ""!=lctDto.getOCTROI()){
				dataArray[7]=Double.valueOf(lctDto.getOCTROI());
			}else {
				dataArray[7]=Double.valueOf(0);
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	
	}


	
}
