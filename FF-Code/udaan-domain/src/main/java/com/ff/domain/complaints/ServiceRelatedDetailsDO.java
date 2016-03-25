package com.ff.domain.complaints;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.ProductDO;

public class ServiceRelatedDetailsDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1677526852113340551L;
	
	private Integer serviceRelatedId;
	private Double weight;
	private String empPhone;
	private String empEmail;
	private ProductDO productDO ;
	private ConsignmentTypeDO consgTypeDO;
	private CNPaperWorksDO paperworkDO ;
	private CityDO cityDO ;
	private PincodeDO pincodeDO ;
	
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getEmpPhone() {
		return empPhone;
	}
	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}
	public String getEmpEmail() {
		return empEmail;
	}
	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}
	public ProductDO getProductDO() {
		return productDO;
	}
	public void setProductDO(ProductDO productDO) {
		this.productDO = productDO;
	}
	public ConsignmentTypeDO getConsgTypeDO() {
		return consgTypeDO;
	}
	public void setConsgTypeDO(ConsignmentTypeDO consgTypeDO) {
		this.consgTypeDO = consgTypeDO;
	}
	public CNPaperWorksDO getPaperworkDO() {
		return paperworkDO;
	}
	public void setPaperworkDO(CNPaperWorksDO paperworkDO) {
		this.paperworkDO = paperworkDO;
	}
	
	public PincodeDO getPincodeDO() {
		return pincodeDO;
	}
	public void setPincodeDO(PincodeDO pincodeDO) {
		this.pincodeDO = pincodeDO;
	}
	/**
	 * @return the cityDO
	 */
	public CityDO getCityDO() {
		return cityDO;
	}
	/**
	 * @param cityDO the cityDO to set
	 */
	public void setCityDO(CityDO cityDO) {
		this.cityDO = cityDO;
	}
	/**
	 * @return the serviceRelatedId
	 */
	public Integer getServiceRelatedId() {
		return serviceRelatedId;
	}
	/**
	 * @param serviceRelatedId the serviceRelatedId to set
	 */
	public void setServiceRelatedId(Integer serviceRelatedId) {
		this.serviceRelatedId = serviceRelatedId;
	}
	

}
