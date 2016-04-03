package com.ff.report.billing.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.report.billing.dao.BillPrintingDAO;
import com.ff.report.billing.dao.BillingCommonDAO;
import com.ff.report.constants.AdminErrorConstants;
import com.ff.business.CustomerTO;
import com.ff.domain.business.CustomerBillingDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;
import com.ff.to.billing.BillAliasTO;
import com.ff.to.billing.BillTO;
import com.ff.to.billing.ConsignmentAliasTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.universe.billing.service.BillingUniversalService;

// TODO: Auto-generated Javadoc
/**
 * The Class BillPrintingServiceImpl.
 */
public class BillPrintingServiceImpl implements BillPrintingService {

    /** The bill printing dao. */
    private BillPrintingDAO billPrintingDAO;

    /** The billing common service. */
    private BillingCommonService billingCommonService;

    /** The billing universal service. */
    private BillingUniversalService billingUniversalService;
    
    private BillingCommonDAO billingCommonDAO;

    public BillingCommonDAO getBillingCommonDAO() {
		return billingCommonDAO;
	}

	public void setBillingCommonDAO(BillingCommonDAO billingCommonDAO) {
		this.billingCommonDAO = billingCommonDAO;
	}


	/** The Constant LOGGER. */
    private final static Logger LOGGER = LoggerFactory
	    .getLogger(BillPrintingServiceImpl.class);

    /**
     * Sets the bill printing dao.
     * 
     * @param billPrintingDAO
     *            the billPrintingDAO to set
     */
    public void setBillPrintingDAO(BillPrintingDAO billPrintingDAO) {
	this.billPrintingDAO = billPrintingDAO;
    }

    /**
     * Sets the billing common service.
     * 
     * @param billingCommonService
     *            the billingCommonService to set
     */
    public void setBillingCommonService(
	    BillingCommonService billingCommonService) {
	this.billingCommonService = billingCommonService;
    }

    /**
     * Sets the billing universal service.
     * 
     * @param billingUniversalService
     *            the billingUniversalService to set
     */
    public void setBillingUniversalService(
	    BillingUniversalService billingUniversalService) {
	this.billingUniversalService = billingUniversalService;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ff.admin.billing.service.BillPrintingService#getStation(java.lang
     * .Integer)
     */
    public CityTO getStation(final Integer cityId) throws CGBusinessException,
	    CGSystemException {

	LOGGER.debug("BillPrintingServiceImpl::getStation::START----->");
	CityTO cityTO1 = null;
	try {
	    CityDO cityDO = billPrintingDAO.getCity(cityId);
	    if (cityDO != null) {
		cityTO1 = new CityTO();
		cityTO1 = (CityTO) CGObjectConverter.createToFromDomain(cityDO,
			cityTO1);
	    }
	} catch (Exception ex) {
	    LOGGER.error("ERROR : BillPrintingServiceImpl::getStation", ex);
	    throw new CGBusinessException(AdminErrorConstants.NO_STATION_FOUND,
		    ex);
	}
	LOGGER.debug("BillPrintingServiceImpl::getStation::END----->");
	return cityTO1;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ff.admin.billing.service.BillPrintingService#getCustomersByBranch
     * (java.util.List)
     */
    public List<CustomerTO> getCustomersByBranch(List<Integer> BranchId)
	    throws CGBusinessException, CGSystemException {

	LOGGER.debug("BillPrintingServiceImpl::getCustomersByBranch::START----->");
	List<CustomerTO> custList = new ArrayList<CustomerTO>();

	if (!StringUtil.isEmptyColletion(BranchId)) {
	    List<CustomerDO> customerDOList = billPrintingDAO
		    .getCustomersByBranch(BranchId);

	    if (!StringUtil.isEmptyColletion(customerDOList)) {
		custList = (List<CustomerTO>) CGObjectConverter
			.createTOListFromDomainList(customerDOList,
				CustomerTO.class);
	    } else {
		throw new CGBusinessException(
			AdminErrorConstants.NO_CUSTOMER_FOUND);
	    }
	}
	/*
	 * } catch (Exception ex) { LOGGER.error(
	 * "ERROR : BillPrintingServiceImpl::getCustomersByBranch", ex); throw
	 * new CGBusinessException(AdminErrorConstants.NO_CUSTOMER_FOUND, ex); }
	 */
	LOGGER.debug("BillPrintingServiceImpl::getCustomersByBranch::END----->");
	return custList;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ff.admin.billing.service.BillPrintingService#getStationaryTypes(java
     * .lang.String)
     */
    @Override
    public List<StockStandardTypeTO> getStationaryTypes(final String typeName)
	    throws CGBusinessException, CGSystemException {

	LOGGER.debug("BillPrintingServiceImpl::getStationaryTypes::START----->");
	List<StockStandardTypeTO> stationeryNames = null;

	stationeryNames = billingCommonService
		.getStandardTypesByTypeName(typeName);
	if (CGCollectionUtils.isEmpty(stationeryNames)) {
	    throw new CGBusinessException(AdminErrorConstants.NO_BILLS_FOUND);
	}

	/*
	 * catch (Exception ex) { LOGGER.error(
	 * "ERROR : BillPrintingServiceImpl::getStationaryTypes", ex); throw new
	 * CGBusinessException(AdminErrorConstants.NO_STATIONARY_AVAILABLE); }
	 */
	LOGGER.debug("BillPrintingServiceImpl::getStationaryTypes::END----->");
	return stationeryNames;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ff.admin.billing.service.BillPrintingService#getBills(java.util.List,
     * java.lang.String, java.lang.String)
     */
    @Override
    public List<BillTO> getBills(List<Integer> custIds, String startDate,
	    String endDate, Integer productId,List<Integer> billingBrachs) throws CGBusinessException,
	    CGSystemException {

	LOGGER.debug("BillPrintingServiceImpl::getBills::START----->");
	List<BillTO> billList = new ArrayList<BillTO>();

	if (!StringUtil.isNull(custIds)) {

	    billList = billingUniversalService.getBillsData(custIds, startDate,
		    endDate, productId,billingBrachs);
	    if (CGCollectionUtils.isEmpty(billList)) {
		throw new CGBusinessException(
			AdminErrorConstants.NO_BILLS_FOUND);
	    }
	    
	    Collections.sort(billList);
	}

	/*
	 * }catch (Exception ex) { LOGGER.error(
	 * "ERROR :BillPrintingServiceImpl::getBills",ex); throw new
	 * CGBusinessException(AdminErrorConstants.NO_BILLS_FOUND, ex); }
	 */
	LOGGER.debug("BillPrintingServiceImpl::getBills::END----->");
	return billList;
    }

    
    /**
     * @see com.ff.admin.billing.service.BillPrintingService#getCustomersByBillingBranch(java.util.List)
     *      Dec 3, 2013
     * @param BranchId
     * @return
     * @throws CGBusinessException
     * @throws CGSystemException
     *             getCustomersByBillingBranch BillPrintingService kgajare
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<CustomerTO> getCustomersByBillingBranch(List<Integer> BranchId)
	    throws CGBusinessException, CGSystemException {

	LOGGER.debug("BillPrintingServiceImpl::getCustomersByBillingBranch::START----->");
	List<CustomerTO> custList = new ArrayList<CustomerTO>();

	if (!StringUtil.isEmptyColletion(BranchId)) {
	    List<CustomerBillingDO> customerDOList = billPrintingDAO.getCustomersByBillingBranch(BranchId);

	    
	    if (!StringUtil.isEmptyColletion(customerDOList)) {
	    	
	    	for (CustomerBillingDO customerBillingDO : customerDOList) {
	    		CustomerTO custTo = new CustomerTO();
	    		custTo.setCustomerCode(customerBillingDO.getCustomerCode());
	    		custTo.setContractNo(customerBillingDO.getContractNo());
	    		custTo.setCustomerId(customerBillingDO.getCustomerId());
	    		custTo.setBusinessName(customerBillingDO.getBusinessName());
	    		custTo.setShippedToCode(customerBillingDO.getShippedToCode());
	    		custList.add(custTo);
			}
	   /* if (!StringUtil.isEmptyColletion(customerDOList)) {
		custList = (List<CustomerTO>) CGObjectConverter
			.createTOListFromDomainList(customerDOList,
				CustomerTO.class);*/
	    } else {
		throw new CGBusinessException(AdminErrorConstants.NO_CUSTOMER_FOUND);
	    }
	}
	LOGGER.debug("BillPrintingServiceImpl::getCustomersByBillingBranch::END----->");
	return custList;

    }
    
    
    public List<CustomerTO>  getCustsByBillingBranchAndFinancialProduct(List<Integer> branchId,Integer financialProdId)  throws CGBusinessException, CGSystemException {

    	LOGGER.debug("BillPrintingServiceImpl::getCustsByBillingBranchAndFinancialProduct::START----->");
    	List<CustomerTO> custList = new ArrayList<CustomerTO>();

    	if (!StringUtil.isEmptyColletion(branchId) && !StringUtil.isEmptyInteger(financialProdId)) {
    	    List<CustomerBillingDO> customerDOList = billPrintingDAO.getCustsByBillingBranchAndFinancialProduct(branchId, financialProdId);

    	    if (!StringUtil.isEmptyColletion(customerDOList)) {
    	    	
    	    	for (CustomerBillingDO customerBillingDO : customerDOList) {
    	    		CustomerTO custTo = new CustomerTO();
    	    		custTo.setCustomerCode(customerBillingDO.getCustomerCode());
    	    		custTo.setContractNo(customerBillingDO.getContractNo());
    	    		custTo.setCustomerId(customerBillingDO.getCustomerId());
    	    		custTo.setBusinessName(customerBillingDO.getBusinessName());
    	    		custTo.setShippedToCode(customerBillingDO.getShippedToCode());
    	    		custList.add(custTo);
				}
    		/*custList = (List<CustomerTO>) CGObjectConverter
    			.createTOListFromDomainList(customerDOList,
    				CustomerTO.class);*/
    	    } else {
    		throw new CGBusinessException(AdminErrorConstants.NO_CUSTOMER_FOUND);
    	    }
    	}
    	LOGGER.debug("BillPrintingServiceImpl::getCustsByBillingBranchAndFinancialProduct::END----->");
    	return custList;
    }
    
    
    public List<OfficeTO> getBranchesUnderReportingHub(Integer officeId)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("BillPrintingServiceImpl :: getBranchesUnderReportingHub() :: Start --------> ::::::");
		List<OfficeTO> officeTOList = null;

		List<OfficeDO> officeDOList = billPrintingDAO.getBranchesUnderReportingHub(officeId);
		/**
		 * Comment START
		 * Commented By - kgajare
		 * Commented Date - 19 May 2014
		 * Commented Reason - An issue was reported for HUB login in BIll Printing where 
		 * user of Standalone HUB cannot print bills for its own customers. The reason was below business exception.
		 * As confirmed with BA Team we have to remove the business exception even if HUB did not have any branches 
		 * under it and let the user use Bill Printing with only HUB itself in Branch List.
		 */
		/*if (StringUtil.isEmptyColletion(officeDOList)) {
			throw new CGBusinessException(AdminErrorConstants.ERROR_IN_GETTING_BRANCH_LIST_OF_HUB);
		}*/
		/**
		 * Comment END
		 */
		if (!StringUtil.isEmptyColletion(officeDOList)) {
			officeTOList = new ArrayList<>();
			for (OfficeDO officeDO : officeDOList) {
				OfficeTO officeTO = new OfficeTO();
				officeTO = (OfficeTO) CGObjectConverter.createToFromDomain(
						officeDO, officeTO);
				officeTOList.add(officeTO);
			}
		}
		LOGGER.trace("BillPrintingServiceImpl :: getBranchesUnderReportingHub() :: End --------> ::::::");
		return officeTOList;
	}
}
