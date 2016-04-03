package com.ff.ud.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ff.ud.constants.OpsmanDBFConstant;

@Entity(name="com.ff.ud.domain.ProductDO")
@Table(name=OpsmanDBFConstant.DATABASE_NAME_MASTER+"ff_d_product")
public class ProductDO  {
    
	@Id
	@Column(name="PRODUCT_ID") private Integer productId;
	@Column(name="CONSG_SERIES") private String consgSeries;
	@Column(name="PRODUCT_NAME") private String productName;
	
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getConsgSeries() {
		return consgSeries;
	}
	public void setConsgSeries(String consgSeries) {
		this.consgSeries = consgSeries;
	}
	


}
