/**
 * 
 */
package com.ff.web.pickup.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.bs.sequence.SequenceGeneratorService;
import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.utility.TwoWayWriteProcessCall;
import com.ff.booking.BookingValidationTO;
import com.ff.business.CustomerTO;
import com.ff.domain.pickup.ReversePickupOrderDetailDO;
import com.ff.domain.ratemanagement.masters.ContractPaymentBillingLocationDO;
import com.ff.geography.CityTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupOrderDetailsTO;
import com.ff.pickup.PickupTwoWayWriteTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.universe.tracking.service.TrackingUniversalService;
import com.ff.web.booking.validator.BookingValidator;
import com.ff.web.pickup.constants.PickupManagementConstants;
import com.ff.web.pickup.dao.PickupGatewayDAO;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * @author kgajare
 * 
 */
public class PickupGatewayServiceImpl implements PickupGatewayService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(PickupGatewayServiceImpl.class);
	private PickupGatewayDAO pickupGatewayDAO;
	private OrganizationCommonService organizationCommonService;
	private SequenceGeneratorService sequenceGeneratorService;
	private BookingValidator bookingValidator;
	private TrackingUniversalService trackingUniversalService;
	private GeographyCommonService geographyCommonService;
	private ServiceOfferingCommonService serviceOfferingCommonService;

	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	public void setSequenceGeneratorService(
			SequenceGeneratorService sequenceGeneratorService) {
		this.sequenceGeneratorService = sequenceGeneratorService;
	}

	public void setBookingValidator(BookingValidator bookingValidator) {
		this.bookingValidator = bookingValidator;
	}

	public void setTrackingUniversalService(
			TrackingUniversalService trackingUniversalService) {
		this.trackingUniversalService = trackingUniversalService;
	}

	/**
	 * @param pickupGatewayDAO
	 *            the pickupGatewayDAO to set
	 */
	public void setPickupGatewayDAO(PickupGatewayDAO pickupGatewayDAO) {
		this.pickupGatewayDAO = pickupGatewayDAO;
	}

	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}

	/**
	 * @see com.ff.web.pickup.service.PickupGatewayService#getBranchEmployees(java.lang.Integer)
	 *      Nov 11, 2012
	 * @param officeId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 *             getBranchEmployees PickupGatewayService kgajare
	 */
	@Override
	public List<EmployeeTO> getBranchEmployees(Integer officeId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupGatewayServiceImpl :: getBranchEmployees() :: Start --------> ::::::");
		List<EmployeeTO> employeeTOs = null;
		try {
			employeeTOs = organizationCommonService
					.getBranchEmployees(officeId);
			if (StringUtil.isEmptyList(employeeTOs)) {
				throw new CGBusinessException(
						UdaanWebErrorConstants.NO_EMPLOYEES_FOUND);
			}
		} catch (CGBusinessException | CGSystemException obj) {
			LOGGER.error("ERROR : PickupGatewayServiceImpl.getBranchEmployees",
					obj);
			throw obj;
		}
		LOGGER.trace("PickupGatewayServiceImpl :: getBranchEmployees() :: End --------> ::::::");
		return employeeTOs;
	}

	/**
	 * @see com.ff.web.pickup.service.PickupGatewayService#getMasterCustomerList(java.lang.Integer)
	 *      Nov 11, 2012
	 * @param officeId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 *             getMasterCustomerList PickupGatewayService kgajare
	 */
	@Override
	public List<CustomerTO> getMasterCustomerList(Integer officeId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupGatewayServiceImpl :: getMasterCustomerList() :: Start --------> ::::::");
		List<CustomerTO> customerTOs = null;
		customerTOs = organizationCommonService.getMasterCustomerList(officeId);

		LOGGER.trace("PickupGatewayServiceImpl :: getMasterCustomerList() :: End --------> ::::::");
		return customerTOs;
	}

	/**
	 * @see com.ff.web.pickup.service.PickupGatewayService#getReverseCustomerList(java.lang.Integer)
	 *      Nov 11, 2012
	 * @param officeId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 *             getReverseCustomerList PickupGatewayService kgajare
	 */
	@Override
	public List<CustomerTO> getReverseCustomerList(Integer officeId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupGatewayServiceImpl :: getReverseCustomerList() :: Start --------> ::::::");
		List<CustomerTO> customerTOs = null;
		customerTOs = organizationCommonService
				.getReverseCustomerList(officeId);
		LOGGER.trace("PickupGatewayServiceImpl :: getReverseCustomerList() :: End --------> ::::::");
		return customerTOs;
	}

	/**
	 * @see com.ff.web.pickup.service.PickupGatewayService#getBranchesUnderHUB(java.lang.Integer)
	 *      Nov 11, 2012
	 * @param officeId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 *             getBranchesUnderHUB PickupGatewayService kgajare
	 */
	@Override
	public List<LabelValueBean> getBranchesUnderHUB(Integer officeId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupGatewayServiceImpl :: getBranchesUnderHUB() :: Start --------> ::::::");
		List<LabelValueBean> branchTOs = null;
		try {
			List<OfficeTO> officeTOs = organizationCommonService
					.getBranchesUnderHUB(officeId);
			if (!StringUtil.isEmptyList(officeTOs)) {
				branchTOs = new ArrayList<LabelValueBean>();
				for (OfficeTO officeTO : officeTOs) {
					branchTOs.add(new LabelValueBean(officeTO.getOfficeCode()
							+ " - " + officeTO.getOfficeName(), officeTO
							.getOfficeId() + ""));
				}
			} else {
				throw new CGBusinessException(
						UdaanWebErrorConstants.NO_OFFICES_FOUND);
			}
		} catch (CGBusinessException | CGSystemException obj) {
			LOGGER.error(
					"ERROR : PickupGatewayServiceImpl.getBranchesUnderHUB", obj);
			throw obj;
		}
		LOGGER.trace("PickupGatewayServiceImpl :: getBranchesUnderHUB() :: End --------> ::::::");
		return branchTOs;
	}

	/**
	 * @see com.ff.web.pickup.service.PickupGatewayService#getOfficeDetails(java.lang.Integer)
	 *      Dec 7, 2012
	 * @param officeId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 *             getOfficeDetails PickupGatewayService kgajare
	 */
	@Override
	public OfficeTO getOfficeDetails(Integer officeId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupGatewayServiceImpl :: getOfficeDetails() :: Start --------> ::::::");
		OfficeTO officeTO = null;

		officeTO = organizationCommonService.getOfficeDetails(officeId);
		LOGGER.trace("PickupGatewayServiceImpl :: getOfficeDetails() :: End --------> ::::::");
		return officeTO;
	}

	@Override
	public List<String> generateRunsheetNumber(Integer noOfRunsheets)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupGatewayServiceImpl :: generateRunsheetNumber() :: Start --------> ::::::");
		List<String> sequenceNumberList = null;
		SequenceGeneratorConfigTO sequenceGeneratorConfigTO = new SequenceGeneratorConfigTO();
		try {
			sequenceGeneratorConfigTO
					.setProcessRequesting(CommonConstants.GENERATE_PICKUP_RUN_SHEET_NO);
			sequenceGeneratorConfigTO
					.setNoOfSequencesToBegenerated(noOfRunsheets);
			sequenceGeneratorConfigTO.setRequestDate(new Date());
			sequenceGeneratorConfigTO = sequenceGeneratorService
					.getGeneratedSequence(sequenceGeneratorConfigTO);
			if (sequenceGeneratorConfigTO.getGeneratedSequences() != null
					&& sequenceGeneratorConfigTO.getGeneratedSequences().size() > 0)
				sequenceNumberList = sequenceGeneratorConfigTO
						.getGeneratedSequences();
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR : PickupGatewayServiceImpl :: generateRunsheetNumber :: ",
					e);
			throw e;
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR : PickupGatewayServiceImpl :: generateRunsheetNumber :: ",
					e);
			throw new CGBusinessException(
					PickupManagementConstants.SEQUENCE_NUMBER_GENERATED);
		}
		LOGGER.trace("PickupGatewayServiceImpl :: generateRunsheetNumber() :: End --------> ::::::");
		return sequenceNumberList;
	}

	@Override
	public BookingValidationTO validateConsignment(
			BookingValidationTO cnValidationTO) throws CGBusinessException,
			CGSystemException {
		return bookingValidator.isValidConsignment(cnValidationTO);
	}

	@Override
	public ProcessTO getProcess(ProcessTO process) throws CGSystemException,
			CGBusinessException {
		return trackingUniversalService.getProcess(process);
	}

	@Override
	public List<OfficeTO> getOfficeListByOfficeTO(OfficeTO officeTo)
			throws CGBusinessException, CGSystemException {
		List<OfficeTO> officeTOList = null;
		officeTOList = organizationCommonService.getOfficesByOffice(officeTo);

		return officeTOList;
	}

	@Override
	public PickupOrderDetailsTO getReversePickupOrderDetail(
			PickupOrderDetailsTO detailTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("PickupGatewayServiceImpl :: getReversePickupOrderDetail() :: Start --------> ::::::");
		if (!StringUtil.isNull(detailTO)) {
			ReversePickupOrderDetailDO detailDO = pickupGatewayDAO
					.getReversePickupOrderDetail(detailTO);
			if(!StringUtil.isNull(detailDO)){
				detailTO = (PickupOrderDetailsTO) CGObjectConverter.createToFromDomain(
						detailDO, detailTO);
			}
		}
		LOGGER.trace("PickupGatewayServiceImpl :: getReversePickupOrderDetail() :: End --------> ::::::");
		return detailTO;
	}

	@Override
	public OfficeTO getOfficeByempId(Integer empId) throws CGBusinessException,
			CGSystemException {
		return organizationCommonService.getOfficeByempId(empId);
	}

	@Override
	public ContractPaymentBillingLocationDO getContractPayBillingLocationDtlsBypickupLocation(
			Integer pickupDlvLocId) throws CGBusinessException,
			CGSystemException {
		ContractPaymentBillingLocationDO contractPaymentBillingLocationDO = null;
		try {
			contractPaymentBillingLocationDO = pickupGatewayDAO
					.getContractPayBillingLocationDtlsBypickupLocation(pickupDlvLocId);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : PickupGatewayServiceImpl :: getContractPayBillingLocationDtlsBypickupLocation() :: ",
					e);
			ExceptionUtil.prepareBusinessException(UdaanWebErrorConstants.DETAILS_NOT_EXIST_FOR_DB_ISSUE, new String[]{"Rate Contract"});
		}
		return contractPaymentBillingLocationDO;
	}
	@Override
	public String getShippedToCodeByLocationId(
			Integer pickupDlvLocId) throws CGBusinessException,
			CGSystemException {
		return pickupGatewayDAO.getShippedToCodeByLocationId(pickupDlvLocId);			
	}
	@Override
	public CityTO getCity(Integer cityId) throws CGSystemException,
			CGBusinessException {
		CityTO cityTO = new CityTO();
		cityTO.setCityId(cityId);
		return geographyCommonService.getCity(cityTO);
	}

	@Override
	public ProductTO getProductByConsgSeries(String consgSeries)
			throws CGBusinessException, CGSystemException {
		return serviceOfferingCommonService
				.getProductByConsgSeries(consgSeries);
	}
	@Override
	public void twoWayWrite(PickupTwoWayWriteTO pickupTwoWayWriteTO) {
		LOGGER.debug("PickupManagementCommonServiceImpl::twoWayWrite::Calling TwoWayWrite service to save the same object in central------------>:::::::");
		TwoWayWriteProcessCall.twoWayWriteProcess(pickupTwoWayWriteTO.getHeaderDoIds(), pickupTwoWayWriteTO.getHeaderProcessNames());	
	}

	@Override
	public String getLatestShipToCodeByCustomer(Integer OfficeId,
			Integer customerId) throws CGBusinessException, CGSystemException {
		return pickupGatewayDAO.getLatestShipToCodeByCustomer(OfficeId, customerId);
	}
}
