/**
 * 
 */
package com.capgemini.lbs.framework.domain;

/**
 * @author mohammes
 *
 */
public class CGBcunInbundDO extends CGBaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1019273873742616864L;
	
	private Number pojoPrimarykey;
	private String businesskey;
	/**
	 * @return the pojoPrimarykey
	 */
	public Number getPojoPrimarykey() {
		return pojoPrimarykey;
	}
	/**
	 * @param pojoPrimarykey the pojoPrimarykey to set
	 */
	public void setPojoPrimarykey(Number pojoPrimarykey) {
		this.pojoPrimarykey = pojoPrimarykey;
	}
	/**
	 * @return the businesskey
	 */
	public String getBusinesskey() {
		return businesskey;
	}
	/**
	 * @param businesskey the businesskey to set
	 */
	public void setBusinesskey(String businesskey) {
		this.businesskey = businesskey;
	}

}
