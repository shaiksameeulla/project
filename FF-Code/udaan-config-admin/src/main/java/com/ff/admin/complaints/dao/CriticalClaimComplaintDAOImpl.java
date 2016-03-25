/**
 * 
 */
package com.ff.admin.complaints.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.complaints.CriticalClaimComplaintTO;
import com.ff.domain.complaints.ServiceRequestComplaintDO;
import com.ff.domain.complaints.ServiceRequestCriticalComplaintClaimDO;


/**
 * @author cbhure
 *
 */
public class CriticalClaimComplaintDAOImpl extends CGBaseDAO implements
CriticalClaimComplaintDAO {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ServiceRequestFollowupDAOImpl.class);

	@Override
	public Boolean saveCriticalClaimComplaint(
			ServiceRequestCriticalComplaintClaimDO criticalComplaintDO)
			throws CGSystemException {
		LOGGER.trace("CriticalClaimComplaintDAOImpl :: saveCriticalClaimComplaint :: START");
		
		boolean result = Boolean.FALSE;
		try {
			getHibernateTemplate().merge(criticalComplaintDO);
			result = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ServiceRequestFollowupDAOImpl :: saveOrUpdateFollowup() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("CriticalClaimComplaintDAOImpl :: saveCriticalClaimComplaint :: END");
		return result;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public ServiceRequestCriticalComplaintClaimDO getCriticalClaimComplaintDtls(
			CriticalClaimComplaintTO criticalComplaintTO) throws CGSystemException {
		LOGGER.trace("CriticalClaimComplaintDAOImpl :: getCriticalClaimComplaintDtls :: START");
		List<ServiceRequestCriticalComplaintClaimDO> criticalClaimComplaintDOs = null;
		ServiceRequestCriticalComplaintClaimDO criticalClaimComplaintDO = null; 
		try{
		String params[] = { ComplaintsCommonConstants.COMPLAINT_NO };
		Object values[] = { criticalComplaintTO.getComplaintNo() };
		criticalClaimComplaintDOs = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						ComplaintsCommonConstants.QRY_PARAM_GET_CRITICAL_CLAIM_COMPLAINT_DTLS,
						params, values);
		if(!StringUtil.isEmptyColletion(criticalClaimComplaintDOs)){
			criticalClaimComplaintDO = criticalClaimComplaintDOs.get(0);
		}
		}catch(Exception e){
			LOGGER.error("ERROR :: CriticalClaimComplaintDAOImpl :: getCriticalClaimComplaintDtls() ::",e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("CriticalClaimComplaintDAOImpl :: getCriticalClaimComplaintDtls :: END");
		return criticalClaimComplaintDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ServiceRequestComplaintDO getCriticalComplaintDtls(String complaintNo) throws CGSystemException {
		LOGGER.trace("CriticalClaimComplaintDAOImpl :: getCriticalComplaintDtls :: START");
		List<ServiceRequestComplaintDO> criticalComplaintDOs = null;
		ServiceRequestComplaintDO criticalComplaintDO = null; 
		try{
		String params[] = { ComplaintsCommonConstants.PARAM_COMPLAINT_NO };
		Object values[] = { complaintNo };
		criticalComplaintDOs = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						ComplaintsCommonConstants.QRY_IS_CRITICAL_COMPLAINT_EXIST,
						params, values);
		if(!StringUtil.isEmptyColletion(criticalComplaintDOs)){
			criticalComplaintDO = criticalComplaintDOs.get(0);
		}
		}catch(Exception e){
			LOGGER.error("ERROR :: CriticalClaimComplaintDAOImpl :: getCriticalComplaintDtls() ::",e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("CriticalClaimComplaintDAOImpl :: getCriticalComplaintDtls :: END");
		return criticalComplaintDO;
	}

}
