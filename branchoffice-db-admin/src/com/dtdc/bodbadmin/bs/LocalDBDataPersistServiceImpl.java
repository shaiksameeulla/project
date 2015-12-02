/**
 * 
 */
package com.dtdc.bodbadmin.bs;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.constants.BusinessConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseDO.CGBaseEntity;
import com.dtdc.bodbadmin.dao.LocalDBDataPersistDAO;
import com.dtdc.bodbadmin.schema.booking.BookingDetailsData;
import com.dtdc.bodbadmin.schema.delivery.DeliveyManifestData;
import com.dtdc.bodbadmin.schema.dispatch.DispatchDetailsData;
import com.dtdc.bodbadmin.schema.heldupRelease.HeldupReleaseData;
import com.dtdc.bodbadmin.schema.manifest.ManifestData;
import com.dtdc.bodbadmin.schema.purchase.goodsCancellation.GoodsCancellationData;
import com.dtdc.bodbadmin.schema.purchase.goodsIssue.GoodsIssueData;
import com.dtdc.bodbadmin.schema.purchase.goodsRenewal.GoodsRenewalData;
import com.dtdc.bodbadmin.schema.rtoManifest.RtoData;
import com.dtdc.bodbadmin.utility.TransferObjectToDomainObjectConverter;
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
 * The Class LocalDBDataPersistServiceImpl.
 *
 * @author nisahoo
 */
public class LocalDBDataPersistServiceImpl implements LocalDBDataPersistService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger
			.getLogger(LocalDBDataPersistServiceImpl.class);

	/** The domain object converter. */
	private TransferObjectToDomainObjectConverter domainObjectConverter;
	
	/** The local db data persist dao. */
	private LocalDBDataPersistDAO localDBDataPersistDAO;

	/**
	 * Gets the domain object converter.
	 *
	 * @return the domain object converter
	 */
	public TransferObjectToDomainObjectConverter getDomainObjectConverter() {
		return domainObjectConverter;
	}

	/**
	 * Sets the domain object converter.
	 *
	 * @param domainObjectConverter the new domain object converter
	 */
	public void setDomainObjectConverter(
			TransferObjectToDomainObjectConverter domainObjectConverter) {
		this.domainObjectConverter = domainObjectConverter;
	}

	/**
	 * Gets the local db data persist dao.
	 *
	 * @return the local db data persist dao
	 */
	public LocalDBDataPersistDAO getLocalDBDataPersistDAO() {
		return localDBDataPersistDAO;
	}

	/**
	 * Sets the local db data persist dao.
	 *
	 * @param localDBDataPersistDAO the new local db data persist dao
	 */
	public void setLocalDBDataPersistDAO(
			LocalDBDataPersistDAO localDBDataPersistDAO) {
		this.localDBDataPersistDAO = localDBDataPersistDAO;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.bs.LocalDBDataPersistService#persistBookingDataToLocalDB(BookingDetailsData)
	 */
	@Override
	public void persistBookingDataToLocalDB(BookingDetailsData bookingData)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistBookingDataToLocalDB() : START");

		try {
			List<BookingDO> bookingDOList = domainObjectConverter
					.prepareDataForBookingDO(bookingData);
			if (bookingDOList != null) {
				localDBDataPersistDAO
						.saveBookingDataToLocalBranch(bookingDOList);
			}
		} catch (Exception e) {
			LOGGER.error("LocalDBDataPersistServiceImpl : persistBookingDataToLocalDB()  : EXCEPTION"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistBookingDataToLocalDB() : END");

	}
	
	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.bs.LocalDBDataPersistService#persistRtoDataToLocalDB(RtoData)
	 */
	@Override
	public void persistRtoDataToLocalDB(RtoData rtoData)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistManifestDataToLocalDB() : START");
		try {
			List<RtnToOrgDO> rtnToOrgDOList = domainObjectConverter
					.createRtnToOrgDOFromRtoData(rtoData);
			if (rtnToOrgDOList != null) {
				localDBDataPersistDAO.saveRtoDataToLocalBranch(rtnToOrgDOList);
			}
		} catch (Exception e) {
			LOGGER.error("LocalDBDataPersistServiceImpl : persistManifestDataToLocalDB()  : EXCEPTION"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistManifestDataToLocalDB() : END");
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.bs.LocalDBDataPersistService#persistManifestDataToLocalDB(ManifestData)
	 */
	@Override
	public void persistManifestDataToLocalDB(ManifestData manifestData)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistManifestDataToLocalDB() : START");
		Integer manifestId = 0;
		List<ManifestDO> manifestDOListUpdated = null;
		try {
			List<ManifestDO> manifestDOList = domainObjectConverter
					.createManifestDOFromManifestTO(manifestData);
			if (manifestDOList != null) {
				manifestDOListUpdated = new ArrayList<ManifestDO>();
				for (ManifestDO manifest : manifestDOList) {
					// Setting Primary Key
					if (StringUtils.equalsIgnoreCase(manifest.getReadByLocal(),
							BusinessConstants.READ_BY_LOCAL_FLAG_MODIFY)) {
						manifestId = localDBDataPersistDAO.getManifestId(
								manifest.getManifestNumber(), manifest
										.getConsgNumber(), manifest
										.getManifestType(), manifest
										.getMnsftTypes().getMnfstTypeId());
						if (manifestId > 0) {
							manifest.setManifestId(manifestId);
						}
					}
					manifestDOListUpdated.add(manifest);
				}
				localDBDataPersistDAO
						.saveManifestDataToLocalBranch(manifestDOListUpdated);
			}
		} catch (Exception e) {
			LOGGER.error("LocalDBDataPersistServiceImpl : persistManifestDataToLocalDB()  : EXCEPTION"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistManifestDataToLocalDB() : END");
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.bs.LocalDBDataPersistService#persistHeldUpReleaseToLocalDB(HeldupReleaseData)
	 */
	@Override
	public Boolean persistHeldUpReleaseToLocalDB(
			HeldupReleaseData heldupReleaseData) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistHeldUpReleaseToLocalDB() : START");
		try {
			List<HeldUpReleaseDO> heldUpReleaseDoList = domainObjectConverter
					.createHeldupReleaseDoFromDataObject(heldupReleaseData);
			if (heldUpReleaseDoList != null && !heldUpReleaseDoList.isEmpty()) {
				localDBDataPersistDAO
						.saveHeldUpReleaseToLocalBranch(heldUpReleaseDoList);
			}
		} catch (Exception e) {
			LOGGER.error("LocalDBDataPersistServiceImpl : persistHeldUpReleaseToLocalDB() : EXCEPTION"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistHeldUpReleaseToLocalDB() : END");
		return true;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.bs.LocalDBDataPersistService#persistGoodsRenewalToLocalDB(GoodsRenewalData)
	 */
	@Override
	public Boolean persistGoodsRenewalToLocalDB(
			GoodsRenewalData goodsRenewalData) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistGoodsRenewalToLocalDB() : START");
		try {
			List<GoodsRenewalDO> goodsRenewalDoList = domainObjectConverter
					.createGoodsRenewalDoFromDataObject(goodsRenewalData);
			if (goodsRenewalDoList != null && !goodsRenewalDoList.isEmpty()) {
				localDBDataPersistDAO
						.saveGoodsRenewalToLocalBranch(goodsRenewalDoList);
			}
		} catch (Exception e) {
			LOGGER.error("LocalDBDataPersistServiceImpl : persistGoodsCanclToLocalDB() : EXCEPTION"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistGoodsRenewalToLocalDB() : END");
		return true;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.bs.LocalDBDataPersistService#persistGoodsCanclToLocalDB(GoodsCancellationData)
	 */
	@Override
	public Boolean persistGoodsCanclToLocalDB(
			GoodsCancellationData goodsCanclData) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistGoodsCanclToLocalDB() : START");
		try {
			List<GoodscCancellationDO> goodsCanclDoList = domainObjectConverter
					.createGoodsCanclDoFromDataObject(goodsCanclData);
			if (goodsCanclDoList != null && !goodsCanclDoList.isEmpty()) {
				localDBDataPersistDAO
						.saveGoodsCanclToLocalBranch(goodsCanclDoList);
			}
		} catch (Exception e) {
			LOGGER.error("LocalDBDataPersistServiceImpl : persistGoodsCanclToLocalDB() : EXCEPTION"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistGoodsCanclToLocalDB() : END");
		return true;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.bs.LocalDBDataPersistService#persistDispatchDetailsToLocalDB(DispatchDetailsData)
	 */
	@Override
	public Boolean persistDispatchDetailsToLocalDB(
			DispatchDetailsData dispatchDetailsData)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistGoodsCanclToLocalDB() : START");
		try {
			List<DispatchDO> dispatchDOList = domainObjectConverter
					.createDispatchDoFromDataObject(dispatchDetailsData);
			if (dispatchDOList != null && !dispatchDOList.isEmpty()) {
				localDBDataPersistDAO
						.saveDispatchDetailsToLocalBranch(dispatchDOList);
			}
		} catch (Exception e) {
			LOGGER.error("LocalDBDataPersistServiceImpl : persistGoodsCanclToLocalDB() : EXCEPTION"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistGoodsCanclToLocalDB() : END");
		return true;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.bs.LocalDBDataPersistService#persistGoodsIssueDetailsToLocalDB(GoodsIssueData)
	 */
	@Override
	public Boolean persistGoodsIssueDetailsToLocalDB(
			GoodsIssueData goodsIssueData) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistGoodsIssueDetailsToLocalDB() : START");
		try {
			List<GoodsIssueDO> goodsIssueDoList = domainObjectConverter
					.createGoodsIssueDoFromDataObject(goodsIssueData);
			LOGGER.debug("LocalDBDataPersistServiceImpl : persistGoodsIssueDetailsToLocalDB() : goodsIssueDoList size"
					+ goodsIssueDoList);
			if (goodsIssueDoList != null && !goodsIssueDoList.isEmpty()) {
				processGoodsIssueDoList(goodsIssueDoList);
				localDBDataPersistDAO
						.saveGoodsIsueDetailsToLocalBranch(goodsIssueDoList);
				LOGGER.debug("LocalDBDataPersistServiceImpl : persistGoodsIssueDetailsToLocalDB() : saved sucessfully");
			}
		} catch (Exception e) {
			LOGGER.error("LocalDBDataPersistServiceImpl : persistGoodsIssueDetailsToLocalDB() : EXCEPTION"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistGoodsIssueDetailsToLocalDB() : END");
		return true;
	}

	/**
	 * Added by Narasimha Rao Kattunga Preparing the Dlv mnaifest domain object.
	 *
	 * @param dlvMnfstData the dlv mnfst data
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Boolean persistDlvMnfstDetailsToLocalDB(
			DeliveyManifestData dlvMnfstData) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistDlvMnfstDetailsToLocalDB() : START");
		try {
			List<DeliveryDO> dlvMnfstList = domainObjectConverter
					.createDlvMnfstDOFromDataObject(dlvMnfstData);
			LOGGER.debug("LocalDBDataPersistServiceImpl : persistDlvMnfstDetailsToLocalDB() : dlvMnfstList size"
					+ dlvMnfstList);
			if (dlvMnfstList != null && !dlvMnfstList.isEmpty()) {
				// Preparing delivery related objects
				// Setting Primary Key

				localDBDataPersistDAO
						.saveDlvMnfstDetailsToLocalBranch(dlvMnfstList);
				LOGGER.debug("LocalDBDataPersistServiceImpl : persistDlvMnfstDetailsToLocalDB() : saved sucessfully");
			}
		} catch (Exception e) {
			LOGGER.error("LocalDBDataPersistServiceImpl : persistGoodsIssueDetailsToLocalDB() : EXCEPTION"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistGoodsIssueDetailsToLocalDB() : END");
		return true;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.bs.LocalDBDataPersistService#processGoodsIssueDoList(List)
	 */
	@Override
	public Boolean processGoodsIssueDoList(List<GoodsIssueDO> issueDoList)
			throws CGBusinessException, CGSystemException {
		Boolean result = false;
		LOGGER.debug("LocalDBDataPersistServiceImpl :** processGoodsIssueDoList()** : START");
		List<GoodsIssueDO> existedDoList = new ArrayList<GoodsIssueDO>(0);
		if (issueDoList != null && !issueDoList.isEmpty()) {
			for (GoodsIssueDO issueDo : issueDoList) {
				if (issueDo.getReadByLocal().equalsIgnoreCase(
						BusinessConstants.READ_BY_LOCAL_FLAG_MODIFY)) {
					List<GoodsIssueDO> existedDo = localDBDataPersistDAO
							.getGoodsIssueDtlsByBusinessKey(issueDo);
					if (existedDo != null && !existedDo.isEmpty()) {
						existedDoList.addAll(existedDo);
						LOGGER.debug("LocalDBDataPersistServiceImpl :** processGoodsIssueDoList() **: RECORD FOUND WITH GOODS ISSUE DO Size["
								+ existedDo.size() + "]");
					}
				}
			}
			if (!existedDoList.isEmpty()) {
				localDBDataPersistDAO
						.deleteGoodsIssueDtlsListByIssueId(existedDoList);
				result = true;
			}

		}

		result = true;
		LOGGER.debug("LocalDBDataPersistServiceImpl :** processGoodsIssueDoList() **: START");
		return result;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.bs.LocalDBDataPersistService#getBookingDoListFromJAXB(BookingDetailsData)
	 */
	@Override
	public List<BookingDO> getBookingDoListFromJAXB(
			BookingDetailsData bookingData) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("LocalDBDataPersistServiceImpl : getBookingDoListFromJAXB() : START");
		List<BookingDO> bookingDOList = domainObjectConverter
		.prepareDataForBookingDO(bookingData);
		LOGGER.debug("LocalDBDataPersistServiceImpl : getBookingDoListFromJAXB() : bookingDOList size :["+bookingDOList!=null && !bookingDOList.isEmpty()?bookingDOList.size():0);
		LOGGER.debug("LocalDBDataPersistServiceImpl : getBookingDoListFromJAXB() : END");
		return bookingDOList;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.bs.LocalDBDataPersistService#persistBookingDoToLocalDB(BookingDO)
	 */
	@Override
	public Boolean persistBookingDoToLocalDB(BookingDO bookingDo)
			throws CGBusinessException, CGSystemException {
		Boolean result = Boolean.FALSE;
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistBookingDoToLocalDB()(single Booking Do) : START");
		try {
			result = localDBDataPersistDAO.saveBookingDataToLocalBranch(bookingDo);
			
		} catch (Exception e) {
			LOGGER.error("LocalDBDataPersistServiceImpl : persistBookingDoToLocalDB()(single Booking Do) : EXCEPTION"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistBookingDoToLocalDB()(single Booking Do) : END");
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.bs.LocalDBDataPersistService#persistBookingDoListToLocalDB(List)
	 */
	@Override
	public Boolean persistBookingDoListToLocalDB(List<BookingDO> bookingDoList)
			throws CGBusinessException, CGSystemException {
		Boolean result = Boolean.FALSE;
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistBookingDoToLocalDB() : START");
		try {
			if(bookingDoList !=null && !bookingDoList.isEmpty()){
			localDBDataPersistDAO
			.saveBookingDataToLocalBranch(bookingDoList);
			}else{
				LOGGER.error("LocalDBDataPersistServiceImpl : persistBookingDoToLocalDB() : ---> Empty booking Do List , Hence throwing Business Exception");
				throw new CGBusinessException();
			}
		} catch (Exception e) {
			LOGGER.error("LocalDBDataPersistServiceImpl : persistBookingDoToLocalDB() : EXCEPTION"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistBookingDoToLocalDB() : END");
		return result;
	}

	public Boolean persistBaseEntityToLocalDB(List<CGBaseEntity> baseEntity)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistBaseEntityToLocalDB() : START");
		Boolean success =false;
		String a =""; 
		try {
			if (baseEntity != null) {
				
				success=localDBDataPersistDAO
						.saveBaseEntityDataToLocalBranch(baseEntity);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block

			LOGGER.error("LocalDBDataPersistServiceImpl : persistBaseEntityToLocalDB()  : EXCEPTION"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		
		LOGGER.debug("LocalDBDataPersistServiceImpl : persistBaseEntityToLocalDB() : END");
		return success;
	}
}
