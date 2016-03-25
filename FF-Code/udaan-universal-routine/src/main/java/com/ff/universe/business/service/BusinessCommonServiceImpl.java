package com.ff.universe.business.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.business.CustomerTO;
import com.ff.business.CustomerTypeTO;
import com.ff.business.LoadMovementVendorTO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.business.LoadMovementVendorDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupDeliveryAddressTO;
import com.ff.universe.business.dao.BusinessCommonDAO;
import com.ff.universe.stockmanagement.util.StockUtility;

public class BusinessCommonServiceImpl implements BusinessCommonService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BusinessCommonServiceImpl.class);
	private BusinessCommonDAO businessCommonDAO;

	public void setBusinessCommonDAO(BusinessCommonDAO businessCommonDAO) {
		this.businessCommonDAO = businessCommonDAO;
	}

	@Override
	public CustomerTO getCustomer(Integer customerId)
			throws CGBusinessException, CGSystemException {
		CustomerTO customerTO = null;
		List<CustomerDO> customerDOList = businessCommonDAO
				.getCustomer(customerId);
		if (customerDOList != null && customerDOList.size() > 0) {
			customerTO = new CustomerTO();
			for (CustomerDO customerDO : customerDOList) {
				customerTO.setCustomerId(customerDO.getCustomerId());
				customerTO.setCustomerCode(customerDO.getCustomerCode());
				customerTO.setBusinessName(customerDO.getBusinessName());
				customerTO.setStatus(customerDO.getStatus());
				
				//Set customer type TO
				if(!StringUtil.isNull(customerDO.getCustomerType())){
					CustomerTypeTO customerTypeTO=StockUtility.populateCustomerTypeFromCustomer(customerDO);
					
					customerTO.setCustomerTypeTO(customerTypeTO);
				}
				
				//Set customer category
				if(!StringUtil.isNull(customerDO.getCustomerCategoryDO())){
					customerTO.setCustomerCategory(customerDO.getCustomerCategoryDO().getRateCustomerCategoryCode());
				}
			}
		}
		return customerTO;
	}

	@Override
	public List<CustomerTO> getAllCustomers() throws CGBusinessException,
			CGSystemException {
		// TODO Auto-generated method stub
		List<CustomerDO> customerDOList = businessCommonDAO.getAllCustomers();
		List<CustomerTO> customerTOList = (List<CustomerTO>) CGObjectConverter
				.createTOListFromDomainList(customerDOList, CustomerTO.class);
		return customerTOList;
	}

	@Override
	public List<CustomerTO> getAllBusinessAssociatesUnderRegion(CustomerTO baTO)
			throws CGSystemException {
		CustomerDO baDO = null;
		List<CustomerDO> baDOlist = null;
		List<CustomerTO> baTOList = null;

		baDO = baTO2DOConverter(baTO);
		baDOlist = businessCommonDAO.getAllBusinessAssociatesUnderRegion(baDO);
		baTOList = baDO2TOConverter(baDOlist);
		return baTOList;
	}


	private List<CustomerTO> baDO2TOConverter(List<CustomerDO> franchiseeList) {
		List<CustomerTO> franchiseeToList = null;
		if (!StringUtil.isEmptyList(franchiseeList)) {
			franchiseeToList = new ArrayList<>(franchiseeList.size());
			try {
				for (CustomerDO frDO : franchiseeList) {
					CustomerTO to = new CustomerTO();
					PropertyUtils.copyProperties(to, frDO);
					franchiseeToList.add(to);
				}
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				LOGGER.error("BusinessCommonServiceImpl ::baDO2TOConverter::Exception", e);
			}
		}
		return franchiseeToList;
	}

	private CustomerDO baTO2DOConverter(CustomerTO baTO) {
		CustomerDO baDO = null;
		if (baTO != null) {
			try {
				baDO = new CustomerDO();
				PropertyUtils.copyProperties(baDO, baTO);
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				// TODO Auto-generated catch block
				LOGGER.error("BusinessCommonServiceImpl ::baTO2DOConverter(CustomerDO)::Exception", e);
			}
			if (baTO.getOfficeMappedTO() != null) {
				OfficeDO officeMappedDo = new OfficeDO();
				try {
					PropertyUtils.copyProperties(officeMappedDo,
							baTO.getOfficeMappedTO());
				} catch (IllegalAccessException | InvocationTargetException
						| NoSuchMethodException e) {
					// TODO Auto-generated catch block
					LOGGER.error("BusinessCommonServiceImpl ::baTO2DOConverter (officeMappedDO)::Exception", e);
				}
				baDO.setOfficeMappedDO(officeMappedDo);
			}
		}
		return baDO;
	}

	@Override
	public List<CustomerTO> getAllCustomersUnderRegion(CustomerTO customerTo)
			throws CGBusinessException, CGSystemException {
		CustomerDO baDO = null;
		List<CustomerDO> baDOlist = null;
		List<CustomerTO> baTOList = null;

		baDO = customerTO2DOConverter(customerTo);
		baDOlist = businessCommonDAO.getAllCustomersUnderRegion(baDO);
		baTOList = customerDO2TOConverter(baDOlist);
		return baTOList;
	}

	private CustomerDO customerTO2DOConverter(CustomerTO baTO) {
		CustomerDO baDO = null;
		if (baTO != null) {
			try {
				baDO = new CustomerDO();
				PropertyUtils.copyProperties(baDO, baTO);

				if (!StringUtil.isNull(baTO.getCustomerTypeTO())) {
					CustomerTypeDO customerType = new CustomerTypeDO();
					PropertyUtils.copyProperties(customerType,
							baTO.getCustomerTypeTO());
					baDO.setCustomerType(customerType);
				}
				if (!StringUtil.isNull(baTO.getOfficeMappedTO())) {
					OfficeDO officeDO = new OfficeDO();
					PropertyUtils.copyProperties(officeDO,
							baTO.getOfficeMappedTO());
					baDO.setOfficeMappedDO(officeDO);
				}
				if (!StringUtil.isNull(baTO.getSalesOffice())) {
					OfficeDO salesOfficeDO = new OfficeDO();
					PropertyUtils.copyProperties(salesOfficeDO,
							baTO.getSalesOffice());
					baDO.setSalesOfficeDO(salesOfficeDO);
				}
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				// TODO Auto-generated catch block
				LOGGER.error("BusinessCommonServiceImpl ::customerTO2DOConverter ::Exception", e);
			}

		}
		return baDO;
	}

	private List<CustomerTO> customerDO2TOConverter(
			List<CustomerDO> customerDoList) {
		List<CustomerTO> customerTOList = null;
		if (!StringUtil.isEmptyList(customerDoList)) {
			customerTOList = new ArrayList<>(customerDoList.size());
			try {
				for (CustomerDO frDO : customerDoList) {
					CustomerTO to = new CustomerTO();
					PropertyUtils.copyProperties(to, frDO);
					if (frDO.getCustomerType() != null) {
						CustomerTypeTO customerTypeTO =StockUtility.populateCustomerTypeFromCustomer(frDO);
						to.setCustomerTypeTO(customerTypeTO);
					}
					customerTOList.add(to);
				}
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				// TODO Auto-generated catch block
				LOGGER.error("BusinessCommonServiceImpl ::customerDO2TOConverter ::Exception", e);
			}
		}
		return customerTOList;
	}

	public Integer isValiedBACode(CustomerTO businessAssociateTO)
			throws CGBusinessException, CGSystemException {
		return businessCommonDAO.getCustomerIdByCode(businessAssociateTO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoadMovementVendorTO> getPartyNamesForCC()
			throws CGSystemException {

		List<LoadMovementVendorTO> ccTOList = null;
		try {
			List<LoadMovementVendorDO> ccDOList = businessCommonDAO
					.getPartyNamesForCC();
			if (ccDOList != null && ccDOList.size() > 0) {
				ccTOList = (List<LoadMovementVendorTO>) CGObjectConverter
						.createTOListFromDomainList(ccDOList,
								LoadMovementVendorTO.class);
			}
		} catch (Exception ex) {
			LOGGER.error("BusinessCommonServiceImpl ::getPartyNamesForCC ::Exception", ex);
		}
		return ccTOList;

	}

	@Override
	public CustomerTO getCustomerByIdOrCode(Integer customerId,
			String customerCode) throws CGSystemException, CGBusinessException {
		CustomerTO customerTO = null;
		CustomerDO customerDO = null;
		customerDO = businessCommonDAO.getCustomerByIdOrCode(customerId,
				customerCode);
		if (!StringUtil.isNull(customerDO)) {
			customerTO = new CustomerTO();
			customerTO = (CustomerTO) CGObjectConverter.createToFromDomain(
					customerDO, customerTO);
			if (!StringUtil.isNull(customerDO.getAddressDO())) {
				PickupDeliveryAddressTO address = new PickupDeliveryAddressTO();
				address = (PickupDeliveryAddressTO) CGObjectConverter
						.createToFromDomain(customerDO.getAddressDO(), address);
				customerTO.setAddress(address);
			}
			// Setting customer type
			if (!StringUtil.isNull(customerDO.getCustomerType())) {
				CustomerTypeTO custType =StockUtility.populateCustomerTypeFromCustomer(customerDO);
				customerTO.setCustomerTypeTO(custType);
			}
		}
		return customerTO;
	}

	@Override
	public CustomerTO getBusinessAssociateByIdOrCode(Integer baId, String baCode)
			throws CGSystemException, CGBusinessException {
		CustomerTO businessAssociateTO = null;
		CustomerDO businessAssociateDO = null;
		businessAssociateDO = businessCommonDAO.getBusinessAssociateByIdOrCode(
				baId, baCode);
		if (!StringUtil.isNull(businessAssociateDO)) {
			businessAssociateTO = new CustomerTO();
			businessAssociateTO = (CustomerTO) CGObjectConverter
					.createToFromDomain(businessAssociateDO,
							businessAssociateTO);
		}
		return businessAssociateTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoadMovementVendorTO> getVendorsList(
			LoadMovementVendorTO loadMovementVendorTO)
			throws CGBusinessException, CGSystemException {

		List<LoadMovementVendorTO> loadMovementVendorTOList = null;
		try {
			List<LoadMovementVendorDO> loadMovementVendorDOList = businessCommonDAO
					.getVendorsList(loadMovementVendorTO);
			if (!CGCollectionUtils.isEmpty(loadMovementVendorDOList)) {
				loadMovementVendorTOList = new ArrayList<LoadMovementVendorTO>();
				for (LoadMovementVendorDO lmvDO : loadMovementVendorDOList) {
					LoadMovementVendorTO lmvTO = new LoadMovementVendorTO();

					lmvTO = (LoadMovementVendorTO) CGObjectConverter
							.createToFromDomain(lmvDO, lmvTO);
					lmvTO.setVendorType(lmvDO.getVendorTypeDO()
							.getVendorTypeId().toString());
					if (!StringUtil.isNull(lmvDO.getOfficeDO())) {
						OfficeTO ofcTO = new OfficeTO();
						ofcTO = (OfficeTO) CGObjectConverter
								.createToFromDomain(lmvDO.getOfficeDO(), ofcTO);
						lmvTO.setOfficeTO(ofcTO);
					}
					loadMovementVendorTOList.add(lmvTO);
				}
			}
		} catch (CGBusinessException ex) {
			LOGGER.error("BusinessCommonServiceImpl ::getVendorsList ::Exception(CGBusinessException)", ex);
			throw ex;
		} catch (CGSystemException ex) {
			LOGGER.error("BusinessCommonServiceImpl ::getVendorsList ::Exception(CGSystemException)", ex);
			throw ex;
		} catch (Exception ex) {
			LOGGER.error("BusinessCommonServiceImpl ::getVendorsList ::Exception", ex);
			throw ex;
		}

		return loadMovementVendorTOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerTO> getDtlsForTPBA(int baID)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		List<CustomerTO> baTO = null;
		List<CustomerDO> baDO = null;
		try {
			baTO = new ArrayList<>();
			baDO = businessCommonDAO.getDtlsForTPBA(baID);

			if (baDO != null && baDO.size() > 0) {
				baTO = (List<CustomerTO>) CGObjectConverter
						.createTOListFromDomainList(baDO, CustomerTO.class);
			}
		} catch (Exception ex) {
			LOGGER.error("BusinessCommonServiceImpl ::getDtlsForTPBA ::Exception", ex);
		}
		return baTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerTO> getDtlsForTPFR(int frID) {
		// TODO Auto-generated method stub
		List<CustomerTO> frTO = null;
		List<CustomerDO> frDO = null;
		try {
			frTO = new ArrayList<>();
			frDO = businessCommonDAO.getDtlsForTPFR(frID);

			if (frDO != null && frDO.size() > 0) {
				frTO = (List<CustomerTO>) CGObjectConverter
						.createTOListFromDomainList(frDO, CustomerTO.class);
			}
		} catch (Exception ex) {
			LOGGER.error("BusinessCommonServiceImpl ::getDtlsForTPFR ::Exception", ex);
		}
		return frTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoadMovementVendorTO> getDtlsForTPCC(int ccID) {
		// TODO Auto-generated method stub
		List<LoadMovementVendorTO> ccTO = null;
		List<LoadMovementVendorDO> ccDO = null;
		try {
			ccTO = new ArrayList<>();
			ccDO = businessCommonDAO.getDtlsForTPCC(ccID);

			if (ccDO != null && ccDO.size() > 0) {
				ccTO = (List<LoadMovementVendorTO>) CGObjectConverter
						.createTOListFromDomainList(ccDO,
								LoadMovementVendorTO.class);
			}
		} catch (Exception ex) {
			LOGGER.error("BusinessCommonServiceImpl ::getDtlsForTPCC ::Exception", ex);
		}
		return ccTO;
	}

	@Override
	public ConsignorConsigneeTO getConsigneeConsignorDtls(String cnNumber)
			throws CGSystemException {
		ConsignorConsigneeTO consignorConsigneeTO = null;
		try {
			ConsigneeConsignorDO consigneeConsignorDO = businessCommonDAO
					.getConsigneeConsignorDtls(cnNumber);
			if (!StringUtil.isNull(consigneeConsignorDO)) {
				consignorConsigneeTO = new ConsignorConsigneeTO();
				consignorConsigneeTO = (ConsignorConsigneeTO) CGObjectConverter
						.createToFromDomain(consigneeConsignorDO,
								consignorConsigneeTO);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR :: BusinessCommonServiceImpl :: getConsigneeConsignorDtls:::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}

		return consignorConsigneeTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.business.service.BusinessCommonService#
	 * getConsigneeConsignorDtls(java.lang.String, java.lang.String)
	 */
	@Override
	public ConsignorConsigneeTO getConsigneeConsignorDtls(String cnNumber,
			String partyType) throws CGSystemException {
		ConsignorConsigneeTO consignorConsigneeTO = null;
		try {
			ConsigneeConsignorDO consigneeConsignorDO = businessCommonDAO
					.getConsigneeConsignorDtls(cnNumber, partyType);
			if (!StringUtil.isNull(consigneeConsignorDO)) {
				consignorConsigneeTO = new ConsignorConsigneeTO();
				consignorConsigneeTO = (ConsignorConsigneeTO) CGObjectConverter
						.createToFromDomain(consigneeConsignorDO,
								consignorConsigneeTO);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR :: BusinessCommonServiceImpl :: getConsigneeConsignorDtls:::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}

		return consignorConsigneeTO;
	}

	@Override
	public List<CustomerTO> getCustomersByOfficeId(Integer officeId)
			throws CGSystemException {
		List<CustomerDO> customerDOs = businessCommonDAO
				.getCustomersByOfficeId(officeId);
		List<CustomerTO> customerTOs = customerDO2TOConverter(customerDOs);
		return customerTOs;
	}

	@Override
	public List<LoadMovementVendorTO> getPartyNames(String partyType,
			Integer officeId) throws CGSystemException {
		List<LoadMovementVendorTO> partyTOList = null;
		try {
			List<LoadMovementVendorDO> partyDOList = businessCommonDAO
					.getPartyNames(partyType, officeId);
			if (partyDOList != null && partyDOList.size() > 0) {

				partyTOList = (List<LoadMovementVendorTO>) CGObjectConverter
						.createTOListFromDomainList(partyDOList,
								LoadMovementVendorTO.class);
			}
		} catch (Exception ex) {
			LOGGER.error("BusinessCommonServiceImpl ::getPartyNames ::Exception", ex);
			throw new CGSystemException(ex);
		}
		return partyTOList;
	}

	@Override
	public List<LoadMovementVendorTO> getVendorsListByServiceTypeAndCity(
			String serviceByTypeCode, Integer cityId) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BusinessCommonServiceImpl ::getVendorsListByServiceTypeAndRegion ::START");
		List<LoadMovementVendorTO> loadMovementVendorTOList = null;
		try {
			List<LoadMovementVendorDO> loadMovementVendorDOList = businessCommonDAO
					.getVendorsListByServiceTypeAndCity(serviceByTypeCode,cityId);
			if (!CGCollectionUtils.isEmpty(loadMovementVendorDOList)) {
				loadMovementVendorTOList = new ArrayList<LoadMovementVendorTO>();
				for (LoadMovementVendorDO lmvDO : loadMovementVendorDOList) {
					LoadMovementVendorTO lmvTO = new LoadMovementVendorTO();

					lmvTO = (LoadMovementVendorTO) CGObjectConverter
							.createToFromDomain(lmvDO, lmvTO);
					lmvTO.setVendorType(lmvDO.getVendorTypeDO()
							.getVendorTypeId().toString());
					if (!StringUtil.isNull(lmvDO.getOfficeDO())) {
						OfficeTO ofcTO = new OfficeTO();
						ofcTO = (OfficeTO) CGObjectConverter
								.createToFromDomain(lmvDO.getOfficeDO(), ofcTO);
						lmvTO.setOfficeTO(ofcTO);
					}
					loadMovementVendorTOList.add(lmvTO);
				}
			}
		} catch (CGBusinessException ex) {
			LOGGER.error("BusinessCommonServiceImpl ::getVendorsList ::Exception(CGBusinessException)", ex);
			throw ex;
		} catch (CGSystemException ex) {
			LOGGER.error("BusinessCommonServiceImpl ::getVendorsList ::Exception(CGSystemException)", ex);
			throw ex;
		} catch (Exception ex) {
			LOGGER.error("BusinessCommonServiceImpl ::getVendorsList ::Exception", ex);
			throw ex;
		}
		LOGGER.trace("BusinessCommonServiceImpl ::getVendorsListByServiceTypeAndRegion ::END");
		return loadMovementVendorTOList;
	}
	
	/**
	 * Gets the franchise vendor dtls for drs.
	 *
	 * @param partyType the party type
	 * @param loggdCityId the loggd city id
	 * @return the franchise vendor dtls for drs
	 * @throws CGSystemException the cG system exception
	 */
	public List<LoadMovementVendorTO> getVendorDtlsForDrsByLoggdCity(String partyType,Integer loggdCityId) throws CGSystemException{
		
		List<LoadMovementVendorTO> partyTOList = null;
		try {
			List<LoadMovementVendorDO> partyDOList = businessCommonDAO
					.getPartyNamesByCityId(partyType, loggdCityId);
			if (partyDOList != null && partyDOList.size() > 0) {

				partyTOList = (List<LoadMovementVendorTO>) CGObjectConverter
						.createTOListFromDomainList(partyDOList,
								LoadMovementVendorTO.class);
			}
		} catch (Exception ex) {
			LOGGER.error("BusinessComonServiceImpl ::getFranchiseVendorDtlsForDrs ::Exception", ex);
			throw new CGSystemException(ex);
		}
		return partyTOList;
		
	}

	@Override
	public List<CustomerTO> getCustomerForContractByShippedToCode(
			String shippedToCode) throws CGBusinessException, CGSystemException {
		LOGGER.trace("BusinessComonServiceImpl::getCustomerForContractByShippedToCode ::START FOR shippedToCode :["+shippedToCode+"]");
		List <CustomerTO> customerTOs=null;
		if(!StringUtil.isStringEmpty(shippedToCode)){
			List<CustomerDO> customerDOs = businessCommonDAO
					.getCustomerForContractByShippedToCode(shippedToCode);
			if (!CGCollectionUtils.isEmpty(customerDOs)) {

				customerTOs = (List<CustomerTO>) CGObjectConverter
						.createTOListFromDomainList(customerDOs,
								CustomerTO.class);
			}
			

		}
		LOGGER.trace("BusinessComonServiceImpl::getCustomerForContractByShippedToCode ::ENDS FOR shippedToCode :["+shippedToCode+"] ");
		return customerTOs;
	}

}
