package com.ff.rate.configuration.rateConfiguration.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.domain.ratemanagement.operations.cash.CashRateConfigAdditionalChargesDO;
import com.ff.domain.ratemanagement.operations.cash.CashRateConfigFixedChargesDO;
import com.ff.domain.ratemanagement.operations.cash.CashRateConfigHeaderDO;
import com.ff.domain.ratemanagement.operations.cash.CashRateConfigRTOChargesDO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.ratecontract.constants.RateContractConstants;
import com.ff.to.ratemanagement.operations.rateconfiguration.CashRateConfigHeaderTO;

/**
 * @author hkansagr
 */

public class CashRateConfigurationDAOImpl extends CGBaseDAO
	implements CashRateConfigurationDAO {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CashRateConfigurationDAOImpl.class);

	@Override
	public boolean saveOrUpdateCashRateProductDtls(CashRateConfigHeaderDO domain)
			throws CGSystemException {
		LOGGER.debug("CashRateConfigurationDAOImpl::saveOrUpdateCashRateProductDtls()::START");
		boolean result = Boolean.FALSE;
		Session session = null;
		Transaction tx = null;
		try {
			session = createSession();
			tx = session.beginTransaction();
			domain = (CashRateConfigHeaderDO)session.merge(domain);
			//getHibernateTemplate().saveOrUpdate(domain);
			tx.commit();
			result = Boolean.TRUE;
		} catch(Exception e) {
			tx.rollback();
			LOGGER.error("Exception occurs in CashRateConfigurationDAOImpl::saveOrUpdateCashRateProductDtls()::"
				+e.getMessage());
			throw new CGSystemException(e);
		}finally{
			session.close();
		}
		LOGGER.debug("CashRateConfigurationDAOImpl::saveOrUpdateCashRateProductDtls()::END");
		return result;
	}

	@Override
	public boolean saveOrUpdateFixedChrgsDtls(Set<CashRateConfigFixedChargesDO> fixChrgDOs)
			throws CGSystemException {
		LOGGER.debug("CashRateConfigurationDAOImpl::saveOrUpdateFixedChrgsDtls()::START");
		boolean result = Boolean.FALSE;
		try {
			getHibernateTemplate().saveOrUpdateAll(fixChrgDOs);
			result = Boolean.TRUE;
		} catch(Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationDAOImpl::saveOrUpdateFixedChrgsDtls()::"
				+e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("CashRateConfigurationDAOImpl::saveOrUpdateFixedChrgsDtls()::END");
		return result;
	}

	@Override
	public boolean saveOrUpdateAdditionalChrgsDtls(Set<CashRateConfigAdditionalChargesDO> 
		additionalChrgDOs) throws CGSystemException {
		LOGGER.debug("CashRateConfigurationDAOImpl::saveOrUpdateAdditionalChrgsDtls()::START");
		boolean result = Boolean.FALSE;
		try {
			getHibernateTemplate().saveOrUpdateAll(additionalChrgDOs);
			result = Boolean.TRUE;
		} catch(Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationDAOImpl::saveOrUpdateAdditionalChrgsDtls()::"
				+e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("CashRateConfigurationDAOImpl::saveOrUpdateAdditionalChrgsDtls()::END");
		return result;
	}

	@Override
	public boolean saveOrUpdateRTOChrgsDtls(Set<CashRateConfigRTOChargesDO> rtoChrgDOs)
			throws CGSystemException {
		LOGGER.debug("CashRateConfigurationDAOImpl::saveOrUpdateRTOChrgsDtls()::START");
		boolean result = Boolean.FALSE;
		try {
			getHibernateTemplate().saveOrUpdateAll(rtoChrgDOs);
			result = Boolean.TRUE;
		} catch(Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationDAOImpl::saveOrUpdateRTOChrgsDtls()::"
				+e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("CashRateConfigurationDAOImpl::saveOrUpdateRTOChrgsDtls()::END");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CashRateConfigHeaderDO searchCashRateProductDtls(
			CashRateConfigHeaderDO domain) throws CGSystemException {
		LOGGER.debug("CashRateConfigurationDAOImpl::searchCashRateProductDtls()::START");
		List<CashRateConfigHeaderDO> cashRateConfigHeaderDOList = null;
		CashRateConfigHeaderDO cashRateConfigHeaderDO = null;
		//String[] params = {RateCommonConstants.PARAM_LOGGED_IN_DATE, RateCommonConstants.PARAM_REGION_ID};
	/*	String[] params = {RateCommonConstants.PARAM_REGION_ID};
		 Current Date. 
		//String currentDate = DateUtil.getCurrentDateInDDMMYYYY();
		Object[] values = {DateUtil.slashDelimitedstringToDDMMYYYYFormat(currentDate), 
				domain.getRegionId()};
		cashRateConfigHeaderDOs = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(RateCommonConstants.QRY_GET_CASH_RATE_HEADER_DTLS, 
						params, values);*/
		Session session = null;
		try{
		session = createSession();
		
		Query qry = session.getNamedQuery(RateCommonConstants.QRY_GET_CASH_RATE_HEADER_DTLS);
		qry.setParameter(RateCommonConstants.PARAM_REGION_ID, domain.getRegionId());
		
		qry.setMaxResults(2);
		cashRateConfigHeaderDOList = qry.list();
		if(!CGCollectionUtils.isEmpty(cashRateConfigHeaderDOList)){
		if(cashRateConfigHeaderDOList.size() == 1){
			cashRateConfigHeaderDO = cashRateConfigHeaderDOList.get(0);
		}
		else if(!StringUtil.isNull(cashRateConfigHeaderDOList.get(1).getToDate())
				&& (cashRateConfigHeaderDOList.get(1).getToDate().compareTo(DateUtil.stringToDDMMYYYYFormat(DateUtil.getCurrentDateInYYYYMMDDHHMM())))<0	
				&& cashRateConfigHeaderDOList.get(0).getHeaderStatus().equals(RateCommonConstants.STATUS_ACTIVE)){
			cashRateConfigHeaderDO = cashRateConfigHeaderDOList.get(0);
		}else{
			cashRateConfigHeaderDO = cashRateConfigHeaderDOList.get(1);
		}
		}
		}catch(Exception e){
			throw new CGSystemException(e);
		}
		finally{
			session.close();
		}
		
		LOGGER.debug("CashRateConfigurationDAOImpl::searchCashRateProductDtls()::END");
		/*return (!StringUtil.isEmptyColletion(
				cashRateConfigHeaderDOs))?cashRateConfigHeaderDOs.get(0):null;*/
		return cashRateConfigHeaderDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CashRateConfigFixedChargesDO searchFixedChrgsDtls(Integer productMapId) 
			throws CGSystemException {
		LOGGER.debug("CashRateConfigurationDAOImpl::searchFixedChrgsDtls()::START");
		List<CashRateConfigFixedChargesDO> fixedChrgsDOs = null;
		String[] params = {RateCommonConstants.PARAM_PRODUCT_MAP_ID};
		Object[] values = {productMapId};
		fixedChrgsDOs = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(RateCommonConstants.QRY_GET_CASH_RATE_FIXED_CHRGS_DTLS, 
						params, values);
		LOGGER.debug("CashRateConfigurationDAOImpl::searchFixedChrgsDtls()::END");
		return (!StringUtil.isEmptyColletion(fixedChrgsDOs))?fixedChrgsDOs.get(0):null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CashRateConfigAdditionalChargesDO> searchAdditionalChrgsDtls(
			Integer productMapId) throws CGSystemException {
		LOGGER.debug("CashRateConfigurationDAOImpl::searchAdditionalChrgsDtls()::START");
		List<CashRateConfigAdditionalChargesDO> additionalChrgsDOs = null;
		String[] params = {RateCommonConstants.PARAM_PRODUCT_MAP_ID};
		Object[] values = {productMapId};
		additionalChrgsDOs = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(RateCommonConstants.QRY_GET_CASH_RATE_ADDITIONAL_CHRGS_DTLS, 
						params, values);
		LOGGER.debug("CashRateConfigurationDAOImpl::searchAdditionalChrgsDtls()::END");
		return (!StringUtil.isEmptyColletion(additionalChrgsDOs))?additionalChrgsDOs:null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CashRateConfigRTOChargesDO searchRTOChrgsDtls(Integer productMapId) 
			throws CGSystemException {
		LOGGER.debug("CashRateConfigurationDAOImpl::searchRTOChrgsDtls()::START");
		List<CashRateConfigRTOChargesDO> rtoChrgsDOs = null;
		String[] params = {RateCommonConstants.PARAM_PRODUCT_MAP_ID};
		Object[] values = {productMapId};
		rtoChrgsDOs = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(RateCommonConstants.QRY_GET_CASH_RATE_RTO_CHRGS_DTLS, 
						params, values);
		LOGGER.debug("CashRateConfigurationDAOImpl::searchRTOChrgsDtls()::END");
		return (!StringUtil.isEmptyColletion(rtoChrgsDOs))?rtoChrgsDOs.get(0):null;
	}

	@Override
	public boolean submitCashRateDtls(Integer cashRateHeaderId, String fromDateStr, 
			String toDateStr) throws CGSystemException {
		LOGGER.debug("CashRateConfigurationDAOImpl::submitCashRateDtls()::START");
		boolean result = Boolean.FALSE;
		Session session = null;
		Transaction tx = null;
		try{
			session = createSession();
			tx = session.beginTransaction();
			Query qry = session.getNamedQuery(RateCommonConstants
					.QRY_UPDATE_CASH_RATE_HEADER_STATUS);
			qry.setParameter(RateCommonConstants
					.PARAM_CASH_RATE_HEADER_ID, cashRateHeaderId);
			qry.setParameter(RateCommonConstants
					.PARAM_FROM_DATE, DateUtil.slashDelimitedstringToDDMMYYYYFormat(fromDateStr));
			qry.setParameter(RateCommonConstants
					.PARAM_TO_DATE, DateUtil.slashDelimitedstringToDDMMYYYYFormat(toDateStr));
			qry.setParameter(RateCommonConstants
					.PARAM_STATUS, RateContractConstants.ACTIVE);
			int i = qry.executeUpdate();
			if(i>0){
				result = Boolean.TRUE;
			}
			tx.commit();
		} catch(Exception e) {
			if(tx!=null) tx.rollback();
			LOGGER.error("Exception occurs in CashRateConfigurationDAOImpl::submitCashRateDtls()::"
				+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
 		LOGGER.debug("CashRateConfigurationDAOImpl::submitCashRateDtls()::END");
		return result;
	}

	@Override
	public boolean deleteFixedChrgs(List<Integer> productMapIds)
			throws CGSystemException {
		LOGGER.debug("CashRateConfigurationDAOImpl::deleteFixedChrgs()::START");
		boolean result = Boolean.FALSE;
		Session session = null;
		Transaction tx = null;
		try{
			session = createSession();
			tx = session.beginTransaction();
			Query qry = session.getNamedQuery(RateCommonConstants
					.QRY_DELETE_CASH_RATE_FIXED_CHRG_DTLS);
			qry.setParameterList(RateCommonConstants
					.PARAM_PRODUCT_MAP_IDS, productMapIds);
			int i = qry.executeUpdate();
			if(i>0){
				result = Boolean.TRUE;
			}
			tx.commit();
		} catch(Exception e) {
			if(tx!=null) tx.rollback();
			LOGGER.error("Exception occurs in CashRateConfigurationDAOImpl::deleteFixedChrgs()::"
				+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
 		LOGGER.debug("CashRateConfigurationDAOImpl::deleteFixedChrgs()::END");
		return result;
	}

	@Override
	public boolean deleteAdditionalChrgs(List<Integer> productMapIds)
			throws CGSystemException {
		LOGGER.debug("CashRateConfigurationDAOImpl::deleteAdditionalChrgs()::START");
		boolean result = Boolean.FALSE;
		Session session = null;
		Transaction tx = null;
		try{
			session = createSession();
			tx = session.beginTransaction();
			Query qry = session.getNamedQuery(RateCommonConstants
					.QRY_DELETE_CASH_RATE_ADDITIONAL_CHRG_DTLS);
			qry.setParameterList(RateCommonConstants
					.PARAM_PRODUCT_MAP_IDS, productMapIds);
			int i = qry.executeUpdate();
			if(i>0){
				result = Boolean.TRUE;
			}
			tx.commit();
		} catch(Exception e) {
			if(tx!=null) tx.rollback();
			LOGGER.error("Exception occurs in CashRateConfigurationDAOImpl::deleteAdditionalChrgs()::"
				+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
 		LOGGER.debug("CashRateConfigurationDAOImpl::deleteAdditionalChrgs()::END");
		return result;
	}
	
	@Override
	public boolean deleteRTOChrgs(List<Integer> productMapIds)
			throws CGSystemException {
		LOGGER.debug("CashRateConfigurationDAOImpl::deleteRTOChrgs()::START");
		boolean result = Boolean.FALSE;
		Session session = null;
		Transaction tx = null;
		try{
			session = createSession();
			tx = session.beginTransaction();
			Query qry = session.getNamedQuery(RateCommonConstants
					.QRY_DELETE_CASH_RATE_RTO_CHRG_DTLS);
			qry.setParameterList(RateCommonConstants
					.PARAM_PRODUCT_MAP_IDS, productMapIds);
			int i = qry.executeUpdate();
			if(i>0){
				result = Boolean.TRUE;
			}
			tx.commit();
		} catch(Exception e) {
			if(tx!=null) tx.rollback();
			LOGGER.error("Exception occurs in CashRateConfigurationDAOImpl::deleteRTOChrgs()::"
				+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
 		LOGGER.debug("CashRateConfigurationDAOImpl::deleteRTOChrgs()::END");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SectorDO getOriginSectorByRegionId(Integer regionId)
			throws CGSystemException {
		LOGGER.debug("CashRateConfigurationDAOImpl::getOriginSectorByRegionId()::START");
		List<SectorDO> sectorDOs = null;
		String[] params = {RateCommonConstants.PARAM_REGION_ID};
		Object[] values = {regionId};
		sectorDOs = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(RateCommonConstants.QRY_GET_ORIGIN_SECTOR_BY_REGION_ID, 
						params, values);
		LOGGER.debug("CashRateConfigurationDAOImpl::getOriginSectorByRegionId()::END");
		return (!StringUtil.isEmptyColletion(sectorDOs))?sectorDOs.get(0):null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isCashRateConfigExist(CashRateConfigHeaderTO to)
			throws CGSystemException {
		LOGGER.debug("CashRateConfigurationDAOImpl::isCashRateConfigExist()::START");
		boolean result = Boolean.FALSE;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(CashRateConfigHeaderDO.class, "cashRate");
			cr.add(Restrictions.eq("cashRate.regionId", to.getRegionId()));
			List<CashRateConfigHeaderDO> cashRateHeaderDOs = cr.list();
			if(!StringUtil.isEmptyColletion(cashRateHeaderDOs)){
				result = Boolean.TRUE;
			}
		} catch(Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationDAOImpl::isCashRateConfigExist()::"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("CashRateConfigurationDAOImpl::isCashRateConfigExist()::END");
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public CashRateConfigHeaderDO searchCashRateProductDtlsForRenew(
			CashRateConfigHeaderDO domain, Date dt) throws CGSystemException {
		LOGGER.debug("CashRateConfigurationDAOImpl::searchCashRateProductDtlsForRenew()::START");
		List<CashRateConfigHeaderDO> cashRateConfigHeaderDOs = null;
		String[] params = {RateCommonConstants.PARAM_FROM_DATE, 
				RateCommonConstants.PARAM_REGION_ID};
		Object[] values = {dt, domain.getRegionId()};
		cashRateConfigHeaderDOs = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(RateCommonConstants.QRY_GET_CASH_RATE_HEADER_DTLS_FOR_RENEW, 
						params, values);
		LOGGER.debug("CashRateConfigurationDAOImpl::searchCashRateProductDtlsForRenew()::END");
		return (!StringUtil.isEmptyColletion(
				cashRateConfigHeaderDOs))?cashRateConfigHeaderDOs.get(0):null;
	}
	
	@Override
	public boolean updateCashRateConfigToDate(Integer cashRateHeaderId, String toDate)
			throws CGSystemException {
		LOGGER.debug("CashRateConfigurationDAOImpl::updateCashRateConfigToDate()::START");
		boolean result = Boolean.FALSE;
		Session session = null;
		Transaction tx = null;
		try{
			session = createSession();
			tx = session.beginTransaction(); 
			Query qry = session.getNamedQuery(RateCommonConstants
					.QRY_UPDATE_CASH_RATE_CONFIG_TO_DATE);
			qry.setParameter(RateCommonConstants.PARAM_CASH_RATE_HEADER_ID, 
					cashRateHeaderId);
			qry.setParameter(RateCommonConstants.PARAM_TO_DATE, 
					DateUtil.slashDelimitedstringToDDMMYYYYFormat(toDate));
			int i = qry.executeUpdate();
			if(i>0){
				result = Boolean.TRUE;
			}
			tx.commit();
		} catch(Exception e) {
			if(tx!=null) tx.rollback();
			LOGGER.error("Exception occurs in CashRateConfigurationDAOImpl::updateCashRateConfigToDate()::"
				+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
 		LOGGER.debug("CashRateConfigurationDAOImpl::updateCashRateConfigToDate()::END");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Date getFromDateByCashRateHeaderId(CashRateConfigHeaderTO to)
			throws CGSystemException {
		LOGGER.debug("CashRateConfigurationDAOImpl::getToDateByCashRateHeaderId()::START");
		String[] params = {
				RateCommonConstants.PARAM_CASH_RATE_HEADER_ID, 
				};
		Object[] values = {to.getPrevCashRateHeaderId()};
		List<Date> fromDates = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(RateCommonConstants.QRY_GET_FROM_DATE_BY_CASH_RATE_HEADER_ID, 
						params, values);
		Date fromDate = null;
		if(!StringUtil.isEmptyColletion(fromDates)){
			fromDate = fromDates.get(0);
		}
		LOGGER.debug("CashRateConfigurationDAOImpl::getToDateByCashRateHeaderId()::END");
		return fromDate;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public CashRateConfigHeaderDO getCurrentPeriodCashConfig(CashRateConfigHeaderTO to)
			throws CGSystemException {
		LOGGER.debug("CashRateConfigurationDAOImpl::getCurrentPeriodCashConfig()::START");
		List<CashRateConfigHeaderDO> cashRateConfigHeaderDOs = null;
		String[] params = {
				RateCommonConstants.PARAM_REGION_ID, 
				RateCommonConstants.PARAM_STATUS, 
				RateCommonConstants.PARAM_LOGGED_IN_DATE, 
				};
		String currentDateStr = DateUtil.getCurrentDateInDDMMYYYY();
		Date currentDate = DateUtil.slashDelimitedstringToDDMMYYYYFormat(currentDateStr);
		Object[] values = {
				to.getRegionId(), 
				RateContractConstants.ACTIVE, 
				currentDate
				};
		cashRateConfigHeaderDOs = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(RateCommonConstants.QRY_GET_CURR_PERIOD_CASH_CONFIG, 
						params, values);
		LOGGER.debug("CashRateConfigurationDAOImpl::getCurrentPeriodCashConfig()::END");
		return (!StringUtil.isEmptyColletion(cashRateConfigHeaderDOs))?
				cashRateConfigHeaderDOs.get(0):null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CashRateConfigHeaderDO getCashRateConfigDetails(Integer headerId)
			throws CGSystemException {
		LOGGER.debug("CashRateConfigurationDAOImpl::getCashRateConfigDetails()::START");
		List<CashRateConfigHeaderDO> cashRateConfigHeaderDOs = null;
		CashRateConfigHeaderDO crchDO = null;
		try{
		String[] params = {
				RateCommonConstants.PARAM_CASH_RATE_HEADER_ID, 
				};
		Object[] values = {headerId};
		cashRateConfigHeaderDOs = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(RateCommonConstants.QRY_GET_CASH_RATE_DETAILS_BY_HEADER_ID, 
						params, values);
		if(!StringUtil.isEmptyColletion(cashRateConfigHeaderDOs)){
			crchDO = cashRateConfigHeaderDOs.get(0);
		}
		}catch(Exception e){
			LOGGER.error("Exception occurs in CashRateConfigurationDAOImpl::getCashRateConfigDetails()::"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("CashRateConfigurationDAOImpl::getCashRateConfigDetails()::END");
		return crchDO;
	}

	@Override
	public CashRateConfigHeaderDO saveOrUpdateCashRateConfig(CashRateConfigHeaderDO domain)
			throws CGSystemException {
		LOGGER.debug("CashRateConfigurationDAOImpl::saveOrUpdateCashRateConfig()::START");
		try {
			if(!StringUtil.isNull(domain))
				domain = getHibernateTemplate().merge(domain);
		} catch(Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationDAOImpl::saveOrUpdateCashRateProductDtls()::"
				+e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("CashRateConfigurationDAOImpl::saveOrUpdateCashRateConfig()::END");
		
		return domain;
	}
	
}
