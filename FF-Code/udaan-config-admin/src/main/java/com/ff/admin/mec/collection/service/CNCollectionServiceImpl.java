/**
 * 
 */
package com.ff.admin.mec.collection.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.mec.collection.converter.CollectionConverter;
import com.ff.admin.mec.collection.dao.CollectionCommonDAO;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.mec.collection.CollectionDtlsDO;
import com.ff.mec.collection.CNCollectionDtlsTO;
import com.ff.mec.collection.CNCollectionTO;

/**
 * @author prmeher
 * 
 */
public class CNCollectionServiceImpl implements CNCollectionService {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CNCollectionServiceImpl.class);

	/** The collectionCommonDAO. */
	private CollectionCommonDAO collectionCommonDAO;

	/**
	 * @param collectionCommonDAO
	 *            the collectionCommonDAO to set
	 */
	public void setCollectionCommonDAO(CollectionCommonDAO collectionCommonDAO) {
		this.collectionCommonDAO = collectionCommonDAO;
	}

	/**
	 * Gets the All delivered consignments details for Day.
	 * 
	 * @param todayDate
	 * @param originOffId
	 * @return DeliveryDetailsTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@Override
	public CNCollectionTO getTodaysDeliverdConsgDtls(
			CNCollectionTO cnCollectionTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("CNCollectionServiceImpl :: getTodaysDeliverdConsgDtls() :: Start --------> ::::::");
		List<CollectionDtlsDO> collectionDtls = null;
		CNCollectionTO cnCollection = null;
		// boolean result = Boolean.FALSE;

		// String Format (DD/MM/YYYY)
		cnCollectionTO.setCurrentDate(DateUtil.getCurrentDateInDDMMYYYY());
		cnCollectionTO.setNextDate(DateUtil.getCurrentDateInDDMMYYYY());
		// Date format (DD/MM/YYYY HH:MM:SS)
		cnCollectionTO
				.setCurrentDt(DateUtil.combineDateWithTimeHHMMSS(
						cnCollectionTO.getCurrentDate(),
						MECCommonConstants.MIN_HHMMSS));
		cnCollectionTO.setNextDt(DateUtil.combineDateWithTimeHHMMSS(
				cnCollectionTO.getNextDate(), MECCommonConstants.MAX_HHMMSS));

		/* To get All Todays delivered CN detail(s) from collection detail(s) */
		collectionDtls = collectionCommonDAO
				.getAllDeliverdConsgDtlsByDate(cnCollectionTO);
		if (!CGCollectionUtils.isEmpty(collectionDtls)
				&& collectionDtls.size() > 0) {
			cnCollection = new CNCollectionTO();
			List<CNCollectionDtlsTO> cnCollectionDtlsTO = new ArrayList<CNCollectionDtlsTO>();
			for (CollectionDtlsDO collnDtls : collectionDtls) {
				if (!StringUtil.isNull(collnDtls.getCollectionDO())) {
					// Already Saved Detail(s)
					CollectionDO collectionDO = collnDtls.getCollectionDO();
					/*
					 * If status SUBMITTED or VALIDATED then it should not
					 * display to screen
					 */
					/*
					 * if (!StringUtil.equals(collectionDO.getStatus(),
					 * MECCommonConstants.STATUS_OPENED)) continue;
					 */
					/*
					 * for (CollectionDtlsDO cnDtlsDO : collectionDO
					 * .getCollectionDtls()) {
					 */
					CNCollectionDtlsTO cnDtls = new CNCollectionDtlsTO();
					cnDtls.setBankName(collectionDO.getBankName());
					cnDtls.setChqDate(DateUtil
							.getDDMMYYYYDateToString(collectionDO.getChqDate()));
					cnDtls.setChqNo(collectionDO.getChqNo());
					cnDtls.setCnfor(collnDtls.getCollectionFor());
					if (!StringUtil.isNull(collnDtls.getConsgDO())) {
						cnDtls.setCnNo(collnDtls.getConsgDO().getConsgNo());
						cnDtls.setConsgId(collnDtls.getConsgDO().getConsgId());

					}
					cnDtls.setCollectionID(collectionDO.getCollectionId());
					cnDtls.setCollectionType(collnDtls.getCollectionType());
					cnDtls.setEntryId(collnDtls.getEntryId());
					if (!StringUtil.isNull(collectionDO.getPaymentModeDO())) {
						cnDtls.setPaymentModeId(collectionDO.getPaymentModeDO()
								.getPaymentId());
					}
					if (!StringUtil.isEmptyDouble(collnDtls.getBillAmount()))
						cnDtls.setAmount(collnDtls.getBillAmount());
					if (!StringUtil.isEmptyDouble(collnDtls.getRecvAmount()))
						cnDtls.setRcvdAmt(collnDtls.getRecvAmount());
					if (!StringUtil.isEmptyDouble(collnDtls.getTdsAmount()))
						cnDtls.setTdsAmt(collnDtls.getTdsAmount());
					if (!StringUtil.isEmptyDouble(collnDtls
							.getTotalBillAmount()))
						cnDtls.setTotal(collnDtls.getTotalBillAmount());

					/* Total Amount */
					/*
					 * if (!StringUtil
					 * .isEmptyDouble(collectionDO.getTotalAmount()))
					 * cnDtls.setTotal(collectionDO.getTotalAmount());
					 */

					cnDtls.setReceiptNo(collnDtls.getReceiptNo());
					// cnDtls.setSrNo(cnDtlsDO.getPosition());
					cnDtls.setTxnNo(collectionDO.getTxnNo());
					cnDtls.setStatus(collectionDO.getStatus());
					if (!StringUtil.isNull(collnDtls.getReasonDO())) {
						cnDtls.setReasonId(collnDtls.getReasonDO()
								.getReasonId());
					}
					if (!StringUtil.isNull(collnDtls.getConsgDeliveryDate())) {
						cnDtls.setConsgDeliveryDate(DateUtil
								.getDateInDDMMYYYYHHMMSlashFormat(collnDtls
										.getConsgDeliveryDate()));
					}
					// Added by Himal - Bank GL
					if (!StringUtil.isNull(collectionDO.getBankGLDO())) {
						cnDtls.setBankGL(collectionDO.getBankGLDO().getGlId());
					}
					if (!StringUtil.isEmptyInteger(collectionDO.getCreatedBy())) {
						cnDtls.setCreatedBy(collectionDO.getCreatedBy());
					}
					if (!StringUtil.isEmptyInteger(collectionDO.getUpdatedBy())) {
						cnDtls.setUpdatedBy(collectionDO.getUpdatedBy());
					}
					cnCollectionDtlsTO.add(cnDtls);
					// }
				} /*
				 * else {// New CN Detail(s) available CNCollectionDtlsTO cnDtls
				 * = new CNCollectionDtlsTO();
				 * cnDtls.setEntryId(collnDtls.getEntryId()); if
				 * (!StringUtil.isNull(collnDtls.getConsgDO())) {
				 * cnDtls.setCnNo(collnDtls.getConsgDO().getConsgNo());
				 * cnDtls.setConsgId(collnDtls.getConsgDO().getConsgId());
				 * cnDtls.setAmount(collnDtls.getBillAmount()); } if
				 * (!StringUtil.isNull(collnDtls.getConsgDeliveryDate())) {
				 * cnDtls.setConsgDeliveryDate(DateUtil
				 * .getDateInDDMMYYYYHHMMSlashFormat(collnDtls
				 * .getConsgDeliveryDate())); }
				 * cnDtls.setCollectionType(collnDtls.getCollectionType());
				 * cnCollectionDtlsTO.add(cnDtls); }
				 */
			}
			Collections.sort(cnCollectionDtlsTO);
			cnCollection.setCnCollectionDtls(cnCollectionDtlsTO);
		} else {
			throw new CGBusinessException(
					AdminErrorConstants.NO_CONSG_DTLS_FOUND_FOR_TODAYS_DATE);
		}
		LOGGER.trace("CNCollectionServiceImpl :: getTodaysDeliverdConsgDtls() :: END --------> ::::::");
		return cnCollection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.mec.collection.service.CNCollectionService#
	 * saveOrUpdateCNCollection(com.ff.mec.collection.CNCollectionTO)
	 */
	@Override
	public CNCollectionTO saveOrUpdateCNCollection(CNCollectionTO cnCollectionTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("CNCollectionServiceImpl :: saveOrUpdateCNCollection() :: Start --------> ::::::");
		if (!StringUtil.isNull(cnCollectionTO)) {
			for (CNCollectionDtlsTO cnCollectionDtls : cnCollectionTO
					.getCnCollectionDtls()) {
				CollectionDO collectionDO = CollectionConverter
						.cnCollectionDomainConverter(cnCollectionTO,
								cnCollectionDtls);
				if (!StringUtil.isNull(collectionDO)) {
					collectionCommonDAO.saveOrUpdateCNCollection(collectionDO);
					cnCollectionTO.setIsSaved(CommonConstants.SUCCESS);
				} else {
					throw new CGBusinessException(
							AdminErrorConstants.DETAILS_NOT_SAVED);
				}
			}
		}
		LOGGER.trace("CNCollectionServiceImpl :: saveOrUpdateCNCollection() :: END --------> ::::::");
		return cnCollectionTO;
	}

}
