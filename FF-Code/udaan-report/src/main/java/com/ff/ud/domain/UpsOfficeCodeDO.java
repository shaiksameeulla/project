package com.ff.ud.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ff.ud.constants.OpsmanDBFConstant;

@Entity(name="com.ff.ud.domain.UpsOfficeCodeDO")
@Table(name=OpsmanDBFConstant.DATABASE_NAME_MASTER+"ff_d_pickup_delivery_contract")
public class UpsOfficeCodeDO {
	
	@Id
	@Column(name="customer_id") private String customerId;
	@Column(name="office_id") private String officeId;
	
	
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	
	

}
