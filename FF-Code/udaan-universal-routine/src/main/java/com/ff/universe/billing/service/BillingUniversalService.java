package com.ff.universe.billing.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.consignment.ConsignmentTO;
import com.ff.to.billing.BillTO;
import com.ff.to.ratemanagement.masters.RateContractTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface BillingUniversalService.
 * 
 * @author narmdr
 */
public interface BillingUniversalService {

	/**
	 * Gets the bills data by customer id. Get Bills Data for customer.
	 * 
	 * @param customerId
	 *            the customer id
	 * @return the bills data by customer id
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<BillTO> getBillsDataByCustomerId(final Integer customerId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the bills data.
	 * 
	 * @param invoiceNumbers
	 *            the invoice numbers
	 * @return the bills data
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<BillTO> getBillsData(final List<String> invoiceNumbers)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the bills data.
	 * 
	 * @param shippedToCode
	 *            the shipped to code
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return the bills data
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<BillTO> getBillsData(final String shippedToCode,
			final String startDate, final String endDate)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the bills data.
	 * 
	 * @param customerIds
	 *            the customer ids
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @param productId
	 *            the product id
	 * @return the bills data
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<BillTO> getBillsData(final List<Integer> customerIds,
			final String startDate, final String endDate,
			final Integer productId, List<Integer> billingBrachs)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the shipped to codes by customer id.
	 * 
	 * @param customerId
	 *            the customer id
	 * @return the shipped to codes by customer id
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<String> getShippedToCodesByCustomerId(final Integer customerId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the rate contracts by customer ids.
	 * 
	 * @param customerId
	 *            the customer id
	 * @return the rate contracts by customer ids
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<RateContractTO> getRateContractsByCustomerIds(
			final List<Integer> customerId) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the booked consignments by cust id date range.
	 * 
	 * @param customerId
	 *            the customer id
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return the booked consignments by cust id date range
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<ConsignmentTO> getBookedConsignmentsByCustIdDateRange(
			final Integer customerId, final String startDate,
			final String endDate) throws CGBusinessException, CGSystemException;

	/**
	 * Gets the booked transferred consignments by cust id date range.
	 * 
	 * @param customerId
	 *            the customer id
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return the booked transferred consignments by cust id date range
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<ConsignmentTO> getBookedTransferredConsignmentsByCustIdDateRange(
			final Integer customerId, final String startDate,
			final String endDate) throws CGBusinessException, CGSystemException;

	/**
	 * Gets the bills data by customer id for payment Get Bills Data for
	 * customer payment
	 * 
	 * @param customerId
	 *            the customer id
	 * @param locationOperationType
	 *            payment or billing or billing-payment
	 * @param officeId
	 * @return the bills data by customer id for payment
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<BillTO> getPaymentBillsDataByCustomerId(Integer customerId,
			String[] locationOperationType, Integer officeId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the bills data by customer id for payment Get Bills Data for
	 * customer payment
	 * 
	 * @param customerId
	 *            the customer id
	 * @param officeId
	 * @return the bills data by customer id for payment
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<BillTO> getSAPBillsDataByCustomerId(Integer customerId,
			Integer officeId) throws CGBusinessException, CGSystemException;

	public List<BillTO> getBillsByCustomerId(Integer custId, String startDt,
			String endDt) throws CGBusinessException, CGSystemException;
}
