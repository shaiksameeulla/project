/**
 * 
 */
package com.ff.web.pickup.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.pickup.ReversePickupOrderDetailDO;
import com.ff.organization.OfficeTO;
import com.ff.web.pickup.constants.PickupManagementConstants;

/**
 * @author uchauhan
 * 
 */
public class ConfirmPickupOrderDAOImpl extends CGBaseDAO implements
		ConfirmPickupOrderDAO {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ConfirmPickupOrderDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<ReversePickupOrderDetailDO> getPickupOrderRequestList(
			OfficeTO officeTO) throws CGSystemException {
		LOGGER.trace("ConfirmPickupOrderDAOImpl :: getPickupOrderRequestList() :: Start --------> ::::::");
		String param[] = PickupManagementConstants.PARAMS.split(",");
		Integer officeId = officeTO.getOfficeId();
		String status = "P";
		Object[] values = { officeId, status };
		List<ReversePickupOrderDetailDO> pickupDOs = null;
		try {
			pickupDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					PickupManagementConstants.GET_REQUEST_DETAILS, param,
					values);
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ConfirmPickupOrderDAOImpl::getPickupOrderRequestList() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("ConfirmPickupOrderDAOImpl :: getPickupOrderRequestList() :: End --------> ::::::");
		return pickupDOs;
	}

	@Override
	public boolean updatePickupOrderDetails(String status, Integer officeId,
			Integer detailId,Integer updatedBy) throws CGSystemException {
		LOGGER.trace("ConfirmPickupOrderDAOImpl :: updatePickupOrderDetails() :: Start --------> ::::::");
		Session session = null;
		boolean isUpdated = Boolean.FALSE;
		try {
			session = openTransactionalSession();
			Query query = session
					.getNamedQuery(PickupManagementConstants.UPDATE_PICKUP_ORDER_DETAILS);
			query.setParameter("status", status);
			query.setParameter("officeId", officeId);
			query.setParameter("detailId", detailId);
			query.setParameter("updatedBy", updatedBy);
			query.setParameter(PickupManagementConstants.CURRENT_DATE, Calendar.getInstance().getTime());
			
			int updatedRows = query.executeUpdate();

			if (updatedRows > 0) {
				isUpdated = Boolean.TRUE;
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ConfirmPickupOrderDAOImpl::updatePickupOrderDetails() :: "
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
		}
		LOGGER.trace("ConfirmPickupOrderDAOImpl :: updatePickupOrderDetails() :: End --------> ::::::");
		return isUpdated;
	}

	@Override
	public boolean updateBranchOrderDetails(String status, Integer officeId,
			Integer detailId,Integer updatedBy) throws CGSystemException {

		LOGGER.trace("ConfirmPickupOrderDAOImpl :: updateBranchOrderDetails() :: Start --------> ::::::");
		Session session = null;
		boolean isUpdated = Boolean.FALSE;
		try {
			session = openTransactionalSession();
			Query query = session
					.getNamedQuery(PickupManagementConstants.UPDATE_BRANCH_ORDER_DETAILS);
			query.setParameter("status", status);
			query.setParameter("officeId", officeId);
			query.setParameter("detailId", detailId);
			query.setParameter("updatedBy", updatedBy);
			query.setParameter(PickupManagementConstants.CURRENT_DATE, Calendar.getInstance().getTime());
			
			int updatedRows = query.executeUpdate();

			if (updatedRows > 0) {
				isUpdated = Boolean.TRUE;
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ConfirmPickupOrderDAOImpl::updateBranchOrderDetails() :: "
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
		}
		LOGGER.trace("ConfirmPickupOrderDAOImpl :: updateBranchOrderDetails() :: End --------> ::::::");
		return isUpdated;
	}

	@Override
	public boolean updateforDataSync(Integer officeId, Integer detailId)
			throws CGSystemException {
		LOGGER.trace("ConfirmPickupOrderDAOImpl :: updateforDataSync() :: Start --------> ::::::");
		Session session = null;
		boolean isUpdated = Boolean.FALSE;
		try {
			String dtCentral = "N";
			String dtUpdateCentral = "Y";
			session = openTransactionalSession();
			Query query = session
					.getNamedQuery(PickupManagementConstants.UPDATE_DATA_SYNC_DETAILS);
			query.setParameter("dtCentral", dtCentral);
			query.setParameter("dtUpdateCentral", dtUpdateCentral);
			query.setParameter("officeId", officeId);
			query.setParameter("detailId", detailId);

			int updatedRows = query.executeUpdate();

			if (updatedRows > 0) {
				isUpdated = Boolean.TRUE;
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ConfirmPickupOrderDAOImpl::updateforDataSync() :: "
					,e);
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
		}
		LOGGER.trace("ConfirmPickupOrderDAOImpl :: updateforDataSync() :: End --------> ::::::");
		return isUpdated;
	}

	@Override
	public List<ReversePickupOrderDetailDO> getPickupOrderBranchMappingDtls(
			Integer detailId) throws CGSystemException {
		LOGGER.trace("ConfirmPickupOrderDAOImpl :: getPickupOrderBranchMappingDtls() :: Start --------> ::::::");
		List<ReversePickupOrderDetailDO> pickupOrderDetailDOs = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = openTransactionalSession();
			criteria = session.createCriteria(ReversePickupOrderDetailDO.class);
			criteria.add(Restrictions.eq("detailId", detailId));
			pickupOrderDetailDOs = criteria.list();
		}catch (Exception e) {
			LOGGER.error("Exception Occured in::ConfirmPickupOrderDAOImpl::getPickupOrderBranchMappingDtls() :: "
					,e);
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
		}
		LOGGER.trace("ConfirmPickupOrderDAOImpl :: getPickupOrderBranchMappingDtls() :: End --------> ::::::");
		return pickupOrderDetailDOs;
	}
}
