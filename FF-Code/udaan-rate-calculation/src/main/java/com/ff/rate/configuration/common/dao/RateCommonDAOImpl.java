package com.ff.rate.configuration.common.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.ratemanagement.masters.CodChargeDO;
import com.ff.domain.ratemanagement.masters.RateContractDO;
import com.ff.domain.ratemanagement.masters.RateCustomerCategoryDO;
import com.ff.domain.ratemanagement.masters.RateCustomerProductCatMapDO;
import com.ff.domain.ratemanagement.masters.RateIndustryCategoryDO;
import com.ff.domain.ratemanagement.masters.RateMinChargeableWeightDO;
import com.ff.domain.ratemanagement.masters.RateProductCategoryDO;
import com.ff.domain.ratemanagement.masters.RateSectorsDO;
import com.ff.domain.ratemanagement.masters.RateTaxComponentDO;
import com.ff.domain.ratemanagement.masters.RateVobSlabsDO;
import com.ff.domain.ratemanagement.masters.RateWeightSlabsDO;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.domain.ratemanagement.operations.rateBenchmarkDiscount.RegionRateBenchMarkDiscountDO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.ratecontract.constants.RateContractConstants;
import com.ff.to.ratemanagement.masters.RateContractTO;
import com.ff.universe.ratemanagement.constant.RateUniversalConstants;


public class RateCommonDAOImpl extends CGBaseDAO implements RateCommonDAO{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(RateCommonDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RateIndustryCategoryDO> getRateIndustryCategoryList()
			throws CGSystemException {
		LOGGER.info("RateCommonDAOImpl :: getIndustryCategoryList() :: Start --------> ::::::");
		 
		 Session session = null;
		 List<RateIndustryCategoryDO> rateIndustryCategoryDOList = null;
		 Criteria criteria=null;
			try{
				session = createSession();
				criteria = session.createCriteria(RateIndustryCategoryDO.class,"rateIndustryCategoryDO");
				criteria.addOrder(Order.asc("rateIndustryCategoryDO.rateIndustryCategoryId"));
				rateIndustryCategoryDOList = criteria.list(); 
			}
			catch (Exception e) {
				LOGGER.error("ERROR : RateCommonDAOImpl.getIndustryCategoryList", e);
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.info("RateCommonDAOImpl :: getIndustryCategoryList() :: End --------> ::::::");
			return rateIndustryCategoryDOList;

	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RateProductCategoryDO> getRateProductCategoryList()
			throws CGSystemException {
		LOGGER.info("RateCommonDAOImpl :: getRateProductCategoryList() :: Start --------> ::::::");
		 
		 Session session = null;
		 List<RateProductCategoryDO> rateProductCategoryDOList = null;
		 Criteria criteria=null;
			try{
				session = createSession();
				criteria = session.createCriteria(RateProductCategoryDO.class,"rateProductCategoryDO");
				criteria.add(Restrictions.eq("rateProductCategoryDO.rateProductCategoryType",RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N));
				criteria.addOrder(Order.asc("rateProductCategoryDO.rateProductCategoryId"));
				rateProductCategoryDOList = criteria.list(); 
			}
			catch (Exception e) {
				LOGGER.error("ERROR : RateCommonDAOImpl.getRateProductCategoryList", e);
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.info("RateCommonDAOImpl :: getRateProductCategoryList() :: End --------> ::::::");
			return rateProductCategoryDOList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RateVobSlabsDO> getRateVobSlabsList()
			throws CGSystemException {
		LOGGER.info("RateCommonDAOImpl :: getVobSlabsList() :: Start --------> ::::::");
		 
		 Session session = null;
		 List<RateVobSlabsDO> rateVobSlabsDOList = null;
		
			try{
				
				String query = RateCommonConstants.QRY_GET_RATE_VOB_SLAB_LIST_BY_EFFECTIVE_DATE;
				
				String params[] = {RateCommonConstants.PARAM_EFFECTIVE_FROM,RateCommonConstants.PARAM_EFFECTIVE_TO,RateCommonConstants.PARAM_RATE_CUST_CAT_CODE,RateCommonConstants.PARAM_RATE_PROD_CAT_TYPE};
				
				Date date = DateUtil.stringToDDMMYYYYFormat(DateUtil.getCurrentDateInDDMMYYYY());
				
				Object values[] = {date,date,RateCommonConstants.RATE_BENCH_MARK_CUST_CAT_CODE,RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N};
				
				rateVobSlabsDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);

			}
			catch (Exception e) {
				LOGGER.error("ERROR : RateBenchMarkDAOImpl.getVobSlabsList", e);
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.info("RateCommonDAOImpl :: getVobSlabsList() :: End --------> ::::::");
			return rateVobSlabsDOList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RateWeightSlabsDO> getRateWeightSlabsList()
			throws CGSystemException {
		LOGGER.info("RateCommonDAOImpl :: getRateWeightSlabsList() :: Start --------> ::::::");
		 
		 Session session = null;
		 List<RateWeightSlabsDO> rateWeightSlabsDOList = null;
		
			try{
				
				String query = RateCommonConstants.QRY_GET_RATE_WEIGHT_SLAB_LIST_BY_EFFECTIVE_DATE;
				
				String params[] = {RateCommonConstants.PARAM_EFFECTIVE_FROM,RateCommonConstants.PARAM_EFFECTIVE_TO,RateCommonConstants.PARAM_RATE_CUST_CAT_CODE,RateCommonConstants.PARAM_RATE_PROD_CAT_TYPE};
				
				Date date = DateUtil.stringToDDMMYYYYFormat(DateUtil.getCurrentDateInDDMMYYYY());
				
				Object values[] = {date,date,RateCommonConstants.RATE_BENCH_MARK_CUST_CAT_CODE,RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N};
				
				rateWeightSlabsDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
				
				
			}
			catch (Exception e) {
				LOGGER.error("ERROR : RateCommonDAOImpl.getRateWeightSlabsList", e);
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.info("RateCommonDAOImpl :: getRateWeightSlabsList() :: End --------> ::::::");
			return rateWeightSlabsDOList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RateSectorsDO> getRateSectorsList() throws CGSystemException {
		LOGGER.info("RateCommonDAOImpl :: getRateSectorsList() :: Start --------> ::::::");
		 
		Session session = null;
		List<RateSectorsDO> rateSectorsDOList = null;
				
			try{
						
					String query = RateCommonConstants.QRY_GET_RATE_SECTORS;
						
					String params[] = {RateCommonConstants.PARAM_RATE_CUST_CAT_CODE,RateCommonConstants.PARAM_RATE_PROD_CAT_TYPE};
					
					Object values[] = {RateCommonConstants.RATE_BENCH_MARK_CUST_CAT_CODE,RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N};
						
					rateSectorsDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
						
			}catch (Exception e) {
				LOGGER.error("ERROR : RateCommonDAOImpl.getRateSectorsList", e);
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.info("RateCommonDAOImpl :: getRateSectorsList() :: End --------> ::::::");
			return rateSectorsDOList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RateCustomerProductCatMapDO> getRateCustomerProductCatMapList()
			throws CGSystemException {
		LOGGER.info("RateCommonDAOImpl :: getRateCustomerProductCatMapList() :: Start --------> ::::::");
		 
		 Session session = null;
		 List<RateCustomerProductCatMapDO> rateCustProductCatMapDOList = null;
		 Criteria criteria=null;
			try{
				session = createSession();
				criteria = session.createCriteria(RateCustomerProductCatMapDO.class,"rateCustProdCatMapDO");
				criteria.add(Restrictions.eq("rateCustProdCatMapDO.rateCustomerCategoryDO.rateCustomerCategoryId",1));
				criteria.addOrder(Order.asc("rateCustProdCatMapDO.rateProductCategoryDO.rateProductCategoryId"));
				rateCustProductCatMapDOList = criteria.list(); 
			}
			catch (Exception e) {
				LOGGER.error("ERROR : RateCommonDAOImpl.getRateCustomerProductCatMapList", e);
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.info("RateCommonDAOImpl :: getRateCustomerProductCatMapList() :: End --------> ::::::");
			return rateCustProductCatMapDOList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RateMinChargeableWeightDO> getRateMinChrgWtList()
			throws CGSystemException {
		LOGGER.info("RateCommonDAOImpl :: getRateMinChargeWtList() :: Start --------> ::::::");
		 
			 Session session = null;
			List<RateMinChargeableWeightDO> rateMinChargeWtDOList = null;
			
				try{
					
					String query = RateCommonConstants.QRY_GET_RATE_MIN_CHRG_WEIGHT;
					
					String params[] = {RateCommonConstants.PARAM_RATE_CUST_CAT_CODE,RateCommonConstants.PARAM_RATE_PROD_CAT_TYPE};
					
					Object values[] = {RateCommonConstants.RATE_BENCH_MARK_CUST_CAT_CODE,RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N};
					
					rateMinChargeWtDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
					
					
				}catch (Exception e) {
					LOGGER.error("ERROR : RateCommonDAOImpl.getRateMinChrgWtList", e);
					throw new CGSystemException(e);
				}
				finally{
					closeSession(session);
				}		
				LOGGER.info("RateCommonDAOImpl :: getRateMinChrgWtList() :: End --------> ::::::");
				return rateMinChargeWtDOList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RateCustomerCategoryDO> getRateCustomerCategoryList()
			throws CGSystemException {
		LOGGER.info("RateCommonDAOImpl :: getRateCustomerCategoryList() :: Start --------> ::::::");
		 
		 Session session = null;
		 List<RateCustomerCategoryDO> rateCustomerCategoryDOList = null;
		 Criteria criteria=null;
			try{
				session = createSession();
				criteria = session.createCriteria(RateCustomerCategoryDO.class,"rateCustomerCategoryDO");
				criteria.addOrder(Order.asc("rateCustomerCategoryDO.rateCustomerCategoryId"));
				rateCustomerCategoryDOList = criteria.list(); 
			}
			catch (Exception e) {
				LOGGER.error("ERROR : RateCommonDAOImpl.getRateCustomerCategoryList", e);
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.info("RateCommonDAOImpl :: getRateCustomerCategoryList() :: End --------> ::::::");
			return rateCustomerCategoryDOList;
	}

	@Override
	public boolean blockOrUnblockCustomer(Integer customerId, String status)
			throws CGSystemException {
		LOGGER.debug("RateCommonDAOImpl::blockOrUnblockCustomer()::START");
		boolean result=Boolean.FALSE;
		Session session=null;
		try{
			session=createSession();
			Query query=session.getNamedQuery(RateCommonConstants.QRY_BLOCK_OR_UNBLOCK_CUSTOMER);
			query.setParameter(RateCommonConstants.PARAM_CUSTOMER_ID, customerId);
			query.setParameter(RateCommonConstants.PARAM_STATUS, status);
			int i=query.executeUpdate();
			if(i>0)
				result=Boolean.TRUE;
		} catch(Exception e) {
			LOGGER.error("Exception occurs in RateCommonDAOImpl::blockOrUnblockCustomer()::"
				,e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("RateCommonDAOImpl::blockOrUnblockCustomer()::END");
		return result;
	}

	@Override
	public boolean updateCustPanNo(String panNo, Integer customerId)
			throws CGSystemException {
		LOGGER.debug("RateCommonDAOImpl::updateCustPanNo()::START");
		boolean result=Boolean.FALSE;
		Session session=null;
		try{
			session=createSession();
			Query query=session.getNamedQuery(RateCommonConstants.QRY_UPDATE_CUST_PAN_NO);
			query.setParameter(RateCommonConstants.PARAM_PAN_NO, panNo);
			query.setParameter(RateCommonConstants.PARAM_CUSTOMER_ID, customerId);
			int i=query.executeUpdate();
			if(i>0)
				result=Boolean.TRUE;
		} catch(Exception e) {
			LOGGER.error("Exception occurs in RateCommonDAOImpl::updateCustPanNo()::"
				,e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("RateCommonDAOImpl::updateCustPanNo()::END");
		return result;
	}
		
	@Override
	public boolean updateCustTanNo(String tanNo, Integer customerId)
			throws CGSystemException {
		LOGGER.debug("RateCommonDAOImpl::updateCustTanNo()::START");
		boolean result=Boolean.FALSE;
		Session session=null;
		try{
			session=createSession();
			Query query=session.getNamedQuery(RateCommonConstants.QRY_UPDATE_CUST_TAN_NO);
			query.setParameter(RateCommonConstants.PARAM_TAN_NO, tanNo);
			query.setParameter(RateCommonConstants.PARAM_CUSTOMER_ID, customerId);
			int i=query.executeUpdate();
			if(i>0)
				result=Boolean.TRUE;
		} catch(Exception e) {
			LOGGER.error("Exception occurs in RateCommonDAOImpl::updateCustTanNo()::"
				,e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("RateCommonDAOImpl::updateCustTanNo()::END");
		return result;
	}

	@Override
	public boolean updateContractStatus(RateContractTO rateContractTO)
			throws CGSystemException {
		LOGGER.debug("RateCommonDAOImpl::updateContractStatus()::START");
		boolean result=Boolean.FALSE;
		Session session=null;
		try{
			session=createSession();
			Query query=session.getNamedQuery(RateCommonConstants.QRY_UPDATE_CONTRACT_STATUS);
			query.setParameter(RateCommonConstants.PARAM_RATE_CONTRACT_ID,
					rateContractTO.getRateContractId());
			query.setParameter(RateCommonConstants.PARAM_CONTRACT_STATUS,
					rateContractTO.getContractStatus());
			query.setParameter("dtToBranch","N");
			int i=query.executeUpdate();
			if(i>0)
				result=Boolean.TRUE;
		} catch(Exception e) {
			LOGGER.error("Exception occurs in RateCommonDAOImpl::updateContractStatus()::"
				,e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("RateCommonDAOImpl::updateContractStatus()::END");
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RateSectorsDO> getRateSectorList(String rateProductCategory, 
			String rateCustomerCategory) throws CGSystemException {
		LOGGER.debug("RateCommonDAOImpl::getRateSectorList()::START::");
		List<RateSectorsDO> rateSectorsDOs = null;
		try {
			String query = RateUniversalConstants.QRY_GET_RATE_SECTORS_LIST;
			String params[] = { RateCommonConstants.PARAM_RATE_PROD_CAT_TYPE,
					RateUniversalConstants.PARAM_RATE_CUST_CAT_CODE };
			Object values[] = {rateProductCategory, rateCustomerCategory };
			rateSectorsDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(query, params, values);
		} catch (Exception e) {
			LOGGER.error("Exception occurs in RateCommonDAOImpl::getRateSectorList()::"
				+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateCommonDAOImpl::getRateSectorList()::END::");
		return rateSectorsDOs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RateWeightSlabsDO> getRateWeightSlabList(String rateProductCategory, 
			String rateCustomerCategory) throws CGSystemException {
		LOGGER.debug("RateCommonDAOImpl::getRateWeightSlabList()::START::");
		List<RateWeightSlabsDO> rateWeightSlabsDOs = null;
		try {
			String query = RateUniversalConstants.QRY_GET_RATE_WEIGHT_SLAB_LIST_BY_EFFECTIVE_DATE_FOR_COURIER;
			String params[] = { 
					RateUniversalConstants.PARAM_EFFECTIVE_FROM,
					RateUniversalConstants.PARAM_EFFECTIVE_TO,
					RateUniversalConstants.PARAM_RATE_CUST_CAT_CODE,
					RateUniversalConstants.PARAM_RATE_PROD_CAT_TYPE 
				};
			Date date = DateUtil.stringToDDMMYYYYFormat(DateUtil.getCurrentDateInDDMMYYYY());
			Object values[] = { date, date, rateCustomerCategory, rateProductCategory };
			rateWeightSlabsDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(query, params, values);
		} catch (Exception e) {
			LOGGER.error("Exception occurs in RateCommonDAOImpl::getRateWeightSlabList()::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateCommonDAOImpl::getRateWeightSlabList()::END::");
		return rateWeightSlabsDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateTaxComponentDO> getTaxComponents(Integer stateId,
			Date currentDate, String taxGroup) throws CGSystemException {
		List<RateTaxComponentDO> rateTaxComponents = null;
		try {
			String[] paramNames = { RateCommonConstants.STATE_ID,
					RateCommonConstants.CURRENT_DATE, "taxGroup" };
			Object[] values = { stateId, currentDate, taxGroup };

			rateTaxComponents = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							RateCommonConstants.QRY_GET_TAX_COMPONENTS,
							paramNames, values);
		} catch (Exception e) {
			LOGGER.error("Exception occurs in RateCommonDAOImpl::getTaxComponents()::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateCommonDAOImpl::getTaxComponents()::END::");
		
		return rateTaxComponents;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RateTaxComponentDO> getTaxComponentsForRateConfiguration(
			Integer stateId, Date currentDateWithoutTime)
			throws CGSystemException {
		List<RateTaxComponentDO> rateTaxComponents = null;
		try {
			String[] paramNames = { RateCommonConstants.STATE_ID,
					RateCommonConstants.CURRENT_DATE, };
			Object[] values = { stateId, currentDateWithoutTime};

			rateTaxComponents = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							RateCommonConstants.QRY_GET_TAX_COMPONENTS_FOR_CONFIGURATION,
							paramNames, values);
		} catch (Exception e) {
			LOGGER.error("Exception occurs in RateCommonDAOImpl::getTaxComponentsForRateConfiguration()::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateCommonDAOImpl::getTaxComponentsForRateConfiguration()::END::");
		
		return rateTaxComponents;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SectorDO getSectorByRegionId(Integer regionId)
			throws CGSystemException {
		LOGGER.debug("RateCommonDAOImpl::getOriginSectorByRegionId()::START");
		List<SectorDO> sectorDOs = null;
		String[] params = {RateCommonConstants.PARAM_REGION_ID};
		Object[] values = {regionId};
		sectorDOs = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(RateCommonConstants.QRY_GET_ORIGIN_SECTOR_BY_REGION_ID, 
						params, values);
		LOGGER.debug("RateCommonDAOImpl::getOriginSectorByRegionId()::END");
		return (!StringUtil.isEmptyColletion(sectorDOs))?sectorDOs.get(0):null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public RateContractDO getRateContractDetails(Integer rateContractId) throws CGSystemException{
		LOGGER.debug("RateCommonDAOImpl::getRateContractDetails()::START");
		List<RateContractDO> rateContractDOs = null;
		try{
		String params[] = { RateContractConstants.RATE_CONTRACT_ID };
		Object values[] = { rateContractId };
		rateContractDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
				RateContractConstants.QRY_GET_RATE_CONTRACT_BY_ID, params,
				values);
		LOGGER.debug("RateCommonDAOImpl::getRateContractDetails()::END");
		return (!StringUtil.isEmptyColletion(rateContractDOs)) ? rateContractDOs
				.get(0) : null;
		}catch (Exception e) {
			LOGGER.error("ERROR : RateCommonDAOImpl.getRateIndustryType", e);
			throw new CGSystemException(e);
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public EmployeeDO getRegionalApprovalDetails(Integer regionId,
			Integer indCatId) throws CGSystemException {
		LOGGER.debug("RateCommonDAOImpl::getRegionalApprovalDetails()::START");
		List<RegionRateBenchMarkDiscountDO> discountDOs = null;
		try{
		String params[] = { "regionId","industryCatId"};
		Object values[] = { regionId, indCatId};
		discountDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
				RateCommonConstants.QRY_GET_REGIONAL_APPROVER, params,
				values);
		LOGGER.debug("RateCommonDAOImpl::getRegionalApprovalDetails()::END");
		return (!StringUtil.isEmptyColletion(discountDOs)) ? discountDOs
				.get(0).getEmployeeDO() : null;
		}catch (Exception e) {
			LOGGER.error("ERROR : RateCommonDAOImpl.getRegionalApprovalDetails", e);
			throw new CGSystemException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CodChargeDO> getDeclaredValueCodCharge(String configuredType)
			throws CGSystemException {
		LOGGER.trace("RateQuotationDAOImpl :: getDeclaredValueCodCharge() :: Start --------> ::::::");

		Session session = null;
		List<CodChargeDO> codChargeDOs = null;
		Criteria criteria = null;
		try {
			session = createSession();
			criteria = session.createCriteria(CodChargeDO.class, "codChargeDO");
			criteria.add(Restrictions.eq("codChargeDO.configuredFor", configuredType));
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


	@SuppressWarnings("unchecked")
	@Override
	public List<CodChargeDO> getDeclaredValueCodChargeForBA(
			String configuredType, Integer rateCustomerCategoryId)
			throws CGSystemException {
		LOGGER.trace("RateQuotationDAOImpl :: getDeclaredValueCodChargeForBA() :: Start --------> ::::::");
		List<CodChargeDO> chargeDOs = null;
		try{
			String params[] = { "configuredFor","rateCustomerCategoryId"};
			Object values[] = { configuredType, rateCustomerCategoryId};
			chargeDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					RateCommonConstants.QRY_GET_COD_CHARGES_BY_CONFIGURED_FOR_AND_RATE_CUSTOMER_CATEGORY, params,
					values);
		}catch(Exception e){
			LOGGER.error("ERROR : RateQuotationDAOImpl.getDeclaredValueCodChargeForBA", e);
			throw new CGSystemException(e);
		}
		
		LOGGER.trace("RateQuotationDAOImpl :: getDeclaredValueCodChargeForBA() :: End --------> ::::::");
		return chargeDOs;
	}	
}
