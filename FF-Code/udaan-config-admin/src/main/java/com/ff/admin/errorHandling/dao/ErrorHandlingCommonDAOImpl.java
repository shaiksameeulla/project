package com.ff.admin.errorHandling.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.admin.errorHandling.constants.ErrorHandlingCommonConstants;
import com.ff.domain.sap.error.conf.SAPInterfaceErrorConfigDO;

public class ErrorHandlingCommonDAOImpl extends CGBaseDAO implements ErrorHandlingCommonDAO {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ErrorHandlingCommonDAOImpl.class);
	
	Session session = null;

	@SuppressWarnings("unchecked")
	@Override
	public List<CGBaseDO> getSAPCustomerData(String effectiveFromDate, String effectiveToDate, String interfaceName, String status, 
			SAPInterfaceErrorConfigDO SAPInterfaceErrorConfigDoObj) throws CGSystemException {
		LOGGER.trace("ErrorHandlingCommonDAOImpl :: getSAPCustomerData() :: Start --------> ::::::");
		
 		String dynamicQuery = "FROM " +SAPInterfaceErrorConfigDoObj.getTableName()+ " sapCustomer " + 
		"WHERE sapCustomer."+SAPInterfaceErrorConfigDoObj.getStatusColumnName()+ " = :status " + 
		" and sapCustomer.createdDate between :effectiveFromDate and :effectiveToDate";
 		
 		LOGGER.debug("dynamicQuery: " + dynamicQuery);

		List<CGBaseDO> sapCustomerDOs= null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery(dynamicQuery);
			query.setParameter("effectiveFromDate", DateUtil.stringToDDMMYYYYFormat(effectiveFromDate));
			query.setParameter("effectiveToDate", DateUtil.stringToDDMMYYYYFormat(effectiveToDate));
			query.setParameter("status", status);

			sapCustomerDOs = query.list();

	
		}catch(Exception e){
			LOGGER.error("Exception Occured in::ErrorHandlingCommonDAOImpl::getSAPCustomerData() :: " + e);
			throw new CGSystemException(e);
		}
	
		LOGGER.trace("ErrorHandlingCommonDAOImpl :: getSAPCustomerData() :: End --------> ::::::");
		return sapCustomerDOs;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<SAPInterfaceErrorConfigDO> getSAPInterfaceConfigData(String interfaceType) throws CGSystemException {
		LOGGER.trace("ErrorHandlingCommonDAOImpl :: getSAPInterfaceConfigData() :: Start --------> ::::::");

		List<SAPInterfaceErrorConfigDO> sapInterfaceErrorConfigDOs= null;
		try{
			sapInterfaceErrorConfigDOs= getHibernateTemplate().findByNamedQueryAndNamedParam(
					ErrorHandlingCommonConstants.QRY_GET_SAP_INTERFACE_ERROR_CONFIG_DATA,
					new String[] {"interfaceType"},
					new Object[] {interfaceType});
	
		}catch(Exception e){
			LOGGER.error("Exception Occured in::ErrorHandlingCommonDAOImpl::getSAPInterfaceConfigData() :: " + e);
			throw new CGSystemException(e);
		}
		
		LOGGER.trace("ErrorHandlingCommonDAOImpl :: getSAPInterfaceConfigData() :: End --------> ::::::");
		return sapInterfaceErrorConfigDOs;
	}

}
