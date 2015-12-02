/**
 * 
 */
package com.dtdc.bodbadmin.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseDO.CGBaseEntity;
import com.dtdc.domain.booking.BookingDO;
import com.dtdc.domain.dataextraction.DataExtractionDO;
import com.dtdc.domain.dispatch.DispatchDO;
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.RtnToOrgDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.purchase.GoodsIssueDO;
import com.dtdc.domain.purchase.GoodsRenewalDO;
import com.dtdc.domain.purchase.GoodscCancellationDO;
import com.dtdc.domain.transaction.delivery.DeliveryDO;
import com.dtdc.domain.transaction.heldup.HeldUpReleaseDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface CentralDataExtractorDAO.
 *
 * @author nisahoo
 */
public interface CentralDataExtractorDAO {

	/**
	 * Gets the outgoing manifests for branch.
	 *
	 * @param branchCode the branch code
	 * @param maxRecord the max record
	 * @return the outgoing manifests for branch
	 * @throws CGSystemException the cG system exception
	 */
	public List<ManifestDO> getOutgoingManifestsForBranch(String branchCode, Integer maxRecord) throws CGSystemException;

	/**
	 * Gets the booking details by cn.
	 *
	 * @param cnList the cn list
	 * @return the booking details by cn
	 */
	public List<BookingDO> getBookingDetailsByCn(List<String> cnList);

	/**
	 * Gets the booking type details.
	 *
	 * @param consignmentNumber the consignment number
	 * @param bookingType the booking type
	 * @return the booking type details
	 */
	public CGBaseEntity getBookingTypeDetails(String consignmentNumber,
			String bookingType);

	/**
	 * Update booking with read by local.
	 *
	 * @param bookingDOList the booking do list
	 */
	public void updateBookingWithReadByLocal(List<BookingDO> bookingDOList);

	/**
	 * Update manifests with read by local.
	 *
	 * @param manifestDOList the manifest do list
	 */
	public void updateManifestsWithReadByLocal(List<ManifestDO> manifestDOList);

	/**
	 * Gets the all office codes.
	 *
	 * @return the all office codes
	 */
	public List<String> getAllOfficeCodes();

	/**
	 * Save extracted data for branch.
	 *
	 * @param extractionDo the extraction do
	 * @return the boolean
	 */
	Boolean saveExtractedDataForBranch(DataExtractionDO extractionDo,
			String flag);

	/**
	 * Save extracted data for branch list.
	 *
	 * @param extractionList the extraction list
	 * @return the boolean
	 */
	public Boolean saveExtractedDataForBranchList(
			List<DataExtractionDO> extractionList);

	/**
	 * Gets the extracted data by branch code.
	 *
	 * @param branchCode the branch code
	 * @param maxFetchRecords the max fetch records
	 * @return the extracted data by branch code
	 * @throws CGSystemException the cG system exception
	 */
	public List<DataExtractionDO> getExtractedDataByBranchCode(
			String branchCode, Integer maxFetchRecords)
			throws CGSystemException;

	/**
	 * Gets the held up dtls by branch code.
	 *
	 * @param branchCode the branch code
	 * @param maxSize the max size
	 * @return the held up dtls by branch code
	 * @throws CGSystemException the cG system exception
	 */
	public List<HeldUpReleaseDO> getHeldUpDtlsByBranchCode(String branchCode,
			Integer maxSize) throws CGSystemException;

	/**
	 * Gets the goods cancl data by branch code.
	 *
	 * @param branchList the branch list
	 * @param maxSize the max size
	 * @return the goods cancl data by branch code
	 * @throws CGSystemException the cG system exception
	 */
	public List<GoodscCancellationDO> getGoodsCanclDataByBranchCode(
			List<String> branchList, Integer maxSize) throws CGSystemException;

	/**
	 * Gets the goods renewal data by branch code.
	 *
	 * @param branchList the branch list
	 * @param maxSize the max size
	 * @return the goods renewal data by branch code
	 * @throws CGSystemException the cG system exception
	 */
	public List<GoodsRenewalDO> getGoodsRenewalDataByBranchCode(
			List<String> branchList, Integer maxSize) throws CGSystemException;

	/**
	 * Update goods renewal with read by local.
	 *
	 * @param goodsRenewalDOList the goods renewal do list
	 */
	public void updateGoodsRenewalWithReadByLocal(
			List<GoodsRenewalDO> goodsRenewalDOList);

	/**
	 * Update goods cancl with read by local.
	 *
	 * @param goodsCanclDOList the goods cancl do list
	 */
	public void updateGoodsCanclWithReadByLocal(
			List<GoodscCancellationDO> goodsCanclDOList);

	/**
	 * Gets the dispatch data by branch code.
	 *
	 * @param branchCode the branch code
	 * @param maxSize the max size
	 * @return the dispatch data by branch code
	 * @throws CGSystemException the cG system exception
	 */
	public List<DispatchDO> getDispatchDataByBranchCode(String branchCode, Integer maxSize) throws CGSystemException;

	/**
	 * Update dispatch details with read by local.
	 *
	 * @param dispatchDOList the dispatch do list
	 */
	public void updateDispatchDetailsWithReadByLocal(
			List<DispatchDO> dispatchDOList);

	/**
	 * Update held up with read by local.
	 *
	 * @param heldUpReleaseDOList the held up release do list
	 * @return the boolean
	 */
	public Boolean updateHeldUpWithReadByLocal(
			List<HeldUpReleaseDO> heldUpReleaseDOList);

	/**
	 * Gets the goods issue data by branch code.
	 *
	 * @param branchCodeList the branch code list
	 * @param maxSize the max size
	 * @return the goods issue data by branch code
	 * @throws CGSystemException the cG system exception
	 */
	public List<GoodsIssueDO> getGoodsIssueDataByBranchCode(List branchCodeList,
			Integer maxSize) throws CGSystemException;

	/**
	 * Update goods issue with read by local.
	 *
	 * @param goodsIssueDOList the goods issue do list
	 * @return the boolean
	 */
	public Boolean updateGoodsIssueWithReadByLocal(
			List<GoodsIssueDO> goodsIssueDOList);

	/**
	 * Update data extraction with read data status.
	 *
	 * @param dataExtractionDoList the data extraction do list
	 * @return the boolean
	 */
	public Boolean updateDataExtractionWithReadDataStatus(
			List<DataExtractionDO> dataExtractionDoList);

	/**
	 * Update data extraction with transit status.
	 *
	 * @param dataExtractionDoList the data extraction do list
	 * @return the boolean
	 */
	public Boolean updateDataExtractionWithTransitStatus(
			List<DataExtractionDO> dataExtractionDoList);

	/**
	 * Gets the rto data for branch code.
	 *
	 * @param branchCode the branch code
	 * @param maxSize the max size
	 * @return the rto data for branch code
	 * @throws CGSystemException the cG system exception
	 */
	public List<RtnToOrgDO> getRtoDataForBranchCode(String branchCode,
			Integer maxSize) throws CGSystemException;

	/**
	 * Update return to origin with read by local.
	 *
	 * @param returnToOrgList the return to org list
	 * @return the boolean
	 */
	public Boolean updateReturnToOriginWithReadByLocal(
			List<RtnToOrgDO> returnToOrgList);

	/**
	 * Gets the non dox paper consignment.
	 *
	 * @param parentCN the parent cn
	 * @return the non dox paper consignment
	 * @throws CGSystemException the cG system exception
	 */
	public BookingDO getNonDoxPaperConsignment(String parentCN)
			throws CGSystemException;

	/**
	 * Update booking records read by local.
	 *
	 * @param bookingDOList the booking do list
	 * @return the boolean
	 */
	public Boolean updateBookingRecordsReadByLocal(List<BookingDO> bookingDOList);

	/**
	 * Gets the booking details by office code.
	 *
	 * @param officeCode the office code
	 * @param maxSize the max size
	 * @return the booking details by office code
	 * @throws CGSystemException the cG system exception
	 */
	public List<BookingDO> getBookingDetailsByOfficeCode(String officeCode,
			Integer maxSize) throws CGSystemException;

	/**
	 * Gets the extracted data by branch code.
	 *
	 * @param branchCode the branch code
	 * @return the extracted data by branch code
	 */
	public List<DataExtractionDO> getExtractedDataByBranchCode(String branchCode);

	/**
	 * Gets the all offices under ro.
	 *
	 * @param branch the branch
	 * @return the all offices under ro
	 * @throws CGSystemException the cG system exception
	 */
	public List<String> getAllOfficesUnderRo(String branch)
			throws CGSystemException;

	/**
	 * Gets the transit extracted data by branch code.
	 *
	 * @param branchCode the branch code
	 * @return the transit extracted data by branch code
	 * @throws CGSystemException the cG system exception
	 */
	public List<DataExtractionDO> getTransitExtractedDataByBranchCode(
			String branchCode) throws CGSystemException;
	
	/**
	 * Gets the transit extracted data.
	 *
	 * @param branchCode the branch code
	 * @param idList the id list
	 * @return the transit extracted data
	 * @throws CGSystemException the cG system exception
	 */
	public List<DataExtractionDO> getTransitExtractedData(
			String branchCode ,List<Integer> idList) throws CGSystemException;

	/**
	 * Gets the office details by branch code.
	 *
	 * @param branchCode the branch code
	 * @return the office details by branch code
	 * @throws CGSystemException the cG system exception
	 */
	public OfficeDO getOfficeDetailsByBranchCode(String branchCode)
			throws CGSystemException;

	/**
	 * Gets the all siblings of bo.
	 *
	 * @param branchCode the branch code
	 * @return the all siblings of bo
	 * @throws CGSystemException the cG system exception
	 */
	public List<String> getAllSiblingsOfBo(String branchCode)
			throws CGSystemException;

	/**
	 * Gets the all descendents of ro.
	 *
	 * @param branchCode the branch code
	 * @return the all descendents of ro
	 * @throws CGSystemException the cG system exception
	 */
	public List<String> getAllDescendentsOfRo(String branchCode)
			throws CGSystemException;

	/**
	 * Gets the delivery manifest dtls by branch code.
	 *
	 * @param branchCode the branch code
	 * @param maxSize the max size
	 * @return the delivery manifest dtls by branch code
	 * @throws CGSystemException the cG system exception
	 */
	public List<DeliveryDO> getDeliveryManifestDtlsByBranchCode(
			String branchCode, Integer maxSize) throws CGSystemException;

	/**
	 * Update dlv mnfst with read by local.
	 *
	 * @param dlvMnfstList the dlv mnfst list
	 * @return the boolean
	 */
	public Boolean updateDlvMnfstWithReadByLocal(List<DeliveryDO> dlvMnfstList);
	
	/**
	 * Modify data extraction status to transmission.
	 *
	 * @param dataExIdList the data ex id list
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean modifyDataExtractionStatusToTransmission(List<Integer> dataExIdList) throws CGSystemException;
	
	/**
	 * Restore data extraction status to transmission.
	 *
	 * @param dataExIdList the data ex id list
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean restoreDataExtractionStatusToTransmission(List<Integer> dataExIdList) throws CGSystemException;

	/**
	 * Update read by local flag in booking.
	 *
	 * @param dataExIdList the data ex id list
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean updateReadByLocalFlagInBooking(List<Integer> dataExIdList)
			throws CGSystemException;
	
	/**
	 * Update goods renewal with read by franchisee.
	 *
	 * @param goodsRenewalDOList the goods renewal do list
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean updateGoodsRenewalWithReadByFranchisee(
			List<Integer> goodsRenewalDOList)throws CGSystemException;

	/**
	 * Update goods cancl with read by franchisee.
	 *
	 * @param goodsCanclDOList the goods cancl do list
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean updateGoodsCanclWithReadByFranchisee(
			List<Integer> goodsCanclDOList)throws CGSystemException;
	
	/**
	 * Update goods issue with read by franchisee.
	 *
	 * @param goodsIssueDOList the goods issue do list
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean updateGoodsIssueWithReadByFranchisee(
			List<Integer> goodsIssueDOList)throws CGSystemException;
	
	/**
	 * Gets the all franchisee codes.
	 *
	 * @return the all franchisee codes
	 */
	public List<String> getAllFranchiseeCodes();

	/**
	 * Gets the goods renewal data for fr module.
	 *
	 * @param FrCodeList the fr code list
	 * @param maxGoodsRenewalSize the max goods renewal size
	 * @return the goods renewal data for fr module
	 * @throws CGSystemException the cG system exception
	 */
	public List<GoodsRenewalDO> getGoodsRenewalDataForFrModule(
			List<String> FrCodeList, Integer maxGoodsRenewalSize)
			throws CGSystemException;

	/**
	 * Gets the goods cancl data for fr module.
	 *
	 * @param FrCodeList the fr code list
	 * @param maxGoodsCanclSize the max goods cancl size
	 * @return the goods cancl data for fr module
	 * @throws CGSystemException the cG system exception
	 */
	public List<GoodscCancellationDO> getGoodsCanclDataForFrModule(
			List<String> FrCodeList, Integer maxGoodsCanclSize)
			throws CGSystemException;

	/**
	 * Gets the goods issue data for fr module.
	 *
	 * @param FrCodeList the fr code list
	 * @param maxSize the max size
	 * @return the goods issue data for fr module
	 * @throws CGSystemException the cG system exception
	 */
	public List<GoodsIssueDO> getGoodsIssueDataForFrModule(List<String> FrCodeList,
			Integer maxSize) throws CGSystemException;
	public void updateUserReadByLocal(List<UserDO> userDOList);
	public List<UserDO> getUserDetailsByOfficeCode(String officeCode,
			Integer maxSize) throws CGSystemException;
	public List<CGBaseEntity> getUnSyncData(String namedQuery, Integer maxRow)
			throws CGSystemException;
	public void updateUnSyncData(CGBaseEntity baseEntity);

	public List<BookingDO> getBookingDetailsByFranchiseeCode(List<String> frCodeList,
			Integer maxSize) throws CGSystemException;

	public Boolean updateFrSyncFlagInBookingForFranchisee(List<Integer> bookingExIdList)
			throws CGSystemException;
	
	
	public List<ManifestDO> getManifestDetailsByFranchiseeCode(List<String> frCodeList,
			Integer maxSize) throws CGSystemException;
	
	public Boolean updateFrSyncFlagInManifestForFranchisee(List<Integer> manifestExIdList)
	throws CGSystemException;

	public List<DeliveryDO> getDeliveryDetailsByFranchiseeCode(
			List<String> frCodeList, Integer maxDeliveryRecords) throws CGSystemException;

	public Boolean updateFrSyncFlagInDeliveryForFranchisee(
			List<Integer> deliveryExIdList) throws CGSystemException;

	public List<DispatchDO> getDispatchDataByFranchiseeCode(List<String> frCodeList,
			Integer maxSize) throws CGSystemException;
	
	public Boolean updateFrSyncFlagInDispatchForFranchisee(List<Integer> dispatchExIdList)
	throws CGSystemException;

}
