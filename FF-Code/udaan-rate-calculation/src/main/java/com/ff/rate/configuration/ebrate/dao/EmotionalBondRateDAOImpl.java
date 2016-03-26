package com.ff.rate.configuration.ebrate.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.ratemanagement.masters.EBRateConfigDO;
import com.ff.rate.configuration.ebrate.constants.EmotionalBondRateConstants;

public class EmotionalBondRateDAOImpl extends CGBaseDAO implements
		EmotionalBondRateDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(EmotionalBondRateDAOImpl.class);

	@Override
	public Boolean deactivatePreferences(List<Integer> prefIds)
			throws CGSystemException {
		boolean transStatus = Boolean.FALSE;
		Session session = null;
		try {
			LOGGER.trace("EmotionalBondRateDAOImpl::deactivatePreferences::START------------>:::::::");
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(EmotionalBondRateConstants.QRY_DEACTIVATE_PREFERENCE);
			query.setParameterList(EmotionalBondRateConstants.PREF_IDS, prefIds);
			query.executeUpdate();
			
			Query qry = session
					.getNamedQuery(EmotionalBondRateConstants.QRY_CHANGE_EB_RATE_FLAG);
			//query1.setString("dtToBranch", "N");
			qry.setParameterList(EmotionalBondRateConstants.PREF_IDS, prefIds);
			qry.executeUpdate();
			transStatus = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("EmotionalBondRateDAOImpl :: deactivatePreferences()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("EmotionalBondRateDAOImpl::deactivatePreferences::END------------>:::::::");
		return transStatus;
	}

	@Override
	public EBRateConfigDO saveOrUpdateEBRate(EBRateConfigDO ebRateConfigDO)
			throws CGSystemException {
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		Transaction tx = null;
		try {
			LOGGER.trace("EmotionalBondRateDAOImpl::saveOrUpdateEBRate::START------------>:::::::");
			tx = session.beginTransaction();
			ebRateConfigDO = (EBRateConfigDO) session.merge(ebRateConfigDO);

			tx.commit();
			session.flush();
		} catch (Exception e) {
			tx.rollback();
			LOGGER.error("Error occured in EmotionalBondRateDAOImpl :: saveOrUpdateEBRate()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {

			session.close();
		}
		LOGGER.trace("EmotionalBondRateDAOImpl::saveOrUpdateEBRate::END------------>:::::::");
		return ebRateConfigDO;
	}

	@Override
	public void updateEBRateTODate(Integer currentEBRateConfigId, Date date)
			throws CGSystemException {
		Session session = null;
		try {
			LOGGER.trace("EmotionalBondRateDAOImpl::updateEBRateTODate::START------------>:::::::");
			if (!StringUtil.isNull(currentEBRateConfigId)
					&& currentEBRateConfigId != 0) {
				session = createSession();
				Query qry = null;
				qry = session
						.getNamedQuery(EmotionalBondRateConstants.QRY_UPDATE_EB_RATE_TO_DATE);
				qry.setDate(EmotionalBondRateConstants.PARAM_EB_RATE_VALID_TO,
						date);
				qry.setInteger(
						EmotionalBondRateConstants.PARAM_EB_RATE_CONFIG_ID,
						currentEBRateConfigId);

				qry.executeUpdate();
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::EmotionalBondRateDAOImpl::updateEBRateTODate :: "
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("EmotionalBondRateDAOImpl::updateEBRateTODate::END------------>:::::::");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EBRateConfigDO> loadDefaultEBRates(Integer stateId)
			throws CGSystemException {
		Session session = null;
		List<EBRateConfigDO> ebRateConfigDO = null;
		Criteria criteria = null;
		try {
			LOGGER.trace("EmotionalBondRateDAOImpl::loadDefaultEBRates::START------------>:::::::");
			session = createSession();
			criteria = session.createCriteria(EBRateConfigDO.class,
					"ebRateConfigDO");
			criteria.addOrder(Order.desc("ebRateConfigDO.ebRateConfigId"));
			criteria.setMaxResults(2);

			ebRateConfigDO = (List<EBRateConfigDO>) criteria.list();

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::EmotionalBondRateDAOImpl::loadDefaultEBRates :: "
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("EmotionalBondRateDAOImpl::loadDefaultEBRates::END------------>:::::::");
		return ebRateConfigDO;

	}

	@Override
	public EBRateConfigDO isRateRenewed(List<EBRateConfigDO> ebRateConfigDO)
			throws CGSystemException {
		try {
			LOGGER.trace("EmotionalBondRateDAOImpl :: isRateRenewed() :: Start --------> ::::::");
			if (ebRateConfigDO.size() == 1) {
					return null;
				} else if (StringUtil.isNull(ebRateConfigDO.get(0)
						.getValidToDate())
						&& !StringUtil.isNull(ebRateConfigDO.get(1)
								.getValidToDate())
						&& (ebRateConfigDO.get(1).getValidToDate()
								.compareTo(DateUtil.stringToDDMMYYYYFormat(DateUtil
										.getCurrentDateInYYYYMMDDHHMM()))) < 0) {
					return null;
				} else {
					return ebRateConfigDO.get(0);
				}
			
		} catch (Exception e) {
			LOGGER.error("ERROR : EmotionalBondRateDAOImpl.isRateRenewed", e);
			throw new CGSystemException(e);
		}
	
	}

	
}