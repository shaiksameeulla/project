package com.ff.notification.dao;

import java.util.List;

import com.ff.domain.booking.BookingDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.masters.ConsignmentStatusSmsDO;
import com.ff.domain.masters.NotificationConfigDO;


public interface ConsignmentNotificationDAO  {
	
	/**
	 * @param cnNo
	 * @return
	 */
	public ConsignmentDO getCnDetailsByCnNo(String cnNo);
	/**
	 * @param custId
	 * @return
	 */
	public CustomerDO getCustomerById(int custId);
	/**
	 * @param cnNo
	 * @return
	 */
	public DeliveryDetailsDO getDeliveryDetailsByCn(String cnNo);
	/**
	 * @param product
	 * @param cnStatus
	 * @return 
	 */
	public List<Integer> getCustomersByProductAndStatus(String product, String cnStatus);
	/**
	 * @return 
	 * 
	 */
	public List<NotificationConfigDO> getNotificationConfig();
	/**
	 * @param cnNumber
	 * @return 
	 */
	public BookingDO getBookingDetailsByCnNo(String cnNumber);
	/**
	 * @param cnCustomer
	 */
	public List<NotificationConfigDO>  getNotificationConfigByCustomer(Integer cnCustomer, String product, String status);
	List<NotificationConfigDO> getNotificationDetailsByProductNStatus(
			String product, String cnStatus);
	public ConsignmentStatusSmsDO getConsignmentStatusSmsDeatails(
			String  mobileNumber,String consgNumber,String consgStatus,String drsNumber);
	public DeliveryDetailsDO getdrsNumberByConsgNo(String consgNo);
	public void getSaveConsgStatusOfSmsDetails(ConsignmentStatusSmsDO consStatus);  
}
