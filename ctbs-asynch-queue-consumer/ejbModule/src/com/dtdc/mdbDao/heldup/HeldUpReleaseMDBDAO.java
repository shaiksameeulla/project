/**
 * 
 */
package src.com.dtdc.mdbDao.heldup;


import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;
import com.dtdc.domain.master.ReasonDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.transaction.heldup.HeldUpReleaseDO;
import com.dtdc.domain.transaction.heldup.HeldUpReleaseItemsDtlsDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface HeldUpReleaseMDBDAO.
 *
 * @author mohammes
 */
public interface HeldUpReleaseMDBDAO {
	
	/**
	 * Save held up.
	 *
	 * @param heldUpReleaseDO the held up release do
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean saveHeldUp(HeldUpReleaseDO heldUpReleaseDO) throws CGBusinessException,CGSystemException;
	
	/**
	 * Delete.
	 *
	 * @param heldupDoList the heldup do list
	 * @throws CGBusinessException the cG business exception
	 */
	public void delete(List<HeldUpReleaseDO> heldupDoList) throws CGBusinessException;
	
	/**
	 * Find.
	 *
	 * @param heldupNo the heldup no
	 * @return the held up release do
	 * @throws CGBusinessException the cG business exception
	 */
	public HeldUpReleaseDO find(String heldupNo) throws CGBusinessException;
	
	/**
	 * Gets the release reasons.
	 *
	 * @param reasonCd the reason cd
	 * @return the release reasons
	 * @throws CGBusinessException the cG business exception
	 */
	public List<ReasonDO> getReleaseReasons(String reasonCd) throws CGBusinessException;
	
	/**
	 * Save release details.
	 *
	 * @param heldUpReleaseDo the held up release do
	 * @param miscListDoList the misc list do list
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean saveReleaseDetails(HeldUpReleaseDO heldUpReleaseDo, List<MiscExpenseDO> miscListDoList) throws CGBusinessException,CGSystemException ;
	
	/**
	 * Gets the manifest type do.
	 *
	 * @return the manifest type do
	 * @throws CGBusinessException the cG business exception
	 */
	public List<ManifestTypeDO> getManifestTypeDO()  throws CGBusinessException;
	
	/**
	 * Gets the reason name.
	 *
	 * @param reasonId the reason id
	 * @return the reason name
	 * @throws CGBusinessException the cG business exception
	 */
	public String getReasonName(Integer reasonId) throws CGBusinessException;
	
	/**
	 * Gets the manifest type do.
	 *
	 * @param mnfstTypeId the mnfst type id
	 * @return the manifest type do
	 * @throws CGBusinessException the cG business exception
	 */
	public String getManifestTypeDO(String mnfstTypeId) throws CGBusinessException;
	
	/**
	 * Edits the.
	 *
	 * @param itemDetailslist the item detailslist
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 */
	public Boolean edit(List<HeldUpReleaseItemsDtlsDO> itemDetailslist) throws CGBusinessException;
	
	/**
	 * Gets the reason id.
	 *
	 * @param reasonCd the reason cd
	 * @return the reason id
	 * @throws CGBusinessException the cG business exception
	 */
	public int getReasonId(String reasonCd) throws CGBusinessException;
	
	/**
	 * Validate manifest details.
	 *
	 * @param mnfstNumber the mnfst number
	 * @param mnfstTypeId the mnfst type id
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public boolean validateManifestDetails(String mnfstNumber, Integer mnfstTypeId) throws CGBusinessException,CGSystemException;

	/**
	 * Validate manifest no details.
	 *
	 * @param mnfstNumber the mnfst number
	 * @param mnfstTypeId the mnfst type id
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public boolean validateManifestNoDetails(String mnfstNumber,
			Integer mnfstTypeId) throws CGBusinessException,CGSystemException;
	
	/**
	 * Gets the office detail by office code.
	 *
	 * @param officeCode the office code
	 * @param loggedInOfficeCode the logged in office code
	 * @return the office detail by office code
	 * @throws CGSystemException the cG system exception
	 */
	public OfficeDO getOfficeDetailByOfficeCode(String officeCode, String loggedInOfficeCode)	throws CGSystemException;

	/**
	 * Gets the manifest type object.
	 *
	 * @param mnfstTypeId the mnfst type id
	 * @return the manifest type object
	 * @throws CGSystemException the cG system exception
	 */
	public ManifestTypeDO getManifestTypeObject(Integer mnfstTypeId) throws CGSystemException;
	
	/**
	 * Update manifest.
	 *
	 * @param manifestList the manifest list
	 * @throws CGSystemException the cG system exception
	 */
	public void updateManifest(List<ManifestDO> manifestList) throws CGSystemException;
	
	/**
	 * Update vehicle manifest.
	 *
	 * @param vehicleMFNo the vehicle mf no
	 * @param heldupdo the heldupdo
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	public List<String> updateVehicleManifest(Integer vehicleMFNo, HeldUpReleaseDO heldupdo) throws CGSystemException;
	
	/**
	 * Update fr delivery manifest.
	 *
	 * @param deliveryManifestNo the delivery manifest no
	 * @param heldupdo the heldupdo
	 * @param heldUpReleaseStatus the held up release status
	 * @throws CGSystemException the cG system exception
	 */
	public void updateFRDeliveryManifest(String deliveryManifestNo, HeldUpReleaseDO heldupdo, String heldUpReleaseStatus) throws CGSystemException;
	
	/**
	 * Update br delivery manifest.
	 *
	 * @param deliveryManifestNo the delivery manifest no
	 * @param heldupdo the heldupdo
	 * @param heldUpReleaseStatus the held up release status
	 * @throws CGSystemException the cG system exception
	 */
	public void updateBRDeliveryManifest(String deliveryManifestNo, HeldUpReleaseDO heldupdo, String heldUpReleaseStatus) throws CGSystemException;
	
	/**
	 * Update booking consignment.
	 *
	 * @param deliveryManifestNo the delivery manifest no
	 * @param heldupdo the heldupdo
	 * @param heldUpReleaseStatus the held up release status
	 * @throws CGSystemException the cG system exception
	 */
	public void updateBookingConsignment(String deliveryManifestNo, HeldUpReleaseDO heldupdo, String heldUpReleaseStatus) throws CGSystemException;
	
	/**
	 * Update cd dispatch.
	 *
	 * @param dispatchNo the dispatch no
	 * @param heldupdo the heldupdo
	 * @param heldUpReleaseStatus the held up release status
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public void updateCDDispatch(String dispatchNo, HeldUpReleaseDO heldupdo, String heldUpReleaseStatus) throws CGSystemException, CGBusinessException;
	
	/**
	 * Delete details.
	 *
	 * @param itemDetailslist the item detailslist
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 */
	public Boolean deleteDetails(Set<HeldUpReleaseItemsDtlsDO> itemDetailslist) throws CGBusinessException;
	
	/**
	 * Save misc exp for release.
	 *
	 * @param miscExpenseDoList the misc expense do list
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 */
	public Boolean saveMiscExpForRelease(List<MiscExpenseDO> miscExpenseDoList) throws CGBusinessException;
	
	/**
	 * Gets the masterbag child list.
	 *
	 * @param manifestNumber the manifest number
	 * @return the masterbag child list
	 * @throws CGBusinessException the cG business exception
	 */
	public List<ManifestDO> getMasterbagChildList(String manifestNumber)
	throws CGBusinessException;
	
	/**
	 * Update booking by consignment num.
	 *
	 * @param manifestDo the manifest do
	 * @param HeldUpReleaseId the held up release id
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 */
	public Boolean updateBookingByConsignmentNum(ManifestDO manifestDo, Integer HeldUpReleaseId)
	throws CGBusinessException;
	
	/**
	 * Find release details for pop up.
	 *
	 * @param manifestNum the manifest num
	 * @param manifestCode the manifest code
	 * @return the held up release items dtls do
	 * @throws CGBusinessException the cG business exception
	 */
	public HeldUpReleaseItemsDtlsDO findReleaseDetailsForPopUp(String manifestNum, String manifestCode)throws CGBusinessException;
	
	/**
	 * Gets the masterbag child list for dox non dox.
	 *
	 * @param manifestNumber the manifest number
	 * @param mnfstDoxNonDoxCode the mnfst dox non dox code
	 * @return the masterbag child list for dox non dox
	 * @throws CGBusinessException the cG business exception
	 */
	List<ManifestDO> getMasterbagChildListForDoxNonDox(String manifestNumber,
			String mnfstDoxNonDoxCode) throws CGBusinessException;
	
	/**
	 * Find heldup dtls.
	 *
	 * @param heldupNums the heldup nums
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	List<HeldUpReleaseDO> findHeldupDtls(List<String> heldupNums)
			throws CGBusinessException;
	
	/**
	 * Save held up release list.
	 *
	 * @param heldUpReleaseDoList the held up release do list
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveHeldUpReleaseList(List<HeldUpReleaseDO> heldUpReleaseDoList)
			throws CGBusinessException, CGSystemException;
	
}
