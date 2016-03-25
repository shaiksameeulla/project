package com.ff.admin.mec.collection.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.mec.collection.dao.ValidateCollectionDAO;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.mec.collection.ValidateCollectionEntryDtlsTO;
import com.ff.mec.collection.ValidateCollectionEntryTO;

/**
 * @author prmeher
 * 
 */
public class ValidateCollectionServiceImpl implements ValidateCollectionService {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ValidateCollectionServiceImpl.class);

	/** The validate collection DAO. */
	ValidateCollectionDAO validateCollectionDAO;

	/**
	 * @param validateCollectionDAO
	 *            the validateCollectionDAO to set
	 */
	public void setValidateCollectionDAO(
			ValidateCollectionDAO validateCollectionDAO) {
		this.validateCollectionDAO = validateCollectionDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.mec.collection.service.ValidateCollectionService#
	 * searchCollectionDetlsForValidation(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ValidateCollectionEntryTO searchCollectionDetlsForValidation(
			String frmDate, String toDate, String stationId, String officeId,
			String headerStatus, String headerTransNo)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ValidateCollectionServiceImpl :: searchCollectionDetlsForValidation() :: Start --------> ::::::");
		Date fromDate = DateUtil.slashDelimitedstringToDDMMYYYYFormat(frmDate);
		Date to_Date = DateUtil.slashDelimitedstringToDDMMYYYYFormat(toDate);
		Integer origOfficeId = StringUtil.parseInteger(officeId);

		List<CollectionDO> collectionDos = validateCollectionDAO
				.searchCollectionDetlsForValidation(fromDate, to_Date,
						origOfficeId, headerStatus, headerTransNo);
		ValidateCollectionEntryTO validateCollectionEntryTO = null;
		if (!StringUtil.isEmptyColletion(collectionDos)) {
			validateCollectionEntryTO = convertCollectionDomainToValidateCollectionTransferObject(collectionDos);
		} else {
			throw new CGBusinessException(AdminErrorConstants.NO_DTLS_FOUND);
		}
		LOGGER.trace("ValidateCollectionServiceImpl :: searchCollectionDetlsForValidation() :: End --------> ::::::");
		return validateCollectionEntryTO;
	}

	/**
	 * To convert COllection domain object to transfer object
	 * 
	 * @param collectionDos
	 * @return validateCollectionEntry
	 */
	public ValidateCollectionEntryTO convertCollectionDomainToValidateCollectionTransferObject(
			List<CollectionDO> collectionDos) {
		LOGGER.trace("ValidateCollectionServiceImpl :: convertCollectionDomainToValidateCollectionTransferObject() :: Start --------> ::::::");
		ValidateCollectionEntryTO validateCollectionEntry = new ValidateCollectionEntryTO();
		List<ValidateCollectionEntryDtlsTO> validateCollectionEntryDtls = new ArrayList<ValidateCollectionEntryDtlsTO>(
				collectionDos.size());
		for (CollectionDO collectionDO : collectionDos) {
			ValidateCollectionEntryDtlsTO validateEntry = new ValidateCollectionEntryDtlsTO();
			validateEntry.setTransactionNo(collectionDO.getTxnNo());
			validateEntry.setTransactionDate(DateUtil
					.getDDMMYYYYDateString(collectionDO.getCollectionDate()));
			if (!StringUtil.isNull(collectionDO.getCustomerDO()))
				validateEntry.setCustName(collectionDO.getCustomerDO()
						.getBusinessName());
			if (!StringUtil.isStringEmpty(collectionDO.getCollectionCategory())
					&& collectionDO.getCollectionCategory().equals(
							MECCommonConstants.BILL_COLLECTION_TYPE)) {
				validateEntry.setCollectionAgainst("BILL");
				validateEntry.setCollectionType(collectionDO
						.getCollectionCategory());
			}
			if (!StringUtil.isStringEmpty(collectionDO.getCollectionCategory())
					&& collectionDO.getCollectionCategory().equals(
							MECCommonConstants.CN_COLLECTION_TYPE)) {
				validateEntry.setCollectionAgainst("CN");
				validateEntry.setCollectionType(collectionDO
						.getCollectionCategory());
			}
			if (!StringUtil.isNull(collectionDO.getPaymentModeDO())) {
				validateEntry.setPaymentMode(collectionDO.getPaymentModeDO()
						.getPaymentType());
			}
			if (!StringUtil.isEmptyDouble(collectionDO.getTotalAmount())) {
				validateEntry.setAmount(collectionDO.getTotalAmount());
			}
			if (!StringUtil.isEmptyInteger(collectionDO.getCollectionOfficeDO()
					.getOfficeId())) {
				validateEntry.setCollectionOfficeId(collectionDO
						.getCollectionOfficeDO().getOfficeId());
			}
			if (collectionDO.getStatus().equals(MECCommonConstants.STATUS_SAVE))
				validateEntry.setStatus(MECCommonConstants.SAVED);
			if (collectionDO.getStatus().equals(
					MECCommonConstants.STATUS_SUBMITTED))
				validateEntry.setStatus(MECCommonConstants.SUBMITTED);
			if (collectionDO.getStatus().equals(
					MECCommonConstants.STATUS_VALIDATED))
				validateEntry.setStatus(MECCommonConstants.VALIDATED);
			validateCollectionEntryDtls.add(validateEntry);
		}
		validateCollectionEntry.setCollectionDtls(validateCollectionEntryDtls);
		LOGGER.trace("ValidateCollectionServiceImpl :: convertCollectionDomainToValidateCollectionTransferObject() :: End --------> ::::::");
		return validateCollectionEntry;
	}

	@Override
	public void validateTxns(List<String> txnsList, Integer updatedBy)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ValidateCollectionServiceImpl :: validateTxns() :: START");
		validateCollectionDAO.validateTxns(txnsList, updatedBy);
		LOGGER.trace("ValidateCollectionServiceImpl :: validateTxns() :: END");
	}

}
