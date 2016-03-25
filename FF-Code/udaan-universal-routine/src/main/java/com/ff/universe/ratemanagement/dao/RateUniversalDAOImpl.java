/**
 * 
 */
package com.ff.universe.ratemanagement.dao;

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
import com.ff.domain.booking.BookingTypeConfigDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.pickup.PickupDeliveryContractWrapperDO;
import com.ff.domain.pickup.PickupDeliveryLocationDO;
import com.ff.domain.ratemanagement.masters.AccountGroupSapDO;
import com.ff.domain.ratemanagement.masters.ContractPaymentBillingLocationDO;
import com.ff.domain.ratemanagement.masters.CustomerGroupDO;
import com.ff.domain.ratemanagement.masters.RateContractDO;
import com.ff.domain.ratemanagement.masters.RateCustomerCategoryDO;
import com.ff.domain.ratemanagement.masters.RateCustomerProductCatMapDO;
import com.ff.domain.ratemanagement.masters.RateIndustryCategoryDO;
import com.ff.domain.ratemanagement.masters.RateIndustryTypeDO;
import com.ff.domain.ratemanagement.masters.RateMinChargeableWeightDO;
import com.ff.domain.ratemanagement.masters.RateProductCategoryDO;
import com.ff.domain.ratemanagement.masters.RateSectorsDO;
import com.ff.domain.ratemanagement.masters.RateTaxComponentDO;
import com.ff.domain.ratemanagement.masters.RateVobSlabsDO;
import com.ff.domain.ratemanagement.masters.RateWeightSlabsDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;
import com.ff.domain.ratemanagement.operations.ba.BAMaterialRateDetailsDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.to.ratemanagement.operations.StockRateTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.ratemanagement.constant.RateUniversalConstants;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;

/**
 * @author rmaladi
 *
 */
public class RateUniversalDAOImpl extends CGBaseDAO implements RateUniversalDAO {
	private final static Logger LOGGER = LoggerFactory.getLogger(RateUniversalDAOImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public List<RateIndustryCategoryDO> getRateIndustryCategoryList(String applicable)
			throws CGSystemException {
		LOGGER.trace("RateUniversalDAOImpl :: getIndustryCategoryList() :: Start --------> ::::::");
		 
		 Session session = null;
		 List<RateIndustryCategoryDO> rateIndustryCategoryDOList = null;
		 Criteria criteria=null;
			try{
				session = createSession();
				criteria = session.createCriteria(RateIndustryCategoryDO.class,"rateIndustryCategoryDO");
				if(!StringUtil.isStringEmpty(applicable) && applicable.equals(RateUniversalConstants.RATE_BENCH_MARK))
					criteria.add(Restrictions.eq("rateIndustryCategoryDO.rateBenchMarkApplicable", RateUniversalConstants.YES));
				else if	(!StringUtil.isStringEmpty(applicable) && applicable.equals(RateUniversalConstants.RATE_QUOTATION))
					criteria.add(Restrictions.eq("rateIndustryCategoryDO.rateQuotationApplicable", RateUniversalConstants.YES));
				
				criteria.addOrder(Order.asc("rateIndustryCategoryDO.rateIndustryCategoryId"));
				rateIndustryCategoryDOList = criteria.list(); 
			}
			catch (Exception e) {
				LOGGER.error("ERROR : RateUniversalDAOImpl.getIndustryCategoryList", e);
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.trace("RateUniversalDAOImpl :: getIndustryCategoryList() :: End --------> ::::::");
			return rateIndustryCategoryDOList;

	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RateProductCategoryDO> getRateProductCategoryList(String type)
			throws CGSystemException {
		LOGGER.trace("RateUniversalDAOImpl :: getRateProductCategoryList() :: Start --------> ::::::");
		 
		 Session session = null;
		 List<RateProductCategoryDO> rateProductCategoryDOList = null;
		 Criteria criteria=null;
			try{
				session = createSession();
				criteria = session.createCriteria(RateProductCategoryDO.class,"rateProductCategoryDO");
				criteria.add(Restrictions.eq("rateProductCategoryDO.rateProductCategoryType",type));
				criteria.addOrder(Order.asc("rateProductCategoryDO.rateProductCategoryId"));
				rateProductCategoryDOList = criteria.list(); 
			}
			catch (Exception e) {
				LOGGER.error("ERROR : RateUniversalDAOImpl.getRateProductCategoryList", e);
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.trace("RateUniversalDAOImpl :: getRateProductCategoryList() :: End --------> ::::::");
			return rateProductCategoryDOList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RateVobSlabsDO> getRateVobSlabsList(String type, String custCatCode)
			throws CGSystemException {
		LOGGER.trace("RateUniversalDAOImpl :: getVobSlabsList() :: Start --------> ::::::");
		 
		 Session session = null;
		 List<RateVobSlabsDO> rateVobSlabsDOList = null;
		
			try{
				
				String query = RateUniversalConstants.QRY_GET_RATE_VOB_SLAB_LIST_BY_EFFECTIVE_DATE;
				
				String params[] = {RateUniversalConstants.PARAM_EFFECTIVE_FROM,RateUniversalConstants.PARAM_EFFECTIVE_TO,RateUniversalConstants.PARAM_RATE_CUST_CAT_CODE,RateUniversalConstants.PARAM_RATE_PROD_CAT_TYPE};
				
				Date date = DateUtil.stringToDDMMYYYYFormat(DateUtil.getCurrentDateInDDMMYYYY());
				
				Object values[] = {date,date,custCatCode,type};
				
				rateVobSlabsDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);

			}
			catch (Exception e) {
				LOGGER.error("ERROR : RateBenchMarkDAOImpl.getVobSlabsList");
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.trace("RateUniversalDAOImpl :: getVobSlabsList() :: End --------> ::::::");
			return rateVobSlabsDOList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RateWeightSlabsDO> getRateWeightSlabsList(String type, String custCatCode)
			throws CGSystemException {
		LOGGER.trace("RateUniversalDAOImpl :: getRateWeightSlabsList() :: Start --------> ::::::");
		 
		 Session session = null;
		 List<RateWeightSlabsDO> rateWeightSlabsDOList = null;
		
			try{
				
				String query = RateUniversalConstants.QRY_GET_RATE_WEIGHT_SLAB_LIST_BY_EFFECTIVE_DATE;
				
				String params[] = {RateUniversalConstants.PARAM_EFFECTIVE_FROM,RateUniversalConstants.PARAM_EFFECTIVE_TO,RateUniversalConstants.PARAM_RATE_CUST_CAT_CODE,RateUniversalConstants.PARAM_RATE_PROD_CAT_TYPE};
				
				Date date = DateUtil.stringToDDMMYYYYFormat(DateUtil.getCurrentDateInDDMMYYYY());
				
				Object values[] = {date,date,custCatCode,type};
				
				rateWeightSlabsDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
				
				
			}
			catch (Exception e) {
				LOGGER.error("ERROR : RateUniversalDAOImpl.getRateWeightSlabsList");
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.trace("RateUniversalDAOImpl :: getRateWeightSlabsList() :: End --------> ::::::");
			return rateWeightSlabsDOList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RateSectorsDO> getRateSectorsList(String type, String custCatCode) throws CGSystemException {
		LOGGER.trace("RateUniversalDAOImpl :: getRateSectorsList() :: Start --------> ::::::");
		 
		Session session = null;
		List<RateSectorsDO> rateSectorsDOList = null;
				
			try{
						
					String query = RateUniversalConstants.QRY_GET_RATE_SECTORS;
						
					String params[] = {RateUniversalConstants.PARAM_RATE_CUST_CAT_CODE,RateUniversalConstants.PARAM_RATE_PROD_CAT_TYPE};
					
					Object values[] = {custCatCode,type};
						
					rateSectorsDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
						
			}catch (Exception e) {
				LOGGER.error("ERROR : RateUniversalDAOImpl.getRateSectorsList");
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.trace("RateUniversalDAOImpl :: getRateSectorsList() :: End --------> ::::::");
			return rateSectorsDOList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RateCustomerProductCatMapDO> getRateCustomerProductCatMapList()
			throws CGSystemException {
		LOGGER.trace("RateUniversalDAOImpl :: getRateCustomerProductCatMapList() :: Start --------> ::::::");
		 
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
				LOGGER.error("ERROR : RateUniversalDAOImpl.getRateCustomerProductCatMapList");
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.trace("RateUniversalDAOImpl :: getRateCustomerProductCatMapList() :: End --------> ::::::");
			return rateCustProductCatMapDOList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RateMinChargeableWeightDO> getRateMinChrgWtList(String type, String custCatCode)
			throws CGSystemException {
		LOGGER.trace("RateUniversalDAOImpl :: getRateMinChargeWtList() :: Start --------> ::::::");
		 
			 Session session = null;
			List<RateMinChargeableWeightDO> rateMinChargeWtDOList = null;
			
				try{
					
					String query = RateUniversalConstants.QRY_GET_RATE_MIN_CHRG_WEIGHT;
					
					String params[] = {RateUniversalConstants.PARAM_RATE_CUST_CAT_CODE,RateUniversalConstants.PARAM_RATE_PROD_CAT_TYPE};
					
					Object values[] = {custCatCode,type};
					
					rateMinChargeWtDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
					
					
				}catch (Exception e) {
					LOGGER.error("ERROR : RateUniversalDAOImpl.getRateMinChrgWtList");
					throw new CGSystemException(e);
				}
				finally{
					closeSession(session);
				}		
				LOGGER.trace("RateUniversalDAOImpl :: getRateMinChrgWtList() :: End --------> ::::::");
				return rateMinChargeWtDOList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RateCustomerCategoryDO> getRateCustomerCategoryList()
			throws CGSystemException {
		LOGGER.trace("RateUniversalDAOImpl :: getRateCustomerCategoryList() :: Start --------> ::::::");
		 
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
				LOGGER.error("ERROR : RateUniversalDAOImpl.getRateCustomerCategoryList");
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.trace("RateUniversalDAOImpl :: getRateCustomerCategoryList() :: End --------> ::::::");
			return rateCustomerCategoryDOList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RateSectorsDO> getRateConfigSectorsList(String type, String custCatCode)
			throws CGSystemException {
		LOGGER.trace("RateUniversalDAOImpl :: getRateConfigSectorsList() :: START --------> ::::::");
		Session session = null;
		List<RateSectorsDO> rateSectorsDOList = null;
				
			try{
						
					String query = RateUniversalConstants.QRY_GET_RATE_CONFIG_SECTORS;
						
					String params[] = {RateUniversalConstants.PARAM_RATE_CUST_CAT_CODE,RateUniversalConstants.PARAM_RATE_PROD_CAT_TYPE};
					
					Object values[] = {custCatCode,type};
						
					rateSectorsDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
						
			}catch (Exception e) {
				LOGGER.error("ERROR : RateUniversalDAOImpl.getRateConfigSectorsList");
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.trace("RateUniversalDAOImpl :: getRateConfigSectorsList() :: End --------> ::::::");
			return rateSectorsDOList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<StockStandardTypeDO> getStandardType(String typeName)
			throws CGSystemException {
		LOGGER.trace("RateUniversalDAOImpl :: getStandardType() :: START --------> ::::::");
		List<StockStandardTypeDO> stockStandardTypeDO = null;
		try {
			
			String queryName = "getStockStdTypeByTypeName";
			
			stockStandardTypeDO = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, "typeName", typeName);

			
			
		} catch (Exception e) {
			LOGGER.error("ERROR : RateUniversalDAOImpl.getStandardType", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("RateUniversalDAOImpl :: getStandardType() :: END --------> ::::::");
		return stockStandardTypeDO;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public RateIndustryTypeDO getIndustryTypeByCode(String rateIndustryTypeCode)
			throws CGSystemException {
		LOGGER.debug("RateUniversalDAOImpl :: getIndustryTypeByCode :: Start");
		List<RateIndustryTypeDO> rateIndustryTypeList = null;
		RateIndustryTypeDO rateIndustryTypeDO = null;
		try{
			String queryName = RateUniversalConstants.QRY_GET_RATE_INDUSTRY_TYPE_BY_CODE;
			String params[] = {RateUniversalConstants.RATE_INDUSTRY_TYPE_CODE};
			String values[] = {rateIndustryTypeCode};
			rateIndustryTypeList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values);
			if(!StringUtil.isEmptyColletion(rateIndustryTypeList)){
				rateIndustryTypeDO = rateIndustryTypeList.get(0);
			}
		}catch(Exception e){
			LOGGER.error("ERROR : RateUniversalDAOImpl.getIndustryTypeByCode", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateUniversalDAOImpl :: getIndustryTypeByCode :: End");
		return rateIndustryTypeDO;
	}


	@SuppressWarnings("unchecked")
	@Override
	public RateCustomerCategoryDO getRateCustCategoryGrpByCode(
			String rateCustomerCategoryCode) throws CGSystemException {
		LOGGER.debug("RateUniversalDAOImpl :: getRateCustCategoryGrpByCode :: Start");
		List<RateCustomerCategoryDO> rateCustomerCategoryDOList = null;
		RateCustomerCategoryDO rateCustomerCategoryDO = null;
		try{
			String queryName = RateUniversalConstants.QRY_GET_RATE_CUST_CATEGORY_BY_CODE;
			String params[] = {RateUniversalConstants.CUST_CATEGORY_CODE};
			String values[] = {rateCustomerCategoryCode};
			rateCustomerCategoryDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values);
			if(!StringUtil.isEmptyColletion(rateCustomerCategoryDOList)){
				rateCustomerCategoryDO = rateCustomerCategoryDOList.get(0);
			}
		}catch(Exception e){
			LOGGER.error("ERROR : RateUniversalDAOImpl.getRateCustCategoryGrpByCode", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateUniversalDAOImpl :: getRateCustCategoryGrpByCode :: End");
		return rateCustomerCategoryDO;
	}


	@SuppressWarnings("unchecked")
	@Override
	public CustomerGroupDO getCustomerGroupByCode(String customerGroupCode)
			throws CGSystemException {
		LOGGER.debug("RateUniversalDAOImpl :: getCustomerGroupByCode :: Start");
		List<CustomerGroupDO> customerGroupDOList = null;
		CustomerGroupDO customerGroupDO = null;
		try{
			String queryName = RateUniversalConstants.QRY_GET_CUST_GROUP_BY_CODE;
			String params[] = {RateUniversalConstants.CUST_GROUP_CODE};
			String values[] = {customerGroupCode};
			customerGroupDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values);
			if(!StringUtil.isEmptyColletion(customerGroupDOList)){
				customerGroupDO = customerGroupDOList.get(0);
			}
		}catch(Exception e){
			LOGGER.error("ERROR : RateUniversalDAOImpl.getCustomerGroupByCode", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateUniversalDAOImpl :: getCustomerGroupByCode :: End");
		return customerGroupDO;
	}


	@SuppressWarnings("unchecked")
	@Override
	public StockStandardTypeDO getBillingCycleByStdCode(String stdTypeCode)
			throws CGSystemException {
		LOGGER.debug("RateUniversalDAOImpl :: getBillingCycleByStdCode :: Start");
		List<StockStandardTypeDO> stockStandardTypeDOList = null;
		StockStandardTypeDO stockStandardTypeDO = null;
		try{
			String queryName = RateUniversalConstants.QRY_GET_BILLING_CYCLE_BY_STD_CODE;
			String params[] = {RateUniversalConstants.STD_TYPE_CODE};
			String values[] = {stdTypeCode};
			stockStandardTypeDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values);
			if(!StringUtil.isEmptyColletion(stockStandardTypeDOList)){
				stockStandardTypeDO = stockStandardTypeDOList.get(0);
			}
		}catch(Exception e){
			LOGGER.error("ERROR : RateUniversalDAOImpl.getBillingCycleByStdCode", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateUniversalDAOImpl :: getBillingCycleByStdCode :: End");
		return stockStandardTypeDO;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<String> getShippedToCodesByCustomerId(Integer customerId)
			throws CGSystemException {
		LOGGER.debug("RateUniversalDAOImpl :: getShippedToCodesByCustomerId() :: Start --------> ::::::");
		List<String> shippedToCodes = null;
		try {
			shippedToCodes = getHibernateTemplate().findByNamedQueryAndNamedParam(
					RateUniversalConstants.QRY_GET_SHIPPED_TO_CODES_BY_CUSTOMER_ID,
					RateUniversalConstants.PARAM_CUSTOMER_ID,
					customerId);

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::RateUniversalDAOImpl::getShippedToCodesByCustomerId() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateUniversalDAOImpl :: getShippedToCodesByCustomerId() :: End --------> ::::::");
		return shippedToCodes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateContractDO> getRateContractsByCustomerIds(
			List<Integer> customerIds) throws CGSystemException {
		LOGGER.debug("RateUniversalDAOImpl :: getRateContractsByCustomerIds() :: Start --------> ::::::");
		List<RateContractDO> rateContractDOs = null;
		try {
			rateContractDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					RateUniversalConstants.QRY_GET_RATE_CONTRACTS_BY_CUSTOMER_IDS,
					RateUniversalConstants.PARAM_CUSTOMER_IDS,
					customerIds);

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::RateUniversalDAOImpl::getRateContractsByCustomerIds() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateUniversalDAOImpl :: getRateContractsByCustomerIds() :: End --------> ::::::");
		return rateContractDOs;
	}


	@SuppressWarnings("unchecked")
	@Override
	public CustomerTypeDO getCustomerTypeByCode(String customerTypeCode)throws CGSystemException {
		LOGGER.debug("RateUniversalDAOImpl :: getCustomerTypeByCode :: Start");
		List<CustomerTypeDO> custTypeDOList = null;
		CustomerTypeDO custTypeDO = null;
		try{
			String queryName = RateUniversalConstants.QRY_GET_CUST_TYPE_BY_CODE;
			String params[] = {RateUniversalConstants.CUST_TYPE_CODE};
			String values[] = {customerTypeCode};
			custTypeDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values);
			if(!StringUtil.isEmptyColletion(custTypeDOList)){
				custTypeDO = custTypeDOList.get(0);
			}
		}catch(Exception e){
			LOGGER.error("ERROR : RateUniversalDAOImpl.getCustomerTypeByCode", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateUniversalDAOImpl :: getCustomerTypeByCode :: End");
		return custTypeDO;
	}


	@SuppressWarnings("unchecked")
	@Override
	public AccountGroupSapDO getRateCustCategoryByCustGroup(String customerGroup)
			throws CGSystemException {
		LOGGER.debug("RateUniversalDAOImpl :: getRateCustCategoryByCustGroup :: Start");
		List<AccountGroupSapDO> accGrpSAPDOList = null;
		AccountGroupSapDO accGrpSAPDO = null;
		try{
			String queryName = RateUniversalConstants.QRY_GET_RATE_CUST_CAT_BY_CUST_GROUP_CODE;
			String params[] = {RateUniversalConstants.CUST_GROUP};
			String values[] = {customerGroup};
			accGrpSAPDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values);
			if(!StringUtil.isEmptyColletion(accGrpSAPDOList)){
				accGrpSAPDO = accGrpSAPDOList.get(0);
			}
		}catch(Exception e){
			LOGGER.error("ERROR : RateUniversalDAOImpl.getRateCustCategoryByCustGroup", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateUniversalDAOImpl :: getRateCustCategoryByCustGroup :: End");
		return accGrpSAPDO;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDO> getCustomersByPickupDeliveryLocation(
			Integer officeId) throws CGSystemException {
		LOGGER.debug("RateUniversalDAOImpl :: getCustomersByPickupDeliveryLocation :: Start");
		List<CustomerDO> customerDOs = null;
		try{
			customerDOs = getHibernateTemplate()
						.findByNamedQueryAndNamedParam(
								RateUniversalConstants.QRY_GET_CUSTOMERS_BY_PICKUP_DELIVERY_LOCATION,
							new String[] {RateUniversalConstants.PARAM_OFFICE_ID},
							new Object[] {officeId});
		}catch(Exception e){
			LOGGER.error("ERROR : RateUniversalDAOImpl.getCustomersByPickupDeliveryLocation", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateUniversalDAOImpl :: getCustomersByPickupDeliveryLocation :: End");
		return customerDOs;
	}


	@SuppressWarnings("unchecked")
	@Override
	public RateContractDO getContractDtlsByContractNo(String contractNo) throws CGSystemException {
		LOGGER.debug("RateUniversalDAOImpl :: getContractDtlsByContractNo :: START");
		List<RateContractDO> rateContractDOList = null;
		RateContractDO rateContractDO = null;
		try{
			rateContractDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.QRY_GET_CONTRACT_DTLS_BY_NO,
					UdaanCommonConstants.RATE_CONTRACT_NO, contractNo);
			if(!StringUtil.isNull(rateContractDOList)
					&&(!StringUtil.isEmptyColletion(rateContractDOList))){
				rateContractDO = rateContractDOList.get(0);
			}
		}catch(Exception e){
			 LOGGER.error("OrganizationCommonDAOImpl :: getContractDtlsByContractNo ",e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateUniversalDAOImpl :: getContractDtlsByContractNo :: END");
		return rateContractDO;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<ContractPaymentBillingLocationDO> getContractPayBillingLocationDtlsByRateContractId(Integer rateContractId) throws CGSystemException {
		LOGGER.debug("RateUniversalDAOImpl :: getContractPayBillingLocationDtlsByRateContractId ::Start");
		List<ContractPaymentBillingLocationDO> rateContractPaymentDOList = null;
		try{
			rateContractPaymentDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
							UdaanCommonConstants.QRY_GET_CONTRACT_PAY_DTLS,
							UdaanCommonConstants.RATE_CONTRACT_ID, rateContractId);
		}catch(Exception e){
			 LOGGER.error("ERROR : OrganizationCommonDAOImpl.getContractDtlsByContractNo",e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateUniversalDAOImpl :: getContractPayBillingLocationDtlsByRateContractId :: End");
		return rateContractPaymentDOList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<BAMaterialRateDetailsDO> getBAMaterialRateDetailsByMaterial(StockRateTO stockRateTo) throws CGSystemException {
		LOGGER.debug("RateUniversalDAOImpl :: getBAMaterialRateDetailsByMaterial ::Start");
		List<BAMaterialRateDetailsDO> rateContractPaymentDOList = null;
		try{
			String params[]={StockUniveralConstants.QRY_PARAM_ITEM_ID,StockUniveralConstants.QRY_PARAM_LOGGED_IN_DATE};
			Object value[]={stockRateTo.getItemId(),DateUtil.trimTimeFromDate(stockRateTo.getDate())};
			rateContractPaymentDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					StockUniveralConstants.QRY_GET_BA_RATE_CONFIG_BY_MATERIAL,params,value);
		}catch(Exception e){
			 LOGGER.error("RateUniversalDAOImpl : getBAMaterialRateDetailsByMaterial.::Exception",e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateUniversalDAOImpl :: getBAMaterialRateDetailsByMaterial :: End");
		return rateContractPaymentDOList;
	}
	
	/**
	 * getTaxComponents 
	 * @param stateId
	 * @param currentDate
	 * @param taxGroup
	 * @return
	 * 
	 */
	
	//Note : it's duplicate Method it's 
	@Override
	public List<RateTaxComponentDO> getTaxComponents(Integer stateId,
			Date currentDate) {
		LOGGER.trace("RateUniversalDAOImpl :: getTaxComponents ::Start");
		List<RateTaxComponentDO> rateTaxComponents = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(RateUniversalConstants.QRY_GET_TAX_COMPONENTS_FOR_CONFIGURATION);
			query.setParameter(RateUniversalConstants.STATE_ID, stateId);
			query.setParameter(RateUniversalConstants.CURRENT_DATE, currentDate);
			rateTaxComponents = query.list();
		}catch(Exception e){
			 LOGGER.error("RateUniversalDAOImpl : getTaxComponents.::Exception",e.getMessage());			
		} finally {
			session.close();
		}
		LOGGER.trace("RateUniversalDAOImpl :: getTaxComponents ::END");
		return rateTaxComponents;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<PickupDeliveryContractWrapperDO> getPickUpDeliveryContrat(Integer customerId) throws CGSystemException {
		LOGGER.debug("RateUniversalDAOImpl :: getPickUpDeliveryContrat :: Start");
		List<PickupDeliveryContractWrapperDO> pickUpcontractWrapperDOs = null;
		try{
			pickUpcontractWrapperDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					RateUniversalConstants.QRY_GET_PICKUP_CONTRACT_DTLS,
					RateUniversalConstants.PARAM_CUSTOMER_ID, customerId);
			
		}catch(Exception e){
			LOGGER.error("ERROR : RateUniversalDAOImpl.getPickUpDeliveryContrat",e);
			throw new CGSystemException(e);	
		}
		LOGGER.debug("RateUniversalDAOImpl :: getPickUpDeliveryContrat :: End");
		return pickUpcontractWrapperDOs;
	}

	//Added By CBHURE
	//Emp ID - 47892
	/* 
	 * 
	 * Added common service to get contract payment billing location dtls by pick up location ID
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public ContractPaymentBillingLocationDO getContractPayBillingLocationDtlsBypickupLocation(
			Integer pickupDlvLocId) throws CGSystemException {
		LOGGER.debug("RateUniversalDAOImpl :: getContractPayBillingLocationDtlsBypickupLocation :: Start");
		List<ContractPaymentBillingLocationDO> contractPaymentBillingLocationDOs = null;
		ContractPaymentBillingLocationDO contractPaymentBillingLocationDO = null;
		try{
			contractPaymentBillingLocationDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					RateUniversalConstants.QRY_GET_CONTRACT_PAY_BILLING_LOCATION_DTLS,
					RateUniversalConstants.PICKUP_DELIVERY_LOCATION, pickupDlvLocId);
			
			if(!StringUtil.isEmptyColletion(contractPaymentBillingLocationDOs)){
				contractPaymentBillingLocationDO = contractPaymentBillingLocationDOs.get(0);
			}
		}catch(Exception e){
			LOGGER.error("ERROR : RateUniversalDAOImpl.getContractPayBillingLocationDtlsBypickupLocation",e);
			throw new CGSystemException(e);	
		}
		LOGGER.debug("RateUniversalDAOImpl :: getContractPayBillingLocationDtlsBypickupLocation :: End");
		return contractPaymentBillingLocationDO;
	}

	// Added By hkansagr
	// Emp. Id - 46599
	/*
	 * 
	 * Added common service to get pickup delivery location by pick up contract
	 * id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PickupDeliveryLocationDO getPickupDlvLocationByContractId(
			Integer contractId) throws CGSystemException {
		LOGGER.debug("RateUniversalDAOImpl :: getPickupDlvLocationByContractId() :: START");
		List<PickupDeliveryLocationDO> pickupLocationDOs = null;
		PickupDeliveryLocationDO pickupLocationDO = null;
		try {
			pickupLocationDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							RateUniversalConstants.QRY_GET_PICKUP_DLV_LOCATION_BY_CONTRACT_ID,
							RateUniversalConstants.PARAM_CONTRACT_ID,
							contractId);

			if (!StringUtil.isEmptyColletion(pickupLocationDOs)) {
				pickupLocationDO = pickupLocationDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : RateUniversalDAOImpl.getPickupDlvLocationByContractId() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateUniversalDAOImpl :: getPickupDlvLocationByContractId() :: END");
		return pickupLocationDO;
	}
	
	@Override
	public BookingTypeConfigDO getBookingTypeConfigVWDeno()
			throws CGSystemException {
		Session session = null;
		BookingTypeConfigDO configDO = null;
		Criteria criteria = null;
		try {
			LOGGER.trace("RateUniversalDAOImpl::getBookingTypeConfigVWDeno::START------------>:::::::");
			session = createSession();
			criteria = session.createCriteria(BookingTypeConfigDO.class,
					"bookingTypeConfigDO");
			criteria.addOrder(Order.asc("bookingTypeConfigDO.bookingTypeConfigId"));
			criteria.setMaxResults(1);

			configDO = (BookingTypeConfigDO) criteria.uniqueResult();

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::RateUniversalDAOImpl::getBookingTypeConfigVWDeno :: "
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("RateUniversalDAOImpl::getBookingTypeConfigVWDeno::END------------>:::::::");
		return configDO;

	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PickupDeliveryContractWrapperDO> getPickUpDeliveryContratByOfficeId(Integer officeId) throws CGSystemException {
		LOGGER.debug("RateUniversalDAOImpl :: getPickUpDeliveryContratByOfficeId :: Start");
		List<PickupDeliveryContractWrapperDO> pickupDeliveryContractWrapperDOs = null;
		try{
			pickupDeliveryContractWrapperDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					RateUniversalConstants.QRY_GET_PICKUP_CONTRACT_DTLS_BY_OFC_ID,
					RateUniversalConstants.PARAM_OFFICE_ID, officeId);
		}catch(Exception e){
			LOGGER.error("ERROR : RateUniversalDAOImpl.getPickUpDeliveryContratByOfficeId",e);
			throw new CGSystemException(e);	
		}
		LOGGER.debug("RateUniversalDAOImpl :: getPickUpDeliveryContratByOfficeId :: End");
		return pickupDeliveryContractWrapperDOs;
	}

	
	/*Kamal for outsatnding report */

	@SuppressWarnings("unchecked")
	@Override
	public List<PickupDeliveryContractWrapperDO> getPickUpDeliveryContratWrapperDOByOfficeId(
			Integer officeId) throws CGSystemException {
		LOGGER.debug("RateUniversalDAOImpl :: getPickUpDeliveryContratByOfficeId :: Start");
		
		List<PickupDeliveryContractWrapperDO> pickupDeliveryContractWrapperDOs = null;
		try{
			pickupDeliveryContractWrapperDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					RateUniversalConstants.QRY_GET_PICKUP_CONTRACT_WRAPPERDO_BY_OFC_ID,
					RateUniversalConstants.PARAM_OFFICE_ID, officeId);
		}catch(Exception e){
			LOGGER.error("ERROR : RateUniversalDAOImpl.getPickUpDeliveryContratByOfficeId",e);
			throw new CGSystemException(e);	
		}
		
		return pickupDeliveryContractWrapperDOs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public RateIndustryCategoryDO getIndustryCategoryByCode(String indCatCode)throws CGSystemException {
		LOGGER.debug("RateUniversalDAOImpl :: getIndustryCategoryByCode :: Start");
		List<RateIndustryCategoryDO> indCatDOList = null;
		RateIndustryCategoryDO indCatDO = null;
		try{
			String queryName = RateUniversalConstants.QRY_GET_IND_CAT_BY_CODE;
			String params[] = {RateUniversalConstants.IND_CAT_CODE};
			String values[] = {indCatCode};
			indCatDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values);
			if(!StringUtil.isEmptyColletion(indCatDOList)){
				indCatDO = indCatDOList.get(0);
			}
		}catch(Exception e){
			LOGGER.error("ERROR : RateUniversalDAOImpl.getIndustryCategoryByCode", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateUniversalDAOImpl :: getIndustryCategoryByCode :: End");
		return indCatDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WeightSlabDO> getWeightSlabByWtSlabCate(String wtSlabCate)
			throws CGSystemException {
		LOGGER.trace("RateUniversalDAOImpl :: getWeightSlabByWtSlabCate() :: START");
		List<WeightSlabDO> wtSlabDOs = null;
		try {
			String queryName = RateUniversalConstants.QRY_GET_WEIGHT_SLAB_BY_WT_SLAB_CATE;
			String[] paramName = { RateUniversalConstants.QRY_PARAM_WT_SLAB_CAT };
			Object[] value = { wtSlabCate };
			wtSlabDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, paramName, value);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in RateUniversalDAOImpl :: getWeightSlabByWtSlabCate() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("RateUniversalDAOImpl :: getWeightSlabByWtSlabCate() :: END");
		return wtSlabDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WeightSlabDO> getWeightSlabByWtSlabCateAndEndWt(
			String wtSlabCate, Double endWeight) throws CGSystemException {
		LOGGER.trace("RateUniversalDAOImpl :: getWeightSlabByWtSlabCateAndEndWt() :: START");
		List<WeightSlabDO> wtSlabDOs = null;
		try {
			String queryName = RateUniversalConstants.QRY_GET_WEIGHT_SLAB_BY_WT_SLAB_CATE_AND_END_WT;
			String[] paramName = {
					RateUniversalConstants.QRY_PARAM_WT_SLAB_CAT,
					RateUniversalConstants.QRY_PARAM_END_WT };
			Object[] value = { wtSlabCate, endWeight };
			wtSlabDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, paramName, value);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in RateUniversalDAOImpl :: getWeightSlabByWtSlabCateAndEndWt() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("RateUniversalDAOImpl :: getWeightSlabByWtSlabCateAndEndWt() :: END");
		return wtSlabDOs;
	}

}
