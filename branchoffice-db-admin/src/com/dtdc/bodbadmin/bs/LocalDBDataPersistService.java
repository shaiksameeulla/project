/**
 * 
 */
package com.dtdc.bodbadmin.bs;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseDO.CGBaseEntity;
import com.dtdc.bodbadmin.schema.booking.BookingDetailsData;
import com.dtdc.bodbadmin.schema.delivery.DeliveyManifestData;
import com.dtdc.bodbadmin.schema.dispatch.DispatchDetailsData;
import com.dtdc.bodbadmin.schema.heldupRelease.HeldupReleaseData;
import com.dtdc.bodbadmin.schema.manifest.ManifestData;
import com.dtdc.bodbadmin.schema.purchase.goodsCancellation.GoodsCancellationData;
import com.dtdc.bodbadmin.schema.purchase.goodsIssue.GoodsIssueData;
import com.dtdc.bodbadmin.schema.purchase.goodsRenewal.GoodsRenewalData;
import com.dtdc.bodbadmin.schema.rtoManifest.RtoData;
import com.dtdc.domain.booking.BookingDO;
import com.dtdc.domain.purchase.GoodsIssueDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface LocalDBDataPersistService.
 *
 * @author nisahoo
 */
public interface LocalDBDataPersistService {

	/**
	 * Persist booking data to local db.
	 *
	 * @param bookingDataTO the booking data to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public void persistBookingDataToLocalDB(BookingDetailsData bookingDataTO)
			throws CGBusinessException,CGSystemException;

	/**
	 * Persist rto data to local db.
	 *
	 * @param rtoData the rto data
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public void persistRtoDataToLocalDB(RtoData rtoData)
			throws CGBusinessException,CGSystemException;
	
	/**
	 * Persist manifest data to local db.
	 *
	 * @param manifestData the manifest data
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public void persistManifestDataToLocalDB(ManifestData manifestData)
		throws CGBusinessException,CGSystemException;

	/**
	 * Persist held up release to local db.
	 *
	 * @param heldupReleaseData the heldup release data
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean persistHeldUpReleaseToLocalDB(
			HeldupReleaseData heldupReleaseData) throws CGBusinessException,CGSystemException;

	/**
	 * Persist goods renewal to local db.
	 *
	 * @param goodsRenewalData the goods renewal data
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean persistGoodsRenewalToLocalDB(
			GoodsRenewalData goodsRenewalData) throws CGBusinessException,CGSystemException;

	/**
	 * Persist goods cancl to local db.
	 *
	 * @param goodsCanclData the goods cancl data
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean persistGoodsCanclToLocalDB(
			GoodsCancellationData goodsCanclData) throws CGBusinessException,CGSystemException;

	/**
	 * Persist dispatch details to local db.
	 *
	 * @param dispatchDetailsData the dispatch details data
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean persistDispatchDetailsToLocalDB(
			DispatchDetailsData dispatchDetailsData) throws CGBusinessException,CGSystemException;

	/**
	 * Persist goods issue details to local db.
	 *
	 * @param goodsIssueData the goods issue data
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean persistGoodsIssueDetailsToLocalDB(
			GoodsIssueData goodsIssueData) throws CGBusinessException,CGSystemException;

	/**
	 * Persist dlv mnfst details to local db.
	 *
	 * @param dlvMnfstData the dlv mnfst data
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean persistDlvMnfstDetailsToLocalDB(
			DeliveyManifestData dlvMnfstData) throws CGBusinessException,CGSystemException;
	
	/**
	 * Process goods issue do list.
	 *
	 * @param issueDoList the issue do list
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean processGoodsIssueDoList(
			List<GoodsIssueDO>  issueDoList) throws CGBusinessException,CGSystemException;

	
	/**
	 * Gets the booking do list from jaxb.
	 *
	 * @param bookingDataTO the booking data to
	 * @return the booking do list from jaxb
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public List<BookingDO> getBookingDoListFromJAXB(BookingDetailsData bookingDataTO)
	throws CGBusinessException,CGSystemException;
	
	/**
	 * Persist booking do to local db.
	 *
	 * @param bookingDo the booking do
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean persistBookingDoToLocalDB(BookingDO bookingDo)
	throws CGBusinessException,CGSystemException;

	/**
	 * Persist booking do list to local db.
	 *
	 * @param bookingDoList the booking do list
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean persistBookingDoListToLocalDB(List<BookingDO> bookingDoList)
			throws CGBusinessException, CGSystemException;
	
	public Boolean persistBaseEntityToLocalDB(List<CGBaseEntity> baseEntity)
	throws CGBusinessException,CGSystemException;
}
