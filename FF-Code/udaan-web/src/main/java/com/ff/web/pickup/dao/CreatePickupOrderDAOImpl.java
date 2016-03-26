package com.ff.web.pickup.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.pickup.ReversePickupOrderDetailDO;
import com.ff.domain.pickup.ReversePickupOrderHeaderDO;
import com.ff.pickup.PickupOrderDetailsTO;
import com.ff.web.pickup.constants.PickupManagementConstants;

public class CreatePickupOrderDAOImpl extends CGBaseDAO implements
		CreatePickupOrderDAO {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(CreatePickupOrderDAOImpl.class);

	@Override
	public ReversePickupOrderHeaderDO savePickupOrder(
			ReversePickupOrderHeaderDO headerDO) throws CGSystemException {
		LOGGER.trace("CreatePickupOrderDAOImpl :: savePickupOrder() :: Start --------> ::::::");
		try {
			getHibernateTemplate().save(headerDO);

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CreatePickupOrderDAOImpl::savePickupOrder() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("CreatePickupOrderDAOImpl :: savePickupOrder() :: End --------> ::::::");
		return headerDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ReversePickupOrderDetailDO getPickupOrderDetail(
			PickupOrderDetailsTO detailTO) throws CGSystemException {
		LOGGER.trace("CreatePickupOrderDAOImpl :: getPickupOrderDetail() :: Start --------> ::::::");
		String orderNum = detailTO.getOrderNumber();
		List<ReversePickupOrderDetailDO> detailsDO = null;
		ReversePickupOrderDetailDO detailDO = null;
		try {
			detailsDO = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getPickupOrderDetail", "orderNum", orderNum);
			if (detailsDO != null && !detailsDO.isEmpty()) {
				detailDO = detailsDO.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CreatePickupOrderDAOImpl::getPickupOrderDetail() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("CreatePickupOrderDAOImpl :: getPickupOrderDetail() :: End --------> ::::::");
		return detailDO;
	}
	@Override
	public List<Object[]> getCustomersInContractByBranch(Integer officeId)
			throws CGSystemException {
		Session session=null;
		try {
			session = createSession();
			List<Object[]> customerDOs = null;
			Query query = session.createSQLQuery(PickupManagementConstants.QRY_GET_CUSTOMERS_IN_CONTRACT_BY_BRANCH);			
			query.setInteger("officeId", officeId);
			customerDOs = query.list();			
			return customerDOs;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationServiceDAOImpl.getCustomersInContractByBranch",
					e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
	}
}
