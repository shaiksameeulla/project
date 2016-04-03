package com.ff.ud.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name="com.ff.ud.domain.PacketDO")
@Table(name="OPSMAN.dbo.Stg_In_PACKET")
//@Table(name="opsman.dbo.BOBPACKET")
public class PacketDO  {
    
	@Id
	@GeneratedValue
	@Column(name="PKT_NO") private String packetNo;
	//@Transient private String packetNo;
	@Column(name="DESP_DATE") private String dispatchDate;
	@Column(name="CSA_CD") private String CSA_CD;
	@Column(name="BPL_NO") private String bplNo;
	@Column(name="MBPL_NO") private String mbplNo;
	@Column(name="MBL_NO") private String mblNo;
	@Column(name="LOAD_NO") private String loadNo;
	@Column(name="CON_CNT") private String conContent;
	@Column(name="No_Of_CN") private String noOfConsignment;
	@Column(name="TYPE") private String consignmentType;
	@Column(name="TRANS_CODE") private String TRANS_CODE;
	@Column(name="TRANS_DEST") private String TRANS_DEST;
	@Column(name="CLOS_FLAG") private String closedFlag;
	@Column(name="COUNTER") private String COUNTER;
	@Column(name="CHANNEL") private String CHANNEL;
	@Column(name="M_CON_CD") private String M_CON_CD;
	@Column(name="MACHINE") private String MACHINE;
	@Column(name="WEIGHT") private String weight;
	@Column(name="HUB_WEIGHT") private String hubWeight;
	@Column(name="DIRECT") private String direct;
	@Column(name="HUB_LOAD") private String hubLoad;
	@Column(name="BAG_LOCK") private String bagLockNo;
	@Column(name="WEBUPD") private String WEBUPD;
	@Column(name="FILENAME") private String FILENAME;
	@Column(name="SELECTION") private String SELECTION;
	@Column(name="MBPL_LOCK") private String MBPL_LOCK;
	@Column(name="NO_OF_BAG") private String NO_OF_BAG;
	@Column(name="FILEID") private String FILEID;
	@Column(name="ORIGIN") private String originCity;
	@Column(name="INSERTTIME") private String INSERTTIME;
	/*@Column(name="DT_UDAAN") private String DT_UDAAN;
	@Column(name="DT_DBF") private String DT_DBF;
	@Column(name="DR_UDAAN") private String DR_UDAAN;
	@Column(name="DT_OPS_CENTRAL") private String DT_OPS_CENTRAL;
	@Column(name="DT_REGION") private String DT_REGION;
	@Column(name="DT_OFF_TYPE") private String DT_OFF_TYPE;*/
	
	/*private DomainBase domainBase;
	
	
	
	public DomainBase getDomainBase() {
		return domainBase;
	}
	public void setDomainBase(DomainBase domainBase) {
		this.domainBase = domainBase;
	}*/
	
	@OneToMany(cascade={CascadeType.ALL})
	//@OneToMany(fetch = FetchType.EAGER, mappedBy = "PacketDO")
    @JoinColumn(name="PKT_NO")
    //@IndexColumn(name="BR_PKT_NO")
	private List<OutgoDO> outgo;
	
	
	public List<OutgoDO> getOutgo() {
		return outgo;
	}
	public void setOutgo(List<OutgoDO> outgo) {
		this.outgo = outgo;
	}
	public boolean assignValuesToDBFArray(Object[] dataArray,PacketDO pktdo){
		try{
			dataArray[0]=pktdo.getOriginCity();
			dataArray[1]=pktdo.getDispatchDate();
			dataArray[2]="";
			dataArray[3]="";
			dataArray[4]="";
			dataArray[5]="";
			dataArray[6]=pktdo.getTRANS_CODE();
			dataArray[7]=pktdo.getPacketNo();
			dataArray[8]=pktdo.getBplNo();
			dataArray[9]=pktdo.getMbplNo();
			dataArray[10]=pktdo.getMblNo();
			dataArray[11]="";
			dataArray[12]="";
			dataArray[13]="";
			dataArray[14]=pktdo.getNoOfConsignment();	
			dataArray[15]="";
			dataArray[16]=pktdo.getNO_OF_BAG();
			dataArray[17]=pktdo.getWeight();
			dataArray[18]="";
			dataArray[19]="";	
			dataArray[20]=pktdo.getLoadNo();
			dataArray[21]="";
			dataArray[22]=pktdo.getWEBUPD();
			dataArray[23]="";
			dataArray[24]=pktdo.getFILENAME();
			dataArray[25]=pktdo.getMACHINE();
			dataArray[26]="";
			dataArray[27]="";
			dataArray[28]="";
			dataArray[29]="";
			dataArray[30]="";
			dataArray[31]="";
			dataArray[32]=pktdo.getHubWeight();
			dataArray[33]="";
			dataArray[34]="";
			dataArray[35]="";
			dataArray[36]=pktdo.getBagLockNo();
			dataArray[37]=pktdo.getMBPL_LOCK();
			dataArray[38]="";
			dataArray[39]="";
			dataArray[40]="";
			dataArray[41]="";
			dataArray[42]=pktdo.getSELECTION();
			//dataArray[43]=pktdo.getDtDbf();
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false; 
		}
	}
	public String getPacketNo() {
		return packetNo;
	}
	public void setPacketNo(String packetNo) {
		this.packetNo = packetNo;
	}
	public String getDispatchDate() {
		return dispatchDate;
	}
	public void setDispatchDate(String dispatchDate) {
		this.dispatchDate = dispatchDate;
	}
	public String getCSA_CD() {
		return CSA_CD;
	}
	public void setCSA_CD(String cSA_CD) {
		CSA_CD = cSA_CD;
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
	public String getConContent() {
		return conContent;
	}
	public void setConContent(String conContent) {
		this.conContent = conContent;
	}
	public String getNoOfConsignment() {
		return noOfConsignment;
	}
	public void setNoOfConsignment(String noOfConsignment) {
		this.noOfConsignment = noOfConsignment;
	}
	public String getConsignmentType() {
		return consignmentType;
	}
	public void setConsignmentType(String consignmentType) {
		this.consignmentType = consignmentType;
	}
	public String getTRANS_CODE() {
		return TRANS_CODE;
	}
	public void setTRANS_CODE(String tRANS_CODE) {
		TRANS_CODE = tRANS_CODE;
	}
	public String getTRANS_DEST() {
		return TRANS_DEST;
	}
	public void setTRANS_DEST(String tRANS_DEST) {
		TRANS_DEST = tRANS_DEST;
	}
	public String getClosedFlag() {
		return closedFlag;
	}
	public void setClosedFlag(String closedFlag) {
		this.closedFlag = closedFlag;
	}
	public String getCOUNTER() {
		return COUNTER;
	}
	public void setCOUNTER(String cOUNTER) {
		COUNTER = cOUNTER;
	}
	public String getCHANNEL() {
		return CHANNEL;
	}
	public void setCHANNEL(String cHANNEL) {
		CHANNEL = cHANNEL;
	}
	public String getM_CON_CD() {
		return M_CON_CD;
	}
	public void setM_CON_CD(String m_CON_CD) {
		M_CON_CD = m_CON_CD;
	}
	public String getMACHINE() {
		return MACHINE;
	}
	public void setMACHINE(String mACHINE) {
		MACHINE = mACHINE;
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
	public String getBagLockNo() {
		return bagLockNo;
	}
	public void setBagLockNo(String bagLockNo) {
		this.bagLockNo = bagLockNo;
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
	public String getSELECTION() {
		return SELECTION;
	}
	public void setSELECTION(String sELECTION) {
		SELECTION = sELECTION;
	}
	public String getMBPL_LOCK() {
		return MBPL_LOCK;
	}
	public void setMBPL_LOCK(String mBPL_LOCK) {
		MBPL_LOCK = mBPL_LOCK;
	}
	public String getNO_OF_BAG() {
		return NO_OF_BAG;
	}
	public void setNO_OF_BAG(String nO_OF_BAG) {
		NO_OF_BAG = nO_OF_BAG;
	}
	public String getFILEID() {
		return FILEID;
	}
	public void setFILEID(String fILEID) {
		FILEID = fILEID;
	}
	public String getOriginCity() {
		return originCity;
	}
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	public String getINSERTTIME() {
		return INSERTTIME;
	}
	public void setINSERTTIME(String iNSERTTIME) {
		INSERTTIME = iNSERTTIME;
	}
	
	@Override
	public String toString() {
		return "PacketDO [packetNo=" + packetNo + ", dispatchDate="
				+ dispatchDate + ", CSA_CD=" + CSA_CD + ", bplNo=" + bplNo
				+ ", mbplNo=" + mbplNo + ", mblNo=" + mblNo + ", loadNo="
				+ loadNo + ", conContent=" + conContent + ", noOfConsignment="
				+ noOfConsignment + ", consignmentType=" + consignmentType
				+ ", TRANS_CODE=" + TRANS_CODE + ", TRANS_DEST=" + TRANS_DEST
				+ ", closedFlag=" + closedFlag + ", COUNTER=" + COUNTER
				+ ", CHANNEL=" + CHANNEL + ", M_CON_CD=" + M_CON_CD
				+ ", MACHINE=" + MACHINE + ", weight=" + weight
				+ ", hubWeight=" + hubWeight + ", direct=" + direct
				+ ", hubLoad=" + hubLoad + ", bagLockNo=" + bagLockNo
				+ ", WEBUPD=" + WEBUPD + ", FILENAME=" + FILENAME
				+ ", SELECTION=" + SELECTION + ", MBPL_LOCK=" + MBPL_LOCK
				+ ", NO_OF_BAG=" + NO_OF_BAG + ", FILEID=" + FILEID
				+ ", originCity=" + originCity + ", INSERTTIME=" + INSERTTIME
				+ "]";
	}

    
	

}
