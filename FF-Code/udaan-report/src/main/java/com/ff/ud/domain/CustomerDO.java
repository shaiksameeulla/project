package com.ff.ud.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.ff.ud.constants.OpsmanDBFConstant;

@Entity(name="com.ff.ud.domain.CustomerDO")
@Table(name=OpsmanDBFConstant.DATABASE_NAME_MASTER+"ff_d_customer")
public class CustomerDO {
	
	
       @Id
       @Column(name="CUSTOMER_ID")private Integer customerId;
       @Column(name="BUSINESS_NAME")private String customerName;
       @Column(name="SALES_OFFICE")private Integer salesOffice;
       @Column(name="customer_type")private Integer customerType;
	   
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Integer getCustomerType() {
		return customerType;
	}
	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}
	
	   
}