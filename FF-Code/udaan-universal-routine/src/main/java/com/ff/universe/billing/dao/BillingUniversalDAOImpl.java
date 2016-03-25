package com.ff.universe.billing.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.billing.BillDO;
import com.ff.domain.billing.BillingConsignmentDO;
import com.ff.domain.billing.BillingConsignmentSummaryDO;
import com.ff.domain.billing.SAPBillSalesOrderDO;
import com.ff.universe.billing.constants.BillingUniversalConstants;

/**
 * The Class BillingUniversalDAOImpl.
 * 
 * @author narmdr
 */
public class BillingUniversalDAOImpl extends CGBaseDAO implements
		BillingUniversalDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BillingUniversalDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.billing.dao.BillingUniversalDAO#getBillsByCustomerId(
	 * java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillDO> getBillsByCustomerId(Integer customerId)
			throws CGSystemException {
		LOGGER.debug("BillingUniversalDAOImpl :: getBillsByCustomerId() :: Start --------> ::::::");
		List<BillDO> billDOs = null;
		try {
			billDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					BillingUniversalConstants.QRY_GET_BILLS_BY_CUSTOMER_ID,
					BillingUniversalConstants.PARAM_CUSTOMER_ID, customerId);

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::BillingUniversalDAOImpl::getBillsByCustomerId() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("BillingUniversalDAOImpl :: getBillsByCustomerId() :: End --------> ::::::");
		return billDOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.billing.dao.BillingUniversalDAO#getPaymentBillsByCustomerId
	 * (java.lang.Integer, java.lang.String[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillDO> getPaymentBillsByCustomerId(Integer customerId,
			String[] locationOperationType, Integer officeId)
			throws CGSystemException {
		LOGGER.debug("BillingUniversalDAOImpl :: getPaymentBillsByCustomerId() :: Start --------> ::::::");
		List<BillDO> billDOs = null;
		try {
			String[] params = { BillingUniversalConstants.PARAM_CUSTOMER_ID,
					BillingUniversalConstants.PARAM_LOCATION_OPT_TYPE,
					BillingUniversalConstants.PARAM_OFFICE_ID };
			Object[] values = { customerId, locationOperationType, officeId };
			billDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							BillingUniversalConstants.QRY_GET_PAYMENT_BILLS_BY_CUSTOMER_ID,
							params, values);

		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in BillingUniversalDAOImpl :: getPaymentBillsByCustomerId() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("BillingUniversalDAOImpl :: getPaymentBillsByCustomerId() :: End --------> ::::::");
		return billDOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.billing.dao.BillingUniversalDAO#getSAPBillsByCustomerId
	 * (java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillDO> getSAPBillsByCustomerId(Integer customerId,
			Integer officeId) throws CGSystemException {
		LOGGER.debug("BillingUniversalDAOImpl :: getSAPBillsByCustomerId() :: Start --------> ::::::");
		List<BillDO> billDOs = null;
		try {
			String[] params = { BillingUniversalConstants.PARAM_CUSTOMER_ID,
					BillingUniversalConstants.PARAM_OFFICE_ID };
			Object[] values = { customerId, officeId };
			billDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					BillingUniversalConstants.QRY_GET_SAP_BILLS_BY_CUSTOMER_ID,
					params, values);

		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in BillingUniversalDAOImpl :: getSAPBillsByCustomerId() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("BillingUniversalDAOImpl :: getSAPBillsByCustomerId() :: End --------> ::::::");
		return billDOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.billing.dao.BillingUniversalDAO#getBillsByInvoiceNos(
	 * java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillDO> getBillsByInvoiceNos(List<String> invoiceNumbers)
			throws CGSystemException {
		LOGGER.debug("BillingUniversalDAOImpl :: getBillsByInvoiceNos() :: Start --------> ::::::");
		List<BillDO> billDOs = null;
		try {
			billDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					BillingUniversalConstants.QRY_GET_BILLS_BY_INVOICE_NUMBERS,
					BillingUniversalConstants.PARAM_INVOICE_NUMBERS,
					invoiceNumbers);

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::BillingUniversalDAOImpl::getBillsByInvoiceNos() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("BillingUniversalDAOImpl :: getBillsByInvoiceNos() :: End --------> ::::::");
		return billDOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.billing.dao.BillingUniversalDAO#
	 * getBillsByShippedToCodeAndStartEndDate(java.lang.String, java.util.Date,
	 * java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillDO> getBillsByShippedToCodeAndStartEndDate(
			String shippedToCode, Date startDate, Date endDate)
			throws CGSystemException {
		LOGGER.debug("BillingUniversalDAOImpl :: getBillsByShippedToCodeAndStartEndDate() :: Start --------> ::::::");
		List<BillDO> billDOs = null;
		try {
			billDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							BillingUniversalConstants.QRY_GET_BILLS_BY_SHIPPED_TO_CODE_AND_START_END_DATE,
							new String[] {
									BillingUniversalConstants.PARAM_SHIPPED_TO_CODE,
									BillingUniversalConstants.PARAM_START_DATE,
									BillingUniversalConstants.PARAM_END_DATE },
							new Object[] { shippedToCode, startDate, endDate });

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::BillingUniversalDAOImpl::getBillsByShippedToCodeAndStartEndDate() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("BillingUniversalDAOImpl :: getBillsByShippedToCodeAndStartEndDate() :: End --------> ::::::");
		return billDOs;
	}

	@SuppressWarnings("unchecked")
	public List<BillDO> getBillsByCustomerId(Integer custId, Date startDate,
			Date endDate) throws CGSystemException {

		LOGGER.debug("BillingUniversalDAOImpl :: getBillsByShippedToCodeAndStartEndDate() :: Start --------> ::::::");
		List<BillDO> billDOs = null;
		try {
			billDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							BillingUniversalConstants.QRY_GET_BILLS_BY_CUSTOMER_AND_START_END_DATE,
							new String[] {
									BillingUniversalConstants.PARAM_CUSTOMER_ID,
									BillingUniversalConstants.PARAM_START_DATE,
									BillingUniversalConstants.PARAM_END_DATE },
							new Object[] { custId, startDate, endDate });

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::BillingUniversalDAOImpl::getBillsByShippedToCodeAndStartEndDate() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("BillingUniversalDAOImpl :: getBillsByShippedToCodeAndStartEndDate() :: End --------> ::::::");
		return billDOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.billing.dao.BillingUniversalDAO#
	 * getBillsByCustomerIdsAndStartEndDate(java.util.List, java.util.Date,
	 * java.util.Date, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillDO> getBillsByCustomerIdsAndStartEndDate(
			List<Integer> customerIds, String startDate, String endDate,
			final Integer productId, List<Integer> billingBrachs)
			throws CGSystemException {
		LOGGER.debug("BillingUniversalDAOImpl :: getBillsByCustomerIdsAndStartEndDate() :: Start --------> ::::::");
		List<BillDO> billDOs = null;
		Session session = null;
		try {
			/*
			 * billDOs = getHibernateTemplate() .findByNamedQueryAndNamedParam(
			 * BillingUniversalConstants
			 * .QRY_GET_BILLS_BY_CUSTOMER_IDS_AND_START_END_DATE, new String[] {
			 * BillingUniversalConstants.PARAM_CUSTOMER_IDS,
			 * BillingUniversalConstants.PARAM_START_DATE,
			 * BillingUniversalConstants.PARAM_END_DATE,
			 * BillingUniversalConstants.PARAM_PRODUCT_ID }, new Object[] {
			 * customerIds, startDate, endDate, productId });
			 */

			session = createSession();
			Query query = session
					.createSQLQuery(
							BillingUniversalConstants.GETBILLSBYCUSTOMERID)
					.addEntity(BillDO.class).setString("startDate", startDate)
					.setString("endDate", endDate)
					.setInteger("productId", productId)
					.setParameterList("customerId", customerIds)
					.setParameterList("billingOfficeId", billingBrachs);
			billDOs = query.list();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::BillingUniversalDAOImpl::getBillsByCustomerIdsAndStartEndDate() :: "
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("BillingUniversalDAOImpl :: getBillsByCustomerIdsAndStartEndDate() :: End --------> ::::::");
		return billDOs;
	}

	@SuppressWarnings("unchecked")
	public BillingConsignmentSummaryDO getBCSDtlsBySummaryID(
			Integer billingConsignmentId) throws CGSystemException {

		LOGGER.debug("BillingUniversalDAOImpl :: getBCSDtlsBySummaryID :: Start");
		List<BillingConsignmentSummaryDO> bcsDOs = null;
		BillingConsignmentSummaryDO bcsDO = null;
		try {
			bcsDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					BillingUniversalConstants.QRY_GET_BCS_DTLS_BY_SUMMARY_ID,
					BillingUniversalConstants.SUMMARY_ID, billingConsignmentId);
			if (!StringUtil.isEmptyColletion(bcsDOs)) {
				bcsDO = bcsDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in :: BillingUniversalDAOImpl :: getBCSDtlsBySummaryID() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("BillingUniversalDAOImpl :: getBCSDtlsBySummaryID :: End ");
		return bcsDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BillingConsignmentDO> getInvoiceAgainstSummary(
			Integer billingConsignmentId) throws CGSystemException {
		LOGGER.debug("BillingUniversalDAOImpl :: getInvoiceAgainstSummary :: Start");
		List<BillingConsignmentDO> billingConsgDOs = null;
		try {
			billingConsgDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							BillingUniversalConstants.QRY_GET_BILLING_CONSG_DTLS_BY_SUMMARY_ID,
							BillingUniversalConstants.SUMMARY_ID,
							billingConsignmentId);
		} catch (Exception e) {
			LOGGER.error("Exception Occured in :: BillingUniversalDAOImpl :: getInvoiceAgainstSummary() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("BillingUniversalDAOImpl :: getInvoiceAgainstSummary :: End ");
		return billingConsgDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SAPBillSalesOrderDO getSAPBillSalesOrderDetails(Integer summaryId)
			throws CGSystemException {
		LOGGER.debug("SALES ORDER :: BillingUniversalDAOImpl :: getSAPBillSalesOrderDetails :: Start");
		List<SAPBillSalesOrderDO> sapBillSalesOrderDOs = null;
		SAPBillSalesOrderDO sapBillSalesOrderDO = null;
		try {
			sapBillSalesOrderDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							BillingUniversalConstants.QRY_GET_SAP_BILL_SALES_ORDER_DTLS,
							BillingUniversalConstants.SAP_SUMMARY_ID, summaryId);
			if (!StringUtil.isEmptyColletion(sapBillSalesOrderDOs)) {
				sapBillSalesOrderDO = sapBillSalesOrderDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"SALES ORDER :: Exception Occured in :: BillingUniversalDAOImpl :: getSAPBillSalesOrderDetails() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("SALES ORDER :: BillingUniversalDAOImpl :: getSAPBillSalesOrderDetails :: End ");
		return sapBillSalesOrderDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SAPBillSalesOrderDO getSAPBillSalesOrderDetailsByBillNumber(
			String billNumber) throws CGSystemException {
		LOGGER.debug("BillingUniversalDAOImpl :: getSAPBillSalesOrderDetailsByBillNumber :: Start");
		List<SAPBillSalesOrderDO> sapBillSalesOrderDOs = null;
		SAPBillSalesOrderDO sapBillSalesOrderDO = null;
		try {
			sapBillSalesOrderDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							BillingUniversalConstants.QRY_GET_SAP_BILL_SALES_ORDER_DTLS_BY_BILL,
							BillingUniversalConstants.BILL_NUMBER, billNumber);
			if (!StringUtil.isEmptyColletion(sapBillSalesOrderDOs)) {
				sapBillSalesOrderDO = sapBillSalesOrderDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in :: BillingUniversalDAOImpl :: getSAPBillSalesOrderDetailsByBillNumber() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("BillingUniversalDAOImpl :: getSAPBillSalesOrderDetailsByBillNumber :: End ");
		return sapBillSalesOrderDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SAPBillSalesOrderDO> getSAPBillSalesOrderDetailsList(
			List<Integer> summaryIds) throws CGSystemException {

		LOGGER.debug("SALES ORDER :: BillingUniversalDAOImpl :: getSAPBillSalesOrderDetails :: Start");
		List<SAPBillSalesOrderDO> sapBillSalesOrderDOs = null;
		try {
			sapBillSalesOrderDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							BillingUniversalConstants.QRY_GET_SAP_BILL_SALES_ORDER_DTLS_LIST,
							BillingUniversalConstants.SAP_SUMMARY_IDS, summaryIds);
		} catch (Exception e) {
			LOGGER.error(
					"SALES ORDER :: Exception Occured in :: BillingUniversalDAOImpl :: getSAPBillSalesOrderDetails() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("SALES ORDER :: BillingUniversalDAOImpl :: getSAPBillSalesOrderDetails :: End ");
		return sapBillSalesOrderDOs;
	
	}

}
