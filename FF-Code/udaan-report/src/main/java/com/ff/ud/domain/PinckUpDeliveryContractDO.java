package com.ff.ud.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ff.ud.constants.OpsmanDBFConstant;

@Entity(name="com.ff.ud.domain.PinckUpDeliveryContractDO")
@Table(name=OpsmanDBFConstant.DATABASE_NAME_MASTER+"ff_d_pickup_delivery_contract")
public class PinckUpDeliveryContractDO {

	@Id
	@Column(name="office_id") private Integer officeId;
	@Column(name="customer_id") private String customerId;
	
	
	
	public Integer getOfficeId() {
		return officeId;
	}
	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	
	
	
	
}
