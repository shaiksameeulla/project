/**
 * 
 */
package com.ff.universe.ratemanagement.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.bs.sequence.SequenceGeneratorService;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingPreferenceDetailsTO;
import com.ff.booking.BookingTypeConfigTO;
import com.ff.business.CustomerTO;
import com.ff.business.CustomerTypeTO;
import com.ff.domain.booking.BookingTypeConfigDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.pickup.PickupDeliveryContractWrapperDO;
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
import com.ff.geography.CityTO;
import com.ff.geography.CountryTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.geography.StateTO;
import com.ff.geography.ZoneTO;
import com.ff.organization.DepartmentTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.InsuredByTO;
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
import com.ff.to.ratemanagement.masters.VobSlabTO;
import com.ff.to.ratemanagement.masters.WeightSlabTO;
import com.ff.to.ratemanagement.operations.StockRateTO;
import com.ff.to.ratemanagement.operations.ratequotation.CustomerGroupTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateIndustryTypeTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationTO;
import com.ff.to.stockmanagement.masters.ItemTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserTO;
import com.ff.universe.booking.service.BookingUniversalService;
import com.ff.universe.constant.UniversalErrorConstants;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.ratemanagement.constant.RateUniversalConstants;
import com.ff.universe.ratemanagement.dao.RateUniversalDAO;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.universe.stockmanagement.service.StockUniversalService;
import com.ff.universe.umc.service.UserManagementCommonService;
import com.ff.universe.util.UniversalConverterUtil;

/**
 * @author rmaladi
 * 
 */
public class RateUniversalServiceImpl implements RateUniversalService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RateUniversalServiceImpl.class);
	private OrganizationCommonService organizationCommonService;
	private RateUniversalDAO rateUniversalDAO;
	private GeographyCommonService geographyCommonService;
	private ServiceOfferingCommonService serviceOfferingCommonService;
	private SequenceGeneratorService sequenceGeneratorService;
	private BookingUniversalService bookingUniversalService;
	/** The stockUniversalService. */
	private StockUniversalService stockUniversalService;
	private UserManagementCommonService umcCommonService;

	
	public UserManagementCommonService getUmcCommonService() {
		return umcCommonService;
	}

	public void setUmcCommonService(UserManagementCommonService umcCommonService) {
		this.umcCommonService = umcCommonService;
	}

	/**
	 * @return the stockUniversalService
	 */
	public StockUniversalService getStockUniversalService() {
		return stockUniversalService;
	}

	/**
	 * @param stockUniversalService
	 *            the stockUniversalService to set
	 */
	public void setStockUniversalService(
			StockUniversalService stockUniversalService) {
		this.stockUniversalService = stockUniversalService;
	}

	public SequenceGeneratorService getSequenceGeneratorService() {
		return sequenceGeneratorService;
	}

	public void setSequenceGeneratorService(
			SequenceGeneratorService sequenceGeneratorService) {
		this.sequenceGeneratorService = sequenceGeneratorService;
	}

	/**
	 * @return the serviceOfferingCommonService
	 */
	public ServiceOfferingCommonService getServiceOfferingCommonService() {
		return serviceOfferingCommonService;
	}

	/**
	 * @param serviceOfferingCommonService
	 *            the serviceOfferingCommonService to set
	 */
	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}

	public GeographyCommonService getGeographyCommonService() {
		return geographyCommonService;
	}

	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	public RateUniversalDAO getRateUniversalDAO() {
		return rateUniversalDAO;
	}

	public void setRateUniversalDAO(RateUniversalDAO rateUniversalDAO) {
		this.rateUniversalDAO = rateUniversalDAO;
	}

	public OrganizationCommonService getOrganizationCommonService() {
		return organizationCommonService;
	}

	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	@Override
	public List<EmployeeTO> getEmployeeDetails(EmployeeTO employeeTO)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getEmployeeDetails(employeeTO);
	}

	/**
	 * @param bookingUniversalService
	 *            the bookingUniversalService to set
	 */
	public void setBookingUniversalService(
			BookingUniversalService bookingUniversalService) {
		this.bookingUniversalService = bookingUniversalService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateIndustryCategoryTO> getRateIndustryCategoryList(
			String applicable) throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateUniversalServiceImpl::getRateIndustryCategoryList()::START");
		List<RateIndustryCategoryTO> rateIndCatTOList = null;
		try {
			List<RateIndustryCategoryDO> rateIndCatDOList = rateUniversalDAO
					.getRateIndustryCategoryList(applicable);

			if (rateIndCatDOList != null && rateIndCatDOList.size() > 0) {
				rateIndCatTOList = (List<RateIndustryCategoryTO>) CGObjectConverter
						.createTOListFromDomainList(rateIndCatDOList,
								RateIndustryCategoryTO.class);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in RateUniversalServiceImpl :: getRateIndustryCategoryList() ::"
					+ e.getMessage());
			throw new CGBusinessException();
		}
		LOGGER.trace("RateUniversalServiceImpl::getRateIndustryCategoryList()::END");
		return rateIndCatTOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateProductCategoryTO> getRateProductCategoryList(String type)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateUniversalServiceImpl::getRateProductCategoryList()::START");
		List<RateProductCategoryTO> rateProdCatTOList = null;
		try {
			List<RateProductCategoryDO> rateProdCatDOList = rateUniversalDAO
					.getRateProductCategoryList(type);

			if (!CGCollectionUtils.isEmpty(rateProdCatDOList)
					&& rateProdCatDOList.size() > 0) {
				rateProdCatTOList = (List<RateProductCategoryTO>) CGObjectConverter
						.createTOListFromDomainList(rateProdCatDOList,
								RateProductCategoryTO.class);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in RateUniversalServiceImpl :: getRateProductCategoryList() ::"
					+ e.getMessage());
			throw new CGBusinessException();
		}
		LOGGER.trace("RateUniversalServiceImpl::getRateProductCategoryList()::END");
		return rateProdCatTOList;
	}

	@Override
	public List<RateVobSlabsTO> getRateVobSlabsList(String type,
			String custCatCode) throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateUniversalServiceImpl::getRateVobSlabsList()::START");
		List<RateVobSlabsTO> rateVobSlabsTOList = null;
		try {
			List<RateVobSlabsDO> rateVobSlabsDOList = rateUniversalDAO
					.getRateVobSlabsList(type, custCatCode);
			if (!CGCollectionUtils.isEmpty(rateVobSlabsDOList)) {
				rateVobSlabsTOList = new ArrayList<RateVobSlabsTO>();
				converterRateVobSlabsDOList2RateVobSlabsTOList(
						rateVobSlabsDOList, rateVobSlabsTOList);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in RateUniversalServiceImpl :: getRateVobSlabsList() ::"
					+ e.getMessage());
			throw new CGBusinessException();
		}
		LOGGER.trace("RateUniversalServiceImpl::getRateVobSlabsList()::END");
		return rateVobSlabsTOList;
	}

	private void converterRateVobSlabsDOList2RateVobSlabsTOList(
			List<RateVobSlabsDO> rateVobSlabsDOList,
			List<RateVobSlabsTO> rateVobSlabsTOList) throws CGBusinessException {
		LOGGER.trace("RateUniversalServiceImpl::converterRateVobSlabsDOList2RateVobSlabsTOList()::START");
		RateVobSlabsTO rateVobSlabsTO = null;
		VobSlabTO vobSlabTO = null;
		RateCustomerProductCatMapTO rcpmTO = null;
		RateCustomerCategoryTO rccTO = null;
		RateProductCategoryTO rpcTO = null;
		if (!CGCollectionUtils.isEmpty(rateVobSlabsDOList)
				&& rateVobSlabsDOList.size() > 0) {
			for (RateVobSlabsDO rateVobSlabsDO : rateVobSlabsDOList) {
				if (!StringUtil.isNull(rateVobSlabsDO)) {
					rateVobSlabsTO = new RateVobSlabsTO();
					rateVobSlabsTO = (RateVobSlabsTO) CGObjectConverter
							.createToFromDomain(rateVobSlabsDO, rateVobSlabsTO);
					if (!StringUtil.isNull(rateVobSlabsDO.getVobSlabDO())) {
						vobSlabTO = new VobSlabTO();
						vobSlabTO = (VobSlabTO) CGObjectConverter
								.createToFromDomain(
										rateVobSlabsDO.getVobSlabDO(),
										vobSlabTO);
						rateVobSlabsTO.setVobSlabTO(vobSlabTO);
					}
					if (!StringUtil.isNull(rateVobSlabsDO
							.getRateCustomerProductCatMapDO())) {
						rcpmTO = new RateCustomerProductCatMapTO();
						rcpmTO = (RateCustomerProductCatMapTO) CGObjectConverter
								.createToFromDomain(rateVobSlabsDO
										.getRateCustomerProductCatMapDO(),
										rcpmTO);
						if (!StringUtil.isNull(rateVobSlabsDO
								.getRateCustomerProductCatMapDO()
								.getRateCustomerCategoryDO())) {
							rccTO = new RateCustomerCategoryTO();
							rccTO = (RateCustomerCategoryTO) CGObjectConverter
									.createToFromDomain(rateVobSlabsDO
											.getRateCustomerProductCatMapDO()
											.getRateCustomerCategoryDO(), rccTO);
							rcpmTO.setRateCustomerCategoryTO(rccTO);
						}
						if (!StringUtil.isNull(rateVobSlabsDO
								.getRateCustomerProductCatMapDO()
								.getRateProductCategoryDO())) {
							rpcTO = new RateProductCategoryTO();
							rpcTO = (RateProductCategoryTO) CGObjectConverter
									.createToFromDomain(rateVobSlabsDO
											.getRateCustomerProductCatMapDO()
											.getRateProductCategoryDO(), rpcTO);
							rcpmTO.setRateProductCategoryTO(rpcTO);
						}

						rateVobSlabsTO.setRateCustomerProductCatMapTO(rcpmTO);
					}
					rateVobSlabsTOList.add(rateVobSlabsTO);
				}

			}
		}
		LOGGER.trace("RateUniversalServiceImpl::converterRateVobSlabsDOList2RateVobSlabsTOList()::END");
	}

	@Override
	public List<RateWeightSlabsTO> getRateWeightSlabsList(String type,
			String custCatCode) throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateUniversalServiceImpl::getRateWeightSlabsList()::START");
		List<RateWeightSlabsTO> rateWtSlabsTOList = null;
		try {
			List<RateWeightSlabsDO> rateWtSlabsDOList = rateUniversalDAO
					.getRateWeightSlabsList(type, custCatCode);

			if (!CGCollectionUtils.isEmpty(rateWtSlabsDOList)) {
				rateWtSlabsTOList = new ArrayList<RateWeightSlabsTO>();
				converterRateWtSlabsDOList2RateWtSlabsTOList(rateWtSlabsDOList,
						rateWtSlabsTOList);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in RateUniversalServiceImpl :: getRateWeightSlabsList() ::"
					+ e.getMessage());
			throw new CGBusinessException();
		}
		LOGGER.trace("RateUniversalServiceImpl::getRateWeightSlabsList()::END");
		return rateWtSlabsTOList;
	}

	private void converterRateWtSlabsDOList2RateWtSlabsTOList(
			List<RateWeightSlabsDO> rateWtSlabsDOList,
			List<RateWeightSlabsTO> rateWtSlabsTOList)
			throws CGBusinessException {
		LOGGER.trace("RateUniversalServiceImpl::converterRateWtSlabsDOList2RateWtSlabsTOList()::START");
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
		LOGGER.trace("RateUniversalServiceImpl::converterRateWtSlabsDOList2RateWtSlabsTOList()::END");
	}

	@Override
	public List<RateSectorsTO> getRateSectorsList(String type,
			String custCatCode) throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateUniversalServiceImpl::getRateSectorsList()::START");

		List<RateSectorsTO> rateSectorsTOList = null;
		try {
			List<RateSectorsDO> rateSectorsDOList = rateUniversalDAO
					.getRateSectorsList(type, custCatCode);

			if (!CGCollectionUtils.isEmpty(rateSectorsDOList)) {
				rateSectorsTOList = new ArrayList<RateSectorsTO>();
				converterRateSectorsDOList2RateSectorsTOList(rateSectorsDOList,
						rateSectorsTOList);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in RateUniversalServiceImpl :: getRateSectorsList() ::"
					+ e.getMessage());
			throw new CGBusinessException();
		}
		LOGGER.trace("RateUniversalServiceImpl::getRateSectorsList()::END");
		return rateSectorsTOList;
	}

	private void converterRateSectorsDOList2RateSectorsTOList(
			List<RateSectorsDO> rateSectorsDOList,
			List<RateSectorsTO> rateSectorsTOList) throws CGBusinessException {
		LOGGER.trace("RateUniversalServiceImpl::converterRateSectorsDOList2RateSectorsTOList()::START");
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
		LOGGER.trace("RateUniversalServiceImpl::converterRateSectorsDOList2RateSectorsTOList()::END");
	}

	@Override
	public List<RateCustomerProductCatMapTO> getRateCustomerProductCatMapList()
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateUniversalServiceImpl::getRateCustomerProductCatMapList()::START");
		List<RateCustomerProductCatMapTO> rateCustProdCatTOList = null;
		try {
			List<RateCustomerProductCatMapDO> rateCustProdCatDOList = rateUniversalDAO
					.getRateCustomerProductCatMapList();

			if (!CGCollectionUtils.isEmpty(rateCustProdCatDOList)
					&& rateCustProdCatDOList.size() > 0) {
				rateCustProdCatTOList = new ArrayList<RateCustomerProductCatMapTO>();
				converterRateCustomerProductCatMapDOList2RateCustomerProductCatMapTOList(
						rateCustProdCatDOList, rateCustProdCatTOList);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in RateUniversalServiceImpl :: getRateCustomerProductCatMapList() ::"
					+ e.getMessage());
			throw new CGBusinessException();
		}
		LOGGER.trace("RateUniversalServiceImpl::getRateCustomerProductCatMapList()::END");
		return rateCustProdCatTOList;
	}

	private void converterRateCustomerProductCatMapDOList2RateCustomerProductCatMapTOList(
			List<RateCustomerProductCatMapDO> rateCustProdCatDOList,
			List<RateCustomerProductCatMapTO> rateCustProdCatTOList)
			throws CGBusinessException {
		LOGGER.trace("RateUniversalServiceImpl::converterRateCustomerProductCatMapDOList2RateCustomerProductCatMapTOList()::START");
		RateCustomerProductCatMapTO rcpcmTO = null;
		RateCustomerCategoryTO rccTO = null;
		RateProductCategoryTO rpcTO = null;
		if (!CGCollectionUtils.isEmpty(rateCustProdCatDOList)
				&& rateCustProdCatDOList.size() > 0) {
			for (RateCustomerProductCatMapDO rcpcmDO : rateCustProdCatDOList) {
				if (!StringUtil.isNull(rcpcmDO)) {
					rcpcmTO = new RateCustomerProductCatMapTO();
					rcpcmTO = (RateCustomerProductCatMapTO) CGObjectConverter
							.createToFromDomain(rcpcmDO, rcpcmTO);
					if (!StringUtil.isNull(rcpcmDO.getRateCustomerCategoryDO())) {
						rccTO = new RateCustomerCategoryTO();
						rccTO = (RateCustomerCategoryTO) CGObjectConverter
								.createToFromDomain(
										rcpcmDO.getRateCustomerCategoryDO(),
										rccTO);
						rcpcmTO.setRateCustomerCategoryTO(rccTO);
					}
					if (!StringUtil.isNull(rcpcmDO.getRateProductCategoryDO())) {
						rpcTO = new RateProductCategoryTO();
						rpcTO = (RateProductCategoryTO) CGObjectConverter
								.createToFromDomain(
										rcpcmDO.getRateProductCategoryDO(),
										rpcTO);
						rcpcmTO.setRateProductCategoryTO(rpcTO);
					}

					rateCustProdCatTOList.add(rcpcmTO);
				}

			}
		}
		LOGGER.trace("RateUniversalServiceImpl::converterRateCustomerProductCatMapDOList2RateCustomerProductCatMapTOList()::END");
	}

	@Override
	public List<RateMinChargeableWeightTO> getRateMinChrgWtList(String type,
			String custCatCode) throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateUniversalServiceImpl::getRateMinChrgWtList()::START");
		List<RateMinChargeableWeightTO> rateMinChrgWtTOList = null;
		try {
			List<RateMinChargeableWeightDO> rateMinChrgWtDOList = rateUniversalDAO
					.getRateMinChrgWtList(type, custCatCode);

			if (!CGCollectionUtils.isEmpty(rateMinChrgWtDOList)
					&& rateMinChrgWtDOList.size() > 0) {
				rateMinChrgWtTOList = new ArrayList<RateMinChargeableWeightTO>();
				ConverterRateMinChrgWtDOList2RateMinChrgWtTOList(
						rateMinChrgWtDOList, rateMinChrgWtTOList);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in RateUniversalServiceImpl :: getRateMinChrgWtList() ::"
					+ e.getMessage());
			throw new CGBusinessException();
		}
		LOGGER.trace("RateUniversalServiceImpl::getRateMinChrgWtList()::END");
		return rateMinChrgWtTOList;
	}

	private void ConverterRateMinChrgWtDOList2RateMinChrgWtTOList(
			List<RateMinChargeableWeightDO> rateMinChrgWtDOList,
			List<RateMinChargeableWeightTO> rateMinChrgWtTOList)
			throws CGBusinessException {
		LOGGER.trace("RateUniversalServiceImpl::ConverterRateMinChrgWtDOList2RateMinChrgWtTOList()::START");
		RateMinChargeableWeightTO rateMinChrgWtTO = null;
		if (!CGCollectionUtils.isEmpty(rateMinChrgWtDOList)
				&& rateMinChrgWtDOList.size() > 0) {
			for (RateMinChargeableWeightDO rateMinChrgWtDO : rateMinChrgWtDOList) {
				if (!StringUtil.isNull(rateMinChrgWtDO)) {
					rateMinChrgWtTO = new RateMinChargeableWeightTO();
					ConverterRateMinChrgWtDO2RateMinChrgWtTO(rateMinChrgWtDO,
							rateMinChrgWtTO);

					rateMinChrgWtTOList.add(rateMinChrgWtTO);
				}

			}
		}
		LOGGER.trace("RateUniversalServiceImpl::ConverterRateMinChrgWtDOList2RateMinChrgWtTOList()::END");
	}

	private void ConverterRateMinChrgWtDO2RateMinChrgWtTO(
			RateMinChargeableWeightDO rateMinChrgWtDO,
			RateMinChargeableWeightTO rateMinChrgWtTO)
			throws CGBusinessException {
		LOGGER.trace("RateUniversalServiceImpl::ConverterRateMinChrgWtDO2RateMinChrgWtTO()::START");
		RateCustomerProductCatMapTO rcpmTO = null;
		RateCustomerCategoryTO rccTO = null;
		RateProductCategoryTO rpcTO = null;
		if (!StringUtil.isNull(rateMinChrgWtDO)) {

			if (!StringUtil.isNull(rateMinChrgWtDO)) {

				rateMinChrgWtTO = (RateMinChargeableWeightTO) CGObjectConverter
						.createToFromDomain(rateMinChrgWtDO, rateMinChrgWtTO);

				if (!StringUtil.isNull(rateMinChrgWtDO
						.getRateCustomerProductCatMapDO())) {
					rcpmTO = new RateCustomerProductCatMapTO();
					rcpmTO = (RateCustomerProductCatMapTO) CGObjectConverter
							.createToFromDomain(rateMinChrgWtDO
									.getRateCustomerProductCatMapDO(), rcpmTO);
					if (!StringUtil.isNull(rateMinChrgWtDO
							.getRateCustomerProductCatMapDO()
							.getRateCustomerCategoryDO())) {
						rccTO = new RateCustomerCategoryTO();
						rccTO = (RateCustomerCategoryTO) CGObjectConverter
								.createToFromDomain(rateMinChrgWtDO
										.getRateCustomerProductCatMapDO()
										.getRateCustomerCategoryDO(), rccTO);
						rcpmTO.setRateCustomerCategoryTO(rccTO);
					}
					if (!StringUtil.isNull(rateMinChrgWtDO
							.getRateCustomerProductCatMapDO()
							.getRateProductCategoryDO())) {
						rpcTO = new RateProductCategoryTO();
						rpcTO = (RateProductCategoryTO) CGObjectConverter
								.createToFromDomain(rateMinChrgWtDO
										.getRateCustomerProductCatMapDO()
										.getRateProductCategoryDO(), rpcTO);
						rcpmTO.setRateProductCategoryTO(rpcTO);
					}
					rateMinChrgWtTO.setRateCustomerProductCatMapTO(rcpmTO);
				}

			}
		}
		LOGGER.trace("RateUniversalServiceImpl::ConverterRateMinChrgWtDO2RateMinChrgWtTO()::END");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateCustomerCategoryTO> getRateCustomerCategoryList()
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateUniversalServiceImpl::getRateCustomerCategoryList()::START");
		List<RateCustomerCategoryTO> rateCustCatTOList = null;
		try {
			List<RateCustomerCategoryDO> rateCustCatDOList = rateUniversalDAO
					.getRateCustomerCategoryList();

			if (!CGCollectionUtils.isEmpty(rateCustCatDOList)
					&& rateCustCatDOList.size() > 0) {
				rateCustCatTOList = (List<RateCustomerCategoryTO>) CGObjectConverter
						.createTOListFromDomainList(rateCustCatDOList,
								RateCustomerCategoryTO.class);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in RateUniversalServiceImpl :: getRateCustomerCategoryList() ::"
					+ e.getMessage());
			throw new CGBusinessException();
		}
		LOGGER.trace("RateUniversalServiceImpl::getRateCustomerCategoryList()::END");
		return rateCustCatTOList;
	}

	@Override
	public CityTO getCityByPincode(String pincode) throws CGBusinessException,
			CGSystemException {
		return geographyCommonService.getCity(pincode);
	}

	@Override
	public List<RateSectorsTO> getRateConfigSectorsList(String type,
			String custCatCode) throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateUniversalServiceImpl::getRateConfigSectorsList()::START");
		List<RateSectorsTO> rateSectorsTOList = null;
		try {
			List<RateSectorsDO> rateSectorsDOList = rateUniversalDAO
					.getRateConfigSectorsList(type, custCatCode);

			if (!CGCollectionUtils.isEmpty(rateSectorsDOList)) {
				rateSectorsTOList = new ArrayList<RateSectorsTO>();
				converterRateSectorsDOList2RateSectorsTOList(rateSectorsDOList,
						rateSectorsTOList);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in RateUniversalServiceImpl :: getRateConfigSectorsList() ::"
					+ e.getMessage());
			throw new CGBusinessException();
		}
		LOGGER.trace("RateUniversalServiceImpl::getRateConfigSectorsList()::END");
		return rateSectorsTOList;
	}

	@Override
	public List<ZoneTO> getZoneByZoneId(Integer zoneId)
			throws CGBusinessException, CGSystemException {

		return geographyCommonService.getZoneByZoneId(zoneId);
	}

	@SuppressWarnings("unchecked")
	public List<StockStandardTypeTO> getStandardType(String typeName)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RateUniversalServiceImpl::getStandardType()::START");
		List<StockStandardTypeTO> stockStandardTypeTO = null;
		try {
			List<StockStandardTypeDO> StockStandardTypeDOList = rateUniversalDAO
					.getStandardType(typeName);
			if (StockStandardTypeDOList != null
					&& StockStandardTypeDOList.size() > 0) {
				stockStandardTypeTO = (List<StockStandardTypeTO>) CGObjectConverter
						.createTOListFromDomainList(StockStandardTypeDOList,
								StockStandardTypeTO.class);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in RateUniversalServiceImpl :: getStandardType() ::"
					+ e.getMessage());
			throw new CGBusinessException();
		}
		LOGGER.trace("RateUniversalServiceImpl::getStandardType()::END");
		return stockStandardTypeTO;
	}

	@Override
	public OfficeTO getOfficeDetails(Integer loggedInRHO)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getOfficeDetails(loggedInRHO);
	}

	@Override
	public List<CityTO> getCitiesByCity(CityTO rhoCityTO)
			throws CGBusinessException, CGSystemException {
		return geographyCommonService.getCitiesByCity(rhoCityTO);
	}

	@Override
	public CityTO getCity(CityTO cityTO) throws CGBusinessException,
			CGSystemException {
		return geographyCommonService.getCity(cityTO);
	}

	@Override
	public List<InsuredByTO> getInsuarnceBy() throws CGBusinessException,
			CGSystemException {
		return serviceOfferingCommonService.getInsuarnceBy();
	}

	@Override
	public SequenceGeneratorConfigTO getGeneratedSequence(
			SequenceGeneratorConfigTO sequenceGeneratorConfigTO)
			throws CGBusinessException, CGSystemException {
		return sequenceGeneratorService
				.getGeneratedSequence(sequenceGeneratorConfigTO);
	}

	@Override
	public List<OfficeTO> getAllOfficesByCity(Integer loginCityId)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getAllOfficesByCity(loginCityId);
	}

	@Override
	public List<EmployeeTO> getEmployeesOfOffice(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getEmployeesOfOffice(officeTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.ratemanagement.service.RateUniversalService#
	 * getShippedToCodesByCustomerId(java.lang.Integer)
	 */
	@Override
	public List<String> getShippedToCodesByCustomerId(Integer customerId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RateUniversalServiceImpl::getShippedToCodesByCustomerId()::START");
		List<String> shippedToCodes = rateUniversalDAO
				.getShippedToCodesByCustomerId(customerId);
		if (StringUtil.isEmptyList(shippedToCodes)) {
			shippedToCodes = null;
		}
		LOGGER.trace("RateUniversalServiceImpl::getShippedToCodesByCustomerId()::END");
		return shippedToCodes;
	}

	@Override
	public List<OfficeTO> getRHOOfficesByUserId(Integer userId)
			throws CGBusinessException, CGSystemException {

		return organizationCommonService.getRHOOfficesByUserId(userId);
	}

	@Override
	public List<CityTO> getCityListOfReportedOffices(Integer rhoOfficeId)
			throws CGBusinessException, CGSystemException {

		return geographyCommonService.getCityListOfReportedOffices(rhoOfficeId);
	}

	@Override
	public List<CityTO> getCityListOfAssignedOffices(Integer userId)
			throws CGBusinessException, CGSystemException {
		return geographyCommonService.getCityListOfAssignedOffices(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.ratemanagement.service.RateUniversalService#getAllRegions
	 * ()
	 */
	@Override
	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException {
		return geographyCommonService.getAllRegions();
	}

	/**
	 * Get all the cities comes under the Region.
	 * 
	 * @param regionTO
	 *            the region to
	 * @return List<CityTO> will get filled with all the city details.
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	@Override
	public List<CityTO> getCitiesByRegion(RegionTO regionTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RateUniversalServiceImpl::getCitiesByRegion()::START");
		List<CityTO> cityTOs = null;
		if (!StringUtil.isNull(regionTO)) {
			CityTO cityTO = new CityTO();
			cityTO.setRegion(regionTO.getRegionId());
			cityTOs = geographyCommonService.getCitiesByCity(cityTO);
		}
		LOGGER.trace("RateUniversalServiceImpl::getCitiesByRegion()::END");
		return cityTOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.ratemanagement.service.RateUniversalService#
	 * getRateContractsByCustomerIds(java.util.List)
	 */
	@Override
	public List<RateContractTO> getRateContractsByCustomerIds(
			List<Integer> customerIds) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("RateUniversalServiceImpl::getRateContractsByCustomerIds()::START");
		List<RateContractDO> rateContractDOs = rateUniversalDAO
				.getRateContractsByCustomerIds(customerIds);
		List<RateContractTO> rateContractTOs = convertRateContractDOsToTOs(rateContractDOs);
		LOGGER.trace("RateUniversalServiceImpl::getRateContractsByCustomerIds()::END");
		return rateContractTOs;
	}

	/**
	 * Convert rate contract DOs to TOs.
	 * 
	 * @param rateContractDOs
	 *            the rate contract DOs
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private List<RateContractTO> convertRateContractDOsToTOs(
			List<RateContractDO> rateContractDOs) throws CGBusinessException {
		LOGGER.trace("RateUniversalServiceImpl::convertRateContractDOsToTOs()::START");
		List<RateContractTO> rateContractTOs = null;
		if (StringUtil.isEmptyColletion(rateContractDOs)) {
			rateContractTOs = new ArrayList<>(rateContractDOs.size());

			for (RateContractDO rateContractDO : rateContractDOs) {
				RateContractTO rateContractTO = convertRateContractDOToTO(rateContractDO);
				rateContractTOs.add(rateContractTO);
			}
		}
		LOGGER.trace("RateUniversalServiceImpl::convertRateContractDOsToTOs()::END");
		return rateContractTOs;
	}

	/**
	 * Convert rate contract do to to.
	 * 
	 * @param rateContractDO
	 *            the rate contract do
	 * @return the rate contract to
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private RateContractTO convertRateContractDOToTO(
			RateContractDO rateContractDO) throws CGBusinessException {
		LOGGER.trace("RateUniversalServiceImpl::convertRateContractDOToTO()::START");
		RateContractTO rateContractTO = new RateContractTO();
		CGObjectConverter.createToFromDomain(rateContractDO, rateContractTO);

		if (rateContractDO.getRateQuotationDO() != null) {
			RateQuotationTO rateQuotationTO = new RateQuotationTO();
			CGObjectConverter.createToFromDomain(
					rateContractDO.getRateQuotationDO(), rateQuotationTO);
			rateContractTO.setRateQuotationTO(rateQuotationTO);
		}
		LOGGER.trace("RateUniversalServiceImpl::convertRateContractDOToTO()::END");
		return rateContractTO;
	}

	@Override
	public List<OfficeTO> getPickupBranchsByPincode(String pincode)
			throws CGBusinessException, CGSystemException {
		//return organizationCommonService.getBranchesServicing(pincode);
		//return organizationCommonService.getOfficesAndHubOfficesServicedByPincode(pincode);
		// TF ID: artf3400502 
		return organizationCommonService.getOfficesAndAllHubOfficesofCityServicedByPincode(pincode);
	}

	@Override
	public PincodeTO validatePincode(PincodeTO pincode)
			throws CGBusinessException, CGSystemException {
		return geographyCommonService.validatePincode(pincode);
	}

	@Override
	public StateTO getStateByCode(String stateCode) throws CGBusinessException,
			CGSystemException {
		return geographyCommonService.getStateByCode(stateCode);
	}

	@Override
	public List<BookingPreferenceDetailsTO> getBookingPrefDetails()
			throws CGSystemException, CGBusinessException {
		return bookingUniversalService.getBookingPrefDetails();
	}

	@Override
	public OfficeTO getOfficeByempId(Integer empId) throws CGBusinessException,
			CGSystemException {

		return organizationCommonService.getOfficeByempId(empId);
	}

	@Override
	public List<CustomerTO> getCustomersByPickupDeliveryLocation(
			Integer officeId) throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateUniversalServiceImpl::getCustomersByPickupDeliveryLocation()::START");
		List<CustomerDO> customerDOs = rateUniversalDAO
				.getCustomersByPickupDeliveryLocation(officeId);
		List<CustomerTO> customerTOs = new ArrayList<CustomerTO>();
		for (CustomerDO customerDO : customerDOs) {
			CustomerTO customerTO = new CustomerTO();
			customerTO = (CustomerTO) CGObjectConverter.createToFromDomain(
					customerDO, customerTO);
			customerTOs.add(customerTO);
		}
		LOGGER.trace("RateUniversalServiceImpl::getCustomersByPickupDeliveryLocation()::END");
		return customerTOs;
	}

	@Override
	public CountryTO getCountryByCode(String string) throws CGSystemException,
			CGBusinessException {
		return geographyCommonService.getCountryByCode(string);
	}

	@Override
	public Map<Integer, String> getItemTypeAsMap() throws CGSystemException,
			CGBusinessException {
		return stockUniversalService.getItemTypeAsMap();
	}

	@Override
	public Map<Integer, String> getItemByTypeAsMap(Integer itemTypeId)
			throws CGSystemException, CGBusinessException {
		return stockUniversalService.getItemByTypeAsMap(itemTypeId);
	}

	@Override
	public ItemTO getItemByItemTypeAndItemId(Integer itemTypeId, Integer itemId)
			throws CGSystemException, CGBusinessException {
		return stockUniversalService.getItemByItemTypeAndItemId(itemTypeId,
				itemId);
	}

	@Override
	public List<CityTO> getCityListOfReportedOfficesOfRHO(List<OfficeTO> ofcList)
			throws CGBusinessException, CGSystemException {
		return geographyCommonService
				.getCityListOfReportedOfficesOfRHO(ofcList);
	}

	/**
	 * calculateRateForBAMaterial.
	 *
	 * @param stockRateTo 
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@Override
	public void calculateRateForBAMaterial(StockRateTO stockRateTo)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RateUniversalServiceImpl::calculateRateForBAMaterial()::START");
		List<BAMaterialRateDetailsDO> rateConfigDtlsDoList = null;
		BAMaterialRateDetailsDO reateConfigDtlDO = null;
		if (isValidInputs(stockRateTo)) {
			processStockRateTO(stockRateTo);
			rateConfigDtlsDoList = rateUniversalDAO
					.getBAMaterialRateDetailsByMaterial(stockRateTo);
			if (!CGCollectionUtils.isEmpty(rateConfigDtlsDoList)) {
				reateConfigDtlDO = rateConfigDtlsDoList.get(0);
				if (!StringUtil
						.isEmptyDouble(reateConfigDtlDO.getRatePerUnit())) {
					stockRateTo.setRatePerUnit(BigDecimal.valueOf(reateConfigDtlDO.getRatePerUnit()));
					
					BigDecimal multiplier = BigDecimal.valueOf(0.01);
					BigDecimal taxableAmount = BigDecimal
							.valueOf(1);// A
					
					
					if (!StringUtil.isNull(stockRateTo.getIsForPanIndia())) {

						// Tax for J&K
						if (!StringUtil.isNull(stockRateTo.getStateTax())) {
							// Formula
							// A : Base Value
							// B : ST * 0.01 * A (State tax On Base Value)
							// C : CessTx * 0.1 *B (Cess.Tax on B)
							// Final Amount = A+B+C
							Double sttp = stockRateTo.getStateTax();
							Double surgTx = stockRateTo.getSurChrgeStateTax();
							BigDecimal stateTax = !StringUtil
									.isEmptyDouble(sttp) ? BigDecimal
									.valueOf(sttp) : BigDecimal.ZERO;
							BigDecimal surcgTx = !StringUtil
									.isEmptyDouble(surgTx) ? BigDecimal
									.valueOf(surgTx) : BigDecimal.ZERO;

							stateTax = stateTax.multiply(multiplier).multiply(
									taxableAmount);// B
							stockRateTo.setStateTaxAmount(stateTax.doubleValue());
							surcgTx = surcgTx.multiply(multiplier).multiply(
									stateTax);// C
							stockRateTo.setStateTaxAmount(surcgTx.doubleValue());
							BigDecimal toatalAmount = taxableAmount.add(
									stateTax).add(surcgTx);// A+B+C
							/** The total tax per quantity. it's total applied Tax per Unit Quantity */
							stockRateTo.setTotalTaxPerQuantityPerRupe(toatalAmount.doubleValue());
							toatalAmount=toatalAmount.multiply(BigDecimal.valueOf(reateConfigDtlDO.getRatePerUnit()));//Rate Per unit 
							if (!StringUtil.isEmptyInteger(stockRateTo
									.getQuantity())) {
								/** calculate final amount for given imnputs*/
								stockRateTo.setTotalAmount(toatalAmount.multiply(BigDecimal.valueOf(stockRateTo.getQuantity())).doubleValue());
							}
						} else {
							// Tax For Pan India
							// Formula (Available Components : Base rate,
							// ServiceTax,EDuCess,HEdCess)
							// A : Base Value
							// B: ServiceTx on A
							// C: EdCess on B
							// D:Hedcess on C
							// Total :A + B+C+D

							Double st = stockRateTo.getServiceTax();
							Double et = stockRateTo.getEduCessTax();
							Double ht = stockRateTo.getHeduCessTax();
							BigDecimal serviceTax = !StringUtil
									.isEmptyDouble(st) ? BigDecimal.valueOf(st)
									: BigDecimal.ZERO;
							BigDecimal eduTax = !StringUtil.isEmptyDouble(et) ? BigDecimal
									.valueOf(et) : BigDecimal.ZERO;
							BigDecimal heduTax = !StringUtil.isEmptyDouble(ht) ? BigDecimal
									.valueOf(ht) : BigDecimal.ZERO;
							serviceTax = serviceTax.multiply(multiplier)
									.multiply(taxableAmount);// B
							stockRateTo.setServiceTaxAmount(serviceTax.doubleValue());
							
							/*eduTax = eduTax.multiply(multiplier).multiply(
									serviceTax);// C */			
							// New change for Swachh Bharat Cess
							eduTax = eduTax.multiply(multiplier).multiply(taxableAmount);// C
							
							stockRateTo.setEduCessTaxAmount(eduTax.doubleValue());
							heduTax = heduTax.multiply(multiplier).multiply(
									serviceTax);
							stockRateTo.setHeduCessTaxAmount(heduTax.doubleValue());
							BigDecimal toatalAmount = taxableAmount
									.add(serviceTax).add(eduTax).add(heduTax);// A+B+C
							stockRateTo.setTotalTaxPerQuantityPerRupe(toatalAmount.doubleValue());
							if (!StringUtil.isEmptyInteger(stockRateTo
									.getQuantity())) {
								BigDecimal totalTaxPerQuantity=toatalAmount.multiply(BigDecimal.valueOf(reateConfigDtlDO.getRatePerUnit()));
								stockRateTo.setTotalTaxPerQuantity(totalTaxPerQuantity.doubleValue());
								stockRateTo.setTotalAmount(totalTaxPerQuantity.multiply(BigDecimal.valueOf(stockRateTo
									.getQuantity())).doubleValue());
							}
							

						}// End of Else block
						
					}// end Of Tax Compoent If
					
					

				} else {
					stockRateTo.setRatePerUnit(BigDecimal.ZERO);
				}
			} else {
				throw new CGBusinessException(UniversalErrorConstants.BA_ITEM_RATE_NOT_CONFIGURED);
			}

		} else {
			throw new CGBusinessException(UniversalErrorConstants.INVALID_INPUT_FOR_BA_RATE);
		}
		LOGGER.trace("RateUniversalServiceImpl::calculateRateForBAMaterial()::END");
	}

	/**
	 * @param itemTO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	private void processStockRateTO(StockRateTO stockRateTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateUniversalServiceImpl::processStockRateTO()::START");
		
		if (StringUtil.isEmptyInteger(stockRateTO.getItemId())) {
			List<ItemTO> itemToList = null;
			ItemTO itemTo= new ItemTO();
			itemToList = stockUniversalService.getAllItemsByType(itemTo);
			if (!CGCollectionUtils.isEmpty(itemToList)) {
				stockRateTO.setItemId(itemToList.get(0).getItemId());
			}else{
				throw new CGBusinessException(UniversalErrorConstants.INVALID_MATERIAL);
			}
		}
		if (StringUtil.isEmptyInteger(stockRateTO.getCityTO().getState())) {
			// get CityTO'state ;
			List<CityTO> cityListTo = getCitiesByCity(stockRateTO.getCityTO());
			if (!CGCollectionUtils.isEmpty(cityListTo)) {
				stockRateTO.setCityTO(cityListTo.get(0));
			}
		}
		Map<String, Double> taxComponents = getTaxComponents(
				stockRateTO.getCityTO(), stockRateTO.getDate());
		//validate tax components
		UniversalConverterUtil.prepareValidTaxComponents(taxComponents);
		if (!CGCollectionUtils.isEmpty(taxComponents)) {

			for(String taxKey:taxComponents.keySet()){
				switch(taxKey){
				case RateUniversalConstants.STATE_TAX_CODE:
					stockRateTO.setStateTax(taxComponents.get(taxKey));
					stockRateTO.setIsForPanIndia(false);
					break;
				case RateUniversalConstants.SURCHARGE_ON_STATE_TAX_CODE:
					stockRateTO.setSurChrgeStateTax(taxComponents.get(taxKey));
					break;
				case RateUniversalConstants.SERVICE_TAX_CODE:
					stockRateTO.setServiceTax(taxComponents.get(taxKey));
					stockRateTO.setIsForPanIndia(true);
					break;
				case RateUniversalConstants.EDU_CESS_CODE:
					stockRateTO.setEduCessTax(taxComponents.get(taxKey));
					break;
				case RateUniversalConstants.HIGHER_EDU_CES_CODE:
					stockRateTO.setHeduCessTax(taxComponents.get(taxKey));
					break;
				}
			}
		
			
		} else {
			throw new CGBusinessException(UniversalErrorConstants.INVALID_MATERIAL);
		}
		LOGGER.trace("RateUniversalServiceImpl::processStockRateTO()::END");
	}

	/**
	 * @param itemTO
	 */
	private boolean isValidInputs(StockRateTO rateTO) {
		LOGGER.trace("RateUniversalServiceImpl::isValidInputs()::START");
		boolean isValid = true;
		
		if (StringUtil.isEmptyInteger(rateTO.getItemId())) {
			isValid = false;
		}
		if (StringUtil.isNull(rateTO.getCityTO())) {
			isValid = false;
		}
		if (StringUtil.isEmptyInteger(rateTO.getCityTO().getCityId())) {
			isValid = false;
		}
		LOGGER.trace("RateUniversalServiceImpl::isValidInputs()::END");
		return isValid;
	}

	@Override
	public Map<String, Double> getTaxComponents(CityTO city, Date currentDate)
			throws CGBusinessException {
		LOGGER.trace("RateUniversalServiceImpl::getTaxComponents()::START");
		Map<String, Double> taxFigures = null;
		// Get tax components for SEC
		List<RateTaxComponentDO> rateTaxComponents = rateUniversalDAO
				.getTaxComponents(city.getState(), currentDate);
		if (!CGCollectionUtils.isEmpty(rateTaxComponents)) {
			taxFigures = new HashMap<>(rateTaxComponents.size());
				for (RateTaxComponentDO rateTaxComponent : rateTaxComponents) {
					taxFigures.put(rateTaxComponent.getRateComponentCode(),
							rateTaxComponent.getTaxPercentile());
				}
		}
		LOGGER.trace("RateUniversalServiceImpl::getTaxComponents()::END");
		return taxFigures;
	}

	@Override
	public CustomerTypeTO getCustomerTypeId(String custTypeCode)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RateUniversalServiceImpl::getCustomerTypeId()::START");
		CustomerTypeDO ctDO = rateUniversalDAO
				.getCustomerTypeByCode(custTypeCode);
		CustomerTypeTO ctTO = null;
		if (!StringUtil.isNull(ctDO)) {
			ctTO = new CustomerTypeTO();
			ctTO = (CustomerTypeTO) CGObjectConverter.createToFromDomain(ctDO,
					ctTO);
		}
		LOGGER.trace("RateUniversalServiceImpl::getCustomerTypeId()::END");
		return ctTO;
	}

	@Override
	public BookingTypeConfigTO getBookingTypeConfigVWDeno()
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RateUniversalServiceImpl::getBookingTypeConfig()::START");
		BookingTypeConfigDO configDO = rateUniversalDAO
				.getBookingTypeConfigVWDeno();
		BookingTypeConfigTO configTO = null;
		if (!StringUtil.isNull(configDO)) {
			configTO = new BookingTypeConfigTO();
			configTO = (BookingTypeConfigTO) CGObjectConverter
					.createToFromDomain(configDO, configTO);
		}
		LOGGER.trace("RateUniversalServiceImpl::getBookingTypeConfig()::END");
		return configTO;
	}


	@Override
	public List<PickupDeliveryContractWrapperDO> getPickUpDeliveryContratWrapperDOByOfficeId(
			Integer officeID) throws CGSystemException {
		LOGGER.trace("RateUniversalServiceImpl::getPickUpDeliveryContratWrapperDOByOfficeId()::START");
		List<PickupDeliveryContractWrapperDO>  pickupDeliveryContractWrapperDOList;
		pickupDeliveryContractWrapperDOList = rateUniversalDAO.getPickUpDeliveryContratWrapperDOByOfficeId(officeID);
		LOGGER.trace("RateUniversalServiceImpl::getPickUpDeliveryContratWrapperDOByOfficeId()::END");
		return pickupDeliveryContractWrapperDOList;
	}

	@Override
	public List<OfficeTO> getAllRegionalOffices() throws CGBusinessException,
			CGSystemException {
		
		return organizationCommonService.getAllRegionalOffices();
	}

	@Override
	public OfficeTO getOfficeByUserId(Integer userId)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getOfficeByUserId(userId);
	}

	@Override
	public List<StateTO> getStatesList() throws CGBusinessException,
			CGSystemException {
		
		return geographyCommonService.getStatesList();
	}

	@Override
	public List<CityTO> getCityListByStateId(Integer stateId)
			throws CGBusinessException, CGSystemException {
		return geographyCommonService.getCityListByStateId(stateId);
	}

	@Override
	public OfficeTO getOfficeByOfcCode(String ofcCode) throws CGBusinessException, CGSystemException{
		
		return organizationCommonService.getOfficeByOfcCode(ofcCode);
	}
	
	@Override
	public EmployeeTO getEmployeeByEmpCode(String empCode) throws CGBusinessException, CGSystemException{
	
		return organizationCommonService.getEmployeeByEmpCode(empCode);
	}
	
	@Override
	public UserTO getUserByUserName(String userName) throws CGBusinessException, CGSystemException{
		
		return umcCommonService.getUserByUserName(userName);
	}

	@Override
	public RateCustomerCategoryTO getRateCustCategoryByCode(
			String rateCustomerCategoryCode) throws CGBusinessException,
			CGSystemException {
		
		RateCustomerCategoryTO custCatTO = null;
		RateCustomerCategoryDO custCatDO = null;
		
		custCatDO = rateUniversalDAO.getRateCustCategoryGrpByCode(rateCustomerCategoryCode);
		if(!StringUtil.isNull(custCatDO)){
			custCatTO = new RateCustomerCategoryTO();
			custCatTO = (RateCustomerCategoryTO)CGObjectConverter.createToFromDomain(custCatDO, custCatTO);
		}
		return custCatTO;
	}
	
	@Override
	public RateIndustryTypeTO getRateIndustryTypeByCode(
			String rateIndustryTypeCode) throws CGBusinessException,
			CGSystemException {
		
		RateIndustryTypeTO custTypeTO = null;
		RateIndustryTypeDO custTypeDO = null;
		
		custTypeDO = rateUniversalDAO.getIndustryTypeByCode(rateIndustryTypeCode);
		if(!StringUtil.isNull(custTypeDO)){
			custTypeTO = new RateIndustryTypeTO();
			custTypeTO = (RateIndustryTypeTO)CGObjectConverter.createToFromDomain(custTypeDO, custTypeTO);
		}
		return custTypeTO;
	}

	@Override
	public CustomerGroupTO getCustomerGroupByCode(String customerGroupCode)
			throws CGBusinessException, CGSystemException {
		CustomerGroupTO custGroupTO = null;
		CustomerGroupDO custGroupDO = null;
		
		custGroupDO = rateUniversalDAO.getCustomerGroupByCode(customerGroupCode);
		if(!StringUtil.isNull(custGroupDO)){
			custGroupTO = new CustomerGroupTO();
			custGroupTO = (CustomerGroupTO)CGObjectConverter.createToFromDomain(custGroupDO, custGroupTO);
		}
		return custGroupTO;
	}

	@Override
	public CustomerTypeTO getCustomerTypeByCode(String customerTypeCode)
			throws CGBusinessException, CGSystemException {
		CustomerTypeTO custTypeTO = null;
		CustomerTypeDO custTypeDO = null;
		
		custTypeDO = rateUniversalDAO.getCustomerTypeByCode(customerTypeCode);
		if(!StringUtil.isNull(custTypeDO)){
			custTypeTO = new CustomerTypeTO();
			custTypeTO = (CustomerTypeTO)CGObjectConverter.createToFromDomain(custTypeDO, custTypeTO);
		}
		return custTypeTO;
	}

	@Override
	public RateIndustryCategoryTO getIndustryCategoryByCode(String indCatCode)
			throws CGBusinessException, CGSystemException {
		RateIndustryCategoryTO indCatTO = null;
		RateIndustryCategoryDO indCatDO = null;
		
		indCatDO = rateUniversalDAO.getIndustryCategoryByCode(indCatCode);
		if(!StringUtil.isNull(indCatDO)){
			indCatTO = new RateIndustryCategoryTO();
			indCatTO = (RateIndustryCategoryTO)CGObjectConverter.createToFromDomain(indCatDO, indCatTO);
		}
		return indCatTO;
	}

	@Override
	public DepartmentTO getDepartmentByCode(String deptCode)
			throws CGBusinessException, CGSystemException {
		
		return organizationCommonService.getDepartmentByCode(deptCode);
	}

	@Override
	public List<WeightSlabDO> getWeightSlabByWtSlabCate(String wtSlabCate)
			throws CGBusinessException, CGSystemException {
		return rateUniversalDAO.getWeightSlabByWtSlabCate(wtSlabCate);
	}
	
	@Override
	public List<WeightSlabDO> getWeightSlabByWtSlabCateAndEndWt(
			String wtSlabCate, Double endWeight) throws CGBusinessException,
			CGSystemException {
		return rateUniversalDAO.getWeightSlabByWtSlabCateAndEndWt(wtSlabCate,
				endWeight);
	}

	@Override
	public List<OfficeTO> getAllOfficesByCityAndOfcTypeCode(
			Integer cityId, String ofcTypeCode)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getAllOfficesByCityAndOfficeType(cityId, ofcTypeCode);
	}
	
}
