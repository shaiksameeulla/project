package com.ff.universe.business.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.CustomerTO;
import com.ff.business.LoadMovementVendorTO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.business.LoadMovementVendorDO;
import com.ff.domain.business.VendorTypeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.organization.OfficeTO;
import com.ff.universe.business.constant.BusinessUniversalConstants;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.manifest.constant.ManifestUniversalConstants;

public class BusinessCommonDAOImpl extends CGBaseDAO implements
		BusinessCommonDAO {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BusinessCommonDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDO> getCustomer(Integer customerId)
			throws CGSystemException {
		List<CustomerDO> customerDO = null;
		try {
			customerDO = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.GET_CUSTOMER,
					UdaanCommonConstants.CUSTOMER_ID, customerId);
		} catch (Exception e) {
			throw new CGSystemException(e);
		}
		return customerDO;
	}

	@Override
	public List<CustomerDO> getAllCustomers() throws CGSystemException {
		// TODO Auto-generated method stub

		List<CustomerDO> customerDO = null;
		try {
			customerDO = getHibernateTemplate().findByNamedQuery(
					UdaanCommonConstants.GET_ALL_CUSTOMERS);
		} catch (Exception e) {
			throw new CGSystemException(e);
		}
		return customerDO;
	}

	/*@Override
	public List<FranchiseeDO> getAllFranchiseeUnderRegion(
			FranchiseeDO franchiseeDo) throws CGSystemException {

		List<FranchiseeDO> franchiseeDOLst = null;
		Criteria frCriteria = null;
		DetachedCriteria frOffice = null;
		Session session = null;
		try {
			session = createSession();
			// for main Query ######START######
			frCriteria = session.createCriteria(FranchiseeDO.class,
					"franchisee");
			if (!StringUtil.isStringEmpty(franchiseeDo.getFranchiseeCode())) {
				frCriteria.add(Restrictions.eq("franchisee.franchiseeCode",
						franchiseeDo.getFranchiseeCode()));
			}

			if (franchiseeDo.getOfficeMappedDo() != null) {
				// for Sub Query ****START****
				frOffice = DetachedCriteria
						.forClass(OfficeDO.class, "frOffice");
				Disjunction disc = Restrictions.disjunction();
				if (!StringUtil.isEmptyInteger(franchiseeDo.getOfficeMappedDo()
						.getOfficeId())) {
					disc.add(Restrictions.eq("frOffice.officeId", franchiseeDo
							.getOfficeMappedDo().getOfficeId()));
				}
				if (!StringUtil.isEmptyInteger(franchiseeDo.getOfficeMappedDo()
						.getReportingRHO())) {
					disc.add(Restrictions.eq("frOffice.reportingRHO",
							franchiseeDo.getOfficeMappedDo().getReportingRHO()));
				}

				frOffice.add(disc);
				frOffice.setProjection(Projections
						.property("frOffice.officeId"));
				// for Sub Query ****END****

				// for Main Query Join Condition
				frCriteria.add(Property.forName(
						"franchisee.officeMappedDo.officeId").in(frOffice));
			}
			// frOffice.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			// frOffice.setResultTransformer(Transformers.aliasToBean(FranchiseeDO.class));
			franchiseeDOLst = frCriteria.list();
			// for main Query ######END######
		} catch (Exception e) {
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return franchiseeDOLst;
	}*/

	@Override
	public List<CustomerDO> getAllBusinessAssociatesUnderRegion(
			CustomerDO baDo) throws CGSystemException {

		List<CustomerDO> businessAssociateDOList = null;
		Criteria frCriteria = null;
		DetachedCriteria frOffice = null;
		Session session = null;
		try {
			session = createSession();
			// for main Query ######START######
			frCriteria = session
					.createCriteria(CustomerDO.class, "ba");
			if (!StringUtil.isStringEmpty(baDo.getCustomerCode())) {
				frCriteria.add(Restrictions.eq("ba.customerCode", baDo.getCustomerCode()));
			}

			if (baDo.getOfficeMappedDO() != null) {
				// for Sub Query ****START****
				frOffice = DetachedCriteria
						.forClass(OfficeDO.class, "baOffice");
				Disjunction disc = Restrictions.disjunction();
				if (!StringUtil.isEmptyInteger(baDo.getOfficeMappedDO()
						.getOfficeId())) {
					disc.add(Restrictions.eq("baOffice.officeId", baDo
							.getOfficeMappedDO().getOfficeId()));
				}
				if (!StringUtil.isEmptyInteger(baDo.getOfficeMappedDO()
						.getReportingRHO())) {
					disc.add(Restrictions.eq("baOffice.reportingRHO", baDo
							.getOfficeMappedDO().getReportingRHO()));
				}

				frOffice.add(disc);
				frOffice.setProjection(Projections
						.property("baOffice.officeId"));
				// for Sub Query ****END****

				// for Main Query Join Condition
				frCriteria.add(Property.forName("ba.officeMappedDO.officeId")
						.in(frOffice));
			}
			// frOffice.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			// frOffice.setResultTransformer(Transformers.aliasToBean(FranchiseeDO.class));
			businessAssociateDOList = frCriteria.list();
			// for main Query ######END######
		} catch (Exception e) {
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return businessAssociateDOList;
	}

	@Override
	public List<CustomerDO> getAllCustomersUnderRegion(CustomerDO customerDo)
			throws CGSystemException {

		List<CustomerDO> customerList = null;
		Criteria customerCriteria = null;
		DetachedCriteria customerOffice = null;
		DetachedCriteria customerType = null;
		Session session = null;
		try {
			session = createSession();
			// for main Query ######START######
			customerCriteria = session.createCriteria(CustomerDO.class,
					"customer");
			customerCriteria.add(Restrictions.eq("customer.status",
					CommonConstants.RECORD_STATUS_ACTIVE));
			if (!StringUtil.isEmptyInteger(customerDo.getCustomerId())) {
				customerCriteria.add(Restrictions.eq("customer.customerId",
						customerDo.getCustomerId()));
			}
			if (!StringUtil.isStringEmpty(customerDo.getCustomerCode())) {
				customerCriteria.add(Restrictions.eq("customer.customerCode",
						customerDo.getCustomerCode()));
			}

			if (!StringUtil.isNull(customerDo.getOfficeMappedDO())) {
				// for Sub Query ****START****
				customerOffice = DetachedCriteria.forClass(OfficeDO.class,
						"customerOffice");
				Disjunction disc = Restrictions.disjunction();
				if (!StringUtil.isEmptyInteger(customerDo.getOfficeMappedDO().getOfficeId())) {
					disc.add(Restrictions.eq("customerOffice.officeId",
							customerDo.getOfficeMappedDO().getOfficeId()));
				}
				if (!StringUtil.isEmptyInteger(customerDo.getOfficeMappedDO().getReportingRHO())) {
					disc.add(Restrictions.eq("customerOffice.reportingRHO",
							customerDo.getOfficeMappedDO().getReportingRHO()));
				}
				if (!StringUtil.isEmptyInteger(customerDo.getOfficeMappedDO().getReportingHUB())) {
					disc.add(Restrictions.eq("customerOffice.reportingHUB",
							customerDo.getOfficeMappedDO().getReportingHUB()));
				}

				customerOffice.add(disc);
				customerOffice.setProjection(Projections
						.property("customerOffice.officeId"));
				// for Sub Query ****END****

				// for Main Query Join Condition
				customerCriteria.add(Property.forName("customer.officeMappedDO.officeId")
						.in(customerOffice));
			}
			
			if (!StringUtil.isNull(customerDo.getSalesOfficeDO())) {
				// for Sub Query ****START****
				DetachedCriteria  salesOffice = DetachedCriteria.forClass(OfficeDO.class,
						"salesOffice");
				Disjunction disc = Restrictions.disjunction();
				if (!StringUtil.isEmptyInteger(customerDo.getSalesOfficeDO().getOfficeId())) {
					disc.add(Restrictions.eq("salesOffice.officeId",
							customerDo.getSalesOfficeDO().getOfficeId()));
				}
				if (!StringUtil.isEmptyInteger(customerDo.getSalesOfficeDO().getReportingRHO())) {
					disc.add(Restrictions.eq("salesOffice.reportingRHO",
							customerDo.getSalesOfficeDO().getReportingRHO()));
				}
				if (!StringUtil.isEmptyInteger(customerDo.getSalesOfficeDO().getReportingHUB())) {
					disc.add(Restrictions.eq("salesOffice.reportingHUB",
							customerDo.getSalesOfficeDO().getReportingHUB()));
				}

				salesOffice.add(disc);
				salesOffice.setProjection(Projections
						.property("salesOffice.officeId"));
				// for Sub Query ****END****

				// for Main Query Join Condition
				customerCriteria.add(Property.forName("customer.salesOfficeDO.officeId")
						.in(salesOffice));
			}
			if(!StringUtil.isNull(customerDo.getCustomerType())){
				//Customer type
				String customerTypeCode=customerDo.getCustomerType().getCustomerTypeCode();
				// for Sub Query ****START****
				customerType = DetachedCriteria.forClass(CustomerTypeDO.class,
						"customerTypeDO");

				if (!StringUtil.isStringEmpty(customerTypeCode)) {
					if(!customerTypeCode.contains(FrameworkConstants.CHARACTER_COMMA)){
					customerType.add(Restrictions.eq("customerTypeDO.customerTypeCode",
							customerTypeCode));
					}else{
						customerType.add(Restrictions.in("customerTypeDO.customerTypeCode",
								customerTypeCode.split(",")));
					}
				}
				customerType.setProjection(Projections
						.property("customerTypeDO.customerTypeId"));
				// for Sub Query ****END****

				// for Main Query Join Condition
				customerCriteria.add(Property.forName("customer.customerType.customerTypeId")
						.in(customerType));
			}
			
			// frOffice.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			// frOffice.setResultTransformer(Transformers.aliasToBean(FranchiseeDO.class));
			customerList = customerCriteria.list();
			// for main Query ######END######
		} catch (Exception e) {
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return customerList;
	}

	@Override
	public Integer getCustomerIdByCode(
			CustomerTO businessAssociateTO) throws  CGSystemException {
		Integer customerId = null;
		try {
			List<Object> customerIds = getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getCustomerIdByCode",
							"customerCode", businessAssociateTO.getCustomerCode());
			if (customerIds != null && customerIds.size() > 0) {
				customerId = (Integer) customerIds.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingCommonDAOImpl :: getCustomerIdByCode..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return customerId;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LoadMovementVendorDO> getPartyNamesForCC()
			throws CGSystemException {
		List<LoadMovementVendorDO> ccDOList=null;
		try {

			/* ccDOList = getHibernateTemplate()
					.findByNamedQuery(
							BusinessUniversalConstants.QRY_GET_PARTY_NAME_FOR_CC);*/

			return ccDOList;
		} catch (Exception e) {
			LOGGER.error("ERROR : GlobalServiceDAOImpl.getPartyNamesForFR", e);
			throw new CGSystemException(e);
		}
	}

	

	

	@SuppressWarnings("unchecked")
	@Override
	public CustomerDO getCustomerByIdOrCode(Integer customerId,
			String customerCode) throws CGSystemException {
		Criteria customerCriteria = null;
		Session session = null;
		CustomerDO customer = null;
		try {
			session = createSession();
			customerCriteria = session.createCriteria(CustomerDO.class);
			if (!StringUtil.isEmptyInteger(customerId))
				customerCriteria.add(Restrictions.eq("customerId", customerId));
			if (StringUtils.isNotEmpty(customerCode))
				customerCriteria.add(Restrictions.eq("customerCode",
						customerCode));
			customer = (CustomerDO) customerCriteria.uniqueResult();
		} catch (Exception e) {
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		return customer;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomerDO getBusinessAssociateByIdOrCode(Integer baId,
			String baCode) throws CGSystemException {
		Criteria baCriteria = null;
		Session session = null;
		CustomerDO customer = null;
		try {
			session = createSession();
			baCriteria = session.createCriteria(CustomerDO.class);
			if (!StringUtil.isEmptyInteger(baId)){
				baCriteria.add(Restrictions.eq("baId", baId));
			}
			if (StringUtils.isNotEmpty(baCode)){
				baCriteria.add(Restrictions.eq("baCode", baCode));
			}
			customer = (CustomerDO) baCriteria.uniqueResult();
		} catch (Exception e) {
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		return customer;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoadMovementVendorDO> getVendorsList(
			LoadMovementVendorTO loadMovementVendorTO) throws CGSystemException {
		Session session = null;
		List<LoadMovementVendorDO> loadMovementVendorDOList = null;
		Criteria criteria = null;
		try {
			session = createSession();
			criteria = session.createCriteria(LoadMovementVendorDO.class,
					"vendor");

			if (!StringUtil.isStringEmpty(loadMovementVendorTO.getVendorType())) {
				
				DetachedCriteria vendorType = DetachedCriteria.forClass(VendorTypeDO.class,
						"vendorType");
				
				vendorType.add(Restrictions.eq("vendorTypeCode", loadMovementVendorTO.getVendorType()));
				vendorType.setProjection(Projections
						.property("vendorType.vendorTypeId"));
				criteria.add(Property.forName("vendor.vendorTypeDO")
						.in(vendorType));
				
			}
			if (!StringUtil.isNull(loadMovementVendorTO.getOfficeTO())) {
				OfficeTO officeTO= loadMovementVendorTO.getOfficeTO();
				// for Sub Query ****START****
				DetachedCriteria vendorOffice = DetachedCriteria.forClass(OfficeDO.class,
						"mappedOffice");

				if(!StringUtil.isEmptyInteger(officeTO.getOfficeId())){
					vendorOffice.add(Restrictions.eq("mappedOffice.officeId",
							officeTO.getOfficeId()));
				}
				if(!StringUtil.isStringEmpty(officeTO.getOfficeCode())){
					vendorOffice.add(Restrictions.eq("mappedOffice.officeCode",
							officeTO.getOfficeCode()));
				}
				if(!StringUtil.isEmptyInteger(officeTO.getCityId())){
					vendorOffice.add(Restrictions.eq("mappedOffice.cityId",
							officeTO.getCityId()));
				}
				vendorOffice.setProjection(Projections
						.property("mappedOffice.officeId"));
				// for Sub Query ****END****
				// for Main Query Join Condition
				criteria.add(Property.forName("vendor.officeDO")
						.in(vendorOffice));
			}

			loadMovementVendorDOList = criteria.list();

		} catch (Exception e) {
			LOGGER.error("ERROR : GlobalServiceDAOImpl.getVendorsList", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.info("GlobalServiceDAOImpl :: getVendorsList() :: End --------> ::::::");
		return loadMovementVendorDOList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDO> getDtlsForTPBA(int baId)
			throws CGSystemException {
		List<CustomerDO> baDO = null;
		/*try {
			baDO = new ArrayList<>();
			baDO = getHibernateTemplate().findByNamedQueryAndNamedParam(
					BusinessUniversalConstants.QRY_GET_BA_CODE_BY_BA_ID,
					"baId", baId);
		} catch (Exception e) {
			throw new CGSystemException(e);
		}*/
		return baDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoadMovementVendorDO> getDtlsForTPCC(int partyId)
			throws CGSystemException {
		List<LoadMovementVendorDO> vendorDO = null;
		try {
			vendorDO = new ArrayList<>();
			vendorDO = getHibernateTemplate().findByNamedQueryAndNamedParam(
					BusinessUniversalConstants.QRY_GET_PARTY_CODE_BY_PARTY_ID,
					"vendorId", partyId);
		} catch (Exception e) {
			throw new CGSystemException(e);
		}
		return vendorDO;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDO> getDtlsForTPFR(int frId) throws CGSystemException {
		// TODO Auto-generated method stub
		List<CustomerDO> frDO = null;
		/*try {
			frDO = new ArrayList<>();
			frDO = getHibernateTemplate().findByNamedQueryAndNamedParam(
					BusinessUniversalConstants.QRY_GET_FR_CODE_BY_FR_ID,
					"franchiseeId", frId);
		} catch (Exception e) {
			throw new CGSystemException(e);
		}*/
		return frDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ConsigneeConsignorDO getConsigneeConsignorDtls(String cnNumber)
			throws CGSystemException {
		ConsigneeConsignorDO consigneeConsignorDO = null;
		try {
			String[] params = { BusinessUniversalConstants.CONSGINMENT_NUMBER };
			Object[] values = { cnNumber };
			List<ConsigneeConsignorDO> consignorDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							BusinessUniversalConstants.QRY_GET_CONSIGNEE_CONSIGNOR_DTLS,
							params, values);
			if (!StringUtil.isEmptyList(consignorDOs)) {
				consigneeConsignorDO = consignorDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR :: BusinessCommonDAOImpl :: getConsigneeConsignorDtls:::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return consigneeConsignorDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.business.dao.BusinessCommonDAO#getConsigneeConsignorDtls
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public ConsigneeConsignorDO getConsigneeConsignorDtls(String cnNumber,
			String partyType) throws CGSystemException {
		ConsigneeConsignorDO consigneeConsignorDO = null;
		try {
			String queryName = "";
			String[] params = { BusinessUniversalConstants.CONSGINMENT_NUMBER };
			Object[] values = { cnNumber };
			if (StringUtils.equalsIgnoreCase(partyType,
					CommonConstants.PARTY_TYPE_CONSIGNEE)) {
				queryName = BusinessUniversalConstants.QRY_GET_CONSIGNEE_CONSIGNOR_DTLS;
			} else if (StringUtils.equalsIgnoreCase(partyType,
					CommonConstants.PARTY_TYPE_CONSIGNER)) {
				queryName = BusinessUniversalConstants.QRY_GET_CONSIGNOR_DTLS;
			}
			List<ConsigneeConsignorDO> consignorDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);
			if (!StringUtil.isEmptyList(consignorDOs)) {
				consigneeConsignorDO = consignorDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR :: BusinessCommonDAOImpl :: getConsigneeConsignorDtls:::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return consigneeConsignorDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDO> getCustomersByOfficeId(Integer officeId)
			throws CGSystemException {
		LOGGER.debug("BusinessCommonDAOImpl :: getCustomersByOfficeId() :: Start --------> ::::::");
		List<CustomerDO> customerDOs = null;
		try {
			customerDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					BusinessUniversalConstants.QRY_GET_CUSTOMERS_BY_OFFICE_ID,
					BusinessUniversalConstants.PARAM_OFFICE_ID, officeId);

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::BusinessCommonDAOImpl::getCustomersByOfficeId() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("BusinessCommonDAOImpl :: getCustomersByOfficeId() :: End --------> ::::::");
		return customerDOs;
	}

	@Override
	public List<LoadMovementVendorDO> getPartyNames(String partyType,Integer officeId)
			throws CGSystemException {
		try {
			/*String[] params = { ManifestUniversalConstants.PARAM_VENDOR_CODE,ManifestUniversalConstants.PARAM_REGION_ID};
			Object[] values = { partyType,regionId };*/
			
			String[] params = { ManifestUniversalConstants.PARAM_VENDOR_CODE,BusinessUniversalConstants.PARAM_OFFICE_ID};
			Object[] values = { partyType, officeId};
			
			List<LoadMovementVendorDO> partyNameList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(BusinessUniversalConstants.QRY_GET_PARTY_NAMES,params,values);

			return partyNameList;
		} catch (Exception e) {
			LOGGER.error("ERROR : BusinessCommonDAOImpl.getPartyNames", e);
			throw new CGSystemException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoadMovementVendorDO> getVendorsListByServiceTypeAndCity(
			String serviceByTypeCode, Integer cityId) throws CGSystemException {
		try {
			
			String[] params = { BusinessUniversalConstants.PARAM_VENDOR_TYPE_CODE,BusinessUniversalConstants.PARAM_CITY_ID};
			Object[] values = { serviceByTypeCode, cityId};
			
			List<LoadMovementVendorDO> vendorList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(BusinessUniversalConstants.QRY_GET_VENDORS_BY_SRVC_TYPE_AND_CITY,params,values);

			return vendorList;
		} catch (Exception e) {
			LOGGER.error("ERROR : BusinessCommonDAOImpl.getVendorsListByServiceTypeAndCity", e);
			throw new CGSystemException(e);
		}
	}

	@Override
	public List<LoadMovementVendorDO> getPartyNamesByCityId(String partyType,Integer loggdCityId)
			throws CGSystemException {
		try {
			/*String[] params = { ManifestUniversalConstants.PARAM_VENDOR_CODE,ManifestUniversalConstants.PARAM_REGION_ID};
			Object[] values = { partyType,regionId };*/
			
			String[] params = { ManifestUniversalConstants.PARAM_VENDOR_CODE,"loggdCityId"};
			Object[] values = { partyType, loggdCityId};
			
			List<LoadMovementVendorDO> partyNameList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getVendorPartyNameByLoggedInCityId",params,values);

			return partyNameList;
		} catch (Exception e) {
			LOGGER.error("ERROR : DeliveryUniversalDAOImpl.getPartyNames", e);
			throw new CGSystemException(e);
		}
	}

	@Override
	public List<CustomerDO> getCustomerForContractByShippedToCode(
			String shippedToCode) throws CGSystemException {
		String namedQry = BusinessUniversalConstants.QRY_GET_CUSTOMER_ID_BY_SHIPPED_TO_CODE;
		String[] params = { BusinessUniversalConstants.QRY_PARAM_SHIPPEDTOCODE };
		Object[] values = { shippedToCode.trim() };
		List<CustomerDO> custDos = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(namedQry, params, values);
		return custDos;
	}
	
}
