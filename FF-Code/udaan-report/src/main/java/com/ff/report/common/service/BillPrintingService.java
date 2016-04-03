package com.ff.report.common.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;
import com.ff.to.billing.BillAliasTO;
import com.ff.to.billing.BillTO;
import com.ff.to.billing.ConsignmentAliasTO;
import com.ff.to.billing.ReBillGDRAliasTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface BillPrintingService.
 */
public interface BillPrintingService {

 
	/**
     * Gets the bills.
     * 
     * @param invoiceNumbers
     *            the invoice numbers
     * @param startDate
     *            the start date
     * @param endDate
     *            the end date
     * @param productId
     *            the product id
     * @return the bills for printing purpose
     * @throws CGBusinessException
     *             the cG business exception
     * @throws CGSystemException
     *             the cG system exception kgajare
     */
    public List<BillAliasTO> getBillsForBillNumbers(
	    List<Integer> invoiceNumbers, String startDate, 
	    String endDate, Integer financialProductId, 
	    List<Integer> billingOfficeIds) throws CGBusinessException, CGSystemException;
    
	/**
     * Gets the bills.
     * 
     * @param custIds
     *            the customer ids
     * @param startDate
     *            the start date
     * @param endDate
     *            the end date
     * @param productId
     *            the product id
     * @return the bills for printing purpose
     * @throws CGBusinessException
     *             the cG business exception
     * @throws CGSystemException
     *             the cG system exception kgajare
     */
    public List<BillAliasTO> getBillsForCustomers(List<Integer> custIds,
	    String startDate, String endDate, Integer financialProductId, 
	    List<Integer> billingOfficeIds)
	    throws CGBusinessException, CGSystemException;

    /**
     * Gets the consignments for the provided bill/invoice.
     * 
     * @param invoiceId
     * 		  the invoice/bill id
     * @return the consinments for printing purpose
     * @throws CGBusinessException
     *             the cG business exception
     * @throws CGSystemException
     *             the cG system exception kgajare
     * kgajare
     */
    public List<ConsignmentAliasTO> getConsignmentsForPrinting(Integer invoiceId)
	    throws CGBusinessException, CGSystemException;

    public List<ReBillGDRAliasTO> getRebillingGDRDetails(String  reBillId)throws CGBusinessException, CGSystemException;
    
    //Code copied from config admin
    /**
     * Gets the station.
     * 
     * @param cityId
     *            the city id
     * @return the station
     * @throws CGBusinessException
     *             the cG business exception
     * @throws CGSystemException
     *             the cG system exception
     */
    public CityTO getStation(final Integer cityId) throws CGBusinessException,
	    CGSystemException;

    /**
     * Gets the customers by branch.
     * 
     * @param BranchId
     *            the branch id
     * @return the customers by branch
     * @throws CGBusinessException
     *             the cG business exception
     * @throws CGSystemException
     *             the cG system exception
     */
    List<CustomerTO> getCustomersByBranch(List<Integer> BranchId)
	    throws CGBusinessException, CGSystemException;

    /**
     * Gets the stationary types.
     * 
     * @param typeName
     *            the type name
     * @return the stationary types
     * @throws CGBusinessException
     *             the cG business exception
     * @throws CGSystemException
     *             the cG system exception
     */
    public List<StockStandardTypeTO> getStationaryTypes(final String typeName)
	    throws CGBusinessException, CGSystemException;

    /**
     * Gets the bills.
     * 
     * @param custIds
     *            the cust ids
     * @param startDate
     *            the start date
     * @param endDate
     *            the end date
     * @return the bills
     * @throws CGBusinessException
     *             the cG business exception
     * @throws CGSystemException
     *             the cG system exception
     */
    public List<BillTO> getBills(List<Integer> custIds, String startDate,
	    String endDate, Integer productId,List<Integer> billingBrachs) throws CGBusinessException,
	    CGSystemException;

   
    public List<CustomerTO> getCustomersByBillingBranch(List<Integer> BranchId)
	    throws CGBusinessException, CGSystemException;
    
    public List<CustomerTO>  getCustsByBillingBranchAndFinancialProduct(List<Integer> branchId,Integer financialProdId)  throws CGBusinessException, CGSystemException;
    
    
    public List<OfficeTO> getBranchesUnderReportingHub(Integer officeId)
			throws CGSystemException, CGBusinessException;
    
    /**
     * Gets the consignments for the provided bill/invoice for printing the bills in bulk
     * 
     * @param invoiceId the invoice/bill id
     * @return the consinments for printing purpose
     * @throws CGBusinessException
     *             the cG business exception
     * @throws CGSystemException
     *             the cG system exception 
     */
    public List<ConsignmentAliasTO> getConsignmentsForBulkBillPrinting(Integer invoiceId)
	    throws CGBusinessException, CGSystemException;
    
    public List<CustomerTO>  getCustsByBillingBranchAndFinancialProductForReport(List<Integer> branchId,List<Integer> productIds)  throws CGBusinessException, CGSystemException;

	List<BillAliasTO> getBillsForAllCustomers(String startDate, String endDate,
			Integer financialProductId, List<Integer> billingOfficeIds)
			throws CGBusinessException, CGSystemException;
  
}