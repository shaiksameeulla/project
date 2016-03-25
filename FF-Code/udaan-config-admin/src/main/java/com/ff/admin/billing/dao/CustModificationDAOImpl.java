package com.ff.admin.billing.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.admin.billing.constants.BillingConstants;
import com.ff.domain.business.CustomerBillingDO;
import com.ff.domain.geography.RegionDO;


public class CustModificationDAOImpl extends CGBaseDAO implements CustModificationDAO {

	private final static Logger LOGGER = LoggerFactory.getLogger(CustModificationDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerBillingDO> getCustomerDetails(Integer cityId, Integer officeId) throws CGSystemException {
		LOGGER .debug("CustModificationDAOImpl: updateSapLiabilityEntryDetails(): START");
		List<CustomerBillingDO> customerBillingList = null;
		Session session=null;
		try {  
			session =createSession();
			String query_string=BillingConstants.GET_CUSTOMERS_BY_CONTRACT_BRANCHES;		
			customerBillingList = session.createSQLQuery(query_string)
					.addScalar("customerId")
					.addScalar("customerCode")
					.addScalar("businessName")
					.addScalar("shippedToCode")
					.setParameter("officeIds", officeId)
					.setParameter("cityId", cityId)
					.setResultTransformer(Transformers.aliasToBean(CustomerBillingDO.class)).list();
		}catch (Exception e) {
			LOGGER.error(
					"Exception In :: CustModificationDAOImpl :: saveOrUpdateCustModification ::",
					e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		return customerBillingList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RegionDO> getRegionByRegionCode(String regionCode)
			throws CGSystemException {
		LOGGER .debug("CustModificationDAOImpl: getRegionByRegionCode(): START");
		List<RegionDO> regionDO = null;
		Session session = null;
		Query query=null;
		try {
			String queryString="getAllRegionByRegionCode";
			session =createSession();
			query=session.getNamedQuery(queryString);
			query.setString("regionCode", regionCode);
			regionDO=query.list();
		}catch (Exception e) {
			LOGGER.error("Error occured in CustModificationDAOImpl :: getRegionByRegionCode()..:", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("CustModificationDAOImpl: getRegionByRegionCode(): END");
		return regionDO;
	}
}
