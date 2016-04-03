package com.ff.ud.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity(name="com.ff.ud.domain.OutgoDO")
@Table(name="OPSMAN.dbo.Stg_In_Outgo")
//@Table(name="OPSMAN.dbo.hobpacket")

public class OutgoDO {
	
	@Id
	@Column(name="CON_NO") private String conNo;
	@Column(name="BKING_DATE") private String bkingDate;
	@Column(name="BOOK_TIME") private String bookTime;
	@Column(name="BPL_NO") private String bplNo;
	
	
	//@Column(name="BR_PKT_NO") private String brPktNo;
    @Transient	private String brPktNo;
	
	@Column(name="BRDEST") private String brdest;
	@Column(name="BREADTH") private String breadth;
	@Column(name="CLIENTID") private String clientid;
	@Column(name="CLIENTNO") private String clientno;
	@Column(name="COD_AMT") private String codAmt;
	@Column(name="CON_CODE") private String conCode;
	@Column(name="CON_TYPE") private String conType;
	@Column(name="CONSNAME") private String consname;
	@Column(name="CONTENTS") private String contents;
	/*@Column(name="DECLARE") private String declare;*/
	@Column(name="DESP_DATE") private String despDate;
	@Column(name="DEST_CODE") private String destCode;
	@Column(name="DIRECT") private String direct;
	/*@Column(name="DR_UDAAN") private String drUdaan;
	@Column(name="DT_DBF") private String dtDbf;
	@Column(name="DT_OFF_TYPE") private String dtOffType;
	@Column(name="DT_OPS_CENTRAL") private String dtOpsCentral;
	@Column(name="DT_REGION") private String dtRegion;
	@Column(name="DT_UDAAN") private String dtUdaan;*/
	@Column(name="EXPO_NET") private String expoNet;
	@Column(name="FILEID") private String fileid;
	@Column(name="FILENAME") private String filename;
	@Column(name="FLAG") private String flag;
	@Column(name="FORM") private String form;
	@Column(name="FORMS") private String forms;
	@Column(name="HEIGHT") private String height;
	@Column(name="Hub_pin") private String hubPin;
	@Column(name="HUB_WEIGHT") private String hubWeight;
	@Column(name="INSERTTIME") private String inserttime;
	@Column(name="INSURED") private String insured;
	@Column(name="INVOICE") private String invoice;
	@Column(name="LCL_BR_LD") private String lclBrId;
	@Column(name="LCT") private String lct;
	@Column(name="LCT_AMT") private String lctAmt;
	@Column(name="LD_DESP") private String ldDesp;
	@Column(name="LENGTH") private String length;
	@Column(name="LLOAD") private String lload;
	@Column(name="LOAD_NO") private String loadNo;
	@Column(name="M_CON_CD") private String mConCd;
	@Column(name="MAN_FLAG") private String manFlag;
	@Column(name="MISRTE_RET") private String misrteRet;
	@Column(name="MPL_FLAG") private String mplFlag;
	@Column(name="MPL_NO") private String mplNo;
	@Column(name="ORIGIN") private String origin;
	@Column(name="PIECES") private String pieces;
	@Column(name="PINCODE") private String pincode;
	@Column(name="PKT_NO") private String pktNo;
	@Column(name="REMARKS") private String remarks;
	@Column(name="RET_CD") private String retCd;
	@Column(name="STCODE") private String stcode;
	@Column(name="TOP_AMT") private String topAmt;
	@Column(name="TRAN_CODE") private String tranCode;
	@Column(name="U_BR_CD") private String uBrCd;
	@Column(name="U_CON_CD") private String uConCd;
	@Column(name="U_EMP_NO") private String uEmpNo;
	@Column(name="U_VERSION") private String uVersion;
	@Column(name="USERID") private String userid;
	@Column(name="VALUE") private String value;
	@Column(name="VENDORID") private String vendorid;
	@Column(name="VENDORNAME") private String vendorname;
	@Column(name="WEBUPD") private String webupd;
	@Column(name="WEBUPD1") private String webupd1;
	
	
	
	@ManyToOne
	@JoinColumn(name = "PKT_NO",insertable=false,updatable=false,nullable=false)
	private ProductDO packetDO;
	
	
	
	public boolean assignValuesToDBFArray(Object[] dataArray,OutgoDO outgo){
		try{
			dataArray[0]=outgo.getOrigin();
			dataArray[1]=outgo.getDespDate();
			dataArray[2]=outgo.getLoadNo();
			dataArray[3]=outgo.getDestCode();
			dataArray[4]=outgo.getStcode();
			dataArray[5]=outgo.getPktNo();
			dataArray[6]=outgo.getConNo();
			dataArray[7]=outgo.getBplNo();
			dataArray[8]=outgo.getConCode();
			dataArray[9]=outgo.getConType();
			dataArray[10]=outgo.getManFlag();
			dataArray[11]=outgo.getMisrteRet();
			dataArray[12]=outgo.getContents();
			dataArray[13]=outgo.getLct();
			dataArray[14]=outgo.getLctAmt();	
			dataArray[15]=outgo.getTopAmt();
			dataArray[16]=outgo.getCodAmt();
			dataArray[17]=outgo.getHubWeight();
			dataArray[18]=outgo.getuVersion();
			dataArray[19]=outgo.getPieces();	
			dataArray[20]=outgo.getLclBrId();
			dataArray[21]=outgo.getRetCd();
			dataArray[22]=outgo.getInvoice();
			dataArray[23]="";
			dataArray[24]=outgo.getValue();
			dataArray[25]=outgo.getLength();
			dataArray[26]=outgo.getBreadth();
			dataArray[27]=outgo.getHeight();
			dataArray[28]=outgo.getInsured();
			dataArray[29]=outgo.getForm();
			dataArray[30]=outgo.getRemarks();
			dataArray[31]=outgo.getExpoNet();
			dataArray[32]=outgo.getLdDesp();
			dataArray[33]=outgo.getWebupd();
			dataArray[34]=outgo.getWebupd1();
			dataArray[35]="";
			dataArray[36]="";
			dataArray[37]=outgo.getBrdest();
			dataArray[38]=outgo.getConsname();
			dataArray[39]=outgo.getFilename();
			dataArray[40]=outgo.getClientid();
			dataArray[41]=outgo.getPincode();
			dataArray[42]=outgo.getHubPin();
			dataArray[43]=outgo.getVendorid();
			dataArray[44]=outgo.getClientno();
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false; 
		}
	}
	
	/*private DomainBase domainBase;
	
	
	
	
	public DomainBase getDomainBase() {
		return domainBase;
	}
	public void setDomainBase(DomainBase domainBase) {
		this.domainBase = domainBase;
	}*/
	public String getConNo() {
		return conNo;
	}
	public void setConNo(String conNo) {
		this.conNo = conNo;
	}
	public String getBkingDate() {
		return bkingDate;
	}
	public void setBkingDate(String bkingDate) {
		this.bkingDate = bkingDate;
	}
	public String getBookTime() {
		return bookTime;
	}
	public void setBookTime(String bookTime) {
		this.bookTime = bookTime;
	}
	public String getBplNo() {
		return bplNo;
	}
	public void setBplNo(String bplNo) {
		this.bplNo = bplNo;
	}
	public String getBrPktNo() {
		return brPktNo;
	}
	public void setBrPktNo(String brPktNo) {
		this.brPktNo = brPktNo;
	}
	public String getBrdest() {
		return brdest;
	}
	public void setBrdest(String brdest) {
		this.brdest = brdest;
	}
	public String getBreadth() {
		return breadth;
	}
	public void setBreadth(String breadth) {
		this.breadth = breadth;
	}
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	public String getClientno() {
		return clientno;
	}
	public void setClientno(String clientno) {
		this.clientno = clientno;
	}
	public String getCodAmt() {
		return codAmt;
	}
	public void setCodAmt(String codAmt) {
		this.codAmt = codAmt;
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
	public String getConsname() {
		return consname;
	}
	public void setConsname(String consname) {
		this.consname = consname;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public String getDespDate() {
		return despDate;
	}
	public void setDespDate(String despDate) {
		this.despDate = despDate;
	}
	public String getDestCode() {
		return destCode;
	}
	public void setDestCode(String destCode) {
		this.destCode = destCode;
	}
	public String getDirect() {
		return direct;
	}
	public void setDirect(String direct) {
		this.direct = direct;
	}
	
	public String getExpoNet() {
		return expoNet;
	}
	public void setExpoNet(String expoNet) {
		this.expoNet = expoNet;
	}
	public String getFileid() {
		return fileid;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public String getForms() {
		return forms;
	}
	public void setForms(String forms) {
		this.forms = forms;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getHubPin() {
		return hubPin;
	}
	public void setHubPin(String hubPin) {
		this.hubPin = hubPin;
	}
	public String getHubWeight() {
		return hubWeight;
	}
	public void setHubWeight(String hubWeight) {
		this.hubWeight = hubWeight;
	}
	public String getInserttime() {
		return inserttime;
	}
	public void setInserttime(String inserttime) {
		this.inserttime = inserttime;
	}
	public String getInsured() {
		return insured;
	}
	public void setInsured(String insured) {
		this.insured = insured;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public String getLclBrId() {
		return lclBrId;
	}
	public void setLclBrId(String lclBrId) {
		this.lclBrId = lclBrId;
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
	public String getLdDesp() {
		return ldDesp;
	}
	public void setLdDesp(String ldDesp) {
		this.ldDesp = ldDesp;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getLload() {
		return lload;
	}
	public void setLload(String lload) {
		this.lload = lload;
	}
	public String getLoadNo() {
		return loadNo;
	}
	public void setLoadNo(String loadNo) {
		this.loadNo = loadNo;
	}
	public String getmConCd() {
		return mConCd;
	}
	public void setmConCd(String mConCd) {
		this.mConCd = mConCd;
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
	public String getMplFlag() {
		return mplFlag;
	}
	public void setMplFlag(String mplFlag) {
		this.mplFlag = mplFlag;
	}
	public String getMplNo() {
		return mplNo;
	}
	public void setMplNo(String mplNo) {
		this.mplNo = mplNo;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getPieces() {
		return pieces;
	}
	public void setPieces(String pieces) {
		this.pieces = pieces;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getPktNo() {
		return pktNo;
	}
	public void setPktNo(String pktNo) {
		this.pktNo = pktNo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRetCd() {
		return retCd;
	}
	public void setRetCd(String retCd) {
		this.retCd = retCd;
	}
	public String getStcode() {
		return stcode;
	}
	public void setStcode(String stcode) {
		this.stcode = stcode;
	}
	public String getTopAmt() {
		return topAmt;
	}
	public void setTopAmt(String topAmt) {
		this.topAmt = topAmt;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getuBrCd() {
		return uBrCd;
	}
	public void setuBrCd(String uBrCd) {
		this.uBrCd = uBrCd;
	}
	public String getuConCd() {
		return uConCd;
	}
	public void setuConCd(String uConCd) {
		this.uConCd = uConCd;
	}
	public String getuEmpNo() {
		return uEmpNo;
	}
	public void setuEmpNo(String uEmpNo) {
		this.uEmpNo = uEmpNo;
	}
	public String getuVersion() {
		return uVersion;
	}
	public void setuVersion(String uVersion) {
		this.uVersion = uVersion;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getVendorid() {
		return vendorid;
	}
	public void setVendorid(String vendorid) {
		this.vendorid = vendorid;
	}
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
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
	@Override
	public String toString() {
		return "OutgoDO [conNo=" + conNo + ", bkingDate=" + bkingDate
				+ ", bookTime=" + bookTime + ", bplNo=" + bplNo + ", brPktNo="
				+ brPktNo + ", brdest=" + brdest + ", breadth=" + breadth
				+ ", clientid=" + clientid + ", clientno=" + clientno
				+ ", codAmt=" + codAmt + ", conCode=" + conCode + ", conType="
				+ conType + ", consname=" + consname + ", contents=" + contents
				+ ", despDate=" + despDate + ", destCode=" + destCode
				+ ", direct=" + direct + ", expoNet=" + expoNet + ", fileid="
				+ fileid + ", filename=" + filename + ", flag=" + flag
				+ ", form=" + form + ", forms=" + forms + ", height=" + height
				+ ", hubPin=" + hubPin + ", hubWeight=" + hubWeight
				+ ", inserttime=" + inserttime + ", insured=" + insured
				+ ", invoice=" + invoice + ", lclBrId=" + lclBrId + ", lct="
				+ lct + ", lctAmt=" + lctAmt + ", ldDesp=" + ldDesp
				+ ", length=" + length + ", lload=" + lload + ", loadNo="
				+ loadNo + ", mConCd=" + mConCd + ", manFlag=" + manFlag
				+ ", misrteRet=" + misrteRet + ", mplFlag=" + mplFlag
				+ ", mplNo=" + mplNo + ", origin=" + origin + ", pieces="
				+ pieces + ", pincode=" + pincode + ", pktNo=" + pktNo
				+ ", remarks=" + remarks + ", retCd=" + retCd + ", stcode="
				+ stcode + ", topAmt=" + topAmt + ", tranCode=" + tranCode
				+ ", uBrCd=" + uBrCd + ", uConCd=" + uConCd + ", uEmpNo="
				+ uEmpNo + ", uVersion=" + uVersion + ", userid=" + userid
				+ ", value=" + value + ", vendorid=" + vendorid
				+ ", vendorname=" + vendorname + ", webupd=" + webupd
				+ ", webupd1=" + webupd1 +"]";
	}
	
	
	

}
