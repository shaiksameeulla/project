package com.ff.sap.integration.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.LongType;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.email.EmailSenderUtil;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.billing.BillingConsignmentSummaryDO;
import com.ff.domain.billing.ConsignmentBilling;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.billing.ConsignmentBookingBillingMappingDO;
import com.ff.domain.billing.SAPBillSalesOrderDO;
import com.ff.domain.billing.SAPBillSalesOrderStagingDO;
import com.ff.domain.billing.SAPBillingConsignmentSummaryDO;
import com.ff.domain.billing.SAPStagingBillSalesOrderDO;
import com.ff.domain.billing.SalesOrderInterfaceDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingInterfaceWrapperDO;
import com.ff.domain.business.CSDSAPLoadMovementVendorDO;
import com.ff.domain.business.SAPVendorDO;
import com.ff.domain.coloading.CSDSAPColoaderInvoiceDO;
import com.ff.domain.coloading.ColoaderRatesDO;
import com.ff.domain.coloading.SAPCocourierDO;
import com.ff.domain.coloading.SAPColoaderRatesDO;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.mec.LiabilityDO;
import com.ff.domain.mec.LiabilityDetailsDO;
import com.ff.domain.mec.SAPLiabilityEntriesDO;
import com.ff.domain.mec.SAPLiabilityPaymentDO;
import com.ff.domain.mec.SAPOutstandingPaymentDO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.mec.collection.CollectionDtlsDO;
import com.ff.domain.mec.collection.SAPCollectionDO;
import com.ff.domain.mec.expense.ExpenseDO;
import com.ff.domain.mec.expense.SAPExpenseDO;
import com.ff.domain.organization.CSDSAPEmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.organization.SAPEmployeeDO;
import com.ff.domain.organization.SAPOfficeDO;
import com.ff.domain.pndcommission.SAPDeliveryCommissionCalcDO;
import com.ff.domain.ratemanagement.masters.CSDSAPCustomerDO;
import com.ff.domain.ratemanagement.masters.ContractPaymentBillingLocationDO;
import com.ff.domain.ratemanagement.masters.SAPContractDO;
import com.ff.domain.ratemanagement.masters.SAPCustomerDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.stockmanagement.masters.CSDSAPItemDO;
import com.ff.domain.stockmanagement.masters.SAPItemDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.domain.stockmanagement.operations.cancel.SAPStockCancellationDO;
import com.ff.domain.stockmanagement.operations.cancel.StockCancellationDO;
import com.ff.domain.stockmanagement.operations.issue.SAPStockIssueDO;
import com.ff.domain.stockmanagement.operations.issue.StockIssueItemDtlsDO;
import com.ff.domain.stockmanagement.operations.receipt.SAPStockReceiptDO;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptDO;
import com.ff.domain.stockmanagement.operations.reduction.SAPStockConsolidationDO;
import com.ff.domain.stockmanagement.operations.requisition.SAPStockRequisitionDO;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionItemDtlsDO;
import com.ff.domain.stockmanagement.operations.stockreturn.SAPStockReturnDO;
import com.ff.domain.stockmanagement.operations.stockreturn.StockReturnDO;
import com.ff.domain.stockmanagement.operations.transfer.SAPStockTransferDO;
import com.ff.domain.stockmanagement.operations.transfer.StockTransferDO;
import com.ff.domain.umc.EmployeeUserDO;
import com.ff.domain.umc.UserDO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.to.SAPBillingConsgSummaryTO;
import com.ff.sap.integration.to.SAPCoCourierTO;
import com.ff.sap.integration.to.SAPCollectionTO;
import com.ff.sap.integration.to.SAPColoaderTO;
import com.ff.sap.integration.to.SAPContractTO;
import com.ff.sap.integration.to.SAPErrorTO;
import com.ff.sap.integration.to.SAPExpenseTO;
import com.ff.sap.integration.to.SAPLiabilityEntriesTO;
import com.ff.sap.integration.to.SAPLiabilityPaymentTO;
import com.ff.sap.integration.to.SAPOutstandingPaymentTO;
import com.ff.sap.integration.to.SAPRegionCodeTO;
import com.ff.sap.integration.to.SAPSalesOrderTO;
import com.ff.sap.integration.to.SAPStockCancellationTO;
import com.ff.sap.integration.to.SAPStockIssueTO;
import com.ff.sap.integration.to.SAPStockReceiptTO;
import com.ff.sap.integration.to.SAPStockRequisitionTO;
import com.ff.sap.integration.to.SAPStockReturnTO;
import com.ff.sap.integration.to.SAPStockTransferTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;

public class SAPIntegrationDAOImpl extends CGBaseDAO implements
		SAPIntegrationDAO {

	Logger logger = Logger.getLogger(SAPIntegrationDAOImpl.class);
	private EmailSenderUtil emailSenderUtil;

	/**
	 * @param emailSenderUtil
	 *            the emailSenderUtil to set
	 */
	public void setEmailSenderUtil(EmailSenderUtil emailSenderUtil) {
		this.emailSenderUtil = emailSenderUtil;
	}

	public List<CSDSAPItemDO> saveDetailsOneByOneForMaterials(
			List<CSDSAPItemDO> baseDO) throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: saveDetailsOneByOneForMaterials :: start======>");
		List<CSDSAPItemDO> errorReords = null;
		String exception = null;
		// boolean isSaved = false;
		errorReords = new ArrayList<>();
		for (CSDSAPItemDO baseDo : baseDO) {
			try {
				getHibernateTemplate().save(baseDO);
				// isSaved = true;
			} catch (Exception e) {
				exception = e.getMessage();
				// /baseDo.setException(exception);
				errorReords.add(baseDo);
			}
		}
		logger.debug("SAPIntegrationDAOImpl::saveDetailsOneByOneForMaterials:: end======>");
		return errorReords;
	}

	public SAPErrorTO saveDetails(CGBaseDO baseDO) throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl::saveDetails:: start======>");
		SAPErrorTO errorTO = new SAPErrorTO();
		try {
			getHibernateTemplate().save(baseDO);
		} catch (Exception ex) {
			logger.error(
					"Exception In :: SAPIntegrationDAOImpl :: updateEmployeeDetails :: Error",
					ex);
			OfficeDO ofcDO = (OfficeDO) baseDO;
			if (!StringUtil
					.isStringEmpty(ex.getCause().getCause().getMessage())) {
				errorTO.setErrorMessage(ex.getCause().getCause().getMessage());
			}
			if (!StringUtil.isStringEmpty(ofcDO.getOfficeCode())) {
				errorTO.setTransactionNo(ofcDO.getOfficeCode());
			}
		}
		logger.debug("SAPIntegrationDAOImpl::saveDetails:: end======>");
		return errorTO;
	}

	public boolean saveOrUpdateVendorDetails(
			List<CSDSAPLoadMovementVendorDO> baseDO) throws CGSystemException {
		logger.debug("VENDOR :: SAPIntegrationDAOImpl::saveOrUpdateVendorDetails:: start======>");
		boolean isSaved = false;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		for (CSDSAPLoadMovementVendorDO vendorDO : baseDO) {
			try {
				getHibernateTemplate().save(vendorDO);
			} catch (Exception ex) {
				logger.error(
						"VENDOR :: Exception In :: SAPIntegrationDAOImpl :: saveOrUpdateVendorDetails ::",
						ex);
				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(ex.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(ex.getCause().getCause()
							.getMessage());
				}
				if (!StringUtil.isStringEmpty(vendorDO.getVendorCode())) {
					errorTO.setTransactionNo(vendorDO.getVendorCode());
				}
				sapErroTOlist.add(errorTO);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Vendor Error Records");
		isSaved = true;
		logger.debug("VENDOR :: SAPIntegrationDAOImpl::saveOrUpdateVendorDetails:: end======>");
		return isSaved;
	}

	public boolean saveDetails(List<CGBaseDO> baseDOList)
			throws CGSystemException {
		logger.debug("SALES ORDER :: SAPIntegrationDAOImpl::saveDetails:: start======>");
		boolean isSaved = false;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		for (CGBaseDO baseDO : baseDOList) {
			try {
				getHibernateTemplate().save(baseDO);
				isSaved = true;
			} catch (Exception ex) {
				logger.error(
						"SALES ORDER :: Exception In :: SAPIntegrationDAOImpl :: updateEmployeeDetails :: Error",
						ex);
				SAPErrorTO errorTO = new SAPErrorTO();
				SAPBillSalesOrderDO sapbillSalesOrderDO = (SAPBillSalesOrderDO) baseDO;
				if (!StringUtil.isStringEmpty(ex.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(ex.getCause().getCause()
							.getMessage());
				}
				if (!StringUtil.isStringEmpty(sapbillSalesOrderDO.getBillNo())) {
					errorTO.setTransactionNo(sapbillSalesOrderDO.getBillNo());
				}
				sapErroTOlist.add(errorTO);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Expense Error Records");
		logger.debug("SALES ORDER :: SAPIntegrationDAOImpl::saveDetails:: end======>");
		return isSaved;
	}

	public List<CSDSAPLoadMovementVendorDO> saveDetailsOneByOne(
			List<CSDSAPLoadMovementVendorDO> baseDO) throws CGSystemException {
		logger.debug("VENDOR :: SAPIntegrationDAOImpl :: saveDetailsOneByOne :: start======>");
		List<CSDSAPLoadMovementVendorDO> errorReords = null;
		String exception = null;
		errorReords = new ArrayList<CSDSAPLoadMovementVendorDO>();
		for (CSDSAPLoadMovementVendorDO baseDo : baseDO) {
			try {
				getHibernateTemplate().save(baseDO);
			} catch (Exception e) {
				exception = e.getMessage();
				baseDo.setException(exception);
				errorReords.add(baseDo);
			}
		}
		logger.debug("VENDOR :: SAPIntegrationDAOImpl::saveDetailsOneByOne:: end======>");
		return errorReords;
	}

	@Override
	public List<OfficeDO> saveDetailsOneByOneOffice(List<OfficeDO> baseDO)
			throws CGSystemException {
		logger.debug("OFFICE :: SAPIntegrationDAOImpl :: saveDetailsOneByOneOffice :: start======>");
		List<OfficeDO> errorReords = null;
		String exception = null;
		// boolean isSaved = false;
		errorReords = new ArrayList<OfficeDO>();
		for (OfficeDO baseDo : baseDO) {
			try {
				getHibernateTemplate().save(baseDO);
				// isSaved = true;
			} catch (Exception e) {
				exception = e.getMessage();
				baseDo.setException(exception);
				errorReords.add(baseDo);
			}
		}
		logger.debug("OFFICE :: SAPIntegrationDAOImpl::saveDetailsOneByOneOffice:: end======>");
		return errorReords;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<StockRequisitionItemDtlsDO> getDtls(String
	 * queryName, String[] paramNames, Object[] paramValues) throws
	 * CGSystemException {
	 * logger.debug("SAPIntegrationDAOImpl::getDtls:: start======>");
	 * List<StockRequisitionItemDtlsDO> stkReqDO = null; try{ stkReqDO =
	 * getHibernateTemplate
	 * ().findByNamedQueryAndNamedParam(queryName,paramNames, paramValues);
	 * logger.error("Exception In :: SAPIntegrationDAOImpl :: getDtls ::",e);
	 * throw new CGSystemException(e); }
	 * logger.debug("SAPIntegrationDAOImpl::getDtls:: end======>"); return
	 * stkReqDO; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<StockRequisitionItemDtlsDO> getDtls(
			SAPStockRequisitionTO stockReqTO, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("STOCKREQUISION :: SAPIntegrationDAOImpl :: getDtls :: start======>");
		List<StockRequisitionItemDtlsDO> stkReqDO = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_REQ_DETAILS_FOR_SAP);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					stockReqTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			stkReqDO = query.list();
		} catch (Exception e) {
			logger.error(
					"STOCKREQUISION :: Exception In :: SAPIntegrationDAOImpl :: getDtls ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("STOCKREQUISION :: SAPIntegrationDAOImpl :: getDtls :: End======>");
		return stkReqDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockRequisitionItemDtlsDO> getDtlsForRHOExternal(
			SAPStockRequisitionTO stockReqTO, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("STOCKREQUISION :: SAPIntegrationDAOImpl :: getDtlsForRHOExternal :: start======>");
		List<StockRequisitionItemDtlsDO> stkReqDO = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_REQ_DTLS_FOR_SAP_RHO_EXT);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					stockReqTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			stkReqDO = query.list();
		} catch (Exception e) {
			logger.error(
					"STOCKREQUISION :: Exception In :: SAPIntegrationDAOImpl :: getDtlsForRHOExternal ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("STOCKREQUISION :: SAPIntegrationDAOImpl :: getDtlsForRHOExternal :: End======>");
		return stkReqDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockIssueItemDtlsDO> getDtlsSAPStockIssue(
			SAPStockIssueTO sapStockIssueTO, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("STOCKISSUE :: SAPIntegrationDAOImpl :: getDtlsSAPStockIssue :: start======>");
		List<StockIssueItemDtlsDO> stkIssueDO = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_ISSUE_DETAILS_FOR_SAP);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					sapStockIssueTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			stkIssueDO = query.list();
		} catch (Exception e) {
			logger.error(
					"STOCKISSUE :: Exception In :: SAPIntegrationDAOImpl :: getDtlsSAPStockIssue ::",
					e);
			throw new CGSystemException(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		logger.debug("STOCKISSUE :: SAPIntegrationDAOImpl :: getDtlsSAPStockIssue :: end======>");
		return stkIssueDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockReceiptDO> getDtlsSAPStockReceipt(
			SAPStockReceiptTO receiptTO, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("STOCKACKNOWLEDGEMENT :: SAPIntegrationDAOImpl :: getDtlsSAPStockReceipt :: Start");
		List<StockReceiptDO> receiptDO = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_RECEIPT_DETAILS_FOR_SAP);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					receiptTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			receiptDO = query.list();
		} catch (Exception e) {
			logger.error(
					"STOCKACKNOWLEDGEMENT :: Exception In :: SAPIntegrationDAOImpl :: getDtlsSAPStockReceipt ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("STOCKACKNOWLEDGEMENT :: SAPIntegrationDAOImpl :: getDtlsSAPStockReceipt :: End");
		return receiptDO;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<StockReceiptDO> getDtlsSAPStockReceipt(String
	 * queryName, String[] paramNames, Object[] paramValues) throws
	 * CGSystemException {
	 * 
	 * logger.debug(
	 * "SAPIntegrationDAOImpl :: getDtlsSAPStockReceipt :: start======>");
	 * List<StockReceiptDO> receiptDO = null; try{ receiptDO =
	 * getHibernateTemplate
	 * ().findByNamedQueryAndNamedParam(queryName,paramNames, paramValues);
	 * }catch(Exception e){ logger.error(
	 * "Exception In :: SAPIntegrationDAOImpl :: getDtlsSAPStockReceipt ::",e);
	 * throw new CGSystemException(e); }
	 * logger.debug("SAPIntegrationDAOImpl :: getDtlsSAPStockReceipt :: end======>"
	 * ); return receiptDO;
	 * 
	 * }
	 */

	public void updateDateTimeAndStatusFlag(
			SAPStockRequisitionDO stockRequisitionDO) throws CGSystemException {
		logger.debug("STOCKREQUISION :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlag :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_REQ_DTLS;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = Calendar.getInstance().getTime();
			String dateStamp = df.format(today);
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					stockRequisitionDO.getSapStatus());
			qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
			qry.setInteger(SAPIntegrationConstants.STOCK_REQ_DTLS_ID,
					stockRequisitionDO.getStockRequisitionItemDtlsId());
			qry.executeUpdate();
		} catch (Exception e) {
			logger.error(
					"STOCKREQUISION :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlag:: Exception  ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("STOCKREQUISION :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlag :: END");
	}

	public void updateDateTimeAndStatusFlagOfStockIssue(
			SAPStockIssueDO stockIssueDO) throws CGSystemException {
		logger.debug("STOCKISSUE :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfStockIssue::=======>Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_ISSUE_DTLS;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = Calendar.getInstance().getTime();
			String dateStamp = df.format(today);
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					stockIssueDO.getSapStatus());
			qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
			qry.setLong(SAPIntegrationConstants.STOCK_ISSUE_DTLS_ID,
					stockIssueDO.getStockIssueItemDtlsId());
			qry.executeUpdate();
		} catch (Exception e) {
			logger.error(
					"STOCKISSUE :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfStockIssue:: Exception  ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("STOCKISSUE :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfStockIssue::=======>END");
	}

	public void updateDateTimeAndStatusFlagOfStockReceipt(
			SAPStockReceiptDO stockReceiptDO) throws CGSystemException {
		logger.debug("STOCKACKNOWLEDGEMENT :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfStockReceipt::=======>Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_RECEIPT_DTLS_FOR_SAP;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = Calendar.getInstance().getTime();
			String dateStamp = df.format(today);
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					stockReceiptDO.getSapStatus());
			qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
			qry.setString(SAPIntegrationConstants.STOCK_RECEIPT_NO,
					stockReceiptDO.getAckNumber());
			qry.executeUpdate();
		} catch (Exception e) {
			logger.error(
					"STOCKACKNOWLEDGEMENT :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfStockReceipt:: Exception  ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("STOCKACKNOWLEDGEMENT :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfStockReceipt::||||||||=======>END");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpenseDO> findExpenseDtlsForSAPIntegration(
			SAPExpenseTO expenseTO, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("EXPENSE :: SAPIntegrationDAOImpl :: findExpenseDtlsForSAPIntegration :: start======>");
		List<ExpenseDO> expenseDO = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_EXPENSE_DETAILS_FOR_SAP);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					expenseTO.getSapStatus());
			query.setParameter(SAPIntegrationConstants.EXPENSE_STATUS,
					expenseTO.getStatus());
			query.setParameter(SAPIntegrationConstants.EXPENSE_OFC_RHO,
					expenseTO.getReportingRHOID());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			expenseDO = query.list();
		} catch (Exception e) {
			logger.error(
					"EXPENSE :: Exception In :: SAPIntegrationDAOImpl :: findExpenseDtlsForSAPIntegration ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("EXPENSE :: SAPIntegrationDAOImpl :: findExpenseDtlsForSAPIntegration :: end======>");
		return expenseDO;
	}

	public void updateDateTimeAndStatusFlagOfExpense(SAPExpenseDO expDOList)
			throws CGSystemException {
		logger.debug("EXPENSE :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfExpense::=======>Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_EXPENSE_DTLS;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = Calendar.getInstance().getTime();
			String dateStamp = df.format(today);
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					expDOList.getSapStatus());
			qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
			qry.setString(SAPIntegrationConstants.EXPENSE_NO,
					expDOList.getTxNumber());
			qry.executeUpdate();
		} catch (Exception e) {
			logger.error(
					"EXPENSE :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfExpense:: Exception  ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("EXPENSE :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfExpense::||||||||=======>END");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CollectionDO> findCollectionDtlsForSAPIntegration(
			SAPCollectionTO sapCollectionTO, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("COLLECTION :: SAPIntegrationDAOImpl::findCollectionDtlsForSAPIntegration:: start======>");
		List<CollectionDO> collectionDO = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_COLLECTION_DETAILS_FOR_SAP);
			query.setParameter(SAPIntegrationConstants.COLLECTION_STATUS,
					sapCollectionTO.getStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			collectionDO = query.list();
		} catch (Exception e) {
			logger.error(
					"COLLECTION :: Exception In :: SAPIntegrationDAOImpl :: findCollectionDtlsForSAPIntegration ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("COLLECTION :: SAPIntegrationDAOImpl::findCollectionDtlsForSAPIntegration:: end======>");
		return collectionDO;
	}

	public boolean updateDateTimeAndStatusFlagOfCollection(
			SAPCollectionDO collnDOList) throws CGSystemException {
		logger.debug("COLLECTION :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfCollection::=======>Start");
		boolean isUpdated = false;
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_COLLECTION_DTLS;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = Calendar.getInstance().getTime();
			String dateStamp = df.format(today);
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					collnDOList.getSapStatus());
			qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
			qry.setString(SAPIntegrationConstants.COLL_NO,
					collnDOList.getTxNumber());
			qry.executeUpdate();
		} catch (Exception e) {
			logger.error(
					"COLLECTION :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfCollection:: Exception  ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("COLLECTION :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfCollection::||||||||=======>END");
		return isUpdated;
	}

	@Override
	public void saveExpenseStagingData(List<SAPExpenseDO> sapExpDOList)
			throws CGSystemException {
		logger.debug("EXPENSE :: SAPIntegrationDAOImpl :: saveExpenseStagingData :: start======>");
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		MailSenderTO mailTO = new MailSenderTO();
		String emailID = null;
		for (SAPExpenseDO sapExpDO : sapExpDOList) {
			try {
				getHibernateTemplate().save(sapExpDO);
				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time
				Date dateTime = Calendar.getInstance().getTime();
				sapExpDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>" + sapExpDO.getSapTimestamp());
				sapExpDO.setSapStatus("C");
				logger.debug("SAP Status ------>" + sapExpDO.getSapStatus());
				updateDateTimeAndStatusFlagOfExpense(sapExpDO);
			} catch (Exception ex) {
				logger.error(
						"STOCKCANCELLATION :: Exception In :: SAPIntegrationDAOImpl :: Error",
						ex);
				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time
				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(ex.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(ex.getCause().getCause()
							.getMessage());
				}
				if (!StringUtil.isStringEmpty(sapExpDO.getTxNumber())) {
					errorTO.setTransactionNo(sapExpDO.getTxNumber());
				}
				sapErroTOlist.add(errorTO);
				Date dateTime = Calendar.getInstance().getTime();
				sapExpDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>" + sapExpDO.getSapTimestamp());
				sapExpDO.setSapStatus("N");
				logger.debug("SAP Status ------>" + sapExpDO.getSapStatus());
				updateDateTimeAndStatusFlagOfExpense(sapExpDO);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Expense Error Records");
		logger.debug("EXPENSE :: SAPIntegrationDAOImpl::saveExpenseStagingData:: end======>");
	}

	@Override
	public void saveCollectionStagingData(List<SAPCollectionDO> sapColnDOList)
			throws CGSystemException {
		logger.debug("COLLECTION :: SAPIntegrationDAOImpl::saveCollectionStagingData:: start======>");
		Date dateTime = Calendar.getInstance().getTime();
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		for (SAPCollectionDO collcnDO : sapColnDOList) {
			try {
				getHibernateTemplate().save(collcnDO);
				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time
				collcnDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>" + collcnDO.getSapTimestamp());
				collcnDO.setSapStatus("C");
				logger.debug("SAP Status ------>" + collcnDO.getSapStatus());
				updateDateTimeAndStatusFlagOfCollection(collcnDO);
			} catch (Exception ex) {
				logger.error(
						"COLLECTION :: SAPIntegrationDAOImpl :: saveCollectionStagingData :: Exception  ",
						ex);
				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time

				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(ex.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(ex.getCause().getCause()
							.getMessage());
				}
				if (!StringUtil.isStringEmpty(collcnDO.getTxNumber())) {
					errorTO.setTransactionNo(collcnDO.getTxNumber());
				}
				sapErroTOlist.add(errorTO);
				collcnDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>" + collcnDO.getSapTimestamp());
				collcnDO.setSapStatus("N");
				logger.debug("SAP Status ------>" + collcnDO.getSapStatus());
				updateDateTimeAndStatusFlagOfCollection(collcnDO);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Collection Error Records");
		logger.debug("COLLECTION :: SAPIntegrationDAOImpl::saveCollectionStagingData:: end======>");
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<LiabilityDO>
	 * findLiabilityPaymentDtlsForSAPIntegration( String queryName, String[]
	 * paramNames, Object[] paramValues) throws CGSystemException {
	 * logger.debug(
	 * "SAPIntegrationDAOImpl::findLiabilityPaymentDtlsForSAPIntegration:: start======>"
	 * ); List<LiabilityDO> liabilityDO = null; try { liabilityDO =
	 * getHibernateTemplate().findByNamedQueryAndNamedParam( queryName,
	 * logger.error(
	 * "SAPIntegrationDAOImpl :: findLiabilityPaymentDtlsForSAPIntegration :: Exception  "
	 * , e); throw new CGSystemException(e); } logger.debug(
	 * "SAPIntegrationDAOImpl::findLiabilityPaymentDtlsForSAPIntegration:: end======>"
	 * ); return liabilityDO; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<LiabilityDO> findLiabilityPaymentDtlsForSAPIntegration(
			SAPLiabilityPaymentTO liabilityPaytTO, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("LIABILITY PAYMENT :: SAPIntegrationDAOImpl::findLiabilityPaymentDtlsForSAPIntegration:: start======>");
		List<LiabilityDO> liabilityDO = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_LIABILITY_PAYMENT_DETAILS_FOR_SAP);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					liabilityPaytTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			liabilityDO = query.list();
		} catch (Exception e) {
			logger.error(
					"LIABILITY PAYMENT :: Exception In :: SAPIntegrationDAOImpl :: findLiabilityPaymentDtlsForSAPIntegration ::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("LIABILITY PAYMENT :: SAPIntegrationDAOImpl::findLiabilityPaymentDtlsForSAPIntegration:: end======>");
		return liabilityDO;
	}

	public void updateDateTimeAndStatusFlagOfLiability(
			SAPLiabilityPaymentDO liabilityDOList) throws CGSystemException {
		logger.debug("LIABILITY PAYMENT :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfLiability :: Start");
		boolean isUpdated = false;
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_LIABILITY_DTLS;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = Calendar.getInstance().getTime();
			String dateStamp = df.format(today);
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					liabilityDOList.getSapStatus());
			qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
			qry.setString(SAPIntegrationConstants.EXPENSE_NO,
					liabilityDOList.getTxNumber());
			qry.executeUpdate();
		} catch (Exception e) {
			isUpdated = false;
			logger.error(
					"LIABILITY PAYMENT :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfLiability:: Exception  ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("LIABILITY PAYMENT :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfLiability :: END");
	}

	@Override
	public void saveLiabilityStagingData(
			List<SAPLiabilityPaymentDO> sapLiabilityDOList)
			throws CGSystemException {
		logger.debug("LIABILITY PAYMENT :: SAPIntegrationDAOImpl :: saveLiabilityStagingData :: start======>");
		Date dateTime = Calendar.getInstance().getTime();
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		boolean isSaved = false;
		for (SAPLiabilityPaymentDO sapLiabilityPayment : sapLiabilityDOList) {
			try {
				getHibernateTemplate().save(sapLiabilityPayment);
				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully
				// saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time

				sapLiabilityPayment.setSapTimestamp(dateTime);
				logger.debug("LIABILITY PAYMENT :: Date Stamp ------>"
						+ sapLiabilityPayment.getSapTimestamp());
				sapLiabilityPayment.setSapStatus("C");
				logger.debug("LIABILITY PAYMENT :: SAP Status ------>"
						+ sapLiabilityPayment.getSapStatus());
				updateDateTimeAndStatusFlagOfLiability(sapLiabilityPayment);
			} catch (Exception e) {
				logger.error(
						"LIABILITY PAYMENT :: Exception In :: SAPIntegrationDAOImpl :: saveLiabilityStagingData ::",
						e);
				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time

				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(e.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(e.getCause().getCause()
							.getMessage());
				}
				if (!StringUtil
						.isStringEmpty(sapLiabilityPayment.getTxNumber())) {
					errorTO.setTransactionNo(sapLiabilityPayment.getTxNumber());
				}
				sapErroTOlist.add(errorTO);
				sapLiabilityPayment.setSapTimestamp(dateTime);
				logger.debug("LIABILITY PAYMENT :: Date Stamp ------>"
						+ sapLiabilityPayment.getSapTimestamp());
				sapLiabilityPayment.setSapStatus("N");
				logger.debug("LIABILITY PAYMENT :: SAP Status ------>"
						+ sapLiabilityPayment.getSapStatus());
				updateDateTimeAndStatusFlagOfLiability(sapLiabilityPayment);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Liability Payment Error Records");
		logger.debug("LIABILITY PAYMENT :: SAPIntegrationDAOImpl :: saveLiabilityStagingData :: end======>");

	}

	@Override
	public boolean savePurchaseReqStagingData(
			List<SAPStockRequisitionDO> sapStkRequisitionDOList)
			throws CGSystemException {
		logger.debug("STOCKREQUISION :: SAPIntegrationDAOImpl :: savePurchaseReqStagingData :: Start ======>");
		boolean isSaved = false;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		Date dateTime = Calendar.getInstance().getTime();
		for (SAPStockRequisitionDO stockRequisitionDO : sapStkRequisitionDOList) {
			try {
				getHibernateTemplate().save(stockRequisitionDO);
				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time
				stockRequisitionDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"
						+ stockRequisitionDO.getSapTimestamp());
				stockRequisitionDO.setSapStatus("C");
				logger.debug("SAP Status ------>"
						+ stockRequisitionDO.getSapStatus());
				updateDateTimeAndStatusFlag(stockRequisitionDO);
			} catch (Exception ex) {
				isSaved = false;
				logger.error(
						"STOCKREQUISION :: Exception In :: SAPIntegrationDAOImpl :: savePurchaseReqStagingData ::",
						ex);
				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(ex.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(ex.getCause().getCause()
							.getMessage());
				}
				if (!StringUtil.isStringEmpty(stockRequisitionDO
						.getRequisitionNumber())) {
					errorTO.setTransactionNo(stockRequisitionDO
							.getRequisitionNumber());
				}
				sapErroTOlist.add(errorTO);
				stockRequisitionDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"
						+ stockRequisitionDO.getSapTimestamp());
				stockRequisitionDO.setSapStatus("N");
				logger.debug("SAP Status ------>"
						+ stockRequisitionDO.getSapStatus());
				updateDateTimeAndStatusFlag(stockRequisitionDO);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Stock Requisition Error Records");
		logger.debug("STOCKREQUISION :: SAPIntegrationDAOImpl :: savePurchaseReqStagingData :: End ======>");
		return isSaved;
	}

	@Override
	public boolean saveStockIssueStagingData(
			List<SAPStockIssueDO> sapStkIssueDOList) throws CGSystemException {
		logger.debug("STOCKISSUE :: SAPIntegrationDAOImpl :: saveStockIssueStagingData :: start======>");
		boolean isSaved = false;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		Date dateTime = Calendar.getInstance().getTime();
		for (SAPStockIssueDO stockIssueDO : sapStkIssueDOList) {
			try {
				getHibernateTemplate().save(stockIssueDO);
				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time
				stockIssueDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"
						+ stockIssueDO.getSapTimestamp());
				stockIssueDO.setSapStatus("C");
				logger.debug("SAP Status ------>" + stockIssueDO.getSapStatus());
				updateDateTimeAndStatusFlagOfStockIssue(stockIssueDO);
			} catch (Exception e) {
				logger.error(
						"STOCKISSUE :: Exception In :: SAPIntegrationDAOImpl :: saveStockIssueStagingData ::",
						e);
				stockIssueDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"
						+ stockIssueDO.getSapTimestamp());
				stockIssueDO.setSapStatus("N");
				logger.debug("SAP Status ------>" + stockIssueDO.getSapStatus());
				updateDateTimeAndStatusFlagOfStockIssue(stockIssueDO);

				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time

				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(e.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(e.getCause().getCause()
							.getMessage());
				}
				if (!StringUtil.isStringEmpty(stockIssueDO.getIssueNumber())) {
					errorTO.setTransactionNo(stockIssueDO.getIssueNumber());
				}
				sapErroTOlist.add(errorTO);
				throw new CGSystemException(e);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Stock Issue Error Records");
		logger.debug("STOCKISSUE :: SAPIntegrationDAOImpl :: saveStockIssueStagingData :: end======>");
		return isSaved;
	}

	@Override
	public boolean saveStockReceiptStagingData(
			List<SAPStockReceiptDO> sapStkReceiptDOList)
			throws CGSystemException {
		logger.debug("STOCKACKNOWLEDGEMENT :: SAPIntegrationDAOImpl :: saveStockReceiptStagingData :: start======>");
		boolean isSaved = false;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		Date dateTime = Calendar.getInstance().getTime();
		try {
			for (SAPStockReceiptDO stockReceiptDO : sapStkReceiptDOList) {
				try {
					getHibernateTemplate().save(stockReceiptDO);

					// 2 A
					// Updating status and time stamp in CSD Table if Data
					// successfully saved t o Staging Table
					// if flag = true status = C and Time stamp = current time
					// if flag = false status = N and time Stamp = current Time

					stockReceiptDO.setSapTimestamp(dateTime);
					logger.debug("Date Stamp ------>"
							+ stockReceiptDO.getSapTimestamp());
					stockReceiptDO.setSapStatus("C");
					logger.debug("SAP Status ------>"
							+ stockReceiptDO.getSapStatus());
					updateDateTimeAndStatusFlagOfStockReceipt(stockReceiptDO);
				} catch (Exception ex) {
					logger.error(
							"STOCKACKNOWLEDGEMENT :: Exception In :: SAPIntegrationDAOImpl :: ",
							ex);
					SAPErrorTO errorTO = new SAPErrorTO();
					if (!StringUtil.isStringEmpty(ex.getCause().getCause()
							.getMessage())) {
						errorTO.setErrorMessage(ex.getCause().getCause()
								.getMessage());
					}
					if (!StringUtil
							.isStringEmpty(stockReceiptDO.getAckNumber())) {
						errorTO.setTransactionNo(stockReceiptDO.getAckNumber());
					}
					sapErroTOlist.add(errorTO);
					stockReceiptDO.setSapTimestamp(dateTime);
					logger.debug("Date Stamp ------>"
							+ stockReceiptDO.getSapTimestamp());
					stockReceiptDO.setSapStatus("N");
					logger.debug("SAP Status ------>"
							+ stockReceiptDO.getSapStatus());
					updateDateTimeAndStatusFlagOfStockReceipt(stockReceiptDO);
				}
			}
			sendSapIntgErrorMail(
					sapErroTOlist,
					SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
					"Stock Acknowlwdgement Error Records");
		} catch (Exception e) {
			isSaved = false;
			logger.error(
					"STOCKACKNOWLEDGEMENT :: Exception In :: SAPIntegrationDAOImpl :: saveStockReceiptStagingData ::",
					e);
			throw new CGSystemException(e);
		}
		logger.debug("STOCKACKNOWLEDGEMENT :: SAPIntegrationDAOImpl :: saveStockReceiptStagingData :: end======>");
		return isSaved;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<ConsignmentDO>
	 * findLiabilityEntriesDtlsForSAPIntegration( String queryName, String[]
	 * paramNames, Object[] paramValues) throws CGSystemException {
	 * logger.debug(
	 * "SAPIntegrationDAOImpl :: findLiabilityEntriesDtlsForSAPIntegration :: start "
	 * ); List<ConsignmentDO> consgDOs = null; try { consgDOs =
	 * getHibernateTemplate().findByNamedQueryAndNamedParam( queryName,
	 * logger.error(
	 * "Exception In :: SAPIntegrationDAOImpl :: findLiabilityEntriesDtlsForSAPIntegration ::"
	 * , e); throw new CGSystemException(e); } logger.debug(
	 * "SAPIntegrationDAOImpl :: findLiabilityEntriesDtlsForSAPIntegration :: end "
	 * ); return consgDOs; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsignmentDO> findLiabilityEntriesDtlsForSAPIntegration(
			SAPLiabilityEntriesTO sapLiEntriesTO, Long maxDataCountLimit,
			ArrayList<Integer> productIDList) throws CGSystemException {
		logger.debug("CODLC BOOKING :: SAPIntegrationDAOImpl :: findLiabilityEntriesDtlsForSAPIntegration :: start ");
		List<ConsignmentDO> consgDOs = null;
		Session session = null;
		try {

			int maxDataCount = maxDataCountLimit.intValue();
			session =createSession();
			Query query = session.getNamedQuery(SAPIntegrationConstants.GET_BOOKED_COD_LC_CONSG);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,	sapLiEntriesTO.getSapStatus());
			query.setParameterList(SAPIntegrationConstants.PRODUCT_ID_LIST,	productIDList);
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			consgDOs = query.list();
		}
		catch (Exception e) {
			logger.error(
					"CODLC BOOKING :: Exception In :: SAPIntegrationDAOImpl :: findLiabilityEntriesDtlsForSAPIntegration ::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("CODLC BOOKING :: SAPIntegrationDAOImpl :: findLiabilityEntriesDtlsForSAPIntegration :: end ");
		return consgDOs;
	}

	/*
	 * @SuppressWarnings("unchecked") public List<LiabilityDetailsDO>
	 * findLiabilityEntriesDtlsForSAPIntegration( String queryName,String[]
	 * paramNames, Object[] paramValues) { logger.debug(
	 * "SAPIntegrationDAOImpl :: findLiabilityEntriesDtlsForSAPIntegration :: start "
	 * ); List<LiabilityDetailsDO> liabilityDetailsDOs = null; try{
	 * liabilityDetailsDOs =
	 * getHibernateTemplate().findByNamedQueryAndNamedParam
	 * (queryName,paramNames, paramValues); }catch(Exception e){
	 * "SAPIntegrationDAOImpl :: findLiabilityEntriesDtlsForSAPIntegration :: end "
	 * ); return liabilityDetailsDOs; }
	 */

	@Override
	public boolean updateDateTimeAndStatusFlagOfLiabilityDtls(
			List<LiabilityDetailsDO> liabilityDOsList) throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfLiabilityDtls :: Start");
		boolean isUpdated = false;
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_LIABILITY_ENTRIES_DTLS;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (LiabilityDetailsDO liabilityDtlsDO : liabilityDOsList) {
				Date today = Calendar.getInstance().getTime();
				String dateStamp = df.format(today);
				Query qry = session.getNamedQuery(queryName);
				qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
						liabilityDtlsDO.getSapStatus());
				qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
				qry.setLong(SAPIntegrationConstants.LIABILITY_DTLS_ID,
						liabilityDtlsDO.getLiabilityDetailId());
				qry.executeUpdate();
				isUpdated = true;
			}
		} catch (Exception e) {
			isUpdated = false;
			logger.error(
					"SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfLiabilityDtls:: Exception  ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfLiabilityDtls :: END");
		return isUpdated;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockCancellationDO> findCancellationDtlsForSAPIntegration(
			SAPStockCancellationTO stkCancellationTO, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("STOCKCANCELLATION :: SAPIntegrationDAOImpl :: findCancellationDtlsForSAPIntegration :: Start");
		List<StockCancellationDO> cancellationDOs = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_CANCELLATION_DETAILS_FOR_SAP);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					stkCancellationTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			cancellationDOs = query.list();
		} catch (Exception e) {
			logger.error(
					"STOCKCANCELLATION :: Exception In :: SAPIntegrationDAOImpl :: findCancellationDtlsForSAPIntegration ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("STOCKCANCELLATION :: SAPIntegrationDAOImpl :: findCancellationDtlsForSAPIntegration :: End");
		return cancellationDOs;

	}

	public void updateDateTimeAndStatusFlagOfStockCancel(
			SAPStockCancellationDO stockCancellationDO)
			throws CGSystemException {
		logger.debug("STOCKCANCELLATION :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfStockCancel :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_CANCELLATION_DTLS;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = Calendar.getInstance().getTime();
			String dateStamp = df.format(today);
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					stockCancellationDO.getSapStatus());
			qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
			qry.setString(SAPIntegrationConstants.STOCK_CANCELLED_NO,
					stockCancellationDO.getCancellationNumber());
			qry.executeUpdate();
		} catch (Exception e) {
			logger.error(
					"STOCKCANCELLATION :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfStockCancel:: Exception  ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("STOCKCANCELLATION :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfStockCancel :: END");
	}

	@Override
	public boolean saveStockCancelStagingData(
			List<SAPStockCancellationDO> sapStkCancelDOList)
			throws CGSystemException {
		logger.debug("STOCKCANCELLATION :: SAPIntegrationDAOImpl :: saveStockCancelStagingData :: Start");
		boolean isSaved = false;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		Date dateTime = Calendar.getInstance().getTime();
		for (SAPStockCancellationDO stkCancellationDO : sapStkCancelDOList) {
			try {
				getHibernateTemplate().save(stkCancellationDO);

				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time
				stkCancellationDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"
						+ stkCancellationDO.getSapTimestamp());
				stkCancellationDO.setSapStatus("C");
				logger.debug("SAP Status ------>"
						+ stkCancellationDO.getSapStatus());
				updateDateTimeAndStatusFlagOfStockCancel(stkCancellationDO);
			} catch (Exception ex) {
				logger.error(
						"STOCKCANCELLATION :: Exception In :: SAPIntegrationDAOImpl :: Error",
						ex);
				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time

				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(ex.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(ex.getCause().getCause()
							.getMessage());
				}
				if (!StringUtil.isStringEmpty(stkCancellationDO
						.getCancellationNumber())) {
					errorTO.setTransactionNo(stkCancellationDO
							.getCancellationNumber());
				}
				sapErroTOlist.add(errorTO);
				// Date dateTime = Calendar.getInstance().getTime();
				stkCancellationDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"
						+ stkCancellationDO.getSapTimestamp());
				stkCancellationDO.setSapStatus("N");
				logger.debug("SAP Status ------>"
						+ stkCancellationDO.getSapStatus());
				updateDateTimeAndStatusFlagOfStockCancel(stkCancellationDO);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Stock Cancellation Error Records");
		logger.debug("STOCKCANCELLATION :: SAPIntegrationDAOImpl::saveStockCancelStagingData:: End");
		return isSaved;
	}

	@Override
	public void sendSapIntgErrorMail(List<SAPErrorTO> sapErroTOlist,
			String templateMane, String subName) {
		if (!StringUtil.isEmptyList(sapErroTOlist)) {
			MailSenderTO mailTO = new MailSenderTO();
			String[] to = { getEmailIdForErrorRecord() };
			mailTO.setTo(to);
			mailTO.setMailSubject(subName);
			Map<Object, Object> templateVariables = new HashMap<Object, Object>();
			templateVariables.put("sapErroTOlist", sapErroTOlist);
			mailTO.setTemplateName(templateMane);
			mailTO.setTemplateVariables(templateVariables);
			emailSenderUtil.sendEmail(mailTO);
		}
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<CSDSAPCustomerDO>
	 * findContractDtlsForSAPIntegration( String queryName, String[] paramNames,
	 * Object[] paramValues) throws CGSystemException { logger.debug(
	 * "SAPIntegrationDAOImpl : :findContractDtlsForSAPIntegrationFromStagingTable :: Start"
	 * ); List<CSDSAPCustomerDO> custNewDO = null; try { custNewDO =
	 * getHibernateTemplate().findByNamedQueryAndNamedParam( queryName,
	 * logger.error(
	 * "Exception In :: SAPIntegrationDAOImpl :: findContractDtlsForSAPIntegration ::"
	 * , e); throw new CGSystemException(e); } logger.debug(
	 * "SAPIntegrationDAOImpl :: findContractDtlsForSAPIntegrationFromStagingTable :: End"
	 * ); return custNewDO; }
	 */

	private String getEmailIdForErrorRecord() {
		logger.debug("SAPIntegrationDAOImpl :: getEmailIdForErrorRecord :: Start --------> ::::::");
		String paramName = "SAP_INTEGRATION_EMAIL_ID";
		String email = null;
		try {
			email = (String) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							SAPIntegrationConstants.QRY_GET_LIMIT_FOR_BILLING_BATCH,
							SAPIntegrationConstants.PARAM_NAME, paramName).get(
							0);

		} catch (Exception e) {
			logger.error(
					"SAPIntegrationDAOImpl :: getEmailIdForErrorRecord()..:", e);
		}
		logger.debug("SAPIntegrationDAOImpl :: getEmailIdForErrorRecord :: End --------> ::::::");
		return email;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CSDSAPCustomerDO> findContractDtlsForSAPIntegration(
			SAPContractTO sapContractTO, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("CONTRACT :: SAPIntegrationDAOImpl :: findContractDtlsForSAPIntegration() :: START ");
		List<CSDSAPCustomerDO> custNewDO = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_CONTRACT_DTLS_FOR_SAP);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,	sapContractTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			custNewDO = query.list();
		} catch (Exception e) {
			logger.error(
					"CONTRACT :: Exception in SAPIntegrationDAOImpl :: findContractDtlsForSAPIntegration :: ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("CONTRACT :: SAPIntegrationDAOImpl :: findContractDtlsForSAPIntegration :: END ");
		return custNewDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SAPExpenseDO> findExpenseDtlsFromStaging(
			String stagingQueryName, String[] stagingParamNames,
			Object[] stagingParamValues) {
		logger.debug("SAPIntegrationDAOImpl::findExpenseDtlsFromStaging:: start======>");
		List<SAPExpenseDO> sapExpenseDO = null;
		try {
			sapExpenseDO = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(stagingQueryName,
							stagingParamNames, stagingParamValues);
		} catch (Exception e) {
			logger.error(
					"SAPIntegrationDAOImpl :: findExpenseDtlsFromStaging:: Exception  ",
					e);
		}
		logger.debug("SAPIntegrationDAOImpl::findExpenseDtlsFromStaging:: end======>");
		return sapExpenseDO;
	}

	@Override
	public boolean updateDateTimeAndStatusFlagForContarctStaging(
			List<SAPContractDO> sapCntractDOList) throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagForContarctStaging :: Start");
		boolean isUpdated = false;
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_CONTRACT_DTLS;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SAPContractDO sapContractDO : sapCntractDOList) {
				Date today = Calendar.getInstance().getTime();
				String dateStamp = df.format(today);
				Query qry = session.getNamedQuery(queryName);
				qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
						sapContractDO.getSapStatus());
				qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
				qry.setLong(SAPIntegrationConstants.CONTRACT_ID,
						sapContractDO.getId());
				qry.executeUpdate();
				isUpdated = true;
			}
		} catch (Exception e) {
			isUpdated = false;
			logger.error(
					"SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagForContarctStaging:: Exception  ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagForContarctStaging :: END");
		return isUpdated;
	}

	/*
	 * @Override public void
	 * saveLcLiabilityConsgStagingData(List<SAPLiabilityEntriesDO>
	 * sapLiabilityEntriesDOList) throws CGSystemException {
	 * logger.debug("SAPIntegrationDAOImpl::saveLcLiabilityConsgStagingData:: Start"
	 * ); boolean isSaved = false; try{
	 * getHibernateTemplate().saveOrUpdateAll(sapLiabilityEntriesDOList); false;
	 * logger.error(
	 * "SAPIntegrationDAOImpl :: saveLcLiabilityConsgStagingData :: Exception  "
	 * ,e); throw new CGSystemException(e); } logger.debug(
	 * "SAPIntegrationDAOImpl :: saveLcLiabilityConsgStagingData :: End"); }
	 */

	@Override
	public void updateExpenseStagingStatusFlag(List<SAPExpenseDO> sapExpDOList) {
		logger.debug("SAPIntegrationDAOImpl :: UpdateExpenseStagingStatusFlag::=======>Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_EXPENSE_DTLS_IN_STAGING;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SAPExpenseDO sapExpDO : sapExpDOList) {
				Date today = Calendar.getInstance().getTime();
				String dateStamp = df.format(today);
				Query qry = session.getNamedQuery(queryName);
				qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
						sapExpDO.getSapStatus());
				qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
				qry.setString(SAPIntegrationConstants.EXCEPTION,
						sapExpDO.getException());
				qry.setLong(SAPIntegrationConstants.SAP_STAGING_ID,
						sapExpDO.getId());
				qry.executeUpdate();
			}
		} catch (Exception e) {
			logger.error(
					"SAPIntegrationDAOImpl :: UpdateExpenseStagingStatusFlag:: Exception  ",
					e);
		} finally {
			closeSession(session);
		}
		logger.debug("SAPIntegrationDAOImpl :: UpdateExpenseStagingStatusFlag::||||||||=======>END");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpenseDO> getAllExpenseOfficeRHO(String queryName) {
		logger.debug("EXPENSE :: SAPIntegrationDAOImpl :: getAllExpenseOfficeRHO :: start ");
		List<ExpenseDO> expenseDOs = null;
		try {
			expenseDOs = getHibernateTemplate().findByNamedQuery(queryName);
		} catch (Exception e) {
			logger.error("EXPENSE :: Error in :: getAllExpenseOfficeRHO :: ", e);
		}
		logger.debug("EXPENSE :: SAPIntegrationDAOImpl :: getAllExpenseOfficeRHO :: end ");
		return expenseDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SAPCollectionDO> findCollectionDtlsFromStaging(
			String stagingQueryName, String[] stagingParamNames,
			Object[] stagingParamValues) {

		logger.debug("SAPIntegrationDAOImpl::findCollectionDtlsFromStaging:: start======>");
		List<SAPCollectionDO> sapCollnDO = null;
		try {
			sapCollnDO = getHibernateTemplate().findByNamedQueryAndNamedParam(
					stagingQueryName, stagingParamNames, stagingParamValues);
		} catch (Exception e) {
			logger.error(
					"SAPIntegrationDAOImpl::findCollectionDtlsFromStaging:: error======>",
					e);
		}
		logger.debug("SAPIntegrationDAOImpl::findCollectionDtlsFromStaging:: end======>");
		return sapCollnDO;
	}

	@Override
	public void updateCollnStagingStatusFlag(
			List<SAPCollectionDO> sapCollnDOList) {
		logger.debug("COLLECTION :: SAPIntegrationDAOImpl :: UpdateCollnStagingStatusFlag::=======>Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_COLLECTION_DETAILS_IN_STAGING;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SAPCollectionDO sapCollnDO : sapCollnDOList) {
				Date today = Calendar.getInstance().getTime();
				String dateStamp = df.format(today);
				Query qry = session.getNamedQuery(queryName);
				qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
						sapCollnDO.getSapStatus());
				qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
				qry.setString(SAPIntegrationConstants.EXCEPTION,
						sapCollnDO.getException());
				qry.setLong(SAPIntegrationConstants.SAP_STAGING_ID,
						sapCollnDO.getId());
				qry.executeUpdate();
			}
		} catch (Exception e) {
			logger.error(
					"COLLECTION :: SAPIntegrationDAOImpl :: UpdateCollnStagingStatusFlag:: Exception  ",
					e);
		} finally {
			closeSession(session);
		}
		logger.debug("COLLECTION :: SAPIntegrationDAOImpl :: UpdateCollnStagingStatusFlag::=======>END");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockReturnDO> findStockReturnForSAPIntegration(
			SAPStockReturnTO sapStockReturnTO, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("STOCKRETURN :: SAPIntegrationDAOImpl :: findStockReturnForSAPIntegration :: start======>");
		List<StockReturnDO> stkReturnDO = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_GET_STOCK_RETUN_FROM_CSD);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					sapStockReturnTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			stkReturnDO = query.list();
		} catch (Exception e) {
			logger.error(
					"STOCKRETURN :: Exception In :: SAPIntegrationDAOImpl :: findStockReturnForSAPIntegration ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("STOCKRETURN :: SAPIntegrationDAOImpl :: findStockReturnForSAPIntegration :: end======>");
		return stkReturnDO;

	}

	@Override
	public boolean saveStockReturnStagingData(
			List<SAPStockReturnDO> sapStkRetDtlsDOList)
			throws CGSystemException {
		logger.debug("STOCKRETURN :: SAPIntegrationDAOImpl::saveStockReturnStagingData:: start======>");
		boolean isSaved = false;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		Date dateTime = Calendar.getInstance().getTime();
		for (SAPStockReturnDO stockReturnDO : sapStkRetDtlsDOList) {
			try {
				getHibernateTemplate().save(stockReturnDO);

				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time
				stockReturnDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"
						+ stockReturnDO.getSapTimestamp());
				stockReturnDO.setSapStatus("C");
				logger.debug("SAP Status ------>"
						+ stockReturnDO.getSapStatus());
				updateDateTimeAndStatusFlagOfStockReturn(stockReturnDO);
			} catch (Exception ex) {
				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time
				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(ex.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(ex.getCause().getCause()
							.getMessage());
				}
				if (!StringUtil.isStringEmpty(stockReturnDO.getReturnNumber())) {
					errorTO.setTransactionNo(stockReturnDO.getReturnNumber());
				}
				sapErroTOlist.add(errorTO);
				stockReturnDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"
						+ stockReturnDO.getSapTimestamp());
				stockReturnDO.setSapStatus("N");
				logger.debug("SAP Status ------>"
						+ stockReturnDO.getSapStatus());
				updateDateTimeAndStatusFlagOfStockReturn(stockReturnDO);
				logger.error(
						"STOCKRETURN :: Exception In :: SAPIntegrationDAOImpl :: saveStockIssueStagingData ::",
						ex);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Stock Return Error Records");
		logger.debug("STOCKRETURN :: SAPIntegrationDAOImpl::saveStockReturnStagingData:: end======>");
		return isSaved;
	}

	public void updateDateTimeAndStatusFlagOfStockReturn(
			SAPStockReturnDO stockReturnDO) {
		logger.debug("STOCKRETURN :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfStockReturn :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_STOCK_RETUN_CSD;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = Calendar.getInstance().getTime();
			String dateStamp = df.format(today);
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					stockReturnDO.getSapStatus());
			qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
			qry.setString(SAPIntegrationConstants.STOCK_RETURN_NO,
					stockReturnDO.getReturnNumber());
			qry.executeUpdate();
		} catch (Exception e) {
			logger.error(
					"STOCKRETURN :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfStockReturn:: Exception  ",
					e);
		} finally {
			closeSession(session);
		}
		logger.debug("STOCKRETURN :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfStockReturn :: END");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SAPStockReturnDO> findStkReturnFromStaging(
			SAPStockReturnTO sapStockReturnTO, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("STOCKRETURN :: SAPIntegrationDAOImpl :: findStkReturnFromStaging :: start======>");
		List<SAPStockReturnDO> sapStockReturnDOs = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_GET_STOCK_RETUN_FROM_STAGING);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					sapStockReturnTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			sapStockReturnDOs = query.list();
		} catch (Exception e) {
			logger.error(
					"STOCKRETURN :: Exception In :: SAPIntegrationDAOImpl :: findStkReturnFromStaging ::",
					e);
			throw new CGSystemException(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		logger.debug("STOCKRETURN :: SAPIntegrationDAOImpl :: findStkReturnFromStaging :: end======>");
		return sapStockReturnDOs;
	}

	@Override
	public void updateStkReturnStagingStatusFlag(
			List<SAPStockReturnDO> sapStkReturnDOList) {
		logger.debug("STOCKRETURN :: SAPIntegrationDAOImpl :: UpdateExpenseStagingStatusFlag::=======>Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_STOCK_RETUN_IN_STAGING;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SAPStockReturnDO sapStkReturnDO : sapStkReturnDOList) {
				Date today = Calendar.getInstance().getTime();
				String dateStamp = df.format(today);
				Query qry = session.getNamedQuery(queryName);
				qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
						sapStkReturnDO.getSapStatus());
				qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
				qry.setLong(SAPIntegrationConstants.SAP_STAGING_ID,
						sapStkReturnDO.getId());
				qry.setString(SAPIntegrationConstants.EXCEPTION,
						sapStkReturnDO.getException());
				qry.executeUpdate();
			}
		} catch (Exception e) {
			logger.error(
					"STOCKRETURN :: SAPIntegrationDAOImpl :: UpdateExpenseStagingStatusFlag:: Exception  ",
					e);
		} finally {
			closeSession(session);
		}
		logger.debug("STOCKRETURN :: SAPIntegrationDAOImpl :: UpdateExpenseStagingStatusFlag::||||||||=======>END");

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookingDO> findPincodeAgainstConsgNo(String queryName,
			String[] paramNames, Object[] paramValues) {
		logger.debug("SAPIntegrationDAOImpl::findPincodeAgainstConsgNo:: start======>");
		List<BookingDO> bookingDO = null;
		try {
			bookingDO = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, paramNames, paramValues);
		} catch (Exception e) {
			logger.debug(
					"SAPIntegrationDAOImpl::findPincodeAgainstConsgNo:: error::",
					e);
		}
		logger.debug("SAPIntegrationDAOImpl::findPincodeAgainstConsgNo:: end======>");
		return bookingDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	/*
	 * public List<SAPStockRequisitionDO> findStkRequisitionFromStaging(String
	 * stagingQueryName, String[] stagingParamNames,Object[] stagingParamValues)
	 * throws CGSystemException {
	 * logger.debug("SAPIntegrationDAOImpl::findStkRequisitionFromStaging:: start"
	 * ); List<SAPStockRequisitionDO> sapStkRequisitionDO = null; try{
	 * sapStkRequisitionDO =
	 * getHibernateTemplate().findByNamedQueryAndNamedParam
	 * (stagingQueryName,stagingParamNames, stagingParamValues);
	 * "Exception in :: SAPIntegrationDAOImpl :: findStkRequisitionFromStaging"
	 * ); throw new CGSystemException(e); }
	 * logger.debug("SAPIntegrationDAOImpl::findStkRequisitionFromStaging:: end"
	 * ); return sapStkRequisitionDO; }
	 */
	public List<SAPStockRequisitionDO> findStkRequisitionFromStaging(
			SAPStockRequisitionTO stockReqTO, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: findStkRequisitionFromStaging :: start");
		List<SAPStockRequisitionDO> sapStkRequisitionDO = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_GET_STOCK_REQUISITION_FROM_STAGING);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					stockReqTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			sapStkRequisitionDO = query.list();
		} catch (Exception e) {
			logger.error("Exception In :: SAPIntegrationDAOImpl :: getDtls ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("SAPIntegrationDAOImpl::findStkRequisitionFromStaging:: end");
		return sapStkRequisitionDO;
	}

	@Override
	public void updateStkRequisitionStagingStatusFlag(
			List<SAPStockRequisitionDO> sapStkRequrnDOList) {
		logger.debug("STOCKREQUISION :: SAPIntegrationDAOImpl :: updateStkRequisitionStagingStatusFlag :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_STOCK_REQUISITION_IN_STAGING;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SAPStockRequisitionDO sapStkReqDO : sapStkRequrnDOList) {
				Date today = Calendar.getInstance().getTime();
				String dateStamp = df.format(today);
				Query qry = session.getNamedQuery(queryName);
				qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
						sapStkReqDO.getSapStatus());
				qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
				qry.setLong(SAPIntegrationConstants.SAP_STAGING_ID,
						sapStkReqDO.getId());
				qry.setString(SAPIntegrationConstants.EXCEPTION,
						sapStkReqDO.getException());
				qry.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("STOCKREQUISION :: SAPIntegrationDAOImpl :: updateStkRequisitionStagingStatusFlag:: Exception  "
					+ e.getLocalizedMessage());
		} finally {
			closeSession(session);
		}
		logger.debug("STOCKREQUISION :: SAPIntegrationDAOImpl :: updateStkRequisitionStagingStatusFlag :: END");
	}

	@Override
	public boolean saveOrUpdateOfficeDetails(List<OfficeDO> baseDOList)
			throws CGSystemException {
		logger.debug("OFFICE :: SAPIntegrationDAOImpl :: saveOrUpdateOfficeDetails :: start======>");
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		boolean isUpdated = false;
		for (OfficeDO ofcDO : baseDOList) {
			try {
				getHibernateTemplate().merge(ofcDO);
				isUpdated = true;
			} catch (Exception ex) {
				logger.error(
						"Exception In :: SAPIntegrationDAOImpl :: updateEmployeeDetails :: Error",
						ex);
				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(ex.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(ex.getCause().getCause()
							.getMessage());
				}
				if (!StringUtil.isStringEmpty(ofcDO.getOfficeCode())) {
					errorTO.setTransactionNo(ofcDO.getOfficeCode());
				}
				sapErroTOlist.add(errorTO);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Office Error Records");
		logger.debug("OFFICE :: SAPIntegrationDAOImpl :: saveOrUpdateOfficeDetails :: end======>");
		return isUpdated;
	}

	@Override
	public SAPErrorTO saveOrUpdateOfficeDetail(OfficeDO officeDO)
			throws CGSystemException {
		logger.trace("SAPIntegrationDAOImpl :: saveOrUpdateOfficeDetail :: START");
		SAPErrorTO errorTO = null;
		try {
			getHibernateTemplate().saveOrUpdate(officeDO);
		} catch (Exception ex) {
			logger.error(
					"Exception In :: SAPIntegrationDAOImpl :: saveOrUpdateOfficeDetail :: Error",
					ex);
			errorTO = new SAPErrorTO();
			if (!StringUtil
					.isStringEmpty(ex.getCause().getCause().getMessage())) {
				errorTO.setErrorMessage(ex.getCause().getCause().getMessage());
			}
			if (!StringUtil.isStringEmpty(officeDO.getOfficeCode())) {
				errorTO.setTransactionNo(officeDO.getOfficeCode());
			}
		}
		logger.trace("SAPIntegrationDAOImpl :: saveOrUpdateOfficeDetails :: END");
		return errorTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SAPStockIssueDO> findStockIssueDtlsFromStaging(
			SAPStockIssueTO stkissueTO, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl::findStockIssueDtlsFromStaging:: start======>");
		List<SAPStockIssueDO> sapIssueDOList = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_GET_STOCK_ISSUE_FROM_STAGING);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					stkissueTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			sapIssueDOList = query.list();
		} catch (Exception e) {
			logger.error(
					"Exception In :: SAPIntegrationDAOImpl :: findStockIssueDtlsFromStaging ::",
					e);
			throw new CGSystemException(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		logger.debug("SAPIntegrationDAOImpl::findStockIssueDtlsFromStaging:: end======>");
		return sapIssueDOList;
	}

	@Override
	public void updateStockIssueStagingStatusFlag(
			List<SAPStockIssueDO> sapStkIssueDOList) {
		logger.debug("STOCKISSUE :: SAPIntegrationDAOImpl :: updateStockIssueStagingStatusFlag :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_STOCK_ISSUE_IN_STAGING;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SAPStockIssueDO sapStkIssueDO : sapStkIssueDOList) {
				Date today = Calendar.getInstance().getTime();
				String dateStamp = df.format(today);
				Query qry = session.getNamedQuery(queryName);
				qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
						sapStkIssueDO.getSapStatus());
				qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
				qry.setLong(SAPIntegrationConstants.SAP_STAGING_ID,
						sapStkIssueDO.getId());
				qry.setString(SAPIntegrationConstants.EXCEPTION,
						sapStkIssueDO.getException());
				qry.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("STOCKISSUE :: SAPIntegrationDAOImpl :: updateStockIssueStagingStatusFlag:: Exception  "
					+ e.getLocalizedMessage());
		} finally {
			closeSession(session);
		}
		logger.debug("STOCKISSUE :: SAPIntegrationDAOImpl :: updateStockIssueStagingStatusFlag :: END");
	}

	@Override
	public void updatePlantMasterCSDInboundStatus(List<CGBaseDO> sapOfficeDOs,
			String error) {

		logger.debug("SAPIntegrationDAOImpl :: updatePlantMasterCSDInboundStatus :: Start");
		/*
		 * List<CGBaseDO> sapOfcDOList = new ArrayList<>(); SAPOfficeDO sod =
		 * null; for(CGBaseDO sapOfcDO : sapOfficeDOs){ sod = (SAPOfficeDO)
		 * sapOfcDO; SAPOfficeDO sapOffcDO = new SAPOfficeDO();
		 * sapOffcDO.setId(sod.getId());
		 * logger.debug("SAP Office Id ------>"+sapOffcDO.getId()); Date
		 * dateTime = Calendar.getInstance().getTime();
		 * sapOffcDO.setSapTimestamp(dateTime);
		 * logger.debug("Date Stamp ------>"+sapOffcDO.getSapTimestamp());
		 * if(!StringUtil.isStringEmpty(error)){
		 * //sapOffcDO.setSapStatusInBound("N");
		 * logger.debug("SAP Inbound ------>"+sapOffcDO.getSapStatusInBound());
		 * sapOffcDO.setIsError("Y");
		 * logger.debug("Is Err  ------>"+sapOffcDO.getIsError());
		 * sapOffcDO.setErrorDesc(error);
		 * logger.debug("Err Desc  ------>"+sapOffcDO.getErrorDesc()); }else{
		 * //sapOffcDO.setSapStatusInBound("C");
		 * logger.debug("SAP Inbound ------>"+sapOffcDO.getSapStatusInBound());
		 * sapOffcDO.setIsError("N");
		 * logger.debug("Is Err  ------>"+sapOffcDO.getIsError());
		 * //sapOffcDO.setErrorDesc(error); } sapOfcDOList.add(sapOffcDO); } try
		 * { Session session = createSession(); String queryName =
		 * SAPIntegrationConstants.QRY_PARAM_UPDATE_OFC_STATUS; DateFormat df =
		 * new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); SAPOfficeDO sapoffcDO =
		 * null; for(CGBaseDO sapOfccDO : sapOfcDOList){ sapoffcDO =
		 * (SAPOfficeDO)sapOfccDO; Date today =
		 * Calendar.getInstance().getTime(); String dateStamp =
		 * df.format(today); Query qry = session.getNamedQuery(queryName);
		 * qry.setString(SAPIntegrationConstants.DT_SAP_INBOUND,
		 * sapoffcDO.getSapStatusInBound());
		 * qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
		 * qry.setString(SAPIntegrationConstants.IS_ERROR,
		 * sapoffcDO.getIsError());
		 * if(!StringUtil.isStringEmpty(sapoffcDO.getErrorDesc())){
		 * qry.setString(SAPIntegrationConstants.ERR_DESC,
		 * sapoffcDO.getErrorDesc()); }
		 * qry.setLong(SAPIntegrationConstants.SAP_STAGING_ID,
		 * sapoffcDO.getId()); qry.executeUpdate(); } } catch (Exception e) {
		 * "SAPIntegrationDAOImpl :: updateStockIssueStagingStatusFlag:: Exception  "
		 * +e.getLocalizedMessage()); }
		 */

		logger.debug("SAPIntegrationDAOImpl :: updatePlantMasterCSDInboundStatus :: End");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SAPStockReceiptDO> findStkReceiptFromStaging(
			SAPStockReceiptTO receiptTO, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("STOCKACKNOWLEDGEMENT :: SAPIntegrationDAOImpl :: findStkReceiptFromStaging :: start");
		List<SAPStockReceiptDO> sapStockReceiptDOList = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_GET_STOCK_RECEIPT_FROM_STAGING);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					receiptTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			sapStockReceiptDOList = query.list();
		} catch (Exception e) {
			logger.error(
					"STOCKACKNOWLEDGEMENT :: Exception In :: SAPIntegrationDAOImpl :: findStkReceiptFromStaging ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("STOCKACKNOWLEDGEMENT :: SAPIntegrationDAOImpl::findStkReceiptFromStaging:: end");
		return sapStockReceiptDOList;
	}

	@Override
	public void updateStockReceiptStagingStatusFlag(
			List<SAPStockReceiptDO> sapStkReceiptDOList) {
		logger.debug("STOCKACKNOWLEDGEMENT :: SAPIntegrationDAOImpl :: updateStockReceiptStagingStatusFlag :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_STOCK_RECEIPT_IN_STAGING;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SAPStockReceiptDO sapStkReceiptDO : sapStkReceiptDOList) {
				Date today = Calendar.getInstance().getTime();
				String dateStamp = df.format(today);
				Query qry = session.getNamedQuery(queryName);
				qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
						sapStkReceiptDO.getSapStatus());
				qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
				qry.setLong(SAPIntegrationConstants.SAP_STAGING_ID,
						sapStkReceiptDO.getId());
				qry.setString(SAPIntegrationConstants.EXCEPTION,
						sapStkReceiptDO.getException());
				qry.executeUpdate();
			}
		} catch (Exception e) {
			logger.error(
					"STOCKACKNOWLEDGEMENT :: SAPIntegrationDAOImpl :: updateStockReceiptStagingStatusFlag:: Exception  ",
					e);
		} finally {
			closeSession(session);
		}
		logger.debug("STOCKACKNOWLEDGEMENT :: SAPIntegrationDAOImpl :: updateStockReceiptStagingStatusFlag :: END");

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SAPStockCancellationDO> findStkCancelFromStaging(
			String stagingQueryName, String[] stagingParamNames,
			Object[] stagingParamValues) {
		logger.debug("SAPIntegrationDAOImpl::findStkCancelFromStaging:: Start");
		List<SAPStockCancellationDO> sapStockCancelDOList = null;
		try {
			sapStockCancelDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(stagingQueryName,
							stagingParamNames, stagingParamValues);
		} catch (Exception e) {
			logger.error(
					"SAPIntegrationDAOImpl::findStkCancelFromStaging:: error",
					e);
		}
		logger.debug("SAPIntegrationDAOImpl::findStkCancelFromStaging:: End");
		return sapStockCancelDOList;
	}

	@Override
	public void updateStockCancelStagingStatusFlag(
			List<SAPStockCancellationDO> sapStkCancelDOList) {
		logger.debug("STOCKCANCELLATION :: SAPIntegrationDAOImpl :: updateStockCancelStagingStatusFlag :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_STOCK_CANCEL_IN_STAGING;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SAPStockCancellationDO sapStkCancelDO : sapStkCancelDOList) {
				Date today = Calendar.getInstance().getTime();
				String dateStamp = df.format(today);
				Query qry = session.getNamedQuery(queryName);
				qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
						sapStkCancelDO.getSapStatus());
				qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
				qry.setString(SAPIntegrationConstants.EXCEPTION,
						sapStkCancelDO.getException());
				qry.setLong(SAPIntegrationConstants.SAP_STAGING_ID,
						sapStkCancelDO.getId());
				qry.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("STOCKCANCELLATION :: SAPIntegrationDAOImpl :: updateStockCancelStagingStatusFlag:: Exception  "
					+ e.getLocalizedMessage());
		} finally {
			closeSession(session);
		}
		logger.debug("STOCKCANCELLATION :: SAPIntegrationDAOImpl :: updateStockCancelStagingStatusFlag :: END");
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<SAPLiabilityPaymentDO>
	 * findLiabilityPaymentDtlsFromStaging( String stagingQueryName, String[]
	 * stagingParamNames, Object[] stagingParamValues) { logger.debug(
	 * "SAPIntegrationDAOImpl :: findLiabilityPaymentDtlsFromStaging :: Start");
	 * List<SAPLiabilityPaymentDO> sapLiabilityPaymentDOList = null; try {
	 * sapLiabilityPaymentDOList = getHibernateTemplate()
	 * .findByNamedQueryAndNamedParam(stagingQueryName, stagingParamNames,
	 * logger
	 * .debug("SAPIntegrationDAOImpl :: findLiabilityPaymentDtlsFromStaging :: End"
	 * ); return sapLiabilityPaymentDOList; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<SAPLiabilityPaymentDO> findLiabilityPaymentDtlsFromStaging(
			SAPLiabilityPaymentTO liabilityPaytTO, Long maxDataCountLimit) {
		logger.debug("LIABILITY PAYMENT :: SAPIntegrationDAOImpl :: findLiabilityPaymentDtlsFromStaging :: Start");
		List<SAPLiabilityPaymentDO> sapLiabilityPaymentDOList = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = createSession();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_GET_LIABILITY_DETAILS_FROM_STAGING);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					liabilityPaytTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			sapLiabilityPaymentDOList = query.list();
		} catch (Exception e) {
			logger.error(
					"LIABILITY PAYMENT :: SAPIntegrationDAOImpl :: findLiabilityPaymentDtlsFromStaging :: Exception  ",
					e);
		} finally {
			session.close();
		}
		logger.debug("LIABILITY PAYMENT :: SAPIntegrationDAOImpl :: findLiabilityPaymentDtlsFromStaging :: End");
		return sapLiabilityPaymentDOList;
	}

	/*
	 * @Override public void updateLiabilityPaymentStagingStatusFlag(
	 * List<SAPLiabilityPaymentDO> sapLiabilityPayDOList) { logger.debug(
	 * "SAPIntegrationDAOImpl :: updateLiabilityPaymentStagingStatusFlag :: Start"
	 * ); Session session = null; try { session = createSession(); String
	 * queryName =
	 * SAPIntegrationConstants.QRY_PARAM_UPDATE_LIABILITY_PAYMENT_IN_STAGING;
	 * DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); for
	 * (SAPLiabilityPaymentDO sapLiabilityPaymentDO : sapLiabilityPayDOList) {
	 * Date today = Calendar.getInstance().getTime(); String dateStamp =
	 * df.format(today); Query qry = session.getNamedQuery(queryName);
	 * qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
	 * sapLiabilityPaymentDO.getSapStatus());
	 * qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
	 * qry.setLong(SAPIntegrationConstants.SAP_STAGING_ID,
	 * sapLiabilityPaymentDO.getId()); qry.executeUpdate(); } } catch (Exception
	 * "SAPIntegrationDAOImpl :: updateLiabilityPaymentStagingStatusFlag :: Exception  "
	 * + e.getLocalizedMessage()); } finally { closeSession(session); }
	 * logger.debug
	 * ("SAPIntegrationDAOImpl :: updateLiabilityPaymentStagingStatusFlag :: END"
	 * );
	 * 
	 * }
	 */

	@Override
	public void updateLiabilityPaymentStagingStatusFlag(
			List<SAPLiabilityPaymentDO> sapLiabilityPayDOList)
			throws CGSystemException {
		logger.debug("LIABILITY PAYMENT :: SAPIntegrationDAOImpl :: updateLiabilityPaymentStagingStatusFlag :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_LIABILITY_PAYMENT_IN_STAGING;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SAPLiabilityPaymentDO sapLiabilityPaymentDO : sapLiabilityPayDOList) {
				Date today = Calendar.getInstance().getTime();
				String dateStamp = df.format(today);
				Query qry = session.getNamedQuery(queryName);
				qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
						sapLiabilityPaymentDO.getSapStatus());
				qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
				qry.setString(SAPIntegrationConstants.EXCEPTION,
						sapLiabilityPaymentDO.getException());
				qry.setLong(SAPIntegrationConstants.SAP_STAGING_ID,
						sapLiabilityPaymentDO.getId());
				qry.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("LIABILITY PAYMENT :: SAPIntegrationDAOImpl :: updateLiabilityPaymentStagingStatusFlag :: Exception  "
					+ e.getLocalizedMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("LIABILITY PAYMENT :: SAPIntegrationDAOImpl :: updateLiabilityPaymentStagingStatusFlag :: END");

	}

	/*
	 * @Override public boolean saveDetailsForMaterial(List<CGBaseDO>
	 * materialDOs) {
	 * logger.debug("SAPIntegrationDAOImpl::saveDetailsForMaterial:: start======>"
	 * ); boolean isSaved = false; for (CGBaseDO itemDO : materialDOs) {
	 * getHibernateTemplate().merge(itemDO); } isSaved = true;
	 * logger.debug("SAPIntegrationDAOImpl::saveDetailsForMaterial:: end======>"
	 * ); return isSaved; }
	 */

	@Override
	public boolean saveDetailsForMaterial(List<CSDSAPItemDO> materialDOs) {
		logger.debug("ITEM :: SAPIntegrationDAOImpl::saveDetailsForMaterial:: start======>");
		boolean isSaved = false;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		for (CSDSAPItemDO itemDO : materialDOs) {
			try {
				getHibernateTemplate().merge(itemDO);
			} catch (Exception ex) {
				logger.error(
						"ITEM :: STOCKCANCELLATION :: Exception In :: SAPIntegrationDAOImpl :: Error",
						ex);
				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time
				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(ex.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(ex.getCause().getCause()
							.getMessage());
				}
				if (!StringUtil.isStringEmpty(itemDO.getItemCode())) {
					errorTO.setTransactionNo(itemDO.getItemCode());
				}
				sapErroTOlist.add(errorTO);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Material Master Error Records");
		isSaved = true;
		logger.debug("ITEM :: SAPIntegrationDAOImpl::saveDetailsForMaterial:: end======>");
		return isSaved;
	}

	@Override
	public boolean saveContractStagingData(List<SAPContractDO> sapContractDOList) {
		logger.debug("CONTRACT :: SAPIntegrationDAOImpl :: saveContractStagingData() :: START ======> ");
		boolean isSaved = false;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		// String emailID = null;
		Date dateTime = Calendar.getInstance().getTime();
		for (SAPContractDO contractDO : sapContractDOList) {
			try {
				getHibernateTemplate().save(contractDO);
				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time
				contractDO.setSapTimestamp(dateTime);
				contractDO.setSapStatus("C");
				updateDateTimeAndStatusFlagOfContract(contractDO);
			} catch (Exception ex) {
				logger.error(
						"CONTRACT :: Exception In :: SAPIntegrationDAOImpl :: Error",
						ex);
				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time
				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(ex.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(ex.getCause().getCause()
							.getMessage());
				}
				if (!StringUtil.isStringEmpty(contractDO.getContractNo())) {
					errorTO.setTransactionNo(contractDO.getContractNo());
				}
				sapErroTOlist.add(errorTO);
				contractDO.setSapTimestamp(dateTime);
				contractDO.setSapStatus("N");
				updateDateTimeAndStatusFlagOfContract(contractDO);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Contract Error Records");
		logger.debug("CONTRACT :: SAPIntegrationDAOImpl :: saveContractStagingData() :: END ======> ");
		return isSaved;
	}

	public void updateDateTimeAndStatusFlagOfContract(SAPContractDO custDOList) {
		logger.debug("CONTRACT :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfContract::=======>Start");
		boolean isUpdated = false;
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_CONTRACT_DTLS;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = Calendar.getInstance().getTime();
			String dateStamp = df.format(today);
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					custDOList.getSapStatus());
			qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
			qry.setString(SAPIntegrationConstants.CONTRACT_NO,
					custDOList.getContractNo());
			qry.executeUpdate();
		} catch (Exception e) {
			logger.error("CONTRACT :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfContract:: Exception  "
					+ e.getLocalizedMessage());
		} finally {
			closeSession(session);
		}
		logger.debug("CONTRACT :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfContract::=======>END");
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<SAPContractDO> findContractDtlsFromStaging( String
	 * stagingQueryName, String[] stagingParamNames, Object[]
	 * stagingParamValues) { logger.debug(
	 * "SAPIntegrationDAOImpl :: findContractDtlsFromStaging :: start======>");
	 * List<SAPContractDO> sapContractDO = null; try { sapContractDO =
	 * getHibernateTemplate() .findByNamedQueryAndNamedParam(stagingQueryName,
	 * stagingParamNames, stagingParamValues); } catch (Exception e) {
	 * "SAPIntegrationDAOImpl :: findContractDtlsFromStaging :: end======>");
	 * return sapContractDO; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<SAPContractDO> findContractDtlsFromStaging(
			SAPContractTO sapContractTO, Long maxDataCountLimit) {
		logger.debug("CONTRACT :: SAPIntegrationDAOImpl :: findContractDtlsFromStaging :: start======>");
		List<SAPContractDO> sapContractDO = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = createSession();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_GET_CONTRACT_DETAILS_FROM_STAGING);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					sapContractTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			sapContractDO = query.list();
		} catch (Exception e) {
			logger.error(
					"CONTRACT :: Exception in :: SAPIntegrationDAOImpl :: findContractDtlsFromStaging :: ",
					e);
		} finally {
			closeSession(session);
		}
		logger.debug("CONTRACT :: SAPIntegrationDAOImpl :: findContractDtlsFromStaging :: end======>");
		return sapContractDO;
	}

	/*
	 * @Override public void updateContractStagingStatusFlag(
	 * List<SAPContractDO> sapContractDOList) { logger.debug(
	 * "SAPIntegrationDAOImpl :: updateContractStagingStatusFlag :: Start");
	 * Session session = null; try { session = createSession(); String queryName
	 * = SAPIntegrationConstants.QRY_PARAM_UPDATE_CONTRACT_IN_STAGING;
	 * DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); for
	 * (SAPContractDO sapContractDO : sapContractDOList) { Date today =
	 * Calendar.getInstance().getTime(); String dateStamp = df.format(today);
	 * Query qry = session.getNamedQuery(queryName);
	 * qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
	 * sapContractDO.getSapStatus());
	 * qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
	 * qry.setLong(SAPIntegrationConstants.SAP_STAGING_ID,
	 * sapContractDO.getId()); qry.executeUpdate(); } } catch (Exception e) {
	 * "SAPIntegrationDAOImpl :: updateContractStagingStatusFlag:: Exception  "
	 * + e.getLocalizedMessage()); } finally { closeSession(session); }
	 * logger.debug
	 * ("SAPIntegrationDAOImpl :: updateContractStagingStatusFlag :: END"); }
	 */

	@Override
	public void updateContractStagingStatusFlag(
			List<SAPContractDO> sapContractDOList) {
		logger.debug("CONTRACT :: SAPIntegrationDAOImpl :: updateContractStagingStatusFlag :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_CONTRACT_IN_STAGING;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SAPContractDO sapContractDO : sapContractDOList) {
				Date today = Calendar.getInstance().getTime();
				String dateStamp = df.format(today);
				Query qry = session.getNamedQuery(queryName);
				qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
						sapContractDO.getSapStatus());
				qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
				qry.setString(SAPIntegrationConstants.EXCEPTION,
						sapContractDO.getException());
				qry.setLong(SAPIntegrationConstants.SAP_STAGING_ID,
						sapContractDO.getId());
				qry.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("CONTRACT :: SAPIntegrationDAOImpl :: updateContractStagingStatusFlag:: Exception  "
					+ e.getLocalizedMessage());
		} finally {
			closeSession(session);
		}
		logger.debug("CONTRACT :: SAPIntegrationDAOImpl :: updateContractStagingStatusFlag :: END");
	}

	@Override
	public void updateMaterialStagingStatus(String sapStatus,
			List<CGBaseDO> baseDOList) {
		logger.debug("SAPIntegrationDAOImpl :: updateMaterialStagingStatus :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_MATERIAL_STATUS;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SAPItemDO sapItemDO = null;
			// SAPOfficeDO sapoffcDO = null;
			for (CGBaseDO sapMaterialDO : baseDOList) {
				// sapoffcDO = (SAPOfficeDO)sapMaterialDO;
				sapItemDO = (SAPItemDO) sapMaterialDO;
				Date today = Calendar.getInstance().getTime();
				String dateStamp = df.format(today);
				Query qry = session.getNamedQuery(queryName);
				qry.setString(SAPIntegrationConstants.DT_SAP_INBOUND, sapStatus);
				qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
				// qry.setString(SAPIntegrationConstants.IS_ERROR,
				// sapoffcDO.getIsError());
				/*
				 * if(!StringUtil.isStringEmpty(sapoffcDO.getErrorDesc())){
				 * qry.setString(SAPIntegrationConstants.ERR_DESC,
				 * sapoffcDO.getErrorDesc()); }
				 */
				qry.setLong(SAPIntegrationConstants.SAP_STAGING_ID,
						sapItemDO.getId());
				qry.executeUpdate();
			}
		} catch (Exception e) {
			logger.error(
					"SAPIntegrationDAOImpl :: updateStockIssueStagingStatusFlag:: Exception  ",
					e);
		} finally {
			closeSession(session);
		}
		logger.debug("SAPIntegrationDAOImpl :: updateMaterialStagingStatus :: End");
	}

	@Override
	public boolean saveDetailsFOrCustomerNewUAT(List<CGBaseDO> baseDOList) {
		logger.debug("SAPIntegrationDAOImpl::saveDetailsFOrCustomerNewUAT:: start======>");
		boolean isSaved = false;
		for (CGBaseDO customerDO : baseDOList) {
			getHibernateTemplate().merge(customerDO);
		}
		isSaved = true;
		logger.debug("SAPIntegrationDAOImpl::saveDetailsFOrCustomerNewUAT:: end======>");
		return isSaved;
	}

	/*
	 * @Override public boolean saveDetailsForCustNew(List<CGBaseDO> custDO) {
	 * logger
	 * .debug("SAPIntegrationDAOImpl :: saveDetailsForCustNew :: start======>");
	 * boolean isSaved = false; for (CGBaseDO customerDO : custDO) {
	 * getHibernateTemplate().merge(customerDO); } isSaved = true;
	 * logger.debug("SAPIntegrationDAOImpl :: saveDetailsForCustNew :: end======>"
	 * ); return isSaved; }
	 */

	@Override
	public boolean saveDetailsForCustNew(List<CSDSAPCustomerDO> custDO) {
		logger.debug("SAPIntegrationDAOImpl :: saveDetailsForCustNew :: start======>");
		boolean isSaved = false;
		// for (CGBaseDO customerDO : custDO) {
		getHibernateTemplate().saveOrUpdateAll(custDO);
		isSaved = true;
		logger.debug("SAPIntegrationDAOImpl :: saveDetailsForCustNew :: end======>");
		return isSaved;
	}


	@Override
	public boolean updateDetailsForCustNew(CSDSAPCustomerDO updateBACustDO) throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: updateDetailsForCustNew :: START");
		boolean isSaved = false;
		Session session = null;
		try {
			session = openTransactionalSession();
			session.saveOrUpdate(updateBACustDO);
			isSaved = true;
		} catch (Exception ex) {
			logger.error("Exception In :: SAPIntegrationDAOImpl :: updateDetailsForCustNew :: ERROR", ex);
			throw new CGSystemException(ex);
		}finally{
			closeTransactionalSession(session);
		}
	
		logger.debug("SAPIntegrationDAOImpl :: updateDetailsForCustNew :: END");
		return isSaved;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockTransferDO> findStockTransferForSAPIntegration(
			SAPStockTransferTO sapStkRetTo, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("STOCKTRANSFER :: SAPIntegrationDAOImpl::findStockTransferForSAPIntegration:: start======>");
		List<StockTransferDO> stkTransferDO = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_GET_STOCK_TRANSFER_FROM_CSD);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					sapStkRetTo.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			stkTransferDO = query.list();
		} catch (Exception e) {
			logger.error(
					"STOCKTRANSFER :: Exception In :: SAPIntegrationDAOImpl :: findStockTransferForSAPIntegration ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("STOCKTRANSFER :: SAPIntegrationDAOImpl::findStockTransferForSAPIntegration:: end======>");
		return stkTransferDO;
	}

	@Override
	public boolean saveStockTransferStagingData(
			List<SAPStockTransferDO> sapStkTransferDtlsDOList) {
		logger.debug("STOCKTRANSFER :: SAPIntegrationDAOImpl :: saveStockTransferStagingData :: start======>");
		boolean isSaved = false;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		Date dateTime = Calendar.getInstance().getTime();
		for (SAPStockTransferDO stockTransferDO : sapStkTransferDtlsDOList) {
			try {
				getHibernateTemplate().save(stockTransferDO);
				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time

				stockTransferDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"
						+ stockTransferDO.getSapTimestamp());
				stockTransferDO.setSapStatus("C");
				logger.debug("SAP Status ------>"
						+ stockTransferDO.getSapStatus());
				updateDateTimeAndStatusFlagOfStockTransfer(stockTransferDO);
			} catch (Exception ex) {
				logger.error(
						"STOCKCANCELLATION :: Exception In :: SAPIntegrationDAOImpl :: Error",
						ex);

				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time

				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(ex.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(ex.getCause().getCause()
							.getMessage());
				}
				if (!StringUtil
						.isStringEmpty(stockTransferDO.getReturnNumber())) {
					errorTO.setTransactionNo(stockTransferDO.getReturnNumber());
				}
				sapErroTOlist.add(errorTO);
				stockTransferDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"
						+ stockTransferDO.getSapTimestamp());
				stockTransferDO.setSapStatus("N");
				logger.debug("SAP Status ------>"
						+ stockTransferDO.getSapStatus());
				updateDateTimeAndStatusFlagOfStockTransfer(stockTransferDO);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Stock Transfer To BA Error Records");
		logger.debug("STOCKTRANSFER :: SAPIntegrationDAOImpl :: saveStockTransferStagingData :: end======>");
		return isSaved;
	}

	public void updateDateTimeAndStatusFlagOfStockTransfer(
			SAPStockTransferDO stockTransferDO) {

		logger.debug("STOCKTRANSFER :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfStockTransfer::=======>Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_TRANSFER_DTLS;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = Calendar.getInstance().getTime();
			String dateStamp = df.format(today);
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					stockTransferDO.getSapStatus());
			qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
			qry.setString(SAPIntegrationConstants.STOCK_TRANSFER_NO,
					stockTransferDO.getReturnNumber());
			qry.executeUpdate();
		} catch (Exception e) {
			logger.error("STOCKTRANSFER :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfStockTransfer :: Exception  "
					+ e.getLocalizedMessage());
		} finally {
			closeSession(session);
		}
		logger.debug("STOCKTRANSFER :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfStockTransfer :: =======>END");

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SAPStockTransferDO> findStkTransferFromStaging(
			SAPStockTransferTO sapStkRetTo, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("STOCKTRANSFER :: SAPIntegrationDAOImpl :: findStkTransferFromStaging :: start");
		List<SAPStockTransferDO> sapStkTransferDOs = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_GET_STOCK_TRANSFER_FROM_STAGING);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					sapStkRetTo.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			sapStkTransferDOs = query.list();
		} catch (Exception e) {
			logger.error(
					"STOCKTRANSFER :: Exception In :: SAPIntegrationDAOImpl :: findStkTransferFromStaging ::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		logger.debug("STOCKTRANSFER :: SAPIntegrationDAOImpl :: findStkTransferFromStaging :: end======>");
		return sapStkTransferDOs;
	}

	@Override
	public void updateStkTransferStagingStatusFlag(
			List<SAPStockTransferDO> sapStkReturnDOList) {
		logger.debug("STOCKTRANSFER :: SAPIntegrationDAOImpl :: updateStkTransferStagingStatusFlag :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_STOCK_TRANSFER_IN_STAGING;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SAPStockTransferDO sapStkCancelDO : sapStkReturnDOList) {
				Date today = Calendar.getInstance().getTime();
				String dateStamp = df.format(today);
				Query qry = session.getNamedQuery(queryName);
				qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
						sapStkCancelDO.getSapStatus());
				qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
				qry.setString(SAPIntegrationConstants.EXCEPTION,
						sapStkCancelDO.getException());
				qry.setLong(SAPIntegrationConstants.SAP_STAGING_ID,
						sapStkCancelDO.getId());
				qry.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("STOCKTRANSFER :: SAPIntegrationDAOImpl :: updateStkTransferStagingStatusFlag:: Exception  "
					+ e.getLocalizedMessage());
		} finally {
			closeSession(session);
		}
		logger.debug("STOCKTRANSFER :: SAPIntegrationDAOImpl :: updateStkTransferStagingStatusFlag :: END");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BillingConsignmentSummaryDO> getBillingConsgSummaryDtls(
			String queryName, String[] paramNames, Object[] paramValues) {
		logger.debug("SAPIntegrationDAOImpl :: getBillingConsgSummaryDtls :: start");
		List<BillingConsignmentSummaryDO> billingConsgSummaryDOs = null;
		try {
			billingConsgSummaryDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, paramNames,
							paramValues);
		} catch (Exception e) {
			logger.error(
					"SAPIntegrationDAOImpl :: getBillingConsgSummaryDtls :: error",
					e);
		}
		logger.debug("SAPIntegrationDAOImpl :: getBillingConsgSummaryDtls :: end");
		return billingConsgSummaryDOs;
	}

	@Override
	public boolean saveDetailsForVendor(List<CGBaseDO> vendorDOs) {
		logger.debug("SAPIntegrationDAOImpl::saveDetailsForVendor:: start======>");
		boolean isSaved = false;
		for (CGBaseDO vendorDO : vendorDOs) {
			getHibernateTemplate().merge(vendorDO);
		}
		isSaved = true;
		logger.debug("SAPIntegrationDAOImpl::saveDetailsForVendor:: end======>");
		return isSaved;
	}

	@Override
	public boolean saveBillingConsgSummaryStagingData(
			List<SAPBillingConsignmentSummaryDO> sapBillConsignmentSummaryDOs) {
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: saveBillingConsgSummaryStagingData :: start======>");
		boolean isSaved = false;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		String emailID = null;
		Date dateTime = Calendar.getInstance().getTime();
		for (SAPBillingConsignmentSummaryDO billConsgSummaryDO : sapBillConsignmentSummaryDOs) {
			try {
				getHibernateTemplate().save(billConsgSummaryDO);

				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time

				billConsgSummaryDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"
						+ billConsgSummaryDO.getSapTimestamp());
				billConsgSummaryDO.setSapStatus("Y");
				logger.debug("SAP Status ------>"
						+ billConsgSummaryDO.getSapStatus());
				updateDateTimeAndStatusFlagForBCS(billConsgSummaryDO);
			} catch (Exception ex) {
				logger.error(
						"STOCKCANCELLATION :: Exception In :: SAPIntegrationDAOImpl :: Error",
						ex);

				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time

				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(ex.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(ex.getCause().getCause()
							.getMessage());
				}
				if (!StringUtil.isEmptyInteger(billConsgSummaryDO
						.getTransactionNumber())) {
					errorTO.setTransactionNo(billConsgSummaryDO
							.getTransactionNumber().toString());
				}
				sapErroTOlist.add(errorTO);
				billConsgSummaryDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"
						+ billConsgSummaryDO.getSapTimestamp());
				billConsgSummaryDO.setSapStatus("N");
				logger.debug("SAP Status ------>"
						+ billConsgSummaryDO.getSapStatus());
				updateDateTimeAndStatusFlagForBCS(billConsgSummaryDO);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Billing Consignment Summary Error Records");
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: saveBillingConsgSummaryStagingData :: end======>");
		return isSaved;
	}

	public void updateDateTimeAndStatusFlagForBCS(
			SAPBillingConsignmentSummaryDO bcsList) {
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagForBCS :: =======>Start");
		boolean isUpdated = false;
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_BCS_DTLS_FOR_SAP;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = Calendar.getInstance().getTime();
			String dateStamp = df.format(today);
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.TRANSFER_STATUS,
					bcsList.getSapStatus());
			qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
			qry.setLong(SAPIntegrationConstants.BCS_ID,
					bcsList.getTransactionNumber());
			qry.executeUpdate();
		} catch (Exception e) {
			logger.error(
					"BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagForBCS :: Exception  ",
					e);
		} finally {
			closeSession(session);
		}
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagForBCS :: ||||||||=======>END");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SAPBillingConsignmentSummaryDO> findBCSDtlsFromStaging(
			SAPBillingConsgSummaryTO sapBillConsgSummaryTO,
			Long maxDataCountLimit) throws CGSystemException {
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: findBCSDtlsFromStaging :: start");
		List<SAPBillingConsignmentSummaryDO> sapBCSDO = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_GET_BCS_FROM_STAGING);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					sapBillConsgSummaryTO.getSapStatus());
			/*query.setParameter("startDate", fromDate);
			query.setParameter("endDate", toDate);*/
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			sapBCSDO = query.list();
		} catch (Exception e) {
			logger.error(
					"BillingSummaryCreationScheduler - for Summary scheduler :: Exception In :: SAPIntegrationDAOImpl :: findBCSDtlsFromStaging ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: findBCSDtlsFromStaging :: end");
		return sapBCSDO;
	}

	@Override
	public void updateBCSStagingStatusFlag(
			List<SAPBillingConsignmentSummaryDO> sapBCSDOList) {
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: updateBCSStagingStatusFlag :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_BCS_IN_STAGING;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SAPBillingConsignmentSummaryDO sapBCSDO : sapBCSDOList) {
				Date today = Calendar.getInstance().getTime();
				String dateStamp = df.format(today);
				Query qry = session.getNamedQuery(queryName);
				qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
						sapBCSDO.getSapStatus());
				qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
				qry.setString(SAPIntegrationConstants.EXCEPTION,
						sapBCSDO.getException());
				qry.setLong(SAPIntegrationConstants.SAP_STAGING_ID,
						sapBCSDO.getId());
				qry.executeUpdate();
			}
		} catch (Exception e) {
			logger.error(
					"BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: updateBCSStagingStatusFlag :: Exception  ",
					e);
		} finally {
			closeSession(session);
		}
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: updateBCSStagingStatusFlag :: END");

	}

	@Override
	public Long getSalesOrderInterfaceDtls(
			SAPBillingConsgSummaryTO sapBillConsgSummaryTO)
			throws ParseException, CGSystemException {
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: getSalesOrderInterfaceDtls :: Start");
		Session session = null;
		List<Long> countLong = null;
		Long count = 0L;
		try {
			String sql = "SELECT count(1) as recordCount FROM ff_f_billing_consignment_summary summary  WHERE summary.TRANSFER_STATUS = 'N'";
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createSQLQuery(sql).addScalar("recordCount", LongType.INSTANCE);
			//List<SalesOrderInterfaceDO> salesOrderDtls = query.list();
			countLong=query.list();
			
			if(!CGCollectionUtils.isEmpty(countLong) && countLong.get(0) != null && countLong.get(0).longValue() >0L){
				count=countLong.get(0).longValue();
			}
			session.close();
			// soDOList = prepareSODtlsList(salesOrderDtls);
		} catch (Exception e) {
			logger.error(
					"BillingSummaryCreationScheduler - for Summary scheduler ::  Exception in :: SAPIntegrationDAOImpl :: getSalesOrderInterfaceDtls",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: getSalesOrderInterfaceDtls :: End");
		return count;
	}

	@Override
	public List<SalesOrderInterfaceDO> getSalesOrderInterfaceDtls(
			SAPBillingConsgSummaryTO sapBillConsgSummaryTO, Long maxDataCount)
			throws ParseException, CGSystemException {
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: getSalesOrderInterfaceDtls :: Start");
		Session session = null;
		List<SalesOrderInterfaceDO> soDOList = null;
		try {
			// String sql =
			// "SELECT	summary.BOOKING_DATE,summary.SHIP_TO_CODE,customer.CUSTOMER_CODE,summary.DISTRIBUTION_CHANNEL,ofc.OFFICE_CODE,summary.SUMMARY_ID,prod.CONSG_SERIES,summary.NO_OF_PICKUPS,IF(FINAL_SLAB_RATE IS NULL,0,FINAL_SLAB_RATE) AS FREIGHT,IF(FUEL_SURCHARGE IS NULL,0,FUEL_SURCHARGE) AS FUEL_SURCHARGE,IF(RISK_SURCHARGE IS NULL,0,RISK_SURCHARGE)AS RISK_SURCHARGE,IF(PARCEL_HANDLING_CHARGE IS NULL,0,PARCEL_HANDLING_CHARGE) AS PARCEL_HANDLING_CHARGES,IF(AIRPORT_HANDLING_CHARGE IS NULL,0,AIRPORT_HANDLING_CHARGE) AS AIRPORT_HANDLING_CHARGE,IF(DOCUMENT_HANDLING_CHARGE IS NULL,0,DOCUMENT_HANDLING_CHARGE) AS DOCUMENT_HANDLING_CHARGE,IF(COD_CHARGES IS NULL,0,COD_CHARGES) AS COD_CHARGES,IF(TO_PAY_CHARGE IS NULL,0,TO_PAY_CHARGE) AS TO_PAY_CHARGE,IF(LC_CHARGE IS NULL,0,LC_CHARGE) AS LC_CHARGE,SUM(IF(OTHER_OR_SPECIAL_CHARGES IS NULL,0,OTHER_OR_SPECIAL_CHARGES) + IF(OCTROI IS NULL,0,OCTROI) + IF(SERVICE_CHARGE_ON_OCTROI IS NULL,0,SERVICE_CHARGE_ON_OCTROI)) AS OTHERS,SUM(IF(SERVICE_TAX IS NULL,0,SERVICE_TAX)  + IF(SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE IS NULL,0,SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE)) AS SERVICE_TAX,SUM(IF(EDUCATION_CESS IS NULL,0,EDUCATION_CESS) + IF(EDU_CESS_ON_OCTROI_SERVICE_CHARGE IS NULL,0,EDU_CESS_ON_OCTROI_SERVICE_CHARGE)) AS EDU_CESS,SUM(IF(HIGHER_EDUCATION_CES IS NULL,0,HIGHER_EDUCATION_CES) + IF(HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE IS NULL,0,HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE)) AS SEC_HIGH_EDU_CESS,SUM(IF(STATE_TAX IS NULL,0,STATE_TAX) + IF(OCTROI_SALES_TAX_ON_OCTROI_SERVICE_CHARGE IS NULL,0 , OCTROI_SALES_TAX_ON_OCTROI_SERVICE_CHARGE)) AS STATE_TAX, SUM(IF(SURCHARGE_ON_STATE_TAX IS NULL, 0 ,SURCHARGE_ON_STATE_TAX) + IF(OCTROI_SURCHARGE_ON_STATE_TAX_ON_OCTROI_SERVICE_CHARGE IS NULL,0, OCTROI_SURCHARGE_ON_STATE_TAX_ON_OCTROI_SERVICE_CHARGE)) AS SURCHARGE_ON_STATE_TAX, IF (GRAND_TOTAL_INCLUDING_TAX IS NULL,0,GRAND_TOTAL_INCLUDING_TAX) AS GRAND_TOTAL_INCLUDING_TAX, summary.SUMMARY_CATEGORY, summary.DESTINATION_OFFICE FROM	ff_f_billing_consignment_summary summary JOIN ff_f_billing_consignment consignment ON consignment.SUMMARY = summary.SUMMARY_ID JOIN ff_f_billing_consignment_rate rate ON rate.BILLING_CONSIGNMENT = consignment.BILLING_CONSIGNMENT_ID JOIN ff_f_booking bk on consignment.CONSG_NO = bk.CONSG_NUMBER left join ff_d_customer customer ON bk.CUSTOMER = customer.CUSTOMER_ID JOIN  ff_d_product prod ON prod.PRODUCT_CODE = summary.PRODUCT_CODE JOIN  ff_d_office ofc  ON ofc.office_id = summary.PICKUP_OFFICE_ID WHERE summary.TRANSFER_STATUS = 'N' GROUP BY summary.SUMMARY_ID; ";
			int maxDataCnt = maxDataCount.intValue();
			String sql = "SELECT summary.BOOKING_DATE,summary.SHIP_TO_CODE,customer.CUSTOMER_CODE,summary.DISTRIBUTION_CHANNEL,ofc.OFFICE_CODE,summary.SUMMARY_ID,prod.CONSG_SERIES, summary.NO_OF_PICKUPS, SUM(if(FINAL_SLAB_RATE IS NULL, 0, FINAL_SLAB_RATE)) AS FREIGHT, SUM(if(FUEL_SURCHARGE IS NULL, 0, FUEL_SURCHARGE)) AS FUEL_SURCHARGE, SUM(if(RISK_SURCHARGE IS NULL, 0, RISK_SURCHARGE)) AS RISK_SURCHARGE, SUM(if(PARCEL_HANDLING_CHARGE IS NULL, 0, PARCEL_HANDLING_CHARGE)) AS PARCEL_HANDLING_CHARGE, SUM(if(AIRPORT_HANDLING_CHARGE IS NULL, 0, AIRPORT_HANDLING_CHARGE)) AS AIRPORT_HANDLING_CHARGE, SUM(if(DOCUMENT_HANDLING_CHARGE IS NULL, 0, DOCUMENT_HANDLING_CHARGE)) AS DOCUMENT_HANDLING_CHARGE, SUM(if(COD_CHARGES IS NULL, 0, COD_CHARGES)) AS COD_CHARGES, SUM(if(TO_PAY_CHARGE IS NULL, 0, TO_PAY_CHARGE)) AS TO_PAY_CHARGE, SUM(if(LC_CHARGE IS NULL, 0, LC_CHARGE)) AS LC_CHARGE, SUM(if(OTHER_OR_SPECIAL_CHARGES IS NULL, 0, OTHER_OR_SPECIAL_CHARGES) + if(OCTROI IS NULL, 0, OCTROI)+ if(SERVICE_CHARGE_ON_OCTROI IS NULL, 0, SERVICE_CHARGE_ON_OCTROI)) AS OTHER_CHARGES, SUM(if(SERVICE_TAX IS NULL, 0, SERVICE_TAX)+ if(SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE IS NULL,0,SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE))AS SERVICE_TAX, SUM(if(EDUCATION_CESS IS NULL, 0, EDUCATION_CESS)+ if(EDU_CESS_ON_OCTROI_SERVICE_CHARGE IS NULL,0,EDU_CESS_ON_OCTROI_SERVICE_CHARGE))AS EDUCATION_CESS, SUM(if(HIGHER_EDUCATION_CES IS NULL, 0, HIGHER_EDUCATION_CES)+ if(HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE IS NULL,0, HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE))  AS SEC_HIGHER_EDU_CESS,  SUM(if(STATE_TAX IS NULL, 0, STATE_TAX) + if(OCTROI_SALES_TAX_ON_OCTROI_SERVICE_CHARGE IS NULL,0, OCTROI_SALES_TAX_ON_OCTROI_SERVICE_CHARGE))AS STATE_TAX, SUM(if(SURCHARGE_ON_STATE_TAX IS NULL, 0, SURCHARGE_ON_STATE_TAX) + if(OCTROI_SURCHARGE_ON_STATE_TAX_ON_OCTROI_SERVICE_CHARGE IS NULL,0,OCTROI_SURCHARGE_ON_STATE_TAX_ON_OCTROI_SERVICE_CHARGE)) AS SURCHARGE_ON_STATE_TAX, SUM(if(GRAND_TOTAL_INCLUDING_TAX IS NULL, 0, GRAND_TOTAL_INCLUDING_TAX)),summary.SUMMARY_CATEGORY, summary.DESTINATION_OFFICE FROM ff_f_billing_consignment_summary summary  JOIN ff_f_billing_consignment consignment  ON consignment.SUMMARY = summary.SUMMARY_ID  JOIN ff_f_billing_consignment_rate rate   ON rate.BILLING_CONSIGNMENT = consignment.BILLING_CONSIGNMENT_ID  JOIN ff_f_booking bk ON consignment.CONSG_NO = bk.CONSG_NUMBER  LEFT JOIN ff_d_customer customer ON bk.CUSTOMER = customer.CUSTOMER_ID JOIN  ff_d_product prod ON prod.PRODUCT_CODE = summary.PRODUCT_CODE 	JOIN  ff_d_office ofc  ON ofc.office_id = summary.PICKUP_OFFICE_ID  WHERE summary.TRANSFER_STATUS = 'N' GROUP BY summary.SUMMARY_ID";

			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createSQLQuery(sql);
			query.setMaxResults(maxDataCnt);
			query.setCacheable(false);
			List<SalesOrderInterfaceDO> salesOrderDtls = query.list();
			session.close();
			soDOList = prepareSODtlsList(salesOrderDtls);
		} catch (Exception e) {
			logger.error(
					"BillingSummaryCreationScheduler - for Summary scheduler :: Exception in :: SAPIntegrationDAOImpl :: getSalesOrderInterfaceDtls",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: getSalesOrderInterfaceDtls :: End");
		return soDOList;
	}

	private List<SalesOrderInterfaceDO> prepareSODtlsList(List salesOrderValue)
			throws ParseException {
		List<SalesOrderInterfaceDO> soDOList = null;
		if (salesOrderValue != null && !salesOrderValue.isEmpty()) {
			soDOList = new ArrayList<SalesOrderInterfaceDO>();
			for (int i = 0; i < salesOrderValue.size(); i++) {
				Object[] row = (Object[]) salesOrderValue.get(i);
				SalesOrderInterfaceDO soDO = new SalesOrderInterfaceDO();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				// Bookin Date -
				if (row[0] != null) {
					String date = row[0].toString();
					Date bookingDate = df.parse(date);
					soDO.setBookingDate(bookingDate);
				}

				// Ship To code
				if (row[1] != null) {
					soDO.setCustShipTo(row[1].toString());
				}

				// Sold To Code
				if (row[2] != null) {
					soDO.setCustSoldTo(row[2].toString());
				}

				// Dist Channel
				if (row[3] != null) {
					soDO.setDistributionChannel(row[3].toString());
				}

				// Booking Ofc Code
				if (row[4] != null) {
					soDO.setBookingOffice(row[4].toString());
				}

				// Transaction No - Summary Id
				if (row[5] != null) {
					soDO.setSummaryId(Integer.parseInt(row[5].toString()));
				}

				// Product Code - Consg Series
				if (row[6] != null) {
					soDO.setProductCode(row[6].toString());
				}

				// Quantity - No of Pick Up
				if (row[7] != null) {
					soDO.setQuantity(Integer.parseInt(row[7].toString()));
				}

				// Final Slab Rate - Freight SurCharge
				if (row[8] != null) {
					soDO.setFreight(Double.parseDouble(row[8].toString()));
				}

				// Fuel Surcharge
				if (row[9] != null) {
					soDO.setFuelSurcharge(Double.parseDouble(row[9].toString()));
				}

				if (row[10] != null) {
					soDO.setRiskSurcharge(Double.parseDouble(row[10].toString()));
				}

				if (row[11] != null) {
					soDO.setParcelHandlingCharges(Double.parseDouble(row[11]
							.toString()));
				}

				if (row[12] != null) {
					soDO.setAirportHandlingCharges(Double.parseDouble(row[12]
							.toString()));
				}

				if (row[13] != null) {
					soDO.setDocumentHandlingCharges(Double.parseDouble(row[13]
							.toString()));
				}
				// Value Of Material - Missing in Query

				if (row[14] != null) {
					soDO.setCodCharges(Double.parseDouble(row[14].toString()));
				}
				if (row[15] != null) {
					soDO.setToPayCharges(Double.parseDouble(row[15].toString()));
				}
				if (row[16] != null) {
					soDO.setLcCharges(Double.parseDouble(row[16].toString()));
				}
				if (row[17] != null) {
					soDO.setOthers(Double.parseDouble(row[17].toString()));
				}
				if (row[18] != null) {
					soDO.setServiceTax(Double.parseDouble(row[18].toString()));
				}
				if (row[19] != null) {
					soDO.setEducationCess(Double.parseDouble(row[19].toString()));
				}
				if (row[20] != null) {
					soDO.setSecHighEduCess(Double.parseDouble(row[20]
							.toString()));
				}
				if (row[21] != null) {
					soDO.setStateTax(Double.parseDouble(row[21].toString()));
				}
				if (row[22] != null) {
					soDO.setSurchargeOnStateTax(Double.parseDouble(row[22]
							.toString()));
				}
				if (row[23] != null) {
					soDO.setGrandTotal(Double.parseDouble(row[23].toString()));
				}
				if (row[24] != null) {
					soDO.setSummaryCategory((row[24].toString()));
				}
				if (row[25] != null) {
					soDO.setDestinationOfc((row[25].toString()));
				}
				soDOList.add(soDO);
			}
		}
		return soDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductDO> getProductByCode(String qryName) {

		List<ProductDO> productDOList = new ArrayList<>();
		productDOList = getHibernateTemplate().findByNamedQuery(qryName);
		return productDOList;
	}

	@Override
	public void saveLiabilityEntriesStagingData(
			List<SAPLiabilityEntriesDO> lEntriesStagingDOList) {
		logger.debug("CODLC BOOKING :: SAPIntegrationDAOImpl :: saveLiabilityEntriesStagingData :: start");
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		Session session = null;
		Transaction tx = null;
		try{
			session = createSession();
			for (SAPLiabilityEntriesDO sapLiabilityEntriesDO : lEntriesStagingDOList) {
				try {
					tx = session.beginTransaction();
					
					// Save SAPLiabilityEntriesDO in ff_f_sap_liability_entries table
					session.save(sapLiabilityEntriesDO);
					
					// Update DT_SAP_OUTBOUND flag in ff_f_consignment table
					String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_COD_LC_DTLS_FOR_SAP;
					Query qry = session.getNamedQuery(queryName);
					qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND, SAPIntegrationConstants.SAP_STATUS_C);
					qry.setLong(SAPIntegrationConstants.CONSG_ID, sapLiabilityEntriesDO.getConsgId());
					qry.executeUpdate();
					tx.commit();
				} catch (Exception e) {
					logger.error("CODLC BOOKING :: Exception In :: SAPIntegrationDAOImpl :: saveLiabilityEntriesStagingData :: Error", e);
					tx.rollback();

					SAPErrorTO errorTO = new SAPErrorTO();
					errorTO.setErrorMessage(ExceptionUtil.getMessageFromException(e));
					if (!StringUtil.isStringEmpty(sapLiabilityEntriesDO.getConsgNo())) {
						errorTO.setTransactionNo(sapLiabilityEntriesDO.getConsgNo());
					}
					sapErroTOlist.add(errorTO);
					sendSapIntgErrorMail(sapErroTOlist,
							SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
							"Liability Entries Consignment Error Records");
				}
			}
		}
		catch(Exception e){
			logger.error("CODLC BOOKING :: Exception In :: SAPIntegrationDAOImpl :: saveLiabilityEntriesStagingData :: Error", e);
		}
		finally{
			closeSession(session);
		}
		logger.debug("CODLC BOOKING :: SAPIntegrationDAOImpl :: saveLiabilityEntriesStagingData :: End");
	}

	@Override
	public void updateDateTimeAndStatusFlagOfLiabilityEntries(
			SAPLiabilityEntriesDO consgDOList) {
		logger.debug("CODLC BOOKING :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfLiabilityEntries :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_COD_LC_DTLS_FOR_SAP;
			// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// Date today = Calendar.getInstance().getTime();
			// String dateStamp = df.format(today);
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND, consgDOList.getSapStatus());
			// qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP,
			// dateStamp);
			qry.setLong(SAPIntegrationConstants.CONSG_ID, consgDOList.getConsgId());
			qry.executeUpdate();
		} catch (Exception e) {
			logger.error("CODLC BOOKING :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfLiabilityEntries :: Exception occurred while updating the sap status of " +
					"consignment_id [" + consgDOList.getConsgId() + "] in ff_f_consignment table",e);
		} finally {
			closeSession(session);
		}
		logger.debug("CODLC BOOKING :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfLiabilityEntries :: END");
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<SAPLiabilityEntriesDO> findCODLCFromStaging( String
	 * stagingQueryName, String[] stagingParamNames, Object[]
	 * stagingParamValues) {
	 * logger.debug("SAPIntegrationDAOImpl :: findCODLCFromStaging :: start");
	 * List<SAPLiabilityEntriesDO> sapCODLCDO = null; try { sapCODLCDO =
	 * getHibernateTemplate().findByNamedQueryAndNamedParam( stagingQueryName,
	 * stagingParamNames, stagingParamValues); } catch (Exception e) {
	 * logger.debug("SAPIntegrationDAOImpl :: findCODLCFromStaging :: end");
	 * return sapCODLCDO; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<SAPLiabilityEntriesDO> findCODLCFromStaging(
			SAPLiabilityEntriesTO sapLiEntriesTO, Long maxDataCountLimit) {
		logger.debug("CODLC STAGING :: SAPIntegrationDAOImpl :: findCODLCFromStaging :: start");
		List<SAPLiabilityEntriesDO> sapCODLCDO = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_GET_COD_LC_FROM_STAGING);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					sapLiEntriesTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			sapCODLCDO = query.list();
			// sapCODLCDO =
			// getHibernateTemplate().findByNamedQueryAndNamedParam(
			// stagingQueryName, stagingParamNames, stagingParamValues);
		} catch (Exception e) {
			logger.error(
					"CODLC STAGING :: Exception In :: SAPIntegrationDAOImpl :: findCODLCFromStaging ::",
					e);
		} finally {
			closeSession(session);
		}
		logger.debug("CODLC STAGING :: SAPIntegrationDAOImpl :: findCODLCFromStaging :: end ");
		return sapCODLCDO;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<SAPLiabilityEntriesDO> findConsgStatusFromStaging(
	 * String stagingQueryName, String[] stagingParamNames, Object[]
	 * stagingParamValues) {
	 * logger.debug("SAPIntegrationDAOImpl :: findConsgStatusFromStaging :: start"
	 * ); List<SAPLiabilityEntriesDO> sapCODLCDOList = null; try {
	 * sapCODLCDOList = getHibernateTemplate()
	 * .findByNamedQueryAndNamedParam(stagingQueryName, stagingParamNames,
	 * logger
	 * .debug("SAPIntegrationDAOImpl :: findConsgStatusFromStaging :: end");
	 * return sapCODLCDOList; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<SAPLiabilityEntriesDO> findConsgStatusFromStaging(
			SAPLiabilityEntriesTO sapCODLCTO, Long maxDataCountLimit) {
		logger.debug("CODLC RTODRS :: SAPIntegrationDAOImpl :: findConsgStatusFromStaging :: start");
		List<SAPLiabilityEntriesDO> sapCODLCDOList = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery(SAPIntegrationConstants.GET_COD_LC_CONSG_STATUS_FROM_STAGING);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,	sapCODLCTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			sapCODLCDOList = query.list();
		} catch (Exception e) {
			logger.error(
					"CODLC RTODRS :: Exception In :: SAPIntegrationDAOImpl :: findConsgStatusFromStaging ::", e);
		} finally {
			session.close();
		}
		logger.debug("CODLC RTODRS :: SAPIntegrationDAOImpl :: findConsgStatusFromStaging :: end");
		return sapCODLCDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ConsignmentDO getConsgStatusDelivered(String consgNo) {
		logger.debug("CODLC DELIVERED :: SAPIntegrationDAOImpl :: getConsgStatusDelivered :: start");
		ConsignmentDO consgDO = null;
		try {
			List<ConsignmentDO> consgDOList = new ArrayList<>();
			String queryName = SAPIntegrationConstants.QRY_PARAM_GET_CONSG_STATUS_DELIVERED;
			consgDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, SAPIntegrationConstants.CONSG_NUMBER, consgNo);
			if (!StringUtil.isEmptyColletion(consgDOList)) {
				consgDO = consgDOList.get(0);
			}
		} catch (Exception e) {
			logger.debug(
					"CODLC DELIVERED :: Error IN SAPIntegrationDAOImpl :: getConsgStatusDelivered :: ",	e);
		}
		logger.debug("CODLC DELIVERED :: SAPIntegrationDAOImpl :: getConsgStatusDelivered :: End");
		return consgDO;
	}

	@Override
	public void updateConsgDeliveredStatusInStaging(
			SAPLiabilityEntriesDO sapCODLCDO) {
		logger.debug("CODLC DELIVERED :: SAPIntegrationDAOImpl :: updateConsgDeliveredStatusInStaging :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_CONSG_DELIVERED_STATUS_IN_STAGING;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = Calendar.getInstance().getTime();
			String dateStamp = df.format(today);
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.CONSG_DELIVERED, sapCODLCDO.getConsgDelivered());
			qry.setString(SAPIntegrationConstants.UPDATED_DATE, dateStamp);
			qry.setInteger(SAPIntegrationConstants.UPDATED_BY, SAPIntegrationConstants.SAP_USER_ID);
			qry.setString(SAPIntegrationConstants.CONSG_NUMBER, sapCODLCDO.getConsgNo());
			qry.executeUpdate();
		} catch (Exception e) {
			logger.error("CODLC DELIVERED :: SAPIntegrationDAOImpl :: updateConsgDeliveredStatusInStaging :: Exception :",e);
		} finally {
			closeSession(session);
		}
		logger.debug("CODLC DELIVERED :: SAPIntegrationDAOImpl :: updateConsgDeliveredStatusInStaging :: End");
	}

	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO getConsgStatusRTO(Integer consgId)
			throws CGSystemException {
		logger.debug("CODLC RTO :: SAPIntegrationDAOImpl :: getConsgStatusRTO :: start");
		ManifestDO manifestDO = null;
		Query query = null;
		Session session = null;
		List<ManifestDO> manifestDOList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			String queryName = "getConsgStatusRTO";
			query = session.getNamedQuery(queryName);
			query.setParameter("consgId", consgId);
			manifestDOList = query.list();
			if (!StringUtil.isEmptyColletion(manifestDOList)) {
				manifestDO = manifestDOList.get(0);
			}
			if (manifestDO != null) {
				Hibernate.initialize(manifestDO.getConsignments());
			}
		} catch (Exception e) {
			logger.error(
					"CODLC RTO :: Error IN SAPIntegrationDAOImpl :: getConsgStatusRTO :: ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("CODLC RTO :: SAPIntegrationDAOImpl :: getConsgStatusRTO :: End");
		return manifestDO;
	}

	@Override
	public void updateConsgRTOStatusInStaging(
			List<SAPLiabilityEntriesDO> sapCODRTOList) throws CGSystemException {
		logger.debug("CODLC RTO :: SAPIntegrationDAOImpl :: updateConsgRTOStatusInStaging :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_CONSG_RTO_STATUS_IN_STAGING;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SAPLiabilityEntriesDO sapCODLCDO : sapCODRTOList) {
				try{
					if (!StringUtil.isNull(sapCODLCDO)) {
						Date today = Calendar.getInstance().getTime();
						String dateStamp = df.format(today);
						Query qry = session.getNamedQuery(queryName);
						qry.setDate(SAPIntegrationConstants.RTO_DATE, sapCODLCDO.getRtoDate());
						qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND, sapCODLCDO.getSapStatus());
						qry.setString(SAPIntegrationConstants.UPDATED_DATE, dateStamp);
						qry.setInteger(SAPIntegrationConstants.UPDATED_BY, SAPIntegrationConstants.SAP_USER_ID);
						qry.setString(SAPIntegrationConstants.CONSG_NUMBER, sapCODLCDO.getConsgNo());
						qry.executeUpdate();
					}
				}
				catch(Exception e){
					logger.error("CODLC RTO :: SAPIntegrationDAOImpl :: updateConsgRTOStatusInStaging :: Exception :: Update failed for consignment " +
							"number [" + sapCODLCDO.getConsgNo() + "]", e);
				}
			}
		} catch (Exception e) {
			logger.error("CODLC RTO :: SAPIntegrationDAOImpl :: updateConsgRTOStatusInStaging :: Exception : ", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("CODLC RTO :: SAPIntegrationDAOImpl :: updateConsgRTOStatusInStaging :: End");
	}

	@SuppressWarnings("unchecked")
	@Override
	public ConsignmentDO getConsgStatusRTODRS(String consgNo) {
		logger.debug("SAPIntegrationDAOImpl :: getConsgStatusRTODRS :: start");
		ConsignmentDO consgDO = null;
		try {
			List<ConsignmentDO> consgDOList = new ArrayList<>();
			String queryName = SAPIntegrationConstants.QRY_PARAM_GET_CONSG_STATUS_RTO_DRS;
			consgDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, SAPIntegrationConstants.CONSG_NUMBER, consgNo);
			if (!StringUtil.isEmptyColletion(consgDOList)) {
				consgDO = consgDOList.get(0);
			}
		} catch (Exception e) {
			logger.error("SAPIntegrationDAOImpl :: getConsgStatusRTODRS :: error", e);
		}
		logger.debug("SAPIntegrationDAOImpl :: getConsgStatusRTODRS :: End");
		return consgDO;
	}

	@Override
	public void updateConsgRTODRSStatusInStaging(
			List<SAPLiabilityEntriesDO> sapCODLCDIList) {
		logger.debug("CODLC RTODRS :: SAPIntegrationDAOImpl :: updateConsgRTODRSStatusInStaging :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_CONSG_RTO_DRS_STATUS_IN_STAGING;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SAPLiabilityEntriesDO sapCODLCDO : sapCODLCDIList) {
				try{
					if (!StringUtil.isNull(sapCODLCDO)) {
						Date today = Calendar.getInstance().getTime();
						String dateStamp = df.format(today);
						Query qry = session.getNamedQuery(queryName);
						qry.setDate(SAPIntegrationConstants.RTO_DRS_DATE, sapCODLCDO.getRtoDrsDate());
						qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND, sapCODLCDO.getSapStatus());
						qry.setString(SAPIntegrationConstants.UPDATED_DATE, dateStamp);
						qry.setInteger(SAPIntegrationConstants.UPDATED_BY, SAPIntegrationConstants.SAP_USER_ID);
						qry.setString(SAPIntegrationConstants.CONSG_NUMBER, sapCODLCDO.getConsgNo());
						qry.executeUpdate();
					}
				}
				catch(Exception e){
					logger.error(
							"CODLC RTODRS :: SAPIntegrationDAOImpl :: updateConsgRTODRSStatusInStaging :: Exception : Updating failed for" +
							"consignment number [" + sapCODLCDO.getConsgNo() + "]", e);
				}
			}
		} catch (Exception e) {
			logger.error(
					"CODLC RTODRS :: SAPIntegrationDAOImpl :: updateConsgRTODRSStatusInStaging :: Exception : ", e);
		} finally {
			closeSession(session);
		}
		logger.debug("CODLC RTODRS :: SAPIntegrationDAOImpl :: updateConsgRTODRSStatusInStaging :: End");
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<CollectionDtlsDO> getConsgStatusConsignee() {
	 * logger
	 * .debug("SAPIntegrationDAOImpl :: getConsgStatusConsignee :: Start");
	 * List<CollectionDtlsDO> collnDtlsList = null; try { String queryName =
	 * "getConsgStatusConsignee"; collnDtlsList =
	 * getHibernateTemplate().findByNamedQuery(queryName); } catch (Exception e)
	 * logger.debug("SAPIntegrationDAOImpl :: getConsgStatusConsignee :: End");
	 * return collnDtlsList; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<CollectionDtlsDO> getConsgStatusConsignee(Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("CODLC CONSIGNEE :: SAPIntegrationDAOImpl :: getConsgStatusConsignee :: Start");
		List<CollectionDtlsDO> collnDtlsList = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_GET_CONSG_STATUS_CONSIGNEE);
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			collnDtlsList = query.list();
		} catch (Exception e) {
			logger.error(
					"CODLC CONSIGNEE :: Exception In :: SAPIntegrationDAOImpl :: getConsgStatusConsignee ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("CODLC CONSIGNEE :: SAPIntegrationDAOImpl :: getConsgStatusConsignee :: End");
		return collnDtlsList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SAPLiabilityEntriesDO getCODLCDtlsByConsgNo(String consgNo) {
		logger.debug("CODLC CONSIGNEE :: SAPIntegrationDAOImpl :: getCODLCDtlsByConsgNo :: start");
		List<SAPLiabilityEntriesDO> spaCODLCDOList = null;
		SAPLiabilityEntriesDO sapCODLCDO = null;
		try {
			sapCODLCDO = new SAPLiabilityEntriesDO();
			String queryName = SAPIntegrationConstants.QRY_PARAM_GET_COD_LC_DETAILS_BY_CONSG_NO;
			spaCODLCDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, SAPIntegrationConstants.CONSG_NUMBER,
							consgNo);
			if (!StringUtil.isEmptyColletion(spaCODLCDOList)) {
				sapCODLCDO = spaCODLCDOList.get(0);
			}
		} catch (Exception e) {
			logger.error(
					"CODLC CONSIGNEE :: Exception In :: SAPIntegrationDAOImpl :: getCODLCDtlsByConsgNo :: ", e);
		}
		logger.debug("CODLC CONSIGNEE :: SAPIntegrationDAOImpl :: getCODLCDtlsByConsgNo :: End");
		return sapCODLCDO;
	}

	@Override
	public boolean updateConsigneeDateInStaging(
			List<SAPLiabilityEntriesDO> sapCODLCDOList) {
		logger.debug("CODLC CONSIGNEE :: SAPIntegrationDAOImpl :: updateConsigneeDateInStaging :: Start");
		boolean isUpdated = false;
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_CONSIGNEE_DATE_IN_STAGING;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SAPLiabilityEntriesDO sapCODLCDO : sapCODLCDOList) {
				if (!StringUtil.isNull(sapCODLCDO)) {
					Date today = Calendar.getInstance().getTime();
					String dateStamp = df.format(today);
					Query qry = session.getNamedQuery(queryName);
					qry.setDate(SAPIntegrationConstants.CONSIGNEE_DATE, sapCODLCDO.getConsigneeDate());
					qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND, sapCODLCDO.getSapStatus());
					qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
					qry.setString(SAPIntegrationConstants.CONSG_NUMBER, sapCODLCDO.getConsgNo());
					qry.executeUpdate();
					isUpdated = true;
				}
			}
		} catch (Exception e) {
			logger.error(
					"CODLC CONSIGNEE :: SAPIntegrationDAOImpl :: updateConsigneeDateInStaging :: Exception : ", e);
		} finally {
			closeSession(session);
		}
		logger.debug("CODLC CONSIGNEE :: SAPIntegrationDAOImpl :: updateConsigneeDateInStaging :: End");
		return isUpdated;
	}

	/*
	 * @Override public void updateCODLCStagingStatusFlag(
	 * List<SAPLiabilityEntriesDO> sapCODLCDOList) {
	 * logger.debug("SAPIntegrationDAOImpl :: updateCODLCStagingStatusFlag :: Start"
	 * ); Session session = null; try { session = createSession(); String
	 * queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_COD_LC_SAP_STATUS;
	 * DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); for
	 * (SAPLiabilityEntriesDO sapCODLCDO : sapCODLCDOList) { if
	 * (!StringUtil.isNull(sapCODLCDO)) { Date today =
	 * Calendar.getInstance().getTime(); String dateStamp = df.format(today);
	 * Query qry = session.getNamedQuery(queryName);
	 * qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
	 * sapCODLCDO.getSapStatus());
	 * qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
	 * qry.setLong(SAPIntegrationConstants.SAP_STAGING_ID, sapCODLCDO.getId());
	 * logger
	 * .error("SAPIntegrationDAOImpl :: updateCODLCStagingStatusFlag :: Exception  "
	 * + e.getLocalizedMessage()); } finally { closeSession(session); }
	 * logger.debug
	 * ("SAPIntegrationDAOImpl :: updateCODLCStagingStatusFlag :: End"); }
	 */

	@Override
	public void updateCODLCStagingStatusFlag(
			List<SAPLiabilityEntriesDO> sapCODLCDOList) {
		logger.debug("SAPIntegrationDAOImpl :: updateCODLCStagingStatusFlag :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_COD_LC_SAP_STATUS;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SAPLiabilityEntriesDO sapCODLCDO : sapCODLCDOList) {
				if (!StringUtil.isNull(sapCODLCDO)) {
					Date today = Calendar.getInstance().getTime();
					String dateStamp = df.format(today);
					Query qry = session.getNamedQuery(queryName);
					qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
							sapCODLCDO.getSapStatus());
					qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP,
							dateStamp);
					qry.setString(SAPIntegrationConstants.EXCEPTION,
							sapCODLCDO.getException());
					qry.setLong(SAPIntegrationConstants.SAP_STAGING_ID,
							sapCODLCDO.getId());
					qry.executeUpdate();
				}
			}
		} catch (Exception e) {
			logger.error(
					"SAPIntegrationDAOImpl :: updateCODLCStagingStatusFlag :: Exception  ",
					e);
		} finally {
			closeSession(session);
		}
		logger.debug("SAPIntegrationDAOImpl :: updateCODLCStagingStatusFlag :: End");
	}

	@Override
	public boolean updateContractPayBillDtls(
			List<ContractPaymentBillingLocationDO> contractPayBillingDo) {
		logger.debug("SAPIntegrationDAOImpl :: updateContractPayBillDtls :: Start");
		boolean isUpdated = false;
		for (ContractPaymentBillingLocationDO contractPayBillDO : contractPayBillingDo) {
			getHibernateTemplate().update(contractPayBillDO);
		}
		isUpdated = true;
		logger.debug("SAPIntegrationDAOImpl :: updateContractPayBillDtls :: End");
		return isUpdated;
	}

	@Override
	public boolean saveorUpdateCustomer(List<CGBaseDO> updateCustDO) {
		logger.debug("SAPIntegrationDAOImpl::saveorUpdateCustomer:: start======>");
		boolean isSaved = false;
		for (CGBaseDO custDO : updateCustDO) {
			getHibernateTemplate().saveOrUpdate(custDO);
		}
		isSaved = true;
		logger.debug("SAPIntegrationDAOImpl::saveorUpdateCustomer:: end======>");
		return isSaved;
	}

	@Override
	public void updateCustNoAgaistContarct(CSDSAPCustomerDO custNewDO)
			throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: updateCustNoAgaistContarct :: START");
		Session session = null;
		try {
			session = openTransactionalSession();
			String userName = "SAP_USER";
			UserDO userDO = new UserDO();
			userDO = getSAPUserDtls(userName);
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_CUST_NO_AGAINST_CONTRACT;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = Calendar.getInstance().getTime();
			String dateStamp = df.format(today);
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.CUSTOMER_NO, custNewDO.getCustomerCode());
			qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND, SAPIntegrationConstants.SAP_STATUS_C);
			qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
			qry.setString(SAPIntegrationConstants.CURRENT_STATUS, UdaanCommonConstants.STATUS_ACTIVE);
			qry.setString(SAPIntegrationConstants.DT_TO_BRANCH, CommonConstants.NO);
			qry.setLong(SAPIntegrationConstants.CUSTOMER_ID, custNewDO.getCustomerId());
			if (!StringUtil.isNull(userDO) && !StringUtil.isEmptyInteger(userDO.getUserId())) {
				qry.setInteger(SAPIntegrationConstants.UPDATED_BY, userDO.getUserId());
				qry.setString(SAPIntegrationConstants.UPDATED_DATE, dateStamp);
				qry.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("SAPIntegrationDAOImpl :: updateCustNoAgaistContarct:: Exception ", e);
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
		}
		logger.debug("SAPIntegrationDAOImpl :: updateCustNoAgaistContarct :: END");
	}

	@Override
	public void updateRateCustStatusAgaistContarct(String contractNo) throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: updateRateCustStatusAgaistContarct :: Start");
		Session session = null;
		try {
			session = openTransactionalSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_CUST_STATUS_AGAINST_CONTRACT;
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.CONTRACT_STATUS, UdaanCommonConstants.STATUS_ACTIVE);
			qry.setString(SAPIntegrationConstants.DT_TO_BRANCH, CommonConstants.NO);
			qry.setString(SAPIntegrationConstants.RATE_CONTRACT_NO, contractNo);
			qry.setDate(SAPIntegrationConstants.UPDATED_DATE, DateUtil.getCurrentDate());
			qry.executeUpdate();
		} catch (Exception e) {
			logger.error("SAPIntegrationDAOImpl :: updateRateCustStatusAgaistContarct:: Exception ", e);
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
		}
		logger.debug("SAPIntegrationDAOImpl :: updateRateCustStatusAgaistContarct :: END");
	}

	@Override
	public void updateShipToCode(Integer rateContractId,
			String customerNo) throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: updateShipToCode :: START");
		Session session = null;
		try {
			session = openTransactionalSession();
			String userName = "SAP_USER";
			UserDO userDO = null;
			userDO = getSAPUserDtls(userName);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = Calendar.getInstance().getTime();
			String dateStamp = df.format(today);
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_SHIP_TO_CODE;
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.SHIP_TO_CODE, customerNo);
			qry.setInteger(SAPIntegrationConstants.RATE_CONTRACT_ID, rateContractId);
			if (!StringUtil.isNull(userDO) && !StringUtil.isEmptyInteger(userDO.getUserId())) {
				qry.setInteger(SAPIntegrationConstants.UPDATED_BY, userDO.getUserId());
				qry.setString(SAPIntegrationConstants.UPDATED_DATE, dateStamp);
				qry.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("SAPIntegrationDAOImpl :: updateShipToCode :: Exception ",e);
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
		}
		logger.debug("SAPIntegrationDAOImpl :: updateShipToCode :: END");
	}

	@Override
	public void updateConsgCollStatus(List<CollectionDtlsDO> collnDtlsList,
			boolean isUpdated) throws CGSystemException {
		logger.debug("CODLC CONSIGNEE :: SAPIntegrationDAOImpl :: updateConsgCollStatus :: Start");
		Session session = null;
		try {
			String sapStatus = null;
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_CONSG_COLL_STATUS;
			for (CollectionDtlsDO collDtlsDO : collnDtlsList) {
				if (!StringUtil.isNull(collDtlsDO)) {
					if (isUpdated) {
						sapStatus = "C";
					} else {
						sapStatus = "N";
					}
					Query qry = session.getNamedQuery(queryName);
					qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND, sapStatus);
					qry.setInteger(SAPIntegrationConstants.COLLECTION_ENTRY_ID, collDtlsDO.getEntryId());
					qry.executeUpdate();
				}
			}
		} catch (Exception e) {
			logger.error("CODLC CONSIGNEE :: SAPIntegrationDAOImpl :: updateConsgCollStatus :: Exception  "
					+ e.getLocalizedMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("CODLC CONSIGNEE :: SAPIntegrationDAOImpl :: updateConsgCollStatus :: End");
	}

	@Override
	public boolean updateSalesOrderNumber(BillingConsignmentSummaryDO bcsDO,
			SAPSalesOrderTO soTO) {
		logger.debug("SOUpdateSAPIntegrationServiceImpl :: updateSalesOrderNumber :: Start");
		boolean isUpdated = false;
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_SO_NUMBER;
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.SO_NUMBER,
					soTO.getSalesOrderNumber());
			logger.debug("Sales Order Number  -----> "
					+ soTO.getSalesOrderNumber());
			qry.setInteger(SAPIntegrationConstants.BCS_ID,
					bcsDO.getBillingConsignmentSummaryId());
			logger.debug("BCS_ID ----> "
					+ bcsDO.getBillingConsignmentSummaryId());
			qry.executeUpdate();
			isUpdated = true;
		} catch (Exception e) {
			logger.error(
					"SOUpdateSAPIntegrationServiceImpl :: updateSalesOrderNumber :: Exception  ",
					e);
		} finally {
			closeSession(session);
		}
		logger.debug("SOUpdateSAPIntegrationServiceImpl :: updateSalesOrderNumber :: End");
		return isUpdated;
	}

	/*
	 * @Override public boolean updateInvoiceNumber(BillingConsignmentDO
	 * billConsgDO,SAPSalesOrderTO soTO) { logger.debug(
	 * "SOUpdateSAPIntegrationServiceImpl :: updateInvoiceNumber :: Start");
	 * boolean isUpdated = false; Session session = null; try { session =
	 * createSession(); String queryName =
	 * SAPIntegrationConstants.QRY_PARAM_UPDATE_INVOICE_NUMBER; Query qry =
	 * session.getNamedQuery(queryName);
	 * qry.setString(SAPIntegrationConstants.INVOICE_NUMBER
	 * ,soTO.getBillNumber());
	 * logger.debug("Invoice Number  -----> "+soTO.getBillNumber());
	 * qry.setInteger(SAPIntegrationConstants.INVOICE_ID,
	 * billConsgDO.getBillDO().getInvoiceId());
	 * logger.debug("INVOICE_ID ----> "+billConsgDO.getBillDO().getInvoiceId());
	 * int i = qry.executeUpdate(); if(!StringUtil.isEmptyInteger(i)){ isUpdated
	 * "SOUpdateSAPIntegrationServiceImpl :: updateSalesOrderNumber :: Exception  "
	 * +e.getLocalizedMessage()); } finally{ closeSession(session); }
	 * logger.debug
	 * ("SOUpdateSAPIntegrationServiceImpl :: updateSalesOrderNumber :: End");
	 * return isUpdated; }
	 */

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<SAPOutstandingPaymentDO>
	 * findOutstandingPaymentDtls( String stagingQueryName, String[]
	 * stagingParamNames, Object[] stagingParamValues) {
	 * logger.debug("SAPIntegrationDAOImpl :: findOutstandingPaymentDtls :: Start"
	 * ); List<SAPOutstandingPaymentDO> sapOutstandingReportDOs = null; try {
	 * sapOutstandingReportDOs = getHibernateTemplate()
	 * .findByNamedQueryAndNamedParam(stagingQueryName, stagingParamNames,
	 * stagingParamValues); } catch (Exception e) { logger.error(
	 * "SOUpdateSAPIntegrationServiceImpl :: findOutstandingPaymentDtls :: Exception  "
	 * , e); }
	 * logger.debug("SAPIntegrationDAOImpl :: findOutstandingPaymentDtls :: End"
	 * ); return sapOutstandingReportDOs; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<SAPOutstandingPaymentDO> findOutstandingPaymentDtls(
			SAPOutstandingPaymentTO outPaymentTO, Long maxDataCountLimit) {
		logger.debug("OUTSTANDING REPORT :: SAPIntegrationDAOImpl :: findOutstandingPaymentDtls :: Start");
		List<SAPOutstandingPaymentDO> sapOutstandingReportDOs = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_GET_OUT_STANDING_PAYMENT_DTLS);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					outPaymentTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			sapOutstandingReportDOs = query.list();
		} catch (Exception e) {
			logger.error(
					"OUTSTANDING REPORT :: SOUpdateSAPIntegrationServiceImpl :: findOutstandingPaymentDtls :: Exception  ",
					e);
		} finally {
			closeSession(session);
		}
		logger.debug("OUTSTANDING REPORT :: SAPIntegrationDAOImpl :: findOutstandingPaymentDtls :: End");
		return sapOutstandingReportDOs;
	}

	/*
	 * @Override public void updateOutStandingPaymentStagingStatusFlag(
	 * List<SAPOutstandingPaymentDO> sapOutStandingPaymentList) { logger.debug(
	 * "SAPIntegrationDAOImpl :: updateOutStandingPaymentStagingStatusFlag :: Start"
	 * ); Session session = null; try { session = createSession(); String
	 * queryName =
	 * SAPIntegrationConstants.QRY_PARAM_UPDATE_OUT_STANDING_PAYMENT_DTLS;
	 * DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); for
	 * (SAPOutstandingPaymentDO sapCOPDO : sapOutStandingPaymentList) { Date
	 * today = Calendar.getInstance().getTime(); String dateStamp =
	 * df.format(today); Query qry = session.getNamedQuery(queryName);
	 * qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
	 * sapCOPDO.getSapStatus());
	 * qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
	 * qry.setLong(SAPIntegrationConstants.SAP_STAGING_ID, sapCOPDO.getId());
	 * logger.error(
	 * "SAPIntegrationDAOImpl :: updateOutStandingPaymentStagingStatusFlag :: Exception  "
	 * + e.getLocalizedMessage()); } finally { closeSession(session); }
	 * logger.debug
	 * ("SAPIntegrationDAOImpl :: updateOutStandingPaymentStagingStatusFlag :: END"
	 * );
	 * 
	 * }
	 */

	@Override
	public void updateOutStandingPaymentStagingStatusFlag(
			List<SAPOutstandingPaymentDO> sapOutStandingPaymentList)
			throws CGSystemException {
		logger.debug("OUTSTANDING REPORT :: SAPIntegrationDAOImpl :: updateOutStandingPaymentStagingStatusFlag :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_OUT_STANDING_PAYMENT_DTLS;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SAPOutstandingPaymentDO sapCOPDO : sapOutStandingPaymentList) {
				Date today = Calendar.getInstance().getTime();
				String dateStamp = df.format(today);
				Query qry = session.getNamedQuery(queryName);
				qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
						sapCOPDO.getSapStatus());
				qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
				qry.setString(SAPIntegrationConstants.EXCEPTION,
						sapCOPDO.getException());
				qry.setLong(SAPIntegrationConstants.SAP_STAGING_ID,
						sapCOPDO.getId());
				qry.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("OUTSTANDING REPORT :: SAPIntegrationDAOImpl :: updateOutStandingPaymentStagingStatusFlag :: Exception  "
					+ e.getLocalizedMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("OUTSTANDING REPORT :: SAPIntegrationDAOImpl :: updateOutStandingPaymentStagingStatusFlag :: END");

	}

	@Override
	public Long getLiabilityEntriesCount(String sapStatus,
			ArrayList<Integer> productIDList) throws CGSystemException {
		logger.debug("CODLC BOOKING :: SAPIntegrationDAOImpl :: getLiabilityEntriesCount :: start======>");
		Long count;
		try {
			String queryName = SAPIntegrationConstants.QRY_PARAM_GET_LIABILITY_ENTRIES_COUNT;

			String paramNames[] = { SAPIntegrationConstants.DT_SAP_OUTBOUND,
					SAPIntegrationConstants.PRODUCT_ID_LIST };

			Object paramValues[] = { sapStatus, productIDList };

			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, paramNames,
							paramValues).get(0);
		} catch (Exception e) {
			logger.error("CODLC BOOKING :: ERROR :: SAPIntegrationDAOImpl :: getLiabilityEntriesCount():",e);
			throw new CGSystemException(e);
		}
		logger.debug("CODLC BOOKING :: SAPIntegrationDAOImpl :: getLiabilityEntriesCount :: End======>");
		return count;
	}

	@Override
	public Long getCODLCStagingCount(String sapStatus) throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: getCODLCStagingCount :: start======>");
		Long count;
		try {
			String queryName = SAPIntegrationConstants.QRY_PARAM_GET_COD_LC_STAGING_COUNT;
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, SAPIntegrationConstants.PARAM_SAP_STATUS,
							sapStatus).get(0);
		} catch (Exception e) {
			logger.error("ERROR :: SAPIntegrationDAOImpl :: getCODLCStagingCount() :", e);
			throw new CGSystemException(e);
		}
		logger.debug("SAPIntegrationDAOImpl :: getCODLCStagingCount :: End======>");
		return count;
	}

	@Override
	public Long getCountOfConsgStatusConsignee() throws CGSystemException {
		logger.debug("CODLC CONSIGNEE :: SAPIntegrationDAOImpl :: getCountOfConsgStatusConsignee :: start======>");
		Long count;
		try {
			String queryName = SAPIntegrationConstants.QRY_PARAM_GET_CONSG_STATUS_COUNT_FOR_CONSIGNEE;
			count = (Long) getHibernateTemplate().findByNamedQuery(queryName).get(0);
		} catch (Exception e) {
			logger.error("CODLC CONSIGNEE :: EXCEPTION IN :: SAPIntegrationDAOImpl :: getCountOfConsgStatusConsignee() : ",e);
			throw new CGSystemException(e);
		}
		logger.debug("CODLC CONSIGNEE :: SAPIntegrationDAOImpl :: getCountOfConsgStatusConsignee :: End======>");
		return count;
	}

	@Override
	public Long getLiabilityEntriesDtlsCount(String sapStatus)
			throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: getLiabilityEntriesDtlsCount :: start======>");
		Long count;
		try {
			String queryName = "getCODLCStagingCount";
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "sapStatus",
							sapStatus).get(0);
		} catch (Exception e) {
			logger.error("ERROR :: SAPIntegrationDAOImpl :: getLiabilityEntriesDtlsCount()..:"
					+ e.getMessage());
			throw new CGSystemException(e);

		}
		logger.debug("SAPIntegrationDAOImpl :: getLiabilityEntriesDtlsCount :: End======>");
		return count;

	}

	@Override
	public Long getLiabilityPaymentCount(String sapStatus)
			throws CGSystemException {
		logger.debug("LIABILITY PAYMENT :: SAPIntegrationDAOImpl :: getLiabilityPaymentCount :: start======>");
		Long count;
		try {
			String queryName = "getLiabilityPaymentCount";
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "sapStatus",
							sapStatus).get(0);
		} catch (Exception e) {
			logger.error("LIABILITY PAYMENT :: SAPIntegrationDAOImpl :: getLiabilityPaymentCount()..:"
					+ e.getMessage());
			throw new CGSystemException(e);

		}
		logger.debug("LIABILITY PAYMENT :: SAPIntegrationDAOImpl :: getLiabilityPaymentCount :: End======>");
		return count;
	}

	@Override
	public Long getOutstandingPaymentDtls(String sapStatus)
			throws CGSystemException {
		logger.debug("OUTSTANDING REPORT :: SAPIntegrationDAOImpl :: getOutstandingPaymentDtls :: start======>");
		Long count;
		try {
			String queryName = "getOutstandingPaymentDtls";
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "sapStatus",
							sapStatus).get(0);
		} catch (Exception e) {
			logger.error("OUTSTANDING REPORT :: ERROR :: SAPIntegrationDAOImpl :: getOutstandingPaymentDtls()..:"
					+ e.getMessage());
			throw new CGSystemException(e);

		}
		logger.debug("OUTSTANDING REPORT :: SAPIntegrationDAOImpl :: getOutstandingPaymentDtls :: End======>");
		return count;
	}

	@Override
	public Long getContractDtlsForSAP(String sapStatus)
			throws CGSystemException {
		logger.debug("CONTRACT :: SAPIntegrationDAOImpl :: getContractDtlsForSAP :: start======>");
		Long count;
		try {
			String queryName = "getContractDtlsForSAP";
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "sapStatus",
							sapStatus).get(0);
		} catch (Exception e) {
			logger.error("CONTRACT :: ERROR :: SAPIntegrationDAOImpl :: getContractDtlsForSAP()..:"
					+ e.getMessage());
			throw new CGSystemException(e);

		}
		logger.debug("CONTRACT :: SAPIntegrationDAOImpl :: getContractDtlsForSAP :: End======>");
		return count;
	}

	@Override
	public boolean saveMaterialErrorRecords(List<SAPItemDO> stagingItemDOs)
			throws CGSystemException {
		logger.debug("ITEM :: SAPIntegrationDAOImpl :: saveMaterialErrorRecords :: start======>");
		boolean isSaved = false;
		try {
			for (SAPItemDO sapItemDO : stagingItemDOs) {
				getHibernateTemplate().save(sapItemDO);
			}
			isSaved = true;
		} catch (Exception e) {
			logger.error(
					"ITEM :: Exception IN :: SAPIntegrationDAOImpl :: saveVendorErrorRecords :: ",
					e);
			throw new CGSystemException(e);
		}
		logger.debug("ITEM :: SAPIntegrationDAOImpl :: saveVendorErrorRecords :: end======>");
		return isSaved;
	}

	@Override
	public boolean updateEmployeeDetails(
			List<CSDSAPEmployeeDO> updateEmployeeDOs) throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl::updateEmployeeDetails:: start======>");
		boolean isUpdated = false;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		for (CSDSAPEmployeeDO baseDO : updateEmployeeDOs) {
			try {
				getHibernateTemplate().update(baseDO);
				isUpdated = true;
			} catch (Exception ex) {
				logger.error(
						"Exception In :: SAPIntegrationDAOImpl :: updateEmployeeDetails :: Error",
						ex);
				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(ex.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(ex.getCause().getCause()
							.getMessage());
				}
				if (!StringUtil.isStringEmpty(baseDO.getEmpCode())) {
					errorTO.setTransactionNo(baseDO.getEmpCode());
				}
				sapErroTOlist.add(errorTO);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Employee Error Records");
		logger.debug("SAPIntegrationDAOImpl :: updateEmployeeDetails :: End======>");
		return isUpdated;
	}

	@Override
	public List<CSDSAPEmployeeDO> saveDetailsOneByOneForEmployee(
			List<CSDSAPEmployeeDO> updateEmployeeDOs) throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: saveDetailsOneByOneForEmployee :: start======>");
		List<CSDSAPEmployeeDO> errorReords = null;
		// String exception = null;
		// boolean isSaved = false;
		errorReords = new ArrayList<>();
		for (CSDSAPEmployeeDO baseDo : updateEmployeeDOs) {
			try {
				getHibernateTemplate().save(updateEmployeeDOs);
				// isSaved = true;
			} catch (Exception e) {
				// exception = e.getMessage();
				// baseDo.setException(exception);
				errorReords.add(baseDo);
			}
		}
		logger.debug("SAPIntegrationDAOImpl::saveDetailsOneByOneForEmployee:: end======>");
		return errorReords;
	}

	@Override
	public boolean saveEmployeeErrorRecords(
			List<SAPEmployeeDO> stagingEmployeeDOs) throws CGSystemException {
		logger.debug("EMPLOYEE :: SAPIntegrationDAOImpl :: saveEmployeeErrorRecords :: start======>");
		boolean isSaved = false;
		try {
			for (SAPEmployeeDO sapEmployeeDO : stagingEmployeeDOs) {
				getHibernateTemplate().save(sapEmployeeDO);
			}
			isSaved = true;
		} catch (Exception e) {
			logger.error(
					"EMPLOYEE :: Exception IN :: SAPIntegrationDAOImpl :: saveEmployeeErrorRecords :: ",
					e);
			throw new CGSystemException(e);
		}
		logger.debug("EMPLOYEE :: SAPIntegrationDAOImpl :: saveEmployeeErrorRecords :: end======>");
		return isSaved;
	}

	@Override
	public boolean saveDetailsForEmployee(List<CSDSAPEmployeeDO> employeeDOs)
			throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl::saveDetailsForEmployee:: start======>");
		boolean isSaved = false;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		for (CSDSAPEmployeeDO sapEmpDO : employeeDOs) {
			try {
				getHibernateTemplate().save(sapEmpDO);
				isSaved = true;
			} catch (Exception ex) {
				logger.error(
						"Exception In :: SAPIntegrationDAOImpl :: saveDetailsForEmployee :: Error",
						ex);
				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(ex.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(ex.getCause().getCause()
							.getMessage());
				}
				if (!StringUtil.isStringEmpty(sapEmpDO.getEmpCode())) {
					errorTO.setTransactionNo(sapEmpDO.getEmpCode());
				}
				sapErroTOlist.add(errorTO);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Employee Error Records");
		logger.debug("SAPIntegrationDAOImpl :: saveDetailsForEmployee :: end======>");
		return isSaved;
	}

	@Override
	public List<CSDSAPCustomerDO> saveDetailsOneByOneForCustomers(
			List<CSDSAPCustomerDO> updateCustDO) throws CGSystemException {
		logger.debug("CUSTOMER :: SAPIntegrationDAOImpl :: saveDetailsOneByOneForCustomers :: start======>");
		List<CSDSAPCustomerDO> errorReords = null;
		String exception = null;
		// boolean isSaved = false;
		errorReords = new ArrayList<>();
		for (CSDSAPCustomerDO baseDo : updateCustDO) {
			try {
				getHibernateTemplate().save(updateCustDO);
				// isSaved = true;
			} catch (Exception e) {
				exception = e.getMessage();
				// baseDo.setException(exception);
				errorReords.add(baseDo);
			}
		}
		logger.debug("CUSTOMER :: SAPIntegrationDAOImpl::saveDetailsOneByOneForCustomers:: end======>");
		return errorReords;
	}

	@Override
	public boolean saveCustomerErrorRecords(
			List<SAPCustomerDO> stagingCustomerDOs) throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: saveCustomerErrorRecords :: start======>");
		boolean isSaved = false;
		try {
			for (SAPCustomerDO sapEmployeeDO : stagingCustomerDOs) {
				getHibernateTemplate().save(sapEmployeeDO);
			}
			isSaved = true;
		} catch (Exception e) {
			logger.error(
					"Exception IN :: SAPIntegrationDAOImpl :: saveCustomerErrorRecords :: ",
					e);
			throw new CGSystemException(e);
		}
		logger.debug("SAPIntegrationDAOImpl :: saveCustomerErrorRecords :: end======>");
		return isSaved;
	}

	@Override
	public boolean updateSalesOrderDetails(
			List<SAPBillSalesOrderDO> updateSAPBillSalesOrderDOs)
			throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl::updateEmployeeDetails:: start======>");
		boolean isUpdated = false;
		try {
			// for (CSDSAPEmployeeDO baseDO : updateEmployeeDOs) {
			getHibernateTemplate().update(updateSAPBillSalesOrderDOs);
			isUpdated = true;
		} catch (Exception e) {
			logger.error(
					"Exception IN :: SAPIntegrationDAOImpl :: updateSalesOrderDetails :: ",
					e);
		}
		logger.debug("SAPIntegrationDAOImpl::updateEmployeeDetails:: end======>");
		return isUpdated;
	}

	@Override
	public List<SAPBillSalesOrderDO> saveDetailsOneByOneForSalesOrder(
			List<SAPBillSalesOrderDO> updateSAPBillSalesOrderDOs)
			throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: saveDetailsOneByOneForSalesOrder :: start======>");
		List<SAPBillSalesOrderDO> errorReords = null;
		String exception = null;
		// boolean isSaved = false;
		errorReords = new ArrayList<>();
		for (SAPBillSalesOrderDO baseDo : updateSAPBillSalesOrderDOs) {
			try {
				getHibernateTemplate().save(updateSAPBillSalesOrderDOs);
				// isSaved = true;
			} catch (Exception e) {
				exception = e.getMessage();
				// baseDo.setException(exception);
				errorReords.add(baseDo);
			}
		}
		logger.debug("SAPIntegrationDAOImpl::saveDetailsOneByOneForSalesOrder:: end======>");
		return errorReords;
	}

	@Override
	public boolean saveBillSalesOrderErrorRecords(
			List<SAPStagingBillSalesOrderDO> stagingSalesOrderDOs)
			throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: saveBillSalesOrderErrorRecords :: start======>");
		boolean isSaved = false;
		try {
			for (SAPStagingBillSalesOrderDO sapEmployeeDO : stagingSalesOrderDOs) {
				getHibernateTemplate().save(sapEmployeeDO);
			}
			isSaved = true;
		} catch (Exception e) {
			logger.error(
					"Exception IN :: SAPIntegrationDAOImpl :: saveBillSalesOrderErrorRecords :: ",
					e);
			throw new CGSystemException(e);
		}
		logger.debug("SAPIntegrationDAOImpl :: saveBillSalesOrderErrorRecords :: end======>");
		return isSaved;
	}

	@Override
	public boolean saveDetailsForBillSalesOrder(
			List<SAPBillSalesOrderDO> sapBillSalesOrderDOs)
			throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl::saveDetailsForBillSalesOrder:: start======>");
		boolean isSaved = false;
		try {
			getHibernateTemplate().save(sapBillSalesOrderDOs);
			isSaved = true;
		} catch (Exception e) {
			logger.error(
					"Exception In :: SAPIntegrationDAOImpl :: saveDetailsForBillSalesOrder ::",
					e);
			throw new CGSystemException(e);
		}
		logger.debug("SAPIntegrationDAOImpl::saveDetailsForBillSalesOrder:: end======>");
		return isSaved;
	}

	@Override
	public boolean SaveOrUpdateStagingColoaderInvoiceNo(
			List<CSDSAPColoaderInvoiceDO> updateColoaderDOs)
			throws CGSystemException {
		logger.debug("COLOADERINNVOICE ::  SAPIntegrationDAOImpl::SaveOrUpdateStagingColoaderInvoiceNo:: start======>");
		boolean isSaved = false;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		for (CSDSAPColoaderInvoiceDO csdSapColoaderInvoiceDO : updateColoaderDOs) {
			try {
				getHibernateTemplate().saveOrUpdate(csdSapColoaderInvoiceDO);
				isSaved = true;
			} catch (Exception ex) {
				logger.error(
						"Exception In :: SAPIntegrationDAOImpl :: updateEmployeeDetails :: Error",
						ex);
				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(ex.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(ex.getCause().getCause()
							.getMessage());
				}
				if (!StringUtil.isStringEmpty(csdSapColoaderInvoiceDO
						.getInvoiceNo())) {
					errorTO.setTransactionNo(csdSapColoaderInvoiceDO
							.getInvoiceNo());
				}
				sapErroTOlist.add(errorTO);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Employee Error Records");
		logger.debug("COLOADERINNVOICE ::  SAPIntegrationDAOImpl::SaveOrUpdateStagingColoaderInvoiceNo:: end======>");
		return isSaved;
	}

	@Override
	public boolean updateInvoiceStagingStatus(ColoaderRatesDO coloaderRatesDO)
			throws CGSystemException {
		logger.debug("COLOADERINNVOICE ::  SAPIntegrationDAOImpl :: updateInvoiceStagingStatus :: Start");
		boolean isUpdate = false;
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_INVOICE;
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND, "C");
			qry.executeUpdate();
			qry.setLong(SAPIntegrationConstants.SAP_STAGING_ID,
					coloaderRatesDO.getId());
			qry.executeUpdate();
			isUpdate = true;
		} catch (Exception e) {
			logger.error(
					"COLOADERINNVOICE :: SAPIntegrationDAOImpl :: updateInvoiceStagingStatus:: Exception  ",
					e);
		} finally {
			closeSession(session);
		}
		logger.debug("COLOADERINNVOICE ::  SAPIntegrationDAOImpl :: updateInvoiceStagingStatus :: END");
		return isUpdate;
	}

	@Override
	public void updateBillSalesOrderNumber(String queryName)
			throws CGSystemException {
		Session session = null;
		try {
			session = createSession();
			Query q = session.getNamedQuery("updateBillSalesorderDtls");
			@SuppressWarnings("unused")
			List list = q.list();
			if (list.get(0) instanceof String) {
				logger.error("BillCreationScheduler - for bill generation scheduler :: SAPIntegrationDAOImpl: updateBillSalesOrderNumber: sp_sap_bill_sales_order SP Result:"
						+ list);
			} else {
				for (int i = 0; i < list.size(); i++) {
					Object[] row = (Object[]) list.get(i);
					if (row[0] != null) {
						logger.error("BillCreationScheduler - for bill generation scheduler :: SAPIntegrationDAOImpl :: updateBillSalesOrderNumber: sp_sap_bill_sales_order SP Result:"
								+ row[0]);
					}
					if (row[1] != null) {
						logger.error("BillCreationScheduler - for bill generation scheduler :: SAPIntegrationDAOImpl: updateBillSalesOrderNumber: sp_sap_bill_sales_order SP Result:"
								+ row[1]);
					}
					if (row[2] != null) {
						logger.error("BillCreationScheduler - for bill generation scheduler ::SAPIntegrationDAOImpl: updateBillSalesOrderNumber: sp_sap_bill_sales_order SP Result:"
								+ row[2]);
					}
				}
			}
		} catch (Exception e) {
			logger.error(
					"BillCreationScheduler - for Summary scheduler :: Exception in :: SAPIntegrationDAOImpl :: updateBillSalesorderDtls",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfigurableParamsDO> getMaxDataCount(String query,
			String[] param, Object[] paramValue) throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: getMaxDataCount :: start======>");
		List<ConfigurableParamsDO> configParamDO = null;
		try {
			configParamDO = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(query, param, paramValue);
		} catch (Exception e) {
			logger.error("Exception In :: getMaxDataCount :: getDtls ::", e);
			throw new CGSystemException(e);
		}
		logger.debug("SAPIntegrationDAOImpl :: getMaxDataCount :: end======>");
		return configParamDO;
	}

	@Override
	public Long getPRCount(String sapStatus) throws CGSystemException {
		logger.debug("STOCKREQUISION :: SAPIntegrationDAOImpl :: getPRCount :: start======>");
		Long count;
		try {
			String queryName = "getCountRequisitionDetailsForSAP";
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "sapStatus",
							sapStatus).get(0);

		} catch (Exception e) {
			logger.error("STOCKREQUISION :: ERROR :: SAPIntegrationDAOImpl :: getPRCount()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		logger.debug("STOCKREQUISION :: SAPIntegrationDAOImpl :: getPRCount :: End======>");
		return count;
	}

	@Override
	public Long getPRCountForRHOExternal(String sapStatus)
			throws CGSystemException {
		logger.debug("STOCKREQUISION :: SAPIntegrationDAOImpl :: getPRCountForRHOExternal :: start======>");
		Long count = 0L;
		try {
			String queryName = "getCountRequisitionDtlsForSAPRHOExternal";
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "sapStatus",
							sapStatus).get(0);
		} catch (Exception e) {
			logger.error("STOCKREQUISION :: ERROR :: SAPIntegrationDAOImpl :: getPRCountForRHOExternal()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		logger.debug("STOCKREQUISION :: SAPIntegrationDAOImpl :: getPRCountForRHOExternal :: End======>");
		return count;
	}

	@SuppressWarnings("unused")
	public boolean saveVendorErrorRecords(List<SAPVendorDO> stagingVendorDOs)
			throws CGSystemException {
		logger.debug("VENDOR :: SAPIntegrationDAOImpl :: saveVendorErrorRecords :: start======>");
		List<SAPVendorDO> errorReords = null;
		boolean isSaved = false;
		errorReords = new ArrayList<SAPVendorDO>();
		try {
			for (SAPVendorDO sapVendorDO : stagingVendorDOs) {
				getHibernateTemplate().save(sapVendorDO);
			}
			isSaved = true;
		} catch (Exception e) {
			logger.error(
					"VENDOR :: Exception IN :: SAPIntegrationDAOImpl :: saveVendorErrorRecords :: ",
					e);
			throw new CGSystemException(e);
		}
		logger.debug("VENDOR :: SAPIntegrationDAOImpl :: saveVendorErrorRecords :: end======>");
		return isSaved;
	}

	@Override
	public boolean saveOfficeErrorRecords(List<SAPOfficeDO> stagingOfficeDOs)
			throws CGSystemException {
		logger.debug("OFFCIE :: SAPIntegrationDAOImpl :: saveOfficeErrorRecords :: start======>");
		List<SAPOfficeDO> errorReords = null;
		boolean isSaved = false;
		errorReords = new ArrayList<SAPOfficeDO>();
		try {
			for (SAPOfficeDO sapOfficeDO : stagingOfficeDOs) {
				getHibernateTemplate().save(sapOfficeDO);
			}
			isSaved = true;
		} catch (Exception e) {
			logger.error(
					"OFFCIE :: Exception IN :: SAPIntegrationDAOImpl :: saveOfficeErrorRecords :: ",
					e);
			throw new CGSystemException(e);
		}
		logger.debug("OFFCIE :: SAPIntegrationDAOImpl :: saveOfficeErrorRecords :: end======>");
		return isSaved;
	}

	@Override
	public Long getStockReceiptCount(String sapStatus) throws CGSystemException {
		logger.debug("STOCKACKNOWLEDGEMENT :: SAPIntegrationDAOImpl :: getStockReceiptCount :: start======>");
		Long count;
		try {
			String queryName = "getCountOfStockReceiptForSAP";
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "sapStatus",
							sapStatus).get(0);

		} catch (Exception e) {
			logger.error("STOCKACKNOWLEDGEMENT :: ERROR :: SAPIntegrationDAOImpl :: getStockReceiptCount()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		logger.debug("STOCKACKNOWLEDGEMENT :: SAPIntegrationDAOImpl :: getStockReceiptCount :: End======>");
		return count;
	}

	public Long getLimitOfRecordProcessedForBilling(String paramName) throws CGSystemException {
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: getLimitOfRecordProcessedForBilling() :: Start --------> ::::::");
		Long count = 0L;
		//String paramName = "BILLING_JOB_MAX_CN";

		try {
			String count1 = (String) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							SAPIntegrationConstants.QRY_GET_LIMIT_FOR_BILLING_BATCH,
							SAPIntegrationConstants.BILLING_LIMIT, paramName)
					.get(0);
			count = Long.parseLong(count1);

		} catch (Exception e) {
			logger.error("BillingSummaryCreationScheduler - for Summary scheduler :: ERROR :: SAPIntegrationDAOImpl :: getLimitOfRecordProcessedForBilling()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: getLimitOfRecordProcessedForBilling() :: End --------> ::::::");
		return count;
	}

	@SuppressWarnings("unchecked")
	public Long getTotalCNForBillingJob() throws CGSystemException {
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: getTotalCNForBillingJob() :: Start --------> ::::::");
		Long count = 0L;
		List<Long> countLong=null;
		Session session = null;
		try {
			session = createSession();
			Query query = session
					.createSQLQuery(SAPIntegrationConstants.ELIGIBLE_CN_COUNT_FOR_BILLING).addScalar("totalCount", Hibernate.LONG);
			/*BigInteger c = (BigInteger) query.uniqueResult();
			String a = c.toString();
			count = Long.parseLong(a);*/
			logger.debug("BillingSummaryCreationScheduler----->  :: " + query.getQueryString());
			countLong=query.list();
			
			if(!CGCollectionUtils.isEmpty(countLong) && countLong.get(0) >0L){
				count=countLong.get(0);
			}
			
		} catch (Exception e) {
			logger.error("BillingSummaryCreationScheduler - for Summary scheduler :: ERROR :: SAPIntegrationDAOImpl :: getTotalCNForBillingJob()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.billing.dao.BillingCommonDAO#getConsignmentForRate()
	 */
	@SuppressWarnings("unchecked")
	public List<ConsignmentBilling> getConsignmentForRate(Long limit)
			throws CGBusinessException, CGSystemException {

		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: getConsignmentForRate() :: Start --------> ::::::");
		List<ConsignmentBilling> consignmentList = new ArrayList<ConsignmentBilling>();
		// Session session = openTransactionalSession();
		Session session = null;
		try {
			session = createSession();
			Query query = session.createSQLQuery(
					SAPIntegrationConstants.ELIGIBLE_CN_FOR_BILLING).addEntity(
					ConsignmentBilling.class);
			query.setMaxResults(limit.intValue());
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: getConsignmentForRate() :: Query --------> ::::::" + query.getQueryString());
			consignmentList = query.list();
		} catch (Exception e) {
			logger.error("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: getConsignmentForRate()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl: getConsignmentForRate(): END");
		return consignmentList;
	}

	public ConsignmentBillingRateDO getAlreadyExistConsgRate(
			ConsignmentDO consingnment, String rateFor)
			throws CGBusinessException, CGSystemException {

		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: getAlreadyExistConsgRate() :: Start --------> ::::::");
		ConsignmentBillingRateDO consignmentBillingRateDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(ConsignmentBillingRateDO.class,
					"consignmentBillingRateDO");
			cr.add(Restrictions.eq("consignmentBillingRateDO.consignmentDO",
					consingnment));
			cr.add(Restrictions.eq(
					"consignmentBillingRateDO.rateCalculatedFor", rateFor));
			List<ConsignmentBillingRateDO> consignmentBillingRateDOs = (List<ConsignmentBillingRateDO>) cr
					.list();
			if (!StringUtil.isEmptyColletion(consignmentBillingRateDOs)) {
				consignmentBillingRateDO = consignmentBillingRateDOs.get(0);
			}
		} catch (Exception e) {
			logger.error("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: getAlreadyExistConsgRate()::::::"
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl: getAlreadyExistConsgRate(): END");
		return consignmentBillingRateDO;
	}

	public ConsignmentBillingRateDO saveOrUpdateConsgRate(
			ConsignmentBillingRateDO consignmentBillingRateDO, String consgNo)
			throws CGBusinessException, CGSystemException {

		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: saveOrUpdateConsgRate() :: Start --------> ::::::");
		Session session = null;
		Transaction tx = null;
		Query query = null;
		boolean result = Boolean.FALSE;
		List<String> consgNos = new ArrayList<String>();
		Criteria cr = null;
		ConsignmentDO consigDO = null;
		try {
			session = createSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(consignmentBillingRateDO);

			// Updating flag billingstatus into consignment table
			if (!StringUtil.isNull(consgNo)) {
				consgNos.add(consgNo);
			}

			cr = session.createCriteria(ConsignmentDO.class, "consgDO");
			cr.add(Restrictions.eq("consgDO.consgNo", consgNo));
			List<ConsignmentDO> consignmentDOs = (List<ConsignmentDO>) cr
					.list();
			if (!StringUtil.isEmptyColletion(consignmentDOs)) {
				consigDO = consignmentDOs.get(0);
			}

			if (!StringUtil.isNull(consigDO)) {
				String billStatus = consigDO.getBillingStatus();
				if (StringUtil.isNull(billStatus) || !billStatus.equals("TRB")) {
					query = session
							.getNamedQuery(SAPIntegrationConstants.QRY_UPDATE_BILLING_STATUS);
					query.setString(SAPIntegrationConstants.BILLING_STATUS_RTB,
							SAPIntegrationConstants.RTB_STATUS);
					query.setParameterList(
							SAPIntegrationConstants.CONSIGNMENT_NO, consgNos);
					query.setTimestamp(SAPIntegrationConstants.UPDATED_DATE,
							Calendar.getInstance().getTime());
					query.setInteger(SAPIntegrationConstants.UPDATED_BY, 4);
					int i = query.executeUpdate();
					if (i > 0)
						result = Boolean.TRUE;
				} else {
					query = session
							.getNamedQuery(SAPIntegrationConstants.QRY_UPDATE_BILLING_STATUS);
					query.setString(SAPIntegrationConstants.BILLING_STATUS_RTB,
							SAPIntegrationConstants.RRB_STATUS);
					query.setParameterList(
							SAPIntegrationConstants.CONSIGNMENT_NO, consgNos);
					query.setTimestamp(SAPIntegrationConstants.UPDATED_DATE,
							Calendar.getInstance().getTime());
					query.setInteger(SAPIntegrationConstants.UPDATED_BY, 4);
					int i = query.executeUpdate();
					if (i > 0)
						result = Boolean.TRUE;
				}

			}
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			logger.error("BillingSummaryCreationScheduler - for Summary scheduler :: Exception Occured in::SAPIntegrationDAOImpl::saveOrUpdateConsgRate() :: "
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: saveOrUpdateConsgRate() :: End --------> ::::::");
		return consignmentBillingRateDO;
	}

	@SuppressWarnings("unchecked")
	public boolean UpdateConsignmentBillingStatus(List consgNo)
			throws CGBusinessException, CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: UpdateConsignmentBillingStatus() :: Start --------> ::::::");
		boolean result = Boolean.FALSE;
		List<String> eligibleForRTB = new ArrayList<String>();
		List<String> eligibleForRRB = new ArrayList<String>();
		Session session = null;
		List<ConsignmentDO> consignmentDOs = null;
		Query query = null;
		Criteria cr = null;
		// ConsignmentDO consigDO = null;
		try {
			// Get All Consignment
			session = createSession();
			cr = session.createCriteria(ConsignmentDO.class, "consgDO");
			cr.add(Restrictions.in("consgDO.consgNo", consgNo));
			consignmentDOs = (List<ConsignmentDO>) cr.list();
			if (!StringUtil.isEmptyColletion(consignmentDOs)) {
				for (ConsignmentDO consigDO : consignmentDOs) {
					String billStatus = consigDO.getBillingStatus();
					if (StringUtil.isNull(billStatus)
							|| !billStatus.equals("TRB")) {
						eligibleForRTB.add(consigDO.getConsgNo());
					} else {
						eligibleForRRB.add(consigDO.getConsgNo());
					}
				}
			}
			if (!CGCollectionUtils.isEmpty(eligibleForRTB)) {
				query = session
						.getNamedQuery(SAPIntegrationConstants.QRY_UPDATE_BILLING_STATUS);
				query.setString(SAPIntegrationConstants.BILLING_STATUS_RTB,
						SAPIntegrationConstants.RTB_STATUS);
				query.setParameterList(SAPIntegrationConstants.CONSIGNMENT_NO,
						eligibleForRTB);
				query.setTimestamp(SAPIntegrationConstants.UPDATED_DATE,
						Calendar.getInstance().getTime());
				query.setInteger(SAPIntegrationConstants.UPDATED_BY, 4);
				int i = query.executeUpdate();
				if (i > 0)
					result = Boolean.TRUE;
			}
			if (!CGCollectionUtils.isEmpty(eligibleForRRB)) {
				query = session
						.getNamedQuery(SAPIntegrationConstants.QRY_UPDATE_BILLING_STATUS);
				query.setString(SAPIntegrationConstants.BILLING_STATUS_RTB,
						SAPIntegrationConstants.RRB_STATUS);
				query.setParameterList(SAPIntegrationConstants.CONSIGNMENT_NO,
						eligibleForRRB);
				query.setTimestamp(SAPIntegrationConstants.UPDATED_DATE,
						Calendar.getInstance().getTime());
				query.setInteger(SAPIntegrationConstants.UPDATED_BY, 4);
				int i = query.executeUpdate();
				if (i > 0)
					result = Boolean.TRUE;
			}
		} catch (Exception e) {
			logger.error("SAPIntegrationDAOImpl :: UpdateConsignmentBillingStatus()::::::"
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("SAPIntegrationDAOImpl: UpdateConsignmentBillingStatus(): END");
		return result;
	}

	public BookingDO getCustomerFromTypeBooking(String consgNo)
			throws CGBusinessException, CGSystemException {

		logger.debug("SAPIntegrationDAOImpl :: getCustomerFromTypeBooking() :: Start --------> ::::::");
		BookingDO bookingDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(BookingDO.class, "booking");
			cr.add(Restrictions.eq("booking.consgNumber", consgNo));
			List<BookingDO> bookingDOs = (List<BookingDO>) cr.list();
			if (!StringUtil.isEmptyColletion(bookingDOs)) {
				bookingDO = bookingDOs.get(0);
			}
		} catch (Exception e) {
			logger.error("SAPIntegrationDAOImpl :: getCustomerFromTypeBooking()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("SAPIntegrationDAOImpl: getCustomerFromTypeBooking(): END");
		return bookingDO;
	}

	@SuppressWarnings("unchecked")
	public ProductDO getProduct(Integer productId) throws CGBusinessException,
			CGSystemException {

		logger.debug("SAPIntegrationDAOImpl :: getProduct() :: Start --------> ::::::");
		ProductDO productDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(ProductDO.class, "product");
			cr.add(Restrictions.eq("product.productId", productId));
			List<ProductDO> productDOs = (List<ProductDO>) cr.list();
			if (!StringUtil.isEmptyColletion(productDOs)) {
				productDO = productDOs.get(0);
			}
		} catch (Exception e) {
			logger.error("SAPIntegrationDAOImpl :: getProduct()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("SAPIntegrationDAOImpl: getProduct(): END");
		return productDO;

	}

	public boolean billing_consolidation_Proc() throws CGBusinessException,
			CGSystemException {

		logger.debug("SAPIntegrationDAOImpl :: billing_consolidation_Proc() :: Start --------> ::::::");
		Session session = null;
		boolean flag = false;
		// Session session = openTransactionalSession();
		try {
			// session = getSession();
			// Query q =
			// session.createSQLQuery(" { call sp_billing_consolidation_1() }");
			session = createSession();
			Query q = session
					.getNamedQuery(SAPIntegrationConstants.BILLING_CONSOLIDATION_PROC);
			List list = q.list();

			// logger.debug("SAPIntegrationDAOImpl: billing_consolidation_Proc: Bill Consolidation SP Result:"
			// +list);
			if (list.get(0) instanceof String) {
				logger.debug("SAPIntegrationDAOImpl: billing_consolidation_Proc: Stock billing_consolidation SP Result:"
						+ list);
			} else {
				for (int i = 0; i < list.size(); i++) {
					Object[] row = (Object[]) list.get(i);
					if (row[0] != null) {
						logger.debug("SAPIntegrationDAOImpl: billing_consolidation_Proc: billing_consolidation SP Result:"
								+ row[0]);
					}
					if (row[1] != null) {
						logger.debug("SAPIntegrationDAOImpl: billing_consolidation_Proc: billing_consolidation SP Result:"
								+ row[1]);
					}
					if (row[2] != null) {
						logger.debug("SAPIntegrationDAOImpl: billing_consolidation_Proc: billing_consolidation SP Result:"
								+ row[2]);
					}
				}
			}
			flag = true;
		} catch (Exception ex) {
			flag = false;
			logger.error("Error occured SAPIntegrationDAOImpl ::  billing_consolidation_Proc"
					+ ex);
			new CGSystemException(ex);
		} finally {
			closeSession(session);
			// closeTransactionalSession(session);

		}
		logger.debug("SAPIntegrationDAOImpl: billing_consolidation_Proc(): END");
		return flag;

	}

	public boolean billing_Stock_consolidation_Proc()
			throws CGBusinessException, CGSystemException {

		logger.debug("SAPIntegrationDAOImpl :: billing_Stock_consolidation_Proc() :: Start --------> ::::::");
		Session session = null;
		boolean flag = false;
		// Session session = openTransactionalSession();
		try {
			// session = getSession();
			// Query q =
			// session.createSQLQuery(" { call sp_billing_consolidation_1() }");
			session = createSession();
			Query q = session
					.getNamedQuery(SAPIntegrationConstants.BILLING_STOCK_CONSOLIDATION_PROC);
			List list = q.list();
			// logger.debug("SAPIntegrationDAOImpl: billing_Stock_consolidation_Proc: Stock Consolidation SP Result:"
			// +list);
			// list.get(0);
			if (list.get(0) instanceof String) {
				logger.debug("SAPIntegrationDAOImpl: billing_Stock_consolidation_Proc: Stock Consolidation SP Result:"
						+ list);
			} else {
				for (int i = 0; i < list.size(); i++) {
					Object[] row = (Object[]) list.get(i);
					if (row[0] != null) {
						logger.debug("SAPIntegrationDAOImpl: billing_Stock_consolidation_Proc: Stock Consolidation SP Result:"
								+ row[0]);
					}
					if (row[1] != null) {
						logger.debug("SAPIntegrationDAOImpl: billing_Stock_consolidation_Proc: Stock Consolidation SP Result:"
								+ row[1]);
					}
					if (row[2] != null) {
						logger.debug("SAPIntegrationDAOImpl: billing_Stock_consolidation_Proc: Stock Consolidation SP Result:"
								+ row[2]);
					}
				}
			}

			flag = true;
		} catch (Exception ex) {
			flag = false;
			logger.error("Error occured SAPIntegrationDAOImpl ::  billing_Stock_consolidation_Proc"
					+ ex);
			new CGSystemException(ex);
		} finally {
			closeSession(session);
			// closeTransactionalSession(session);

		}
		logger.debug("SAPIntegrationDAOImpl: billing_Stock_consolidation_Proc(): END");
		return flag;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getDistinctBookingOfcAndDate()
			throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: getDistinctBookingOfcAndDate :: Start");
		List<Object[]> bookingDos = null;
		try {
			String queryName = "getDistinctBookingOfcAndDate";
			bookingDos = getHibernateTemplate().findByNamedQuery(queryName);
		} catch (Exception e) {
			logger.error(
					"ERROR : OrganizationCommonDAOImpl.getReverseCustomerList",
					e);
			throw new CGSystemException(e);
		}
		logger.debug("SAPIntegrationDAOImpl :: getDistinctBookingOfcAndDate :: End");
		return bookingDos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findConsignmentCollection(String queryName,
			String[] paramNames, Object[] paramValues) {
		logger.debug("SAPIntegrationDAOImpl :: findConsignmentCollection :: start======>");
		List<Object[]> consgRateDO = null;
		try {
			consgRateDO = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, paramNames, paramValues);
		} catch (Exception e) {
			logger.error(
					"SAPIntegrationDAOImpl :: findConsignmentCollection :: error::",
					e);
		}
		logger.debug("SAPIntegrationDAOImpl :: findConsignmentCollection :: end======>");
		return consgRateDO;
	}

	@Override
	public boolean saveConsignmentCollection(CollectionDO collectionDO, SAPCollectionTO sapcollnTO, BookingInterfaceWrapperDO bookingWarpperDO)
			throws CGSystemException {
		logger.debug("COLLECTIONCR :: SAPIntegrationDAOImpl :: saveConsignmentCollection :: Start");
		boolean isSaved = false;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		Session session = null;
		Transaction tx = null;
		try {
			session = createSession();
			tx = session.beginTransaction();
			
			// Save CollectionDO
			session.save(collectionDO);
			
			// Update Records in booking table
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_CASH_BOOKING_RECORDS;
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND, sapcollnTO.getSapStatus());
			qry.setTimestamp(SAPIntegrationConstants.BOOKING_END_DATE,  bookingWarpperDO.getBookingDate());
			qry.setTimestamp(SAPIntegrationConstants.BOOKING_START_DATE, DateUtil.trimTimeFromDate(bookingWarpperDO.getBookingDate()));
			qry.setInteger(SAPIntegrationConstants.BOOKING_OFFICE, bookingWarpperDO.getBookingOfficeId());
			if(qry.executeUpdate() > 0){
				isSaved = true;
			}
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			logger.error(
					"COLLECTIONCR :: Exception In :: SAPIntegrationDAOImpl :: saveConsignmentCollection ::",
					e);

			// 2 A
			// Updating status and time stamp in CSD Table if Data successfully
			// saved t o Staging Table
			// if flag = true status = C and Time stamp = current time
			// if flag = false status = N and time Stamp = current Time

			SAPErrorTO errorTO = new SAPErrorTO();
			if (!StringUtil.isStringEmpty(e.getCause().getCause().getMessage())) {
				errorTO.setErrorMessage(e.getCause().getCause().getMessage());
			}
			if (!StringUtil.isStringEmpty(collectionDO.getTxnNo())) {
				errorTO.setTransactionNo(collectionDO.getTxnNo());
			}
			sapErroTOlist.add(errorTO);

			sendSapIntgErrorMail(
					sapErroTOlist,
					SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
					"Cash Consignment Error Records");
		}
		finally{
			closeSession(session);
		}
		logger.debug("COLLECTIONCR :: SAPIntegrationDAOImpl :: saveConsignmentCollection :: End");
		return isSaved;
	}

	@Override
	public boolean updateCashBookingConsg(SAPCollectionTO sapcollnTO,
			BookingInterfaceWrapperDO grandTotalBookingDO) {
		logger.debug("COLLECTIONCR :: SAPIntegrationDAOImpl :: updateCashBookingConsg :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_CASH_BOOKING_RECORDS;
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND, sapcollnTO.getSapStatus());
			qry.setTimestamp(SAPIntegrationConstants.BOOKING_END_DATE,  grandTotalBookingDO.getBookingDate());
			qry.setTimestamp(SAPIntegrationConstants.BOOKING_START_DATE, DateUtil.trimTimeFromDate(grandTotalBookingDO.getBookingDate()));
			qry.setInteger(SAPIntegrationConstants.BOOKING_OFFICE, grandTotalBookingDO.getBookingOfficeId());
			qry.executeUpdate();
		} catch (Exception e) {
			logger.error(
					"COLLECTIONCR :: SAPIntegrationDAOImpl :: updateCashBookingConsg:: Exception  ",
					e);
		} finally {
			closeSession(session);
		}
		logger.debug("COLLECTIONCR :: SAPIntegrationDAOImpl :: updateCashBookingConsg :: END");
		return false;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookingDO> findCashBookingConsg(String qName, String[] pNames,
			Object[] pValues) {
		logger.debug("SAPIntegrationDAOImpl :: findConsignmentCollection :: start======>");
		List<BookingDO> bookingDO = null;
		try {
			bookingDO = getHibernateTemplate().findByNamedQueryAndNamedParam(
					qName, pNames, pValues);
		} catch (Exception e) {
			logger.error(
					"SAPIntegrationDAOImpl :: findConsignmentCollection :: error::",
					e);
		}
		logger.debug("SAPIntegrationDAOImpl :: findConsignmentCollection :: end======>");
		return bookingDO;
	}

	@Override
	public List<ConsignmentDO> findConsgIdsAgainstConsgNo(String queryName,
			String[] paramNames, Object[] paramValues) throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: findConsgIdsAgainstConsgNo :: start ");
		List<ConsignmentDO> consgDOs = null;
		try {
			consgDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, paramNames, paramValues);
		} catch (Exception e) {
			logger.error(
					"Exception In :: SAPIntegrationDAOImpl :: findConsgIdsAgainstConsgNo ::",
					e);
			throw new CGSystemException(e);
		}
		logger.debug("SAPIntegrationDAOImpl :: findConsgIdsAgainstConsgNo :: end ");
		return consgDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getBookingOfcsOfCurrDate(String queryName)
			throws CGSystemException {
		logger.debug("COLLECTIONCR :: SAPIntegrationDAOImpl :: getBookingOfcsOfCurrDate :: Start ");
		List<Integer> officeIds = null;
		try {
			officeIds = getHibernateTemplate().findByNamedQuery(queryName);
		} catch (Exception e) {
			logger.error(
					"COLLECTIONCR :: Exception In :: SAPIntegrationDAOImpl :: findConsgIdsAgainstConsgNo ::",
					e);
			throw new CGSystemException(e);
		}
		logger.debug("COLLECTIONCR :: SAPIntegrationDAOImpl :: getBookingOfcsOfCurrDate :: end ");
		return officeIds;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookingInterfaceWrapperDO> getGrandTotalSum(String qName,
			String[] pNames, Object[] pvalues) throws CGSystemException {
		logger.debug("COLLECTIONCR :: SAPIntegrationDAOImpl :: getGrandTotalSum :: Start ");
		List<BookingInterfaceWrapperDO> grandTotalDOs = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.GET_COLLECTION_CONSIGNMENT);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					pvalues[0]);
			query.setParameterList(SAPIntegrationConstants.OFFICE_IDS,
					(List<Integer>) pvalues[1]);
			query.setParameter(SAPIntegrationConstants.PAYMENT_CODE, pvalues[2]);
			query.setParameter(SAPIntegrationConstants.BOOKING_TYPE, pvalues[3]);
			query.setCacheable(false);
			List<Object[]> objList = (List<Object[]>) query.list();
			if (!CGCollectionUtils.isEmpty(objList)) {
				grandTotalDOs = new ArrayList<BookingInterfaceWrapperDO>(
						objList.size());
				for (Object[] objArr : objList) {
					BookingInterfaceWrapperDO grandTotalDO = new BookingInterfaceWrapperDO();
					grandTotalDO.setBookingOfficeId((Integer) objArr[0]);
					grandTotalDO.setBookingDate((Date) objArr[1]);
					grandTotalDO.setGrandTotalIncludingTax((Double) objArr[2]);
					grandTotalDOs.add(grandTotalDO);
				}
			}
		} catch (Exception e) {
			logger.error(
					"COLLECTIONCR :: Exception In :: SAPIntegrationDAOImpl :: getGrandTotalSum ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("COLLECTIONCR :: SAPIntegrationDAOImpl :: getGrandTotalSum :: end ");
		return grandTotalDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookingInterfaceWrapperDO> getGrandTotalSumForInterface(
			SAPCollectionTO sapcollnTO, Long maxDataCountLimit,
			List<Integer> officeIds) throws CGSystemException {
		logger.debug("COLLECTIONCR :: SAPIntegrationDAOImpl :: getGrandTotalSumForInterface :: start======>");
		List<BookingInterfaceWrapperDO> grandTotalDOs = null;
		Session session = null;
		try {
			//int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.GET_COLLECTION_CONSIGNMENT);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					sapcollnTO.getSapStatus());
			query.setParameterList(SAPIntegrationConstants.OFFICE_IDS,
					officeIds);
			query.setParameter(SAPIntegrationConstants.PAYMENT_CODE,
					sapcollnTO.getPaymentCode());
			query.setParameter(SAPIntegrationConstants.BOOKING_TYPE,
					sapcollnTO.getBookingType());
			// query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			List<Object[]> objList = (List<Object[]>) query.list();
			if (!CGCollectionUtils.isEmpty(objList)) {
				grandTotalDOs = new ArrayList<BookingInterfaceWrapperDO>(
						objList.size());
				for (Object[] objArr : objList) {
					BookingInterfaceWrapperDO grandTotalDO = new BookingInterfaceWrapperDO();
					grandTotalDO.setBookingOfficeId((Integer) objArr[0]);
					grandTotalDO.setBookingDate((Date) objArr[1]);
					grandTotalDO.setGrandTotalIncludingTax((Double) objArr[2]);
					grandTotalDOs.add(grandTotalDO);
				}
			}
		} catch (Exception e) {
			logger.error(
					"COLLECTIONCR :: Exception In :: SAPIntegrationDAOImpl :: getGrandTotalSumForInterface ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("COLLECTIONCR :: SAPIntegrationDAOImpl :: getGrandTotalSumForInterface :: end======>");
		return grandTotalDOs;
	}

	@Override
	public Long getColoaderAirTrinVehicleCount(String sapStatus)
			throws CGSystemException {
		logger.debug("COLOADER :: SAPIntegrationDAOImpl :: getColoaderAirTrinVehicleCount :: start======>");
		Long count;
		try {
			String queryName = "getColoaderAirTrinVehicleCount";
			count = (Long) getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, "sapStatus",sapStatus).get(0);

		} catch (Exception e) {
			logger.error("COLOADER :: ERROR :: SAPIntegrationDAOImpl :: getColoaderAirTrinVehicleCount()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		logger.debug("COLOADER :: SAPIntegrationDAOImpl :: getColoaderAirTrinVehicleCount :: End======>");
		return count;
	}

	@Override
	public List<ColoaderRatesDO> getColoaderAirTrainVehicleDtls(
			SAPColoaderTO sapColoaderTO, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("COLOADER :: SAPIntegrationDAOImpl :: getColoaderAirTrainVehicleDtls :: Start");
		List<ColoaderRatesDO> coloaderDolist = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_COLOADER_DETLS_SAP);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					sapColoaderTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			coloaderDolist = query.list();
		} catch (Exception e) {
			logger.error(
					"COLOADER :: Exception In :: SAPIntegrationDAOImpl :: getColoaderAirTrainVehicleDtls ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("COLOADER :: SAPIntegrationDAOImpl :: getColoaderAirTrainVehicleDtls :: End");
		return coloaderDolist;
	}

	/*
	 * @Override public boolean saveColoaderStagingData(List<SAPColoaderRatesDO>
	 * sapColoaderList) throws CGSystemException { logger.debug(
	 * "COLOADER :: SAPIntegrationDAOImpl :: savePurchaseReqStagingData :: start======>"
	 * ); boolean isSaved = false; Session session = null; Transaction tx =
	 * null; try { session = createSession(); tx = session.beginTransaction();
	 * for (SAPColoaderRatesDO sapColoaderDO : sapColoaderList) {
	 * session.save(sapColoaderDO); } tx.commit(); isSaved = true; } catch
	 * (Exception e) { tx.rollback(); isSaved = false; logger.error(
	 * "COLOADER :: Exception In :: SAPIntegrationDAOImpl :: savePurchaseReqStagingData ::"
	 * ,e); throw new CGSystemException(e); } finally { closeSession(session); }
	 * logger.debug(
	 * "COLOADER :: SAPIntegrationDAOImpl :: savePurchaseReqStagingData :: end======>"
	 * ); return isSaved; }
	 */

	@Override
	public boolean saveColoaderStagingData(
			List<SAPColoaderRatesDO> sapColoaderList) throws CGSystemException {
		logger.debug("COLOADER :: SAPIntegrationDAOImpl :: savePurchaseReqStagingData :: start======>");
		boolean isSaved = false;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		for (SAPColoaderRatesDO coloaderRatesDO : sapColoaderList) {
			try {
				getHibernateTemplate().save(coloaderRatesDO);

				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time

				Date dateTime = Calendar.getInstance().getTime();
				coloaderRatesDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"
						+ coloaderRatesDO.getSapTimestamp());
				coloaderRatesDO.setSapStatus("C");
				logger.debug("SAP Status ------>"
						+ coloaderRatesDO.getSapStatus());
				updateDateTimeAndStatusFlagOfColoader(coloaderRatesDO);
			} catch (Exception ex) {
				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time

				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(ex.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(ex.getCause().getCause()
							.getMessage());
				}
				if (!StringUtil.isEmptyInteger(coloaderRatesDO
						.getTransactionNumber())) {
					errorTO.setTransactionNo(coloaderRatesDO
							.getTransactionNumber().toString());
				}
				sapErroTOlist.add(errorTO);
				Date dateTime = Calendar.getInstance().getTime();
				coloaderRatesDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"
						+ coloaderRatesDO.getSapTimestamp());
				coloaderRatesDO.setSapStatus("N");
				logger.debug("SAP Status ------>"
						+ coloaderRatesDO.getSapStatus());
				updateDateTimeAndStatusFlagOfColoader(coloaderRatesDO);
				logger.error(
						"COLOADER :: Exception In :: SAPIntegrationDAOImpl :: savePurchaseReqStagingData ::",
						ex);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Coloader Air-Train_Vehicle Error Records");
		logger.debug("COLOADER :: SAPIntegrationDAOImpl :: savePurchaseReqStagingData :: end======>");
		return isSaved;
	}

	public void updateDateTimeAndStatusFlagOfColoader(
			SAPColoaderRatesDO coloaderRatesDO) throws CGSystemException {
		logger.debug("COLOADER :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfColoader :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_COLOADER_DTLS;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = Calendar.getInstance().getTime();
			String dateStamp = df.format(today);
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					coloaderRatesDO.getSapStatus());
			qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
			qry.setLong(SAPIntegrationConstants.COLOADER_ID,
					coloaderRatesDO.getTransactionNumber());
			qry.executeUpdate();
		} catch (Exception e) {
			logger.error(
					"COLOADER :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfColoader:: Exception  ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("COLOADER :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfColoader :: END");
	}

	@Override
	public List<SAPColoaderRatesDO> findColoaderDtlsFromStaging(
			SAPColoaderTO sapColoaderTO, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: findColoaderDtlsFromStaging :: start");
		List<SAPColoaderRatesDO> sapColoaderDOList = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_GET_COLOADER_DTLS_FROM_STAGING);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					sapColoaderTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			sapColoaderDOList = query.list();
		} catch (Exception e) {
			logger.error("Exception In :: SAPIntegrationDAOImpl :: getDtls ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("SAPIntegrationDAOImpl::findColoaderDtlsFromStaging:: end");
		return sapColoaderDOList;
	}

	@Override
	public void updateColoaderStagingStatusFlag(
			List<SAPColoaderRatesDO> sapColoaderDOList) {
		logger.debug("COLOADER :: SAPIntegrationDAOImpl :: updateColoaderStagingStatusFlag :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_COLOADER_IN_STAGING;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SAPColoaderRatesDO sapColoaderDO : sapColoaderDOList) {
				Date today = Calendar.getInstance().getTime();
				String dateStamp = df.format(today);
				Query qry = session.getNamedQuery(queryName);
				qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
						sapColoaderDO.getSapStatus());
				qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
				qry.setLong(SAPIntegrationConstants.COLOADER_ID,
						sapColoaderDO.getId());
				qry.setString(SAPIntegrationConstants.EXCEPTION,
						sapColoaderDO.getException());
				qry.executeUpdate();
			}
		} catch (Exception e) {
			logger.error(
					"COLOADER :: SAPIntegrationDAOImpl :: updateColoaderStagingStatusFlag:: Exception  ",
					e);
		} finally {
			closeSession(session);
		}
		logger.debug("COLOADER :: SAPIntegrationDAOImpl :: updateColoaderStagingStatusFlag :: END");
	}

	@Override
	public boolean updateColoaderInvoiceNumber(ColoaderRatesDO coloaderRatesDO) {
		logger.debug("COLOADERINNVOICE ::  SAPIntegrationDAOImpl :: updateColoaderInvoiceNumber :: Start");
		boolean isUpdate = false;
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_COLOADER_INVOICE_NO;
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.COLOADER_INV_NO,
					coloaderRatesDO.getInoviceNo());
			qry.setLong(SAPIntegrationConstants.COLOADER_ID,
					coloaderRatesDO.getId());
			qry.executeUpdate();
			isUpdate = true;
		} catch (Exception e) {
			logger.error("COLOADERINNVOICE :: SAPIntegrationDAOImpl :: updateColoaderInvoiceNumber:: Exception  "
					+ e.getLocalizedMessage());
		} finally {
			closeSession(session);
		}
		logger.debug("COLOADERINNVOICE ::  SAPIntegrationDAOImpl :: updateColoaderInvoiceNumber :: END");
		return isUpdate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeUserDO> getEmployeeUserDtlsByEmpID(Integer employeeId)
			throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: getEmployeeUserDtlsByEmpID :: Start");
		List<EmployeeUserDO> employeeUserDOs = null;
		// EmployeeUserDO employeeUserDO = null;
		try {

			String queryName = "getEmployeeUserDtlsByEmpID";
			employeeUserDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "employeeId",
							employeeId);
			/*
			 * if(!StringUtil.isEmptyColletion(employeeUserDOs)){ employeeUserDO
			 * = employeeUserDOs.get(0); }
			 */
		} catch (Exception e) {
			logger.error(
					"ERROR : SAPIntegrationDAOImpl.getEmployeeUserDtlsByEmpID------>",
					e);
			throw new CGSystemException(e);
		}
		logger.debug("SAPIntegrationDAOImpl :: getEmployeeUserDtlsByEmpID :: End");
		return employeeUserDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDO> getUserDtlsByUserID(Integer userId)
			throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: getUserDtlsByUserID :: Start");
		List<UserDO> userDOs = null;
		try {

			String queryName = "getUserDtlsByUserID";
			userDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "userId", userId);
		} catch (Exception e) {
			logger.error(
					"ERROR : SAPIntegrationDAOImpl.getUserDtlsByUserID------>",
					e);
			throw new CGSystemException(e);
		}
		logger.debug("SAPIntegrationDAOImpl :: getUserDtlsByUserID :: End");
		return userDOs;
	}

	@Override
	public boolean deactiveUaserDtls(Integer userId) {

		logger.debug("SAPIntegrationDAOImpl :: deactiveUaserDtls :: Start");
		Session session = null;
		boolean isUpdate = false;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_DEACTIVATE_USER_DTLS;
			if (!StringUtil.isEmptyInteger(userId)) {
				Query qry = session.getNamedQuery(queryName);
				// qry.setString(SAPIntegrationConstants.DT_TO_BRANCH,
				// "dtToBranch");
				// qry.setString(SAPIntegrationConstants.STATUS, "active");
				qry.setLong(SAPIntegrationConstants.USER_ID, userId);
				qry.executeUpdate();
				isUpdate = true;
			}
		} catch (Exception e) {
			logger.error(
					"SAPIntegrationDAOImpl :: deactiveUaserDtls:: Exception  ",
					e);
		} finally {
			closeSession(session);
		}
		logger.debug("SAPIntegrationDAOImpl :: deactiveUaserDtls :: END");
		return isUpdate;

	}

	@SuppressWarnings("unchecked")
	@Override
	public StockStandardTypeDO getStdDetls(Integer id) throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: getStdDetls :: Start");
		List<StockStandardTypeDO> stdDOs = null;
		StockStandardTypeDO stdDO = null;
		try {

			String queryName = "getStdDetls";
			stdDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "id", id);
			if (!StringUtil.isEmptyColletion(stdDOs)) {
				stdDO = stdDOs.get(0);
			}
		} catch (Exception e) {
			logger.error("ERROR : SAPIntegrationDAOImpl.getStdDetls------>", e);
			throw new CGSystemException(e);
		}
		logger.debug("SAPIntegrationDAOImpl :: getStdDetls :: End");
		return stdDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryDetailsDO> getCocourierDtls(
			SAPCoCourierTO sapCoCourierTO, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("COCOURIER :: SAPIntegrationDAOImpl :: getCocourierDtls :: Start");
		List<DeliveryDetailsDO> deliveryDtlsDOList = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_COCOURIER_DETLS_SAP);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					sapCoCourierTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			deliveryDtlsDOList = query.list();
		} catch (Exception e) {
			logger.error(
					"COCOURIER :: Exception In :: SAPIntegrationDAOImpl :: getCocourierDtls ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("COCOURIER :: SAPIntegrationDAOImpl :: getCocourierDtls :: End");
		return deliveryDtlsDOList;
	}

	@Override
	public boolean saveCocourierStagingData(
			List<SAPCocourierDO> sapCocourierDOList) throws CGSystemException {
		logger.debug("COCOURIER :: SAPIntegrationDAOImpl :: saveCocourierStagingData :: start======>");
		boolean isSaved = false;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		for (SAPCocourierDO cocourierDO : sapCocourierDOList) {
			try {
				getHibernateTemplate().save(cocourierDO);
				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time

				Date dateTime = Calendar.getInstance().getTime();
				cocourierDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"
						+ cocourierDO.getSapTimestamp());
				cocourierDO.setSapStatus("C");
				logger.debug("SAP Status ------>" + cocourierDO.getSapStatus());
				updateDateTimeAndStatusFlagOfCocourier(cocourierDO);
			} catch (Exception ex) {
				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time

				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(ex.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(ex.getCause().getCause()
							.getMessage());
				}
				if (!StringUtil.isEmptyLong(cocourierDO.getId())) {
					errorTO.setTransactionNo(cocourierDO.getId().toString());
				}
				sapErroTOlist.add(errorTO);
				Date dateTime = Calendar.getInstance().getTime();
				cocourierDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"
						+ cocourierDO.getSapTimestamp());
				cocourierDO.setSapStatus("N");
				logger.debug("SAP Status ------>" + cocourierDO.getSapStatus());
				updateDateTimeAndStatusFlagOfCocourier(cocourierDO);

				logger.error(
						"SAPIntegrationDAOImpl :: saveCocourierStagingData :: Exception  ",
						ex);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Co Courier Error Records");
		logger.debug("COCOURIER :: SAPIntegrationDAOImpl::saveCocourierStagingData:: end======>");
		return isSaved;
	}

	public void updateDateTimeAndStatusFlagOfCocourier(
			SAPCocourierDO cocourierDO) throws CGSystemException {
		logger.debug("COCOURIER :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfCocourier :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_COCOURIER_DTLS;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = Calendar.getInstance().getTime();
			String dateStamp = df.format(today);
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					cocourierDO.getSapStatus());
			qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
			qry.setLong(SAPIntegrationConstants.DEL_DTLS_ID,
					cocourierDO.getDeliveryDetailId());
			qry.executeUpdate();
		} catch (Exception e) {
			logger.error(
					"COCOURIER :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfCocourier :: Exception  ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("COCOURIER :: SAPIntegrationDAOImpl :: updateDateTimeAndStatusFlagOfCocourier :: END");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SAPCocourierDO> findCocourierDtlsFromStaging(
			SAPCoCourierTO sapCoCourierTO, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("COCOURIER :: SAPIntegrationDAOImpl :: findCocourierDtlsFromStaging :: start");
		List<SAPCocourierDO> sapCocourierDOList = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_GET_COCOURIER_DTLS_FROM_STAGING);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					sapCoCourierTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			sapCocourierDOList = query.list();
		} catch (Exception e) {
			logger.error(
					"COCOURIER :: Exception In :: SAPIntegrationDAOImpl :: findCocourierDtlsFromStaging ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("COCOURIER :: SAPIntegrationDAOImpl::findCocourierDtlsFromStaging:: end");
		return sapCocourierDOList;
	}

	@Override
	public Long getCocourierDtlsCount(String sapStatus)
			throws CGSystemException {
		logger.debug("COCOURIER :: SAPIntegrationDAOImpl :: getCocourierDtlsCount :: start======>");
		Long count;
		try {
			String queryName = "getCountCoCourierDtls";
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "sapStatus",
							sapStatus).get(0);

		} catch (Exception e) {
			logger.error("COCOURIER :: ERROR :: SAPIntegrationDAOImpl :: getCocourierDtlsCount()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		logger.debug("COCOURIER :: SAPIntegrationDAOImpl :: getCocourierDtlsCount :: End======>");
		return count;
	}

	@Override
	public void updateCocourierStagingStatusFlag(
			List<SAPCocourierDO> sapCocourierDOList) {
		logger.debug("SAPIntegrationDAOImpl :: updateCocourierStagingStatusFlag :: Start");
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_COCOURIER_IN_STAGING;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SAPCocourierDO sapCocourierDO : sapCocourierDOList) {
				Date today = Calendar.getInstance().getTime();
				String dateStamp = df.format(today);
				Query qry = session.getNamedQuery(queryName);
				qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND,
						sapCocourierDO.getSapStatus());
				qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
				qry.setLong(SAPIntegrationConstants.COLOADER_ID,
						sapCocourierDO.getId());
				qry.setString(SAPIntegrationConstants.EXCEPTION,
						sapCocourierDO.getException());
				qry.executeUpdate();
			}
		} catch (Exception e) {
			logger.error(
					"SAPIntegrationDAOImpl :: updateCocourierStagingStatusFlag :: Exception  ",
					e);
		} finally {
			closeSession(session);
		}
		logger.debug("SAPIntegrationDAOImpl :: updateCocourierStagingStatusFlag :: END");

	}

	@Override
	public boolean updateInvoiceStatusInStaging(
			SAPBillSalesOrderDO sapBillSalesOrderDO,
			SAPSalesOrderTO sapsalesOrderTO) throws CGSystemException {
		logger.debug("SALES ORDER :: SAPIntegrationDAOImpl :: updateInvoiceStatusInStaging :: Start");
		boolean isUpdated = false;
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_INVOICE_STATUS;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = Calendar.getInstance().getTime();
			String dateStamp = df.format(today);
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.BILL_NO,
					sapBillSalesOrderDO.getBillNo());
			logger.debug("SALES ORDER :: Bill Number From Table ---> "
					+ sapBillSalesOrderDO.getBillNo());
			qry.setString(SAPIntegrationConstants.BILL_STATUS,
					sapsalesOrderTO.getBillStatus());
			logger.debug("SALES ORDER :: BILL Status ---> "
					+ sapsalesOrderTO.getBillStatus());
			logger.debug("SALES ORDER :: SAP BILL Table Date Time ---> "
					+ dateStamp);
			qry.executeUpdate();
			isUpdated = true;
		} catch (Exception e) {
			isUpdated = false;
			logger.error(
					"SALES ORDER :: SAPIntegrationDAOImpl :: updateInvoiceStatusInStaging:: Exception",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("SALES ORDER :: SAPIntegrationDAOImpl :: updateInvoiceStatusInStaging :: END");
		return isUpdated;
	}

	@Override
	public boolean updateInvoiceStatusInBillTable(
			SAPBillSalesOrderDO sapBillSalesOrderDO,
			SAPSalesOrderTO sapsalesOrderTO) throws CGSystemException {
		logger.debug("SALES ORDER :: SAPIntegrationDAOImpl :: updateInvoiceStatusInBillTable :: Start");
		boolean isUpdate = false;
		Session session = null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_INVOICE_STATUS_IN_BILL;
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.INVOICE_NUMBER,
					sapBillSalesOrderDO.getBillNo());
			logger.debug("SALES ORDER :: Bill Number From Table ---> "
					+ sapBillSalesOrderDO.getBillNo());
			qry.setString(SAPIntegrationConstants.BILL_STATUS,
					sapsalesOrderTO.getBillStatus());
			logger.debug("SALES ORDER :: BILL Status ---> "
					+ sapsalesOrderTO.getBillStatus());
			qry.executeUpdate();
			isUpdate = true;
		} catch (Exception e) {
			isUpdate = false;
			logger.error(
					"SALES ORDER :: SAPIntegrationDAOImpl :: updateInvoiceStatusInBillTable:: Exception",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("SALES ORDER :: SAPIntegrationDAOImpl :: updateInvoiceStatusInBillTable :: END");
		return isUpdate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CollectionDO getCollectionDtlsByTransactionNumber(
			String transactionNumber) throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: getCollectionDtlsByTransactionNumber :: Start");
		List<CollectionDO> collectionDOS = null;
		CollectionDO collDO = null;
		try {

			String queryName = "getCollectionDtlsByTxnNo";
			collectionDOS = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "txnNo",
							transactionNumber);
			if (!StringUtil.isEmptyColletion(collectionDOS)) {
				collDO = collectionDOS.get(0);
			}
		} catch (Exception e) {
			logger.error(
					"ERROR : SAPIntegrationDAOImpl.getCollectionDtlsByTransactionNumber------>",
					e);
			throw new CGSystemException(e);
		}
		logger.debug("SAPIntegrationDAOImpl :: getCollectionDtlsByTransactionNumber :: End");
		return collDO;
	}

	@Override
	public boolean updateVendorOfficeMappedDO(List<Integer> offcIds,
			SAPRegionCodeTO regionTO) throws CGSystemException {
		logger.debug("VENDOR :: SAPIntegrationDAOImpl :: updateVendorOfficeMappedDO :: Start");
		boolean isUpdate = false;
		Session session = null;
		try {
			session = createSession();
			for (Integer ofcId : offcIds) {
				String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_VENDOR_OFFICE_MAPPING;
				Query qry = session.getNamedQuery(queryName);
				qry.setInteger(SAPIntegrationConstants.OFFICE_ID, ofcId);
				logger.debug("VENDOR :: Ofc ID of Vendors ---> " + ofcId);
				qry.setString(SAPIntegrationConstants.VENDOR_STATUS,
						regionTO.getStatus());
				logger.debug("VENDOR :: Status ---> " + regionTO.getStatus());
				qry.executeUpdate();
				isUpdate = true;
			}
		} catch (Exception e) {
			isUpdate = false;
			logger.error("VENDOR :: SAPIntegrationDAOImpl :: updateVendorOfficeMappedDO:: Exception"
					+ e.getLocalizedMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("VENDOR :: SAPIntegrationDAOImpl :: updateVendorOfficeMappedDO :: END");
		return isUpdate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SAPStockIssueDO getStockIssueDtlsFromStaging(String issueNumber)
			throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: getStockIssueDtlsFromStaging :: Start");
		List<SAPStockIssueDO> sapStockIssueDOs = null;
		SAPStockIssueDO sapStockIssueDO = null;
		try {
			String queryName = "getStockIssueDtlsFromStaging";
			sapStockIssueDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "issueNumber",
							issueNumber);
			if (!StringUtil.isEmptyColletion(sapStockIssueDOs)) {
				sapStockIssueDO = new SAPStockIssueDO();
				sapStockIssueDO = sapStockIssueDOs.get(0);
			}
		} catch (Exception e) {
			logger.error(
					"ERROR : SAPIntegrationDAOImpl.getStockIssueDtlsFromStaging",
					e);
			throw new CGSystemException(e);
		}
		logger.debug("SAPIntegrationDAOImpl :: getStockIssueDtlsFromStaging :: End");
		return sapStockIssueDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserDO getSAPUserDtls(String userName) throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: getSAPUserDtls :: Start");
		List<UserDO> userDOs = null;
		UserDO userDO = null;
		try {
			String queryName = "getUserbyUserName";
			userDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "userName", userName);
			if (!StringUtil.isEmptyColletion(userDOs)) {
				userDO = userDOs.get(0);
			}
		} catch (Exception e) {
			logger.error("ERROR : SAPIntegrationDAOImpl.getSAPUserDtls", e);
			throw new CGSystemException(e);
		}
		logger.debug("SAPIntegrationDAOImpl :: getSAPUserDtls :: End");
		return userDO;
	}

	@Override
	public Long getStockIssueDtlsCount(String sapStatus)
			throws CGSystemException {
		logger.debug("STOCKISSUE :: SAPIntegrationDAOImpl :: getStockIssueDtlsCount :: start======>");
		Long count;
		try {
			String queryName = "getCountOfIssueDtlsForSAP";
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "sapStatus",
							sapStatus).get(0);

		} catch (Exception e) {
			logger.error("STOCKISSUE :: ERROR :: SAPIntegrationDAOImpl :: getStockIssueDtlsCount()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		logger.debug("STOCKISSUE :: SAPIntegrationDAOImpl :: getStockIssueDtlsCount :: End======>");
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SAPStockConsolidationDO> getSAPStockConsolidationDtls()
			throws CGSystemException {
		logger.trace("STOCKCONSUMPTION :: SAPIntegrationDAOImpl :: getSAPStockConsolidationDtls() :: START");
		List<SAPStockConsolidationDO> sapStockConsolidationDOs = null;
		try {
			String queryString = StockUniveralConstants.QRY_GET_SAP_STOCK_CONSOLIDATION_DTLS;
			String[] paramNames = { StockUniveralConstants.QRY_PARAM_SAP_TRANSFER_STATUS };
			Object[] values = { CommonConstants.NO };
			sapStockConsolidationDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryString, paramNames,
							values);
		} catch (Exception e) {
			logger.error(
					"STOCKCONSUMPTION :: Exception occurs in SAPIntegrationDAOImpl :: getSAPStockConsolidationDtls() :: ",
					e);
			throw new CGSystemException(e);
		}
		logger.trace("STOCKCONSUMPTION :: SAPIntegrationDAOImpl :: getSAPStockConsolidationDtls() :: END");
		return sapStockConsolidationDOs;
	}

	@Override
	public boolean saveStockConsolidationDtls(
			SAPStockConsolidationDO stckConsolidationDO)
			throws CGSystemException {
		logger.trace("STOCKCONSUMPTION :: SAPIntegrationDAOImpl :: saveStockConsolidationDtls() :: START");
		boolean result = Boolean.FALSE;
		try {
			getHibernateTemplate().saveOrUpdate(stckConsolidationDO);
			result = Boolean.TRUE;
		} catch (Exception e) {
			logger.error(
					"STOCKCONSUMPTION :: Exception occurs SAPIntegrationDAOImpl :: saveStockConsolidationDtls() ::",
					e);
			throw new CGSystemException(e);
		}
		logger.trace("STOCKCONSUMPTION :: SAPIntegrationDAOImpl :: saveStockConsolidationDtls() :: END");
		return result;
	}

	@Override
	public Long getStockCancellationCount(String sapStatus)
			throws CGSystemException {
		logger.debug("STOCKCANCELLATION :: SAPIntegrationDAOImpl :: getStockCancellationCount :: start======>");
		Long count;
		try {
			String queryName = "getStockCancellationCount";
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "sapStatus",
							sapStatus).get(0);

		} catch (Exception e) {
			logger.error("STOCKCANCELLATION :: ERROR :: SAPIntegrationDAOImpl :: getStockCancellationCount()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		logger.debug("STOCKCANCELLATION :: SAPIntegrationDAOImpl :: getStockCancellationCount :: End======>");
		return count;
	}

	@Override
	public List<SAPStockCancellationDO> findStkCancellationDtlsFromStaging(
			SAPStockCancellationTO stockCancellationTO, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("STOCKCANCELLATION :: SAPIntegrationDAOImpl :: findStkCancellationDtlsFromStaging :: start");
		List<SAPStockCancellationDO> sapStkCancellationDOList = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_GET_STOCK_CANCEL_FROM_STAGING);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					stockCancellationTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			sapStkCancellationDOList = query.list();
		} catch (Exception e) {
			logger.error(
					"STOCKCANCELLATION :: Exception In :: SAPIntegrationDAOImpl :: findStkCancellationDtlsFromStaging ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("STOCKCANCELLATION :: SAPIntegrationDAOImpl::findStkCancellationDtlsFromStaging:: end");
		return sapStkCancellationDOList;
	}

	@Override
	public Long getStockReturnCount(String sapStatus) throws CGSystemException {
		logger.debug("STOCKRETURN :: SAPIntegrationDAOImpl :: getStockReturnCount :: start======>");
		Long count;
		try {
			String queryName = "getStockReturnCount";
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "sapStatus",
							sapStatus).get(0);
		} catch (Exception e) {
			logger.error("STOCKRETURN :: ERROR :: SAPIntegrationDAOImpl :: getStockReturnCount()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		logger.debug("STOCKRETURN :: SAPIntegrationDAOImpl :: getStockReturnCount :: End======>");
		return count;
	}

	@Override
	public Long getStockTransferCount(String sapStatus)
			throws CGSystemException {
		logger.debug("STOCKTRANSFER :: SAPIntegrationDAOImpl :: getStockTransferCount :: start======>");
		Long count;
		try {
			String queryName = "getStockTransferCount";
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "sapStatus",
							sapStatus).get(0);
		} catch (Exception e) {
			logger.error("STOCKTRANSFER :: ERROR :: SAPIntegrationDAOImpl :: getStockTransferCount()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		logger.debug("STOCKTRANSFER :: SAPIntegrationDAOImpl :: getStockTransferCount :: End======>");
		return count;
	}

	@Override
	public Long getCollectionDetailsCount(String status)
			throws CGSystemException {
		logger.debug("COLLECTION :: SAPIntegrationDAOImpl :: getCollectionDetailsCount :: start======>");
		Long count;
		try {
			String queryName = "getCollectionDetailsCount";
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "status", status)
					.get(0);
		} catch (Exception e) {
			logger.error("COLLECTION :: ERROR :: SAPIntegrationDAOImpl :: getCollectionDetailsCount()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		logger.debug("COLLECTION :: SAPIntegrationDAOImpl :: getCollectionDetailsCount :: End======>");
		return count;
	}

	@Override
	public List<SAPCollectionDO> findCollectionDetailsFromStaging(
			SAPCollectionTO collectionTO, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("COLLECTION :: SAPIntegrationDAOImpl :: findCollectionDetailsFromStaging :: start");
		List<SAPCollectionDO> sapCollDO = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_COLLECTION_DETAILS_FROM_STAGING);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					collectionTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			sapCollDO = query.list();
		} catch (Exception e) {
			logger.error(
					"COLLECTION :: Exception In :: SAPIntegrationDAOImpl :: findCollectionDetailsFromStaging ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("COLLECTION :: SAPIntegrationDAOImpl::findCollectionDetailsFromStaging:: end");
		return sapCollDO;
	}

	@Override
	public Long getExpenseCount(SAPExpenseTO expenseTo)
			throws CGSystemException {
		logger.debug("EXPENSE :: SAPIntegrationDAOImpl :: getExpenseCount :: start======>");
		Long count;
		try {
			String queryName = SAPIntegrationConstants.QRY_PARAM_EXPENSE_DETAILS_COUNT;
			String paramNames[] = { SAPIntegrationConstants.DT_SAP_OUTBOUND,
					SAPIntegrationConstants.EXPENSE_STATUS,
					SAPIntegrationConstants.EXPENSE_OFC_RHO };
			Object paramValues[] = { expenseTo.getSapStatus(),
					expenseTo.getStatus(), expenseTo.getReportingRHOID() };

			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, paramNames,
							paramValues).get(0);
		} catch (Exception e) {
			logger.error("EXPENSE :: ERROR :: SAPIntegrationDAOImpl :: getExpenseCount()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		logger.debug("EXPENSE :: SAPIntegrationDAOImpl :: getExpenseCount :: End======>");
		return count;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SAPExpenseDO> findExpenseDetailsFromStaging(
			SAPExpenseTO expenseTO, Long maxDataCountLimit)
			throws CGSystemException {
		logger.debug("EXPENSE :: SAPIntegrationDAOImpl::findExpenseDtlsFromStaging:: start======>");
		List<SAPExpenseDO> sapExpenseDO = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_GET_EXPENSE_DETAILS_FROM_STAGING);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					expenseTO.getSapStatus());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			sapExpenseDO = query.list();
		} catch (Exception e) {
			logger.error(
					"EXPENSE :: Exception In :: SAPIntegrationDAOImpl :: findCollectionDetailsFromStaging ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("EXPENSE :: SAPIntegrationDAOImpl::findExpenseDtlsFromStaging:: end======>");
		return sapExpenseDO;
	}

	@Override
	public boolean updateDetails(List<CGBaseDO> updateSAPBillSalesOrderDOs)
			throws CGSystemException {
		logger.debug("SALES ORDER :: SAPIntegrationDAOImpl::updateDetails:: start======>");
		boolean isUpdated = false;
		//List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		try {
			for (CGBaseDO baseDO : updateSAPBillSalesOrderDOs) {
				getHibernateTemplate().merge(baseDO);
			}
			/*getHibernateTemplate().saveOrUpdateAll(updateSAPBillSalesOrderDOs);*/
			isUpdated = true;
		} catch (Exception ex) {
			logger.error(
					"Exception In :: SAPIntegrationDAOImpl :: updateEmployeeDetails :: Error",
					ex);
		}
		
/*		for (CGBaseDO baseDO : updateSAPBillSalesOrderDOs) {
			try {
				getHibernateTemplate().merge(baseDO);
				isUpdated = true;
			} catch (Exception ex) {
				logger.error(
						"Exception In :: SAPIntegrationDAOImpl :: updateEmployeeDetails :: Error",
						ex);
				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(ex.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(ex.getCause().getCause()
							.getMessage());
				}
				SAPBillSalesOrderDO sapBillSalesOrderDO = (SAPBillSalesOrderDO) baseDO;
				if (!StringUtil.isStringEmpty(sapBillSalesOrderDO.getBillNo())) {
					errorTO.setTransactionNo(sapBillSalesOrderDO.getBillNo());
				}
				sapErroTOlist.add(errorTO);
			}
		}
		if (!CGCollectionUtils.isEmpty(sapErroTOlist)){
			sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Bill No Error Records");
		}*/
		logger.debug("SALES ORDER :: SAPIntegrationDAOImpl::updateDetails:: end======>");
		return isUpdated;
	}

	@Override
	public boolean updateDetailsOfMaterial(List<CSDSAPItemDO> updateMaterialDOs)
			throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl::updateDetails:: start======>");
		boolean isUpdated = false;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		for (CGBaseDO baseDO : updateMaterialDOs) {
			try {
				getHibernateTemplate().merge(baseDO);
			} catch (Exception ex) {
				logger.error("ITEM :: SAPIntegrationDAOImpl :: Error", ex);
				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time
				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(ex.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(ex.getCause().getCause()
							.getMessage());
				}
				CSDSAPItemDO itemDO = (CSDSAPItemDO) baseDO;
				if (!StringUtil.isNull(itemDO)
						&& !StringUtil.isStringEmpty(itemDO.getItemCode())) {
					errorTO.setTransactionNo(itemDO.getItemCode());
				}
				sapErroTOlist.add(errorTO);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Material Master Error Records");
		isUpdated = true;
		logger.debug("SAPIntegrationDAOImpl::updateDetails:: end======>");
		return isUpdated;
	}

	@Override
	public Long getCODLCDeliveredStagingCount(SAPLiabilityEntriesTO sapCODLCTO)
			throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: getCODLCDeliveredStagingCount :: start======>");
		Long count;
		try {
			String queryName = "getCODLCDeliveredStagingCount";
			String paramNames[] = { SAPIntegrationConstants.DT_SAP_OUTBOUND,
					SAPIntegrationConstants.STATUS_FLAG };
			Object paramValues[] = { sapCODLCTO.getSapStatus(),
					sapCODLCTO.getStatusFlag() };
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, paramNames,
							paramValues).get(0);
		} catch (Exception e) {
			logger.error("ERROR :: SAPIntegrationDAOImpl :: getCODLCDeliveredStagingCount()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		logger.debug("SAPIntegrationDAOImpl :: getCODLCDeliveredStagingCount :: End======>");
		return count;
	}

	@Override
	public List<SAPLiabilityEntriesDO> getCODLCDeliveredStaging(
			SAPLiabilityEntriesTO sapCODLCTO, Long maxDataCountLimit) {
		logger.debug("SAPIntegrationDAOImpl :: getCODLCDeliveredStaging :: start");
		List<SAPLiabilityEntriesDO> sapCODLCDO = null;
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("getCODLCDeliveredStaging");
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					sapCODLCTO.getSapStatus());
			query.setParameter(SAPIntegrationConstants.STATUS_FLAG,
					sapCODLCTO.getStatusFlag());
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			sapCODLCDO = query.list();
		} catch (Exception e) {
			logger.error(
					"Exception In :: SAPIntegrationDAOImpl :: getCODLCDeliveredStaging ::",
					e);
		} finally {
			closeSession(session);
		}
		logger.debug("SAPIntegrationDAOImpl :: getCODLCDeliveredStaging :: end ");
		return sapCODLCDO;
	}

	@Override
	public String getDeliveryDetails(String consignmentNumber)
			throws CGSystemException {
		logger.debug("CODLC DELIVERED :: SAPIntegrationDAOImpl :: getDeliveryDetails :: start");
		String deliveryOfcCode = null;
		try {
			String queryName = SAPIntegrationConstants.QRY_PARAM_GET_DELIVERY_DETAILS;
			deliveryOfcCode = (String) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName,
							SAPIntegrationConstants.PARAM_CONSIGNMENT_NUMBER, consignmentNumber).get(0);
		} catch (Exception e) {
			logger.debug(
					"CODLC DELIVERED :: Error IN SAPIntegrationDAOImpl :: getDeliveryDetails :: ", e);
		}
		logger.debug("CODLC DELIVERED :: SAPIntegrationDAOImpl :: getDeliveryDetails :: End");
		return deliveryOfcCode;
	}

	@Override
	public void saveMisrouteEntry(SAPLiabilityEntriesDO sapCODLCDO)
			throws CGSystemException {
		logger.debug("CODLC DELIVERED :: SAPIntegrationDAOImpl :: saveMisrouteEntry :: start======>");
		// List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		Session session = null;
		Transaction tx = null;
		try {
			session = createSession();
			tx = session.beginTransaction();
			
			// Updating CONSG_DELIVERED = "D" in staging table
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_CONSG_DELIVERED_STATUS_IN_STAGING;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = Calendar.getInstance().getTime();
			String dateStamp = df.format(today);
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.CONSG_DELIVERED, sapCODLCDO.getConsgDelivered());
			qry.setString(SAPIntegrationConstants.UPDATED_DATE, dateStamp);
			qry.setInteger(SAPIntegrationConstants.UPDATED_BY, SAPIntegrationConstants.SAP_USER_ID);
			qry.setString(SAPIntegrationConstants.CONSG_NUMBER, sapCODLCDO.getConsgNo());
			int count = qry.executeUpdate();
			if(count > 0){
				// Creating a new misroute entry in staging table
				sapCODLCDO.setUpdatedBy(SAPIntegrationConstants.SAP_USER_ID);
				sapCODLCDO.setUpdatedDate(today);
				session.save(sapCODLCDO);
			}
			tx.commit();
		} catch (Exception e) {
			logger.error("CODLC DELIVERED :: Exception In :: SAPIntegrationDAOImpl :: saveMisrouteEntry ::",e);
			tx.rollback();
			
			/*SAPErrorTO errorTO = new SAPErrorTO();
			if (!StringUtil.isStringEmpty(e.getCause().getCause().getMessage())) {
				errorTO.setErrorMessage(e.getCause().getCause().getMessage());
			}
			if (!StringUtil.isStringEmpty(sapCODLCDO.getConsgNo())) {
				errorTO.setTransactionNo(sapCODLCDO.getConsgNo());
			}
			sapErroTOlist.add(errorTO);
			
			sendSapIntgErrorMail(sapErroTOlist,
					SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
					"Misroute Entry Error Records");*/
		}
		finally{
			closeSession(session);
		}
		logger.debug("CODLC DELIVERED :: SAPIntegrationDAOImpl :: saveMisrouteEntry :: end======>");
	}

	@Override
	public boolean updateVendorDetails(
			List<CSDSAPLoadMovementVendorDO> updateVendorDOs) {
		logger.debug("VENDOR :: SAPIntegrationDAOImpl :: updateVendorDetails :: start======>");
		boolean isUpdated = false;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		for (CSDSAPLoadMovementVendorDO baseDO : updateVendorDOs) {
			try {
				getHibernateTemplate().merge(baseDO);
			} catch (Exception ex) {
				logger.error(
						"VENDOR :: Exception In :: SAPIntegrationDAOImpl :: Error",
						ex);
				// 2 A
				// Updating status and time stamp in CSD Table if Data
				// successfully saved t o Staging Table
				// if flag = true status = C and Time stamp = current time
				// if flag = false status = N and time Stamp = current Time
				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(ex.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(ex.getCause().getCause()
							.getMessage());
				}
				if (!StringUtil.isStringEmpty(baseDO.getVendorCode())) {
					errorTO.setTransactionNo(baseDO.getVendorCode());
				}
				sapErroTOlist.add(errorTO);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Vendor Error Records");
		isUpdated = true;
		logger.debug("VENDOR :: SAPIntegrationDAOImpl :: updateVendorDetails :: end======>");
		return isUpdated;
	}

	@Override
	public boolean updateCODLCStagingConsignmentStaus(String bookingStartDate,
			String bookingEndDate) {
		logger.debug("CONSG POSTING :: SAPIntegrationDAOImpl :: updateCODLCStagingConsignmentStaus::=======>Start");
		Session session = null;
		boolean isUpdate = false;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_COD_LC_CONSG_STAGING_STATUS;
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.BOOKING_START_DATE,
					bookingStartDate);
			qry.setString(SAPIntegrationConstants.BOOKING_END_DATE,
					bookingEndDate);
			qry.executeUpdate();
			isUpdate = true;
		} catch (Exception e) {
			logger.error("CONSG POSTING :: SAPIntegrationDAOImpl :: updateCODLCStagingConsignmentStaus:: Exception  "
					+ e.getLocalizedMessage());
		} finally {
			closeSession(session);
		}
		logger.debug("CONSG POSTING :: SAPIntegrationDAOImpl :: updateCODLCStagingConsignmentStaus::=======>END");
		return isUpdate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SAPDeliveryCommissionCalcDO> getSAPDlvCommissionDtls()
			throws CGSystemException {
		logger.trace("SAPIntegrationDAOImpl :: getSAPDlvCommissionDtls() :: START");
		List<SAPDeliveryCommissionCalcDO> sapDlvCommDOs = null;
		try {
			String queryName = SAPIntegrationConstants.QRY_GET_SAP_DLV_COMM_DTLS;
			String[] paramNames = { SAPIntegrationConstants.DT_SAP_OUTBOUND };
			Object[] values = { SAPIntegrationConstants.SAP_STATUS };
			sapDlvCommDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, paramNames,
							values);
		} catch (Exception e) {
			logger.trace(
					"Exception occurs in SAPIntegrationDAOImpl :: getSAPDlvCommissionDtls() :: EXCEPTION :: ",
					e);
			throw new CGSystemException(e);
		}
		logger.trace("SAPIntegrationDAOImpl :: getSAPDlvCommissionDtls() :: END");
		return sapDlvCommDOs;
	}

	@Override
	public void saveOrUpdateSAPDlvCommissionDtls(
			SAPDeliveryCommissionCalcDO sapDlvCommDO) throws CGSystemException {
		logger.trace("SAPIntegrationDAOImpl :: saveOrUpdateSAPDlvCommissionDtls() :: START");
		try {
			getHibernateTemplate().saveOrUpdate(sapDlvCommDO);
		} catch (Exception e) {
			logger.trace(
					"Exception occurs in SAPIntegrationDAOImpl :: saveOrUpdateSAPDlvCommissionDtls() :: EXCEPTION :: ",
					e);
			throw new CGSystemException(e);
		}
		logger.trace("SAPIntegrationDAOImpl :: saveOrUpdateSAPDlvCommissionDtls() :: END");
	}

	@Override
	public boolean updateCustStatus(CSDSAPCustomerDO custNewDO)
			throws CGSystemException {
		logger.debug("CUSTOMER :: SAPIntegrationDAOImpl :: updateCustStatus :: Start");
		boolean isUpdated = false;
		Session session = null;
		try {
			session = createSession();
			String queryName = "updateCustomerStatus";
			Query qry = session.getNamedQuery(queryName);
			qry.setString(SAPIntegrationConstants.CURRENT_STATUS,
					custNewDO.getCurrentStatus());
			logger.debug("CUSTOMER :: Cust Table Current Status ---> "
					+ custNewDO.getCurrentStatus());
			qry.setLong(SAPIntegrationConstants.CUSTOMER_ID,
					custNewDO.getCustomerId());
			qry.setDate(SAPIntegrationConstants.UPDATED_DATE,
					DateUtil.getCurrentDate());
			logger.debug("CUSTOMER :: Cust Table Cust ID---> "
					+ custNewDO.getCustomerId());
			qry.executeUpdate();
			isUpdated = true;
		} catch (Exception e) {
			isUpdated = false;
			logger.error(
					"CUSTOMER :: SAPIntegrationDAOImpl :: updateCustNoAgaistContarct:: Exception  ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("CUSTOMER :: SAPIntegrationDAOImpl :: updateCustNoAgaistContarct :: END");
		return isUpdated;

	}

	@Override
	public void sendSapIntgErrorMailForPickUp(List<SAPErrorTO> sapErroTOlist,
			String templateName, String message) {
		// TODO Auto-generated method stub
		if (!StringUtil.isEmptyList(sapErroTOlist)) {
			MailSenderTO mailTO = new MailSenderTO();
			String[] to = { getEmailIdForErrorRecord() };
			mailTO.setTo(to);
			mailTO.setMailSubject(message);
			Map<Object, Object> templateVariables = new HashMap<Object, Object>();
			templateVariables.put("sapErroTOlist", sapErroTOlist);
			mailTO.setTemplateName(templateName);
			mailTO.setTemplateVariables(templateVariables);
			emailSenderUtil.sendEmail(mailTO);
		}
	}

	@Override
	public boolean saveCustomerDetails(List<CSDSAPCustomerDO> custDO) {
		logger.debug("CUSTOMER :: SAPIntegrationDAOImpl :: saveCustomerDetails :: start======>");
		boolean isSaved = false;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		for (CSDSAPCustomerDO custmerDO : custDO) {
			try {
				getHibernateTemplate().save(custmerDO);
				isSaved = true;
				logger.debug("Cust DO ID ------>" + custmerDO.getCustomerId());
			} catch (Exception ex) {
				logger.error(
						"Exception In :: SAPIntegrationDAOImpl :: saveCustomerDetails :: Error",
						ex);
				SAPErrorTO errorTO = new SAPErrorTO();
				if (!StringUtil.isStringEmpty(ex.getCause().getCause()
						.getMessage())) {
					errorTO.setErrorMessage(ex.getCause().getCause()
							.getMessage());
				}
				if (!StringUtil.isStringEmpty(custmerDO.getCustomerCode())) {
					errorTO.setTransactionNo(custmerDO.getCustomerCode());
				}
				sapErroTOlist.add(errorTO);
			}
		}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"Customer Error Records");
		logger.debug("CUSTOMER :: SAPIntegrationDAOImpl :: saveCustomerDetails :: end======>");
		return isSaved;
	}

	@Override
	public void saveSapCustomerDOs(List<SAPCustomerDO> sapCustomerDOs)
			throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: saveSapCustomerDOs() :: START");
		try {
			getHibernateTemplate().saveOrUpdateAll(sapCustomerDOs);
		} catch (Exception e) {
			logger.error("SAPIntegrationDAOImpl :: saveSapCustomerDOs() :: ERROR",e);
			throw new CGSystemException(e);
		}
		logger.debug("SAPIntegrationDAOImpl :: saveSapCustomerDOs() :: END");
	}

	@Override
	public void saveSapCustomerDO(SAPCustomerDO sapCustomerDO)
			throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: saveSapCustomerDO() :: START");
		try {
			getHibernateTemplate().saveOrUpdate(sapCustomerDO);
		} catch (Exception e) {
			logger.error("SAPIntegrationDAOImpl :: saveSapCustomerDO() :: ",e);
			throw new CGSystemException(e);
		}
		logger.debug("SAPIntegrationDAOImpl :: saveSapCustomerDO() :: END");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SAPCustomerDO> getPendingSapCustomerList(String sapInboundStatus)
			throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: getPendingSapCustomerList() :: START");
		List<SAPCustomerDO> pendindSapCustDOs = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(SAPCustomerDO.class, "sapCustomer");
			cr.add(Restrictions.eq("sapCustomer.sapInbound", sapInboundStatus));
			pendindSapCustDOs = cr.list();
		} catch (Exception e) {
			logger.error("SAPIntegrationDAOImpl :: getPendingSapCustomerList() :: ", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("SAPIntegrationDAOImpl :: getPendingSapCustomerList() :: END");
		return pendindSapCustDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void searchAlreadySavedSAPCustDtls(List<SAPCustomerDO> sapCustomerDOs)
			throws CGSystemException {
		logger.debug("CUSTOMER :: SAPIntegrationDAOImpl :: searchAlreadySavedSAPCustDtls() :: START");
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			for (SAPCustomerDO sapCustomerDO : sapCustomerDOs) {
				cr = session.createCriteria(SAPCustomerDO.class, "sapCustomer");
				if (!StringUtil.isStringEmpty(sapCustomerDO.getCustomerNo())) {
					cr.add(Restrictions.eq("sapCustomer.customerNo",
							sapCustomerDO.getCustomerNo()));
				} else if (!StringUtil.isStringEmpty(sapCustomerDO
						.getContarctNo())) {
					cr.add(Restrictions.eq("sapCustomer.contarctNo",
							sapCustomerDO.getContarctNo()));
				} else {
					continue;
					// Never execute - Dead code snippet
				}
				List<SAPCustomerDO> savedSAPCustDOs = cr.list();
				if (!CGCollectionUtils.isEmpty(savedSAPCustDOs)) {
					SAPCustomerDO savedSAPCustDO = savedSAPCustDOs.get(0);
					sapCustomerDO.setId(savedSAPCustDO.getId());
				}
				sapCustomerDO.setSapInbound("N");
			}
		} catch (Exception e) {
			logger.error(
					"CUSTOMER :: SAPIntegrationDAOImpl :: searchAlreadySavedSAPCustDtls() :: ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("CUSTOMER :: SAPIntegrationDAOImpl :: searchAlreadySavedSAPCustDtls() :: END");
	}

	@SuppressWarnings("unchecked")
	@Override
	public BookingDO getBookingDtlsByConsgNo(String consgNumber)
			throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: getBookingByConsgNo() :: START");
		List<BookingDO> bookingDOList = null;
		BookingDO bookingDO = null;
		try {
			bookingDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getBookingDtlsByConsgNo",
							"consgNumber", consgNumber);
			if (!StringUtil.isEmptyColletion(bookingDOList)) {
				bookingDO = bookingDOList.get(0);
			}
		} catch (Exception e) {
			logger.error(
					"Error occured in SAPIntegrationDAOImpl :: getBookingByConsgNo()..:",
					e);
			throw new CGSystemException(e);
		}
		logger.debug("SAPIntegrationDAOImpl :: getBookingByConsgNo() :: END");
		return bookingDO;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Long getBCSDtlsCountFromStaging(
			SAPBillingConsgSummaryTO sapBillConsgSummaryTO)
			throws CGSystemException {

		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: getBCSDtlsCountFromStaging :: start");
		Long count = 0L;
		List<Long> countLong = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("getBCSCountFromStaging");
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,
					sapBillConsgSummaryTO.getSapStatus());
			countLong = query.list();
			if (!CGCollectionUtils.isEmpty(countLong) && countLong.get(0) > 0L) {
				count = countLong.get(0);
			}

		} catch (Exception e) {
			logger.error(
					"BillingSummaryCreationScheduler - for Summary scheduler :: Exception In :: SAPIntegrationDAOImpl :: getBCSDtlsCountFromStaging ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: getBCSDtlsCountFromStaging :: end");
		return count;

	}

	@Override
	public boolean UpdateConsgnBillStatusByConsgnNo(List<String> consgNo,
			String billingStatus) throws CGSystemException {
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: UpdateConsgnBillStatusByConsgnNo :: START");
		Session session = null;
		Query query = null;
		boolean result = false;
		try {
			session = createSession();
			query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_UPDATE_BILLING_STATUS);
			query.setString(SAPIntegrationConstants.BILLING_STATUS_RTB,
					billingStatus);
			query.setParameterList(SAPIntegrationConstants.CONSIGNMENT_NO,
					consgNo);
			query.setTimestamp(SAPIntegrationConstants.UPDATED_DATE, Calendar
					.getInstance().getTime());
			query.setInteger(SAPIntegrationConstants.UPDATED_BY, 4);
			int i = query.executeUpdate();
			if (i > 0)
				result = Boolean.TRUE;
		} catch (Exception e) {
			logger.error(
					"BillingSummaryCreationScheduler - for Summary scheduler :: Exception In :: SAPIntegrationDAOImpl :: UpdateConsgnBillStatusByConsgnNo ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: UpdateConsgnBillStatusByConsgnNo :: END");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ConsignmentBookingBillingMappingDO getConsignmentBookingMappingByConsgNo(
			String consgNo) throws CGSystemException {
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: getConsignmentBookingMappingByConsgNo() :: Start --------> ::::::");
		ConsignmentBookingBillingMappingDO consignmentBookingBillingMappingDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(
					ConsignmentBookingBillingMappingDO.class,
					"consignmentBookingBillingMappingDO");
			cr.add(Restrictions.eq("consignmentBookingBillingMappingDO.consgNumber",
					consgNo));
			List<ConsignmentBookingBillingMappingDO> consignmentBookingBillingMappingDOs = (List<ConsignmentBookingBillingMappingDO>) cr
					.list();
			if (!StringUtil
					.isEmptyColletion(consignmentBookingBillingMappingDOs)) {
				consignmentBookingBillingMappingDO = consignmentBookingBillingMappingDOs
						.get(0);
			}
		} catch (Exception e) {
			logger.error("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: getConsignmentBookingMappingByConsgNo()::::::"
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl: getConsignmentBookingMappingByConsgNo(): END");
		return consignmentBookingBillingMappingDO;
	}

	@Override
	public boolean saveOrUpdateConsignmentBookingMapping(Collection<ConsignmentBookingBillingMappingDO> consgnBookingMappDOs)
			throws CGSystemException {
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl:: saveOrUpdateConsignmentBookingMapping() :: START");
		boolean result = Boolean.FALSE;
		try {
			getHibernateTemplate().saveOrUpdateAll(consgnBookingMappDOs);
			result = Boolean.TRUE;
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl:: saveOrUpdateConsignmentBookingMapping() :: Total Record Saved " + consgnBookingMappDOs.size());
		} catch (Exception e) {
			logger.error(
					"BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl:: saveOrUpdateConsignmentBookingMapping() ::",
					e);
			throw new CGSystemException(e);
		}
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl:: saveOrUpdateConsignmentBookingMapping() :: END");
		return result;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void bulkSaveOrUpdateConsgRate(
			Map<String, ConsignmentBillingRateDO> calcCNRateDOsMap)
			throws CGSystemException {
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: bulkSaveOrUpdateConsgRate() :: Start --------> ::::::");
		Session session = null;
		Transaction tx = null;
		Query query = null;
		Set<String> consgNos = null;
		Criteria cr = null;
		//List<ConsignmentBillingRateDO> consignmentBillingRateDOs = null;
		List<String> eligibleForRTB = new ArrayList<String>();
		List<String> eligibleForRRB = new ArrayList<String>();
		try {
			session = createSession();
			tx = session.beginTransaction();
			//consignmentBillingRateDOs = (List<ConsignmentBillingRateDO>) calcCNRateDOsMap.values();
			if (!StringUtil.isEmptyColletion(calcCNRateDOsMap.values())){
				getHibernateTemplate().saveOrUpdateAll(calcCNRateDOsMap.values());
				logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: bulkSaveOrUpdateConsgRate() :: Total Rates Saved --------> ::::::" + calcCNRateDOsMap.size());
			}
			// Updating flag billingstatus into consignment table
			if (!StringUtil.isEmptyColletion(calcCNRateDOsMap.keySet())) {
				consgNos = calcCNRateDOsMap.keySet();
			}
			// Get Consignments
			cr = session.createCriteria(ConsignmentDO.class, "consgDO");
			cr.add(Restrictions.in("consgDO.consgNo", consgNos));
			/*cr.setProjection( Projections.projectionList()
				    .add( Projections.property("consgDO.consgNo"))
				    .add( Projections.property("consgDO.billingStatus")));*/
			List<ConsignmentDO> consignmentDOs = (List<ConsignmentDO>) cr
					.list();
			if (!StringUtil.isEmptyColletion(consignmentDOs)) {
				for (ConsignmentDO consigDO : consignmentDOs) {
					String billStatus = consigDO.getBillingStatus();
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: bulkSaveOrUpdateConsgRate() :: Consign No =====> ::::::"
							+ consigDO.getConsgNo()
							+ " billStatus :: "
							+ billStatus);
					if (StringUtil.isNull(billStatus)
							|| !billStatus.equals("TRB")) {
						eligibleForRTB.add(consigDO.getConsgNo());
					} else {
						eligibleForRRB.add(consigDO.getConsgNo());
					}
				}
			}
 
			if (!CGCollectionUtils.isEmpty(eligibleForRTB)) {
				query = session
						.getNamedQuery(SAPIntegrationConstants.QRY_UPDATE_BILLING_STATUS);
				query.setString(SAPIntegrationConstants.BILLING_STATUS_RTB,
						SAPIntegrationConstants.RTB_STATUS);
				query.setParameterList(
						SAPIntegrationConstants.CONSIGNMENT_NO, eligibleForRTB);
				query.setTimestamp(SAPIntegrationConstants.UPDATED_DATE,
						Calendar.getInstance().getTime());
				query.setInteger(SAPIntegrationConstants.UPDATED_BY, 4);
				query.executeUpdate();
				logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: bulkSaveOrUpdateConsgRate() :: Total number of Consignment made RTB =====> ::::::"
						+ eligibleForRTB.size());
			}
			
			if (!CGCollectionUtils.isEmpty(eligibleForRRB)) {
				query = session
						.getNamedQuery(SAPIntegrationConstants.QRY_UPDATE_BILLING_STATUS);
				query.setString(SAPIntegrationConstants.BILLING_STATUS_RTB,
						SAPIntegrationConstants.RRB_STATUS);
				query.setParameterList(
						SAPIntegrationConstants.CONSIGNMENT_NO, eligibleForRRB);
				query.setTimestamp(SAPIntegrationConstants.UPDATED_DATE,
						Calendar.getInstance().getTime());
				query.setInteger(SAPIntegrationConstants.UPDATED_BY, 4);
				query.executeUpdate();
				logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: bulkSaveOrUpdateConsgRate() :: Total number of Consignment made RRB =====> ::::::"
						+ eligibleForRRB.size());
			}
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			logger.error("BillingSummaryCreationScheduler - for Summary scheduler :: Exception Occured in::SAPIntegrationDAOImpl::bulkSaveOrUpdateConsgRate() :: "
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: bulkSaveOrUpdateConsgRate() :: End --------> ::::::");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookingDO> getBookingDtlsByConsgNos(List<String> consignNos)
			throws CGSystemException {
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl: getBookingDtlsByConsgNos(): START");
		Session session = null;
		Criteria cr = null;
		List<BookingDO> bookingDOs = null;
		try {
			session = createSession();
			cr = session.createCriteria(BookingDO.class, "bookingDO");
			cr.add(Restrictions.in("bookingDO.consgNumber", consignNos));
			bookingDOs = (List<BookingDO>) cr.list();
		} catch (Exception e) {
			logger.error("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: getBookingDtlsByConsgNos()::::::"
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl: getBookingDtlsByConsgNos(): END");
		return bookingDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsignmentBookingBillingMappingDO> getConsignmentBookingMappingByConsgNos(
			List<String> consignNos) throws CGSystemException {
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: getConsignmentBookingMappingByConsgNos() :: Start --------> ::::::");
		Session session = null;
		Criteria cr = null;
		List<ConsignmentBookingBillingMappingDO> consignmentBookingBillingMappingDOs =  null;
		try {
			session = createSession();
			cr = session.createCriteria(
					ConsignmentBookingBillingMappingDO.class,
					"consignmentBookingBillingMappingDO");
			cr.add(Restrictions.in("consignmentBookingBillingMappingDO.consgNumber",
					consignNos));
			consignmentBookingBillingMappingDOs = (List<ConsignmentBookingBillingMappingDO>) cr
					.list();
		} catch (Exception e) {
			logger.error("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: getConsignmentBookingMappingByConsgNos()::::::"
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl: getConsignmentBookingMappingByConsgNos(): END");
		return consignmentBookingBillingMappingDOs;
	}
	
	
	@Override
	public void updateSapStatusAndConsigneeDateForConsgInterface(CollectionDtlsDO collnDtlsDO, SAPLiabilityEntriesDO sapCODLCDO)
			throws CGSystemException {
		logger.debug("CODLC CONSIGNEE :: SAPIntegrationDAOImpl :: updateSapStatusAndConsigneeDateForConsgInterface :: START");
		Session session = null;
		Transaction tx = null;
		int count = 0;
		try{
			session = createSession();
			tx = session.beginTransaction();

			// Updating CONSIGNEE_DATE in staging table
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_CONSIGNEE_DATE_IN_STAGING;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (!StringUtil.isNull(sapCODLCDO)) {
				Date today = Calendar.getInstance().getTime();
				String dateStamp = df.format(today);
				Query qry = session.getNamedQuery(queryName);
				qry.setDate(SAPIntegrationConstants.CONSIGNEE_DATE, sapCODLCDO.getConsigneeDate());
				qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND, sapCODLCDO.getSapStatus());
				qry.setString(SAPIntegrationConstants.UPDATED_DATE, dateStamp);
				qry.setInteger(SAPIntegrationConstants.UPDATED_BY, SAPIntegrationConstants.SAP_USER_ID);
				qry.setString(SAPIntegrationConstants.CONSG_NUMBER, sapCODLCDO.getConsgNo());
				count = qry.executeUpdate();
			}
			
			// Updating DT_SAP_OUTBOUND = "C" in ff_f_collection_entries table
			if (!StringUtil.isNull(collnDtlsDO) && (count > 0)) {
				queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_CONSG_COLL_STATUS;
				Query qry = session.getNamedQuery(queryName);
				qry.setString(SAPIntegrationConstants.DT_SAP_OUTBOUND, SAPIntegrationConstants.SAP_STATUS_C);
				qry.setInteger(SAPIntegrationConstants.COLLECTION_ENTRY_ID, collnDtlsDO.getEntryId());
				qry.executeUpdate();
			}
			
			tx.commit();
		}
		catch(Exception e){
			logger.error("CODLC CONSIGNEE :: SAPIntegrationDAOImpl :: updateSapStatusAndConsigneeDateForConsgInterface :: Exception : ", e);
			tx.rollback();
		}
		finally{
			closeSession(session);
		}
		logger.debug("CODLC CONSIGNEE :: SAPIntegrationDAOImpl :: updateSapStatusAndConsigneeDateForConsgInterface :: END");
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean ba_billing_consolidation_Proc() {
		logger.debug("SAPIntegrationDAOImpl :: billing_consolidation_Proc() :: Start --------> ::::::");
		Session session = null;
		boolean flag = false;
		try {
			session = createSession();
			Query q = session.getNamedQuery(SAPIntegrationConstants.BA_BILLING_CONSOLIDATION_PROC);
			List list = q.list();
			if (list.get(0) instanceof String) {
				logger.error("SAPIntegrationDAOImpl: billing_consolidation_Proc: Stock billing_consolidation SP Result:"+ list);
			} else {
				for (int i = 0; i < list.size(); i++) {
					Object[] row = (Object[]) list.get(i);
					if (row[0] != null) {
						logger.error("SAPIntegrationDAOImpl: billing_consolidation_Proc: billing_consolidation SP Result:"
								+ row[0]);
					}
					if (row[1] != null) {
						logger.error("SAPIntegrationDAOImpl: billing_consolidation_Proc: billing_consolidation SP Result:"
								+ row[1]);
					}
					if (row[2] != null) {
						logger.error("SAPIntegrationDAOImpl: billing_consolidation_Proc: billing_consolidation SP Result:"
								+ row[2]);
					}
				}
			}
			flag = true;
		} catch (Exception ex) {
			flag = false;
			logger.error("Error occured SAPIntegrationDAOImpl ::  billing_consolidation_Proc"
					+ ex);
			new CGSystemException(ex);
		} finally {
			closeSession(session);
		}
		logger.debug("SAPIntegrationDAOImpl: billing_consolidation_Proc(): END");
		return flag;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Integer getContractIdByContractNo(String contractNo)
			throws CGSystemException {
		logger.debug("SAPIntegrationDAOImpl :: getContractIdByContractNo :: START");
		List<Integer> rateContractIdList = null;
		Integer rateContractId = null;
		try{
			rateContractIdList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.QRY_GET_CONTRACT_ID_BY_CONTRACT_NO,
					UdaanCommonConstants.RATE_CONTRACT_NO, contractNo);
			if(!StringUtil.isEmptyColletion(rateContractIdList)){
				rateContractId = rateContractIdList.get(0);
			}
		}catch(Exception e){
			logger.error("SAPIntegrationDAOImpl :: getContractIdByContractNo :: ERROR ",e);
			throw new CGSystemException(e);
		}
		logger.debug("SAPIntegrationDAOImpl :: getContractIdByContractNo :: END");
		return rateContractId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SalesOrderInterfaceDO> getStockConsolidationDtls()
			throws CGSystemException {

		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: getStockConsolidationDtls :: Start");
		Session session = null;
		List<SalesOrderInterfaceDO> soDOList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createSQLQuery(SAPIntegrationConstants.QRY_GET_STOCK_CONSOLIDATION_DETAILS);
			query.setCacheable(false);
			List<SalesOrderInterfaceDO> salesOrderDtls = query.list();
			session.close();
			soDOList = prepareSODtlsList(salesOrderDtls);
		} catch (Exception e) {
			logger.error(
					"BillingSummaryCreationScheduler - for Summary scheduler :: Exception in :: SAPIntegrationDAOImpl :: getStockConsolidationDtls",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPIntegrationDAOImpl :: getStockConsolidationDtls :: End");
		return soDOList;
	
	}

	@Override
	public void saveOrUpdateSalesOrderInStaging(
			List<SAPBillSalesOrderStagingDO> dOList) throws CGSystemException {
		logger.trace("SAPIntegrationDAOImpl :: saveOrUpdateSalesOrderInStaging :: START");
		try {
			getHibernateTemplate().saveOrUpdateAll(dOList);
		} catch (Exception ex) {
			logger.error("SAPIntegrationDAOImpl :: saveOrUpdateSalesOrderInStaging :: Error",ex);
			throw new CGSystemException(ex);
		}
		logger.trace("SAPIntegrationDAOImpl :: saveOrUpdateSalesOrderInStaging :: END");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SAPBillSalesOrderStagingDO> getSalesOrderDataFromStaging()
			throws CGSystemException {
		logger.trace("SAPIntegrationDAOImpl :: getSalesOrderDataFromStaging :: START");
		Session session = null;
		List<SAPBillSalesOrderStagingDO> sapBillSalesOrderStagingDOs = null;
		try {
			session = createSession();
			Criteria criteria = session.createCriteria(SAPBillSalesOrderStagingDO.class, "sapBillSalesOrderStagingDO");
			criteria.add(Restrictions.eq("sapBillSalesOrderStagingDO.sapInbound", "N"));
			criteria.setMaxResults(10000);
			sapBillSalesOrderStagingDOs = (List<SAPBillSalesOrderStagingDO>) criteria.list();
			if (!StringUtil.isEmptyColletion(sapBillSalesOrderStagingDOs)) {
				logger.warn("SAPIntegrationDAOImpl :: getSalesOrderDataFromStaging :: Count of SAPBillSalesOrderStagingDOs = [" + 
						sapBillSalesOrderStagingDOs.size() + "]");
			}
		}
		catch (Exception e) {
			logger.error("SAPIntegrationDAOImpl :: getSalesOrderDataFromStaging :: ERROR", e);
			throw new CGSystemException(e);
		}
		finally {
			closeSession(session);
		}
		logger.trace("SAPIntegrationDAOImpl :: getSalesOrderDataFromStaging :: END");
		return sapBillSalesOrderStagingDOs;
	}

	@Override
	public Integer findApplicableContractForConsignment(String consgNumber)
			throws CGSystemException {

		logger.debug("SAPIntegrationDAOImpl :: findApplicableContractForConsignment :: START");
		Integer rateContractId = null;
		List<Integer> rateContracts=null;
		Session session =null;
		try {
			session = createSession();
			Query query=session.createSQLQuery(SAPIntegrationConstants.FIND_APPLICABLE_CONTRACT).addScalar("rateContractId", Hibernate.INTEGER);
			query.setString(0, consgNumber);
			rateContracts = query.list();
			if(!CGCollectionUtils.isEmpty(rateContracts)){
				rateContractId=rateContracts.get(0);
			}
		} catch (Exception e) {
			logger.error("SAPIntegrationDAOImpl :: findApplicableContractForConsignment()::::::"
 					+ e.getMessage());
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		logger.debug("SAPIntegrationDAOImpl :: findApplicableContractForConsignment :: END");
		return rateContractId;
	
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public boolean summary_staging_insertion_Proc() {
		logger.debug("SAPIntegrationDAOImpl :: summary_staging_insertion_Proc() :: Start --------> ::::::");
		Session session = null;
		boolean flag = false;
		try {
			session = createSession();
			Query q = session
					.getNamedQuery(SAPIntegrationConstants.SUMMARY_STAGING_INSERTION_PROC);
			List list = q.list();
			if (list.get(0) instanceof String) {
				logger.debug("SAPIntegrationDAOImpl: summary_staging_insertion_Proc: Stock billing_consolidation SP Result:" + list);
			} else {
				for (int i = 0; i < list.size(); i++) {
					Object[] row = (Object[]) list.get(i);
					if (row[0] != null) {
						logger.debug("SAPIntegrationDAOImpl: summary_staging_insertion_Proc: billing_consolidation SP Result:" + row[0]);
					}
					if (row[1] != null) {
						logger.debug("SAPIntegrationDAOImpl: summary_staging_insertion_Proc: billing_consolidation SP Result:" + row[1]);
					}
					if (row[2] != null) {
						logger.debug("SAPIntegrationDAOImpl: summary_staging_insertion_Proc: billing_consolidation SP Result:" + row[2]);
					}
				}
			}
			flag = true;
		} catch (Exception ex) {
			flag = false;
			logger.error("Error occured SAPIntegrationDAOImpl ::  summary_staging_insertion_Proc" + ex);
			new CGSystemException(ex);
		} finally {
			closeSession(session);
		}
		logger.debug("SAPIntegrationDAOImpl: summary_staging_insertion_Proc(): END");
		return flag;

	}

}
