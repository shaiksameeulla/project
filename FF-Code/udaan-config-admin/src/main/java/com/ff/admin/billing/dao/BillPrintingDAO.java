package com.ff.admin.billing.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.business.CustomerBillingDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.to.billing.BillAliasTO;
import com.ff.to.billing.ConsignmentAliasTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface BillPrintingDAO.
 */
public interface BillPrintingDAO {

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
}
