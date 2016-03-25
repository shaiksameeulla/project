package com.ff.admin.billing.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;
import com.ff.to.billing.BillAliasTO;
import com.ff.to.billing.BillTO;
import com.ff.to.billing.ConsignmentAliasTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface BillPrintingService.
 */
public interface BillPrintingService {

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
}