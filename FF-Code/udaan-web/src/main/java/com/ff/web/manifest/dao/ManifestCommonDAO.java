package com.ff.web.manifest.dao;

import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.BookingConsignmentDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.pickup.PickupRunsheetHeaderDO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.OutManifestValidate;

/**
 * @author hkansagr
 * 
 */
public interface ManifestCommonDAO {

	/**
	 * To get Manifest Details
	 * 
	 * @param manifestDO
	 * @return manifestDO
	 * @throws CGSystemException
	 */
	ManifestDO getManifest(ManifestDO manifestDO) throws CGSystemException;

	/**
	 * To get Manifest fetch profile
	 * 
	 * @param manifestDO
	 * @param fetchProfile
	 * @return manifestDO
	 * @throws CGSystemException
	 */
	ManifestDO getManifestDtlsByFetchProfile(ManifestDO manifestDO,
			String fetchProfile) throws CGSystemException;

	/**
	 * To save or update manifest details
	 * 
	 * @param manifestDO
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean saveOrUpdateManifest(ManifestDO manifestDO)
			throws CGSystemException;

	/**
	 * To get consignments by consg. nos.
	 * 
	 * @param consgNos
	 * @return consgDOs
	 * @throws CGSystemException
	 */
	List<ConsignmentDO> getConsignments(List<String> consgNos)
			throws CGSystemException;

	/**
	 * To get ManifestDOs
	 * 
	 * @param manifestNos
	 * @param officeId
	 * @param manifestProcessType
	 * @param manifestDirection
	 * @return manifestDOs
	 * @throws CGSystemException
	 */
	List<ManifestDO> getManifests(List<String> manifestNos, Integer officeId)
			throws CGSystemException;

	/**
	 * To save or update manifest process details
	 * 
	 * @param manifestDO
	 * @return boolean
	 * @throws CGSystemException
	 */

	/*boolean saveOrUpdateManifestProcess(ManifestProcessDO manifestProcessDO)
			throws CGSystemException;*/

	/**
	 * To get Booking ConsignmentDOs
	 * 
	 * @param consgNos
	 * @return BookingConsignmentDOs
	 * @throws CGSystemException
	 */
	List<BookingConsignmentDO> getBookingConsignmentDO(List<String> consgNos)
			throws CGSystemException;

	ConsignmentDO getConsignment(String consgNo) throws CGSystemException;

	BookingDO getBookingConsignment(String consgNo) throws CGSystemException;

	List<ManifestDO> getMisrouteManifests(List<String> manifestNOList,
			Integer loginOfficeId) throws CGSystemException;

	List<ManifestDO> getManifestForCreation(ManifestDO manifestDO)
			throws CGSystemException;

	List<ManifestDO> getManifestDtls(ManifestInputs manifestTOs)
			throws CGSystemException;

	List<BookingDO> getBookings(List<String> consgNos) throws CGSystemException;

	ManifestDO getManifestDtlsByFetchProfileForOutDOX(ManifestDO manifestDO,
			String fetchProfile) throws CGSystemException;

	void saveOrUpdateBookings(List<BookingDO> allBooking,
			List<ConsignmentDO> bookingConsignment) throws CGSystemException;

	ManifestDO isOutManifestExistByFetchProfile(ManifestDO manifestDO,
			String fetchProfileManifestParcel) throws CGSystemException;

	/**
	 * To search consignment billing rate details
	 * 
	 * @param pickupConsgNos
	 * @return consignmentBillingRateDOs
	 * @throws CGSystemException
	 */
	List<ConsignmentBillingRateDO> searchConsgBillingRateDtls(
			List<String> pickupConsgNos) throws CGSystemException;

	List<ConsignmentDO> getConsignmentsAndEvictFromSession(List<String> consgNos)
			throws CGSystemException;

	/**
	 * @param allBooking
	 * @return
	 * @throws CGSystemException
	 */
	boolean saveOrUpdateBookingCNs(List<BookingDO> allBooking)
			throws CGSystemException;

	ManifestDO getManifestDetailsWithFetchProfile(ManifestBaseTO manifestBaseTO)
			throws CGSystemException;

	/**
	 * To validate scanned manifest no in header during scanning consg. in grid
	 * 
	 * @param cnValidateTO
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean isValidateScanedManifestNo(OutManifestValidate cnValidateTO)
			throws CGSystemException;

	/**
	 * @param consgNo
	 * @return
	 * @throws CGSystemException
	 */
	PickupRunsheetHeaderDO getPickupRunsheetHeaderByConsignmentNo(String consgNo) throws CGSystemException;

	/**
	 * @param pickupRunsheetHeader
	 * @return
	 * @throws CGSystemException
	 */
	boolean saveOrUpdatePickupRunsheetHeaderDetails(
			Set<PickupRunsheetHeaderDO> pickupRunsheetHeader)throws CGSystemException;

	/**
	 * Gets the manifest details.
	 *
	 * @param manifestInputs the manifest inputs
	 * @return the manifest details
	 * @throws CGSystemException 
	 */
	List<ManifestDO> getManifestDetails(ManifestInputs manifestInputs) throws CGSystemException;
	
}
