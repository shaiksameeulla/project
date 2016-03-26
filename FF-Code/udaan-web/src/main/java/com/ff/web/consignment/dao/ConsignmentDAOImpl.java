package com.ff.web.consignment.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.utility.TwoWayWriteProcessCall;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.consignment.ConsignmentDOXDO;
import com.ff.universe.booking.constant.UniversalBookingConstants;

public class ConsignmentDAOImpl extends CGBaseDAO implements ConsignmentDAO {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ConsignmentDAOImpl.class);

	/*
	 * public boolean saveOrUpdateConsignment(ConsignmentDO consg) throws
	 * CGSystemException { boolean isConsgAdded = Boolean.FALSE; try {
	 * getHibernateTemplate().saveOrUpdate(consg); isConsgAdded = Boolean.TRUE;
	 * } catch (Exception ex) { LOGGER.error(
	 * "ERROR : ConsignmentDAOImpl.saveOrUpdateConsignment()", ex); } return
	 * isConsgAdded; }
	 */

	public List<Integer> saveOrUpdateConsignment(List<ConsignmentDO> consignments)
			throws CGSystemException {
		LOGGER.debug("ConsignmentDAOImpl::saveOrUpdateConsignment::START------------>:::::::"+DateUtil.getCurrentTimeInMilliSeconds());
		//boolean isConsgAdded = Boolean.FALSE;
		List<Integer> successCnsIds = new ArrayList(consignments.size());
		boolean is2wayWriteEnabled = TwoWayWriteProcessCall.isTwoWayWriteEnabled();
		Session session = null;
		Transaction tx = null;
		try {
			session = createSession();
			tx = session.beginTransaction();
			for (ConsignmentDO consg : consignments) {
				if(is2wayWriteEnabled) {
					consg.setDtToCentral(CommonConstants.YES);
					consg.setDtUpdateToCentral(CommonConstants.YES);
				}
				//getHibernateTemplate().saveOrUpdate(consg);
				session.merge(consg);
				//isConsgAdded = Boolean.TRUE;
				successCnsIds.add(consg.getConsgId());
			}
			tx.commit();
		} catch (Exception ex) {
			tx.rollback();
			LOGGER.error(
					"ERROR : ConsignmentDAOImpl.saveOrUpdateConsignment()", ex);
			throw new CGSystemException(ex);
		} finally{
			session.close();
		}
		LOGGER.debug("ConsignmentDAOImpl::saveOrUpdateConsignment::END------------>:::::::"+DateUtil.getCurrentTimeInMilliSeconds());
		return successCnsIds;
	}

	public boolean updateConsignmentForOutDoxMF(
			List<ConsignmentDOXDO> consignments) throws CGSystemException {
		LOGGER.trace("ConsignmentDAOImpl::updateConsignmentForOutDoxMF::START------------>:::::::");
		boolean isConsgAdded = Boolean.FALSE;
		try {
			for (ConsignmentDOXDO consg : consignments) {
				getHibernateTemplate().saveOrUpdate(consg);
				isConsgAdded = Boolean.TRUE;
			}
		} catch (Exception ex) {
			LOGGER.error(
					"ERROR : ConsignmentDAOImpl.updateConsignmentForOutDoxMF()", ex);
			throw new CGSystemException(ex);
		}
		LOGGER.trace("ConsignmentDAOImpl::updateConsignmentForOutDoxMF::END------------>:::::::");
		return isConsgAdded;
	}

	public List<ConsignmentDO> saveOrUpdateConsignments(
			List<ConsignmentDO> consignments) throws CGSystemException {
		LOGGER.trace("ConsignmentDAOImpl::saveOrUpdateConsignments::START------------>:::::::");
		List<ConsignmentDO> consgDos = new ArrayList<>();
		try {
			for (ConsignmentDO consg : consignments) {
				getHibernateTemplate().saveOrUpdate(consg);
				consgDos.add(consg);
			}
		} catch (Exception ex) {
			LOGGER.error(
					"ERROR : ConsignmentDAOImpl.saveOrUpdateConsignments()", ex);
			throw new CGSystemException(ex);
		}
		LOGGER.trace("ConsignmentDAOImpl::saveOrUpdateConsignments::END------------>:::::::");
		return consgDos;
	}

/*	public boolean saveOrUpdateCNPricingDtls(
			List<CNPricingDetailsDO> cnPrincingDtls) throws CGSystemException {
		LOGGER.trace("ConsignmentDAOImpl::saveOrUpdateCNPricingDtls::START------------>:::::::");
		boolean isCNPrincingAdded = Boolean.FALSE;
		try {
			for (CNPricingDetailsDO consgPricingDetailsDO : cnPrincingDtls)
				getHibernateTemplate().saveOrUpdate(consgPricingDetailsDO);
			isCNPrincingAdded = Boolean.TRUE;
		} catch (Exception ex) {
			LOGGER.error(
					"ERROR : ConsignmentDAOImpl.saveOrUpdateCNPricingDtls()",
					ex);
			throw new CGSystemException(ex);
		}
		LOGGER.trace("ConsignmentDAOImpl::saveOrUpdateCNPricingDtls::END------------>:::::::");
		return isCNPrincingAdded;
	}*/

	@SuppressWarnings("unchecked")
	@Override
	public Integer getChildConsgIdByConsgNo(String consgNumber)
			throws CGSystemException {
		LOGGER.debug("ConsignmentCommonDAOImpl :: getChildConsgIdByConsgNo() :: Start --------> ::::::");
		Integer consgId = null;
		try {
			List<Integer> consgIds = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UniversalBookingConstants.QRY_GET_CHILD_CONSIGNMENT_ID_BY_CONSG_NO,
							"consgNumber", consgNumber);
			consgId = !StringUtil.isEmptyList(consgIds) ? consgIds.get(0)
					: null;

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ConsignmentCommonDAOImpl::getChildConsgIdByConsgNo() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ConsignmentCommonDAOImpl :: getChildConsgIdByConsgNo() :: End --------> ::::::");
		return consgId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getConsigneeConsignorIds(String consgNumber)
			throws CGSystemException {
		LOGGER.debug("ConsignmentCommonDAOImpl :: getChildConsgIdByConsgNo() :: Start --------> ::::::");
		List<Object> cneCnrIds = null;
		try {
			cneCnrIds = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getConsignorConsigneeIds", "consgNumber", consgNumber);

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ConsignmentCommonDAOImpl::getChildConsgIdByConsgNo() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ConsignmentCommonDAOImpl :: getChildConsgIdByConsgNo() :: End --------> ::::::");
		return cneCnrIds;
	}
	
	public boolean createConsignment(ConsignmentDO consignment)
			throws CGSystemException {
		LOGGER.trace("ConsignmentDAOImpl::createConsignment::START------------>:::::::");
		boolean isConsgAdded = Boolean.FALSE;
		try {
				getHibernateTemplate().merge(consignment);
				isConsgAdded = Boolean.TRUE;
		} catch (Exception ex) {
			LOGGER.error(
					"ERROR : ConsignmentDAOImpl.createConsignment()", ex);
			throw new CGSystemException(ex);
		}
		LOGGER.trace("ConsignmentDAOImpl::createConsignment::END------------>:::::::");
		return isConsgAdded;
	}

	/*
	 * @Override public RateComponentDO getRateComponentByCode(String
	 * rateCompCode) throws CGSystemException { LOGGER.debug(
	 * "OutManifestCommonServiceImpl :: getRateComponentByCode() :: START------------>:::::::"
	 * ); Session session = null; Criteria criteria = null; RateComponentDO
	 * componentDO=null; try { session = createSession(); criteria =
	 * session.createCriteria(RateComponentDO.class, "rateComponentDO");
	 * criteria
	 * .add(Restrictions.eq("rateComponentDO.rateComponentCode",rateCompCode));
	 * componentDO = (RateComponentDO) criteria.uniqueResult(); } catch
	 * (Exception e) { LOGGER.error(
	 * "ERROR :: OutManifestCommonDAOImpl :: getRateComponentByCode() ::" +
	 * e.getMessage()); }finally { closeSession(session); } LOGGER.debug(
	 * "OutManifestCommonServiceImpl :: getRateComponentByCode() :: END------------>:::::::"
	 * ); return componentDO; }
	 */

}
