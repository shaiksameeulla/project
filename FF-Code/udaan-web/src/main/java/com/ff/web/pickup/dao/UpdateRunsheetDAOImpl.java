package com.ff.web.pickup.dao;

import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.pickup.PickupRunsheetHeaderDO;
import com.ff.web.pickup.constants.PickupManagementConstants;

/**
 * The Class UpdateRunsheetDAOImpl.
 */
public class UpdateRunsheetDAOImpl extends CGBaseDAO implements
		UpdateRunsheetDAO {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(UpdateRunsheetDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	// getting Pickup Run sheet Details by Pickup Run sheet Header Id
	public List<PickupRunsheetHeaderDO> getPickupRunsheetDetails(
			String runsheetNo) throws CGSystemException {
		LOGGER.trace("UpdateRunsheetDAOImpl :: getPickupRunsheetDetails() :: Start --------> ::::::");
		List<PickupRunsheetHeaderDO> pickupRunsheetHeaderList = null;
		try {
			HibernateTemplate hibernateTemplate = getHibernateTemplate();
			pickupRunsheetHeaderList = hibernateTemplate
					.findByNamedQueryAndNamedParam(
							PickupManagementConstants.GET_PICKUP_RUNSHEET_DETAILS_RUNSHEET_NO,
							PickupManagementConstants.RUNSHEET_NO,
							runsheetNo);
		} catch (Exception e) {
			LOGGER.error("ERROR::UpdateRunsheetDAOImpl - getPickupRunsheetDetails() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("UpdateRunsheetDAOImpl :: getPickupRunsheetDetails() :: End --------> ::::::");
		return pickupRunsheetHeaderList;
	}

	@Override
	public boolean updatePickupRunsheet(
			PickupRunsheetHeaderDO pickupRunsheetHeaderDO)
			throws CGSystemException {
		LOGGER.trace("UpdateRunsheetDAOImpl :: updatePickupRunsheet() :: Start --------> ::::::");
		boolean transactionStatus = Boolean.FALSE;
		Session session = null;
		try {
			session = openTransactionalSession();
//			session.update(pickupRunsheetHeaderDO);
			session.saveOrUpdate(pickupRunsheetHeaderDO);
			transactionStatus = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : UpdateRunsheetDAOImpl::updatePickupRunsheet :: ",
					e);
			throw new CGSystemException(e);
		}finally{
			closeTransactionalSession(session);
		}
		LOGGER.trace("UpdateRunsheetDAOImpl :: updatePickupRunsheet() :: End --------> ::::::");
		return transactionStatus;
	}

}
