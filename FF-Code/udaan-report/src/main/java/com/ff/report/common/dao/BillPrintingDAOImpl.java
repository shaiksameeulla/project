package com.ff.report.common.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.business.CustomerBillingDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.report.common.util.BillingConstants;
import com.ff.to.billing.BillAliasTO;
import com.ff.to.billing.ConsignmentAliasTO;
import com.ff.to.billing.ReBillGDRAliasTO;

/**
 * The Class BillPrintingDAOImpl.
 */
public class BillPrintingDAOImpl extends CGBaseDAO implements BillPrintingDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BillPrintingDAOImpl.class);

	/**
	 * @see com.ff.admin.billing.dao.BillPrintingDAO#getBillsForBillNumbers(java.util.List,
	 *      java.lang.String, java.lang.String, int) Nov 21, 2013
	 * @param invoiceNumbers
	 * @param startDate
	 * @param endDate
	 * @param productId
	 * @return
	 * @throws CGBusinessException
	 *             getBillsForBillNumbers BillPrintingDAO kgajare
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillAliasTO> getBillsForBillNumbers(
			List<Integer> invoiceNumbers, String startDate, String endDate,
			Integer financialProductId, List<Integer> billingOfficeIds) throws CGSystemException {
		LOGGER.debug("BillPrintingDAOImpl :: getBillsForBillNumbers() :: Start --------> ::::::");
		List<BillAliasTO> billAliasTOs = null;
		Session session = null;
		String query = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			query = BillingConstants.GET_BILL_DATA_FOR_PRINTING
					+ "  ffb.INVOICE_NUMBER in (:invoiceNumbers)";
			billAliasTOs = session
					.createSQLQuery(query)
					.addScalar("invoiceId")
					.addScalar("invoiceNumber")
					.addScalar("createdDate")
					.addScalar("fromDate")
					.addScalar("toDate")
					.addScalar("numberOfPickups")
					.addScalar("freight")
					.addScalar("riskSurcharge")
					.addScalar("docHandlingCharge")
					.addScalar("parcelHandlingCharge")
					.addScalar("airportHandlingCharge")
					.addScalar("otherCharges")
					.addScalar("total")
					.addScalar("fuelSurcharge")
					.addScalar("serviceTax")
					.addScalar("educationCess")
					.addScalar("higherEducationCess")
					.addScalar("stateTax")
					.addScalar("surchargeOnStateTax")
					.addScalar("grandTotal")
					.addScalar("grandTotalRoundedOff")
					.addScalar("billingOfficeName")
					.addScalar("billingOfficePhone")
					.addScalar("billingRHOOfficeName")
					.addScalar("billingRHOOfficeAddress1")
					.addScalar("billingRHOOfficeAddress2")
					.addScalar("billingRHOOfficeAddress3")
					.addScalar("billingRHOOfficePhone")
					.addScalar("billingRHOOfficeEmail")
					.addScalar("rhoCityName")
					.addScalar("customerId")
					.addScalar("customerBusinessName")
					.addScalar("customerCode")
					.addScalar("customerAddress1")
					.addScalar("customerAddress2")
					.addScalar("customerAddress3")
					.addScalar("customerTypeCode")
					.addScalar("productId")
					.addScalar("productSeries")
					.addScalar("financialProductId")
					.addScalar("stateCode")
					.addScalar("fuelSurchargePercentage")
					.addScalar("fuelSurchargePercentageFormula")
					.addScalar("billGenerationDate")
					.addScalar("lcAmount")
					.addScalar("lcCharge")
					.addScalar("codAmount")
					.addScalar("codCharge")
					.addScalar("rtoCharge")
					.addScalar("billType")
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameterList("billingOfficeIds", billingOfficeIds)
					.setParameter("financialProductId", financialProductId)
					.setParameterList("invoiceNumbers", invoiceNumbers)
					.setResultTransformer(
							Transformers.aliasToBean(BillAliasTO.class)).list();
		} catch (Exception e) {
			LOGGER.error("ERROR : BillPrintingDAOImpl.getBillsForBillNumbers",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
			session = null;
		}

		LOGGER.debug("BillPrintingDAOImpl :: getBillsForBillNumbers() :: End --------> ::::::");
		return billAliasTOs;
	}

	/**
	 * @see com.ff.admin.billing.dao.BillPrintingDAO#getBillsForCustomers(java.util.List,
	 *      java.lang.String, java.lang.String, int) Nov 21, 2013
	 * @param custIds
	 * @param startDate
	 * @param endDate
	 * @param productId
	 * @return
	 * @throws CGBusinessException
	 *             getBillsForCustomers BillPrintingDAO kgajare
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillAliasTO> getBillsForCustomers(List<Integer> custIds,
			String startDate, String endDate, Integer financialProductId, 
			List<Integer> billingOfficeIds)
					throws CGSystemException {
		LOGGER.debug("BillPrintingDAOImpl :: getBillsForCustomers() :: Start --------> ::::::");
		List<BillAliasTO> billAliasTOs = null;
		Session session = null;
		String query = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			query = BillingConstants.GET_BILL_DATA_FOR_PRINTING
					+ "  fdc.CUSTOMER_ID in (:customers)";
			billAliasTOs = session
					.createSQLQuery(query)
					.addScalar("invoiceId")
					.addScalar("invoiceNumber")
					.addScalar("createdDate")
					.addScalar("fromDate")
					.addScalar("toDate")
					.addScalar("numberOfPickups")
					.addScalar("freight")
					.addScalar("riskSurcharge")
					.addScalar("docHandlingCharge")
					.addScalar("parcelHandlingCharge")
					.addScalar("airportHandlingCharge")
					.addScalar("otherCharges")
					.addScalar("total")
					.addScalar("fuelSurcharge")
					.addScalar("serviceTax")
					.addScalar("educationCess")
					.addScalar("higherEducationCess")
					.addScalar("stateTax")
					.addScalar("surchargeOnStateTax")
					.addScalar("grandTotal")
					.addScalar("grandTotalRoundedOff")
					.addScalar("billingOfficeName")
					.addScalar("billingOfficePhone")
					.addScalar("billingRHOOfficeName")
					.addScalar("billingRHOOfficeAddress1")
					.addScalar("billingRHOOfficeAddress2")
					.addScalar("billingRHOOfficeAddress3")
					.addScalar("billingRHOOfficePhone")
					.addScalar("billingRHOOfficeEmail")
					.addScalar("rhoCityName")
					.addScalar("customerId")
					.addScalar("customerBusinessName")
					.addScalar("customerCode")
					.addScalar("customerAddress1")
					.addScalar("customerAddress2")
					.addScalar("customerAddress3")
					.addScalar("customerTypeCode")
					.addScalar("productId")
					.addScalar("productSeries")
					.addScalar("financialProductId")
					.addScalar("stateCode")
					.addScalar("fuelSurchargePercentage")
					.addScalar("fuelSurchargePercentageFormula")
					.addScalar("billGenerationDate")
					.addScalar("lcAmount")
					.addScalar("lcCharge")
					.addScalar("codAmount")
					.addScalar("codCharge")
					.addScalar("rtoCharge")
					.addScalar("billType")
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameterList("billingOfficeIds", billingOfficeIds)
					.setParameter("financialProductId", financialProductId)
					.setParameterList("customers", custIds)
					.setResultTransformer(
							Transformers.aliasToBean(BillAliasTO.class)).list();
		} catch (Exception e) {
			LOGGER.error("ERROR : BillPrintingDAOImpl.getBillsForCustomers", e);
			throw new CGSystemException(e);
		} finally {
			session.close();
			session = null;
		}

		LOGGER.debug("BillPrintingDAOImpl :: getBillsForCustomers() :: End --------> ::::::");
		return billAliasTOs;
	}

	/**
	 * @see com.ff.admin.billing.dao.BillPrintingDAO#getConsignmentsForPrinting(java.lang.Integer)
	 * Nov 21, 2013
	 * @param invoiceId
	 * @return
	 * @throws CGSystemException
	 * getConsignmentsForPrinting
	 * BillPrintingDAO
	 * kgajare
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ConsignmentAliasTO> getConsignmentsForPrinting(Integer invoiceId)
			throws CGSystemException {
		LOGGER.debug("BillPrintingDAOImpl :: getConsignmentsForPrinting() :: Start --------> ::::::");
		List<ConsignmentAliasTO> consignmentAliasTOs = null;
		Session session = null;
		String query = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			query = BillingConstants.GET_CONSIGNMENT_DATA_FOR_PRINTING;
			consignmentAliasTOs = session.createSQLQuery(query)
					.addScalar("billingConsignmentId")
					.addScalar("consgId")
					.addScalar("consgNo")
					.addScalar("salesOfficeName")
					.addScalar("businessName")
					.addScalar("invoiceNumber")
					.addScalar("bookingDate")
					.addScalar("consignmentNumber")
					.addScalar("consignmentType")
					.addScalar("originCityName")
					.addScalar("originOfficeName")
					.addScalar("customerReferenceNumber")
					.addScalar("destinationCityName")
					.addScalar("finalWeight")
					/*.addScalar("weightModified")
		    .addScalar("newRateAdded")*/
					.addScalar("lcAmount")
					.addScalar("codAmount")
					.addScalar("finalSlabRate")
					.addScalar("lcCharge")
					.addScalar("codCharge")
					.addScalar("rtoCharge")
					.addScalar("riskSurCharge")
					.addScalar("documentHandlingCharge")
					.addScalar("parcelHandlingCharge")
					.addScalar("airportHandlingCharge")
					.addScalar("otherCharges")
					.addScalar("totalCharges")
					.addScalar("vendorCode")
					.addScalar("deliveryDate")
					.addScalar("deltaTransfer")
					.addScalar("rtoMarked")
					.addScalar("rowNumber")
					.addScalar("pageNumber")
					
					.setParameter("invoiceId", invoiceId)
					.setResultTransformer(
							Transformers.aliasToBean(ConsignmentAliasTO.class)).list();
		} catch (Exception e) {
			LOGGER.error("ERROR : BillPrintingDAOImpl.getConsignmentsForPrinting", e);
			throw new CGSystemException(e);
		} finally {
			session.close();
			session = null;
		}

		LOGGER.debug("BillPrintingDAOImpl :: getConsignmentsForPrinting() :: End --------> ::::::");
		return consignmentAliasTOs;
	}


	@SuppressWarnings("unchecked")
	public List<ReBillGDRAliasTO> getRebillingGDRDetails(Integer reBillId)throws CGSystemException{
		LOGGER.debug("ReBillingDAOImpl :: getRebillingGDRDetails() :: Start --------> ::::::");
		List<ReBillGDRAliasTO> reBillGDRAliasTOs = null;
		Session session = null;
		String query = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			query = BillingConstants.QRY_REBILLGDR_DETAILS;

			reBillGDRAliasTOs = session
					.createSQLQuery(query)
					.addScalar("reBillingID")
					.addScalar("invoiceNumber")
					.addScalar("businessName")
					.addScalar("officeName")
					.addScalar("bookingDate")
					.addScalar("consignmentNumber")
					.addScalar("consignmentType")
					.addScalar("cityName")
					.addScalar("finalWeight")
					.addScalar("contractFor")
					.addScalar("rateCalculatedFor")
					.addScalar("freightCharges")
					.addScalar("riskSurcharge")
					.addScalar("documentHandlingCharge")
					.addScalar("parcelHandlingCharge")
					.addScalar("airportHandlingCharge")
					.addScalar("otherCharges")
					.addScalar("totalCharges")

					.setParameter("reBillId", reBillId)
					.setResultTransformer(
							Transformers.aliasToBean(ReBillGDRAliasTO.class)).list();
		} catch (Exception e) {
			LOGGER.error("ERROR : ReBillingDAOImpl.getRebillingGDRDetails",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
			session = null;
		}

		LOGGER.debug("ReBillingDAOImpl :: getRebillingGDRDetails() :: End --------> ::::::");
		return reBillGDRAliasTOs;

	}

	//code added from config admin

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

	/* (non-Javadoc)
	 * @see com.ff.report.common.dao.BillPrintingDAO#getConsignmentsForBulkBillPrinting(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ConsignmentAliasTO> getConsignmentsForBulkBillPrinting(Integer invoiceId)
			throws CGSystemException {
		LOGGER.debug("BillPrintingDAOImpl :: getConsignmentsForPrinting() :: Start --------> ::::::");
		List<ConsignmentAliasTO> consignmentAliasTOs = null;
		Session session = null;
		String query = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			query = BillingConstants.GET_CONSIGNMENT_DATA_FOR_BULK_BILL_PRINTING;
			SQLQuery sqlQuery = session.createSQLQuery(query);
			sqlQuery
			.addScalar("billingConsignmentId")
			.addScalar("consgId")
			.addScalar("consgNo")
			.addScalar("salesOfficeName")
			.addScalar("businessName")
			.addScalar("invoiceNumber")
			.addScalar("bookingDate")
			.addScalar("consignmentType")
			.addScalar("originCityName")
			.addScalar("originOfficeName")
			.addScalar("customerReferenceNumber")
			.addScalar("destinationCityName")
			.addScalar("finalWeight")
			/*.addScalar("weightModified")
		    .addScalar("newRateAdded")*/
			.addScalar("lcAmount")
			.addScalar("codAmount")
			.addScalar("finalSlabRate")
			.addScalar("lcCharge")
			.addScalar("codCharge")
			.addScalar("rtoCharge")
			.addScalar("riskSurCharge")
			.addScalar("documentHandlingCharge")
			.addScalar("parcelHandlingCharge")
			.addScalar("airportHandlingCharge")
			.addScalar("otherCharges")
			.addScalar("totalCharges")
			.addScalar("vendorCode")
			.addScalar("deliveryDate")
			.addScalar("deltaTransfer")
			.addScalar("rtoMarked")
			.addScalar("rowNumber")
			.addScalar("pageNumber")
			.setParameter("paramInvoiceId", invoiceId);
			Query resultQuery = sqlQuery.setResultTransformer(Transformers.aliasToBean(ConsignmentAliasTO.class));
			LOGGER.debug("BillPrintingDAOImpl :: getConsignmentsForPrinting() :: SQL query formed : ",resultQuery.getQueryString());
			consignmentAliasTOs = resultQuery.list();
		} catch (Exception e) {
			LOGGER.error("ERROR : BillPrintingDAOImpl.getConsignmentsForPrinting", e);
			throw new CGSystemException(e);
		} finally {
			session.close();
			session = null;
		}
		LOGGER.debug("BillPrintingDAOImpl :: getConsignmentsForPrinting() :: End --------> ::::::");
		return consignmentAliasTOs;
	}
	
	@SuppressWarnings("unchecked")
	public List<CustomerBillingDO> getCustsByBillingBranchAndFinancialProductForReport(List<Integer> branchId,List<Integer> productIds)throws CGSystemException {
		LOGGER.debug("BillPrintingDAOImpl :: getCustsByBillingBranchAndFinancialProductList() :: START --------> ::::::");
		List<CustomerBillingDO> customerBillingList = null;
		List<CustomerBillingDO> customerBillingList1 = null;

		Session session = null;
		try {
			String queryName ="getNonContractedCustomersByBillingBranchForReport";
			String queryName1 = "getContractedCustomersByBillingBranchForReport";


			session = getHibernateTemplate().getSessionFactory().openSession();
			customerBillingList = getNonContractedCustomersByBillingBranchForReport(branchId, productIds);
			/*customerBillingList = session.getNamedQuery(queryName)
    	    		    		.setParameterList("billingOfficeId", branchId).setInteger("financialProductId", financialProdId).list();
			 */
			/* customerBillingList1 = session.getNamedQuery(queryName1)
    	    	    		.setParameterList("billingOfficeId", branchId).setInteger("financialProductId", financialProdId).list();*/
			customerBillingList1 = getContractedCustomersByBillingBranchForReport(branchId, productIds);
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
	
	private List<CustomerBillingDO> getNonContractedCustomersByBillingBranchForReport(List<Integer> branchId,List<Integer> productIds)
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
					.setParameterList("financialProductId", productIds)

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

	private List<CustomerBillingDO> getContractedCustomersByBillingBranchForReport(List<Integer> branchId,List<Integer> productIds)
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
					.setParameterList("financialProductId", productIds)

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
	
	//Created By Shaheed For All customers
	
	public List<BillAliasTO> getBillsForAllCustomers(String startDate, String endDate, Integer financialProductId, 
			List<Integer> billingOfficeIds)
					throws CGSystemException {
		LOGGER.debug("BillPrintingDAOImpl :: getBillsForCustomers() :: Start --------> ::::::");
		List<BillAliasTO> billAliasTOs = null;
		Session session = null;
		String query = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			query = BillingConstants.GET_BILL_DATA_FOR_ALL_CUSTOMERS;
			billAliasTOs = session
					.createSQLQuery(query)
					.addScalar("invoiceId")
					.addScalar("invoiceNumber")
					.addScalar("createdDate")
					.addScalar("fromDate")
					.addScalar("toDate")
					.addScalar("numberOfPickups")
					.addScalar("freight")
					.addScalar("riskSurcharge")
					.addScalar("docHandlingCharge")
					.addScalar("parcelHandlingCharge")
					.addScalar("airportHandlingCharge")
					.addScalar("otherCharges")
					.addScalar("total")
					.addScalar("fuelSurcharge")
					.addScalar("serviceTax")
					.addScalar("educationCess")
					.addScalar("higherEducationCess")
					.addScalar("stateTax")
					.addScalar("surchargeOnStateTax")
					.addScalar("grandTotal")
					.addScalar("grandTotalRoundedOff")
					.addScalar("billingOfficeName")
					.addScalar("billingOfficePhone")
					.addScalar("billingRHOOfficeName")
					.addScalar("billingRHOOfficeAddress1")
					.addScalar("billingRHOOfficeAddress2")
					.addScalar("billingRHOOfficeAddress3")
					.addScalar("billingRHOOfficePhone")
					.addScalar("billingRHOOfficeEmail")
					.addScalar("rhoCityName")
					.addScalar("customerId")
					.addScalar("customerBusinessName")
					.addScalar("customerCode")
					.addScalar("customerAddress1")
					.addScalar("customerAddress2")
					.addScalar("customerAddress3")
					.addScalar("customerTypeCode")
					.addScalar("productId")
					.addScalar("productSeries")
					.addScalar("financialProductId")
					.addScalar("stateCode")
					.addScalar("fuelSurchargePercentage")
					.addScalar("fuelSurchargePercentageFormula")
					.addScalar("billGenerationDate")
					.addScalar("lcAmount")
					.addScalar("lcCharge")
					.addScalar("codAmount")
					.addScalar("codCharge")
					.addScalar("rtoCharge")
					.addScalar("billType")
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameterList("billingOfficeIds", billingOfficeIds)
					.setParameter("financialProductId", financialProductId)
					.setResultTransformer(
							Transformers.aliasToBean(BillAliasTO.class)).list();
		} catch (Exception e) {
			LOGGER.error("ERROR : BillPrintingDAOImpl.getBillsForCustomers", e);
			throw new CGSystemException(e);
		} finally {
			session.close();
			session = null;
		}

		LOGGER.debug("BillPrintingDAOImpl :: getBillsForCustomers() :: End --------> ::::::");
		return billAliasTOs;
	}

}
