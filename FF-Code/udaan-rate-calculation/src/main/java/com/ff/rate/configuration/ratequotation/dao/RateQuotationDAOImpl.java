/**
 * 
 */
package com.ff.rate.configuration.ratequotation.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.ratemanagement.masters.CodChargeDO;
import com.ff.domain.ratemanagement.masters.CustomerGroupDO;
import com.ff.domain.ratemanagement.masters.OctroiChargeDO;
import com.ff.domain.ratemanagement.masters.RateComponentDO;
import com.ff.domain.ratemanagement.masters.RateContractDO;
import com.ff.domain.ratemanagement.masters.RateIndustryTypeDO;
import com.ff.domain.ratemanagement.masters.RateProductCategoryDO;
import com.ff.domain.ratemanagement.masters.RateTaxComponentDO;
import com.ff.domain.ratemanagement.operations.rateBenchmarkDiscount.RegionRateBenchMarkDiscountDO;
import com.ff.domain.ratemanagement.operations.ratebenchmark.RateBenchMarkHeaderDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationCODChargeDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationFixedChargesConfigDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationFixedChargesDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationProductCategoryHeaderDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationRTOChargesDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationSlabRateDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationWeightSlabDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationWrapperDO;
import com.ff.domain.umc.EmployeeUserDO;
import com.ff.domain.umc.UserDO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.ratequotation.constants.RateQuotationConstants;
import com.ff.to.ratemanagement.operations.ratequotation.OctroiChargeTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationTO;

/**
 * @author rmaladi
 *
 */
public class RateQuotationDAOImpl extends CGBaseDAO implements RateQuotationDAO {
	private final static Logger LOGGER = LoggerFactory.getLogger(RateQuotationDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<RateIndustryTypeDO> getRateIndustryType()
			throws CGSystemException {
		LOGGER.trace("RateQuotationDAOImpl :: getRateIndustryType() :: Start --------> ::::::");

		Session session = null;
		List<RateIndustryTypeDO> rateIndustryTypeDOList = null;
		Criteria criteria = null;
		try {
			session = createSession();
			criteria = session.createCriteria(RateIndustryTypeDO.class,
					"rateIndustryTypeDO");
			criteria.addOrder(Order
					.asc("rateIndustryTypeDO.rateIndustryTypeId"));
			rateIndustryTypeDOList = criteria.list();
		} catch (Exception e) {
			LOGGER.error("ERROR : RateQuotationDAOImpl.getRateIndustryType", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("RateQuotationDAOImpl :: getRateIndustryType() :: End --------> ::::::");
		return rateIndustryTypeDOList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerGroupDO> getcustomerGroup() throws CGSystemException {
		LOGGER.trace("RateQuotationDAOImpl :: getcustomerGroup() :: Start --------> ::::::");

		Session session = null;
		List<CustomerGroupDO> customerGroupDOList = null;
		Criteria criteria = null;
		try {
			session = createSession();
			criteria = session.createCriteria(CustomerGroupDO.class,
					"customerGroupDO");
			criteria.addOrder(Order.asc("customerGroupDO.customerGroupId"));
			customerGroupDOList = criteria.list();
		} catch (Exception e) {
			LOGGER.error("ERROR : RateQuotationDAOImpl.getcustomerGroup", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("RateQuotationDAOImpl :: getcustomerGroup() :: End --------> ::::::");
		return customerGroupDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public EmployeeUserDO getEmployeeUser(Integer userId)
			throws CGSystemException {
		LOGGER.trace("RateQuotationDAOImpl :: getEmployeeUser() :: Start --------> ::::::");
		List<EmployeeUserDO> result = null;
		try{
		result = getHibernateTemplate().findByNamedQueryAndNamedParam(
				RateQuotationConstants.QRY_GET_EMP_USERDO_BY_USERID,
				RateQuotationConstants.USER_ID, userId);
		LOGGER.trace("RateQuotationDAOImpl :: getEmployeeUser() :: END --------> ::::::");
		if (result.isEmpty()){
			return null;
		}else if (result.size() > 1){
			return null;
		}else {
			EmployeeUserDO domain = result.get(0);
			return domain;
		}
		}catch (Exception e) {
			LOGGER.error("ERROR : RateQuotationDAOImpl.getcustomerGroup", e);
			throw new CGSystemException(e);
		}
	}

	@Override
	public RateQuotationDO saveOrUpdateBasicInfo(RateQuotationDO quotationDO)
			throws CGSystemException {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = null;
		try {
			LOGGER.trace("RateQuotationDAOImpl :: saveOrUpdateBasicInfo() :: Start --------> ::::::");
			tx = session.beginTransaction();
			quotationDO = (RateQuotationDO) session.merge(quotationDO);
			LOGGER.trace("RateQuotationDAOImpl :: saveOrUpdateBasicInfo() ");
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			LOGGER.trace("Error occured in RateQuotationDAOImpl :: saveOrUpdateBasicInfo():" , e);			
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.trace("RateQuotationDAOImpl :: saveOrUpdateBasicInfo() :: END --------> ::::::");
		return quotationDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateQuotationDO> searchQuotationDetails(
			RateQuotationTO rateQuotationTO) throws CGSystemException {
		LOGGER.trace("RateQuotationDAOImpl :: searchQuotationDetails() :: Start --------> ::::::");
		List<RateQuotationDO> quotationDO = null;
		try{
		String[] paramNames = { RateCommonConstants.QUOTATION_ID,
				RateCommonConstants.QUOTATION_NO,
				RateCommonConstants.CREATED_BY,
				RateCommonConstants.RATE_QUOTATION_TYPE};
		Object[] values = { rateQuotationTO.getRateQuotationId(),
				rateQuotationTO.getRateQuotationNo(),
				rateQuotationTO.getCreatedBy(),
				rateQuotationTO.getRateQuotationType()};
		quotationDO = (List<RateQuotationDO>) getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_SEARCH_QUOTATION, paramNames,
						values);
		}catch (Exception e) {
			LOGGER.error("ERROR : RateQuotationDAOImpl.getRateIndustryType", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("RateQuotationDAOImpl :: searchQuotationDetails() :: END --------> ::::::");
		return quotationDO;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateQuotationFixedChargesDO> loadDefaultFixedChargesValue(
			Integer quotationId) throws CGSystemException {

		Session session = null;
		List<RateQuotationFixedChargesDO> fixedChargesDOs = null;
		Criteria criteria = null;
		try {
			LOGGER.trace("RateQuotationDAOImpl :: loadDefaultFixedChargesValue() :: Start --------> ::::::");
			session = createSession();
			criteria = session.createCriteria(RateQuotationFixedChargesDO.class,"rateQuotationfixedCharges");
			criteria.add(Restrictions.eq("rateQuotationfixedCharges.rateQuotationDO.rateQuotationId",
					quotationId));
			fixedChargesDOs = criteria.list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : RateQuotationDAOImpl.loadDefaultFixedChargesValue",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("RateQuotationDAOImpl :: loadDefaultFixedChargesValue() :: End --------> ::::::");
		return fixedChargesDOs;

	}

	@Override
	public OctroiChargeDO getOctroiChargeValue(OctroiChargeTO octroiChargeTO)
			throws CGSystemException {
		LOGGER.trace("RateQuotationDAOImpl :: getOctroiChargeValue() :: Start --------> ::::::");

		Session session = null;
		OctroiChargeDO octroiChargeDO = null;
		Criteria criteria = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(OctroiChargeDO.class);
			criteria.add(Restrictions.eq("octroiBourneBy",
					octroiChargeTO.getOctroiBourneBy()));
			octroiChargeDO = (OctroiChargeDO) criteria.uniqueResult();
		} catch (Exception e) {
			LOGGER.error("ERROR : RateQuotationDAOImpl.getOctroiChargeValue", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("RateQuotationDAOImpl :: getOctroiChargeValue() :: End --------> ::::::");
		return octroiChargeDO;

	}

	@Override
	public RateQuotationDO saveOrUpdateFixedCharges(
			RateQuotationDO quotationDO,
			Set<RateQuotationFixedChargesConfigDO> configDO)
			throws CGSystemException {
		//Session session = null;
		//Transaction tx = null;
		//Boolean isDeleted=false;
		
		
		try {
			LOGGER.trace("RateQuotationDAOImpl :: saveOrUpdateFixedCharges() :: Start --------> ::::::");
			/*session = getHibernateTemplate().getSessionFactory()
					.openSession();
			tx = session.beginTransaction();
			if (StringUtil.isNull(quotationDO.getRateQuotationId())) {
				tx = session.beginTransaction();
				session.saveOrUpdate(quotationDO);
				if (!StringUtil.isNull(configDO.getOctroiBourneBy())
						|| !StringUtil.isNull(configDO.getInsuredBy())) {
					configDO.setQuotationId(quotationDO.getRateQuotationId());
					session.saveOrUpdate(configDO);
				}
				tx.commit();
				session.flush();
			} else {
				isDeleted=deleteFixedCharges(quotationDO);
					
				getHibernateTemplate().saveOrUpdateAll(
						quotationDO.getRateFixedChargeDO());
			
				if (!StringUtil.isNull(configDO.getOctroiBourneBy())
						|| !StringUtil.isNull(configDO.getInsuredBy())) {

					session.saveOrUpdate(configDO);
				}
				tx.commit();
				session.flush();

			}*/
			saveOrUpdateBasicInfo(quotationDO);
		} catch (Exception e) {
			//tx.rollback();
			LOGGER.error("Error occured in RateQuotationDAOImpl :: saveOrUpdateFixedCharges()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("RateQuotationDAOImpl :: saveOrUpdateFixedCharges() :: END --------> ::::::");
		return quotationDO;
	}

	/*private Boolean deleteFixedCharges(RateQuotationDO quotationDO)
			throws CGSystemException {
		Session session = null;
		Transaction tx = null;
		Boolean isDeleted=false;
		try {
			LOGGER.trace("RateQuotationDAOImpl :: deleteFixedCharges() :: Start --------> ::::::");
			session = getHibernateTemplate().getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query query = session
					.getNamedQuery(RateCommonConstants.QRY_DELETE_FIXED_CHARGES);
			query.setParameter(RateCommonConstants.QUOTATION_ID,
					quotationDO.getRateQuotationId());
			query.executeUpdate();

			Query queryConfig = session
					.getNamedQuery(RateCommonConstants.QRY_DELETE_FIXED_CHARGES_CONFIG);
			queryConfig.setParameter(RateCommonConstants.QUOTATION_ID,
					quotationDO.getRateQuotationId());
			queryConfig.executeUpdate();

			Query queryRemarks = session
					.getNamedQuery(RateCommonConstants.QRY_UPDATE_QUOTATION);
			queryRemarks.setParameter(RateCommonConstants.QUOTATION_ID,
					quotationDO.getRateQuotationId());
			queryRemarks.setParameter(RateCommonConstants.APPROVERS_REMARKS,
					quotationDO.getApproversRemarks());
			queryRemarks.setParameter(RateCommonConstants.EXCECUTIVE_REMARKS,
					quotationDO.getExcecutiveRemarks());
			queryRemarks.executeUpdate();
			tx.commit();
			session.flush();
			isDeleted=true;

		} catch (Exception e) {
			tx.rollback();
			LOGGER.error("Error occured in RateQuotationDAOImpl :: deleteFixedCharges()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {

			session.close();
		}
		LOGGER.trace("RateQuotationDAOImpl :: deleteFixedCharges() :: END --------> ::::::");
		return isDeleted;

	}*/

	@SuppressWarnings("unchecked")
	@Override
	public List<RateComponentDO> loadDefaultRateComponentValue()
			throws CGSystemException {

		Session session = null;
		List<RateComponentDO> rateComponentDOs = null;
		Criteria criteria = null;
		try {
			LOGGER.trace("RateQuotationDAOImpl :: loadDefaultRateComponentValue() :: Start --------> ::::::");
			session = createSession();
			criteria = session.createCriteria(RateComponentDO.class);
			rateComponentDOs = criteria.list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : RateQuotationDAOImpl.loadDefaultRateComponentValue",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("RateQuotationDAOImpl :: loadDefaultRateComponentValue() :: End --------> ::::::");
		return rateComponentDOs;
	}

	@Override
	public RateQuotationFixedChargesConfigDO loadDefaultFixedChargesConfigValue(
			Integer rateQuotationId) throws CGSystemException {

		Session session = null;
		RateQuotationFixedChargesConfigDO configDO = null;
		Criteria criteria = null;
		try {
			LOGGER.trace("RateQuotationDAOImpl :: loadDefaultFixedChargesConfigValue() :: Start --------> ::::::");
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session
					.createCriteria(RateQuotationFixedChargesConfigDO.class);
			criteria.createAlias("rateQuotationDO", "rateQuotation");
			criteria.add(Restrictions.eq("rateQuotation.rateQuotationId",rateQuotationId));

			configDO = (RateQuotationFixedChargesConfigDO) criteria
					.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : RateQuotationDAOImpl.loadDefaultFixedChargesConfigValue",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("RateQuotationDAOImpl :: loadDefaultFixedChargesConfigValue() :: End --------> ::::::");
		return configDO;

	}

	@Override
	public RateQuotationDO saveOrUpdateRTOCharges(RateQuotationDO quotationDO,
			RateQuotationRTOChargesDO rtoChargesDO) throws CGSystemException {
		//Session session = getHibernateTemplate().getSessionFactory()
			//	.openSession();
		//Transaction tx = null;
		try {
			LOGGER.trace("RateQuotationDAOImpl :: saveOrUpdateRTOCharges() :: Start --------> ::::::");
	/*		if (StringUtil.isNull(quotationDO.getRateQuotationId())) {
				tx = session.beginTransaction();
				session.saveOrUpdate(quotationDO);
				tx.commit();
				session.flush();
			} else {
				session = getHibernateTemplate().getSessionFactory()
						.openSession();
				try {
					Query query = session
							.getNamedQuery(RateCommonConstants.QRY_DELETE_RTO_CHARGES);
					query.setParameter(RateCommonConstants.QUOTATION_ID,
							quotationDO.getRateQuotationId());
					query.executeUpdate();
				} catch (Exception e) {
					LOGGER.error("ERROR : RateQuotationDAOImpl:saveOrUpdateRTOCharges()::"
							+ e.getMessage());
				}
				tx = session.beginTransaction();
				session.saveOrUpdate(rtoChargesDO);
				tx.commit();
				session.flush();

			}*/
			saveOrUpdateBasicInfo(quotationDO);
		} catch (Exception e) {
			//tx.rollback();
			LOGGER.error("Error occured in RateQuotationDAOImpl :: saveOrUpdateRTOCharges()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		
		LOGGER.trace("RateQuotationDAOImpl :: saveOrUpdateRTOCharges() :: END --------> ::::::");
		return quotationDO;

	}

	@Override
	public RateQuotationRTOChargesDO loadRTOChargesDefault(String quotationId)
			throws CGSystemException {
		Session session = null;
		RateQuotationRTOChargesDO quotationRTOChargesDOs = null;
		Criteria criteria = null;
		try {
			LOGGER.trace("RateQuotationDAOImpl :: loadRTOChargesDefault() :: Start --------> ::::::");
			session = createSession();
			criteria = session.createCriteria(RateQuotationRTOChargesDO.class);
			criteria.createAlias("rateQuotationDO", "rateQuotation");
			criteria.add(Restrictions.eq("rateQuotation.rateQuotationId",
					Integer.parseInt(quotationId)));

			quotationRTOChargesDOs = (RateQuotationRTOChargesDO) criteria
					.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : RateQuotationDAOImpl.loadRTOChargesDefault",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("RateQuotationDAOImpl :: loadRTOChargesDefault() :: End --------> ::::::");
		return quotationRTOChargesDOs;

	}

	@Override
	public RateQuotationDO copyQuotation(RateQuotationTO rateQuotationTO,
			String quotationNo, RateQuotationDO copyQuotationDO) throws CGSystemException {

		//RateQuotationDO copyQuotationDO = null;
		Session session = null;

		Transaction tx = null;
		try {
			LOGGER.trace("RateQuotationDAOImpl :: copyQuotation() :: Start --------> ::::::");
			/*rateQuotationTO.setQuotationUsedFor(RateQuotationConstants.QUOTATION);
			List<RateQuotationDO> quotationDOs = searchQuotationDetails(rateQuotationTO);
			
			copyQuotationDO = quotationDOs.get(0);
			copyQuotationDO = createCopyQuotation(copyQuotationDO, quotationNo, RateQuotationConstants.CHAR_Q);
			copyQuotationDO.setQuotationCreatedBy(rateQuotationTO.getUpdatedBy());
			copyQuotationDO.setQuotationCreatedDate(Calendar.getInstance().getTime());
			copyQuotationDO.setUpdatedBy(null);
			copyQuotationDO.setUpdatedDate(null);*/
			
			session = createSession();
			tx = session.beginTransaction();
			copyQuotationDO = (RateQuotationDO) session.merge(copyQuotationDO);
			tx.commit();

		} catch (Exception e) {
			tx.rollback();
			LOGGER.error("Error occured in RateQuotationDAOImpl :: copyQuotation()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {

			session.close();
		}
		LOGGER.trace("RateQuotationDAOImpl :: copyQuotation() :: END --------> ::::::");
		return copyQuotationDO;

	}
	
	public RateQuotationDO createCopyQuotation(RateQuotationDO copyQuotationDO, String quotationNo, String process) throws CGSystemException{
		/*RateQuotationRTOChargesDO quotationRTOChargesDO = null;
		try{
			LOGGER.trace("RateQuotationDAOImpl :: createCopyQuotation() :: Start --------> ::::::");
			 Prepare Customer DO  
			CustomerDO customerNewDO = copyQuotationDO.getCustomer();
			if(process.equals(RateQuotationConstants.CHAR_Q)){
				customerNewDO.setCustomerId(null);
			}
			customerNewDO.setDtToBranch(CommonConstants.NO);
			if(StringUtil.isStringEmpty(customerNewDO.getCustomerCode())){
				customerNewDO.setCustomerCode(null);
			}
			AddressDO addressDO = copyQuotationDO.getCustomer().getAddressDO();
			ContactDO primaryContactDO = copyQuotationDO.getCustomer()
					.getPrimaryContactDO();
			ContactDO secondaryContactDO = copyQuotationDO.getCustomer()
					.getSecondaryContactDO();

			if (!StringUtil.isNull(addressDO)) {
				if(process.equals(RateQuotationConstants.CHAR_Q)){
					addressDO.setAddressId(null);
				}
				addressDO.setDtToBranch(CommonConstants.NO);
				customerNewDO.setAddressDO(addressDO);
			}

			if (!StringUtil.isNull(secondaryContactDO)) {
				if(process.equals(RateQuotationConstants.CHAR_Q)){
					secondaryContactDO.setContactId(null);
				}
				secondaryContactDO.setDtToBranch(CommonConstants.NO);
				customerNewDO.setSecondaryContactDO(secondaryContactDO);
			}

			if (!StringUtil.isNull(primaryContactDO)) {
				if(process.equals(RateQuotationConstants.CHAR_Q)){
					primaryContactDO.setContactId(null);
				}
				primaryContactDO.setDtToBranch(CommonConstants.NO);
				customerNewDO.setPrimaryContactDO(primaryContactDO);
			}

		copyQuotationDO.setCustomer(customerNewDO);

		 Prepare RateQuotationFixedCharges DO 
		Set<RateQuotationFixedChargesDO> rqFixedDO = copyQuotationDO
				.getRateFixedChargeDO();
		Set<RateQuotationFixedChargesDO> rqCopyFixedDO = new HashSet<RateQuotationFixedChargesDO>();
		for (RateQuotationFixedChargesDO fixedChargesDO : rqFixedDO) {
			fixedChargesDO.setRateQuotationFixedChargesId(null);
			fixedChargesDO.setRateQuotationDO(copyQuotationDO);
			rqCopyFixedDO.add(fixedChargesDO);

		}
		copyQuotationDO.setRateFixedChargeDO(rqCopyFixedDO);
		
		 Prepare RateQuotationProductCategoryHeader DO 
		Set<RateQuotationProductCategoryHeaderDO> rqProductCatDO = copyQuotationDO
				.getRateQuotationProductCategoryHeaderDO();
		Set<RateQuotationProductCategoryHeaderDO> rqCopyProductCatDO = new HashSet<RateQuotationProductCategoryHeaderDO>();
		Set<RateQuotationWeightSlabDO> rateQuotationWeightSlabDO = new HashSet<RateQuotationWeightSlabDO>();
		Set<RateQuotationSlabRateDO> rateQuotationSlabRateDO = new HashSet<RateQuotationSlabRateDO>();
		Set<RateQuotationSpecialDestinationDO> rateQuotationSpecialDestinationDO = new HashSet<RateQuotationSpecialDestinationDO>();

		for (RateQuotationProductCategoryHeaderDO categoryHeaderDO : rqProductCatDO) {
			categoryHeaderDO.setRateQuotationProductCategoryHeaderId(null);
			categoryHeaderDO.setRateQuotationDO(copyQuotationDO);
			for (RateQuotationWeightSlabDO quotationWeightSlabDO : categoryHeaderDO
					.getRateQuotationWeightSlabDO()) {
				quotationWeightSlabDO.setRateQuotationWeightSlabId(null);
				quotationWeightSlabDO
						.setRateQuotProductCategoryHeaderDO(categoryHeaderDO);
				for (RateQuotationSlabRateDO quotationSlabRateDO : quotationWeightSlabDO
						.getRateQuotationSlabRateDO()) {
					quotationSlabRateDO.setSlabRateId(null);
					quotationSlabRateDO
							.setRateQuotationWeightSlabDO(quotationWeightSlabDO);
					quotationSlabRateDO
							.setRateQuotationProductCategoryHeader(categoryHeaderDO);
					rateQuotationSlabRateDO.add(quotationSlabRateDO);
				}
				for (RateQuotationSpecialDestinationDO destinationDO : quotationWeightSlabDO
						.getRateQuotationSpecialDestinationDO()) {
					destinationDO
							.setRateQuotationSpecialDestinationId(null);
					destinationDO
							.setRateQuotationWeightSlabDO(quotationWeightSlabDO);
					destinationDO
							.setRateQuotationProductCategoryHeaderDO(categoryHeaderDO);
					rateQuotationSpecialDestinationDO.add(destinationDO);

				}
				rateQuotationWeightSlabDO.add(quotationWeightSlabDO);
			}
			rqCopyProductCatDO.add(categoryHeaderDO);
		}

		copyQuotationDO
				.setRateQuotationProductCategoryHeaderDO(rqCopyProductCatDO);

		if (copyQuotationDO.getRateQuotationType().equals(
				RateQuotationConstants.ECOMMERCE)) {
			List<RateQuotationCODChargeDO> chargeDOList = loadQuotationCodCharge(copyQuotationDO
					.getRateQuotationId());
			Set<RateQuotationCODChargeDO> chargeDOs = new HashSet<RateQuotationCODChargeDO>(
					chargeDOList);

			Set<RateQuotationCODChargeDO> rqCODChargeDO = new HashSet<RateQuotationCODChargeDO>();
			for (RateQuotationCODChargeDO do1 : chargeDOs) {
				do1.setRateQuotationCODChargeId(null);
				do1.setRateQuotationDO(copyQuotationDO);
				rqCODChargeDO.add(do1);

			}
			copyQuotationDO.setCodChargeDO(rqCODChargeDO);

		}

		 Prepare RateQuotationProductCategoryHeader DO 
		quotationRTOChargesDO = loadRTOChargesDefault(copyQuotationDO
				.getRateQuotationId().toString());
		if(!StringUtil.isNull(quotationRTOChargesDO)){
		quotationRTOChargesDO.setRateQuotationRTOChargesId(null);
		copyQuotationDO.setRateQuotationRtoChargesDO(quotationRTOChargesDO);
		quotationDO
				.setRateQuotationId(copyQuotationDO.getRateQuotationId());
		quotationRTOChargesDO.setRateQuotationDO(copyQuotationDO);
		}
		if(!StringUtil.isNull(copyQuotationDO.getRateQuotationRtoChargesDO())){
			quotationRTOChargesDO = copyQuotationDO.getRateQuotationRtoChargesDO();
			quotationRTOChargesDO.setRateQuotationRTOChargesId(null);
			copyQuotationDO.setRateQuotationRtoChargesDO(quotationRTOChargesDO);
			quotationRTOChargesDO.setRateQuotationDO(copyQuotationDO);
			}
		quotationDO
		.setRateQuotationId(copyQuotationDO.getRateQuotationId());
		RateQuotationFixedChargesConfigDO chargesConfigDO=loadDefaultFixedChargesConfigValue(copyQuotationDO
		.getRateQuotationId());
		if(!StringUtil.isNull(chargesConfigDO)){
			chargesConfigDO.setQuotaionFixedChargesConfigId(null);
			copyQuotationDO.setFixedChargesConfigDO(chargesConfigDO);
			quotationDO
					.setRateQuotationId(copyQuotationDO.getRateQuotationId());
			chargesConfigDO.setRateQuotationDO(copyQuotationDO);
			}
		Set<RateQuotationFixedChargesConfigDO> chargesConfigDO=copyQuotationDO.getFixedChargesConfigDO();
		if(!CGCollectionUtils.isEmpty(chargesConfigDO)){
		Set<RateQuotationFixedChargesConfigDO> fixedChargesConfigDO=new HashSet<RateQuotationFixedChargesConfigDO>();
		for(RateQuotationFixedChargesConfigDO fccDO :  chargesConfigDO){
			fccDO.setQuotaionFixedChargesConfigId(null);
			fixedChargesConfigDO.add(fccDO);
		}
		copyQuotationDO.setFixedChargesConfigDO(fixedChargesConfigDO);
		}
		
		
		copyQuotationDO.setRateQuotationId(null);
		
	

		if (copyQuotationDO.getRateQuotationType().equals(
				RateQuotationConstants.ECOMMERCE)) {
			copyQuotationDO
					.setRateQuotationType(RateQuotationConstants.ECOMMERCE);

		} else {
			copyQuotationDO
					.setRateQuotationType(RateQuotationConstants.NORMAL);
		}
		
		if(quotationNo.equals(copyQuotationDO.getRateQuotationNo())){
			copyQuotationDO.setQuotationUsedFor(RateQuotationConstants.CONTRACT);
		}
		else{
			copyQuotationDO.setQuotationUsedFor(RateQuotationConstants.QUOTATION);
			copyQuotationDO
			.setRateQuotationOriginatedfromType(RateQuotationConstants.COPY);
			copyQuotationDO.setStatus(RateQuotationConstants.NEW);
			copyQuotationDO.setRatequotationOriginatedFrom(quotationDO);
			copyQuotationDO.setQuotationCreatedDate(DateUtil.getCurrentDate());
			copyQuotationDO.setExcecutiveRemarks(null);
			copyQuotationDO.setApproversRemarks(null);
			copyQuotationDO.setApprovalRequired(null);
			copyQuotationDO.setApprovedAt(null);
		}
		copyQuotationDO.setRateQuotationNo(quotationNo);
		} catch (Exception e) {
			LOGGER.error("Error occured in RateQuotationDAOImpl :: createCopyQuotation()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} 
		LOGGER.trace("RateQuotationDAOImpl :: createCopyQuotation() :: END --------> ::::::");*/
		return copyQuotationDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CodChargeDO> getDeclaredValueCodCharge()
			throws CGSystemException {
		LOGGER.trace("RateQuotationDAOImpl :: getDeclaredValueCodCharge() :: Start --------> ::::::");

		Session session = null;
		List<CodChargeDO> codChargeDOs = null;
		Criteria criteria = null;
		try {
			session = createSession();
			criteria = session.createCriteria(CodChargeDO.class, "codChargeDO");
			criteria.addOrder(Order.asc("codChargeDO.codChargeId"));
			codChargeDOs = criteria.list();
		} catch (Exception e) {
			LOGGER.error("ERROR : RateQuotationDAOImpl.getDeclaredValueCodCharge", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("RateQuotationDAOImpl :: getDeclaredValueCodCharge() :: End --------> ::::::");
		return codChargeDOs;

	}


	@Override
	public RateQuotationDO saveOrUpdateEcomerceFixedCharges(
			RateQuotationDO quotationDO,
			Set<RateQuotationCODChargeDO> quotationCOD)
			throws CGSystemException {
		//Session session = getHibernateTemplate().getSessionFactory()
			//	.openSession();
		//Transaction tx = null;
		try {
			LOGGER.trace("RateQuotationDAOImpl :: saveOrUpdateEcomerceFixedCharges() :: Start --------> ::::::");
			/*try {
				Query query = session
						.getNamedQuery(RateCommonConstants.QRY_DELETE_FIXED_CHARGES);
				query.setParameter(RateCommonConstants.QUOTATION_ID,
						quotationDO.getRateQuotationId());
				query.executeUpdate();
			} catch (Exception e) {
				LOGGER.error("ERROR : RateQuotationDAOImpl:saveOrUpdateFixedCharges()::"
						+ e.getMessage());

			}

			try {
				Query query = session
						.getNamedQuery(RateCommonConstants.QRY_DELETE_COD_CHARGES);
				query.setParameter(RateCommonConstants.QUOTATION_ID,
						quotationDO.getRateQuotationId());
				query.executeUpdate();
			} catch (Exception e) {
				LOGGER.error("ERROR : RateQuotationDAOImpl:saveOrUpdateEcomerceFixedCharges()::"
						+ e.getMessage());

			}

			Query query = session
					.getNamedQuery(RateCommonConstants.QRY_UPDATE_QUOTATION);
			query.setParameter(RateCommonConstants.QUOTATION_ID,
					quotationDO.getRateQuotationId());
			query.setParameter(RateCommonConstants.APPROVERS_REMARKS,
					quotationDO.getApproversRemarks());
			query.setParameter(RateCommonConstants.EXCECUTIVE_REMARKS,
					quotationDO.getExcecutiveRemarks());
			query.executeUpdate();
			tx = session.beginTransaction();
			getHibernateTemplate().saveOrUpdateAll(
					quotationDO.getRateFixedChargeDO());
			for (RateQuotationCODChargeDO chargeDO : quotationCOD) {
				session.saveOrUpdate(chargeDO);
			}

			tx.commit();
			session.flush();*/
			saveOrUpdateBasicInfo(quotationDO);
		} catch (Exception e) {
			//tx.rollback();
			LOGGER.error("Error occured in RateQuotationDAOImpl :: saveOrUpdateEcomerceFixedCharges()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} 
		LOGGER.trace("RateQuotationDAOImpl :: saveOrUpdateEcomerceFixedCharges() :: END --------> ::::::");
		return quotationDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateQuotationCODChargeDO> loadQuotationCodCharge(
			Integer rateQuotationId) throws CGSystemException {
		List<RateQuotationCODChargeDO> codChargeDOs = null;
		try {
			String[] params = { RateQuotationConstants.QUOTATION_ID};
			Object[] values = { rateQuotationId };
			codChargeDOs = (List<RateQuotationCODChargeDO>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							RateQuotationConstants.QRY_GET_RATE_COD_CHARGES,
							params, values);
			
		} catch (Exception e) {
			LOGGER.error("ERROR : RateQuotationDAOImpl.loadQuotationCodCharge",
					e);
			throw new CGSystemException(e);
		} 
		LOGGER.trace("RateQuotationDAOImpl :: loadQuotationCodCharge() :: End --------> ::::::");
		return codChargeDOs;
	}

	@Override
	public RateContractDO createContract(RateQuotationTO rateQuotationTO,
			String contractNo, String quotationNo, RateContractDO contractDO) throws CGSystemException {
		//RateContractDO contractDO = new RateContractDO();
		Session session = null;
		Transaction tx = null;
		try {
			LOGGER.trace("RateQuotationDAOImpl :: createContract() :: Start --------> ::::::");
			//session = getHibernateTemplate().getSessionFactory().openSession();
			
			//copyQuotationDO = copyQuotation(rateQuotationTO, quotationNo);
			/*rateQuotationTO.setQuotationUsedFor(RateQuotationConstants.QUOTATION);
			List<RateQuotationDO> quotationDOs = searchQuotationDetails(rateQuotationTO);
			
			copyQuotationDO = quotationDOs.get(0);
			
			copyQuotationDO = createCopyQuotation(copyQuotationDO, quotationNo, RateQuotationConstants.CHAR_Q);
			//copyQuotationDO.setRateContractDO(contractDO);
			contractDO.setRateQuotationDO(copyQuotationDO);
			contractDO.setRateContractNo(contractNo);
			contractDO.setCustomerId(copyQuotationDO.getCustomer()
					.getCustomerId());
			contractDO.setCreatedBy(rateQuotationTO.getUpdatedBy());
			contractDO.setCreatedDate(DateUtil.getCurrentDate());
			if(copyQuotationDO.getRateQuotationType().equals(RateQuotationConstants.NORMAL)){
				contractDO.setRateContractType(RateContractConstants.NORMAL_CONTRACT);
			}else{
				contractDO.setRateContractType(RateContractConstants.ECCOMERCE_CONTRACT);	
			}
			contractDO.setContractStatus(RateContractConstants.CREATED);*/
			session = createSession();
			tx = session.beginTransaction();
			contractDO = (RateContractDO) session.merge(contractDO);
			
			/*Query query = session.getNamedQuery(RateQuotationConstants.QRY_UPDATE_CONTRACT_NO);
			
			query.setParameter(RateCommonConstants.CUSTOMER_ID,
					contractDO.getRateContractId());
			query.setParameter(RateCommonConstants.CONTRACT_NO,
					contractDO.getRateQuotationDO().getRateQuotationId());
			
			query.executeUpdate();*/
			
			tx.commit();
			//session.flush();

		} catch (Exception e) {
			tx.rollback();
			LOGGER.error("ERROR : RateQuotationDAOImpl.createContract", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("RateQuotationDAOImpl :: createContract() :: End --------> ::::::");
		return contractDO;
	}

	@SuppressWarnings("unchecked")
	public String getRHONameByCreatedBy(Integer userId) {
		LOGGER.trace("RateQuotationDAOImpl :: getRateIndustryType() :: Start --------> ::::::");
		List<String>  rhoName = null;
		rhoName = getHibernateTemplate().findByNamedQueryAndNamedParam(
				RateQuotationConstants.QRY_GET_RHO_BY_CREATEDBY,
				RateQuotationConstants.USER_ID, userId);
		LOGGER.trace("RateQuotationDAOImpl :: getRateIndustryType() :: Start --------> ::::::");
		if (rhoName.isEmpty()){
			return null;
		}else {
			return rhoName.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getStationNameByCreatedBy(Integer userId) throws CGSystemException{
		LOGGER.trace("RateQuotationDAOImpl :: getStationNameByCreatedBy() :: Start --------> ::::::");
		List<String>  stationName = null;
		try{
		stationName = getHibernateTemplate().findByNamedQueryAndNamedParam(
				RateQuotationConstants.QRY_GET_STATION_BY_CREATEDBY,
				RateQuotationConstants.USER_ID, userId);
		LOGGER.trace("RateQuotationDAOImpl :: getStationNameByCreatedBy() :: END --------> ::::::");
		if (stationName.isEmpty()){
			return null;
		}else {
			return stationName.get(0);
		}
		}catch (Exception e) {
			LOGGER.error("ERROR : RateQuotationDAOImpl.getRateIndustryType", e);
			throw new CGSystemException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getSalesOffcNameByCustomr(Integer custId) throws CGSystemException{
		LOGGER.trace("RateQuotationDAOImpl :: getSalesOffcNameByCustomr() :: Start --------> ::::::");
		List<String>  salesOffcName = null;
		try{
		salesOffcName = getHibernateTemplate().findByNamedQueryAndNamedParam(
				RateQuotationConstants.QRY_GET_SALES_OFFC_NAME_BY_CUSTOMER,
				RateQuotationConstants.CUST_ID, custId);
		LOGGER.trace("RateQuotationDAOImpl :: getSalesOffcNameByCustomr() :: END --------> ::::::");
		if (salesOffcName.isEmpty()){
			return null;
		}else {
			return salesOffcName.get(0);
		}
		}catch (Exception e) {
			LOGGER.error("ERROR : RateQuotationDAOImpl.getRateIndustryType", e);
			throw new CGSystemException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getSalesPersonNameByCustomr(Integer custId) throws CGSystemException{
		LOGGER.trace("RateQuotationDAOImpl :: getSalesPersonNameByCustomr() :: Start --------> ::::::");
		List<String>  salesPersonName = null;
		try{
		salesPersonName = getHibernateTemplate().findByNamedQueryAndNamedParam(
				RateQuotationConstants.QRY_GET_SALES_PERSON_NAME_BY_CUSTOMER,
				RateQuotationConstants.CUST_ID, custId);
		LOGGER.trace("RateQuotationDAOImpl :: getSalesPersonNameByCustomr() :: END --------> ::::::");
		if (salesPersonName.isEmpty()){
			return null;
		}else {
			return salesPersonName.get(0);
		}
		}catch (Exception e) {
			LOGGER.error("ERROR : RateQuotationDAOImpl.getRateIndustryType", e);
			throw new CGSystemException(e);
		}
	}




@Override
	public List<RateTaxComponentDO> loadDefaultRateTaxComponentValue(
			Integer stateId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<RateQuotationWrapperDO> searchQuotationForRegional(List<Integer> regionId, Integer rateIndustryCategryIdList, String effectiveFrom, String effectiveTo, String status, String quotationNo, String isEQApprover, Integer userRegionId) throws CGSystemException{
		
		List<RateQuotationWrapperDO> rateQuotatnDOs = null;
		List<RateQuotationWrapperDO> rateEcomQuotatnDOs = null;
		//List<RateIndustryCategoryDO> ricDOList = null;
		String[] pStatus = null;
		//String indCode = null;
		try {
			LOGGER.trace("RateQuotationDAOImpl :: searchQuotationForRegional() :: Start --------> ::::::");
			/*ricDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							RateCommonConstants.QRY_GET_INDUSTRY_CATEGORY,
							new String[] {RateQuotationConstants.RATE_INDUSTRY_CAT_ID_LIST},new Object[]{rateIndustryCategryIdList});
			if(!CGCollectionUtils.isEmpty(ricDOList)){
				for(RateIndustryCategoryDO rcDO: ricDOList){
					if(rcDO.getRateIndustryCategoryCode().equals(RateCommonConstants.RATE_BENCH_MARK_IND_CAT_CODE))
						indCode = rcDO.getRateIndustryCategoryCode();
				}
			}*/
			
			
			if(StringUtil.isStringEmpty(quotationNo)){
				if(!StringUtil.isNull(rateIndustryCategryIdList)){	
					String[] paramNames = {RateQuotationConstants.PARAM_REGION,
							RateQuotationConstants.RATE_INDUSTRY_CAT_ID_LIST, 
							RateQuotationConstants.STATUS,
							RateQuotationConstants.APPROVAL_REQUIRD_PARAM,
							RateQuotationConstants.PARAM_FROM_DATE,
							RateQuotationConstants.PARAM_TO_DATE,
							};
					if(status.equals(RateQuotationConstants.SUBMITTED)){
						Object[] values = { regionId, rateIndustryCategryIdList, 
							status, 
							new String[]{RateQuotationConstants.REGIONAL_OPERATOR, 
								RateQuotationConstants.REGIONAL_CORP},
							DateUtil.stringToDDMMYYYYFormat(effectiveFrom),
							DateUtil.stringToDDMMYYYYFormat(effectiveTo)
							};
						rateQuotatnDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(RateQuotationConstants.QRY_SEARCH_QUOTATION_FOR_REGION, paramNames, values);
						
						
					}else if(status.equals(RateQuotationConstants.APPROVED) || status.equals(RateQuotationConstants.REJECTED)){
						if(status.equals(RateQuotationConstants.APPROVED)){
							pStatus = new String[] {RateQuotationConstants.SUBMITTED,RateQuotationConstants.APPROVED};
						}else{
							pStatus = new String[] {RateQuotationConstants.REJECTED};
						}
						Object[] values = { regionId, rateIndustryCategryIdList, 
								pStatus, 
								new String[]{RateQuotationConstants.REGIONAL_OPERATOR, 
									RateQuotationConstants.REGIONAL_CORP},
								DateUtil.stringToDDMMYYYYFormat(effectiveFrom),
								DateUtil.stringToDDMMYYYYFormat(effectiveTo)
								};
							rateQuotatnDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(RateQuotationConstants.QRY_SRH_APRV_OR_REJ_QUOTATION_FOR_REGION, paramNames, values);
					}
				}
					
				//if(!StringUtil.isStringEmpty(indCode) && indCode.equals(RateCommonConstants.RATE_BENCH_MARK_IND_CAT_CODE)){
				if(!StringUtil.isStringEmpty(isEQApprover) && isEQApprover.equals(CommonConstants.YES)){
					String[] pNames = {RateQuotationConstants.PARAM_REGION,
							RateQuotationConstants.STATUS,
							RateQuotationConstants.APPROVAL_REQUIRD_PARAM,
							RateQuotationConstants.PARAM_FROM_DATE,
							RateQuotationConstants.PARAM_TO_DATE,
							};
					if(status.equals(RateQuotationConstants.SUBMITTED)){
						Object[] values = { userRegionId, 
							status, 
							new String[]{RateQuotationConstants.REGIONAL_OPERATOR, 
								RateQuotationConstants.REGIONAL_CORP},
							DateUtil.stringToDDMMYYYYFormat(effectiveFrom),
							DateUtil.stringToDDMMYYYYFormat(effectiveTo)
							};
						rateEcomQuotatnDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(RateQuotationConstants.QRY_SEARCH_ECOMMERCE_QUOTATION_FOR_REGION, pNames, values);
					}else if(status.equals(RateQuotationConstants.APPROVED) || status.equals(RateQuotationConstants.REJECTED)){
							if(status.equals(RateQuotationConstants.APPROVED)){
								pStatus = new String[] {RateQuotationConstants.SUBMITTED,RateQuotationConstants.APPROVED};
							}else{
								pStatus = new String[] {RateQuotationConstants.REJECTED};
							}
						Object[] values = { userRegionId, 
								pStatus,
								new String[]{RateQuotationConstants.REGIONAL_OPERATOR, 
									RateQuotationConstants.REGIONAL_CORP},
								DateUtil.stringToDDMMYYYYFormat(effectiveFrom),
								DateUtil.stringToDDMMYYYYFormat(effectiveTo)
								};
						rateEcomQuotatnDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(RateQuotationConstants.QRY_SRH_APRV_OR_REJ_ECOM_QUOTATION_FOR_REGION, pNames, values);
					}
					
				}
			}
			else{
				if(!StringUtil.isNull(rateIndustryCategryIdList)){
					String[] paramNames = {RateQuotationConstants.PARAM_REGION,
							RateQuotationConstants.RATE_INDUSTRY_CAT_ID_LIST, 
							RateQuotationConstants.APPROVAL_REQUIRD_PARAM,
							RateQuotationConstants.QUOTATION_NO
							};
					Object[] values = { regionId, rateIndustryCategryIdList, 
							new String[]{RateQuotationConstants.REGIONAL_OPERATOR, 
								RateQuotationConstants.REGIONAL_CORP},
								quotationNo	
							};
					rateQuotatnDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(RateQuotationConstants.QRY_SEARCH_QUOTATION_FOR_REGION_BY_QUOT_NO, paramNames, values);
				}
					//if(!StringUtil.isStringEmpty(indCode) && indCode.equals(RateCommonConstants.RATE_BENCH_MARK_IND_CAT_CODE)){
				if(!StringUtil.isStringEmpty(isEQApprover) && isEQApprover.equals(CommonConstants.YES)){
					String[] pNames = {RateQuotationConstants.PARAM_REGION,
							RateQuotationConstants.APPROVAL_REQUIRD_PARAM,
							RateQuotationConstants.QUOTATION_NO
							};
					Object[] vals = { userRegionId,
							new String[]{RateQuotationConstants.REGIONAL_OPERATOR, 
								RateQuotationConstants.REGIONAL_CORP},
								quotationNo	
							};
					rateEcomQuotatnDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(RateQuotationConstants.QRY_SEARCH_ECOMMERCE_QUOTATION_FOR_REGION_BY_QUOT_NO, pNames, vals);
				}
			}
			if(StringUtil.isEmptyColletion(rateQuotatnDOs) && StringUtil.isEmptyColletion(rateEcomQuotatnDOs)){
				return null;
			}else if(StringUtil.isEmptyColletion(rateQuotatnDOs) && !StringUtil.isEmptyColletion(rateEcomQuotatnDOs)){
				return rateEcomQuotatnDOs;
			}else if(!StringUtil.isEmptyColletion(rateQuotatnDOs) && !StringUtil.isEmptyColletion(rateEcomQuotatnDOs)){
				for(RateQuotationWrapperDO rqwDO: rateEcomQuotatnDOs){
					rateQuotatnDOs.add(rqwDO);
				}
			}
			
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : RateQuotationDAOImpl.searchQuotationForRegional",
					e);
			throw new CGSystemException(e);
		} 
		LOGGER.trace("RateQuotationDAOImpl :: searchQuotationForRegional() :: END --------> ::::::");
		return rateQuotatnDOs;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<RateQuotationWrapperDO> getQuotationDtlsByUserIdDateAndStatus(Integer userId,Date fromDate,Date toDate,String status) throws CGSystemException{
		
		try {
			LOGGER.trace("RateQuotationDAOImpl :: getQuotationDtlsByUserIdDateAndStatus() :: Start --------> ::::::");
			List<RateQuotationWrapperDO>  rateQuotatnDOList = null;
			String[] paramNames = { RateQuotationConstants.USER_ID,
									RateQuotationConstants.FROM_DATE, 
									RateQuotationConstants.TO_DATE,
									RateQuotationConstants.STATUS
									};
			Object[] values = { userId, fromDate, toDate, status};
			
			rateQuotatnDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(RateQuotationConstants.QRY_GET_QUOTATION_DTLS_BY_ID_DATE_STATUS, paramNames, values);
			LOGGER.trace("RateQuotationDAOImpl :: getQuotationDtlsByUserIdDateAndStatus() :: END --------> ::::::");
			if (rateQuotatnDOList.isEmpty()){
				return null;
			}else {
				return rateQuotatnDOList;
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : RateQuotationDAOImpl.getQuotationDtlsByUserIdDateAndStatus",
					e);
			throw new CGSystemException(e);
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<RateQuotationWrapperDO> getQuotationDtlsByUserIdStatusAndQuotatnNo(Integer userId,String quotatnNo,String status) throws CGSystemException{
		
		try {
			LOGGER.trace("RateQuotationDAOImpl :: getQuotationDtlsByUserIdStatusAndQuotatnNo() :: Start --------> ::::::");
			List<RateQuotationWrapperDO>  rateQuotatnDOList = null;
			String[] paramNames = { RateQuotationConstants.USER_ID,
								RateQuotationConstants.QUOTATION_NO,
									RateQuotationConstants.STATUS
									};
			Object[] values = { userId,quotatnNo,status};

			rateQuotatnDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(RateQuotationConstants.QRY_GET_QUOTATION_DTLS_BY_ID_QUOTATION_NO_STATUS, paramNames, values);
			LOGGER.trace("RateQuotationDAOImpl :: getQuotationDtlsByUserIdStatusAndQuotatnNo() :: END --------> ::::::");
			if (rateQuotatnDOList.isEmpty()){
				return null;
			}else {
				return rateQuotatnDOList;
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : RateQuotationDAOImpl.getQuotationDtlsByUserIdStatusAndQuotatnNo",
					e);
			throw new CGSystemException(e);
		}
	}



@SuppressWarnings("unchecked")
@Override
	public List<OfficeDO> getAllOfficesUnderLoggedInRHO(Integer loggedInId)
			throws CGSystemException{

		Session session = null;
		List<OfficeDO> officeDOs = null;
		Criteria criteria = null;
		try {
			LOGGER.trace("RateQuotationDAOImpl :: getAllOfficesUnderLoggedInRHO() :: Start --------> ::::::");
			session = createSession();
			criteria = session.createCriteria(OfficeDO.class);
			criteria.add(Restrictions.eq("reportingRHO", loggedInId));
			officeDOs = criteria.list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : RateQuotationDAOImpl.getAllOfficesUnderLoggedInRHO",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("RateQuotationDAOImpl :: getAllOfficesUnderLoggedInRHO() :: End --------> ::::::");
		return officeDOs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserDO> getAllUsersOfOffice(Integer officeId)
			throws CGSystemException{
		LOGGER.trace("RateQuotationDAOImpl :: getAllUsersOfOffice() :: Start --------> ::::::");
		List<UserDO> userDOList = null;
		try{
		userDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				RateQuotationConstants.QRY_GET_USERDO_LIST_BY_OFFICEID,
				RateQuotationConstants.OFFIC_ID, officeId);
		LOGGER.trace("RateQuotationDAOImpl :: getAllUsersOfOffice() :: END --------> ::::::");
		if (userDOList.isEmpty()){
			return null;
		}else {
			return userDOList;
		}
		}catch (Exception e) {
			LOGGER.error("ERROR : RateQuotationDAOImpl.getRateIndustryType", e);
			throw new CGSystemException(e);
		}
	}
	

	public RateQuotationProductCategoryHeaderDO saveOrUpdateRateQuotProposedRates(
			RateQuotationProductCategoryHeaderDO rqpchDO, RateQuotationDO rqDO, Integer originSector, Boolean weightSlab)
			throws CGSystemException {

		LOGGER.trace("RateQuotationDAOImpl :: saveOrUpdateRateQuotProposedRates() :: Start --------> ::::::");
		Session session = null;
		Transaction tx = null;
		//boolean isRBMCreated = Boolean.FALSE;

		try{
			
			session = createSession();
			tx = session.beginTransaction();
			
			deleteRecords(rqpchDO, originSector, weightSlab, session);
			
			if(StringUtil.isNull(rqDO.getRateQuotationId()) || rqDO.getRateQuotationId() == 0){
			rqDO = (RateQuotationDO)session.merge(rqDO);
				if(!StringUtil.isNull(rqDO.getRateQuotationId()) && rqDO.getRateQuotationId() != 0){
					for(RateQuotationProductCategoryHeaderDO rqpDO : rqDO.getRateQuotationProductCategoryHeaderDO()){
						rqpchDO = rqpDO;
					}
				}else{
					rqpchDO = null;
				}
			}else{	
			if(!StringUtil.isNull(rqpchDO)){
				rqpchDO = (RateQuotationProductCategoryHeaderDO)session.merge(rqpchDO);
				if(StringUtil.isNull(rqpchDO.getRateQuotationProductCategoryHeaderId()) && rqpchDO.getRateQuotationProductCategoryHeaderId() == 0){
					rqpchDO = null;
				}
			}
			}
			tx.commit();
		}
		catch(Exception e){			
			if(!StringUtil.isNull(tx)){
				tx.rollback();
			}
			LOGGER.error("Exception Occured in::RateQuotationDAOImpl::saveOrUpdateRateQuotProposedRates :: " + e);
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}		
		LOGGER.trace("RateQuotationDAOImpl :: saveOrUpdateRateQuotProposedRates() :: End --------> ::::::");
		return rqpchDO;
	}
	
	private void deleteRecords(RateQuotationProductCategoryHeaderDO rqpchDO, Integer originSector, Boolean weightSlab,Session session) throws CGSystemException{
		
		LOGGER.trace("RateQuotationDAOImpl :: deleteRecords() :: Start --------> ::::::");
		 
	//	 Session session = null;
		
		
			try{
				//session = createSession();
				Query qry=null;
				if(weightSlab == Boolean.TRUE && !StringUtil.isNull(rqpchDO.getRateQuotationProductCategoryHeaderId()) && rqpchDO.getRateQuotationProductCategoryHeaderId() != 0){
					
					qry = session.getNamedQuery(RateQuotationConstants.QRY_DELETE_SLAB_RATE);
					qry.setInteger(RateQuotationConstants.PARAM_RATE_QUOT_PROD_CAT_HEADER_ID,rqpchDO.getRateQuotationProductCategoryHeaderId());
					qry.executeUpdate();
					
					qry = session.getNamedQuery(RateQuotationConstants.QRY_DELETE_SPECIAL_DEST_SLAB_RATE);
					qry.setInteger(RateQuotationConstants.PARAM_RATE_QUOT_PROD_CAT_HEADER_ID,rqpchDO.getRateQuotationProductCategoryHeaderId());
					qry.executeUpdate();
					
					qry = session.getNamedQuery(RateQuotationConstants.QRY_DELETE_WEIGHT_SLABS);
					qry.setInteger(RateQuotationConstants.PARAM_RATE_QUOT_PROD_CAT_HEADER_ID,rqpchDO.getRateQuotationProductCategoryHeaderId());
					qry.executeUpdate();
					}
				else if(originSector != 0 && !StringUtil.isNull(originSector) && !StringUtil.isNull(rqpchDO.getRateQuotationProductCategoryHeaderId()) && rqpchDO.getRateQuotationProductCategoryHeaderId() != 0){
					qry = session.getNamedQuery(RateQuotationConstants.QRY_DELETE_ORG_REG_SLAB_RATE);
					qry.setInteger(RateQuotationConstants.PARAM_RATE_QUOT_ORG_REG, originSector);
					qry.setInteger(RateQuotationConstants.PARAM_RATE_QUOT_PROD_CAT_HEADER_ID,rqpchDO.getRateQuotationProductCategoryHeaderId());
					qry.executeUpdate();
					
					qry = session.getNamedQuery(RateQuotationConstants.QRY_DELETE_ORG_REG_SPL_DEST_SLAB_RATE);
					qry.setInteger(RateQuotationConstants.PARAM_RATE_QUOT_ORG_REG, originSector);
					qry.setInteger(RateQuotationConstants.PARAM_RATE_QUOT_PROD_CAT_HEADER_ID,rqpchDO.getRateQuotationProductCategoryHeaderId());
					qry.executeUpdate();
					
					
				}else if(!StringUtil.isNull(rqpchDO.getRateQuotationProductCategoryHeaderId()) && rqpchDO.getRateQuotationProductCategoryHeaderId() != 0){
				qry = session.getNamedQuery(RateQuotationConstants.QRY_DELETE_SLAB_RATE);
				qry.setInteger(RateQuotationConstants.PARAM_RATE_QUOT_PROD_CAT_HEADER_ID,rqpchDO.getRateQuotationProductCategoryHeaderId());
				qry.executeUpdate();
				
				qry = session.getNamedQuery(RateQuotationConstants.QRY_DELETE_SPECIAL_DEST_SLAB_RATE);
				qry.setInteger(RateQuotationConstants.PARAM_RATE_QUOT_PROD_CAT_HEADER_ID,rqpchDO.getRateQuotationProductCategoryHeaderId());
				qry.executeUpdate();
				}
				
				
					
				
			}catch (Exception e) {
				LOGGER.error("ERROR : RateQuotationDAOImpl.deleteRecords", e);
				throw new CGSystemException(e);
			}					
			LOGGER.trace("RateQuotationDAOImpl :: deleteRecords() :: End --------> ::::::");
			//return rateQuotationWtslabDOList;
		
	}
	
   @SuppressWarnings("unchecked")
public List<RateQuotationWeightSlabDO> getRateQuotationWeightSlabs(Integer quotationId, Integer productCatId) throws CGSystemException{
	   
	   
	   LOGGER.trace("RateQuotationDAOImpl :: getRateQuotationWeightSlabs() :: Start --------> ::::::");
		 
		 //Session session = null;
		List<RateQuotationWeightSlabDO> rateQuotationWtslabDOList = null;
		
			try{
				
				String query = RateQuotationConstants.QRY_GET_RATE_QUOT_WT_SLABS_BY_PROD_CAT;
				
				String params[] = {RateQuotationConstants.PARAM_RATE_PROD_CAT_ID,RateQuotationConstants.PARAM_RATE_QUOT_ID};
				
				Object values[] = {productCatId,quotationId};
				
				rateQuotationWtslabDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
				
				
			}catch (Exception e) {
				LOGGER.error("ERROR : RateQuotationDAOImpl.getRateQuotationWeightSlabs", e);
				throw new CGSystemException(e);
			}
			LOGGER.trace("RateQuotationDAOImpl :: getRateQuotationWeightSlabs() :: End --------> ::::::");
			return rateQuotationWtslabDOList;
   }
	
   @SuppressWarnings("unchecked")
   public List<RateQuotationSlabRateDO> getRateQuotationSlabRates(Integer quotationId, Integer productCatId) throws CGSystemException{
   	   
   	   
   	   LOGGER.trace("RateQuotationDAOImpl :: getRateQuotationSlabRates() :: Start --------> ::::::");
   		 
   		List<RateQuotationSlabRateDO> rateQuotationSlabRateDOList = null;
   		
   			try{
   				
   				String query = RateQuotationConstants.QRY_GET_RATE_QUOT_SLAB_RATES;
   				
   				String params[] = {RateQuotationConstants.PARAM_RATE_PROD_CAT_ID,RateQuotationConstants.PARAM_RATE_QUOT_ID};
   				
   				Object values[] = {productCatId,quotationId};
   				
   				rateQuotationSlabRateDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
   				
   				
   			}catch (Exception e) {
   				LOGGER.error("ERROR : RateQuotationDAOImpl.getRateQuotationSlabRates", e);
   				throw new CGSystemException(e);
   			}
   			LOGGER.trace("RateQuotationDAOImpl :: getRateQuotationSlabRates() :: End --------> ::::::");
   			return rateQuotationSlabRateDOList;
      }
	
   
   @SuppressWarnings("unchecked")
   public List<RateQuotationProductCategoryHeaderDO> getRateQuotationProductCatHeader(Integer quotationId, Integer productCatId) throws CGSystemException{
   	   
   	   
   	   LOGGER.trace("RateQuotationDAOImpl :: getRateQuotationProductCatHeader() :: Start --------> ::::::");
   		 
   		List<RateQuotationProductCategoryHeaderDO> rateQuotationProductCategoryHeaderDOList = null;
   		
   			try{
   				
   				String query = RateQuotationConstants.QRY_GET_RATE_QUOT_PROD_CAT_HEADER;
   				
   				String params[] = {RateQuotationConstants.PARAM_RATE_PROD_CAT_ID,RateQuotationConstants.PARAM_RATE_QUOT_ID};
   				
   				Object values[] = {productCatId,quotationId};
   				
   				rateQuotationProductCategoryHeaderDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
   				
   			}catch (Exception e) {
   				LOGGER.error("ERROR : RateQuotationDAOImpl.getRateQuotationProductCatHeader", e);
   				throw new CGSystemException(e);
   			}
   			LOGGER.trace("RateQuotationDAOImpl :: getRateQuotationProductCatHeader() :: End --------> ::::::");
   			return rateQuotationProductCategoryHeaderDOList;
      }

@SuppressWarnings("unchecked")
@Override
public List<RateQuotationProductCategoryHeaderDO> getRateQuotProposedRateDetailsByProdHeader(
		Integer prodCatHeaderId) throws CGSystemException {
	LOGGER.trace("RateQuotationDAOImpl :: getRateQuotProposedRateDetailsByProdHeader() :: Start --------> ::::::");
		 
		List<RateQuotationProductCategoryHeaderDO> rateQuotationProductCategoryHeaderDOList = null;
		
			try{
				
				String query = RateQuotationConstants.QRY_GET_RATE_QUOT_SLAB_RATE_BY_PROD_CAT_HEADER;
				
				String params[] = {RateQuotationConstants.PARAM_RATE_QUOT_PROD_CAT_HEADER_ID};
				
				Object values[] = {prodCatHeaderId};
				
				rateQuotationProductCategoryHeaderDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
				
				
			}catch (Exception e) {
				LOGGER.error("ERROR : RateQuotationDAOImpl.getRateQuotProposedRateDetailsByProdHeader", e);
				throw new CGSystemException(e);
			}
			LOGGER.trace("RateQuotationDAOImpl :: getRateQuotProposedRateDetailsByProdHeader() :: End --------> ::::::");
			return rateQuotationProductCategoryHeaderDOList;
}

@Override
public RateQuotationRTOChargesDO saveOrUpdateQuotationRTOCharges(
		RateQuotationDO quotationDO, RateQuotationRTOChargesDO rtoChargesDO)
		throws CGSystemException {
	Session session = null;
	Transaction tx = null;
	RateQuotationRTOChargesDO rtoChrgDO = null;
	try {
		LOGGER.trace("RateQuotationDAOImpl :: saveOrUpdateQuotationRTOCharges() :: Start --------> ::::::");
		session = createSession();
		tx = session.beginTransaction();
		if (StringUtil.isNull(quotationDO.getRateQuotationId())|| (quotationDO.getRateQuotationId() == 0)) {
			quotationDO = (RateQuotationDO) session.merge(quotationDO);
			tx.commit();
			session.flush();
			rtoChrgDO = new RateQuotationRTOChargesDO();
			rtoChrgDO =  quotationDO.getRateQuotationRtoChargesDO();
		} else {
			try {
				Query query = session
						.getNamedQuery(RateCommonConstants.QRY_DELETE_RTO_CHARGES);
				query.setParameter(RateCommonConstants.QUOTATION_ID,
						quotationDO.getRateQuotationId());
				query.executeUpdate();
			} catch (Exception e) {
				LOGGER.error("ERROR : RateQuotationDAOImpl:saveOrUpdateQuotationRTOCharges()::"
						+ e.getMessage());
			}
			tx = session.beginTransaction();
			rtoChrgDO = new RateQuotationRTOChargesDO();
			rtoChrgDO = (RateQuotationRTOChargesDO) session.merge(rtoChargesDO);
			tx.commit();
			session.flush();

		}
	} catch (Exception e) {
		tx.rollback();
		LOGGER.error("Error occured in RateQuotationDAOImpl :: saveOrUpdateQuotationRTOCharges()..:"
				+ e.getMessage());
		throw new CGSystemException(e);
	} finally {
		session.close();
	}
	LOGGER.trace("RateQuotationDAOImpl :: saveOrUpdateQuotationRTOCharges() :: END --------> ::::::");
	return rtoChrgDO;
}

@Override
public void submitQuotation(RateQuotationDO quotationDO) throws CGSystemException {
	
	Session session = null;
	try{
		LOGGER.trace("RateQuotationDAOImpl :: submitQuotation() :: Start --------> ::::::");
		Query qry=null;
		session = createSession();
		
		qry = session.getNamedQuery(RateQuotationConstants.UPDATE_RATE_QUOTATION_STATUS);
		qry.setString("status", quotationDO.getStatus());
		qry.setString("approvalRequired", quotationDO.getApprovalRequired());
		qry.setInteger("rateQuotationId", quotationDO.getRateQuotationId());
		
		qry.executeUpdate();
		
	}catch(Exception e){
		LOGGER.error("Error occured in RateQuotationDAOImpl :: submitQuotation()..:"
				+ e.getMessage());
		throw new CGSystemException(e);
	}
	finally {
		session.close();
	}
	LOGGER.trace("RateQuotationDAOImpl :: submitQuotation() :: END --------> ::::::");
}

@SuppressWarnings("unchecked")
@Override
public List<RateQuotationWrapperDO> rateQuotationListViewDetails(Integer userId, Integer[] region, Integer[] city, String fromDate, String toDate, String quotationNo, String status, String type, String officeType) throws CGSystemException {
	
	
	List<RateQuotationWrapperDO> rqwDOList = null;
	try{
		LOGGER.trace("RateQuotationDAOImpl :: rateQuotationListViewDetails() :: Start --------> ::::::");
		
		if(StringUtil.isStringEmpty(quotationNo)){
		
		if(officeType.equalsIgnoreCase(CommonConstants.OFF_TYPE_CORP_OFFICE)){	
		/*String query = RateQuotationConstants.QRY_GET_LIST_VIEW_RATE_QUOTATION_FOR_CO;
		
		String params[] = {RateQuotationConstants.PARAM_FROM_DATE, RateQuotationConstants.PARAM_TO_DATE, RateQuotationConstants.PARAM_STATUS};
		
		Object values[] = {DateUtil.stringToDDMMYYYYFormat(fromDate),DateUtil.stringToDDMMYYYYFormat(toDate),status};
		
		rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);*/
			if(StringUtil.isEmpty(city)){
				String query = RateQuotationConstants.QRY_GET_LIST_VIEW_RATE_QUOTATION_ALL_RO;
				
				String params[] = {RateQuotationConstants.PARAM_REGION, RateQuotationConstants.PARAM_FROM_DATE, RateQuotationConstants.PARAM_TO_DATE, RateQuotationConstants.PARAM_STATUS};
				
				Object values[] = {region,DateUtil.stringToDDMMYYYYFormat(fromDate),DateUtil.stringToDDMMYYYYFormat(toDate),status};
				
				rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
				}else{
					String query = RateQuotationConstants.QRY_GET_LIST_VIEW_RATE_QUOTATION_ALL_RO_WITH_CITY;
					
					String params[] = {RateQuotationConstants.PARAM_CITY, RateQuotationConstants.PARAM_FROM_DATE, RateQuotationConstants.PARAM_TO_DATE, RateQuotationConstants.PARAM_STATUS};
					
					Object values[] = {city,DateUtil.stringToDDMMYYYYFormat(fromDate),DateUtil.stringToDDMMYYYYFormat(toDate),status};
					
					rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
				}
		
		}else if(officeType.equalsIgnoreCase(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){
			if(StringUtil.isEmpty(city)){
			String query = RateQuotationConstants.QRY_GET_LIST_VIEW_RATE_QUOTATION_ALL_RO;
			
			String params[] = {RateQuotationConstants.PARAM_REGION, RateQuotationConstants.PARAM_FROM_DATE, RateQuotationConstants.PARAM_TO_DATE, RateQuotationConstants.PARAM_STATUS};
			
			Object values[] = {region,DateUtil.stringToDDMMYYYYFormat(fromDate),DateUtil.stringToDDMMYYYYFormat(toDate),status};
			
			rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
			}else{
				String query = RateQuotationConstants.QRY_GET_LIST_VIEW_RATE_QUOTATION_ALL_RO_WITH_CITY;
				
				String params[] = {RateQuotationConstants.PARAM_CITY, RateQuotationConstants.PARAM_FROM_DATE, RateQuotationConstants.PARAM_TO_DATE, RateQuotationConstants.PARAM_STATUS};
				
				Object values[] = {city,DateUtil.stringToDDMMYYYYFormat(fromDate),DateUtil.stringToDDMMYYYYFormat(toDate),status};
				
				rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
			}
		}else if(officeType.equalsIgnoreCase(CommonConstants.OFF_TYPE_HUB_OFFICE)){
			
				String query = RateQuotationConstants.QRY_GET_LIST_VIEW_RATE_QUOTATION_ALL_HO;
				
				String params[] = {RateQuotationConstants.USER_ID,RateQuotationConstants.PARAM_CITY, RateQuotationConstants.PARAM_FROM_DATE, RateQuotationConstants.PARAM_TO_DATE, RateQuotationConstants.PARAM_STATUS};
				
				Object values[] = {userId,city,DateUtil.stringToDDMMYYYYFormat(fromDate),DateUtil.stringToDDMMYYYYFormat(toDate),status};
				
				rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
			
		}
		else{
			
			String query = RateQuotationConstants.QRY_GET_LIST_VIEW_RATE_QUOTATION;
			
			String params[] = {RateQuotationConstants.USER_ID, RateQuotationConstants.PARAM_FROM_DATE, RateQuotationConstants.PARAM_TO_DATE, RateQuotationConstants.PARAM_STATUS};
			
			Object values[] = {userId,DateUtil.stringToDDMMYYYYFormat(fromDate),DateUtil.stringToDDMMYYYYFormat(toDate),status};
			
			rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
			
		}
		}else{

			
			if(officeType.equalsIgnoreCase(CommonConstants.OFF_TYPE_CORP_OFFICE)){
			String query = RateQuotationConstants.QRY_GET_RATE_QUOTATION_FOR_CO;
			
			String params[] = {RateQuotationConstants.PARAM_QUOTATION_NO};
			
			Object values[] = {quotationNo};
			
			rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
			}else if(officeType.equalsIgnoreCase(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){
				
				String query = RateQuotationConstants.QRY_GET_RATE_QUOTATION_FOR_ALL_RO;
				
				String params[] = {RateQuotationConstants.PARAM_REGION, RateQuotationConstants.PARAM_QUOTATION_NO};
				
				Object values[] = {region,quotationNo};
				
				rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
			}else if(officeType.equalsIgnoreCase(CommonConstants.OFF_TYPE_HUB_OFFICE)){
				String query = RateQuotationConstants.QRY_GET_RATE_QUOTATION_FOR_ALL_HO;
						
				String params[] = {RateQuotationConstants.USER_ID,RateQuotationConstants.PARAM_CITY, RateQuotationConstants.PARAM_QUOTATION_NO};
						
				Object values[] = {userId,city,quotationNo};

				rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
				
			}else{
				
				String query = RateQuotationConstants.QRY_GET_RATE_QUOTATION_FOR_USER;
				
				String params[] = {RateQuotationConstants.USER_ID, RateQuotationConstants.PARAM_QUOTATION_NO};
				
				Object values[] = {userId,quotationNo};
				
				rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
			}			

		}
		
		
	}catch(Exception e){
		LOGGER.error("Error occured in RateQuotationDAOImpl :: rateQuotationListViewDetails()..:"
				+ e.getMessage());
		throw new CGSystemException(e);
	}
	LOGGER.trace("RateQuotationDAOImpl :: rateQuotationListViewDetails() :: END --------> ::::::");
	return rqwDOList;
	}

public boolean approveRejectDomesticQuotation(String[] quotationNoOfTypeRO,String[] quotationNoArrayOfTypeRC,String opName,String approver) throws CGSystemException {

	Session session = null;
	boolean isApproved = Boolean.FALSE;
	Transaction tx = null;
	
	try{
		LOGGER.trace("RateQuotationDAOImpl :: approveRejectDomesticQuotation() :: Start --------> ::::::");
		session = createSession();
		tx = session.beginTransaction();
		Query query = session.getNamedQuery(RateQuotationConstants.QRY_APPROVE_REJECT_DOMESTIC_QUOTATION);
		if(!StringUtil.isEmpty(quotationNoOfTypeRO) && !StringUtil.isNull(quotationNoOfTypeRO[0])){
			query.setParameterList(RateQuotationConstants.QUOTATION_NO, quotationNoOfTypeRO);
			query.setString(RateQuotationConstants.QUOTATION_USED_FOR, RateQuotationConstants.CHAR_Q);
			query.setString(RateQuotationConstants.APPROVED_AT_PARAM, RateQuotationConstants.REGIONAL_OPERATOR);
			if(opName.equalsIgnoreCase(RateQuotationConstants.APPROVE_RATE)){
				query.setString(RateQuotationConstants.STATUS, RateQuotationConstants.APPROVED);
			}else if(opName.equalsIgnoreCase(RateQuotationConstants.REJECT_RATE)){
				query.setString(RateQuotationConstants.STATUS, RateQuotationConstants.REJECTED);
			}
			query.executeUpdate();
		}
		if(!StringUtil.isEmpty(quotationNoArrayOfTypeRC) && !StringUtil.isNull(quotationNoArrayOfTypeRC[0])){
			query.setParameterList(RateQuotationConstants.QUOTATION_NO, quotationNoArrayOfTypeRC);
			query.setString(RateQuotationConstants.QUOTATION_USED_FOR, RateQuotationConstants.CHAR_Q);
			if(opName.equalsIgnoreCase(RateQuotationConstants.APPROVE_RATE)){
				if(approver.equals(RateQuotationConstants.TYPE_R)){
					query.setString(RateQuotationConstants.STATUS, RateQuotationConstants.SUBMITTED);
					query.setString(RateQuotationConstants.APPROVED_AT_PARAM, RateQuotationConstants.REGIONAL_OPERATOR);
				}else if(approver.equals(RateQuotationConstants.TYPE_C)){
					query.setString(RateQuotationConstants.STATUS, RateQuotationConstants.APPROVED);
					query.setString(RateQuotationConstants.APPROVED_AT_PARAM, RateQuotationConstants.REGIONAL_CORP);
				}
			}else if(opName.equalsIgnoreCase(RateQuotationConstants.REJECT_RATE)){
				query.setString(RateQuotationConstants.STATUS, RateQuotationConstants.REJECTED);
				if(approver.equals(RateQuotationConstants.TYPE_R)){
					query.setString(RateQuotationConstants.APPROVED_AT_PARAM, RateQuotationConstants.REGIONAL_OPERATOR);
				}else if(approver.equals(RateQuotationConstants.TYPE_C)){
					query.setString(RateQuotationConstants.APPROVED_AT_PARAM, RateQuotationConstants.REGIONAL_CORP);
				}
			}
			query.executeUpdate();
		}
		
		tx.commit();
		isApproved = Boolean.TRUE;
	}catch (Exception e) {
		if (tx != null){
			tx.rollback();
		}
		LOGGER.error("Error occured in RateQuotaionDAOImpl :: approveRejectDomesticQuotation()..:"
				+ e.getMessage());
		throw new CGSystemException(e);
	} finally {
		session.close();
	}
	LOGGER.trace("RateQuotationDAOImpl :: approveRejectDomesticQuotation() :: END --------> ::::::");
	return isApproved;
}

@SuppressWarnings("unchecked")
public List<RegionRateBenchMarkDiscountDO> checkEmpIdRegionalApprovr(Integer empId)  throws CGSystemException{
	LOGGER.trace("RateQuotationDAOImpl :: checkEmpIdRegionalApprovr() :: Start --------> ::::::");
	try{
	List<RegionRateBenchMarkDiscountDO> regionRateBnchMarkDiscontList = getHibernateTemplate().findByNamedQueryAndNamedParam(RateQuotationConstants.QRY_IS_EMP_ID_REGIONAL_APPROVR, RateQuotationConstants.EMP_ID, empId);
	LOGGER.trace("RateQuotationDAOImpl :: checkEmpIdRegionalApprovr() :: END --------> ::::::");
	if(!StringUtil.isEmptyList(regionRateBnchMarkDiscontList)){
		return  regionRateBnchMarkDiscontList;
	}else{
		return null;	
	}
	}catch (Exception e) {
		LOGGER.error("ERROR : RateQuotationDAOImpl.checkEmpIdRegionalApprovr", e);
		throw new CGSystemException(e);
	}
}


@SuppressWarnings("unchecked")
public List<RateBenchMarkHeaderDO> checkEmpIdCorpApprovr(Integer empId) throws CGSystemException{
	LOGGER.trace("RateQuotationDAOImpl :: checkEmpIdCorpApprovr() :: Start --------> ::::::");
	try{
	List<RateBenchMarkHeaderDO> rateBnchMarkHeadrList = getHibernateTemplate().findByNamedQueryAndNamedParam(RateQuotationConstants.QRY_IS_EMP_ID_CORP_APPROVR, RateQuotationConstants.EMP_ID, empId);
	LOGGER.trace("RateQuotationDAOImpl :: checkEmpIdCorpApprovr() :: END --------> ::::::");
	if(!StringUtil.isEmptyList(rateBnchMarkHeadrList)){
		return  rateBnchMarkHeadrList;
	}else{
		return null;	
	}
	}catch (Exception e) {
		LOGGER.error("ERROR : RateQuotationDAOImpl.getRateIndustryType", e);
		throw new CGSystemException(e);
	}
}

/* (non-Javadoc)
 * @see com.ff.rate.configuration.ratequotation.dao.RateQuotationDAO#getQuotationDtlsForCorp(java.util.List)
 */
@SuppressWarnings("unchecked")
public List<RateQuotationWrapperDO> searchQuotationDtlsForCorp(List<Integer> rateIndustryCategoryIdList, String effectiveFrom, String effectiveTo, String status, String quotationNo, String isEQApprover) throws CGSystemException{
	
	List<RateQuotationWrapperDO> quotationDO = null;
	List<RateQuotationWrapperDO> rateEcomQuotatnDOs = null;
	//List<RateIndustryCategoryDO> ricDOList = null;
	//String indCode = null;
	try{
		LOGGER.trace("RateQuotationDAOImpl :: searchQuotationDtlsForCorp() :: Start --------> ::::::");
		/*ricDOList = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_GET_INDUSTRY_CATEGORY,
						new String[] {RateQuotationConstants.RATE_INDUSTRY_CAT_ID_LIST},new Object[]{rateIndustryCategoryIdList});
		if(!CGCollectionUtils.isEmpty(ricDOList)){
			for(RateIndustryCategoryDO rcDO: ricDOList){
				if(rcDO.getRateIndustryCategoryCode().equals(RateCommonConstants.RATE_BENCH_MARK_IND_CAT_CODE)){
					indCode = rcDO.getRateIndustryCategoryCode();
				}
			}
		}*/
		
	if(StringUtil.isStringEmpty(quotationNo)){
		if(StringUtil.isEmptyColletion(rateIndustryCategoryIdList)){	
			String[] paramNames = {
					RateQuotationConstants.RATE_INDUSTRY_CAT_ID_LIST, 
					RateQuotationConstants.STATUS,
					RateQuotationConstants.APPROVAL_REQUIRD_PARAM,
					RateQuotationConstants.APPROVED_AT_PARAM,
					RateQuotationConstants.PARAM_FROM_DATE,
					RateQuotationConstants.PARAM_TO_DATE,
					};
			if(status.equals(RateQuotationConstants.SUBMITTED)){
			Object[] values = { rateIndustryCategoryIdList, 
					status, 
					RateQuotationConstants.REGIONAL_CORP,
					RateQuotationConstants.REGIONAL_OPERATOR,
					DateUtil.stringToDDMMYYYYFormat(effectiveFrom),
					DateUtil.stringToDDMMYYYYFormat(effectiveTo)
					};
			quotationDO = (List<RateQuotationWrapperDO>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							RateQuotationConstants.QRY_SEARCH_QUOTATION_FOR_CORP, paramNames,
							values);
			}else{
				if(status.equals(RateQuotationConstants.APPROVED) || status.equals(RateQuotationConstants.REJECTED)){
					Object[] values = { rateIndustryCategoryIdList, 
							status, 
							RateQuotationConstants.REGIONAL_CORP,
							RateQuotationConstants.REGIONAL_CORP,
							DateUtil.stringToDDMMYYYYFormat(effectiveFrom),
							DateUtil.stringToDDMMYYYYFormat(effectiveTo)
							};
					quotationDO = (List<RateQuotationWrapperDO>) getHibernateTemplate()
							.findByNamedQueryAndNamedParam(
									RateQuotationConstants.QRY_SEARCH_QUOTATION_FOR_CORP, paramNames,
									values);
					}
			}
		}
	//if(!StringUtil.isStringEmpty(indCode) && indCode.equals(RateCommonConstants.RATE_BENCH_MARK_IND_CAT_CODE)){
	if(!StringUtil.isStringEmpty(isEQApprover) && isEQApprover.equals(CommonConstants.YES)){
		String[] pNames = {
				RateQuotationConstants.STATUS,
				RateQuotationConstants.APPROVAL_REQUIRD_PARAM,
				RateQuotationConstants.APPROVED_AT_PARAM,
				RateQuotationConstants.PARAM_FROM_DATE,
				RateQuotationConstants.PARAM_TO_DATE,
				};
		if(status.equals(RateQuotationConstants.SUBMITTED)){
			Object[] values = { 
				status, 
				RateQuotationConstants.REGIONAL_CORP,
				RateQuotationConstants.REGIONAL_OPERATOR,
				DateUtil.stringToDDMMYYYYFormat(effectiveFrom),
				DateUtil.stringToDDMMYYYYFormat(effectiveTo)
				};
			rateEcomQuotatnDOs = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateQuotationConstants.QRY_SEARCH_ECOMMERCE_QUOTATION_FOR_CORP, pNames,
						values);
		}else{
			if(status.equals(RateQuotationConstants.APPROVED) || status.equals(RateQuotationConstants.REJECTED)){
				Object[] values = { 
						status, 
						RateQuotationConstants.REGIONAL_CORP,
						RateQuotationConstants.REGIONAL_CORP,
						DateUtil.stringToDDMMYYYYFormat(effectiveFrom),
						DateUtil.stringToDDMMYYYYFormat(effectiveTo)
						};
				rateEcomQuotatnDOs = (List<RateQuotationWrapperDO>) getHibernateTemplate()
						.findByNamedQueryAndNamedParam(
								RateQuotationConstants.QRY_SEARCH_ECOMMERCE_QUOTATION_FOR_CORP, pNames,
								values);
				}
			}
		}	
	}else{
		if(StringUtil.isEmptyColletion(rateIndustryCategoryIdList)){	
			String[] paramNames = {
					RateQuotationConstants.RATE_INDUSTRY_CAT_ID_LIST, 
					RateQuotationConstants.APPROVAL_REQUIRD_PARAM,
					RateQuotationConstants.APPROVED_AT_PARAM,
					RateQuotationConstants.QUOTATION_NO
					};
			Object[] values = { rateIndustryCategoryIdList, 
					RateQuotationConstants.REGIONAL_CORP,
					new String[]{RateQuotationConstants.REGIONAL_OPERATOR, 
								RateQuotationConstants.REGIONAL_CORP},
					quotationNo
					};
			quotationDO = (List<RateQuotationWrapperDO>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							RateQuotationConstants.QRY_SEARCH_QUOTATION_FOR_CORP_BY_QUOT_NO, paramNames,
							values);
			
		}
		//if(!StringUtil.isStringEmpty(indCode) && indCode.equals(RateCommonConstants.RATE_BENCH_MARK_IND_CAT_CODE)){
		if(!StringUtil.isStringEmpty(isEQApprover) && isEQApprover.equals(CommonConstants.YES)){
			String[] pNames = {
					RateQuotationConstants.APPROVAL_REQUIRD_PARAM,
					RateQuotationConstants.APPROVED_AT_PARAM,
					RateQuotationConstants.QUOTATION_NO
					};
			Object[] vals = { 
					RateQuotationConstants.REGIONAL_CORP,
					new String[]{RateQuotationConstants.REGIONAL_OPERATOR, 
							RateQuotationConstants.REGIONAL_CORP},
					quotationNo
					};
			rateEcomQuotatnDOs = (List<RateQuotationWrapperDO>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							RateQuotationConstants.QRY_SEARCH_ECOMMERCE_QUOTATION_FOR_CORP_BY_QUOT_NO, pNames,
							vals);
		}
	}
	
	if(StringUtil.isEmptyColletion(quotationDO) && StringUtil.isEmptyColletion(rateEcomQuotatnDOs)){
		return null;
	}else if(StringUtil.isEmptyColletion(quotationDO) && !StringUtil.isEmptyColletion(rateEcomQuotatnDOs)){
		return rateEcomQuotatnDOs;
	}else if(!StringUtil.isEmptyColletion(quotationDO) && !StringUtil.isEmptyColletion(rateEcomQuotatnDOs)){
		for(RateQuotationWrapperDO rqwDO: rateEcomQuotatnDOs){
			quotationDO.add(rqwDO);
		}
	}
	}catch (Exception e) {
		LOGGER.error(
				"ERROR : RateQuotationDAOImpl.searchQuotationDtlsForCorp",
				e);
		throw new CGSystemException(e);
	}
	LOGGER.trace("RateQuotationDAOImpl :: searchQuotationDtlsForCorp() :: END --------> ::::::");
	
	return quotationDO;
	
}

@SuppressWarnings("unchecked")
@Override
public List<RateQuotationWrapperDO> rateQuotationListViewSEDetails(
		Integer userId, String fromDate, String toDate, String quotationNo, String status)
		throws CGSystemException {
	
	List<RateQuotationWrapperDO> rqwDOList = null;
	try{
		LOGGER.trace("RateQuotationDAOImpl :: rateQuotationListViewSEDetails() :: Start --------> ::::::");
		
		if(StringUtil.isStringEmpty(quotationNo)){
		
			String query = RateQuotationConstants.QRY_GET_LIST_VIEW_RATE_QUOTATION_SE;
		
			String params[] = {RateQuotationConstants.PARAM_USER_ID, RateQuotationConstants.PARAM_FROM_DATE, RateQuotationConstants.PARAM_TO_DATE, RateQuotationConstants.PARAM_STATUS};
		
			Object values[] = {userId,DateUtil.stringToDDMMYYYYFormat(fromDate),DateUtil.stringToDDMMYYYYFormat(toDate),status};
			
			rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
		
		}else{
			String query = RateQuotationConstants.QRY_GET_RATE_QUOTATION_SE;
			
			String params[] = {RateQuotationConstants.PARAM_USER_ID, RateQuotationConstants.PARAM_QUOTATION_NO};
			
			Object values[] = {userId,quotationNo};
		
			rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
		}
		
		
	}catch(Exception e){
		LOGGER.error("Error occured in RateQuotationDAOImpl :: rateQuotationListViewSEDetails()..:"
				+ e.getMessage());
		throw new CGSystemException(e);
	}
	LOGGER.trace("RateQuotationDAOImpl :: rateQuotationListViewSEDetails() :: END --------> ::::::");
	return rqwDOList;
	
}

@SuppressWarnings("unchecked")
@Override
public RateQuotationDO getRateQuotation(String quotationNo, String quotationUsedFor, Integer quotationId)
		throws CGSystemException {
		LOGGER.trace("RateQuotationDAOImpl :: getRateQuotation() :: Start --------> ::::::");
		List<RateQuotationDO> quotationDO = null;
		RateQuotationDO rqDO = null;
		 Session session = null;
		 Criteria criteria = null;
		try{
		/*String[] paramNames = {RateCommonConstants.QUOTATION_NO,RateCommonConstants.QUOTATION_USER_FOR};
		Object[] values = {quotationNo,quotationUsedFor};
		quotationDO = (List<RateQuotationDO>) getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_SEARCH_RATE_QUOTATION, paramNames,
						values);
		if(!CGCollectionUtils.isEmpty(quotationDO)){
			rqDO = quotationDO.get(0);
		}*/
		session = createSession();	
		criteria = session.createCriteria(RateQuotationDO.class,"quotation" );
		if(!StringUtil.isStringEmpty(quotationNo)){
			criteria.add(Restrictions.eq("quotation.rateQuotationNo", quotationNo));
		}
		if(!StringUtil.isEmptyInteger(quotationId)){
			criteria.add(Restrictions.eq("quotation.rateQuotationId", quotationId));
		}
		criteria.add(Restrictions.eq("quotation.quotationUsedFor", quotationUsedFor));
		
		quotationDO = criteria.list();
		
		if(!CGCollectionUtils.isEmpty(quotationDO)){
			rqDO = quotationDO.get(0);
		}
		}catch (Exception e) {
			LOGGER.error("ERROR : RateQuotationDAOImpl.getRateIndustryType", e);
			throw new CGSystemException(e);
		}finally{
			session.close();
		}
		LOGGER.trace("RateQuotationDAOImpl :: getRateQuotation() :: END --------> ::::::");
		return rqDO;
	}

@Override
public void saveBulkRateQuotaions(List<RateQuotationDO> dataList)
		throws CGSystemException {
	Session session = createSession();
	Transaction tx = null;
	try {
		LOGGER.trace("RateQuotationDAOImpl :: saveBulkRateQuotaions() :: Start --------> ::::::");
		tx = session.beginTransaction();
		for(RateQuotationDO quotDO : dataList)
		session.save(quotDO);
		LOGGER.trace("RateQuotationDAOImpl :: saveBulkRateQuotaions() ");
		tx.commit();
	} catch (Exception e) {
		tx.rollback();
		LOGGER.trace("Error occured in RateQuotationDAOImpl :: saveBulkRateQuotaions()..:"
				, e);			
		throw new CGSystemException(e);
	} finally {
		session.close();
	}
	LOGGER.trace("RateQuotationDAOImpl :: saveBulkRateQuotaions() :: END --------> ::::::");
	}

@SuppressWarnings("unchecked")
@Override
public RateQuotationDO rateQuotationByQuotationId(Integer quotationId)	throws CGSystemException {
	
	List<RateQuotationDO> rqDOList = null;
	RateQuotationDO rqDO = null;
	try{
		LOGGER.trace("RateQuotationDAOImpl :: rateQuotationByQuotationId() :: Start --------> ::::::");
		
		if(!StringUtil.isEmptyInteger(quotationId)){
		
			String query = RateQuotationConstants.GET_RATE_QUOTATION_BY_ID;
		
			String params[] = {RateQuotationConstants.RATE_QUOTATION_ID};
		
			Object values[] = {quotationId};
			
			rqDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
			if(!CGCollectionUtils.isEmpty(rqDOList)){
				rqDO = rqDOList.get(0);
			}
		}
		
	}catch(Exception e){
		LOGGER.error("Error occured in RateQuotationDAOImpl :: rateQuotationByQuotationId()..:"
				+ e.getMessage());
		throw new CGSystemException(e);
	}
	LOGGER.trace("RateQuotationDAOImpl :: rateQuotationByQuotationId() :: END --------> ::::::");
	return rqDO;
	
}

@SuppressWarnings("unchecked")
@Override
public boolean isRateQuotationExist(String quotationNo, String quotationUsedFor)	throws CGSystemException {
	
	List<Integer> rqIdList = null;
	
	try{
		LOGGER.trace("RateQuotationDAOImpl :: rateQuotationByQuotationId() :: Start --------> ::::::");
		
		if(!StringUtil.isStringEmpty(quotationNo)){
		
			String query = RateQuotationConstants.QRY_IS_RATE_QUOTATION_EXIST;
		
			String params[] = {RateQuotationConstants.QUOT_NUMBER,RateQuotationConstants.QUOT_USED_FOR};
		
			Object values[] = {quotationNo, quotationUsedFor};
			
			rqIdList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
			if(!CGCollectionUtils.isEmpty(rqIdList)){
				return true;
			}
		}
		
	}catch(Exception e){
		LOGGER.error("Error occured in RateQuotationDAOImpl :: rateQuotationByQuotationId()..:"
				+ e.getMessage());
		throw new CGSystemException(e);
	}
	LOGGER.trace("RateQuotationDAOImpl :: rateQuotationByQuotationId() :: END --------> ::::::");
	return false;
	
}


@SuppressWarnings("unchecked")
@Override
public Integer getUserIdByUserName(String userName)	throws CGSystemException {
	
	List<Integer> userIdList = null;
	Integer userId = null;
	
	try{
		LOGGER.trace("RateQuotationDAOImpl :: rateQuotationByQuotationId() :: Start --------> ::::::");
		
		if(!StringUtil.isStringEmpty(userName)){
		
			String query = "getUserIdByUserName";
		
			String params[] = {"userName"};
		
			Object values[] = {userName};
			
			userIdList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
			if(!CGCollectionUtils.isEmpty(userIdList)){
				userId = userIdList.get(0);
			}
		}
		
	}catch(Exception e){
		LOGGER.error("Error occured in RateQuotationDAOImpl :: rateQuotationByQuotationId()..:"
				+ e.getMessage());
		throw new CGSystemException(e);
	}
	LOGGER.trace("RateQuotationDAOImpl :: rateQuotationByQuotationId() :: END --------> ::::::");
	return userId;
	
}

@SuppressWarnings("unchecked")
@Override
public Integer getOfcIdByOfcCode(String ofcCode) throws CGSystemException {
	
	List<Integer> ofcIdList = null;
	Integer ofcId = null;
	
	try{
		LOGGER.trace("RateQuotationDAOImpl :: rateQuotationByQuotationId() :: Start --------> ::::::");
		
		if(!StringUtil.isStringEmpty(ofcCode)){
		
			String query = "getOfcIdByOfcCode";
		
			String params[] = {"ofcCode"};
		
			Object values[] = {ofcCode};
			
			ofcIdList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
			if(!CGCollectionUtils.isEmpty(ofcIdList)){
				ofcId = ofcIdList.get(0);
			}
		}
		
	}catch(Exception e){
		LOGGER.error("Error occured in RateQuotationDAOImpl :: rateQuotationByQuotationId()..:"
				+ e.getMessage());
		throw new CGSystemException(e);
	}
	LOGGER.trace("RateQuotationDAOImpl :: rateQuotationByQuotationId() :: END --------> ::::::");
	return ofcId;
	
}

@SuppressWarnings("unchecked")
@Override
public boolean isEcommerceQuotationApprover(Integer userId, String screenCode)
		throws CGSystemException {
	
	List<Integer> rightsIdList = null;
	boolean isApprover = Boolean.FALSE;
	
	try{
		LOGGER.trace("RateQuotationDAOImpl :: rateQuotationByQuotationId() :: Start --------> ::::::");
		
		if(!StringUtil.isStringEmpty(screenCode)){
		
			String query = "isEcommerceQuotationApprover";
		
			String params[] = {"userId", "screenCode"};
		
			Object values[] = {userId, screenCode};
			
			rightsIdList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
			if(!CGCollectionUtils.isEmpty(rightsIdList)){
				isApprover = Boolean.TRUE; 
			}
		}
		
	}catch(Exception e){
		LOGGER.error("Error occured in RateQuotationDAOImpl :: rateQuotationByQuotationId()..:"
				+ e.getMessage());
		throw new CGSystemException(e);
	}
	LOGGER.trace("RateQuotationDAOImpl :: rateQuotationByQuotationId() :: END --------> ::::::");
	return isApprover;
}

	@SuppressWarnings("unchecked")
	@Override
	public RateProductCategoryDO getRateProductDetailsFromRateProductId(
			Integer rateProductCategoryId) throws CGSystemException {
		LOGGER.trace("RateQuotationDAOImpl :: getRateProductDetailsFromRateProductId :: START");
		Session session = null;
		Query query = null;
		List<RateProductCategoryDO> rateProductCategoryDoList = null;
		RateProductCategoryDO rateProductCategoryDo = null;
		try {
			session = createSession();
			query = session.getNamedQuery(RateCommonConstants.QRY_GET_RATE_PRODUCT_DETAILS_FROM_RATE_PRODUCT_ID);
			query.setParameter(RateCommonConstants.PARAM_RATE_PRODUCT_CATEGORY_ID, rateProductCategoryId);
			rateProductCategoryDoList = query.list();
			if (!CollectionUtils.isEmpty(rateProductCategoryDoList)) {
				rateProductCategoryDo = rateProductCategoryDoList.get(0);
			}
		}
		catch (Exception e) {
			throw new CGSystemException(e);
		}
		finally {
			closeSession(session);
		}
		LOGGER.trace("RateQuotationDAOImpl :: getRateProductDetailsFromRateProductId :: ERROR");
		return rateProductCategoryDo;
	}

}

