package com.ff.admin.complaints.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.complaints.CriticalComplaintTO;
import com.ff.domain.complaints.ServiceRequestComplaintDO;
import com.ff.domain.complaints.ServiceRequestDO;

/**
 * @author hkansagr
 */
public class CriticalComplaintDAOImpl extends CGBaseDAO implements
		CriticalComplaintDAO {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CriticalComplaintDAOImpl.class);

	@Override
	public void saveOrUpdateCriticalComplaint(ServiceRequestComplaintDO domain)
			throws CGSystemException {
		LOGGER.trace("CriticalComplaintDAOImpl :: saveOrUpdateCriticalComplaint() :: START");
		try {
			getHibernateTemplate().saveOrUpdate(domain);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in CriticalComplaintDAOImpl :: saveOrUpdateCriticalComplaint() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("CriticalComplaintDAOImpl :: saveOrUpdateCriticalComplaint() :: END");
	}

	@SuppressWarnings("unchecked")
	@Override
	public ServiceRequestDO getServiceRequestDtls(CriticalComplaintTO to)
			throws CGSystemException {
		LOGGER.trace("CriticalComplaintDAOImpl :: getServiceRequestDtls() :: START");
		ServiceRequestDO serviceRequestDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(ServiceRequestDO.class);
			cr.add(Restrictions.eq(
					ComplaintsCommonConstants.PARAM_SERVICE_REQUEST_NO,
					to.getComplaintNo()));
			List<ServiceRequestDO> serviceRequestDOs = cr.list();
			if (!CGCollectionUtils.isEmpty(serviceRequestDOs)) {
				serviceRequestDO = serviceRequestDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in CriticalComplaintDAOImpl :: getServiceRequestDtls() :: ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("CriticalComplaintDAOImpl :: getServiceRequestDtls() :: END");
		return serviceRequestDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ServiceRequestComplaintDO searchCriticalComplaint(
			CriticalComplaintTO to) throws CGSystemException {
		LOGGER.trace("CriticalComplaintDAOImpl :: searchCriticalComplaint() :: START");
		ServiceRequestComplaintDO serviceRequestComplaintDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(ServiceRequestComplaintDO.class,
					"complaint");
			cr.createAlias("complaint.serviceRequestDO", "serviceRequest");
			cr.add(Restrictions.eq("serviceRequest.serviceRequestNo",
					to.getComplaintNo()));
			List<ServiceRequestComplaintDO> serviceRequestComplaintDOs = cr
					.list();
			if (!CGCollectionUtils.isEmpty(serviceRequestComplaintDOs)) {
				serviceRequestComplaintDO = serviceRequestComplaintDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in CriticalComplaintDAOImpl :: searchCriticalComplaint() ::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("CriticalComplaintDAOImpl :: searchCriticalComplaint() :: END");
		return serviceRequestComplaintDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isCriticalComplaintExist(String complaintNo)
			throws CGSystemException {
		LOGGER.trace("CriticalComplaintDAOImpl :: isCriticalComplaintExist() :: START");
		boolean result = Boolean.FALSE;
		try {
			List<ServiceRequestComplaintDO> serviceRequestComplaintDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ComplaintsCommonConstants.QRY_IS_CRITICAL_COMPLAINT_EXIST,
							ComplaintsCommonConstants.PARAM_COMPLAINT_NO,
							complaintNo);
			if (!CGCollectionUtils.isEmpty(serviceRequestComplaintDOs)) {
				result = Boolean.TRUE;
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in CriticalComplaintDAOImpl :: searchCriticalComplaint() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("CriticalComplaintDAOImpl :: isCriticalComplaintExist() :: END");
		return result;
	}

}
