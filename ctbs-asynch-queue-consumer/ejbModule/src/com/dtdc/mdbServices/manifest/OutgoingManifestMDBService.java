package src.com.dtdc.mdbServices.manifest;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.common.OfficeTO;
import com.dtdc.to.manifest.BagManifestDoxTO;
import com.dtdc.to.manifest.PacketManifestDoxTO;

// TODO: Auto-generated Javadoc
/**
 * Author: Narasimha OutgoingManifestService.java
 */

public interface OutgoingManifestMDBService {

	/**
	 * Save or update packet manifest.
	 *
	 * @param packetManifestDoxTO the packet manifest dox to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveOrUpdatePacketManifest(
			PacketManifestDoxTO packetManifestDoxTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Save or update packet manifest.
	 *
	 * @param packetManifestDoxTO the packet manifest dox to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveOrUpdatePacketManifest(CGBaseTO packetManifestDoxTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Save or update pkt bag n dox mnp mis route manifest db sync.
	 *
	 * @param manifestDoxTOs the manifest dox t os
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveOrUpdatePktBagNDoxMnpMisRouteManifestDBSync(
			List<PacketManifestDoxTO> manifestDoxTOs) throws CGSystemException,
			CGBusinessException;

	/**
	 * Save or update pkt bag n dox mnp mis route manifest db sync.
	 *
	 * @param packetManifestDoxTO the packet manifest dox to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveOrUpdatePktBagNDoxMnpMisRouteManifestDBSync(
			CGBaseTO packetManifestDoxTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Save or update master bag dox manifest db sync.
	 *
	 * @param BagManifestDoxTO the bag manifest dox to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveOrUpdateMasterBagDoxManifestDBSync(
			CGBaseTO BagManifestDoxTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Save or update master bag dox manifest db sync.
	 *
	 * @param manifestDoxTOs the manifest dox t os
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveOrUpdateMasterBagDoxManifestDBSync(
			List<BagManifestDoxTO> manifestDoxTOs) throws CGSystemException,
			CGBusinessException;

	/**
	 * Save or update bag manifest.
	 *
	 * @param bagManifestDoxTO the bag manifest dox to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveOrUpdateBagManifest(BagManifestDoxTO bagManifestDoxTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Save or update bag manifest.
	 *
	 * @param bagManifestDoxTO the bag manifest dox to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveOrUpdateBagManifest(CGBaseTO bagManifestDoxTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the origin rep office details.
	 *
	 * @param employeeId the employee id
	 * @param employeeCode the employee code
	 * @return the origin rep office details
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public OfficeTO getOriginRepOfficeDetails(Integer employeeId,
			String employeeCode) throws CGBusinessException, CGSystemException;

	/**
	 * Find by manifest number.
	 *
	 * @param manifestNum the manifest num
	 * @param manifestCode the manifest code
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public List<PacketManifestDoxTO> findByManifestNumber(String manifestNum,
			String manifestCode) throws CGSystemException, CGBusinessException;

	/**
	 * Find by master manifest number.
	 *
	 * @param mmfNumber the mmf number
	 * @param manifestCode the manifest code
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public List<BagManifestDoxTO> findByMasterManifestNumber(String mmfNumber,
			String manifestCode) throws CGSystemException, CGBusinessException;

	/**
	 * Gets the branch details by branch code.
	 *
	 * @param branchcode the branchcode
	 * @return the branch details by branch code
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public StringBuilder getBranchDetailsByBranchCode(String branchcode)
			throws CGBusinessException, CGSystemException;

	// public DocumentDO getDocumentID(String manifestCode) throws
	// CGSystemException,CGBusinessException;

	/**
	 * Gets the manifest by type.
	 *
	 * @param manifestNumber the manifest number
	 * @param manifestType the manifest type
	 * @param productCode the product code
	 * @param modeId the mode id
	 * @param manifestCode the manifest code
	 * @param mnfstTypeOnFirstRec the mnfst type on first rec
	 * @param editFlag the edit flag
	 * @return the manifest by type
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String getManifestByType(String manifestNumber, String manifestType,
			String productCode, Integer modeId, String manifestCode,
			String mnfstTypeOnFirstRec, String editFlag)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the master bag.
	 *
	 * @param manifestNumber the manifest number
	 * @return the master bag
	 * @throws CGBusinessException the cG business exception
	 */
	public String getMasterBag(String manifestNumber)
			throws CGBusinessException;

	/**
	 * Pending manifest count.
	 *
	 * @param manifestDate the manifest date
	 * @return the long
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public long pendingManifestCount(Date manifestDate)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the org dest off ids by manifest.
	 *
	 * @param manifestNumber the manifest number
	 * @param manifestCode the manifest code
	 * @return the org dest off ids by manifest
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String getOrgDestOffIdsByManifest(String manifestNumber,
			String manifestCode) throws CGSystemException, CGBusinessException;

	/**
	 * Gets the weight by consg.
	 *
	 * @param consgNumber the consg number
	 * @return the weight by consg
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public Double getWeightByConsg(String consgNumber)
			throws CGSystemException, CGBusinessException;

	/**
	 * Pending mnp bookings.
	 *
	 * @param manifestDate the manifest date
	 * @return the long
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public long pendingMNPBookings(Date manifestDate) throws CGSystemException,
			CGBusinessException;

	/**
	 * Checks if is consignment held up.
	 *
	 * @param consgNum the consg num
	 * @return true, if is consignment held up
	 * @throws CGSystemException the cG system exception
	 */
	public boolean isConsignmentHeldUp(String consgNum)
			throws CGSystemException;

}
