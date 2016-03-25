package com.ff.integration.coloading.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.coloading.ColoadingVehicleContractDO;
import com.ff.domain.coloading.ColoadingVehicleServiceEntryDO;


/**
 * The Class ColoadingCentralDAOImpl.
 *
 * @author narmdr
 */
public class ColoadingCentralDAOImpl extends CGBaseDAO implements
		ColoadingCentralDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ColoadingCentralDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public boolean isVehicleContractExist(String date, String vehicleRegNumber)
			throws CGSystemException {		
		LOGGER.trace("ColoadingUniversalDAOImpl::isVehicleContractExist::START------------>:::::::");
		Session session = null;
		Criteria criteria = null;
		boolean isVehicleContract;
		try {
			session = createSession();
			criteria = session.createCriteria(ColoadingVehicleContractDO.class, "coloadingVehicleContractDO");			

			criteria.add(Restrictions.eq("vehicleNo",
					vehicleRegNumber));
			criteria.add(Restrictions.le("effectiveFrom", DateUtil
					.slashDelimitedstringToDDMMYYYYFormat(date)));
			criteria.add(Restrictions.isNull("effectiveTill"));
			criteria.add(Restrictions.eq("storeStatus",
					'P'));			
			criteria.setProjection(Projections
					.property("coloadingVehicleContractDO.id"));
			criteria.setMaxResults(1);
			List<Integer> coloadingVehicleContractIds = criteria.list();

			isVehicleContract = !StringUtil.isEmptyList(coloadingVehicleContractIds) ? Boolean.TRUE
					: Boolean.FALSE;

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ColoadingUniversalDAOImpl::isVehicleContractExist() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		LOGGER.debug("ColoadingUniversalDAOImpl :: isVehicleContractExist() :: End --------> ::::::");
		return isVehicleContract;
	}

	@Override
	public void saveVehicleServiceEntryDO(
			ColoadingVehicleServiceEntryDO coloadingVehicleServiceEntryDO)
			throws CGSystemException {
		LOGGER.debug("ColoadingUniversalDAOImpl :: saveVehicleServiceEntryDO() :: Start --------> ::::::");
		try {
			getHibernateTemplate().saveOrUpdate(coloadingVehicleServiceEntryDO);
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::ColoadingUniversalDAOImpl::saveVehicleServiceEntryDO() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ColoadingUniversalDAOImpl :: saveVehicleServiceEntryDO() :: End --------> ::::::");
	}
	
}
