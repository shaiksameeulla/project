package com.ff.rate.configuration.ratecontract.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.CustomerWrapperDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.ratemanagement.masters.ContractPaymentBillingLocationDO;
import com.ff.domain.ratemanagement.masters.RateContractDO;
import com.ff.domain.ratemanagement.masters.RateContractSpocDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationWrapperDO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.common.constants.RateErrorConstants;
import com.ff.rate.configuration.ratecontract.constants.RateContractConstants;
import com.ff.rate.configuration.ratequotation.dao.RateQuotationDAOImpl;
import com.ff.to.ratemanagement.masters.RateContractTO;

public class RateContractDAOImpl extends RateQuotationDAOImpl implements RateContractDAO {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RateContractDAOImpl.class);

	@Override
	public RateContractDO saveRateContractBillingDtls(
			RateContractDO rateContractDO) throws CGSystemException {
		LOGGER.debug("RateContractDAOImpl::saveRateContractBillingDtls()::START");
		Session session=null;
		try {
			//getHibernateTemplate().saveOrUpdate(rateContractDO);
			/* To update respective fields for rate contract billing details */
			session = createSession();
			Query query = session.getNamedQuery(
					RateContractConstants.QRY_UPDATE_RATE_CON_BILL_DTLS);
			//SET following details:
			query.setParameter(RateCommonConstants.PARAM_CUSTOMER_ID, 
					rateContractDO.getCustomerId());//Customer
			query.setParameter(RateContractConstants.PARAM_FROM_DT, 
					rateContractDO.getValidFromDate());//From Date
			query.setParameter(RateContractConstants.PARAM_TO_DT, 
					rateContractDO.getValidToDate());//To Date
			query.setParameter(RateContractConstants.PARAM_BILL_CON_TYPE, 
					rateContractDO.getBillingContractType());//N or R
			query.setParameter(RateContractConstants.PARAM_BILL_TYPE, 
					rateContractDO.getTypeOfBilling());//DBDP, CBCP, DBCP
			query.setParameter(RateContractConstants.PARAM_BILL_MODE, 
					rateContractDO.getModeOfBilling());//H or S
			query.setParameter(RateContractConstants.PARAM_BILL_CYCLE, 
					rateContractDO.getBillingCycle());//M or F
			query.setParameter(RateContractConstants.PARAM_PAYMENT_TERM, 
					rateContractDO.getPaymentTerm());//Payment Terms i.e. P001,P002..P006
			query.setParameter(RateContractConstants.PARAM_UPDATED_BY, 
					rateContractDO.getUpdatedBy());//Updated By User
			query.setParameter(RateContractConstants.PARAM_UPDATED_DT, 
					Calendar.getInstance().getTime());
			//WHERE rate contract id is:
			query.setParameter(RateCommonConstants.PARAM_RATE_CONTRACT_ID, 
					rateContractDO.getRateContractId());

			int i = query.executeUpdate();

			if(i<1){
				throw new Exception(RateContractConstants.ERR_DTLS_NOT_UPDATED);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurs in RateContractDAOImpl::saveRateContractBillingDtls()::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("RateContractDAOImpl::saveRateContractBillingDtls()::END");
		return rateContractDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RateContractDO searchRateContractBillingDtls(
			RateContractTO rateContractTO) throws CGSystemException {
		LOGGER.debug("RateContractDAOImpl::searchRateContractBillingDtls()::START");
		List<RateContractDO> rateContractDOs = null;
		try{
			String params[] = { RateContractConstants.RATE_CONTRACT_ID };
			Object values[] = { rateContractTO.getRateContractId() };
			rateContractDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					RateContractConstants.QRY_GET_RATE_CONTRACT_BY_ID, params,
					values);
			LOGGER.debug("RateContractDAOImpl::searchRateContractBillingDtls()::END");
			return (!StringUtil.isEmptyColletion(rateContractDOs)) ? rateContractDOs.get(0) : null;
		}catch (Exception e) {
			LOGGER.error("ERROR : RateContractDAOImpl.getRateIndustryType", e);
			throw new CGSystemException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateContractDO> searchContractDetails(RateContractTO contractTO) throws CGSystemException {
		LOGGER.trace("RateContractDAOImpl::searchContractDetails()::START");
		List<RateContractDO> rateContractDOs = null;
		/*Session session = null;
		Transaction tx = null;*/
		try {

			String[] paramNames = { RateContractConstants.CONTRACT_ID,
					RateContractConstants.RATE_CONTRACT_NO,
					RateContractConstants.CREATED_BY ,
					RateContractConstants.RATE_CONTRACT_TYPE};
			Object[] values = { contractTO.getRateContractId(),
					contractTO.getRateContractNo(), contractTO.getCreatedBy(),contractTO.getRateContractType() };
			rateContractDOs = (List<RateContractDO>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(RateContractConstants.QRY_SEARCH_CONTRACT, paramNames, values);

			/*
			Date fromdate = null;
			long dateDiff;
			fromdate = rateContractDOs.get(0).getValidToDate();*/
			/*if (!StringUtil.isNull(fromdate)) {
				dateDiff = DateUtil
						.DayDifferenceBetweenTwoDatesIncludingBackDate(
								fromdate, DateUtil.getCurrentDate());
				if (!StringUtil.isNull(dateDiff) && dateDiff > 0) {
					session = getHibernateTemplate().getSessionFactory().openSession();
					tx = session.beginTransaction();
					Query query = session
							.getNamedQuery(RateCommonConstants.QRY_UPDATE_CONTRACT_STATUS);
					query.setParameter(
							RateCommonConstants.PARAM_RATE_CONTRACT_ID,
							rateContractDOs.get(0).getRateContractId());
					query.setParameter(
							RateCommonConstants.PARAM_CONTRACT_STATUS,
							RateContractConstants.CONTRACT_INACTIVE);

					query.executeUpdate();
					tx.commit();
					session.flush();

				}
			}*/
		} catch (Exception e) {
			/*tx.rollback();*/
			LOGGER.error("Error occured in RateContractDAOImpl :: searchContractDetails()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} /*finally {
			session.close();
		}*/
		LOGGER.trace("RateContractDAOImpl::searchContractDetails()::END");
		return rateContractDOs;
	}

	@Override
	public boolean saveRateContractPickupDlvDtls(List<ContractPaymentBillingLocationDO>
	domainList) throws CGSystemException {
		LOGGER.debug("RateContractDAOImpl::saveRateContractPickupDtls()::START");
		boolean result = Boolean.FALSE;
		try {
			getHibernateTemplate().saveOrUpdateAll(domainList);
			result = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Exception occurs in RateContractDAOImpl::saveRateContractPickupDtls()::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateContractDAOImpl::saveRateContractPickupDtls()::END");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerWrapperDO> searchCustomerInfo(String custName)
			throws CGSystemException {
		List<CustomerWrapperDO> custDOs = null;
		LOGGER.debug("RateContractDAOImpl::searchCustomerInfo()::START");
		try {
			custDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					RateContractConstants.QRY_SEARCH_CUSTOMER_INFO, "custName",
					custName+"%");
		} catch (Exception e) {
			LOGGER.error("Exception occurs in RateContractDAOImpl::searchCustomerInfo()::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateContractDAOImpl::searchCustomerInfo()::END");
		return custDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateQuotationWrapperDO> rateContractListViewDetails(
			Integer userId, Integer[] region, Integer[] city, String fromDate,
			String toDate, String contractNo, String status, String type, String officeType)
					throws CGSystemException {
		LOGGER.trace("RateContractDAOImpl::rateContractListViewDetails()::START");


		List<RateQuotationWrapperDO> rqwDOList = null;
		try {
			if(StringUtil.isStringEmpty(contractNo)){

				if(officeType.equalsIgnoreCase(CommonConstants.OFF_TYPE_CORP_OFFICE)){	
					/*String query = RateContractConstants.QRY_GET_LIST_VIEW_RATE_CONTRACT_FOR_CO;

				String params[] = {RateContractConstants.PARAM_FROM_DATE, RateContractConstants.PARAM_TO_DATE, RateContractConstants.PARAM_STATUS};

				Object values[] = {DateUtil.stringToDDMMYYYYFormat(fromDate),DateUtil.stringToDDMMYYYYFormat(toDate),status};

				rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);*/
					if(StringUtil.isEmpty(city)){
						String query = RateContractConstants.QRY_GET_LIST_VIEW_RATE_CONTRACT_RO;

						String params[] = {RateContractConstants.PARAM_REGION, RateContractConstants.PARAM_FROM_DATE, RateContractConstants.PARAM_TO_DATE, RateContractConstants.PARAM_STATUS};

						Object values[] = {region,DateUtil.stringToDDMMYYYYFormat(fromDate),DateUtil.stringToDDMMYYYYFormat(toDate),status};

						rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
					}else{
						String query = RateContractConstants.QRY_GET_LIST_VIEW_RATE_CONTRACT_RO_WITH_CITY;

						String params[] = {RateContractConstants.PARAM_CITY, RateContractConstants.PARAM_FROM_DATE, RateContractConstants.PARAM_TO_DATE, RateContractConstants.PARAM_STATUS};

						Object values[] = {city,DateUtil.stringToDDMMYYYYFormat(fromDate),DateUtil.stringToDDMMYYYYFormat(toDate),status};

						rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
					}

				}else if(officeType.equalsIgnoreCase(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){
					if(StringUtil.isEmpty(city)){
						String query = RateContractConstants.QRY_GET_LIST_VIEW_RATE_CONTRACT_RO;

						String params[] = {RateContractConstants.PARAM_REGION, RateContractConstants.PARAM_FROM_DATE, RateContractConstants.PARAM_TO_DATE, RateContractConstants.PARAM_STATUS};

						Object values[] = {region,DateUtil.stringToDDMMYYYYFormat(fromDate),DateUtil.stringToDDMMYYYYFormat(toDate),status};

						rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
					}else{
						String query = RateContractConstants.QRY_GET_LIST_VIEW_RATE_CONTRACT_RO_WITH_CITY;

						String params[] = {RateContractConstants.PARAM_CITY, RateContractConstants.PARAM_FROM_DATE, RateContractConstants.PARAM_TO_DATE, RateContractConstants.PARAM_STATUS};

						Object values[] = {city,DateUtil.stringToDDMMYYYYFormat(fromDate),DateUtil.stringToDDMMYYYYFormat(toDate),status};

						rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
					}
				}else if(officeType.equalsIgnoreCase(CommonConstants.OFF_TYPE_HUB_OFFICE)){

					String query = RateContractConstants.QRY_GET_LIST_VIEW_RATE_CONTRACT_HO;

					String params[] = {RateContractConstants.PARAM_USER_ID,RateContractConstants.PARAM_CITY, RateContractConstants.PARAM_FROM_DATE, RateContractConstants.PARAM_TO_DATE, RateContractConstants.PARAM_STATUS};

					Object values[] = {userId,city,DateUtil.stringToDDMMYYYYFormat(fromDate),DateUtil.stringToDDMMYYYYFormat(toDate),status};

					rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);

				}
				else{

					String query = RateContractConstants.QRY_GET_LIST_VIEW_RATE_CONTRACT;

					String params[] = {RateContractConstants.PARAM_USER_ID, RateContractConstants.PARAM_FROM_DATE, RateContractConstants.PARAM_TO_DATE, RateContractConstants.PARAM_STATUS};

					Object values[] = {userId,DateUtil.stringToDDMMYYYYFormat(fromDate),DateUtil.stringToDDMMYYYYFormat(toDate),status};

					rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);

				}
			}else{


				if(officeType.equalsIgnoreCase(CommonConstants.OFF_TYPE_CORP_OFFICE)){
					String query = RateContractConstants.QRY_GET_RATE_CONTRACT_FOR_CO;

					String params[] = {RateContractConstants.PARAM_CONTRACT_NO};

					Object values[] = {contractNo};

					rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
				}else if(officeType.equalsIgnoreCase(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){

					String query = RateContractConstants.QRY_GET_RATE_CONTRACT_FOR_RO;

					String params[] = {RateContractConstants.PARAM_REGION, RateContractConstants.PARAM_CONTRACT_NO};

					Object values[] = {region,contractNo};

					rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
				}else if(officeType.equalsIgnoreCase(CommonConstants.OFF_TYPE_HUB_OFFICE)){
					String query = RateContractConstants.QRY_GET_RATE_CONTRACT_FOR_HO;

					String params[] = {RateContractConstants.PARAM_USER_ID,RateContractConstants.PARAM_CITY, RateContractConstants.PARAM_CONTRACT_NO};

					Object values[] = {userId,city,contractNo};

					rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);

				}else{

					String query = RateContractConstants.QRY_GET_RATE_CONTRACT_FOR_USER;

					String params[] = {RateContractConstants.PARAM_USER_ID, RateContractConstants.PARAM_CONTRACT_NO};

					Object values[] = {userId,contractNo};

					rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
				}			

			}

		} catch (Exception e) {
			LOGGER.error("Error occured in RateContractDAOImpl :: rateContractListViewDetails()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("RateContractDAOImpl::rateContractListViewDetails()::END");
		return rqwDOList;
	}

	/* The below method has many logger statements in it. They have been added to debug a crucial issue.
	 * Issue : Contract number & distribution channels is getting stamped as null in customer table after contract submit.
	 * The extra logger statements will be removed once the issue is resolved.
	 * -- Added by Tejas on 21/07/2015 */
	@Override
	public Boolean submitContract(RateContractTO contractTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("RateContractDAOImpl::submitContract()::START");
		Boolean flag = false;
		Boolean isUpdated = Boolean.FALSE;
		Session session = null;
		Transaction tx = null;
		Query query = null;

		try {
			session = createSession();
			tx = session.beginTransaction();
			query = session.getNamedQuery(RateCommonConstants.QRY_UPDATE_CONTRACT_STATUS);
			query.setParameter(RateCommonConstants.PARAM_RATE_CONTRACT_ID,contractTO.getRateContractId());

			// If the contract is being "renewed"
			// IF-1
			if(!StringUtil.isEmptyInteger(contractTO.getOriginatedRateContractId()) 
					|| (!StringUtil.isStringEmpty(contractTO.getSoldToCode()) && contractTO.getContractStatus().equals(RateContractConstants.CREATED))){
				LOGGER.warn("RateContractDAOImpl::submitContract()::Inside IF-1");
				// If contract number or distribution channel is null, then throw an exception
				if(StringUtil.isStringEmpty(contractTO.getRateContractNo()) || StringUtil.isStringEmpty(contractTO.getDistributionChannel())){
					LOGGER.error("RateContractDAOImpl::submitContract():: Contract_no or distribution_channel is null. Throwing business exception -- ["
							+ contractTO.getRateContractNo() + ", " + contractTO.getDistributionChannel() + "]");
					ExceptionUtil.prepareBusinessException(RateErrorConstants.CONTRACT_NOT_SUBMITTED_SUCCESSFULLY);
				}

				query.setParameter(RateCommonConstants.PARAM_CONTRACT_STATUS,RateContractConstants.CONTRACT_ACTIVE);
				query.setParameter(RateContractConstants.DT_TO_BRANCH, "N");
				contractTO.setContractStatus(RateContractConstants.ACTIVE);

				// inner query
				flag = true;
				Query qry = session.getNamedQuery(RateContractConstants.QRY_UPDATE_CONTRACT_NO_AND_DSTRB_CHNL);
				qry.setParameter(RateCommonConstants.CUSTOMER_ID,contractTO.getRateQuotationTO().getCustomer().getCustomerId());
				qry.setParameter(RateCommonConstants.CONTRACT_NO,contractTO.getRateContractNo());
				qry.setParameter(RateContractConstants.DISTRIBUTION_CHANNELS, contractTO.getDistributionChannel());
				qry.setParameter(RateContractConstants.SAP_STATUS, contractTO.getCustomerSapStatus());
				qry.setParameter(RateContractConstants.PARAM_STATUS, RateContractConstants.ACTIVE);
				qry.setParameter(RateContractConstants.DT_TO_BRANCH, RateCommonConstants.NO);
				printCustomerDetailsForDebugging(session,contractTO,"before");
				int innerQueryUpdateCount = qry.executeUpdate();
				printCustomerDetailsForDebugging(session,contractTO,"after");
				LOGGER.warn("RateContractDAOImpl::submitContract():: Rows affected in customer table after update = " + innerQueryUpdateCount);

				// If the update query fails, then throw an exception
				if(innerQueryUpdateCount <= 0){
					LOGGER.error("RateContractDAOImpl::submitContract():: Error while updating customer table[" 
							+ contractTO.getRateContractNo() + ", " + contractTO.getDistributionChannel() + "]");
					ExceptionUtil.prepareBusinessException(RateErrorConstants.CONTRACT_NOT_SUBMITTED_SUCCESSFULLY);
				}
			}

			// ELSE-IF-1 (If the user modifies a non-renewed contract via super-edit)
			else if (StringUtil.isEmptyInteger(contractTO.getOriginatedRateContractId()) && 
					(!StringUtil.isStringEmpty(contractTO.getUserType()) && (contractTO.getUserType().equals("S")))){
				LOGGER.warn("RateContractDAOImpl::submitContract()::Inside ELSE-IF-1");
				// If contract number or distribution channel is null, then throw an exception
				if(StringUtil.isStringEmpty(contractTO.getRateContractNo()) || StringUtil.isStringEmpty(contractTO.getDistributionChannel())){
					LOGGER.error("RateContractDAOImpl::submitContract():: Contract_no or distribution_channel is null. Throwing business exception -- ["
							+ contractTO.getRateContractNo() + ", " + contractTO.getDistributionChannel() + "]");
					ExceptionUtil.prepareBusinessException(RateErrorConstants.CONTRACT_NOT_SUBMITTED_SUCCESSFULLY);
				}

				query.setParameter(RateCommonConstants.PARAM_CONTRACT_STATUS,RateContractConstants.CONTRACT_ACTIVE);
				query.setParameter(RateContractConstants.DT_TO_BRANCH, "N");
				contractTO.setContractStatus(RateContractConstants.ACTIVE);

				// inner query
				flag = true;
				Query qry = session.getNamedQuery(RateContractConstants.QRY_UPDATE_CONTRACT_NO_AND_DSTRB_CHNL);
				qry.setParameter(RateCommonConstants.CUSTOMER_ID,contractTO.getRateQuotationTO().getCustomer().getCustomerId());
				qry.setParameter(RateCommonConstants.CONTRACT_NO,contractTO.getRateContractNo());
				qry.setParameter(RateContractConstants.DISTRIBUTION_CHANNELS, contractTO.getDistributionChannel());
				qry.setParameter(RateContractConstants.SAP_STATUS, contractTO.getCustomerSapStatus());
				qry.setParameter(RateContractConstants.PARAM_STATUS, RateContractConstants.ACTIVE);
				qry.setParameter(RateContractConstants.DT_TO_BRANCH, RateCommonConstants.NO);
				printCustomerDetailsForDebugging(session,contractTO,"before");
				int innerQueryUpdateCount = qry.executeUpdate();
				printCustomerDetailsForDebugging(session,contractTO,"after");
				LOGGER.warn("RateContractDAOImpl::submitContract():: Rows affected in customer table after update = " + innerQueryUpdateCount);

				// If the update query fails, then throw an exception
				if(innerQueryUpdateCount <= 0){
					LOGGER.error("RateContractDAOImpl::submitContract():: Error while updating customer table[" 
							+ contractTO.getRateContractNo() + ", " + contractTO.getDistributionChannel() + "]");
					ExceptionUtil.prepareBusinessException(RateErrorConstants.CONTRACT_NOT_SUBMITTED_SUCCESSFULLY);
				}
			}

			// If the user is a super-edit user
			// ELSE-IF-2
			else if ((!StringUtil.isStringEmpty(contractTO.getUserType()) && (contractTO.getUserType().equals("S")))){
				LOGGER.warn("RateContractDAOImpl::submitContract()::Inside ELSE-IF-2");
				query.setParameter(RateCommonConstants.PARAM_CONTRACT_STATUS,RateContractConstants.CONTRACT_ACTIVE);
				query.setParameter(RateContractConstants.DT_TO_BRANCH, "N");
				contractTO.setContractStatus(RateContractConstants.ACTIVE);
			}

			// This code block is used in the general scenario where we submit the contract for the first time
			// DEFAULT-ELSE
			else{
				LOGGER.warn("RateContractDAOImpl::submitContract()::Inside DEFAULT-ELSE");
				// If contract number or distribution channel is null, then throw an exception
				if(StringUtil.isStringEmpty(contractTO.getRateContractNo()) || StringUtil.isStringEmpty(contractTO.getDistributionChannel())){
					LOGGER.error("RateContractDAOImpl::submitContract():: Contract_no or distribution_channel is null. Throwing business exception -- ["
							+ contractTO.getRateContractNo() + ", " + contractTO.getDistributionChannel() + "]");
					ExceptionUtil.prepareBusinessException(RateErrorConstants.CONTRACT_NOT_SUBMITTED_SUCCESSFULLY);
				}

				query.setParameter(RateCommonConstants.PARAM_CONTRACT_STATUS,RateContractConstants.CONTRACT_SUBMITTED);
				query.setParameter(RateContractConstants.DT_TO_BRANCH, "N");
				contractTO.setContractStatus(RateContractConstants.CONTRACT_SUBMITTED);

				// inner query
				flag = true;
				Query qry = session.getNamedQuery(RateContractConstants.QRY_UPDATE_CONTRACT_NO_AND_DSTRB_CHNL);
				qry.setParameter(RateCommonConstants.CUSTOMER_ID,contractTO.getRateQuotationTO().getCustomer().getCustomerId());
				qry.setParameter(RateCommonConstants.CONTRACT_NO,contractTO.getRateContractNo());
				qry.setParameter(RateContractConstants.DISTRIBUTION_CHANNELS, contractTO.getDistributionChannel());
				qry.setParameter(RateContractConstants.SAP_STATUS, contractTO.getCustomerSapStatus());
				qry.setParameter(RateContractConstants.PARAM_STATUS, RateContractConstants.INACTIVE);
				if(RateContractConstants.BILL_TYPE_DBCP.equals(contractTO.getTypeOfBilling())){
					qry.setParameter(RateContractConstants.DT_TO_BRANCH, RateCommonConstants.NO);
				}
				else{
					qry.setParameter(RateContractConstants.DT_TO_BRANCH, CommonConstants.CHARACTER_R);
				}
				printCustomerDetailsForDebugging(session,contractTO,"before");
				int innerQueryUpdateCount = qry.executeUpdate();
				printCustomerDetailsForDebugging(session,contractTO,"after");
				LOGGER.warn("RateContractDAOImpl::submitContract():: Rows affected in customer table after update = " + innerQueryUpdateCount);

				// If the update query fails, then throw an exception
				if(innerQueryUpdateCount <= 0){
					LOGGER.error("RateContractDAOImpl::submitContract():: Error while updating customer table[" 
							+ contractTO.getRateContractNo() + ", " + contractTO.getDistributionChannel() + "]");
					ExceptionUtil.prepareBusinessException(RateErrorConstants.CONTRACT_NOT_SUBMITTED_SUCCESSFULLY);
				}
			}

			int outerQueryUpdateCount = query.executeUpdate();
			LOGGER.warn("RateContractDAOImpl::submitContract():: Rows affected in contract table after update = " + outerQueryUpdateCount);
			if(outerQueryUpdateCount <= 0){
				LOGGER.error("RateContractDAOImpl::submitContract():: Error while updating contract table[" 
						+ contractTO.getRateContractNo() + ", " + contractTO.getDistributionChannel() + "]");
				ExceptionUtil.prepareBusinessException(RateErrorConstants.CONTRACT_NOT_SUBMITTED_SUCCESSFULLY);
			}

			// IF-2 This block of code will update the status of the old contract
			if(!StringUtil.isEmptyInteger(contractTO.getOriginatedRateContractId())){
				LOGGER.warn("RateContractDAOImpl::submitContract()::Inside IF-2");
				query = session.getNamedQuery(RateContractConstants.UPDATE_RATE_CONTRACT_RENEWED_STATUS);
				query.setString(RateContractConstants.IS_RENEWED, RateContractConstants.YES);
				query.setDate(RateContractConstants.VALID_TO_DATE,  contractTO.getOldExpDate())	;
				query.setInteger(RateContractConstants.RATE_CONTRACT_ID, contractTO.getOriginatedRateContractId());
				if(query.executeUpdate() <= 0){
					LOGGER.error("RateContractDAOImpl::submitContract():: Error while updating contract table[" 
							+ contractTO.getRateContractNo() + ", " + contractTO.getDistributionChannel() + "]");
					ExceptionUtil.prepareBusinessException(RateErrorConstants.CONTRACT_NOT_SUBMITTED_SUCCESSFULLY);
				}
			}

			if(flag){
				LOGGER.error("RateContractDAOImpl::submitContract()::Data updated successfully in customer table:: [" 
						+ contractTO.getRateContractNo() + ", " + contractTO.getDistributionChannel() + "]");
			}
			else{
				LOGGER.error("RateContractDAOImpl::submitContract()::Data not updated successfully in customer table:: [" 
						+ contractTO.getRateContractNo() + ", " + contractTO.getDistributionChannel() + "]");
			}
			tx.commit();
			session.flush();
			isUpdated = Boolean.TRUE;
		}catch(CGBusinessException e){ 
			tx.rollback();
			LOGGER.error("Error occurred in RateContractDAOImpl :: submitContract()..:", e);
			throw e;
		}
		catch (Exception e) {
			tx.rollback();
			LOGGER.error("Error occurred in RateContractDAOImpl :: submitContract()..:", e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.debug("RateContractDAOImpl::submitContract()::END");
		return isUpdated;
	}

	/* The below method has been added to simply retrieve the required columns from the customer table.
	 * This method is purely used for debugging purpose & does not follow standard java coding practices. */
	@SuppressWarnings("unchecked")
	private void printCustomerDetailsForDebugging(Session session, RateContractTO contractTO, String beforeAfter){
		try{
			String sqlQuery = "select " +
					"cu.CUSTOMER_ID,cu.CONTRACT_NO,cu.CUSTOMER_CODE,cu.DISTRIBUTION_CHANNELS,cu.CUR_STATUS,cu.DT_SAP_OUTBOUND,cu.DT_TO_BRANCH " +
					"from ff_d_customer cu where cu.CUSTOMER_ID = :custId";
			Query query  = session.createSQLQuery(sqlQuery);
			Integer custId = contractTO.getRateQuotationTO().getCustomer().getCustomerId();
			query.setParameter("custId", custId);
			List<Object[]> list = (List<Object[]>)query.list();
			if(!StringUtil.isEmptyColletion(list)){
				Object[] objArr = list.get(0);
				LOGGER.warn("RateContractDAOImpl::printCustomerDetailsForDebugging():: Customer details " + beforeAfter + " update = [" 
						+ objArr[0] + ", "
						+ objArr[1]	+ ", "
						+ objArr[2]	+ ", "
						+ objArr[3]	+ ", "
						+ objArr[4]	+ ", "
						+ objArr[5]	+ ", "
						+ objArr[6]	+ "]");
			}
			else{
				LOGGER.warn("RateContractDAOImpl::printCustomerDetailsForDebugging():: Customer-Id does not exist in the database [" 
						+ custId + "]");
			}
		}
		catch(Exception e){
			LOGGER.error("RateContractDAOImpl::printCustomerDetailsForDebugging()::ERROR",e);
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public CustomerDO getCustById(Integer customerId)
			throws CGSystemException {
		LOGGER.debug("RateContractDAOImpl::getCustById()::START");
		CustomerDO custNewDO = null;
		Session session=null;
		try {
			session=createSession();
			Criteria cr=session.createCriteria(CustomerDO.class,"cust");
			cr.add(Restrictions.eq(RateCommonConstants.PARAM_CUSTOMER_ID, customerId));
			List<CustomerDO> custNewDOs = (List<CustomerDO>)cr.list();
			if(!StringUtil.isEmptyColletion(custNewDOs)){
				custNewDO = custNewDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurs in RateContractDAOImpl::getCustById()::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("RateContractDAOImpl::getCustById()::END");
		return custNewDO;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<ContractPaymentBillingLocationDO> searchRateContractPickupDlvDtls(
			RateContractTO rateContractTO) throws CGSystemException {
		LOGGER.trace("RateContractDAOImpl::searchRateContractPickupDlvDtls()::START");
		List<ContractPaymentBillingLocationDO> domainList = null;
		Session session = null;
		try {
			session = createSession();
			Criteria cr = session.createCriteria(ContractPaymentBillingLocationDO.class, "conPayBillLoc");
			cr.add(Restrictions.eq("conPayBillLoc.rateContractId",rateContractTO.getRateContractId()));
			domainList = cr.list();
		} catch(Exception e) {
			LOGGER.error("Exception occurs in RateContractDAOImpl::searchRateContractPickupDlvDtls::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("RateContractDAOImpl::searchRateContractPickupDlvDtls()::END");
		return domainList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RateContractDO renewContract(Integer contractId, Integer quotationId, String contractNo, Integer userId, RateContractDO rateContractDO)
			throws CGSystemException {
		LOGGER.trace("RateContractDAOImpl::renewContract()::START");
		/*List<RateContractDO> rateContractDOList = null;
		RateContractDO rateContractDO = null;*/
		Session session = null;
		Transaction tx = null;

		try{

			session = createSession();

			/*String params[] = { RateContractConstants.RATE_CONTRACT_ID };
			Object values[] = { contractId };
			rateContractDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					RateContractConstants.QRY_GET_RATE_CONTRACT_BY_ID, params,
					values);
			if(!CGCollectionUtils.isEmpty(rateContractDOList)){
			rateContractDO = createRenewContract(rateContractDOList.get(0),contractNo, userId);
			}*/
			tx = session.beginTransaction();

			if(!StringUtil.isNull(rateContractDO)){
				rateContractDO = (RateContractDO)session.merge(rateContractDO);
			}
			tx.commit();
			session.flush();

		} catch (Exception e) {
			tx.rollback();
			LOGGER.error("Error occured in RateContractDAOImpl :: renewContract()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally{
			session.close();
		}
		LOGGER.trace("RateContractDAOImpl::renewContract()::END");
		return rateContractDO;
	}

	/*private RateContractDO createRenewContract(RateContractDO rateContractDO, String contractNo, Integer userId) 
			throws CGSystemException{
		LOGGER.trace("RateContractDAOImpl::createRenewContract()::START");
		RateQuotationDO rateQuotationDO = null;
		Set<ContractPaymentBillingLocationDO> blDOSet = null;
		Set<ContractPaymentBillingLocationDO> blocDOSet = null;
		Set<RateContractSpocDO> spocDOSet = null;
		Set<RateContractSpocDO> spDOSet = null;
		Session session = null;
		Transaction tx = null;
		Integer contractId = null;
		try{

			session = createSession();
			rateQuotationDO = rateContractDO.getRateQuotationDO();

			rateQuotationDO = createCopyQuotation(rateQuotationDO, rateQuotationDO.getRateQuotationNo(), RateQuotationConstants.CHAR_C);

			rateQuotationDO.getCustomer().setPanNo(rateContractDO.getRateQuotationDO().getCustomer().getPanNo());
			rateQuotationDO.getCustomer().setTanNo(rateContractDO.getRateQuotationDO().getCustomer().getTanNo());
			blDOSet = rateContractDO.getConPayBillLocDO();
			spDOSet = rateContractDO.getRateContractSpocDO();

			contractId = rateContractDO.getRateContractId();
			rateContractDO.setRateContractId(null);
			rateContractDO.setRateQuotationDO(rateQuotationDO);

			RateContractDO rcDO = new RateContractDO();
			rcDO.setRateContractId(contractId);

			rateContractDO.setOriginatedRateContractDO(rcDO);

			 rateQuotationDO.setRateContractDO(rateContractDO);
			 //rateContractDO.setRateContractId(null);
			 rateContractDO.setValidFromDate(null);
			 rateContractDO.setValidToDate(null);
			 rateContractDO.setCreatedDate(DateUtil.getCurrentDate());
			 rateContractDO.setUpdatedDate(null);
			 rateContractDO.setRateContractNo(contractNo);
			 rateContractDO.setUserId(userId);
			 rateContractDO.setContractStatus(RateContractConstants.CREATED);
			 rateContractDO.setCustomerId(rateQuotationDO.getCustomer().getCustomerId());


			if(!CGCollectionUtils.isEmpty(blDOSet)){
			blocDOSet = new HashSet<ContractPaymentBillingLocationDO>(blDOSet.size());

			 for(ContractPaymentBillingLocationDO BLDo : blDOSet){

				 BLDo.setContractPaymentBillingLocationId(null);

				 BLDo.setRateContractDO(rateContractDO);
				 PickupDeliveryLocationDO pdlDO = new PickupDeliveryLocationDO();

				 pdlDO = BLDo.getPickupDeliveryLocation();

				 pdlDO.setPickupDlvLocId(null);

				 PickupDeliveryContractDO pdcDO =  new PickupDeliveryContractDO();

				 pdcDO = pdlDO.getPickupDlvContract();

				 pdcDO.setContractId(null);

				 CustomerDO cDO = new CustomerDO();

				 cDO = pdcDO.getCustomer();

				 cDO.setCustomerId(null);

				 pdcDO.setCustomer(cDO);

				 pdlDO.setPickupDlvContract(pdcDO);

				 AddressDO aDO = new AddressDO();

				 aDO = pdlDO.getAddress();

				 aDO.setAddressId(null);

				 pdlDO.setAddress(aDO);


				 BLDo.setPickupDeliveryLocation(pdlDO);


				 blocDOSet.add(BLDo);
			 }	 
			}else{
				blocDOSet = new HashSet<ContractPaymentBillingLocationDO>();
			}
			 rateContractDO.setConPayBillLocDO(blocDOSet);
			 if(!CGCollectionUtils.isEmpty(spDOSet)){
				 spocDOSet = new HashSet<RateContractSpocDO>(spDOSet.size());

				 for(RateContractSpocDO spDO: spDOSet){
					 spDO.setContractSpocId(null);
					 spDO.setRateContractDO(rateContractDO);
					 spocDOSet.add(spDO);
				 }

			 }else{
				 spocDOSet = new HashSet<RateContractSpocDO>();
			 }
			 rateContractDO.setRateContractSpocDO(spocDOSet);
			 tx = session.beginTransaction();

			 if(!StringUtil.isNull(rateContractDO)){
				 rateContractDO = (RateContractDO)session.merge(rateContractDO);
			 }
			tx.commit();
			session.flush();
		}catch (Exception e) {
			tx.rollback();
			LOGGER.error("Error occured in RateContractDAOImpl :: createRenewContract()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} 
		finally{
			session.close();
		}
		LOGGER.trace("RateContractDAOImpl::createRenewContract()::END");

		return rateContractDO;
	}*/

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkContractIsReNew(Integer rateContractId) throws CGSystemException {
		LOGGER.trace("RateContractDAOImpl::checkContractIsReNew()::START");
		try{
			List<RateContractDO> rateContractDOList = null;
			String params[] = { RateContractConstants.RATE_CONTRACT_ID };
			Object values[] = { rateContractId };
			rateContractDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					RateContractConstants.QRY_GET_CONTRACT_BY_ORIGINATED_CONTRACT, params,
					values);
			if(!CGCollectionUtils.isEmpty(rateContractDOList) && rateContractDOList.size()>0){
				return Boolean.TRUE;
			}

		}catch(Exception e) {

			LOGGER.error("Error occured in RateContractDAOImpl :: checkContractIsNew()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} 
		LOGGER.trace("RateContractDAOImpl::checkContractIsReNew()::END");
		return Boolean.FALSE;
	}


	@Override
	public List<RateQuotationWrapperDO> getContractListForEmailForArea(
			Date noOfDay, String expDays, Date todayDate) throws CGSystemException {
		LOGGER.trace("RateContractDAOImpl::getContractListForEmailForArea()::START");
		List<RateQuotationWrapperDO> quotationWrapperDOs=null;
		Session session = null;
		try {


			/*String sql = "SELECT "
					+ " con.RATE_CONTRACT_ID,con.RATE_CONTRACT_NUMBER,con.VALID_TO_DATE,con.VALID_FROM_DATE," 
					+ "touser.EMAIL_ID,ccuser.EMAIL_ID,cus.BUSINESS_NAME,cus.CUSTOMER_CODE,touser.FIRST_NAME,touser.LAST_NAME "
					+ "FROM ff_d_rate_contract con,ff_d_customer cus,"
					+ "(SELECT emp.EMPLOYEE_ID as EMPLOYEE_ID,emp.EMAIL_ID as EMAIL_ID,"
					+ "offc.OFFICE_ID as OFFICE_ID,"
					+ "emp.OFFICE as OFFICE "
					+ "FROM ff_d_employee emp,"
					+ " ff_d_employee_user empusr,"
					+ "ff_d_app_scrn scrn,"
					+ "ff_d_app_rights app,"
					+ "ff_d_user_rights rght,"
					+ "ff_d_user_office_rights offc "
					+ "WHERE scrn.SCREEN_CODE =? "
					+ "AND offc.USER_ID = empusr.USER_ID "
					+ "AND scrn.SCREEN_ID = app.SCREEN_ID "
					+ "AND app.ROLE_ID = rght.ROLE_ID "
					+ "AND rght.USER_ID = empusr.USER_ID "
					+ "AND empusr.EMPLOYEE_ID = emp.EMPLOYEE_ID and offc.MAPPED_TO = 'A') ccuser,"
					+ "(SELECT  emp.EMPLOYEE_ID as EMPLOYEE_ID,emp.FIRST_NAME as FIRST_NAME,emp.LAST_NAME as LAST_NAME,"
					+ "emp.EMAIL_ID as EMAIL_ID"
					+ " FROM ff_d_employee emp) touser "
					+"WHERE con.CUSTOMER = cus.CUSTOMER_ID "
					+ "AND touser.EMPLOYEE_ID=cus.SALES_PERSON "
					+ "AND con.CUSTOMER = cus.CUSTOMER_ID "
					+"AND con.VALID_FROM_DATE < ? "
					+ "AND con.VALID_TO_DATE=? "
					+ "AND (cus.SALES_OFFICE= ccuser.OFFICE "
					+ "OR cus.SALES_OFFICE IN (SELECT rhoofc.OFFICE_ID "
					+ "FROM ff_d_office rhoofc where REPORTING_RHO in (ccuser.OFFICE_ID)) "
					+ "OR cus.SALES_OFFICE in (SELECT regofc.OFFICE_ID "
					+ "FROM ff_d_office regofc, ff_d_office ofcmap "
					+ "WHERE ofcmap.REPORTING_RHO = regofc.OFFICE_ID "
					+ "AND (regofc.OFFICE_ID = ccuser.OFFICE "
					+ "OR regofc.OFFICE_ID = ccuser.OFFICE_ID) "
					+ "AND regofc.OFFICE_TYPE_ID = (select OFFICE_TYPE_ID  from ff_d_office_type where OFFICE_TYPE_CODE = 'RO'))) "
					+ "ORDER BY VALID_FROM_DATE DESC";*/
			String sql = RateContractConstants.QRY_CONTRACT_LIST_FOR_EMAIL_FOR_AREA;	
			session = getHibernateTemplate().getSessionFactory()
					.openSession();
			Query query = session.createSQLQuery(sql);
			query.setString(0, expDays);
			query.setDate(1, todayDate);
			query.setDate(2, noOfDay);



			List componentsValue = query.list();
			quotationWrapperDOs = prepareQuotationWrapper(componentsValue);

		} catch (Exception e) {
			LOGGER.error("ERROR : RateContractDAOImpl.getContractListForEmailForArea",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("RateContractDAOImpl :: getContractListForEmailForArea() :: End --------> ::::::");
		return quotationWrapperDOs;
	}

	private List<RateQuotationWrapperDO> prepareQuotationWrapper(
			List components) {
		LOGGER.trace("RateContractDAOImpl :: prepareQuotationWrapper() :: START --------> ::::::");
		List<RateQuotationWrapperDO> wrapperDOs = null;
		if (components != null && !components.isEmpty()) {
			wrapperDOs = new ArrayList<RateQuotationWrapperDO>(components.size());
			int componentsSize = components.size();
			for (int i = 0; i < componentsSize; i++) {
				Object[] row = (Object[]) components.get(i);
				RateQuotationWrapperDO wrapperDO = new RateQuotationWrapperDO();
				if (row[0] != null){
					wrapperDO.setRateContractId(row[0]
							.toString());
				}
				if (row[1] != null){
					wrapperDO.setContractNo(row[1].toString());
				}
				if (row[2] != null){
					wrapperDO.setValidToDate(row[2].toString());
				}
				if (row[3] != null){
					wrapperDO.setValidFromDate(row[3].toString());
				}
				if (row[4] != null){
					wrapperDO.setEmailIdTO(row[4].toString());
				}
				if (row[5] != null){
					wrapperDO.setEmailIdCC(row[5].toString());
				}
				if (row[6] != null){
					wrapperDO.setBusinessName(row[6].toString());
				}
				if (row[7] != null){
					wrapperDO.setCustomerCode(row[7].toString());
				}
				if (row[8] != null){
					wrapperDO.setFirstName(row[8].toString());
				}
				if (row[9] != null){
					wrapperDO.setLastName(row[9].toString());
				}

				wrapperDOs.add(wrapperDO);
			}
		}
		LOGGER.trace("RateContractDAOImpl :: prepareQuotationWrapper() :: END --------> ::::::");
		return wrapperDOs;
	}

	@Override
	public List<RateQuotationWrapperDO> getContractListForEmailForRHO(
			Date noOfDay, String expDays, Date todayDate) throws CGSystemException {
		LOGGER.trace("RateContractDAOImpl :: getContractListForEmailForRHO() :: START --------> ::::::");
		List<RateQuotationWrapperDO> quotationWrapperDOs=null;
		Session session = null;
		try {


			/*String sql = "SELECT "
					+ " con.RATE_CONTRACT_ID,con.RATE_CONTRACT_NUMBER,con.VALID_TO_DATE,con.VALID_FROM_DATE," 
					+ "touser.EMAIL_ID,ccuser.EMAIL_ID,cus.BUSINESS_NAME,cus.CUSTOMER_CODE,touser.FIRST_NAME,touser.LAST_NAME "
					+ "FROM ff_d_rate_contract con,ff_d_customer cus,"
					+ "(SELECT emp.EMPLOYEE_ID as EMPLOYEE_ID,emp.EMAIL_ID as EMAIL_ID,"
					+ "offc.OFFICE_ID as OFFICE_ID,"
					+ "emp.OFFICE as OFFICE "
					+ "FROM ff_d_employee emp,"
					+ " ff_d_employee_user empusr,"
					+ "ff_d_app_scrn scrn,"
					+ "ff_d_app_rights app,"
					+ "ff_d_user_rights rght,"
					+ "ff_d_user_office_rights offc "
					+ "WHERE scrn.SCREEN_CODE =? "
					+ "AND offc.USER_ID = empusr.USER_ID "
					+ "AND scrn.SCREEN_ID = app.SCREEN_ID "
					+ "AND app.ROLE_ID = rght.ROLE_ID "
					+ "AND rght.USER_ID = empusr.USER_ID "
					+ "AND empusr.EMPLOYEE_ID = emp.EMPLOYEE_ID and offc.MAPPED_TO = 'R') ccuser,"
					+ "(SELECT  emp.EMPLOYEE_ID as EMPLOYEE_ID,emp.FIRST_NAME as FIRST_NAME,emp.LAST_NAME as LAST_NAME,"
					+ "emp.EMAIL_ID as EMAIL_ID"
					+ " FROM ff_d_employee emp) touser "
					+"WHERE con.CUSTOMER = cus.CUSTOMER_ID "
					+ "AND touser.EMPLOYEE_ID=cus.SALES_PERSON "
					+ "AND con.CUSTOMER = cus.CUSTOMER_ID "
					+"AND con.VALID_FROM_DATE < ? "
					+ "AND con.VALID_TO_DATE=? "
					+ "AND (cus.SALES_OFFICE= ccuser.OFFICE "
					+ "OR cus.SALES_OFFICE IN (SELECT rhoofc.OFFICE_ID "
					+ "FROM ff_d_office rhoofc where REPORTING_RHO in (ccuser.OFFICE_ID)) "
					+ "OR cus.SALES_OFFICE in (SELECT regofc.OFFICE_ID "
					+ "FROM ff_d_office regofc, ff_d_office ofcmap "
					+ "WHERE ofcmap.REPORTING_RHO = regofc.OFFICE_ID "
					+ "AND (regofc.OFFICE_ID = ccuser.OFFICE "
					+ "OR regofc.OFFICE_ID = ccuser.OFFICE_ID) "
					+ "AND regofc.OFFICE_TYPE_ID = (select OFFICE_TYPE_ID  from ff_d_office_type where OFFICE_TYPE_CODE = 'RO'))) "
					+ "ORDER BY VALID_FROM_DATE DESC";*/
			String sql = RateContractConstants.QRY_CONTRACT_LIST_FOR_EMAIL_FOR_RHO;
			session = getHibernateTemplate().getSessionFactory()
					.openSession();
			Query query = session.createSQLQuery(sql);
			query.setString(0, expDays);
			query.setDate(1, todayDate);
			query.setDate(2, noOfDay);



			List componentsValue = query.list();
			quotationWrapperDOs = prepareQuotationWrapper(componentsValue);

		} catch (Exception e) {
			LOGGER.error("ERROR : RateContractDAOImpl.getContractListForEmailForRHO",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("RateContractDAOImpl :: getContractListForEmailForRHO() :: END --------> ::::::");
		return quotationWrapperDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateQuotationWrapperDO> rateContractListViewSEDetails(
			Integer userId, String fromDate, String toDate, String contractNo,
			String status) throws CGSystemException {
		LOGGER.trace("RateContractDAOImpl :: rateContractListViewSEDetails() :: START --------> ::::::");
		Session session = null;

		List<RateQuotationWrapperDO> rqwDOList = null;
		try{
			session = createSession();

			if(StringUtil.isStringEmpty(contractNo)){

				String query = RateContractConstants.QRY_GET_LIST_VIEW_RATE_CONTRACT_SE;

				String params[] = {RateContractConstants.PARAM_USER_ID, RateContractConstants.PARAM_FROM_DATE, RateContractConstants.PARAM_TO_DATE, RateContractConstants.PARAM_STATUS};

				Object values[] = {userId,DateUtil.stringToDDMMYYYYFormat(fromDate),DateUtil.stringToDDMMYYYYFormat(toDate),status};

				rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);

			}else{
				String query = RateContractConstants.QRY_GET_RATE_CONTRACT_SE;

				String params[] = {RateContractConstants.PARAM_USER_ID, RateContractConstants.PARAM_CONTRACT_NO};

				Object values[] = {userId,contractNo};

				rqwDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
			}


		}catch(Exception e){
			LOGGER.error("Error occured in RateContractDAOImpl :: rateContractListViewSEDetails()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		finally {
			session.close();
		}
		LOGGER.trace("RateContractDAOImpl :: rateContractListViewSEDetails() :: END --------> ::::::");
		return rqwDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateContractSpocDO> getRateContractSpocDetails(
			String contactType, Integer contractId) throws CGSystemException {
		LOGGER.trace("RateContractDAOImpl :: getRateContractSpocDetails() :: START --------> ::::::");
		List<RateContractSpocDO> rcsDOList = new ArrayList<RateContractSpocDO>();
		try{
			String query = RateContractConstants.QRY_GET_CONTRACT_SPOC_DETAILS;

			String params[] = {"contactType", "contractId"};

			Object values[] = {contactType,contractId};

			rcsDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
		}catch(Exception e){
			LOGGER.error("Error occured in RateContractDAOImpl :: getRateContractSpocDetails()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("RateContractDAOImpl :: getRateContractSpocDetails() :: END --------> ::::::");
		return rcsDOList;
	}

	@Override
	public Boolean saveOrUpdateRateContractSpocDetails(
			List<RateContractSpocDO> rcsDOList) throws CGSystemException {
		LOGGER.debug("RateContractDAOImpl::saveOrUpdateRateContractSpocDetails()::START");
		Boolean operation = Boolean.FALSE;
		try{
			if(!CGCollectionUtils.isEmpty(rcsDOList)){
				getHibernateTemplate().saveOrUpdateAll(rcsDOList);
				operation = Boolean.TRUE;
			}

		}catch(Exception e){
			LOGGER.error("Error Occured :: RateContractDAOImpl::saveOrUpdateRateContractSpocDetails()");
			throw new CGSystemException(e);			
		}
		LOGGER.debug("RateContractDAOImpl::saveOrUpdateRateContractSpocDetails()::END");
		return operation;
	}

	@Override
	public void updateCustomerCode(String soldToCode, Integer customerId) throws CGSystemException {
		LOGGER.trace("RateContractDAOImpl::updateCustomerCode()::START");
		Session session = null;
		try{
			session = createSession();
			Query query = session.getNamedQuery(
					RateContractConstants.QRY_UPDATE_CUSTOMER_CODE);
			query.setParameter("customerCode", soldToCode);
			query.setParameter("customerId", customerId);
			query.executeUpdate();

		}catch(Exception e){
			LOGGER.error("Error Occured :: RateContractDAOImpl::updateCustomerCode()");	
			throw new CGSystemException(e);		
		}finally{
			session.close();
		}
		LOGGER.trace("RateContractDAOImpl::updateCustomerCode()::END");

	}

	@Override
	public boolean updateCustomerBillDtls(RateContractTO rateContractTO,
			Integer custId, Integer custTypeId) throws CGSystemException {
		LOGGER.debug("RateContractDAOImpl::updateCustomerBillDtls()::START");
		boolean result = Boolean.FALSE;
		Session session = null;
		Transaction tx = null;
		try{
			session = createSession();
			tx = session.beginTransaction();
			Query query = session.getNamedQuery(RateContractConstants
					.QRY_UPDATE_CUSTOMER_BILL_DTLS);
			query.setParameter(RateCommonConstants.PARAM_PAN_NO,
					rateContractTO.getPanNo());
			query.setParameter(RateCommonConstants.PARAM_TAN_NO,
					rateContractTO.getTanNo());
			query.setParameter(RateContractConstants.BILLING_CYCLE, 
					rateContractTO.getBillingCycle());
			query.setParameter(RateContractConstants.PAYMENT_TERM, 
					rateContractTO.getPaymentTerm());
			if(!StringUtil.isStringEmpty(rateContractTO.getSoldToCode())){
				query.setParameter(RateContractConstants.CUSTOMER_CODE, 
						rateContractTO.getSoldToCode());
			}else{
				query.setParameter(RateContractConstants.CUSTOMER_CODE, 
						null);
			}
			query.setParameter(RateContractConstants.CUSTOMER_TYPE_ID, 
					custTypeId);
			query.setParameter(RateCommonConstants.PARAM_CUSTOMER_ID, 
					custId);
			if(rateContractTO.getTypeOfBilling().equals(RateContractConstants.BILL_TYPE_DBCP)){
				query.setParameter(RateContractConstants.SAP_STATUS, CommonConstants.YES);
			}else{
				query.setParameter(RateContractConstants.SAP_STATUS, CommonConstants.NO);
			}
			int i = query.executeUpdate();
			if(i>0){
				result = Boolean.TRUE;
			}
			tx.commit();
		} catch(Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			LOGGER.error("Exception occures in RateContractDAOImpl::updateCustomerBillDtls()::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.debug("RateContractDAOImpl::updateCustomerBillDtls()::END");
		return result;
	}

	@Override
	public RateContractDO saveOrUpdateContract(RateContractDO rateContractDO)
			throws CGSystemException {
		Session session = null;
		Transaction tx = null;
		try {
			LOGGER.trace("RateContractDAOImpl :: saveOrUpdateBasicInfo() :: START --------> ::::::");
			session = createSession();
			tx = session.beginTransaction();
			rateContractDO = (RateContractDO) session.merge(rateContractDO);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			LOGGER.trace("Error occured in RateContractDAOImpl :: saveOrUpdateContract()..:"
					, e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.trace("RateContractDAOImpl :: saveOrUpdateBasicInfo() :: END --------> ::::::");
		return rateContractDO;
	}

	@Override
	public RateContractDO searchContractDetailsForSuperUser(
			RateContractTO contractTO) throws CGSystemException {
		LOGGER.trace("RateContractDAOImpl :: searchContractDetailsForSuperUser() :: START --------> ::::::");
		RateContractDO rateContractDO = null;
		Session session=null;
		try {
			session=createSession();
			Criteria criteria=session.createCriteria(RateContractDO.class);
			criteria.add(Restrictions.eq("rateContractNo",contractTO.getRateContractNo()));
			criteria.add(Restrictions.eq("contractStatus", "A"));
			criteria.add(Restrictions.eq("rateContractType",contractTO.getRateContractType()));
			criteria.add(Restrictions.isNull("isRenewed"));
			rateContractDO = (RateContractDO) criteria.uniqueResult();

		} catch (Exception e) {
			LOGGER.error("Exception occurs in RateContractDAOImpl::searchContractDetailsForSuperUser()::" + e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("RateContractDAOImpl :: searchContractDetailsForSuperUser() :: END --------> ::::::");
		return rateContractDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeDO> getEmployeesforContractAlert(OfficeDO ofcDO)
			throws CGSystemException {
		LOGGER.trace("RateContractDAOImpl :: getEmployeesforContractAlert() :: START --------> ::::::");
		List<EmployeeDO> employeeDOList = null;
		Session session = null;
		try{
			session = createSession();
			Query qry = session.getNamedQuery(RateContractConstants.QRY_GET_EMPLOYEES_FOR_CONTRACT_ALERT);
			qry.setParameterList(RateContractConstants.OFFICE_ID_LIST, new Integer[]{ofcDO.getOfficeId(),ofcDO.getReportingHUB(),ofcDO.getReportingRHO()});
			qry.setString(RateContractConstants.SCRN_CODE, RateContractConstants.CONTRACT_ALERT_SCREEN_CODE);
			if(!StringUtil.isEmptyInteger(ofcDO.getReportingRHO())){
				qry.setInteger(RateContractConstants.RHO_OFFICE_ID, ofcDO.getReportingRHO());
			}else{
				qry.setParameter(RateContractConstants.RHO_OFFICE_ID, null);
			}
			qry.setInteger(RateContractConstants.OFFICE_ID, ofcDO.getOfficeId());
			qry.setString(RateContractConstants.CORP_OFC_TYPE, CommonConstants.OFF_TYPE_CORP_OFFICE);
			employeeDOList = qry.list();
		}catch(Exception e){
			LOGGER.error("Error occured in RateContractDAOImpl :: getEmployeesforContractAlert()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}finally{
			session.close();
		}
		LOGGER.trace("RateContractDAOImpl :: getEmployeesforContractAlert() :: END --------> ::::::");
		return employeeDOList;
	}

	@Override
	public void clearPickupOrDeliveryLocations(RateContractDO rateContractDo) throws CGSystemException {
		LOGGER.debug("RateContractDAOImpl::clearPickupOrDeliveryLocations()::START");
		Session session=null;
		try {
			//getHibernateTemplate().saveOrUpdate(rateContractDO);
			/* To update respective fields for rate contract billing details */
			session = createSession();
			Query query = session.getNamedQuery(RateContractConstants.QRY_CLEAR_PICKUP_DLV_LOCATIONS);
			query.setParameter("rateContractId", rateContractDo.getRateContractId());
			int i = query.executeUpdate();

			if(i<1){
				throw new Exception(RateContractConstants.ERR_DTLS_NOT_UPDATED);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurs in RateContractDAOImpl::clearPickupOrDeliveryLocations()::"	+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("RateContractDAOImpl::clearPickupOrDeliveryLocations()::END");
	}
}
