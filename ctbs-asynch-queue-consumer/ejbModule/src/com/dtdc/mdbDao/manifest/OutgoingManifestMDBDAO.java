/**
 * Author: Narasimha Rao Kattunga 
 * OutgoingManifestServiceImpl
 */
package src.com.dtdc.mdbDao.manifest;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseDO.CGBaseEntity;
import com.dtdc.domain.booking.BookingLogDO;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;
import com.dtdc.domain.manifest.RtnToOrgDO;
import com.dtdc.domain.master.document.DocumentDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.transaction.manifest.ManifestBookingDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface OutgoingManifestMDBDAO.
 */
public interface OutgoingManifestMDBDAO {

	/**
	 * Save or update manifest.
	 *
	 * @param manifestDOList the manifest do list
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	public String saveOrUpdateManifest(List<ManifestDO> manifestDOList)
			throws CGSystemException;

	/**
	 * Find by manifest number.
	 *
	 * @param manifestNumber the manifest number
	 * @param manifestCode the manifest code
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	public List<ManifestDO> findByManifestNumber(String manifestNumber,
			String manifestCode) throws CGSystemException;

	/**
	 * Gets the manifest type.
	 *
	 * @param manifestType the manifest type
	 * @return the manifest type
	 * @throws CGSystemException the cG system exception
	 */
	public ManifestTypeDO getManifestType(String manifestType)
			throws CGSystemException;

	/**
	 * Gets the booking details by con num.
	 *
	 * @param conNum the con num
	 * @return the booking details by con num
	 * @throws CGSystemException the cG system exception
	 */
	public ManifestBookingDO getBookingDetailsByConNum(String conNum)
			throws CGSystemException;

	/**
	 * Gets the duplicate consignment.
	 *
	 * @param conNum the con num
	 * @param manifestType the manifest type
	 * @param mnfstTypeId the mnfst type id
	 * @return the duplicate consignment
	 * @throws CGSystemException the cG system exception
	 */
	public int getDuplicateConsignment(String conNum, String manifestType,
			int mnfstTypeId) throws CGSystemException;

	/**
	 * Check duplicate manifest number.
	 *
	 * @param manifestNum the manifest num
	 * @return the int
	 * @throws CGSystemException the cG system exception
	 */
	public int checkDuplicateManifestNumber(String manifestNum)
			throws CGSystemException;

	/**
	 * Gets the document id.
	 *
	 * @param manifestCode the manifest code
	 * @return the document id
	 * @throws CGSystemException the cG system exception
	 */
	public DocumentDO getDocumentID(String manifestCode)
			throws CGSystemException;

	/**
	 * Gets the manifest by type.
	 *
	 * @param manifestNumber the manifest number
	 * @param manifestType the manifest type
	 * @return the manifest by type
	 * @throws CGSystemException the cG system exception
	 */
	public List<ManifestDO> getManifestByType(String manifestNumber,
			String manifestType) throws CGSystemException;

	/**
	 * Checks if is consignment delivered.
	 *
	 * @param consgNum the consg num
	 * @param consgStatuses the consg statuses
	 * @return true, if is consignment delivered
	 * @throws CGSystemException the cG system exception
	 */
	public boolean isConsignmentDelivered(String consgNum,
			List<String> consgStatuses) throws CGSystemException;

	/**
	 * Gets the rTO manifest.
	 *
	 * @param manifestNumber the manifest number
	 * @return the rTO manifest
	 * @throws CGSystemException the cG system exception
	 */
	public List<RtnToOrgDO> getRTOManifest(String manifestNumber)
			throws CGSystemException;

	/**
	 * Update booked weight.
	 *
	 * @param consgNumber the consg number
	 * @param finalWeight the final weight
	 * @param rateAmount the rate amount
	 * @param updattedProcessFrom the updatted process from
	 * @throws CGSystemException the cG system exception
	 */
	public void updateBookedWeight(String consgNumber, Double finalWeight,
			Double rateAmount, String updattedProcessFrom)
			throws CGSystemException;

	/**
	 * Update sap intg consg status.
	 *
	 * @param consgNumber the consg number
	 * @param billingStatus the billing status
	 * @throws CGSystemException the cG system exception
	 */
	public void updateSAPIntgConsgStatus(String consgNumber,
			String billingStatus) throws CGSystemException;

	/**
	 * Gets the held up by manifest.
	 *
	 * @param manifestNumber the manifest number
	 * @return the held up by manifest
	 * @throws CGSystemException the cG system exception
	 */
	public int getHeldUpByManifest(String manifestNumber)
			throws CGSystemException;

	/**
	 * Find by manifest number.
	 *
	 * @param manifestNumber the manifest number
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	public List<ManifestDO> findByManifestNumber(String manifestNumber)
			throws CGSystemException;

	/**
	 * Find by master bag.
	 *
	 * @param manifestNumber the manifest number
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	public List<ManifestDO> findByMasterBag(String manifestNumber)
			throws CGSystemException;

	/**
	 * Validate pin code.
	 *
	 * @param consgpinCode the consgpin code
	 * @param officeID the office id
	 * @return the pincode do
	 * @throws CGSystemException the cG system exception
	 */
	public PincodeDO validatePinCode(String consgpinCode, int officeID)
			throws CGSystemException;

	/**
	 * Gets the booking.
	 *
	 * @param consgNumber the consg number
	 * @param entity the entity
	 * @return the booking
	 * @throws CGSystemException the cG system exception
	 */
	public CGBaseEntity getBooking(String consgNumber, CGBaseEntity entity)
			throws CGSystemException;

	/**
	 * Insert booking log.
	 *
	 * @param logDo the log do
	 * @throws CGSystemException the cG system exception
	 */
	public void insertBookingLog(BookingLogDO logDo) throws CGSystemException;

	/**
	 * Gets the final weight.
	 *
	 * @param bookingId the booking id
	 * @return the final weight
	 * @throws CGSystemException the cG system exception
	 */
	public Double getFinalWeight(int bookingId) throws CGSystemException;

	/**
	 * Checks if is misrouted.
	 *
	 * @param consgNumber the consg number
	 * @param destOffId the dest off id
	 * @return true, if is misrouted
	 * @throws CGSystemException the cG system exception
	 */
	public boolean isMisrouted(String consgNumber, int destOffId)
			throws CGSystemException;

	/**
	 * Gets the incmg mnfst.
	 *
	 * @param consgNumber the consg number
	 * @return the incmg mnfst
	 * @throws CGSystemException the cG system exception
	 */
	public ManifestDO getIncmgMnfst(String consgNumber)
			throws CGSystemException;

	/**
	 * Save misc exp for release.
	 *
	 * @param miscExpenseDoList the misc expense do list
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	public String saveMiscExpForRelease(List<MiscExpenseDO> miscExpenseDoList)
			throws CGSystemException;

	/**
	 * Pending manifest count.
	 *
	 * @param manifestDate the manifest date
	 * @return the long
	 * @throws CGSystemException the cG system exception
	 */
	public long pendingManifestCount(Date manifestDate)
			throws CGSystemException;

	/**
	 * Gets the org dest off ids by manifest.
	 *
	 * @param manifestNumber the manifest number
	 * @param manifestCode the manifest code
	 * @return the org dest off ids by manifest
	 * @throws CGSystemException the cG system exception
	 */
	public String getOrgDestOffIdsByManifest(String manifestNumber,
			String manifestCode) throws CGSystemException;

	/**
	 * Gets the weight by consg.
	 *
	 * @param consgNumber the consg number
	 * @return the weight by consg
	 * @throws CGSystemException the cG system exception
	 */
	public Double getWeightByConsg(String consgNumber) throws CGSystemException;

	/**
	 * Pending mnp bookings.
	 *
	 * @param manifestDate the manifest date
	 * @return the long
	 * @throws CGSystemException the cG system exception
	 */
	public long pendingMNPBookings(Date manifestDate) throws CGSystemException;

	/**
	 * Validate apex for branch.
	 *
	 * @param apexOfficeCode the apex office code
	 * @param branchOfficeCode the branch office code
	 * @return true, if successful
	 */
	public boolean validateApexForBranch(String apexOfficeCode,
			String branchOfficeCode);

	/**
	 * Gets the manifest id.
	 *
	 * @param manifestNumber the manifest number
	 * @param consgNumber the consg number
	 * @param mnifestType the mnifest type
	 * @param mnfstTypeId the mnfst type id
	 * @return the manifest id
	 * @throws CGSystemException the cG system exception
	 */
	public Integer getManifestId(String manifestNumber, String consgNumber,
			String mnifestType, Integer mnfstTypeId) throws CGSystemException;
}
