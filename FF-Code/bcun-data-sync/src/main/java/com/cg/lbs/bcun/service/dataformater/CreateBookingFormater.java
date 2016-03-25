package com.cg.lbs.bcun.service.dataformater;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.booking.BcunBookingDO;
import com.ff.domain.booking.BcunBookingPreferenceMappingDO;
import com.ff.domain.booking.BookingDuplicateDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.InsuredByDO;

public class CreateBookingFormater extends AbstractDataFormater {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CreateBookingFormater.class);

	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) throws CGBusinessException, CGSystemException {
		LOGGER.trace("CreateBookingFormater::formatInsertData::START------------>:::::::");
		formatUpdateData(baseDO, bcunService);
		LOGGER.trace("CreateBookingFormater::formatInsertData::END------------>:::::::");
		return baseDO;
	}

	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) throws CGBusinessException, CGSystemException {
		LOGGER.trace("CreateBookingFormater::formatUpdateData::START------------>:::::::");
		BcunBookingDO headerDO = (BcunBookingDO) baseDO;
		getAndSetBooking(bcunService, headerDO);		
		LOGGER.trace("CreateBookingFormater::formatUpdateData::END------------>:::::::");
		return headerDO;
	}

	/**
	 * Gets the and set booking.
	 *
	 * @param bcunService the bcun service
	 * @param sourceBookingDO the source booking do
	 * @return the and set booking
	 * @throws CGSystemException 
	 * @throws CGBusinessException 
	 */
	private static void getAndSetBooking(BcunDatasyncService bcunService,
			BcunBookingDO sourceBookingDO) throws CGSystemException, CGBusinessException {
		LOGGER.info("CreateBookingFormater::getAndSetBooking::START------------>:::::::");
		try {
			BcunBookingDO destBookingDO = getBookingDtls(bcunService,
					sourceBookingDO);
			if(destBookingDO!=null && !StringUtil.isEmptyInteger(destBookingDO.getCreatedBy()) && (destBookingDO.getCreatedBy().intValue()== FrameworkConstants.FFCL_ADMIN_USER_ID || destBookingDO.getCreatedBy().intValue()== FrameworkConstants.FFCL_RE_BILLING_USER_ID)){
				sourceBookingDO=destBookingDO;
				/**
				 * If existing booking details have already been updated/created by consignment-modification screen then ignore the XML Booking DO ie no updated in existing booking details
				 * */
				return;
			}
			if (!StringUtil.isNull(sourceBookingDO)
					&& !StringUtil.isNull(destBookingDO)) {// / officeid should
															// be equal
				if (sourceBookingDO.getBookingOfficeId().equals(
						destBookingDO.getBookingOfficeId())) {
					updateBookingData(bcunService, destBookingDO,
							sourceBookingDO);
				} else {
					errorOutDuplicateBooking(bcunService, sourceBookingDO);
				}
				
				/*Integer consgOrgOffId = getConsgOriginOffIdByConsgNo(
						bcunService, sourceBookingDO);
				if (StringUtil.isEmptyInteger(consgOrgOffId)
						|| (!StringUtil.isEmptyInteger(consgOrgOffId)
								&& !StringUtil.isEmptyInteger(sourceBookingDO
										.getBookingOfficeId()) && sourceBookingDO
								.getBookingOfficeId().equals(consgOrgOffId))) {

					createBookingData(bcunService, sourceBookingDO);
				} else {
					errorOutDuplicateBooking(bcunService, sourceBookingDO);
				}*/
		

			}else if (StringUtil.isNull(destBookingDO)) {

				ConsignmentDO destConsignmentDO = getConsignmentDtls(
						bcunService, sourceBookingDO);

				if (StringUtil.isNull(destConsignmentDO)) {
					createBookingData(bcunService, sourceBookingDO);
				} else {
					if (destConsignmentDO.getIsExcessConsg().equals("Y")) {
						createBookingData(bcunService, sourceBookingDO);
						updateConsgDetails(bcunService, sourceBookingDO,
								destConsignmentDO);
					} else {
						Integer consgOrgOffId = destConsignmentDO.getOrgOffId();
						if (StringUtil.isEmptyInteger(consgOrgOffId)
								|| (!StringUtil.isEmptyInteger(consgOrgOffId)
										&& !StringUtil
												.isEmptyInteger(sourceBookingDO
														.getBookingOfficeId()) && sourceBookingDO
										.getBookingOfficeId().equals(
												consgOrgOffId))) {
							createBookingData(bcunService, sourceBookingDO);

						} else {
							errorOutDuplicateBooking(bcunService,
									sourceBookingDO);
						}
					}

				}
			}

			//Billing related validation
			//Update consg billing status as TBB in consignment if Billing status is PFB
			updateConsgBillinigStatus(bcunService, sourceBookingDO.getConsgNumber());

		} catch (Exception e) {
			LOGGER.error(
					"Exception happened in getAndSetBooking of CreateBookingFormater...",
					e);
			throw e;
		}
		LOGGER.info("CreateBookingFormater::getAndSetBooking::END------------>:::::::");
	}

	private static void updateConsgBillinigStatus(
			BcunDatasyncService bcunService, String consgNumber) throws CGSystemException {
		//try {
			boolean update = bcunService.updateConsgBillingStatus(consgNumber);
			LOGGER.info("CreateBookingFormater::updateConsgBillinigStatus::consgNumber::"+consgNumber+" update::"+update+"------------>:::::::");			
		/*} catch (Exception e) {
			LOGGER.error("Exception happened in updateConsgBillinigStatus of CreateBookingFormater...",e);
			//TODO need to throw exception
			throw new CGBusinessException("Exception happened in updateConsgBillinigStatus of consgNumber :: "+consgNumber+" in CreateBookingFormater");
		}*/
	}

	private static void updateConsgDetails(BcunDatasyncService bcunService,
			BcunBookingDO sourceBookingDO, ConsignmentDO destConsignmentDO) {
		// TODO Auto-generated method stub
		updateConsgFields(sourceBookingDO, destConsignmentDO);
		bcunService.saveOrUpdateTransferedEntity(destConsignmentDO);

	}

	private static void updateConsgFields(BcunBookingDO sourceBookingDO,
			ConsignmentDO destConsignmentDO) {
		// TODO Auto-generated method stub
		//if(!StringUtil.isEmptyDouble(sourceBookingDO.getLength())){
			destConsignmentDO.setLength(sourceBookingDO.getLength());
		
		//if(!StringUtil.isEmptyDouble(sourceBookingDO.getBreath())){
			destConsignmentDO.setBreath(sourceBookingDO.getBreath());
		
		//if(!StringUtil.isEmptyDouble(sourceBookingDO.getHeight())){
			destConsignmentDO.setHeight(sourceBookingDO.getHeight());
		
		//if(!StringUtil.isEmptyDouble(sourceBookingDO.getDeclaredValue())){
			destConsignmentDO.setDeclaredValue(sourceBookingDO.getDeclaredValue());
		
		if (!StringUtil.isEmptyInteger(sourceBookingDO.getCnContentId())) {
			if (destConsignmentDO.getCnContentId() != null) {
				destConsignmentDO.getCnContentId().setCnContentId(
						sourceBookingDO.getCnContentId());
			} else {
				CNContentDO cnContentDO = new CNContentDO();
				cnContentDO.setCnContentId(sourceBookingDO.getCnContentId());
				destConsignmentDO.setCnContentId(cnContentDO);
			}
		}else{
			destConsignmentDO.setCnContentId(null);
		}
            
		if (!StringUtil.isEmptyInteger(sourceBookingDO.getConsgTypeId())) {
			if (destConsignmentDO.getConsgType() != null) {
				destConsignmentDO.getConsgType().setConsignmentId(
						sourceBookingDO.getConsgTypeId());
			} else {
				ConsignmentTypeDO cnTypeDO = new ConsignmentTypeDO();
				cnTypeDO.setConsignmentId(sourceBookingDO.getConsgTypeId());
				destConsignmentDO.setConsgType(cnTypeDO);
			}
		}else{
			destConsignmentDO.setConsgType(null);
		}
		
		if (!StringUtil.isEmptyInteger(sourceBookingDO.getPincodeId())) {
			if (destConsignmentDO.getDestPincodeId() != null) {
				destConsignmentDO.getDestPincodeId().setPincodeId(
						sourceBookingDO.getPincodeId());
			} else {
				PincodeDO pincodeDO = new PincodeDO();
				pincodeDO.setPincodeId(sourceBookingDO.getPincodeId());
				destConsignmentDO.setDestPincodeId(pincodeDO);
			}
		}else{
			destConsignmentDO.setDestPincodeId(null);
		}
		
		if (!StringUtil.isEmptyInteger(sourceBookingDO.getCnPaperWorkId())) {
			if (destConsignmentDO.getCnPaperWorkId() != null) {
				destConsignmentDO.getCnPaperWorkId().setCnPaperWorkId(
						sourceBookingDO.getCnPaperWorkId());
			} else {
				CNPaperWorksDO worksDO = new CNPaperWorksDO();
				worksDO.setCnPaperWorkId(sourceBookingDO.getCnPaperWorkId());
				destConsignmentDO.setCnPaperWorkId(worksDO);
			}
		}else{
			destConsignmentDO.setCnPaperWorkId(null);
		}
		
		if (!StringUtil.isEmptyInteger(sourceBookingDO.getInsuredBy())) {
			if (destConsignmentDO.getInsuredBy() != null) {
				destConsignmentDO.getInsuredBy().setInsuredById(
						sourceBookingDO.getInsuredBy());
			} else {
				InsuredByDO byDO = new InsuredByDO();
				byDO.setInsuredById(sourceBookingDO.getInsuredBy());
				destConsignmentDO.setInsuredBy(byDO);
			}
		}else{
			destConsignmentDO.setInsuredBy(null);
		}

		//if(!StringUtil.isStringEmpty(sourceBookingDO.getOtherCNContent())){
			destConsignmentDO.setOtherCNContent(sourceBookingDO.getOtherCNContent());
		
		//if(!StringUtil.isStringEmpty(sourceBookingDO.getInsurencePolicyNo())){
			destConsignmentDO.setInsurencePolicyNo(sourceBookingDO.getInsurencePolicyNo());
		
		//if(!StringUtil.isEmptyInteger(sourceBookingDO.getNoOfPieces())){
			destConsignmentDO.setNoOfPcs(sourceBookingDO.getNoOfPieces());
		
		//if(!StringUtil.isEmptyDouble(sourceBookingDO.getPrice())){
			destConsignmentDO.setPrice(sourceBookingDO.getPrice());
	
		//if(!StringUtil.isEmptyInteger(sourceBookingDO.getOriginOfficeId())){
			destConsignmentDO.setOrgOffId(sourceBookingDO.getOriginOfficeId());
	
		//if(!StringUtil.isEmptyInteger(sourceBookingDO.getCustomerId())){
			destConsignmentDO.setCustomer(sourceBookingDO.getCustomerId());
		
			
		if (!StringUtil.isEmptyDouble(sourceBookingDO.getActualWeight())
				&& !StringUtil.isEmptyDouble(destConsignmentDO
						.getActualWeight()) &&
						sourceBookingDO.getActualWeight() > destConsignmentDO
						.getActualWeight()){
			destConsignmentDO.setActualWeight(sourceBookingDO.getActualWeight());		
		}
		if (!StringUtil.isEmptyDouble(sourceBookingDO.getVolWeight())
				&& !StringUtil.isEmptyDouble(destConsignmentDO
						.getVolWeight()) &&
						sourceBookingDO.getVolWeight() > destConsignmentDO
						.getVolWeight()){
			destConsignmentDO.setVolWeight(sourceBookingDO.getVolWeight());		
		}
		if (!StringUtil.isEmptyDouble(sourceBookingDO.getFianlWeight())
				&& !StringUtil.isEmptyDouble(destConsignmentDO
						.getFinalWeight()) &&
						sourceBookingDO.getFianlWeight() > destConsignmentDO
						.getFinalWeight()){
			destConsignmentDO.setFinalWeight(sourceBookingDO.getFianlWeight());		
		}
		
	}

	private static ConsignmentDO getConsignmentDtls(
			BcunDatasyncService bcunService, BcunBookingDO sourceBookingDO) {

		String[] params = { "consgNo" };
		Object[] values = { sourceBookingDO.getConsgNumber() };
		@SuppressWarnings("unchecked")
		List<ConsignmentDO> destConsgDos = (List<ConsignmentDO>) bcunService
				.getDataByNamedQueryAndNamedParam("getConsignmentByConsgNo", params,
						values);
		ConsignmentDO destConsgDo = (!StringUtil
				.isEmptyColletion(destConsgDos)) ? destConsgDos.get(0) : null;

		return destConsgDo;

	}

	/**
	 * Gets the consg origin off id by consg no.
	 *
	 * @param bcunService the bcun service
	 * @param sourceBookingDO the source booking do
	 * @return the consg origin off id by consg no
	 */
	
	/*Commented because of private method was not in use in local class
	 * 
	 * private static Integer getConsgOriginOffIdByConsgNo(
			BcunDatasyncService bcunService, BcunBookingDO sourceBookingDO) {
		LOGGER.trace("CreateBookingFormater::getConsgOriginOffIdByConsgNo::START------------>:::::::");
		String[] params = { "consgNumber", };
		Object[] values = { sourceBookingDO.getConsgNumber() };

		Integer consgOrgOffId = bcunService.getUniqueId(
				"getConsgOriginOffIdByConsgNo", params, values);
		LOGGER.trace("CreateBookingFormater::getConsgOriginOffIdByConsgNo::END------------>:::::::");
		return consgOrgOffId;
	}*/

	/**
	 * Error out duplicate booking.
	 *
	 * @param bcunService the bcun service
	 * @param sourceBookingDO the source booking do
	 * @throws CGBusinessException the cG business exception
	 */
	private static void errorOutDuplicateBooking(
			BcunDatasyncService bcunService, BcunBookingDO sourceBookingDO)
			throws CGBusinessException {
		LOGGER.error("CreateBookingFormater::errorOutDuplicateBooking::Saving Duplicate booking in dulpicate table.------------>:::::::");
		CGBaseDO cgBaseDO = getBookingDuplicateDoDtls(sourceBookingDO);
		bcunService.saveOrUpdateTransferedEntity(cgBaseDO);
//		throw new CGBusinessException(
//				"Data in Both the Booking Office's for Src and destn is not equal !");
		// Error out the invalid booking
		throw new CGBusinessException(
				"Invalid booking office Id :: Both central and incoming object booking office must be same.");
	}

	/**
	 * Gets the booking duplicate do dtls.
	 *
	 * @param sourceBookingDO the source booking do
	 * @return the booking duplicate do dtls
	 */
	private static CGBaseDO getBookingDuplicateDoDtls(
			BcunBookingDO sourceBookingDO) {

		LOGGER.trace("CreateBookingFormater::getBookingDuplicateDoDtls::START------------>:::::::");
		BookingDuplicateDO bookingDuplicateDO = new BookingDuplicateDO();
		try {
			PropertyUtils.copyProperties(bookingDuplicateDO, sourceBookingDO);
			bookingDuplicateDO.setBookingId(null);
		} catch (Exception e) {
			LOGGER.error(
					"Data in Both the Booking Office's for Src and destn is not equal !",
					e);
		}
		LOGGER.trace("CreateBookingFormater::getBookingDuplicateDoDtls::END------------>:::::::");

		return bookingDuplicateDO;
	}

	/**
	 * Gets the booking dtls.
	 *
	 * @param bcunService the bcun service
	 * @param bookingDO the booking do
	 * @return the booking dtls
	 */
	private static BcunBookingDO getBookingDtls(
			BcunDatasyncService bcunService, BcunBookingDO bookingDO) {
		String[] params = { "consgNumber" };
		Object[] values = { bookingDO.getConsgNumber() };
		@SuppressWarnings("unchecked")
		List<BcunBookingDO> destBookingDOs = (List<BcunBookingDO>) bcunService
				.getDataByNamedQueryAndNamedParam("getBookingDtls", params,
						values);
		BcunBookingDO destBookingDO = (!StringUtil
				.isEmptyColletion(destBookingDOs)) ? destBookingDOs.get(0)
				: null;

		return destBookingDO;
	}

	/**
	 * Creates the booking data.
	 *
	 * @param bcunService the bcun service
	 * @param sourceBookingDO the source booking do
	 */
	private static void createBookingData(BcunDatasyncService bcunService,
			BcunBookingDO sourceBookingDO) {
		sourceBookingDO.setBookingId(null);

		if (!StringUtil.isNull(sourceBookingDO.getBookingPayment())) {
			sourceBookingDO.getBookingPayment().setBookingPaymentId(null);
		}
		if (!StringUtil.isNull(sourceBookingDO.getBulkBookingVendorDtls())) {
			sourceBookingDO.getBulkBookingVendorDtls()
					.setBulkBookingVendorDtlsId(null);
			sourceBookingDO.getBulkBookingVendorDtls().setBookingId(null);
		}
		
		createBookingPrefs(sourceBookingDO);
	}

	/**
	 * Update booking data.
	 *
	 * @param bcunService the bcun service
	 * @param destBookingDO the dest booking do :: branch/central
	 * @param sourceBookingDO the source booking do :: incoming
	 */
	private static void updateBookingData(BcunDatasyncService bcunService,
			BcunBookingDO destBookingDO, BcunBookingDO sourceBookingDO) {

		sourceBookingDO.setBookingId(destBookingDO.getBookingId());

		// Setting Booking Payment Mode id
		if (!StringUtil.isNull(sourceBookingDO.getBookingPayment()) 
				&& !StringUtil.isNull(destBookingDO.getBookingPayment())) {
			sourceBookingDO.getBookingPayment().setBookingPaymentId(
					destBookingDO.getBookingPayment().getBookingPaymentId());
		} 
//		else {
//			sourceBookingDO.setBookingPayment(destBookingDO.getBookingPayment());
//		}

		// Setting Bulk booking ID
		if (!StringUtil.isNull(sourceBookingDO.getBulkBookingVendorDtls())
				&& !StringUtil.isNull(destBookingDO.getBulkBookingVendorDtls())) {
			sourceBookingDO.getBulkBookingVendorDtls()
					.setBulkBookingVendorDtlsId(
							destBookingDO.getBulkBookingVendorDtls()
									.getBulkBookingVendorDtlsId());
			sourceBookingDO.getBulkBookingVendorDtls().setBookingId(
					destBookingDO.getBookingId());
		} 
//		else {
//			sourceBookingDO.setBulkBookingVendorDtls(destBookingDO.getBulkBookingVendorDtls());
//		}

		// setting booking preference details.
		if (!StringUtil.isNull(sourceBookingDO.getBokingPrefs())
				&& !StringUtil.isNull(destBookingDO.getBokingPrefs())) {

			for (BcunBookingPreferenceMappingDO sourceBookingPrefMappingDO : sourceBookingDO
					.getBokingPrefs()) {
				sourceBookingPrefMappingDO.setBookingId(destBookingDO
						.getBookingId());
				sourceBookingPrefMappingDO.setBookingPreId(null);
				
//				Iterator<BcunBookingPreferenceMappingDO> detSetItr = destBookingDO.getBokingPrefs().iterator();
//				while (detSetItr.hasNext()) {
//					BcunBookingPreferenceMappingDO destBookingPrefMap = (BcunBookingPreferenceMappingDO) detSetItr
//							.next();
//					if (!StringUtil.isNull(sourceBookingMappingDO)
//							&& !StringUtil.isNull(destBookingPrefMap)
//							&& sourceBookingMappingDO.getReferenceId().equals(
//									destBookingPrefMap.getReferenceId()))
//						sourceBookingMappingDO
//								.setBookingPreId(destBookingPrefMap
//										.getBookingPreId());
//					break;
//				}
				for (BcunBookingPreferenceMappingDO destBookingPrefMap : destBookingDO
						.getBokingPrefs()) {
					if (sourceBookingPrefMappingDO.getReferenceId() != null
							&& destBookingPrefMap.getReferenceId() != null
							&& sourceBookingPrefMappingDO
									.getReferenceId()
									.equals(destBookingPrefMap.getReferenceId())) {
						sourceBookingPrefMappingDO
								.setBookingPreId(destBookingPrefMap
										.getBookingPreId());
						break;
					}
				}
			}
		} else if (!StringUtil.isNull(sourceBookingDO.getBokingPrefs())
				&& StringUtil.isNull(destBookingDO.getBokingPrefs())) {
			createBookingPrefs(sourceBookingDO);
		} else {
			sourceBookingDO.setBokingPrefs(destBookingDO.getBokingPrefs());
		}

	}

	/**
	 * Creates the booking prefs.
	 *
	 * @param sourceBookingDO the source booking do
	 */
	private static void createBookingPrefs(BcunBookingDO sourceBookingDO) {
		if (!StringUtil.isEmptyColletion(sourceBookingDO.getBokingPrefs())) {
			for (BcunBookingPreferenceMappingDO bookingPreMappingDO : sourceBookingDO
					.getBokingPrefs()) {
				bookingPreMappingDO.setBookingPreId(null);
				bookingPreMappingDO
						.setBookingId(sourceBookingDO.getBookingId());
			}
		}
	}

}
