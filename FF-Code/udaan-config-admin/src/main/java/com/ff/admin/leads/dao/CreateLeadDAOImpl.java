package com.ff.admin.leads.dao;

import java.util.List;

import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.admin.leads.constants.LeadCommonConstants;
import com.ff.domain.leads.LeadDO;
import com.ff.domain.umc.EmployeeUserDO;

public class CreateLeadDAOImpl extends CGBaseDAO implements CreateLeadDAO {

	private final static Logger LOGGER= LoggerFactory.getLogger(CreateLeadDAOImpl.class);

	/*@Override
	public boolean saveLead(LeadDO leadDO) throws CGSystemException {
		LOGGER.trace("CreateLeadDAOImpl :: saveLead() :: Start --------> ::::::");
		boolean isSaved = Boolean.FALSE;
		try{
			getHibernateTemplate().saveOrUpdate(leadDO);
			isSaved = Boolean.TRUE;
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::CreateLeadDAOImpl::saveLead() :: " + e);
			throw new CGSystemException(e);
		}		
		LOGGER.trace("CreateLeadDAOImpl :: saveLead() :: End --------> ::::::");
		return isSaved;

	
	}*/
	
	@Override
	public boolean saveLead(LeadDO leadDO) throws CGSystemException {
		LOGGER.trace("CreateLeadDAOImpl :: saveLead() :: Start --------> ::::::");
		boolean isSaved = Boolean.FALSE;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.merge(leadDO);
			tx.commit();
			isSaved = Boolean.TRUE;
		} catch (Exception e) {
			tx.rollback();
			LOGGER.error("Exception Occured in::CreateLeadDAOImpl::saveLead() :: "
					+ e);
			throw new CGSystemException(e);
		} finally {
			session.flush();
			closeSession(session);
		}
		LOGGER.trace("CreateLeadDAOImpl :: saveLead() :: End --------> ::::::");
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
}
