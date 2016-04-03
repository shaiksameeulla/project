package com.ff.ud.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity(name="com.ff.ud.domain.MblDO")
@Table(name="OPSMAN.dbo.Stg_In_MBL")
public class MblDO {

	@Id
	@Column(name="MBL_NO") private String mblNo;
	
	@Column(name="HUB") private String hubCode;
	@Column(name="DEST_CODE") private String destinationCityCode;
	@Column(name="DESP_DATE") private String dispatchDate;
	@Column(name="VEH_NAME") private String vehicleName;
	@Column(name="VEH_NO") private String vehicleNo;
	@Column(name="BAG_WEIGHT") private String bagWeight;
	@Column(name="MBL_FLAG") private String mblFlag;
	@Column(name="DESP_TYPE") private String dispatchType;
	@Column(name="DESP_TO") private String dispatchTo;
	@Column(name="SELECTION") private String selection;
	@Column(name="FILEID") private String fileid;
	@Column(name="ORIGIN") private String originCityCode;
	@Column(name="INSERTTIME") private String inserttime;
	
	
	
	
	public boolean assignValuesToDBFArray(Object[] dataArray,MblDO mbldo){
		try{
			dataArray[0]=mbldo.getOriginCityCode();
			dataArray[1]=mbldo.getHubCode();
			dataArray[2]=mbldo.getDestinationCityCode();
			dataArray[3]="";
			dataArray[4]=mbldo.getDispatchDate();
			dataArray[5]="";
			dataArray[6]=mbldo.getMblNo();
			dataArray[7]=mbldo.getVehicleName();
			dataArray[8]="";
			dataArray[9]="";
			dataArray[10]="";
			dataArray[11]=mbldo.getVehicleNo();
			dataArray[12]=mbldo.getBagWeight();
			dataArray[13]=mbldo.getMblFlag();
			dataArray[14]="";	
			dataArray[15]="";
			dataArray[16]="";
			dataArray[17]="";
			dataArray[18]=mbldo.getDispatchType();
			dataArray[19]=mbldo.getDispatchTo();	
			dataArray[20]=mbldo.getSelection();
			/*dataArray[21]="";
			dataArray[22]=mbldo.getWEBUPD();
			dataArray[23]="";
			dataArray[24]=mbldo.getFILENAME();
			dataArray[25]=mbldo.getMACHINE();
			dataArray[26]="";
			dataArray[27]="";
			dataArray[28]="";
			dataArray[29]="";
			dataArray[30]="";
			dataArray[31]="";
			dataArray[32]=mbldo.getHubWeight();
			dataArray[33]="";
			dataArray[34]="";
			dataArray[35]="";
			dataArray[36]=mbldo.getBagLockNo();
			dataArray[37]=mbldo.getMBPL_LOCK();
			dataArray[38]="";
			dataArray[39]="";
			dataArray[40]="";
			dataArray[41]="";
			dataArray[42]=mbldo.getSELECTION();
			//dataArray[43]=mbldo.getDtDbf();
*/			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false; 
		}
	}
	
	//private DomainBase domainBase;
	
	
	
	/*public DomainBase getDomainBase() {
		return domainBase;
	}
	public void setDomainBase(DomainBase domainBase) {
		this.domainBase = domainBase;
	}*/
	public String getMblNo() {
		return mblNo;
	}
	public void setMblNo(String mblNo) {
		this.mblNo = mblNo;
	}
	public String getHubCode() {
		return hubCode;
	}
	public void setHubCode(String hubCode) {
		this.hubCode = hubCode;
	}
	public String getDestinationCityCode() {
		return destinationCityCode;
	}
	public void setDestinationCityCode(String destinationCityCode) {
		this.destinationCityCode = destinationCityCode;
	}
	public String getDispatchDate() {
		return dispatchDate;
	}
	public void setDispatchDate(String dispatchDate) {
		this.dispatchDate = dispatchDate;
	}
	public String getVehicleName() {
		return vehicleName;
	}
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getBagWeight() {
		return bagWeight;
	}
	public void setBagWeight(String bagWeight) {
		this.bagWeight = bagWeight;
	}
	public String getMblFlag() {
		return mblFlag;
	}
	public void setMblFlag(String mblFlag) {
		this.mblFlag = mblFlag;
	}
	public String getDispatchType() {
		return dispatchType;
	}
	public void setDispatchType(String dispatchType) {
		this.dispatchType = dispatchType;
	}
	public String getDispatchTo() {
		return dispatchTo;
	}
	public void setDispatchTo(String dispatchTo) {
		this.dispatchTo = dispatchTo;
	}
	public String getSelection() {
		return selection;
	}
	public void setSelection(String selection) {
		this.selection = selection;
	}
	public String getFileid() {
		return fileid;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	public String getOriginCityCode() {
		return originCityCode;
	}
	public void setOriginCityCode(String originCityCode) {
		this.originCityCode = originCityCode;
	}
	public String getInserttime() {
		return inserttime;
	}
	public void setInserttime(String inserttime) {
		this.inserttime = inserttime;
	}
	@Override
	public String toString() {
		return "MblDO [mblNo=" + mblNo + ", hubCode=" + hubCode
				+ ", destinationCityCode=" + destinationCityCode
				+ ", dispatchDate=" + dispatchDate + ", vehicleName="
				+ vehicleName + ", vehicleNo=" + vehicleNo + ", bagWeight="
				+ bagWeight + ", mblFlag=" + mblFlag + ", dispatchType="
				+ dispatchType + ", dispatchTo=" + dispatchTo + ", selection="
				+ selection + ", fileid=" + fileid + ", originCityCode="
				+ originCityCode + ", inserttime=" + inserttime
				+ "]";
	}
	
	
	


	

}
