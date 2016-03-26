package com.ff.web.booking.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.exception.MessageType;
import com.capgemini.lbs.framework.exception.MessageWrapper;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.utility.TwoWayWriteProcessCall;
import com.ff.booking.BookingParcelTO;
import com.ff.booking.BookingPaymentTO;
import com.ff.booking.BookingResultTO;
import com.ff.booking.BookingTO;
import com.ff.booking.BookingTypeConfigTO;
import com.ff.booking.BookingTypeTO;
import com.ff.booking.BookingValidationTO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.business.CustomerTO;
import com.ff.consignment.ChildConsignmentTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingPaymentDO;
import com.ff.domain.booking.BookingTypeConfigDO;
import com.ff.domain.booking.BookingTypeDO;
import com.ff.domain.booking.BookingWrapperDO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.domain.serviceOffering.PrivilegeCardTransactionDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeProductServiceabilityTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupBookingTO;
import com.ff.pickup.PickupDeliveryLocationTO;
import com.ff.pickup.PickupRunsheetTO;
import com.ff.rate.calculation.service.RateCalculationUniversalService;
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
import com.ff.serviceOfferring.ProductTO;
import com.ff.serviceOfferring.VolumetricWeightTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.to.rate.RateCalculationInputTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.umc.ApplScreensTO;
import com.ff.universe.booking.service.BookingUniversalService;
import com.ff.universe.business.service.BusinessCommonService;
import com.ff.universe.consignment.dao.ConsignmentCommonDAO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.pickup.service.PickupManagementCommonService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.universe.tracking.service.TrackingUniversalService;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.constants.BookingErrorCodesConstants;
import com.ff.web.booking.dao.BookingCommonDAO;
import com.ff.web.booking.utils.BookingUtils;
import com.ff.web.booking.validator.BookingValidator;
import com.ff.web.common.UdaanCommonErrorCodes;
import com.ff.web.consignment.service.ConsignmentService;
import com.ff.web.global.service.GlobalService;

/**
 * The Class BookingCommonServiceImpl.
 */
public class BookingCommonServiceImpl implements BookingCommonService {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BookingCommonServiceImpl.class);

	/** The booking validator. */
	private BookingValidator bookingValidator;

	/** The service offering common service. */
	private ServiceOfferingCommonService serviceOfferingCommonService;

	/** The organization common service. */
	private OrganizationCommonService organizationCommonService;

	/** The consignment service. */
	private ConsignmentService consignmentService;

	/** The booking common dao. */
	private BookingCommonDAO bookingCommonDAO;

	/** The geography common service. */
	private GeographyCommonService geographyCommonService;

	/** The business common service. */
	private BusinessCommonService businessCommonService;

	/** The tracking universal service. */
	private TrackingUniversalService trackingUniversalService;

	/** The global service. */
	private GlobalService globalService;

	/* private BookingContextService bookingContextService; */

	/** The booking universal service. */
	private PickupManagementCommonService pickupManagementCommonService;
	private RateCalculationUniversalService rateCalculationUniversalService;

	private ConsignmentCommonDAO consignmentCommonDAO;
	private BookingUniversalService bookingUniversalService;

	/**
	 * @return the bookingUniversalService
	 */
	public BookingUniversalService getBookingUniversalService() {
		return bookingUniversalService;
	}

	/**
	 * @param bookingUniversalService
	 *            the bookingUniversalService to set
	 */
	public void setBookingUniversalService(
			BookingUniversalService bookingUniversalService) {
		this.bookingUniversalService = bookingUniversalService;
	}

	/**
	 * @param rateCalculationUniversalService
	 *            the rateCalculationUniversalService to set
	 */
	public void setRateCalculationUniversalService(
			RateCalculationUniversalService rateCalculationUniversalService) {
		this.rateCalculationUniversalService = rateCalculationUniversalService;
	}

	/**
	 * Gets the business common service.
	 * 
	 * @return the business common service
	 */
	public BusinessCommonService getBusinessCommonService() {
		return businessCommonService;
	}

	/**
	 * Gets the service offering common service.
	 * 
	 * @return the service offering common service
	 */
	public ServiceOfferingCommonService getServiceOfferingCommonService() {
		return serviceOfferingCommonService;
	}

	/**
	 * Sets the service offering common service.
	 * 
	 * @param serviceOfferingCommonService
	 *            the new service offering common service
	 */
	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}

	/**
	 * Gets the organization common service.
	 * 
	 * @return the organization common service
	 */
	public OrganizationCommonService getOrganizationCommonService() {
		return organizationCommonService;
	}

	/**
	 * Sets the consignment service.
	 * 
	 * @param consignmentService
	 *            the new consignment service
	 */
	public void setConsignmentService(ConsignmentService consignmentService) {
		this.consignmentService = consignmentService;
	}

	/**
	 * Gets the booking common dao.
	 * 
	 * @return the booking common dao
	 */
	public BookingCommonDAO getBookingCommonDAO() {
		return bookingCommonDAO;
	}

	/**
	 * Sets the booking common dao.
	 * 
	 * @param bookingCommonDAO
	 *            the new booking common dao
	 */
	public void setBookingCommonDAO(BookingCommonDAO bookingCommonDAO) {
		this.bookingCommonDAO = bookingCommonDAO;
	}

	/**
	 * Gets the consignment service.
	 * 
	 * @return the consignment service
	 */
	public ConsignmentService getConsignmentService() {
		return consignmentService;
	}

	/**
	 * Gets the geography common service.
	 * 
	 * @return the geography common service
	 */
	public GeographyCommonService getGeographyCommonService() {
		return geographyCommonService;
	}

	/**
	 * Sets the geography common service.
	 * 
	 * @param geographyCommonService
	 *            the new geography common service
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	/**
	 * Sets the business common service.
	 * 
	 * @param businessCommonService
	 *            the new business common service
	 */
	public void setBusinessCommonService(
			BusinessCommonService businessCommonService) {
		this.businessCommonService = businessCommonService;
	}

	/**
	 * Sets the tracking universal service.
	 * 
	 * @param trackingUniversalService
	 *            the new tracking universal service
	 */
	public void setTrackingUniversalService(
			TrackingUniversalService trackingUniversalService) {
		this.trackingUniversalService = trackingUniversalService;
	}

	/**
	 * Sets the global service.
	 * 
	 * @param globalService
	 *            the new global service
	 */
	public void setGlobalService(GlobalService globalService) {
		this.globalService = globalService;
	}

	public void setConsignmentCommonDAO(
			ConsignmentCommonDAO consignmentCommonDAO) {
		this.consignmentCommonDAO = consignmentCommonDAO;
	}

	/**
	 * Sets the organization common service.
	 * 
	 * @param organizationCommonService
	 *            the organizationCommonService to set
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	/**
	 * Gets the booking validator.
	 * 
	 * @return the booking validator
	 */
	public BookingValidator getBookingValidator() {
		return bookingValidator;
	}

	/**
	 * Sets the booking validator.
	 * 
	 * @param bookingValidator
	 *            the new booking validator
	 */
	public void setBookingValidator(BookingValidator bookingValidator) {
		this.bookingValidator = bookingValidator;
	}

	/**
	 * @param pickupManagementCommonService
	 *            the pickupManagementCommonService to set
	 */
	public void setPickupManagementCommonService(
			PickupManagementCommonService pickupManagementCommonService) {
		this.pickupManagementCommonService = pickupManagementCommonService;
	}

	/**
	 * @param bookingContextService
	 *            the bookingContextService to set
	 */
	/*
	 * public void setBookingContextService( BookingContextService
	 * bookingContextService) { this.bookingContextService =
	 * bookingContextService; }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#validateConsignment(com
	 * .ff.booking.BookingValidationTO)
	 */
	public BookingValidationTO validateConsignment(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException,
			CGSystemException {
		
		long startTimeInMilis=System.currentTimeMillis();
		
		LOGGER.debug("BookingCommonServiceImpl::validateConsignment::START------------>:::::::Consignment No ::"+ bookingvalidateTO.getConsgNumber()+" Start time :: " +startTimeInMilis);
		try {
			bookingvalidateTO = bookingValidator
					.validateConsignment(bookingvalidateTO);
		} catch (CGBusinessException e) {
			LOGGER.debug("Exception occurred in BookingCommonServiceImpl :: validateConsignment() :"
					+ e.getMessage());
			throw new CGBusinessException(e.getMessage());
		} catch (CGSystemException e) {
			LOGGER.debug("Exception occurred in BookingCommonServiceImpl :: validateConsignment() :"
					+ e.getMessage());
			throw new CGSystemException(e);

		}
		long endTimeInMilis=System.currentTimeMillis();
		
		long diff=endTimeInMilis-startTimeInMilis;
		
		LOGGER.debug("BookingCommonServiceImpl::validateConsignment::END------------>:::::::   End time::"+endTimeInMilis+" Diff in HH:MM:SS::"+DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		return bookingvalidateTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#savePickupBooking(com
	 * .ff.pickup.PickupBookingTO)
	 */
	public List<Integer> savePickupBooking(PickupBookingTO bookingTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BookingCommonServiceImpl :: savePickupBooking :: start");
		List<Integer> successBookingIds = null;
		try {
			successBookingIds = callPickupBookingProcess(bookingTO);
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR : BookingCommonServiceImpl.savePickupBooking()", e);
			// throw new CGSystemException(e);
			throw e;

		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR : BookingCommonServiceImpl.savePickupBooking()", e);
			// throw new CGBusinessException(e);
			throw e;
		}
		LOGGER.debug("BookingCommonServiceImpl :: savePickupBooking :: End");
		return successBookingIds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#saveOrUpdateBooking(com
	 * .ff.booking.BookingTO)
	 */
	public String saveOrUpdateBooking(BookingTO bookingTO)
			throws CGBusinessException, CGBusinessException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#updateBooking(com.ff.
	 * booking.ConsignmentModificationTO)
	 */
	public BookingResultTO updateBooking(ConsignmentModificationTO bookingTO,
			Map<String, ConsignmentRateCalculationOutputTO> rateCompnents)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BookingCommonServiceImpl :: updateBooking :: Start");
		BookingResultTO result = null;
		try {
			result = callUpdateBookingProcess(bookingTO, rateCompnents);
		} catch (CGSystemException e) {
			LOGGER.error("ERROR : BookingCommonServiceImpl.updateBooking()", e);
			throw e;

		} catch (CGBusinessException e) {
			LOGGER.error("ERROR : BookingCommonServiceImpl.updateBooking()", e);
			throw e;
		}
		LOGGER.debug("BookingCommonServiceImpl :: updateBooking :: End");
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.booking.service.BookingCommonService#getConsignmentType()
	 */
	@Override
	public List<ConsignmentTypeTO> getConsignmentType()
			throws CGBusinessException, CGSystemException {
		return serviceOfferingCommonService.getConsignmentType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getConsignmentType(com
	 * .ff.serviceOfferring.ConsignmentTypeTO)
	 */
	@Override
	public List<ConsignmentTypeTO> getConsignmentType(
			ConsignmentTypeTO consgType) throws CGBusinessException,
			CGSystemException {
		return serviceOfferingCommonService.getConsignmentTypes(consgType);
	}

	// shankar added this service impl method
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.booking.service.BookingCommonService#getPaymentDetails()
	 */
	@Override
	public List<PaymentModeTO> getPaymentDetails() throws CGBusinessException,
			CGSystemException {
		// TODO Auto-generated method stub
		// return serviceOfferingCommonService.getPaymentDetails();
		return serviceOfferingCommonService
				.getPaymentModeDtls(CommonConstants.PROCESS_BOOKING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getPaymentDetails(java
	 * .lang.Integer)
	 */
	public PaymentModeTO getPaymentDetails(Integer paymentId)
			throws CGBusinessException {
		// TODO Auto-generated method stub
		return serviceOfferingCommonService.getPaymentDetails(paymentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getApprovers(java.lang
	 * .Integer)
	 */
	@Override
	public List<EmployeeTO> getApprovers(Integer screenId, Integer loginOffId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BookingCommonServiceImpl :: getApprovers :: Start");
		/*
		 * List<EmployeeTO> employeeTOs = organizationCommonService
		 * .getApproversUnderRegion(regionId);
		 */
		List<EmployeeTO> employeeTOs = organizationCommonService
				.geScreentApproversByOffice(screenId, loginOffId);
		LOGGER.debug("BookingCommonServiceImpl :: getApprovers :: End");
		return employeeTOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.booking.service.BookingCommonService#getContentValues()
	 */
	@Override
	public List<CNContentTO> getContentValues() throws CGSystemException,
			CGBusinessException {
		return serviceOfferingCommonService.getContentValues();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getPaperWorks(com.ff.
	 * serviceOfferring.CNPaperWorksTO)
	 */
	@Override
	public List<CNPaperWorksTO> getPaperWorks(
			CNPaperWorksTO paperWorkValidationTO) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("BookingCommonServiceImpl :: getPaperWorks :: Start");
		// Getting consingment type config
		List<CNPaperWorksTO> paperWorks = null;
		ConsignmentTypeConfigTO consgTypeConfigTO = null;
		consgTypeConfigTO = new ConsignmentTypeConfigTO();
		consgTypeConfigTO.setDeclaredValue(paperWorkValidationTO
				.getDeclatedValue());
		consgTypeConfigTO.setDocType(paperWorkValidationTO.getDocType());
		ConsignmentTypeConfigTO consgTypeConfig = serviceOfferingCommonService
				.getConsgTypeConfigDtls(consgTypeConfigTO);
		if (!StringUtil.isNull(consgTypeConfig)) {
			if (paperWorkValidationTO.getDeclatedValue().doubleValue() > consgTypeConfig
					.getDeclaredValue().doubleValue()) {
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						consgTypeConfig.getIsPaperworkMandatory())) {
					paperWorks = serviceOfferingCommonService
							.getPaperWorks(paperWorkValidationTO);
				}
			}
		}
		LOGGER.debug("BookingCommonServiceImpl :: getPaperWorks :: End");
		return paperWorks;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getProductByConsgSeries
	 * (java.lang.String)
	 */
	@Override
	public ProductTO getProductByConsgSeries(String consgSeries)
			throws CGSystemException {
		return serviceOfferingCommonService
				.getProductByConsgSeries(consgSeries);
	}

	/**
	 * Checks if is consg booked.
	 * 
	 * @param consgNumber
	 *            the consg number
	 * @return : boolean
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 * @Method : isConsgBooked
	 * @Desc : Check for whether consignment is booked not
	 */
	public boolean isConsgBooked(String consgNumber)
			throws CGBusinessException, CGSystemException {
		boolean isCNBooked = Boolean.FALSE;
		LOGGER.debug("BookingCommonServiceImpl :: isConsgBooked :: Start");
		try {
			isCNBooked = bookingCommonDAO.isConsgBooked(consgNumber);
		} catch (Exception e) {
			LOGGER.debug("Exception in isConsgBooked() :" + e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("BookingCommonServiceImpl :: isConsgBooked :: End");
		return isCNBooked;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getBookingTypeProductMap
	 * (java.lang.String)
	 */
	@Override
	public BookingTypeProductMapTO getBookingTypeProductMap(String bookingType)
			throws CGSystemException {
		return serviceOfferingCommonService
				.getBookingTypeProductMap(bookingType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getPrivilegeCardTransDtls
	 * (java.lang.String)
	 */
	@Override
	public List<PrivilegeCardTransactionTO> getPrivilegeCardTransDtls(
			String privilegeCardNo) throws CGSystemException,
			CGBusinessException {
		return serviceOfferingCommonService
				.getPrivilegeCardTransDtls(privilegeCardNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getPrivilegeCardDtls(
	 * java.lang.String)
	 */
	@Override
	public PrivilegeCardTO getPrivilegeCardDtls(String privilegeCardNo)
			throws CGSystemException, CGBusinessException {
		return serviceOfferingCommonService
				.getPrivilegeCardDtls(privilegeCardNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getBookingTypeConfig(
	 * java.lang.String)
	 */
	public BookingTypeConfigTO getBookingTypeConfig(String bookingType)
			throws CGBusinessException {
		LOGGER.debug("BookingCommonServiceImpl :: getBookingTypeConfig :: Start");
		BookingTypeConfigTO bookingTypeConfigTO = null;
		BookingTypeConfigDO bookingTypeConfigDO = null;
		try {
			bookingTypeConfigDO = bookingCommonDAO
					.getBookingTypeConfig(bookingType);
			if (bookingTypeConfigDO != null) {
				bookingTypeConfigTO = new BookingTypeConfigTO();
				bookingTypeConfigTO = (BookingTypeConfigTO) CGObjectConverter
						.createToFromDomain(bookingTypeConfigDO,
								bookingTypeConfigTO);
				BookingTypeDO bookingTypeDO = bookingTypeConfigDO
						.getBookingType();
				if (bookingTypeDO != null) {
					BookingTypeTO bookingTypeTO = new BookingTypeTO();
					bookingTypeTO = (BookingTypeTO) CGObjectConverter
							.createToFromDomain(bookingTypeDO, bookingTypeTO);
					bookingTypeConfigTO.setBookingTypeTO(bookingTypeTO);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"BookingCommonService::getBookingTypeConfig:: error is :",
					e);

		}
		LOGGER.debug("BookingCommonServiceImpl :: getBookingTypeConfig :: End");
		return bookingTypeConfigTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#validatePincode(com.ff
	 * .booking.BookingValidationTO)
	 */
	public BookingValidationTO validatePincode(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BookingCommonServiceImpl :: validatePincode :: Start");

		bookingvalidateTO = bookingValidator.isValidPincode(bookingvalidateTO);
		/*
		 * catch(Exception e){ LOGGER.debug("Exception in validatePincode() :"
		 * ,e); throw new CGBusinessException(e); }
		 */
		LOGGER.debug("BookingCommonServiceImpl :: validatePincode :: End");
		return bookingvalidateTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#validateDeclaredValue
	 * (com.ff.booking.BookingValidationTO)
	 */
	public BookingValidationTO validateDeclaredValue(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BookingCommonServiceImpl :: validateDeclaredValue :: Start");

		bookingvalidateTO = bookingValidator
				.validateDeclaredValue(bookingvalidateTO);

		LOGGER.debug("BookingCommonServiceImpl :: validateDeclaredValue :: End");
		return bookingvalidateTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.booking.service.BookingCommonService#
	 * validatePincodeForPriorityProduct(com.ff.booking.BookingValidationTO)
	 */
	public BookingValidationTO validatePincodeForPriorityProduct(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException,
			CGSystemException {

		LOGGER.debug("BookingCommonServiceImpl :: validatePincodeForPriorityProduct :: Start");

		bookingvalidateTO = bookingValidator
				.validatePincodeProductServiceability(bookingvalidateTO);

		LOGGER.debug("BookingCommonServiceImpl :: validatePincodeForPriorityProduct :: End");
		return bookingvalidateTO;
	}

	/**
	 * Call pickup booking process.
	 * 
	 * @param bookingTO
	 *            the booking to
	 * @return the string
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	private List<Integer> callPickupBookingProcess(PickupBookingTO bookingTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BookingCommonServiceImpl::callPickupBookingProcess::START------------>:::::::");
		List<BookingDO> bookings = new ArrayList<BookingDO>(bookingTO
				.getConsgNumbers().size());
		BookingDO booking = null;
		// boolean isSaved = Boolean.FALSE;
		// Check for weather Consignment is booking or not
		for (String consgNumber : bookingTO.getConsgNumbers()) {
			bookingTO.setConsgNumber(consgNumber);
			bookingTO.setBookingId(0);
			// getBookingDtls() - method will get only active consignments, if
			// the consignments are in in-Active state,
			// then we can reassign the consignments for the another customer.
			// So we need to get all consignment numbers
			ConsignmentModificationTO bookingDtlsTO = getAllStatusBookingDtls(consgNumber);
			if (!StringUtil.isNull(bookingDtlsTO)) {
				bookingTO.setBookingId(bookingDtlsTO.getBookingId());
				if (!StringUtils.equalsIgnoreCase(
						bookingTO.getPickupRunsheetNo(),
						bookingDtlsTO.getPickupRunsheetNo())) {
					throw new CGBusinessException(
							BookingErrorCodesConstants.INVALID_CN_PICK_UP_BOOKING);
				}
			}
			booking = pickupBookingDomainConverter(bookingTO);
			if (!StringUtil.isNull(booking))
				bookings.add(booking);
		}
		List<Integer> successBookingIds = null;
		if (!StringUtil.isEmptyList(bookings)) {
			successBookingIds = bookingCommonDAO.savePickupBooking(bookings);
		}
		LOGGER.debug("BookingCommonServiceImpl::callPickupBookingProcess::END------------>:::::::");
		return successBookingIds;
	}

	/**
	 * Pickup booking domain converter.
	 * 
	 * @param bookingTO
	 *            the booking to
	 * @return the booking do
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private BookingDO pickupBookingDomainConverter(PickupBookingTO bookingTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BookingCommonServiceImpl::pickupBookingDomainConverter::START------------>:::::::");
		BookingDO pickupBookingDO = new BookingDO();
		if (!StringUtil.isEmptyInteger(bookingTO.getBookingId())) {
			pickupBookingDO.setBookingId(bookingTO.getBookingId());
		}
		// Audit columns
		pickupBookingDO.setCreatedBy(bookingTO.getLoggedInUserId());
		pickupBookingDO.setUpdatedBy(bookingTO.getLoggedInUserId());
		pickupBookingDO.setCreatedDate(new Date());
		pickupBookingDO.setUpdatedDate(new Date());

		pickupBookingDO.setConsgNumber(bookingTO.getConsgNumber());
		pickupBookingDO.setBookingOfficeId(bookingTO.getOfficeId());
		CustomerDO customer = new CustomerDO();
		customer.setCustomerId(bookingTO.getCustomerId());
		pickupBookingDO.setCustomerId(customer);
		ProcessDO process = new ProcessDO();
		process.setProcessId(bookingTO.getProcessId());
		pickupBookingDO.setUpdatedProcess(process);
		pickupBookingDO.setPickRunsheetNo(bookingTO.getPickupRunsheetNo());
		Date bookingDate = DateUtil
				.parseStringDateToDDMMYYYYHHMMFormat(bookingTO.getBookingDate());
		pickupBookingDO.setBookingDate(bookingDate);
		// By default bookings created by pickup are CREDIT booking only.
		BookingTypeDO bookingTypeDO = bookingCommonDAO
				.getBookingType(BookingConstants.CCC_BOOKING);
		pickupBookingDO.setBookingType(bookingTypeDO);
		// Setting Consignor address
		if (!StringUtil.isNull(bookingTO.getConsignorAddress())) {
			ConsigneeConsignorDO consignor = new ConsigneeConsignorDO();
			consignor.setAddress(bookingTO.getConsignorAddress().getAddress());
			consignor.setMobile(bookingTO.getConsignorAddress().getMobile());
			consignor.setPhone(bookingTO.getConsignorAddress().getPhone());
			pickupBookingDO.setConsignorId(consignor);
		}
		// Pickup process always creates a partial Booking record. so that data
		// should not transfer from branch to central till it is updated by
		// booking/manifest.
		// So we need to set dt_to_central as 'R' while partial booking. while
		// updating by booking/manifest, dt_to_central='N'.
		pickupBookingDO.setDtToCentral(CommonConstants.DT_TO_CENTRAL_R);

		// set shipped to code for billing purpose.
		pickupBookingDO.setShippedToCode(bookingTO.getShippedToCode());
		LOGGER.debug("BookingCommonServiceImpl::pickupBookingDomainConverter::END------------>:::::::");
		return pickupBookingDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#saveCNPricingDtls(com
	 * .ff.booking.BookingTO)
	 */
	public List<Integer> saveConsignmentAndRateDox(BookingTO bookingTO,
			Map<String, ConsignmentRateCalculationOutputTO> consgRateDtls)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BookingCommonServiceImpl::saveConsignmentAndRateDox::START------------>:::::::");
		List<Integer> successCnIds = null;
		// Setting CNPricing details
		ConsignmentTO consgTO = new ConsignmentTO();
		// Setting Billing flags
		setBillingStatus(consgTO);
		Integer consgTypeId = Integer.parseInt(bookingTO.getConsgTypeName().split("#")[0]);
		consgTO.setConsgTypeId(consgTypeId);
		consgTO.setConsgNo(bookingTO.getConsgNumber());
		consgTO.setOrgOffId(bookingTO.getBookingOfficeId());
		consgTO.setOperatingOffice(bookingTO.getBookingOfficeId());
		PincodeTO pincode = new PincodeTO();
		pincode.setPincodeId(bookingTO.getPincodeId());
		consgTO.setDestPincode(pincode);
		consgTO.setPrice(bookingTO.getPrice());
		consgTO.setNoOfPcs(bookingTO.getNoOfPieces());
		consgTO.setUpdatedProcessFrom(bookingTO.getProcessTO());
		if ((bookingTO.getFinalWeight() != null && bookingTO.getFinalWeight() > 0) 
				|| BookingConstants.EMOTIONAL_BOND_BOOKING.equals(bookingTO.getBookingType()))
			consgTO.setFinalWeight(bookingTO.getFinalWeight());
		if ((bookingTO.getActualWeight() != null	&& bookingTO.getActualWeight() > 0)
				|| BookingConstants.EMOTIONAL_BOND_BOOKING.equals(bookingTO.getBookingType())) {
			consgTO.setActualWeight(bookingTO.getActualWeight());
		} else {
			consgTO.setActualWeight(bookingTO.getFinalWeight());
		}

		ProductTO productTO = getProductByConsgSeries(bookingTO.getConsgNumber().substring(4, 5));
		if (productTO != null && productTO.getProductId() != null
				&& productTO.getProductId() > 0)
			consgTO.setProductId(productTO.getProductId());
		// Setting Declared value
		CNPricingDetailsTO cnPriceDtls = bookingTO.getCnPricingDtls();
		if (!StringUtil.isNull(cnPriceDtls)) {
			consgTO.setConsgPriceDtls(cnPriceDtls);
		}
		// Child Consignments
		if (StringUtils.isNotEmpty(bookingTO.getChildCNsDtls())) {
			Set<ChildConsignmentTO> chilsCns = BookingUtils
					.setUpChildConsignmentTOs(bookingTO.getChildCNsDtls());
			if (!StringUtil.isEmptyColletion(chilsCns)){
				for(ChildConsignmentTO child:chilsCns){
					child.setCreatedBy(bookingTO.getCreatedBy());
					child.setUpdatedBy(bookingTO.getUpdatedBy());
				}
				consgTO.setChildTOSet(chilsCns);
			}
		}
		consgTO.setReCalcRateReq(Boolean.FALSE);
		// Setting operaring level
		OfficeTO loggedInOffice = new OfficeTO();
		loggedInOffice.setOfficeId(bookingTO.getBookingOfficeId());
		/*Integer consgOpLevel = consignmentService.getConsgOperatingLevel(
				consgTO, loggedInOffice);
		consgTO.setOperatingLevel(consgOpLevel);*/
		if (!StringUtil.isNull(bookingTO.getConsignee())) {
			consgTO.setMobileNo(bookingTO.getConsignee().getMobile());
			bookingTO.getConsignee().setCreatedBy(bookingTO.getCreatedBy());
			bookingTO.getConsignee().setUpdatedBy(bookingTO.getUpdatedBy());
			consgTO.setConsigneeTO(bookingTO.getConsignee());
		}
		if (!StringUtil.isNull(bookingTO.getConsignor())) {
			bookingTO.getConsignor().setCreatedBy(bookingTO.getCreatedBy());
			bookingTO.getConsignor().setUpdatedBy(bookingTO.getUpdatedBy());
			consgTO.setConsignorTO(bookingTO.getConsignor());
		}
		// Setting cuatomer / Business Associate
		if (!StringUtil.isEmptyInteger(bookingTO.getCustomerId())) {
			consgTO.setCustomer(bookingTO.getCustomerId());
		} else if (!StringUtil.isEmptyInteger(bookingTO.getBizAssociateId())) {
			consgTO.setCustomer(bookingTO.getBizAssociateId());
		}

		if (StringUtils.equalsIgnoreCase(
				CommonConstants.PRODUCT_SERIES_PRIORITY, bookingTO.getConsgNumber().substring(4, 5))) {
			if (!StringUtil.isStringEmpty(bookingTO.getPriorityServiced())) {
				String priorityService = bookingTO.getPriorityServiced();
				if (priorityService.contains("Before")) {
					consgTO.setServicedOn("B");
				} else if (priorityService.contains("After")) {
					consgTO.setServicedOn("A");
				} else {
					consgTO.setServicedOn("S");
				}
				// consgTO.setServicedOn(priorityService != null &&  priorityService.contains("Before") ? "B" : "A");
			}

		}
		consgTO.setEventDate(DateUtil
				.parseStringDateToDDMMYYYYHHMMFormat(bookingTO.getBookingDate()));
		if (!CGCollectionUtils.isEmpty(consgRateDtls)) {
			consgTO.setConsgRateOutputTOs(consgRateDtls);
		} else {
			LOGGER.error("Error occured in :: BookingCommonServiceImpl::saveConsignmentAndRateDox::: Rate Not Calculated");
			new CGBusinessException(
					BookingErrorCodesConstants.RATE_NOT_CALCULATED);
		}
		consgTO.setCreatedBy(bookingTO.getCreatedBy());
		consgTO.setUpdatedBy(bookingTO.getUpdatedBy());
		List<ConsignmentTO> consgTOs = new ArrayList<ConsignmentTO>(1);
		consgTOs.add(consgTO);
		successCnIds = consignmentService.createConsingment(consgTOs);
		LOGGER.debug("BookingCommonServiceImpl::saveConsignmentAndRateDox::END------------>:::::::");
		return successCnIds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#saveCNPricingDtls(com
	 * .ff.booking.BookingParcelTO)
	 */
	public List<Integer> saveConsignmentAndRateParcel(
			BookingParcelTO bookingTO,
			Map<String, ConsignmentRateCalculationOutputTO> consgRateDtls)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BookingCommonServiceImpl::saveConsignmentAndRateParcel::START------------>:::::::");
		// Setting CNPricing details
		// List<CNPricingDetailsTO> cnPricingDtls = new ArrayList();
		List<Integer> successCnIds = null;
		ConsignmentTO consgTO = new ConsignmentTO();
		// Setting Billing flags
		setBillingStatus(consgTO);
		Integer consgTypeId = Integer.parseInt(bookingTO.getConsgTypeName()
				.split("#")[0]);
		consgTO.setConsgTypeId(consgTypeId);
		consgTO.setConsgNo(bookingTO.getConsgNumber());
		consgTO.setOrgOffId(bookingTO.getBookingOfficeId());
		consgTO.setOperatingOffice(bookingTO.getBookingOfficeId());
		PincodeTO pincode = new PincodeTO();
		pincode.setPincodeId(bookingTO.getPincodeId());
		consgTO.setDestPincode(pincode);
		consgTO.setPrice(bookingTO.getPrice());
		consgTO.setNoOfPcs(bookingTO.getNoOfPieces());
		consgTO.setUpdatedProcessFrom(bookingTO.getProcessTO());
		if (bookingTO.getFinalWeight() != null
				&& bookingTO.getFinalWeight() > 0)
			consgTO.setFinalWeight(bookingTO.getFinalWeight());
		if (bookingTO.getActualWeight() != null
				&& bookingTO.getActualWeight() > 0) {
			consgTO.setActualWeight(bookingTO.getActualWeight());
		} else {
			consgTO.setActualWeight(bookingTO.getFinalWeight());
		}

		ProductTO productTO = getProductByConsgSeries(bookingTO
				.getConsgNumber().substring(4, 5));
		if (productTO != null && productTO.getProductId() != null
				&& productTO.getProductId() > 0)
			consgTO.setProductId(productTO.getProductId());
		// Child Consignments
		if (StringUtils.isNotEmpty(bookingTO.getChildCNsDtls()) && bookingTO.getNoOfPieces()>1) {
			Set<ChildConsignmentTO> chilsCns = BookingUtils
					.setUpChildConsignmentTOs(bookingTO.getChildCNsDtls());
			if (!StringUtil.isEmptyColletion(chilsCns)){
				for(ChildConsignmentTO child:chilsCns){
					child.setCreatedBy(bookingTO.getCreatedBy());
					child.setUpdatedBy(bookingTO.getUpdatedBy());
				}
				consgTO.setChildTOSet(chilsCns);
			}
		}

		if (!StringUtil.isEmptyInteger(bookingTO.getCnContents()
				.getCnContentId())) {
			CNContentTO content = new CNContentTO();
			content.setCnContentId(bookingTO.getCnContents().getCnContentId());
			consgTO.setCnContents(content);
			if (!StringUtil.isNull(bookingTO.getOtherCNContent())) {
				consgTO.setOtherCNContent(bookingTO.getOtherCNContent());
			} else {
				consgTO.setOtherCNContent(null);
			}
		}

		// Setting Paperworks
		if (!StringUtil.isEmptyInteger(bookingTO.getCnPaperworkId())) {
			CNPaperWorksTO cnPaperwork = new CNPaperWorksTO();
			cnPaperwork.setCnPaperWorkId(bookingTO.getCnPaperworkId());
			cnPaperwork.setPaperWorkRefNum(bookingTO.getPaperWorkRefNo());
			consgTO.setCnPaperWorks(cnPaperwork);
		}
		// Setting insured by
		if (!StringUtil.isEmptyInteger(bookingTO.getInsuredById())) {
			InsuredByTO insuredBy = new InsuredByTO();
			insuredBy.setInsuredById(bookingTO.getInsuredById());
			insuredBy.setPolicyNo(bookingTO.getPolicyNo());
			consgTO.setInsuredByTO(insuredBy);
		}
		if (!StringUtil.isNull(bookingTO.getConsignee())) {
			consgTO.setMobileNo(bookingTO.getConsignee().getMobile());
			bookingTO.getConsignee().setCreatedBy(bookingTO.getCreatedBy());
			bookingTO.getConsignee().setUpdatedBy(bookingTO.getUpdatedBy());
			consgTO.setConsigneeTO(bookingTO.getConsignee());
		}
		if (!StringUtil.isNull(bookingTO.getConsignor())) {
			bookingTO.getConsignor().setCreatedBy(bookingTO.getCreatedBy());
			bookingTO.getConsignor().setUpdatedBy(bookingTO.getUpdatedBy());
			consgTO.setConsignorTO(bookingTO.getConsignor());
		}

		if (bookingTO.getVolWeight() != null && bookingTO.getVolWeight() > 0) {
			VolumetricWeightTO volWeight = new VolumetricWeightTO();
			volWeight.setVolWeight(bookingTO.getVolWeight());
			if (bookingTO.getLength() != null && bookingTO.getLength() > 0)
				volWeight.setLength(bookingTO.getLength());
			if (bookingTO.getHeight() != null && bookingTO.getHeight() > 0)
				volWeight.setHeight(bookingTO.getHeight());
			if (bookingTO.getBreath() != null && bookingTO.getBreath() > 0)
				volWeight.setBreadth(bookingTO.getBreath());
			consgTO.setVolWightDtls(volWeight);
		}
		// Setting Declared value
		CNPricingDetailsTO cnPriceDtls = bookingTO.getCnPricingDtls();
		if (!StringUtil.isNull(cnPriceDtls)) {
			consgTO.setConsgPriceDtls(cnPriceDtls);
		}
		consgTO.setReCalcRateReq(Boolean.FALSE);
		// Setting operaring level
		OfficeTO loggedInOffice = new OfficeTO();
		loggedInOffice.setOfficeId(bookingTO.getBookingOfficeId());
	/*	Integer consgOpLevel = consignmentService.getConsgOperatingLevel(
				consgTO, loggedInOffice);
		consgTO.setOperatingLevel(consgOpLevel);*/
		// Setting cuatomer / Business Associate
		if (!StringUtil.isEmptyInteger(bookingTO.getCustomerId())) {
			consgTO.setCustomer(bookingTO.getCustomerId());
		} else if (!StringUtil.isEmptyInteger(bookingTO.getBizAssociateId())) {
			consgTO.setCustomer(bookingTO.getBizAssociateId());
		}

		if (StringUtils.equalsIgnoreCase(
				CommonConstants.PRODUCT_SERIES_PRIORITY, bookingTO
						.getConsgNumber().substring(4, 5))) {
			if (!StringUtil.isStringEmpty(bookingTO.getPriorityServiced())) {
				String priorityService = bookingTO.getPriorityServiced();
				if (priorityService.contains("Before")) {
					consgTO.setServicedOn("B");
				} else if (priorityService.contains("After")) {
					consgTO.setServicedOn("A");
				} else {
					consgTO.setServicedOn("S");
				}
				// consgTO.setServicedOn(priorityService != null &&  priorityService.contains("Before") ? "B" : "A");
			}

		}
		consgTO.setCreatedBy(bookingTO.getCreatedBy());
		consgTO.setUpdatedBy(bookingTO.getUpdatedBy());
		consgTO.setEventDate(DateUtil
				.parseStringDateToDDMMYYYYHHMMFormat(bookingTO.getBookingDate()));
		if (!CGCollectionUtils.isEmpty(consgRateDtls)) {
			consgTO.setConsgRateOutputTOs(consgRateDtls);
		} else {
			LOGGER.error("Error occured in :: BookingCommonServiceImpl::saveConsignmentAndRateParcel::: Rate Not Calculated");
			new CGBusinessException(
					BookingErrorCodesConstants.RATE_NOT_CALCULATED);
		}

		List<ConsignmentTO> consgTOs = new ArrayList<ConsignmentTO>(1);
		consgTOs.add(consgTO);
		successCnIds = consignmentService.createConsingment(consgTOs);
		LOGGER.debug("BookingCommonServiceImpl::saveConsignmentAndRateParcel::END------------>:::::::");
		return successCnIds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#saveCNPricingDtlsParcel
	 * (java.util.List)
	 */
	public List<Integer> saveCNPricingDtlsParcel(
			List<? extends BookingParcelTO> bookingTOs)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BookingCommonServiceImpl::saveCNPricingDtlsParcel::START------------>:::::::");
		// Setting CNPricing details
		// List<CNPricingDetailsTO> cnPricingDtls = new ArrayList();
		List<ConsignmentTO> consgTOs = new ArrayList<ConsignmentTO>(
				bookingTOs.size());
		for (BookingParcelTO bookingTO : bookingTOs) {
			ConsignmentTO consgTO = new ConsignmentTO();
			// Setting Billing flags
			setBillingStatus(consgTO);
			if (!StringUtil.isNull(bookingTO.getBookingDate())) {
				Date bookingDate = DateUtil
						.parseStringDateToDDMMYYYYHHMMFormat(bookingTO
								.getBookingDate());
				consgTO.setBookingDate(bookingDate);
			}
			ConsignmentTypeTO consgType = new ConsignmentTypeTO();
			consgType.setConsignmentId(bookingTO.getConsgTypeId());
			consgType.setConsignmentCode(bookingTO.getConsgTypeName());
			consgTO.setTypeTO(consgType);
			consgTO.setConsgTypeId(bookingTO.getConsgTypeId());
			consgTO.setConsgNo(bookingTO.getConsgNumber().toUpperCase());
			consgTO.setOrgOffId(bookingTO.getBookingOfficeId());
			consgTO.setOperatingOffice(bookingTO.getBookingOfficeId());
			PincodeTO pinCode = new PincodeTO();
			pinCode.setPincodeId(bookingTO.getPincodeId());
			if (!StringUtil.isNull(bookingTO.getPincode()))
				pinCode.setPincode(bookingTO.getPincode());
			consgTO.setDestPincode(pinCode);
			consgTO.setNoOfPcs(bookingTO.getNoOfPieces());
			if (bookingTO.getFinalWeight() != null
					&& bookingTO.getFinalWeight() > 0)
				consgTO.setFinalWeight(bookingTO.getFinalWeight());
			if (bookingTO.getActualWeight() != null
					&& bookingTO.getActualWeight() > 0)
				consgTO.setActualWeight(bookingTO.getActualWeight());
			if (bookingTO.getVolWeight() != null
					&& bookingTO.getVolWeight() > 0) {
				VolumetricWeightTO volWeight = new VolumetricWeightTO();
				volWeight.setVolWeight(bookingTO.getVolWeight());
				consgTO.setVolWightDtls(volWeight);
				if (bookingTO.getLength() != null && bookingTO.getLength() > 0)
					volWeight.setLength(bookingTO.getLength());
				if (bookingTO.getHeight() != null && bookingTO.getHeight() > 0)
					volWeight.setHeight(volWeight.getHeight());
				if (bookingTO.getBreath() != null && bookingTO.getBreath() > 0)
					volWeight.setBreadth(bookingTO.getBreath());
				consgTO.setVolWightDtls(volWeight);
			}
			String productCode = bookingTO.getConsgNumber().substring(4, 5);
			ProductTO productTO = getProductByConsgSeries(productCode);
			if (productTO != null && productTO.getProductId() != null
					&& productTO.getProductId() > 0) {
				consgTO.setProductId(productTO.getProductId());
				consgTO.setProductTO(productTO);
			}
			consgTO.setUpdatedProcessFrom(bookingTO.getProcessTO());
			// Child Consignments
			if (StringUtils.isNotEmpty(bookingTO.getChildCNsDtls())) {
				Set<ChildConsignmentTO> chilsCns = BookingUtils
						.setUpChildConsignmentTOs(bookingTO.getChildCNsDtls());
				if (!StringUtil.isEmptyColletion(chilsCns))
					consgTO.setChildTOSet(chilsCns);
			}
			// Setting CN Content
			if (!StringUtil.isEmptyInteger(bookingTO.getCnContentId())) {
				CNContentTO content = new CNContentTO();
				content.setCnContentId(bookingTO.getCnContentId());
				content.setOtherContent(bookingTO.getOtherCNContent());
				consgTO.setCnContents(content);
			}
			// Setting Paperworks
			if (!StringUtil.isEmptyInteger(bookingTO.getCnPaperworkId())) {
				CNPaperWorksTO cnPaperwork = new CNPaperWorksTO();
				cnPaperwork.setCnPaperWorkId(bookingTO.getCnPaperworkId());
				cnPaperwork.setPaperWorkRefNum(bookingTO.getPaperWorkRefNo());
				consgTO.setCnPaperWorks(cnPaperwork);
			}
			// Setting insured by
			if (!StringUtil.isEmptyInteger(bookingTO.getInsuredById())) {
				InsuredByTO insuredBy = new InsuredByTO();
				insuredBy.setInsuredById(bookingTO.getInsuredById());
				insuredBy.setPolicyNo(bookingTO.getPolicyNo());
				consgTO.setInsuredByTO(insuredBy);
			}
			CNPricingDetailsTO cnPriceDtls = bookingTO.getCnPricingDtls();
			if (!StringUtil.isNull(cnPriceDtls)) {
				if (StringUtils.equalsIgnoreCase(
						CommonConstants.PRODUCT_SERIES_PRIORITY, productCode)) {
					cnPriceDtls.setServicesOn("A");
				}
				consgTO.setConsgPriceDtls(cnPriceDtls);
				/*
				 * if (!StringUtil.isEmptyDouble(cnPriceDtls.getDeclaredvalue())
				 * && cnPriceDtls.getDeclaredvalue() > 0) {
				 * consgTO.setDeclaredValue(cnPriceDtls.getDeclaredvalue()); }
				 */
			}
			// consgTO.setReCalcRateReq(Boolean.FALSE);
			OfficeTO loggedInOffice = new OfficeTO();
			loggedInOffice.setOfficeId(bookingTO.getBookingOfficeId());
		/*	Integer consgOpLevel = consignmentService.getConsgOperatingLevel(
					consgTO, loggedInOffice);
			consgTO.setOperatingLevel(consgOpLevel);*/
			if (!StringUtil.isNull(bookingTO.getConsignee())) {
				consgTO.setMobileNo(bookingTO.getConsignee().getMobile());
				consgTO.setConsigneeTO(bookingTO.getConsignee());
			}
			if (!StringUtil.isNull(bookingTO.getAltConsigneeAddr())) {
				consgTO.setMobileNo(bookingTO.getAltConsigneeAddr().getMobile());
				consgTO.setAltConsigneeAddrTO(bookingTO.getAltConsigneeAddr());
			}
			if (!StringUtil.isNull(bookingTO.getConsignor())) {
				consgTO.setConsignorTO(bookingTO.getConsignor());
			}
			/*
			 * CNPricingDetailsTO cnPriceDtls = null; cnPriceDtls =
			 * bookingTO.getCnPricingDtls(); // Setting Consignment pricing
			 * details if (bookingTO.getCnPricingDtls() != null) {
			 * consgTO.setPrice(cnPriceDtls.getFinalPrice()); if
			 * (!StringUtil.isNull(cnPriceDtls.getConsigmentTO()) &&
			 * !StringUtil.isEmptyInteger(cnPriceDtls
			 * .getConsigmentTO().getConsgId())) {
			 * consgTO.setConsgId(cnPriceDtls.getConsigmentTO() .getConsgId());
			 * } cnPriceDtls.setConsigmentTO(consgTO);
			 * cnPriceDtls.setReCalcRateReq(Boolean.FALSE); } else { cnPriceDtls
			 * = new CNPricingDetailsTO(); cnPriceDtls.setConsigmentTO(consgTO);
			 * }
			 */
			// Setting customer / Business Associate
			if (!StringUtil.isEmptyInteger(bookingTO.getCustomerId())) {
				consgTO.setCustomer(bookingTO.getCustomerId());
			} else if (!StringUtil
					.isEmptyInteger(bookingTO.getBizAssociateId())) {
				consgTO.setCustomer(bookingTO.getBizAssociateId());
			}
			consgTO.setEventDate(DateUtil
					.parseStringDateToDDMMYYYYHHMMFormat(bookingTO
							.getBookingDate()));

			if (StringUtils.isNotEmpty(bookingTO.getInsuredBy())) {
				InsuredByTO insuredBy = new InsuredByTO();
				insuredBy.setInsuredById(bookingTO.getInsuredById());
				insuredBy.setInsuredByCode(bookingTO.getInsuredBy());
				insuredBy.setPolicyNo(bookingTO.getPolicyNo());
				consgTO.setInsuredByTO(insuredBy);
			}
			// Calculating rate
			if (bookingTO.isReCalcRateReq()) {
				// Calculating Rate for Consignment
				ConsignmentRateCalculationOutputTO consgRate = calcRateForConsingment(consgTO);
				Map<String, ConsignmentRateCalculationOutputTO> consgRateMap = new HashMap<>();
				consgRateMap.put(bookingTO.getConsgNumber(), consgRate);
				consgTO.setConsgRateOutputTOs(consgRateMap);
			}

			if (StringUtils.isNotEmpty(bookingTO.getPolicyNo())) {
				consgTO.setInsurencePolicyNo(bookingTO.getPolicyNo());
			}

			consgTOs.add(consgTO);
		}
		List<Integer> successCnIds = null;
		if (!StringUtil.isEmptyList(consgTOs))
			successCnIds = consignmentService.createConsingment(consgTOs);
		LOGGER.debug("BookingCommonServiceImpl::saveCNPricingDtlsParcel::END------------>:::::::");
		return successCnIds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#saveCNPricingDtlsDox(
	 * java.util.List)
	 */
	public List<Integer> saveCNPricingDtlsDox(
			List<? extends BookingTO> bookingTOs) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("BookingCommonServiceImpl::saveCNPricingDtlsDox::START------------>:::::::");
		// Setting CNPricing details
		// List<CNPricingDetailsTO> cnPricingDtls = new ArrayList();
		List<Integer> successCnIds = null;
		List<ConsignmentTO> consgTOs = new ArrayList<ConsignmentTO>(
				bookingTOs.size());
		for (BookingTO bookingTO : bookingTOs) {
			ConsignmentTO consgTO = new ConsignmentTO();
			// Setting Billing flags
			setBillingStatus(consgTO);
			if (!StringUtil.isNull(bookingTO.getConsigmentTO())) {
				consgTO.setConsgId(bookingTO.getConsigmentTO().getConsgId());
			}
			consgTO.setConsgTypeId(bookingTO.getConsgTypeId());
			consgTO.setConsgNo(bookingTO.getConsgNumber());
			consgTO.setOrgOffId(bookingTO.getBookingOfficeId());
			consgTO.setOperatingOffice(bookingTO.getBookingOfficeId());
			consgTO.setNoOfPcs(bookingTO.getNoOfPieces());
			PincodeTO pinCode = new PincodeTO();
			pinCode.setPincodeId(bookingTO.getPincodeId());
			consgTO.setDestPincode(pinCode);
			if (bookingTO.getFinalWeight() != null
					&& bookingTO.getFinalWeight() > 0)
				consgTO.setFinalWeight(bookingTO.getFinalWeight());
			if (bookingTO.getActualWeight() != null
					&& bookingTO.getActualWeight() > 0)
				consgTO.setActualWeight(bookingTO.getActualWeight());
			ProductTO productTO = getProductByConsgSeries(bookingTO
					.getConsgNumber().substring(4, 5));
			if (productTO != null && productTO.getProductId() != null
					&& productTO.getProductId() > 0)
				consgTO.setProductId(productTO.getProductId());
			consgTO.setUpdatedProcessFrom(bookingTO.getProcessTO());
			if (!StringUtil.isNull(bookingTO.getConsignee()))
				consgTO.setMobileNo(bookingTO.getConsignee().getMobile());
			// Setting Consignment Rate Details
			/*
			 * if (bookingTO.isReCalcRateReq()) { RateCalculationInputTO inputTO
			 * = new RateCalculationInputTO(); inputTO.setRateType("CC");
			 * List<RateComponentTO> consgRates = getConsgRate(inputTO);
			 * Set<ConsignmentRateTO> rateCompoments = BookingUtils
			 * .setUpRateCompomnets(consgRates);
			 * consgTO.setConsgRateDetails(rateCompoments); } else if
			 * (StringUtils.isNotEmpty(bookingTO.getConsgRateDtls())) {
			 * Set<ConsignmentRateTO> rateCompoments = BookingUtils
			 * .setUpRateCompomnets(bookingTO.getConsgRateDtls());
			 * consgTO.setConsgRateDetails(rateCompoments); }
			 */
			// Setting Declared value
			CNPricingDetailsTO cnPriceDtls = bookingTO.getCnPricingDtls();
			if (!StringUtil.isNull(cnPriceDtls)) {
				consgTO.setConsgPriceDtls(cnPriceDtls);
				/*
				 * if (!StringUtil.isEmptyDouble(cnPriceDtls.getDeclaredvalue())
				 * && cnPriceDtls.getDeclaredvalue() > 0) {
				 * consgTO.setDeclaredValue(cnPriceDtls.getDeclaredvalue()); }
				 */
			}

			consgTO.setReCalcRateReq(Boolean.FALSE);
			OfficeTO loggedInOffice = new OfficeTO();
			loggedInOffice.setOfficeId(bookingTO.getBookingOfficeId());
			/*Integer consgOpLevel = consignmentService.getConsgOperatingLevel(
					consgTO, loggedInOffice);
			consgTO.setOperatingLevel(consgOpLevel);*/
			if (!StringUtil.isNull(bookingTO.getConsignee())) {
				consgTO.setMobileNo(bookingTO.getConsignee().getMobile());
				consgTO.setConsigneeTO(bookingTO.getConsignee());
			}
			if (!StringUtil.isNull(bookingTO.getConsignor())) {
				consgTO.setConsignorTO(bookingTO.getConsignor());
			}
			// Setting cuatomer / Business Associate
			if (!StringUtil.isEmptyInteger(bookingTO.getCustomerId())) {
				consgTO.setCustomer(bookingTO.getCustomerId());
			} else if (!StringUtil
					.isEmptyInteger(bookingTO.getBizAssociateId())) {
				consgTO.setCustomer(bookingTO.getBizAssociateId());
			}
			consgTO.setEventDate(DateUtil
					.parseStringDateToDDMMYYYYHHMMFormat(bookingTO
							.getBookingDate()));
			consgTOs.add(consgTO);
		}
		if (!StringUtil.isEmptyList(consgTOs))
			successCnIds = consignmentService.createConsingment(consgTOs);
		LOGGER.debug("BookingCommonServiceImpl::saveCNPricingDtlsDox::END------------>:::::::");
		// Saving process map
		// saveProcessMapBooking(bookingTOs);
		return successCnIds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getInsuarnceConfigDtls
	 * (java.lang.Double, java.lang.String)
	 */
	public List<InsuranceConfigTO> getInsuarnceConfigDtls(Double declarebValue,
			String bookingType) throws CGSystemException, CGBusinessException {
		return serviceOfferingCommonService.getInsuarnceConfigDtls(
				declarebValue, bookingType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getPincodeDlvTimeMaps
	 * (java.lang.String, java.lang.Integer, java.lang.String)
	 */
	public List<PincodeProductServiceabilityTO> getPincodeDlvTimeMaps(
			String pinCode, Integer cityId, String consgSeries)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BookingCommonServiceImpl::getPincodeDlvTimeMaps::START------------>:::::::");
		List<PincodeProductServiceabilityTO> pincodeProductServiceabilityTOs=null;
		if (StringUtil.isEmptyInteger(cityId)
				|| StringUtil.isStringEmpty(pinCode)
				|| StringUtil.isStringEmpty(consgSeries))
			throw new CGBusinessException(
					BookingErrorCodesConstants.LOGIN_CITY_PINCODE_NOT_FOUND);

		pincodeProductServiceabilityTOs= geographyCommonService.getPincodeDlvTimeMaps(pinCode, cityId,
				consgSeries);
		
		if(CGCollectionUtils.isEmpty(pincodeProductServiceabilityTOs)){
			throw new CGBusinessException(
					BookingErrorCodesConstants.INVALID_PRIORITY);
		}
		LOGGER.debug("BookingCommonServiceImpl::getPincodeDlvTimeMaps::END------------>:::::::");
		return pincodeProductServiceabilityTOs;
	}

	/**
	 * Gets the insured by dtls.
	 * 
	 * @return :
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 * @Method : getInsuarnceBy
	 * @Desc : gets Insuarance details
	 * @author uchauhan
	 */
	@Override
	public List<InsuredByTO> getInsuredByDtls() throws CGBusinessException,
			CGSystemException {
		return serviceOfferingCommonService.getInsuarnceBy();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getBookingType(java.lang
	 * .String)
	 */
	public BookingTypeTO getBookingType(String bookingType)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BookingCommonServiceImpl :: getBookingType :: START"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		BookingTypeTO bookingTypeTO = new BookingTypeTO();
		BookingTypeDO bookingTypeDO = bookingCommonDAO
				.getBookingType(bookingType);
		if (!StringUtil.isNull((bookingTypeDO)))
			bookingTypeTO = (BookingTypeTO) CGObjectConverter
					.createToFromDomain(bookingTypeDO, bookingTypeTO);
		LOGGER.debug("BookingCommonServiceImpl :: getBookingType :: End"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return bookingTypeTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#validateCustCode(java
	 * .lang.String, java.lang.Integer)
	 */
	@Override
	public CustomerTO validateCustCode(String custCode, Integer custId)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BookingCommonServiceImpl :: validateCustCode :: START");
		CustomerTO customer = null;
		if (StringUtils.isNotEmpty(custCode))
			customer = businessCommonService.getCustomerByIdOrCode(
					CommonConstants.EMPTY_INTEGER, custCode);
		else if (!StringUtil.isEmptyInteger(custId))
			customer = businessCommonService.getCustomerByIdOrCode(custId,
					CommonConstants.EMPTY_STRING);
		LOGGER.debug("BookingCommonServiceImpl :: validateCustCode :: End");
		return customer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#isProductServicedByBooking
	 * (java.lang.String, java.lang.String)
	 */
	public boolean isProductServicedByBooking(String bookingType,
			String congSeries) throws CGBusinessException, CGSystemException {
		return serviceOfferingCommonService.isProductServicedByBooking(
				bookingType, congSeries);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.booking.service.BookingCommonService#
	 * isNormalProductServicedByBooking(java.lang.String, java.lang.String)
	 */
	public boolean isNormalProductServicedByBooking(String bookingType,
			String prodCode) throws CGSystemException {
		return serviceOfferingCommonService.isNormalProductServicedByBooking(
				bookingType, prodCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getProcess(com.ff.tracking
	 * .ProcessTO)
	 */
	public ProcessTO getProcess(ProcessTO process) throws CGSystemException,
			CGBusinessException {
		return trackingUniversalService.getProcess(process);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#inActivePickupBookings
	 * (com.ff.pickup.PickupBookingTO)
	 */
	@Override
	public void inActivePickupBookings(PickupRunsheetTO pickupRunsheetTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BookingCommonServiceImpl :: inActivePickupBookings :: START");
		if (!StringUtil.isNull(pickupRunsheetTO)) {
			List<String> consignmentList = pickupRunsheetTO
					.getInactiveConsgNumbers();
			if (!StringUtil.isEmptyList(consignmentList)) {
				for (String consgNumber : consignmentList) {
					ConsignmentModificationTO bookingDtlsTO = getBookingDtls(consgNumber);
					if (!StringUtil.isNull(bookingDtlsTO)) {
						if (!StringUtils.equalsIgnoreCase(
								pickupRunsheetTO.getRunsheetNoField(),
								bookingDtlsTO.getPickupRunsheetNo())
								|| !StringUtils.equalsIgnoreCase(
										CommonConstants.PROCESS_PICKUP,
										bookingDtlsTO.getProcessTO()
												.getProcessCode()))
							throw new CGBusinessException(
									BookingErrorCodesConstants.PICK_UP_BOOKING_CONSG_IN_USE);
					}
				}
			}
		}
		LOGGER.debug("BookingCommonServiceImpl :: inActivePickupBookings :: END");
	}

	@Override
	public boolean deleteInActivePickupBookings(
			List<String> inactiveConsgNumbers) throws CGSystemException,
			CGBusinessException {
		boolean isdeleted = Boolean.FALSE;
		isdeleted = bookingCommonDAO.deleteConsignments(inactiveConsgNumbers);
		return isdeleted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getStandardTypes(java
	 * .lang.String)
	 */
	public List<StockStandardTypeTO> getStandardTypes(String typeName)
			throws CGBusinessException, CGSystemException {
		return globalService.getAllStockStandardType(typeName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getAllProducts(java.lang
	 * .String)
	 */
	@Override
	public List<ProductTO> getAllProducts(String bookingType)
			throws CGBusinessException, CGSystemException {
		return serviceOfferingCommonService
				.getAllBookingProductMapping(bookingType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getBookingDtls(java.lang
	 * .String, java.lang.String)
	 */

	@Override
	public ConsignmentModificationTO getBookingDtls(String consgNumber)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BookingCommonServiceImpl :: getBookingDtls :: START");
		ConsignmentModificationTO booking = null;
		try {
			List<Object[]> resultList = bookingCommonDAO
					.getBookingDtls(consgNumber);
			if (resultList != null && resultList.size() > 0) {
				booking = new ConsignmentModificationTO();
				Object[] obj = (Object[]) resultList.get(0);
				if (!StringUtil.isEmpty(obj)) {
					booking.setBookingId(Integer.parseInt((String) obj[0]
							.toString()));
					booking.setPickupRunsheetNo((String) obj[1]);
					ProcessTO processTO = new ProcessTO();
					processTO.setProcessCode((String) obj[2]);
					booking.setProcessTO(processTO);
				}
			}
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in BookingCommonServiceImpl :: getBookingDtls()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("BookingCommonServiceImpl :: getBookingDtls :: End");
		return booking;
	}

	@Override
	public ConsignmentModificationTO getAllStatusBookingDtls(String consgNumber)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BookingCommonServiceImpl :: getAllStatusBookingDtls :: START");
		ConsignmentModificationTO booking = null;
		try {
			List<Object[]> resultList = bookingCommonDAO
					.getBookingDtlsByStatus(consgNumber);
			if (resultList != null && resultList.size() > 0) {
				booking = new ConsignmentModificationTO();
				Object[] obj = (Object[]) resultList.get(0);
				if (!StringUtil.isEmpty(obj)) {
					booking.setBookingId(Integer.parseInt((String) obj[0]
							.toString()));
					booking.setPickupRunsheetNo((String) obj[1]);
				}
			}
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in BookingCommonServiceImpl :: getAllStatusBookingDtls()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("BookingCommonServiceImpl :: getAllStatusBookingDtls :: End");
		return booking;
	}

	/**
	 * Call update booking process.
	 * 
	 * @param bookingTO
	 *            the booking to
	 * @param rateCompnents
	 * @return the string
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	private BookingResultTO callUpdateBookingProcess(
			ConsignmentModificationTO bookingTO,
			Map<String, ConsignmentRateCalculationOutputTO> rateCompnents)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BookingCommonServiceImpl :: callUpdateBookingProcess :: START------------>:::::::");
		BookingDO booking = null;
		Integer bookingId = null;
		List<Integer> updatedCNs = null;
		BookingResultTO result = new BookingResultTO();
		if (StringUtils.isNotEmpty(bookingTO.getConsgNumber())) {
			booking = updateBookingDomainConverter(bookingTO);
			if (!StringUtil.isNull(booking)) {
				bookingId = bookingCommonDAO.updateBooking(booking);
				updatedCNs = updateConsignment(bookingTO, rateCompnents);
			}

			result.setSuccessBookingId(bookingId);
			result.setSuccessCNId(updatedCNs.get(0));
			result.setTransMessage("Y");
		}
		LOGGER.debug("BookingCommonServiceImpl :: callUpdateBookingProcess :: END------------>:::::::");
		return result;
	}

	/**
	 * Update booking domain converter.
	 * 
	 * @param bookingTO
	 *            the booking to
	 * @return the booking do
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	private BookingDO updateBookingDomainConverter(
			ConsignmentModificationTO bookingTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BookingCommonServiceImpl::updateBookingDomainConverter::START------------>:::::::");
		BookingDO booking = null;
		
		if (StringUtils.isNotEmpty(bookingTO.getConsgNumber())) {
			booking = bookingCommonDAO
					.getBookingByProcess(bookingTO.getConsgNumber(),
							CommonConstants.PROCESS_BOOKING);
			if (!StringUtil.isNull(booking)) {
				booking.setDtFromOpsman(booking.getDtFromOpsman());
				// Check for pincode whether is changes or not
				if (!StringUtils.equalsIgnoreCase(bookingTO.getPincode(),
						booking.getPincodeId().getPincode())) {
					// isRecalcReq = Boolean.TRUE;
					PincodeDO pincode = new PincodeDO();
					pincode.setPincodeId(bookingTO.getPincodeId());
					booking.setPincodeId(pincode);
				}
				booking.setUpdatedDate(new Date());
				Integer consgTypeId = 0;
				if (StringUtils.isNotEmpty(bookingTO.getConsgTypeName())) {
					consgTypeId = Integer.parseInt(bookingTO.getConsgTypeName().split(
							"#")[0]);
					bookingTO.setConsgTypeId(consgTypeId);
					ConsignmentTypeDO consgType = new ConsignmentTypeDO();
					consgType.setConsignmentId(consgTypeId);
					booking.setConsgTypeId(consgType);
				}
				booking.setFianlWeight(bookingTO.getFinalWeight());
				if (!StringUtil.isEmptyDouble(bookingTO.getActualWeight()))
					booking.setActualWeight(bookingTO.getActualWeight());

				// Setting Consignee and Consignor
				setUpConsigneeConsignorDtls(bookingTO, booking);

				// Setting payment mode
				if (!StringUtil.isNull(booking.getBookingPayment())
						&& !StringUtil.isEmptyInteger(booking
								.getBookingPayment().getBookingPaymentId())) {
					BookingPaymentTO payment = bookingTO.getBookingPayment();
					BookingPaymentDO bookingPayment = BookingUtils
							.setUpPaymentDetails(bookingTO.getBookingPayment());
					bookingPayment.setBookingPaymentId(booking
							.getBookingPayment().getBookingPaymentId());
					booking.setBookingPayment(bookingPayment);

					String paymentMode = payment.getPaymentMode().split("#")[1];
					payment.setPrivilegeCardAmt(bookingTO.getCnPricingDtls()
							.getFinalPrice());
					if (StringUtils.equalsIgnoreCase(
							BookingConstants.PAYMENT_MODE_PRIVILEGE_CARD,
							paymentMode)) {
						PrivilegeCardTransactionTO cardTransactionTO = serviceOfferingCommonService
								.getprivilegeCardDtls(booking.getConsgNumber());

						PrivilegeCardTransactionDO privgCardDO = BookingUtils
								.setUpPrivilegeCardDtls(payment,
										booking.getBookingType(),
										booking.getConsgNumber());

						if (!StringUtil.isEmptyInteger(cardTransactionTO
								.getPrivilegeCardTransactionId())) {
							privgCardDO
									.setPrivilegeCardTransactionId(cardTransactionTO
											.getPrivilegeCardTransactionId());
						}

						if (privgCardDO != null) {
							bookingCommonDAO
									.saveOrUpdatePrivilegeCardTransDtls(privgCardDO);
						}
					}
				}
				String consgType = bookingTO.getConsgTypeCode();
				if (StringUtils.equalsIgnoreCase(
						CommonConstants.CONSIGNMENT_TYPE_PARCEL, consgType)) {

					if (bookingTO.getVolWeight() != null
							&& bookingTO.getVolWeight() > 0) {
						booking.setVolWeight(bookingTO.getVolWeight());
						booking.setHeight(bookingTO.getHeight());
						booking.setLength(bookingTO.getLength());
						booking.setBreath(bookingTO.getBreath());
					}

					booking.setNoOfPieces(bookingTO.getNoOfPieces());
					if (!StringUtil.isNull(bookingTO.getCnContents())
							&& !StringUtil.isEmptyInteger(bookingTO
									.getCnContents().getCnContentId())
							&& bookingTO.getCnContents().getCnContentId() > 0) {
						CNContentDO content = new CNContentDO();
						content.setCnContentId(bookingTO.getCnContents()
								.getCnContentId());
						booking.setCnContentId(content);
						if (StringUtils.isNotBlank(bookingTO
								.getOtherCNContent())) {
							booking.setOtherCNContent(bookingTO
									.getOtherCNContent());
						} else {
							booking.setOtherCNContent(null);
						}
					}

					// Insured By
					InsuredByTO insuredBy = bookingTO.getInsuredBy();
					if (!StringUtil.isNull(insuredBy)) {
						Integer insuredById = insuredBy
								.getInsuredById();
						if (!StringUtil.isEmptyInteger(insuredById)) {
							if (insuredById.intValue() != insuredBy
									.getInsuredById()) {
								InsuredByDO insuredByObj = new InsuredByDO();
								insuredByObj.setInsuredById(insuredById);
								booking.setInsuredBy(insuredByObj);
							} else {
								InsuredByDO insuredByObj = new InsuredByDO();
								insuredByObj.setInsuredById(insuredById);
								booking.setInsuredBy(insuredByObj);
							}
						}

					}
					if (StringUtils
							.isNotEmpty(bookingTO.getInsurencePolicyNo()))
						booking.setInsurencePolicyNo(bookingTO
								.getInsurencePolicyNo());
					
					CNPaperWorksTO paperWorksTO=bookingTO.getCnPaperWorks();
					if(!StringUtil.isNull(paperWorksTO)){
					Integer paperworkId = bookingTO.getCnPaperWorks()
							.getCnPaperWorkId();
					if (!StringUtil.isEmptyInteger(paperworkId)) {
						CNPaperWorksDO paperWork = new CNPaperWorksDO();
						paperWork.setCnPaperWorkId(bookingTO.getCnPaperWorks()
								.getCnPaperWorkId());
						booking.setCnPaperWorkId(paperWork);
					}
					}
					String paperWorkRefNo = bookingTO.getPaperWorkRefNo();
					if (StringUtils.isNotEmpty(paperWorkRefNo))
						booking.setPaperWorkRefNo(paperWorkRefNo);

					CNPricingDetailsTO cnPricingDtls = bookingTO
							.getCnPricingDtls();
					if (!StringUtil.isEmptyDouble(cnPricingDtls
							.getDeclaredvalue()))
						booking.setDeclaredValue(cnPricingDtls
								.getDeclaredvalue());
				} else {

					booking.setVolWeight(null);
					booking.setHeight(null);
					booking.setLength(null);
					booking.setBreath(null);
					booking.setNoOfPieces(null);
					booking.setCnContentId(null);
					booking.setOtherCNContent(null);
					booking.setInsuredBy(null);
					booking.setInsurencePolicyNo(null);
					booking.setCnPaperWorkId(null);
					booking.setPaperWorkRefNo(null);
					booking.setDeclaredValue(null);
				}

				if (StringUtils.isNotEmpty(bookingTO.getRefNo()))
					booking.setRefNo(bookingTO.getRefNo());

				// updateting approver
				Integer approverId = bookingTO.getApprovedById();
				if (!StringUtil.isEmptyInteger(approverId)) {
					booking.setApprovedBy(approverId);
				} else {
					booking.setApprovedBy(null);
				}
				// Updating delivery map details
				String prodCode = booking.getConsgNumber().substring(4, 5);
				if (StringUtils.equalsIgnoreCase(
						CommonConstants.PRODUCT_SERIES_PRIORITY, prodCode)) {
					booking.setPincodeDlvTimeMapId(bookingTO.getDlvTimeMapId());
				}
				
				booking.setPrice(bookingTO.getCnPricingDtls()
							.getFinalPrice());
			
			}
		}
		LOGGER.debug("BookingCommonServiceImpl::updateBookingDomainConverter::END------------>:::::::");
		return booking;
	}

	/**
	 * Sets the up consignee consignor dtls.
	 * 
	 * @param bookingTO
	 *            the booking to
	 * @param booking
	 *            the booking
	 */
	private void setUpConsigneeConsignorDtls(
			ConsignmentModificationTO bookingTO, BookingDO booking) {
		LOGGER.debug("BookingCommonServiceImpl :: setUpConsigneeConsignorDtls :: START");
		ConsigneeConsignorDO cne = booking.getConsigneeId();
		if (!StringUtil.isNull(cne)) {
			if (!StringUtils.equalsIgnoreCase(bookingTO.getConsignee()
					.getFirstName(), cne.getFirstName())) {
				cne.setFirstName(bookingTO.getConsignee().getFirstName());
			}
			if (!StringUtils.equalsIgnoreCase(bookingTO.getConsignee()
					.getMobile(), cne.getMobile())) {
				cne.setMobile(bookingTO.getConsignee().getMobile());
			}
			if (!StringUtils.equalsIgnoreCase(bookingTO.getConsignee()
					.getPhone(), cne.getPhone())) {
				cne.setPhone(bookingTO.getConsignee().getPhone());
			}
			if (!StringUtils.equalsIgnoreCase(bookingTO.getConsignee()
					.getAddress(), cne.getAddress())) {
				cne.setAddress(bookingTO.getConsignee().getAddress());
			}
			booking.setConsigneeId(cne);
		} else {
			cne = new ConsigneeConsignorDO();
			cne.setFirstName(bookingTO.getConsignee().getFirstName());
			cne.setMobile(bookingTO.getConsignee().getMobile());
			cne.setPhone(bookingTO.getConsignee().getPhone());
			cne.setAddress(bookingTO.getConsignee().getAddress());
			booking.setConsigneeId(cne);
		}

		ConsigneeConsignorDO cnr = booking.getConsignorId();
		if (!StringUtil.isNull(cnr)) {
			if (!StringUtils.equalsIgnoreCase(bookingTO.getConsignor()
					.getFirstName(), cnr.getFirstName())) {
				cnr.setFirstName(bookingTO.getConsignor().getFirstName());
			}
			if (!StringUtils.equalsIgnoreCase(bookingTO.getConsignor()
					.getMobile(), cnr.getMobile())) {
				cnr.setMobile(bookingTO.getConsignor().getMobile());

			}
			if (!StringUtils.equalsIgnoreCase(bookingTO.getConsignor()
					.getPhone(), cnr.getPhone())) {
				cnr.setPhone(bookingTO.getConsignor().getPhone());
			}
			if (!StringUtils.equalsIgnoreCase(bookingTO.getConsignor()
					.getAddress(), cnr.getAddress())) {
				cnr.setAddress(bookingTO.getConsignor().getAddress());
			}
			booking.setConsignorId(cnr);
		} else {
			cnr = new ConsigneeConsignorDO();
			cnr.setFirstName(bookingTO.getConsignee().getFirstName());
			cnr.setMobile(bookingTO.getConsignee().getMobile());
			cnr.setPhone(bookingTO.getConsignee().getPhone());
			cnr.setAddress(bookingTO.getConsignee().getAddress());
			booking.setConsigneeId(cnr);
		}
		LOGGER.debug("BookingCommonServiceImpl :: setUpConsigneeConsignorDtls :: End");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getPaperWorkByPincode
	 * (java.lang.String, java.lang.String)
	 */
	public CNPaperWorksTO getPaperWorkByPincode(String pincode,
			String paperWorkName) throws CGSystemException, CGBusinessException {
		return serviceOfferingCommonService.getPaperWorkByPincode(pincode,
				paperWorkName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getCNContentByName(java
	 * .lang.String)
	 */
	public CNContentTO getCNContentByName(String cnContentName)
			throws CGSystemException, CGBusinessException {
		return serviceOfferingCommonService.getCNContentByName(cnContentName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getCustomerByIdOrCode
	 * (java.lang.Integer, java.lang.String)
	 */
	public CustomerTO getCustomerByIdOrCode(Integer customerId,
			String customerCode) throws CGSystemException, CGBusinessException {
		return businessCommonService.getCustomerByIdOrCode(customerId,
				customerCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.booking.service.BookingCommonService#
	 * getBusinessAssociateByIdOrCode(java.lang.Integer, java.lang.String)
	 */
	/*
	 * public BusinessAssociateTO getBusinessAssociateByIdOrCode(Integer baId,
	 * String baCode) throws CGSystemException, CGBusinessException { return
	 * businessCommonService.getBusinessAssociateByIdOrCode(baId, baCode); }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getInsuredByNameOrCode
	 * (java.lang.String, java.lang.String)
	 */
	public InsuredByTO getInsuredByNameOrCode(String insuredByName,
			String insuredByCode, Integer insuredById)
			throws CGSystemException, CGBusinessException {
		return serviceOfferingCommonService.getInsuredByNameOrCode(
				insuredByName, insuredByCode, insuredById);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#saveOrUpdateConsignment
	 * (com.ff.booking.BookingParcelTO)
	 */
	public void updateConsignment(BookingParcelTO bookingTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BookingCommonServiceImpl::updateConsignment::START------------>:::::::");
		// Setting CNPricing details
		ConsignmentTO consgTO = new ConsignmentTO();
		Integer consgTypeId = Integer.parseInt(bookingTO.getConsgTypeName()
				.split("#")[0]);
		consgTO.setConsgTypeId(consgTypeId);
		consgTO.setConsgNo(bookingTO.getConsgNumber());
		consgTO.setOrgOffId(bookingTO.getBookingOfficeId());
		PincodeTO pincode = new PincodeTO();
		pincode.setPincodeId(bookingTO.getPincodeId());
		consgTO.setDestPincode(pincode);
		consgTO.setPrice(bookingTO.getPrice());
		consgTO.setUpdatedProcessFrom(bookingTO.getProcessTO());
		consgTO.setNoOfPcs(bookingTO.getNoOfPieces());
		if (bookingTO.getFinalWeight() != null
				&& bookingTO.getFinalWeight() > 0)
			consgTO.setFinalWeight(bookingTO.getFinalWeight());
		if (bookingTO.getActualWeight() != null
				&& bookingTO.getActualWeight() > 0)
			consgTO.setActualWeight(bookingTO.getActualWeight());
		if (bookingTO.getVolWeight() != null && bookingTO.getVolWeight() > 0) {
			VolumetricWeightTO volWeight = new VolumetricWeightTO();
			volWeight.setVolWeight(bookingTO.getVolWeight());
			consgTO.setVolWightDtls(volWeight);
			if (bookingTO.getLength() != null && bookingTO.getLength() > 0)
				volWeight.setVolWeight(bookingTO.getLength());
			if (bookingTO.getHeight() != null && bookingTO.getHeight() > 0)
				volWeight.setHeight(volWeight.getHeight());
			if (bookingTO.getBreath() != null && bookingTO.getBreath() > 0)
				volWeight.setBreadth(bookingTO.getBreath());
			consgTO.setVolWightDtls(volWeight);
		}
		// Child Consignments
		if (StringUtils.isNotEmpty(bookingTO.getChildCNsDtls())) {
			Set<ChildConsignmentTO> chilsCns = BookingUtils
					.setUpChildConsignmentTOs(bookingTO.getChildCNsDtls());
			if (!StringUtil.isEmptyColletion(chilsCns))
				consgTO.setChildTOSet(chilsCns);
		}
		// Setting CN Content
		if (!StringUtil.isEmptyInteger(bookingTO.getCnContentId())) {
			CNContentTO content = new CNContentTO();
			content.setCnContentId(bookingTO.getCnContentId());
			content.setOtherContent(bookingTO.getOtherCNContent());
			consgTO.setCnContents(content);
		}
		// Setting Paperworks
		if (!StringUtil.isEmptyInteger(bookingTO.getCnPaperworkId())) {
			CNPaperWorksTO cnPaperwork = new CNPaperWorksTO();
			cnPaperwork.setCnPaperWorkId(bookingTO.getCnPaperworkId());
			cnPaperwork.setPaperWorkRefNum(bookingTO.getPaperWorkRefNo());
			consgTO.setCnPaperWorks(cnPaperwork);
		}
		// Setting insured by
		if (!StringUtil.isEmptyInteger(bookingTO.getInsuredById())) {
			InsuredByTO insuredBy = new InsuredByTO();
			insuredBy.setInsuredById(bookingTO.getInsuredById());
			insuredBy.setPolicyNo(bookingTO.getPolicyNo());
			consgTO.setInsuredByTO(insuredBy);
		}
		// Setting Mobile
		if (!StringUtil.isNull(bookingTO.getConsignee())) {
			consgTO.setMobileNo(bookingTO.getConsignee().getMobile());
		}
		ProductTO productTO = getProductByConsgSeries(bookingTO
				.getConsgNumber().substring(4, 5));
		if (productTO != null && productTO.getProductId() != null
				&& productTO.getProductId() > 0)
			consgTO.setProductId(productTO.getProductId());
		// Setting Declared value
		/*
		 * if (!StringUtil.isEmptyDouble(bookingTO.getDeclaredValue()))
		 * consgTO.setDeclaredValue(bookingTO.getDeclaredValue());
		 */
		List<ConsignmentTO> consgTOs = new ArrayList<ConsignmentTO>(1);
		consgTOs.add(consgTO);
		consignmentService.saveOrUpdateConsignment(consgTOs);
		LOGGER.debug("BookingCommonServiceImpl::updateConsignment::END------------>:::::::");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#saveOrUpdateConsignment
	 * (com.ff.booking.BookingTO)
	 */
	public void updateConsignment(BookingTO bookingTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BookingCommonServiceImpl::updateConsignment::START------------>:::::::");
		// Setting CNPricing details
		ConsignmentTO consgTO = new ConsignmentTO();
		Integer consgTypeId = Integer.parseInt(bookingTO.getConsgTypeName()
				.split("#")[0]);
		consgTO.setConsgTypeId(consgTypeId);
		consgTO.setConsgNo(bookingTO.getConsgNumber());
		consgTO.setOrgOffId(bookingTO.getBookingOfficeId());
		PincodeTO pincode = new PincodeTO();
		pincode.setPincodeId(bookingTO.getPincodeId());
		consgTO.setDestPincode(pincode);
		consgTO.setPrice(bookingTO.getPrice());
		consgTO.setUpdatedProcessFrom(bookingTO.getProcessTO());
		if (bookingTO.getFinalWeight() != null
				&& bookingTO.getFinalWeight() > 0)
			consgTO.setFinalWeight(bookingTO.getFinalWeight());
		if (bookingTO.getActualWeight() != null
				&& bookingTO.getActualWeight() > 0)
			consgTO.setActualWeight(bookingTO.getActualWeight());
		// Setting Mobile
		if (!StringUtil.isNull(bookingTO.getConsignee())) {
			consgTO.setConsigneeTO(bookingTO.getConsignee());
		}
		if (!StringUtil.isNull(bookingTO.getConsignor())) {
			consgTO.setConsignorTO(bookingTO.getConsignor());
		}
		consgTO.setConsgPriceDtls(bookingTO.getCnPricingDtls());
		ProductTO productTO = getProductByConsgSeries(bookingTO
				.getConsgNumber().substring(4, 5));
		if (productTO != null && productTO.getProductId() != null
				&& productTO.getProductId() > 0)
			consgTO.setProductId(productTO.getProductId());
		List<ConsignmentTO> consgTOs = new ArrayList<ConsignmentTO>(1);
		consgTOs.add(consgTO);
		consignmentService.saveOrUpdateConsignment(consgTOs);
		LOGGER.debug("BookingCommonServiceImpl::updateConsignment::END------------>:::::::");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getCityByIdOrCode(java
	 * .lang.Integer, java.lang.String)
	 */
	public CityTO getCityByIdOrCode(Integer cityId, String cityCode)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BookingCommonServiceImpl :: getCityByIdOrCode :: Start"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		CityTO cityTO = null;
		CityTO city = new CityTO();
		city.setCityId(cityId);
		city.setCityCode(cityCode);
		List<CityTO> cities = geographyCommonService.getCitiesByCity(city);
		if (!StringUtil.isEmptyList(cities))
			cityTO = cities.get(0);
		else {
			MessageWrapper msgWrapper = ExceptionUtil
					.getMessageWrapper(UdaanCommonErrorCodes.ENTITY_NOT_FOUND,
							MessageType.Warning);
			throw new CGBusinessException(msgWrapper);
		}
		LOGGER.debug("BookingCommonServiceImpl : :getCityByIdOrCode :: End"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return cityTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getOfficeByIdOrCode(java
	 * .lang.Integer, java.lang.String)
	 */
	@Override
	public OfficeTO getOfficeByIdOrCode(Integer officeId, String offCode)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		return organizationCommonService.getOfficeByIdOrCode(officeId, offCode);
	}

	public PickupDeliveryLocationTO getPickupDlvLocation(Integer customerId,
			Integer officeId) throws CGBusinessException, CGSystemException {
		return pickupManagementCommonService.getPickupDlvLocation(customerId,
				officeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getProcess(com.ff.tracking
	 * .ProcessTO)
	 */
	public String getProcessNumber(String processCode, String officeCode)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BookingCommonServiceImpl :: getProcessNumber :: Start");
		String processNo = null;
		if (StringUtils.isNotEmpty(processCode)
				&& StringUtils.isNotEmpty(officeCode)) {
			ProcessTO process = new ProcessTO();
			process.setProcessCode(processCode);
			OfficeTO office = new OfficeTO();
			office.setOfficeCode(officeCode);
			processNo = trackingUniversalService.createProcessNumber(process,
					office);
		}
		LOGGER.debug("BookingCommonServiceImpl :: getProcessNumber :: End");
		return processNo;
	}

/*	public boolean saveProcessMapBooking(List<? extends BookingTO> bookingTOs)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BookingCommonServiceImpl :: saveProcessMapBooking :: Start");
		List<TrackingProcessTO> trackingProcessTOs = new ArrayList<TrackingProcessTO>(
				bookingTOs.size());
		for (BookingTO bookingTO : bookingTOs) {
			TrackingProcessTO trackingProcessTO = new TrackingProcessTO();
			ProcessMapTO processMapTO = prepareInputsForProcessMap(bookingTO);
			trackingProcessTO.setProcessMapTO(processMapTO);
			// Setting Consignment
			ConsignmentTO consg = new ConsignmentTO();
			consg.setOrgOffId(bookingTO.getBookingOfficeId());
			CityTO city = new CityTO();
			city.setCityId(bookingTO.getCityId());
			consg.setDestCity(city);
			// Setting officeTo
			OfficeTO office = new OfficeTO();
			office.setOfficeId(bookingTO.getBookingOfficeId());
			office.setCityId(bookingTO.getCityId());
			trackingProcessTO.setConsgTO(consg);
			trackingProcessTO.setOfficeTO(office);
			trackingProcessTOs.add(trackingProcessTO);
		}
		trackingUniversalService.saveProcessMap(trackingProcessTOs);
		LOGGER.debug("BookingCommonServiceImpl :: saveProcessMapBooking :: End");
		return true;
	}
*/
	/*private ProcessMapTO prepareInputsForProcessMap(BookingTO bookingTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BookingCommonServiceImpl :: prepareInputsForProcessMap :: Start");
		ProcessMapTO processMapTO = new ProcessMapTO();
		List<TrackingParameterTO> trackingParameterTOs = new ArrayList<>();
		ProcessTO processTO = new ProcessTO();
		processMapTO.setConsgNo(bookingTO.getConsgNumber());
		processTO.setProcessCode(CommonConstants.PROCESS_BOOKING);
		processTO = trackingUniversalService.getProcess(processTO);
		processMapTO.setProcessNumber(bookingTO.getProcessNumber());
		setupTrackingParameterTO(trackingParameterTOs,
				UniversalTrackingConstants.BRANCH_OFFICE,
				bookingTO.getBookingOffCode());
		setupTrackingParameterTO(trackingParameterTOs,
				UniversalTrackingConstants.CITY, bookingTO.getOriginCity());
		setupTrackingParameterTO(trackingParameterTOs,
				UniversalTrackingConstants.WEIGHT, bookingTO.getFinalWeight()
						.toString());
		if (!StringUtil.isEmptyInteger(bookingTO.getNoOfPieces())) {
			setupTrackingParameterTO(trackingParameterTOs,
					UniversalTrackingConstants.NO_PIECES, bookingTO
							.getNoOfPieces().toString());
		}
		//
		processMapTO.setParameterTOs(trackingParameterTOs);
		processMapTO.setProcessOrder(processTO.getProcessOrder());
		processMapTO.setArtifactType(CommonConstants.CONSIGNMENT);
		LOGGER.debug("BookingCommonServiceImpl :: prepareInputsForProcessMap :: End");
		return processMapTO;
	}*/

	/*private void setupTrackingParameterTO(
			List<TrackingParameterTO> trackingParameterTOs, String paramKey,
			String paramValue) {
		LOGGER.debug("BookingCommonServiceImpl :: setupTrackingParameterTO :: Start");
		TrackingParameterTO trackingParameterTO = new TrackingParameterTO();
		trackingParameterTO.setParamKey(paramKey);
		trackingParameterTO.setParamValue(paramValue);
		trackingParameterTOs.add(trackingParameterTO);
		LOGGER.debug("BookingCommonServiceImpl :: setupTrackingParameterTO :: End");
	}*/

	@Override
	public CNPricingDetailsTO getConsgRate(RateCalculationInputTO inputTO)
			throws CGBusinessException, CGSystemException {
		CNPricingDetailsTO consgRateDtls = new CNPricingDetailsTO();
		LOGGER.debug("BookingCommonServiceImpl :: getConsgRate :: Start");
		try {
			/*
			 * RateCalculationService rateService = rateCalcFactory
			 * .getService(inputTO.getRateType());
			 */
			/*
			 * // result = rateService.calculateRate(inputTO); RateComponentTO
			 * rate1 = new RateComponentTO(); rate1.setRateComponentId(1);
			 * rate1.setRateComponentCode("FNSLB");
			 * rate1.setCalculatedValue(100.00); components.add(rate1);
			 * 
			 * RateComponentTO rate2 = new RateComponentTO();
			 * rate2.setRateComponentId(2); rate2.setRateComponentCode("FSCHG");
			 * rate2.setCalculatedValue(1.00); components.add(rate2);
			 * 
			 * RateComponentTO rate3 = new RateComponentTO();
			 * rate3.setRateComponentId(3); rate3.setRateComponentCode("RSKCG");
			 * rate3.setCalculatedValue(1.00); components.add(rate3);
			 * 
			 * RateComponentTO rate4 = new RateComponentTO();
			 * rate4.setRateComponentId(4); rate4.setRateComponentCode("TPCHG");
			 * rate4.setCalculatedValue(1.00); components.add(rate4);
			 * 
			 * RateComponentTO rate5 = new RateComponentTO();
			 * rate5.setRateComponentId(7); rate5.setRateComponentCode("ARHCG");
			 * rate5.setCalculatedValue(1.00); components.add(rate5);
			 * 
			 * RateComponentTO rate6 = new RateComponentTO();
			 * rate6.setRateComponentId(12);
			 * rate6.setRateComponentCode("SRVTX");
			 * rate6.setCalculatedValue(1.00); components.add(rate6);
			 * 
			 * RateComponentTO rate7 = new RateComponentTO();
			 * rate7.setRateComponentId(12);
			 * rate7.setRateComponentCode("HEDCS");
			 * rate7.setCalculatedValue(1.00); components.add(rate7);
			 * 
			 * RateComponentTO rate8 = new RateComponentTO();
			 * rate8.setRateComponentId(13);
			 * rate8.setRateComponentCode("EDUCS");
			 * rate8.setCalculatedValue(1.00); components.add(rate8);
			 * 
			 * RateComponentTO rate9 = new RateComponentTO();
			 * rate9.setRateComponentId(10);
			 * rate9.setRateComponentCode("OTSCG");
			 * rate9.setCalculatedValue(1.00); components.add(rate9);
			 * 
			 * result = new RateCalculationOutputTO();
			 * result.setComponents(components);
			 */
			consgRateDtls.setAirportHandlingChg(1.00);
			consgRateDtls.setFinalPrice(100.00);
			consgRateDtls.setCodChg(11.00);
			consgRateDtls.setFuelChg(1.00);
			consgRateDtls.setServiceTax(1.00);
			consgRateDtls.setRiskSurChg(1.00);
			consgRateDtls.setTopayChg(1.00);
			consgRateDtls.setEduCessChg(1.00);
			consgRateDtls.setHigherEduCessChg(1.00);
			consgRateDtls.setFreightChg(100.00);

		} catch (Exception e) {
			LOGGER.error(
					"BookingCommonServiceImpl::getConsgRate::error in rate calculation::",
					e);
			throw new CGBusinessException(e);
		}
		LOGGER.debug("BookingCommonServiceImpl :: getConsgRate :: End");
		return consgRateDtls;
	}

	public ConsignmentRateCalculationOutputTO calcRateForConsingment(
			ConsignmentTO inputTO) throws CGBusinessException,
			CGSystemException {
		long startTimeInMilis=System.currentTimeMillis();
	    StringBuffer logger = new StringBuffer();
	    logger.append("BookingCommonServiceImpl :: calcRateForConsingment :: Start  :: Consignment No::--->"+inputTO.getConsgNo());
	    if(!StringUtil.isNull(inputTO.getCustomerTO())){
	    	logger.append(" Customer Code ::" + inputTO.getCustomerTO().getCustomerCode());
	    }
	    logger.append(" Rate Type ::" + inputTO.getConsgPriceDtls().getRateType());
	    logger.append(" StartTime :: " + startTimeInMilis);
		LOGGER.debug(logger.toString());
		ConsignmentRateCalculationOutputTO rateOutput = null;
		String productCode=inputTO.getProductTO().getConsgSeries();
		try {
			rateOutput = rateCalculationUniversalService
					.calculateRateForConsignment(inputTO);
			if (!StringUtil.isNull(rateOutput)) {
				switch (productCode) {
				case CommonConstants.PRODUCT_SERIES_LETTER_OF_CREDIT:
					if (StringUtil.isEmptyDouble(rateOutput.getLcAmount())) {
						throw new CGBusinessException(
								BookingErrorCodesConstants.RATE_RETURN_NULL_FOR_LC);
					}
					break;

				case CommonConstants.PRODUCT_SERIES_CASH_COD:
					if (StringUtil.isEmptyDouble(rateOutput.getCodAmount())) {
						throw new CGBusinessException(
								BookingErrorCodesConstants.RATE_RETURN_NULL_FOR_COD);
					}
					break;

				}
			} else {
				LOGGER.error(
						"BookingCommonServiceImpl::calcRateForConsingment::throwing business exception due to rate unavailability");
				throw new CGBusinessException(
						BookingErrorCodesConstants.RATE_NOT_CALCULATED);
			}
		} catch (CGSystemException e) {
			LOGGER.error(
					"BookingCommonServiceImpl::calcRateForConsingment::------------>:::::::",
					e);
			throw e;// new CGBusinessException(e);
		}
		long endTimeInMilis=System.currentTimeMillis();
		
		LOGGER.debug("BookingCommonServiceImpl :: calcRateForConsingment :: End"+"  Consignment No::--->"+inputTO.getConsgNo()
				+" End Time ::"+ endTimeInMilis+"  Time Difference in miliseconds:: "+(endTimeInMilis-startTimeInMilis)+" Time Difference in HH:MM:SS :: "+DateUtil.convertMilliSecondsTOHHMMSSStringFormat(endTimeInMilis-startTimeInMilis));
		return rateOutput;
	}

	public ApplScreensTO getScreenByCodeOrName(String screenCode,
			String screenName) throws CGBusinessException, CGSystemException {
		return globalService.getScreenByCodeOrName(screenCode, screenName);
	}

	private ConsignmentTO setBillingStatus(ConsignmentTO consg) {
		LOGGER.debug("BookingCommonServiceImpl :: setBillingStatus :: Start");
		consg.setBillingStatus(CommonConstants.BILLING_STATUS_TBB);
		consg.setChangedAfterBillingWtDest(CommonConstants.NO);
		consg.setChangedAfterNewRateCmpnt(CommonConstants.NO);
		LOGGER.debug("BookingCommonServiceImpl :: setBillingStatus :: End");
		return consg;
	}

	public InsuranceConfigTO validateInsuarnceConfigDtls(Double declarebValue,
			String bookingType, Integer insuredById) throws CGSystemException,
			CGBusinessException {
		return serviceOfferingCommonService.validateInsuarnceConfigDtls(
				declarebValue, bookingType, insuredById);
	}

	public List<Integer> updateConsignment(ConsignmentModificationTO bookingTO,
			Map<String, ConsignmentRateCalculationOutputTO> rateCompnents)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BookingCommonServiceImpl :: updateConsignment :: Start");
		// Setting CNPricing details
		ConsignmentTO consgTO = new ConsignmentTO();
		CNPricingDetailsTO cnPricingTO = bookingTO.getCnPricingDtls();

		consgTO.setConsgNo(bookingTO.getConsgNumber());
		consgTO.setOrgOffId(bookingTO.getBookingOfficeId());
		consgTO.setOperatingOffice(bookingTO.getBookingOfficeId());

		PincodeTO pincode = new PincodeTO();
		pincode.setPincodeId(bookingTO.getPincodeId());
		pincode.setPincode(bookingTO.getPincode());
		consgTO.setDestPincode(pincode);
		
		Integer consgTypeId = 0;
		if (StringUtils.isNotEmpty(bookingTO.getConsgTypeName())) {
			consgTypeId = Integer.parseInt(bookingTO.getConsgTypeName().split(
					"#")[0]);
			consgTO.setConsgTypeId(consgTypeId);
			consgTO.setConsgTypeCode(bookingTO.getConsgTypeName().split(
					"#")[1]);
		}

		consgTO.setPrice(bookingTO.getPrice());
		
		CNPricingDetailsTO cnPricingDtls = bookingTO
				.getCnPricingDtls();
		if (!StringUtil.isEmptyDouble(cnPricingDtls
				.getDeclaredvalue())){
			consgTO.setDeclaredValue(cnPricingDtls
					.getDeclaredvalue());
		}else {
			consgTO.setDeclaredValue(0.0);
		}

		consgTO.setPrice(cnPricingDtls.getFinalPrice());
		consgTO.setSplChg(cnPricingDtls.getSplChg());
		
		consgTO.setUpdatedProcessFrom(bookingTO.getProcessTO());

		if (bookingTO.getFinalWeight() != null
				&& bookingTO.getFinalWeight() > 0)
			consgTO.setFinalWeight(bookingTO.getFinalWeight());

		if (bookingTO.getActualWeight() != null
				&& bookingTO.getActualWeight() > 0)
			consgTO.setActualWeight(bookingTO.getActualWeight());

			if (bookingTO.getVolWeight() != null
					&& bookingTO.getVolWeight() > 0) {
				VolumetricWeightTO volWeight = new VolumetricWeightTO();
				volWeight.setVolWeight(bookingTO.getVolWeight());
				if (bookingTO.getLength() != null && bookingTO.getLength() > 0)
					volWeight.setLength(bookingTO.getLength());
				if (bookingTO.getHeight() != null && bookingTO.getHeight() > 0)
					volWeight.setHeight(bookingTO.getHeight());
				if (bookingTO.getBreath() != null && bookingTO.getBreath() > 0)
					volWeight.setBreadth(bookingTO.getBreath());
				consgTO.setVolWightDtls(volWeight);
			}
			// Child Consignments
			if (StringUtils.isNotEmpty(bookingTO.getChildCNsDtls())) {
				Set<ChildConsignmentTO> chilsCns = BookingUtils
						.setUpChildConsignmentTOs(bookingTO.getChildCNsDtls());
				if (!StringUtil.isEmptyColletion(chilsCns))
					consgTO.setChildTOSet(chilsCns);
			} else {
				consgTO.setChildTOSet(null);
			}

			if (!StringUtil.isNull(bookingTO.getCnContents())
					&& !StringUtil.isEmptyInteger(bookingTO.getCnContents()
							.getCnContentId())
					&& bookingTO.getCnContents().getCnContentId() > 0) {
				CNContentTO contentTO = bookingTO.getCnContents();
				if (StringUtils.isNotBlank(bookingTO.getOtherCNContent())) {
					contentTO.setOtherContent(bookingTO.getOtherCNContent());
				} else {
					contentTO.setOtherContent(null);
				}
				consgTO.setCnContents(contentTO);

			} else if (StringUtil.isEmptyInteger(bookingTO.getCnContents()
					.getCnContentId())){
				CNContentTO contentTO = bookingTO.getCnContents();
				contentTO.setOtherContent(new String());
				consgTO.setCnContents(contentTO);
			}

			if (!StringUtil.isEmptyInteger(bookingTO.getNoOfPieces())) {
				consgTO.setNoOfPcs(bookingTO.getNoOfPieces());
			}else{
				consgTO.setNoOfPcs(0);
			}

			// Setting Paperworks
			if (!StringUtil.isNull(bookingTO.getCnPaperWorks())) {
				bookingTO.getCnPaperWorks().setPaperWorkRefNum(
						bookingTO.getPaperWorkRefNo());
				consgTO.setCnPaperWorks(bookingTO.getCnPaperWorks());
			}
			// Setting insured by
			if (!StringUtil.isNull(bookingTO.getInsuredBy())) {
				InsuredByTO byTO = bookingTO.getInsuredBy();
				if (!StringUtil.isNull(bookingTO.getInsurencePolicyNo())) {
					byTO.setPolicyNo(bookingTO.getInsurencePolicyNo());
				}
				consgTO.setInsuredByTO(byTO);
			}

		
		if (!StringUtil.isEmptyDouble(cnPricingTO.getDiscount())) {
			consgTO.setDiscount(cnPricingTO.getDiscount());
		} else {
			consgTO.setDiscount(0.0);
		}
		// Setting Mobile
		if (!StringUtil.isNull(bookingTO.getConsignee())) {
			consgTO.setConsigneeTO(bookingTO.getConsignee());
		}
		if (!StringUtil.isNull(bookingTO.getConsignor())) {
			consgTO.setConsignorTO(bookingTO.getConsignor());
		}
		String productCode = bookingTO.getConsgNumber().substring(4, 5);
		ProductTO productTO = getProductByConsgSeries(productCode);
		if (productTO != null && productTO.getProductId() != null
				&& productTO.getProductId() > 0) {
			consgTO.setProductId(productTO.getProductId());
			consgTO.setProductTO(productTO);
		}
		// Re-Calculating Rate for Consignment
		Date bookingDate = DateUtil
				.parseStringDateToDDMMYYYYHHMMFormat(bookingTO.getBookingDate());
		consgTO.setBookingDate(bookingDate);
		ConsignmentTypeTO cnType = new ConsignmentTypeTO();
		cnType.setConsignmentCode(bookingTO.getConsgTypeCode());
		consgTO.setTypeTO(cnType);

		// for all booking and its available in cash booking only
		if (StringUtils.equalsIgnoreCase(
				CommonConstants.PRODUCT_SERIES_PRIORITY, productCode)) {
			cnPricingTO.setServicesOn("A");
		}
		consgTO.setConsgPriceDtls(cnPricingTO);
		if (!StringUtil.isEmptyInteger(bookingTO.getCustomerId()))
			consgTO.setCustomer(bookingTO.getCustomerId());

		if (!CGCollectionUtils.isEmpty(rateCompnents)) {
			consgTO.setConsgRateOutputTOs(rateCompnents);
		}
		
		if (StringUtils.isNotEmpty(bookingTO.getRefNo())){
			consgTO.setRefNo(bookingTO.getRefNo());
		}
		consgTO.setReCalcRateReq(Boolean.TRUE);
		List<ConsignmentTO> consgTOs = new ArrayList<ConsignmentTO>(1);
		consgTOs.add(consgTO);
		List<Integer> updatedCNIds = consignmentService
				.updateConsignmentForViewEdit(consgTOs);
		LOGGER.debug("BookingCommonServiceImpl :: updateConsignment :: End");

		return updatedCNIds;
	}

	public Integer getConsgOperatingLevel(ConsignmentTO consgTO,
			OfficeTO loggedInOffice) throws CGBusinessException,
			CGSystemException {
		return consignmentService.getConsgOperatingLevel(consgTO,
				loggedInOffice);
	}

	public Integer getChildConsgIdByConsgNo(String consgNumber)
			throws CGSystemException {
		return consignmentService.getChildConsgIdByConsgNo(consgNumber);
	}

	public PaymentModeTO getPaymentMode(String processCode, String payModeCode)
			throws CGBusinessException, CGSystemException {
		return serviceOfferingCommonService.getPaymentMode(processCode,
				payModeCode);
	}

	@Override
	public boolean isConsgNoManifestedForBooking(final String consigNum)
			throws CGSystemException, CGBusinessException {
		return consignmentCommonDAO.isConsgNoManifestedForBooking(consigNum);

	}

	@Override
	public List<CustomerTO> getContractCustomerList(CustomerTO customerTO)
			throws CGSystemException, CGBusinessException {
		return bookingUniversalService.getContractCustomerList(customerTO);
	}

	public void createConsingment(List<ConsignmentTO> validConsignments)
			throws CGSystemException, CGBusinessException {
		consignmentService.createConsingment(validConsignments);
	}

	/*
	 * public Map<String, ConsignmentRateCalculationOutputTO> getConsgRateDtls()
	 * { return bookingContextService.getConsgRateDtls(); }
	 */

	public CustomerTO getCustomer(Integer customerId) throws CGSystemException,
			CGBusinessException {
		return businessCommonService.getCustomer(customerId);
	}

	@Override
	public List<BookingTypeDO> getAllBookingType() throws CGSystemException,
			CGBusinessException {
		return bookingCommonDAO.getAllBookingType();

	}

	@Override
	public void saveBookingAndConsignment(List<BookingDO> bookings,
			List<ConsignmentDO> consinments) throws CGSystemException,
			CGBusinessException {
		bookingCommonDAO.batchBookingSaveUpdate(bookings);
		consignmentCommonDAO.saveOrUpdateConsignments(consinments);
	}

	@Override
	public void saveBookingAndConsignment(BookingDO booking,
			ConsignmentDO consinment) throws CGSystemException,
			CGBusinessException {
		bookingCommonDAO.createBooking(booking);
		consignmentCommonDAO.createConsignment(consinment);
	}

	@Override
	public ProductDO getProductByProductId(Integer productId)
			throws CGSystemException, CGBusinessException {
		return serviceOfferingCommonService.getProductByProductId(productId);
	}

	@Override
	public PincodeTO getPincodeByPincodeId(Integer pincodeId)
			throws CGSystemException, CGBusinessException {
		return geographyCommonService.getPincode(pincodeId);
	}

	public ConsignmentTypeConfigTO getConsgTypeConfigDtls(
			ConsignmentTypeConfigTO consgTypeConfigTO)
			throws CGSystemException, CGBusinessException {
		return serviceOfferingCommonService
				.getConsgTypeConfigDtls(consgTypeConfigTO);
	}

	public List<CustomerTO> getCustomerForContractByShippedToCode(
			String customerCode) throws CGSystemException, CGBusinessException {
		return businessCommonService
				.getCustomerForContractByShippedToCode(customerCode);
	}

	public boolean isChildConsgBooked(String consgNumber)
			throws CGSystemException, CGBusinessException {
		boolean isCNBooked = Boolean.FALSE;
		LOGGER.debug("BookingCommonServiceImpl :: isChildConsgBooked :: Start");
		try {
			isCNBooked = bookingCommonDAO.isChildConsgBooked(consgNumber);
		} catch (Exception e) {
			LOGGER.debug("Exception in isChildConsgBooked() :" + e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("BookingCommonServiceImpl :: isChildConsgBooked :: End");
		return isCNBooked;

	}

	@Override
	public void process2WayWrite(Integer bookingId, Integer cnId) {
		try {
			if (TwoWayWriteProcessCall.isTwoWayWriteEnabled()
					&& !StringUtil.isEmptyInteger(bookingId)
					&& !StringUtil.isEmptyInteger(cnId)) {
				TwoWayWriteProcessCall.twoWayWriteProcess(bookingId,
						CommonConstants.TWO_WAY_WRITE_PROCESS_BOOKING);
				TwoWayWriteProcessCall.twoWayWriteProcess(cnId,
						CommonConstants.TWO_WAY_WRITE_PROCESS_CONSIGNMENT);
			}
		} catch (Exception ex) {
			LOGGER.error(
					"BookingCommonServiceImpl :: process2WayWrite :: error: ",
					ex);
		}
	}

	@Override
	public void process2WayWrite(List<Integer> bookingIds, List<Integer> cnIds) {
		LOGGER.debug("BookingCommonServiceImpl :: process2WayWrite :: start");
		try {
			List<String> bookingProcess = new ArrayList<>();
			List<String> consgProcess = new ArrayList<>();
			if (!StringUtil.isEmptyColletion(bookingIds)
					&& !StringUtil.isEmptyColletion(cnIds)) {
				// TwoWayWriteProcessCall.twoWayWriteProcess(bookingIds,
				// CommonConstants.TWO_WAY_WRITE_PROCESS_BOOKING);
				// TwoWayWriteProcessCall.twoWayWriteProcess(bookingIds,
				// CommonConstants.TWO_WAY_WRITE_PROCESS_CONSIGNMENT);
				for (int i = 0; i < bookingIds.size(); i++) {
					bookingProcess
							.add(CommonConstants.TWO_WAY_WRITE_PROCESS_BOOKING);
					consgProcess
							.add(CommonConstants.TWO_WAY_WRITE_PROCESS_CONSIGNMENT);
					// process2WayWrite(bookingIds.get(i), cnIds.get(i));
				}
				bookingIds.addAll(cnIds);
				bookingProcess.addAll(consgProcess);
				TwoWayWriteProcessCall.twoWayWriteProcess(
						(ArrayList<Integer>) bookingIds,
						(ArrayList<String>) bookingProcess);
			}
		} catch (Exception ex) {
			LOGGER.error(
					"BookingCommonServiceImpl :: process2WayWrite :: error: ",
					ex);
		}
		LOGGER.debug("BookingCommonServiceImpl :: process2WayWrite :: end");
	}

	@Override
	public void process2WayWrite(BookingWrapperDO bookingWrapperDO) {
		LOGGER.debug("BookingCommonServiceImpl :: process2WayWrite :: start");
		try {
			List<Integer> bookingIds = null;
			List<Integer> cnIds = null;
			if (bookingWrapperDO.isBulkSave()) {
				List<BookingDO> bookings = bookingWrapperDO
						.getSucessConsignments();
				List<ConsignmentDO> consignments = bookingWrapperDO
						.getConsingments();
				if (bookings == null || bookings.isEmpty()
						|| consignments == null || consignments.isEmpty())
					return;

				bookingIds = new ArrayList(bookings.size());
				cnIds = new ArrayList(bookings.size());
				for (int i = 0; i < bookings.size(); i++) {
					bookingIds.add(bookings.get(i).getBookingId());
					cnIds.add(consignments.get(i).getConsgId());
				}
			} else {
				bookingIds = bookingWrapperDO.getSuccessBookingsIds();
				cnIds = bookingWrapperDO.getSuccessCNsIds();
			}
			if (!StringUtil.isEmptyColletion(bookingIds)
					&& !StringUtil.isEmptyColletion(cnIds))
				process2WayWrite(bookingIds, cnIds);
		} catch (Exception ex) {
			LOGGER.error(
					"Exception Occurred in ::BookingCommonServiceImpl:: process2WayWrite:BookingWrapperDO------------>",
					ex);
		}
		LOGGER.debug("BookingCommonServiceImpl :: process2WayWrite :: END");
	}

	@Override
	public void prepareBookingCNIds(BookingWrapperDO bookingWrapperDO) {
		LOGGER.debug("BookingCommonServiceImpl :: prepareBookingCNIds :: START");
		try {
			List<Integer> bookingIds = null;
			List<Integer> cnIds = null;
			List<BookingDO> bookings = bookingWrapperDO.getSucessConsignments();
			List<ConsignmentDO> consignments = bookingWrapperDO
					.getConsingments();
			if (bookings == null || bookings.isEmpty() || consignments == null
					|| consignments.isEmpty())
				return;

			bookingIds = new ArrayList(bookings.size());
			cnIds = new ArrayList(bookings.size());
			for (int i = 0; i < bookings.size(); i++) {
				bookingIds.add(bookings.get(i).getBookingId());
				cnIds.add(consignments.get(i).getConsgId());
			}
			LOGGER.debug("BookingCommonServiceImpl :: prepareBookingCNIds :: success booikng ids: " + bookingIds);
			LOGGER.debug("BookingCommonServiceImpl :: prepareBookingCNIds :: success consignments ids: " + cnIds);
			bookingWrapperDO.setSuccessBookingsIds(bookingIds);
			bookingWrapperDO.setSuccessCNsIds(cnIds);
		} catch (Exception ex) {
			LOGGER.error(
					"Exception Occurred in ::BookingCommonServiceImpl:: prepareBookingCNIds------------>",
					ex);
		}
		LOGGER.debug("BookingCommonServiceImpl :: prepareBookingCNIds :: END");
	}

	@Override
	public boolean isConsgBookedForPickup(String consgNumber)
			throws CGSystemException, CGBusinessException {
		boolean isCNBooked = Boolean.FALSE;
		LOGGER.debug("BookingCommonServiceImpl :: isConsgBookedForPickup :: Start");
		try {
			isCNBooked = bookingCommonDAO.isConsgBookedForPickup(consgNumber);
		} catch (Exception e) {
			LOGGER.debug("Exception in isConsgBookedForPickup() :"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("BookingCommonServiceImpl :: isConsgBookedForPickup :: End");
		return isCNBooked;
	}
	
	@Override
	public boolean isConsgBookedAsChildCn(String consgNumber)
			throws CGSystemException, CGBusinessException {
		boolean isCNBooked = Boolean.FALSE;
		LOGGER.debug("BookingCommonServiceImpl :: isConsgBookedAsChildCn :: Start");
		try {
			isCNBooked = bookingCommonDAO.isConsgBookedAsChildCn(consgNumber);
		} catch (Exception e) {
			LOGGER.debug("Exception in isConsgBookedAsChildCn() :"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("BookingCommonServiceImpl :: isConsgBookedAsChildCn :: End");
		return isCNBooked;
	}


}
