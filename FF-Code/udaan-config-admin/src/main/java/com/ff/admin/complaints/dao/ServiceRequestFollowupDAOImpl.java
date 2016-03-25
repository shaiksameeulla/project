/**
 * 
 */
package com.ff.admin.complaints.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.ff.domain.complaints.ServiceRequestDO;
import com.ff.domain.complaints.ServiceRequestFollowupDO;

/**
 * @author prmeher
 * 
 */
public class ServiceRequestFollowupDAOImpl extends CGBaseDAO implements
		ServiceRequestFollowupDAO {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ServiceRequestFollowupDAOImpl.class);

	@Override
	public Boolean saveOrUpdateFollowup(
			ServiceRequestFollowupDO serviceRequestFollowupDO)
			throws CGSystemException {
		LOGGER.trace("ServiceRequestFollowupDAOImpl :: saveOrUpdateFollowup :: START");
		boolean result = Boolean.FALSE;
		try {
			getHibernateTemplate().merge(serviceRequestFollowupDO);
			result = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ServiceRequestFollowupDAOImpl :: saveOrUpdateFollowup() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("ServiceRequestFollowupDAOImpl :: saveOrUpdateFollowup :: END");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceRequestFollowupDO> getComplaintFollowupDetails(
			Integer complaintId) throws CGSystemException {
		LOGGER.debug("ServiceRequestFollowupDAOImpl::getComplaintFollowupDetails ::START");
		List<ServiceRequestFollowupDO> followupDOs = null;
		Session session = null;
		try {
			Criteria cr = null;
			session = getHibernateTemplate().getSessionFactory().openSession();
			cr = session.createCriteria(ServiceRequestFollowupDO.class,
					"followup");
			cr.createAlias("followup.serviceRequestDO", "serviceRequestDO");
			cr.add(Restrictions.eq("serviceRequestDO.serviceRequestId",
					complaintId));
			cr.addOrder(Order.desc("followup.followUpDate"));
			followupDOs = cr.list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR ::ServiceRequestFollowupDAOImpl::getComplaintFollowupDetails ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.debug("ServiceRequestFollowupDAOImpl::getComplaintFollowupDetails ::END");
		return followupDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ServiceRequestDO getComplaintDtlsByComplaintId(Integer complaintId)
			throws CGSystemException {
		LOGGER.debug("ServiceRequestFollowupDAOImpl::getComplaintDtlsByComplaintId ::START");
		List<ServiceRequestDO> serviceRequestDO = null;
		ServiceRequestDO serviceReq = null;
		Session session = null;
		try {
			Criteria cr = null;
			session = getHibernateTemplate().getSessionFactory().openSession();
			cr = session.createCriteria(ServiceRequestDO.class,
					"serviceRequest");
			cr.add(Restrictions.eq("serviceRequest.serviceRequestId",
					complaintId));
			serviceRequestDO = cr.list();
			if(!CGCollectionUtils.isEmpty(serviceRequestDO)){
				serviceReq = serviceRequestDO.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR ::ServiceRequestFollowupDAOImpl::getComplaintDtlsByComplaintId ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.debug("ServiceRequestFollowupDAOImpl::getComplaintDtlsByComplaintId ::END");
		return serviceReq;
	}

	@Override
	public Boolean updateServiceRequest(ServiceRequestDO complaint)
			throws CGSystemException {
		LOGGER.trace("ServiceRequestFollowupDAOImpl :: updateServiceRequest :: START");
		boolean result = Boolean.FALSE;
		try {
			getHibernateTemplate().merge(complaint);
			result = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ServiceRequestFollowupDAOImpl :: updateServiceRequest() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("ServiceRequestFollowupDAOImpl :: updateServiceRequest :: END");
		return result;
	}

}
