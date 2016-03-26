package com.ff.rate.configuration.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.email.EmailSenderUtil;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingPreferenceDetailsTO;
import com.ff.booking.BookingTypeConfigTO;
import com.ff.business.CustomerTypeTO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.ratemanagement.masters.CodChargeDO;
import com.ff.domain.ratemanagement.masters.RateContractDO;
import com.ff.domain.ratemanagement.masters.RateSectorsDO;
import com.ff.domain.ratemanagement.masters.RateTaxComponentDO;
import com.ff.domain.ratemanagement.masters.RateWeightSlabsDO;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;
import com.ff.domain.ratemanagement.operations.ratebenchmark.RateBenchMarkHeaderDO;
import com.ff.geography.CityTO;
import com.ff.geography.CountryTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.geography.StateTO;
import com.ff.geography.ZoneTO;
import com.ff.organization.DepartmentTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.common.constants.RateErrorConstants;
import com.ff.rate.configuration.common.dao.RateCommonDAO;
import com.ff.rate.configuration.ratebenchmark.service.RateBenchMarkService;
import com.ff.rate.configuration.ratequotation.constants.RateQuotationConstants;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.to.rate.RateComponentTO;
import com.ff.to.ratemanagement.masters.RateContractTO;
import com.ff.to.ratemanagement.masters.RateCustomerCategoryTO;
import com.ff.to.ratemanagement.masters.RateCustomerProductCatMapTO;
import com.ff.to.ratemanagement.masters.RateIndustryCategoryTO;
import com.ff.to.ratemanagement.masters.RateMinChargeableWeightTO;
import com.ff.to.ratemanagement.masters.RateProductCategoryTO;
import com.ff.to.ratemanagement.masters.RateSectorsTO;
import com.ff.to.ratemanagement.masters.RateVobSlabsTO;
import com.ff.to.ratemanagement.masters.RateWeightSlabsTO;
import com.ff.to.ratemanagement.masters.SectorTO;
import com.ff.to.ratemanagement.masters.WeightSlabTO;
import com.ff.to.ratemanagement.operations.ratebenchmark.RateBenchMarkHeaderTO;
import com.ff.to.ratemanagement.operations.ratequotation.CodChargeTO;
import com.ff.to.ratemanagement.operations.ratequotation.CustomerGroupTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateIndustryTypeTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateTaxComponentTO;
import com.ff.to.stockmanagement.masters.ItemTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserTO;
import com.ff.universe.ratemanagement.service.RateUniversalService;

public class RateCommonServiceImpl implements RateCommonService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(RateCommonServiceImpl.class);

	private RateCommonDAO rateCommonDAO;
	private RateUniversalService rateUniversalService;
	private RateBenchMarkService rateBenchMarkService;
	private EmailSenderUtil emailSenderUtil;

	/**
	 * @return the emailSenderUtil
	 */
	public EmailSenderUtil getEmailSenderUtil() {
		return emailSenderUtil;
	}

	/**
	 * @param emailSenderUtil
	 *            the emailSenderUtil to set
	 */
	public void setEmailSenderUtil(EmailSenderUtil emailSenderUtil) {
		this.emailSenderUtil = emailSenderUtil;
	}

	/**
	 * @return the rateBenchMarkService
	 */
	public RateBenchMarkService getRateBenchMarkService() {
		return rateBenchMarkService;
	}

	/**
	 * @param rateBenchMarkService
	 *            the rateBenchMarkService to set
	 */
	public void setRateBenchMarkService(
			RateBenchMarkService rateBenchMarkService) {
		this.rateBenchMarkService = rateBenchMarkService;
	}

	public RateUniversalService getRateUniversalService() {
		return rateUniversalService;
	}

	public void setRateUniversalService(
			RateUniversalService rateUniversalService) {
		this.rateUniversalService = rateUniversalService;
	}

	public RateCommonDAO getRateCommonDAO() {
		return rateCommonDAO;
	}

	public void setRateCommonDAO(RateCommonDAO rateCommonDAO) {
		this.rateCommonDAO = rateCommonDAO;
	}

	@Override
	public List<RateIndustryCategoryTO> getRateIndustryCategoryList(
			String applicable) throws CGSystemException, CGBusinessException {

		List<RateIndustryCategoryTO> rateIndCatTOList = null;
		rateIndCatTOList = rateUniversalService
				.getRateIndustryCategoryList(applicable);
		return rateIndCatTOList;
	}

	@Override
	public List<RateProductCategoryTO> getRateProductCategoryList(String type)
			throws CGSystemException, CGBusinessException {
		List<RateProductCategoryTO> rateProdCatTOList = null;
		rateProdCatTOList = rateUniversalService
				.getRateProductCategoryList(type);
		return rateProdCatTOList;
	}

	@Override
	public List<RateVobSlabsTO> getRateVobSlabsList(String type,
			String custCatCode) throws CGSystemException, CGBusinessException {
		List<RateVobSlabsTO> rateVobSlabsTOList = null;
		rateVobSlabsTOList = rateUniversalService.getRateVobSlabsList(type,
				custCatCode);
		return rateVobSlabsTOList;
	}

	@Override
	public List<RateWeightSlabsTO> getRateWeightSlabsList(String type,
			String custCatCode) throws CGSystemException, CGBusinessException {
		List<RateWeightSlabsTO> rateWtSlabsTOList = null;
		rateWtSlabsTOList = rateUniversalService.getRateWeightSlabsList(type,
				custCatCode);
		return rateWtSlabsTOList;
	}

	@Override
	public List<RateSectorsTO> getRateSectorsList(String type,
			String custCatCode) throws CGSystemException, CGBusinessException {
		List<RateSectorsTO> rateSectorsTOList = null;
		rateSectorsTOList = rateUniversalService.getRateSectorsList(type,
				custCatCode);
		return rateSectorsTOList;
	}

	@Override
	public EmployeeTO getEmployeeDetails(String empCode)
			throws CGSystemException, CGBusinessException {

		EmployeeTO empTO = null;
		if (!StringUtil.isNull(empCode)) {
			empTO = new EmployeeTO();
			empTO.setEmpCode(empCode);
			List<EmployeeTO> empTOList = rateUniversalService
					.getEmployeeDetails(empTO);
			if (!CGCollectionUtils.isEmpty(empTOList))
				empTO = empTOList.get(0);
			else
				empTO = null;
		}
		return empTO;
	}

	@Override
	public List<RateCustomerProductCatMapTO> getRateCustomerProductCatMapList()
			throws CGSystemException, CGBusinessException {
		List<RateCustomerProductCatMapTO> rateCustProdCatTOList = null;
		rateCustProdCatTOList = rateUniversalService
				.getRateCustomerProductCatMapList();
		return rateCustProdCatTOList;
	}

	@Override
	public List<RateMinChargeableWeightTO> getRateMinChrgWtList(String type,
			String custCatCode) throws CGSystemException, CGBusinessException {
		List<RateMinChargeableWeightTO> rateMinChrgWtTOList = null;
		rateMinChrgWtTOList = rateUniversalService.getRateMinChrgWtList(type,
				custCatCode);
		return rateMinChrgWtTOList;
	}

	@Override
	public List<RateCustomerCategoryTO> getRateCustomerCategoryList()
			throws CGSystemException, CGBusinessException {
		List<RateCustomerCategoryTO> rateCustCatTOList = null;
		rateCustCatTOList = rateUniversalService.getRateCustomerCategoryList();
		return rateCustCatTOList;
	}

	@Override
	public CityTO getCityByPincode(String pincode) throws CGBusinessException,
			CGSystemException {

		return rateUniversalService.getCityByPincode(pincode);
	}

	@Override
	public List<RateSectorsTO> getRateConfigSectorsList(String type,
			String custCatCode) throws CGSystemException, CGBusinessException {
		List<RateSectorsTO> rateSectorsTOList = null;
		rateSectorsTOList = rateUniversalService.getRateConfigSectorsList(type,
				custCatCode);
		return rateSectorsTOList;
	}

	@Override
	public ZoneTO getZoneByZoneId(Integer zoneId) throws CGBusinessException,
			CGSystemException {

		List<ZoneTO> zoneTOList = null;
		ZoneTO zoneTO = null;
		zoneTOList = rateUniversalService.getZoneByZoneId(zoneId);
		if (!CGCollectionUtils.isEmpty(zoneTOList)) {
			zoneTO = zoneTOList.get(0);
		}
		return zoneTO;
	}

	@Override
	public OfficeTO getOfficeDetails(Integer loggedInRHO)
			throws CGSystemException, CGBusinessException {
		return rateUniversalService.getOfficeDetails(loggedInRHO);
	}

	@Override
	public List<CityTO> getCitiesByCity(CityTO rhoCityTO)
			throws CGSystemException, CGBusinessException {
		return rateUniversalService.getCitiesByCity(rhoCityTO);
	}

	@Override
	public CityTO getCity(CityTO cityTO) throws CGSystemException,
			CGBusinessException {
		return rateUniversalService.getCity(cityTO);
	}

	@Override
	public List<StockStandardTypeTO> getStandardType(String string)
			throws CGSystemException, CGBusinessException {
		return rateUniversalService.getStandardType(string);
	}

	@Override
	public List<InsuredByTO> getRiskSurchargeInsuredBy()
			throws CGSystemException, CGBusinessException {
		return rateUniversalService.getInsuarnceBy();
	}

	@Override
	public SequenceGeneratorConfigTO getGeneratedSequence(
			SequenceGeneratorConfigTO sequenceGeneratorConfigTO)
			throws CGSystemException, CGBusinessException {
		return rateUniversalService
				.getGeneratedSequence(sequenceGeneratorConfigTO);
	}

	@Override
	public List<OfficeTO> getAllOfficesByCity(Integer loginCityId)
			throws CGSystemException, CGBusinessException {
		return rateUniversalService.getAllOfficesByCity(loginCityId);
	}

	@Override
	public List<EmployeeTO> getEmployeesOfOffice(OfficeTO officeTO)
			throws CGSystemException, CGBusinessException {
		return rateUniversalService.getEmployeesOfOffice(officeTO);
	}

	@Override
	public List<OfficeTO> getRHOOfficesByUserId(Integer userId)
			throws CGBusinessException, CGSystemException {

		return rateUniversalService.getRHOOfficesByUserId(userId);
	}

	@Override
	public List<CityTO> getCityListOfReportedOffices(Integer rhoOfficeId)
			throws CGBusinessException, CGSystemException {
		return rateUniversalService.getCityListOfReportedOffices(rhoOfficeId);
	}

	@Override
	public List<CityTO> getCityListOfAssignedOffices(Integer userId)
			throws CGBusinessException, CGSystemException {
		return rateUniversalService.getCityListOfAssignedOffices(userId);
	}

	@Override
	public boolean blockOrUnblockCustomer(Integer customerId, String status)
			throws CGSystemException, CGBusinessException {
		return rateCommonDAO.blockOrUnblockCustomer(customerId, status);
	}

	@Override
	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException {
		List<RegionTO> regionTO = rateUniversalService.getAllRegions();
		return regionTO;
	}

	@Override
	public List<CityTO> getCitiesByRegion(RegionTO regionTO)
			throws CGBusinessException, CGSystemException {
		List<CityTO> cityTOs = rateUniversalService.getCitiesByRegion(regionTO);
		return cityTOs;
	}

	@Override
	public List<OfficeTO> getPickupBranchsByPincode(String pincode)
			throws CGBusinessException, CGSystemException {
		return rateUniversalService.getPickupBranchsByPincode(pincode);
	}

	@Override
	public PincodeTO validatePincode(PincodeTO pincode)
			throws CGBusinessException, CGSystemException {
		return rateUniversalService.validatePincode(pincode);
	}

	@Override
	public boolean updateCustPanNo(String panNo, Integer customerId)
			throws CGBusinessException, CGSystemException {
		return rateCommonDAO.updateCustPanNo(panNo, customerId);
	}

	@Override
	public boolean updateCustTanNo(String tanNo, Integer customerId)
			throws CGBusinessException, CGSystemException {
		return rateCommonDAO.updateCustTanNo(tanNo, customerId);
	}

	@Override
	public boolean updateContractStatus(RateContractTO rateContractTO)
			throws CGBusinessException, CGSystemException {
		return rateCommonDAO.updateContractStatus(rateContractTO);
	}

	@Override
	public List<RateSectorsTO> getRateSectorList(String rateProductCategory,
			String rateCustomerCategory) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("RateCommonServiceImpl::getRateSectorList()::START");
		List<RateSectorsTO> rateSectorsTOs = null;
		List<RateSectorsDO> rateSectorsDOs = rateCommonDAO.getRateSectorList(
				rateProductCategory, rateCustomerCategory);
		if (!CGCollectionUtils.isEmpty(rateSectorsDOs)) {
			rateSectorsTOs = new ArrayList<RateSectorsTO>();
			converterRateSectorsDOList2RateSectorsTOList(rateSectorsDOs,
					rateSectorsTOs);
		}
		LOGGER.debug("RateCommonServiceImpl::getRateSectorList()::END");
		return rateSectorsTOs;

	}

	/**
	 * To convert RateSectorsDO list to RateSectorsTO list
	 * 
	 * @param rateSectorsDOList
	 * @param rateSectorsTOList
	 * @throws CGBusinessException
	 */
	private void converterRateSectorsDOList2RateSectorsTOList(
			List<RateSectorsDO> rateSectorsDOList,
			List<RateSectorsTO> rateSectorsTOList) throws CGBusinessException {
		RateSectorsTO rateSectorsTO = null;
		SectorTO sectorTO = null;
		RateCustomerProductCatMapTO rcpmTO = null;
		RateCustomerCategoryTO rccTO = null;
		RateProductCategoryTO rpcTO = null;
		if (!CGCollectionUtils.isEmpty(rateSectorsDOList)
				&& rateSectorsDOList.size() > 0) {
			for (RateSectorsDO rateSectorsDO : rateSectorsDOList) {
				if (!StringUtil.isNull(rateSectorsDO)) {
					rateSectorsTO = new RateSectorsTO();
					rateSectorsTO = (RateSectorsTO) CGObjectConverter
							.createToFromDomain(rateSectorsDO, rateSectorsTO);

					if (!StringUtil.isNull(rateSectorsDO.getSectorDO())) {
						sectorTO = new SectorTO();
						sectorTO = (SectorTO) CGObjectConverter
								.createToFromDomain(
										rateSectorsDO.getSectorDO(), sectorTO);
						rateSectorsTO.setSectorTO(sectorTO);
					}
					if (!StringUtil.isNull(rateSectorsDO
							.getRateCustomerProductCatMapDO())) {
						rcpmTO = new RateCustomerProductCatMapTO();
						rcpmTO = (RateCustomerProductCatMapTO) CGObjectConverter
								.createToFromDomain(rateSectorsDO
										.getRateCustomerProductCatMapDO(),
										rcpmTO);
						if (!StringUtil.isNull(rateSectorsDO
								.getRateCustomerProductCatMapDO()
								.getRateCustomerCategoryDO())) {
							rccTO = new RateCustomerCategoryTO();
							rccTO = (RateCustomerCategoryTO) CGObjectConverter
									.createToFromDomain(rateSectorsDO
											.getRateCustomerProductCatMapDO()
											.getRateCustomerCategoryDO(), rccTO);
							rcpmTO.setRateCustomerCategoryTO(rccTO);
						}
						if (!StringUtil.isNull(rateSectorsDO
								.getRateCustomerProductCatMapDO()
								.getRateProductCategoryDO())) {
							rpcTO = new RateProductCategoryTO();
							rpcTO = (RateProductCategoryTO) CGObjectConverter
									.createToFromDomain(rateSectorsDO
											.getRateCustomerProductCatMapDO()
											.getRateProductCategoryDO(), rpcTO);
							rcpmTO.setRateProductCategoryTO(rpcTO);
						}
						rateSectorsTO.setRateCustomerProductCatMapTO(rcpmTO);
					}
					rateSectorsTOList.add(rateSectorsTO);
				}

			}
		}
	}

	@Override
	public List<RateWeightSlabsTO> getRateWeightSlabList(
			String rateProductCategory, String rateCustomerCategory)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("RateCommonServiceImpl::getRateWeightSlabList()::START");
		List<RateWeightSlabsTO> rateWtSlabsTOs = null;
		try {
			List<RateWeightSlabsDO> rateWtSlabsDOs = rateCommonDAO
					.getRateWeightSlabList(rateProductCategory,
							rateCustomerCategory);
			if (!CGCollectionUtils.isEmpty(rateWtSlabsDOs)) {
				rateWtSlabsTOs = new ArrayList<RateWeightSlabsTO>();
				converterRateWtSlabsDOList2RateWtSlabsTOList(rateWtSlabsDOs,
						rateWtSlabsTOs);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurs in RateCommonServiceImpl::getRateWeightSlabList()::"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("RateCommonServiceImpl::getRateWeightSlabList()::END");
		return rateWtSlabsTOs;
	}

	/**
	 * To convert RateWeightSlabsTO list to RateWeightSlabsDO list
	 * 
	 * @param rateWtSlabsDOList
	 * @param rateWtSlabsTOList
	 * @throws CGBusinessException
	 */
	private void converterRateWtSlabsDOList2RateWtSlabsTOList(
			List<RateWeightSlabsDO> rateWtSlabsDOList,
			List<RateWeightSlabsTO> rateWtSlabsTOList)
			throws CGBusinessException {
		RateWeightSlabsTO rateWtSlabsTO = null;
		WeightSlabTO wtSlabTO = null;
		RateCustomerProductCatMapTO rcpmTO = null;
		RateCustomerCategoryTO rccTO = null;
		RateProductCategoryTO rpcTO = null;
		if (!CGCollectionUtils.isEmpty(rateWtSlabsDOList)
				&& rateWtSlabsDOList.size() > 0) {
			for (RateWeightSlabsDO rateWtSlabsDO : rateWtSlabsDOList) {
				if (!StringUtil.isNull(rateWtSlabsDO)) {
					rateWtSlabsTO = new RateWeightSlabsTO();
					rateWtSlabsTO = (RateWeightSlabsTO) CGObjectConverter
							.createToFromDomain(rateWtSlabsDO, rateWtSlabsTO);
					if (!StringUtil.isNull(rateWtSlabsDO.getWeightSlabDO())) {
						wtSlabTO = new WeightSlabTO();
						wtSlabTO = (WeightSlabTO) CGObjectConverter
								.createToFromDomain(
										rateWtSlabsDO.getWeightSlabDO(),
										wtSlabTO);
						rateWtSlabsTO.setWeightSlabTO(wtSlabTO);
					}
					if (!StringUtil.isNull(rateWtSlabsDO
							.getRateCustomerProductCatMapDO())) {
						rcpmTO = new RateCustomerProductCatMapTO();
						rcpmTO = (RateCustomerProductCatMapTO) CGObjectConverter
								.createToFromDomain(rateWtSlabsDO
										.getRateCustomerProductCatMapDO(),
										rcpmTO);
						if (!StringUtil.isNull(rateWtSlabsDO
								.getRateCustomerProductCatMapDO()
								.getRateCustomerCategoryDO())) {
							rccTO = new RateCustomerCategoryTO();
							rccTO = (RateCustomerCategoryTO) CGObjectConverter
									.createToFromDomain(rateWtSlabsDO
											.getRateCustomerProductCatMapDO()
											.getRateCustomerCategoryDO(), rccTO);
							rcpmTO.setRateCustomerCategoryTO(rccTO);
						}
						if (!StringUtil.isNull(rateWtSlabsDO
								.getRateCustomerProductCatMapDO()
								.getRateProductCategoryDO())) {
							rpcTO = new RateProductCategoryTO();
							rpcTO = (RateProductCategoryTO) CGObjectConverter
									.createToFromDomain(rateWtSlabsDO
											.getRateCustomerProductCatMapDO()
											.getRateProductCategoryDO(), rpcTO);
							rcpmTO.setRateProductCategoryTO(rpcTO);
						}
						rateWtSlabsTO.setRateCustomerProductCatMapTO(rcpmTO);
					}
					rateWtSlabsTOList.add(rateWtSlabsTO);
				}
			}
		}
	}

	@Override
	public List<InsuredByTO> getInsuarnceBy() throws CGBusinessException,
			CGSystemException {
		return rateUniversalService.getInsuarnceBy();
	}

	public StateTO getStateByCode(String stateCode) throws CGBusinessException,
			CGSystemException {
		return rateUniversalService.getStateByCode(stateCode);
	}

	@Override
	public List<BookingPreferenceDetailsTO> getBookingPrefDetails()
			throws CGBusinessException, CGSystemException {
		return rateUniversalService.getBookingPrefDetails();
	}

	@Override
	public CountryTO getCountryByCode(String string)
			throws CGBusinessException, CGSystemException {
		return rateUniversalService.getCountryByCode(string);
	}

	@Override
	public List<RateTaxComponentTO> loadDefaultRateTaxComponentValue(
			Integer stateId) throws CGSystemException, CGSystemException {
		List<RateTaxComponentTO> taxComponentTOs;
		List<RateTaxComponentDO> rateTaxComponentDO = new ArrayList<RateTaxComponentDO>();
		try {
			List<RateTaxComponentDO> rateTaxComponentSEC = rateCommonDAO
					.getTaxComponents(stateId,
							DateUtil.getCurrentDateWithoutTime(),
							RateCommonConstants.TAX_GROUP_SEC);

			List<RateTaxComponentDO> rateTaxComponentSSU = rateCommonDAO
					.getTaxComponents(stateId,
							DateUtil.getCurrentDateWithoutTime(),
							RateCommonConstants.TAX_GROUP_SSU);
			if (!StringUtil.isEmptyColletion(rateTaxComponentSEC)) {
				int lenSEC = rateTaxComponentSEC.size();
				for (int i = 0; i < lenSEC; i++) {
					rateTaxComponentDO.add(rateTaxComponentSEC.get(i));
				}
			}
			if (!StringUtil.isEmptyColletion(rateTaxComponentSSU)) {
				int lenSSU = rateTaxComponentSSU.size();
				for (int j = 0; j < lenSSU; j++) {
					rateTaxComponentDO.add(rateTaxComponentSSU.get(j));
				}
			}

			taxComponentTOs = new ArrayList<RateTaxComponentTO>(
					rateTaxComponentDO.size());

			if (!CGCollectionUtils.isEmpty(rateTaxComponentDO)) {
				for (RateTaxComponentDO rateTaxComponent : rateTaxComponentDO) {
					RateTaxComponentTO rateTaxComponentTO = new RateTaxComponentTO();
					RateComponentTO componentTO = new RateComponentTO();
					componentTO.setRateComponentCode(rateTaxComponent
							.getRateComponentCode());

					rateTaxComponentTO.setRateComponentId(componentTO);
					rateTaxComponentTO.setTaxPercentile(rateTaxComponent
							.getTaxPercentile());
					taxComponentTOs.add(rateTaxComponentTO);
				}
			} else {
				throw new CGBusinessException(
						RateErrorConstants.TAXES_NOT_CONFIGURED);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in RateCommonServiceImpl :: loadDefaultRateTaxComponentValue()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return taxComponentTOs;
	}

	@Override
	public OfficeTO getOfficeByempId(Integer empId) throws CGBusinessException,
			CGSystemException {
		return rateUniversalService.getOfficeByempId(empId);
	}

	@Override
	public Map<Integer, String> getItemTypeAsMap() throws CGSystemException,
			CGBusinessException {
		return rateUniversalService.getItemTypeAsMap();
	}

	@Override
	public Map<Integer, String> getItemByTypeAsMap(Integer itemTypeId)
			throws CGSystemException, CGBusinessException {
		return rateUniversalService.getItemByTypeAsMap(itemTypeId);
	}

	@Override
	public ItemTO getItemByItemTypeAndItemId(Integer itemTypeId, Integer itemId)
			throws CGSystemException, CGBusinessException {
		return rateUniversalService.getItemByItemTypeAndItemId(itemTypeId,
				itemId);
	}

	@Override
	public List<CityTO> getCityListOfReportedOfficesOfRHO(List<OfficeTO> ofcList)
			throws CGBusinessException, CGSystemException {
		return rateUniversalService.getCityListOfReportedOfficesOfRHO(ofcList);
	}

	@Override
	public List<RateTaxComponentTO> loadDefaultRateTaxComponentValueForConfiguration(
			Integer stateId) throws CGSystemException, CGBusinessException {
		List<RateTaxComponentTO> taxComponentTOs;
		List<RateTaxComponentDO> rateTaxComponentDO = null;
		try {
			rateTaxComponentDO = rateCommonDAO
					.getTaxComponentsForRateConfiguration(stateId,
							DateUtil.getCurrentDateWithoutTime());
			taxComponentTOs = new ArrayList<RateTaxComponentTO>(
					rateTaxComponentDO.size());

			if (!CGCollectionUtils.isEmpty(rateTaxComponentDO)) {
				for (RateTaxComponentDO rateTaxComponent : rateTaxComponentDO) {
					RateTaxComponentTO rateTaxComponentTO = new RateTaxComponentTO();
					RateComponentTO componentTO = new RateComponentTO();
					componentTO.setRateComponentCode(rateTaxComponent
							.getRateComponentCode());
					rateTaxComponentTO.setRateComponentId(componentTO);
					rateTaxComponentTO.setTaxPercentile(rateTaxComponent
							.getTaxPercentile());
					taxComponentTOs.add(rateTaxComponentTO);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in RateCommonServiceImpl :: loadDefaultRateTaxComponentValueForConfiguration()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return taxComponentTOs;
	}

	@Override
	public Integer getCustomerTypeId(String quotationType, String indCode,
			String contractType, String businessType) throws CGSystemException,
			CGBusinessException {
		Integer customerType = null;
		CustomerTypeTO ctTO = null;

		if (!StringUtil.isNull(contractType) && contractType.equals("R")) {
			ctTO = rateUniversalService.getCustomerTypeId("RL");
		} else {
			if (quotationType.equals(RateQuotationConstants.NORMAL)) {
				if (indCode.equals("GNRL")) {
					ctTO = rateUniversalService.getCustomerTypeId("CR");
				} else if (indCode.equals("BFSI") && businessType.equals("CC")) {
					ctTO = rateUniversalService.getCustomerTypeId("CC");
				} else if (indCode.equals("BFSI") && businessType.equals("LC")) {
					ctTO = rateUniversalService.getCustomerTypeId("LC");
				} else if (indCode.equals("BFSI") && businessType.equals("NA")) {
					ctTO = rateUniversalService.getCustomerTypeId("CR");
				} else if (indCode.equals("FR")) {
					ctTO = rateUniversalService.getCustomerTypeId("FR");
				}
			} else if (quotationType.equals(RateQuotationConstants.ECOMMERCE)) {
				ctTO = rateUniversalService.getCustomerTypeId("CD");
			}
		}
		if (!StringUtil.isNull(ctTO)) {
			customerType = ctTO.getCustomerTypeId();
		}
		return customerType;
	}

	@Override
	public BookingTypeConfigTO getBookingTypeConfigVWDeno()
			throws CGSystemException, CGBusinessException {
		return rateUniversalService.getBookingTypeConfigVWDeno();
	}

	@Override
	public SectorTO getSectorByRegionId(Integer regionId)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("RateCommonServiceImpl::getSectorByRegionId()::START");
		SectorDO sectorDO = null;
		SectorTO sectorTO = new SectorTO();
		try {
			sectorDO = rateCommonDAO.getSectorByRegionId(regionId);
			PropertyUtils.copyProperties(sectorTO, sectorDO);
		} catch (Exception e) {
			LOGGER.error("Exception occurs in RateCommonServiceImpl::getSectorByRegionId()::"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("RateCommonServiceImpl::getSectorByRegionId()::END");
		return sectorTO;
	}

	@Override
	public List<OfficeTO> getAllRegionalOffices() throws CGSystemException,
			CGBusinessException {
		return rateUniversalService.getAllRegionalOffices();
	}

	@Override
	public OfficeTO getOfficeByUserId(Integer userId) throws CGSystemException,
			CGBusinessException {
		return rateUniversalService.getOfficeByUserId(userId);
	}

	public void sendEmailForBlockContractCustomer(RateContractTO rateContractTO)
			throws CGBusinessException, CGSystemException {

		LOGGER.debug("RateCommonServiceImpl::sendEmailForBlockContractCustomer()::START");
		RateContractDO rcDO = null;
		Integer rateContractId = null;
		String[] toMail = null;
		String[] ccMail = null;
		EmployeeDO salesEmpDO = null;
		EmployeeDO regEmpDO = null;
		EmployeeDO corpEmpDO = null;
		String mailId = null;
		String regMailId = null;
		String corpMailId = null;
		String templateName = null;
		rateContractId = rateContractTO.getRateContractId();
		if (!StringUtil.isNull(rateContractId)) {
			rcDO = rateCommonDAO.getRateContractDetails(rateContractId);
			if (!StringUtil.isNull(rcDO)
					&& !StringUtil.isNull(rcDO.getRateQuotationDO())
					&& !StringUtil.isNull(rcDO.getRateQuotationDO()
							.getCustomer())
					&& !StringUtil.isNull(rcDO.getRateQuotationDO()
							.getCustomer().getSalesPersonDO())) {
				salesEmpDO = rcDO.getRateQuotationDO().getCustomer()
						.getSalesPersonDO();
				mailId = salesEmpDO.getEmailId();
				toMail = new String[] { mailId };
				Integer indCatId = rcDO.getRateQuotationDO().getCustomer()
						.getIndustryCategoryDO().getRateIndustryCategoryId();
				Integer regionId = rcDO.getRateQuotationDO().getCustomer()
						.getSalesOfficeDO().getMappedRegionDO().getRegionId();
				if (!StringUtil.isNull(rcDO.getRateQuotationDO()
						.getApprovalRequired())) {
					if (rcDO.getRateQuotationDO().getApprovalRequired()
							.equals(RateCommonConstants.REGIONAL_CORP)) {

						regEmpDO = rateCommonDAO.getRegionalApprovalDetails(
								regionId, indCatId);

						if (!StringUtil.isNull(regEmpDO)) {
							regMailId = regEmpDO.getEmailId();
						}

						RateBenchMarkHeaderTO rbmhTO = new RateBenchMarkHeaderTO();
						rbmhTO.setRateIndustryCategoryId(indCatId);

						RateBenchMarkHeaderDO rbmhDO = null;

						rbmhDO = rateBenchMarkService
								.getRateBenchMarkHeaderDetails(rbmhTO);

						if (!StringUtil.isNull(rbmhDO)) {
							corpEmpDO = rbmhDO.getApprover();
						}

						if (!StringUtil.isNull(corpEmpDO)) {
							corpMailId = corpEmpDO.getEmailId();
						}

						if (!StringUtil.isStringEmpty(regMailId)
								&& !StringUtil.isStringEmpty(corpMailId)) {
							ccMail = new String[] { regMailId, corpMailId };
						} else if (!StringUtil.isStringEmpty(regMailId)) {
							ccMail = new String[] { regMailId };
						} else if (!StringUtil.isStringEmpty(corpMailId)) {
							ccMail = new String[] { corpMailId };
						}

					} else if (rcDO.getRateQuotationDO().getApprovalRequired()
							.equals(RateCommonConstants.REGIONAL_OPERATOR)) {
						regEmpDO = rateCommonDAO.getRegionalApprovalDetails(
								regionId, indCatId);
						if (!StringUtil.isNull(regEmpDO)) {
							regMailId = regEmpDO.getEmailId();
						}
					}

				}
				MailSenderTO emailTO = new MailSenderTO();
				emailTO.setFrom(FrameworkConstants.CLIENT_USER_FROM_EMAIL_ID);
				emailTO.setTo(toMail);
				emailTO.setCc(ccMail);
				emailTO.setMailSubject(RateCommonConstants.CUSTOMER_BLOCKED_INTIMATION);

				Map<Object, Object> mailTemplate = new HashMap<Object, Object>();
				mailTemplate.put(RateCommonConstants.RATE_CONTRACT_NUMBER,
						rcDO.getRateContractNo());
				mailTemplate.put(RateCommonConstants.SALES_PERSON_NAME,
						salesEmpDO.getFirstName() + CommonConstants.SPACE
								+ salesEmpDO.getLastName());
				mailTemplate.put(RateCommonConstants.CUSTOMER_NAME, rcDO
						.getRateQuotationDO().getCustomer().getBusinessName());
				mailTemplate.put(RateCommonConstants.SOLD_TO_CODE, rcDO
						.getRateQuotationDO().getCustomer().getCustomerCode());
				mailTemplate.put(
						RateCommonConstants.RATE_CONTRACT_PAYMENT_BILLING_DTLS,
						rcDO.getConPayBillLocDO());
				mailTemplate.put(RateCommonConstants.BLOCKED_DATE,
						DateUtil.getCurrentDate());

				templateName = RateCommonConstants.BLOCK_CUSTOMER_ALERT_EMAIL_VM;
				emailTO.setTemplateName(templateName);
				emailTO.setTemplateVariables(mailTemplate);

				emailSenderUtil.sendEmail(emailTO);
			}

		}
		LOGGER.debug("RateCommonServiceImpl::sendEmailForBlockContractCustomer()::END");
	}

	@Override
	public List<StateTO> getStatesList() throws CGSystemException,
			CGBusinessException {
		return rateUniversalService.getStatesList();
	}

	@Override
	public List<CityTO> getCityListByStateId(Integer stateId)
			throws CGSystemException, CGBusinessException {
		return rateUniversalService.getCityListByStateId(stateId);
	}

	@Override
	public List<CodChargeTO> getDeclaredValueCodCharge(String configuredType)
			throws CGBusinessException, CGSystemException {
		List<CodChargeTO> chargeTOs = null;
		LOGGER.trace("RateCommonServiceImpl::getDeclaredValueCodCharge::START------------>:::::::");
		List<CodChargeDO> codChargeDOs = rateCommonDAO
				.getDeclaredValueCodCharge(configuredType);
		chargeTOs = new ArrayList<CodChargeTO>(codChargeDOs.size());

		if (!CGCollectionUtils.isEmpty(codChargeDOs)) {
			for (CodChargeDO chargeDO : codChargeDOs) {
				CodChargeTO chargeTO = new CodChargeTO();
				chargeTO.setCodChargeId(chargeDO.getCodChargeId());
				chargeTO.setMinimumDeclaredValue(chargeDO
						.getMinimumDeclaredValue());
				chargeTO.setMaximumDeclaredValue(chargeDO
						.getMaximumDeclaredValue());
				chargeTO.setConfiguredFor(chargeDO.getConfiguredFor());
				chargeTO.setMinimumDeclaredValLabel(chargeDO
						.getMinimumDeclaredValLabel());
				chargeTO.setMaximumDeclaredValLabel(chargeDO
						.getMaximumDeclaredValLabel());
				chargeTOs.add(chargeTO);
			}
		}
		LOGGER.trace("RateCommonServiceImpl::getDeclaredValueCodCharge::END------------>:::::::");
		return chargeTOs;
	}

	@Override
	public OfficeTO getOfficeByOfcCode(String ofcCode)
			throws CGBusinessException, CGSystemException {

		return rateUniversalService.getOfficeByOfcCode(ofcCode);
	}

	@Override
	public EmployeeTO getEmployeeByEmpCode(String empCode)
			throws CGBusinessException, CGSystemException {

		return rateUniversalService.getEmployeeByEmpCode(empCode);
	}

	@Override
	public UserTO getUserByUserName(String userName)
			throws CGBusinessException, CGSystemException {

		return rateUniversalService.getUserByUserName(userName);
	}

	@Override
	public RateCustomerCategoryTO getRateCustCategoryByCode(
			String rateCustomerCategoryCode) throws CGBusinessException,
			CGSystemException {

		return rateUniversalService
				.getRateCustCategoryByCode(rateCustomerCategoryCode);
	}

	@Override
	public RateIndustryTypeTO getRateIndustryTypeByCode(
			String rateIndustryTypeCode) throws CGBusinessException,
			CGSystemException {

		return rateUniversalService
				.getRateIndustryTypeByCode(rateIndustryTypeCode);
	}

	@Override
	public CustomerGroupTO getCustomerGroupByCode(String customerGroupCode)
			throws CGBusinessException, CGSystemException {

		return rateUniversalService.getCustomerGroupByCode(customerGroupCode);
	}

	@Override
	public CustomerTypeTO getCustomerTypeByCode(String customerTypeCode)
			throws CGBusinessException, CGSystemException {

		return rateUniversalService.getCustomerTypeByCode(customerTypeCode);
	}

	@Override
	public RateIndustryCategoryTO getIndustryCategoryByCode(String indCatCode)
			throws CGBusinessException, CGSystemException {

		return rateUniversalService.getIndustryCategoryByCode(indCatCode);
	}

	@Override
	public DepartmentTO getDepartmentByCode(String deptCode)
			throws CGBusinessException, CGSystemException {

		return rateUniversalService.getDepartmentByCode(deptCode);
	}
	

	@Override
	public List<CodChargeTO> getDeclaredValueCodChargeForBA(
			String configuredType, Integer rateCustomerCategoryId)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateCommonServiceImpl::getDeclaredValueCodChargeForBA::START------------>:::::::");
		List<CodChargeTO> codChargeTOs = null;
		List<CodChargeDO> codChargeDOs = rateCommonDAO.getDeclaredValueCodChargeForBA(configuredType, rateCustomerCategoryId);
		
		codChargeTOs = new ArrayList<>(codChargeDOs.size());
		if(!StringUtil.isEmptyColletion(codChargeDOs)){
			for (CodChargeDO codChargeDO : codChargeDOs) {
				CodChargeTO codChargeTO = new CodChargeTO();
				//codChargeTO = (CodChargeTO) CGObjectConverter.createToFromDomain(codChargeDO, codChargeTO);
				codChargeTO.setCodChargeId(codChargeDO.getCodChargeId());
				codChargeTO.setMinimumDeclaredValue(codChargeDO.getMinimumDeclaredValue());
				codChargeTO.setMaximumDeclaredValue(codChargeDO.getMaximumDeclaredValue());
				codChargeTO.setCodChargeType(codChargeDO.getCodChargeType());
				codChargeTO.setPercentileValue(codChargeDO.getPercentileValue());
				codChargeTO.setFixedValue(codChargeDO.getFixedValue());
				codChargeTO.setConfiguredFor(codChargeDO.getConfiguredFor());
				codChargeTO.setMinimumDeclaredValLabel(codChargeDO.getMinimumDeclaredValLabel());
				codChargeTO.setMaximumDeclaredValLabel(codChargeDO.getMaximumDeclaredValLabel());
				RateCustomerCategoryTO rateCustomerCategoryTO =  new RateCustomerCategoryTO();
				rateCustomerCategoryTO = (RateCustomerCategoryTO) CGObjectConverter.createToFromDomain(codChargeDO.getRateCustomerCategory(), rateCustomerCategoryTO);
				codChargeTO.setRateCustomerCategoryTO(rateCustomerCategoryTO);
				codChargeTOs.add(codChargeTO);
			}
		}
		LOGGER.trace("RateCommonServiceImpl::getDeclaredValueCodChargeForBA::END------------>:::::::");
		return codChargeTOs;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<WeightSlabTO> getWeightSlabByWtSlabCate(String wtSlabCate)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RateCommonServiceImpl :: getWeightSlabByWtSlabCate() :: START");
		List<WeightSlabTO> wtSlabTOs = null;
		List<WeightSlabDO> wtSlabDOs = rateUniversalService
				.getWeightSlabByWtSlabCate(wtSlabCate);
		if (!CGCollectionUtils.isEmpty(wtSlabDOs)) {
			wtSlabTOs = (List<WeightSlabTO>) CGObjectConverter
					.createTOListFromDomainList(wtSlabDOs, WeightSlabTO.class);
		}
		LOGGER.trace("RateCommonServiceImpl :: getWeightSlabByWtSlabCate() :: END");
		return wtSlabTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WeightSlabTO> getWeightSlabByWtSlabCateAndEndWt(
			String wtSlabCate, Double endWeight) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("RateCommonServiceImpl :: getWeightSlabByWtSlabCateAndEndWt() :: START");
		List<WeightSlabTO> wtSlabTOs = null;
		List<WeightSlabDO> wtSlabDOs = rateUniversalService
				.getWeightSlabByWtSlabCateAndEndWt(wtSlabCate, endWeight);
		if (!CGCollectionUtils.isEmpty(wtSlabDOs)) {
			wtSlabTOs = (List<WeightSlabTO>) CGObjectConverter
					.createTOListFromDomainList(wtSlabDOs, WeightSlabTO.class);
		}
		LOGGER.trace("RateCommonServiceImpl :: getWeightSlabByWtSlabCateAndEndWt() :: END");
		return wtSlabTOs;
	}

	@Override
	public List<OfficeTO> getAllOfficesByCityAndOfcTypeCode(
			Integer cityId, String ofcTypeCode)
			throws CGBusinessException, CGSystemException {
		return rateUniversalService.getAllOfficesByCityAndOfcTypeCode(cityId, ofcTypeCode);
	}
	
}
