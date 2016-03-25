package com.capgemini.lbs.framework.dao.sms;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.domain.SmsDO;
import com.capgemini.lbs.framework.exception.CGSystemException;

// TODO: Auto-generated Javadoc
/**
 * The Class SmsSenderDAOImpl.
 * 
 * @author narmdr
 */
public class SmsSenderDAOImpl extends CGBaseDAO implements SmsSenderDAO{

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(SmsSenderDAOImpl.class);

	/* (non-Javadoc)
	 * @see com.capgemini.lbs.framework.dao.sms.SmsSenderDAO#getConfigurableParamMapByNames(java.util.List)
	 */
	@Override
	public List<?> getConfigurableParamMapByNames(List<String> paramNames)
			throws CGSystemException {
		List<?> configParams = null;
		LOGGER.debug("SmsSenderDAOImpl :: getConfigurableParamMapByNames() :: Start --------> ::::::");
		Session session = null;
		try {
			session = createSession();
			Query qry = session
					.getNamedQuery("getConfigurableParamMapByNames");
			qry.setParameterList("paramNames", paramNames);
			
			configParams = qry.list();

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::SmsSenderDAOImpl::getConfigurableParamMapByNames() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("SmsSenderDAOImpl :: getConfigurableParamMapByNames() :: End --------> ::::::");
		return configParams;
	}

	/* (non-Javadoc)
	 * @see com.capgemini.lbs.framework.dao.sms.SmsSenderDAO#saveSmsDetails(com.capgemini.lbs.framework.domain.SmsDO)
	 */
	@Override
	public SmsDO saveSmsDetails(SmsDO smsDO) throws CGSystemException {
		LOGGER.debug("SmsSenderDAOImpl :: saveSmsDetails() :: Start --------> ::::::");
		SmsDO smsDO2 = null;
		try {
			getHibernateTemplate().saveOrUpdate(smsDO);
			smsDO2 = smsDO;
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::SmsSenderDAOImpl::saveSmsDetails() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("SmsSenderDAOImpl :: saveSmsDetails() :: End --------> ::::::");
		return smsDO2;
	}
	
}
