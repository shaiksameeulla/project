package com.ff.ud.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="HOBOUTGO",schema="opsman.dbo")
public class OgmDTO {

	@Id
	@Column(name="CON_NO")		private String conNo;  
	
	@Column(name="ORIGIN")      private String origin;         
	@Column(name="DESP_DATE")	private String despDate;       
	@Column(name="LOAD_NO")		private String loadNo;         
	@Column(name="DEST_CODE")	private String destCode;       
	@Column(name="STCODE")		private String stcode;         
	@Column(name="PKT_NO")		private String pktNo;          
	        
	@Column(name="BPL_NO")		private String bplNo;          
	@Column(name="CON_CODE")	private String conCode;        
	@Column(name="CON_TYPE")	private String conType;        
	@Column(name="MAN_FLAG")	private String manFlag;        
	@Column(name="MISRTE_RET")	private String misrteRet;      
	@Column(name="CONTENTS")	private String contents;       
	@Column(name="LCT")		private String lct;            
	@Column(name="LCT_AMT")		private String lctAmt;         
	@Column(name="TOP_AMT")		private String topAmt;         
	@Column(name="COD_AMT")		private String codAmt;         
	@Column(name="HUB_WEIGHT")	private String hubWeight;      
	@Column(name="U_VERSION")	private String uVersion;       
	@Column(name="PIECES")		private String pieces;         
	@Column(name="LCL_BR_LD")	private String lclBrLd;        
	@Column(name="RET_CD")		private String retCd;          
	@Column(name="INVOICE")		private String invoice;        
	//@Column(name="'DECLARE'")		private String declare;        
	@Transient		private String declare;
	@Column(name="VALUE")		private String value;          
	@Column(name="LENGTH")		private String length;         
	@Column(name="BREADTH")		private String breadth;        
	@Column(name="HEIGHT")		private String height;         
	@Column(name="INSURED")		private String insured;        
	@Column(name="FORM")		private String form;           
	@Column(name="REMARKS")		private String remarks;        
	@Column(name="EXPO_NET")	private String expoNet;        
	@Column(name="LD_DESP")		private String ldDesp;
	@Transient       private String webupd;
	@Transient      private String webupd1;
	@Transient       private String lflag;
	@Transient        private String bracc;
	@Column(name="BRDEST")		private String brdest;             
	@Column(name="CONSNAME")	private String consname;           
	@Column(name="CLIENTID")	private String clientid;  
	@Transient 	private String filename;
	@Column(name="PINCODE")		private String pincode;        
	@Column(name="HUB_PIN")		private String hubPin;         
	@Column(name="VENDORID")	private String vendorid;       
	@Column(name="CLIENTNO")	private String clientno;
	
	public boolean assignValuesToDBFArray(Object[] dataArray,OgmDTO ogmDTO){
		try{
			
			dataArray[0]=ogmDTO.getOrigin();
			dataArray[1]=ogmDTO.getDespDate();
			dataArray[2]=ogmDTO.getLoadNo();
			dataArray[3]=ogmDTO.getDestCode();
			dataArray[4]=ogmDTO.getStcode();
			dataArray[5]=ogmDTO.getPktNo();
			dataArray[6]=ogmDTO.getConNo();
			dataArray[7]=ogmDTO.getBplNo();
			dataArray[8]=ogmDTO.getConCode();
			dataArray[9]=ogmDTO.getConType();
			dataArray[10]=ogmDTO.getManFlag();
			dataArray[11]=ogmDTO.getMisrteRet();
			dataArray[12]=ogmDTO.getContents();
			dataArray[13]=ogmDTO.getLct();
			dataArray[14]=ogmDTO.getLctAmt();
			dataArray[15]=ogmDTO.getTopAmt();
			dataArray[16]=ogmDTO.getCodAmt();
			dataArray[17]=ogmDTO.getHubWeight();
			dataArray[18]=ogmDTO.getuVersion();
			dataArray[19]=ogmDTO.getPieces();
			dataArray[20]=ogmDTO.getLclBrLd();
			dataArray[21]=ogmDTO.getRetCd();
			dataArray[22]=ogmDTO.getInvoice();
			dataArray[23]=ogmDTO.getDeclare();
			dataArray[24]=ogmDTO.getValue();
			dataArray[25]=ogmDTO.getLength();
			dataArray[26]=ogmDTO.getBreadth();
			dataArray[27]=ogmDTO.getHeight();
			dataArray[28]=ogmDTO.getInsured();
			dataArray[29]=ogmDTO.getForm();
			dataArray[30]=ogmDTO.getRemarks();
			dataArray[31]=ogmDTO.getExpoNet();
			dataArray[32]=ogmDTO.getLdDesp();
			dataArray[33]=ogmDTO.getWebupd();
			dataArray[34]=ogmDTO.getWebupd1();
			dataArray[35]=ogmDTO.getLflag();
			dataArray[36]=ogmDTO.getBracc();
			dataArray[37]=ogmDTO.getBrdest();
			dataArray[38]=ogmDTO.getConsname();
			dataArray[39]=ogmDTO.getFilename();
			dataArray[40]=ogmDTO.getClientid();
			dataArray[41]=ogmDTO.getPincode();
			dataArray[42]=ogmDTO.getHubPin();
			dataArray[43]=ogmDTO.getVendorid();
			dataArray[44]=ogmDTO.getClientno();
			
			return true;
			
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public OgmDTO(){
		
	}
	
	public OgmDTO (String origin,String despDate,String loadNo,
			String destCode,String stcode,String pktNo,
			String conNo, String bplNo, String conCode,
			String conType,String manFlag,String misrteRet,
			String contents,String lct,String lctAmt,String topAmt,
			String codAmt,String hubWeight,String uVersion,String pieces,
			String lclBrLd,String retCd, String invoice,String declare,
			String value, String length,String breadth,String height,String insured,
			String form,  String remarks,String expoNet,String ldDesp,
			String webupd, String webupd1,String lflag,String  bracc,
			String brdest,String consname,String filename,String clientid,String pincode,
			String hubPin,String vendorid,String clientno)
	{
		
		this.origin=origin;
		this.despDate=despDate;
		this.loadNo=loadNo;
		this.destCode=destCode;
		this.stcode=stcode;
		this.pktNo=pktNo;
		this.conNo=conNo;
		this.bplNo=bplNo;
		this.conCode=conCode;
		this.conType=conType;
		this.manFlag=manFlag;
		this.misrteRet=misrteRet;
		this.contents=contents;
		this.lct=lct;
		this.lctAmt=lctAmt;
		this.topAmt=topAmt;
		this.codAmt=codAmt;
		this.hubWeight=hubWeight;
		this.uVersion=uVersion;
		this.pieces=pieces;
		this.lclBrLd=lclBrLd;
		this.retCd=retCd;
		this.invoice=invoice;
		this.declare=declare;
		this.value=value;
		this.length=length;
		this.breadth=breadth;
		this.height=height;
		this.insured=insured;
		this.form=form;
		this.remarks=remarks;
		this.expoNet=expoNet;
		this.ldDesp=ldDesp;
		this.webupd=webupd;
		this.webupd1=webupd1;
		this.lflag=lflag;
		this.bracc=bracc;
		this.brdest=brdest;
		this.consname=consname;
		this.filename=filename;
		this.clientid=clientid;
		this.pincode=pincode;
		this.hubPin=hubPin;
		this.vendorid=vendorid;
		this.clientno=clientno;
	}
	

	public String getConNo() {
		return conNo;
	}

	public void setConNo(String conNo) {
		this.conNo = conNo;
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

	public String getLoadNo() {
		return loadNo;
	}

	public void setLoadNo(String loadNo) {
		this.loadNo = loadNo;
	}

	public String getDestCode() {
		return destCode;
	}

	public void setDestCode(String destCode) {
		this.destCode = destCode;
	}

	public String getStcode() {
		return stcode;
	}

	public void setStcode(String stcode) {
		this.stcode = stcode;
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

	public String getConCode() {
		return conCode;
	}

	public void setConCode(String conCode) {
		this.conCode = conCode;
	}

	public String getConType() {
		return conType;
	}

	public void setConType(String conType) {
		this.conType = conType;
	}

	public String getManFlag() {
		return manFlag;
	}

	public void setManFlag(String manFlag) {
		this.manFlag = manFlag;
	}

	public String getMisrteRet() {
		return misrteRet;
	}

	public void setMisrteRet(String misrteRet) {
		this.misrteRet = misrteRet;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getLct() {
		return lct;
	}

	public void setLct(String lct) {
		this.lct = lct;
	}

	public String getLctAmt() {
		return lctAmt;
	}

	public void setLctAmt(String lctAmt) {
		this.lctAmt = lctAmt;
	}

	public String getTopAmt() {
		return topAmt;
	}

	public void setTopAmt(String topAmt) {
		this.topAmt = topAmt;
	}

	public String getCodAmt() {
		return codAmt;
	}

	public void setCodAmt(String codAmt) {
		this.codAmt = codAmt;
	}

	public String getHubWeight() {
		return hubWeight;
	}

	public void setHubWeight(String hubWeight) {
		this.hubWeight = hubWeight;
	}

	public String getuVersion() {
		return uVersion;
	}

	public void setuVersion(String uVersion) {
		this.uVersion = uVersion;
	}

	public String getPieces() {
		return pieces;
	}

	public void setPieces(String pieces) {
		this.pieces = pieces;
	}

	public String getLclBrLd() {
		return lclBrLd;
	}

	public void setLclBrLd(String lclBrLd) {
		this.lclBrLd = lclBrLd;
	}

	public String getRetCd() {
		return retCd;
	}

	public void setRetCd(String retCd) {
		this.retCd = retCd;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getDeclare() {
		return declare;
	}

	public void setDeclare(String declare) {
		this.declare = declare;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getBreadth() {
		return breadth;
	}

	public void setBreadth(String breadth) {
		this.breadth = breadth;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getInsured() {
		return insured;
	}

	public void setInsured(String insured) {
		this.insured = insured;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getExpoNet() {
		return expoNet;
	}

	public void setExpoNet(String expoNet) {
		this.expoNet = expoNet;
	}

	public String getLdDesp() {
		return ldDesp;
	}

	public void setLdDesp(String ldDesp) {
		this.ldDesp = ldDesp;
	}

	public String getWebupd() {
		return webupd;
	}

	public void setWebupd(String webupd) {
		this.webupd = webupd;
	}

	public String getWebupd1() {
		return webupd1;
	}

	public void setWebupd1(String webupd1) {
		this.webupd1 = webupd1;
	}

	public String getLflag() {
		return lflag;
	}

	public void setLflag(String lflag) {
		this.lflag = lflag;
	}

	public String getBracc() {
		return bracc;
	}

	public void setBracc(String bracc) {
		this.bracc = bracc;
	}

	public String getBrdest() {
		return brdest;
	}

	public void setBrdest(String brdest) {
		this.brdest = brdest;
	}

	public String getConsname() {
		return consname;
	}

	public void setConsname(String consname) {
		this.consname = consname;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getHubPin() {
		return hubPin;
	}

	public void setHubPin(String hubPin) {
		this.hubPin = hubPin;
	}

	public String getVendorid() {
		return vendorid;
	}

	public void setVendorid(String vendorid) {
		this.vendorid = vendorid;
	}

	public String getClientno() {
		return clientno;
	}

	public void setClientno(String clientno) {
		this.clientno = clientno;
	}
}
