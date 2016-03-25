/** Start of Changes by <31913> on 16/11/2012
 * 
 */
package com.ff.universe.stockmanagement.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.email.EmailSenderUtil;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.CustomerTO;
import com.ff.business.CustomerTypeTO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.stockmanagement.masters.ItemDO;
import com.ff.domain.stockmanagement.masters.ItemTypeDO;
import com.ff.domain.stockmanagement.operations.issue.StockIssueDO;
import com.ff.domain.stockmanagement.operations.transfer.StockTransferDO;
import com.ff.domain.stockmanagement.wrapper.StockCustomerWrapperDO;
import com.ff.geography.CityTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.to.ratemanagement.operations.StockRateTO;
import com.ff.to.stockmanagement.StockIssueValidationTO;
import com.ff.to.stockmanagement.StockSearchInputTO;
import com.ff.to.stockmanagement.StockUpdateInputTO;
import com.ff.to.stockmanagement.StockUserTO;
import com.ff.to.stockmanagement.masters.ItemTO;
import com.ff.to.stockmanagement.masters.ItemTypeTO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;
import com.ff.universe.booking.service.BookingUniversalService;
import com.ff.universe.business.service.BusinessCommonService;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.constant.UniversalErrorConstants;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.global.service.GlobalUniversalService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.ratemanagement.service.RateUniversalService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;
import com.ff.universe.stockmanagement.dao.StockUniversalDAO;
import com.ff.universe.stockmanagement.util.StockSeriesGenerator;
import com.ff.universe.stockmanagement.util.StockUtility;

/**
 * The Class StockUniversalServiceImpl.
 * 
 * @author mohammes
 */
public class StockUniversalServiceImpl implements StockUniversalService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StockUniversalServiceImpl.class);

	/** The stock universal dao. */
	private StockUniversalDAO stockUniversalDAO;

	/** The business common service. */
	private BusinessCommonService businessCommonService;

	/** The booking universal service. */
	private BookingUniversalService bookingUniversalService;

	/** The organization common service. */
	private OrganizationCommonService organizationCommonService;

	/** The geography common service. */
	private GeographyCommonService geographyCommonService;

	/** The email sender service. */
	private EmailSenderUtil emailSenderUtil;

	private RateUniversalService rateUniversalService;

	private ServiceOfferingCommonService serviceOfferingCommonService;

	private GlobalUniversalService globalUniversalService;

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

	/**
	 * Gets the business common service.
	 * 
	 * @return the business common service
	 */
	public BusinessCommonService getBusinessCommonService() {
		return businessCommonService;
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
	 * Gets the stock universal dao.
	 * 
	 * @return the stock universal dao
	 */
	public StockUniversalDAO getStockUniversalDAO() {
		return stockUniversalDAO;
	}

	/**
	 * @return the rateUniversalService
	 */
	public RateUniversalService getRateUniversalService() {
		return rateUniversalService;
	}

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
	 * @return the globalUniversalService
	 */
	public GlobalUniversalService getGlobalUniversalService() {
		return globalUniversalService;
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
	 * @param rateUniversalService
	 *            the rateUniversalService to set
	 */
	public void setRateUniversalService(
			RateUniversalService rateUniversalService) {
		this.rateUniversalService = rateUniversalService;
	}

	/**
	 * Sets the stock universal dao.
	 * 
	 * @param stockUniversalDAO
	 *            the new stock universal dao
	 */
	public void setStockUniversalDAO(StockUniversalDAO stockUniversalDAO) {
		this.stockUniversalDAO = stockUniversalDAO;
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
	 * Sets the organization common service.
	 * 
	 * @param organizationCommonService
	 *            the new organization common service
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.stockmanagement.service.StockUniversalService#isSeriesIssued
	 * (com.ff.to.stockmanagement.StockIssueValidationTO)
	 */
	@Deprecated
	@Override
	public StockIssueValidationTO isSeriesIssued(
			StockIssueValidationTO validationTo) throws CGSystemException,
			CGBusinessException {

		StockSeriesGenerator.calculateLeafInfo(validationTo);
		if (validationTo.getBusinessException() == null) {
			validationTo = getIssuedDetails(validationTo);
		} else {
			return validationTo;
		}
		return validationTo;
	}

	/**
	 * Name : isConsignmentLeafIssued purpose : to validate whether Given Cn
	 * Series is Issued to given party type, if issued then gets party type
	 * details Input :StockIssueValidationTO : plz check StockIssueValidationTO
	 * for More details return : StockIssueValidationTO : if given data is valid
	 * then return required details. Exceptional Flow : (i)throws CGBusiness
	 * Exception for various BR violation (ii) throws CGSystem Exception for
	 * DB/Data/Connection related issues
	 * 
	 * @param validationTo
	 *            the validation to
	 * @return the stock issue validation to
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	@Override
	public StockIssueValidationTO validateStock(
			StockIssueValidationTO validationTo) throws CGSystemException,
			CGBusinessException {
		long startMilliseconds=System.currentTimeMillis();
		LOGGER.debug(
				"StockUniversalServiceImpl::validateStock::start------------>:::::::startMilliseconds"+startMilliseconds+"For Series["+validationTo.getStockItemNumber()+"]");
		if (!StringUtil.isStringEmpty(validationTo.getStockItemNumber())) {
			validationTo.setStockItemNumber(validationTo.getStockItemNumber()
					.toUpperCase());
			String itemType = validationTo.getSeriesType();

			/** Get Expected length of the Series by Material */
			ItemTO itemTo = getItemDetailsByMaterialForStockIntegration(
					validationTo.getIssuedTOPartyType(),
					validationTo.getStockItemNumber());
			if (itemTo != null && itemTo.getItemTypeTO() != null) {
				String resultSeriesType = itemTo.getItemTypeTO()
						.getItemTypeCode();
				if (!StringUtil.isStringEmpty(resultSeriesType)) {// check
																	// whether
																	// itemType
																	// is null
																	// or not
					if (!resultSeriesType.equalsIgnoreCase(itemType)) {
						// set business Exception for Invalid Material
						// information
						LOGGER.error("StockUniversalServiceImpl :: validateStock :: Throwing Business Exception:: Given & resulted material Type is invalid "+validationTo.toString());
						validationTo
								.setBusinessException(new CGBusinessException(
										UniversalErrorConstants.INVALID_MATERIAL_INFO));
						return validationTo;
					}
				} else {
					// set business Exception for Invalid Material information
					LOGGER.error("StockUniversalServiceImpl :: validateStock :: Throwing Business Exception::  resulted material Type is empty/null "+validationTo.toString());
					validationTo.setBusinessException(new CGBusinessException(
							UniversalErrorConstants.INVALID_MATERIAL_INFO));
					return validationTo;
				}
				Integer length = itemTo.getSeriesLength();
				validationTo.setExpectedSeriesLength(length);
				validationTo.setItemId(itemTo.getItemId());
			} else {
				// set business Exception for Invalid Material information
				LOGGER.error("StockUniversalServiceImpl :: validateStock :: Throwing Business Exception::  Material type doesnot exist "+validationTo.toString());
				validationTo.setBusinessException(new CGBusinessException(
						UniversalErrorConstants.INVALID_MATERIAL_INFO));
				return validationTo;
			}
			// FIXME need to modify
			StockSeriesGenerator.prepareLeafDetailsForSeries(validationTo);

			if (StringUtil.isNull(validationTo.getBusinessException())) {

				Boolean isCancelled = isSeriesCancelledForIntegration(validationTo);
				if (isCancelled) {
					/**
					 * Since Series is cancelled, appliaction can not use the
					 * Serial number
					 */
					validationTo.setBusinessException(new CGBusinessException(
							UniversalErrorConstants.CANCELLED_SERIES));
					return validationTo;
				}
				validationTo = getIssuedDetails(validationTo);
			} else {
				return validationTo;
			}
			// FIXME need to check series cancellation
		} else {
			// Throws CG Business Exception : Given series is Empty/null
			LOGGER.warn("StockUniversalServiceImpl :: validateStock :: Throwing Business Exception::  Empty Series"+validationTo.toString());
			throw new CGBusinessException(UniversalErrorConstants.EMPTY_SERIES);
		}
		long endMilliSeconds=System.currentTimeMillis();
		long diff=endMilliSeconds-startMilliseconds;
		LOGGER.debug(
				"StockUniversalServiceImpl::validateStock::END------------>::::::: endMilliSeconds:["+endMilliSeconds+"] Difference"+(diff) +" Difference IN HH:MM:SS format ::"+DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff)+"For Series["+validationTo.getStockItemNumber()+"]");
		return validationTo;
	}

	/**
	 * Gets the issued details.
	 * 
	 * @param to
	 *            the to
	 * @return the issued details
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private StockIssueValidationTO getIssuedDetails(StockIssueValidationTO to)
			throws CGSystemException, CGBusinessException {
		switch (to.getIssuedTOPartyType()) {
		case UdaanCommonConstants.ISSUED_TO_BRANCH:
			getStockReceiptDetailsForBranch(to);
			break;
		default:
			to = getIssueDetailsByType(to);
		}
		return to;
	}

	/**
	 * @param to
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private StockIssueValidationTO getIssueDetailsByType(
			StockIssueValidationTO to) throws CGBusinessException,
			CGSystemException {
		StockUserTO issuedTO = null;
		StockIssueDO issuedDo = null;
		try {
			issuedDo = stockUniversalDAO.getStockIssuedDtls(to);
		} catch (CGSystemException e) {
			LOGGER.error("StockUniversalServiceImpl :: getIssuedDetails :: Exception "
					,e);
			throw e;
		}
		if (issuedDo != null) {
			Date StockIssuedDate = issuedDo.getStockIssueDate();
			String issuedToType = issuedDo.getIssuedToType();
			validateForStockSeriesExpiry(StockIssuedDate, issuedToType);
			LOGGER.debug("StockUniversalServiceImpl :: validateStock::getIssueDetailsByType::Details issued:: with issue number :: "
					+ issuedDo.getStockIssueNumber());
			Date transferDate = getLatestTransferDateBySerialNumber(to
					.getStockItemNumber());
			LOGGER.debug("StockUniversalServiceImpl :: getLatestTransferDateBySerialNumber ::transferDate :: transferDate ["
					+ transferDate
					+ "] Issued Date:["
					+ issuedDo.getStockIssueDate() + "]");
			if (!StringUtil.isNull(transferDate)) {
				/**
				 * BR: check transfer Date with Issue Date if issueDate <
				 * transferDate date
				 */
				if (transferDate.after(issuedDo.getTransactionModifiedDate())) {
					/** BR: check latest series is with whom */
					if (StringUtil.isStringEmpty(to.getIssuedTOPartyType())
							|| (!StringUtil.isStringEmpty(to
									.getIssuedTOPartyType())
									&& !to.getIssuedTOPartyType()
											.equalsIgnoreCase(
													UdaanCommonConstants.ISSUED_TO_BRANCH) && !to
									.getIssuedTOPartyType().equalsIgnoreCase(
											UdaanCommonConstants.ISSUED_TO_FR))) {
						StockTransferDO stockTransferDO = stockUniversalDAO
								.getStockTransferDtls(to);
						if (stockTransferDO != null) {
							issuedTO = populateStockTransferDetails(to,
									stockTransferDO);
							if (!StringUtil.isStringEmpty(stockTransferDO
									.getTransferTOType())
									&& !StringUtil.isStringEmpty(to
											.getIssuedTOPartyType())) {
								if (StringUtil.isNull(to.getBusinessException())&& stockTransferDO.getTransferTOType()
										.equalsIgnoreCase(
												to.getIssuedTOPartyType())) {
									to.setIsIssuedTOParty(true);
								}
							}

						} else {
							LOGGER.warn("#####StockUniversalServiceImpl :: getIssuedDetails :: and then Stock  transferred  to others##########"+"["+to.getStockItemNumber()+"]");
						}
					} else {
						LOGGER.warn("#########StockUniversalServiceImpl :: getIssuedDetails :: can not invoke Stock  transferred details since IssuedTOType ["
								+ to.getIssuedTOPartyType() + "] ########"+to.toString());
					}
				} else {
					/** continue with issue Details */
					LOGGER.debug("StockUniversalServiceImpl :: getIssuedDetails :: Stock Not transferred after Issue then continue with issue Details");
					issuedTO = populateStockIssueDetailsForValidations(to,
							issuedDo);
				}
			} else {
				/** Extract issue Details */
				LOGGER.warn("#####StockUniversalServiceImpl :: getIssuedDetails :: Not Yet transferred to others ##########");
				issuedTO = populateStockIssueDetailsForValidations(to, issuedDo);
			}

		} else {
			/*** check in the stock Transfer Table */
			if (StringUtil.isStringEmpty(to.getIssuedTOPartyType())
					|| (!StringUtil.isStringEmpty(to.getIssuedTOPartyType())
							&& !to.getIssuedTOPartyType().equalsIgnoreCase(
									UdaanCommonConstants.ISSUED_TO_BRANCH) && !to
							.getIssuedTOPartyType().equalsIgnoreCase(
									UdaanCommonConstants.ISSUED_TO_FR))) {
				StockTransferDO stockTransferDO = stockUniversalDAO
						.getStockTransferDtls(to);
				if (stockTransferDO != null) {
					issuedTO = populateStockTransferDetails(to, stockTransferDO);
					if (!StringUtil.isStringEmpty(stockTransferDO
							.getTransferTOType())
							&& !StringUtil.isStringEmpty(to
									.getIssuedTOPartyType())) {
						if (StringUtil.isNull(to.getBusinessException()) && stockTransferDO.getTransferTOType()
								.equalsIgnoreCase(to.getIssuedTOPartyType())) {
							to.setIsIssuedTOParty(true);
						}
					}
				}

			}

			if (issuedTO == null) {// Details Does not Exist
				to.setBusinessException(new CGBusinessException(
						UniversalErrorConstants.ISSUE_DETAILS_DOES_NOT_EXIST));
				to.setIsIssuedTOParty(Boolean.FALSE);
				LOGGER.error("#########StockUniversalServiceImpl :: getIssuedDetails :: Throwing Business Exception ########"+to.toString());
				// throw new
				// CGBusinessException(UniversalErrorConstants.ISSUE_DETAILS_DOES_NOT_EXIST);
			}
		}
		to.setIssuedTO(issuedTO);
		return to;
	}

	private void validateForStockSeriesExpiry(Date StockIssuedDate,
			String issuedToType) throws CGBusinessException, CGSystemException {
		/** check for CN Expiry if it issued to FR */
		if (!StringUtil.isStringEmpty(issuedToType)
				&& (issuedToType
						.equalsIgnoreCase(UdaanCommonConstants.ISSUED_TO_FR) || issuedToType
						.equalsIgnoreCase(UdaanCommonConstants.ISSUED_TO_BA))) {
			String days = null;
			Integer expiryDays = null;
			if (issuedToType
					.equalsIgnoreCase(UdaanCommonConstants.ISSUED_TO_FR)) {
				days = getConfigParamValueByName(CommonConstants.CONFIG_PARAM_STOCK_ISSUE_FR_EXPIRY_DAYS);
				expiryDays = StockUniveralConstants.SERIES_FR_EXPIRED;
			} else if (issuedToType
					.equalsIgnoreCase(UdaanCommonConstants.ISSUED_TO_BA)) {
				days = getConfigParamValueByName(CommonConstants.CONFIG_PARAM_STOCK_ISSUE_BA_EXPIRY_DAYS);
				expiryDays = StockUniveralConstants.SERIES_BA_EXPIRED;
			}

			if (StringUtil.isStringContainsInteger(days)) {
				expiryDays = StringUtil.parseInteger(days);
			}
			if (DateUtil.DayDifferenceBetweenTwoDates(StockIssuedDate,
					DateUtil.getCurrentDate()) > expiryDays) {
				/** then throw Exception for Expiry */
				ExceptionUtil
						.prepareBusinessException(UniversalErrorConstants.STOCK_ALREADY_EXPIRED);
			}
		}
	}

	/**
	 * @param to
	 * @param issuedDo
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	private StockUserTO populateStockTransferDetails(StockIssueValidationTO to,
			StockTransferDO stockTransferDO) throws CGSystemException,
			CGBusinessException {
		StockUserTO stockUserTO = null;
		if (stockTransferDO != null) {
			stockUserTO = new StockUserTO();
			String shippedToCode = stockTransferDO.getShippedToCode();
			stockUserTO.setStockUserType(stockTransferDO.getTransferTOType());
			switch (stockTransferDO.getTransferTOType()) {
			case UdaanCommonConstants.ISSUED_TO_BA:
				CustomerDO baDO = stockTransferDO.getTransferTOBaDO();
				if (baDO != null) {
					stockUserTO.setStockUserId(baDO.getCustomerId());
					stockUserTO.setStockUserCode(!StringUtil
							.isStringEmpty(shippedToCode) ? shippedToCode
							: baDO.getCustomerCode());
					stockUserTO.setStockUserName(baDO.getBusinessName());
					validateBACityWithLoggedInCity(to, baDO);
				}
				break;
			case UdaanCommonConstants.ISSUED_TO_BRANCH:
				OfficeDO officeDO = stockTransferDO.getTransferTOOfficeDO();
				if (officeDO != null) {
					stockUserTO.setStockUserId(officeDO.getOfficeId());
					stockUserTO.setStockUserCode(officeDO.getOfficeCode());
					stockUserTO.setStockUserName(officeDO.getOfficeName());
				}
				break;
			case UdaanCommonConstants.ISSUED_TO_EMPLOYEE:
				EmployeeDO empDO = stockTransferDO.getTransferTOEmpDO();
				if (empDO != null) {
					stockUserTO.setStockUserId(empDO.getEmployeeId());
					stockUserTO.setStockUserCode(empDO.getEmpCode());
					stockUserTO.setStockUserName(StockUtility
							.getEmployeeName(empDO));
				}
				break;
			case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
				CustomerDO customerDO = stockTransferDO
						.getTransferTOCustomerDO();
				if (customerDO != null) {
					stockUserTO.setStockUserId(customerDO.getCustomerId());
					stockUserTO.setStockUserCode(!StringUtil
							.isStringEmpty(shippedToCode) ? shippedToCode
							: customerDO.getCustomerCode());
					stockUserTO.setStockUserName(customerDO.getBusinessName());
				}
				break;

			}
		}
		return stockUserTO;
	}

	/**
	 * @param to
	 * @param issuedDo
	 * @return
	 * @throws CGBusinessException 
	 */
	private StockUserTO populateStockIssueDetailsForValidations(
			StockIssueValidationTO to, StockIssueDO issuedDo) throws CGBusinessException {
		StockUserTO issuedTO;
		issuedTO = new StockUserTO();
		String shippedToCode = issuedDo.getShippedToCode();
		switch (issuedDo.getIssuedToType()) {
		case UdaanCommonConstants.ISSUED_TO_BA:
			CustomerDO ba = issuedDo.getIssuedToBA();
			if (ba != null) {
				issuedTO.setStockUserId(ba.getCustomerId());
				issuedTO.setStockUserCode(!StringUtil
						.isStringEmpty(shippedToCode) ? shippedToCode : ba
						.getCustomerCode());
				issuedTO.setStockUserName(ba.getBusinessName());
				issuedTO.setStockUserType(issuedDo.getIssuedToType());
				validateBACityWithLoggedInCity(to, ba);
			}
			break;
		case UdaanCommonConstants.ISSUED_TO_FR:
			CustomerDO frDo = issuedDo.getIssuedToFranchisee();
			if (frDo != null) {
				issuedTO.setStockUserId(frDo.getCustomerId());
				issuedTO.setStockUserCode(!StringUtil
						.isStringEmpty(shippedToCode) ? shippedToCode : frDo
						.getCustomerCode());
				issuedTO.setStockUserName(frDo.getBusinessName());
				issuedTO.setStockUserType(issuedDo.getIssuedToType());
			}
			break;
		case UdaanCommonConstants.ISSUED_TO_BRANCH:
			OfficeDO officeDO = issuedDo.getIssuedToOffice();
			if (officeDO != null) {
				issuedTO.setStockUserId(officeDO.getOfficeId());
				issuedTO.setStockUserCode(officeDO.getOfficeCode());
				issuedTO.setStockUserName(officeDO.getOfficeName());
				issuedTO.setStockUserType(issuedDo.getIssuedToType());
			}
			break;
		case UdaanCommonConstants.ISSUED_TO_EMPLOYEE:
			EmployeeDO empTo = issuedDo.getIssuedToPickupBoy();
			if (empTo != null) {
				String name = null;
				issuedTO.setStockUserId(empTo.getEmployeeId());
				name = StockUtility.getEmployeeName(empTo);
				issuedTO.setStockUserCode(empTo.getEmpCode());
				issuedTO.setStockUserName(name);
				issuedTO.setStockUserType(issuedDo.getIssuedToType());
			}
			break;
		case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
			CustomerDO customerDO = issuedDo.getIssuedToCustomer();
			if (customerDO != null) {
				issuedTO.setStockUserId(customerDO.getCustomerId());
				issuedTO.setStockUserCode(!StringUtil
						.isStringEmpty(shippedToCode) ? shippedToCode
						: customerDO.getCustomerCode());
				issuedTO.setStockUserName(customerDO.getBusinessName());
				issuedTO.setStockUserType(issuedDo.getIssuedToType());
			}
			break;

		}
		if (!StringUtil.isStringEmpty(issuedDo.getIssuedToType())
				&& !StringUtil.isStringEmpty(to.getIssuedTOPartyType())) {
			if (StringUtil.isNull(to.getBusinessException()) && issuedDo.getIssuedToType().equalsIgnoreCase(
					to.getIssuedTOPartyType())) {
				to.setIsIssuedTOParty(true);
			}
		}
		return issuedTO;
	}

	/**
	 * @param to
	 * @param ba
	 * @throws CGBusinessException
	 */
	public void validateBACityWithLoggedInCity(StockIssueValidationTO to,
			CustomerDO ba) throws CGBusinessException {
		if(ba!=null && ba.getSalesOfficeDO()!=null){
			Integer loggedInCityId=ba.getSalesOfficeDO().getCityId();
			if( StringUtil.isEmptyInteger(loggedInCityId) || StringUtil.isEmptyInteger(to.getLoggedInCityId())  || loggedInCityId.intValue() != to.getLoggedInCityId().intValue()){
				
				to.setBusinessException(new CGBusinessException(
						UniversalErrorConstants.ISSUE_DETAILS_DOES_NOT_EXIST));
				to.setIsIssued(false);
			}
		}
	}

	/**
	 * @param empDo
	 * @return
	 * @throws CGSystemException
	 */

	public void getStockReceiptDetailsForBranch(
			StockIssueValidationTO validationTo) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("StockUniversalServiceImpl :: getStockReceiptDetailsForBranch :: START"+validationTo.toString());
		Long receiptId = null;
		try {
			switch (validationTo.getSeriesType()) {
			case UdaanCommonConstants.SERIES_TYPE_CNOTES:
				receiptId = stockUniversalDAO
						.getStockReceiptDetailsForBranch(validationTo);
				if (!StringUtil.isEmptyLong(receiptId)) {
					isAlreadyIssuedForCnote(validationTo);
				}
				break;
			default:
				receiptId = stockUniversalDAO
						.getStockReceiptDetailsForBranch(validationTo);
				if (StringUtil.isNull(receiptId)) {
					receiptId = stockUniversalDAO
							.getStockReceiptDetailsUnderRegion(validationTo);
					isAlreadyIssuedForOtherThanCnote(validationTo);
				}
			}

		} catch (CGBusinessException e) {
			LOGGER.error("StockUniversalServiceImpl :: getStockReceiptDetailsForBranch :: CGBusinessException "
					+ e.getMessage()+validationTo.toString());
			throw e;
		} catch (Exception e) {
			LOGGER.error("StockUniversalServiceImpl :: getStockReceiptDetailsForBranch :: Exception "
					,e);
			throw new CGSystemException(e.getMessage(), e);
		}

		if (!StringUtil.isNull(receiptId)
				&& StringUtil.isNull(validationTo.getBusinessException())) {
			validationTo.setIsIssuedTOParty(true);
		} else {
			LOGGER.warn("StockUniversalServiceImpl :: getStockReceiptDetailsForBranch :: details Does not Exist"+validationTo.toString());
			if (StringUtil.isNull(validationTo.getBusinessException())) {
				validationTo.setBusinessException(new CGBusinessException(
						UniversalErrorConstants.ISSUE_DETAILS_DOES_NOT_EXIST));
			}
			validationTo.setIsIssuedTOParty(Boolean.FALSE);
		}
		LOGGER.debug("StockUniversalServiceImpl :: getStockReceiptDetailsForBranch :: END"+validationTo.toString());
	}

	/**
	 * @param validationTo
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void isAlreadyIssuedForCnote(StockIssueValidationTO validationTo)
			throws CGBusinessException, CGSystemException {
		Date issuedDate = getLatestIssuedDateBySerialNumber(validationTo
				.getStockItemNumber());
		LOGGER.debug("StockUniversalServiceImpl::isAlreadyIssued ::issuedDate : ["
				+ issuedDate + "]");
		Date receiptDate = getLatestReceiptDateBySerialNumber(validationTo
				.getStockItemNumber());
		LOGGER.debug("StockUniversalServiceImpl::isAlreadyIssued ::receiptDate : ["
				+ receiptDate + "]");
		if (receiptDate != null && issuedDate != null) {
			if (issuedDate.after(receiptDate)) {
				Date transferDate = getLatestTransferDateBySerialNumber(validationTo
						.getStockItemNumber());
				if (transferDate != null && transferDate.after(issuedDate)) {

					/*** check in the stock Transfer Table */
					if (!StringUtil.isStringEmpty(validationTo
							.getIssuedTOPartyType())
							&& validationTo
									.getIssuedTOPartyType()
									.equalsIgnoreCase(
											UdaanCommonConstants.ISSUED_TO_BRANCH)) {
						StockTransferDO stockTransferDO = stockUniversalDAO
								.getStockTransferDtls(validationTo);
						if (stockTransferDO != null) {
							StockUserTO issuedTO = populateStockTransferDetails(
									validationTo, stockTransferDO);
							if (!StringUtil.isStringEmpty(stockTransferDO
									.getTransferTOType())
									&& !StringUtil.isStringEmpty(validationTo
											.getIssuedTOPartyType())) {
								if (StringUtil.isNull(validationTo.getBusinessException()) && stockTransferDO
										.getTransferTOType()
										.equalsIgnoreCase(
												validationTo
														.getIssuedTOPartyType())) {
									validationTo.setIssuedTO(issuedTO);
									validationTo.setIsIssuedTOParty(true);
								}
							}
						} else {
							validationTo
									.setBusinessException(new CGBusinessException(
											UniversalErrorConstants.STOCK_ALREADY_ISSUED));
						}

					} else {
						validationTo
								.setBusinessException(new CGBusinessException(
										UniversalErrorConstants.STOCK_ALREADY_ISSUED));
					}

				} else {
					// throw new
					// CGBusinessException(UniversalErrorConstants.STOCK_ALREADY_ISSUED);
					validationTo.setBusinessException(new CGBusinessException(
							UniversalErrorConstants.STOCK_ALREADY_ISSUED));
				}

			}
		}
	}

	private void isAlreadyIssuedForOtherThanCnote(
			StockIssueValidationTO validationTo) throws CGBusinessException,
			CGSystemException {
		Date issuedDate = getLatestIssuedDateBySerialNumber(validationTo
				.getStockItemNumber());
		LOGGER.debug("StockUniversalServiceImpl::isAlreadyIssued ::issuedDate : ["
				+ issuedDate + "]");
		Date receiptDate = getLatestReceiptDateBySerialNumber(validationTo
				.getStockItemNumber());
		LOGGER.debug("StockUniversalServiceImpl::isAlreadyIssued ::receiptDate : ["
				+ receiptDate + "]");
		if (receiptDate != null && issuedDate != null) {
			if (issuedDate.after(receiptDate)) {
				Date transferDate = getLatestTransferDateBySerialNumber(validationTo
						.getStockItemNumber());
				if (transferDate != null && transferDate.after(issuedDate)) {

					/*** check in the stock Transfer Table */
					if (!StringUtil.isStringEmpty(validationTo
							.getIssuedTOPartyType())
							&& validationTo
									.getIssuedTOPartyType()
									.equalsIgnoreCase(
											UdaanCommonConstants.ISSUED_TO_BRANCH)) {
						StockTransferDO stockTransferDO = stockUniversalDAO
								.getStockTransferDtls(validationTo);
						if (stockTransferDO != null) {
							StockUserTO issuedTO = populateStockTransferDetails(
									validationTo, stockTransferDO);
							if (!StringUtil.isStringEmpty(stockTransferDO
									.getTransferTOType())
									&& !StringUtil.isStringEmpty(validationTo
											.getIssuedTOPartyType())) {
								if (StringUtil.isNull(validationTo.getBusinessException()) && stockTransferDO
										.getTransferTOType()
										.equalsIgnoreCase(
												validationTo
														.getIssuedTOPartyType())) {
									validationTo.setIssuedTO(issuedTO);
									validationTo.setIsIssuedTOParty(true);
								}
							}
						} else {
							validationTo
									.setBusinessException(new CGBusinessException(
											UniversalErrorConstants.STOCK_ALREADY_ISSUED));
						}

					} else {
						validationTo
								.setBusinessException(new CGBusinessException(
										UniversalErrorConstants.STOCK_ALREADY_ISSUED));
					}

				} else {
					Date returnedDate = getLatestReturnedDateBySerialNumber(validationTo
							.getStockItemNumber());
					if (returnedDate != null) {

					}

					// throw new
					// CGBusinessException(UniversalErrorConstants.STOCK_ALREADY_ISSUED);
					validationTo.setBusinessException(new CGBusinessException(
							UniversalErrorConstants.STOCK_ALREADY_ISSUED));
				}

			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * getAllFranchissees(com.ff.business.FranchiseeTO)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * isSeriesCancelled
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Boolean isSeriesCancelled(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException {
		Long result = null;

		/*long starttime = System.currentTimeMillis();
		LOGGER.debug("StockUniversalService::isSeriesCancelled ..Start Time:[ "
				+ starttime + "]");*/
		result = stockUniversalDAO.isSeriesCancelled(validationTO);
		/*long endtime = System.currentTimeMillis();
		LOGGER.debug("StockUniversalService::isSeriesCancelled ..After calling Method: isSeriesCancelled Time [ "
				+ endtime + "]Total diff :[" + (endtime - starttime) + "]");*/

		return StringUtil.isNull(result) ? Boolean.FALSE : Boolean.TRUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * isSeriesIssuedToPartyType
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public List<Long> isSeriesIssuedToPartyType(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException {
		String qryName = null;
		List<Long> result = null;
		if (!StringUtil.isStringEmpty(validationTO.getIssuedTOType())) {
			switch (validationTO.getIssuedTOType()) {
			case UdaanCommonConstants.ISSUED_TO_BA:
				qryName = StockUniveralConstants.QRY_SERIES_ISSUED_TO_BA;
				break;
			case UdaanCommonConstants.ISSUED_TO_BRANCH:
				qryName = StockUniveralConstants.QRY_SERIES_ISSUED_TO_OFFICE;
				break;
			case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
				qryName = StockUniveralConstants.QRY_SERIES_ISSUED_TO_CUSTOMER;
				break;
			case UdaanCommonConstants.ISSUED_TO_EMPLOYEE:
				qryName = StockUniveralConstants.QRY_SERIES_ISSUED_TO_EMP;
				break;
			case UdaanCommonConstants.ISSUED_TO_FR:
				qryName = StockUniveralConstants.QRY_SERIES_ISSUED_TO_FR;
				break;
			}
		}
		if (StringUtil.isStringEmpty(qryName)) {
			LOGGER.error("StockUniversalServiceImpl::isSeriesIssuedToPartyType :: insuffient inputs");
			throw new CGBusinessException("Invalid Issued To Type");
		}
		result = stockUniversalDAO.isSeriesIssuedToPartyType(qryName,
				validationTO);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * isSeriesExistAtTransferredTOPartyType
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public List<Long> isSeriesExistAtTransferredTOPartyType(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException {
		List<Long> result = null;
		String qryName = null;
		if (!StringUtil.isStringEmpty(validationTO.getIssuedTOType())) {
			switch (validationTO.getIssuedTOType()) {
			case UdaanCommonConstants.ISSUED_TO_BA:
				qryName = StockUniveralConstants.QRY_SERIES_TRANSFERRED_TO_BA;
				break;
			case UdaanCommonConstants.ISSUED_TO_BRANCH:
				qryName = StockUniveralConstants.QRY_SERIES_TRANSFERRED_TO_OFFICE;
				break;
			case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
				qryName = StockUniveralConstants.QRY_SERIES_TRANSFERRED_TO_CUSTOMER;
				break;
			case UdaanCommonConstants.ISSUED_TO_EMPLOYEE:
				qryName = StockUniveralConstants.QRY_SERIES_TRANSFERRED_TO_EMP;
				break;

			}
		}
		if (StringUtil.isStringEmpty(qryName)) {
			throw new CGBusinessException("Invalid Issued To Type");
		}
		result = stockUniversalDAO.isSeriesExistAtTransferredTOPartyType(
				qryName, validationTO);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * isSeriesTransferredFromPartyType
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public List<Long> isSeriesTransferredFromPartyType(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException {
		List<Long> result = null;
		String qryName = null;
		if (!StringUtil.isStringEmpty(validationTO.getIssuedTOType())) {
			switch (validationTO.getIssuedTOType()) {
			case UdaanCommonConstants.ISSUED_TO_BA:
				qryName = StockUniveralConstants.QRY_SERIES_TRANSFERRED_FROM_BA;
				break;
			case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
				qryName = StockUniveralConstants.QRY_SERIES_TRANSFERRED_FROM_CUSTOMER;
				break;
			case UdaanCommonConstants.ISSUED_TO_EMPLOYEE:
				qryName = StockUniveralConstants.QRY_SERIES_TRANSFERRED_FROM_EMP;
				break;

			}
		}
		if (StringUtil.isStringEmpty(qryName)) {
			throw new CGBusinessException("Invalid Issued From Type");
		}
		result = stockUniversalDAO.isSeriesTransferredFromPartyType(qryName,
				validationTO);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * isSeriesAvailableWithTransferFromPartyType
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public List<Long> isSeriesAvailableWithTransferFromPartyType(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException {
		List<Long> result = null;
		String qryName = null;
		if (!StringUtil.isStringEmpty(validationTO.getIssuedTOType())) {
			switch (validationTO.getIssuedTOType()) {
			case UdaanCommonConstants.ISSUED_TO_BA:
				qryName = StockUniveralConstants.QRY_ISSERIES_EXIST_AT_TRANSFERRED_FROM_BA;
				break;
			case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
				qryName = StockUniveralConstants.QRY_ISSERIES_EXIST_AT_TRANSFERRED_FROM_CUSTOMER;
				break;
			case UdaanCommonConstants.ISSUED_TO_EMPLOYEE:
				qryName = StockUniveralConstants.QRY_ISSERIES_EXIST_AT_TRANSFERRED_FROM_EMP;
				break;

			}
		}
		if (StringUtil.isStringEmpty(qryName)) {
			throw new CGBusinessException("Invalid Issued From Type");
		}
		result = stockUniversalDAO.isSeriesAvailableWithTransferFromPartyType(
				qryName, validationTO);
		// if it's not empty means series doesnot exist
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * isSeriesAcknowledged
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Boolean isSeriesAcknowledged(StockValidationTO validationTO)
			throws CGBusinessException, CGSystemException {
		Long result = null;
		result = stockUniversalDAO.isSeriesAcknowledged(validationTO);
		return StringUtil.isNull(result) ? Boolean.FALSE : Boolean.TRUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * isSeriesExistInBranch
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Boolean isSeriesExistInBranch(StockValidationTO validationTO)
			throws CGBusinessException, CGSystemException {
		List<Long> notExistSeries = null;
		notExistSeries = stockUniversalDAO.isSeriesExistInBranch(validationTO);
		return !StringUtil.isEmptyList(notExistSeries) ? Boolean.FALSE
				: Boolean.TRUE;
	}

	@Override
	public Boolean isSeriesExistInBranchByLeaf(StockValidationTO validationTO)
			throws CGBusinessException, CGSystemException {
		Long isExistInBranch = null;
		isExistInBranch = stockUniversalDAO
				.isSeriesExistInBranchByLeaf(validationTO);
		return StringUtil.isEmptyLong(isExistInBranch) ? Boolean.FALSE
				: Boolean.TRUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * isSeriesReturnedToPlant
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Boolean isSeriesReturnedToPlant(StockValidationTO validationTO)
			throws CGBusinessException, CGSystemException {
		Long result = null;
		result = stockUniversalDAO.isSeriesReturned(validationTO,
				StockUniveralConstants.RETURN_TYPE_RECEIPT);
		return StringUtil.isNull(result) ? Boolean.FALSE : Boolean.TRUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * isSeriesReturnedFromPlant
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Boolean isSeriesReturnedFromPlant(StockValidationTO validationTO)
			throws CGBusinessException, CGSystemException {
		Long result = null;
		result = stockUniversalDAO.isSeriesReturned(validationTO,
				StockUniveralConstants.RETURN_TYPE_RETURN);
		return StringUtil.isNull(result) ? Boolean.FALSE : Boolean.TRUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * isSeriesAlreadyIssuedFromBranch
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public List<Long> isSeriesAlreadyIssuedFromBranch(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException {

		return stockUniversalDAO.isSeriesAlreadyIssuedFromBranch(validationTO);

	}

	@Override
	public Boolean isSeriesAlreadyIssuedFromBranchByLeaf(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException {
		return stockUniversalDAO
				.isSeriesAlreadyIssuedFromBranchByLeaf(validationTO);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * getLatestSeriesIssueDateForBranch
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Date getLatestSeriesIssueDateForBranch(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException {
		Date date = null;
		String issueQry = StockUniveralConstants.QRY_STOCK_ISSUE_DATE;
		Map<String, Object> paramValuePair = prepareParamValuePairMap(validationTO);
		date = stockUniversalDAO.getLatestSeriesDate(issueQry, paramValuePair);
		return date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * getLatestDateWithSeries
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Map<Long, Date> getLatestDateWithSeries(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException {
		Map<Long, Date> seriesDate = null;
		String issueQry = StockUniveralConstants.QRY_STOCK_ISSUE_DATE;
		seriesDate = stockUniversalDAO.getLatestDateWithSeries(issueQry,
				validationTO);
		return seriesDate;
	}

	@Override
	public Date getLatestStockIssueDateWithLeaf(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException {

		String issueQry = StockUniveralConstants.QRY_STOCK_ISSUE_DATE;
		return stockUniversalDAO.getLatestDateByLeaf(issueQry, validationTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * getLatestDateWithSeriesInReceipt
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Map<Long, Date> getLatestDateWithSeriesInReceipt(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException {
		Map<Long, Date> seriesDate = null;
		String issueQry = StockUniveralConstants.QRY_SERIES_RECEIPT_DATE;
		seriesDate = stockUniversalDAO.getLatestDateWithSeries(issueQry,
				validationTO);
		return seriesDate;
	}

	@Override
	public Date getLatestReceiptDateByLeaf(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException {
		String issueQry = StockUniveralConstants.QRY_SERIES_RECEIPT_DATE;
		return stockUniversalDAO.getLatestDateByLeaf(issueQry, validationTO);
	}

	@Override
	public Map<Long, Date> getLatestDateWithSeriesInReceiptWithoutOffice(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException {
		Map<Long, Date> seriesDate = null;
		String issueQry = StockUniveralConstants.QRY_SERIES_RECEIPT_DATE_WITHOUT_OFFICE;
		seriesDate = stockUniversalDAO.getLatestDateWithSeriesWithoutOffice(
				issueQry, validationTO);
		return seriesDate;
	}

	@Override
	public Date getLatestReceiptDateByLeafWithoutOffice(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException {
		String issueQry = StockUniveralConstants.QRY_SERIES_RECEIPT_DATE_WITHOUT_OFFICE;
		return stockUniversalDAO.getLatestDateByleafWithoutOffice(issueQry,
				validationTO);
	}

	/**
	 * Gets the latest transfer date with series.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the latest transfer date with series
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	@Override
	public Map<Long, Date> getLatestTransferDateWithSeries(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException {
		Map<Long, Date> seriesDate = null;
		String issueQry = StockUniveralConstants.QRY_LATEST_TRANSFER_DATE_BY_SERIES;
		seriesDate = stockUniversalDAO.getLatestDateWithSeries(issueQry,
				validationTO);
		return seriesDate;
	}

	@Override
	public Date getLatestTransferDateByLeaf(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException {
		String issueQry = StockUniveralConstants.QRY_LATEST_TRANSFER_DATE_BY_SERIES;
		return stockUniversalDAO.getLatestDateByLeaf(issueQry, validationTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * getLatestDateWithSeriesInReturn
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Map<Long, Date> getLatestDateWithSeriesInReturn(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException {
		Map<Long, Date> seriesDate = null;
		String issueQry = StockUniveralConstants.QRY_STOCK_RETURN_FROM_DATE;
		seriesDate = stockUniversalDAO.getLatestDateWithSeries(issueQry,
				validationTO);
		return seriesDate;
	}

	/**
	 * getLatestDateWithSeriesInReturnToRHO
	 * 
	 * @param validationTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	@Override
	public Map<Long, Date> getLatestDateWithSeriesInReturnToRHO(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException {
		Map<Long, Date> seriesDate = null;
		String issueQry = StockUniveralConstants.QRY_STOCK_RETURN_TO_DATE;
		seriesDate = stockUniversalDAO.getLatestDateWithSeries(issueQry,
				validationTO);
		return seriesDate;
	}

	@Override
	public Map<Long, Date> getLatestDateWithSeriesInReturnWithoutOffice(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException {
		Map<Long, Date> seriesDate = null;
		String issueQry = StockUniveralConstants.QRY_LATEST_STOCK_RETURN_DATE;
		seriesDate = stockUniversalDAO.getLatestDateWithSeriesWithoutOffice(
				issueQry, validationTO);
		return seriesDate;
	}

	@Override
	public Date getLatestStockReturnDateByLeaf(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException {
		String issueQry = StockUniveralConstants.QRY_LATEST_STOCK_RETURN_DATE;
		return stockUniversalDAO.getLatestDateByleafWithoutOffice(issueQry,
				validationTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * getLatestSeriesReturnFromDateForBranch
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Date getLatestSeriesReturnFromDateForBranch(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException {
		Date date = null;
		String issueQry = StockUniveralConstants.QRY_STOCK_RETURN_FROM_DATE;
		Map<String, Object> paramValuePair = prepareParamValuePairMap(validationTO);
		date = stockUniversalDAO.getLatestSeriesDate(issueQry, paramValuePair);
		return date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * getLatestSeriesTransferTODateForBranch
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Date getLatestSeriesTransferTODateForBranch(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException {
		Date date = null;
		String transferQry = StockUniveralConstants.QRY_SERIES_TRANSFER_TO_OFFICE_DATE;
		Map<String, Object> paramValuePair = prepareParamValuePairMap(validationTO);
		date = stockUniversalDAO.getLatestSeriesDate(transferQry,
				paramValuePair);
		return date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * getLatestSeriesReturnTODateForBranch
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Date getLatestSeriesReturnTODateForBranch(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException {
		Date date = null;
		String issueQry = StockUniveralConstants.QRY_STOCK_RETURN_TO_DATE;
		Map<String, Object> paramValuePair = prepareParamValuePairMap(validationTO);
		date = stockUniversalDAO.getLatestSeriesDate(issueQry, paramValuePair);
		return date;
	}

	/**
	 * Prepare param value pair map.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the map
	 */
	private Map<String, Object> prepareParamValuePairMap(
			StockValidationTO validationTO) {
		Map<String, Object> paramValuePair = new HashMap<>(7);
		// paramValuePair.put(StockUniveralConstants.QRY_PARAM_TYPE_ID,
		// validationTO.getPartyTypeId());
		paramValuePair.put(StockUniveralConstants.QRY_PARAM_ITEM_ID,
				validationTO.getItemId());
		paramValuePair.put(StockUniveralConstants.QRY_PARAM_OFFICE_CODE,
				validationTO.getOfficeProduct());
		paramValuePair.put(StockUniveralConstants.QRY_PARAM_LEAF,
				validationTO.getLeaf());
		paramValuePair.put(StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS,
				StockUniveralConstants.TRANSACTION_STATUS);
		return paramValuePair;
	}

	/**
	 * Gets the booking universal service.
	 * 
	 * @return the booking universal service
	 */
	public BookingUniversalService getBookingUniversalService() {
		return bookingUniversalService;
	}

	/**
	 * Sets the booking universal service.
	 * 
	 * @param bookingUniversalService
	 *            the new booking universal service
	 */
	public void setBookingUniversalService(
			BookingUniversalService bookingUniversalService) {
		this.bookingUniversalService = bookingUniversalService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * isSeriesConsumed
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Boolean isSeriesConsumed(StockValidationTO validationTo)
			throws CGSystemException, CGBusinessException {
		Boolean isConsumed = null;
		/*long starttime = System.currentTimeMillis();
		LOGGER.debug("StockUniversalServiceImpl::isSeriesConsumed ..Start Time:[ "
				+ starttime + "]");*/
		isConsumed = bookingUniversalService
				.isAtleastOneConsignmentBooked(validationTo.getSeriesList());
		/*long endtime = System.currentTimeMillis();
		LOGGER.debug("StockUniversalServiceImpl::isSeriesConsumed ..Start Time [ "
				+ endtime + "]Total diff :[" + (endtime - starttime) + "]");*/
		return isConsumed;
	}

	@Override
	public Boolean isSeriesConsumedForStock(StockValidationTO validationTo)
			throws CGSystemException, CGBusinessException {
		Boolean isConsumed = null;
		/*long starttime = System.currentTimeMillis();
		LOGGER.debug("StockUniversalServiceImpl::isSeriesConsumedForStock ..Start Time:[ "
				+ starttime + "]");*/
		isConsumed = stockUniversalDAO
				.isAtleastOneConsignmentBooked(validationTo);
		/*long endtime = System.currentTimeMillis();
		LOGGER.debug("StockUniversalServiceImpl::isSeriesConsumedForStock ..Start Time [ "
				+ endtime + "]Total diff :[" + (endtime - starttime) + "]");*/
		return isConsumed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * getStockQuantityByItemAndPartyType
	 * (com.ff.to.stockmanagement.StockSearchInputTO)
	 */
	@Override
	public Integer getStockQuantityByItemAndPartyType(
			StockSearchInputTO searchTO) throws CGSystemException,
			CGBusinessException {
		if (StringUtil.isNull(searchTO)
				|| StringUtil.isStringEmpty(searchTO.getPartyType())
				|| StringUtil.isEmptyInteger(searchTO.getPartyTypeId())) {
			LOGGER.error("StockUniversalServiceImpl::getStockQuantityByItemAndPartyType :: insuffient inputs");
			throw new CGBusinessException("Invalid Inputs for Stock Search");
		}
		return stockUniversalDAO.getStockQuantityByItemAndPartyType(searchTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * updateStockByPartyType(com.ff.to.stockmanagement.StockUpdateInputTO)
	 */
	@Override
	public Boolean updateStockByPartyType(StockUpdateInputTO searchTO)
			throws CGSystemException {
		Integer result = null;
		if (searchTO != null
				&& !StringUtil.isStringEmpty(searchTO.getPartyType())
				&& !StringUtil.isNull(searchTO.getIsDecrease())
				&& !StringUtil.isEmptyInteger(searchTO.getPartyTypeId())
				&& !StringUtil.isEmptyInteger(searchTO.getItemId())) {
			result = stockUniversalDAO.updateStockByPartyType(searchTO);
		} else {
			LOGGER.error("StockUniversalServiceImpl::itemTO2DOConverter :: Insufficient details");
			// Throw Business Exception
		}
		return !StringUtil.isEmptyInteger(result) ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * getAllItemsByType(com.ff.to.stockmanagement.masters.ItemTO)
	 */
	@Override
	public List<ItemTO> getAllItemsByType(ItemTO itemTO)
			throws CGSystemException, CGBusinessException {
		List<ItemTO> result = null;
		ItemDO itemDO = null;
		List<ItemDO> itemDoList = null;

		itemDO = itemTO2DOConverter(itemTO);
		itemDoList = stockUniversalDAO.getAllItemsByType(itemDO);
		result = itemDOList2TOConverter(itemDoList);
		return result;
	}

	/**
	 * Item t o2 do converter.
	 * 
	 * @param itemTo
	 *            the item to
	 * @return the item do
	 */
	private ItemDO itemTO2DOConverter(ItemTO itemTo) {
		ItemDO itemDo = null;
		if (itemTo != null) {
			try {
				itemDo = new ItemDO();
				PropertyUtils.copyProperties(itemDo, itemTo);

				if (itemTo.getItemTypeTO() != null) {
					ItemTypeDO itemTypeDo = new ItemTypeDO();
					PropertyUtils.copyProperties(itemTypeDo,
							itemTo.getItemTypeTO());
					itemDo.setItemTypeDO(itemTypeDo);
				}
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				LOGGER.error("StockUniversalServiceImpl::itemTO2DOConverter ::Exception "
						,e);
			}

		}
		return itemDo;
	}

	/**
	 * Item do list2 to converter.
	 * 
	 * @param empDOList
	 *            the emp do list
	 * @return the list
	 */
	private List<ItemTO> itemDOList2TOConverter(List<ItemDO> empDOList) {
		List<ItemTO> itemToList = null;
		if (!StringUtil.isEmptyList(empDOList)) {
			itemToList = new ArrayList<>(empDOList.size());
			try {
				for (ItemDO itemDO : empDOList) {
					ItemTO to = convertItemDO2TO(itemDO);
					itemToList.add(to);
				}
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				LOGGER.error("StockUniversalServiceImpl::itemDOList2TOConverter ::Exception "
						,e);
				itemToList = null;
			}
		}
		return itemToList;
	}

	/**
	 * @param frDO
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private ItemTO convertItemDO2TO(ItemDO itemDO)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		ItemTO to = null;
		if (!StringUtil.isNull(itemDO)) {
			to = new ItemTO();
			PropertyUtils.copyProperties(to, itemDO);
			if (itemDO.getItemTypeDO() != null) {
				ItemTypeTO itemTypeTo = new ItemTypeTO();
				PropertyUtils
						.copyProperties(itemTypeTo, itemDO.getItemTypeDO());
				to.setItemTypeTO(itemTypeTo);
			}
		}
		return to;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * getAllCustomerForIssue(com.ff.business.CustomerTO)
	 */
	@Override
	public List<CustomerTO> getAllCustomerForIssue(CustomerTO customerTO)
			throws CGSystemException, CGBusinessException {
		List<CustomerTO> customerTOList = null;
		customerTOList = businessCommonService
				.getAllCustomersUnderRegion(customerTO);
		return customerTOList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * getAllEmployeeForIssue(com.ff.organization.EmployeeTO)
	 */
	@Override
	public List<EmployeeTO> getAllEmployeeForIssue(EmployeeTO employeeTo)
			throws CGSystemException, CGBusinessException {
		List<EmployeeTO> employeeToList = null;
		employeeToList = organizationCommonService
				.getAllEmployeesUnderRegion(employeeTo);
		return employeeToList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * getItemDetailsByItemSeries(java.lang.String)
	 */
	@Override
	public ItemTO getItemDetailsByItemSeries(String series)
			throws CGSystemException, CGBusinessException {
		List<ItemTO> resultTO = null;
		ItemTO result = null;
		String requiredSeriesItem = null;
		if (!StringUtil.isStringEmpty(series)) {

			requiredSeriesItem = StockSeriesGenerator.getProductDtls(series
					.toUpperCase());
		} else {
			requiredSeriesItem = null;
		}
		result = new ItemTO();
		result.setIsSeriesVerifier(true);
		result.setItemSeries(requiredSeriesItem);
		ItemTypeTO itemTypeTO = new ItemTypeTO();
		itemTypeTO
				.setItemHasSeries(StockUniveralConstants.STOCK_ITEM_HAS_SERIES_YES);
		result.setItemTypeTO(itemTypeTO);
		resultTO = getAllItemsByType(result);
		if (!CGCollectionUtils.isEmpty(resultTO)) {
			result = resultTO.get(0);
		} else {
			result = null;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.stockmanagement.service.StockUniversalService#
	 * getOfficeDetails(java.lang.Integer)
	 */
	@Override
	public OfficeTO getOfficeDetails(Integer officeId)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getOfficeDetails(officeId);
	}

	@Override
	public List<OfficeTO> getOfficesByOffice(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getOfficesByOffice(officeTO);
	}

	/**
	 * Gets the geography common service.
	 * 
	 * @return the geographyCommonService
	 */
	public GeographyCommonService getGeographyCommonService() {
		return geographyCommonService;
	}

	/**
	 * Sets the geography common service.
	 * 
	 * @param geographyCommonService
	 *            the geographyCommonService to set
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.stockmanagement.service.StockUniversalService#getCity
	 * (com.ff.geography.CityTO)
	 */
	@Override
	public CityTO getCity(CityTO cityTO) throws CGSystemException,
			CGBusinessException {
		return geographyCommonService.getCity(cityTO);
	}


	/**
	 * Purpose : Retrieves item Details based on itemCode If the Item belongs to
	 * cnote category then we'll get details based on
	 * 
	 * @param itemCode
	 *            , Item Product
	 * @return Item TO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */

	private ItemTO getItemDetailsByMaterial(String itemCode, String serialNumber)
			throws CGBusinessException, CGSystemException {
		ItemTO itemTo = null;
		if (!StringUtil.isStringEmpty(itemCode)) {
			itemTo = new ItemTO();
			/** create ItemTo */
			ItemTypeTO typeTo = new ItemTypeTO();
			/** create ItemTypeTo */
			typeTo.setItemHasSeries(StockUniveralConstants.STOCK_ITEM_HAS_SERIES_YES);
			/** Assuming this service only used for Serial number items */
			switch (itemCode) {
			case UdaanCommonConstants.SERIES_TYPE_CNOTES:
				/** For CNOTES */
				typeTo.setItemTypeCode(itemCode);
				if (!StringUtil.isStringEmpty(serialNumber)) {
					if (StockSeriesGenerator.isNormalCnote(serialNumber)) {
						/** For CNOTES :Normal consignments */
						itemTo.setIsSeriesVerifier(true);
					} else {
						/** For CNOTES :For other consignments */
						String product = StockSeriesGenerator
								.getProductDtls(serialNumber);
						itemTo.setItemSeries(product);// item product
					}
				}
				break;
			default:
				typeTo.setItemTypeCode(itemCode);
			}
			itemTo.setItemTypeTO(typeTo);

		}
		if (itemTo != null) {
			List<ItemTO> resultTO = getAllItemsByType(itemTo);
			if (!CGCollectionUtils.isEmpty(resultTO)) {
				itemTo = resultTO.get(0);
			} else {
				itemTo = null;
			}
		}
		return itemTo;
	}

	private ItemTO getItemDetailsByMaterialForStockIntegration(
			String issuedToType, String serialNumber)
			throws CGBusinessException, CGSystemException {
		ItemTO itemTo = null;
		String query = null;
		ItemDO itemDo = null;
		if (!StringUtil.isStringEmpty(issuedToType)) {
			switch (issuedToType) {
			case UdaanCommonConstants.ISSUED_TO_BRANCH:
				/** For CNOTES */
				query = StockUniveralConstants.QRY_ITEM_DTLS_FOR_STOCK_RECEIPT_BY_SERIAL_NUMBER;
				break;
			default:
				query = StockUniveralConstants.QRY_ITEM_DTLS_FOR_STOCK_ISSUE_BY_SERIAL_NUMBER;
			}

			itemDo = stockUniversalDAO.getStockItemDtlsForStockSeries(query,
					serialNumber);
		} else {
			query = StockUniveralConstants.QRY_ITEM_DTLS_FOR_STOCK_ISSUE_BY_SERIAL_NUMBER;
			itemDo = stockUniversalDAO.getStockItemDtlsForStockSeries(query,
					serialNumber);
		}
		if (StringUtil.isNull(itemDo)) {
			query = StockUniveralConstants.QRY_ITEM_DTLS_FOR_STOCK_RECEIPT_BY_SERIAL_NUMBER;
			itemDo = stockUniversalDAO.getStockItemDtlsForStockSeries(query,
					serialNumber);
		}

		try {
			itemTo = convertItemDO2TO(itemDo);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			// TODO Auto-generated catch block
			LOGGER.error("StockUniversalServiceImpl::getItemDetailsByMaterialForStockIntegration::EXCEPTION"
					+ e.getLocalizedMessage());
		}

		return itemTo;
	}

	/**
	 * For length of the Material getItemLengthByMaterial
	 * 
	 * @param itemCode
	 * @param product
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@Override
	public Integer getItemLengthByMaterial(String itemCode, String product)
			throws CGBusinessException, CGSystemException {
		Integer length = null;
		ItemTO itemTo = null;
		try {
			itemTo = getItemDetailsByMaterial(itemCode, product);
		} catch (Exception e) {
			LOGGER.error("StockRequisitionServiceImpl::getItemLengthByMaterial::EXCEPTION"
					+ e.getLocalizedMessage());
		}
		if (itemTo != null) {
			length = itemTo.getSeriesLength();
		}
		return length;
	}

	@Override
	public Boolean isSeriesCancelledForIntegration(
			StockIssueValidationTO validationTo) throws CGBusinessException,
			CGSystemException {
		Long cancelledId = stockUniversalDAO
				.isSeriesCancelledForIntegration(validationTo);
		return StringUtil.isEmptyLong(cancelledId) ? false : true;
	}

	@Override
	public Date getLatestTransferDateBySerialNumber(String serialNumber)
			throws CGBusinessException, CGSystemException {
		return stockUniversalDAO
				.getLatestTransferDateBySerialNumber(
						StockUniveralConstants.QRY_LATEST_TRANSFER_DATE_BY_SERIAL_NUMBER,
						serialNumber);
	}

	@Override
	public Date getLatestIssuedDateBySerialNumber(String serialNumber)
			throws CGBusinessException, CGSystemException {
		return stockUniversalDAO.getLatestTransferDateBySerialNumber(
				StockUniveralConstants.QRY_LATEST_ISSUE_DATE_BY_SERIAL_NUMBER,
				serialNumber);
	}

	@Override
	public Date getLatestReceiptDateBySerialNumber(String serialNumber)
			throws CGBusinessException, CGSystemException {
		return stockUniversalDAO.getLatestTransferDateBySerialNumber(
				StockUniveralConstants.QRY_LATEST_RECEIPT_DATE_SERIAL_NUMBER,
				serialNumber);
	}

	@Override
	public Date getLatestReturnedDateBySerialNumber(String serialNumber)
			throws CGBusinessException, CGSystemException {
		return stockUniversalDAO
				.getLatestTransferDateBySerialNumber(
						StockUniveralConstants.QRY_LATEST_RETURNED_DATE_BY_SERIAL_NUMBER,
						serialNumber);
	}

	/**
	 * sendEmail : send a mail using the information provided in EmailUtil TO
	 * 
	 * @param emailTO
	 * @throws CGBusinessException
	 *             ,, CGSystemException
	 */
	@Override
	public void sendEmail(MailSenderTO emailTO) throws CGBusinessException,
			CGSystemException {
		emailSenderUtil.sendEmail(emailTO);
	}

	/**
	 * get Office details as map under given office Id
	 * 
	 * @param officeId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@Override
	public Map<Integer, String> getOfficeUnderOfficeIdAsMap(Integer officeId)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getOfficeUnderOfficeIdAsMap(officeId);
	}

	@Override
	public Map<Integer, String> getItemTypeAsMap() throws CGSystemException,
			CGBusinessException {
		List<?> itemTypeList = null;
		Map<Integer, String> itemTypeMap = null;
		itemTypeList = stockUniversalDAO.getItemTypeAsMap();
		itemTypeMap = prepareMapFromList(itemTypeList);
		if (!CGCollectionUtils.isEmpty(itemTypeMap)) {
			itemTypeMap = CGCollectionUtils.sortByValue(itemTypeMap);
		}
		return itemTypeMap;
	}

	@Override
	public Map<Integer, String> getItemByTypeAsMap(Integer itemTypeId)
			throws CGSystemException, CGBusinessException {
		List<?> itemList = null;
		Map<Integer, String> itemMap = null;
		itemList = stockUniversalDAO.getItemByTypeAsMap(itemTypeId);
		itemMap = prepareMapFromList(itemList);
		if (!CGCollectionUtils.isEmpty(itemMap)) {
			itemMap = CGCollectionUtils.sortByValue(itemMap);
		}
		return itemMap;
	}

	/**
	 * 
	 * @param customerTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	@Override
	public List<CustomerTO> getContractCustomerListForStock(
			CustomerTO customerTO) throws CGSystemException,
			CGBusinessException {
		List<StockCustomerWrapperDO> shippedCustomerList = null;
		CustomerDO customerDo = prepareCustomerDO(customerTO);
		shippedCustomerList = stockUniversalDAO
				.getContractCustomerListForStock(customerDo);
		List<CustomerTO> customerList = null;
		if (!StringUtil.isEmptyList(shippedCustomerList)) {
			customerList = new ArrayList<>(shippedCustomerList.size());
			for (StockCustomerWrapperDO customerWrapper : shippedCustomerList) {
				String shippedCode = customerWrapper.getShippedTOCode();
				CustomerDO customer = customerWrapper.getCustomerDO();
				if (!StringUtil.isNull(customer)) {
					CustomerTO customerResultTO = new CustomerTO();
					customerResultTO.setCustomerId(customer.getCustomerId());
					customerResultTO
							.setCustomerCode(customer.getCustomerCode());
					customerResultTO
							.setBusinessName(customer.getBusinessName());
					CustomerTypeTO customerTypeTO = StockUtility.populateCustomerTypeFromCustomer(customer);
					customerResultTO.setCustomerTypeTO(customerTypeTO);
					customerResultTO.setShippedToCode(shippedCode);
					customerList.add(customerResultTO);
				}

			}
		}

		return customerList;
	}

	

	/**
	 * @param customerTO
	 * @return
	 * @throws CGBusinessException
	 */
	public CustomerDO prepareCustomerDO(CustomerTO customerTO)
			throws CGBusinessException {
		CustomerDO customerDo = new CustomerDO();
		CGObjectConverter.createDomainFromTo(customerTO, customerDo);
		if (customerTO.getOfficeMappedTO() != null) {
			OfficeDO officeMappedDO = new OfficeDO();
			officeMappedDO.setOfficeId(customerTO.getOfficeMappedTO()
					.getOfficeId());
			officeMappedDO.setReportingRHO(customerTO.getOfficeMappedTO()
					.getReportingRHO());
			officeMappedDO.setReportingHUB(customerTO.getOfficeMappedTO()
					.getReportingHUB());
			customerDo.setOfficeMappedDO(officeMappedDO);
		}
		if (customerTO.getCustomerTypeTO() != null) {
			CustomerTypeDO customerType = new CustomerTypeDO();
			customerType.setCustomerTypeCode(customerTO.getCustomerTypeTO()
					.getCustomerTypeCode());
			customerDo.setCustomerType(customerType);
		}
		return customerDo;
	}

	/**
	 * Prepare map from list.
	 * 
	 * @param itemTypeList
	 *            the item type list
	 * @return the map
	 */
	@SuppressWarnings("rawtypes")
	private Map<Integer, String> prepareMapFromList(List<?> itemTypeList) {
		Map<Integer, String> itemTypeMap = null;
		if (!StringUtil.isEmptyList(itemTypeList)) {
			itemTypeMap = new HashMap<Integer, String>(itemTypeList.size());
			for (Object itemType : itemTypeList) {
				Map map = (Map) itemType;
				String name = (String) map
						.get(StockUniveralConstants.TYPE_NAME);
				itemTypeMap.put(
						(Integer) map.get(StockUniveralConstants.TYPE_ID),
						name.replaceAll(",", ""));
			}
		}
		return itemTypeMap;
	}

	@Override
	public ItemTO getItemByItemTypeAndItemId(Integer itemTypeId, Integer itemId)
			throws CGSystemException, CGBusinessException {
		ItemDO itemDo = null;
		ItemTO itemTo = null;
		itemDo = stockUniversalDAO.getItemByItemTypeAndItemId(itemTypeId,
				itemId);
		if (!StringUtil.isNull(itemDo)) {
			itemTo = new ItemTO();
			try {
				PropertyUtils.copyProperties(itemTo, itemDo);
				if (itemDo.getItemTypeDO() != null) {
					ItemTypeTO itemTypeTO = new ItemTypeTO();
					PropertyUtils.copyProperties(itemTypeTO,
							itemDo.getItemTypeDO());
					itemTo.setItemTypeTO(itemTypeTO);
					String seriesType = itemTypeTO.getItemTypeCode();

					// below code is important from Series validations
					// perspective
					if (!StringUtil.isStringEmpty(seriesType)
							&& seriesType
									.equalsIgnoreCase(UdaanCommonConstants.SERIES_TYPE_CNOTES)) {
						itemTo.setSeriesType(UdaanCommonConstants.SERIES_TYPE_CNOTES);
					} else {
						itemTo.setSeriesType(itemTo.getItemCode());
					}
				}
			} catch (IllegalAccessException e) {
				LOGGER.error("StockUniversalServiceImpl::getItemByItemTypeAndItemId---Exception"
						,e);
			} catch (InvocationTargetException e) {
				LOGGER.error("StockUniversalServiceImpl::getItemByItemTypeAndItemId---Exception"
						,e);
			} catch (NoSuchMethodException e) {
				LOGGER.error("StockUniversalServiceImpl::getItemByItemTypeAndItemId---Exception"
						,e);
			}
		} else {
			LOGGER.warn("StockUniversalServiceImpl::getItemByItemTypeAndItemId---Details doesnot exist");
		}
		return itemTo;
	}

	/**
	 * getPaymentModeDtls : get the payment details based on the Process
	 * 
	 * @param processCode
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@Override
	public List<PaymentModeTO> getPaymentModeDtls(String processCode)
			throws CGBusinessException, CGSystemException {
		return serviceOfferingCommonService.getPaymentModeDtls(processCode);
	}

	@Override
	public void calculateRateForBAMaterial(StockRateTO stockRateTo)
			throws CGBusinessException, CGSystemException {
		rateUniversalService.calculateRateForBAMaterial(stockRateTo);
	}

	@Override
	public Map<String, Double> getTaxComponents(CityTO cityTo, Date taxDate)
			throws CGBusinessException, CGSystemException {
		return rateUniversalService.getTaxComponents(cityTo, taxDate);
	}

	@Override
	public String getConfigParamValueByName(String paramName)
			throws CGBusinessException, CGSystemException {
		return globalUniversalService.getConfigParamValueByName(paramName);
	}

	@Override
	public ItemDO getItemDtlsByMaterialNumber(String materialNo)
			throws CGBusinessException, CGSystemException {
		return stockUniversalDAO
				.getStockItemDtlsForStockSeries(
						StockUniveralConstants.QRY_ITEM_DTLS_FOR_STOCK_RECEIPT_BY_SERIAL_NUMBER,
						materialNo);
	}

}
/**
 * End of Changes by <31913> on 18/09/2013
 * 
 */
