package com.ff.ud.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ff.ud.constants.OpsmanDBFConstant;



@Entity(name="com.ff.ud.dto.VendorDetailsDTO")
@Table(name=OpsmanDBFConstant.DATABASE_NAME_MASTER+"ff_d_vendor_type")
public class VendorDetailsDTO {
	
	@Id
	@Column(name="vendor_type_id") private Integer vendorTypeId;
	@Column(name="vendor_type_desc") private String vendorTypeName;
	
	public VendorDetailsDTO(){
		super();
	}
	
	
	public VendorDetailsDTO(Integer vendorTypeId, String vendorTypeName) {
		super();
		this.vendorTypeId = vendorTypeId;
		this.vendorTypeName = vendorTypeName;
		
		
	}




	public String getVendorTypeName() {
		return vendorTypeName;
	}


	public void setVendorTypeName(String vendorTypeName) {
		this.vendorTypeName = vendorTypeName;
	}


	public Integer getVendorTypeId() {
		return vendorTypeId;
	}


	public void setVendorTypeId(Integer vendorTypeId) {
		this.vendorTypeId = vendorTypeId;
	}


	
          
          
          
          
}
