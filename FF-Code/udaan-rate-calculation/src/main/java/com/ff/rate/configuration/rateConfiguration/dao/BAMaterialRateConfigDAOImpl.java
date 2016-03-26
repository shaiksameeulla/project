package com.ff.rate.configuration.rateConfiguration.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.ratemanagement.operations.ba.BAMaterialRateConfigDO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.to.ratemanagement.operations.rateconfiguration.BAMaterialRateConfigTO;

/**
 * @author hkansagr
 */

public class BAMaterialRateConfigDAOImpl extends CGBaseDAO implements BAMaterialRateConfigDAO {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BAMaterialRateConfigDAOImpl.class);

	@Override
	public boolean saveBAMaterialRateDtls(BAMaterialRateConfigDO domain)
			throws CGSystemException {
		LOGGER.debug("BAMaterialRateConfigDAOImpl::saveBAMaterialRateDtls()::START");
		boolean result = Boolean.FALSE;
		try {
			getHibernateTemplate().saveOrUpdate(domain);
			result = Boolean.TRUE;
		} catch(Exception e) {
			LOGGER.error("Exception occurs in BAMaterialRateConfigDAOImpl::saveBAMaterialRateDtls()::" 
				+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("BAMaterialRateConfigDAOImpl::saveBAMaterialRateDtls()::END");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BAMaterialRateConfigDO searchBAMaterialRateDtls(BAMaterialRateConfigTO to) 
			throws CGSystemException {
		LOGGER.debug("BAMaterialRateConfigDAOImpl::searchBAMaterialRateDtls()::START");
		List<BAMaterialRateConfigDO> baMtrlRateConfigDOs = null;
		String[] params = { RateCommonConstants.LOGGED_IN_DATE};
		Object[] values = { DateUtil.slashDelimitedstringToDDMMYYYYFormat(to.getLoggedInDateStr()) };
		baMtrlRateConfigDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
				RateCommonConstants.QRY_GET_BA_MTRL_RATE_CONFIG_DTLS, params, values);
		LOGGER.debug("BAMaterialRateConfigDAOImpl::searchBAMaterialRateDtls()::END");
		return (!StringUtil.isEmptyColletion(baMtrlRateConfigDOs))?baMtrlRateConfigDOs.get(0):null;
	}

	@Override
	public boolean updateValidToDate(BAMaterialRateConfigTO to)
			throws CGSystemException {
		LOGGER.debug("BAMaterialRateConfigDAOImpl::updateValidToDate()::START");
		boolean result = Boolean.FALSE;
		Session session = null;
		Transaction tx = null;
		try{
			session = createSession();
			tx = session.beginTransaction();
			Query qry = session.getNamedQuery(RateCommonConstants.QRY_UPDATE_VALID_TO_DATE);
			qry.setParameter(RateCommonConstants.PARAM_BA_MTRL_RATE_ID, 
					to.getPrevBAMtrlRateId());
			qry.setParameter(RateCommonConstants.PARAM_VALID_TO_DATE, 
					to.getToDate());
			int i = qry.executeUpdate();
			if(i>0) {
				result = Boolean.TRUE;
			}
			tx.commit();
		} catch(Exception e) {
			if(tx!=null) tx.rollback();
			LOGGER.error("Exception occurs in BAMaterialRateConfigDAOImpl::updateValidToDate()::" 
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("BAMaterialRateConfigDAOImpl::updateValidToDate()::END");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BAMaterialRateConfigDO searchRenewedBAMaterialRateDtls(
			BAMaterialRateConfigTO to) throws CGSystemException {
		LOGGER.debug("BAMaterialRateConfigDAOImpl::searchRenewedBAMaterialRateDtls()::START");
		List<BAMaterialRateConfigDO> baMtrlRateConfigDOs = null;
		String[] params = { RateCommonConstants.LOGGED_IN_DATE};
		Object[] values = { DateUtil.slashDelimitedstringToDDMMYYYYFormat(to.getLoggedInDateStr()) };
		baMtrlRateConfigDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
				RateCommonConstants.QRY_GET_RENEWED_BA_MTRL_RATE_CONFIG_DTLS, params, values);
		LOGGER.debug("BAMaterialRateConfigDAOImpl::searchRenewedBAMaterialRateDtls()::END");
		return (!StringUtil.isEmptyColletion(baMtrlRateConfigDOs))?baMtrlRateConfigDOs.get(0):null;
	}

}
