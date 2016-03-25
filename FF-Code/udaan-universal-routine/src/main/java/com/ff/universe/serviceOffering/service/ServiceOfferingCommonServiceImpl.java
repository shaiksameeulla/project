package com.ff.universe.serviceOffering.service;

/**
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingTypeTO;
import com.ff.domain.booking.BookingTypeDO;
import com.ff.domain.locationSearch.LocationSearchDO;
import com.ff.domain.manifest.LoadLotDO;
import com.ff.domain.serviceOffering.BookingTypeProductMapDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.ConsignmentTypeConfigDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.IdentityProofTypeDO;
import com.ff.domain.serviceOffering.InsuranceConfigDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.domain.serviceOffering.PaymentModeDO;
import com.ff.domain.serviceOffering.PrivilegeCardDO;
import com.ff.domain.serviceOffering.PrivilegeCardTransactionDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.serviceOffering.ProductGroupServiceabilityDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.domain.serviceOffering.RelationDO;
import com.ff.domain.serviceOffering.RemarksDO;
import com.ff.domain.serviceability.PincodeBranchServiceabilityCityNameDO;
import com.ff.domain.serviceability.PincodeBranchServiceabilityDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.global.RemarksTO;
import com.ff.manifest.LoadLotTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.BookingTypeProductMapTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeConfigTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuranceConfigTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.serviceOfferring.PrivilegeCardTO;
import com.ff.serviceOfferring.PrivilegeCardTransactionTO;
import com.ff.serviceOfferring.ProductGroupServiceabilityTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.serviceability.PincodeBranchServiceabilityCityNameTO;
import com.ff.serviceability.PincodeBranchServiceabilityTO;
import com.ff.to.serviceofferings.IdentityProofTypeTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.to.serviceofferings.RelationTO;
import com.ff.to.utilities.LocationSearchTO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.serviceOffering.dao.ServiceOfferingServiceDAO;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;

/**
 * @author uchauhan
 * 
 */
public class ServiceOfferingCommonServiceImpl implements
ServiceOfferingCommonService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ServiceOfferingCommonServiceImpl.class);
	private ServiceOfferingServiceDAO serviceOfferingServiceDAO;
	private GeographyCommonService geographyCommonService;
	private OrganizationCommonService organizationCommonService;

	/**
	 * @param serviceOfferingServiceDAO
	 *            the serviceOfferingServiceDAO to set
	 */
	public void setServiceOfferingServiceDAO(
			ServiceOfferingServiceDAO serviceOfferingServiceDAO) {
		this.serviceOfferingServiceDAO = serviceOfferingServiceDAO;
	}
	
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	/**
	 * @param organizationCommonService the organizationCommonService to set
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	@Override
	public List<ConsignmentTypeTO> getConsignmentType()
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("ServiceOfferingCommonServiceImpl::getConsignmentType----------->Start:::::::"+DateUtil.getCurrentTimeInMilliSeconds());
		List<ConsignmentTypeTO> consignmentTypeTOs = new ArrayList<>();
		List<ConsignmentTypeDO> consignmanetTypeDOs = new ArrayList<>();
		ConsignmentTypeTO consignmentTypeTO;
		consignmanetTypeDOs = serviceOfferingServiceDAO.getConsignmentType();
		for (ConsignmentTypeDO consignmanetTypeDO : consignmanetTypeDOs) {
			consignmentTypeTO = new ConsignmentTypeTO();
			consignmentTypeTO = (ConsignmentTypeTO) CGObjectConverter
					.createToFromDomain(consignmanetTypeDO, consignmentTypeTO);
			consignmentTypeTOs.add(consignmentTypeTO);

		}
		LOGGER.debug("ServiceOfferingCommonServiceImpl::getConsignmentType----------->end:::::::"+DateUtil.getCurrentTimeInMilliSeconds());
		return consignmentTypeTOs;
	}

	@Override
	public List<ConsignmentTypeTO> getConsignmentTypes(
			ConsignmentTypeTO consgTypeTO) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("ServiceOfferingCommonServiceImpl::getConsignmentTypes::START------------>:::::::"+DateUtil.getCurrentTimeInMilliSeconds());
		ConsignmentTypeDO consgTypeDO = new ConsignmentTypeDO();
		consgTypeDO.setConsignmentCode(consgTypeTO.getConsignmentCode());
		consgTypeDO.setConsignmentName(consgTypeTO.getConsignmentName());
		List<ConsignmentTypeTO> consignmentTypeTOs = new ArrayList<>();
		List<ConsignmentTypeDO> consignmanetTypeDOs = new ArrayList<>();

		ConsignmentTypeTO consignmentTypeTO;
		consignmanetTypeDOs = serviceOfferingServiceDAO
				.getConsignmentTypes(consgTypeDO);
		for (ConsignmentTypeDO consignmanetTypeDO : consignmanetTypeDOs) {
			consignmentTypeTO = new ConsignmentTypeTO();
			consignmentTypeTO = (ConsignmentTypeTO) CGObjectConverter
					.createToFromDomain(consignmanetTypeDO, consignmentTypeTO);
			consignmentTypeTOs.add(consignmentTypeTO);

		}
		LOGGER.debug("ServiceOfferingCommonServiceImpl::getConsignmentTypes::START------------>:::::::"+DateUtil.getCurrentTimeInMilliSeconds());
		return consignmentTypeTOs;
	}

	@Override
	public List<PaymentModeTO> getPaymentDetails() {
		List<PaymentModeTO> paymentModeTOs = new ArrayList<>();
		List<PaymentModeDO> paymentModeDOs = new ArrayList<>();
		PaymentModeTO paymentModeTO;
		try {
			paymentModeDOs = serviceOfferingServiceDAO.getPaymentMode();
			for (PaymentModeDO paymentModeDO : paymentModeDOs) {
				paymentModeTO = new PaymentModeTO();
				paymentModeTO = (PaymentModeTO) CGObjectConverter
						.createToFromDomain(paymentModeDO, paymentModeTO);
				paymentModeTOs.add(paymentModeTO);
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingCommonServiceImpl.getPaymentMode()",
					e);
		}

		return paymentModeTOs;
	}

	@Override
	public PaymentModeTO getPaymentDetails(Integer paymentId) {
		PaymentModeTO paymentModeTOs = new PaymentModeTO();
		PaymentModeDO paymentModeDOs = null;

		try {
			paymentModeDOs = serviceOfferingServiceDAO
					.getPaymentMode(paymentId);
			if (paymentModeDOs != null)
				paymentModeTOs = (PaymentModeTO) CGObjectConverter
				.createToFromDomain(paymentModeDOs, paymentModeTOs);

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingCommonServiceImpl.getPaymentMode()",
					e);
		}

		return paymentModeTOs;
	}

	@Override
	public List<CNContentTO> getContentValues() throws CGSystemException,
	CGBusinessException {
		List<CNContentTO> cnContentTOs = new ArrayList<>();
		CNContentTO cnContentTO = null;
		List<CNContentDO> cnContentDOs = new ArrayList<>();
		cnContentDOs = serviceOfferingServiceDAO.getContentValues();
		if (cnContentDOs != null && !cnContentDOs.isEmpty()) {
			for (CNContentDO cnContentDO : cnContentDOs) {
				cnContentTO = new CNContentTO();
				cnContentTO = (CNContentTO) CGObjectConverter
						.createToFromDomain(cnContentDO, cnContentTO);
				cnContentTOs.add(cnContentTO);
			}
		}
		return cnContentTOs;
	}

	@Override
	public List<CNPaperWorksTO> getPaperWorks(
			CNPaperWorksTO paperWorkValidationTO) throws CGSystemException,
			CGBusinessException {
		List<CNPaperWorksTO> cnPaperWorksTOs = null;
		CNPaperWorksTO cnPaperWorksTO = null;
		List<CNPaperWorksDO> cnPaperWorksDOs = null;
		cnPaperWorksDOs = serviceOfferingServiceDAO
				.getPaperWorks(paperWorkValidationTO);
		if (!CGCollectionUtils.isEmpty(cnPaperWorksDOs)) {
			cnPaperWorksTOs = new ArrayList<>(cnPaperWorksDOs.size());
			for (CNPaperWorksDO cnPaperWorksDO : cnPaperWorksDOs) {
				cnPaperWorksTO = new CNPaperWorksTO();
				cnPaperWorksTO = (CNPaperWorksTO) CGObjectConverter
						.createToFromDomain(cnPaperWorksDO, cnPaperWorksTO);
				cnPaperWorksTOs.add(cnPaperWorksTO);
			}
		}
		return cnPaperWorksTOs;
	}

	@Override
	public ProductTO getProductByConsgSeries(String consgSeries)
			throws CGSystemException {
		ProductDO product = null;
		ProductTO productTO = new ProductTO();
		try {
			product = serviceOfferingServiceDAO
					.getProductByConsgSeries(consgSeries);
			if (product != null)
				productTO = (ProductTO) CGObjectConverter.createToFromDomain(
						product, productTO);
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR : ServiceOfferingCommonServiceImpl.getProductByConsgSeries()",
					e);
		}
		return productTO;
	}

	@Override
	public List<ProductTO> getAllBookingProductMapping(String bookingType)
			throws CGBusinessException, CGSystemException {
		List<BookingTypeProductMapDO> productMap = null;
		List<ProductTO> productTOs = null;
		List<ProductDO> productDOs = null;
		try {
			productMap = serviceOfferingServiceDAO.getAllBookingProductMapping(bookingType);
			if (!StringUtil.isEmptyList(productMap)) {
				productDOs = new ArrayList<ProductDO>(productMap.size());
				 //productTOs = new ArrayList<ProductTO>(productMap.size());
				for (BookingTypeProductMapDO prdMap : productMap) {
					productDOs.add(prdMap.getProduct());
				}
				productTOs = (List<ProductTO>) CGObjectConverter
						.createTOListFromDomainList(productDOs, ProductTO.class);
			}
			
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR : ServiceOfferingCommonServiceImpl.getProductByConsgSeries()",
					e);
		}
		return productTOs;
	}

	@Override
	public PrivilegeCardTO getPrivilegeCardDtls(String privilegeCardNo)
			throws CGSystemException {
		PrivilegeCardTO privgCardTO = null;
		PrivilegeCardDO privgCardDO = null;
		try {
			privgCardDO = serviceOfferingServiceDAO
					.getPrivilegeCardDtls(privilegeCardNo);
			if (privgCardDO != null) {
				privgCardTO = new PrivilegeCardTO();
				privgCardTO = (PrivilegeCardTO) CGObjectConverter
						.createToFromDomain(privgCardDO, privgCardTO);
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR : ServiceOfferingCommonServiceImpl.getPrivilegeCardDtls()",
					e);
		}
		return privgCardTO;
	}

	@Override
	public BookingTypeProductMapTO getBookingTypeProductMap(String bookingType)
			throws CGSystemException {
		BookingTypeProductMapTO bookingProductMapTO = null;
		BookingTypeProductMapDO bookingProductMapDO = null;
		try {
			bookingProductMapDO = serviceOfferingServiceDAO
					.getBookingProductMapping(bookingType);
			if (bookingProductMapDO != null) {
				bookingProductMapTO = new BookingTypeProductMapTO();
				bookingProductMapTO = (BookingTypeProductMapTO) CGObjectConverter
						.createToFromDomain(bookingProductMapDO,
								bookingProductMapTO);
				BookingTypeDO bookingTypeDO = bookingProductMapDO
						.getBookingType();
				if (bookingTypeDO != null) {
					BookingTypeTO bookingTypeTO = new BookingTypeTO();
					bookingTypeTO = (BookingTypeTO) CGObjectConverter
							.createToFromDomain(bookingTypeDO, bookingTypeTO);
					bookingProductMapTO.setBookingTypeTO(bookingTypeTO);
				}
				ProductDO productDO = bookingProductMapDO.getProduct();
				if (productDO != null) {
					ProductTO productTO = new ProductTO();
					productTO = (ProductTO) CGObjectConverter
							.createToFromDomain(productDO, productTO);
					bookingProductMapTO.setProductTO(productTO);
				}
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR : ServiceOfferingCommonServiceImpl.getProductByConsgSeries()",
					e);
		}
		return bookingProductMapTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PrivilegeCardTransactionTO> getPrivilegeCardTransDtls(
			String privilegeCardNo) throws CGSystemException {
		
		List<PrivilegeCardTransactionTO> privgCardTransDtlsTOs = null;
		List<PrivilegeCardTransactionDO> privgCardTransDtlsDOs = null;
		
		PrivilegeCardTO privgCardTO = null;
		try {
			privgCardTransDtlsDOs = serviceOfferingServiceDAO
					.getPrivilegeCardTransDtls(privilegeCardNo);
			if (!StringUtil.isEmptyList(privgCardTransDtlsDOs)) {
				privgCardTransDtlsTOs = (List<PrivilegeCardTransactionTO>) CGObjectConverter
						.createTOListFromDomainList(privgCardTransDtlsDOs,
								PrivilegeCardTransactionTO.class);
				PrivilegeCardDO privgCardDO = privgCardTransDtlsDOs.get(0)
						.getPrivilegeCard();
				if (privgCardDO != null) {
					privgCardTO = new PrivilegeCardTO();
					privgCardTO = (PrivilegeCardTO) CGObjectConverter
							.createToFromDomain(privgCardDO, privgCardTO);
				}
				for (PrivilegeCardTransactionTO priviCard : privgCardTransDtlsTOs) {
					priviCard.setPrivilegeCardTO(privgCardTO);
					/*
					 * BookingTypeDO bookingTypeDO = privgCardTransDtlsDO
					 * .getBookingType(); if (bookingTypeDO != null) {
					 * BookingTypeTO bookingTypeTO = new BookingTypeTO();
					 * bookingTypeTO = (BookingTypeTO) CGObjectConverter
					 * .createToFromDomain(bookingTypeDO, bookingTypeTO);
					 * privgCardTransDtlsTO.setBookingTypeTO(bookingTypeTO); }
					 */
				}

			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR : ServiceOfferingCommonServiceImpl.getPrivilegeCardTransDtls()",
					e);
		}
		return privgCardTransDtlsTOs;
	}

	@Override
	public List<InsuredByTO> getInsuarnceBy() throws CGSystemException,
	CGBusinessException {
		List<InsuredByTO> insuredByTOs = null;
		List<InsuredByDO> insuredByDOs = null;
		try {
			insuredByDOs = serviceOfferingServiceDAO.getInsuredBy();
			if (!StringUtil.isEmptyList(insuredByDOs)) {
				insuredByTOs = new ArrayList<InsuredByTO>();
				insuredByTOs = (List<InsuredByTO>) CGObjectConverter
						.createTOListFromDomainList(insuredByDOs,
								InsuredByTO.class);

			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR : ServiceOfferingCommonServiceImpl.getInsuarnceBy()",
					e);
			throw new CGBusinessException(e.getMessage());
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR : ServiceOfferingCommonServiceImpl.getInsuarnceBy()",
					e);
			throw new CGSystemException(e);
		}
		return insuredByTOs;
	}

	@Override
	public List<InsuranceConfigTO>  getInsuarnceConfigDtls(Double declarebValue,
			String bookingType) throws CGSystemException, CGBusinessException {
		InsuranceConfigDO insuredByDO = null;
		List<InsuranceConfigTO> insuredByTO=null;
	List<InsuranceConfigDO> insuranceConfigDOs = serviceOfferingServiceDAO.getInsuarnceConfigDtls(
				declarebValue, bookingType);
		if (!CGCollectionUtils.isEmpty(insuranceConfigDOs)) {
		 insuredByTO = (List<InsuranceConfigTO>) CGObjectConverter
					.createTOListFromDomainList(insuranceConfigDOs, InsuranceConfigTO.class);
		} else{
			insuredByTO=new ArrayList<InsuranceConfigTO>();
			InsuranceConfigTO configTO=new InsuranceConfigTO();
			configTO.setTrasnStatus("NOINSURENCEMAPPING");
			insuredByTO.add(configTO);
		}
			
		return insuredByTO;
	}

	@Override
	public InsuranceConfigTO validateInsuarnceConfigDtls(Double declarebValue,
			String bookingType, Integer insuredById) throws CGSystemException,
			CGBusinessException {
		InsuranceConfigTO insuredByTO = new InsuranceConfigTO();
		InsuranceConfigDO insuredByDO = null;
		insuredByDO = serviceOfferingServiceDAO.validateInsuarnceConfigDtls(
				declarebValue, bookingType, insuredById);
		if (!StringUtil.isNull(insuredByDO)) {
			insuredByTO = (InsuranceConfigTO) CGObjectConverter
					.createToFromDomain(insuredByDO, insuredByTO);
		} else
			insuredByTO.setTrasnStatus("NOINSURENCEMAPPING");
		return insuredByTO;
	}

	@Override
	public ConsignmentTypeConfigTO getConsgTypeConfigDtls(
			ConsignmentTypeConfigTO consgTypeConfigTO)
					throws CGSystemException, CGBusinessException {
		ConsignmentTypeConfigTO configTO = null;
		ConsignmentTypeConfigDO configDO = null;
		try {
			configDO = serviceOfferingServiceDAO
					.getConsgTypeConfigDtls(consgTypeConfigTO);
			if (!StringUtil.isNull(configDO)) {
				configTO = new ConsignmentTypeConfigTO();
				configTO.setConsignmentTypeConfigId(configDO
						.getConsignmentTypeConfigId());
				configTO.setDeclaredValue(configDO.getDeclaredValue());
				configTO.setDocId(configDO.getConsignmentId()
						.getConsignmentId());
				if(!StringUtil.isNull(configDO.getConsignmentId())){
					configTO.setDocType(configDO.getConsignmentId()
							.getConsignmentName());
					configTO.setConsignmentType(configDO.getConsignmentId().getConsignmentCode());
				}
				configTO.setIsPaperworkMandatory(configDO
						.getIsPaperworkMandatory());
			}

		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR : ServiceOfferingCommonServiceImpl.getConsgTypeConfigDtls()",
					e);
			throw new CGSystemException(e);
		}
		return configTO;
	}
	@Override
	public List<ConsignmentTypeConfigTO> getConsgTypeConfigDtlsByConsgCongType(
			ConsignmentTypeConfigTO consgTypeConfigTO)
					throws CGSystemException, CGBusinessException {
		List<ConsignmentTypeConfigTO> configTOList = null;
		List<ConsignmentTypeConfigDO> configDOList = null;
		try {
			configDOList = serviceOfferingServiceDAO
					.getConsgTypeConfigDtlsByConsgConfigType(consgTypeConfigTO);
			if (!CGCollectionUtils.isEmpty(configDOList)) {
				configTOList=new ArrayList<>(configDOList.size());
				for(ConsignmentTypeConfigDO consgConfigDO:configDOList){
					ConsignmentTypeConfigTO configTO = new ConsignmentTypeConfigTO();
					configTO.setConsignmentTypeConfigId(consgConfigDO
							.getConsignmentTypeConfigId());
					configTO.setDeclaredValue(consgConfigDO.getDeclaredValue());
					configTO.setDocId(consgConfigDO.getConsignmentId()
							.getConsignmentId());
					if(!StringUtil.isNull(consgConfigDO.getConsignmentId())){
						configTO.setDocType(consgConfigDO.getConsignmentId()
								.getConsignmentName());
						configTO.setConsignmentType(consgConfigDO.getConsignmentId().getConsignmentCode());
					}
					configTO.setIsPaperworkMandatory(consgConfigDO
							.getIsPaperworkMandatory());
					configTOList.add(configTO);
				}
			}

		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR : ServiceOfferingCommonServiceImpl.getConsgTypeConfigDtls()",
					e);
			throw new CGSystemException(e);
		}
		return configTOList;
	}

	@Override
	public boolean isProductServicedByBooking(String bookingType,
			String congSeries) throws CGBusinessException, CGSystemException {
		return serviceOfferingServiceDAO.isProductServicedByBooking(
				bookingType, congSeries);
	}

	@Override
	public boolean isNormalProductServicedByBooking(String bookingType,
			String prodCode) throws CGSystemException {
		return serviceOfferingServiceDAO.isNormalProductServicedByBooking(
				bookingType, prodCode);
	}

	@Override
	public CNPaperWorksTO getPaperWorkByPincode(String pincode,
			String paperWorkName) throws CGSystemException, CGBusinessException {
		CNPaperWorksTO cnPaperWorksTO = null;
		CNPaperWorksDO cnPaperWorksDO = null;
		cnPaperWorksDO = serviceOfferingServiceDAO.getPaperWorkByPincode(
				pincode, paperWorkName);
		if (!StringUtil.isNull(cnPaperWorksDO)) {
			cnPaperWorksTO = new CNPaperWorksTO();
			cnPaperWorksTO = (CNPaperWorksTO) CGObjectConverter
					.createToFromDomain(cnPaperWorksDO, cnPaperWorksTO);
		}
		return cnPaperWorksTO;
	}

	@Override
	public CNContentTO getCNContentByName(String cnContentName)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("ServiceOfferingCommonServiceImpl::getCNContentByName----------->Start:::::::");
		CNContentTO cnContentTO = null;
		CNContentDO cnContentDO = null;
		cnContentDO = serviceOfferingServiceDAO
				.getCNContentByName(cnContentName);
		if (!StringUtil.isNull(cnContentDO)) {
			cnContentTO = new CNContentTO();
			cnContentTO = (CNContentTO) CGObjectConverter.createToFromDomain(
					cnContentDO, cnContentTO);
		}
		LOGGER.debug("ServiceOfferingCommonServiceImpl::getCNContentByName----------->END:::::::");
		return cnContentTO;
	}

	@Override
	public InsuredByTO getInsuredByNameOrCode(String insuredByName,
			String insuredByCode, Integer insuredById)
					throws CGSystemException, CGBusinessException {
		LOGGER.debug("ServiceOfferingCommonServiceImpl::getInsuredByNameOrCode::START------------>:::::::",DateUtil.getCurrentTimeInMilliSeconds());
		InsuredByTO insuredByTO = null;
		InsuredByDO insuredByDO = null;
		insuredByDO = serviceOfferingServiceDAO.getInsuredByNameOrCode(
				insuredByName, insuredByCode, insuredById);
		if (!StringUtil.isNull(insuredByDO)) {
			insuredByTO = new InsuredByTO();
			insuredByTO = (InsuredByTO) CGObjectConverter.createToFromDomain(
					insuredByDO, insuredByTO);

		}
		LOGGER.debug("ServiceOfferingCommonServiceImpl::getInsuredByNameOrCode::END------------>:::::::",DateUtil.getCurrentTimeInMilliSeconds());
		return insuredByTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.serviceOffering.service.ServiceOfferingCommonService#
	 * getConsgType(com.ff.serviceOfferring.ConsignmentTypeTO)
	 */
	@Override
	public ConsignmentTypeTO getConsgType(ConsignmentTypeTO consignmentTypeTO)
			throws CGSystemException, CGBusinessException {
		ConsignmentTypeTO consgTypeTO = null;
		ConsignmentTypeDO consignmentTypeDO = serviceOfferingServiceDAO
				.getConsgType(consignmentTypeTO);
		if (consignmentTypeDO != null) {
			consgTypeTO = new ConsignmentTypeTO();
			consgTypeTO = (ConsignmentTypeTO) CGObjectConverter
					.createToFromDomain(consignmentTypeDO, consgTypeTO);
		}
		return consgTypeTO;
	}

	@Override
	public Map<String, String> getStandardTypesAsMap(String typeName)
			throws CGSystemException, CGBusinessException {
		List<?> stdTypeMap = null;
		Map<String, String> stockStdTypeMap = null;
		stdTypeMap = serviceOfferingServiceDAO.getStandardTypesAsMap(typeName);
		stockStdTypeMap = prepareMapFromStdType(stdTypeMap);
		return stockStdTypeMap;
	}

	/**
	 * Prepare map from std type.
	 * 
	 * @param standardTypeList
	 *            the standard type list
	 * @return the map
	 */
	private Map<String, String> prepareMapFromStdType(List<?> standardTypeList) {
		Map<String, String> itemTypeMap = null;
		if (!StringUtil.isEmptyList(standardTypeList)) {
			itemTypeMap = new HashMap<String, String>(standardTypeList.size());
			for (Object itemType : standardTypeList) {
				Map map = (Map) itemType;
				itemTypeMap.put(
						(String) map.get(StockUniveralConstants.TYPE_ID),
						(String) map.get(StockUniveralConstants.TYPE_NAME));
			}
		}
		return itemTypeMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoadLotTO> getLoadNo() throws CGBusinessException,
	CGSystemException {
		List<LoadLotTO> loadLotTOList = null;
		List<LoadLotDO> LoadLotDOList = serviceOfferingServiceDAO.getLoadNo();
		if (!CGCollectionUtils.isEmpty(LoadLotDOList)) {
			loadLotTOList = (List<LoadLotTO>) CGObjectConverter
					.createTOListFromDomainList(LoadLotDOList, LoadLotTO.class);
		}
		return loadLotTOList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.serviceOffering.service.ServiceOfferingCommonService#
	 * getReasonsByReasonType(com.ff.to.serviceofferings.ReasonTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReasonTO> getReasonsByReasonType(ReasonTO reasonTO)
			throws CGBusinessException, CGSystemException {
		List<ReasonDO> reasonDOs = serviceOfferingServiceDAO
				.getReasonsByReasonType(reasonTO);
		List<ReasonTO> reasonTOs = null;
		if (!StringUtil.isEmptyColletion(reasonDOs)) {
			reasonTOs = (List<ReasonTO>) CGObjectConverter
					.createTOListFromDomainList(reasonDOs, ReasonTO.class);
		}
		return reasonTOs;
	}

	/**
	 * Gets the all relations.
	 * 
	 * @param inputTo
	 *            the input to
	 * @return the all relations
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RelationTO> getAllRelations(RelationTO inputTo)
			throws CGBusinessException, CGSystemException {
		List<RelationTO> relationTOList = null;
		List<RelationDO> relationDOList = null;
		RelationDO relationDO = null;
		if (!StringUtil.isNull(inputTo)) {
			relationDO = new RelationDO();
			CGObjectConverter.createDomainFromTo(inputTo, relationDO);
			relationDOList = serviceOfferingServiceDAO
					.getAllRelations(relationDO);
		}

		if (!CGCollectionUtils.isEmpty(relationDOList)) {
			relationTOList = (List<RelationTO>) CGObjectConverter
					.createTOListFromDomainList(relationDOList,
							RelationTO.class);
		}
		return relationTOList;
	}

	/**
	 * Gets the all identity proofs.
	 * 
	 * @param inputTo
	 *            the input to
	 * @return the all identity proofs
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IdentityProofTypeTO> getAllIdentityProofs(
			IdentityProofTypeTO inputTo) throws CGBusinessException,
			CGSystemException {
		List<IdentityProofTypeTO> idProofTypeToList = null;
		List<IdentityProofTypeDO> idProofTypeDOList = null;
		IdentityProofTypeDO idProofDO = null;
		if (!StringUtil.isNull(inputTo)) {
			idProofDO = new IdentityProofTypeDO();
			CGObjectConverter.createDomainFromTo(inputTo, idProofDO);
			idProofTypeDOList = serviceOfferingServiceDAO
					.getAllIdentityProofs(idProofDO);
		}

		if (!CGCollectionUtils.isEmpty(idProofTypeDOList)) {
			idProofTypeToList = (List<IdentityProofTypeTO>) CGObjectConverter
					.createTOListFromDomainList(idProofTypeDOList,
							IdentityProofTypeTO.class);
		}
		return idProofTypeToList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentModeTO> getPaymentModeDtls(String processCode)
			throws CGBusinessException, CGSystemException {
		List<PaymentModeTO> paymentModeTOs = new ArrayList<>();
		List<PaymentModeDO> paymentModeDOs = new ArrayList<>();
		try {
			paymentModeDOs = serviceOfferingServiceDAO
					.getPaymentModeDtls(processCode);
			paymentModeTOs = (List<PaymentModeTO>) CGObjectConverter
					.createTOListFromDomainList(paymentModeDOs,
							PaymentModeTO.class);
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR : ServiceOfferingCommonServiceImpl.getPaymentModeDtls()",
					e);
			throw new CGBusinessException(e.getMessage());
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingCommonServiceImpl.getPaymentModeDtls()",
					e);
			throw new CGSystemException(e);
		}
		return paymentModeTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaymentModeTO getPaymentMode(String processCode, String payModeCode)
			throws CGBusinessException, CGSystemException {
		PaymentModeTO paymentModeTO = null;
		PaymentModeDO paymentModeDO = null;
		try {
			paymentModeDO = serviceOfferingServiceDAO.getPaymentMode(
					processCode, payModeCode);
			if (!StringUtil.isNull(paymentModeDO)) {
				paymentModeTO = new PaymentModeTO();
				paymentModeTO = (PaymentModeTO) CGObjectConverter
						.createToFromDomain(paymentModeDO, paymentModeTO);
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR : ServiceOfferingCommonServiceImpl.getPaymentModeDtls()",
					e);
			throw new CGBusinessException(e.getMessage());
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingCommonServiceImpl.getPaymentModeDtls()",
					e);
			throw new CGSystemException(e);
		}
		return paymentModeTO;
	}

	@Override
	public CNContentTO getCNContentByCodeOrId(Integer contentId,
			String contentCode) throws CGSystemException, CGBusinessException {
		CNContentTO cnContentTO = null;
		CNContentDO cnContentDO = null;
		cnContentDO = serviceOfferingServiceDAO.getCNContentByCodeOrId(
				contentId, contentCode);
		if (!StringUtil.isNull(cnContentDO)) {
			cnContentTO = new CNContentTO();
			cnContentTO = (CNContentTO) CGObjectConverter.createToFromDomain(
					cnContentDO, cnContentTO);
		}
		return cnContentTO;
	}

	@Override
	public CNPaperWorksTO getPaperWorkByCodeOrId(Integer paperWorkId,
			String paperWorkCode) throws CGSystemException, CGBusinessException {
		CNPaperWorksTO cnPaperWorksTO = null;
		CNPaperWorksDO cnPaperWorksDO = null;
		cnPaperWorksDO = serviceOfferingServiceDAO.getPaperWorkByCodeOrId(
				paperWorkId, paperWorkCode);
		if (!StringUtil.isNull(cnPaperWorksDO)) {
			cnPaperWorksTO = new CNPaperWorksTO();
			cnPaperWorksTO = (CNPaperWorksTO) CGObjectConverter
					.createToFromDomain(cnPaperWorksDO, cnPaperWorksTO);
		}
		return cnPaperWorksTO;
	}

	public List<ProductTO> getAllProducts() throws CGBusinessException,
	CGSystemException {
		List<ProductDO> products = null;
		List<ProductTO> productTOs = null;
		try {
			products = serviceOfferingServiceDAO.getAllProducts();
			if (!StringUtil.isEmptyList(products)) {
				productTOs = new ArrayList<ProductTO>();
				productTOs = (List<ProductTO>) CGObjectConverter
						.createTOListFromDomainList(products, ProductTO.class);
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR : ServiceOfferingCommonServiceImpl.getProductByConsgSeries()",
					e);
		}
		return productTOs;
	}

	@Override
	public List<ProductTO> getProductDetailsByProduct(ProductTO productTO) throws CGBusinessException,
	CGSystemException {
		List<ProductDO> products = null;
		List<ProductTO> productTOs = null;
		ProductDO productDO=null;
		if(!StringUtil.isNull(productTO)){
			productDO=new ProductDO();
			CGObjectConverter.createDomainFromTo(productTO,productDO);

			products = serviceOfferingServiceDAO.getProductByProduct(productDO);
			if (!StringUtil.isEmptyList(products)) {
				productTOs = (List<ProductTO>) CGObjectConverter
						.createTOListFromDomainList(products, ProductTO.class);
			}
		}
		return productTOs;
	}

	@Override
	public List<ProductGroupServiceabilityTO> getAllProductGroup()
			throws CGBusinessException, CGSystemException {
		List<ProductGroupServiceabilityDO> serviceabilityDOs = null;
		List<ProductGroupServiceabilityTO> serviceabilityTOs = null;
		try {
			serviceabilityDOs = serviceOfferingServiceDAO.getAllProductGroup();
			if (!StringUtil.isEmptyList(serviceabilityDOs)) {
				serviceabilityTOs = new ArrayList<ProductGroupServiceabilityTO>();
				serviceabilityTOs = (List<ProductGroupServiceabilityTO>) CGObjectConverter
						.createTOListFromDomainList(serviceabilityDOs, ProductGroupServiceabilityTO.class);
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR : ServiceOfferingCommonServiceImpl.getProductByConsgSeries()",
					e);
		}
		return serviceabilityTOs;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<RemarksTO> getRemarksByType(String remarkType)
			throws CGBusinessException, CGSystemException {
		List<RemarksDO> remarksDOs = null;
		List<RemarksTO> remarksTOs = null;
		try {
			remarksDOs = serviceOfferingServiceDAO.getRemarksByType(remarkType);
			if (!StringUtil.isEmptyList(remarksDOs)) {
				remarksTOs = new ArrayList<RemarksTO>();
				remarksTOs = (List<RemarksTO>) CGObjectConverter
						.createTOListFromDomainList(remarksDOs, RemarksTO.class);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR : ServiceOfferingCommonServiceImpl.getRemarksByType()",e);
		}
		return remarksTOs;
	}

	@Override
	public PrivilegeCardTransactionTO getprivilegeCardDtls(String consgNo)
			throws CGBusinessException, CGSystemException {
		PrivilegeCardTransactionDO cardTransactionDO = null;
		PrivilegeCardTransactionTO transactionTO = null;
		try {
			cardTransactionDO = serviceOfferingServiceDAO.getprivilegeCardDtls(consgNo);
			if (!StringUtil.isNull(cardTransactionDO)) {
				transactionTO = new PrivilegeCardTransactionTO();
				transactionTO = (PrivilegeCardTransactionTO) CGObjectConverter
						.createToFromDomain(cardTransactionDO, transactionTO);
				PrivilegeCardTO cardTO=new PrivilegeCardTO();
				cardTO.setPrivilegeCardId(cardTransactionDO.getPrivilegeCard().getPrivilegeCardId());
				cardTO.setPrivilegeCardNo(cardTransactionDO.getPrivilegeCard().getPrivilegeCardNo());
				transactionTO.setPrivilegeCardTO(cardTO);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR : ServiceOfferingCommonServiceImpl.getRemarksByType()",e);
		}
		return transactionTO;
	}

	@Override
	public ProductDO getProductByProductId(Integer productId)
			throws CGSystemException, CGBusinessException {
		return serviceOfferingServiceDAO.getProductByProductId(productId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PincodeBranchServiceabilityTO> getAllServicingOfficebyPincode(String pincode)
			throws CGBusinessException, CGSystemException {
		List<PincodeBranchServiceabilityDO>  dosList = serviceOfferingServiceDAO.getAllServicingOfficebyPincode(pincode);
		
		
		/*List<PincodeBranchServiceabilityTO> toList = (List<PincodeBranchServiceabilityTO>) CGObjectConverter
				.createTOListFromDomainList(dosList, PincodeBranchServiceabilityTO.class);*/
		
		List<PincodeBranchServiceabilityTO> pincodeTOs = new ArrayList<>();
		
		//List<PincodeBranchServiceabilityDO>  dosList1 = (List<PincodeBranchServiceabilityDO>)dosList;
		
		for (PincodeBranchServiceabilityDO pincodeBranchServiceabilityDO : dosList) {
			PincodeBranchServiceabilityTO pincodeTO = new PincodeBranchServiceabilityTO();
			pincodeTO = pincodeConverter(pincodeBranchServiceabilityDO);
			pincodeTOs.add(pincodeTO);
		}
		return pincodeTOs;
	}
	
	
	
	private PincodeBranchServiceabilityTO pincodeConverter(PincodeBranchServiceabilityDO pincodeDO)
			throws CGBusinessException,CGSystemException{
		LOGGER.debug("ServiceOfferingCommonServiceImpl::pincodeConverter::START----->");
		PincodeBranchServiceabilityTO pincodeBranchServiceabilityTO = new PincodeBranchServiceabilityTO();
		
		OfficeTO officeTO = new OfficeTO();
		CGObjectConverter.createToFromDomain(pincodeDO.getOffice(), officeTO);
		//CityTO cityTO = new CityTO();
		CityTO cityTo = geographyCommonService.getCityByPincodeId(pincodeDO.getPincode().getPincodeId());
		officeTO.setCityName(cityTo.getCityName());
		//CGObjectConverter.createToFromDomain(pincodeDO.getCity(), cityTO);
		RegionTO regionTO = new RegionTO();
		CGObjectConverter.createToFromDomain(pincodeDO.getOffice().getMappedRegionDO(), regionTO);
		officeTO.setRegionTO(regionTO);
		
		pincodeBranchServiceabilityTO.setOfficeTO(officeTO);
		
		PincodeTO pincodeTO2 = new PincodeTO();
		// CGObjectConverter.createToFromDomain(pincodeDO.getPincode() , pincodeTO2);
		pincodeTO2.setPincodeId(pincodeDO.getPincode().getPincodeId());
		pincodeTO2.setPincode(pincodeDO.getPincode().getPincode());
		pincodeTO2.setCityId(pincodeDO.getPincode().getCityId());
		
		pincodeBranchServiceabilityTO.setPincodeTO(pincodeTO2);
		
		pincodeBranchServiceabilityTO.setCreatedBy(pincodeDO.getCreatedBy());
		pincodeBranchServiceabilityTO.setUpdateBy(pincodeDO.getUpdateBy());
		pincodeBranchServiceabilityTO.setCreationDate(pincodeDO.getCreatedDate());
		pincodeBranchServiceabilityTO.setUpdateDate(pincodeDO.getUpdateDate());
		pincodeBranchServiceabilityTO.setDtToBranch(pincodeDO.getDtToBranch());
		pincodeBranchServiceabilityTO.setStatus(pincodeDO.getStatus());
		
		LOGGER.debug("ServiceOfferingCommonServiceImpl::pincodeConverter::END----->");
		return pincodeBranchServiceabilityTO;
	}

	/*private static String getCityNameForStation(Integer cityId){
		
		return "";
	}*/
	
	@Override
	public List<PincodeBranchServiceabilityTO> getAllServicingOfficebyOfficeId(
			String officeCode) throws CGBusinessException, CGSystemException {
		List<PincodeBranchServiceabilityDO>  dosList = serviceOfferingServiceDAO.getAllServicingOfficebyBranch(officeCode);
		/*List<PincodeBranchServiceabilityTO> toList = (List<PincodeBranchServiceabilityTO>) CGObjectConverter
				.createTOListFromDomainList(dosList, PincodeBranchServiceabilityTO.class);*/
		
		List<PincodeBranchServiceabilityTO> pincodeTOs = new ArrayList<>();
		
		for (PincodeBranchServiceabilityDO pincodeBranchServiceabilityDO : dosList) {
			PincodeBranchServiceabilityTO pincodeTO = new PincodeBranchServiceabilityTO();
			pincodeTO = pincodeConverter(pincodeBranchServiceabilityDO);
			pincodeTOs.add(pincodeTO);
		}
		return pincodeTOs;

	}
	
	public List<RegionTO> getAllRegionsList() throws CGBusinessException, CGSystemException {
		List<RegionTO> regionTOs = geographyCommonService.getAllRegions();
		return regionTOs;
	}
	
	public List<CityTO> getAllCites() throws CGBusinessException, CGSystemException  {
		List<CityTO> cityTOs = geographyCommonService.getAllCities();
		return cityTOs;
	}
	
	@Override
	public List<OfficeTO> getAllOfficesByCityId(Integer cityId) throws CGBusinessException,
			CGSystemException {
		List<OfficeTO> officeTOs = organizationCommonService.getAllOfficesByCity(cityId);
		return officeTOs;
	}
	
	@Override
	public List<OfficeTO> getAllBranchOfficesByCityId(Integer cityId) throws CGBusinessException,
			CGSystemException {
		return organizationCommonService.getAllBranchOfficesByCity(cityId);
	}
	
	@Override
	public List<OfficeTO> getAllBranchAndStandaloneOfficesByCity(Integer cityId) throws CGBusinessException,
			CGSystemException {
		return organizationCommonService.getAllBranchAndStandaloneOfficesByCity(cityId);
	}

	@Override
	public List<PincodeBranchServiceabilityTO> getAllPincodesByOfficeId(Integer officeId)
			throws CGBusinessException, CGSystemException {
		List<PincodeBranchServiceabilityDO> pincodeDOs = serviceOfferingServiceDAO.getAllPincodesByOfficeId(officeId);
		List<PincodeBranchServiceabilityTO> pincodeTOs = new ArrayList<>();
		
		for (PincodeBranchServiceabilityDO pincodeBranchServiceabilityDO : pincodeDOs) {
			PincodeBranchServiceabilityTO pincodeTO = new PincodeBranchServiceabilityTO();
			pincodeTO = pincodeConverter(pincodeBranchServiceabilityDO);
			pincodeTOs.add(pincodeTO);
		}
		return pincodeTOs;
	}
	
	@Override
	public List<String> getAllServicingSearchLocation() throws CGBusinessException, CGSystemException {
		return serviceOfferingServiceDAO.getServicingLocationSearch();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LocationSearchTO> getlocationDetails(String locationAddrs, String cityName, OfficeTO loggedInOffice ) throws CGBusinessException, CGSystemException {
		LOGGER.debug("ServiceOfferingCommonServiceImpl::getlocationDetails::START----->");
		List<LocationSearchTO> locationTos=new ArrayList<>();
		List<LocationSearchDO>  locationDos = serviceOfferingServiceDAO.getServicingLocationDetails(locationAddrs, cityName, loggedInOffice);
		
		if(!StringUtil.isNull(locationDos)) {
			locationTos = (List<LocationSearchTO>)CGObjectConverter.createTOListFromDomainList(locationDos, LocationSearchTO.class);
		}
		LOGGER.debug("ServiceOfferingCommonServiceImpl::getlocationDetails::END----->");
		return locationTos;
	}
  
	@SuppressWarnings("unchecked")
	@Override
	public List<PincodeBranchServiceabilityCityNameTO> getAllServicingOfficebyPincodeCity(String pincode)
			throws CGBusinessException, CGSystemException {
		
		List<PincodeBranchServiceabilityCityNameDO> listvaluesDos=serviceOfferingServiceDAO.getAllServicingOfficebyPincodeCity(pincode);
		List<PincodeBranchServiceabilityCityNameTO> listvaluesTos2=new ArrayList<PincodeBranchServiceabilityCityNameTO>(listvaluesDos.size());
		
		if(!StringUtil.isNull(listvaluesDos)){
			listvaluesTos2 = (List<PincodeBranchServiceabilityCityNameTO>)CGObjectConverter.createTOListFromDomainList(listvaluesDos, PincodeBranchServiceabilityCityNameTO.class);
			
		}
		
		LOGGER.debug("ServiceOfferingCommonServiceImpl::getlocationDetails::END----->");
		return listvaluesTos2;
	}

	@Override
	public ConsignmentTypeDO getConsignmentTypeByCode(String consignmentTypeCode)
			throws CGBusinessException, CGSystemException {
		return serviceOfferingServiceDAO.getConsignmentTypeByCode(consignmentTypeCode);
	}
	
	
}
