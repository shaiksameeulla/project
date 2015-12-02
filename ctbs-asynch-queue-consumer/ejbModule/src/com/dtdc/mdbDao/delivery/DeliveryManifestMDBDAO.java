package src.com.dtdc.mdbDao.delivery;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseDO.CGBaseEntity;
import com.dtdc.domain.booking.BookingLogDO;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.master.HandHeldDeviceDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.geography.AreaDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.transaction.delivery.DeliveryDO;
import com.dtdc.domain.transaction.delivery.DeliveryManifestBookingDO;
import com.dtdc.domain.transaction.delivery.DeliveryManifestFranchiseeDO;
import com.dtdc.domain.transaction.delivery.DeliveryManifestOfficeDO;
import com.dtdc.domain.transaction.delivery.FranchiseDeliveryManifestDO;
import com.dtdc.domain.transaction.delivery.FranchiseDeliveryManifestHandOverDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface DeliveryManifestMDBDAO.
 */
public interface DeliveryManifestMDBDAO {

	/**
	 * Save or update br delivery manifest.
	 *
	 * @param deiveryManifestList the deivery manifest list
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveOrUpdateBrDeliveryManifest(
			List<DeliveryDO> deiveryManifestList) throws CGBusinessException;

	/**
	 * Save or update fr delivery manifest.
	 *
	 * @param deiveryManifestList the deivery manifest list
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveOrUpdateFrDeliveryManifest(
			List<FranchiseDeliveryManifestDO> deiveryManifestList)
			throws CGBusinessException;

	/**
	 * Gets the booking details.
	 *
	 * @param consgNum the consg num
	 * @return the booking details
	 * @throws CGBusinessException the cG business exception
	 */
	public DeliveryManifestBookingDO getBookingDetails(String consgNum)
			throws CGBusinessException;

	// find the Delivery Manifest by DRS Number

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

	/*
	 * public Integer getDuplicateConsignment(String consgNum, Date fromDate,
	 * Date toDate) throws CGBusinessException;
	 */

	/*
	 * public String getfdmHandOverByManifest(String manifestNum) throws
	 * CGBusinessException;
	 */

	/**
	 * Gets the employees by branch code.
	 *
	 * @param branchId the branch id
	 * @return the employees by branch code
	 * @throws CGBusinessException the cG business exception
	 */
	public DeliveryManifestOfficeDO getEmployeesByBranchCode(int branchId)
			throws CGBusinessException;

	/**
	 * Gets the employees by branch code.
	 *
	 * @param brCode the br code
	 * @return the employees by branch code
	 * @throws CGBusinessException the cG business exception
	 */
	public DeliveryManifestOfficeDO getEmployeesByBranchCode(String brCode)
			throws CGBusinessException;

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
	 * Gets the br delivery manifest details.
	 *
	 * @param drsNumber the drs number
	 * @return the br delivery manifest details
	 * @throws CGBusinessException the cG business exception
	 */
	public List<DeliveryDO> getBrDeliveryManifestDetails(String drsNumber)
			throws CGBusinessException;

	/**
	 * Gets the franchisee and reporting branch.
	 *
	 * @param frCode the fr code
	 * @return the franchisee and reporting branch
	 * @throws CGBusinessException the cG business exception
	 */
	public DeliveryManifestFranchiseeDO getFranchiseeAndReportingBranch(
			String frCode) throws CGBusinessException;

	/**
	 * Find by fdm number.
	 *
	 * @param fdmNum the fdm num
	 * @return List<FranchiseeDeliveryManifestTO>
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public List<FranchiseDeliveryManifestDO> findByFDMNumber(String fdmNum)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the employees by branch id.
	 *
	 * @param branchID the branch id
	 * @return the employees by branch id
	 * @throws CGBusinessException the cG business exception
	 */
	public List<EmployeeDO> getEmployeesByBranchID(int branchID)
			throws CGBusinessException;

	/**
	 * Fdm to bdm conversion.
	 *
	 * @param deliveryIDs the delivery i ds
	 * @param brDeliveryList the br delivery list
	 * @param totalWeight the total weight
	 * @param totalPieces the total pieces
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 */
	public String fdmToBdmConversion(List<Integer> deliveryIDs,
			List<DeliveryDO> brDeliveryList, Double totalWeight, int totalPieces)
			throws CGBusinessException;

	/**
	 * Bdm to fdm conversion.
	 *
	 * @param deliveryIDs the delivery i ds
	 * @param frDeliveryList the fr delivery list
	 * @param totalWeight the total weight
	 * @param totalPieces the total pieces
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 */
	public String bdmToFdmConversion(List<Integer> deliveryIDs,
			List<FranchiseDeliveryManifestDO> frDeliveryList,
			Double totalWeight, int totalPieces) throws CGBusinessException;

	/**
	 * Gets the fr delivery manifest by i ds.
	 *
	 * @param deliveryIDList the delivery id list
	 * @return the fr delivery manifest by i ds
	 * @throws CGBusinessException the cG business exception
	 */
	public List<FranchiseDeliveryManifestDO> getFrDeliveryManifestByIDs(
			List<Integer> deliveryIDList) throws CGBusinessException;

	/**
	 * Gets the br delivery manifest by i ds.
	 *
	 * @param deliveryIDList the delivery id list
	 * @return the br delivery manifest by i ds
	 * @throws CGBusinessException the cG business exception
	 */
	public List<DeliveryDO> getBrDeliveryManifestByIDs(
			List<Integer> deliveryIDList) throws CGBusinessException;

	/**
	 * Save fr delivery ho manifest.
	 *
	 * @param deiveryManifestList the deivery manifest list
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveFrDeliveryHOManifest(
			List<FranchiseDeliveryManifestHandOverDO> deiveryManifestList)
			throws CGBusinessException;

	/**
	 * Checks if is consignment manifested.
	 *
	 * @param consgNumber the consg number
	 * @param manifestStatus the manifest status
	 * @return true, if is consignment manifested
	 * @throws CGSystemException the cG system exception
	 */
	public boolean isConsignmentManifested(String consgNumber,
			String manifestStatus) throws CGSystemException;

	/**
	 * Gets the fr delivery manifest details.
	 *
	 * @param fdmNum the fdm num
	 * @return the fr delivery manifest details
	 * @throws CGSystemException the cG system exception
	 */
	public List<FranchiseDeliveryManifestDO> getFrDeliveryManifestDetails(
			String fdmNum) throws CGSystemException;

	/**
	 * Checks if is rTO consignment.
	 *
	 * @param consgNum the consg num
	 * @param rtoManiefstType the rto maniefst type
	 * @return true, if is rTO consignment
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean isRTOConsignment(String consgNum, String rtoManiefstType)
			throws CGBusinessException;

	/**
	 * Gets the attempt number.
	 *
	 * @param consgNum the consg num
	 * @param status the status
	 * @return the attempt number
	 * @throws CGBusinessException the cG business exception
	 */
	public int getAttemptNumber(String consgNum, String[] status)
			throws CGBusinessException;

	/*
	 * public Integer getDuplicateConsignment(String consgNum, Date preDate)
	 * throws CGBusinessException;
	 */

	/**
	 * Gets the delivery by consg.
	 *
	 * @param consgNumber the consg number
	 * @param status the status
	 * @return the delivery by consg
	 * @throws CGBusinessException the cG business exception
	 */
	public DeliveryDO getDeliveryByConsg(String consgNumber, String status)
			throws CGBusinessException;

	/**
	 * Gets the fr dlivery day count.
	 *
	 * @param frId the fr id
	 * @param fdmDate the fdm date
	 * @return the fr dlivery day count
	 * @throws CGBusinessException the cG business exception
	 */
	public int getFrDliveryDayCount(Integer frId, Date fdmDate)
			throws CGBusinessException;

	/**
	 * Fdm preparation notification template.
	 *
	 * @param frDeliveryList the fr delivery list
	 * @param fdmNumber the fdm number
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 */
	public String fdmPreparationNotificationTemplate(
			List<FranchiseDeliveryManifestDO> frDeliveryList, String fdmNumber)
			throws CGBusinessException;

	/**
	 * Update booked weight.
	 *
	 * @param consgNumber the consg number
	 * @param finalWeight the final weight
	 * @param rateAmount the rate amount
	 * @param updatedfProcessFrom the updatedf process from
	 * @throws CGSystemException the cG system exception
	 */
	public void updateBookedWeight(String consgNumber, Double finalWeight,
			Double rateAmount, String updatedfProcessFrom)
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
	 * Gets the pincode area.
	 *
	 * @param pincodeID the pincode id
	 * @return the pincode area
	 * @throws CGSystemException the cG system exception
	 */
	public AreaDO getPincodeArea(int pincodeID) throws CGSystemException;

	/**
	 * Validate agnst franchisee.
	 *
	 * @param franchiseeID the franchisee id
	 * @param pincodeID the pincode id
	 * @return true, if successful
	 * @throws CGSystemException the cG system exception
	 */
	public boolean validateAgnstFranchisee(int franchiseeID, int pincodeID)
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
	 * Update dest into booking.
	 *
	 * @param pinCode the pin code
	 * @param conNum the con num
	 * @param rateAmount the rate amount
	 * @param updatedfProcessFrom the updatedf process from
	 * @throws CGSystemException the cG system exception
	 */
	public void updateDestIntoBooking(String pinCode, String conNum,
			Double rateAmount, String updatedfProcessFrom)
			throws CGSystemException;

	/**
	 * Gets the incmg mnfst.
	 *
	 * @param consgNumber the consg number
	 * @param manifestTypeIds the manifest type ids
	 * @return the incmg mnfst
	 * @throws CGSystemException the cG system exception
	 */
	public ManifestDO getIncmgMnfst(String consgNumber,
			List<Integer> manifestTypeIds) throws CGSystemException;

	/**
	 * Checks if is consignment delivered.
	 *
	 * @param consgNum the consg num
	 * @return true, if is consignment delivered
	 * @throws CGSystemException the cG system exception
	 */
	public boolean isConsignmentDelivered(String consgNum)
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
	 * Checks if is consignment held up.
	 *
	 * @param consgNum the consg num
	 * @return true, if is consignment held up
	 * @throws CGSystemException the cG system exception
	 */
	public boolean isConsignmentHeldUp(String consgNum)
			throws CGSystemException;

	/**
	 * Checks if is paper wrk consg.
	 *
	 * @param cnNum the cn num
	 * @return true, if is paper wrk consg
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean isPaperWrkConsg(String cnNum) throws CGBusinessException;

	/**
	 * Checks if is consignment returned.
	 *
	 * @param consgNum the consg num
	 * @return true, if is consignment returned
	 * @throws CGSystemException the cG system exception
	 */
	public boolean isConsignmentReturned(String consgNum)
			throws CGSystemException;

	/**
	 * Checks if is already ho.
	 *
	 * @param fdmNumber the fdm number
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 */
	public String isAlreadyHO(String fdmNumber) throws CGBusinessException;

	/**
	 * Find by fr h oumber.
	 *
	 * @param frHONum the fr ho num
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public List<FranchiseDeliveryManifestDO> findByFrHOumber(String frHONum)
			throws CGSystemException, CGBusinessException;

	/**
	 * Checks if is paper work manifested.
	 *
	 * @param consgNum the consg num
	 * @param mnfstTypeId the mnfst type id
	 * @return true, if is paper work manifested
	 * @throws CGSystemException the cG system exception
	 */
	public boolean isPaperWorkManifested(String consgNum, Integer mnfstTypeId)
			throws CGSystemException;

	/**
	 * Gets the phones by id.
	 *
	 * @param branchId the branch id
	 * @param branchCode the branch code
	 * @return the phones by id
	 * @throws CGSystemException the cG system exception
	 */
	public List<HandHeldDeviceDO> getPhonesByID(Integer branchId,
			String branchCode) throws CGSystemException;

	/*
	 * public List<ManifestDO> getPODManifest(String manifestNumber) throws
	 * CGBusinessException;
	 */
	/**
	 * Gets the pin code id by code.
	 *
	 * @param pinCode the pin code
	 * @return the pin code id by code
	 * @throws CGSystemException the cG system exception
	 */
	public int getPinCodeIdByCode(String pinCode) throws CGSystemException;

	/**
	 * Gets the br delivery id.
	 *
	 * @param consgNumber the consg number
	 * @param attemptNumber the attempt number
	 * @param consgStatus the consg status
	 * @param dlvStatus the dlv status
	 * @param runSheetNum the run sheet num
	 * @return the br delivery id
	 * @throws CGSystemException the cG system exception
	 */
	public Integer getBrDeliveryId(String consgNumber, Integer attemptNumber,
			String consgStatus, String dlvStatus, String runSheetNum)
			throws CGSystemException;
	
	/**
	 * Gets the fr delivery id.
	 *
	 * @param consgNumber the consg number
	 * @param attemptNumber the attempt number
	 * @param consgStatus the consg status
	 * @param dlvStatus the dlv status
	 * @param fdmNumber the fdm number
	 * @return the fr delivery id
	 * @throws CGSystemException the cG system exception
	 */
	public Integer getFrDeliveryId(String consgNumber, Integer attemptNumber,
			String consgStatus, String dlvStatus, String fdmNumber)
			throws CGSystemException;
	
	/**
	 * Gets the fr ho delivery id.
	 *
	 * @param consgNumber the consg number
	 * @param attemptNumber the attempt number
	 * @param consgStatus the consg status
	 * @param dlvStatus the dlv status
	 * @param hoNumber the ho number
	 * @return the fr ho delivery id
	 * @throws CGSystemException the cG system exception
	 */
	public Integer getFrHODeliveryId(String consgNumber, Integer attemptNumber,
			String consgStatus, String dlvStatus, String hoNumber)
			throws CGSystemException;
}
