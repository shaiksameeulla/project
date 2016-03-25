package com.ff.universe.mec.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.geography.RegionDO;
import com.ff.domain.mec.BankDO;
import com.ff.domain.mec.GLMasterDO;
import com.ff.domain.mec.SAPLiabilityEntriesDO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.mec.collection.CollectionDtlsDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.ratemanagement.masters.RateCustomerCategoryDO;
import com.ff.domain.stockmanagement.wrapper.StockCustomerWrapperDO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.universe.mec.constant.MECUniversalConstants;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;

public class MECUniversalDAOImpl extends CGBaseDAO implements MECUniversalDAO {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(MECUniversalDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public BankDO getBankDtlsById(Integer bankId) throws CGSystemException {
		LOGGER.trace("MECUniversalDAOImpl::getBankDtlsById()::START");
		List<BankDO> bankDOs = null;
		try {
			String[] params = { MECUniversalConstants.PARAM_BANK_ID };
			Object[] values = { bankId };
			bankDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					MECUniversalConstants.QRY_GET_BANK_DTLS_BY_ID, params,
					values);
		} catch (Exception e) {
			LOGGER.error("Exception Occurs in MECUniversalDAOImpl::getBankDtlsById()::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("MECUniversalDAOImpl::getBankDtlsById()::END");
		return !StringUtil.isEmptyList(bankDOs) ? bankDOs.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankDO> getAllBankDtls() throws CGSystemException {
		LOGGER.trace("MECUniversalDAOImpl::getAllBankDtls()::START");
		List<BankDO> bankDOs = null;
		try {
			bankDOs = getHibernateTemplate().findByNamedQuery(
					MECUniversalConstants.QRY_GET_ALL_BANK_DTLS);
		} catch (Exception e) {
			LOGGER.error("Exception Occurs in MECUniversalDAOImpl::getAllBankDtls()::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("MECUniversalDAOImpl::getAllBankDtls()::END");
		return bankDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GLMasterDO> getGLDtlsByRegionId(Integer regionId)
			throws CGSystemException {
		LOGGER.trace("MECUniversalDAOImpl::getGLDtlsByRegionId()::START");
		List<GLMasterDO> glDOs = null;
		String params[] = { MECUniversalConstants.PARAM_REGION_ID };
		Object[] values = new Object[] { regionId };
		glDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
				MECUniversalConstants.QRY_GET_GL_DTLS_BY_REGION_ID, params,
				values);
		LOGGER.trace("MECUniversalDAOImpl::getGLDtlsByRegionId()::END");
		return glDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ConsignmentDO getConsignmentDtls(String consgNo)
			throws CGSystemException {
		LOGGER.trace("MECUniversalDAOImpl::getConsignmentDtls()::START");
		List<ConsignmentDO> consgDOs = null;
		try {
			String params[] = { MECUniversalConstants.PARAM_CONSG_NO };
			Object[] values = new Object[] { consgNo };
			consgDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					MECUniversalConstants.QRY_GET_CONSIGNMENT_BY_CONSG_NO,
					params, values);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in MECUniversalDAOImpl :: getConsignmentDtls() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("MECUniversalDAOImpl::getConsignmentDtls()::END");
		return (!CGCollectionUtils.isEmpty(consgDOs)) ? consgDOs.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Deprecated
	public List<CustomerDO> getLiabilityCustomers(Integer regionId)
			throws CGSystemException {
		LOGGER.trace("MECUniversalDAOImpl :: getLiabilityCustomers() :: START");
		List<CustomerDO> customerDO = null;
		try {
			String[] params = {};
			Object[] values = {};
			String[] customerTypes = { CommonConstants.CUSTOMER_CODE_COD,
					CommonConstants.CUSTOMER_CODE_LC };
			if (!StringUtil.isEmptyInteger(regionId)) {
				params = new String[] { MECUniversalConstants.PARAM_REGION_ID,
						MECUniversalConstants.PARAM_CUSTOMER_TYPE };
				values = new Object[] { regionId, customerTypes };
				customerDO = getHibernateTemplate()
						.findByNamedQueryAndNamedParam(
								MECUniversalConstants.QRY_GET_COD_LC_CUSTOMERS,
								params, values);
			} else {
				params = new String[] { MECUniversalConstants.PARAM_CUSTOMER_TYPE };
				values = new Object[] { customerTypes };
				customerDO = getHibernateTemplate()
						.findByNamedQueryAndNamedParam(
								MECUniversalConstants.QRY_GET_ALL_COD_LC_CUSTOMERS,
								params, values);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurs in MECUniversalDAOImpl :: getLiabilityCustomers() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("MECUniversalDAOImpl :: getLiabilityCustomers() :: END");
		return customerDO;
	}
	
	@Override
	public List<StockCustomerWrapperDO> getLiabilityCustomersForLiability(Integer regionId)
			throws CGSystemException {
		LOGGER.trace("MECUniversalDAOImpl :: getLiabilityCustomers() :: START");
		List<StockCustomerWrapperDO> customerDO = null;
		try {
			String[] params = {};
			Object[] values = {};
			String[] customerTypes = { CommonConstants.CUSTOMER_CODE_COD,
					CommonConstants.CUSTOMER_CODE_LC };
			if (!StringUtil.isEmptyInteger(regionId)) {
				params = new String[] { MECUniversalConstants.PARAM_REGION_ID,
						MECUniversalConstants.PARAM_CUSTOMER_TYPE,StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS };
				values = new Object[] { regionId, customerTypes,StockUniveralConstants.TRANSACTION_STATUS };
				customerDO = getHibernateTemplate()
						.findByNamedQueryAndNamedParam(
								MECUniversalConstants.QRY_GET_LIABILITY_CUSTOMERS_FOR_LIABILITY_BY_REGION,
								params, values);
			} else {
				params = new String[] { MECUniversalConstants.PARAM_CUSTOMER_TYPE,StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS };
				values = new Object[] { customerTypes,StockUniveralConstants.TRANSACTION_STATUS };
				customerDO = getHibernateTemplate()
						.findByNamedQueryAndNamedParam(
								MECUniversalConstants.QRY_GET_ALL_LIABILITY_CUSTOMERS_FOR_LIABILITY,
								params, values);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurs in MECUniversalDAOImpl :: getLiabilityCustomers() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("MECUniversalDAOImpl :: getLiabilityCustomers() :: END");
		return customerDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GLMasterDO> getAllBankGLDtls(Integer regionId)
			throws CGSystemException {
		LOGGER.trace("MECUniversalDAOImpl::getAllBankGLDtls()::START");
		List<GLMasterDO> bankGlDOs = null;
		try {
			String[] params = { MECUniversalConstants.PARAM_IS_BANK_GL,
					MECUniversalConstants.PARAM_REGION_ID };
			Object[] values = { CommonConstants.YES, regionId };
			bankGlDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					MECUniversalConstants.QRY_GET_ALL_BANK_GL_DTLS_BY_REGION,
					params, values);
		} catch (Exception e) {
			LOGGER.error("Exception Occurs in MECUniversalDAOImpl::getAllBankGLDtls()::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("MECUniversalDAOImpl::getAllBankGLDtls()::END");
		return bankGlDOs;
	}

	@Override
	public boolean saveOtherCollectionDtls(
			List<CollectionDtlsDO> collectionDtlsDOs) throws CGSystemException {
		LOGGER.trace("MECUniversalDAOImpl::saveOtherCollectionDtls()::START");
		getHibernateTemplate().saveOrUpdateAll(collectionDtlsDOs);
		LOGGER.trace("MECUniversalDAOImpl::saveOtherCollectionDtls()::END");
		return Boolean.TRUE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GLMasterDO> getBankGLDtls() throws CGSystemException {
		LOGGER.trace("MECUniversalDAOImpl::getBankGLDtls()::START");
		List<GLMasterDO> bankGlDOs = null;
		try {
			String[] params = { MECUniversalConstants.PARAM_IS_BANK_GL };
			Object[] values = { CommonConstants.YES };
			bankGlDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					MECUniversalConstants.QRY_GET_ALL_BANK_GL_DTLS, params,
					values);
		} catch (Exception e) {
			LOGGER.error("Exception Occurs in MECUniversalDAOImpl::getBankGLDtls()::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("MECUniversalDAOImpl::getBankGLDtls()::END");
		return bankGlDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RateCustomerCategoryDO getRateCustCategoryByCustId(Integer customerId)
			throws CGSystemException {
		LOGGER.trace("MECUniversalDAOImpl :: getRateCustCategoryByCustId() :: START");
		RateCustomerCategoryDO rateCustCatDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(CustomerDO.class, "cust");
			cr.add(Restrictions.eq("cust.customerId", customerId));
			List<CustomerDO> customerDOs = cr.list();
			CustomerDO customerDO = null;
			if (!CGCollectionUtils.isEmpty(customerDOs)
					&& customerDOs.size() > 0) {
				customerDO = customerDOs.get(0);
				rateCustCatDO = customerDO.getCustomerCategoryDO();
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in MECUniversalDAOImpl :: getRateCustCategoryByCustId() :: ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("MECUniversalDAOImpl :: getRateCustCategoryByCustId() :: END");
		return rateCustCatDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ConfigurableParamsDO getMaxDataCount(String paramName)
			throws CGSystemException {
		logger.debug("COLLECTIONCR :: MECUniversalDAOImpl :: getMaxDataCount() :: Start");
		List<ConfigurableParamsDO> configParamDOs = null;
		ConfigurableParamsDO configParamDO = null;
		try {
			String queryName = MECUniversalConstants.QRY_PARAM_GET_MAX_DATA_COUNT;
			configParamDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, MECUniversalConstants.PARAM_NAME,
							paramName);
			if (!StringUtil.isEmptyColletion(configParamDOs)) {
				configParamDO = configParamDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("COLLECTIONCR :: ERROR : MECUniversalDAOImpl.getMaxDataCount", e);
			throw new CGSystemException(e);
		}
		logger.debug("COLLECTIONCR :: MECUniversalDAOImpl :: getMaxDataCount() :: End");
		return configParamDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RegionDO getRegionByOffice(Integer officeId)
			throws CGSystemException {
		LOGGER.trace("MECUniversalDAOImpl :: getRegionByOffice() :: START");
		RegionDO regionDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(OfficeDO.class, "office");
			cr.add(Restrictions.eq("office.officeId", officeId));
			List<OfficeDO> officeDOs = cr.list();
			if (!CGCollectionUtils.isEmpty(officeDOs)) {
				OfficeDO officeDO = officeDOs.get(0);
				if (!StringUtil.isNull(officeDO)) {
					regionDO = officeDO.getMappedRegionDO();
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in MECUniversalDAOImpl :: getRegionByOffice() :: ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("MECUniversalDAOImpl :: getRegionByOffice() :: END");
		return regionDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDO> getCustomersForBillCollection(Integer officeId)
			throws CGSystemException {
		LOGGER.debug("MECUniversalDAOImpl :: getCustomersForBillCollection() :: START");
		List<CustomerDO> customerDOs = null;
		try {
			customerDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							MECUniversalConstants.QRY_GET_CUSTOMERS_FOR_BILL_COLLECTION,
							new String[] { MECUniversalConstants.PARAM_OFFICE_ID },
							new Object[] { officeId });
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in MECUniversalDAOImpl :: getCustomersForBillCollection() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("MECUniversalDAOImpl :: getCustomersForBillCollection() :: END");
		return customerDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CollectionDtlsDO> getCollectionDtlsFromDeliveryDtls(
			List<Integer> consgIds) throws CGSystemException {
		LOGGER.debug("MECUniversalDAOImpl :: getCollectionDtlsFromDeliveryDtls() :: START");
		List<CollectionDtlsDO> collDtlsDOs = null;
		try {
			String queryName = MECUniversalConstants.QRY_GET_COLLECTION_DTLS_FROM_DELIVERY_DTLS;
			String[] params = { MECUniversalConstants.PARAM_CONSG_IDS };
			Object[] values = { consgIds };
			collDtlsDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, params, values);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in MECUniversalDAOImpl :: getCollectionDtlsFromDeliveryDtls() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("MECUniversalDAOImpl :: getCollectionDtlsFromDeliveryDtls() :: END");
		return collDtlsDOs;
	}

	@Override
	public void saveExpConsgCollectionDtls(List<CollectionDO> collectionDOs)
			throws CGSystemException {
		LOGGER.trace("MECUniversalDAOImpl :: saveExpConsgCollectionDtls() :: START");
		try {
			for (CollectionDO collectionDO : collectionDOs) {
				getHibernateTemplate().merge(collectionDO);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occurs in MECUniversalDAOImpl :: saveExpConsgCollectionDtls() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("MECUniversalDAOImpl :: saveExpConsgCollectionDtls() :: END");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryDetailsDO> getDrsDtlsForExpenseTypeCollectoin()
			throws CGSystemException {
		LOGGER.trace("MECUniversalDAOImpl :: getDrsDtlsForExpenseTypeCollectoin() :: START");
		List<DeliveryDetailsDO> detailsDOList = null;
		try {
			String[] params = {
					UniversalDeliveryContants.QRY_PARAM_DELIVERY_STATUS,
					UniversalDeliveryContants.QRY_PARAM_YESTERDAY_DATE,
					UniversalDeliveryContants.QRY_PARAM_TODAY_DATE };

			// Date yesterday = DateUtil.getCurrentDateWithoutTime();
			Date yesterday = DateUtil.trimTimeFromDate(DateUtil
					.getPreviousDate(2));
			Date today = DateUtil.appendLastHourToDate(DateUtil
					.getCurrentDateWithoutTime());

			Object[] values = {
					UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED,
					yesterday, today };
			String queryName = UniversalDeliveryContants.QRY_GET_DRS_DTLS_FOR_EXPENSE_TYPE_COLLECTOIN;
			detailsDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);
		} catch (Exception e) {
			LOGGER.error(
					"Expecetion occurs in MECUniversalDAOImpl :: getDrsDtlsForExpenseTypeCollectoin() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("MECUniversalDAOImpl :: getDrsDtlsForExpenseTypeCollectoin() :: START");
		return detailsDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SAPLiabilityEntriesDO getLiabilityEntryByConsigNoForSAPLiabilityScheduler(
			String consgNo) throws CGSystemException {
		logger.debug("CODLC BOOKING :: MECUniversalDAOImpl :: getLiabilityEntryByConsigNoForSAPLiabilityScheduler() :: START");
		List<SAPLiabilityEntriesDO> liaDtlsDOs = null;
		SAPLiabilityEntriesDO liabilityEntriesDO =null;
		try {
			String queryName = MECUniversalConstants.QRY_GET_LIABILITY_DTLS_FOR_SAP_LIABILITY_SCHEDULER;
			String[] params = { MECUniversalConstants.PARAM_CONSG_NO };
			Object[] values = { consgNo };
			liaDtlsDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, params, values);
			liabilityEntriesDO = !StringUtil.isEmptyList(liaDtlsDOs) ? liaDtlsDOs
					.get(0) : null;
		} catch (Exception e) {
			LOGGER.error(
					"CODLC BOOKING :: Exception occurs in MECUniversalDAOImpl :: getLiabilityEntryByConsigNoForSAPLiabilityScheduler() :: ",
					e);
			throw new CGSystemException(e);
		}
		logger.debug("CODLC BOOKING :: MECUniversalDAOImpl :: getLiabilityEntryByConsigNoForSAPLiabilityScheduler() :: END");
		return liabilityEntriesDO;
	}
}
