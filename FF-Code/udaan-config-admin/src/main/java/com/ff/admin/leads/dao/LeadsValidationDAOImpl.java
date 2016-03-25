package com.ff.admin.leads.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.capgemini.lbs.framework.dao.CGBaseDAO;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.leads.constants.LeadCommonConstants;
import com.ff.domain.leads.LeadDO;
import com.ff.domain.umc.EmployeeUserDO;

public class LeadsValidationDAOImpl extends CGBaseDAO implements LeadsValidationDAO{

	private final static Logger LOGGER= LoggerFactory.getLogger(LeadsValidationDAOImpl.class);
	@Override
	public boolean approveLead(LeadDO leadDO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("LeadsValidationDAOImpl :: approveLead() :: Start --------> ::::::");
		boolean isSaved = Boolean.FALSE;
		try{
			getHibernateTemplate().saveOrUpdate(leadDO);
			isSaved = Boolean.TRUE;
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::LeadsValidationDAOImpl::approveLead() :: " + e);
			throw new CGSystemException(e);
		}		
		LOGGER.trace("LeadsValidationDAOImpl :: approveLead() :: End --------> ::::::");
		return isSaved;

	
	}
	
	@SuppressWarnings("unchecked")
	public List<EmployeeUserDO> getSalesExecutive(final Integer officeId)
			throws CGSystemException {
		LOGGER.trace("CreateLeadDAOImpl :: getSalesExecutive() :: Start --------> ::::::");
		List<EmployeeUserDO> employeeUserDOList = null;
		try{
			employeeUserDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							LeadCommonConstants.QRY_GET_EMPLOYEES_DTLS_BY_DEPARTMENT_TYPE_AND_OFFICE_ID,
							new String[] { LeadCommonConstants.DEPARTMENT_NAME,LeadCommonConstants.OFFICEID  },
							new Object[] { LeadCommonConstants.DEPARTMENT_NAME_TYPE,officeId });
			
			}catch(Exception e){
			LOGGER.error("Exception Occured in::CreateLeadDAOImpl::getSalesExecutive() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("CreateLeadDAOImpl :: getSalesExecutive() :: End --------> ::::::");
		return employeeUserDOList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LeadDO getLeadDetails(final String leadNumber) throws CGSystemException {
		LOGGER.trace("LeadCommonDAOImpl :: getLeadDetails() :: Start --------> ::::::");
		LeadDO leadDO =null;
		Session session = null;
		session = createSession();
		try{
		List<LeadDO>	leadDOs = (List<LeadDO>) session.createCriteria(
					LeadDO.class).add(Restrictions.eq("leadNumber", leadNumber)).list();
		if(!StringUtil.isEmptyColletion(leadDOs)){
			leadDO=leadDOs.get(0);
			}
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::LeadCommonDAOImpl::getLeadDetails() :: "
					+ e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		LOGGER.trace("LeadCommonDAOImpl :: getLeadDetails() :: End --------> ::::::");
		
		return leadDO;
	}
	
}
