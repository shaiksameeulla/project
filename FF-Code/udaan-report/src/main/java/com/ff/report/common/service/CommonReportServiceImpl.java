package com.ff.report.common.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.ConsignmentCustomerTO;
import com.ff.business.CustomerTO;
import com.ff.business.CustomerTypeTO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.RateRevisionDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.RegionDO;
import com.ff.domain.mec.LiabilityDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.ratemanagement.masters.RateIndustryTypeDO;
import com.ff.domain.ratemanagement.masters.RateProductCategoryDO;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.domain.report.wrapper.ClientGainedWrapperDO;
import com.ff.domain.routeserviced.ServiceByTypeDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.stockmanagement.masters.ItemTypeDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.domain.transport.TransportModeDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.LoadLotTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.report.CategoryReportAliasTO;
import com.ff.report.LcCodReportAliasTO;
import com.ff.report.PriorityTypeTO;
import com.ff.report.ReportTypeTO;
import com.ff.report.common.dao.CommonReportDAO;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.report.wrapper.ClientGainedWrapperTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.mec.LiabilityTO;
import com.ff.to.ratemanagement.masters.RateProductCategoryTO;
import com.ff.to.ratemanagement.masters.SectorTO;
import com.ff.to.ratemanagement.masters.VobSlabTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateIndustryTypeTO;
import com.ff.to.stockmanagement.masters.ItemTypeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.universe.constant.UniversalErrorConstants;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.global.service.GlobalUniversalService;
import com.ff.universe.mec.service.MECUniversalService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;
import com.ff.universe.stockmanagement.service.StockUniversalService;

/**
 * @author khassan
 * 
 */
public class CommonReportServiceImpl implements CommonReportService {
	/** The geography common service. */
	private transient GeographyCommonService geographyCommonService;
	/** The service offering common service. */
	private transient ServiceOfferingCommonService serviceOfferingCommonService;
	private transient GlobalUniversalService globalUniversalService;

	private transient MECUniversalService mecUniversalService;
	
	/** The stock universal service. */
	private StockUniversalService stockUniversalService;

	
	
	
	/**
	 * @return the stockUniversalService
	 */
	public StockUniversalService getStockUniversalService() {
		return stockUniversalService;
	}

	/**
	 * @param stockUniversalService the stockUniversalService to set
	 */
	public void setStockUniversalService(StockUniversalService stockUniversalService) {
		this.stockUniversalService = stockUniversalService;
	}

	/**
	 * @param globalUniversalService
	 *            the globalUniversalService to set
	 */
	public void setGlobalUniversalService(
			GlobalUniversalService globalUniversalService) {
		this.globalUniversalService = globalUniversalService;
	}

	/**
	 * @param mecUniversalService
	 *            the mecUniversalService to set
	 */
	public void setMecUniversalService(MECUniversalService mecUniversalService) {
		this.mecUniversalService = mecUniversalService;
	}

	private final static Logger LOGGER = LoggerFactory
			.getLogger(CommonReportServiceImpl.class);
	CommonReportDAO commonReportDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.report.common.service.CommonReportService#getOfficesByCityIdForReport
	 * (java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<OfficeTO> getOfficesByCityIdForReport(Integer cityId)
			throws CGSystemException, CGBusinessException {
		List<OfficeTO> officeTOList = null;
		try {
			List<OfficeDO> officeDOList = commonReportDAO
					.getOfficesByCityIdForReport(cityId);

			officeTOList = (List<OfficeTO>) CGObjectConverter
					.createTOListFromDomainList(officeDOList, OfficeTO.class);
		} catch (Exception e) {
			LOGGER.error("ERROR:: CommonReportServiceImpl.class:: getOfficesByCityIdForReport method "
					+ e);
			throw new CGSystemException(e);
		}

		return officeTOList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.billing.service.BillingCommonService#getRegions()
	 */
	@Override
	public List<RegionTO> getRegions() throws CGBusinessException,
			CGSystemException {
		return geographyCommonService.getAllRegions();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.billing.service.BillingCommonService#getProducts()
	 */
	@Override
	public List<ProductTO> getProducts() throws CGBusinessException,
			CGSystemException {
		return serviceOfferingCommonService.getAllProducts();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.admin.billing.service.BillingCommonService#getCitiesByRegionId
	 * (java.lang.Integer)
	 */
	@Override
	public List<CityTO> getCitiesByRegionId(final Integer regionId)
			throws CGBusinessException, CGSystemException {
		final CityTO cityTO = new CityTO();
		cityTO.setRegion(regionId);
		return geographyCommonService.getCitiesByCity(cityTO);
	}

	/**
	 * @param geographyCommonService
	 *            the geographyCommonService to set
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	/**
	 * @param serviceOfferingCommonService
	 *            the serviceOfferingCommonService to set
	 */
	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}

	/**
	 * @param commonReportDAO
	 *            the commonReportDAO to set
	 */
	public void setCommonReportDAO(CommonReportDAO commonReportDAO) {
		this.commonReportDAO = commonReportDAO;
	}

	public List<OfficeTO> getAllHubsByCityID(Integer cityId)
			throws CGBusinessException, CGSystemException {
		List<OfficeTO> officeTOs = null;
		List<OfficeDO> officeDOs = commonReportDAO.getAllHubsByCityID(cityId);
		if (StringUtil.isEmptyList(officeDOs)) {
			throw new CGBusinessException(
					UniversalErrorConstants.NO_OFFICE_DETAILS_FOUND);
		}
		if (!StringUtil.isEmptyList(officeDOs)) {
			officeTOs = new ArrayList<>(officeDOs.size());
			for (OfficeDO officeDO : officeDOs) {
				OfficeTO officeTO = new OfficeTO();
				officeTO = (OfficeTO) CGObjectConverter.createToFromDomain(
						officeDO, officeTO);

				OfficeTypeTO officeTypeTO = new OfficeTypeTO();
				officeTypeTO = (OfficeTypeTO) CGObjectConverter
						.createToFromDomain(officeDO.getOfficeTypeDO(),
								officeTypeTO);
				officeTO.setOfficeTypeTO(officeTypeTO);

				officeTOs.add(officeTO);
			}
		}
		return officeTOs;
	}

	@Override
	public List<ItemTypeTO> getItemTypeListForUsed() throws CGSystemException,
			CGBusinessException {
		List<ItemTypeDO> itemTypeDoList = null;
		List<ItemTypeTO> itemTypeToList = null;

		itemTypeDoList = commonReportDAO.getItemTypeListForUsed();
		if (!StringUtil.isEmptyList(itemTypeDoList)) {
			itemTypeToList = new ArrayList<>(itemTypeDoList.size());
			try {
				for (ItemTypeDO itemTypeDO : itemTypeDoList) {
					ItemTypeTO itemTypeTO = new ItemTypeTO();
					PropertyUtils.copyProperties(itemTypeTO, itemTypeDO);
					itemTypeToList.add(itemTypeTO);
				}
			} catch (IllegalAccessException e) {
				LOGGER.error("StockCommonServiceImpl::getItemTypeList---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			} catch (InvocationTargetException e) {
				LOGGER.error("StockCommonServiceImpl::getItemTypeList---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			} catch (NoSuchMethodException e) {
				LOGGER.error("StockCommonServiceImpl::getItemTypeList---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			}

		} else {
			LOGGER.warn("StockCommonServiceImpl::getItemTypeList---Details doesnot exist");
		}
		return itemTypeToList;
	}

	@Override
	public List<CustomerTO> getCustomersByOfficeId(Integer officeId)
			throws CGSystemException {
		List<CustomerDO> customerDOs = commonReportDAO
				.getCustomersByOfficeId(officeId);
		List<CustomerTO> customerTOs = customerDO2TOConverter(customerDOs);
		return customerTOs;
	}
	
	public List<CustomerTO> getCustomersByOfficeIdForStockReport(Integer officeId,String offcType)
			throws CGSystemException, CGBusinessException {
		//List<CustomerDO> customerDOs = commonReportDAO.getCustomersByOfficeId(officeId);
		
		List<CustomerTO> customerTOs = getAllCustomerForStockReport(officeId,offcType);
		//List<CustomerTO> customerTOs = customerDO2TOConverter(customerDOs);
		return customerTOs;
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
						CustomerTypeTO customerTypeTO = new CustomerTypeTO();
						PropertyUtils.copyProperties(customerTypeTO,
								frDO.getCustomerType());
						to.setCustomerTypeTO(customerTypeTO);
					}
					customerTOList.add(to);
				}
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return customerTOList;
	}

	public List<EmployeeTO> getEmployeesByOfficeIdForReport(Integer cityId)
			throws CGSystemException, CGBusinessException {
		List<EmployeeTO> officeTOList = null;
		try {
			List<EmployeeDO> officeDOList = commonReportDAO
					.getEmployeesByOfficeIdForReport(cityId);

			officeTOList = (List<EmployeeTO>) CGObjectConverter
					.createTOListFromDomainList(officeDOList, EmployeeTO.class);
		} catch (Exception e) {
			LOGGER.error("ERROR:: CommonReportServiceImpl.class:: getOfficesByCityIdForReport method "
					+ e);
			throw new CGSystemException(e);
		}

		return officeTOList;
	}

	public List<EmployeeTO> getSalesPersonsTitlesList(String DepartmentType)
			throws CGSystemException {
		List<EmployeeDO> itemTypeDoList = null;
		List<EmployeeTO> itemTypeToList = null;
		itemTypeDoList = commonReportDAO
				.getSalesPersonsTitlesList(DepartmentType);
		if (!StringUtil.isEmptyList(itemTypeDoList)) {
			try {
				itemTypeToList = (List<EmployeeTO>) CGObjectConverter
						.createTOListFromDomainList(itemTypeDoList,
								EmployeeTO.class);
			} catch (CGBusinessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			/*
			 * itemTypeToList = new ArrayList<>(itemTypeDoList.size());
			 * 
			 * for(EmployeeDO itemTypeDO:itemTypeDoList){ EmployeeTO itemTypeTO=
			 * new EmployeeTO(); PropertyUtils.copyProperties(itemTypeTO,
			 * itemTypeDO); itemTypeToList.add(itemTypeTO); }
			 */
		} else {
			LOGGER.warn("StockCommonServiceImpl::getItemTypeList---Details doesnot exist");
		}
		/*
		 * List<EmployeeTO> nwToList=new ArrayList<EmployeeTO>(); CityTO
		 * cityTO=null; EmployeeTO n=new EmployeeTO(); for (EmployeeTO
		 * e:itemTypeToList){ try {
		 * cityTO=geographyCommonService.getCityByOfficeId(e.getOfficeId());
		 * n=e; n.setCityId(cityTO.getCityId());
		 * n.setRegionId(cityTO.getRegion()); nwToList.add(n); } catch
		 * (CGBusinessException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); }
		 * 
		 * } return nwToList;
		 */
		return itemTypeToList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CGBaseTO> getSalesPersonsForClientGained(Integer officeId,
			Integer cityId, Integer regionId) throws CGBusinessException,
			CGSystemException {
		List<ClientGainedWrapperDO> empDOs = null;
		List<CGBaseTO> empTOs = null;
		empDOs = commonReportDAO.getSalesPersonsForClientGained(officeId,
				cityId, regionId);
		if (!StringUtil.isEmptyList(empDOs)) {
			try {
				empTOs = (List<CGBaseTO>) CGObjectConverter
						.createTOListFromDomainList(empDOs, ClientGainedWrapperTO.class);
			} catch (Exception e1) {
				LOGGER.error(
						"Exception occurs in StockCommonServiceImpl :: getItemTypeList() .. Exception ::",
						e1);
				e1.printStackTrace();
				throw new CGBusinessException(e1);
			}
		} else {
			LOGGER.info("StockCommonServiceImpl::getItemTypeList---Details doesnot exist");
		}
		return empTOs;
	}
	
	
	
	public List<RateIndustryTypeTO> getNatureOfBusinesses()
			throws CGSystemException {
		List<RateIndustryTypeDO> itemTypeDoList = null;
		List<RateIndustryTypeTO> itemTypeToList = null;

		itemTypeDoList = commonReportDAO.getNatureOfBusinesses();
		if (!StringUtil.isEmptyList(itemTypeDoList)) {
			itemTypeToList = new ArrayList<>(itemTypeDoList.size());
			try {
				for (RateIndustryTypeDO itemTypeDO : itemTypeDoList) {
					RateIndustryTypeTO itemTypeTO = new RateIndustryTypeTO();
					PropertyUtils.copyProperties(itemTypeTO, itemTypeDO);
					itemTypeToList.add(itemTypeTO);
				}
			} catch (IllegalAccessException e) {
				LOGGER.error("StockCommonServiceImpl::getItemTypeList---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			} catch (InvocationTargetException e) {
				LOGGER.error("StockCommonServiceImpl::getItemTypeList---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			} catch (NoSuchMethodException e) {
				LOGGER.error("StockCommonServiceImpl::getItemTypeList---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			}

		} else {
			LOGGER.warn("StockCommonServiceImpl::getItemTypeList---Details doesnot exist");
		}
		return itemTypeToList;
	}

	@Override
	public List<RateProductCategoryTO> getRateProdcuts()
			throws CGBusinessException, CGSystemException {
		List<RateProductCategoryTO> categoryTOs = commonReportDAO
				.getRateProdcuts();
		return categoryTOs;
	}

	@Override
	public List<VobSlabTO> getSlabList(Integer productId)
			throws CGBusinessException, CGSystemException {
		List<VobSlabTO> slabTOs = commonReportDAO.getSlabList(productId);
		return slabTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityTO> getCityByCityID(Integer cityId)
			throws CGBusinessException, CGSystemException {

		List<CityTO> cityTOList = null;
		try {
			List<CityDO> cityDOList = commonReportDAO.getCityByCityID(cityId);

			cityTOList = (List<CityTO>) CGObjectConverter
					.createTOListFromDomainList(cityDOList, CityTO.class); //

		} catch (Exception e) {
			LOGGER.error("ERROR:: CommonReportServiceImpl.class:: getOfficesByCityIdForReport method "
					+ e);
			throw new CGSystemException(e);
		}

		return cityTOList;
	}

	// 28-02-2014
	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeTO> getOfficecByCityIDAndUserIDForReport(Integer userId,
			Integer cityId) throws CGBusinessException, CGSystemException {

		List<OfficeTO> officeTOList = null;
		try {
			List<OfficeDO> officeDOList = commonReportDAO
					.getOfficecByCityIDAndUserIDForReport(userId, cityId);

			officeTOList = (List<OfficeTO>) CGObjectConverter
					.createTOListFromDomainList(officeDOList, OfficeTO.class);
		} catch (Exception e) {
			LOGGER.error("ERROR:: CommonReportServiceImpl.class:: getOfficesByCityIdForReport method "
					+ e);
			throw new CGSystemException(e);
		}

		return officeTOList;
	}

	public Long getworkingDaysFromHoliday(Integer regionId, String fromDate)
			throws CGBusinessException, CGSystemException {
		Long totHoliday = 0L;
		if (!StringUtil.isEmptyInteger(regionId)
				&& !StringUtil.isNull(fromDate)) {
			totHoliday = commonReportDAO.getworkingDaysFromHoliday(regionId,
					fromDate);

		}
		return totHoliday;
	}

	public List<SectorTO> getOriginSectors() throws CGSystemException {
		List<SectorDO> itemTypeDoList = null;
		List<SectorTO> itemTypeToList = null;

		itemTypeDoList = commonReportDAO.getOriginSectors();
		if (!StringUtil.isEmptyList(itemTypeDoList)) {
			itemTypeToList = new ArrayList<>(itemTypeDoList.size());
			try {
				for (SectorDO itemTypeDO : itemTypeDoList) {
					SectorTO itemTypeTO = new SectorTO();
					PropertyUtils.copyProperties(itemTypeTO, itemTypeDO);
					itemTypeToList.add(itemTypeTO);
				}
			} catch (IllegalAccessException e) {
				LOGGER.error("CommonReportServiceImpl::getOriginSectorByRegionId---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			} catch (InvocationTargetException e) {
				LOGGER.error("CommonReportServiceImpl::getOriginSectorByRegionId---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			} catch (NoSuchMethodException e) {
				LOGGER.error("CommonReportServiceImpl::getOriginSectorByRegionId---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			}

		} else {
			LOGGER.warn("CommonReportServiceImpl::getOriginSectorByRegionId---Details doesnot exist");
		}
		return itemTypeToList;
	}

	public List<RateProductCategoryTO> getProductsByCustomer(Integer customerId)
			throws CGSystemException {
		List<RateProductCategoryDO> itemTypeDoList = null;
		List<RateProductCategoryTO> itemTypeToList = null;

		itemTypeDoList = commonReportDAO.getProductsByCustomer(customerId);
		if (!StringUtil.isEmptyList(itemTypeDoList)) {
			itemTypeToList = new ArrayList<>(itemTypeDoList.size());
			try {
				for (RateProductCategoryDO itemTypeDO : itemTypeDoList) {
					RateProductCategoryTO itemTypeTO = new RateProductCategoryTO();
					PropertyUtils.copyProperties(itemTypeTO, itemTypeDO);
					itemTypeToList.add(itemTypeTO);
				}
			} catch (IllegalAccessException e) {
				LOGGER.error("CommonReportServiceImpl::getProductsByCustomer---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			} catch (InvocationTargetException e) {
				LOGGER.error("CommonReportServiceImpl::getProductsByCustomer---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			} catch (NoSuchMethodException e) {
				LOGGER.error("CommonReportServiceImpl::getProductsByCustomer---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			}

		} else {
			LOGGER.warn("CommonReportServiceImpl::getProductsByCustomer---Details doesnot exist");
		}
		return itemTypeToList;
	}

	public List<CityTO> getCitiesByRegionIds(String regionIds)
			throws CGSystemException, CGBusinessException {
		List<CityDO> cityDoList = null;
		List<CityTO> cityToList = null;

		cityDoList = commonReportDAO.getCitiesByRegionIds(regionIds);
		if (!StringUtil.isEmptyList(cityDoList)) {
			cityToList = new ArrayList<>(cityDoList.size());
			try {
				for (CityDO itemTypeDO : cityDoList) {
					CityTO itemTypeTO = new CityTO();
					PropertyUtils.copyProperties(itemTypeTO, itemTypeDO);
					cityToList.add(itemTypeTO);
				}
			} catch (IllegalAccessException e) {
				LOGGER.error("CommonReportServiceImpl::getCitiesByRegionIds---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			} catch (InvocationTargetException e) {
				LOGGER.error("CommonReportServiceImpl::getCitiesByRegionIds---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			} catch (NoSuchMethodException e) {
				LOGGER.error("CommonReportServiceImpl::getCitiesByRegionIds---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			}

		} else {
			LOGGER.warn("CommonReportServiceImpl::getCitiesByRegionIds---Details doesnot exist");
		}
		return cityToList;
	}

	public List<OfficeTO> getOfficesByCityIds(String cityIds)
			throws CGSystemException, CGBusinessException {
		List<OfficeDO> officeDoList = null;
		List<OfficeTO> officeToList = null;

		officeDoList = commonReportDAO.getOfficesByCityIds(cityIds);
		if (!StringUtil.isEmptyList(officeDoList)) {
			officeToList = new ArrayList<>(officeDoList.size());
			try {
				for (OfficeDO officeDO : officeDoList) {
					OfficeTO officeTO = new OfficeTO();
					PropertyUtils.copyProperties(officeTO, officeDO);
					officeToList.add(officeTO);
				}
			} catch (IllegalAccessException e) {
				LOGGER.error("CommonReportServiceImpl::getOfficesByCityIds---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			} catch (InvocationTargetException e) {
				LOGGER.error("CommonReportServiceImpl::getOfficesByCityIds---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			} catch (NoSuchMethodException e) {
				LOGGER.error("CommonReportServiceImpl::getOfficesByCityIds---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			}

		} else {
			LOGGER.warn("CommonReportServiceImpl::getOfficesByCityIds---Details doesnot exist");
		}
		return officeToList;
	}

	public List getMonthList() throws CGBusinessException, CGSystemException {
		List compList = null;
		compList = commonReportDAO.getMonthList();
		return compList;
	}

	@Override
	public List<OfficeTO> getOfficeForBOUser(Integer cityId, Integer userID)
			throws CGSystemException, CGBusinessException {
		List<OfficeDO> officeDoList = null;
		List<OfficeTO> officeToList = null;

		officeDoList = commonReportDAO.getOfficeForBOUser(cityId, userID);
		try {
			officeToList = (List<OfficeTO>) CGObjectConverter
					.createTOListFromDomainList(officeDoList, OfficeTO.class);
		} catch (Exception e) {
			LOGGER.error("ERROR:: CommonReportServiceImpl.class:: getOfficeForBOUser method "
					+ e);
			throw new CGSystemException(e);
		}

		return officeToList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegionTO> getAllRegionForBoUser(Integer userId, Integer officeId)
			throws CGSystemException, CGBusinessException {
		List<RegionDO> regionDoList = null;
		List<RegionTO> regionToList = null;

		regionDoList = commonReportDAO.getAllRegionForBoUser(userId, officeId);
		try {
			regionToList = (List<RegionTO>) CGObjectConverter
					.createTOListFromDomainList(regionDoList, RegionTO.class);
		} catch (Exception e) {
			LOGGER.error("ERROR:: CommonReportServiceImpl.class:: getAllRegionForBoUser method "
					+ e);
			throw new CGSystemException(e);
		}

		return regionToList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityTO> getCityByRegionForBranchUser(Integer userId,
			Integer regionId) throws CGSystemException, CGBusinessException {
		// TODO Auto-generated method stub
		List<CityTO> cityToList = null;
		List<CityDO> cityDoList = null;

		try {
			cityDoList = commonReportDAO.getCityByRegionForBranchUser(userId,
					regionId);

			cityToList = (List<CityTO>) CGObjectConverter
					.createTOListFromDomainList(cityDoList, CityTO.class);
		} catch (Exception e) {
			LOGGER.error("ERROR:: CommonReportServiceImpl.class:: getCityByRegionForBranchUser method "
					+ e);
			throw new CGSystemException(e);
		}

		return cityToList;
	}

	@Override
	public List<RegionTO> getAllRegionByUserType(Integer userId,
			Integer officeId, String officeType) throws CGSystemException,
			CGBusinessException {
		List<RegionTO> regionToList = null;
		List<RegionDO> regionDoList = null;

		try {
			regionDoList = commonReportDAO.getAllRegionByUserType(userId,
					officeId, officeType);
			regionToList = (List<RegionTO>) CGObjectConverter
					.createTOListFromDomainList(regionDoList, RegionTO.class);
		} catch (Exception e) {
			LOGGER.error("ERROR:: CommonReportServiceImpl.class:: getAllRegionForHubUser method "
					+ e);
			throw new CGSystemException(e);
		}

		return regionToList;
	}

	@Override
	public List<CityTO> getCitiesByRegionForHubUser(Integer userId,
			Integer officeId, Integer regionID) throws CGSystemException,
			CGBusinessException {

		List<CityTO> cityToList = null;
		List<CityDO> cityDoList = null;

		try {
			cityDoList = commonReportDAO.getCitiesByRegionForHubUser(userId,
					officeId, regionID);
			cityToList = (List<CityTO>) CGObjectConverter
					.createTOListFromDomainList(cityDoList, CityTO.class);
		} catch (Exception e) {
			LOGGER.error("ERROR:: CommonReportServiceImpl.class:: getCitiesByRegionForHubUser method "
					+ e);
			throw new CGSystemException(e);
		}

		return cityToList;
	}

	@Override
	public List<OfficeTO> getOfficeByUserType(String officeType,
			Integer userId, Integer officeId, Integer cityId)
			throws CGSystemException, CGBusinessException {

		List<OfficeTO> officeToList = null;
		List<OfficeDO> officeDoList = null;

		try {
			officeDoList = commonReportDAO.getOfficeByUserType(officeType,
					userId, officeId, cityId);
			officeToList = (List<OfficeTO>) CGObjectConverter
					.createTOListFromDomainList(officeDoList, OfficeTO.class);
		} catch (Exception e) {
			LOGGER.error("ERROR:: CommonReportServiceImpl.class:: getCitiesByRegionForHubUser method "
					+ e);
			throw new CGSystemException(e);
		}
		return officeToList;
	}

	@Override
	public List<CityTO> getCitiesByRegionForRhoUser(Integer userId,
			Integer officeId, Integer regionID) throws CGSystemException,
			CGBusinessException {
		List<CityTO> citryToList = null;
		List<CityDO> cityDoList = null;

		try {
			cityDoList = commonReportDAO.getCitiesByRegionForRhoUser(userId,
					officeId, regionID);
			citryToList = (List<CityTO>) CGObjectConverter
					.createTOListFromDomainList(cityDoList, CityTO.class);
		} catch (Exception e) {
			LOGGER.error("ERROR:: CommonReportServiceImpl.class:: getCitiesByRegionForHubUser method "
					+ e);
			throw new CGSystemException(e);
		}

		return citryToList;
	}

	public List<StockStandardTypeTO> getStandardTypeForLcCod(String typeName)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("CommonReportServiceImpl::getStandardTypeForLcCod()::START");
		List<StockStandardTypeTO> stockTypeList = null;
		List<StockStandardTypeDO> stockTypeDOList = commonReportDAO
				.getStandardTypeForLcCod(typeName);
		if (StringUtil.isEmptyList(stockTypeDOList)) {
			throw new CGBusinessException(CommonReportConstant.NO_DATA_FOUND);
		} else {
			stockTypeList = (List<StockStandardTypeTO>) CGObjectConverter
					.createTOListFromDomainList(stockTypeDOList,
							StockStandardTypeTO.class);
		}
		LOGGER.trace("CommonReportServiceImpl::getStandardTypeForLcCod()::END");
		return stockTypeList;
	}

	public List<LcCodReportAliasTO> getCustomerByRegionAndProduct(
			List<Integer> regionId, List<String> prodSeries)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("CommonReportServiceImpl::getCustomerByRegionAndProduct()::START");
		List<LcCodReportAliasTO> customerList = commonReportDAO
				.getCustomerByRegionAndProduct(regionId, prodSeries);
		if (StringUtil.isEmptyList(customerList)) {
			throw new CGBusinessException(
					CommonReportConstant.NO_CUSTOMER_FOUND);
		}
		Collections.sort(customerList);
		LOGGER.trace("CommonReportServiceImpl::getCustomerByRegionAndProduct()::END");

		return customerList;
	}

	@Override
	public List<OfficeTO> getOfficeForMultipleCitiesForBOUser(Integer[] cityId,
			Integer userID) throws CGSystemException, CGBusinessException {
		List<OfficeDO> officeDoList = null;
		List<OfficeTO> officeToList = null;

		officeDoList = commonReportDAO.getOfficeForMultipleCitiesForBOUser(
				cityId, userID);
		try {
			officeToList = (List<OfficeTO>) CGObjectConverter
					.createTOListFromDomainList(officeDoList, OfficeTO.class);
		} catch (Exception e) {
			LOGGER.error("ERROR:: CommonReportServiceImpl.class:: getOfficeForBOUser method "
					+ e);
			throw new CGSystemException(e);
		}

		return officeToList;
	}

	@Override
	public List<OfficeTO> getOfficeForMultipleCitiesUserType(String officeType,
			Integer userId, Integer officeId, Integer[] cityId)
			throws CGSystemException, CGBusinessException {

		List<OfficeTO> officeToList = null;
		List<OfficeDO> officeDoList = null;

		try {
			officeDoList = commonReportDAO.getOfficeForMultipleCitiesUserType(
					officeType, userId, officeId, cityId);
			officeToList = (List<OfficeTO>) CGObjectConverter
					.createTOListFromDomainList(officeDoList, OfficeTO.class);
		} catch (Exception e) {
			LOGGER.error("ERROR:: CommonReportServiceImpl.class:: getCitiesByRegionForHubUser method "
					+ e);
			throw new CGSystemException(e);
		}
		return officeToList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeTO> getOfficecByMultipleCityIDAndUserIDForReport(
			Integer[] cityId) throws CGBusinessException, CGSystemException {

		List<OfficeTO> officeTOList = null;
		try {
			List<OfficeDO> officeDOList = commonReportDAO
					.getOfficesByMultipleCityIdForReport(cityId);

			officeTOList = (List<OfficeTO>) CGObjectConverter
					.createTOListFromDomainList(officeDOList, OfficeTO.class);
		} catch (Exception e) {
			LOGGER.error("ERROR:: CommonReportServiceImpl.class:: getOfficesByCityIdForReport method "
					+ e);
			throw new CGSystemException(e);
		}

		return officeTOList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityTO> getCityByMultipleRegionForBranchUser(Integer userId,
			Integer[] regionId) throws CGSystemException, CGBusinessException {
		// TODO Auto-generated method stub
		List<CityTO> cityToList = null;
		List<CityDO> cityDoList = null;

		try {
			cityDoList = commonReportDAO.getCityByMultipleRegionForBranchUser(
					userId, regionId);

			cityToList = (List<CityTO>) CGObjectConverter
					.createTOListFromDomainList(cityDoList, CityTO.class);
		} catch (Exception e) {
			LOGGER.error("ERROR:: CommonReportServiceImpl.class:: getCityByRegionForBranchUser method "
					+ e);
			throw new CGSystemException(e);
		}

		return cityToList;
	}

	@Override
	public List<CityTO> getCitiesByMultipleRegionForHubUser(Integer userId,
			Integer officeId, Integer[] regionID) throws CGSystemException,
			CGBusinessException {

		List<CityTO> cityToList = null;
		List<CityDO> cityDoList = null;

		try {
			cityDoList = commonReportDAO.getCitiesByMultipleRegionForHubUser(
					userId, officeId, regionID);
			cityToList = (List<CityTO>) CGObjectConverter
					.createTOListFromDomainList(cityDoList, CityTO.class);
		} catch (Exception e) {
			LOGGER.error("ERROR:: CommonReportServiceImpl.class:: getCitiesByRegionForHubUser method "
					+ e);
			throw new CGSystemException(e);
		}

		return cityToList;
	}

	@Override
	public List<CityTO> getCitiesByMultipleRegionForRhoUser(Integer userId,
			Integer officeId, Integer[] regionID) throws CGSystemException,
			CGBusinessException {
		List<CityTO> citryToList = null;
		List<CityDO> cityDoList = null;

		try {
			cityDoList = commonReportDAO.getCitiesByMultipleRegionForRhoUser(
					userId, officeId, regionID);
			citryToList = (List<CityTO>) CGObjectConverter
					.createTOListFromDomainList(cityDoList, CityTO.class);
		} catch (Exception e) {
			LOGGER.error("ERROR:: CommonReportServiceImpl.class:: getCitiesByRegionForHubUser method "
					+ e);
			throw new CGSystemException(e);
		}

		return citryToList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.admin.billing.service.BillingCommonService#getCitiesByRegionId
	 * (java.lang.Integer)
	 */
	@Override
	public List<CityTO> getCitiesByMultipleRegionId(final Integer[] regionId)
			throws CGBusinessException, CGSystemException {
		List<CityDO> cityDoList = null;
		List<CityTO> cityToList = null;

		cityDoList = commonReportDAO.getCitiesByMultipleRegionIds(regionId);
		if (!StringUtil.isEmptyList(cityDoList)) {
			cityToList = new ArrayList<>(cityDoList.size());
			try {
				for (CityDO itemTypeDO : cityDoList) {
					CityTO itemTypeTO = new CityTO();
					PropertyUtils.copyProperties(itemTypeTO, itemTypeDO);
					cityToList.add(itemTypeTO);
				}
			} catch (IllegalAccessException e) {
				LOGGER.error("CommonReportServiceImpl::getCitiesByRegionIds---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			} catch (InvocationTargetException e) {
				LOGGER.error("CommonReportServiceImpl::getCitiesByRegionIds---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			} catch (NoSuchMethodException e) {
				LOGGER.error("CommonReportServiceImpl::getCitiesByRegionIds---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			}

		} else {
			LOGGER.warn("CommonReportServiceImpl::getCitiesByRegionIds---Details doesnot exist");
		}
		return cityToList;
	}

	public List<ConsignmentTypeTO> getConsignmentType()
			throws CGBusinessException, CGSystemException {
		return serviceOfferingCommonService.getConsignmentType();
	}

	/**
	 * Gets the category.
	 * 
	 * @return the category
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<CategoryReportAliasTO> getCategory()
			throws CGBusinessException, CGSystemException {
		return commonReportDAO.getCategory();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.report.common.service.CommonReportService#getLoad()
	 */
	public List<LoadLotTO> getLoad() throws CGBusinessException,
			CGSystemException {
		return commonReportDAO.getLoad();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeTO> getReportingOfficesForRhoUser(Integer userId,
			Integer cityId, Integer officeId) throws CGBusinessException,
			CGSystemException {
		List<OfficeTO> officeToList = null;
		List<OfficeDO> officeDoList = null;
		try {
			// officeDoList = commonReportDAO.getOfficeByUserType(officeType,
			// userId, officeId, cityId);
			officeDoList = commonReportDAO.getReportingOfficesForRhoUser(
					userId, cityId, officeId);

			officeToList = (List<OfficeTO>) CGObjectConverter
					.createTOListFromDomainList(officeDoList, OfficeTO.class);
		} catch (Exception e) {
			LOGGER.error("ERROR:: CommonReportServiceImpl.class:: getCitiesByRegionForHubUser method "
					+ e);
			throw new CGSystemException(e);
		}
		return officeToList;
	}

	@Override
	public List<CustomerTO> getCustomerByBranchAndRateProductCategory(
			Integer[] officeIds, Integer rateProduct)
			throws CGBusinessException, CGSystemException {
		List<CustomerDO> customerDOs = commonReportDAO
				.getCustomerByBranchAndRateProductCategory(officeIds,
						rateProduct);
		List<CustomerTO> customerTOs = customerDO2TOConverter(customerDOs);
		return customerTOs;
	}

	@Override
	public List<CustomerTO> getCustomersByOfficeIds(Integer[] officeId)
			throws CGBusinessException, CGSystemException {

		List<CustomerDO> customerDOs = commonReportDAO
				.getCustomersByOfficeIds(officeId);
		List<CustomerTO> customerTOs = customerDO2TOConverter(customerDOs);
		return customerTOs;

	}

	@Override
	public List<CustomerTO> getCustomersByOffIds(Integer[] officeId,
			Integer regionId) throws CGBusinessException, CGSystemException {
		List<CustomerDO> customerDOs = commonReportDAO.getCustomersByOffIds(
				officeId, regionId);
		List<CustomerTO> customerTOs = customerDO2TOConverter(customerDOs);
		return customerTOs;
	}
	
	@Override
	public List<CustomerTO> getCustomerByStationAndProduct(
			Integer[] productIds, Integer stationId)
			throws CGBusinessException, CGSystemException {
		List<CustomerDO> customerDOs = commonReportDAO
				.getCustomerByStationAndProduct(productIds, stationId);
		List<CustomerTO> customerTOs = customerDO2TOConverter(customerDOs);
		return customerTOs;
	}

	public List<CustomerTO> getCustomerByStation(Integer stationId)
			throws CGBusinessException, CGSystemException {
		List<CustomerDO> customerDOs = commonReportDAO
				.getCustomerByStation(stationId);
		List<CustomerTO> customerTOs = customerDO2TOConverter(customerDOs);
		return customerTOs;
	}

	@Override
	public List<ProductTO> getProductByCustomers(Integer[] customerIds)
			throws CGBusinessException, CGSystemException {
		List<ProductDO> productDOs = commonReportDAO
				.getProductByCustomers(customerIds);
		List<ProductTO> productTOs = (List<ProductTO>) CGObjectConverter
				.createTOListFromDomainList(productDOs, ProductTO.class);
		return productTOs;
	}

	@Override
	public List<StockStandardTypeTO> getStandardTypesByTypeName(String typeName)
			throws CGSystemException, CGBusinessException {
		return globalUniversalService.getStandardTypesByTypeName(typeName);
	}

	@Override
	public List<SectorTO> getSectors() throws CGSystemException,
			CGBusinessException {
		return geographyCommonService.getSectors();
	}

	@Override
	public List<CustomerTO> getCustomerByRegion(Integer regionId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("CommonReportServiceImpl::getCustomerByRegion()::START");
		List<CustomerDO> customerDOList = mecUniversalService
				.getLiabilityCustomers(regionId);
		@SuppressWarnings("unchecked")
		List<CustomerTO> customerTOList = (List<CustomerTO>) CGObjectConverter
				.createTOListFromDomainList(customerDOList, CustomerTO.class);
		LOGGER.trace("CommonReportServiceImpl::getCustomerByRegion()::END");
		return customerTOList;
	}

	@Override
	public List<String> getchequeNumbers(Integer customerId,Date fromDate,Date toDate)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("CommonReportServiceImpl::getchequeNumbers()::START");
		List<String> list = new ArrayList<String>();
		List<LiabilityDO> liabilityList = commonReportDAO
				.getchequeNumbers(customerId,fromDate,toDate);
		if (liabilityList != null) {
			for (LiabilityDO liabilityDO : liabilityList) {
				list.add(liabilityDO.getChqNo());
			}
		}
		LOGGER.trace("CommonReportServiceImpl::getchequeNumbers()::END");
		return list;
	}
	
	
	
	@Override
	public List<String> getchequeNumbersByRegion(Integer regionId,Date fromDate,Date toDate)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("CommonReportServiceImpl::getchequeNumbers()::START");
		List<String> list = new ArrayList<String>();
		List<LiabilityTO> liabilityList = commonReportDAO
				.getchequeNumbersByRegion(regionId,fromDate,toDate);
		if (liabilityList != null) {
			for (LiabilityTO liabilityDO : liabilityList) {
				list.add(liabilityDO.getChqNo());
			}
		}
		
		
		LOGGER.trace("CommonReportServiceImpl::getchequeNumbers()::END");
		return list;
	}

	/**
	 * This method gets the customers on the basis of below criteria,
	 * officeIds - 	customers with type CR/CC/LC/CD/GV/FR having given office(s) as its Pickup/Billing office
	 * 				customers with type AC having given office(s) as its Mapped To office
	 * cityId	 -	customers with type BA/BV having given city as city of its Mapped To office
	 * and all Reverse Logistics (RL) customers
	 */
	@Override
	public List<CustomerTO> getCustomersByContractBranches(Integer[] officeIds,
			Integer cityId) throws CGBusinessException, CGSystemException {
		List<CustomerDO> customerDOs = commonReportDAO.getCustomersByContractBranches(officeIds, cityId);
		List<CustomerTO> customerTOs = customerDO2TOConverter(customerDOs);
		return customerTOs;
	}
	
	@Override
	public List<LabelValueBean> getAllTransportModeList()
			throws CGBusinessException, CGSystemException {
		List<LabelValueBean> transportModeList = new ArrayList<LabelValueBean>();
		List<TransportModeDO> transportModeDOList = commonReportDAO
				.getAllTransportModeList();
		if (!CGCollectionUtils.isEmpty(transportModeDOList)) {
			for (TransportModeDO transportModeDO : transportModeDOList) {
				LabelValueBean lvb = new LabelValueBean();
				lvb.setLabel(transportModeDO.getTransportModeDesc());
				lvb.setValue(transportModeDO.getTransportModeId()
						+ CommonConstants.EMPTY_STRING);
				transportModeList.add(lvb);
			}
		} else {
			throw new CGBusinessException();
		}
		return transportModeList;
	}
	
	@Override
	public List<LabelValueBean> getServiceByTypeListByTransportModeId(
			Integer transportModeId) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("CommonReportServiceImpl::getServiceByTypeListByTransportModeId::START------------>:::::::");
		List<LabelValueBean> serviceByTypeList = new ArrayList<LabelValueBean>();
		List<ServiceByTypeDO> serviceByTypeDOList = commonReportDAO
				.getServiceByTypeListByTransportModeId(transportModeId);
		for (ServiceByTypeDO serviceByTypeDO : serviceByTypeDOList) {
			LabelValueBean lvb = new LabelValueBean();
			lvb.setLabel(serviceByTypeDO.getServiceByTypeDesc());
			lvb.setValue(serviceByTypeDO.getServiceByTypeId()
					+ CommonConstants.EMPTY_STRING);
			serviceByTypeList.add(lvb);
		}
		LOGGER.debug("CommonReportServiceImpl::getServiceByTypeListByTransportModeId::END------------>:::::::");
		return serviceByTypeList;
	}
	
	
	public List<CustomerTO> getAllCustomerForStockReport(Integer officeId, String officeType)
			throws CGSystemException, CGBusinessException {
		List<CustomerTO> contractCreditCustomers =null;
		List<CustomerTO> contractCustomers =null;
		List<CustomerTO> stockCustomerList=null;
		 contractCustomers = getContractCustomerListForStock(officeId, officeType);
		 stockCustomerList = getNonContractCustomerListForStock(officeId, officeType);
		if (!CGCollectionUtils.isEmpty(contractCustomers)
				&& !CGCollectionUtils.isEmpty(stockCustomerList)) {
			stockCustomerList.addAll(contractCustomers);
		} else if (!CGCollectionUtils.isEmpty(contractCustomers)
				&& CGCollectionUtils.isEmpty(stockCustomerList)) {
			stockCustomerList = contractCustomers;
		}
		
		if(!StringUtil.isStringEmpty(officeType) && officeType.equalsIgnoreCase(CommonConstants.OFF_TYPE_HUB_OFFICE)){
			/***Added this code, post UAT AS part of New Enhancement for Credit customers only if office type is HUB office*/
			 contractCreditCustomers = getContractCreditCustomerListForStock(officeId, officeType);
		}
		
		if(!CGCollectionUtils.isEmpty(contractCreditCustomers) && !CGCollectionUtils.isEmpty(stockCustomerList) ){
			stockCustomerList.addAll(contractCreditCustomers);
		}else if(!CGCollectionUtils.isEmpty(contractCreditCustomers) && CGCollectionUtils.isEmpty(stockCustomerList) ){
			stockCustomerList = contractCreditCustomers;
		}
		return stockCustomerList;
	}
	
	
	private List<CustomerTO> getContractCustomerListForStock(Integer officeId, String officeType)
			throws CGSystemException, CGBusinessException {
		CustomerTO customerTO = prepareCustomerTOForStock(officeId);
		if (!StringUtil.isNull(customerTO.getCustomerTypeTO())) {
			/** for Contract Customer types */
			customerTO.getCustomerTypeTO().setCustomerTypeCode(
					StockUniveralConstants.STOCK_CONTRACT_CUSTOMER_TYPE);
		}
		List<CustomerTO> customerDtls = stockUniversalService
				.getContractCustomerListForStock(customerTO);
		if (StringUtil.isEmptyColletion(customerDtls)) {
			LOGGER.warn("StockCommonServiceImpl::getAllCustomerDtlsByLoggedInOffice details does not exist ");
		}
		return customerDtls;
	}
	
	private List<CustomerTO> getNonContractCustomerListForStock(Integer officeId, String officeType)
			throws CGSystemException, CGBusinessException {
		CustomerTO customerTO = prepareCustomerTOForStock(officeId);
		if (!StringUtil.isNull(customerTO.getCustomerTypeTO())) {
			customerTO.getCustomerTypeTO().setCustomerTypeCode(
					StockUniveralConstants.STOCK_NON_CONTRACT_CUSTOMER_TYPE);
		}
		List<CustomerTO> customerDtls = stockUniversalService
				.getAllCustomerForIssue(customerTO);
		if (StringUtil.isEmptyColletion(customerDtls)) {
			LOGGER.warn("StockCommonServiceImpl::getNonContractCustomerListForStock details does not exist ");
		}
		return customerDtls;
	}
	
	private List<CustomerTO> getContractCreditCustomerListForStock(Integer officeId, String officeType)
			throws CGSystemException, CGBusinessException {
		CustomerTO customerTO = prepareCustomerTOForStock(officeId);
		if (!StringUtil.isNull(customerTO.getCustomerTypeTO())) {
			/** for Contract Customer types */
			customerTO.getCustomerTypeTO().setCustomerTypeCode(
					StockUniveralConstants.STOCK_CONTRACT_CREDIT_CUSTOMER_TYPE);
		}
		setCustomerDtlsByOfficeType(officeId, officeType, customerTO);
		List<CustomerTO> customerDtls = stockUniversalService
				.getContractCustomerListForStock(customerTO);
		if (StringUtil.isEmptyColletion(customerDtls)) {
			LOGGER.warn("StockCommonServiceImpl::getAllCustomerDtlsByLoggedInOffice details does not exist ");
		}
		return customerDtls;
	}
	
	
	private CustomerTO prepareCustomerTOForStock(Integer officeId) {
		CustomerTO customerTO = prepareCustomerByOffice(officeId,
				StockUniveralConstants.STOCK_CUSTOMER_TYPE);
		if (customerTO != null && customerTO.getOfficeMappedTO() != null) {
			customerTO.getOfficeMappedTO().setReportingRHO(officeId);
		}
		return customerTO;
	}
	
	private CustomerTO prepareCustomerByOffice(Integer officeId,
			String customerTypeCode) {
		CustomerTO customerTO = new CustomerTO();
		customerTO.setMappedOffice(officeId);
		OfficeTO mappedOffice = new OfficeTO();// set office Id
		mappedOffice.setOfficeId(officeId);
		customerTO.setOfficeMappedTO(mappedOffice);
		CustomerTypeTO customerType = new CustomerTypeTO();// Set Customer type
		customerType.setCustomerTypeCode(customerTypeCode);
		customerTO.setCustomerTypeTO(customerType);
		return customerTO;
	}

	private void setCustomerDtlsByOfficeType(Integer officeId,
			String officeType, CustomerTO customerTO) {
		switch(officeType){
		case CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE:
			customerTO.getOfficeMappedTO().setReportingRHO(officeId);
			customerTO.getOfficeMappedTO().setReportingHUB(null);
			break;
		case CommonConstants.OFF_TYPE_HUB_OFFICE:
			customerTO.getOfficeMappedTO().setReportingRHO(null);
			customerTO.getOfficeMappedTO().setReportingHUB(officeId);
			break;
		}
	}

	public OfficeTO getOfficeDetailsByOfficeId(Integer officeId) throws CGBusinessException, CGSystemException{
		OfficeTO offcTO = stockUniversalService.getOfficeDetails(officeId);
		return offcTO;
	}

	@Override
	public List<CityTO> getAllCities() throws CGBusinessException,
			CGSystemException {
		List<CityTO> cityToList = null;
		List<CityDO> cityDoList=commonReportDAO.getAllCities();
		if (!StringUtil.isEmptyList(cityDoList)) {
			cityToList = new ArrayList<>(cityDoList.size());
			try {
				for (CityDO itemTypeDO : cityDoList) {
					CityTO itemTypeTO = new CityTO();
					PropertyUtils.copyProperties(itemTypeTO, itemTypeDO);
					cityToList.add(itemTypeTO);
				}
			} catch (IllegalAccessException e) {
				LOGGER.error("CommonReportServiceImpl::getCitiesByRegionIds---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			} catch (InvocationTargetException e) {
				LOGGER.error("CommonReportServiceImpl::getCitiesByRegionIds---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			} catch (NoSuchMethodException e) {
				LOGGER.error("CommonReportServiceImpl::getCitiesByRegionIds---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			}

		} else {
			LOGGER.warn("CommonReportServiceImpl::getCitiesByRegionIds---Details doesnot exist");
		}
		return cityToList;

	}

	@Override
	public List<ConsignmentCustomerTO> getCustomersByContractBranchesForConsignmentDetails(
			Integer[] officeIds, Integer[] cityId) throws CGSystemException {
		 List<ConsignmentCustomerTO> customers = commonReportDAO.getCustomersByContractBranchesForConsignmentDetails(officeIds, cityId);
		//List<CustomerTO> customerTOs = customerDO2TOConverter(customerDOs);
		return customers;
	}

	/*@Override
	public List<CustomerTO> getCustomerByStation1(List<Integer> stationNum)
			throws CGBusinessException, CGSystemException {
		List<CustomerDO> customerDOs = commonReportDAO
				.getCustomerByStation1(stationNum);
		List<CustomerTO> customerTOs = customerDO2TOConverter(customerDOs);
		return customerTOs;
	}*/
	
	@Override
	public List<RateRevisionDO> getCustomerByStation2(List<Integer> stationNum)
			throws CGBusinessException, CGSystemException {
		List<RateRevisionDO> customerDOs = commonReportDAO
				.getCustomerByStation1(stationNum);
		//List<CustomerTO> customerTOs = customerDO2TOConverter(customerDOs);
		return customerDOs;
	}


	
	public List<CustomerTO> getAllCustomerByLoggedInOffice(Integer officeId, String officeType)
			throws CGSystemException, CGBusinessException {
		List<CustomerTO> contractCreditCustomers =null;
		List<CustomerTO> contractCustomers =null;
		List<CustomerTO> stockCustomerList=null;
		 contractCustomers = getContractCustomerListForStock(officeId, officeType);
		 stockCustomerList = getNonContractCustomerListForStock(officeId, officeType);
		if (!CGCollectionUtils.isEmpty(contractCustomers)
				&& !CGCollectionUtils.isEmpty(stockCustomerList)) {
			stockCustomerList.addAll(contractCustomers);
		} else if (!CGCollectionUtils.isEmpty(contractCustomers)
				&& CGCollectionUtils.isEmpty(stockCustomerList)) {
			stockCustomerList = contractCustomers;
		}
		
		if(!StringUtil.isStringEmpty(officeType) && officeType.equalsIgnoreCase(CommonConstants.OFF_TYPE_HUB_OFFICE)){
			/***Added this code, post UAT AS part of New Enhancement for Credit customers only if office type is HUB office*/
			 contractCreditCustomers = getContractCreditCustomerListForStock(officeId, officeType);
		}
		
		if(!CGCollectionUtils.isEmpty(contractCreditCustomers) && !CGCollectionUtils.isEmpty(stockCustomerList) ){
			stockCustomerList.addAll(contractCreditCustomers);
		}else if(!CGCollectionUtils.isEmpty(contractCreditCustomers) && CGCollectionUtils.isEmpty(stockCustomerList) ){
			stockCustomerList = contractCreditCustomers;
		}
		return stockCustomerList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CGBaseTO> getSalesPersonsForProspects(Integer officeId,
			Integer cityId, Integer regionId) throws CGBusinessException,
			CGSystemException {
		List<ClientGainedWrapperDO> empDOs = null;
		List<CGBaseTO> empTOs = null;
		empDOs = commonReportDAO.getSalesPersonsForProspects(officeId,
				cityId, regionId);
		if (!StringUtil.isEmptyList(empDOs)) {
			try {
				empTOs = (List<CGBaseTO>) CGObjectConverter
						.createTOListFromDomainList(empDOs, ClientGainedWrapperTO.class);
			} catch (Exception e1) {
				LOGGER.error(
						"Exception occurs in StockCommonServiceImpl :: getItemTypeList() .. Exception ::",
						e1);
				e1.printStackTrace();
				throw new CGBusinessException(e1);
			}
		} else {
			LOGGER.info("StockCommonServiceImpl::getItemTypeList---Details doesnot exist");
		}
		return empTOs;
	}

	@Override
	public List<ReportTypeTO> getReportType() throws CGBusinessException,
			CGSystemException {
		// TODO Auto-generated method stub
		return commonReportDAO.getReportType();
	}


	@Override
	public List<ItemTypeTO> getItemTypeList() throws CGSystemException,
			CGBusinessException {
		List<ItemTypeDO> itemTypeDoList = null;
		List<ItemTypeTO> itemTypeToList = null;

		itemTypeDoList = commonReportDAO.getItemTypeList();
		if (!StringUtil.isEmptyList(itemTypeDoList)) {
			itemTypeToList = new ArrayList<>(itemTypeDoList.size());
			try {
				for (ItemTypeDO itemTypeDO : itemTypeDoList) {
					ItemTypeTO itemTypeTO = new ItemTypeTO();
					PropertyUtils.copyProperties(itemTypeTO, itemTypeDO);
					itemTypeToList.add(itemTypeTO);
				}
			} catch (IllegalAccessException e) {
				LOGGER.error("StockCommonServiceImpl::getItemTypeList---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			} catch (InvocationTargetException e) {
				LOGGER.error("StockCommonServiceImpl::getItemTypeList---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			} catch (NoSuchMethodException e) {
				LOGGER.error("StockCommonServiceImpl::getItemTypeList---Exception"
						+ e.getLocalizedMessage());
				e.printStackTrace();// FIXME remove later
			}

		} else {
			LOGGER.warn("StockCommonServiceImpl::getItemTypeList---Details doesnot exist");
		}
		return itemTypeToList;
	}

	@Override
	public List<CustomerTO> getCustomerByStation1(List<Integer> stationNum)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PriorityTypeTO> getPriorityType() throws CGBusinessException,
			CGSystemException {
		// TODO Auto-generated method stub
				return commonReportDAO.getPriorityType();
	}
	
	
}
