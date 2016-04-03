package com.ff.report.common.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.business.CustomerBillingDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.to.billing.BillAliasTO;
import com.ff.to.billing.ConsignmentAliasTO;
import com.ff.to.billing.ReBillGDRAliasTO;

/**
 * The Interface BillPrintingDAO.
 */
public interface BillPrintingDAO {

    

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
     * 		  the product id
     * @return the bills for printing purpose
     * @throws CGSystemException
     *             the cG system exception
     * kgajare
     */
    public List<BillAliasTO> getBillsForBillNumbers(List<Integer> invoiceNumbers,
	    String startDate, String endDate, Integer financialProductId, 
	    List<Integer> billingOfficeIds)
	    throws CGSystemException;

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
     * 		  the product id
     * @return the bills for printing purpose
     * @throws CGSystemException
     *             the cG system exception
     * kgajare
     */
    public List<BillAliasTO> getBillsForCustomers(List<Integer> custIds,
	    String startDate, String endDate, Integer financialProductId, 
	    List<Integer> billingOfficeIds)
	    throws CGSystemException;

    /**
     * Gets the consignments for the provided bill/invoice.
     * 
     * @param invoiceId
     * 		  the invoice/bill id
     * @return the consinments for printing purpose
     * @throws CGSystemException
     *             the cG system exception
     * kgajare
     */
    public List<ConsignmentAliasTO> getConsignmentsForPrinting(Integer invoiceId) throws CGSystemException;
    
    public List<ReBillGDRAliasTO> getRebillingGDRDetails(Integer reBillId)throws CGSystemException;
    
    //code added from config admin
    /**
     * Gets the city.
     * 
     * @param cityId
     *            the city id
     * @return the city
     * @throws CGSystemException
     *             the cG system exception
     */
    public CityDO getCity(Integer cityId) throws CGSystemException;

    /**
     * Gets the customers by branch.
     * 
     * @param branchId
     *            the branch id
     * @return the customers by branch
     * @throws CGSystemException
     *             the cG system exception
     */
    public List<CustomerDO> getCustomersByBranch(List<Integer> branchId)
	    throws CGSystemException;

    public List<CustomerBillingDO> getCustomersByBillingBranch(List<Integer> branchId) throws CGSystemException;
    
    public List<CustomerBillingDO> getCustsByBillingBranchAndFinancialProduct(List<Integer> branchId,Integer financialProdId)throws CGSystemException;
    
    public List<OfficeDO> getBranchesUnderReportingHub(Integer officeId)
			throws CGSystemException;
    
    /**
     * Gets the consignments for the provided bill/invoice for bill printing in bulk
     * 
     * @param invoiceId the invoice/bill id
     * @return the consinments for printing purpose
     * @throws CGSystemException the cG system exception
     */
    public List<ConsignmentAliasTO> getConsignmentsForBulkBillPrinting(Integer invoiceId) throws CGSystemException;
    
    public List<CustomerBillingDO> getCustsByBillingBranchAndFinancialProductForReport(List<Integer> branchId,List<Integer> productIds)throws CGSystemException;

	public List<BillAliasTO> getBillsForAllCustomers(String startDate,
			String endDate, Integer financialProductId,
			List<Integer> billingOfficeIds)throws CGSystemException;
}
