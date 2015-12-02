/**
 * Author: Narasimha 
 * OutgoingManifestServiceImpl
 */
package src.com.dtdc.mdbServices.manifest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import src.com.dtdc.constants.CommonConstants;
import src.com.dtdc.constants.ManifestConstant;
import src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO;
import src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO;
import src.com.dtdc.mdbServices.CTBSApplicationMDBDAO;

import com.capgemini.lbs.framework.constants.BookingConstants;
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseDO.CGBaseEntity;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.EmailUtility;
import com.dtdc.domain.booking.BookingLogDO;
import com.dtdc.domain.booking.ProductDO;
import com.dtdc.domain.booking.cashbooking.CashBookingDO;
import com.dtdc.domain.booking.dpbooking.DirectPartyBookingDO;
import com.dtdc.domain.booking.frbooking.FranchiseeBookingDO;
import com.dtdc.domain.expense.ExpenditureTypeDO;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;
import com.dtdc.domain.master.StdHandlingInstDO;
import com.dtdc.domain.master.document.DocumentDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.geography.CityDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.product.ServiceDO;
import com.dtdc.domain.opmaster.shipment.ModeDO;
import com.dtdc.domain.transaction.delivery.DeliveryManifestBookingDO;
import com.dtdc.to.common.OfficeTO;
import com.dtdc.to.expense.CnMiscExpenseTO;
import com.dtdc.to.manifest.BagManifestDoxTO;
import com.dtdc.to.manifest.PacketManifestDoxTO;

// TODO: Auto-generated Javadoc
/**
 * The Class OutgoingManifestMDBServiceImpl.
 */
public class OutgoingManifestMDBServiceImpl implements
OutgoingManifestMDBService {

	/** logger. */
	private final static Logger LOGGER = Logger
	.getLogger(OutgoingManifestMDBServiceImpl.class);
	/** The outgoing manifest dao. */
	private OutgoingManifestMDBDAO outgoingManifestMDBDAO = null;

	/** The ctbs application mdbdao. */
	private CTBSApplicationMDBDAO ctbsApplicationMDBDAO = null;

	/** The delivery manifest mdbdao. */
	private DeliveryManifestMDBDAO deliveryManifestMDBDAO = null;

	/**
	 * Gets the outgoing manifest mdbdao.
	 *
	 * @return the outgoing manifest mdbdao
	 */
	public OutgoingManifestMDBDAO getOutgoingManifestMDBDAO() {
		return outgoingManifestMDBDAO;
	}

	/**
	 * Sets the outgoing manifest mdbdao.
	 *
	 * @param outgoingManifestMDBDAO the new outgoing manifest mdbdao
	 */
	public void setOutgoingManifestMDBDAO(
			OutgoingManifestMDBDAO outgoingManifestMDBDAO) {
		this.outgoingManifestMDBDAO = outgoingManifestMDBDAO;
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
	 * Insert Or Update Packet, Re-Route, MNP and Bag-NonDox for DB Sync.
	 *
	 * @param manifestTOs the manifest t os
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public String saveOrUpdatePktBagNDoxMnpMisRouteManifestDBSync(
			List<PacketManifestDoxTO> manifestTOs) throws CGSystemException {
		LOGGER.trace("*********OutgoingManifestMDBServiceImpl::saveOrUpdatePktBagNDoxMnpMisRouteManifestDBSync*******: Start"
				+ System.currentTimeMillis());
		String manifetsDetails = "";
		List<ManifestDO> manifestDOList = null;
		StringBuffer manifestDetailsBuff = new StringBuffer();
		try {
			/*
			 * List<MiscExpenseDO> miscExpenseDOList = new
			 * ArrayList<MiscExpenseDO>();
			 * 
			 * if (packetManifestDoxTO.getMiscExpenseTOList() != null &&
			 * !packetManifestDoxTO.getMiscExpenseTOList().isEmpty()) {
			 * List<CnMiscExpenseTO> miscExpenseToList = packetManifestDoxTO
			 * .getMiscExpenseTOList(); if (miscExpenseToList != null &&
			 * !miscExpenseToList.isEmpty()) { for (CnMiscExpenseTO
			 * miscExpenseTo : miscExpenseToList) { MiscExpenseDO miscExpenseDo
			 * = getMiscExpenseDoFromTo(miscExpenseTo); //
			 * miscExpenseDo.setProcessName("Manifesting");
			 * miscExpenseDOList.add(miscExpenseDo); } } if (miscExpenseDOList
			 * != null && !miscExpenseDOList.isEmpty()) message =
			 * outgoingManifestMDBDAO .saveMiscExpForRelease(miscExpenseDOList);
			 * }
			 */
			LOGGER.trace("*********OutgoingManifestMDBServiceImpl::saveOrUpdatePktBagNDoxMnpMisRouteManifestDBSync*******:Converter: Start"
					+ System.currentTimeMillis());
			manifestDOList = convertorPktMnpBagNDoxMisRouteTOtoDODBSync(manifestTOs);
			LOGGER.trace("*********OutgoingManifestMDBServiceImpl::saveOrUpdatePktBagNDoxMnpMisRouteManifestDBSync*******:Converter: End"
					+ System.currentTimeMillis());
			LOGGER.trace("*********OutgoingManifestMDBServiceImpl::saveOrUpdatePktBagNDoxMnpMisRouteManifestDBSync*******: Start:Save"
					+ System.currentTimeMillis());
			manifetsDetails = outgoingManifestMDBDAO
			.saveOrUpdateManifest(manifestDOList);
			LOGGER.trace("*********OutgoingManifestMDBServiceImpl::saveOrUpdatePktBagNDoxMnpMisRouteManifestDBSync*******: End:Save"
					+ System.currentTimeMillis());
			// Sending email for MNP manifest
			/**
			 * if (StringUtils.equals(manifestCode,
			 * ManifestConstant.MANIFEST_TYPE_MNPBAG)) { if (manifestDOList !=
			 * null && manifestDOList.size() > 0) { ManifestDO mnpManifest =
			 * null; mnpManifest = manifestDOList.get(0); String consgNumber =
			 * mnpManifest.getConsgNumber(); OfficeDO destOffice = null;
			 * destOffice = mnpManifest.getDestBranch(); String email =
			 * destOffice.getEmail(); String subject =
			 * "MNP manifest create notification."; String emailBody =
			 * mnpEmailTemplate(consgNumber, destOffice.getOfficeName());
			 * String[] emails = { email }; EmailUtility.sendEmail(emails, "",
			 * "", subject, emailBody); } }
			 */

		} catch (Exception ex) {
			LOGGER.error("OutgoingManifestMDBServiceImpl::saveOrUpdatePktBagNDoxMnpMisRouteManifestDBSync::Exception occured:"
					+ex.getMessage());
			manifestDetailsBuff.append(ManifestConstant.FAILURE_MSG);

			return manifestDetailsBuff.toString();
		}
		LOGGER.trace("*********OutgoingManifestMDBServiceImpl*******: End");
		return manifetsDetails;
	}

	/**
	 * insert Or Update Packet, Re-Route, MNP and Bag-NonDox for DB Sync.
	 *
	 * @param packetManifestDoxTO the packet manifest dox to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String saveOrUpdatePktBagNDoxMnpMisRouteManifestDBSync(
			CGBaseTO packetManifestDoxTO) throws CGSystemException {
		String message = "";
		LOGGER.trace("*********saveOrUpdatePacketManifest*******: Strat");
		LOGGER.trace("*********saveOrUpdatePacketManifest*******: CGBaseTO:"
				+ packetManifestDoxTO.getBaseList());
		List<PacketManifestDoxTO> manifestTOs = (List<PacketManifestDoxTO>) packetManifestDoxTO
		.getBaseList();
		if (manifestTOs != null && !manifestTOs.isEmpty()) {
			message = saveOrUpdatePktBagNDoxMnpMisRouteManifestDBSync(manifestTOs);
			LOGGER.trace("*********saveOrUpdatePktBagNDoxMnpMisRouteManifestDBSync*******:message:"
					+ message);
		} else {
			LOGGER.error("*********saveOrUpdatePktBagNDoxMnpMisRouteManifestDBSync*******: No records found");
		}
		return message;
	}

	/**
	 * save Or Update Master Bag, Bag Dox Manifest.
	 *
	 * @param bagManifestDoxTO the bag manifest dox to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public String saveOrUpdateMasterBagDoxManifestDBSync(
			List<BagManifestDoxTO> bagManifestDoxTO) throws CGSystemException {
		LOGGER.info("*********OutgoingManifestMDBServiceImpl::saveOrUpdateMasterBagDoxManifestDBSync*******:Start:"
				+ System.currentTimeMillis());
		String manifetsDetails = "";
		List<ManifestDO> manifestDOList = null;
		try {
			manifestDOList = converterMasterBagDoxTOtoDODBSync(bagManifestDoxTO);
			LOGGER.info("*********OutgoingManifestMDBServiceImpl::saveOrUpdateMasterBagDoxManifestDBSync*******:Start:Save"
					+ System.currentTimeMillis());
			manifetsDetails = outgoingManifestMDBDAO
			.saveOrUpdateManifest(manifestDOList);
			LOGGER.info("*********OutgoingManifestMDBServiceImpl::saveOrUpdateMasterBagDoxManifestDBSync*******: End:Save"
					+ System.currentTimeMillis());
		} catch (Exception ex) {
			LOGGER.error("OutgoingManifestMDBServiceImpl::saveOrUpdateMasterBagDoxManifestDBSync::Exception occured:"
					+ex.getMessage());
			new CGBusinessException();
		}

		return manifetsDetails;
	}

	/**
	 * Save Or Update Master Bag, Bag Dox Manifest.
	 *
	 * @param bagManifestDoxTO the bag manifest dox to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String saveOrUpdateMasterBagDoxManifestDBSync(
			CGBaseTO bagManifestDoxTO) throws CGSystemException {
		String message = "";
		LOGGER.trace("*********saveOrUpdateMasterBagDoxManifestDBSync*******: Strat");
		LOGGER.trace("*********saveOrUpdateMasterBagDoxManifestDBSync*******: CGBaseTO:"
				+ bagManifestDoxTO.getBaseList());
		List<BagManifestDoxTO> manifestTOs = (List<BagManifestDoxTO>) bagManifestDoxTO
		.getBaseList();
		if (manifestTOs != null && !manifestTOs.isEmpty()) {
			message = saveOrUpdateMasterBagDoxManifestDBSync(manifestTOs);
			LOGGER.trace("*********saveOrUpdateMasterBagDoxManifestDBSync*******:message:"
					+ message);
		} else {
			LOGGER.trace("*********saveOrUpdateMasterBagDoxManifestDBSync*******: No records found");
		}
		return message;

	}

	/**
	 * Insert Or Update Packet, Re-Route, MNP and Bag-NonDox.
	 *
	 * @param packetManifestDoxTO the packet manifest dox to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public String saveOrUpdatePacketManifest(
			PacketManifestDoxTO packetManifestDoxTO) throws CGSystemException {
		LOGGER.trace("*********OutgoingManifestMDBServiceImpl*******: Start");
		String manifetsDetails = "";
		List<ManifestDO> manifestDOList = null;
		StringBuffer manifestDetailsBuff = new StringBuffer();

		String manifestCode = "";
		try {

			List<MiscExpenseDO> miscExpenseDOList = new ArrayList<MiscExpenseDO>();
			LOGGER.trace("*********OutgoingManifestMDBServiceImpl*******: packetManifestDoxTO:"
					+ packetManifestDoxTO.getConsignmentNo());
			LOGGER.trace("*********OutgoingManifestMDBServiceImpl*******: packetManifestDoxTO:"
					+ packetManifestDoxTO.getManifestNumber());
			LOGGER.trace("*********OutgoingManifestMDBServiceImpl*******: packetManifestDoxTO:"
					+ packetManifestDoxTO.getMiscExpenseTOList().size());
			manifestDOList = convertPacketManifestDoxTOtoManifestDO(packetManifestDoxTO);
			LOGGER.trace("*********OutgoingManifestMDBServiceImpl*******: manifestDOList:"
					+ manifestDOList.size());
			if (packetManifestDoxTO.getMiscExpenseTOList() != null
					&& !packetManifestDoxTO.getMiscExpenseTOList().isEmpty()) {
				List<CnMiscExpenseTO> miscExpenseToList = packetManifestDoxTO
				.getMiscExpenseTOList();
				if (miscExpenseToList != null && !miscExpenseToList.isEmpty()) {
					for (CnMiscExpenseTO miscExpenseTo : miscExpenseToList) {
						MiscExpenseDO miscExpenseDo = getMiscExpenseDoFromTo(miscExpenseTo);
						// miscExpenseDo.setProcessName("Manifesting");
						miscExpenseDOList.add(miscExpenseDo);
					}
				}
				if (miscExpenseDOList != null && !miscExpenseDOList.isEmpty()) {
					outgoingManifestMDBDAO
					.saveMiscExpForRelease(miscExpenseDOList);
				}
			}
			manifetsDetails = outgoingManifestMDBDAO
			.saveOrUpdateManifest(manifestDOList);
			// Sending email for MNP manifest
			manifestCode = packetManifestDoxTO.getManifestCode();
			if (StringUtils.equals(manifestCode,
					ManifestConstant.MANIFEST_TYPE_MNPBAG)) {
				if (manifestDOList != null && manifestDOList.size() > 0) {
					ManifestDO mnpManifest = null;
					mnpManifest = manifestDOList.get(0);
					String consgNumber = mnpManifest.getConsgNumber();
					OfficeDO destOffice = null;
					destOffice = mnpManifest.getDestBranch();
					String email = destOffice.getEmail();
					String subject = "MNP manifest create notification.";
					String emailBody = mnpEmailTemplate(consgNumber,
							destOffice.getOfficeName());
					String[] emails = { email };
					EmailUtility.sendEmail(emails, "", "", subject, emailBody);
				}
			}

		} catch (Exception ex) {
			LOGGER.error("OutgoingManifestMDBServiceImpl::saveOrUpdatePacketManifest::Exception occured:"
					+ex.getMessage());
			manifestDetailsBuff.append(ManifestConstant.FAILURE_MSG);

			return manifestDetailsBuff.toString();
		}
		LOGGER.trace("*********OutgoingManifestMDBServiceImpl*******: End");
		return manifetsDetails;
	}

	/**
	 * insert Or Update Packet, Re-Route, MNP and Bag-NonDox.
	 *
	 * @param packetManifestDoxTO the packet manifest dox to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public String saveOrUpdatePacketManifest(CGBaseTO packetManifestDoxTO)
	throws CGSystemException {
		LOGGER.trace("*********saveOrUpdatePacketManifest*******: Strat");
		LOGGER.trace("*********saveOrUpdatePacketManifest*******: CGBaseTO:"
				+ packetManifestDoxTO.getBaseList());
		PacketManifestDoxTO packetManifestDoxTO_Obj = (PacketManifestDoxTO) packetManifestDoxTO
		.getBaseList().get(0);
		LOGGER.trace("*********saveOrUpdatePacketManifest*******: End");
		String message = saveOrUpdatePacketManifest(packetManifestDoxTO_Obj);
		return message;
		// return saveOrUpdatePacketManifest(packetManifestDoxTO_Obj);
	}

	/**
	 * save Or Update Master Bag, Bag Dox Manifest.
	 *
	 * @param bagManifestDoxTO the bag manifest dox to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public String saveOrUpdateBagManifest(BagManifestDoxTO bagManifestDoxTO)
	throws CGSystemException {
		String manifetsDetails = "";
		List<ManifestDO> manifestDOList = null;
		try {
			manifestDOList = convertBagDoxMasterBagTOtoManifestDO(bagManifestDoxTO);
			manifetsDetails = outgoingManifestMDBDAO
			.saveOrUpdateManifest(manifestDOList);
		} catch (Exception ex) {
			LOGGER.error("OutgoingManifestMDBServiceImpl::saveOrUpdateBagManifest::Exception occured:"
					+ex.getMessage());
			new CGBusinessException();
		}

		return manifetsDetails;
	}

	/**
	 * Save Or Update Master Bag, Bag Dox Manifest.
	 *
	 * @param bagManifestDoxTO the bag manifest dox to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public String saveOrUpdateBagManifest(CGBaseTO bagManifestDoxTO)
	throws CGSystemException {
		BagManifestDoxTO bagManifestDoxTO_Obj = (BagManifestDoxTO) bagManifestDoxTO
		.getBaseList().get(0);

		return saveOrUpdateBagManifest(bagManifestDoxTO_Obj);
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.OutgoingManifestMDBService#findByManifestNumber(String, String)
	 */
	public List<PacketManifestDoxTO> findByManifestNumber(
			String manifestNumber, String manifestCode)
			throws CGSystemException, CGBusinessException {
		List<PacketManifestDoxTO> packetManifestTOList = new ArrayList<PacketManifestDoxTO>();
		List<ManifestDO> packetManifestList = new ArrayList<ManifestDO>();

		packetManifestList = outgoingManifestMDBDAO.findByManifestNumber(
				manifestNumber, manifestCode);
		if (packetManifestList != null && packetManifestList.size() > 0) {
			// Converting DO to To
			packetManifestTOList = packetManifestConvertorDOToTO(packetManifestList);
		}

		return packetManifestTOList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.OutgoingManifestMDBService#findByMasterManifestNumber(String, String)
	 */
	public List<BagManifestDoxTO> findByMasterManifestNumber(
			String manifestNumber, String manifestCode)
			throws CGSystemException, CGBusinessException {
		List<BagManifestDoxTO> manifestTOList = new ArrayList<BagManifestDoxTO>();
		List<ManifestDO> manifestList = new ArrayList<ManifestDO>();

		manifestList = outgoingManifestMDBDAO.findByManifestNumber(
				manifestNumber, manifestCode);
		if (manifestList != null && manifestList.size() > 0) {
			// Converting DO to To
			manifestTOList = masterBagDoxManifestConvertorDOToTO(manifestList);
		}

		return manifestTOList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.OutgoingManifestMDBService#getOriginRepOfficeDetails(Integer, String)
	 */
	@Override
	public OfficeTO getOriginRepOfficeDetails(Integer employeeId,
			String employeeCode) throws CGBusinessException, CGSystemException {
		EmployeeDO employee = ctbsApplicationMDBDAO.getEmployeeByCodeOrID(
				employeeId, employeeCode);
		OfficeDO originOffice = employee.getOffice();
		OfficeTO originOfficeTO = new OfficeTO();
		originOfficeTO = (OfficeTO) CGObjectConverter.createToFromDomain(
				originOffice, originOfficeTO);
		/*
		 * originOfficeTO.setGeographyId(originOffice.getGeography()
		 * .getGeographyId());
		 */
		return originOfficeTO;
	}

	/*
	 * @Override public OfficeTO getRegOfficeDetails(Integer regOfficeId) throws
	 * CGBusinessException, CGSystemException { OfficeTO officeTO = new
	 * OfficeTO(); OfficeDO officeDO = null; officeDO =
	 * ctbsApplicationMDBDAO.getOfficesbyOfficeId(regOfficeId); officeTO =
	 * (OfficeTO) CGObjectConverter.createToFromDomain(officeDO, officeTO);
	 * return officeTO; }
	 */

	/**
	 * Convert packet manifest dox t oto manifest do.
	 *
	 * @param packetManifestDoxTO the packet manifest dox to
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private List<ManifestDO> convertPacketManifestDoxTOtoManifestDO(
			PacketManifestDoxTO packetManifestDoxTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("*********OutgoingManifestMDBServiceImpl*******: convertPacketManifestDoxTOtoManifestDO:****Start");
		List<ManifestDO> manifestDOList = new ArrayList<ManifestDO>();
		String pinCode = "";
		String manifestCode = "";
		int prodID = 0;
		int serviceID = 0;
		int docID = 0;
		String updateBookedWeight = "";
		// setting default booking type
		String bookingType = BookingConstants.FRANCHISEE_TYPE;
		Double rateAmount = 0.0;
		DeliveryManifestBookingDO booking = null;
		// Double totalWeight = 0.0;
		// for (int i = 0; i < packetManifestDoxTO.getConsignmentNo().length;
		// i++) {
		// if (!StringUtils.isEmpty(packetManifestDoxTO.getConsignmentNo()[i]))
		// {
		// totalWeight = totalWeight
		// + packetManifestDoxTO.getWeightMFDetails()[i];
		// }
		// }
		LOGGER.trace("*********OutgoingManifestMDBServiceImpl*******: convertPacketManifestDoxTOtoManifestDO:****getConsignmentNo:"
				+ packetManifestDoxTO.getConsignmentNo().length);
		for (int i = 0; i < packetManifestDoxTO.getConsignmentNo().length; i++) {
			if (!StringUtils.isEmpty(packetManifestDoxTO.getConsignmentNo()[i])) {
				LOGGER.trace("*********OutgoingManifestMDBServiceImpl*******: convertPacketManifestDoxTOtoManifestDO:****getConsignmentNo:"
						+ packetManifestDoxTO.getConsignmentNo()[i]);
				ManifestDO packetManifest = new ManifestDO();
				manifestCode = packetManifestDoxTO.getManifestCode();
				// Setting Grid Data
				packetManifest.setConsgNumber(packetManifestDoxTO
						.getConsignmentNo()[i]);
				packetManifest.setIndvWeightKgs(packetManifestDoxTO
						.getWeightMFDetails()[i]);
				packetManifest.setNoOfPieces(packetManifestDoxTO
						.getNoOfPieces()[i]);
				pinCode = packetManifestDoxTO.getDestPincode()[i];
				PincodeDO pinCodeDO = ctbsApplicationMDBDAO
				.getPinCodeByIdOrCode(-1, pinCode);
				CityDO city = pinCodeDO.getOffice().getArea().getCity();
				packetManifest.setDestCity(city);
				packetManifest.setDestPinCode(pinCodeDO);
				if (StringUtils.equals(ManifestConstant.MANIFEST_TYPE_MNPBAG,
						manifestCode)) {
					packetManifest.setMentionPieceRmks(packetManifestDoxTO
							.getMentionPieceRmks());
				} else {
					packetManifest
					.setRemarks(packetManifestDoxTO.getRemarks()[i]);
				}
				// Header Data
				OfficeDO oiginBranch = new OfficeDO();
				OfficeDO repoBranch = new OfficeDO();
				OfficeDO destBranch = new OfficeDO();
				ModeDO mode = new ModeDO();
				DocumentDO document = new DocumentDO();
				prodID = packetManifestDoxTO.getProductIDs().length;
				if (prodID > 0) {
					ProductDO product = new ProductDO();
					product.setProductId(packetManifestDoxTO.getProductIDs()[i]);
					packetManifest.setProduct(product);
				}
				serviceID = packetManifestDoxTO.getServiceIDs().length;
				if (serviceID > 0) {
					ServiceDO service = new ServiceDO();
					service.setServiceId(packetManifestDoxTO.getServiceIDs()[i]);
					packetManifest.setService(service);
				}
				// For document
				docID = packetManifestDoxTO.getDocumentIDs()[i];
				if (docID > 0) {
					document.setDocumentId(docID);
					packetManifest.setDocument(document);
				}

				StdHandlingInstDO stdHandleInst = new StdHandlingInstDO();
				oiginBranch
				.setOfficeId(packetManifestDoxTO.getOriginBranchID());
				repoBranch.setOfficeId(packetManifestDoxTO.getRepBranchID());
				destBranch.setOfficeId(packetManifestDoxTO.getDestBranchID());
				packetManifest.setOriginBranch(oiginBranch);
				packetManifest.setReportingBranch(repoBranch);
				packetManifest.setDestBranch(destBranch);
				mode.setModeId(packetManifestDoxTO.getModeID());
				if (packetManifestDoxTO.getHandlingInstID() != null
						&& packetManifestDoxTO.getHandlingInstID() > 0) {
					stdHandleInst.setHandleInstId(packetManifestDoxTO
							.getHandlingInstID());
					packetManifest.setStdHandleInst(stdHandleInst);
				}
				packetManifest.setMode(mode);
				// set OTHER instructions
				packetManifest.setOtherInstrctions(packetManifestDoxTO
						.getOtherInstrctions());
				packetManifest.setManifestNumber(packetManifestDoxTO
						.getManifestNumber().toUpperCase());
				packetManifest.setTotWeightKgs(packetManifestDoxTO
						.getTotalWeight());
				packetManifest
				.setManifestDate(DateFormatterUtil
						.slashDelimitedstringToDDMMYYYYFormat(packetManifestDoxTO
								.getManifestDate()));
				packetManifest.setManifestTime(packetManifestDoxTO
						.getManifestTime());
				packetManifest
				.setManifestType(ManifestConstant.MANIFEST_TYPE_AGAINST_OUTGOING);
				packetManifest.setStatus(ManifestConstant.MANIFEST_STATUS);
				// Updating Weight / Destination into Booking
				if (StringUtils.equals(ManifestConstant.MANIFEST_TYPE_PACKET,
						manifestCode)
						|| StringUtils.equals(
								ManifestConstant.MANIFEST_TYPE_REROUTE_PACKET,
								manifestCode)
								|| StringUtils.equals(
										ManifestConstant.MANIFEST_TYPE_MNPBAG,
										manifestCode)) {
					updateBookedWeight = packetManifestDoxTO
					.getUpdateBookedWeight();
					booking = deliveryManifestMDBDAO
					.getBookingDetails(packetManifestDoxTO
							.getConsignmentNo()[i]);
					// Calling rate calculations
					String processName = "";
					if (StringUtils
							.equals(ManifestConstant.MANIFEST_TYPE_PACKET,
									manifestCode)) {
						processName = ManifestConstant.MANIFEST_TYPE_PACKET;
					} else if (StringUtils.equals(
							ManifestConstant.MANIFEST_TYPE_REROUTE_PACKET,
							manifestCode)) {
						processName = ManifestConstant.MANIFEST_TYPE_REROUTE_PACKET;
					} else if (StringUtils
							.equals(ManifestConstant.MANIFEST_TYPE_MNPBAG,
									manifestCode)) {
						processName = ManifestConstant.MANIFEST_TYPE_MNPBAG;
					}
					if (StringUtils.equals(updateBookedWeight,
							CommonConstants.UPDATED_BOOKED_WEIGHT)) {
						// TO DO Rate Calculation part
						/*
						 * if (booking != null) { RateCalculationInputTO inputTo
						 * = DeliveryManifestTOToDOConvertor
						 * .populateValuesRateCalc(booking, packetManifestDoxTO
						 * .getWeightMFDetails()[i]); rateCalcService =
						 * rateCalcFactory .getCalculationServiceFor(inputTo
						 * .getTypeTo()); if (rateCalcService != null) {
						 * outputTO = rateCalcService .calculateRate(inputTo); }
						 * if (outputTO != null) { rateAmount =
						 * outputTO.getSlabCharge(); } }
						 */
						// Updating into booking table
						// Inserting into Log Table
						CGBaseEntity bookingDO = null;
						CGBaseEntity bookingEntity = null;
						String entityName = "";
						if (booking != null) {
							bookingType = booking.getBookingType();
						}

						try {
							if (StringUtils.equals(bookingType,
									BookingConstants.FRANCHISEE_TYPE)) {
								entityName = FranchiseeBookingDO.class
								.getName();
							} else if (StringUtils.equals(bookingType,
									BookingConstants.DIRECT_PARTY_TYPE)) {
								entityName = DirectPartyBookingDO.class
								.getName();
							} else if (StringUtils.equals(bookingType,
									BookingConstants.CASH_TYPE)) {
								entityName = CashBookingDO.class.getName();
							}
							if (entityName != null) {
								Class clazz = Class.forName(entityName);
								bookingEntity = (CGBaseEntity) clazz
								.newInstance();
								bookingDO = outgoingManifestMDBDAO
								.getBooking(packetManifestDoxTO
										.getConsignmentNo()[i],
										bookingEntity);
								if (bookingDO != null) {
									BookingLogDO bookingLog = new BookingLogDO();
									PropertyUtils.copyProperties(bookingLog,
											bookingDO);
									outgoingManifestMDBDAO
									.insertBookingLog(bookingLog);
								}
							}
							outgoingManifestMDBDAO
							.updateBookedWeight(packetManifestDoxTO
									.getConsignmentNo()[i],
									packetManifestDoxTO
									.getWeightMFDetails()[i],
									rateAmount, processName);
							// Updating the status in SAP Integration table
							outgoingManifestMDBDAO
							.updateSAPIntgConsgStatus(
									packetManifestDoxTO
									.getConsignmentNo()[i],
									CommonConstants.CONSGN_BILLING_STATUS_SAP_INTG);

						} catch (Exception e) {
							LOGGER.error("OutgoingManifestMDBServiceImpl::convertPacketManifestDoxTOtoManifestDO::Exception occured:"
									+e.getMessage());
							throw new CGSystemException(e);
						}

					}
					// Updating Destination into Booking
					String destPinCode = "";
					String bookDestPinCode = "";
					destPinCode = packetManifestDoxTO.getDestPincode()[i];
					if (booking != null) {
						PincodeDO pinCodeOnBooking = booking.getDestPinCode();
						if (pinCodeOnBooking != null) {
							bookDestPinCode = pinCodeOnBooking.getPincode();
						}
						if (!StringUtils.equals(destPinCode, bookDestPinCode)) {
							// TO DO Rate Calculation part
							/*
							 * if (booking != null) { RateCalculationInputTO
							 * inputTo = DeliveryManifestTOToDOConvertor
							 * .populateValuesRateCalc( booking,
							 * packetManifestDoxTO .getWeightMFDetails()[i]);
							 * rateCalcService = rateCalcFactory
							 * .getCalculationServiceFor(inputTo .getTypeTo());
							 * if (rateCalcService != null) { outputTO =
							 * rateCalcService .calculateRate(inputTo); } if
							 * (outputTO != null) { rateAmount =
							 * outputTO.getSlabCharge(); } }
							 */

							try {

								// Updating Destination into Booking
								deliveryManifestMDBDAO
								.updateDestIntoBooking(destPinCode,
										packetManifestDoxTO
										.getConsignmentNo()[i],
										rateAmount, processName);
							} catch (Exception e) {
								LOGGER.error("OutgoingManifestMDBServiceImpl::convertPacketManifestDoxTOtoManifestDO::Exception occured:"
										+e.getMessage());
								throw new CGSystemException(e);
							}
						}
					}
				}
				// Volumetric Weight implemenration
				if (StringUtils
						.equals(ManifestConstant.MANIFEST_TYPE_BAG_NONDOX,
								manifestCode)
								|| StringUtils.equals(
										ManifestConstant.MANIFEST_TYPE_MNPBAG,
										manifestCode)
										|| StringUtils.equals(
												ManifestConstant.MANIFEST_TYPE_REROUTE_PACKET,
												manifestCode)) {
					if (packetManifestDoxTO.getLengthInCmsList()[i] > 0) {
						packetManifest.setLength(packetManifestDoxTO
								.getLengthInCmsList()[i]);
					}
					if (packetManifestDoxTO.getBreadthInCmsList()[i] > 0) {
						packetManifest.setBreadth(packetManifestDoxTO
								.getBreadthInCmsList()[i]);
					}
					if (packetManifestDoxTO.getHeightInCmsList()[i] > 0) {
						packetManifest.setHeight(packetManifestDoxTO
								.getHeightInCmsList()[i]);
					}
					if (packetManifestDoxTO.getVolumtericWeightList()[i] > 0) {
						packetManifest.setVolumetricWeight(packetManifestDoxTO
								.getVolumtericWeightList()[i]);
					}
				}
				ManifestTypeDO manifestTypeDO = outgoingManifestMDBDAO
				.getManifestType(manifestCode);
				packetManifest.setMnsftTypes(manifestTypeDO);
				packetManifest.setWeighingType(packetManifestDoxTO
						.getWeighingTypes()[i]);
				packetManifest.setDbServer(packetManifestDoxTO.getDbServer());
				if (packetManifestDoxTO.getManifestIDs()[i] != null
						&& packetManifestDoxTO.getManifestIDs()[i] > 0) {
					packetManifest.setManifestId(packetManifestDoxTO
							.getManifestIDs()[i]);
				}
				manifestDOList.add(packetManifest);
			}
		}
		LOGGER.trace("*********OutgoingManifestMDBServiceImpl*******: convertPacketManifestDoxTOtoManifestDO:****manifestDOList"
				+ manifestDOList.size());
		LOGGER.trace("*********OutgoingManifestMDBServiceImpl*******: convertPacketManifestDoxTOtoManifestDO:****End");
		return manifestDOList;
	}

	/**
	 * Convert bag dox master bag t oto manifest do.
	 *
	 * @param bagManifestDoxTO the bag manifest dox to
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	@SuppressWarnings("unused")
	private List<ManifestDO> convertBagDoxMasterBagTOtoManifestDO(
			BagManifestDoxTO bagManifestDoxTO) throws CGBusinessException {

		List<ManifestDO> manifestDOList = new ArrayList<ManifestDO>();
		String pinCode = "";
		List<Integer> manifestIDList = null;
		int manifestIdCount = 0;
		String manifestCode = "";
		int prodId = 0;
		int serviceId = 0;
		int docID = 0;
		DocumentDO document = null;
		ServiceDO service = null;
		try {
			for (int i = 0; i < bagManifestDoxTO.getManifestNo().length; i++) {
				if (!StringUtils.isEmpty(bagManifestDoxTO.getManifestNo()[i])) {
					ManifestDO packetManifest = new ManifestDO();
					// Setting Grid Data
					packetManifest.setConsgNumber(bagManifestDoxTO
							.getManifestNo()[i].toUpperCase());
					packetManifest.setIndvWeightKgs(bagManifestDoxTO
							.getWeightMFDetails()[i]);

					if (!(StringUtils.equals(
							ManifestConstant.MANIFEST_TYPE_MASTER_BAG,
							bagManifestDoxTO.getManifestCode()))) {
						packetManifest.setTotConsgNum(bagManifestDoxTO
								.getTotalCNNo()[i]);
						/*
						 * pinCode = bagManifestDoxTO.getDestPincode()[i];
						 * PincodeDO pinCodeDO = ctbsApplicationMDBDAO
						 * .getPinCodeByIdOrCode(-1, pinCode);
						 * packetManifest.setDestPinCode(pinCodeDO);
						 */
					}
					packetManifest.setRemarks(bagManifestDoxTO.getRemarks()[i]);
					packetManifest.setManifestTypeDefn(bagManifestDoxTO
							.getManifestType()[i]);
					// Header Data
					OfficeDO oiginBranch = new OfficeDO();
					OfficeDO destBranch = new OfficeDO();
					ModeDO mode = new ModeDO();
					prodId = bagManifestDoxTO.getProductIDs().length;
					if (prodId > 0 && bagManifestDoxTO.getProductIDs()[i] > 0) {
						ProductDO product = new ProductDO();
						product.setProductId(bagManifestDoxTO.getProductIDs()[i]);
						packetManifest.setProduct(product);
					}
					serviceId = bagManifestDoxTO.getServiceIDs().length;
					if (serviceId > 0
							&& bagManifestDoxTO.getServiceIDs()[i] > 0) {
						service = new ServiceDO();
						service.setServiceId(bagManifestDoxTO.getServiceIDs()[i]);
						packetManifest.setService(service);
					}

					oiginBranch.setOfficeId(bagManifestDoxTO
							.getOriginBranchId());
					destBranch.setOfficeId(bagManifestDoxTO.getDestBranchId());
					packetManifest.setOriginBranch(oiginBranch);
					packetManifest.setDestBranch(destBranch);
					mode.setModeId(bagManifestDoxTO.getModeID());
					packetManifest.setMode(mode);
					packetManifest.setManifestNumber(bagManifestDoxTO
							.getMasterManifestNumber().toUpperCase());
					packetManifest.setTotWeightKgs(bagManifestDoxTO
							.getTotalWeight());
					packetManifest.setNoOfPacket(bagManifestDoxTO
							.getNoOfPackets());
					packetManifest
					.setManifestDate(DateFormatterUtil
							.slashDelimitedstringToDDMMYYYYFormat(bagManifestDoxTO
									.getManifestDate()));
					packetManifest.setManifestTime(bagManifestDoxTO
							.getManifestTime());
					packetManifest
					.setManifestType(ManifestConstant.MANIFEST_TYPE_AGAINST_OUTGOING);
					packetManifest.setStatus(ManifestConstant.MANIFEST_STATUS);
					if (StringUtils.equals(
							ManifestConstant.MANIFEST_TYPE_BAG_DOX,
							bagManifestDoxTO.getManifestCode())) {
						manifestCode = ManifestConstant.MANIFEST_TYPE_BAG_DOX;
					} else if (StringUtils.equals(
							ManifestConstant.MANIFEST_TYPE_MASTER_BAG,
							bagManifestDoxTO.getManifestCode())) {
						manifestCode = ManifestConstant.MANIFEST_TYPE_MASTER_BAG;
					}
					ManifestTypeDO manifestTypeDO = outgoingManifestMDBDAO
					.getManifestType(manifestCode);
					packetManifest.setMnsftTypes(manifestTypeDO);
					packetManifest.setWeighingType(bagManifestDoxTO
							.getWeighingTypes()[i]);
					packetManifest.setDbServer(bagManifestDoxTO.getDbServer());
					// For document
					docID = bagManifestDoxTO.getDocumentIDs()[i];
					if (docID > 0) {
						document = new DocumentDO();
						document.setDocumentId(docID);
						packetManifest.setDocument(document);
					}
					// For Edit Flow
					if (bagManifestDoxTO.getManifestIDs()[i] != null
							&& bagManifestDoxTO.getManifestIDs()[i] > 0) {
						packetManifest.setManifestId(bagManifestDoxTO
								.getManifestIDs()[i]);
					}
					manifestDOList.add(packetManifest);
				}
			}
		} catch (Exception ex) {
			LOGGER.error("OutgoingManifestMDBServiceImpl::convertBagDoxMasterBagTOtoManifestDO::Exception occured:"
					+ex.getMessage());
			new CGBaseException();
		}
		return manifestDOList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.OutgoingManifestMDBService#getBranchDetailsByBranchCode(String)
	 */
	@Override
	public StringBuilder getBranchDetailsByBranchCode(String branchcode)
	throws CGBusinessException, CGSystemException {

		StringBuilder officeDetails = new StringBuilder();
		OfficeTO officeTO = null;
		OfficeDO officeDO = null;
		Integer officeID = 0;
		String officeCode = "";
		String officeName = "";
		String officeType = "";
		officeDO = ctbsApplicationMDBDAO.getBranchByCodeOrID(-1, branchcode);
		if (officeDO != null) {
			officeTO = new OfficeTO();
			officeTO.setOfficeId(officeDO.getOfficeId());
			officeTO.setOfficeCode(officeDO.getOfficeCode());
			officeTO.setOfficeName(officeDO.getOfficeName());
			officeTO.setOffType(officeDO.getOffType());
		}
		if (officeTO != null) {
			officeID = officeTO.getOfficeId();
			officeCode = officeTO.getOfficeCode();
			officeName = officeTO.getOfficeName();
			officeType = officeTO.getOffType();
			officeDetails.append(officeID);
			officeDetails.append(",");
			officeDetails.append(officeCode);
			officeDetails.append(",");
			officeDetails.append(officeName);
			officeDetails.append(",");
			officeDetails.append(officeType);
		}
		return officeDetails;
	}

	/**
	 * Packet manifest convertor do to to.
	 *
	 * @param packetManifestList the packet manifest list
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	@SuppressWarnings("unused")
	private List<PacketManifestDoxTO> packetManifestConvertorDOToTO(
			List<ManifestDO> packetManifestList) throws CGSystemException {
		List<PacketManifestDoxTO> packetManifestToList = new ArrayList<PacketManifestDoxTO>();
		ProductDO product = null;
		ServiceDO service = null;
		for (ManifestDO packetManifest : packetManifestList) {
			PacketManifestDoxTO packetManifestTO = new PacketManifestDoxTO();
			packetManifestTO.setManifestId(packetManifest.getManifestId());
			packetManifestTO.setManifestNumber(packetManifest
					.getManifestNumber());
			String manifestDate = DateFormatterUtil
			.getDDMMYYYYDateToString(packetManifest.getManifestDate());
			packetManifestTO.setManifestDate(manifestDate);
			packetManifestTO.setManifestTime(packetManifest.getManifestTime());
			packetManifestTO.setConNum(packetManifest.getConsgNumber());
			packetManifestTO.setTotalWeight(packetManifest.getTotWeightKgs());
			packetManifestTO.setConsgRemarks(packetManifest.getRemarks());
			packetManifestTO.setConsgWeight(packetManifest.getIndvWeightKgs());
			packetManifestTO.setOriginBranchID(packetManifest.getOriginBranch()
					.getOfficeId());
			packetManifestTO.setOriginBranchCode(packetManifest
					.getOriginBranch().getOfficeCode());
			packetManifestTO.setOriginBranchName(packetManifest
					.getOriginBranch().getOfficeName());
			packetManifestTO.setRepBranchID(packetManifest.getReportingBranch()
					.getOfficeId());
			packetManifestTO.setRepBranchCode(packetManifest
					.getReportingBranch().getOfficeCode());
			packetManifestTO.setRepBranchName(packetManifest
					.getReportingBranch().getOfficeName());
			packetManifestTO.setRepBranchType(packetManifest
					.getReportingBranch().getOffType());
			packetManifestTO.setDestBranchID(packetManifest.getDestBranch()
					.getOfficeId());
			packetManifestTO.setDestBranchCode(packetManifest.getDestBranch()
					.getOfficeCode());
			packetManifestTO.setDestBranchName(packetManifest.getDestBranch()
					.getOfficeName());
			packetManifestTO.setMentionPieceRmks(packetManifest
					.getMentionPieceRmks());

			// setting dest Region;
			OfficeDO destRegOff = ctbsApplicationMDBDAO
			.getOfficesbyOfficeId(packetManifest.getDestBranch()
					.getReginolOfficeId());
			packetManifestTO.setDestOffRegion(destRegOff.getOfficeName());
			packetManifestTO.setConsgDestCity(packetManifest.getDestCity()
					.getCityName());
			packetManifestTO.setConsgDestPinCode(packetManifest
					.getDestPinCode().getPincode());
			packetManifestTO.setModeID(packetManifest.getMode().getModeId());
			packetManifestTO
			.setModeName(packetManifest.getMode().getModeName());
			packetManifestTO
			.setModeName(packetManifest.getMode().getModeName());
			packetManifestTO.setConsgNoOfPieces(packetManifest.getNoOfPieces());
			packetManifestTO.setConsgStatus(packetManifest.getStatus());
			packetManifestTO.setManifestType(packetManifest.getManifestType());
			StdHandlingInstDO stdHandling = packetManifest.getStdHandleInst();
			if (stdHandling != null) {
				packetManifestTO.setHandlingInstID(stdHandling
						.getHandleInstId());
				packetManifestTO
				.setHandlingInstName(stdHandling.getInstrName());
			}
			packetManifestTO.setManifestCode(packetManifest.getMnsftTypes()
					.getMnfstCode());
			packetManifestTO.setDocumentID(packetManifest.getDocument()
					.getDocumentId());
			packetManifestTO.setDocumentCode(packetManifest.getDocument()
					.getDocumentType());
			product = packetManifest.getProduct();
			if (product != null) {
				packetManifestTO.setProductID(product.getProductId());
				packetManifestTO.setProductName(product.getProductName());
			}
			service = packetManifest.getService();
			if (service != null) {
				packetManifestTO.setServiceID(service.getServiceId());
				packetManifestTO.setServiceName(service.getServiceName());
			}
			// validating PEP/PTP
			String consgNumberSeries = packetManifest.getConsgNumber()
			.substring(0, 1);
			String prodCode = "";
			if (StringUtils.equals(consgNumberSeries,
					ManifestConstant.CONSG_NUMBER_E_SERIES)) {
				prodCode = ManifestConstant.PRODUCT_PTP_TYPE;
			} else if (StringUtils.equals(consgNumberSeries,
					ManifestConstant.CONSG_NUMBER_V_SERIES)) {
				prodCode = ManifestConstant.PRODUCT_PEP_TYPE;
			} else if (StringUtils.equals(consgNumberSeries,
					ManifestConstant.CONSG_NUMBER_N_SERIES)) {
				prodCode = ManifestConstant.PRODUCT_INTL_TYPE;
			} else if (StringUtils.equals(consgNumberSeries,
					ManifestConstant.CONSG_NUMBER_X_SERIES)) {
				prodCode = ManifestConstant.PRODUCT_PRIORITY_TYPE;
			} else {
				prodCode = ManifestConstant.PRODUCT_LITE_TYPE;
			}
			packetManifestTO.setProductCode(prodCode);
			packetManifestTO.setWeighingType(packetManifest.getWeighingType());
			// For Volumetric weight implementation
			if (packetManifest.getVolumetricWeight() != null) {
				packetManifestTO.setVolumetricWeight(packetManifest
						.getVolumetricWeight());
			}
			if (packetManifest.getLength() != null) {
				packetManifestTO.setLengthInCms(packetManifest.getLength());
			}
			if (packetManifest.getBreadth() != null) {
				packetManifestTO.setBreadthInCms(packetManifest.getBreadth());
			}
			if (packetManifest.getHeight() != null) {
				packetManifestTO.setHeightInCms(packetManifest.getHeight());
			}

			packetManifestToList.add(packetManifestTO);

		}
		return packetManifestToList;
	}

	/**
	 * Master bag dox manifest convertor do to to.
	 *
	 * @param manifestList the manifest list
	 * @return the list
	 */
	@SuppressWarnings("unused")
	private List<BagManifestDoxTO> masterBagDoxManifestConvertorDOToTO(
			List<ManifestDO> manifestList) {
		List<BagManifestDoxTO> manifestToList = new ArrayList<BagManifestDoxTO>();
		ProductDO product = null;
		DocumentDO document = null;
		ModeDO mode = null;
		ServiceDO service = null;
		for (ManifestDO manifest : manifestList) {
			BagManifestDoxTO masterBagDoxManifestTO = new BagManifestDoxTO();
			masterBagDoxManifestTO.setManifestId(manifest.getManifestId());
			masterBagDoxManifestTO.setManifestNumber(manifest
					.getManifestNumber());
			String manifestDate = DateFormatterUtil
			.getDDMMYYYYDateToString(manifest.getManifestDate());
			masterBagDoxManifestTO.setManifestDate(manifestDate);
			masterBagDoxManifestTO.setManifestTime(manifest.getManifestTime());
			masterBagDoxManifestTO.setManifestNumber(manifest.getConsgNumber());
			masterBagDoxManifestTO.setMasterManifestNumber(manifest
					.getManifestNumber());
			masterBagDoxManifestTO.setTotalWeight(manifest.getTotWeightKgs());
			masterBagDoxManifestTO.setConsgRemarks(manifest.getRemarks());
			masterBagDoxManifestTO.setConsgWeight(manifest.getIndvWeightKgs());
			if (!(StringUtils.equals(ManifestConstant.MANIFEST_TYPE_MASTER_BAG,
					manifest.getMnsftTypes().getMnfstCode()))) {
				masterBagDoxManifestTO.setConsgTotalCnNum(manifest
						.getTotConsgNum());
				/*
				 * masterBagDoxManifestTO.setConsgDestPinCode(manifest
				 * .getDestPinCode().getPincode());
				 */
			}
			// Header
			masterBagDoxManifestTO.setOriginBranchId(manifest.getOriginBranch()
					.getOfficeId());
			masterBagDoxManifestTO.setOriginBrCode(manifest.getOriginBranch()
					.getOfficeCode());
			masterBagDoxManifestTO.setOriginBrName(manifest.getOriginBranch()
					.getOfficeName());
			masterBagDoxManifestTO.setOriginOfficeType(manifest
					.getOriginBranch().getOffType());
			masterBagDoxManifestTO.setDestBranchId(manifest.getDestBranch()
					.getOfficeId());
			masterBagDoxManifestTO.setDestBranchCode(manifest.getDestBranch()
					.getOfficeCode());
			masterBagDoxManifestTO.setDestBranchTypeName(manifest
					.getDestBranch().getOfficeName());
			masterBagDoxManifestTO.setDestBranchType(manifest.getDestBranch()
					.getOffType());
			masterBagDoxManifestTO.setNoOfPackets(manifest.getNoOfPacket());
			mode = manifest.getMode();
			if (mode != null) {
				masterBagDoxManifestTO.setModeID(mode.getModeId());
				masterBagDoxManifestTO.setModeCode(mode.getModeCode());
				masterBagDoxManifestTO.setModeName(mode.getModeName());
			}
			// Setting Service and Product
			service = manifest.getService();
			if (service != null) {
				masterBagDoxManifestTO.setServiceID(service.getServiceId());
				masterBagDoxManifestTO.setServiceName(service.getServiceName());
			}
			product = manifest.getProduct();
			if (product != null) {
				masterBagDoxManifestTO.setProductID(product.getProductId());
				masterBagDoxManifestTO.setProductName(product.getProductName());
			}

			if (!(StringUtils.equals(ManifestConstant.MANIFEST_TYPE_MASTER_BAG,
					manifest.getMnsftTypes().getMnfstCode()))) {
				// validating PEP/PTP
				String consgNumberSeries = manifest.getConsgNumber().substring(
						0, 1);
				String prodCode = "";
				if (StringUtils.equals(consgNumberSeries,
						ManifestConstant.CONSG_NUMBER_E_SERIES)) {
					prodCode = ManifestConstant.PRODUCT_PTP_TYPE;
				} else if (StringUtils.equals(consgNumberSeries,
						ManifestConstant.CONSG_NUMBER_V_SERIES)) {
					prodCode = ManifestConstant.PRODUCT_PEP_TYPE;
				} else {
					prodCode = ManifestConstant.PRODUCT_LITE_TYPE;
				}
				masterBagDoxManifestTO.setProductCode(prodCode);
			}
			masterBagDoxManifestTO.setManifestStatus(manifest.getStatus());
			masterBagDoxManifestTO.setConsgManifestType(manifest
					.getManifestType());
			masterBagDoxManifestTO.setManifestCode(manifest.getMnsftTypes()
					.getMnfstCode());
			masterBagDoxManifestTO.setWeighingType(manifest.getWeighingType());
			document = manifest.getDocument();
			if (document != null) {
				masterBagDoxManifestTO.setDocumentID(document.getDocumentId());
			}
			manifestToList.add(masterBagDoxManifestTO);
		}
		return manifestToList;
	}

	/*
	 * @Override public DocumentDO getDocumentID(String manifestCode) throws
	 * CGBusinessException { DocumentDO document = null; try { document =
	 * outgoingManifestMDBDAO.getDocumentID(manifestCode); } catch (Exception
	 * ex) { LOGGER.info("Error occured in getDocumentID" + ex.getMessage());
	 * new CGBaseException(); } return document; }
	 */

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.OutgoingManifestMDBService#getManifestByType(String, String, String, Integer, String, String, String)
	 */
	@Override
	public String getManifestByType(String manifestNumber, String manifestType,
			String productCode, Integer modeId, String manifestCode,
			String mnfstTypeOnFirstRec, String editFlag)
	throws CGBusinessException {
		return null;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.OutgoingManifestMDBService#getMasterBag(String)
	 */
	public String getMasterBag(String manifestNumber)
	throws CGBusinessException {
		String isMasterBag = "";
		try {
			outgoingManifestMDBDAO.findByMasterBag(manifestNumber);
		} catch (Exception ex) {
			LOGGER.info("Error occured in getManifestByType" + ex.getMessage());
			new CGBaseException();
		}
		return isMasterBag;
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
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.OutgoingManifestMDBService#pendingManifestCount(Date)
	 */
	public long pendingManifestCount(Date manifestDate)
	throws CGSystemException, CGBusinessException {
		long pendingManifestCount = 0;
		try {
			pendingManifestCount = outgoingManifestMDBDAO
			.pendingManifestCount(manifestDate);
		} catch (Exception e) {
			LOGGER.error("Error occured while validating the pincode in pendingManifestCount().."
					+ e.getMessage());
		}

		return pendingManifestCount;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.OutgoingManifestMDBService#getOrgDestOffIdsByManifest(String, String)
	 */
	public String getOrgDestOffIdsByManifest(String manifestNumber,
			String manifestCode) throws CGSystemException, CGBusinessException {
		String offIds = null;
		try {
			offIds = outgoingManifestMDBDAO.getOrgDestOffIdsByManifest(
					manifestNumber, manifestCode);
		} catch (Exception e) {
			LOGGER.error("Error occured while validating the getOrgDestOffIdsByManifest()..:"
					+ e.getMessage());
		}
		return offIds;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.OutgoingManifestMDBService#getWeightByConsg(String)
	 */
	public Double getWeightByConsg(String consgNumber)
	throws CGSystemException, CGBusinessException {
		Double weightOnBooking = null;
		try {
			weightOnBooking = outgoingManifestMDBDAO
			.getWeightByConsg(consgNumber);
		} catch (Exception e) {
			LOGGER.error("Error occured while validating the getWeightByConsg()..L:"
					+ e.getMessage());
		}
		return weightOnBooking;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.OutgoingManifestMDBService#pendingMNPBookings(Date)
	 */
	@Override
	public long pendingMNPBookings(Date manifestDate) throws CGSystemException,
	CGBusinessException {
		long penMnpBookings = 0;
		try {
			penMnpBookings = outgoingManifestMDBDAO
			.pendingMNPBookings(manifestDate);
		} catch (Exception e) {
			LOGGER.error("Error occured while get the data in  pendingMNPBookings()..:"
					+ e.getMessage());
		}
		return penMnpBookings;
	}

	/**
	 * Mnp email template.
	 *
	 * @param consgNumber the consg number
	 * @param destOffName the dest off name
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 */
	private String mnpEmailTemplate(String consgNumber, String destOffName)
	throws CGBusinessException {
		String messageBody = null;
		StringBuffer messageBodyBuff = new StringBuffer();
		try {
			messageBodyBuff.append("<html><head></head><body>");
			messageBodyBuff.append("<table width='60%'>");
			messageBodyBuff
			.append("<font style=\"font-family:'Courier New', Courier, monospace\" size=\"2\">Dear Team,<br/><br/>");
			messageBodyBuff.append("<tr><td colSpan='5'>");
			messageBodyBuff
			.append("A shipment has been booked as Mentioned Piece vide Consignment number  '"
					+ consgNumber
					+ ",dated on "
					+ DateFormatterUtil.todaySystemDate()
					+ "'to"
					+ destOffName);
			messageBodyBuff.append("<tr><td></br></br></br>");
			messageBodyBuff
			.append("Please find the details of the contents:</td></tr>");
			messageBodyBuff.append("<tr><br><td>");
			messageBodyBuff
			.append("<tr><br>Display the details captured in the Grid<br></br><td>");
			messageBodyBuff.append("</td></tr>");
			messageBodyBuff.append("<tr><td>");
			messageBodyBuff.append("DTDC Support Team. <br><br>");
			messageBodyBuff
			.append("NOTE: This is an Automated Mail, Please DO NOT reply. <br/><br/><br/>");
			messageBodyBuff.append("</td></tr></font></table></body></html>");
			messageBody = messageBodyBuff.toString();
		} catch (Exception e) {
			LOGGER.error("Method mnpEmailTemplate() " + e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.trace("Email Report Body - mnpEmailTemplate: " + messageBody);
		return messageBody;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.OutgoingManifestMDBService#isConsignmentHeldUp(String)
	 */
	public boolean isConsignmentHeldUp(String consgNum)
	throws CGSystemException {
		boolean isHeldUP = false;
		try {
			isHeldUP = deliveryManifestMDBDAO.isConsignmentHeldUp(consgNum);
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			//e#prntstcktrce
			throw new CGSystemException(e);
		}
		return isHeldUP;
	}

	/**
	 * Convertor pkt mnp bag n dox mis route t oto dodb sync.
	 *
	 * @param manifestDoxTOs the manifest dox t os
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private List<ManifestDO> convertorPktMnpBagNDoxMisRouteTOtoDODBSync(
			List<PacketManifestDoxTO> manifestDoxTOs)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("*********OutgoingManifestMDBServiceImpl*******: convertorPktMnpBagNDoxMisRouteTOtoDODBSync:****Start");
		List<ManifestDO> manifestDOList = new ArrayList<ManifestDO>();
		OfficeDO orgOff = null;
		OfficeDO regOff = null;
		OfficeDO destOff = null;
		CityDO destCity = null;
		PincodeDO destPincode = null;
		ModeDO mode = null;
		StdHandlingInstDO stdHandling = null;
		DocumentDO doc = null;
		ProductDO product = null;
		ServiceDO service = null;
		// setting default objects
		if (manifestDoxTOs != null && manifestDoxTOs.size() > 0) {
			// PacketManifestDoxTO mnfstTO = manifestDoxTOs.get(0);
			/*
			 * if (mnfstTO.getOriginBranchID() != null &&
			 * mnfstTO.getOriginBranchID() > 0) { orgOff = new OfficeDO();
			 * orgOff.setOfficeId(mnfstTO.getOriginBranchID()); }
			 * 
			 * if (mnfstTO.getRepBranchID() != null && mnfstTO.getRepBranchID()
			 * > 0) { regOff = new OfficeDO();
			 * regOff.setOfficeId(mnfstTO.getRepBranchID());
			 * 
			 * } if (mnfstTO.getDestBranchID() != null &&
			 * mnfstTO.getDestBranchID() > 0) { destOff = new OfficeDO();
			 * destOff.setOfficeId(mnfstTO.getDestBranchID()); }
			 * 
			 * if (mnfstTO.getDestCityId() != null && mnfstTO.getDestCityId() >
			 * 0) { destCity = new CityDO();
			 * destCity.setCityId(mnfstTO.getDestCityId()); }
			 * 
			 * if (mnfstTO.getDestPinCodeId() != null &&
			 * mnfstTO.getDestPinCodeId() > 0) { destPincode = new PincodeDO();
			 * destPincode.setPincodeId(mnfstTO.getDestPinCodeId()); }
			 * 
			 * if (mnfstTO.getModeID() != null && mnfstTO.getModeID() > 0) {
			 * mode = new ModeDO(); mode.setModeId(mnfstTO.getModeID()); } if
			 * (mnfstTO.getHandlingInstID() != null &&
			 * mnfstTO.getHandlingInstID() > 0) { stdHandling = new
			 * StdHandlingInstDO();
			 * stdHandling.setHandleInstId(mnfstTO.getHandlingInstID());
			 * 
			 * } if (mnfstTO.getDocumentID() != null && mnfstTO.getDocumentID()
			 * > 0) { doc = new DocumentDO();
			 * doc.setDocumentId(mnfstTO.getDocumentID());
			 * 
			 * } if (mnfstTO.getProductID() != null && mnfstTO.getProductID() >
			 * 0) { product = new ProductDO();
			 * product.setProductId(mnfstTO.getProductID());
			 * 
			 * } if (mnfstTO.getServiceID() != null && mnfstTO.getServiceID() >
			 * 0) { service = new ServiceDO();
			 * service.setServiceId(mnfstTO.getServiceID()); }
			 */

			for (PacketManifestDoxTO manifestTO : manifestDoxTOs) {
				ManifestDO manifest = new ManifestDO();
				if (manifestTO.getOriginBranchID() != null
						&& manifestTO.getOriginBranchID() > 0) {
					orgOff = new OfficeDO();
					orgOff.setOfficeId(manifestTO.getOriginBranchID());
				}

				if (manifestTO.getRepBranchID() != null
						&& manifestTO.getRepBranchID() > 0) {
					regOff = new OfficeDO();
					regOff.setOfficeId(manifestTO.getRepBranchID());

				}
				if (manifestTO.getDestBranchID() != null
						&& manifestTO.getDestBranchID() > 0) {
					destOff = new OfficeDO();
					destOff.setOfficeId(manifestTO.getDestBranchID());
				}

				if (manifestTO.getDestCityId() != null
						&& manifestTO.getDestCityId() > 0) {
					destCity = new CityDO();
					destCity.setCityId(manifestTO.getDestCityId());
				}

				if (manifestTO.getDestPinCodeId() != null
						&& manifestTO.getDestPinCodeId() > 0) {
					destPincode = new PincodeDO();
					destPincode.setPincodeId(manifestTO.getDestPinCodeId());
				}

				if (manifestTO.getModeID() != null
						&& manifestTO.getModeID() > 0) {
					mode = new ModeDO();
					mode.setModeId(manifestTO.getModeID());
				}
				if (manifestTO.getHandlingInstID() != null
						&& manifestTO.getHandlingInstID() > 0) {
					stdHandling = new StdHandlingInstDO();
					stdHandling.setHandleInstId(manifestTO.getHandlingInstID());

				}
				if (manifestTO.getDocumentID() != null
						&& manifestTO.getDocumentID() > 0) {
					doc = new DocumentDO();
					doc.setDocumentId(manifestTO.getDocumentID());

				}
				if (manifestTO.getProductID() != null
						&& manifestTO.getProductID() > 0) {
					product = new ProductDO();
					product.setProductId(manifestTO.getProductID());

				}
				if (manifestTO.getServiceID() != null
						&& manifestTO.getServiceID() > 0) {
					service = new ServiceDO();
					service.setServiceId(manifestTO.getServiceID());
				}

				manifest.setReadByLocal(CommonConstants.READ_BY_LOCAL_FLAG_Y);
				manifest.setDiFlag("N");
				Integer manifestId = 0;
				String manifestType = "O";
				Integer manifestTypeId = manifestTO.getMnfstTypeId();
				// getting Unique manifest id for update if already exists
				manifestId = outgoingManifestMDBDAO.getManifestId(
						manifestTO.getManifestNumber(), manifestTO.getConNum(),
						manifestType, manifestTypeId);
				if (manifestId > 0) {
					manifest.setManifestId(manifestId);
				}
				ManifestTypeDO manifestTypeDO = new ManifestTypeDO();
				manifestTypeDO.setMnfstTypeId(manifestTypeId);
				manifest.setMnsftTypes(manifestTypeDO);
				manifest.setManifestNumber(manifestTO.getManifestNumber());
				manifest.setManifestDate(DateFormatterUtil
						.slashDelimitedstringToDDMMYYYYFormat(manifestTO
								.getManifestDate()));
				manifest.setManifestTime(manifestTO.getManifestTime());
				manifest.setConsgNumber(manifestTO.getConNum());
				manifest.setTotWeightKgs(manifestTO.getTotalWeight());
				manifest.setRemarks(manifestTO.getConsgRemarks());
				manifest.setIndvWeightKgs(manifestTO.getConsgWeight());
				manifest.setMentionPieceRmks(manifestTO.getMentionPieceRmks());
				manifest.setNoOfPieces(manifestTO.getConsgNoOfPieces());
				manifest.setStatus(manifestTO.getConsgStatus());
				manifest.setManifestType(manifestTO.getManifestType());
				manifest.setWeighingType(manifestTO.getWeighingType());
				// For Volumetric weight implementation
				if (manifestTO.getVolumetricWeight() > 0) {
					manifest.setVolumetricWeight(manifestTO
							.getVolumetricWeight());
				}
				if (manifestTO.getLengthInCms() > 0) {
					manifest.setLength(manifestTO.getLengthInCms());
				}
				if (manifestTO.getBreadthInCms() > 0) {
					manifest.setBreadth(manifestTO.getBreadthInCms());
				}
				if (manifestTO.getHeightInCms() > 0) {
					manifest.setHeight(manifestTO.getHeightInCms());
				}
				manifest.setDbServer(manifestTO.getDbServer());
				manifest.setOtherInstrctions(manifestTO.getOtherInstrctions());
				manifest.setMentionPieceRmks(manifestTO.getMentionPieceRmks());
				// Setting forien keys
				if (orgOff != null) {
					manifest.setOriginBranch(orgOff);
				}
				if (regOff != null) {
					manifest.setReportingBranch(regOff);
				}
				if (destOff != null) {
					manifest.setDestBranch(destOff);
				}
				if (destCity != null) {
					manifest.setDestCity(destCity);
				}
				if (destPincode != null) {
					manifest.setDestPinCode(destPincode);
				}
				if (mode != null) {
					manifest.setMode(mode);
				}
				if (stdHandling != null) {
					manifest.setStdHandleInst(stdHandling);
				}
				if (doc != null) {
					manifest.setDocument(doc);
				}
				if (product != null) {
					manifest.setProduct(product);
				}
				if (service != null) {
					manifest.setService(service);
				}
				manifestDOList.add(manifest);
			}
			LOGGER.trace("*********OutgoingManifestMDBServiceImpl*******: convertPacketManifestDoxTOtoManifestDO:****End");
		}
		return manifestDOList;
	}

	/**
	 * Converter master bag dox t oto dodb sync.
	 *
	 * @param manifestTOs the manifest t os
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	@SuppressWarnings("unused")
	private List<ManifestDO> converterMasterBagDoxTOtoDODBSync(
			List<BagManifestDoxTO> manifestTOs) throws CGBusinessException {
		List<ManifestDO> manifestDOList = new ArrayList<ManifestDO>();
		String pinCode = "";
		List<Integer> manifestIDList = null;
		int manifestIdCount = 0;
		String manifestCode = "";
		int prodId = 0;
		int serviceId = 0;
		int docID = 0;
		Integer manifestId = 0;
		String manifestType = "O";
		Integer manifestTypeId = 0;
		try {
			OfficeDO orgOff = null;
			OfficeDO regOff = null;
			OfficeDO destOff = null;
			CityDO destCity = null;
			PincodeDO destPincode = null;
			ModeDO mode = null;
			StdHandlingInstDO stdHandling = null;
			DocumentDO doc = null;
			ProductDO product = null;
			ServiceDO service = null;
			// setting default objects
			if (manifestTOs != null && manifestTOs.size() > 0) {
				// BagManifestDoxTO mnfstTO = manifestTOs.get(0);
				/*
				 * if (mnfstTO.getOriginBranchId() != null &&
				 * mnfstTO.getOriginBranchId() > 0) { orgOff = new OfficeDO();
				 * orgOff.setOfficeId(mnfstTO.getOriginBranchId()); } if
				 * (mnfstTO.getDestBranchId() != null &&
				 * mnfstTO.getDestBranchId() > 0) { destOff = new OfficeDO();
				 * destOff.setOfficeId(mnfstTO.getDestBranchId()); } if
				 * (mnfstTO.getModeID() != null && mnfstTO.getModeID() > 0) {
				 * mode = new ModeDO(); mode.setModeId(mnfstTO.getModeID()); }
				 * if (mnfstTO.getProductID() != null && mnfstTO.getProductID()
				 * > 0) { product.setProductId(mnfstTO.getProductID()); } if
				 * (mnfstTO.getServiceID() != null && mnfstTO.getServiceID() >
				 * 0) { service = new ServiceDO();
				 * service.setServiceId(mnfstTO.getServiceID()); } if
				 * (mnfstTO.getDocumentID() != null && mnfstTO.getDocumentID() >
				 * 0) { doc = new DocumentDO();
				 * doc.setDocumentId(mnfstTO.getDocumentID()); }
				 */
				for (BagManifestDoxTO manifestTO : manifestTOs) {
					ManifestDO manifest = new ManifestDO();
					if (manifestTO.getOriginBranchId() != null
							&& manifestTO.getOriginBranchId() > 0) {
						orgOff = new OfficeDO();
						orgOff.setOfficeId(manifestTO.getOriginBranchId());
					}
					if (manifestTO.getDestBranchId() != null
							&& manifestTO.getDestBranchId() > 0) {
						destOff = new OfficeDO();
						destOff.setOfficeId(manifestTO.getDestBranchId());
					}
					if (manifestTO.getModeID() != null
							&& manifestTO.getModeID() > 0) {
						mode = new ModeDO();
						mode.setModeId(manifestTO.getModeID());
					}
					if (manifestTO.getProductID() != null
							&& manifestTO.getProductID() > 0) {
						product = new ProductDO();
						product.setProductId(manifestTO.getProductID());
					}
					if (manifestTO.getServiceID() != null
							&& manifestTO.getServiceID() > 0) {
						service = new ServiceDO();
						service.setServiceId(manifestTO.getServiceID());
					}
					if (manifestTO.getDocumentID() != null
							&& manifestTO.getDocumentID() > 0) {
						doc = new DocumentDO();
						doc.setDocumentId(manifestTO.getDocumentID());
					}
					manifest.setReadByLocal(CommonConstants.READ_BY_LOCAL_FLAG_Y);
					manifest.setDiFlag("N");
					// getting Unique manifest id for update if already exists
					manifestTypeId = manifestTO.getMnfstTypeId();
					manifestId = outgoingManifestMDBDAO.getManifestId(
							manifestTO.getMasterManifestNumber(),
							manifestTO.getManifestNumber(), manifestType,
							manifestTypeId);
					LOGGER.trace("*********OutgoingManifestMDBServiceImpl*******: converterMasterBagDoxTOtoDO:****manifestId:"
							+ manifestId);
					LOGGER.trace("*********OutgoingManifestMDBServiceImpl*******: converterMasterBagDoxTOtoDO:****consg Number:"
							+ manifestTO.getManifestNumber());
					if (manifestId > 0) {
						manifest.setManifestId(manifestId);
					}
					// Setting Grid Data
					manifest.setConsgNumber(manifestTO.getManifestNumber());
					manifest.setManifestNumber(manifestTO
							.getMasterManifestNumber());
					manifest.setIndvWeightKgs(manifestTO.getConsgWeight());
					manifest.setTotConsgNum(manifestTO.getConsgTotalCnNum());
					manifest.setRemarks(manifestTO.getConsgRemarks());
					ManifestTypeDO manifestTypeDO = new ManifestTypeDO();
					manifestTypeDO.setMnfstTypeId(manifestTypeId);
					manifest.setMnsftTypes(manifestTypeDO);
					manifest.setTotWeightKgs(manifestTO.getTotalWeight());
					manifest.setNoOfPacket(manifestTO.getNoOfPackets());
					manifest.setManifestDate(DateFormatterUtil
							.slashDelimitedstringToDDMMYYYYFormat(manifestTO
									.getManifestDate()));
					manifest.setManifestTime(manifestTO.getManifestTime());
					manifest.setManifestType(manifestTO.getConsgManifestType());
					manifest.setStatus(manifestTO.getManifestStatus());
					manifest.setWeighingType(manifestTO.getWeighingType());
					manifest.setDbServer(manifestTO.getDbServer());
					manifest.setManifestTypeDefn(manifestTO
							.getManifestTypeDefn());
					// Header Data
					if (orgOff != null) {
						manifest.setOriginBranch(orgOff);
					}
					if (destOff != null) {
						manifest.setDestBranch(destOff);
					}
					if (mode != null) {
						manifest.setMode(mode);
					}
					if (product != null) {
						manifest.setProduct(product);
					}
					if (service != null) {
						manifest.setService(service);
					}
					if (doc != null) {
						manifest.setDocument(doc);
					}
					manifestDOList.add(manifest);
				}
				LOGGER.trace("*********OutgoingManifestMDBServiceImpl*******: converterMasterBagDoxTOtoDO:****manifestDOList:"
						+ manifestDOList.size());
			}
		} catch (Exception ex) {
			// TODO: handle exception
			LOGGER.info("Error occured in OutgoingManifestServiceImpl in converterMasterBagDoxTOtoDO():::"
					+ ex.getMessage());
			new CGBaseException();
		}
		return manifestDOList;
	}
}
