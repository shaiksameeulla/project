package com.ff.sap.integration.consignmentratecalculation.dao;

import java.util.ArrayList;
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
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.billing.ConsignmentBilling;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;

/**
 * @author hkansagr
 * 
 */
public class ConsignmentRateCalculationDAOImpl extends CGBaseDAO implements
		ConsignmentRateCalculationDAO {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ConsignmentRateCalculationDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsignmentBilling> getConsgDtlsWhoseRateIsNull()
			throws CGSystemException {
		LOGGER.debug("ConsignmentRateCalculationDAOImpl :: getConsgDtlsWhoseRateIsNull() :: START");
		List<ConsignmentBilling> consignmentList = new ArrayList<ConsignmentBilling>();
		Session session = null;
		try {
			session = createSession();
			Query query = session.createSQLQuery(
					SAPIntegrationConstants.GET_CN_HAVING_NULL_RATE).addEntity(
					ConsignmentBilling.class);
			query.setMaxResults(20000);
			consignmentList = query.list();
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ConsignmentRateCalculationDAOImpl :: getConsgDtlsWhoseRateIsNull() :: ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ConsignmentRateCalculationDAOImpl :: getConsgDtlsWhoseRateIsNull() :: END");
		return consignmentList;
	}

	@Override
	public void saveOrUpdateConsgDtlsWhoseRateIsCalculated(ConsignmentBillingRateDO consignmentBillingRateDO)
			throws CGSystemException {
		LOGGER.debug("ConsignmentRateCalculationDAOImpl :: saveOrUpdateConsgDtlsWhoseRateIsCalculated() :: START");
		try {
			getHibernateTemplate().merge(consignmentBillingRateDO);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ConsignmentRateCalculationDAOImpl :: saveOrUpdateConsgDtlsWhoseRateIsCalculated() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ConsignmentRateCalculationDAOImpl :: saveOrUpdateConsgDtlsWhoseRateIsCalculated() :: END");
	}
	
	/* (non-Javadoc)
	 * @see com.ff.sap.integration.consignmentratecalculation.dao.ConsignmentRateCalculationDAO#UpdateConsgnBillStatusByConsgnNo(java.util.List, java.lang.String)
	 */
	@Override
	public boolean UpdateConsgnBillStatusByConsgnNo(List<String> consgNo,
			String billingStatus) throws CGSystemException {
		logger.debug("ConsignmentRateCalculationDAOImpl :: UpdateConsgnBillStatusByConsgnNo :: START");
		Session session = null;
		Query query = null;
		boolean result = false;
		try {
			session = createSession();
			query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_UPDATE_BILLING_STATUS);
			query.setString(SAPIntegrationConstants.BILLING_STATUS_RTB,
					billingStatus);
			query.setParameterList(SAPIntegrationConstants.CONSIGNMENT_NO,
					consgNo);
			query.setTimestamp(SAPIntegrationConstants.UPDATED_DATE, Calendar
					.getInstance().getTime());
			query.setInteger(SAPIntegrationConstants.UPDATED_BY, 4);
			int i = query.executeUpdate();
			if (i > 0)
				result = Boolean.TRUE;
		} catch (Exception e) {
			logger.error(
					"ConsignmentRateCalculationDAOImpl :: UpdateConsgnBillStatusByConsgnNo ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("ConsignmentRateCalculationDAOImpl :: UpdateConsgnBillStatusByConsgnNo :: END");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsignmentBilling> getRTOConsgDtlsWhoseRateIsNull() throws CGSystemException {

		LOGGER.debug("ConsignmentRateCalculationDAOImpl :: getRTOConsgDtlsWhoseRateIsNull() :: START");
		List<ConsignmentBilling> consignmentList = new ArrayList<ConsignmentBilling>();
		Session session = null;
		try {
			session = createSession();
			Query query = session.createSQLQuery(
					SAPIntegrationConstants.GET_CN_HAVING_RTO_RATE_NULL).addEntity(
					ConsignmentBilling.class);
			query.setMaxResults(10000);
			consignmentList = query.list();
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ConsignmentRateCalculationDAOImpl :: getRTOConsgDtlsWhoseRateIsNull() :: ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ConsignmentRateCalculationDAOImpl :: getRTOConsgDtlsWhoseRateIsNull() :: END");
		return consignmentList;
	
	}

	@Override
	public void updateBookingOpsmanStatus(String consgNo)
			throws CGSystemException {
		LOGGER.debug("ConsignmentRateCalculationDAOImpl :: updateBookingOpsmanStatus() :: START");
		Session session = null;
		Query query = null;
		try {
			session = createSession();
			query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_UPDATE_OPSMAN_STATUS);
			query.setString("dtFromOpsman", "R");
			query.setString("consgNo", consgNo);
			query.executeUpdate();
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ConsignmentRateCalculationDAOImpl :: updateBookingOpsmanStatus() :: ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ConsignmentRateCalculationDAOImpl :: updateBookingOpsmanStatus() :: START");

	}

	@SuppressWarnings("unchecked")
	@Override
	public ConsignmentDO getConsignmentByConsgNo(String consgNo)
			throws CGSystemException {
		Session session = null;
		Criteria cr = null;
		ConsignmentDO consgDO = null;
		try {
			session = createSession();
			cr = session.createCriteria(ConsignmentDO.class,
					"consignmentDO");
			cr.add(Restrictions.eq("consignmentDO.consgNo",
					consgNo));
			List<ConsignmentDO> consignmentDOs = (List<ConsignmentDO>) cr
					.list();
			if (!StringUtil.isEmptyColletion(consignmentDOs)) {
				consgDO = consignmentDOs.get(0);
			}
		} catch (Exception e) {
			logger.error("ConsignmentRateCalculationDAOImpl :: getConsignmentByConsgNo()::::::"
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return consgDO;
	}

	@Override
	public void saveOrUpdateConsgDO(ConsignmentDO consgDO)
			throws CGSystemException {
		LOGGER.debug("ConsignmentRateCalculationDAOImpl :: saveOrUpdateConsgDO() :: START");
		try {
			getHibernateTemplate().merge(consgDO);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ConsignmentRateCalculationDAOImpl :: saveOrUpdateConsgDO() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ConsignmentRateCalculationDAOImpl :: saveOrUpdateConsgDO() :: END");
		
	}
}
