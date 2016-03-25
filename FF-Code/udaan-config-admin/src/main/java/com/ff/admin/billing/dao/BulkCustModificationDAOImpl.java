package com.ff.admin.billing.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.geography.CityDO;

public class BulkCustModificationDAOImpl extends CGBaseDAO implements BulkCustModificationDAO {

	private final static Logger LOGGER = LoggerFactory.getLogger(BulkCustModificationDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<CityDO> getCitysByStateId(Integer stateId)
			throws CGSystemException {
		LOGGER.debug("BulkCustModificationDAOImpl :: getCitysByStateId :: Start --------> ::::::");

		List<CityDO> cityDO;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("getCitiesByRegion");
			query.setInteger("RegionId", stateId);
			cityDO = query.list();

		} catch (Exception e) {
			LOGGER.error("Error occured in BulkCustModificationDAOImpl :: getCitysByStateId()..:", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return cityDO;
	}
	
}
