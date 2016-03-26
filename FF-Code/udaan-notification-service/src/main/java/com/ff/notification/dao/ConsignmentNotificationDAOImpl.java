package com.ff.notification.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.masters.ConsignmentStatusSmsDO;
import com.ff.domain.masters.NotificationConfigDO;

public class ConsignmentNotificationDAOImpl extends CGBaseDAO implements ConsignmentNotificationDAO {

	private static final Logger logger = LoggerFactory.getLogger(ConsignmentNotificationDAOImpl.class);
	@Override
	public ConsignmentDO getCnDetailsByCnNo(String cnNo) {
		logger.debug("ConsignmentNotificationDAOImpl::getCnDetailsByCnNo::start===>");
		String queryName = "getConsgDetailsByCn";
		List<ConsignmentDO> cnList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, "consgNumber", cnNo);
		if(cnList == null || cnList.isEmpty())
			return null;
		else
			logger.debug("ConsignmentNotificationDAOImpl::getCnDetailsByCnNo::End===>");
			return cnList.get(0);
		
	}

	@Override
	public CustomerDO getCustomerById(int custId) {
		logger.debug("ConsignmentNotificationDAOImpl::getCustomerById::start===>");
		String queryName = "getCustomer";
		List<CustomerDO> cnList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, "customerId", custId);
		if(cnList == null || cnList.isEmpty())
			return null;
		else
			logger.debug("ConsignmentNotificationDAOImpl::getCustomerById::End===>");
			return cnList.get(0);
	}
	
	@Override
	public DeliveryDetailsDO getDeliveryDetailsByCn(String cnNo)  {
		logger.debug("ConsignmentNotificationDAOImpl::getDeliveryDetailsByCn::start===>");
//		/getDrsDtlsByConsgNo,consgNo
		String queryName = "getDrsDtlsByConsgNo";
		List<DeliveryDetailsDO> cnList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, "consgNo", cnNo);
		if(cnList == null || cnList.isEmpty())
			return null;
		/*int count = 0;
		for(DeliveryDetailsDO delDetails : cnList) {
			logger.debug("ConsignmentNotificationDAOImpl::getDeliveryDetailsByCn::getDeliveryDetailId"+delDetails.getDeliveryDetailId());
			logger.debug("ConsignmentNotificationDAOImpl::getDeliveryDetailsByCn::"+delDetails.getReasonDO().getReasonName());
			logger.debug("ConsignmentNotificationDAOImpl::getDeliveryDetailsByCn::"+count);
			count++;
		}*/
		logger.debug("ConsignmentNotificationDAOImpl::getDeliveryDetailsByCn::End===>");
		return cnList.get(0);
	}
	@Override
	public void getSaveConsgStatusOfSmsDetails(ConsignmentStatusSmsDO consStatus) {
		logger.debug("ConsignmentNotificationDAOImpl::getSaveConsgStatusOfSmsDetails::start===>");
		 getHibernateTemplate().saveOrUpdate(consStatus);
		 logger.debug("ConsignmentNotificationDAOImpl::getSaveConsgStatusOfSmsDetails::End===>");
	}



	@Override
	public List<Integer> getCustomersByProductAndStatus(String product, String cnStatus) {
		logger.debug("ConsignmentNotificationDAOImpl::getCustomersByProductAndStatus::start===>");
		String hql = "getNotificationConfigByProductNStatus";
		String[] param = {"consgSeries", "consgStatus"};
		Object[] values = {product, cnStatus};
		
		 List<Integer> customers = getHibernateTemplate().findByNamedQueryAndNamedParam(hql, param, values);
		 logger.debug("ConsignmentNotificationDAOImpl::getCustomersByProductAndStatus::End===>");
		 return customers;
	}

	@Override
	public List< NotificationConfigDO> getNotificationDetailsByProductNStatus(String product, String cnStatus) {
		logger.debug("ConsignmentNotificationDAOImpl::getNotificationDetailsByProductNStatus::start===>");
		String hql = "getNotificationConfigByProductNStatus";
		String[] param = {"consgSeries", "consgStatus"};
		Object[] values = {product, cnStatus};
		
		 List<NotificationConfigDO> customers = getHibernateTemplate().findByNamedQueryAndNamedParam(hql, param, values);
		 logger.debug("ConsignmentNotificationDAOImpl::getNotificationDetailsByProductNStatus::End===>");
		 return customers;
	}

	@Override
	public List<NotificationConfigDO> getNotificationConfig() {
		logger.debug("ConsignmentNotificationDAOImpl::getNotificationConfig::start===>");
		String hql = "getNotificationConfig";
		List<NotificationConfigDO> customers = getHibernateTemplate().findByNamedQuery(hql);
		logger.debug("ConsignmentNotificationDAOImpl::getNotificationConfig::end===>");
		return customers;
	}

	@Override
	public BookingDO getBookingDetailsByCnNo(String cnNumber) {
		logger.debug("ConsignmentNotificationDAOImpl::getBookingDetailsByCnNo::start===>");
		BookingDO booking = null;
		// getBookingDtlsByConsgNo, consgNumber
		List<BookingDO> bookings = getHibernateTemplate().findByNamedQueryAndNamedParam("getBookingDtlsByConsgNo", "consgNumber", cnNumber);
		logger.debug("ConsignmentNotificationDAOImpl::getBookingDetailsByCnNo::end===>");
		return booking;
	}
	@Override
	public ConsignmentStatusSmsDO getConsignmentStatusSmsDeatails(String mobileNumber,String consgNumber,
			String consgStatus,String drsNumber) {
		logger.debug("ConsignmentNotificationDAOImpl::getConsignmentStatusSmsDeatails::start===>");
		String hql = "getConsignmentStatusSmsDetails";
		String[] param = {"drsNo","consgStatus","mobNumber", "consgNo"};
		String[] values = {drsNumber,consgStatus,mobileNumber, consgNumber};
		List<ConsignmentStatusSmsDO>  consgDetails= getHibernateTemplate().findByNamedQueryAndNamedParam(hql,param,values);
		
		if(consgDetails == null || consgDetails.isEmpty())
			return null;
		else
			logger.debug("ConsignmentNotificationDAOImpl::getConsignmentStatusSmsDeatails::end===>");
			return consgDetails.get(0);
			
	}
	
	@Override
	public DeliveryDetailsDO getdrsNumberByConsgNo(String consgNo) {
		logger.debug("ConsignmentNotificationDAOImpl::getdrsNumberByConsgNo::start===>");
		String hql = "drsNumberByConsignmentNumber";
		List<DeliveryDetailsDO>  drsDetails= getHibernateTemplate().findByNamedQueryAndNamedParam(hql,"consignmentNumber",consgNo);
		
		if(drsDetails == null || drsDetails.isEmpty())
			return null;
		else
			logger.debug("ConsignmentNotificationDAOImpl::getConsignmentStatusSmsDeatails::end===>");
			return drsDetails.get(0);
		
	}
	
	
	@Override
	public List<NotificationConfigDO>  getNotificationConfigByCustomer(Integer cnCustomer, String product, String status) {
		logger.debug("ConsignmentNotificationDAOImpl::getNotificationConfigByCustomer::start===>");
		List<NotificationConfigDO> configuration= null;
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		try {
			Criteria criteria = session.createCriteria(NotificationConfigDO.class,"nc");
			criteria.add(Restrictions.eq("nc.recordStatus", "A"));
			criteria.add(Restrictions.eq("nc.consgStatus", status));
			criteria.add(Restrictions.eq("nc.product.consgSeries", product));
			/*if(cnCustomer != null)
				criteria.add(Restrictions.eq("nc.customerId", cnCustomer));*/
			criteria.addOrder(Order.desc("nc.applicableToAll"));//applicableToAll desc
			configuration = criteria.list();
		} finally {
			session.flush();
			session.close();
		}
		logger.debug("ConsignmentNotificationDAOImpl::getNotificationConfigByCustomer::End===>");
		return configuration;
	}

	

	
}
