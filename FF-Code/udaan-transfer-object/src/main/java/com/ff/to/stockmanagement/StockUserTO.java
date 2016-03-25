
package com.ff.to.stockmanagement;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class StockUserTO.
 *
 * @author mohammes
 */

public class StockUserTO extends CGBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5947644910712881761L;
	
	/** The stock user id. */
	private Integer stockUserId;
	
	/** The stock user code. */
	private String stockUserCode;
	
	/** The stock user name. */
	private String stockUserName;
	
	/** The stock user office. */
	private String stockUserOffice;
	
	/** The stock user type. */
	private String stockUserType; // The user type should be BA/FR/Employee/BO
	
	
	/**
	 * Gets the stock user type.
	 *
	 * @return the stock user type
	 */
	public String getStockUserType() {
		return stockUserType;
	}
	
	/**
	 * Sets the stock user type.
	 *
	 * @param stockUserType the new stock user type
	 */
	public void setStockUserType(String stockUserType) {
		this.stockUserType = stockUserType;
	}
	
	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/**
	 * Gets the stock user id.
	 *
	 * @return the stock user id
	 */
	public Integer getStockUserId() {
		return stockUserId;
	}
	
	/**
	 * Sets the stock user id.
	 *
	 * @param stockUserId the new stock user id
	 */
	public void setStockUserId(Integer stockUserId) {
		this.stockUserId = stockUserId;
	}
	
	/**
	 * Gets the stock user code.
	 *
	 * @return the stock user code
	 */
	public String getStockUserCode() {
		return stockUserCode;
	}
	
	/**
	 * Sets the stock user code.
	 *
	 * @param stockUserCode the new stock user code
	 */
	public void setStockUserCode(String stockUserCode) {
		this.stockUserCode = stockUserCode;
	}
	
	/**
	 * Gets the stock user name.
	 *
	 * @return the stock user name
	 */
	public String getStockUserName() {
		return stockUserName;
	}
	
	/**
	 * Sets the stock user name.
	 *
	 * @param stockUserName the new stock user name
	 */
	public void setStockUserName(String stockUserName) {
		this.stockUserName = stockUserName;
	}
	
	/**
	 * Gets the stock user office.
	 *
	 * @return the stock user office
	 */
	public String getStockUserOffice() {
		return stockUserOffice;
	}
	
	/**
	 * Sets the stock user office.
	 *
	 * @param stockUserOffice the new stock user office
	 */
	public void setStockUserOffice(String stockUserOffice) {
		this.stockUserOffice = stockUserOffice;
	}
}
