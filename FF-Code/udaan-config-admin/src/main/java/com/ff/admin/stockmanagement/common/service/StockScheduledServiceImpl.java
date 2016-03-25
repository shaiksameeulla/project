/**
 * 
 */
package com.ff.admin.stockmanagement.common.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.admin.stockmanagement.autorequisition.constants.AutoRequisitionConstants;
import com.ff.admin.stockmanagement.common.dao.StockScheduledDAO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.mec.collection.CollectionDtlsDO;
import com.ff.domain.serviceOffering.PaymentModeDO;
import com.ff.domain.stockmanagement.operations.issue.StockIssuePaymentDetailsDO;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;
import com.ff.universe.stockmanagement.service.StockUniversalService;
import com.ff.universe.stockmanagement.util.StockUtility;

/**
 * @author mohammes
 *
 */
public class StockScheduledServiceImpl implements StockScheduledService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StockScheduledServiceImpl.class);
	
	private StockScheduledDAO stockScheduledDAO;
	private StockUniversalService stockUniversalService;
	
	/**
	 * @return the stockUniversalService
	 */
	public StockUniversalService getStockUniversalService() {
		return stockUniversalService;
	}

	/**
	 * @param stockUniversalService the stockUniversalService to set
	 */
	public void setStockUniversalService(StockUniversalService stockUniversalService) {
		this.stockUniversalService = stockUniversalService;
	}

	/**
	 * @return the stockScheduledDAO
	 */
	public StockScheduledDAO getStockScheduledDAO() {
		return stockScheduledDAO;
	}

	/**
	 * @param stockScheduledDAO the stockScheduledDAO to set
	 */
	public void setStockScheduledDAO(StockScheduledDAO stockScheduledDAO) {
		this.stockScheduledDAO = stockScheduledDAO;
	}

	@Override
	public List<StockIssuePaymentDetailsDO> getStockPaymentDetails()
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("StockScheduledServiceImpl ::getStockPaymentDetails ::START ");
		List<StockIssuePaymentDetailsDO> paymntDtlsListDO=stockScheduledDAO.getStockPaymentDetails();
		LOGGER.debug("StockScheduledServiceImpl ::getStockPaymentDetails ::END ");
		return paymntDtlsListDO;
	}
	
	
	/**
	 * @param paymentTypeMap
	 * @param paymntDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@Override
	public void createExpenseFromStockPayment(
			Map<String, Integer> paymentTypeMap,
			StockIssuePaymentDetailsDO paymntDO) throws CGBusinessException,
			CGSystemException {
		CollectionDO collectionDO =prepareExpenseDO(paymntDO,paymentTypeMap);
		prepareCollectionChildDtls(paymntDO, collectionDO);
		paymntDO.setIsProcessed(AutoRequisitionConstants.STOCK_INTEGRATED_WITH_STOCK_YES);
		int updatedCount= stockScheduledDAO.updateStockPaymentDetails(paymntDO);
		if(updatedCount>0){
			stockScheduledDAO.saveCollectionDetails(collectionDO);
		}else{
			LOGGER.error("StockScheduledServiceImpl ::createExpenseFromStockPayment :: collection data is not inserted,Since record not updated into stock Payment table ");
		}
	}

	/**
	 * @param paymntDO
	 * @param collectionDO
	 */
	private void prepareCollectionChildDtls(
			StockIssuePaymentDetailsDO paymntDO, CollectionDO collectionDO) {
		Set<CollectionDtlsDO> collectionDOSet= new HashSet<>(1);
		CollectionDtlsDO collectionDtls= new CollectionDtlsDO();
		collectionDtls.setCollectionDO(collectionDO);
		collectionDtls.setBillAmount(paymntDO.getAmountReceived());
		collectionDtls.setCollectionFor(MECCommonConstants.COLLECTION_FOR_FFCL);
		collectionDtls.setCollectionAgainst(MECCommonConstants.COLL_AGAINST_ON_ACCOUNT);
		collectionDtls.setRecvAmount(paymntDO.getAmountReceived());
		collectionDtls.setBillAmount(paymntDO.getTotalToPayAmount());//FIXME
		collectionDtls.setBillNo(paymntDO.getStockIssueDO().getStockIssueNumber());
		collectionDtls.setTotalBillAmount(paymntDO.getTotalToPayAmount());
		collectionDOSet.add(collectionDtls);
		collectionDO.setCollectionDtls(collectionDOSet);
	}

	/**
	 * @param paymntDtlsListDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@Override
	public  Map<String,Integer> getPaymentModeTypeForCollection()
			throws CGBusinessException, CGSystemException {
		Map<String,Integer> paymentTypeMap=null;
			List<PaymentModeTO> paymentTypeList=stockUniversalService.getPaymentModeDtls(CommonConstants.PROCESS_MEC);
			if(!CGCollectionUtils.isEmpty(paymentTypeList)){
				paymentTypeMap = new HashMap<>(paymentTypeList.size());
				for(PaymentModeTO paymentTypeTO:paymentTypeList){
					paymentTypeMap.put(paymentTypeTO.getPaymentCode(),paymentTypeTO.getPaymentId());
				}
		}
		return paymentTypeMap;
	}
	private CollectionDO prepareExpenseDO(StockIssuePaymentDetailsDO paymntDO,Map<String,Integer> paymentTypeMap)throws CGBusinessException, CGSystemException{
		CollectionDO collectionDO=null;
		collectionDO=  new CollectionDO();
		collectionDO.setCollectionOfficeDO(paymntDO.getStockIssueDO().getIssueOfficeDO());
		collectionDO.setCustomerDO(paymntDO.getStockIssueDO().getIssuedToBA());
		collectionDO.setCollectionCategory(MECCommonConstants.COLL_AGAINST_BILL);
		
		if(!StringUtil.isStringEmpty(paymntDO.getPaymentMode()) && paymntDO.getPaymentMode().equalsIgnoreCase(StockUniveralConstants.STOCK_CASH_PAYMENT_TYPE)){
			collectionDO.setStatus(MECCommonConstants.VALIDATED_STATUS);
		}else{
			collectionDO.setStatus(MECCommonConstants.SUBMITTED_STATUS);
		}
		
		collectionDO.setChqDate(paymntDO.getPaymentDate());
		collectionDO.setChqNo(paymntDO.getChequeNumber());
		collectionDO.setCollectionDate(paymntDO.getStockIssueDO().getStockIssueDate());
		collectionDO.setTotalAmount(paymntDO.getAmountReceived());
		collectionDO.setTxnNo(paymntDO.getStockIssueDO().getStockIssueNumber());//FIXME need check with BA
		collectionDO.setBankName(paymntDO.getBankName());
		PaymentModeDO paymentDO=StockUtility.preparePaymentModeForCollection(paymentTypeMap,paymntDO.getPaymentMode());
		collectionDO.setPaymentModeDO(paymentDO);
		return collectionDO;
	}

	
}
