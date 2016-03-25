/**
 * 
 */
package com.ff.complaints;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;
import com.ff.serviceOfferring.ProductTO;

/**
 * @author mohammes
 *
 */
public class ServiceRequestValidationTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2822175761006185176L;

	private String serviceQueryType;
	private Integer pincodeId;
	private Integer cityId;
	private String consignmentType;
	private Integer productId;
	private Double weight;
	private String customerType;
	
	private String queryResult;
	
	private ProductTO productTO;
	
	private CityTO cityTO;
	/**
	 * @return the serviceQueryType
	 */
	public String getServiceQueryType() {
		return serviceQueryType;
	}
	/**
	 * @return the pincodeId
	 */
	public Integer getPincodeId() {
		return pincodeId;
	}
	/**
	 * @return the cityId
	 */
	public Integer getCityId() {
		return cityId;
	}
	/**
	 * @return the consignmentType
	 */
	public String getConsignmentType() {
		return consignmentType;
	}
	/**
	 * @return the productId
	 */
	public Integer getProductId() {
		return productId;
	}
	/**
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}
	/**
	 * @param serviceQueryType the serviceQueryType to set
	 */
	public void setServiceQueryType(String serviceQueryType) {
		this.serviceQueryType = serviceQueryType;
	}
	/**
	 * @param pincodeId the pincodeId to set
	 */
	public void setPincodeId(Integer pincodeId) {
		this.pincodeId = pincodeId;
	}
	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	/**
	 * @param consignmentType the consignmentType to set
	 */
	public void setConsignmentType(String consignmentType) {
		this.consignmentType = consignmentType;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}
	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	/**
	 * @return the queryResult
	 */
	public String getQueryResult() {
		return queryResult;
	}
	/**
	 * @param queryResult the queryResult to set
	 */
	public void setQueryResult(String queryResult) {
		this.queryResult = queryResult;
	}
	/**
	 * @return the productTO
	 */
	public ProductTO getProductTO() {
		return productTO;
	}
	/**
	 * @return the cityTO
	 */
	public CityTO getCityTO() {
		return cityTO;
	}
	/**
	 * @param cityTO the cityTO to set
	 */
	public void setCityTO(CityTO cityTO) {
		this.cityTO = cityTO;
	}
	/**
	 * @param productTO the productTO to set
	 */
	public void setProductTO(ProductTO productTO) {
		this.productTO = productTO;
	}
	
	
}
