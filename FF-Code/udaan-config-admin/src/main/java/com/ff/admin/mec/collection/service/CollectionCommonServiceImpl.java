package com.ff.admin.mec.collection.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.admin.mec.collection.dao.CollectionCommonDAO;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.to.mec.BankTO;
import com.ff.universe.mec.service.MECUniversalService;

/**
 * @author prmeher
 * 
 */
public class CollectionCommonServiceImpl implements CollectionCommonService {

	/** The collectionCommonDAO. */
	private CollectionCommonDAO collectionCommonDAO;

	/** The mecUniversalService. */
	private MECUniversalService mecUniversalService;

	/**
	 * @param mecUniversalService
	 *            the mecUniversalService to set
	 */
	public void setMecUniversalService(MECUniversalService mecUniversalService) {
		this.mecUniversalService = mecUniversalService;
	}

	/**
	 * @param collectionCommonDAO
	 *            the collectionCommonDAO to set
	 */
	public void setCollectionCommonDAO(CollectionCommonDAO collectionCommonDAO) {
		this.collectionCommonDAO = collectionCommonDAO;
	}

	@Override
	public String getCollectionStatus(String transactionNo)
			throws CGBusinessException, CGSystemException {
		String status = collectionCommonDAO.getCollectionStatus(transactionNo);
		return status;
	}

	@Override
	public List<PaymentModeTO> getPaymentModeDtls(String MECProcessCode)
			throws CGBusinessException, CGSystemException {
		List<PaymentModeTO> modes = mecUniversalService
				.getPaymentModeDtls(MECProcessCode);
		return modes;
	}

	@Override
	public List<BankTO> getAllBankDtls() throws CGBusinessException,
			CGSystemException {
		List<BankTO> bankTO = mecUniversalService.getAllBankDtls();
		return bankTO;
	}

	@Override
	public boolean updateCollectionStatus(String status, Integer collectionId)
			throws CGBusinessException, CGSystemException {
		Boolean isUpdated = collectionCommonDAO.updateCollectionStatus(status,
				collectionId);
		return isUpdated;
	}

}
