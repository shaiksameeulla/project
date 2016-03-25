/**
 * 
 */
package com.ff.admin.complaints.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.domain.complaints.ServiceRequestDO;

/**
 * @author abarudwa
 * 
 */
public class ComplaintsBacklineSummaryDAOImpl extends CGBaseDAO implements
		ComplaintsBacklineSummaryDAO {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ComplaintsBacklineSummaryDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceRequestDO> getComplaintDetailsByServiceRequestNo(
			String serviceRequestNo) throws CGSystemException {
		LOGGER.debug("ComplaintsBacklineSummaryDAOImpl :: getComplaintDetailsByServiceRequestNo() :: Start --------> ::::::");
		List<ServiceRequestDO> serviceRequestDOs = null;
		try {
			serviceRequestDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ComplaintsCommonConstants.QRY_GET_SERVICE_REQUEST_BY_SERVICE_REQUEST_NO,
							new String[] { ComplaintsCommonConstants.SERVICE_REQUEST_NO },
							new Object[] { serviceRequestNo });
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::ComplaintsBacklineSummaryDAOImpl::getComplaintDetailsByServiceRequestNo() :: ",
					e);
		}
		LOGGER.debug("ComplaintsBacklineSummaryDAOImpl :: getComplaintDetailsByServiceRequestNo() :: End --------> ::::::");

		return serviceRequestDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceRequestDO> getComplaintDetailsByServiceRequestStatus(
			String statusName) throws CGSystemException {
		LOGGER.debug("ComplaintsBacklineSummaryDAOImpl :: getComplaintDetailsByServiceRequestStatus() :: Start --------> ::::::");
		List<ServiceRequestDO> serviceRequestDOs = null;
		try {
			serviceRequestDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ComplaintsCommonConstants.QRY_GET_SERVICE_REQUEST_BY_SERVICE_REQUEST_STATUS,
							new String[] { ComplaintsCommonConstants.SERVICE_REQUEST_STATUS },
							new Object[] { statusName });
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::ComplaintsBacklineSummaryDAOImpl::getComplaintDetailsByServiceRequestStatus() :: ",
					e);
		}
		LOGGER.debug("ComplaintsBacklineSummaryDAOImpl :: getComplaintDetailsByServiceRequestStatus() :: End --------> ::::::");

		return serviceRequestDOs;
	}
	@Override
	public List<ServiceRequestDO> getComplaintDetailsByServiceRequestStatus(
			String statusName,Integer employeeId) throws CGSystemException {
		LOGGER.debug("ComplaintsBacklineSummaryDAOImpl :: getComplaintDetailsByServiceRequestStatus() :: Start --------> ::::::");
		List<ServiceRequestDO> serviceRequestDOs = null;
		try {
			serviceRequestDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ComplaintsCommonConstants.QRY_GET_SERVICEREQUEST_DTLS_BYSTATUS_AND_ASSIGNED_EMP,
							new String[] { ComplaintsCommonConstants.SERVICE_REQUEST_STATUS,ComplaintsCommonConstants.EMPLOYEE_ID },
							new Object[] { statusName,employeeId });
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::ComplaintsBacklineSummaryDAOImpl::getComplaintDetailsByServiceRequestStatus() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ComplaintsBacklineSummaryDAOImpl :: getComplaintDetailsByServiceRequestStatus() :: End --------> ::::::");

		return serviceRequestDOs;
	}
	
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceRequestDO> getComplaintDetailsByUser(Integer employeeId)
			throws CGSystemException {
		LOGGER.debug("ComplaintsBacklineSummaryDAOImpl :: getComplaintDetailsByUser() :: Start --------> ::::::");
		List<ServiceRequestDO> serviceRequestDOs = null;
		try {
			serviceRequestDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ComplaintsCommonConstants.QRY_GET_SERVICE_REQUEST_BY_USER,
							new String[] { ComplaintsCommonConstants.EMPLOYEE_ID },
							new Object[] { employeeId });
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::ComplaintsBacklineSummaryDAOImpl::getComplaintDetailsByUser() :: ",
					e);
		}
		LOGGER.debug("ComplaintsBacklineSummaryDAOImpl :: getComplaintDetailsByUser() :: End --------> ::::::");

		return serviceRequestDOs;
	}

}
