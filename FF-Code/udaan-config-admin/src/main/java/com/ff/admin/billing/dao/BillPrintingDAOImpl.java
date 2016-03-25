package com.ff.admin.billing.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.billing.constants.BillingConstants;
import com.ff.domain.business.CustomerBillingDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.organization.OfficeDO;

// TODO: Auto-generated Javadoc
/**
 * The Class BillPrintingDAOImpl.
 */
public class BillPrintingDAOImpl extends CGBaseDAO implements BillPrintingDAO {

    /** The Constant LOGGER. */
    private final static Logger LOGGER = LoggerFactory
	    .getLogger(BillPrintingDAOImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.ff.admin.billing.dao.BillPrintingDAO#getCity(java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public CityDO getCity(Integer cityId) throws CGSystemException {

	LOGGER.debug("BillPrintingDAOImpl :: getCity() :: Start --------> ::::::");
	try {
	    List<CityDO> cityDOs = null;
	    String queryName = null;

	    if (cityId != null) {
		queryName = "getCityByCityId";
		cityDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
			queryName, "cityId", cityId);
	    }
	    if (cityDOs != null && cityDOs.size() > 0) {
		LOGGER.debug("BillPrintingDAOImpl :: getCity() :: END --------> ::::::");
		return cityDOs.get(0);
	    }
	} catch (Exception e) {
	    LOGGER.error("ERROR : BillPrintingDAOImpl.getCity", e);
	    throw new CGSystemException(e);
	}
	LOGGER.debug("BillPrintingDAOImpl :: getCity() :: END --------> ::::::");
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ff.admin.billing.dao.BillPrintingDAO#getCustomersByBranch(java.util
     * .List)
     */
    @Override
    public List<CustomerDO> getCustomersByBranch(List<Integer> branchId)
	    throws CGSystemException {
	List<CustomerDO> customerList = null;

	LOGGER.debug("BillPrintingDAOImpl :: getCustomersByBranch() :: Start --------> ::::::");
	try {
	    String queryName = "getCustomerByOffice";
	    customerList = getHibernateTemplate()
		    .findByNamedQueryAndNamedParam(queryName, "mappedOffice",
			    branchId);

	} catch (Exception e) {
	    LOGGER.error("ERROR : BillPrintingDAOImpl.getCustomersByBranch", e);
	    throw new CGSystemException(e);
	}
	LOGGER.debug("BillPrintingDAOImpl :: getCustomersByBranch() :: END --------> ::::::");
	return customerList;
    }

       /**
     * @see com.ff.admin.billing.dao.BillPrintingDAO#getCustomersByBillingBranch(java.util.List)
     * Dec 3, 2013
     * @param branchId
     * @return
     * @throws CGSystemException
     * getCustomersByBillingBranch
     * BillPrintingDAO
     * kgajare
     */
    @Override
    public List<CustomerBillingDO> getCustomersByBillingBranch(List<Integer> branchId)
	    throws CGSystemException {
	List<CustomerBillingDO> customerList = null;
	List<CustomerBillingDO> customerList1 = null;

	LOGGER.debug("BillPrintingDAOImpl ::getCustomersByBillingBranch :: Start --------> ::::::");
	Session session = null;
	try {
	    String queryName = "getCustomersByBillingBranch";
	    String queryName1 = "getCustomersByBillingBranchUnion";
	    session = getHibernateTemplate().getSessionFactory().openSession();
	    /*customerList = session.getNamedQuery(queryName)
		    		.setParameterList("billingOffice", branchId).list();*/
	    customerList = getCustomersByBillingBranch1(branchId);
	    
	    
	    /*customerList1 = session.getNamedQuery(queryName1)
	    		.setParameterList("billingOffice", branchId).list();
	    */
	    customerList1 = getCustomersByBillingBranchUnion(branchId);
	    if(!StringUtil.isNull(customerList1)){
	    	customerList.addAll(customerList1);
	    }
    
	    /*customerList = getHibernateTemplate()
		    .findByNamedQueryAndNamedParam(queryName, "billingOffice",
			    branchId);*/

	} catch (Exception e) {
	    LOGGER.error("ERROR : BillPrintingDAOImpl.getCustomersByBillingBranch", e);
	    throw new CGSystemException(e);
	}finally {
	    session.close();
	    session = null;
	}
	
	LOGGER.debug("BillPrintingDAOImpl :: getCustomersByBillingBranch() :: END --------> ::::::");
	return customerList;
    }
    
    
    @SuppressWarnings("unchecked")
	public List<CustomerBillingDO> getCustsByBillingBranchAndFinancialProduct(List<Integer> branchId,Integer financialProdId)throws CGSystemException {
    	        LOGGER.debug("BillPrintingDAOImpl :: getCustsByBillingBranchAndFinancialProductList() :: START --------> ::::::");
    	    	List<CustomerBillingDO> customerBillingList = null;
    	    	List<CustomerBillingDO> customerBillingList1 = null;

    	    	Session session = null;
    	    	try {
    	    	    String queryName ="getNonContractedCustomersByBillingBranch";
    	    		String queryName1 = "getContractedCustomersByBillingBranch";
    	    		
    	    	   
    	    	    session = getHibernateTemplate().getSessionFactory().openSession();
    	    	    customerBillingList = getNonContractedCustomersByBillingBranch(branchId, financialProdId);
    	    	    /*customerBillingList = session.getNamedQuery(queryName)
    	    		    		.setParameterList("billingOfficeId", branchId).setInteger("financialProductId", financialProdId).list();
    	    	    */
    	    	   /* customerBillingList1 = session.getNamedQuery(queryName1)
    	    	    		.setParameterList("billingOfficeId", branchId).setInteger("financialProductId", financialProdId).list();*/
    	    	    customerBillingList1 = getContractedCustomersByBillingBranch(branchId, financialProdId);
    	    	    if(!StringUtil.isNull(customerBillingList1)){
    	    	    	customerBillingList.addAll(customerBillingList1);
    	    	    }
    	        
    	    	    /*customerList = getHibernateTemplate()
    	    		    .findByNamedQueryAndNamedParam(queryName, "billingOffice",
    	    			    branchId);*/
                
    	    	} catch (Exception e) {
    	    	    LOGGER.error("ERROR : BillPrintingDAOImpl.getCustsByBillingBranchAndFinancialProductList", e);
    	    	    throw new CGSystemException(e);
    	    	}finally {
    	    	    session.close();
    	    	    session = null;
    	    	}
    	    	LOGGER.debug("BillPrintingDAOImpl :: getCustsByBillingBranchAndFinancialProductList() :: END --------> ::::::");
    	    	return customerBillingList;
     
    }
    
    
    public List<OfficeDO> getBranchesUnderReportingHub(Integer officeId)
			throws CGSystemException {
		LOGGER.trace("BillPrintingDAOImpl :: getBranchesUnderReportingHub() :: Start --------> ::::::");
		List<OfficeDO> officeDOs= null;
		try{
			officeDOs=getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					BillingConstants.QRY_GET_ALL_BRANCHES_UNDER_REPORTING_HUB,
					new String[] { BillingConstants.OFFICE_ID},
					new Object[] { officeId });
	
		}catch(Exception e){
			LOGGER.error("Exception Occured in::BillPrintingDAOImpl::getBranchesUnderReportingHub() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("BillPrintingDAOImpl :: getBranchesUnderReportingHub() :: End --------> ::::::");
		return officeDOs;
	}

	
	private List<CustomerBillingDO> getNonContractedCustomersByBillingBranch(List<Integer> branchId,Integer financialProdId)
			throws CGSystemException {
		List<CustomerBillingDO> customerBillingList1 = null;

    	Session session = null;
    	try {
    	    
    	    //String queryName = null;
    	    String queryName1 = null;
		session = getHibernateTemplate().getSessionFactory().openSession();
	    queryName1 = BillingConstants.GET_NON_CONTRACTED_CUSTOMERS_BY_BILLING_BRANCH;
	    customerBillingList1 = session
			    .createSQLQuery(queryName1)
			    .addScalar("customerId")
    			    .addScalar("contractNo")
				    .addScalar("customerCode")
				    .addScalar("businessName")
				    .addScalar("customerCode1")
			    .setParameterList("billingOfficeId", branchId)
			    .setInteger("financialProductId", financialProdId)
			    
			    .setResultTransformer(Transformers.aliasToBean(CustomerBillingDO.class)).list();
    	} catch (Exception e) {
    	    LOGGER.error("ERROR : BillPrintingDAOImpl.getBillsForCustomers", e);
    	    throw new CGSystemException(e);
    	} finally {
    	    session.close();
    	    session = null;
    	}

    	LOGGER.debug("BillPrintingDAOImpl :: getBillsForCustomers() :: End --------> ::::::");
    	return customerBillingList1;
        }
       


	private List<CustomerBillingDO> getContractedCustomersByBillingBranch(List<Integer> branchId,Integer financialProdId)
			throws CGSystemException {
		List<CustomerBillingDO> customerBillingList = null;

    	Session session = null;
    	try {
    	    
    	    String queryName = null;
    	    //String queryName1 = null;
    	   
    	    session = getHibernateTemplate().getSessionFactory().openSession();
    	    queryName = BillingConstants.GET_CONTRACTED_CUSTOMERS_BY_BILLING_BRANCH;
    	    customerBillingList = session
    			    .createSQLQuery(queryName)
    			    .addScalar("customerId")
    			    .addScalar("contractNo")
				    .addScalar("customerCode")
				    .addScalar("businessName")
				    .addScalar("shippedToCode")
				    .setParameterList("billingOfficeId", branchId)
				    .setInteger("financialProductId", financialProdId)
				    
				    .setResultTransformer(
					    Transformers.aliasToBean(CustomerBillingDO.class)).list();
    	} catch (Exception e) {
    	    LOGGER.error("ERROR : BillPrintingDAOImpl.getBillsForCustomers", e);
    	    throw new CGSystemException(e);
    	} finally {
    	    session.close();
    	    session = null;
    	}

    	LOGGER.debug("BillPrintingDAOImpl :: getBillsForCustomers() :: End --------> ::::::");
    	return customerBillingList;
        }


	private List<CustomerBillingDO> getCustomersByBillingBranch1(
			List<Integer> branchId) throws CGSystemException {
		List<CustomerBillingDO> customerBillingList = null;

    	Session session = null;
    	try {
    	    
    	    String queryName = null;
    	    //String queryName1 = null;
    	   
    	    session = getHibernateTemplate().getSessionFactory().openSession();
    	    queryName = BillingConstants.GET_CUSTOMERS_BY_BILLING_BRANCH;
    	    customerBillingList = session
    			    .createSQLQuery(queryName)
    			    .addScalar("customerId")
    			    .addScalar("contractNo")
				    .addScalar("customerCode")
				    .addScalar("businessName")
				    .addScalar("shippedToCode")
				    .setParameterList("billingOffice", branchId)
				    .setResultTransformer(
					    Transformers.aliasToBean(CustomerBillingDO.class)).list();
    	} catch (Exception e) {
    	    LOGGER.error("ERROR : BillPrintingDAOImpl.getBillsForCustomers", e);
    	    throw new CGSystemException(e);
    	} finally {
    	    session.close();
    	    session = null;
    	}

    	LOGGER.debug("BillPrintingDAOImpl :: getBillsForCustomers() :: End --------> ::::::");
    	return customerBillingList;
        }



	
	private List<CustomerBillingDO> getCustomersByBillingBranchUnion(
			List<Integer> branchId) throws CGSystemException {
		List<CustomerBillingDO> customerBillingList = null;

    	Session session = null;
    	try {
    	    
    	    String queryName = null;
    	    //String queryName1 = null;
    	   
    	    session = getHibernateTemplate().getSessionFactory().openSession();
    	    queryName = BillingConstants.GET_CUSTOMERS_BY_BILLING_BRANCH_UNION;
    	    customerBillingList = session
    			    .createSQLQuery(queryName)
    			    			    .addScalar("customerId")
    			    .addScalar("contractNo")
				    .addScalar("customerCode")
				    .addScalar("businessName")
				    .addScalar("customerCode1")
				    .setParameterList("billingOffice", branchId)
				    .setResultTransformer(
					    Transformers.aliasToBean(CustomerBillingDO.class)).list();
    	} catch (Exception e) {
    	    LOGGER.error("ERROR : BillPrintingDAOImpl.getBillsForCustomers", e);
    	    throw new CGSystemException(e);
    	} finally {
    	    session.close();
    	    session = null;
    	}

    	LOGGER.debug("BillPrintingDAOImpl :: getBillsForCustomers() :: End --------> ::::::");
    	return customerBillingList;
        }
    
}
