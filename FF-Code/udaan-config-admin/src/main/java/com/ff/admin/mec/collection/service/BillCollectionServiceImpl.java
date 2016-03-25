package com.ff.admin.mec.collection.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.mec.collection.converter.CollectionConverter;
import com.ff.admin.mec.collection.dao.BillCollectionDAO;
import com.ff.admin.mec.collection.dao.CollectionCommonDAO;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.admin.mec.common.service.MECCommonService;
import com.ff.business.CustomerTO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.mec.collection.CollectionDtlsDO;
import com.ff.mec.collection.BillCollectionTO;
import com.ff.to.billing.BillTO;
import com.ff.universe.business.service.BusinessCommonService;

public class BillCollectionServiceImpl implements BillCollectionService {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BillCollectionServiceImpl.class);

	/** The businessCommonService. */
	private BusinessCommonService businessCommonService;

	/** The collectionCommonDAO. */
	private CollectionCommonDAO collectionCommonDAO;

	/** The billCollectionDAO. */
	private BillCollectionDAO billCollectionDAO;

	/** The mecCommonService. */
	private MECCommonService mecCommonService;

	/**
	 * @param mecCommonService
	 */
	public void setMecCommonService(MECCommonService mecCommonService) {
		this.mecCommonService = mecCommonService;
	}

	/**
	 * @param businessCommonService
	 *            the businessCommonService to set
	 */
	public void setBusinessCommonService(
			BusinessCommonService businessCommonService) {
		this.businessCommonService = businessCommonService;
	}

	/**
	 * @param collectionCommonDAO
	 *            the collectionCommonDAO to set
	 */
	public void setCollectionCommonDAO(CollectionCommonDAO collectionCommonDAO) {
		this.collectionCommonDAO = collectionCommonDAO;
	}

	/**
	 * @param billCollectionDAO
	 *            the billCollectionDAO to set
	 */
	public void setBillCollectionDAO(BillCollectionDAO billCollectionDAO) {
		this.billCollectionDAO = billCollectionDAO;
	}

	@Override
	public List<CustomerTO> getAllCustomers() throws CGBusinessException,
			CGSystemException {
		List<CustomerTO> customerList = businessCommonService.getAllCustomers();
		return customerList;
	}

	@Override
	public BillCollectionTO saveOrUpdateBillCollection(
			BillCollectionTO billCollectionTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BillCollectionServiceImpl :: saveOrUpdateBillCollection() :: Start --------> ::::::");
		boolean isBillSaved = Boolean.FALSE;
		if (!StringUtil.isNull(billCollectionTO)) {
			CollectionDO collectionDO = CollectionConverter
					.billCollectionDomainConverter(billCollectionTO);
			if (!StringUtil.isNull(collectionDO)) {
				isBillSaved = collectionCommonDAO
						.saveOrUpdateCollection(collectionDO);
				if (!isBillSaved) {
					throw new CGBusinessException(
							AdminErrorConstants.DETAILS_NOT_SAVED);
				}
			} else {
				throw new CGBusinessException(
						AdminErrorConstants.DETAILS_NOT_SAVED);
			}
		}
		LOGGER.trace("BillCollectionServiceImpl :: saveOrUpdateBillCollection() :: END --------> ::::::");
		return billCollectionTO;
	}

	@Override
	public BillCollectionTO searchBillCollectionDtls(String transactionNo,
			String collectionType) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BillCollectionServiceImpl :: searchBillCollectionDtls() :: START --------> ::::::");
		CollectionDO collectionDO = null;
		BillCollectionTO billCollectionTO = null;
		collectionDO = billCollectionDAO.searchBillCollectionDtls(
				transactionNo, collectionType);
		if (!StringUtil.isNull(collectionDO)) {
			billCollectionTO = CollectionConverter
					.billCollectionDOtoTOConverter(collectionDO);
		} else {
			throw new CGBusinessException(
					MECCommonConstants.NO_TX_NO_FOUND_FOR_BRANCH);
		}
		LOGGER.trace("BillCollectionServiceImpl :: searchBillCollectionDtls() :: END --------> ::::::");
		return billCollectionTO;
	}

	@Override
	public BillCollectionTO validateCollection(BillCollectionTO billCollectionTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BillCollectionServiceImpl :: validateCollection() :: START --------> ::::::");
		String isSaved = CommonConstants.NO;
		boolean isBillSaved = Boolean.FALSE;
		if (!StringUtil.isNull(billCollectionTO)) {
			CollectionDO collectionDO = CollectionConverter
					.billCollectionDomainConverter(billCollectionTO);
			if (!StringUtil.isNull(collectionDO)) {
				isBillSaved = collectionCommonDAO
						.saveOrUpdateCollection(collectionDO);
				if (isBillSaved) {
					if (!StringUtil.isEmptyInteger(billCollectionTO
							.getPrevTxnCollectionID())) {
						isBillSaved = collectionCommonDAO
								.updateCollectionStatus(
										MECCommonConstants.VALIDATED_STATUS,
										billCollectionTO
												.getPrevTxnCollectionID());
					}
				}
			}
			if (isBillSaved) {
				isSaved = CommonConstants.SUCCESS;
			} else {
				throw new CGBusinessException(
						AdminErrorConstants.DETAILS_NOT_SAVED);
			}
		}
		billCollectionTO.setIsSaved(isSaved);
		LOGGER.trace("BillCollectionServiceImpl :: validateCollection() :: END --------> ::::::");
		return billCollectionTO;
	}

	@Override
	public BillCollectionTO searchCollectionDtlsByTxnNo(String transactionNo)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BillCollectionServiceImpl :: searchCollectionDtlsByTxnNo() :: START --------> ::::::");
		CollectionDO collectionDO = null;
		BillCollectionTO billCollectionTO = null;
		collectionDO = collectionCommonDAO
				.searchCollectionDtlsByTxnNo(transactionNo);
		if (!StringUtil.isNull(collectionDO)) {
			billCollectionTO = CollectionConverter
					.billCollectionDOtoTOConverter(collectionDO);
		} else {
			throw new CGBusinessException(AdminErrorConstants.DETAILS_NOT_EXIST);
		}
		LOGGER.trace("BillCollectionServiceImpl :: searchCollectionDtlsByTxnNo() :: END --------> ::::::");
		return billCollectionTO;
	}

	@Override
	public List<CustomerTO> getCustomersByPickupDeliveryLocation(
			Integer officeId) throws CGSystemException, CGBusinessException {
		LOGGER.trace("BillCollectionServiceImpl :: searchCollectionDtlsByTxnNo() :: END --------> ::::::");
		/*
		 * To get customer whose pickup or delivery location is payment or
		 * bill-payment
		 */
		List<CustomerTO> customerTOs = mecCommonService
				.getCustomersByPickupDeliveryLocation(officeId);
		// To get customer created by SAP for bill collection
		List<CustomerTO> SAPCustomerTOs = mecCommonService
				.getCustomersForBillCollection(officeId);
		if (!CGCollectionUtils.isEmpty(SAPCustomerTOs)) {
			if (CGCollectionUtils.isEmpty(customerTOs)) {
				customerTOs = SAPCustomerTOs;
			} else {
				customerTOs.addAll(SAPCustomerTOs);
			}
		}
		if (StringUtil.isEmptyColletion(customerTOs)) {
			throw new CGBusinessException(
					AdminErrorConstants.MEC_CUSTOMER_NOT_EXIST);
		}
		LOGGER.trace("BillCollectionServiceImpl :: searchCollectionDtlsByTxnNo() :: END --------> ::::::");
		return customerTOs;
	}

	@Override
	public List<BillTO> getCollectionDetailsByBillNumber(List<BillTO> billTOs)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("BillCollectionServiceImpl :: getCollectionDetailsByBillNumber() :: END --------> ::::::");
		List<BillTO> bilList = new ArrayList<BillTO>();
		List<String> billNos = new ArrayList<String>(billTOs.size());
		for (BillTO billTO : billTOs) {
			billNos.add(billTO.getInvoiceNumber());
		}

		// To search bill no. to validate partial bill collection
		List<CollectionDtlsDO> collDtlsDOs = billCollectionDAO
				.getCollectionDetailsByBillNumber(billNos);

		List<CollectionDtlsDO> collectionDtlsDOs = new ArrayList<CollectionDtlsDO>();
		// To calculating total bill amount for different txn.
		for (CollectionDtlsDO collDtlsDO1 : collDtlsDOs) {
			boolean flag = false;
			double totalBillAmt = 0.0;

			/**
			 * NOTE: Earlier we were using received amount for calculation.
			 * Later it was change to total bill amount by Himal
			 */

			// If already calculate total bill amount then skip same bill No.
			for (CollectionDtlsDO collDtlDO : collectionDtlsDOs) {
				if (collDtlsDO1.getBillNo().equalsIgnoreCase(
						collDtlDO.getBillNo())) {
					flag = true;
					break;
				}
			}
			if (flag)
				continue;
			for (CollectionDtlsDO collDtlsDO2 : collDtlsDOs) {
				if (collDtlsDO1.getBillNo().equalsIgnoreCase(
						collDtlsDO2.getBillNo())) {
					totalBillAmt += collDtlsDO2.getTotalBillAmount();
				}
			}
			collDtlsDO1.setTotalBillAmount(totalBillAmt);
			collectionDtlsDOs.add(collDtlsDO1);
		}

		if (!CGCollectionUtils.isEmpty(collectionDtlsDOs)) {
			DecimalFormat df = new DecimalFormat("#.##");
			for (BillTO billTO : billTOs) {
				boolean flag = true;
				for (CollectionDtlsDO collectionDtlsDO : collectionDtlsDOs) {
					if (collectionDtlsDO.getBillNo().equalsIgnoreCase(
							billTO.getInvoiceNumber())) {
						if (collectionDtlsDO.getTotalBillAmount().doubleValue() == Double
								.valueOf(
										df.format(billTO
												.getGrandTotalRoundedOff()))
								.doubleValue()) {
							flag = false;
							break;
						} else {
							double remainingAmt = billTO
									.getGrandTotalRoundedOff()
									- collectionDtlsDO.getTotalBillAmount();
							if (!StringUtil.isEmptyDouble(remainingAmt)) {
								billTO.setGrandTotalRoundedOff(remainingAmt);
							}
							bilList.add(billTO);
							flag = false;
							break;
						}
					}
				}
				if (flag) {
					bilList.add(billTO);
				}
			}
		} else {
			bilList = billTOs;
		}
		LOGGER.trace("BillCollectionServiceImpl :: getCollectionDetailsByBillNumber() :: END --------> ::::::");
		return bilList;
	}

}
