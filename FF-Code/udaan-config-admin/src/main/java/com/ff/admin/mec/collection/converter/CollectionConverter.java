/**
 * 
 */
package com.ff.admin.mec.collection.converter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.admin.mec.common.service.MECCommonService;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.mec.GLMasterDO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.mec.collection.CollectionDtlsDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.PaymentModeDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.mec.collection.BillCollectionDetailTO;
import com.ff.mec.collection.BillCollectionTO;
import com.ff.mec.collection.CNCollectionDtlsTO;
import com.ff.mec.collection.CNCollectionTO;

/**
 * @author prmeher
 * 
 */
public class CollectionConverter {

	/** The Logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CollectionConverter.class);

	/**
	 * @param billCollectionTO
	 * @return
	 */
	public static BillCollectionTO setUpBillCollectionDetails(
			BillCollectionTO billCollectionTO) {
		List<BillCollectionDetailTO> billCollectiondtlsTOs = new ArrayList<BillCollectionDetailTO>();
		int listLength = billCollectionTO.getTotals().length;
		if (billCollectionTO.getTotals() != null && listLength > 0) {
			for (int rowCount = 0; rowCount < listLength; rowCount++) {
				if (!StringUtil.isEmpty(billCollectionTO.getTotals())
						&& !StringUtil.isEmptyDouble(billCollectionTO
								.getTotals()[rowCount])) {
					BillCollectionDetailTO billCollectionDetailTO = new BillCollectionDetailTO();

					// COLLECTION AGAINST
					if (!StringUtil.isEmpty(billCollectionTO
							.getCollectionAgainsts())
							&& !StringUtil.isStringEmpty(billCollectionTO
									.getCollectionAgainsts()[rowCount]))
						billCollectionDetailTO
								.setCollectionAgainst(billCollectionTO
										.getCollectionAgainsts()[rowCount]);

					// BILL NO
					if (!StringUtil.isEmpty(billCollectionTO.getBillNos())
							&& !StringUtil.isStringEmpty(billCollectionTO
									.getBillNos()[rowCount]))
						billCollectionDetailTO.setBillNo(billCollectionTO
								.getBillNos()[rowCount]);

					// BILL AMOUNT
					if (!StringUtil.isEmpty(billCollectionTO.getBillAmounts())
							&& !StringUtil.isEmptyDouble(billCollectionTO
									.getBillAmounts()[rowCount]))
						billCollectionDetailTO.setBillAmount(billCollectionTO
								.getBillAmounts()[rowCount]);

					// RECEIVED AMOUNT
					if (!StringUtil.isEmpty(billCollectionTO
							.getReceivedAmounts())
							&& !StringUtil.isEmptyDouble(billCollectionTO
									.getReceivedAmounts()[rowCount]))
						billCollectionDetailTO.setRecvdAmt(billCollectionTO
								.getReceivedAmounts()[rowCount]);

					// TDS AMOUNT
					if (!StringUtil.isEmpty(billCollectionTO.getTdsAmounts())
							&& !StringUtil.isEmptyDouble(billCollectionTO
									.getTdsAmounts()[rowCount]))
						billCollectionDetailTO.setTdsAmt(billCollectionTO
								.getTdsAmounts()[rowCount]);

					// DEDUCTION
					if (!StringUtil.isEmpty(billCollectionTO.getDeductions())
							&& !StringUtil.isEmptyDouble(billCollectionTO
									.getDeductions()[rowCount])) {
						billCollectionDetailTO.setDeduction(billCollectionTO
								.getDeductions()[rowCount]);
					}

					// TOTAL BILL AMOUNT
					if (!StringUtil.isEmpty(billCollectionTO.getTotals())
							&& !StringUtil.isEmptyDouble(billCollectionTO
									.getTotals()[rowCount]))
						billCollectionDetailTO.setTotal(billCollectionTO
								.getTotals()[rowCount]);

					// CORRECTED RECEIVED AMOUNT
					if (!StringUtil.isEmpty(billCollectionTO
							.getCorrectedRecvAmount())
							&& !StringUtil.isEmptyDouble(billCollectionTO
									.getCorrectedRecvAmount()[rowCount]))
						billCollectionDetailTO
								.setCorrectedAmount(billCollectionTO
										.getCorrectedRecvAmount()[rowCount]);

					// CORRECTED TDS
					if (!StringUtil.isEmpty(billCollectionTO.getCorrectedTDS())
							&& !StringUtil.isEmptyDouble(billCollectionTO
									.getCorrectedTDS()[rowCount]))
						billCollectionDetailTO.setCorrectedTDS(billCollectionTO
								.getCorrectedTDS()[rowCount]);

					// COLLECTION ENTRY IDs
					if (!StringUtil.isEmpty(billCollectionTO
							.getCollectionEntryIds())
							&& !StringUtil.isEmptyInteger(billCollectionTO
									.getCollectionEntryIds()[rowCount])) {
						billCollectionDetailTO
								.setCollectionEntryId(billCollectionTO
										.getCollectionEntryIds()[rowCount]);
					}

					// REMARKS
					if (!StringUtil.isEmpty(billCollectionTO.getRemarks())
							&& !StringUtil.isStringEmpty(billCollectionTO
									.getRemarks()[rowCount]))
						billCollectionDetailTO.setRemarks(billCollectionTO
								.getRemarks()[rowCount]);

					// CREATED BY
					if (!StringUtil.isEmpty(billCollectionTO.getCreatedBys())
							&& !StringUtil.isEmptyInteger(billCollectionTO
									.getCreatedBys()[rowCount])) {
						billCollectionDetailTO.setCreatedBy(billCollectionTO
								.getCreatedBys()[rowCount]);
					}

					// UPDATED BY
					if (!StringUtil.isEmptyInteger(billCollectionTO
							.getUpdatedBy())) {
						billCollectionDetailTO.setUpdatedBy(billCollectionTO
								.getUpdatedBy());
					}

					// REASON
					if (!StringUtil.isEmpty(billCollectionTO.getReasonIds())
							&& !StringUtil.isEmptyInteger(billCollectionTO
									.getReasonIds()[rowCount]))
						billCollectionDetailTO.setReasonId(billCollectionTO
								.getReasonIds()[rowCount]);

					// RECEIPT NO
					if (!StringUtil.isEmpty(billCollectionTO.getReceiptNo())
							&& !StringUtil.isStringEmpty(billCollectionTO
									.getReceiptNo()[rowCount]))
						billCollectionDetailTO.setReceiptNo(billCollectionTO
								.getReceiptNo()[rowCount]);

					// CONSIGNMENT ID
					if (!StringUtil.isEmpty(billCollectionTO.getConsgIds())
							&& !StringUtil.isEmptyInteger(billCollectionTO
									.getConsgIds()[rowCount]))
						billCollectionDetailTO.setConsgId(billCollectionTO
								.getConsgIds()[rowCount]);

					// COLLECTION TYPE
					if (!StringUtil.isEmpty(billCollectionTO
							.getCollectionTypes())
							&& !StringUtil.isStringEmpty(billCollectionTO
									.getCollectionTypes()[rowCount]))
						billCollectionDetailTO
								.setCollectionType(billCollectionTO
										.getCollectionTypes()[rowCount]);

					// COLLECTION FOR
					if (!StringUtil.isEmpty(billCollectionTO.getCnfor())
							&& !StringUtil.isStringEmpty(billCollectionTO
									.getCnfor()[rowCount]))
						billCollectionDetailTO.setCnfor(billCollectionTO
								.getCnfor()[rowCount]);

					// CONSIG. DELIVERY DATE
					if (!StringUtil.isEmpty(billCollectionTO.getCnDeliveryDt())
							&& !StringUtil.isStringEmpty(billCollectionTO
									.getCnDeliveryDt()[rowCount]))
						billCollectionDetailTO.setCnDeliveryDt(billCollectionTO
								.getCnDeliveryDt()[rowCount]);

					billCollectiondtlsTOs.add(billCollectionDetailTO);
				}
			}
			billCollectionTO.setBillCollectionDetailTO(billCollectiondtlsTOs);
		}
		return billCollectionTO;
	}

	/**
	 * @param billCollectionTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public static CollectionDO billCollectionDomainConverter(
			BillCollectionTO billCollectionTO) throws CGSystemException,
			CGBusinessException {
		return setUpBillCollectionHeaderValues(billCollectionTO);
	}

	/**
	 * @param billCollectionTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	private static CollectionDO setUpBillCollectionHeaderValues(
			BillCollectionTO billCollectionTO) throws CGSystemException,
			CGBusinessException {
		CollectionDO billCollection = new CollectionDO();
		if (!StringUtil.isStringEmpty(billCollectionTO.getTxnNo()))
			billCollection.setTxnNo(billCollectionTO.getTxnNo());
		if (!StringUtil.isEmptyInteger(billCollectionTO.getCollectionID())) {
			billCollection.setCollectionId(billCollectionTO.getCollectionID());
		}
		OfficeDO collectionOffice = null;
		if (!StringUtil.isEmptyInteger(billCollectionTO.getOriginOfficeId())) {
			collectionOffice = new OfficeDO();
			collectionOffice.setOfficeId(billCollectionTO.getOriginOfficeId());
			billCollection.setCollectionOfficeDO(collectionOffice);
		}
		// Bank GL
		if (!StringUtil.isEmptyInteger(billCollectionTO.getBankGL())) {
			GLMasterDO glMasterDO = new GLMasterDO();
			glMasterDO.setGlId(billCollectionTO.getBankGL());
			billCollection.setBankGLDO(glMasterDO);
		}
		if (!StringUtil.isStringEmpty(billCollectionTO.getBankName())) {
			billCollection.setBankName(billCollectionTO.getBankName());
		}
		if (!StringUtil.isStringEmpty(billCollectionTO.getChqDate())) {
			billCollection.setChqDate(DateUtil
					.slashDelimitedstringToDDMMYYYYFormat(billCollectionTO
							.getChqDate()));
		}
		if (!StringUtil.isStringEmpty(billCollectionTO.getChqNo())) {
			billCollection.setChqNo(billCollectionTO.getChqNo());
		}
		if (!StringUtil.isStringEmpty(billCollectionTO.getCollectionDate())) {
			billCollection.setCollectionDate(DateUtil
					.slashDelimitedstringToDDMMYYYYFormat(billCollectionTO
							.getCollectionDate()));
		}
		if (!StringUtil.isEmptyInteger(billCollectionTO.getCollectionModeId())) {
			PaymentModeDO paymentModeDO = new PaymentModeDO();
			paymentModeDO.setPaymentId(billCollectionTO.getCollectionModeId());
			billCollection.setPaymentModeDO(paymentModeDO);
		}
		if (!StringUtil.isEmptyInteger(billCollectionTO.getCustId())) {
			CustomerDO customerDO = new CustomerDO();
			customerDO.setCustomerId(billCollectionTO.getCustId());
			billCollection.setCustomerDO(customerDO);
		}

		// Total Amount
		if (!StringUtil.isStringEmpty(billCollectionTO.getAmount())) {
			billCollection.setTotalAmount(Double.parseDouble(billCollectionTO
					.getAmount()));
		} else {
			billCollection.setTotalAmount(0.0);
		}

		billCollection.setStatus(billCollectionTO.getStatus());
		billCollection.setCollectionCategory(billCollectionTO
				.getCollectionType());
		// Created By
		if (!StringUtil.isEmptyInteger(billCollectionTO.getCreatedBy())) {
			billCollection.setCreatedBy(billCollectionTO.getCreatedBy());
		}
		// Updated By
		if (!StringUtil.isEmptyInteger(billCollectionTO.getUpdatedBy())) {
			billCollection.setUpdatedBy(billCollectionTO.getUpdatedBy());
		}

		// Setting Created Date & Updated Date
		billCollection.setCreatedDate(Calendar.getInstance().getTime());
		billCollection.setUpdatedDate(Calendar.getInstance().getTime());

		// Setting SAP Status = I, if transaction no. -1
		int txnLength = billCollection.getTxnNo().length();
		if (billCollection.getTxnNo().contains(CommonConstants.HYPHEN)
				&& txnLength == 14) {
			billCollection.setSapStatus(CommonConstants.STATUS_INACTIVE);
		}

		// Setting Bill Collection details
		Set<CollectionDtlsDO> billDtls = billCollectionDtlsDomainConverter(
				billCollectionTO, billCollection);
		billCollection.setCollectionDtls(billDtls);
		return billCollection;
	}

	/**
	 * @param billCollectionTO
	 * @param billCollectionDO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public static Set<CollectionDtlsDO> billCollectionDtlsDomainConverter(
			BillCollectionTO billCollectionTO, CollectionDO billCollectionDO)
			throws CGSystemException, CGBusinessException {
		Set<CollectionDtlsDO> billDtls = null;
		if (!CGCollectionUtils.isEmpty(billCollectionTO
				.getBillCollectionDetailTO())) {
			billDtls = new HashSet<CollectionDtlsDO>();
			for (BillCollectionDetailTO billCollectionDtls : billCollectionTO
					.getBillCollectionDetailTO()) {
				CollectionDtlsDO collectionDtls = new CollectionDtlsDO();
				if (!StringUtil.isEmptyInteger(billCollectionDtls
						.getCollectionEntryId())) {
					collectionDtls.setEntryId(billCollectionDtls
							.getCollectionEntryId());
				}

				collectionDtls.setBillNo(billCollectionDtls.getBillNo());
				// collectionDtls.setPosition(billCollectionDtls.getSrNo());
				collectionDtls.setCollectionAgainst(billCollectionDtls
						.getCollectionAgainst());
				collectionDtls
						.setBillAmount(billCollectionDtls.getBillAmount());

				// RECEIVED AMOUNT
				if (!StringUtil.isEmptyDouble(billCollectionDtls.getRecvdAmt())) {
					collectionDtls.setRecvAmount(billCollectionDtls
							.getRecvdAmt());
				} else {
					collectionDtls.setRecvAmount(0.0);
				}

				// TDS
				if (!StringUtil.isEmptyDouble(billCollectionDtls.getTdsAmt())) {
					collectionDtls.setTdsAmount(billCollectionDtls.getTdsAmt());
				} else {
					collectionDtls.setTdsAmount(0.0);
				}

				if (!StringUtil.isEmptyDouble(billCollectionDtls.getTotal())) {
					collectionDtls.setTotalBillAmount(billCollectionDtls
							.getTotal());
				} else {
					collectionDtls.setTotalBillAmount(0.0);
				}
				collectionDtls.setRemarks(billCollectionDtls.getRemarks());
				// Created By
				if (!StringUtil.isEmptyInteger(billCollectionTO.getCreatedBy())) {
					collectionDtls
							.setCreatedBy(billCollectionTO.getCreatedBy());
				}
				// Updated By
				if (!StringUtil.isEmptyInteger(billCollectionTO.getUpdatedBy())) {
					collectionDtls
							.setUpdatedBy(billCollectionTO.getUpdatedBy());
				}
				// Reason
				if (!StringUtil
						.isEmptyInteger(billCollectionDtls.getReasonId())) {
					ReasonDO reasonDO = new ReasonDO();
					reasonDO.setReasonId(billCollectionDtls.getReasonId());
					collectionDtls.setReasonDO(reasonDO);
				}

				// DEDUCTION
				if (!StringUtil
						.isEmptyDouble(billCollectionDtls.getDeduction())) {
					collectionDtls.setDeduction(billCollectionDtls
							.getDeduction());
				}

				// RECEIPT NO
				if (!StringUtil
						.isStringEmpty(billCollectionDtls.getReceiptNo())) {
					collectionDtls.setReceiptNo(billCollectionDtls
							.getReceiptNo());
				}

				// CONSIGNMENT ID
				if (!StringUtil.isEmptyInteger(billCollectionDtls.getConsgId())) {
					ConsignmentDO consgDO = new ConsignmentDO();
					consgDO.setConsgId(billCollectionDtls.getConsgId());
					collectionDtls.setConsgDO(consgDO);
				}

				// COLLECTION TYPE
				if (!StringUtil.isStringEmpty(billCollectionDtls
						.getCollectionType())) {
					collectionDtls.setCollectionType(billCollectionDtls
							.getCollectionType());
				}

				// COLLECTION FOR
				if (!StringUtil.isStringEmpty(billCollectionDtls.getCnfor())) {
					collectionDtls.setCollectionFor(billCollectionDtls
							.getCnfor());
					if (collectionDtls.getCollectionFor().equalsIgnoreCase(
							MECCommonConstants.COLLECTION_FOR_CUSTOMER)) {
						billCollectionDO.setSapStatus(CommonConstants.YES);
					}
				}

				// CONSG. DELIVERY DATE
				if (!StringUtil.isStringEmpty(billCollectionDtls
						.getCnDeliveryDt())) {
					collectionDtls
							.setConsgDeliveryDate(DateUtil
									.parseStringDateToDDMMYYYYHHMMFormat(billCollectionDtls
											.getCnDeliveryDt()));
				}

				// Setting Created Date & Updated Date
				collectionDtls.setCreatedDate(Calendar.getInstance().getTime());
				collectionDtls.setUpdatedDate(Calendar.getInstance().getTime());

				collectionDtls.setCollectionDO(billCollectionDO);
				billDtls.add(collectionDtls);
			}
		}
		return billDtls;
	}

	public static BillCollectionTO billCollectionDOtoTOConverter(
			CollectionDO collectionDO) {
		BillCollectionTO billCollectionTO = new BillCollectionTO();
		billCollectionTO.setTxnNo(collectionDO.getTxnNo());
		billCollectionTO.setCollectionID(collectionDO.getCollectionId());
		billCollectionTO.setCollectionDate(DateUtil
				.getDDMMYYYYDateToString(collectionDO.getCollectionDate()));
		if (!StringUtil.isNull(collectionDO.getCustomerDO())
				&& (!StringUtil.isEmptyInteger(collectionDO.getCustomerDO()
						.getCustomerId()))) {
			billCollectionTO.setCustId(collectionDO.getCustomerDO()
					.getCustomerId());
			billCollectionTO.setCustCode(collectionDO.getCustomerDO()
					.getCustomerCode());
			billCollectionTO.setCustName(collectionDO.getCustomerDO()
					.getBusinessName());
		}
		if (!StringUtil.isNull(collectionDO.getPaymentModeDO())
				&& (!StringUtil.isEmptyInteger(collectionDO.getPaymentModeDO()
						.getPaymentId()))) {
			billCollectionTO.setCollectionModeId(collectionDO
					.getPaymentModeDO().getPaymentId());
		}
		if (!StringUtil.isNull(collectionDO.getChqNo())) {
			billCollectionTO.setChqNo(collectionDO.getChqNo());
			billCollectionTO.setChqDate(DateUtil
					.getDDMMYYYYDateToString(collectionDO.getChqDate()));
		}

		if (!StringUtil.isStringEmpty(collectionDO.getBankName())) {
			billCollectionTO.setBankName(collectionDO.getBankName());
		}

		if (!StringUtil.isNull(collectionDO.getBankGLDO())
				&& !StringUtil.isEmptyInteger(collectionDO.getBankGLDO()
						.getGlId())) {
			billCollectionTO.setBankGL(collectionDO.getBankGLDO().getGlId());
		}

		billCollectionTO
				.setCollectionType(collectionDO.getCollectionCategory());

		if (!StringUtil.isEmptyDouble(collectionDO.getTotalAmount())) {
			// BigDecimal bd = new BigDecimal(collectionDO.getTotalAmount(),
			//		MathContext.DECIMAL64);
			DecimalFormat df = new DecimalFormat("#.00");
			billCollectionTO.setAmount(df.format(collectionDO.getTotalAmount()));
		} else {
			billCollectionTO.setAmount("0");
		}
		billCollectionTO.setStatus(collectionDO.getStatus());

		// Created By
		if (!StringUtil.isEmptyInteger(collectionDO.getCreatedBy())) {
			billCollectionTO.setCreatedBy(collectionDO.getCreatedBy());
		}
		// Updated By
		if (!StringUtil.isEmptyInteger(collectionDO.getUpdatedBy())) {
			billCollectionTO.setUpdatedBy(collectionDO.getUpdatedBy());
		}
		Set<CollectionDtlsDO> collectionDtls = collectionDO.getCollectionDtls();

		List<BillCollectionDetailTO> billCollectionDtls = new ArrayList<BillCollectionDetailTO>();
		for (CollectionDtlsDO billDtls : collectionDtls) {
			BillCollectionDetailTO billCollectionDetailTO = new BillCollectionDetailTO();
			// billCollectionDetailTO.setSrNo(billDtls.getEntryId());
			billCollectionDetailTO.setCollectionEntryId(billDtls.getEntryId());
			billCollectionDetailTO.setCollectionAgainst(billDtls
					.getCollectionAgainst());
			billCollectionDetailTO.setBillNo(billDtls.getBillNo());
			billCollectionDetailTO.setBillAmount(billDtls.getBillAmount());
			billCollectionDetailTO.setRecvdAmt(billDtls.getRecvAmount());
			billCollectionDetailTO.setDeduction(billDtls.getDeduction());
			billCollectionDetailTO.setTdsAmt(billDtls.getTdsAmount());
			billCollectionDetailTO.setTotal(billDtls.getTotalBillAmount());
			billCollectionDetailTO.setRemarks(billDtls.getRemarks());

			if (!StringUtil.isNull(billDtls.getConsgDO())) {
				billCollectionDetailTO.setCnNo(billDtls.getConsgDO()
						.getConsgNo());
				billCollectionDetailTO.setConsgId(billDtls.getConsgDO()
						.getConsgId());
			}

			billCollectionDetailTO.setCollectionType(billDtls
					.getCollectionType());
			billCollectionDetailTO.setCnfor(billDtls.getCollectionFor());

			billCollectionDetailTO.setReceiptNo(billDtls.getReceiptNo());
			billCollectionDetailTO.setCollectionType(billDtls
					.getCollectionType());
			if (!StringUtil.isNull(billDtls.getReasonDO()))
				billCollectionDetailTO.setReasonId(billDtls.getReasonDO()
						.getReasonId());

			billCollectionDetailTO.setCnDeliveryDt(DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat(billDtls
							.getConsgDeliveryDate()));

			billCollectionDetailTO.setCreatedBy(billDtls.getCreatedBy());
			billCollectionDetailTO.setUpdatedBy(billDtls.getUpdatedBy());

			billCollectionDtls.add(billCollectionDetailTO);
		}
		billCollectionTO.setBillCollectionDetailTO(billCollectionDtls);

		return billCollectionTO;

	}

	public static CollectionDO cnCollectionDomainConverter(
			CNCollectionTO cnCollectionTO, CNCollectionDtlsTO cnCollectionDtls)
			throws CGSystemException, CGBusinessException {
		CollectionDO collectionDO = null;
		collectionDO = setUpCNCollectionValues(cnCollectionTO, cnCollectionDtls);
		return collectionDO;
	}

	private static CollectionDO setUpCNCollectionValues(
			CNCollectionTO cnCollectionTO, CNCollectionDtlsTO cnCollectionDtls)
			throws CGSystemException, CGBusinessException {
		CollectionDO collection = new CollectionDO();
		collection.setTxnNo(cnCollectionDtls.getTxnNo());
		if (!StringUtil.isEmptyInteger(cnCollectionDtls.getCollectionID())) {
			collection.setCollectionId(cnCollectionDtls.getCollectionID());
		}
		collection.setCollectionDate(DateUtil
				.slashDelimitedstringToDDMMYYYYFormat(cnCollectionTO
						.getCnCollectionDate()));

		collection.setBankName(cnCollectionDtls.getBankName());
		collection.setChqDate(DateUtil
				.slashDelimitedstringToDDMMYYYYFormat(cnCollectionDtls
						.getChqDate()));
		collection.setChqNo(cnCollectionDtls.getChqNo());
		PaymentModeDO paymentModeDO = null;
		if (!StringUtil.isEmptyInteger(cnCollectionDtls.getPaymentModeId())) {
			paymentModeDO = new PaymentModeDO();
			paymentModeDO.setPaymentId(cnCollectionDtls.getPaymentModeId());
			collection.setPaymentModeDO(paymentModeDO);
		}

		// Total Collection Amount = Rcvd + TDS
		if (!StringUtil.isEmptyDouble(cnCollectionDtls.getTotal())) {
			collection.setTotalAmount(cnCollectionDtls.getTotal());
		} else {
			collection.setTotalAmount(0.0);
		}

		collection.setStatus(cnCollectionTO.getStatus());
		collection.setCollectionCategory(MECCommonConstants.CN_COLLECTION_TYPE);

		OfficeDO collOffDO = new OfficeDO();
		collOffDO.setOfficeId(cnCollectionTO.getOriginOfficeId());
		collection.setCollectionOfficeDO(collOffDO);

		// Added by Himal - Bank GL
		if (!StringUtil.isEmptyInteger(cnCollectionDtls.getBankGL())) {
			GLMasterDO bankGLDO = new GLMasterDO();
			bankGLDO.setGlId(cnCollectionDtls.getBankGL());
			collection.setBankGLDO(bankGLDO);
		}

		// Created By
		if (!StringUtil.isEmptyInteger(cnCollectionDtls.getCreatedBy())) {
			collection.setCreatedBy(cnCollectionDtls.getCreatedBy());
		}
		// Updated By
		if (!StringUtil.isEmptyInteger(cnCollectionDtls.getUpdatedBy())) {
			collection.setUpdatedBy(cnCollectionDtls.getUpdatedBy());
		}

		// Setting Created Date & Updated Date
		collection.setCreatedDate(Calendar.getInstance().getTime());
		collection.setUpdatedDate(Calendar.getInstance().getTime());

		// Setting CN Collection details
		Set<CollectionDtlsDO> cnDtls = new HashSet<CollectionDtlsDO>();
		CollectionDtlsDO collectionDtls = new CollectionDtlsDO();
		if (!StringUtil.isEmptyInteger(cnCollectionDtls.getEntryId())) {
			collectionDtls.setEntryId(cnCollectionDtls.getEntryId());
		}
		collectionDtls.setReceiptNo(cnCollectionDtls.getReceiptNo());

		// Bill Amount
		if (!StringUtil.isEmptyDouble(cnCollectionDtls.getAmount())) {
			collectionDtls.setBillAmount(cnCollectionDtls.getAmount());
		} else {
			collectionDtls.setBillAmount(0.0);
		}

		// Recv. Amount
		if (!StringUtil.isEmptyDouble(cnCollectionDtls.getRcvdAmt())) {
			collectionDtls.setRecvAmount(cnCollectionDtls.getRcvdAmt());
		} else {
			collectionDtls.setRecvAmount(0.0);
		}

		// TDS Amount
		if (!StringUtil.isEmptyDouble(cnCollectionDtls.getTdsAmt())) {
			collectionDtls.setTdsAmount(cnCollectionDtls.getTdsAmt());
		} else {
			collectionDtls.setTdsAmount(0.0);
		}

		// Total Bill Amount
		if (!StringUtil.isEmptyDouble(cnCollectionDtls.getTotal())) {
			collectionDtls.setTotalBillAmount(cnCollectionDtls.getTotal());
		} else {
			collectionDtls.setTotalBillAmount(0.0);
		}

		collectionDtls.setCollectionType(cnCollectionDtls.getCollectionType());
		collectionDtls.setCollectionFor(cnCollectionDtls.getCnfor());
		collectionDtls.setConsgDeliveryDate(DateUtil
				.parseStringDateToDDMMYYYYHHMMFormat(cnCollectionDtls
						.getConsgDeliveryDate()));
		ConsignmentDO consgDO = new ConsignmentDO();
		consgDO.setConsgId(cnCollectionDtls.getConsgId());
		consgDO.setConsgNo(cnCollectionDtls.getCnNo());
		collectionDtls.setConsgDO(consgDO);

		if (!StringUtil.isEmptyInteger(cnCollectionDtls.getReasonId())) {
			ReasonDO reasonDO = new ReasonDO();
			reasonDO.setReasonId(cnCollectionDtls.getReasonId());
			collectionDtls.setReasonDO(reasonDO);
		}
		// Created By
		if (!StringUtil.isEmptyInteger(cnCollectionDtls.getCreatedBy())) {
			collectionDtls.setCreatedBy(cnCollectionDtls.getCreatedBy());
		}
		// Updated By
		if (!StringUtil.isEmptyInteger(cnCollectionDtls.getUpdatedBy())) {
			collectionDtls.setUpdatedBy(cnCollectionDtls.getUpdatedBy());
		}

		// Setting Created Date & Updated Date
		collectionDtls.setCreatedDate(Calendar.getInstance().getTime());
		collectionDtls.setUpdatedDate(Calendar.getInstance().getTime());

		collectionDtls.setCollectionDO(collection);
		cnDtls.add(collectionDtls);
		collection.setCollectionDtls(cnDtls);
		return collection;
	}

	public static CNCollectionTO cnCollectionDOtoTOConverter(
			CollectionDO collectionDO) {
		CNCollectionTO cnTO = new CNCollectionTO();
		List<CNCollectionDtlsTO> cnCollectionDtlsTO = new ArrayList<>();
		for (CollectionDtlsDO cnDtlsDO : collectionDO.getCollectionDtls()) {
			CNCollectionDtlsTO cnDtls = new CNCollectionDtlsTO();
			cnDtls.setAmount(cnDtlsDO.getTotalBillAmount());
			cnDtls.setBankName(collectionDO.getBankName());
			cnDtls.setChqDate(DateUtil.getDDMMYYYYDateString(collectionDO
					.getChqDate()));
			cnDtls.setChqNo(collectionDO.getChqNo());
			cnDtls.setCnfor(cnDtlsDO.getCollectionFor());
			if (!StringUtil.isNull(cnDtlsDO.getConsgDO())) {
				cnDtls.setCnNo(cnDtlsDO.getConsgDO().getConsgNo());
				cnDtls.setConsgId(cnDtlsDO.getConsgDO().getConsgId());

			}

			cnDtls.setCollectionID(collectionDO.getCollectionId());
			cnDtls.setCollectionType(collectionDO.getCollectionCategory());
			cnDtls.setEntryId(cnDtlsDO.getEntryId());
			if (!StringUtil.isNull(collectionDO.getPaymentModeDO())) {
				cnDtls.setPaymentModeId(collectionDO.getPaymentModeDO()
						.getPaymentId());
			}
			cnDtls.setRcvdAmt(cnDtlsDO.getRecvAmount());
			cnDtls.setReceiptNo(cnDtlsDO.getReceiptNo());
			cnDtls.setSrNo(cnDtlsDO.getPosition());
			cnDtls.setTdsAmt(cnDtlsDO.getTdsAmount());
			cnDtls.setTxnNo(collectionDO.getTxnNo());
			cnDtls.setStatus(collectionDO.getStatus());
			cnCollectionDtlsTO.add(cnDtls);
		}
		cnTO.setCnCollectionDtls(cnCollectionDtlsTO);
		return cnTO;
	}

	public static List<CollectionDtlsDO> convertDeliveryDtlsToCnCollDtls(
			List<DeliveryDetailsDO> deliveryDetailsDOs) {
		List<CollectionDtlsDO> collectionDtlsDOs = new ArrayList<CollectionDtlsDO>();
		for (DeliveryDetailsDO domain : deliveryDetailsDOs) {
			if (!StringUtil.isNull(domain.getConsignmentDO())) {
				CollectionDtlsDO collDtlsDO = null;
				/* Consignment */
				ConsignmentDO consgDO = new ConsignmentDO();
				consgDO.setConsgId(domain.getConsignmentDO().getConsgId());
				/* Delivery Date as String Value */
				String deliveryDt = DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(domain
								.getDeliveryDate());
				/* To set COD Amount */
				if (!StringUtil.isEmptyDouble(domain.getCodAmount())) {
					collDtlsDO = new CollectionDtlsDO();
					collDtlsDO.setConsgDO(consgDO);
					collDtlsDO
							.setCollectionType(MECCommonConstants.COLLECTION_TYPE_COD);
					collDtlsDO.setBillAmount(domain.getCodAmount());
					collDtlsDO.setConsgDeliveryDate(DateUtil
							.parseStringDateToDDMMYYYYHHMMFormat(deliveryDt));
					collectionDtlsDOs.add(collDtlsDO);
				}
				/* To set LC Amount */
				if (!StringUtil.isEmptyDouble(domain.getLcAmount())) {
					collDtlsDO = new CollectionDtlsDO();
					collDtlsDO.setConsgDO(consgDO);
					collDtlsDO
							.setCollectionType(MECCommonConstants.COLLECTION_TYPE_LC);
					collDtlsDO.setBillAmount(domain.getLcAmount());
					collDtlsDO.setConsgDeliveryDate(DateUtil
							.parseStringDateToDDMMYYYYHHMMFormat(deliveryDt));
					collectionDtlsDOs.add(collDtlsDO);
				}
				/* To set To Pay Amount */
				if (!StringUtil.isEmptyDouble(domain.getToPayAmount())) {
					collDtlsDO = new CollectionDtlsDO();
					collDtlsDO.setConsgDO(consgDO);
					collDtlsDO
							.setCollectionType(MECCommonConstants.COLLECTION_TYPE_TOPAY);
					collDtlsDO.setBillAmount(domain.getToPayAmount());
					collDtlsDO.setConsgDeliveryDate(DateUtil
							.parseStringDateToDDMMYYYYHHMMFormat(deliveryDt));
					collectionDtlsDOs.add(collDtlsDO);
				}
			}// END IF
		}// END FOR LOOP
		return collectionDtlsDOs;
	}

	/**
	 * To set CN collection details to List
	 * 
	 * @param cnCollectionTO
	 * @param mecCommonService
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public static void setUpCNCollectionDetails(CNCollectionTO cnCollectionTO,
			MECCommonService mecCommonService) throws CGSystemException,
			CGBusinessException {
		LOGGER.trace("CNCollectionServiceImpl :: setUpCNCollectionDetails() :: Start --------> ::::::");
		List<CNCollectionDtlsTO> cnCollectiondtlsTOs = new ArrayList<CNCollectionDtlsTO>();
		int listLength = cnCollectionTO.getCnNo().length;
		if (cnCollectionTO.getCnNo() != null && listLength > 0) {
			for (int rowCount = 0; rowCount < listLength; rowCount++) {
				if (!StringUtil.isNull(cnCollectionTO.getIsChecked()[rowCount])
						&& cnCollectionTO.getIsChecked()[rowCount]
								.equalsIgnoreCase(CommonConstants.YES)) {
					CNCollectionDtlsTO cnCollectionDtlsTO = new CNCollectionDtlsTO();
					if (StringUtils
							.isEmpty(cnCollectionTO.getTxnNo()[rowCount])) {
						/* Auto generates tx. number for Collection */
						/*List<String> seqNOs = (List<String>) mecCommonService
								.generateSequenceNo(
										1,
										CommonConstants.GEN_MISC_CONSG_COLL_TXN_NO);
						String txNumber = MECCommonConstants.TX_CODE_CC
								+ cnCollectionTO.getOriginOfficeCode()
								+ seqNOs.get(0);*/
						
						SequenceGeneratorConfigTO sequenceGeneratorConfigTO= new SequenceGeneratorConfigTO();
						sequenceGeneratorConfigTO.setPrefixCode(cnCollectionTO.getOriginOfficeCode() + MECCommonConstants.TX_CODE_CC);
						sequenceGeneratorConfigTO.setProcessRequesting(MECCommonConstants.TX_CODE_CC);
						sequenceGeneratorConfigTO.setRequestDate(new Date());
						sequenceGeneratorConfigTO.setRequestingBranchCode(cnCollectionTO.getOriginOfficeCode());
						sequenceGeneratorConfigTO.setRequestingBranchId(cnCollectionTO.getOriginOfficeId());
						sequenceGeneratorConfigTO.setSequenceRunningLength(CommonConstants.COLLECTION_RUNNING_NUMBER_LENGTH);
						
						List<String> seqNOs = mecCommonService.generateSequenceNo(sequenceGeneratorConfigTO);
						if(CGCollectionUtils.isEmpty(seqNOs)){
							throw new CGBusinessException(FrameworkConstants.SEQUENCE_NUMBER_NOT_GENERATED);
						}
						String txNumber = seqNOs.get(0);
						cnCollectionDtlsTO.setTxnNo(txNumber);
					} else {
						cnCollectionDtlsTO
								.setTxnNo(cnCollectionTO.getTxnNo()[rowCount]);
					}
					if (!StringUtil.isEmptyInteger(cnCollectionTO
							.getCollectionEntryId()[rowCount])) {
						cnCollectionDtlsTO.setEntryId(cnCollectionTO
								.getCollectionEntryId()[rowCount]);
					}
					// Total Collection
					if (!StringUtil
							.isEmptyDouble(cnCollectionTO.getAmount()[rowCount]))
						cnCollectionDtlsTO
								.setAmount(cnCollectionTO.getAmount()[rowCount]);

					if (!StringUtil
							.isStringEmpty(cnCollectionTO.getReceiptNo()[rowCount]))
						cnCollectionDtlsTO.setReceiptNo(cnCollectionTO
								.getReceiptNo()[rowCount]);

					if (!StringUtil
							.isStringEmpty(cnCollectionTO.getChqDate()[rowCount]))
						cnCollectionDtlsTO.setChqDate(cnCollectionTO
								.getChqDate()[rowCount]);

					if (!StringUtil
							.isStringEmpty(cnCollectionTO.getChqNo()[rowCount]))
						cnCollectionDtlsTO
								.setChqNo(cnCollectionTO.getChqNo()[rowCount]);

					if (!StringUtil
							.isStringEmpty(cnCollectionTO.getCnNo()[rowCount]))
						cnCollectionDtlsTO
								.setCnNo(cnCollectionTO.getCnNo()[rowCount]);

					if (!StringUtil
							.isStringEmpty(cnCollectionTO.getCnfor()[rowCount]))
						cnCollectionDtlsTO
								.setCnfor(cnCollectionTO.getCnfor()[rowCount]);

					if (!StringUtil.isStringEmpty(cnCollectionTO
							.getCollectionType()[rowCount]))
						cnCollectionDtlsTO.setCollectionType(cnCollectionTO
								.getCollectionType()[rowCount]);
					// Total = Rcvd + TDS
					if (!StringUtil
							.isEmptyDouble(cnCollectionTO.getTotals()[rowCount]))
						cnCollectionDtlsTO
								.setTotal(cnCollectionTO.getTotals()[rowCount]);
					// TDS Amount
					if (!StringUtil
							.isEmptyDouble(cnCollectionTO.getTdsAmt()[rowCount]))
						cnCollectionDtlsTO
								.setTdsAmt(cnCollectionTO.getTdsAmt()[rowCount]);
					// Rcvd Amount
					if (!StringUtil
							.isEmptyDouble(cnCollectionTO.getRcvdAmt()[rowCount]))
						cnCollectionDtlsTO.setRcvdAmt(cnCollectionTO
								.getRcvdAmt()[rowCount]);

					if (!StringUtil
							.isEmptyInteger(cnCollectionTO.getConsgIds()[rowCount]))
						cnCollectionDtlsTO.setConsgId(cnCollectionTO
								.getConsgIds()[rowCount]);

					if (!StringUtil
							.isStringEmpty(cnCollectionTO.getMode()[rowCount]))
						cnCollectionDtlsTO
								.setPaymentModeId(StringUtil
										.parseInteger(cnCollectionTO.getMode()[rowCount]));

					if (!StringUtil.isEmptyInteger(cnCollectionTO
							.getCollectionID()[rowCount]))
						cnCollectionDtlsTO.setCollectionID(cnCollectionTO
								.getCollectionID()[rowCount]);

					if (!StringUtil
							.isStringEmpty(cnCollectionTO.getBankName()[rowCount]))
						cnCollectionDtlsTO.setBankName(cnCollectionTO
								.getBankName()[rowCount]);

					if (!StringUtil.isEmptyInteger(cnCollectionTO
							.getReasonIds()[rowCount])
							&& !StringUtil.isEmptyInteger(cnCollectionTO
									.getReasonIds()[rowCount])) {
						cnCollectionDtlsTO.setReasonId(cnCollectionTO
								.getReasonIds()[rowCount]);
					}

					// Added by Himal - Bank GL
					if (!StringUtil
							.isEmptyInteger(cnCollectionTO.getBankGLs()[rowCount]))
						cnCollectionDtlsTO.setBankGL(cnCollectionTO
								.getBankGLs()[rowCount]);

					if (!StringUtil.isStringEmpty(cnCollectionTO
							.getCnDeliveryDt()[rowCount]))
						cnCollectionDtlsTO.setConsgDeliveryDate(cnCollectionTO
								.getCnDeliveryDt()[rowCount]);

					if (!StringUtil.isEmptyInteger(cnCollectionTO
							.getCreatedBy())) {
						cnCollectionDtlsTO.setCreatedBy(cnCollectionTO
								.getCreatedBy());
					}

					if (!StringUtil.isEmptyInteger(cnCollectionTO
							.getUpdatedBy())) {
						cnCollectionDtlsTO.setUpdatedBy(cnCollectionTO
								.getUpdatedBy());
					}

					cnCollectiondtlsTOs.add(cnCollectionDtlsTO);
				}// END IF
			}// END FOR LOOP
		}
		cnCollectionTO.setCnCollectionDtls(cnCollectiondtlsTOs);
		LOGGER.trace("CNCollectionServiceImpl :: setUpCNCollectionDetails() :: END --------> ::::::");
	}
}
