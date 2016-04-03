package com.ff.ud.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ff.ud.constants.XMLConstant;



@Entity(name="com.ff.ud.domain.FFMastOutDO")
@Table(name=XMLConstant.DATABASE_REF_NAME+".dbo.Stg_Out_FFMAST")
public class FFMastOutDO {

	@Id
	@Column(name="M_CON_NO") private String consignmentNo;

	@Column(name="BKING_DATE") private String bookingDate;
	@Column(name="BK_TIME") private String bookingTime;
	@Column(name="BKING_MODE") private String bookingMode;
	@Column(name="M_BR_CD") private String originBranchCodeM;
	@Column(name="U_BR_CD") private String originBranchCodeUdaan;
	@Column(name="M_CON_NAME") private String M_CON_NAME;
	@Column(name="M_CON_CD") private String M_CON_CD;
	@Column(name="U_CON_CD") private String U_CON_CD;
	@Column(name="M_EMP_CD") private String M_EMP_CD;
	@Column(name="U_VERSION") private String U_VERSION;
	@Column(name="U_EMP_NO") private String U_EMP_NO;
	@Column(name="M_TO_PAY") private String M_TO_PAY;
	@Column(name="M_PAY_MODE") private String M_PAY_MODE;
	@Column(name="M_REVEN") private String M_REVEN;
	@Column(name="SERVICETAX") private String SERVICETAX;
	@Column(name="INS_CHRG") private String INS_CHRG;
	@Column(name="TOCHRG") private String TOCHRG;
	@Column(name="CURRENT_BK") private String CURRENT_BK;
	@Column(name="WEIGHT") private String WEIGHT;
	@Column(name="FUEL_CHRG") private String FUEL_CHRG;
	@Column(name="ED_CESS") private String ED_CESS;
	@Column(name="HED_CESS") private String HED_CESS;
	@Column(name="ACC_ENT_DT") private String ACC_ENT_DT;
	@Column(name="ACCT_FLAG") private String ACCT_FLAG;
	@Column(name="ACC_SCAN") private String ACC_SCAN;
	@Column(name="POD_E_DATE") private String POD_E_DATE;
	@Column(name="M_POD_DATE") private String M_POD_DATE;
	@Column(name="DEL_TIME") private String DEL_TIME;
	@Column(name="DEL_RANGE") private String DEL_RANGE;
	@Column(name="RET_CD") private String RET_CD;
	@Column(name="POD_CNT") private String POD_CNT;
	@Column(name="POD_FLAG") private String POD_FLAG;
	@Column(name="POD_DEO") private String POD_DEO;
	@Column(name="MODEM_POD") private String MODEM_POD;
	@Column(name="DESP_DATE") private String DESP_DATE;
	@Column(name="ON_DES_CD") private String ON_DES_CD;
	@Column(name="CONS_TYPE") private String CONS_TYPE;
	@Column(name="PIECES") private String PIECES;
	@Column(name="PKT_MODE") private String PKT_MODE;
	@Column(name="ON_PKT_NO") private String ON_PKT_NO;
	@Column(name="ONMAN_FLAG") private String ONMAN_FLAG;
	@Column(name="STCODE") private String STCODE;
	@Column(name="ENT_CODE") private String ENT_CODE;
	@Column(name="MODIFIED") private String MODIFIED;
	/*@Column(name="LOAD") private String LOAD;*/
	@Column(name="INS_FLAG") private String INS_FLAG;
	@Column(name="INSURED") private String INSURED;
	@Column(name="POLICY_NO") private String POLICY_NO;
	@Column(name="LC_FLAG") private String LC_FLAG;
	@Column(name="LC_AMOUNT") private String LC_AMOUNT;
	@Column(name="LC_AMD") private String LC_AMD;
	@Column(name="REF_NO") private String REF_NO;
	@Column(name="CON_LC_PAY") private String CON_LC_PAY;
	@Column(name="CON_AMT") private String CON_AMT;
	/*@Column(name="MIS_RETURN") private String MIS_RETURN;
	@Column(name="HUB_WEIGHT") private String HUB_WEIGHT;
	@Column(name="RES_CODE") private String RES_CODE;
	@Column(name="RETURN_IN") private String RETURN_IN;
	@Column(name="TEL_NO") private String TEL_NO;
	@Column(name="RECEIVER") private String RECEIVER;
	@Column(name="ACK_DATE") private String ACK_DATE;
	@Column(name="ACK_TIME") private String ACK_TIME;
	@Column(name="PAR_BRANCH") private String PAR_BRANCH;
	@Column(name="PROD_ID") private String PROD_ID;
	@Column(name="ARC_AMT") private String ARC_AMT;
	@Column(name="M_FOC_COM") private String M_FOC_COM;
	@Column(name="MOD_DATE") private String MOD_DATE;
	@Column(name="INVOICE") private String INVOICE;
	@Column(name="[DECLARE]") private String DECLARE;
	@Column(name="BR_LD") private String BR_LD;
	@Column(name="OGMDEST") private String OGMDEST;
	@Column(name="DEPT") private String DEPT;
	@Column(name="SCAN") private String SCAN;
	@Column(name="COM_FOC") private String COM_FOC;
	@Column(name="COM_DT") private String COM_DT;
	@Column(name="ID_CD") private String ID_CD;
	@Column(name="MODEM") private String MODEM;
	@Column(name="A_FLAG") private String A_FLAG;
	@Column(name="LLOAD") private String LLOAD;
	@Column(name="RET_REASON") private String RET_REASON;
	@Column(name="DUP_NO") private String DUP_NO;
	@Column(name="MODI_FLAG") private String MODI_FLAG;
	@Column(name="ADD_AMOUNT") private String ADD_AMOUNT;
	@Column(name="CONTENTS") private String CONTENTS;
	@Column(name="PINCODE") private String PINCODE;
	@Column(name="CODCHRG") private String CODCHRG;
	@Column(name="APT_CHRG") private String APT_CHRG;
	@Column(name="WEBUPD") private String WEBUPD;
	@Column(name="FILENAME") private String FILENAME;
	@Column(name="PCAT") private String PCAT;
	@Column(name="FILEID") private String FILEID;
	@Column(name="ORIGIN") private String ORIGIN;
	@Column(name="INSERTTIME") private String INSERTTIME;*/
	/*@Column(name="DT_UDAAN") private String DT_UDAAN;
	@Column(name="DR_UDAAN") private String DR_UDAAN;
	@Column(name="DT_DBF") private String DT_DBF;
	@Column(name="DT_OPS_CENTRAL") private String DT_OPS_CENTRAL;
	@Column(name="DT_REGION") private String DT_REGION;
	@Column(name="DT_OFF_TYPE") private String DT_OFF_TYPE;*/
    

	//private DomainBase domainBase;


	public String getBookingDate() {
		return bookingDate;
	}


	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}


	public String getBookingTime() {
		return bookingTime;
	}


	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}


	public String getBookingMode() {
		return bookingMode;
	}


	public void setBookingMode(String bookingMode) {
		this.bookingMode = bookingMode;
	}


	public String getOriginBranchCodeM() {
		return originBranchCodeM;
	}


	public void setOriginBranchCodeM(String originBranchCodeM) {
		this.originBranchCodeM = originBranchCodeM;
	}


	public String getOriginBranchCodeUdaan() {
		return originBranchCodeUdaan;
	}


	public void setOriginBranchCodeUdaan(String originBranchCodeUdaan) {
		this.originBranchCodeUdaan = originBranchCodeUdaan;
	}


	public String getM_CON_NAME() {
		return M_CON_NAME;
	}


	public void setM_CON_NAME(String m_CON_NAME) {
		M_CON_NAME = m_CON_NAME;
	}


	public String getM_CON_CD() {
		return M_CON_CD;
	}


	public void setM_CON_CD(String m_CON_CD) {
		M_CON_CD = m_CON_CD;
	}


	public String getU_CON_CD() {
		return U_CON_CD;
	}


	public void setU_CON_CD(String u_CON_CD) {
		U_CON_CD = u_CON_CD;
	}


	


	public String getConsignmentNo() {
		return consignmentNo;
	}


	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}


	public String getM_EMP_CD() {
		return M_EMP_CD;
	}


	public void setM_EMP_CD(String m_EMP_CD) {
		M_EMP_CD = m_EMP_CD;
	}


	public String getU_VERSION() {
		return U_VERSION;
	}


	public void setU_VERSION(String u_VERSION) {
		U_VERSION = u_VERSION;
	}


	public String getU_EMP_NO() {
		return U_EMP_NO;
	}


	public void setU_EMP_NO(String u_EMP_NO) {
		U_EMP_NO = u_EMP_NO;
	}


	public String getM_TO_PAY() {
		return M_TO_PAY;
	}


	public void setM_TO_PAY(String m_TO_PAY) {
		M_TO_PAY = m_TO_PAY;
	}


	public String getM_PAY_MODE() {
		return M_PAY_MODE;
	}


	public void setM_PAY_MODE(String m_PAY_MODE) {
		M_PAY_MODE = m_PAY_MODE;
	}


	public String getM_REVEN() {
		return M_REVEN;
	}


	public void setM_REVEN(String m_REVEN) {
		M_REVEN = m_REVEN;
	}


	public String getSERVICETAX() {
		return SERVICETAX;
	}


	public void setSERVICETAX(String sERVICETAX) {
		SERVICETAX = sERVICETAX;
	}


	public String getINS_CHRG() {
		return INS_CHRG;
	}


	public void setINS_CHRG(String iNS_CHRG) {
		INS_CHRG = iNS_CHRG;
	}


	public String getTOCHRG() {
		return TOCHRG;
	}


	public void setTOCHRG(String tOCHRG) {
		TOCHRG = tOCHRG;
	}


	public String getCURRENT_BK() {
		return CURRENT_BK;
	}


	public void setCURRENT_BK(String cURRENT_BK) {
		CURRENT_BK = cURRENT_BK;
	}


	public String getWEIGHT() {
		return WEIGHT;
	}


	public void setWEIGHT(String wEIGHT) {
		WEIGHT = wEIGHT;
	}


	public String getFUEL_CHRG() {
		return FUEL_CHRG;
	}


	public void setFUEL_CHRG(String fUEL_CHRG) {
		FUEL_CHRG = fUEL_CHRG;
	}


	public String getED_CESS() {
		return ED_CESS;
	}


	public void setED_CESS(String eD_CESS) {
		ED_CESS = eD_CESS;
	}


	public String getHED_CESS() {
		return HED_CESS;
	}


	public void setHED_CESS(String hED_CESS) {
		HED_CESS = hED_CESS;
	}


	public String getACC_ENT_DT() {
		return ACC_ENT_DT;
	}


	public void setACC_ENT_DT(String aCC_ENT_DT) {
		ACC_ENT_DT = aCC_ENT_DT;
	}


	public String getACCT_FLAG() {
		return ACCT_FLAG;
	}


	public void setACCT_FLAG(String aCCT_FLAG) {
		ACCT_FLAG = aCCT_FLAG;
	}


	public String getACC_SCAN() {
		return ACC_SCAN;
	}


	public void setACC_SCAN(String aCC_SCAN) {
		ACC_SCAN = aCC_SCAN;
	}


	public String getPOD_E_DATE() {
		return POD_E_DATE;
	}


	public void setPOD_E_DATE(String pOD_E_DATE) {
		POD_E_DATE = pOD_E_DATE;
	}


	public String getM_POD_DATE() {
		return M_POD_DATE;
	}


	public void setM_POD_DATE(String m_POD_DATE) {
		M_POD_DATE = m_POD_DATE;
	}


	public String getDEL_TIME() {
		return DEL_TIME;
	}


	public void setDEL_TIME(String dEL_TIME) {
		DEL_TIME = dEL_TIME;
	}


	public String getDEL_RANGE() {
		return DEL_RANGE;
	}


	public void setDEL_RANGE(String dEL_RANGE) {
		DEL_RANGE = dEL_RANGE;
	}


	public String getRET_CD() {
		return RET_CD;
	}


	public void setRET_CD(String rET_CD) {
		RET_CD = rET_CD;
	}


	public String getPOD_CNT() {
		return POD_CNT;
	}


	public void setPOD_CNT(String pOD_CNT) {
		POD_CNT = pOD_CNT;
	}


	public String getPOD_FLAG() {
		return POD_FLAG;
	}


	public void setPOD_FLAG(String pOD_FLAG) {
		POD_FLAG = pOD_FLAG;
	}


	public String getPOD_DEO() {
		return POD_DEO;
	}


	public void setPOD_DEO(String pOD_DEO) {
		POD_DEO = pOD_DEO;
	}


	public String getMODEM_POD() {
		return MODEM_POD;
	}


	public void setMODEM_POD(String mODEM_POD) {
		MODEM_POD = mODEM_POD;
	}


	public String getDESP_DATE() {
		return DESP_DATE;
	}


	public void setDESP_DATE(String dESP_DATE) {
		DESP_DATE = dESP_DATE;
	}


	public String getON_DES_CD() {
		return ON_DES_CD;
	}


	public void setON_DES_CD(String oN_DES_CD) {
		ON_DES_CD = oN_DES_CD;
	}


	public String getCONS_TYPE() {
		return CONS_TYPE;
	}


	public void setCONS_TYPE(String cONS_TYPE) {
		CONS_TYPE = cONS_TYPE;
	}


	public String getPIECES() {
		return PIECES;
	}


	public void setPIECES(String pIECES) {
		PIECES = pIECES;
	}


	public String getPKT_MODE() {
		return PKT_MODE;
	}


	public void setPKT_MODE(String pKT_MODE) {
		PKT_MODE = pKT_MODE;
	}


	public String getON_PKT_NO() {
		return ON_PKT_NO;
	}


	public void setON_PKT_NO(String oN_PKT_NO) {
		ON_PKT_NO = oN_PKT_NO;
	}


	public String getONMAN_FLAG() {
		return ONMAN_FLAG;
	}


	public void setONMAN_FLAG(String oNMAN_FLAG) {
		ONMAN_FLAG = oNMAN_FLAG;
	}


	public String getSTCODE() {
		return STCODE;
	}


	public void setSTCODE(String sTCODE) {
		STCODE = sTCODE;
	}


	public String getENT_CODE() {
		return ENT_CODE;
	}


	public void setENT_CODE(String eNT_CODE) {
		ENT_CODE = eNT_CODE;
	}


	public String getMODIFIED() {
		return MODIFIED;
	}


	public void setMODIFIED(String mODIFIED) {
		MODIFIED = mODIFIED;
	}




	public String getINS_FLAG() {
		return INS_FLAG;
	}


	public void setINS_FLAG(String iNS_FLAG) {
		INS_FLAG = iNS_FLAG;
	}


	public String getINSURED() {
		return INSURED;
	}


	public void setINSURED(String iNSURED) {
		INSURED = iNSURED;
	}


	public String getPOLICY_NO() {
		return POLICY_NO;
	}


	public void setPOLICY_NO(String pOLICY_NO) {
		POLICY_NO = pOLICY_NO;
	}


	public String getLC_FLAG() {
		return LC_FLAG;
	}


	public void setLC_FLAG(String lC_FLAG) {
		LC_FLAG = lC_FLAG;
	}


	public String getLC_AMOUNT() {
		return LC_AMOUNT;
	}


	public void setLC_AMOUNT(String lC_AMOUNT) {
		LC_AMOUNT = lC_AMOUNT;
	}


	public String getLC_AMD() {
		return LC_AMD;
	}


	public void setLC_AMD(String lC_AMD) {
		LC_AMD = lC_AMD;
	}


	public String getREF_NO() {
		return REF_NO;
	}


	public void setREF_NO(String rEF_NO) {
		REF_NO = rEF_NO;
	}


	public String getCON_LC_PAY() {
		return CON_LC_PAY;
	}


	public void setCON_LC_PAY(String cON_LC_PAY) {
		CON_LC_PAY = cON_LC_PAY;
	}


	public String getCON_AMT() {
		return CON_AMT;
	}


	public void setCON_AMT(String cON_AMT) {
		CON_AMT = cON_AMT;
	}


	/*public String getMIS_RETURN() {
		return MIS_RETURN;
	}


	public void setMIS_RETURN(String mIS_RETURN) {
		MIS_RETURN = mIS_RETURN;
	}


	public String getHUB_WEIGHT() {
		return HUB_WEIGHT;
	}


	public void setHUB_WEIGHT(String hUB_WEIGHT) {
		HUB_WEIGHT = hUB_WEIGHT;
	}


	public String getRES_CODE() {
		return RES_CODE;
	}


	public void setRES_CODE(String rES_CODE) {
		RES_CODE = rES_CODE;
	}


	public String getRETURN_IN() {
		return RETURN_IN;
	}


	public void setRETURN_IN(String rETURN_IN) {
		RETURN_IN = rETURN_IN;
	}


	public String getTEL_NO() {
		return TEL_NO;
	}


	public void setTEL_NO(String tEL_NO) {
		TEL_NO = tEL_NO;
	}


	public String getRECEIVER() {
		return RECEIVER;
	}


	public void setRECEIVER(String rECEIVER) {
		RECEIVER = rECEIVER;
	}


	public String getACK_DATE() {
		return ACK_DATE;
	}


	public void setACK_DATE(String aCK_DATE) {
		ACK_DATE = aCK_DATE;
	}


	public String getACK_TIME() {
		return ACK_TIME;
	}


	public void setACK_TIME(String aCK_TIME) {
		ACK_TIME = aCK_TIME;
	}


	public String getPAR_BRANCH() {
		return PAR_BRANCH;
	}


	public void setPAR_BRANCH(String pAR_BRANCH) {
		PAR_BRANCH = pAR_BRANCH;
	}


	public String getPROD_ID() {
		return PROD_ID;
	}


	public void setPROD_ID(String pROD_ID) {
		PROD_ID = pROD_ID;
	}


	public String getARC_AMT() {
		return ARC_AMT;
	}


	public void setARC_AMT(String aRC_AMT) {
		ARC_AMT = aRC_AMT;
	}


	public String getM_FOC_COM() {
		return M_FOC_COM;
	}


	public void setM_FOC_COM(String m_FOC_COM) {
		M_FOC_COM = m_FOC_COM;
	}


	public String getMOD_DATE() {
		return MOD_DATE;
	}


	public void setMOD_DATE(String mOD_DATE) {
		MOD_DATE = mOD_DATE;
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


	public String getBR_LD() {
		return BR_LD;
	}


	public void setBR_LD(String bR_LD) {
		BR_LD = bR_LD;
	}


	public String getOGMDEST() {
		return OGMDEST;
	}


	public void setOGMDEST(String oGMDEST) {
		OGMDEST = oGMDEST;
	}


	public String getDEPT() {
		return DEPT;
	}


	public void setDEPT(String dEPT) {
		DEPT = dEPT;
	}


	public String getSCAN() {
		return SCAN;
	}


	public void setSCAN(String sCAN) {
		SCAN = sCAN;
	}


	public String getCOM_FOC() {
		return COM_FOC;
	}


	public void setCOM_FOC(String cOM_FOC) {
		COM_FOC = cOM_FOC;
	}


	public String getCOM_DT() {
		return COM_DT;
	}


	public void setCOM_DT(String cOM_DT) {
		COM_DT = cOM_DT;
	}


	public String getID_CD() {
		return ID_CD;
	}


	public void setID_CD(String iD_CD) {
		ID_CD = iD_CD;
	}


	public String getMODEM() {
		return MODEM;
	}


	public void setMODEM(String mODEM) {
		MODEM = mODEM;
	}


	public String getA_FLAG() {
		return A_FLAG;
	}


	public void setA_FLAG(String a_FLAG) {
		A_FLAG = a_FLAG;
	}


	public String getLLOAD() {
		return LLOAD;
	}


	public void setLLOAD(String lLOAD) {
		LLOAD = lLOAD;
	}


	public String getRET_REASON() {
		return RET_REASON;
	}


	public void setRET_REASON(String rET_REASON) {
		RET_REASON = rET_REASON;
	}


	public String getDUP_NO() {
		return DUP_NO;
	}


	public void setDUP_NO(String dUP_NO) {
		DUP_NO = dUP_NO;
	}


	public String getMODI_FLAG() {
		return MODI_FLAG;
	}


	public void setMODI_FLAG(String mODI_FLAG) {
		MODI_FLAG = mODI_FLAG;
	}


	public String getADD_AMOUNT() {
		return ADD_AMOUNT;
	}


	public void setADD_AMOUNT(String aDD_AMOUNT) {
		ADD_AMOUNT = aDD_AMOUNT;
	}


	public String getCONTENTS() {
		return CONTENTS;
	}


	public void setCONTENTS(String cONTENTS) {
		CONTENTS = cONTENTS;
	}


	public String getPINCODE() {
		return PINCODE;
	}


	public void setPINCODE(String pINCODE) {
		PINCODE = pINCODE;
	}


	public String getCODCHRG() {
		return CODCHRG;
	}


	public void setCODCHRG(String cODCHRG) {
		CODCHRG = cODCHRG;
	}


	public String getAPT_CHRG() {
		return APT_CHRG;
	}


	public void setAPT_CHRG(String aPT_CHRG) {
		APT_CHRG = aPT_CHRG;
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


	public String getPCAT() {
		return PCAT;
	}


	public void setPCAT(String pCAT) {
		PCAT = pCAT;
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

*/
/*	public DomainBase getDomainBase() {
		return domainBase;
	}


	public void setDomainBase(DomainBase domainBase) {
		this.domainBase = domainBase;
	}
	
*/	
	

	
}
