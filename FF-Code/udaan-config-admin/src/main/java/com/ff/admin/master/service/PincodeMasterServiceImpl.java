package com.ff.admin.master.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.master.constants.PincodeMasterErrorConstants;
import com.ff.admin.master.dao.PincodeMasterDao;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.PincodeMasterDO;
import com.ff.domain.geography.PincodeMasterProductServiceabilityDO;
import com.ff.domain.geography.StateDO;
import com.ff.domain.organization.BranchPincodeMasterServiceabilityDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.PaperworksPincodeMasterMapDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.serviceOffering.ProductGroupServiceabilityDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.geography.StateTO;
import com.ff.master.PinCodeMasterTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ProductGroupServiceabilityTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;

public class PincodeMasterServiceImpl implements PincodeMasterService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PincodeMasterServiceImpl.class);
	/** The PincodeMasterDao */
	private PincodeMasterDao pincodeMasterDao;

	/** The GeographyCommonService */
	private GeographyCommonService geographyCommonService;
	private ServiceOfferingCommonService serviceOfferingCommonService;

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

	/** The OrganizationCommonService */
	private OrganizationCommonService organizationCommonService;

	/**
	 * Sets the PincodeMasterDao
	 * 
	 * @param pincodeMasterDao
	 *            the pincodeMasterDao to set
	 */
	public void setPincodeMasterDao(PincodeMasterDao pincodeMasterDao) {
		this.pincodeMasterDao = pincodeMasterDao;
	}

	/**
	 * Sets the GeographyCommonService
	 * 
	 * @param geographyCommonService
	 *            the geographyCommonService to set
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	/**
	 * Sets the OrganizationCommonService
	 * 
	 * @param OrganizationCommonService
	 *            the OrganizationCommonService to set
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.master.service.PincodeMasterService#getRegions()
	 */

	public List<RegionTO> getRegions() throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("PincodeMasterServiceImpl::getRegions::START----->");
		try {
			return geographyCommonService.getAllRegions();
		} catch (Exception ex) {
			LOGGER.error("ERROR : PincodeMasterServiceImpl::getRegions", ex);
			throw new CGBusinessException(AdminErrorConstants.NO_REGION_FOUND,
					ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.admin.master.service.PincodeMasterService#getStatesByRegionId(
	 * java.lang.Integer)
	 */

	@SuppressWarnings("unchecked")
	public List<StateTO> getStatesByRegionId(Integer regionId)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("PincodeMasterServiceImpl::getStatesByRegionId::START----->");
		List<StateTO> stateTO = null;
		try {
			List<StateDO> stateDO = pincodeMasterDao
					.getStatesByRegionId(regionId);

			if (!CGCollectionUtils.isEmpty(stateDO)) {
				stateTO = (List<StateTO>) CGObjectConverter
						.createTOListFromDomainList(stateDO, StateTO.class);
			} else {
				ExceptionUtil
						.prepareBusinessException(PincodeMasterErrorConstants.NO_STATE_FOUND);
			}
		} catch (CGSystemException e) {
			LOGGER.error("PincodeMasterServiceImpl :: getStatesByRegionId() ::"
					+ e.getMessage());
			throw e;
		}
		LOGGER.debug("PincodeMasterServiceImpl::getStatesByRegionId::END----->");
		return stateTO;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.admin.master.service.PincodeMasterService#getCitysByStateId(java
	 * .lang.Integer)
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<CityTO> getCitysByStateId(Integer stateId)
			throws CGBusinessException, CGSystemException {

		LOGGER.debug("PincodeMasterServiceImpl::getCitysByStateId::START----->");
		List<CityTO> cityTO = null;
		try {
			List<CityDO> cityDO = pincodeMasterDao.getCitysByStateId(stateId);

			if (!CGCollectionUtils.isEmpty(cityDO)) {

				cityTO = (List<CityTO>) CGObjectConverter
						.createTOListFromDomainList(cityDO, CityTO.class);
			} else {
				ExceptionUtil
						.prepareBusinessException(PincodeMasterErrorConstants.CITIES_NOT_FOUND);
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR : PincodeMasterServiceImpl::getCitysByStateId",
					ex);
			throw ex;
		}
		LOGGER.debug("PincodeMasterServiceImpl::getCitysByStateId::END----->");
		return cityTO;

	}

	public List<OfficeTO> getBranchesByCity(Integer regionid, Integer officeTypeId)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("PincodeMasterServiceImpl::getBranchesByCity::START----->");
		return organizationCommonService.getAllOfficesByRegionAndOfficeType(regionid, officeTypeId);
	}

	@Override
	public List<ProductTO> getAllProduct() throws CGBusinessException,
			CGSystemException {
		List<ProductTO> productTOs = null;
		List<ProductDO> productDO = pincodeMasterDao.getAllProducts();
		productTOs = new ArrayList<ProductTO>(productDO.size());
		if (!CGCollectionUtils.isEmpty(productDO)) {
			for (ProductDO DO : productDO) {
				ProductTO productTO = new ProductTO();
				productTO.setProductId(DO.getProductId());
				productTO.setConsgSeries(DO.getConsgSeries());
				ProductGroupServiceabilityTO serviceabilityTO = new ProductGroupServiceabilityTO();
				serviceabilityTO.setProdGroupId(DO.getProductGroup()
						.getProdGroupId());
				serviceabilityTO.setProdGroupName(DO.getProductGroup()
						.getProdGroupName());
				productTO.setProductGroupTO(serviceabilityTO);
				productTOs.add(productTO);
			}

		}
		return productTOs;
	}

	@Override
	public List<ProductGroupServiceabilityTO> getAllProductGroup()
			throws CGBusinessException, CGSystemException {
		return serviceOfferingCommonService.getAllProductGroup();
	}

	public List<CNPaperWorksTO> getAllPaperWorks() throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("PincodeMasterServiceImpl::getAllPaperWorks::START----->");

		List<CNPaperWorksTO> cnPaperWorkTos = null;
		CNPaperWorksTO cnPaperWorksTO = null;
		try {

			List<CNPaperWorksDO> cnPaperWorkDOs = pincodeMasterDao
					.getAllPaperWorks();

			if (!CGCollectionUtils.isEmpty(cnPaperWorkDOs)) {
				cnPaperWorkTos = new ArrayList<>(cnPaperWorkDOs.size());
				for (CNPaperWorksDO cnPaperWorksDO : cnPaperWorkDOs) {
					cnPaperWorksTO = new CNPaperWorksTO();
					cnPaperWorksTO = (CNPaperWorksTO) CGObjectConverter
							.createToFromDomain(cnPaperWorksDO, cnPaperWorksTO);
					cnPaperWorkTos.add(cnPaperWorksTO);
				}
			}

		} catch (Exception ex) {
			LOGGER.error("ERROR : PincodeMasterServiceImpl::getAllPaperWorks",
					ex);

		}
		return cnPaperWorkTos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.admin.master.service.PincodeMasterService#savePincodeMaster(com
	 * .ff.master.PinCodeMasterTO)
	 */
	public boolean savePincodeMaster(PinCodeMasterTO pincodeTO) {
		LOGGER.debug("PincodeMasterserviceImpl------savePincodeMaster---START");
		boolean isSaved = Boolean.FALSE;
		try {

			// get cityDO from city id
			/*
			 * CityTO cityTO = new CityTO();
			 * cityTO.setCityId(Integer.parseInt(pincodeTO.getCityId())); CityTO
			 * newCityTO = geographyCommonService.getCity(cityTO); CityDO cityDO
			 * = new CityDO(); CityDO newCityDO = (CityDO)
			 * CGObjectConverter.createDomainFromTo(newCityTO, cityDO);
			 */

			// search for existing PincodeDO
			PincodeMasterDO searchedpincodeDO = pincodeMasterDao.searchPincodeDetls(pincodeTO.getPincodeNo());

			PincodeMasterDO pincodeDO = new PincodeMasterDO();
			pincodeDO.setCityId(Integer.parseInt(pincodeTO.getCityId()));
			pincodeDO.setLocation(pincodeTO.getLocation());
			pincodeDO.setPincode(pincodeTO.getPincodeNo());
			pincodeDO.setCreatedBy(pincodeTO.getCreatedBy());
			pincodeDO.setUpdatedBy(pincodeTO.getUpdatedBy());
			pincodeDO.setCreatedDate(DateUtil.stringToDDMMYYYYFormat(pincodeTO.getCreatedDate()));
			pincodeDO.setUpdatedDate(DateUtil.stringToDDMMYYYYFormat(pincodeTO.getUpdatedate()));
			pincodeDO.setStatus("I");
			// for update
			if (!StringUtil.isNull(searchedpincodeDO)) {
				pincodeDO.setPincodeId(searchedpincodeDO.getPincodeId());
				pincodeDO.setStatus(searchedpincodeDO.getStatus());
			}

			// prepare the BranchPincodeserviceableDO
			BranchPincodeMasterServiceabilityDO branchPincodeServicblDO = null;
			Set<BranchPincodeMasterServiceabilityDO> branchPincodeServiceableSet = new HashSet<BranchPincodeMasterServiceabilityDO>();

			String[] selectdBranchOffices = pincodeTO.getServicablebranch();

			for (int i = 0; i < selectdBranchOffices.length; i++) {
				branchPincodeServicblDO = new BranchPincodeMasterServiceabilityDO();
				String OfficeId = selectdBranchOffices[i];
				branchPincodeServicblDO.setOfficeId(Integer.parseInt(OfficeId));
				branchPincodeServicblDO.setCreatedBy(pincodeTO.getCreatedBy());
				branchPincodeServicblDO.setCreatedDate(DateUtil.stringToDDMMYYYYFormat(pincodeTO.getCreatedDate()));
				branchPincodeServicblDO.setUpdatedBy(pincodeTO.getUpdatedBy());
				branchPincodeServicblDO.setUpdatedDate(DateUtil.stringToDDMMYYYYFormat(pincodeTO.getUpdatedate()));
				branchPincodeServicblDO.setStatus("A");
				// for update
				if (!StringUtil.isNull(searchedpincodeDO)) {
					for (BranchPincodeMasterServiceabilityDO searchedBranchPincodeServcbleDO : 
							searchedpincodeDO.getBranchPincodeServiceables()) {

						if (searchedBranchPincodeServcbleDO.getOfficeId().equals(Integer.parseInt(selectdBranchOffices[i]))) {
							branchPincodeServicblDO
									.setBranchPincodeMasterId(searchedBranchPincodeServcbleDO
											.getBranchPincodeMasterId());
							break;
						}
					}
				}
				branchPincodeServicblDO.setPincodeId(pincodeDO);
				branchPincodeServiceableSet.add(branchPincodeServicblDO);
			}

			if (!StringUtil.isNull(searchedpincodeDO)) {
				boolean flag = Boolean.FALSE;
				for (BranchPincodeMasterServiceabilityDO searchedBranchPincodeServcbleDO : searchedpincodeDO
						.getBranchPincodeServiceables()) {
					flag = Boolean.FALSE;
					for (int x = 0; x < selectdBranchOffices.length; x++) {
						if (searchedBranchPincodeServcbleDO.getOfficeId()
								.equals(Integer
										.parseInt(selectdBranchOffices[x]))) {
							searchedBranchPincodeServcbleDO.setStatus("A");
							branchPincodeServiceableSet
									.add(searchedBranchPincodeServcbleDO);
							flag = Boolean.TRUE;
							break;
						}
					}

					if (!flag) {
						searchedBranchPincodeServcbleDO.setStatus("I");
						branchPincodeServiceableSet
								.add(searchedBranchPincodeServcbleDO);
					}
				}
			}
			pincodeDO.setBranchPincodeServiceables(branchPincodeServiceableSet);

			// prepare the ProductPincodeserviceableDO
			PincodeMasterProductServiceabilityDO pincodeProductServcbltyDO = null;

			Set<PincodeMasterProductServiceabilityDO> pincodeProductServiceableSet = new HashSet<PincodeMasterProductServiceabilityDO>();

			String[] selectdProducts = pincodeTO.getGroupIds();
			String[] selectedGrup3Ids = pincodeTO.getGroup3Ids();
			String[] selectedGrup3Cities = pincodeTO.getGroup3cityList();
			String[] selectedGrup5Cities = pincodeTO.getGroup5cityList();

			for (int i = 0; i < selectdProducts.length; i++) {

				pincodeProductServcbltyDO = new PincodeMasterProductServiceabilityDO();

				String productGrupId = selectdProducts[i];

				// get productGrupServiceDO by grupId
				ProductGroupServiceabilityDO productGrupServicblDO = pincodeMasterDao
						.getProductGroupServiceByGrupId(Integer
								.parseInt(productGrupId));

				if (productGrupId.equalsIgnoreCase("3")	&& !StringUtil.isNull(selectedGrup3Ids)) {
					PincodeMasterProductServiceabilityDO pincodeProductservcbltyDO = null;
					for (int b = 0; b < selectedGrup3Cities.length; b++) {
						for (int j = 0; j < selectedGrup3Ids.length; j++) {

							pincodeProductservcbltyDO = new PincodeMasterProductServiceabilityDO();
							CityDO cityDO = new CityDO();
							cityDO.setCityId(Integer.parseInt(selectedGrup3Cities[b]));
							pincodeProductservcbltyDO.setOriginCity(cityDO);
							if (selectedGrup3Ids[j].equalsIgnoreCase("A")) {
								pincodeProductservcbltyDO.setDlvTimeQualification("A");
								pincodeProductservcbltyDO.setDeliveryTime("14:00");
							}
							if (selectedGrup3Ids[j].equalsIgnoreCase("B")) {
								pincodeProductservcbltyDO.setDlvTimeQualification("B");
								pincodeProductservcbltyDO.setDeliveryTime("14:00");
							}
							if (selectedGrup3Ids[j].equalsIgnoreCase("S")) {
								pincodeProductservcbltyDO.setDlvTimeQualification("S");
								pincodeProductservcbltyDO.setDeliveryTime("48 Hrs");
							}
							pincodeProductservcbltyDO.setStatus("A");
							pincodeProductservcbltyDO.setProductGroupId(productGrupServicblDO);

							// for update
							if (!StringUtil.isNull(searchedpincodeDO)) {
								for (PincodeMasterProductServiceabilityDO searchdProductPincodeDO : searchedpincodeDO.getProductPincodeServiceables()) {
									if (searchdProductPincodeDO
											.getProductGroupId()
											.getProdGroupId()
											.equals(Integer.parseInt("3"))) {
										if (searchdProductPincodeDO
												.getDlvTimeQualification()
												.equals(selectedGrup3Ids[j])
												&& searchdProductPincodeDO
														.getOriginCity()
														.getCityId()
														.equals(Integer
																.parseInt(selectedGrup3Cities[b]))) {
											pincodeProductservcbltyDO
													.setPincodeDeliveryTimeMapId(searchdProductPincodeDO
															.getPincodeDeliveryTimeMapId());
											pincodeProductservcbltyDO.setStatus("A");
											break;
										}
									}
								}
							}
							pincodeProductservcbltyDO.setPincode(pincodeDO);
							pincodeProductServiceableSet.add(pincodeProductservcbltyDO);
						}

					}

					if (!StringUtil.isNull(searchedpincodeDO)) {
						boolean isExist = Boolean.FALSE;
						for (PincodeMasterProductServiceabilityDO searchdProductPincodeDO : searchedpincodeDO
								.getProductPincodeServiceables()) {
							if (searchdProductPincodeDO.getProductGroupId()
									.getProdGroupId()
									.equals(Integer.parseInt("3"))) {
								// isExist=Boolean.FALSE;

								for (int x = 0; x < selectedGrup3Ids.length; x++) {
									for (int d = 0; d < selectedGrup3Cities.length; d++) {
										if (!searchdProductPincodeDO
												.getDlvTimeQualification()
												.equals(selectedGrup3Ids[x])
												&& !searchdProductPincodeDO
														.getOriginCity()
														.getCityId()
														.equals(Integer
																.parseInt(selectedGrup3Cities[d]))) {
											// searchdProductPincodeDO.setStatus("I");
											// pincodeProductServiceableSet.add(searchdProductPincodeDO);
											isExist = Boolean.TRUE;
											break;
										}
									}
								}

								if (!isExist) {
									searchdProductPincodeDO.setStatus("I");
									pincodeProductServiceableSet
											.add(searchdProductPincodeDO);
								}
							}
						}
					}

				} else if (productGrupId.equalsIgnoreCase("5")) {
					PincodeMasterProductServiceabilityDO pincodeProdservcbltyDO = null;
					for (int b = 0; b < selectedGrup5Cities.length; b++) {

						pincodeProdservcbltyDO = new PincodeMasterProductServiceabilityDO();

						CityDO cityDO = new CityDO();
						cityDO.setCityId(Integer
								.parseInt(selectedGrup5Cities[b]));
						pincodeProdservcbltyDO.setOriginCity(cityDO);

						pincodeProdservcbltyDO.setPincode(pincodeDO);

						pincodeProdservcbltyDO
								.setProductGroupId(productGrupServicblDO);
						pincodeProdservcbltyDO.setStatus("A");

						// for update
						if (!StringUtil.isNull(searchedpincodeDO)) {
							for (PincodeMasterProductServiceabilityDO searchdProductPincodeDO : searchedpincodeDO
									.getProductPincodeServiceables()) {
								if (searchdProductPincodeDO.getProductGroupId()
										.getProdGroupId()
										.equals(Integer.parseInt("5"))) {
									if (searchdProductPincodeDO
											.getOriginCity()
											.getCityId()
											.equals(Integer
													.parseInt(selectedGrup5Cities[b]))) {
										pincodeProdservcbltyDO
												.setPincodeDeliveryTimeMapId(searchdProductPincodeDO
														.getPincodeDeliveryTimeMapId());
										pincodeProdservcbltyDO.setStatus("A");
										break;
									}

								}
							}
						}

						pincodeProductServiceableSet
								.add(pincodeProdservcbltyDO);

					}

					// for update
					if (!StringUtil.isNull(searchedpincodeDO)) {
						boolean isExist = Boolean.FALSE;
						for (PincodeMasterProductServiceabilityDO searchdProductPincodeDO : searchedpincodeDO
								.getProductPincodeServiceables()) {
							if (searchdProductPincodeDO.getProductGroupId()
									.getProdGroupId()
									.equals(Integer.parseInt("5"))) {
								isExist = Boolean.FALSE;

								for (int d = 0; d < selectedGrup5Cities.length; d++) {

									if (searchdProductPincodeDO
											.getOriginCity()
											.getCityId()
											.equals(Integer
													.parseInt(selectedGrup5Cities[d]))) {
										// searchdProductPincodeDO.setStatus("I");
										// pincodeProductServiceableSet.add(searchdProductPincodeDO);
										isExist = Boolean.TRUE;
										break;
									}

								}

								if (!isExist) {
									searchdProductPincodeDO.setStatus("I");
									pincodeProductServiceableSet
											.add(searchdProductPincodeDO);
								}
							}
						}
					}

				} else {

					pincodeProductServcbltyDO
							.setProductGroupId(productGrupServicblDO);

					pincodeProductServcbltyDO.setPincode(pincodeDO);

					/*
					 * if(productGrupId.equalsIgnoreCase("1")||productGrupId.
					 * equalsIgnoreCase
					 * ("2")||productGrupId.equalsIgnoreCase("4")){
					 * pincodeProductServcbltyDO.setOriginCity(null); }
					 */
					pincodeProductServcbltyDO.setOriginCity(null);

					pincodeProductServcbltyDO.setStatus("A");
					// for update
					if (!StringUtil.isNull(searchedpincodeDO)) {
						for (PincodeMasterProductServiceabilityDO searchdProductPincodeDO : searchedpincodeDO
								.getProductPincodeServiceables()) {

							if ((searchdProductPincodeDO.getProductGroupId()
									.getProdGroupId().equals(Integer
									.parseInt(selectdProducts[i])))) {

								pincodeProductServcbltyDO
										.setPincodeDeliveryTimeMapId(searchdProductPincodeDO
												.getPincodeDeliveryTimeMapId());
								break;
							}
						}
					}

					pincodeProductServiceableSet.add(pincodeProductServcbltyDO);
				}
				// for update
				if (!StringUtil.isNull(searchedpincodeDO)) {
					boolean isExist = Boolean.FALSE;
					for (PincodeMasterProductServiceabilityDO searchdProductPincodeDO : searchedpincodeDO
							.getProductPincodeServiceables()) {

						isExist = Boolean.FALSE;
						if (searchdProductPincodeDO.getProductGroupId()
								.getProdGroupId()
								.equals(Integer.parseInt(selectdProducts[i]))) {
							// searchdProductPincodeDO.setStatus("I");
							// pincodeProductServiceableSet.add(searchdProductPincodeDO);
							isExist = Boolean.TRUE;
							break;
						}

						if (!isExist) {
							searchdProductPincodeDO.setStatus("I");
							pincodeProductServiceableSet
									.add(searchdProductPincodeDO);
						}

					}
				}

			}

			pincodeDO
					.setProductPincodeServiceables(pincodeProductServiceableSet);

			// prepare the PaperWorkPincodeserviceableDO
			Set<PaperworksPincodeMasterMapDO> paperWorkPincodeSet = new HashSet<PaperworksPincodeMasterMapDO>();
			PaperworksPincodeMasterMapDO paperWorkPincodeDO = null;

			Integer[] paperWorkIds = pincodeTO.getPaperWorkIds();

			if (!StringUtil.isEmpty(paperWorkIds)) {

				for (int i = 0; i < paperWorkIds.length; i++) {
					paperWorkPincodeDO = new PaperworksPincodeMasterMapDO();

					// get the CnPaperworkDO
					CNPaperWorksDO cnPaperWorkDO = pincodeMasterDao
							.getCnPaperWorksByPaperWorkId(paperWorkIds[i]);

					paperWorkPincodeDO.setCnPaperWorkId(cnPaperWorkDO);
					paperWorkPincodeDO.setStatus("A");

					// for update
					if (!StringUtil.isNull(searchedpincodeDO)) {
						for (PaperworksPincodeMasterMapDO searchdPaperWorkPincodeServcbleDO : searchedpincodeDO
								.getPaperWorkPincodes()) {

							if ((searchdPaperWorkPincodeServcbleDO
									.getCnPaperWorkId().getCnPaperWorkId()
									.equals(paperWorkIds[i]))) {
								paperWorkPincodeDO
										.setPaperworksPincodeMapId(searchdPaperWorkPincodeServcbleDO
												.getPaperworksPincodeMapId());
								// searchdPaperWorkPincodeServcbleDO.setStatus("A");
								// paperWorkPincodeSet.add(searchdPaperWorkPincodeServcbleDO);
								break;
							}
						}
					}
					paperWorkPincodeDO.setPincodeId(pincodeDO);

					paperWorkPincodeSet.add(paperWorkPincodeDO);

				}

				if (!StringUtil.isNull(searchedpincodeDO)) {
					boolean isPaperExist = Boolean.FALSE;
					for (PaperworksPincodeMasterMapDO searchdPaperWorkPincodeServcbleDO : searchedpincodeDO
							.getPaperWorkPincodes()) {
						isPaperExist = Boolean.FALSE;
						for (int x = 0; x < paperWorkIds.length; x++) {
							if (searchdPaperWorkPincodeServcbleDO
									.getCnPaperWorkId().getCnPaperWorkId()
									.equals((paperWorkIds[x]))) {
								searchdPaperWorkPincodeServcbleDO
										.setStatus("A");
								paperWorkPincodeSet
										.add(searchdPaperWorkPincodeServcbleDO);
								isPaperExist = Boolean.TRUE;
								break;
							}
						}

						if (!isPaperExist) {
							searchdPaperWorkPincodeServcbleDO.setStatus("I");
							paperWorkPincodeSet
									.add(searchdPaperWorkPincodeServcbleDO);
						}
					}
				}

				pincodeDO.setPaperWorkPincodes(paperWorkPincodeSet);
			}// end of if

			isSaved = pincodeMasterDao.saveOrUpdatePincode(pincodeDO);

		} catch (Exception e) {
			LOGGER.error("PincodeMasterserviceImpl------savePincode---Exception", e);
		}
		LOGGER.debug("PincodeMasterserviceImpl------savePincode---end");
		return isSaved;
	}

	public PinCodeMasterTO searchPincodeDetails(String pincodeNO) {
		LOGGER.debug("PincodeMasterServiceImpl------searcchPincode()---START");
		PinCodeMasterTO pincodeMasterTO = new PinCodeMasterTO();
		String[] offices = null;
		Integer[] paperWorks = null;
		String[] grupIds = null;
		String[] grup3Ids = null;
		String[] grup3Cities = null;
		String[] grup5Cities = null;
		int i = 0;
		int j = 0;
		int k = 0;
		int m = 0;
		int d = 0;
		int l = 0;
		Map<Integer, Integer> grupIdMap = new HashMap<Integer, Integer>();
		try {

			PincodeMasterDO pincodeDO = pincodeMasterDao
					.searchPincodeDetls(pincodeNO);

			if (!StringUtil.isNull(pincodeDO)) {

				pincodeMasterTO.setPincodeId(StringUtil
						.convertIntegerToString(pincodeDO.getPincodeId()));

				// get cityDO from city id
				CityTO cityTO = new CityTO();
				cityTO.setCityId((pincodeDO.getCityId()));
				CityTO newCityTO = geographyCommonService.getCity(cityTO);

				Set<BranchPincodeMasterServiceabilityDO> branchPincodeservicbltySet = pincodeDO
						.getBranchPincodeServiceables();
				Set<PaperworksPincodeMasterMapDO> paperWorkPincodeSet = pincodeDO
						.getPaperWorkPincodes();
				Set<PincodeMasterProductServiceabilityDO> productPincodeSet = pincodeDO
						.getProductPincodeServiceables();
				productPincodeSet.size();

				offices = new String[branchPincodeservicbltySet.size()];
				for (BranchPincodeMasterServiceabilityDO branchPincodeDO : branchPincodeservicbltySet) {
					if (branchPincodeDO.getStatus().equalsIgnoreCase("A")) {
						Integer officeId = branchPincodeDO.getOfficeId();

						String offcId = StringUtil
								.convertIntegerToString(officeId);
						offices[i] = offcId;
						i++;
					}

				}

				pincodeMasterTO.setServicablebranch(offices);

				if (!StringUtil.isEmptyColletion(paperWorkPincodeSet)) {

					paperWorks = new Integer[paperWorkPincodeSet.size()];
					for (PaperworksPincodeMasterMapDO paperWorkPincode : paperWorkPincodeSet) {
						if (paperWorkPincode.getStatus().equalsIgnoreCase("A")) {

							CNPaperWorksDO cnPaperWorkDO = paperWorkPincode
									.getCnPaperWorkId();

							paperWorks[j] = cnPaperWorkDO.getCnPaperWorkId();
							j++;
						}
					}
					pincodeMasterTO.setPaperWorkIds(paperWorks);
				}

				grupIds = new String[productPincodeSet.size()];
				grup3Ids = new String[productPincodeSet.size()];
				grup3Cities = new String[productPincodeSet.size()];
				grup5Cities = new String[productPincodeSet.size()];

				for (PincodeMasterProductServiceabilityDO productPincode : productPincodeSet) {
					if (productPincode.getStatus().equalsIgnoreCase("A")) {

						ProductGroupServiceabilityDO productGrupServcblDO = productPincode
								.getProductGroupId();

						if (productGrupServcblDO.getProdGroupId().equals(3)) {

							if (StringUtil.isNull(grup3Ids[0])) {
								grup3Ids[m] = productPincode
										.getDlvTimeQualification();
								m++;
							} else {
								if (!StringUtil.isNull(productPincode
										.getDlvTimeQualification()) && !grup3Ids[m - 1].contains(productPincode
										.getDlvTimeQualification())) {
									grup3Ids[m] = productPincode
											.getDlvTimeQualification();
									m++;
								}
							}

							boolean isGrup3CityExist = Boolean.FALSE;
							for (int g = 0; g < grup3Cities.length; g++) {

								if (!StringUtil.isNull(grup3Cities[g])
										&& grup3Cities[g]
												.equals(StringUtil
														.convertIntegerToString(productPincode
																.getOriginCity()
																.getCityId()))) {
									isGrup3CityExist = Boolean.TRUE;
									break;
								}
							}
							if (isGrup3CityExist == Boolean.FALSE) {
								grup3Cities[d] = (StringUtil
										.convertIntegerToString(productPincode
												.getOriginCity().getCityId()));
								d++;
							}

							/*
							 * if(!grup3Cities.contains(StringUtil.
							 * convertIntegerToString
							 * (productPincode.getOriginCity().getCityId()))){
							 * grup3Cities[d]
							 * =(StringUtil.convertIntegerToString
							 * (productPincode.getOriginCity().getCityId()));
							 * d++; }
							 */

						}

						if (productGrupServcblDO.getProdGroupId().equals(5)) {

							boolean isGrup5CityExist = Boolean.FALSE;
							for (int g = 0; g < grup5Cities.length; g++) {

								if (!StringUtil.isNull(grup5Cities[g])
										&& grup5Cities[g]
												.equals(StringUtil
														.convertIntegerToString(productPincode
																.getOriginCity()
																.getCityId()))) {
									isGrup5CityExist = Boolean.TRUE;
									break;
								}
							}
							if (isGrup5CityExist == Boolean.FALSE) {
								grup5Cities[l] = (StringUtil
										.convertIntegerToString(productPincode
												.getOriginCity().getCityId()));
								l++;
							}
						}

						if (StringUtil.isNull(grupIdMap
								.get(productGrupServcblDO.getProdGroupId()))) {

							// add in hash map
							grupIdMap.put(
									productGrupServcblDO.getProdGroupId(),
									productGrupServcblDO.getProdGroupId());
							grupIds[k] = StringUtil
									.convertIntegerToString(productGrupServcblDO
											.getProdGroupId());
							k++;
						}
						// grupIds[k] =
						// StringUtil.convertIntegerToString(productGrupServcblDO.getProdGroupId());
						// k++;
					}
				}
				pincodeMasterTO.setGroupIds(grupIds);
				pincodeMasterTO.setGroup3Ids(grup3Ids);
				pincodeMasterTO.setGroup3cityList(grup3Cities);
				pincodeMasterTO.setGroup5cityList(grup5Cities);

				// set cityId
				pincodeMasterTO.setCityId(StringUtil
						.convertIntegerToString(pincodeDO.getCityId()));
				// set state id
				pincodeMasterTO.setStateId(StringUtil
						.convertIntegerToString(newCityTO.getState()));
				// set region id
				pincodeMasterTO.setRegionId(StringUtil
						.convertIntegerToString(newCityTO.getRegion()));

				List<StateTO> stateTOList = getStatesByRegionId(newCityTO
						.getRegion());
				List<CityTO> cityTOList = getCitysByStateId(newCityTO
						.getState());
				List<OfficeTO> officeList = getBranchesByCity(
						newCityTO.getRegion(), 5);

				pincodeMasterTO.setStateList(stateTOList);
				pincodeMasterTO.setCityList(cityTOList);
				pincodeMasterTO.setLocation(pincodeDO.getLocation());
				pincodeMasterTO.setServiceableBranchList(officeList);
				pincodeMasterTO.setStatus(pincodeDO.getStatus());
			}

		} catch (Exception e) {
			LOGGER.error("exception occurred in PincodeMasterServiceImpl-----searchPincode()", e);
		}
		LOGGER.debug("PincodeMasterserviceIMPL-------searchPincode()--END");
		return pincodeMasterTO;
	}

	public boolean activateDeactivatePincode(PinCodeMasterTO pincodeTo) {
		boolean flag = Boolean.FALSE;
		PincodeMasterDO pincodeDO = null;
		try {
			pincodeDO = pincodeMasterDao.searchPincodeDetls(pincodeTo
					.getPincodeNo());

			if (!StringUtil.isNull(pincodeDO)) {
				if (pincodeTo.getStatus().equalsIgnoreCase("A")) {
					pincodeDO.setStatus("A");
					pincodeDO.setUpdatedBy(pincodeTo.getUpdatedBy());
					pincodeDO.setUpdatedDate(DateUtil
							.stringToDDMMYYYYFormat(pincodeTo.getUpdatedate()));
					pincodeDO.setDtToBranch("N");
				} else if (pincodeTo.getStatus().equalsIgnoreCase("I")) {
					pincodeDO.setStatus("I");
					pincodeDO.setUpdatedBy(pincodeTo.getUpdatedBy());
					pincodeDO.setUpdatedDate(DateUtil
							.stringToDDMMYYYYFormat(pincodeTo.getUpdatedate()));
					pincodeDO.setDtToBranch("N");
				}

				flag = pincodeMasterDao.saveOrUpdatePincode(pincodeDO);

			}

		} catch (Exception e) {
			LOGGER.error("exception occurred in PincodeMasterServiceImpl-----activateDeactivatePincode()", e);;
		}
		return flag;

	}

	public List<CityTO> getAllCities() throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("PincodeMasterServiceImpl::getAllCities::START----->");
		try {
			return geographyCommonService.getAllCities();
		} catch (Exception ex) {
			LOGGER.error("ERROR : PincodeMasterServiceImpl::getAllCities", ex);
			throw new CGBusinessException("No Cities Found", ex);
		}
	}

	@Override
	public List<CityTO> getCitysByStateIdAndRegionID(Integer stateId,
			Integer regionId) throws CGBusinessException, CGSystemException {


		LOGGER.debug("PincodeMasterServiceImpl::getCitysByStateIdAndRegionID::START----->");
		List<CityTO> cityTO = null;
		try {
			List<CityDO> cityDO = pincodeMasterDao.getCitysByStateIdAndRegionID(stateId, regionId);

			if (!CGCollectionUtils.isEmpty(cityDO)) {

				cityTO = (List<CityTO>) CGObjectConverter
						.createTOListFromDomainList(cityDO, CityTO.class);
			} else {
				ExceptionUtil
						.prepareBusinessException(PincodeMasterErrorConstants.CITIES_NOT_FOUND);
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR : PincodeMasterServiceImpl::getCitysByStateIdAndRegionID",
					ex);
			throw ex;
		}
		LOGGER.debug("PincodeMasterServiceImpl::getCitysByStateIdAndRegionID::END----->");
		return cityTO;
}

	
}
