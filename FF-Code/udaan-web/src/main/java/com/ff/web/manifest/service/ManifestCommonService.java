package com.ff.web.manifest.service;

import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.BookingConsignmentDO;
import com.ff.domain.manifest.GridItemOrderDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.pickup.PickupRunsheetHeaderDO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.OutManifestValidate;

/**
 * @author hkansagr
 * 
 */
public interface ManifestCommonService {

	/**
	 * To get Manifest Details
	 * 
	 * @param manifestDO
	 * @return manifestDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	ManifestDO getManifest(ManifestDO manifestDO) throws CGBusinessException,
			CGSystemException;

	ManifestDO getManifestForCreation(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get Dox Manifest Details
	 * 
	 * @param manifestDO
	 * @return manifestDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	ManifestDO getDoxManifest(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get Parcel Manifest Details
	 * 
	 * @param manifestDO
	 * @return manifestDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	ManifestDO getParcelManifest(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get Embedded In Manifest Details
	 * 
	 * @param manifestDO
	 * @return manifestDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	ManifestDO getEmbeddedInManifest(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get Parcel and Embedded In Manifest Details
	 * 
	 * @param manifestDO
	 * @return manifestDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	ManifestDO getParcelEmbeddedInManifest(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException;

	/**
	 * To save manifest and manifest process details
	 * 
	 * @param manifestDO
	 * @param manifestProcessDO
	 * @return boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	boolean saveManifest(ManifestDO manifestDO) throws CGBusinessException,
			CGSystemException;

	/**
	 * To update manifest and manifest process details
	 * 
	 * @param manifestDO
	 * @param manifestProcessDO
	 * @return boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	boolean updateManifest(ManifestDO manifestDO) throws CGBusinessException,
			CGSystemException;

	/**
	 * To get consignments by consg. nos.
	 * 
	 * @param consgNos
	 * @return consgDOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<ConsignmentDO> getConsignments(List<String> consgNos)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get ManifestDOs
	 * 
	 * @param manifestNos
	 * @param officeId
	 * @param manifestProcessType
	 * @param manifestDirection
	 * @return manifestDOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<ManifestDO> getManifests(List<String> manifestNos, Integer officeId)
			throws CGBusinessException, CGSystemException;

	ManifestDO getOutDoxManifest(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException;

	/**
	 * To save manifest Process details
	 * 
	 * @param manifesProcessDO
	 * @return boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	/*boolean saveManifestProcess(ManifestProcessDO manifesProcessDO)
			throws CGBusinessException, CGSystemException;*/

	/**
	 * To get Booking ConsignmentDOs
	 * 
	 * @param consgNos
	 * @return bookingConsignmentDOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<BookingConsignmentDO> getBookingConsignmentDO(List<String> consgNos)
			throws CGBusinessException, CGSystemException;

	ConsignmentDO getConsignment(String consgNo) throws CGBusinessException,
			CGSystemException;

	BookingDO getBookingConsignment(String consgNo) throws CGBusinessException,
			CGSystemException;

	List<ManifestDO> getMisrouteManifests(List<String> manifestNOList,
			Integer loginOfficeId) throws CGBusinessException,
			CGSystemException;

	ManifestDO getManifestDtls(ManifestInputs manifestTOs)
			throws CGBusinessException, CGSystemException;

	List<BookingDO> getBookings(List<String> cnNumbers)
			throws CGBusinessException, CGSystemException;

	void saveOrUpdateBookings(List<BookingDO> allBooking,
			List<ConsignmentDO> bookingConsignment) throws CGBusinessException,
			CGSystemException;

	GridItemOrderDO arrangeOrder(GridItemOrderDO gridItemOrderDO,
			String operation) throws CGBusinessException, CGSystemException;

	void isManifestExist(ManifestDO manifestDO) throws CGBusinessException,
			CGSystemException;

	/**
	 * To check whether manifest is exist or not.
	 * 
	 * @param manifestTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	void isManifestExist(ManifestInputs manifestTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * To search consg. billing rate DOs
	 * 
	 * @param pickupConsgNos
	 * @return consignmentBillingRateDOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<ConsignmentBillingRateDO> searchConsgBillingRateDtls(
			List<String> pickupConsgNos) throws CGBusinessException,
			CGSystemException;

	/**
	 * To save or Update Consignment Billing Rate Details for Consignments
	 * 
	 * @param consgBillingRateDOs
	 * @param consgDOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	void saveOrUpdateConsgBillingRateDtls(
			List<ConsignmentBillingRateDO> consgBillingRateDOs,
			Set<ConsignmentDO> consgDOs) throws CGBusinessException,
			CGSystemException;

	List<ConsignmentDO> getConsignmentsAndEvictFromSession(List<String> consgNos)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param consignmentDO
	 * @param updatedIn
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	void updateBillingFlagsInConsignment(ConsignmentDO consignmentDO,
			String updatedIn) throws CGBusinessException, CGSystemException;

	/**
	 * @param allBooking
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	boolean saveOrUpdateBooking(List<BookingDO> allBooking)
			throws CGBusinessException, CGSystemException;

	ManifestDO getManifestDetailsWithFetchProfile(ManifestBaseTO manifestBaseTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * To validate scanned manifest no in header during scanning consg. in grid
	 * 
	 * @param cnValidateTO
	 * @return boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	boolean isValidateScanedManifestNo(OutManifestValidate cnValidateTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param consgNos
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	PickupRunsheetHeaderDO getPickupRunsheetHeaderByConsignmentNo(
			String consgNos) throws CGBusinessException, CGSystemException;

	/**
	 * @param pickupRunsheetHeader
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	boolean saveOrUpdatePickupRunsheetHeaderDetails(
			Set<PickupRunsheetHeaderDO> pickupRunsheetHeader)throws CGBusinessException, CGSystemException;

	/**
	 * Gets the manifest details.
	 *
	 * @param manifestInputs the manifest inputs
	 * @return the manifest details
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ManifestDO getManifestDetails(ManifestInputs manifestInputs)
			throws CGBusinessException, CGSystemException;
	
}
