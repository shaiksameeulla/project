package com.ff.ud.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ff.ud.constants.OpsmanDBFConstant;

@Entity(name="com.ff.ud.domain.FinancialProductDO")
@Table(name=OpsmanDBFConstant.DATABASE_NAME_MASTER+"ff_d_financial_product")
public class FinancialProductDO {
 @Id
 @Column(name="FINANCIAL_PRODUCT_ID")private Integer productId;
 @Column(name="FINANCIAL_PRODUCT_NAME")private String productName;
	
	
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}

	
}
