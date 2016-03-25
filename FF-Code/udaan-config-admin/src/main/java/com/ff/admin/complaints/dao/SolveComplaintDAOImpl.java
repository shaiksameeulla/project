package com.ff.admin.complaints.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.domain.complaints.ServiceRequestDO;

/**
 * @author abarudwa
 * 
 */
public class SolveComplaintDAOImpl extends CGBaseDAO implements
		SolveComplaintDAO {
	private final static Logger LOGGER = LoggerFactory.getLogger(SolveComplaintDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public ServiceRequestDO getComplaintDetailsByServiceRequestNo(
			String serviceRequestNo) throws CGSystemException {
		LOGGER.debug("SolveComplaintDAOImpl :: getComplaintDetailsByServiceRequestNo() :: Start --------> ::::::");
		ServiceRequestDO serviceRequestDO = null;
		List<ServiceRequestDO> serviceRequestDOs = null;
		try{
			serviceRequestDOs=getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ComplaintsCommonConstants.QRY_GET_SERVICE_REQUEST_BY_SERVICE_REQUEST_NO,
							new String[] {ComplaintsCommonConstants.SERVICE_REQUEST_NO },
							new Object[] { serviceRequestNo });
			
			if(!CGCollectionUtils.isEmpty(serviceRequestDOs)){
				serviceRequestDO = serviceRequestDOs.get(0);
			}
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::SolveComplaintDAOImpl::getComplaintDetailsByServiceRequestNo() :: " , e);
		}
		LOGGER.debug("SolveComplaintDAOImpl :: getComplaintDetailsByServiceRequestNo() :: End --------> ::::::");
		
		return serviceRequestDO;
	}

	@Override
	public ServiceRequestDO saveServiceRequestDetails(
			ServiceRequestDO serviceRequestDO) throws CGSystemException {
		LOGGER.debug("SolveComplaintDAOImpl :: saveServiceRequestDetails() :: Start --------> ::::::");
		try{
			
			getHibernateTemplate().merge(serviceRequestDO);
		}
		
		catch(Exception e){
			LOGGER.error("Exception Occured in::SolveComplaintDAOImpl::saveServiceRequestDetails() :: " + e);
			throw new CGSystemException(e);
		}	
		
		LOGGER.debug("SolveComplaintDAOImpl :: saveServiceRequestDetails() :: End --------> ::::::");
	
		return serviceRequestDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ServiceRequestDO getComplaintDetailsByServiceRequestId(
			Integer serviceRequestId) throws CGSystemException {
		LOGGER.debug("SolveComplaintDAOImpl :: getComplaintDetailsByServiceRequestId() :: Start --------> ::::::");
		ServiceRequestDO serviceRequestDO = null;
		List<ServiceRequestDO> serviceRequestDOs = null;
		try{
			serviceRequestDOs=getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ComplaintsCommonConstants.QRY_GET_SERVICE_REQUEST_BY_SERVICE_REQUEST_ID,
							new String[] {ComplaintsCommonConstants.SERVICE_REQUEST_ID },
							new Object[] { serviceRequestId });
			
			if(!CGCollectionUtils.isEmpty(serviceRequestDOs)){
				serviceRequestDO = serviceRequestDOs.get(0);
			}
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::SolveComplaintDAOImpl::getComplaintDetailsByServiceRequestId() :: " , e);
		}
		LOGGER.debug("SolveComplaintDAOImpl :: getComplaintDetailsByServiceRequestId() :: End --------> ::::::");
		
		return serviceRequestDO;
	}
}
