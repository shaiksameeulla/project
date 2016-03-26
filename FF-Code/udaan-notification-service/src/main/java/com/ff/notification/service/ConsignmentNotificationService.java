package com.ff.notification.service;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.queueUtils.NotificationQueueContentTO;
import com.ff.domain.delivery.DeliveryDetailsDO;
//import com.ff.notification.to.NotificationQueueContentTO;
import com.ff.domain.masters.NotificationConfigDO;

/**
 * @author mohammal
 *
 */
public interface ConsignmentNotificationService {
	
	/*public void postNotification(DeliveryDetailsDO deliveryDetails);
	
	public void postNotification(ConsignmentDO consgDetails);*/
	
	public void postNotification(NotificationQueueContentTO notificationTO);
	/**
	 * @param baseDo
	 * @throws CGBusinessException 
	 */
	public void proceedNotification(CGBaseDO baseDo) throws CGBusinessException;
	
	/**
	 * @param notifyTO
	 */
	public void proceedSMSNotification(NotificationQueueContentTO notifyTO) ;
	/**
	 * @param notifyTO
	 * @return
	 */
	public int getNotificationType(NotificationQueueContentTO notifyTO) ;
	/**
	 * @param consgNo
	 * @return
	 */
	public String getProduct(String consgNo);
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
	 * @param cnNo
	 * @param statu
	 * @return
	 */
	public String getSMSText(String cnNo, String template);
	
	public String getSMSText(String cnNo, NotificationConfigDO cnNotifyDo);
	
	/**
	 * @param cn
	 * @return
	 */
	public DeliveryDetailsDO getDeliveryDetailsByCn(String cn);
	void sendSMSNotification(String cnNumber, String mobile)
			throws CGBusinessException, CGSystemException;
	void sendEmailNotification(String cnNumber, String mobile) throws CGBusinessException;
	String getTransitStatusTextByTemplate() throws Exception;
	String testStringTemplate(String consignment, String mobile, String status);
}
