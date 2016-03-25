package com.ff.universe.billing.dao;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.billing.BillDO;
import com.ff.domain.billing.BillingConsignmentDO;
import com.ff.domain.billing.BillingConsignmentSummaryDO;
import com.ff.domain.billing.SAPBillSalesOrderDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface BillingUniversalDAO.
 * 
 * @author narmdr
 */
public interface BillingUniversalDAO {

	/**
	 * Gets the bills by customer id.
	 * 
	 * @param customerId
	 *            the customer id
	 * @return the bills by customer id
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<BillDO> getBillsByCustomerId(Integer customerId)
			throws CGSystemException;

	/**
	 * Gets the bills by invoice nos.
	 * 
	 * @param invoiceNumbers
	 *            the invoice numbers
	 * @return the bills by invoice nos
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<BillDO> getBillsByInvoiceNos(List<String> invoiceNumbers)
			throws CGSystemException;

	/**
	 * Gets the bills by shipped to code and start end date.
	 * 
	 * @param shippedToCode
	 *            the shipped to code
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return the bills by shipped to code and start end date
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<BillDO> getBillsByShippedToCodeAndStartEndDate(String shippedToCode,
			Date startDate, Date endDate) throws CGSystemException;

	/**
	 * Gets the bills by customer ids and start end date.
	 * 
	 * @param customerIds
	 *            the customer ids
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @param productId
	 *            the product id
	 * @return the bills by customer ids and start end date
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<BillDO> getBillsByCustomerIdsAndStartEndDate(
			List<Integer> customerIds, String startDate, String endDate,
			final Integer productId, List<Integer> billingBrachs)
			throws CGSystemException;

	BillingConsignmentSummaryDO getBCSDtlsBySummaryID(
			Integer billingConsignmentId) throws CGSystemException;

	List<BillingConsignmentDO> getInvoiceAgainstSummary(
			Integer billingConsignmentId) throws CGSystemException;

	SAPBillSalesOrderDO getSAPBillSalesOrderDetails(Integer summaryId)
			throws CGSystemException;

	/**
	 * Gets the bills by customer id for payment
	 * 
	 * @param customerId
	 *            the customer id
	 * @param locationOperationType
	 *            payment or billing or billing-payment
	 * @param officeId
	 * @return the bills by customer id for payment
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<BillDO> getPaymentBillsByCustomerId(Integer customerId,
			String[] locationOperationType, Integer officeId)
			throws CGSystemException;

	/**
	 * Gets the bills by customer id for payment
	 * 
	 * @param customerId
	 *            the customer id
	 * @param officeId
	 * @return the bills by customer id for payment
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<BillDO> getSAPBillsByCustomerId(Integer customerId, Integer officeId)
			throws CGSystemException;

	public List<BillDO> getBillsByCustomerId(Integer custId, Date startDate,
			Date endDate) throws CGSystemException;

	SAPBillSalesOrderDO getSAPBillSalesOrderDetailsByBillNumber(
			String billNumber) throws CGSystemException;

	List<SAPBillSalesOrderDO> getSAPBillSalesOrderDetailsList(
			List<Integer> summaryIds) throws CGSystemException;
}
