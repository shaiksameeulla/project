package src.com.dtdc.mdbServices.delivery;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.common.ReasonTO;
import com.dtdc.to.delivery.DeliveryManifestEmployeesTO;
import com.dtdc.to.delivery.DeliveryManifestFranchiseeTO;
import com.dtdc.to.delivery.DeliveryManifestOfficeTO;
import com.dtdc.to.delivery.DeliveryManifestTO;
import com.dtdc.to.delivery.FranchiseeDeliveryHandoverTO;
import com.dtdc.to.delivery.FranchiseeDeliveryManifestTO;
import com.dtdc.to.delivery.HandHeldDeviceTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface DeliveryManifestMDBService.
 */
public interface DeliveryManifestMDBService {

	/**
	 * Save br delivery manifest.
	 *
	 * @param deliveryManifestTO the delivery manifest to
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public String saveBrDeliveryManifest(DeliveryManifestTO deliveryManifestTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save br delivery manifest.
	 *
	 * @param brDeliveryTO the br delivery to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveBrDeliveryManifest(CGBaseTO brDeliveryTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Save fr delivery manifest.
	 *
	 * @param frDeliveryManifestTO the fr delivery manifest to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveFrDeliveryManifest(
			FranchiseeDeliveryManifestTO frDeliveryManifestTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Save fr delivery manifest.
	 *
	 * @param frDeliveryManifestTO the fr delivery manifest to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveFrDeliveryManifest(CGBaseTO frDeliveryManifestTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Save fr delivery manifest ho.
	 *
	 * @param frDeliveryManifestTO the fr delivery manifest to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveFrDeliveryManifestHO(
			FranchiseeDeliveryHandoverTO frDeliveryManifestTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Save fr delivery manifest ho.
	 *
	 * @param frDeliveryManifestTO the fr delivery manifest to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveFrDeliveryManifestHO(CGBaseTO frDeliveryManifestTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Save br delivery manifest db sync.
	 *
	 * @param deliveryManifestTO the delivery manifest to
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public String saveBrDeliveryManifestDBSync(
			List<DeliveryManifestTO> deliveryManifestTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save br delivery manifest db sync.
	 *
	 * @param brDeliveryTO the br delivery to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveBrDeliveryManifestDBSync(CGBaseTO brDeliveryTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Save fr delivery manifest db sync.
	 *
	 * @param frDeliveryManifestTO the fr delivery manifest to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveFrDeliveryManifestDBSync(
			List<FranchiseeDeliveryManifestTO> frDeliveryManifestTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Save fr delivery manifest db sync.
	 *
	 * @param frDeliveryManifestTO the fr delivery manifest to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveFrDeliveryManifestDBSync(CGBaseTO frDeliveryManifestTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Save fr delivery manifest hodb sync.
	 *
	 * @param frDeliveryManifestTO the fr delivery manifest to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveFrDeliveryManifestHODBSync(
			List<FranchiseeDeliveryHandoverTO> frDeliveryManifestTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Save fr delivery manifest hodb sync.
	 *
	 * @param frDeliveryManifestTO the fr delivery manifest to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveFrDeliveryManifestHODBSync(CGBaseTO frDeliveryManifestTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Find by drs number.
	 *
	 * @param drsNum the drs num
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public List<DeliveryManifestTO> findByDRSNumber(String drsNum)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the booking details.
	 *
	 * @param consgNumber the consg number
	 * @param editFlag the edit flag
	 * @param preOffId the pre off id
	 * @param preparationDate the preparation date
	 * @param fdmFlag the fdm flag
	 * @return the booking details
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String getBookingDetails(String consgNumber, String editFlag,
			int preOffId, Date preparationDate, String fdmFlag)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the duplicate consignment.
	 *
	 * @param consgNum the consg num
	 * @param status the status
	 * @return the duplicate consignment
	 * @throws CGBusinessException the cG business exception
	 */
	public long getDuplicateConsignment(String consgNum, String status)
			throws CGBusinessException;

	/**
	 * Gets the fdm hand over by manifest.
	 *
	 * @param manifestNum the manifest num
	 * @return the fdm hand over by manifest
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public String getfdmHandOverByManifest(String manifestNum)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the employees by branch code.
	 *
	 * @param branchId the branch id
	 * @return the employees by branch code
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public DeliveryManifestOfficeTO getEmployeesByBranchCode(int branchId)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the employees by branch code.
	 *
	 * @param branchCode the branch code
	 * @return the employees by branch code
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public DeliveryManifestOfficeTO getEmployeesByBranchCode(String branchCode)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the franchisee and reporting branch.
	 *
	 * @param frCode the fr code
	 * @return the franchisee and reporting branch
	 * @throws CGBusinessException the cG business exception
	 */
	public DeliveryManifestFranchiseeTO getFranchiseeAndReportingBranch(
			String frCode) throws CGBusinessException;

	/**
	 * Gets the employee.
	 *
	 * @param empCode the emp code
	 * @param branchCode the branch code
	 * @return the employee
	 * @throws CGBusinessException the cG business exception
	 */
	public String getEmployee(String empCode, String branchCode)
			throws CGBusinessException;

	/**
	 * Find by fdm number.
	 *
	 * @param fdmNum the fdm num
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public List<FranchiseeDeliveryManifestTO> findByFDMNumber(String fdmNum)
			throws CGSystemException, CGBusinessException;

	/**
	 * Fdm to bdm convertion.
	 *
	 * @param deliveryIDs the delivery i ds
	 * @param frDeliveryTO the fr delivery to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String fdmToBdmConvertion(List<Integer> deliveryIDs,
			FranchiseeDeliveryManifestTO frDeliveryTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Bdm to fdm convertion.
	 *
	 * @param deliveryIDs the delivery i ds
	 * @param brDeliveryTO the br delivery to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String bdmToFdmConvertion(List<Integer> deliveryIDs,
			DeliveryManifestTO brDeliveryTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the reasons.
	 *
	 * @return the reasons
	 */
	public List<ReasonTO> getReasons();

	/**
	 * Gets the employees by branch id.
	 *
	 * @param branchID the branch id
	 * @return the employees by branch id
	 */
	public List<DeliveryManifestEmployeesTO> getEmployeesByBranchID(int branchID);

	/**
	 * Check franchisee capacity.
	 *
	 * @param frCode the fr code
	 * @param fdmDate the fdm date
	 * @return true, if successful
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean checkFranchiseeCapacity(String frCode, String fdmDate)
			throws CGSystemException, CGBusinessException;

	/**
	 * Validate pin code.
	 *
	 * @param consgpinCode the consgpin code
	 * @param officeID the office id
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String validatePinCode(String consgpinCode, int officeID)
			throws CGSystemException, CGBusinessException;

	/**
	 * Validate fdm pin code.
	 *
	 * @param consgpinCode the consgpin code
	 * @param frId the fr id
	 * @param brId the br id
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String validateFDMPinCode(String consgpinCode, int frId, int brId)
			throws CGSystemException, CGBusinessException;

	/**
	 * Find by fr ho number.
	 *
	 * @param hoNumber the ho number
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public List<FranchiseeDeliveryHandoverTO> findByFrHONumber(String hoNumber)
			throws CGSystemException, CGBusinessException;

	/**
	 * Checks if is paper work manifested.
	 *
	 * @param consgNum the consg num
	 * @param mnfstTypeId the mnfst type id
	 * @return true, if is paper work manifested
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean isPaperWorkManifested(String consgNum, Integer mnfstTypeId)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the phones by id.
	 *
	 * @param branchId the branch id
	 * @param branchCode the branch code
	 * @return the phones by id
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public List<HandHeldDeviceTO> getPhonesByID(Integer branchId,
			String branchCode) throws CGSystemException, CGBusinessException;

}
