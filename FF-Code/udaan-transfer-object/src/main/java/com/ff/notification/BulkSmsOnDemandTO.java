package com.ff.notification;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class BulkSmsOnDemandTO extends CGBaseTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5756684695903852494L;
	private String officeType;
	private Integer regionId;
	private Integer cityId;
	private Integer officeId;
	private String fromDate;
	private String toDate;
	private String cnStatus;
	private Integer loginUserId;
	
	private List<BulkSmsConsignmentDtlsTO> consignmentDtlTOs;
	private int count;
	private String[] consignmentNos = new String[count];
	private String[] mobileNos = new String[count];

	public String getOfficeType() {
		return officeType;
	}

	public void setOfficeType(String officeType) {
		this.officeType = officeType;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getOfficeId() {
		return officeId;
	}

	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getCnStatus() {
		return cnStatus;
	}

	public void setCnStatus(String cnStatus) {
		this.cnStatus = cnStatus;
	}

	public Integer getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(Integer loginUserId) {
		this.loginUserId = loginUserId;
	}

	public List<BulkSmsConsignmentDtlsTO> getConsignmentDtlTOs() {
		return consignmentDtlTOs;
	}

	public void setConsignmentDtlTOs(
			List<BulkSmsConsignmentDtlsTO> consignmentDtlTOs) {
		this.consignmentDtlTOs = consignmentDtlTOs;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String[] getConsignmentNos() {
		return consignmentNos;
	}

	public void setConsignmentNos(String[] consignmentNos) {
		this.consignmentNos = consignmentNos;
	}

	public String[] getMobileNos() {
		return mobileNos;
	}

	public void setMobileNos(String[] mobileNos) {
		this.mobileNos = mobileNos;
	}
	
}
