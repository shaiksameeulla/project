/**
 * 
 */
package com.ff.domain.delivery;

import java.util.Calendar;
import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.capgemini.lbs.framework.utils.StringUtil;

/**
 * @author mohammes
 *
 */
public class DeliveryNavigatorDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6337256932696896236L;

	/** The delivery navigator id. */
	private Long deliveryNavigatorId;
	
	/** The drs number. */
	private String drsNumber;
	
	/** The created screen code. */
	private String createdScreenCode;

	
	/** The from screen code. */
	private String fromScreenCode;
	
	/** The drs to url. */
	private String navigateToUrl;
	
	private Date transactionCreateDate = Calendar.getInstance().getTime();
	private Date transactionModifiedDate = Calendar.getInstance().getTime();

	/**
	 * @return the drsNumber
	 */
	public String getDrsNumber() {
		return drsNumber;
	}

	/**
	 * @param drsNumber the drsNumber to set
	 */
	public void setDrsNumber(String drsNumber) {
		this.drsNumber = drsNumber;
		if(!StringUtil.isStringEmpty(this.drsNumber)){
			this.drsNumber=this.drsNumber.toUpperCase();
		}
	}

	

	/**
	 * @return the createdScreenCode
	 */
	public String getCreatedScreenCode() {
		return createdScreenCode;
	}

	/**
	 * @param createdScreenCode the createdScreenCode to set
	 */
	public void setCreatedScreenCode(String createdScreenCode) {
		this.createdScreenCode = createdScreenCode;
	}

	

	/**
	 * @return the navigateToUrl
	 */
	public String getNavigateToUrl() {
		return navigateToUrl;
	}

	

	/**
	 * @param navigateToUrl the navigateToUrl to set
	 */
	public void setNavigateToUrl(String navigateToUrl) {
		this.navigateToUrl = navigateToUrl;
	}

	/**
	 * @return the fromScreenCode
	 */
	public String getFromScreenCode() {
		return fromScreenCode;
	}

	/**
	 * @param fromScreenCode the fromScreenCode to set
	 */
	public void setFromScreenCode(String fromScreenCode) {
		this.fromScreenCode = fromScreenCode;
	}

	/**
	 * @return the deliveryNavigatorId
	 */
	public Long getDeliveryNavigatorId() {
		return deliveryNavigatorId;
	}

	/**
	 * @param deliveryNavigatorId the deliveryNavigatorId to set
	 */
	public void setDeliveryNavigatorId(Long deliveryNavigatorId) {
		this.deliveryNavigatorId = deliveryNavigatorId;
	}

	/**
	 * @return the transactionCreateDate
	 */
	public Date getTransactionCreateDate() {
		return transactionCreateDate;
	}

	/**
	 * @return the transactionModifiedDate
	 */
	public Date getTransactionModifiedDate() {
		return transactionModifiedDate;
	}

	/**
	 * @param transactionCreateDate the transactionCreateDate to set
	 */
	public void setTransactionCreateDate(Date transactionCreateDate) {
		this.transactionCreateDate = transactionCreateDate;
	}

	/**
	 * @param transactionModifiedDate the transactionModifiedDate to set
	 */
	public void setTransactionModifiedDate(Date transactionModifiedDate) {
		this.transactionModifiedDate = transactionModifiedDate;
	}

	
}
