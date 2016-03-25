package com.cg.lbs.bcun.service.dataformater;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.service.inbound.InboundCentralServiceImpl;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.booking.BookingTypeDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.consignment.ConsignmentBcunHelperDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.PincodeDO;

/**
 * The Class BcunManifestUtils.
 * 
 * @author narmdr
 */
public class BcunManifestUtils {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BcunManifestUtils.class);

	/**
	 * Gets the consignment.
	 * 
	 * @param bcunService
	 *            the bcun service
	 * @param consignmentDO
	 *            the consignment do
	 * @return the consignment
	 * @throws CGBusinessException 
	 */
	public static ConsignmentDO getConsignment(BcunDatasyncService bcunService,
			ConsignmentDO consignmentDO) throws CGBusinessException {
		LOGGER.info("BcunManifestUtils::getConsignment::START------------>:::::::");
		LOGGER.trace("BcunManifestUtils::getConsignment for Consignment No. => "
				+ consignmentDO.getConsgNo()
				+ "::Processing------------>:::::::");
		ConsignmentDO consgDO = null;
		
		/***
		 * BR:1. If consignment is updated by consignment modification functionality then those changes will not be overridden
		 * 2. To identify whether cn is updated by cn modification check if the consignment created by user id is EMPDADMIN ie 1
		 * 3.  if cn is updated by cn modification then preserve all these changes in ConsignmentBcunHelperDO 
		 *  and then finally inject all these values to Actual consignment
		 * */
		
		
		PincodeDO deliveredPincode=null;
		
		ConsignmentBcunHelperDO bcunHelperDO=null;
		try {
			consgDO = getConsg(bcunService, consignmentDO);
			
			Set<ConsignmentBillingRateDO> consgRateDtls =  new HashSet<>();
			if (consgDO == null) {
				//TODO uncomment once duplicate consg impl complete
				validateConsgAndBookingOrgOffice(bcunService, consignmentDO);
				consgDO = createConsignment(bcunService, consignmentDO);
				// ignored Branch rate
				consgDO.setConsgRateDtls(consgRateDtls);
				
			} else {

				/*
				 * 
				 * Consignment Billing BR 1: If consignment is Delivered then consignment Destination pincode will be modified
				 * since in Branch db at the time of updating consignment details with drs data , we are overriding consignment destination with Delivery destination
				 * 
				 * NOTE : please do not change the order of statements.
				 * */
				if(consgDO.getDestPincodeId()!=null && !StringUtil.isStringEmpty(consgDO.getConsgStatus()) && consgDO.getConsgStatus().equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_DELIVERED)){
					// Consignment Billing BR 1.1 :preserving Detination pincode of Delivered Consignment it applies if Data is already exist at Central
					// Consignment Billing BR 1 .2 :if Consingment data is not exist at central then not requried any of the validation
					deliveredPincode= new PincodeDO();
					deliveredPincode.setPincodeId(consgDO.getDestPincodeId().getPincodeId());
				}

				if(!StringUtil.isEmptyInteger(consgDO.getCreatedBy())&& (consgDO.getCreatedBy().intValue() == FrameworkConstants.FFCL_ADMIN_USER_ID ||consgDO.getCreatedBy().intValue() == FrameworkConstants.FFCL_RE_BILLING_USER_ID)){
					bcunHelperDO= new ConsignmentBcunHelperDO();
					copyProperties(consgDO, bcunHelperDO);
				}
				if (!StringUtil.isEmptyColletion(consgDO.getConsgRateDtls())) {
					consgRateDtls.addAll(consgDO.getConsgRateDtls());
				}
				//int initialSize = StringUtil.isEmptyColletion(consgRateDtls)?0 : consgRateDtls.size();
				// Weight change
				Double actualWt = consgDO.getActualWeight();
				Double volWt = consgDO.getVolWeight();
				Double finalWt = consgDO.getFinalWeight();
				String isCentralCNExcess = consgDO.getIsExcessConsg();
				String wtChangeFlag = consgDO.getChangedAfterBillingWtDest();
				String billingStatus = consgDO.getBillingStatus();
				
				consgDO = updateConsgObject(bcunService, consignmentDO, consgDO);
				// Retain Central rate
				if (!StringUtil.isEmptyColletion(consgRateDtls)) {
					for (ConsignmentBillingRateDO initialRateDO : consgRateDtls) {
						initialRateDO.setConsignmentDO(consgDO);
						if(consgDO.getBillingStatus().equalsIgnoreCase("TBB")){
							initialRateDO.setBilled("N");
						}
					}
				} 
				if (!StringUtil.isNull(isCentralCNExcess) && isCentralCNExcess.equalsIgnoreCase("N")){
					consgDO.setActualWeight(actualWt);
					consgDO.setVolWeight(volWt);
					consgDO.setFinalWeight(finalWt);
					consgDO.setChangedAfterBillingWtDest(wtChangeFlag);
				}
				// UPdate PFB consignment status
				if (!StringUtil.isNull(billingStatus) && billingStatus.equalsIgnoreCase("PFB")){
					consgDO.setBillingStatus("TBB");
				}
			}
			setDefaultValueToConsg(consgDO);
			validateMandatoryDataForConsignment(consgDO);
			consgDO.setConsgRateDtls(consgRateDtls);
		} catch (Exception e) {
			LOGGER.error("Exception happened in getConsignment of BcunManifestUtils..."
					, e);
			throw e;
		}
		if(bcunHelperDO!=null){
			copyProperties(bcunHelperDO,consgDO);
		}
		if(deliveredPincode!=null){
			// Consignment Billing BR 1 .3 :if Consingment data is  exist at central then update preserved Destination pincode in consignmentDO
			//NOTE : please do not change the order of statements.
			consgDO.setDestPincodeId(deliveredPincode);
		}
		LOGGER.info("BcunManifestUtils::getConsignment::End------------>:::::::");
		return consgDO;
	}

	private static void copyProperties(Object srcEntity,
			Object destEntity) throws CGBusinessException {
		try {
			PropertyUtils.copyProperties(destEntity, srcEntity);
		} catch (Exception obj) {
			LOGGER.error("BcunManifestUtils::getConsignment::error::", obj);
			throw new CGBusinessException(obj.getMessage());
		}
	}

	/**
	 * Gets the consg.
	 * 
	 * @param bcunService
	 *            the bcun service
	 * @param consignmentDO
	 *            the consignment do
	 * @return the consg
	 */
	@SuppressWarnings("unchecked")
	public static ConsignmentDO getConsg(BcunDatasyncService bcunService,
			ConsignmentDO consignmentDO) {
		LOGGER.trace("BcunManifestUtils::getConsg::START------------>:::::::");
		List<ConsignmentDO> consignmentDOs = (List<ConsignmentDO>) bcunService
				.getDataByNamedQueryAndNamedParam("getConsignmentByConsgNo",
						"consgNo", consignmentDO.getConsgNo());
		ConsignmentDO destConsignmentDO = (!StringUtil
				.isEmptyList(consignmentDOs)) ? consignmentDOs.get(0) : null;
		LOGGER.trace("BcunManifestUtils::getConsg::END------------>:::::::");
		return destConsignmentDO;
	}

	/**
	 * Update consg object.
	 *
	 * @param bcunService the bcun service
	 * @param originConsgObj the origin consg obj : branch obj
	 * @param destConsgObj the dest consg obj : central obj
	 * @return the consignment do
	 * @throws CGBusinessException 
	 */
	private static ConsignmentDO updateConsgObject(
			BcunDatasyncService bcunService, ConsignmentDO originConsgObj,
			ConsignmentDO destConsgObj) throws CGBusinessException {

		LOGGER.trace("BcunManifestUtils::updateConsgObject::START------------>:::::::");

		//TODO Deprecated
		//validateIncomingAndCentralConsgOrgOffice(bcunService, originConsgObj, destConsgObj);
		
		validateAndSetChangedAfterBillingWtDest(originConsgObj, destConsgObj);
		
		originConsgObj.setConsgId(destConsgObj.getConsgId());
		originConsgObj.setAltConsigneeAddr(destConsgObj.getAltConsigneeAddr());

		updateConsignmentStatus(originConsgObj, destConsgObj);
		
		// originConsgObj.setConsignee(destConsgObj.getConsignee());
		updateConsignee(originConsgObj, destConsgObj);

		// originConsgObj.setConsignor(destConsgObj.getConsignor());
		updateConsignor(originConsgObj, destConsgObj);
		
		//updateConsignmentRate(bcunService, originConsgObj, destConsgObj);

		if(isInBoundProcess(bcunService)){
			updateBillingFlags(bcunService, originConsgObj, destConsgObj);
		}

		//TODO uncomment once discussion done.
		//updateSapFlags(bcunService, originConsgObj, destConsgObj);
				
		updateConsgWeight(originConsgObj, destConsgObj);

		// Comparing Desitnation pincode
		updateDestPincode(originConsgObj, destConsgObj);

		// Setting child cn details
		updateChildConsignments(originConsgObj, destConsgObj);

		retainCentralValIfEmpty(originConsgObj, destConsgObj);
		retainCentralVal(originConsgObj, destConsgObj);
		

		originConsgObj = validateIncomingAndCentralConsginment(bcunService, originConsgObj, destConsgObj);
		
		LOGGER.trace("BcunManifestUtils::updateConsgObject::End------------>:::::::");
		return originConsgObj;
	}

	private static void validateAndSetChangedAfterBillingWtDest(
			ConsignmentDO incomingConsgObj, ConsignmentDO centralConsgObj) {
		//validate And Set ChangedAfterBillingWtDest Flag in incomingConsgObj
		if ((incomingConsgObj.getDestPincodeId() != null
				&& centralConsgObj.getDestPincodeId() != null && !incomingConsgObj
				.getDestPincodeId().getPincode()
				.equals(centralConsgObj.getDestPincodeId().getPincode()))
				|| (!StringUtil
						.isEmptyDouble(incomingConsgObj.getFinalWeight())
						&& !StringUtil.isEmptyDouble(centralConsgObj
								.getFinalWeight()) && incomingConsgObj
						.getFinalWeight() > centralConsgObj.getFinalWeight())) {
			incomingConsgObj.setChangedAfterBillingWtDest(CommonConstants.YES);
		} else {
			incomingConsgObj.setChangedAfterBillingWtDest(CommonConstants.NO);
		}
	}

	private static void retainCentralVal(ConsignmentDO originConsgObj,
			ConsignmentDO destConsgObj) {
		originConsgObj.setDtFromOpsman(destConsgObj.getDtFromOpsman());
	}

	private static void updateChildConsignments(ConsignmentDO originConsgObj,
			ConsignmentDO destConsgObj) {
		Set<ChildConsignmentDO> destChildConsignments = destConsgObj
				.getChildCNs();
		Set<ChildConsignmentDO> orgChildConsignments = originConsgObj
				.getChildCNs();
		if (!StringUtil.isEmptyColletion(destChildConsignments)
				&& (!StringUtil.isEmptyColletion(orgChildConsignments))) {
			for (ChildConsignmentDO orgChildConsg : orgChildConsignments) {
				for (ChildConsignmentDO destChildConsg : destChildConsignments) {
					if (StringUtils.equalsIgnoreCase(
							destChildConsg.getChildConsgNumber(),
							orgChildConsg.getChildConsgNumber())) {
						orgChildConsg.setBookingChildCNId(destChildConsg
								.getBookingChildCNId());
						if (orgChildConsg.getChildConsgWeight() < destChildConsg
								.getChildConsgWeight()) {
							orgChildConsg.setChildConsgWeight(destChildConsg
									.getChildConsgWeight());
						}
						orgChildConsg
								.setIsChildConsignmentUpdated(Boolean.TRUE);
					}
				}
			}

			// new child cns who all are not updated
			for (ChildConsignmentDO orgChildConsg : orgChildConsignments) {
				if (!orgChildConsg.getIsChildConsignmentUpdated()) {
					orgChildConsg.setBookingChildCNId(null);
				}
			}
			originConsgObj.setChildCNs(orgChildConsignments);

		} else if (!StringUtil.isEmptyColletion(destChildConsignments)
				&& (StringUtil.isEmptyColletion(orgChildConsignments))) {
			originConsgObj.setChildCNs(destChildConsignments);

		} else if (!StringUtil.isEmptyColletion(originConsgObj.getChildCNs())) {// new
																				// child
																				// cn

			for (ChildConsignmentDO childConsignmentDO : originConsgObj
					.getChildCNs()) {
				childConsignmentDO.setBookingChildCNId(null);
			}
		}
		
	}

	private static void updateDestPincode(ConsignmentDO originConsgObj,
			ConsignmentDO destConsgObj) {
		//PincodeDO sourcePincode = originConsgObj.getDestPincodeId();
		PincodeDO destPincode = destConsgObj.getDestPincodeId();
/*		if (!StringUtil.isNull(sourcePincode)
				&& !StringUtil.isNull(destPincode)
				&& !StringUtil.isEmptyInteger(originConsgObj
						.getOperatingLevel())
				&& !StringUtil.isEmptyInteger(destConsgObj.getOperatingLevel())) {
			if (originConsgObj.getOperatingLevel() < destConsgObj
					.getOperatingLevel()) {
				originConsgObj.setDestPincodeId(destPincode);

			} else if (originConsgObj.getOperatingLevel() != null
					&& destConsgObj.getOperatingLevel() != null
					&& originConsgObj.getOperatingLevel().intValue() == destConsgObj
							.getOperatingLevel().intValue()) {*/
				if (originConsgObj.getUpdatedProcess() != null
						&& destConsgObj.getUpdatedProcess() != null
						&& !StringUtil.isEmptyInteger(originConsgObj
								.getUpdatedProcess().getProcessOrder())
						&& !StringUtil.isEmptyInteger(destConsgObj
								.getUpdatedProcess().getProcessOrder())
						&& originConsgObj.getUpdatedProcess().getProcessOrder() < destConsgObj
								.getUpdatedProcess().getProcessOrder()) {
					originConsgObj.setDestPincodeId(destPincode);

				} else if (!StringUtil.isEmptyInteger(originConsgObj
						.getUpdatedProcess().getProcessOrder())
						&& !StringUtil.isEmptyInteger(destConsgObj
								.getUpdatedProcess().getProcessOrder())
						&& originConsgObj.getUpdatedProcess().getProcessOrder()
								.intValue() == destConsgObj.getUpdatedProcess()
								.getProcessOrder().intValue()) {
					if (originConsgObj.getUpdatedDate() != null
							&& destConsgObj.getUpdatedDate() != null
							&& originConsgObj.getUpdatedDate().before(
									destConsgObj.getUpdatedDate())) {
						originConsgObj.setDestPincodeId(destPincode);
					}
				}
/*			}
		}*/
	}

	/**
	 * Update consg weight.
	 *
	 * @param originConsgObj the origin consg obj
	 * @param destConsgObj the dest consg obj
	 */
	private static void updateConsgWeight(ConsignmentDO originConsgObj,
			ConsignmentDO destConsgObj) {
		// Setting weight
		if (!StringUtil.isEmptyDouble(originConsgObj.getFinalWeight())
				&& !StringUtil.isEmptyDouble(destConsgObj.getFinalWeight())
				&& originConsgObj.getFinalWeight().doubleValue() < destConsgObj
						.getFinalWeight().doubleValue()) {
			originConsgObj.setFinalWeight(destConsgObj.getFinalWeight());
			originConsgObj.setActualWeight(destConsgObj.getActualWeight());
			originConsgObj.setVolWeight(destConsgObj.getVolWeight());

		} else {
			if (StringUtil.isEmptyDouble(originConsgObj.getActualWeight())) {
				originConsgObj.setActualWeight(destConsgObj.getActualWeight());
			}
			if (StringUtil.isEmptyDouble(originConsgObj.getVolWeight())) {
				originConsgObj.setVolWeight(destConsgObj.getVolWeight());
			}
		}
	}

	/**
	 * Validate consg and booking org office.
	 *
	 * @param bcunService the bcun service
	 * @param originConsgObj the origin consg obj
	 * @throws CGBusinessException the cG business exception
	 */
	private static void validateConsgAndBookingOrgOffice(
			BcunDatasyncService bcunService, ConsignmentDO originConsgObj)
			throws CGBusinessException {

		if (!isInBoundProcess(bcunService)) {
			LOGGER.trace("BcunManifestUtils::validateConsgAndBookingOrgOffice::No need to validate for outbound process------------>:::::::");
			return;
		}

		Integer bookingOriginOfficeId = getBookingOriginOffice(bcunService,
				originConsgObj);

		/*if (StringUtil.isEmptyInteger(bookingOriginOfficeId)) {
			LOGGER.error("BcunManifestUtils::validateConsgAndBookingOrgOffice::Booking data does not exist. for Consignment No. : "
					+ originConsgObj.getConsgNo() + "------------>:::::::");
		}*/

		if (!StringUtil.isEmptyInteger(bookingOriginOfficeId)
				&& (StringUtil.isEmptyInteger(originConsgObj.getOrgOffId()) || !originConsgObj
						.getOrgOffId().equals(bookingOriginOfficeId))) {
			LOGGER.error("BcunManifestUtils::validateConsgAndBookingOrgOffice::Invalid origin office Id :: Booking office : "
					+ bookingOriginOfficeId
					+ " and Consignment origin office : "
					+ originConsgObj.getOrgOffId() + " of Consg No : " + originConsgObj.getConsgNo() + " should be same.");

			// Excess Consignment
			if (isExcessConsg(originConsgObj)) {
				LOGGER.trace("BcunManifestUtils::validateConsgAndBookingOrgOffice::Excess Consignment------------>:::::::");
				originConsgObj.setOrgOffId(bookingOriginOfficeId);
			} else {
				LOGGER.trace("BcunManifestUtils::validateConsgAndBookingOrgOffice::Normal Consignment------------>:::::::");
				errorOutDuplicateConsignment(bcunService, originConsgObj);
			}
		}
	}

	/**
	 * Checks if is excess consg.
	 *
	 * @param consgObj the consg obj
	 * @return true, if is excess consg
	 */
	private static boolean isExcessConsg(ConsignmentDO consgObj) {
		return StringUtils.equals(consgObj.getIsExcessConsg(),
				CommonConstants.YES);
	}

	private static ConsignmentDO validateIncomingAndCentralConsginment(
			BcunDatasyncService bcunService, ConsignmentDO originConsgObj,
			ConsignmentDO destConsgObj) throws CGBusinessException {

		if (!isInBoundProcess(bcunService)) {
			LOGGER.trace("BcunManifestUtils::validateIncomingAndCentralConsginment::No need to validate for outbound process------------>:::::::");
			return originConsgObj;
		}
/*		check consignment table for consignment
		Found and Excess CN YES
		  Update Existing CN table
		Found and Excess CN NO
		  Check incoming CN Excess YES
			capture  Rate & RTO flag from incoming and update existing 
		  Check incoming CN Excess NO
			Cno and origin Matching
				Pure update
			CNo and origin Not matching
				Duplicate consg / or no action just drop this data*/

		if (!isExcessConsg(destConsgObj)) {
			// Excess Consignment
			if (isExcessConsg(originConsgObj)) {
				LOGGER.trace("BcunManifestUtils::validateIncomingAndCentralConsginment::Excess Consignment------------>:::::::");				
				//capture  Rate & RTO flag from incoming and update existing 
				setBillingAndOtherFieldsToCentralCN(originConsgObj,destConsgObj);
				return destConsgObj;
			} else {
				if (StringUtil.isEmptyInteger(originConsgObj.getOrgOffId())
						|| !originConsgObj.getOrgOffId().equals(
								destConsgObj.getOrgOffId())) {
					LOGGER.error("BcunManifestUtils::validateIncomingAndCentralConsginment::Invalid origin office Id :: central Consignment origin office : "
							+ destConsgObj.getOrgOffId()
							+ " and incoming Consignment origin office : "
							+ originConsgObj.getOrgOffId() + " of Consg No : " + originConsgObj.getConsgNo() + " should be same. Both are not an Excess Consignment.");

					//LOGGER.trace("BcunManifestUtils::validateIncomingAndCentralConsginment::Normal Consignment------------>:::::::");
					errorOutDuplicateConsignment(bcunService, originConsgObj);
				}
			}
		}
	
		return originConsgObj;
	}

	private static void setBillingAndOtherFieldsToCentralCN(
			ConsignmentDO originConsgObj, ConsignmentDO destConsgObj) {
		LOGGER.debug("BcunManifestUtils::setBillingAndOtherFieldsToCentralCN::for Consignment No. : "+ originConsgObj.getConsgNo() +" ::Start  ------------>:::::::");
		//capture  Rate & Billing flag from incoming and update existing 
		//consignor, consignee, consgstatus, consgwt, ChildCNs
		
		if (!StringUtil.isNull(originConsgObj.getConsgStatus())
				&& !StringUtil.isNull(destConsgObj.getConsgStatus())) {
			if (destConsgObj.getConsgStatus().equalsIgnoreCase("D")
					|| destConsgObj.getConsgStatus().equalsIgnoreCase("S"))
				return;
			if ((destConsgObj.getConsgStatus().equalsIgnoreCase("R") || destConsgObj
					.getConsgStatus().equalsIgnoreCase("H"))
					&& (originConsgObj.getConsgStatus().equals("S")
							|| originConsgObj.getConsgStatus().equals("D") || originConsgObj
							.getConsgStatus().equals("R"))) {
			// Consignment Status
			destConsgObj.setConsgStatus(originConsgObj.getConsgStatus());
			//weight
			destConsgObj.setFinalWeight(originConsgObj.getFinalWeight());
			destConsgObj.setActualWeight(originConsgObj.getActualWeight());
			destConsgObj.setVolWeight(originConsgObj.getVolWeight());
			
			//consignor, consignee,
			destConsgObj.setConsignee(originConsgObj.getConsignee());
			destConsgObj.setConsignor(originConsgObj.getConsignor());
			
			//ChildCNs
			destConsgObj.setChildCNs(originConsgObj.getChildCNs());
			
			//Billing Flags
			destConsgObj.setChangedAfterBillingWtDest(originConsgObj.getChangedAfterBillingWtDest());
			destConsgObj.setChangedAfterNewRateCmpnt(originConsgObj.getChangedAfterNewRateCmpnt());
			destConsgObj.setBillingStatus(originConsgObj.getBillingStatus());
			
			//consg rate
			/*if(!"BA".equalsIgnoreCase(destConsgObj.getRateType())){
				destConsgObj.setConsgRateDtls(originConsgObj.getConsgRateDtls());
			}*/
		}
	}
		LOGGER.debug("BcunManifestUtils::setBillingAndOtherFieldsToCentralCN::for Consignment No. : "+ originConsgObj.getConsgNo() +" ::End  ------------>:::::::");
	}

	/**
	 * Validate incoming and central consg org office.
	 *
	 * @param bcunService the bcun service
	 * @param originConsgObj the origin consg obj
	 * @param destConsgObj the dest consg obj
	 * @throws CGBusinessException the cG business exception
	 */
	@SuppressWarnings("unused")
	private static void validateIncomingAndCentralConsgOrgOffice(
			BcunDatasyncService bcunService, ConsignmentDO originConsgObj,
			ConsignmentDO destConsgObj) throws CGBusinessException {

		if (!isInBoundProcess(bcunService)) {
			LOGGER.trace("BcunManifestUtils::validateIncomingAndCentralConsgOrgOffice::No need to validate for outbound process------------>:::::::");
			return;
		}

		if (StringUtil.isEmptyInteger(originConsgObj.getOrgOffId())
				|| !originConsgObj.getOrgOffId().equals(
						destConsgObj.getOrgOffId())) {
			LOGGER.error("BcunManifestUtils::validateIncomingAndCentralConsgOrgOffice::Invalid origin office Id :: central Consignment origin office : "
					+ destConsgObj.getOrgOffId()
					+ " and incoming Consignment origin office : "
					+ originConsgObj.getOrgOffId() + " of Consg No : " + originConsgObj.getConsgNo() + " should be same.");

			// Excess Consignment
			if (isExcessConsg(originConsgObj)) {
				LOGGER.trace("BcunManifestUtils::validateIncomingAndCentralConsgOrgOffice::Excess Consignment------------>:::::::");
				originConsgObj.setOrgOffId(destConsgObj.getOrgOffId());
				
			} else {
				LOGGER.trace("BcunManifestUtils::validateIncomingAndCentralConsgOrgOffice::Normal Consignment------------>:::::::");
				errorOutDuplicateConsignment(bcunService, originConsgObj);				
			}
		}
	}

	/**
	 * Error out duplicate consignment.
	 *
	 * @param bcunService the bcun service
	 * @param consignmentDO the consignment do
	 * @throws CGBusinessException the cG business exception
	 */
	private static void errorOutDuplicateConsignment(
			BcunDatasyncService bcunService, ConsignmentDO consignmentDO)
			throws CGBusinessException {

		// TODO save duplicate consg in duplicate table.
		CreateDuplicateConsignment.createDuplicateConsignment(bcunService, consignmentDO);

		LOGGER.error("BcunManifestUtils::errorOutDuplicateConsignment::Invalid origin office Id. Since Both central and incoming object origin office are not same. So Throwing Business Exception to error out malicious consignment no. :: "+consignmentDO.getConsgNo());
		// Error out the Normal Consignment.
		throw new CGBusinessException(
				"Invalid origin office Id :: Both central and incoming object origin office must be same: ");
	}

	/**
	 * Creates the duplicate consignment.
	 *
	 * @param bcunService the bcun service
	 * @param consignmentDO the consignment do
	 * @throws CGBusinessException 
	 */
//	private static void createDuplicateConsignment(
//			BcunDatasyncService bcunService, ConsignmentDO consignmentDO) {
//		LOGGER.trace("BcunManifestUtils::createDuplicateConsignment::START------------>:::::::");
//		try {
//			LOGGER.trace("BcunManifestUtils::createDuplicateConsignment::start saving of Duplicate Consignment.------------>:::::::");
//			// bcunService.saveOrUpdateTransferedEntity(consignmentDO);
//			LOGGER.trace("BcunManifestUtils::createDuplicateConsignment::Duplicate Consignment saved.------------>:::::::");
//		} catch (Exception e) {
//			LOGGER.error("Exception happened in createDuplicateConsignment of BcunManifestUtils..."
//					, e);
//		}
//		LOGGER.trace("BcunManifestUtils::createDuplicateConsignment::END------------>:::::::");
//	}

	/**
	 * Gets the booking origin office.
	 *
	 * @param bcunService the bcun service
	 * @param originConsgObj the origin consg obj
	 * @return the booking origin office
	 */
	private static Integer getBookingOriginOffice(
			BcunDatasyncService bcunService, ConsignmentDO originConsgObj) {
		LOGGER.trace("BcunManifestUtils::getBookingOriginOffice::START------------>:::::::");
		String[] params = { "consgNumber", };
		Object[] values = { originConsgObj.getConsgNo() };

		Integer bookingOriginOfficeId = bcunService.getUniqueId(
				"getBookingOfficeId", params, values);
		LOGGER.trace("BcunManifestUtils::getBookingOriginOffice::END------------>:::::::");
		return bookingOriginOfficeId;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	private static BookingTypeDO getBookingBookingType(
			BcunDatasyncService bcunService, ConsignmentDO consignmentDO) {
		LOGGER.trace("BcunManifestUtils::getBookingBookingType::START------------>:::::::");

		List<BookingTypeDO> bookingTypeDOs = (List<BookingTypeDO>) bcunService
				.getDataByNamedQueryAndNamedParam("getBookingBookingType",
						"consgNumber", consignmentDO.getConsgNo());
		BookingTypeDO bookingTypeDO = (!StringUtil
				.isEmptyList(bookingTypeDOs)) ? bookingTypeDOs.get(0) : null;		
		LOGGER.trace("BcunManifestUtils::getBookingBookingType::END------------>:::::::");
		return bookingTypeDO;
	}

	private static void updateConsignmentStatus(ConsignmentDO originConsgObj,
			ConsignmentDO destConsgObj) {
		// save originConsgObj

		if (StringUtils.isBlank(originConsgObj.getConsgStatus())
				&& StringUtils.isBlank(destConsgObj.getConsgStatus())) {
			return;
		} else if (StringUtils.isBlank(originConsgObj.getConsgStatus())
				&& StringUtils.isNotBlank(destConsgObj.getConsgStatus())) {
			originConsgObj.setConsgStatus(destConsgObj.getConsgStatus());
			return;
		}

		if (originConsgObj.getConsgStatus().equals(
				CommonConstants.CONSIGNMENT_STATUS_BOOK)
				&& (destConsgObj.getConsgStatus().equals(
						CommonConstants.CONSIGNMENT_STATUS_RTOH)
						|| destConsgObj.getConsgStatus().equals(
								CommonConstants.CONSIGNMENT_STATUS_DELV)
						|| destConsgObj.getConsgStatus().equals(
								CommonConstants.CONSIGNMENT_STATUS_RTH)
						|| destConsgObj.getConsgStatus().equals(
								CommonConstants.CONSIGNMENT_STATUS_STOPDELV) || destConsgObj
						.getConsgStatus().equals(
								CommonConstants.CONSIGNMENT_STATUS_RTO_DRS))) {
			// UPDATE() dest status			
			originConsgObj
					.setConsgStatus(destConsgObj.getConsgStatus());
		} else if (originConsgObj.getConsgStatus().equals(
				CommonConstants.CONSIGNMENT_STATUS_RTH)
				&& (destConsgObj.getConsgStatus().equals(
						CommonConstants.CONSIGNMENT_STATUS_RTOH)
						|| destConsgObj.getConsgStatus().equals(
								CommonConstants.CONSIGNMENT_STATUS_DELV)
						|| destConsgObj.getConsgStatus().equals(
								CommonConstants.CONSIGNMENT_STATUS_STOPDELV) || destConsgObj
						.getConsgStatus().equals(
								CommonConstants.CONSIGNMENT_STATUS_RTO_DRS))) {
			originConsgObj
					.setConsgStatus(destConsgObj.getConsgStatus());

		} else if (originConsgObj.getConsgStatus().equals(
				CommonConstants.CONSIGNMENT_STATUS_RTOH)
				&& (destConsgObj.getConsgStatus().equals(
						CommonConstants.CONSIGNMENT_STATUS_STOPDELV) || destConsgObj
						.getConsgStatus().equals(
								CommonConstants.CONSIGNMENT_STATUS_RTO_DRS))) {
			originConsgObj
					.setConsgStatus(destConsgObj.getConsgStatus());

		}
	}

	/**
	 * Checks if is in bound process.
	 *
	 * @param bcunService the bcun service
	 * @return true, if is in bound process
	 */
	private static boolean isInBoundProcess(BcunDatasyncService bcunService) {
		Boolean isInBoundProcess = Boolean.FALSE;
		if (bcunService instanceof InboundCentralServiceImpl) {
			isInBoundProcess  = Boolean.TRUE;
		} // else  OutboundBranchServiceImpl
		
		return isInBoundProcess;
	}

	/**
	 * Update consignment rate.
	 *
	 * @param bcunService the bcun service
	 * @param originConsgObj the origin consg obj
	 * @param destConsgObj the dest consg obj
	 */
	@SuppressWarnings("unused")
	private static void updateConsignmentRate(BcunDatasyncService bcunService,
			ConsignmentDO originConsgObj, ConsignmentDO destConsgObj) {
		
		// Billing check
		if (!isInBoundProcess(bcunService)) {
			originConsgObj.setConsgRateDtls(destConsgObj.getConsgRateDtls());
			return;
		}

		validateAndSetConsgRateDetails(originConsgObj, destConsgObj);

		if (!StringUtil.isEmptyColletion(originConsgObj.getConsgRateDtls())
				&& !StringUtil
						.isEmptyColletion(destConsgObj.getConsgRateDtls())) {
			for (ConsignmentBillingRateDO originConsignmentBillingRateDO : originConsgObj
					.getConsgRateDtls()) {
				for (ConsignmentBillingRateDO destConsignmentBillingRateDO : destConsgObj
						.getConsgRateDtls()) {
					if (StringUtils
							.equalsIgnoreCase(originConsignmentBillingRateDO
									.getRateCalculatedFor(),
									destConsignmentBillingRateDO
											.getRateCalculatedFor())) {
						originConsignmentBillingRateDO
								.setIsCnBillingRateUpdated(Boolean.TRUE);
						originConsignmentBillingRateDO
								.setConsignmentRateId(destConsignmentBillingRateDO
										.getConsignmentRateId());
						if (originConsignmentBillingRateDO.getConsignmentDO() != null) {
							originConsignmentBillingRateDO.getConsignmentDO()
									.setConsgId(
											destConsignmentBillingRateDO
													.getConsignmentDO()
													.getConsgId());
						}
						validateAndSetBookingRateFlags(
								originConsignmentBillingRateDO,
								destConsignmentBillingRateDO, originConsgObj);
						validateAndSetRtoRateFlags(
								originConsignmentBillingRateDO,
								destConsignmentBillingRateDO);
					}
				}
			}
		}

		// new ConsignmentBillingRate who all are not updated needs to create
		if (!StringUtil.isEmptyColletion(originConsgObj.getConsgRateDtls())) {
			for (ConsignmentBillingRateDO originConsignmentBillingRateDO : originConsgObj
					.getConsgRateDtls()) {
				if (!originConsignmentBillingRateDO.getIsCnBillingRateUpdated()) {
					originConsignmentBillingRateDO.setConsignmentRateId(null);
					if (originConsignmentBillingRateDO.getConsignmentDO() != null) {
						originConsignmentBillingRateDO.getConsignmentDO()
								.setConsgId(originConsgObj.getConsgId());
					}
				}
			}
		}
		
		//validate and check duplicate rates in consignment if duplicate take unique one and drop out other.	
		validateDuplicateRates(originConsgObj,destConsgObj);
	}

	private static void validateDuplicateRates(ConsignmentDO originConsgObj,
			ConsignmentDO destConsgObj) {
		Set<ConsignmentBillingRateDO> newConsgBillingRates = null;
		Set<ConsignmentBillingRateDO> originConsgBillingRates = originConsgObj.getConsgRateDtls();
		// TODO Auto-generated method stub
		if (!StringUtil.isEmptyColletion(originConsgBillingRates) && originConsgBillingRates.size()>1) {
			 newConsgBillingRates = new HashSet<>();
			 getAndSetBillingRate(originConsgBillingRates, newConsgBillingRates, CommonConstants.RATE_CALCULATED_FOR_BOOKING);
			 getAndSetBillingRate(originConsgBillingRates, newConsgBillingRates, CommonConstants.RATE_CALCULATED_FOR_RTO);
			 originConsgObj.setConsgRateDtls(newConsgBillingRates);
		}
	}

	private static void getAndSetBillingRate(
			Set<ConsignmentBillingRateDO> originConsgBillingRates,
			Set<ConsignmentBillingRateDO> newConsgBillingRates,
			String rateCalculatedFor) {
		
		for (ConsignmentBillingRateDO consgBillingRateDO : originConsgBillingRates) {
			if (StringUtils.equalsIgnoreCase(
					consgBillingRateDO.getRateCalculatedFor(),
					rateCalculatedFor)) {
				newConsgBillingRates.add(consgBillingRateDO);
				break;
			}
		}

	}

	/**
	 * Retain central val if empty.
	 *
	 * @param originConsgObj the origin consg obj
	 * @param destConsgObj the dest consg obj
	 */
	private static void retainCentralValIfEmpty(ConsignmentDO originConsgObj,
			ConsignmentDO destConsgObj) {
		if(StringUtils.isBlank(originConsgObj.getRateType())){
			originConsgObj.setRateType(destConsgObj.getRateType());
		}
		if(StringUtil.isEmptyInteger(originConsgObj.getOrgOffId())){
			originConsgObj.setOrgOffId(destConsgObj.getOrgOffId());
		}
		if(StringUtil.isEmptyInteger(originConsgObj.getCustomer())){
			originConsgObj.setCustomer(destConsgObj.getCustomer());
		}
		
		if(StringUtils.isBlank(originConsgObj.getProcessStatus())){
			originConsgObj.setProcessStatus(destConsgObj.getProcessStatus());
		}
		if(StringUtil.isEmptyDouble(originConsgObj.getPrice())){
			originConsgObj.setPrice(destConsgObj.getPrice());
		}
		if(StringUtil.isEmptyInteger(originConsgObj.getProductId())){
			originConsgObj.setProductId(destConsgObj.getProductId());
		}

		if (StringUtil.isEmptyDouble(originConsgObj.getActualWeight())) {
			originConsgObj.setActualWeight(destConsgObj.getActualWeight());
		}
		if (StringUtil.isEmptyDouble(originConsgObj.getVolWeight())) {
			originConsgObj.setVolWeight(destConsgObj.getVolWeight());
		}
		if(StringUtil.isEmptyDouble(originConsgObj.getFinalWeight())){
			originConsgObj.setFinalWeight(destConsgObj.getFinalWeight());
		}
		if (StringUtil.isEmptyDouble(originConsgObj.getHeight())) {
			originConsgObj.setHeight(destConsgObj.getHeight());
		}
		if (StringUtil.isEmptyDouble(originConsgObj.getLength())) {
			originConsgObj.setLength(destConsgObj.getLength());
		}
		if (StringUtil.isEmptyDouble(originConsgObj.getBreath())) {
			originConsgObj.setBreath(destConsgObj.getBreath());
		}

		if(originConsgObj.getCnContentId()==null){
			originConsgObj.setCnContentId(destConsgObj.getCnContentId());
		}
		if(originConsgObj.getCnPaperWorkId()==null){
			originConsgObj.setCnPaperWorkId(destConsgObj.getCnPaperWorkId());
		}
		if(originConsgObj.getInsuredBy()==null){
			originConsgObj.setInsuredBy(destConsgObj.getInsuredBy());
		}

		if(StringUtils.isBlank(originConsgObj.getOtherCNContent())){
			originConsgObj.setOtherCNContent(destConsgObj.getOtherCNContent());
		}
		if(StringUtils.isBlank(originConsgObj.getPaperWorkRefNo())){
			originConsgObj.setPaperWorkRefNo(destConsgObj.getPaperWorkRefNo());
		}
		if(StringUtils.isBlank(originConsgObj.getInsurencePolicyNo())){
			originConsgObj.setInsurencePolicyNo(destConsgObj.getInsurencePolicyNo());
		}
		if(StringUtils.isBlank(originConsgObj.getRefNo())){
			originConsgObj.setRefNo(destConsgObj.getRefNo());
		}
		if(StringUtils.isBlank(originConsgObj.getMobileNo())){
			originConsgObj.setMobileNo(destConsgObj.getMobileNo());
		}
		

		if(originConsgObj.getReceivedDateTime()==null){
			originConsgObj.setReceivedDateTime(destConsgObj.getReceivedDateTime());
		}
		if(originConsgObj.getDeliveredDate()==null){
			originConsgObj.setDeliveredDate(destConsgObj.getDeliveredDate());
		}
		if(originConsgObj.getConsignee()==null){
			originConsgObj.setConsignee(destConsgObj.getConsignee());
		}
		if(originConsgObj.getConsignor()==null){
			originConsgObj.setConsignor(destConsgObj.getConsignor());
		}
		
		if(StringUtils.isBlank(originConsgObj.getRecvNameOrCompName())){
			originConsgObj.setRecvNameOrCompName(destConsgObj.getRecvNameOrCompName());
		}
//		if(StringUtils.isBlank(originConsgObj.getReceivedStatus())){
//			originConsgObj.setReceivedStatus(destConsgObj.getReceivedStatus());
//		}

		if(StringUtil.isEmptyDouble(originConsgObj.getBaAmt())){
			originConsgObj.setBaAmt(destConsgObj.getBaAmt());
		}
		if(StringUtil.isEmptyDouble(originConsgObj.getDiscount())){
			originConsgObj.setDiscount(destConsgObj.getDiscount());
		}
		if(StringUtil.isEmptyDouble(originConsgObj.getTopayAmt())){
			originConsgObj.setTopayAmt(destConsgObj.getTopayAmt());
		}
		if(StringUtil.isEmptyDouble(originConsgObj.getSplChg())){
			originConsgObj.setSplChg(destConsgObj.getSplChg());
		}
		if(StringUtil.isEmptyDouble(originConsgObj.getDeclaredValue())){
			originConsgObj.setDeclaredValue(destConsgObj.getDeclaredValue());
		}
		if(StringUtil.isEmptyDouble(originConsgObj.getCodAmt())){
			originConsgObj.setCodAmt(destConsgObj.getCodAmt());
		}
		if(StringUtil.isEmptyDouble(originConsgObj.getLcAmount())){
			originConsgObj.setLcAmount(destConsgObj.getLcAmount());
		}
		
		if(StringUtils.isBlank(originConsgObj.getLcBankName())){
			originConsgObj.setLcBankName(destConsgObj.getLcBankName());
		}
		if(StringUtils.isBlank(originConsgObj.getServicedOn())){
			originConsgObj.setServicedOn(destConsgObj.getServicedOn());
		}
		if(StringUtils.isBlank(originConsgObj.getEbPreferencesCodes())){
			originConsgObj.setEbPreferencesCodes(destConsgObj.getEbPreferencesCodes());
		}
		
		if(StringUtil.isEmptyInteger(originConsgObj.getOperatingOffice())){
			originConsgObj.setOperatingOffice(destConsgObj.getOperatingOffice());
		}

		if(StringUtils.isBlank(originConsgObj.getRemarks())){
			originConsgObj.setRemarks(destConsgObj.getRemarks());
		}
		if(StringUtils.isBlank(originConsgObj.getStopDelivery())){
			originConsgObj.setStopDelivery(destConsgObj.getStopDelivery());
		}
		if(StringUtils.isBlank(originConsgObj.getMisRouted())){
			originConsgObj.setMisRouted(destConsgObj.getMisRouted());
		}
		if(StringUtils.isBlank(originConsgObj.getIsExcessConsg())){
			originConsgObj.setIsExcessConsg(destConsgObj.getIsExcessConsg());
		}


		if(originConsgObj.getEventDate()==null){
			originConsgObj.setEventDate(destConsgObj.getEventDate());
		}
		if(originConsgObj.getCnReturnReason()==null){
			originConsgObj.setCnReturnReason(destConsgObj.getCnReturnReason());
		}
		if(originConsgObj.getAltConsigneeAddr()==null){
			originConsgObj.setAltConsigneeAddr(destConsgObj.getAltConsigneeAddr());
		}
		if(originConsgObj.getStopReason()==null){
			originConsgObj.setStopReason(destConsgObj.getStopReason());
		}
		if(originConsgObj.getStopDelvDate()==null){
			originConsgObj.setStopDelvDate(destConsgObj.getStopDelvDate());
		}
		if(originConsgObj.getProductDO()==null){
			originConsgObj.setProductDO(destConsgObj.getProductDO());
		}
		if(StringUtil.isEmptyInteger(originConsgObj.getStopDeliveryReqOff())){
			originConsgObj.setStopDeliveryReqOff(destConsgObj.getStopDeliveryReqOff());
		}
	}

	/**
	 * Validate and set consg rate details.
	 * 
	 * @param originConsgObj
	 *            the origin consg obj
	 * @param destConsgObj
	 *            the dest consg obj
	 */
	private static void validateAndSetConsgRateDetails(
			ConsignmentDO originConsgObj, ConsignmentDO destConsgObj) {
		if ((isExcessConsg(originConsgObj) && !StringUtil
				.isEmptyColletion(destConsgObj.getConsgRateDtls()))
				|| StringUtil.isEmptyColletion(originConsgObj
						.getConsgRateDtls())) {
			
			//keeping RTO rate case in mind
			if (!StringUtil.isEmptyColletion(originConsgObj
					.getConsgRateDtls())
					&& !StringUtil.isEmptyColletion(destConsgObj
							.getConsgRateDtls()) && originConsgObj
					.getConsgRateDtls().size() > destConsgObj
					.getConsgRateDtls().size()){
				return;
			}
			originConsgObj.setConsgRateDtls(destConsgObj.getConsgRateDtls());
		}
	}

	/**
	 * Validate and set booking rate flags.
	 *
	 * @param originConsignmentBillingRateDO the origin consignment billing rate do
	 * @param destConsignmentBillingRateDO the dest consignment billing rate do
	 * @param destConsgObj the dest consg obj
	 */
	private static void validateAndSetBookingRateFlags(
			ConsignmentBillingRateDO originConsignmentBillingRateDO,
			ConsignmentBillingRateDO destConsignmentBillingRateDO,
			ConsignmentDO originConsgObj) {
		if (isBillingFlagsUpdate(originConsgObj)
				&& StringUtils.equalsIgnoreCase(
						originConsignmentBillingRateDO.getRateCalculatedFor(),
						destConsignmentBillingRateDO.getRateCalculatedFor())
				&& StringUtils.equalsIgnoreCase(
						destConsignmentBillingRateDO.getRateCalculatedFor(),
						CommonConstants.RATE_CALCULATED_FOR_BOOKING)) {
			originConsignmentBillingRateDO
					.setBilled(destConsignmentBillingRateDO.getBilled());
		}
		// For Excess Consignment & weight destination changed after billing
		if ((originConsgObj.getChangedAfterBillingWtDest()
				.equalsIgnoreCase("Y") || originConsgObj
				.getChangedAfterNewRateCmpnt().equalsIgnoreCase("Y"))
				&& originConsgObj.getBillingStatus().equalsIgnoreCase("TBB")
				&& destConsignmentBillingRateDO.getBilled().equalsIgnoreCase(
						"Y")
				&& StringUtils.equalsIgnoreCase(
						originConsignmentBillingRateDO.getRateCalculatedFor(),
						destConsignmentBillingRateDO.getRateCalculatedFor())
				&& StringUtils.equalsIgnoreCase(
						destConsignmentBillingRateDO.getRateCalculatedFor(),
						CommonConstants.RATE_CALCULATED_FOR_BOOKING)) {
			originConsignmentBillingRateDO.setBilled("N");
			destConsignmentBillingRateDO.setBilled("N");
		}
	}

	/**
	 * Validate and set rto rate flags.
	 *
	 * @param originConsignmentBillingRateDO the origin consignment billing rate do
	 * @param destConsignmentBillingRateDO the dest consignment billing rate do
	 */
	private static void validateAndSetRtoRateFlags(
			ConsignmentBillingRateDO originConsignmentBillingRateDO,
			ConsignmentBillingRateDO destConsignmentBillingRateDO) {
		if (StringUtils.equalsIgnoreCase(
				originConsignmentBillingRateDO.getRateCalculatedFor(),
				destConsignmentBillingRateDO.getRateCalculatedFor())
				&& StringUtils.equalsIgnoreCase(
						destConsignmentBillingRateDO.getRateCalculatedFor(),
						CommonConstants.RATE_CALCULATED_FOR_RTO)
				&& StringUtils.equalsIgnoreCase(
						destConsignmentBillingRateDO.getBilled(),
						CommonConstants.YES)) {
			originConsignmentBillingRateDO
					.setBilled(destConsignmentBillingRateDO.getBilled());
		}
	}

	/**
	 * Update billing flags.
	 *
	 * @param originConsgObj the origin consg obj
	 * @param destConsgObj the dest consg obj
	 */
	private static void updateBillingFlags(BcunDatasyncService bcunService, ConsignmentDO originConsgObj,
			ConsignmentDO destConsgObj) {
		Boolean isBillingStatusRetainByBooking = Boolean.FALSE;
		Boolean isBillingStatusRetainByRTO = Boolean.FALSE;

		//String originChangedAfterBillingWtDest = originConsgObj.getChangedAfterBillingWtDest();
		
		if (isBillingFlagsUpdate(originConsgObj)) {
			originConsgObj.setChangedAfterBillingWtDest(destConsgObj
					.getChangedAfterBillingWtDest());
			originConsgObj.setChangedAfterNewRateCmpnt(destConsgObj
					.getChangedAfterNewRateCmpnt());
			// originConsgObj.setBillingStatus(destConsgObj.getBillingStatus());
			isBillingStatusRetainByBooking = Boolean.TRUE;
		} else {
			/*
			 * if bill_status of centralObj = trb retain central flag
			 */
			if (StringUtils.equals(destConsgObj.getBillingStatus(),
					CommonConstants.BILLING_STATUS_TRB)) {
				isBillingStatusRetainByBooking = Boolean.TRUE;
				// originConsgObj.setBillingStatus(destConsgObj.getBillingStatus());
			}
		}

		/*If RTO rate is received
		c) For RTO rate of central - if BILLED==Y then
		   *-> Retain Central BILLING_STATUS
		    -> Retain BILLED flag of central consignment RTO rate

		d) For RTO rate of central - if BILLED==N then
		   *-> If BILLING_STATUS flag of central consignment object is TRB then,
		                Retain Central BILLING_STATUS
		    -> BILLED flag of central consignment RTO rate (Do nothing)

		If RTO rate is not received
		e) *-> Retain Central BILLING_STATUS

		=>>If RTO Rates received 1st time then BillingStatus should be TBB.*/
		
		String incomingRtoBilled = getRtoRateBilledFlag(originConsgObj);
		String centralRtoBilled = getRtoRateBilledFlag(destConsgObj);
		if ((StringUtils.isBlank(incomingRtoBilled) && StringUtils
				.isBlank(centralRtoBilled))
				|| (StringUtils.isNotBlank(incomingRtoBilled) && StringUtils
						.equalsIgnoreCase(centralRtoBilled, CommonConstants.YES))
				|| (StringUtils.isNotBlank(incomingRtoBilled) && (StringUtils
						.equalsIgnoreCase(centralRtoBilled, CommonConstants.NO) && StringUtils
						.equals(destConsgObj.getBillingStatus(),
								CommonConstants.BILLING_STATUS_TRB)))) {
			isBillingStatusRetainByRTO = Boolean.TRUE;
			// originConsgObj.setBillingStatus(destConsgObj.getBillingStatus());
		}

		if (isBillingStatusRetainByBooking && isBillingStatusRetainByRTO) {
			LOGGER.trace("BcunManifestUtils::updateBillingFlags::Retain Central BILLING_STATUS for Consg No :: "
					+ originConsgObj.getConsgNo() + "------------>:::::::");
			originConsgObj.setBillingStatus(destConsgObj.getBillingStatus());
		}
		///////////////////////////////
		if ((!isBillingStatusRetainByBooking || !isBillingStatusRetainByRTO) && StringUtils.equals(destConsgObj.getBillingStatus(),
				CommonConstants.BILLING_STATUS_PFB)) {
			originConsgObj.setBillingStatus(CommonConstants.BILLING_STATUS_TBB);
		}
		///////////////////////////////
		

		// If RTO Rates received 1st time then BillingStatus should be TBB.
		if (StringUtils.isNotBlank(incomingRtoBilled)
				&& StringUtils.isBlank(centralRtoBilled)) {
			LOGGER.trace("BcunManifestUtils::updateBillingFlags::RTO Rates received 1st time for Consg No :: "
					+ originConsgObj.getConsgNo() + "------------>:::::::");
			originConsgObj.setBillingStatus(CommonConstants.BILLING_STATUS_TBB);
		}
		
		/*If bookingType=CASH_BOOKING OR bookingType = EMOTIONAL_BOND_BOOKING OR bookingType= FOC_BOOKING 
		 * OR ChangedAfterBillingWtDest= 'N' OR  ConsignmentCode = 'DOX' OR BOOKING_RATE_BILLED='Y'
				THEN  SET BILLING_STATUS = 'RTB' in consignment table in central DB using BCUN formatter.*/

		/*String originBookingBilled = getBookingRateBilledFlag(originConsgObj);
		BookingTypeDO bookingTypeDO = getBookingBookingType(bcunService, originConsgObj);
		String bookingType = bookingTypeDO!=null?bookingTypeDO.getBookingType():null;
		if(StringUtils.equals(bookingType,CommonConstants.CASH_BOOKING) 
				|| StringUtils.equals(bookingType,CommonConstants.EMOTIONAL_BOND_BOOKING)
				|| StringUtils.equals(bookingType,CommonConstants.FOC_BOOKING)
				|| StringUtils.equals(originChangedAfterBillingWtDest, CommonConstants.NO)
				|| StringUtils.equals(originBookingBilled, CommonConstants.YES)
				|| (originConsgObj.getConsgType()!=null && StringUtils.equals(
						originConsgObj.getConsgType().getConsignmentCode(), CommonConstants.CONSIGNMENT_TYPE_DOCUMENT_CODE))
				){
			originConsgObj.setBillingStatus(CommonConstants.BILLING_STATUS_RTB);
		}*/
	}

	/**
	 * Gets the rto rate billed flag.
	 *
	 * @param consgObj the consg obj
	 * @return the rto rate billed flag
	 */
	private static String getRtoRateBilledFlag(ConsignmentDO consgObj) {
		/*String billed = null;
		for (ConsignmentBillingRateDO consgBillingRateDO : consgObj
				.getConsgRateDtls()) {
			if (StringUtils.equalsIgnoreCase(
					consgBillingRateDO.getRateCalculatedFor(),
					CommonConstants.RATE_CALCULATED_FOR_RTO)) {
				billed = consgBillingRateDO.getBilled();
				break;
			}
		}
		return billed;*/
		return getRateBilledFlag(consgObj,
				CommonConstants.RATE_CALCULATED_FOR_RTO);
	}

	/**
	 * Gets the booking rate billed flag.
	 *
	 * @param consgObj the consg obj
	 * @return the booking rate billed flag
	 */
	@SuppressWarnings("unused")
	private static String getBookingRateBilledFlag(ConsignmentDO consgObj) {
		return getRateBilledFlag(consgObj,
				CommonConstants.RATE_CALCULATED_FOR_BOOKING);
	}
	
	/**
	 * Gets the rate billed flag.
	 *
	 * @param consgObj the consg obj
	 * @param rateCalculatedFor the rate calculated for
	 * @return the rate billed flag
	 */
	private static String getRateBilledFlag(ConsignmentDO consgObj,
			String rateCalculatedFor) {
		String billed = null;
		if(StringUtil.isEmptyColletion(consgObj.getConsgRateDtls())){
			return billed;
		}
		for (ConsignmentBillingRateDO consgBillingRateDO : consgObj
				.getConsgRateDtls()) {
			if (StringUtils.equalsIgnoreCase(
					consgBillingRateDO.getRateCalculatedFor(),
					rateCalculatedFor)) {
				billed = consgBillingRateDO.getBilled();
				break;
			}
		}
		return billed;
	}
	
	/**
	 * Checks if is billing flags update.
	 *
	 * @param destConsgObj the dest consg obj
	 * @return true, if is billing flags update
	 */
	private static boolean isBillingFlagsUpdate(ConsignmentDO originConsgObj) {
	    
		if (StringUtils.equals(originConsgObj.getChangedAfterBillingWtDest(),
				CommonConstants.NO)
				&& StringUtils.equals(
					originConsgObj.getChangedAfterNewRateCmpnt(),
						CommonConstants.NO)) {
			return true;
		}
		return false;
		/*if ChangedAfterBillingWtDest=N and ChangedAfterNewRateCmpnt=N of incoming objecgt then
				Retain Central flag */
		/*
		For true case :
		 Do not set BILLING_STATUS, CHANGED_AFTER_BILLING_WEIGHT_DEST, CHANGED_AFTER_NEW_RATE_COMPONENT flags of central consignment object (FF_F_CONSIGNMENT table) 
		 Do not set BILLED flag of central consignment rate (FF_F_CONSIGNMENT_RATE table) using inbound consignment rate object - for booking rate
		 ==>that means update incoming object with destination object flags
		 
		For false case :
			 Do not set BILLING_STATUS, CHANGED_AFTER_BILLING_WEIGHT_DEST, CHANGED_AFTER_NEW_RATE_COMPONENT flags of central consignment object (FF_F_CONSIGNMENT table) 
			 Do not set BILLED flag of central consignment rate (FF_F_CONSIGNMENT_RATE table) using inbound consignment rate object - for booking rate
			 ==>No need to do anything*/
	}

	/**
	 * Update consignee.
	 * 
	 * @param originConsgObj
	 *            the origin consg obj
	 * @param destConsgObj
	 *            the dest consg obj
	 */
	private static void updateConsignee(ConsignmentDO originConsgObj,
			ConsignmentDO destConsgObj) {
		LOGGER.trace("BcunManifestUtils::updateConsignee::START------------>:::::::");
		if (originConsgObj.getConsignee() != null
				&& destConsgObj.getConsignee() != null) {
			originConsgObj.getConsignee().setPartyId(
					destConsgObj.getConsignee().getPartyId());
			if (StringUtils.isBlank(originConsgObj.getConsignee().getMobile())) {
				originConsgObj.getConsignee().setMobile(
						destConsgObj.getConsignee().getMobile());
			}
			if (StringUtils.isBlank(originConsgObj.getConsignee().getPhone())) {
				originConsgObj.getConsignee().setPhone(
						destConsgObj.getConsignee().getPhone());
			}

		} else if (originConsgObj.getConsignee() != null) {
			originConsgObj.getConsignee().setPartyId(null);

		} else {
			originConsgObj.setConsignee(destConsgObj.getConsignee());
		}
		LOGGER.trace("BcunManifestUtils::updateConsignee::END------------>:::::::");
	}

	/**
	 * Update consignor.
	 * 
	 * @param originConsgObj
	 *            the origin consg obj
	 * @param destConsgObj
	 *            the dest consg obj
	 */
	private static void updateConsignor(ConsignmentDO originConsgObj,
			ConsignmentDO destConsgObj) {
		LOGGER.trace("BcunManifestUtils::updateConsignor::START------------>:::::::");
		if (originConsgObj.getConsignor() != null
				&& destConsgObj.getConsignor() != null) {
			originConsgObj.getConsignor().setPartyId(
					destConsgObj.getConsignor().getPartyId());
			if (StringUtils.isBlank(originConsgObj.getConsignor().getMobile())) {
				originConsgObj.getConsignor().setMobile(
						destConsgObj.getConsignor().getMobile());
			}
			if (StringUtils.isBlank(originConsgObj.getConsignor().getPhone())) {
				originConsgObj.getConsignor().setPhone(
						destConsgObj.getConsignor().getPhone());
			}

		} else if (originConsgObj.getConsignor() != null) {
			originConsgObj.getConsignor().setPartyId(null);

		} else {
			originConsgObj.setConsignor(destConsgObj.getConsignor());
		}
		LOGGER.trace("BcunManifestUtils::updateConsignor::END------------>:::::::");
	}

	/**
	 * Sets the default value to consg.
	 * 
	 * @param consgDO
	 *            the new default value to consg
	 */
	private static void setDefaultValueToConsg(ConsignmentDO consgDO) {
		if (StringUtils.isBlank(consgDO.getConsgStatus())) {
			consgDO.setConsgStatus("B");
		}
		consgDO.setDtToCentral(CommonConstants.YES);
	}

	/**
	 * Creates the consignment.
	 * 
	 * @param bcunService
	 *            the bcun service
	 * @param consignmentDO
	 *            the consignment do
	 * @return the consignment do
	 */
	@SuppressWarnings("unchecked")
	private static ConsignmentDO createConsignment(
			BcunDatasyncService bcunService, ConsignmentDO consignmentDO) {

		LOGGER.trace("BcunManifestUtils::getConsignment::START------------>:::::::");
		consignmentDO.setConsgId(null);

		// Set Child Consingment
		if (!StringUtil.isEmptyColletion(consignmentDO.getChildCNs())) {
			for (ChildConsignmentDO childConsignmentDO : consignmentDO
					.getChildCNs()) {
				childConsignmentDO.setBookingChildCNId(null);
			}
		}

		// Set Consignment Billing Rate
		if (!StringUtil.isEmptyColletion(consignmentDO.getConsgRateDtls())) {
			for (ConsignmentBillingRateDO consignmentBillingRateDO : consignmentDO
					.getConsgRateDtls()) {
				consignmentBillingRateDO.setConsignmentRateId(null);
			}
			/** Temp change for BA Booking */
			if (!StringUtil.isEmptyInteger(consignmentDO.getCustomer())) {
				List<CustomerDO> custDOs = (List<CustomerDO>) bcunService
						.getDataByNamedQueryAndNamedParam("getCustomer",
								"customerId", consignmentDO.getCustomer());
				CustomerDO customerDO = (!StringUtil.isEmptyList(custDOs)) ? custDOs
						.get(0) : null;
				if (!StringUtil.isNull(customerDO)) {
					String custType = customerDO.getCustomerType()
							.getCustomerTypeCode();
					if (custType.equalsIgnoreCase("BA")
							|| custType.equalsIgnoreCase("BV")) {
						consignmentDO.setConsgRateDtls(null);
					}
				}
			}
		
		}
		if (consignmentDO.getConsignee() != null) {
			consignmentDO.getConsignee().setPartyId(null);
		}
		if (consignmentDO.getConsignor() != null) {
			consignmentDO.getConsignor().setPartyId(null);
		}
		if (consignmentDO.getAltConsigneeAddr() != null) {
			consignmentDO.getAltConsigneeAddr().setPartyId(null);
		}
		
		//as mail of kaustubh Not required to set below fields as its causing prob while billing
		//setBillingFlags(bcunService, consignmentDO);
		
		LOGGER.trace("BcunManifestUtils::getConsignment::End------------>:::::::");
		return consignmentDO;
	}

	/**
	 * Sets the billing flags.
	 *
	 * @param bcunService the bcun service
	 * @param consignmentDO the consignment do
	 */
	@SuppressWarnings("unused")
	private static void setBillingFlags(BcunDatasyncService bcunService,
			ConsignmentDO consignmentDO) {
		if (!isInBoundProcess(bcunService)) {
			return;
		}
		consignmentDO.setChangedAfterBillingWtDest(CommonConstants.NO);
		consignmentDO.setChangedAfterNewRateCmpnt(CommonConstants.NO);
	}
	private static void validateMandatoryDataForConsignment(ConsignmentDO consgDo) throws CGBusinessException{

		if(StringUtil.isStringEmpty(consgDo.getConsgNo()) || consgDo.getConsgType()==null || consgDo.getDestPincodeId() ==null || StringUtil.isEmptyInteger(consgDo.getOrgOffId())||StringUtil.isEmptyInteger(consgDo.getOperatingOffice()) || StringUtil.isEmptyInteger(consgDo.getProductId()) ){
			StringBuilder consgDtls= new StringBuilder();
			consgDtls.append("ConsgNo:");
			consgDtls.append(StringUtil.isStringEmpty(consgDo.getConsgNo()) ?"NO-CN Exist":consgDo.getConsgNo());
			consgDtls.append(" consignment Type:");
			consgDtls.append(consgDo.getConsgType()!=null?consgDo.getConsgType().getConsignmentCode():"null");
			consgDtls.append(",");
			consgDtls.append("Destination Pincode :");
			consgDtls.append(consgDo.getDestPincodeId()!=null?consgDo.getDestPincodeId().getPincode():"null");
			consgDtls.append(",");
			consgDtls.append("Origin office:");
			consgDtls.append(consgDo.getOrgOffId()!=null ? consgDo.getOrgOffId():"null");
			consgDtls.append(",");
			consgDtls.append("Operating office :");
			consgDtls.append(consgDo.getOperatingOffice()!=null?consgDo.getOperatingOffice():"null");
			consgDtls.append(",");
			consgDtls.append(" Product :");
			consgDtls.append(consgDo.getProductId()!=null?consgDo.getProductId():"null");
			consgDtls.append(",");
			consgDo.setMandatoryFlag(CommonConstants.YES);
			LOGGER.error("BcunManifestUtils::validateMandatoryDataForConsignment is missing------------>::::::: consignment dtls"+consgDtls);
		}

	}
}
