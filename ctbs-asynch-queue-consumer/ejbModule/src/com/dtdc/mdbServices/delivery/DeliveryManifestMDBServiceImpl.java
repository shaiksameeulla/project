package src.com.dtdc.mdbServices.delivery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.constants.CommonConstants;
import src.com.dtdc.constants.DeliveryManifestConstants;
import src.com.dtdc.constants.ManifestConstant;
import src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO;
import src.com.dtdc.mdbServices.CTBSApplicationMDBDAO;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.dtdc.domain.expense.ExpenditureTypeDO;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.master.HandHeldDeviceDO;
import com.dtdc.domain.master.ReasonDO;
import com.dtdc.domain.master.customer.ConsigneeAddressDO;
import com.dtdc.domain.master.customer.ConsigneeDO;
import com.dtdc.domain.master.document.DocumentDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.franchisee.FranchiseeDO;
import com.dtdc.domain.master.geography.AreaDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.product.ServiceDO;
import com.dtdc.domain.transaction.delivery.DeliveryDO;
import com.dtdc.domain.transaction.delivery.DeliveryManifestBookingDO;
import com.dtdc.domain.transaction.delivery.DeliveryManifestFranchiseeDO;
import com.dtdc.domain.transaction.delivery.DeliveryManifestOfficeDO;
import com.dtdc.domain.transaction.delivery.FranchiseDeliveryManifestDO;
import com.dtdc.domain.transaction.delivery.FranchiseDeliveryManifestHandOverDO;
import com.dtdc.to.common.ReasonTO;
import com.dtdc.to.delivery.DeliveryManifestEmployeesTO;
import com.dtdc.to.delivery.DeliveryManifestFranchiseeTO;
import com.dtdc.to.delivery.DeliveryManifestOfficeTO;
import com.dtdc.to.delivery.DeliveryManifestTO;
import com.dtdc.to.delivery.FranchiseeDeliveryHandoverTO;
import com.dtdc.to.delivery.FranchiseeDeliveryManifestTO;
import com.dtdc.to.delivery.HandHeldDeviceTO;
import com.dtdc.to.expense.CnMiscExpenseTO;

// TODO: Auto-generated Javadoc
/**
 * The Class DeliveryManifestMDBServiceImpl.
 */
public class DeliveryManifestMDBServiceImpl implements
DeliveryManifestMDBService {
	/** logger. */
	private Logger logger = LoggerFactory
	.getLogger(DeliveryManifestMDBServiceImpl.class);

	/** The DeliveryManifest DAO. */
	private DeliveryManifestMDBDAO deliveryManifestMDBDAO;

	/** The ctbs application mdbdao. */
	private CTBSApplicationMDBDAO ctbsApplicationMDBDAO;

	/**
	 * Gets the delivery manifest mdbdao.
	 *
	 * @return the delivery manifest mdbdao
	 */
	public DeliveryManifestMDBDAO getDeliveryManifestMDBDAO() {
		return deliveryManifestMDBDAO;
	}

	/**
	 * Sets the delivery manifest mdbdao.
	 *
	 * @param deliveryManifestMDBDAO the new delivery manifest mdbdao
	 */
	public void setDeliveryManifestMDBDAO(
			DeliveryManifestMDBDAO deliveryManifestMDBDAO) {
		this.deliveryManifestMDBDAO = deliveryManifestMDBDAO;
	}

	/**
	 * Gets the ctbs application mdbdao.
	 *
	 * @return the ctbs application mdbdao
	 */
	public CTBSApplicationMDBDAO getCtbsApplicationMDBDAO() {
		return ctbsApplicationMDBDAO;
	}

	/**
	 * Sets the ctbs application mdbdao.
	 *
	 * @param ctbsApplicationMDBDAO the new ctbs application mdbdao
	 */
	public void setCtbsApplicationMDBDAO(
			CTBSApplicationMDBDAO ctbsApplicationMDBDAO) {
		this.ctbsApplicationMDBDAO = ctbsApplicationMDBDAO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#saveBrDeliveryManifestDBSync(List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String saveBrDeliveryManifestDBSync(
			List<DeliveryManifestTO> deliveryManifestTO)
	throws CGBusinessException, CGSystemException {
		String bdmDetails = "";
		List<DeliveryDO> brDeliveryList = null;
		try {
			logger.trace("*********DeliveryManifestMDBServiceImpl::saveBrDeliveryManifestDBSync*******: Start:"
					+ System.currentTimeMillis());
			brDeliveryList = DeliveryManifestTOToDOMDBConvertor
			.dlvBrManifestTODOConverterDBSync(deliveryManifestTO,
					deliveryManifestMDBDAO, "BDM");
			if (brDeliveryList != null && !brDeliveryList.isEmpty()) {
				logger.trace("*********DeliveryManifestMDBServiceImpl::saveBrDeliveryManifestDBSync*******: Start:Save"
						+ System.currentTimeMillis());
				bdmDetails = deliveryManifestMDBDAO
				.saveOrUpdateBrDeliveryManifest(brDeliveryList);
				logger.trace("*********DeliveryManifestMDBServiceImpl::saveBrDeliveryManifestDBSync*******: End:Save"
						+ System.currentTimeMillis());
			} else {
				logger.trace("*********saveBrDeliveryManifestDBSync*****:No records found to insert!");
			}
		} catch (CGBusinessException e) {
			logger.error("DeliveryManifestMDBServiceImpl::saveBrDeliveryManifestDBSync::Exception occured:"
					+e.getMessage());
		}
		logger.trace("*********DeliveryManifestMDBServiceImpl::saveBrDeliveryManifestDBSync*******: End:"
				+ System.currentTimeMillis());
		return bdmDetails;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#saveBrDeliveryManifestDBSync(CGBaseTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String saveBrDeliveryManifestDBSync(CGBaseTO brDeliveryTO)
	throws CGSystemException, CGBusinessException {
		String message = "";
		logger.debug("*********saveBrDeliveryManifestDBSync*******: Strat");
		List<DeliveryManifestTO> dlvMnfstTOs = (List<DeliveryManifestTO>) brDeliveryTO
		.getBaseList();
		if (dlvMnfstTOs != null && !dlvMnfstTOs.isEmpty()) {
			message = saveBrDeliveryManifestDBSync(dlvMnfstTOs);
		} else {
			logger.error("*********saveBrDeliveryManifestDBSync*******: No records found");
		}
		return message;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#saveFrDeliveryManifestDBSync(List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String saveFrDeliveryManifestDBSync(
			List<FranchiseeDeliveryManifestTO> frDeliveryManifestTO)
	throws CGSystemException, CGBusinessException {
		logger.trace("********DeliveryManifestMDBServiceImpl:saveFrDeliveryManifestDBSync*******: Start:"
				+ System.currentTimeMillis());
		String fdmDetails = "";
		List<FranchiseDeliveryManifestDO> brDeliveryList = null;
		try {
			brDeliveryList = DeliveryManifestTOToDOMDBConvertor
			.dlvFrManifestTODOConverterDBSync(frDeliveryManifestTO,
					deliveryManifestMDBDAO, "FDM");
			if (brDeliveryList != null && !brDeliveryList.isEmpty()) {
				logger.info("********DeliveryManifestMDBServiceImpl:saveFrDeliveryManifestDBSync*******: Start:Save"
						+ System.currentTimeMillis());
				fdmDetails = deliveryManifestMDBDAO
				.saveOrUpdateFrDeliveryManifest(brDeliveryList);
				logger.info("*********DeliveryManifestMDBServiceImpl:saveFrDeliveryManifestDBSync*******:End:Save"
						+ System.currentTimeMillis());
			} else {
				logger.info("*********saveFrDeliveryManifestDBSync*****:No records found to insert!");
			}
		} catch (CGBusinessException e) {
			logger.error("DeliveryManifestMDBServiceImpl::saveFrDeliveryManifestDBSync::Exception occured:"
					+e.getMessage());
		}
		return fdmDetails;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#saveFrDeliveryManifestDBSync(CGBaseTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String saveFrDeliveryManifestDBSync(CGBaseTO frDeliveryManifestTO)
	throws CGSystemException, CGBusinessException {
		String message = "";
		List<FranchiseeDeliveryManifestTO> frDlvMnfstTOs = (List<FranchiseeDeliveryManifestTO>) frDeliveryManifestTO
		.getBaseList();
		if (frDlvMnfstTOs != null && !frDlvMnfstTOs.isEmpty()) {
			message = saveFrDeliveryManifestDBSync(frDlvMnfstTOs);
		} else {
			logger.error("*********saveFrDeliveryManifestDBSync*******: No records found");
		}
		return message;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#saveFrDeliveryManifestHODBSync(List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String saveFrDeliveryManifestHODBSync(
			List<FranchiseeDeliveryHandoverTO> frDeliveryManifestTO)
	throws CGSystemException, CGBusinessException {
		List<FranchiseDeliveryManifestHandOverDO> frHODeliveryList = null;
		try {
			frHODeliveryList = DeliveryManifestTOToDOMDBConvertor
			.frHandOverTOToDOConvertor(frDeliveryManifestTO,
					deliveryManifestMDBDAO);
			if (frHODeliveryList != null && !frHODeliveryList.isEmpty()) {
				deliveryManifestMDBDAO
				.saveFrDeliveryHOManifest(frHODeliveryList);
			} else {
				logger.info("*********saveFrDeliveryManifestHODBSync*****:No records found to insert!");
			}
		} catch (CGBusinessException e) {
			logger.error("DeliveryManifestMDBServiceImpl::saveFrDeliveryManifestHODBSync::Exception occured:"
					+e.getMessage());
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#saveFrDeliveryManifestHODBSync(CGBaseTO)
	 */
	@Override
	public String saveFrDeliveryManifestHODBSync(CGBaseTO frDlvHOManifest)
	throws CGSystemException, CGBusinessException {
		String message = "";
		logger.debug("*********saveFrDeliveryManifestHODBSync*******: Strat");
		logger.debug("*********saveFrDeliveryManifestHODBSync*******: CGBaseTO:"
				+ frDlvHOManifest.getBaseList());
		@SuppressWarnings("unchecked")
		List<FranchiseeDeliveryHandoverTO> frHODlvMnfstTOs = (List<FranchiseeDeliveryHandoverTO>) frDlvHOManifest
		.getBaseList();
		if (frHODlvMnfstTOs != null && !frHODlvMnfstTOs.isEmpty()) {
			message = saveFrDeliveryManifestHODBSync(frHODlvMnfstTOs);
			logger.error("*********saveFrDeliveryManifestHODBSync*******:message:"
					+ message);
		} else {
			logger.error("*********saveFrDeliveryManifestHODBSync*******: No records found");
		}
		return message;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#saveBrDeliveryManifest(CGBaseTO)
	 */
	@Override
	public String saveBrDeliveryManifest(CGBaseTO brDeliveryTO)
	throws CGSystemException, CGBusinessException {
		DeliveryManifestTO brDeliveryTO_Obj = (DeliveryManifestTO) brDeliveryTO
		.getBaseList().get(0);

		return saveBrDeliveryManifest(brDeliveryTO_Obj);
	}

	/**
	 * Save br delivery manifest.
	 *
	 * @param brDeliveryTO the br delivery to
	 * @return List<DeliveryManifestTO>
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public String saveBrDeliveryManifest(DeliveryManifestTO brDeliveryTO)
	throws CGBusinessException, CGSystemException {
		String bdmDetails = "";
		String empCode = "";

		StringBuilder bdmDetailsBuff = new StringBuilder();
		try {

			empCode = brDeliveryTO.getEmpCode();
			List<MiscExpenseDO> miscExpenseDOList = new ArrayList<MiscExpenseDO>(
					0);
			if (!(StringUtils.isEmpty(empCode))) {
				String[] empList = empCode.split("-");
				empCode = empList[0].trim();
				EmployeeDO employee = ctbsApplicationMDBDAO
				.getEmployeeByCodeOrID(-1, empCode);
				brDeliveryTO.setEmpID((employee.getEmployeeId()));
			}
			if (brDeliveryTO.getMiscExpenseTOList() != null
					&& !brDeliveryTO.getMiscExpenseTOList().isEmpty()) {
				List<CnMiscExpenseTO> miscExpenseToList = brDeliveryTO
				.getMiscExpenseTOList();
				if (miscExpenseToList != null && !miscExpenseToList.isEmpty()) {
					for (CnMiscExpenseTO miscExpenseTo : miscExpenseToList) {
						MiscExpenseDO miscExpenseDo = getMiscExpenseDoFromTo(miscExpenseTo);
						// miscExpenseDo.setProcessName("BDM");
						miscExpenseDOList.add(miscExpenseDo);
					}
				}
				if (miscExpenseDOList != null && !miscExpenseDOList.isEmpty()) {
					deliveryManifestMDBDAO
					.saveMiscExpForRelease(miscExpenseDOList);
				}
			}
			List<DeliveryDO> brDeliveryList = new ArrayList<DeliveryDO>();
			// TO DO Rate Calculation part
			brDeliveryList = DeliveryManifestTOToDOMDBConvertor
			.branchDeliveryTOToDOConvertor(brDeliveryTO,
					deliveryManifestMDBDAO);
			// check the RTO status
			for (DeliveryDO brDelivery : brDeliveryList) {
				String consgNumber = brDelivery.getConNum();
				boolean isRTOConsgment = deliveryManifestMDBDAO
				.isRTOConsignment(consgNumber,
						ManifestConstant.MANIFEST_TYPE_AGAINST_INCOMING);
				if (isRTOConsgment) {
					brDelivery
					.setConsgStatus(CommonConstants.CONSGN_STATUS_RTO);
					brDelivery
					.setDeliveryStatus(CommonConstants.CONSGN_STATUS_RTO);
				}
			}

			if (brDeliveryList != null) {
				if (brDeliveryList.size() > 0) {
					bdmDetails = deliveryManifestMDBDAO
					.saveOrUpdateBrDeliveryManifest(brDeliveryList);
				}
			}
		} catch (CGBusinessException e) {
			logger.error("DeliveryManifestMDBServiceImpl::saveBrDeliveryManifest::Exception occured:"
					+e.getMessage());
			bdmDetailsBuff.append("failure");
			bdmDetailsBuff.append(",");
			bdmDetailsBuff
			.append("Error occured while inserting Branch Delivery data");
			return bdmDetailsBuff.toString();
		}
		return bdmDetails;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#saveFrDeliveryManifest(CGBaseTO)
	 */
	@Override
	public String saveFrDeliveryManifest(CGBaseTO frDeliveryManifestTO)
	throws CGSystemException, CGBusinessException {
		FranchiseeDeliveryManifestTO frDeliveryManifestTO_Obj = (FranchiseeDeliveryManifestTO) frDeliveryManifestTO
		.getBaseList().get(0);

		return saveFrDeliveryManifest(frDeliveryManifestTO_Obj);
	}

	/**
	 * Save fr delivery manifest.
	 *
	 * @param frDeliveryManifestTO the fr delivery manifest to
	 * @return List<FranchiseeDeliveryManifestTO>
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public String saveFrDeliveryManifest(
			FranchiseeDeliveryManifestTO frDeliveryManifestTO)
	throws CGBusinessException, CGSystemException {
		List<FranchiseDeliveryManifestDO> frDeliveryManifestList = new ArrayList<FranchiseDeliveryManifestDO>();
		String fdmNumber = null;
		StringBuilder fdmDetails = new StringBuilder();

		try {

			List<MiscExpenseDO> miscExpenseDOList = new ArrayList<MiscExpenseDO>(
					0);
			if (frDeliveryManifestTO.getMiscExpenseTOList() != null
					&& !frDeliveryManifestTO.getMiscExpenseTOList().isEmpty()) {
				List<CnMiscExpenseTO> miscExpenseToList = frDeliveryManifestTO
				.getMiscExpenseTOList();
				if (miscExpenseToList != null && !miscExpenseToList.isEmpty()) {
					for (CnMiscExpenseTO miscExpenseTo : miscExpenseToList) {
						MiscExpenseDO miscExpenseDo = getMiscExpenseDoFromTo(miscExpenseTo);
						// miscExpenseDo.setProcessName("FDM");
						miscExpenseDOList.add(miscExpenseDo);
					}
				}
				if (miscExpenseDOList != null && !miscExpenseDOList.isEmpty()) {
					deliveryManifestMDBDAO
					.saveMiscExpForRelease(miscExpenseDOList);
				}
			}
			// TO DO Rate Calculation part
			frDeliveryManifestList = DeliveryManifestTOToDOMDBConvertor
			.franchiseeDeliveryTOToDOConvertor(frDeliveryManifestTO,
					deliveryManifestMDBDAO, ctbsApplicationMDBDAO);
			if (frDeliveryManifestList != null) {
				if (frDeliveryManifestList.size() > 0) {
					fdmNumber = deliveryManifestMDBDAO
					.saveOrUpdateFrDeliveryManifest(frDeliveryManifestList);
					// Sending email
					if (StringUtils.isNotEmpty(fdmNumber)) {
						fdmDetails.append(DeliveryManifestConstants.SUCCESS);
						fdmDetails.append(CommonConstants.COMMA);
						fdmDetails.append(fdmNumber);
					}
				}
			}
		} catch (CGBusinessException e) {
			logger.error("DeliveryManifestMDBServiceImpl::saveFrDeliveryManifest::Exception occured:"
					+e.getMessage());
			fdmDetails.append(DeliveryManifestConstants.FAILURE);
			fdmDetails.append(CommonConstants.COMMA);
			fdmDetails
			.append("Error occured while saving Franchise Delivery data");
			throw new CGBusinessException(e);
		}
		return fdmDetails.toString();
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#saveFrDeliveryManifestHO(CGBaseTO)
	 */
	@Override
	public String saveFrDeliveryManifestHO(CGBaseTO frDeliveryManifestHOTO)
	throws CGSystemException, CGBusinessException {
		FranchiseeDeliveryHandoverTO frDeliveryManifestHOTO_Obj = (FranchiseeDeliveryHandoverTO) frDeliveryManifestHOTO
		.getBaseList().get(0);

		return saveFrDeliveryManifestHO(frDeliveryManifestHOTO_Obj);
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#saveFrDeliveryManifestHO(FranchiseeDeliveryHandoverTO)
	 */
	@Override
	public String saveFrDeliveryManifestHO(
			FranchiseeDeliveryHandoverTO frDeliveryManifestTO)
	throws CGSystemException, CGBusinessException {
		String fdmHODetails = "";
		try {
			List<FranchiseDeliveryManifestHandOverDO> fdmHandOverList = new ArrayList<FranchiseDeliveryManifestHandOverDO>();
			fdmHandOverList = DeliveryManifestTOToDOMDBConvertor
			.franchiseeHandOverTOToDOConvertor(frDeliveryManifestTO);
			if (fdmHandOverList != null) {
				if (fdmHandOverList.size() > 0) {
					fdmHODetails = deliveryManifestMDBDAO
					.saveFrDeliveryHOManifest(fdmHandOverList);
				}
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBServiceImpl::saveFrDeliveryManifestHO::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(
					"DeliveryManifestServiceImpl.saveFrDeliveryManifestHO(): Error occured. FDMHandover not saved. ",
					e);

		}
		return fdmHODetails;
	}

	/**
	 * Gets the booking details.
	 *
	 * @param consgNumber the consg number
	 * @param editFlag the edit flag
	 * @param preOffId the pre off id
	 * @param preparationDate the preparation date
	 * @param isFDM the is fdm
	 * @return DTDCBookingDO
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public String getBookingDetails(String consgNumber, String editFlag,
			int preOffId, Date preparationDate, String isFDM)
	throws CGSystemException {
		DeliveryManifestBookingDO bookingDO = null;
		StringBuilder deliveryManifestBooking = null;
		long fdmCount = 0;
		long bdmCount = 0;
		// int samDayConsgCount = 0;
		String consgNumberSeries = "";
		ConsigneeDO consignee = null;
		ConsigneeAddressDO consigneeAddress = null;
		int bookedOfficeId = 0;
		int attemptCount = 0;
		ManifestDO inScanDetails = null;
		List<Integer> maniefstTypeIds = null;
		List<String> manifestCodes = new ArrayList<String>();
		boolean isConsignmentDelivered = Boolean.FALSE;
		String duplicateCongntSameDay = "";
		String[] attemtStatus = { "N", "R" };
		boolean isConsgReturned = Boolean.FALSE;
		DocumentDO document = null;
		PincodeDO destPincode = null;
		ServiceDO service = null;
		try {

			deliveryManifestBooking = new StringBuilder();

			// Checking In Scan consignments
			manifestCodes = new ArrayList<String>();
			manifestCodes.add(ManifestConstant.MANIFEST_TYPE_PACKET);
			manifestCodes.add(ManifestConstant.MANIFEST_TYPE_BAG_NONDOX);
			manifestCodes.add(ManifestConstant.POD_MANIFEST_TYPE_CODE);
			maniefstTypeIds = ctbsApplicationMDBDAO
			.getManifestTypeIds(manifestCodes);
			inScanDetails = deliveryManifestMDBDAO.getIncmgMnfst(consgNumber,
					maniefstTypeIds);
			bookingDO = deliveryManifestMDBDAO.getBookingDetails(consgNumber);

			if (inScanDetails == null && bookingDO == null) {

				return deliveryManifestBooking.toString();
			}
			if (inScanDetails == null) {
				if (bookingDO != null) {
					if (bookingDO.getBookedOffice().getOfficeId() != preOffId) {

						return deliveryManifestBooking.toString();
					}
				}
			}

			consgNumberSeries = consgNumber.substring(0, 1);

			// Check for consignment delivered or not
			isConsignmentDelivered = deliveryManifestMDBDAO
			.isConsignmentDelivered(consgNumber);
			if (isConsignmentDelivered) {

				return deliveryManifestBooking.toString();
			}
			// Cheeck for RTO is returned or not
			isConsgReturned = deliveryManifestMDBDAO
			.isConsignmentReturned(consgNumber);
			if (isConsgReturned) {
				if (bookedOfficeId == preOffId) {

					return deliveryManifestBooking.toString();
				}

			}

			if (StringUtils.isEmpty(editFlag)) {
				attemptCount = deliveryManifestMDBDAO.getAttemptNumber(
						consgNumber, attemtStatus);
				if(attemptCount <= 0){
					fdmCount = deliveryManifestMDBDAO.getDuplicateConsignment(
							consgNumber,
							DeliveryManifestConstants.CONSGN_STATUS_FDM);
					bdmCount = deliveryManifestMDBDAO.getDuplicateConsignment(
							consgNumber,
							DeliveryManifestConstants.CONSGN_STATUS_BDM);
					if (bdmCount > 0) {

						return deliveryManifestBooking.toString();

					} else if (fdmCount > 0) {

						return deliveryManifestBooking.toString();
					}
				}
			}

			if (inScanDetails != null || bookingDO != null) {

				if (bookingDO != null) {
					// Checking the consg has a paperwork or not
					// artf1614228 : paperwork check should not be there for SJR
					/*
					 * isPaperWrkCOnsg = deliveryManifestMDBDAO
					 * .isPaperWrkConsg(consgNumber); if (isPaperWrkCOnsg) {
					 * ManifestTypeDO getManifestType = null; int
					 * paperWrkManiefstTypeId = 0; getManifestType =
					 * ctbsApplicationMDBDAO
					 * .getManifestTypeByCode(ManifestConstant
					 * .PAPER_WORK_MANIFEST); if (getManifestType != null)
					 * paperWrkManiefstTypeId = getManifestType
					 * .getMnfstTypeId(); isPaperWorkExits =
					 * deliveryManifestMDBDAO
					 * .isPaperWorkManifested(consgNumber,
					 * paperWrkManiefstTypeId); if (!isPaperWorkExits) {
					 * deliveryManifestBooking .append(rb
					 * .getString(DeliveryManifestConstants.CONSG_PAPERWORK));
					 * return deliveryManifestBooking.toString(); } }
					 */

					if (StringUtils.equals(consgNumberSeries,
							CommonConstants.CONSIGNMENT_D_SERIES)
							|| StringUtils.equals(consgNumberSeries,
									CommonConstants.CONSIGNMENT_E_SERIES)
									|| StringUtils.equals(consgNumberSeries,
											CommonConstants.CONSIGNMENT_V_SERIES)
											|| StringUtils.equals(consgNumberSeries,
													CommonConstants.CONSIGNMENT_W_SERIES)) {
						consignee = bookingDO.getConsignee();
						if (consignee != null) {
							consigneeAddress = ctbsApplicationMDBDAO
							.getConsigneeAddress(consignee
									.getConsigneeId());
						} else {

							return deliveryManifestBooking.toString();
						}
					}

				}// if booking

				if (inScanDetails != null) {
					if (bookingDO == null) {
						bookingDO = new DeliveryManifestBookingDO();
					}
					if (inScanDetails.getRecvBranch().getOfficeId() == preOffId) {
						inScanDetails.getStatus();
						if (inScanDetails.getNoOfPieces() != null) {
							bookingDO.setNuOfPieces(inScanDetails
									.getNoOfPieces());
						}
						if (inScanDetails.getIndvWeightKgs() != null) {
							bookingDO.setBookedWeight(inScanDetails
									.getIndvWeightKgs());
						}
						// setting volumetric weight
						bookingDO.setLengthInCms(inScanDetails.getLength());
						bookingDO.setBreadthInCms(inScanDetails.getBreadth());
						bookingDO.setHeightInCms(inScanDetails.getHeight());
						bookingDO.setVolumtericWeight(inScanDetails
								.getVolumetricWeight());

						// get pincode if exists
						destPincode = inScanDetails.getDestPinCode();
						if (destPincode != null) {
							bookingDO.setDestPinCode(destPincode);
						}

						// setting the document type
						document = inScanDetails.getDocument();
						if (document == null) {
							document = new DocumentDO();
							document.setDocumentId(1);
							if (inScanDetails.getMnsftTypes().getMnfstTypeId()
									.intValue() == 2) {
								document.setDocumentId(2);
							}
						}
						bookingDO.setDocument(document);

						// setting Service id
						service = inScanDetails.getService();
						if (service != null) {
							bookingDO.setService(service);
						}

					} else {
						deliveryManifestBooking
						.append("Consignment not Inscanned in this branch. Inscanned in "
								+ inScanDetails.getRecvBranch()
								.getOfficeCode() + " branch");
						return deliveryManifestBooking.toString();

					}
				}// if inScanDetails

				// pincode Validations
				destPincode = bookingDO.getDestPinCode();
				if (destPincode != null) {
					String pincodeValidation = validatePinCode(
							destPincode.getPincode(), preOffId);
					if (pincodeValidation.contains(CommonConstants.ERROR)) {

						return deliveryManifestBooking.toString();
					}
				}

				deliveryManifestBooking = DeliveryManifestMDBValidator
				.validateConsignment(bookingDO, consigneeAddress,
						consgNumber, duplicateCongntSameDay,
						ctbsApplicationMDBDAO);

			}

		} catch (CGBusinessException e) {
			logger.error("DeliveryManifestMDBServiceImpl::getBookingDetails::Exception occured:"
					+e.getMessage());
		}
		return deliveryManifestBooking.toString();
	}

	/**
	 * Find by drs number.
	 *
	 * @param drsNum the drs num
	 * @return List<DeliveryManifestTO>
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public List<DeliveryManifestTO> findByDRSNumber(String drsNum)
	throws CGBusinessException, CGSystemException {
		List<DeliveryManifestTO> deliveryManifestListTO = new ArrayList<DeliveryManifestTO>();
		List<DeliveryDO> deliveryManifestListDO = new ArrayList<DeliveryDO>();
		try {
			deliveryManifestListDO = deliveryManifestMDBDAO
			.getBrDeliveryManifestDetails(drsNum);
			// Converting DO to To
			if (deliveryManifestListDO != null) {
				deliveryManifestListTO = DeliveryManifestTOToDOMDBConvertor
				.createTOListFromDomainList(deliveryManifestListDO,
						deliveryManifestMDBDAO, ctbsApplicationMDBDAO);
			}
			// }
		} catch (CGBusinessException e) {
			logger.error("DeliveryManifestMDBServiceImpl::findByDRSNumber::Exception occured:"
					+e.getMessage());
		}
		return deliveryManifestListTO;
	}

	/**
	 * Gets the duplicate consignment.
	 *
	 * @param consgNum the consg num
	 * @param status the status
	 * @return Integer
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public long getDuplicateConsignment(String consgNum, String status)
	throws CGBusinessException {
		long count = 0;
		try {
			count = deliveryManifestMDBDAO.getDuplicateConsignment(consgNum,
					status);
		} catch (CGBusinessException e) {
			logger.error("DeliveryManifestMDBServiceImpl::getDuplicateConsignment::Exception occured:"
					+e.getMessage());
		}
		return count;
	}

	/**
	 * Gets the fdm hand over by manifest.
	 *
	 * @param manifestNum the manifest num
	 * @return String
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public String getfdmHandOverByManifest(String manifestNum)
	throws CGBusinessException, CGSystemException {
		List<FranchiseDeliveryManifestDO> frDeliveryList = null;
		int noOfConsignments = 0;
		Double totalManifestWeight = 0.0;
		String frCode = null;
		StringBuilder fdmHandOver = new StringBuilder();
		String fdmHOOnFdm = "";
		try {

			frDeliveryList = deliveryManifestMDBDAO
			.getFrDeliveryManifestDetails(manifestNum);
			if (frDeliveryList != null && frDeliveryList.size() > 0) {
				fdmHOOnFdm = deliveryManifestMDBDAO.isAlreadyHO(manifestNum);
				if (StringUtils.isNotEmpty(fdmHOOnFdm)) {
					fdmHandOver.append(DeliveryManifestConstants.ERROR);
					fdmHandOver.append(CommonConstants.COMMA);

				} else {
					noOfConsignments = frDeliveryList.size();
					for (FranchiseDeliveryManifestDO frDelivery : frDeliveryList) {
						totalManifestWeight = totalManifestWeight
						+ frDelivery.getConsgWeight();
						if (StringUtils.isEmpty(frCode)) {
							frCode = frDelivery.getFranchisee()
							.getFranchiseeCode();
						}
					}
					fdmHandOver.append(DeliveryManifestConstants.VALID);
					fdmHandOver.append(CommonConstants.COMMA);
					fdmHandOver.append(manifestNum);
					fdmHandOver.append(CommonConstants.COMMA);
					fdmHandOver.append(totalManifestWeight);
					fdmHandOver.append(CommonConstants.COMMA);
					fdmHandOver.append(noOfConsignments);
					fdmHandOver.append(CommonConstants.COMMA);
					fdmHandOver.append(frCode);
				}
			} else {
				fdmHandOver.append(DeliveryManifestConstants.ERROR);
				fdmHandOver.append(CommonConstants.COMMA);

			}

		} catch (Exception e) {
			logger.error("DeliveryManifestMDBServiceImpl::getfdmHandOverByManifest::Exception occured:"
					+e.getMessage());
		}
		return fdmHandOver.toString();
	}

	/**
	 * Gets the employees by branch code.
	 *
	 * @param branchId the branch id
	 * @return OfficeDO
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public DeliveryManifestOfficeTO getEmployeesByBranchCode(int branchId)
	throws CGBusinessException {
		DeliveryManifestOfficeDO officeDO = null;
		DeliveryManifestOfficeTO deliveryOfficeTO = new DeliveryManifestOfficeTO();
		try {
			officeDO = deliveryManifestMDBDAO
			.getEmployeesByBranchCode(branchId);
			if (officeDO != null) {
				deliveryOfficeTO.setOfficeId(officeDO.getOfficeId());
				deliveryOfficeTO.setOfficeCode(officeDO.getOfficeCode());
				deliveryOfficeTO.setOfficeName(officeDO.getOfficeName());
			}
		} catch (CGBusinessException e) {
			logger.error("DeliveryManifestMDBServiceImpl::getEmployeesByBranchCode::Exception occured:"
					+e.getMessage());
		}
		return deliveryOfficeTO;
	}

	/**
	 * Gets the employees by branch code.
	 *
	 * @param branchcode the branchcode
	 * @return OfficeDO
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public DeliveryManifestOfficeTO getEmployeesByBranchCode(String branchcode)
	throws CGBusinessException {
		DeliveryManifestOfficeDO officeDO = null;
		DeliveryManifestOfficeTO deliveryOfficeTO = new DeliveryManifestOfficeTO();
		try {
			officeDO = deliveryManifestMDBDAO
			.getEmployeesByBranchCode(branchcode);
			if (officeDO != null) {
				deliveryOfficeTO.setOfficeId(officeDO.getOfficeId());
				deliveryOfficeTO.setOfficeCode(officeDO.getOfficeCode());
				deliveryOfficeTO.setOfficeName(officeDO.getOfficeName());
				deliveryOfficeTO.setPhoneNumber(officeDO.getPhoneNumber());
			}
		} catch (CGBusinessException e) {
			logger.error("DeliveryManifestMDBServiceImpl::getEmployeesByBranchCode::Exception occured:"
					+e.getMessage());
		}
		return deliveryOfficeTO;
	}

	/**
	 * Gets the franchisee and reporting branch.
	 *
	 * @param frCode the fr code
	 * @return String
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public DeliveryManifestFranchiseeTO getFranchiseeAndReportingBranch(
			String frCode) throws CGBusinessException {
		DeliveryManifestFranchiseeTO franchisseTO = new DeliveryManifestFranchiseeTO();
		DeliveryManifestFranchiseeDO franchisseDO = null;
		DeliveryManifestOfficeDO officeDO = null;
		try {
			franchisseDO = deliveryManifestMDBDAO
			.getFranchiseeAndReportingBranch(frCode);
			if (franchisseDO != null) {
				franchisseTO.setFranchiseeId(franchisseDO.getFranchiseeId());
				franchisseTO
				.setFranchiseeCode(franchisseDO.getFranchiseeCode());
				franchisseTO
				.setFranchiseeName(franchisseDO.getFranchiseeName());
				officeDO = franchisseDO.getReportingBranch();
				if (officeDO != null) {
					DeliveryManifestOfficeTO officeTO = new DeliveryManifestOfficeTO();
					officeTO.setOfficeId(officeDO.getOfficeId());
					officeTO.setOfficeCode(officeDO.getOfficeCode());
					officeTO.setOfficeName(officeDO.getOfficeName());
					franchisseTO.setReportingBranch(officeTO);
				}
				franchisseTO.setRegMobile(franchisseDO.getRegMobile());
			}
		} catch (CGBusinessException e) {
			logger.error("DeliveryManifestMDBServiceImpl::getFranchiseeAndReportingBranch::Exception occured:"
					+e.getMessage());
		}
		return franchisseTO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#getEmployee(String, String)
	 */
	@Override
	public String getEmployee(String empCode, String branchCode)
	throws CGBusinessException {
		String employee = "";
		try {
			employee = deliveryManifestMDBDAO.getEmployee(empCode, branchCode);
		} catch (CGBusinessException e) {
			logger.error("DeliveryManifestMDBServiceImpl::getEmployee::Exception occured:"
					+e.getMessage());
		}
		return employee;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#findByFDMNumber(String)
	 */
	@Override
	public List<FranchiseeDeliveryManifestTO> findByFDMNumber(String fdmNum)
	throws CGSystemException, CGBusinessException {
		List<FranchiseeDeliveryManifestTO> frDeliveryTO = new ArrayList<FranchiseeDeliveryManifestTO>();
		List<FranchiseDeliveryManifestDO> frDeliveryDO = new ArrayList<FranchiseDeliveryManifestDO>();
		try {
			frDeliveryDO = deliveryManifestMDBDAO.findByFDMNumber(fdmNum);
			if (frDeliveryDO != null) {
				if (frDeliveryDO.size() > 0) {
					// Converting DO to To
					frDeliveryTO = DeliveryManifestTOToDOMDBConvertor
					.createFrDeliveryTOFromDomain(frDeliveryDO,
							deliveryManifestMDBDAO,
							ctbsApplicationMDBDAO);
				}
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBServiceImpl::findByFDMNumber::Exception occured:"
					+e.getMessage());

		}
		return frDeliveryTO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#getReasons()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ReasonTO> getReasons() {
		List<ReasonTO> reasonTO = new ArrayList<ReasonTO>();
		List<ReasonDO> reasonDO = new ArrayList<ReasonDO>();
		try {
			reasonDO = ctbsApplicationMDBDAO.getReasons();
			if (reasonDO != null) {
				if (reasonDO.size() > 0) {
					// Converting DO to To
					reasonTO = (List<ReasonTO>) CGObjectConverter
					.createTOListFromDomainList(reasonDO,
							ReasonTO.class);
				}
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBServiceImpl::getReasons::Exception occured:"
					+e.getMessage());

		}
		return reasonTO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#getEmployeesByBranchID(int)
	 */
	@Override
	public List<DeliveryManifestEmployeesTO> getEmployeesByBranchID(int branchID) {
		List<DeliveryManifestEmployeesTO> employeeTOs = new ArrayList<DeliveryManifestEmployeesTO>();
		List<EmployeeDO> employeeList = new ArrayList<EmployeeDO>();
		try {
			employeeList = deliveryManifestMDBDAO
			.getEmployeesByBranchID(branchID);
			if (employeeList != null) {
				if (employeeList.size() > 0) {
					// Converting DO to To
					for (EmployeeDO employee : employeeList) {
						DeliveryManifestEmployeesTO employeeTO = new DeliveryManifestEmployeesTO();
						employeeTO.setEmployeeId(employee.getEmployeeId());
						employeeTO.setEmpCode(employee.getEmpCode());
						employeeTO.setFirstName(employee.getFirstName());
						employeeTOs.add(employeeTO);
					}
				}
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBServiceImpl::getEmployeesByBranchID::Exception occured:"
					+e.getMessage());

		}
		return employeeTOs;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#fdmToBdmConvertion(List, FranchiseeDeliveryManifestTO)
	 */
	@Override
	public String fdmToBdmConvertion(List<Integer> deliveryIDs,
			FranchiseeDeliveryManifestTO frDeliveryTO)
	throws CGSystemException, CGBusinessException {
		Double totalWeight = 0.0;
		int totalPieces = 0;
		String bdmDetails = null;
		List<DeliveryDO> brDeliveryList = new ArrayList<DeliveryDO>();
		// List<DeliveryManifestHistoryDO> logDeliveryManifestList = new
		// ArrayList<DeliveryManifestHistoryDO>();
		// List<DeliveryDO> brDeliveryManifestHistoryList = new
		// ArrayList<DeliveryDO>();
		try {
			// calculating total weight and pieces
			if (frDeliveryTO.getSlNo().length > 0) {
				for (int i = 0; i < frDeliveryTO.getSlNo().length; i++) {
					totalWeight = totalWeight + frDeliveryTO.getWeight()[i];
					totalPieces = totalPieces + frDeliveryTO.getNoOfPieces()[i];
				}
			}

			// getting frLogDetails
			/*
			 * if (deliveryIDs != null) { if (deliveryIDs.size() > 0) {
			 * brDeliveryManifestHistoryList = deliveryManifestMDBDAO
			 * .getBrDeliveryManifestByIDs(deliveryIDs); } if
			 * (brDeliveryManifestHistoryList != null) { if
			 * (brDeliveryManifestHistoryList.size() > 0) { for (DeliveryDO
			 * brDeliveryLog : brDeliveryManifestHistoryList) {
			 * logDeliveryManifestList .add(DeliveryManifestTOToDOConvertor
			 * .converterFrDeliveryDOFrHistoryDO( brDeliveryLog,
			 * DeliveryManifestConstants.FDM_DELIVERY_CODE)); } } } }
			 */

			brDeliveryList = DeliveryManifestTOToDOMDBConvertor
			.fdmToBdmConvertor(frDeliveryTO, deliveryIDs,
					deliveryManifestMDBDAO);
			bdmDetails = deliveryManifestMDBDAO.fdmToBdmConversion(deliveryIDs,
					brDeliveryList, totalWeight, totalPieces);

		} catch (Exception e) {
			logger.error("DeliveryManifestMDBServiceImpl::fdmToBdmConvertion::Exception occured:"
					+e.getMessage());

		}
		return bdmDetails;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#bdmToFdmConvertion(List, DeliveryManifestTO)
	 */
	@Override
	public String bdmToFdmConvertion(List<Integer> deliveryIDs,
			DeliveryManifestTO brDeliveryTO) throws CGSystemException,
			CGBusinessException {
		Double totalWeight = 0.0;
		int totalPieces = 0;
		String bdmDetails = null;
		List<FranchiseDeliveryManifestDO> frDeliveryList = new ArrayList<FranchiseDeliveryManifestDO>();
		String frCode = "";
		try {
			// calculating total weight and pieces
			if (brDeliveryTO.getSlNo().length > 0) {
				for (int i = 0; i < brDeliveryTO.getSlNo().length; i++) {
					totalWeight = totalWeight + brDeliveryTO.getWeight()[i];
					totalPieces = totalPieces + brDeliveryTO.getNoOfPieces()[i];
				}
			}
			frCode = brDeliveryTO.getFrCode();
			if (!(StringUtils.isEmpty(frCode))) {
				String[] frList = frCode
				.split(DeliveryManifestConstants.HIPHON);
				frCode = frList[0].trim();
				FranchiseeDO franchisee = ctbsApplicationMDBDAO
				.getFranchiseesByCode(frCode);
				brDeliveryTO.setFranchiseeID(franchisee.getFranchiseeId());
			}
			// getting FDM
			frDeliveryList = DeliveryManifestTOToDOMDBConvertor
			.bdmToFdmConvertor(brDeliveryTO, deliveryIDs,
					deliveryManifestMDBDAO);
			bdmDetails = deliveryManifestMDBDAO.bdmToFdmConversion(deliveryIDs,
					frDeliveryList, totalWeight, totalPieces);

		} catch (Exception e) {
			logger.error("DeliveryManifestMDBServiceImpl::bdmToFdmConvertion::Exception occured:"
					+e.getMessage());

		}
		return bdmDetails;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#checkFranchiseeCapacity(String, String)
	 */
	@Override
	public boolean checkFranchiseeCapacity(String frCode, String fdmDate)
	throws CGSystemException, CGBusinessException {
		FranchiseeDO franchisee = null;
		int frDeliveryCount = 0;
		int frId = 0;
		int frCapacity = 0;
		boolean isLimitOver = Boolean.FALSE;
		try {
			franchisee = ctbsApplicationMDBDAO.getFranchiseesByCode(frCode);
			if (franchisee != null) {
				frCapacity = franchisee.getDeliveryDayLimit();
				frId = franchisee.getFranchiseeId();
				frDeliveryCount = deliveryManifestMDBDAO.getFrDliveryDayCount(
						frId, DateFormatterUtil
						.slashDelimitedstringToDDMMYYYYFormat(fdmDate));
				if (frDeliveryCount >= frCapacity) {
					isLimitOver = Boolean.TRUE;
				}
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBServiceImpl::checkFranchiseeCapacity::Exception occured:"
					+e.getMessage());

		}
		return isLimitOver;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#validatePinCode(String, int)
	 */
	@Override
	public String validatePinCode(String consgPinCode, int officeID)
	throws CGSystemException, CGBusinessException {
		// TODO Auto-generated method stub
		PincodeDO pincodeDO = null;
		StringBuffer message = new StringBuffer();
		AreaDO area = null;
		int pincodeID = 0;
		// Integer regOffId = 0;
		// OfficeDO regOffice = null;
		OfficeDO officeOnPincode = null;
		// Integer regOffIdOnPinBr = 0;

		try {

			/*
			 * pincodeDO = deliveryManifestMDBDAO.validatePinCode(consgpinCode,
			 * officeID);
			 */
			pincodeDO = ctbsApplicationMDBDAO.getPinCodeByIdOrCode(-1,
					consgPinCode);
			// Getting regOffId
			// regOffice = ctbsApplicationMDBDAO.getROByBranchOffice(officeID);
			// if (regOffice != null) {
			// regOffId = regOffice.getOfficeId();
			// }
			if (pincodeDO == null) {
				message.append(CommonConstants.ERROR);
				message.append(CommonConstants.COMMA);
				message.append(DeliveryManifestConstants.INVALID_PINCODE);
				return message.toString();
			} else {
				officeOnPincode = pincodeDO.getOffice();
				if (officeOnPincode != null) {
					// regOffIdOnPinBr = officeOnPincode.getReginolOfficeId();
					if (officeID != officeOnPincode.getOfficeId().intValue()) {
						message.append(CommonConstants.ERROR);

						return message.toString();
					}
				}
				message.append(CommonConstants.VALID_MESSAGE);
				message.append(CommonConstants.COMMA);
				message.append(pincodeDO.getServiceable());
				if (pincodeDO.getServiceable().equals(
						CommonConstants.PINCODE_SERVICEABLE)) {
					pincodeID = pincodeDO.getPincodeId();
					area = deliveryManifestMDBDAO.getPincodeArea(pincodeID);
					if (area != null) {
						message.append(CommonConstants.COMMA);
						message.append(area.getServiceable());
						if (area.getAreaType().equals(
								CommonConstants.REMOTE_SERVICEABLE)
								|| area.getAreaType().equals(
										CommonConstants.DIPLOMATIC_SERVICEABLE)) {
							message.append(CommonConstants.COMMA);
							message.append(area.getAreaType());
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error("DeliveryManifestMDBServiceImpl::validatePinCode::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}

		return message.toString();
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#validateFDMPinCode(String, int, int)
	 */
	@Override
	public String validateFDMPinCode(String consgpinCode, int frId, int brId)
	throws CGSystemException, CGBusinessException {
		PincodeDO pincodeDO = null;
		StringBuffer message = new StringBuffer();
		AreaDO area = null;
		int pincodeID = 0;
		boolean isValid = Boolean.TRUE;
		try {
			pincodeDO = ctbsApplicationMDBDAO.getPinCodeByIdOrCode(pincodeID,
					consgpinCode);
			if (pincodeDO == null) {
				message.append(CommonConstants.ERROR);
				message.append(CommonConstants.COMMA);
				message.append(DeliveryManifestConstants.INVALID_PINCODE);
			} else {
				message.append(CommonConstants.VALID_MESSAGE);
				if (pincodeDO.getServiceable().equals(
						CommonConstants.PINCODE_SERVICEABLE)) {
					message.append(CommonConstants.COMMA);
					message.append(DeliveryManifestConstants.SERVICEABLE);
					pincodeID = pincodeDO.getPincodeId();
					isValid = deliveryManifestMDBDAO.validateAgnstFranchisee(
							frId, pincodeID);
					if (!isValid) {
						message.append(CommonConstants.COMMA);
						message.append(DeliveryManifestConstants.INVALID);
						message.append(CommonConstants.COMMA);
						message.append(DeliveryManifestConstants.PINCODE_INFO);
					}
					area = deliveryManifestMDBDAO.getPincodeArea(pincodeID);
					if (isValid) {
						if (area != null) {
							message.append(CommonConstants.COMMA);
							message.append(area.getServiceable());

							if (area.getAreaType().equals(
									CommonConstants.REMOTE_SERVICEABLE)
									|| area.getAreaType()
									.equals(CommonConstants.DIPLOMATIC_SERVICEABLE)) {
								message.append(CommonConstants.COMMA);
								message.append(area.getAreaType());
							}
						}
					}

				} else {
					if (pincodeDO.getServiceable().equals(
							DeliveryManifestConstants.NO)) {
						message.append(CommonConstants.COMMA);
						message.append(DeliveryManifestConstants.NONSERVICEABLE);
					}
				}
			}

		} catch (Exception e) {
			logger.error("DeliveryManifestMDBServiceImpl::validateFDMPinCode::Exception occured:"
					+e.getMessage());
		}

		return message.toString();
	}

	/**
	 * Gets the misc expense do from to.
	 *
	 * @param miscExpenseTO the misc expense to
	 * @return the misc expense do from to
	 */
	private MiscExpenseDO getMiscExpenseDoFromTo(CnMiscExpenseTO miscExpenseTO) {
		MiscExpenseDO miscExpenseDO = new MiscExpenseDO();
		miscExpenseDO.setConsignmentNumber("");
		miscExpenseDO.setExpenditureId(miscExpenseTO.getExpndTypeId());
		ExpenditureTypeDO expType = new ExpenditureTypeDO();
		expType.setExpndTypeId(miscExpenseTO.getExpenditureType());
		miscExpenseDO.setExpenditureType(expType);

		miscExpenseDO
		.setExpenditureAmount(miscExpenseTO.getExpenditureAmount());
		EmployeeDO auth = new EmployeeDO();
		auth.setEmployeeId(miscExpenseTO.getEmpId());
		miscExpenseDO.setAuthorizer(auth);
		miscExpenseDO.setRemark(miscExpenseTO.getRemark());
		miscExpenseDO.setExpenditureDate(DateFormatterUtil
				.getDateFromStringDDMMYYY(miscExpenseTO.getExpenditureDate()));
		miscExpenseDO.setVoucherNumber(miscExpenseTO.getVoucherNumber());
		miscExpenseDO.setVoucherDate(DateFormatterUtil
				.getDateFromStringDDMMYYY(miscExpenseTO.getVoucherDate()));
		miscExpenseDO.setConsignmentNumber(miscExpenseTO.getCnNumber());
		miscExpenseDO.setApproved("Y");
		EmployeeDO enteredBy = new EmployeeDO();
		enteredBy.setEmployeeId(Integer.parseInt(miscExpenseTO.getEnteredBy()));
		miscExpenseDO.setEnterebBy(enteredBy);
		return miscExpenseDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#findByFrHONumber(String)
	 */
	@Override
	public List<FranchiseeDeliveryHandoverTO> findByFrHONumber(String frHONum)
	throws CGSystemException, CGBusinessException {
		List<FranchiseeDeliveryHandoverTO> frHOTO = new ArrayList<FranchiseeDeliveryHandoverTO>();
		List<FranchiseDeliveryManifestDO> frHODO = null;
		try {
			frHODO = deliveryManifestMDBDAO.findByFrHOumber(frHONum);
			if (frHODO != null && frHODO.size() > 0) {
				// Converting DO to To
				frHOTO = DeliveryManifestTOToDOMDBConvertor
				.createFrHOTOFromDomain(frHODO);
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBServiceImpl::findByFrHONumber::Exception occured:"
					+e.getMessage());

		}
		return frHOTO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#isPaperWorkManifested(String, Integer)
	 */
	@Override
	public boolean isPaperWorkManifested(String consgNum, Integer mnfstTypeId)
	throws CGSystemException, CGBusinessException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryManifestMDBService#getPhonesByID(Integer, String)
	 */
	@Override
	public List<HandHeldDeviceTO> getPhonesByID(Integer branchId,
			String branchCode) throws CGSystemException, CGBusinessException {

		List<HandHeldDeviceDO> handHeldDeviceDO = null;
		List<HandHeldDeviceTO> handHeldDeviceTO = new ArrayList<HandHeldDeviceTO>();
		HandHeldDeviceTO handHeldDeviceTOsingle = null;
		try {
			handHeldDeviceTOsingle = new HandHeldDeviceTO();
			handHeldDeviceDO = deliveryManifestMDBDAO.getPhonesByID(branchId,
					branchCode);
			/*
			 * handHeldDeviceTOsingle = (HandHeldDeviceTO)
			 * CGObjectConverter.createToFromDomain( handHeldDeviceDO,
			 * handHeldDeviceTO);
			 */
			if (handHeldDeviceDO != null && handHeldDeviceDO.size() > 0) {
				for (int i = 0; i < handHeldDeviceDO.size(); i++) {
					handHeldDeviceTOsingle.setPhoneNumber(handHeldDeviceDO.get(
							i).getPhoneNumber() != null ? handHeldDeviceDO.get(
									i).getPhoneNumber() : "");
					handHeldDeviceTOsingle
					.setReceiverOfficeType(handHeldDeviceDO.get(i)
							.getReceiverOfficeType() != null ? handHeldDeviceDO
									.get(i).getReceiverOfficeType() : "");
					handHeldDeviceTOsingle
					.setHandHeldDeviceId(handHeldDeviceDO.get(i)
							.getHandHeldDeviceId() != null ? handHeldDeviceDO
									.get(i).getHandHeldDeviceId() : 0);
					handHeldDeviceTO.add(handHeldDeviceTOsingle);
				}
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBServiceImpl::getPhonesByID::Exception occured:"
					+e.getMessage());
		}
		return handHeldDeviceTO;
	}

}
