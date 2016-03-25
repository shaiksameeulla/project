/**
 * 
 */
package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.business.CustomerTypeDO;

/**
 * @author prmeher
 *
 */
public class BcunProductCustomerTypeMapDO extends CGMasterDO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6846774274668139812L;
	private Integer productCustomerTypeId;
	private ProductDO productDO;
	private CustomerTypeDO customerTypeDO;
	/**
	 * @return the productCustomerTypeId
	 */
	public Integer getProductCustomerTypeId() {
		return productCustomerTypeId;
	}
	/**
	 * @param productCustomerTypeId the productCustomerTypeId to set
	 */
	public void setProductCustomerTypeId(Integer productCustomerTypeId) {
		this.productCustomerTypeId = productCustomerTypeId;
	}
	/**
	 * @return the productDO
	 */
	public ProductDO getProductDO() {
		return productDO;
	}
	/**
	 * @param productDO the productDO to set
	 */
	public void setProductDO(ProductDO productDO) {
		this.productDO = productDO;
	}
	/**
	 * @return the customerTypeDO
	 */
	public CustomerTypeDO getCustomerTypeDO() {
		return customerTypeDO;
	}
	/**
	 * @param customerTypeDO the customerTypeDO to set
	 */
	public void setCustomerTypeDO(CustomerTypeDO customerTypeDO) {
		this.customerTypeDO = customerTypeDO;
	}
	
	
}
