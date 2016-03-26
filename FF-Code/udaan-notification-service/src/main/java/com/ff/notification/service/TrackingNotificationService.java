package com.ff.notification.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SmsSenderTO;
import com.ff.domain.delivery.DeliveryDetailsDO;

public interface TrackingNotificationService {
	
	
	/**
	 * @param consgNo
	 * @param pendigReason
	 * @param delBrContactNo
	 * @return
	 * @throws Exception 
	 */
	public String getPendingSMSTextByTemplate(String consgNo, String pendigReason, String delBrContactNo) throws Exception ;
	
	/**
	 * @param receiverName
	 * @param deliveryDate
	 * @return
	 */
	public String getDeliveredSMSTextByTemplate(String receiverName, String deliveryDate, String consg) throws Exception;
	
	/**
	 * @param cn
	 * @return
	 */
	public DeliveryDetailsDO getDeliveryDetailsByCn(String cn);
	
	
	
	
	boolean sendSMSNotification(String cnNumber, String mobile)
			throws CGBusinessException, CGSystemException;
	boolean sendEmailNotification(String cnNumber, String mobile) throws CGBusinessException, CGSystemException;
	String getTransitStatusTextByTemplate() throws Exception;

	public void sendSMSNotification(SmsSenderTO smsSenderTO) throws CGBusinessException, CGSystemException;

}
