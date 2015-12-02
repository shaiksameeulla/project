/**
 * 
 */
package com.dtdc.bodbadmin.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseDO.CGBaseEntity;
import com.dtdc.domain.booking.BookingDO;
import com.dtdc.domain.dispatch.DispatchDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.RtnToOrgDO;
import com.dtdc.domain.purchase.GoodsIssueDO;
import com.dtdc.domain.purchase.GoodsRenewalDO;
import com.dtdc.domain.purchase.GoodscCancellationDO;
import com.dtdc.domain.transaction.delivery.DeliveryDO;
import com.dtdc.domain.transaction.heldup.HeldUpReleaseDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface LocalDBDataPersistDAO.
 *
 * @author nisahoo
 */
public interface LocalDBDataPersistDAO {

	/**
	 * Save booking data to local branch.
	 *
	 * @param bookingDOList the booking do list
	 * @throws CGSystemException the cG system exception
	 */
	public void saveBookingDataToLocalBranch(List<BookingDO> bookingDOList)
			throws CGSystemException;

	/**
	 * Save rto data to local branch.
	 *
	 * @param rtnToOrgDOList the rtn to org do list
	 */
	void saveRtoDataToLocalBranch(List<RtnToOrgDO> rtnToOrgDOList);
	
	/**
	 * Save manifest data to local branch.
	 *
	 * @param manifestDOList the manifest do list
	 */
	void saveManifestDataToLocalBranch(List<ManifestDO> manifestDOList);

	/**
	 * Save held up release to local branch.
	 *
	 * @param heldUpReleaseDoList the held up release do list
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean saveHeldUpReleaseToLocalBranch(
			List<HeldUpReleaseDO> heldUpReleaseDoList) throws CGSystemException;

	/**
	 * Save goods renewal to local branch.
	 *
	 * @param goodsRenewalDoList the goods renewal do list
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean saveGoodsRenewalToLocalBranch(
			List<GoodsRenewalDO> goodsRenewalDoList) throws CGSystemException;

	/**
	 * Save goods cancl to local branch.
	 *
	 * @param goodsCanclDoList the goods cancl do list
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean saveGoodsCanclToLocalBranch(
			List<GoodscCancellationDO> goodsCanclDoList)
			throws CGSystemException;

	/**
	 * Save dispatch details to local branch.
	 *
	 * @param dispatchDOList the dispatch do list
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean saveDispatchDetailsToLocalBranch(
			List<DispatchDO> dispatchDOList) throws CGSystemException;

	/**
	 * Save goods isue details to local branch.
	 *
	 * @param goodsIssueDoList the goods issue do list
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean saveGoodsIsueDetailsToLocalBranch(
			List<GoodsIssueDO> goodsIssueDoList) throws CGSystemException;

	/**
	 * Save dlv mnfst details to local branch.
	 *
	 * @param dlvMnfstDoList the dlv mnfst do list
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean saveDlvMnfstDetailsToLocalBranch(
			List<DeliveryDO> dlvMnfstDoList) throws CGSystemException;

	/**
	 * Gets the goods issue dtls by business key.
	 *
	 * @param extractedDo the extracted do
	 * @return the goods issue dtls by business key
	 * @throws CGSystemException the cG system exception
	 */
	public List<GoodsIssueDO> getGoodsIssueDtlsByBusinessKey(GoodsIssueDO extractedDo)
			throws CGSystemException;

	/**
	 * Delete goods issue dtls by issue id.
	 *
	 * @param issueDo the issue do
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean deleteGoodsIssueDtlsByIssueId(GoodsIssueDO issueDo)
			throws CGSystemException;

	/**
	 * Delete goods issue dtls list by issue id.
	 *
	 * @param existedDolist the existed dolist
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean deleteGoodsIssueDtlsListByIssueId(List<GoodsIssueDO> existedDolist)
			throws CGSystemException;

	/**
	 * Gets the manifest id.
	 *
	 * @param mnfstNumber the mnfst number
	 * @param consgNum the consg num
	 * @param mnfstType the mnfst type
	 * @param mnfstTypeId the mnfst type id
	 * @return the manifest id
	 * @throws CGSystemException the cG system exception
	 */
	public Integer getManifestId(String mnfstNumber, String consgNum,
			String mnfstType, Integer mnfstTypeId) throws CGSystemException;

	/**
	 * Save booking data to local branch.
	 *
	 * @param bookingDo the booking do
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean saveBookingDataToLocalBranch(BookingDO bookingDo)
			throws CGSystemException;

	public Boolean saveBaseEntityDataToLocalBranch(List<CGBaseEntity> baseEntityList)
	throws CGSystemException;
}
