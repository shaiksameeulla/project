/**
 * 
 */
package com.dtdc.bodbadmin.dao;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.capgemini.lbs.framework.constants.BookingConstants;
import com.capgemini.lbs.framework.constants.BusinessConstants;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseDO.CGBaseEntity;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.bodbadmin.utility.CentralDataExtractorConstant;
import com.dtdc.domain.booking.BookingDO;
import com.dtdc.domain.booking.cashbooking.CashBookingDO;
import com.dtdc.domain.booking.dpbooking.DirectPartyBookingDO;
import com.dtdc.domain.booking.frbooking.FranchiseeBookingDO;
import com.dtdc.domain.booking.specialcustomer.SplCustomerBookingDO;
import com.dtdc.domain.dataextraction.DataExtractionDO;
import com.dtdc.domain.dispatch.DispatchDO;
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.RtnToOrgDO;
import com.dtdc.domain.master.delivery.FDMBODBConfigDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.purchase.GoodsIssueDO;
import com.dtdc.domain.purchase.GoodsRenewalDO;
import com.dtdc.domain.purchase.GoodscCancellationDO;
import com.dtdc.domain.transaction.delivery.DeliveryDO;
import com.dtdc.domain.transaction.heldup.HeldUpReleaseDO;

// TODO: Auto-generated Javadoc
/**
 * The Class CentralDataExtractorDAOImpl.
 *
 * @author nisahoo Date:
 */
public class CentralDataExtractorDAOImpl extends HibernateDaoSupport implements
CentralDataExtractorDAO {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger
	.getLogger(CentralDataExtractorDAOImpl.class);

	private static final int GET_MAX_ROW_COUNT =50;
	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getAllOfficeCodes()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllOfficeCodes() {
		LOGGER.debug("CentralDataExtractorDAOImpl : getAllOfficeCodes(): START");
		List<String> offCodeList = null;
		// List<String> offCodeList =
		try{
			offCodeList = getHibernateTemplate().findByNamedQuery(
			"getOfficesCentral");	
			
		}catch (Exception e) {
		}
		
		LOGGER.debug("CentralDataExtractorDAOImpl : getAllOfficeCodes(): END");

		return offCodeList;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getOutgoingManifestsForBranch(String, Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ManifestDO> getOutgoingManifestsForBranch(String branchCode,
			Integer maxRecord) throws CGSystemException {
		LOGGER.debug("CentralDataExtractorDAOImpl : getOutgoingManifestsForBranch(): START");
		Integer offId = 0;
		String namedQuery = "getOfficeIdByOfficeCode";
		String params = "officeCode";
		Object values = branchCode;
		List<ManifestDO> manifestList = null;
		Session session = null;
		try {
			List<Integer> offIdList = getHibernateTemplate()
			.findByNamedQueryAndNamedParam(namedQuery, params, values);
			if (offIdList != null && !offIdList.isEmpty()) {
				offId = offIdList.get(0);
			}
			if (offId > 0) {
				String query = CentralDataExtractorConstant.GET_OUTGOING_MANIFESTS_FOR_BRANCH_QUERY_BO_DB_ADMIN;
				session = getHibernateTemplate().getSessionFactory()
				.openSession();
				Query qury = session.getNamedQuery(query);
				qury.setInteger(
						CentralDataExtractorConstant.GET_OUTGOING_MANIFESTS_FOR_BRANCH_QUERY_PARAM1,
						offId);
				qury.setMaxResults(maxRecord);
				manifestList = qury.list();
			}

		} catch (Exception e) {
			logger.error("CentralDataExtractorDAOImpl::getOutgoingManifestsForBranch::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		return manifestList;

	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getBookingDetailsByCn(List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BookingDO> getBookingDetailsByCn(List<String> cnList) {
		LOGGER.debug("CentralDataExtractorDAOImpl : getBookingDetailsByCn(): START");

		List<BookingDO> bookingDOList = getHibernateTemplate()
		.findByNamedQueryAndNamedParam(
				CentralDataExtractorConstant.GET_BOOKING_DETAILS_BY_CONSIGNMENTS_QUERY,
				CentralDataExtractorConstant.GET_BOOKING_DETAILS_BY_CONSIGNMENTS_QUERY_PARAM,
				cnList);

		LOGGER.debug("CentralDataExtractorDAOImpl : getBookingDetailsByCn(): END");
		return bookingDOList;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getBookingTypeDetails(String, String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CGBaseEntity getBookingTypeDetails(String consignmentNumber,
			String bookingType) {
		LOGGER.debug("CentralDataExtractorDAOImpl : getBookingTypeDetails(): START");

		FranchiseeBookingDO frBookingDO = null;
		CashBookingDO cashBookingDO = null;
		SplCustomerBookingDO spclCustomerBookingDO = null;
		DirectPartyBookingDO dpBookingDO = null;
		CGBaseEntity baseEntity = null;

		if (bookingType
				.equals(CentralDataExtractorConstant.BOOKING_TYPE_FRANCHISEE_BOOKING)) {
			LOGGER.debug("FRBOOKING CN = [ " + consignmentNumber + " ]");
			List<FranchiseeBookingDO> frBookingDOList = getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					CentralDataExtractorConstant.GET_FRBOOKING_DETAILS_BY_CN_QUERY,
					CentralDataExtractorConstant.GET_FRBOOKING_DETAILS_BY_CN_QUERY_PARAM,
					consignmentNumber);

			if (frBookingDOList != null && frBookingDOList.size() > 0) {
				frBookingDO = frBookingDOList.get(0);
			}
			baseEntity = frBookingDO;

		} else if (bookingType
				.equals(CentralDataExtractorConstant.BOOKING_TYPE_CASH_BOOKING)) {
			LOGGER.debug("CASHBOOKING CN = [ " + consignmentNumber + " ]");
			List<CashBookingDO> cashBookingDOList = getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					CentralDataExtractorConstant.GET_CASHBOOKING_DETAILS_BY_CN_QUERY,
					CentralDataExtractorConstant.GET_CASHBOOKING_DETAILS_BY_CN_QUERY_PARAM,
					consignmentNumber);

			if (cashBookingDOList != null && cashBookingDOList.size() > 0) {
				cashBookingDO = cashBookingDOList.get(0);
			}
			baseEntity = cashBookingDO;

		} else if (bookingType
				.equals(CentralDataExtractorConstant.BOOKING_TYPE_SPECIAL_CUSTOMER_BOOKING)) {
			LOGGER.debug("SPCLCUSTOMERBOOKING CN = [ " + consignmentNumber
					+ " ]");
			List<SplCustomerBookingDO> spclCustomerBookingDOList = getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					CentralDataExtractorConstant.GET_SPCLCUSTBOOKING_DETAILS_BY_CN_QUERY,
					CentralDataExtractorConstant.GET_SPCLCUSTBOOKING_DETAILS_BY_CN_QUERY_PARAM,
					consignmentNumber);

			if (spclCustomerBookingDOList != null
					&& spclCustomerBookingDOList.size() > 0) {
				spclCustomerBookingDO = spclCustomerBookingDOList.get(0);
			}
			baseEntity = spclCustomerBookingDO;

		} else if (bookingType
				.equals(CentralDataExtractorConstant.BOOKING_TYPE_DIRECT_PARTY_BOOKING)) {
			LOGGER.debug("DPBOOKING CN = [ " + consignmentNumber + " ]");
			List<DirectPartyBookingDO> dpBookingDOList = getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					CentralDataExtractorConstant.GET_DPBOOKING_DETAILS_BY_CN_QUERY,
					CentralDataExtractorConstant.GET_DPBOOKING_DETAILS_BY_CN_QUERY_PARAM,
					consignmentNumber);

			if (dpBookingDOList != null && dpBookingDOList.size() > 0) {
				dpBookingDO = dpBookingDOList.get(0);
			}
			baseEntity = dpBookingDO;
		}

		LOGGER.debug("CentralDataExtractorDAOImpl : getBookingTypeDetails(): END");
		return baseEntity;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#updateBookingWithReadByLocal(List)
	 */
	@Override
	public void updateBookingWithReadByLocal(List<BookingDO> bookingDOList) {
		LOGGER.debug("CentralDataExtractorDAOImpl : updateBookingWithReadByLocal(): START");
		if (bookingDOList != null && bookingDOList.size() > 0) {
			for (BookingDO bookingDO : bookingDOList) {
				/** Set the READ_BY_LOCAL status */
				bookingDO
				.setReadByLocal(CentralDataExtractorConstant.READ_BY_LOCAL_SUCCESS);
			}

			getHibernateTemplate().saveOrUpdateAll(bookingDOList);
		}
		LOGGER.debug("CentralDataExtractorDAOImpl : updateBookingWithReadByLocal(): END");
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#updateManifestsWithReadByLocal(List)
	 */
	@Override
	public void updateManifestsWithReadByLocal(List<ManifestDO> manifestDOList) {
		LOGGER.debug("CentralDataExtractorDAOImpl : updateManifestsWithReadByLocal(): START");

		if (manifestDOList != null && manifestDOList.size() > 0) {
			for (ManifestDO manifestDO : manifestDOList) {
				/** Set the READ_BY_LOCAL status */
				manifestDO
				.setReadByLocal(CentralDataExtractorConstant.READ_BY_LOCAL_SUCCESS);
			}

			getHibernateTemplate().saveOrUpdateAll(manifestDOList);
		}
		LOGGER.debug("CentralDataExtractorDAOImpl : updateManifestsWithReadByLocal(): END");
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#updateGoodsRenewalWithReadByLocal(List)
	 */
	@Override
	public void updateGoodsRenewalWithReadByLocal(
			List<GoodsRenewalDO> goodsRenewalDOList) {
		LOGGER.debug("CentralDataExtractorDAOImpl : updateGoodsRenewalWithReadByLocal(): START");

		if (goodsRenewalDOList != null && goodsRenewalDOList.size() > 0) {
			for (GoodsRenewalDO goodsRenewalDO : goodsRenewalDOList) {
				/** Set the READ_BY_LOCAL status */
				goodsRenewalDO
				.setReadByLocal(CentralDataExtractorConstant.READ_BY_LOCAL_SUCCESS);
			}

			getHibernateTemplate().saveOrUpdateAll(goodsRenewalDOList);
		}
		LOGGER.debug("CentralDataExtractorDAOImpl : updateGoodsRenewalWithReadByLocal(): END");

	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#updateGoodsCanclWithReadByLocal(List)
	 */
	@Override
	public void updateGoodsCanclWithReadByLocal(
			List<GoodscCancellationDO> goodsCanclDOList) {
		LOGGER.debug("CentralDataExtractorDAOImpl : updateGoodsCanclWithReadByLocal(): START");

		if (goodsCanclDOList != null && goodsCanclDOList.size() > 0) {
			for (GoodscCancellationDO goodsCanclDO : goodsCanclDOList) {
				/** Set the READ_BY_LOCAL status */
				goodsCanclDO
				.setReadByLocal(CentralDataExtractorConstant.READ_BY_LOCAL_SUCCESS);
			}

			getHibernateTemplate().saveOrUpdateAll(goodsCanclDOList);
		}
		LOGGER.debug("CentralDataExtractorDAOImpl : updateGoodsCanclWithReadByLocal(): END");
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#updateDispatchDetailsWithReadByLocal(List)
	 */
	@Override
	public void updateDispatchDetailsWithReadByLocal(
			List<DispatchDO> dispatchDOList) {
		LOGGER.debug("CentralDataExtractorDAOImpl : updateDispatchDetailsWithReadByLocal(): START");

		if (dispatchDOList != null && dispatchDOList.size() > 0) {
			for (DispatchDO dispatchDO : dispatchDOList) {
				/** Set the READ_BY_LOCAL status */
				dispatchDO
				.setReadByLocal(CentralDataExtractorConstant.READ_BY_LOCAL_SUCCESS);
			}

			getHibernateTemplate().saveOrUpdateAll(dispatchDOList);
		}
		LOGGER.debug("CentralDataExtractorDAOImpl : updateDispatchDetailsWithReadByLocal(): END");
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#saveExtractedDataForBranch(DataExtractionDO)
	 */
	@Override
	public Boolean saveExtractedDataForBranch(DataExtractionDO extractionDo, String flag) {
		Boolean result = false;
		LOGGER.debug("CentralDataExtractorDAOImpl : saveExtractedDataForBranch(): START");
		List<String> branchList;
		List<String> franchiseeList;
		try {
			if(flag == null){
				branchList = getOfficeDataSyncDetails();
				if(null!=branchList){
					for(String branchCode :branchList){
						extractionDo.setBranchCode(branchCode);
						getHibernateTemplate().save(extractionDo);
					}
				}
			}else{
				franchiseeList = getOfficeDataSyncByFranchisee();
				for(String frCode :franchiseeList){
					extractionDo.setBranchCode(frCode);
					getHibernateTemplate().save(extractionDo);
				}
			}
			
		} catch (CGSystemException e) {
		}
	
		
		

		LOGGER.debug("CentralDataExtractorDAOImpl : saveExtractedDataForBranch(): END");
		result = true;

		return result;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getExtractedDataByBranchCode(String, Integer)
	 */
	@Override
	public List<DataExtractionDO> getExtractedDataByBranchCode(
			String branchCode, Integer maxFetchRecords)
			throws CGSystemException {
		LOGGER.debug("CentralDataExtractorDAOImpl : getExtractedDataByBranchCode(): START");
		LOGGER.debug("CentralDataExtractorDAOImpl : getExtractedDataByBranchCode(): PARAMS branchCode :"
				+ branchCode + "\tmaxFetchRecords :" + maxFetchRecords);
		Session session = null;
		String query = "getExtractedDataByBranchCode";
		/*
		 * String param = "branchCode"; List<DataExtractionDO>
		 * dataExtractionDOList =
		 * getHibernateTemplate().findByNamedQueryAndNamedParam(query, param,
		 */
		List<DataExtractionDO> dataExtractionDOList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qury = session.getNamedQuery(query);
			qury.setString(BusinessConstants.BRANCH_CODE, branchCode.trim());
			qury.setMaxResults(maxFetchRecords);
			dataExtractionDOList = qury.list();
		} catch (Exception e) {
			logger.error("CentralDataExtractorDAOImpl::getExtractedDataByBranchCode::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.close();
		}

		LOGGER.debug("CentralDataExtractorDAOImpl : getExtractedDataByBranchCode(): END");

		return dataExtractionDOList;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getHeldUpDtlsByBranchCode(String, Integer)
	 */
	@Override
	public List<HeldUpReleaseDO> getHeldUpDtlsByBranchCode(String branchCode,
			Integer maxSize) throws CGSystemException {
		// List<HeldUpReleaseDO> dataExtractionDOList =
		// getHibernateTemplate().findByNamedQueryAndNamedParam(query, param,
		String query = "getHeldUpReleaseDtlsByOfficeCode";
		Session session = null;
		List<HeldUpReleaseDO> heldupReleaseList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qury = session.getNamedQuery(query);
			qury.setString(BusinessConstants.BRANCH_CODE, branchCode.trim());
			qury.setMaxResults(maxSize);
			heldupReleaseList = qury.list();
		} catch (Exception e) {
			logger.error("CentralDataExtractorDAOImpl::getHeldUpDtlsByBranchCode::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.close();
		}

		return heldupReleaseList;

	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getGoodsCanclDataByBranchCode(List, Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<GoodscCancellationDO> getGoodsCanclDataByBranchCode(
			List<String> branchList, Integer maxGoodsCanclSize)
			throws CGSystemException {
		LOGGER.debug("CentralDataExtractorDAOImpl : getGoodsCanclDataByBranchCode(): START");
		String queryGoodsCancl = "getGoodsCanclDataByBranchCode";
		Session session = null;
		List<GoodscCancellationDO> dataExtrctGoodsCanclDOList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qury = session.getNamedQuery(queryGoodsCancl);
			qury.setParameterList(BusinessConstants.BRANCH_CODE, branchList);
			qury.setMaxResults(maxGoodsCanclSize);
			dataExtrctGoodsCanclDOList = qury.list();
		} catch (Exception e) {
			logger.error("CentralDataExtractorDAOImpl::getGoodsCanclDataByBranchCode::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.close();
		}

		LOGGER.debug("CentralDataExtractorDAOImpl : getGoodsCanclDataByBranchCode(): END");
		return dataExtrctGoodsCanclDOList;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getGoodsRenewalDataByBranchCode(List, Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<GoodsRenewalDO> getGoodsRenewalDataByBranchCode(
			List<String> branchList, Integer maxGoodsRenewalSize)
			throws CGSystemException {
		LOGGER.debug("CentralDataExtractorDAOImpl : getGoodsRenewalDataByBranchCode(): START");
		String queryGoodsRenewal = "getGoodsRenewalDataByBranchCode";

		Session session = null;
		List<GoodsRenewalDO> dataExtrctGoodsRenewalDOList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qury = session.getNamedQuery(queryGoodsRenewal);
			qury.setParameterList(BusinessConstants.BRANCH_CODE, branchList);
			qury.setMaxResults(maxGoodsRenewalSize);
			dataExtrctGoodsRenewalDOList = qury.list();
		} catch (Exception e) {
			logger.error("CentralDataExtractorDAOImpl::getGoodsRenewalDataByBranchCode::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.debug("CentralDataExtractorDAOImpl : getGoodsRenewalDataByBranchCode(): END");
		return dataExtrctGoodsRenewalDOList;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#updateHeldUpWithReadByLocal(List)
	 */
	@Override
	public Boolean updateHeldUpWithReadByLocal(
			List<HeldUpReleaseDO> heldUpReleaseDOList) {
		Boolean result = false;
		if (heldUpReleaseDOList != null && !heldUpReleaseDOList.isEmpty()) {
			for (HeldUpReleaseDO heldUpReleaseDO : heldUpReleaseDOList) {
				/** Set the READ_BY_LOCAL status */
				heldUpReleaseDO
				.setReadByLocal(CentralDataExtractorConstant.READ_BY_LOCAL_SUCCESS);
			}

			getHibernateTemplate().saveOrUpdateAll(heldUpReleaseDOList);
			result = true;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getDispatchDataByBranchCode(String, Integer)
	 */
	public List<DispatchDO> getDispatchDataByBranchCode(String branchCode,
			Integer maxSize) throws CGSystemException {
		LOGGER.debug("CentralDataExtractorDAOImpl : getDispatchDataByBranchCode(): START");
		String queryDispatchData = "getDispatchDataByBranchCode";
		Session session = null;
		List<DispatchDO> dispatchDataList = null;

		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qury = session.getNamedQuery(queryDispatchData);
			qury.setString(BusinessConstants.BRANCH_CODE, branchCode.trim());
			qury.setMaxResults(maxSize);
			dispatchDataList = qury.list();
		} catch (Exception e) {
			logger.error("CentralDataExtractorDAOImpl::getDispatchDataByBranchCode::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.close();
		}

		LOGGER.debug("CentralDataExtractorDAOImpl : getDispatchDataByBranchCode(): END");
		return dispatchDataList;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getGoodsIssueDataByBranchCode(List, Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsIssueDO> getGoodsIssueDataByBranchCode(
			List branchCodeList, Integer maxSize) throws CGSystemException {
		String query = "getRecvngBranchData";

		Session session = null;
		List<GoodsIssueDO> goodsIssueDoList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qury = session.getNamedQuery(query);
			qury.setParameterList(BusinessConstants.BRANCH_CODE, branchCodeList);
			qury.setMaxResults(maxSize);
			goodsIssueDoList = qury.list();
		} catch (Exception e) {
			logger.error("CentralDataExtractorDAOImpl::getGoodsIssueDataByBranchCode::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.close();
		}

		return goodsIssueDoList;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getBookingDetailsByOfficeCode(String, Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BookingDO> getBookingDetailsByOfficeCode(String officeCode,
			Integer maxSize) throws CGSystemException {
		LOGGER.debug("CentralDataExtractorDAOImpl : getBookingDetailsByOfficeCode(): START");
		String query = CentralDataExtractorConstant.GET_BOOKINGDATA_BY_OFFICE;
		Session session = null;
		List<BookingDO> bookingDOList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();

			Query qury = session.getNamedQuery(query);
			qury.setString(BusinessConstants.BRANCH_CODE, officeCode.trim());
			qury.setMaxResults(maxSize);
			bookingDOList = qury.list();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		LOGGER.debug("CentralDataExtractorDAOImpl : getBookingDetailsByCn(): Total count :"
				+ ((bookingDOList != null && !bookingDOList.isEmpty()) ? bookingDOList
						.size() : "0"));

		LOGGER.debug("CentralDataExtractorDAOImpl : getBookingDetailsByCn(): END");
		return bookingDOList;

	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#updateGoodsIssueWithReadByLocal(List)
	 */
	@Override
	public Boolean updateGoodsIssueWithReadByLocal(
			List<GoodsIssueDO> goodsIssueDOList) {
		Boolean result = false;
		if (goodsIssueDOList != null && !goodsIssueDOList.isEmpty()) {
			for (GoodsIssueDO goodsIssueDO : goodsIssueDOList) {
				/** Set the READ_BY_LOCAL status */
				goodsIssueDO
				.setReadByLocal(CentralDataExtractorConstant.READ_BY_LOCAL_SUCCESS);
			}

			getHibernateTemplate().saveOrUpdateAll(goodsIssueDOList);
			result = true;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#updateDataExtractionWithReadDataStatus(List)
	 */
	@Override
	public Boolean updateDataExtractionWithReadDataStatus(
			List<DataExtractionDO> dataExtractionDoList) {
		LOGGER.debug("CentralDataExtractorDAOImpl : updateDataExtractionWithDataStatus(): START");
		Boolean result = false;
		if (dataExtractionDoList != null && !dataExtractionDoList.isEmpty()) {
			LOGGER.debug("CentralDataExtractorDAOImpl : updateDataExtractionWithDataStatus(): dataExtractionDoList SIZE "
					+ dataExtractionDoList.size());
			for (DataExtractionDO dataExtractionDo : dataExtractionDoList) {
				/** Set the DATA_READ status */
				dataExtractionDo
				.setDataStatus(CentralDataExtractorConstant.EXTRACTED_DATA_READ_STATUS);
				dataExtractionDo.setTransactionLastMdfDate(Calendar
						.getInstance().getTime());
			}
			LOGGER.debug("CentralDataExtractorDAOImpl : updateDataExtractionWithDataStatus(): dataExtractionDoList updating data extraction do list with read status");
			getHibernateTemplate().saveOrUpdateAll(dataExtractionDoList);
			result = true;
			LOGGER.debug("CentralDataExtractorDAOImpl : updateDataExtractionWithDataStatus(): dataExtractionDoList updated status :"
					+ result);
		}

		LOGGER.debug("CentralDataExtractorDAOImpl : updateDataExtractionWithDataStatus(): END");
		return result;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getRtoDataForBranchCode(String, Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RtnToOrgDO> getRtoDataForBranchCode(String branchCode,
			Integer maxSize) throws CGSystemException {
		String query = "getReturnTOOrgDtlsByOfficeCode";

		Session session = null;
		List<RtnToOrgDO> rtnOrgDOList = null;

		try {

			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qury = session.getNamedQuery(query);
			qury.setString(BusinessConstants.BRANCH_CODE, branchCode.trim());
			qury.setMaxResults(maxSize);
			rtnOrgDOList = qury.list();

		} catch (Exception e) {
			logger.error("CentralDataExtractorDAOImpl::getRtoDataForBranchCode::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {

			session.close();
		}

		return rtnOrgDOList;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getNonDoxPaperConsignment(String)
	 */
	@Override
	public BookingDO getNonDoxPaperConsignment(String parentCN)
	throws CGSystemException {
		LOGGER.debug("CentralDataExtractorDAOImpl : getNonDoxPaperConsignment(): START");
		List<BookingDO> result = null;
		BookingDO bookingDO = null;
		String queryForChildConsignments = "getNonDoxPaperWorkCN";
		result = getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryForChildConsignments, BookingConstants.CONSIGNMENT_NUMBER,
				parentCN);
		bookingDO = (result != null && !result.isEmpty()) ? result.get(0)
				: null;
		LOGGER.debug("CentralDataExtractorDAOImpl : getNonDoxPaperConsignment(): END");
		return bookingDO;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getExtractedDataByBranchCode(String)
	 */
	@Override
	public List<DataExtractionDO> getExtractedDataByBranchCode(String branchCode) {
		LOGGER.debug("CentralDataExtractorDAOImpl : getExtractedDataByBranchCode(): START");

		String query = "getExtractedDataByBranchCode";
		String param = "branchCode";

		List<DataExtractionDO> dataExtractionDOList = getHibernateTemplate()
		.findByNamedQueryAndNamedParam(query, param, branchCode);

		LOGGER.debug("CentralDataExtractorDAOImpl : getExtractedDataByBranchCode(): END");
		return dataExtractionDOList;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#updateBookingRecordsReadByLocal(List)
	 */
	@Override
	public Boolean updateBookingRecordsReadByLocal(List<BookingDO> bookingDOList) {
		Boolean result = false;
		LOGGER.debug("CentralDataExtractorDAOImpl : updateBookingRecordsReadByLocal(): Booking--START-> With Size :["
				+ bookingDOList != null
				&& !bookingDOList.isEmpty() ? bookingDOList.size() : 0 + "]");
		if (bookingDOList != null && !bookingDOList.isEmpty()) {
			for (BookingDO bookingDO : bookingDOList) {
				/** Set the READ_BY_LOCAL status */
				bookingDO
				.setReadByLocal(CentralDataExtractorConstant.READ_BY_LOCAL_SUCCESS);
			}

			getHibernateTemplate().saveOrUpdateAll(bookingDOList);
			LOGGER.debug("CentralDataExtractorDAOImpl : updateBookingRecordsReadByLocal(): Booking--updated read by local field->");
			result = true;
		}
		LOGGER.debug("CentralDataExtractorDAOImpl : updateBookingRecordsReadByLocal(): Booking--END->");
		return result;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#updateReturnToOriginWithReadByLocal(List)
	 */
	@Override
	public Boolean updateReturnToOriginWithReadByLocal(
			List<RtnToOrgDO> rtnToOrgDOList) {
		Boolean result = false;
		if (rtnToOrgDOList != null && !rtnToOrgDOList.isEmpty()) {
			for (RtnToOrgDO rtnToOrgDO : rtnToOrgDOList) {
				/** Set the READ_BY_LOCAL status */
				rtnToOrgDO
				.setReadByLocal(CentralDataExtractorConstant.READ_BY_LOCAL_SUCCESS);
			}

			getHibernateTemplate().saveOrUpdateAll(rtnToOrgDOList);
			result = true;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getAllOfficesUnderRo(String)
	 */
	@Override
	public List<String> getAllOfficesUnderRo(String branchCode)
	throws CGSystemException {
		String qry = "getAllOfficeUnderRo";
		String param = "branchCode";
		LOGGER.debug("CentralDataExtractorDAOImpl : getAllOfficesUnderRo(): START");
		LOGGER.debug("CentralDataExtractorDAOImpl : getAllOfficesUnderRo()-->PARAMS ===: branchCode :"
				+ branchCode);
		List<String> officeList = getHibernateTemplate()
		.findByNamedQueryAndNamedParam(qry, param, branchCode);
		LOGGER.debug("CentralDataExtractorDAOImpl : getAllOfficesUnderRo(): result : "
				+ officeList);
		LOGGER.debug("CentralDataExtractorDAOImpl : getAllOfficesUnderRo(): END ");
		return officeList;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#updateDataExtractionWithTransitStatus(List)
	 */
	@Override
	public Boolean updateDataExtractionWithTransitStatus(
			List<DataExtractionDO> dataExtractionDoList) {
		LOGGER.debug("CentralDataExtractorDAOImpl : updateDataExtractionWithTransitStatus(): START");
		Boolean result = false;
		if (dataExtractionDoList != null && !dataExtractionDoList.isEmpty()) {
			LOGGER.debug("CentralDataExtractorDAOImpl : updateDataExtractionWithTransitStatus(): dataExtractionDoList SIZE "
					+ dataExtractionDoList.size());
			for (DataExtractionDO dataExtractionDo : dataExtractionDoList) {
				/** Set the DATA_READ status */
				dataExtractionDo
				.setDataStatus(CentralDataExtractorConstant.EXTRACTED_DATA_TRANSIT_STATUS);
				dataExtractionDo.setTransactionCrDate(Calendar.getInstance()
						.getTime());
			}
			LOGGER.debug("CentralDataExtractorDAOImpl : updateDataExtractionWithTransitStatus(): dataExtractionDoList updating data extraction do list with read status");
			getHibernateTemplate().saveOrUpdateAll(dataExtractionDoList);
			result = true;
			LOGGER.debug("CentralDataExtractorDAOImpl : updateDataExtractionWithTransitStatus(): dataExtractionDoList updated status :"
					+ result);
		}
		LOGGER.debug("CentralDataExtractorDAOImpl : updateDataExtractionWithTransitStatus(): END");
		return result;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getTransitExtractedDataByBranchCode(String)
	 */
	@Override
	public List<DataExtractionDO> getTransitExtractedDataByBranchCode(
			String branchCode) throws CGSystemException {
		LOGGER.debug("CentralDataExtractorDAOImpl : getTransitExtractedDataByBranchCode(): START");
		String query = "getTransitExtractedDataByBranchCode";
		String param = "branchCode";
		List<DataExtractionDO> dataExtractionDOList = getHibernateTemplate()
		.findByNamedQueryAndNamedParam(query, param, branchCode);

		LOGGER.debug("CentralDataExtractorDAOImpl : getTransitExtractedDataByBranchCode(): END");
		return dataExtractionDOList;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#saveExtractedDataForBranchList(List)
	 */
	@Override
	public Boolean saveExtractedDataForBranchList(
			List<DataExtractionDO> extractionList) {
		Boolean result = false;
		LOGGER.debug("CentralDataExtractorDAOImpl : saveExtractedDataForBranch(): START");
		getHibernateTemplate().saveOrUpdateAll(extractionList);
		result = true;
		LOGGER.debug("CentralDataExtractorDAOImpl : saveExtractedDataForBranch(): END");
		return result;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getOfficeDetailsByBranchCode(String)
	 */
	@Override
	public OfficeDO getOfficeDetailsByBranchCode(String branchCode)
	throws CGSystemException {
		List<OfficeDO> officeList = null;
		OfficeDO officeDo = null;
		String query = "getOfficeDetailByOfficeCode";
		String param = "officeCode";
		officeList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				query, param, branchCode);
		if (officeList != null && !officeList.isEmpty()) {
			officeDo = officeList.get(0);
		}
		return officeDo;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getAllSiblingsOfBo(String)
	 */
	@Override
	public List<String> getAllSiblingsOfBo(String branchCode)
	throws CGSystemException {
		List<String> officeList = null;
		String query = "getAllSiblingsOfBo";
		String param = "branchCode";
		officeList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				query, param, branchCode);
		return officeList;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getAllDescendentsOfRo(String)
	 */
	@Override
	public List<String> getAllDescendentsOfRo(String branchCode)
	throws CGSystemException {
		List<String> officeList = null;
		String query = "getAllDescendentsOfRo";
		String param = "branchCode";
		officeList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				query, param, branchCode);
		return officeList;
	}

	/**
	 * Added by Narasimha Rao Kattunga Getting delivery manifest details.
	 *
	 * @param branchCode the branch code
	 * @param maxSize the max size
	 * @return the delivery manifest dtls by branch code
	 * @throws CGSystemException the cG system exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryDO> getDeliveryManifestDtlsByBranchCode(
			String branchCode, Integer maxSize) throws CGSystemException {
		String query = "getDeliveryManifestDtlsByFrIds";
		List<DeliveryDO> dlvMnfstList = null;
		Integer reqOffId = 0;
		OfficeDO regOff = null;
		FDMBODBConfigDO fdmBODBConfig = null;
		try {
			regOff = getBranchByCodeOrID(-1, branchCode);
			if (regOff != null) {
				reqOffId = regOff.getOfficeId();
			}
			if (reqOffId > 0) {
				fdmBODBConfig = getFDMBOBDConfig(reqOffId);
				if (fdmBODBConfig != null) {
					String frIds = fdmBODBConfig.getAddtionalFrIds();
					List<Integer> frIdsArr = StringUtil.parseIntegerList(frIds,
					",");
					String[] params = { "frIds", "officeId" };
					Object[] values = { frIdsArr, reqOffId };
					dlvMnfstList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(query, params,
							values);
				}
			}
		} catch (Exception e) {
			logger.error("CentralDataExtractorDAOImpl::getDeliveryManifestDtlsByBranchCode::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		return dlvMnfstList;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#updateDlvMnfstWithReadByLocal(List)
	 */
	@Override
	public Boolean updateDlvMnfstWithReadByLocal(List<DeliveryDO> dlvMnfstList) {
		Boolean result = false;
		if (dlvMnfstList != null && !dlvMnfstList.isEmpty()) {
			for (DeliveryDO deliveryDO : dlvMnfstList) {
				/** Set the READ_BY_LOCAL status */
				deliveryDO
				.setReadByLocal(CentralDataExtractorConstant.READ_BY_LOCAL_SUCCESS);
			}
			getHibernateTemplate().saveOrUpdateAll(dlvMnfstList);
			result = true;
		}
		return result;
	}

	/**
	 * Gets the branch by code or id.
	 *
	 * @param branchId the branch id
	 * @param branchCode the branch code
	 * @return the branch by code or id
	 * @throws CGSystemException the cG system exception
	 */
	private OfficeDO getBranchByCodeOrID(Integer branchId, String branchCode)
	throws CGSystemException {
		OfficeDO officeDO = new OfficeDO();
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			if (StringUtils.isEmpty(branchCode)) {
				officeDO = (OfficeDO) session
				.createCriteria(OfficeDO.class)
				.add(Restrictions.eq(
						CentralDataExtractorConstant.OFFICE_ID,
						branchId)).uniqueResult();
			} else {
				officeDO = (OfficeDO) session
				.createCriteria(OfficeDO.class)
				.add(Restrictions.eq(
						CentralDataExtractorConstant.OFFICE_CODE,
						branchCode)).uniqueResult();
			}
		} catch (Exception obj) {
			logger.error("CentralDataExtractorDAOImpl::getBranchByCodeOrID::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		} finally {
			session.close();
		}
		return officeDO;
	}

	/**
	 * Gets the fDMBOBD config.
	 *
	 * @param branchId the branch id
	 * @return the fDMBOBD config
	 * @throws CGSystemException the cG system exception
	 */
	@SuppressWarnings("unchecked")
	private FDMBODBConfigDO getFDMBOBDConfig(Integer branchId)
	throws CGSystemException {
		FDMBODBConfigDO fdmBoDbConfigDO = null;
		List<FDMBODBConfigDO> fdmBoDbConfigDOs = null;
		try {
			String fdmBodbAdminCofig = "getFDMBoDBConfig";
			fdmBoDbConfigDOs = getHibernateTemplate()
			.findByNamedQueryAndNamedParam(fdmBodbAdminCofig,
					"branchId", branchId);
			if (fdmBoDbConfigDOs != null && fdmBoDbConfigDOs.size() > 0) {
				fdmBoDbConfigDO = fdmBoDbConfigDOs.get(0);
			}
		} catch (Exception obj) {
			logger.error("CentralDataExtractorDAOImpl::getFDMBOBDConfig::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		}
		return fdmBoDbConfigDO;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getTransitExtractedData(String, List)
	 */
	@Override
	public List<DataExtractionDO> getTransitExtractedData(String branchCode,
			List<Integer> idList) throws CGSystemException {
		LOGGER.debug("CentralDataExtractorDAOImpl : getTransitExtractedDataByBranchCodeAndIds(): START");
		String query = null;
		query = "getTransitExtractedDataByIdList";
		List<DataExtractionDO> dataExtractionDOList = getHibernateTemplate()
		.findByNamedQueryAndNamedParam(query, "idList", idList);

		LOGGER.debug("CentralDataExtractorDAOImpl : getTransitExtractedDataByBranchCode(): END");
		return dataExtractionDOList;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#modifyDataExtractionStatusToTransmission(List)
	 */
	@Override
	public Boolean modifyDataExtractionStatusToTransmission(
			List<Integer> dataExIdList) throws CGSystemException {
		Session session = null;
		Boolean result = Boolean.FALSE;
		LOGGER.debug("CentralDataExtractorDAOImpl : modifyDataExtractionStatusToTransmission(): START with params"
				+ dataExIdList.toString());
		session = getHibernateTemplate().getSessionFactory().openSession();
		Query qry;
		try {
			qry = session.getNamedQuery("updateRecordStatusToTransmission");
			qry.setParameterList("idList", dataExIdList);
			qry.setTimestamp("transDate", Calendar.getInstance().getTime());
			LOGGER.debug("CentralDataExtractorDAOImpl : modifyDataExtractionStatusToTransmission(): END updated count:["
					+ qry.executeUpdate() + "]");
			result = Boolean.TRUE;
		} catch (Exception obj) {
			logger.error("CentralDataExtractorDAOImpl::modifyDataExtractionStatusToTransmission::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		} finally {
			session.close();
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#restoreDataExtractionStatusToTransmission(List)
	 */
	@Override
	public Boolean restoreDataExtractionStatusToTransmission(
			List<Integer> dataExIdList) throws CGSystemException {
		Session session = null;
		Boolean result = Boolean.FALSE;
		LOGGER.debug("CentralDataExtractorDAOImpl : restoreDataExtractionStatusToTransmission(): START with params"
				+ dataExIdList.toString());
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qry = session.getNamedQuery("restoreRecordStatusToUnread");
			qry.setParameterList("idList", dataExIdList);
			result = Boolean.TRUE;
			LOGGER.debug("CentralDataExtractorDAOImpl : restoreDataExtractionStatusToTransmission(): END updated count:["
					+ qry.executeUpdate() + "]");
		} catch (Exception obj) {
			logger.error("CentralDataExtractorDAOImpl::restoreDataExtractionStatusToTransmission::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		} finally {
			session.close();
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#updateReadByLocalFlagInBooking(List)
	 */
	@Override
	public Boolean updateReadByLocalFlagInBooking(List<Integer> bookingExIdList)
	throws CGSystemException {
		Session session = null;
		Boolean result = Boolean.FALSE;
		LOGGER.debug("CentralDataExtractorDAOImpl : updateReadByLocalFlagInBooking(): START with params"
				+ bookingExIdList.toString());
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qry = session.getNamedQuery("updateReadByLocalFlag");
			qry.setParameterList("bookingIdList", bookingExIdList);
			qry.setString("readByLocal",
					CentralDataExtractorConstant.READ_BY_LOCAL_SUCCESS);
			result = Boolean.TRUE;
			LOGGER.debug("CentralDataExtractorDAOImpl : updateReadByLocalFlagInBooking(): END updated count:["
					+ qry.executeUpdate() + "]");
		} catch (Exception obj) {
			logger.error("CentralDataExtractorDAOImpl::updateReadByLocalFlagInBooking::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		}

		finally {
			session.close();
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#updateGoodsRenewalWithReadByFranchisee(List)
	 */
	@Override
	public Boolean updateGoodsRenewalWithReadByFranchisee(
			List<Integer> goodsRenewalIdList) throws CGSystemException{
		Session session = null;
		Boolean result = Boolean.FALSE;
		LOGGER.debug("CentralDataExtractorDAOImpl : updateGoodsRenewalWithReadByFranchisee(): START with params"
				+ goodsRenewalIdList.toString());
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qry = session.getNamedQuery(CentralDataExtractorConstant.QRY_UPDATE_GOODS_RENEWAL_FOR_FR_MODULE);
			qry.setParameterList(CentralDataExtractorConstant.QRY_PARAM_GOODS_RENEWAL_ID_LIST, goodsRenewalIdList);
			qry.setString(CentralDataExtractorConstant.READ_BY_FRANCHISEE,
					CentralDataExtractorConstant.READ_BY_LOCAL_SUCCESS);
			result = Boolean.TRUE;
			LOGGER.debug("CentralDataExtractorDAOImpl : updateGoodsRenewalWithReadByFranchisee(): END updated count:["
					+ qry.executeUpdate() + "]");
		} catch (Exception obj) {
			logger.error("CentralDataExtractorDAOImpl::updateGoodsRenewalWithReadByFranchisee::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		}

		finally {
			session.close();
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#updateGoodsCanclWithReadByFranchisee(List)
	 */
	@Override
	public Boolean updateGoodsCanclWithReadByFranchisee(
			List<Integer> goodsCanclIdList) throws CGSystemException{
		Session session = null;
		Boolean result = Boolean.FALSE;
		LOGGER.debug("CentralDataExtractorDAOImpl : updateGoodsCanclWithReadByFranchisee(): START with params"
				+ goodsCanclIdList.toString());
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qry = session.getNamedQuery(CentralDataExtractorConstant.QRY_UPDATE_GOODS_CANCEL_FOR_FR_MODULE);
			qry.setParameterList(CentralDataExtractorConstant.QRY_PARAM_GOODS_CANCEL_ID_LIST, goodsCanclIdList);
			qry.setString(CentralDataExtractorConstant.READ_BY_FRANCHISEE,
					CentralDataExtractorConstant.READ_BY_LOCAL_SUCCESS);
			result = Boolean.TRUE;
			LOGGER.debug("CentralDataExtractorDAOImpl : updateGoodsCanclWithReadByFranchisee(): END updated count:["
					+ qry.executeUpdate() + "]");
		} catch (Exception obj) {
			logger.error("CentralDataExtractorDAOImpl::updateGoodsCanclWithReadByFranchisee::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		}

		finally {
			session.close();
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#updateGoodsIssueWithReadByFranchisee(List)
	 */
	@Override
	public Boolean updateGoodsIssueWithReadByFranchisee(
			List<Integer> goodsIssueIdList) throws CGSystemException{
		Session session = null;
		Boolean result = Boolean.FALSE;
		LOGGER.debug("CentralDataExtractorDAOImpl : updateGoodsIssueWithReadByFranchisee(): START with params"
				+ goodsIssueIdList.toString());
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qry = session.getNamedQuery(CentralDataExtractorConstant.QRY_UPDATE_GOODS_ISSUE_FOR_FR_MODULE);
			qry.setParameterList(CentralDataExtractorConstant.QRY_PARAM_GOODS_ISSUE_ID_LIST, goodsIssueIdList);
			qry.setString(CentralDataExtractorConstant.READ_BY_FRANCHISEE,
					CentralDataExtractorConstant.READ_BY_LOCAL_SUCCESS);
			result = Boolean.TRUE;
			LOGGER.debug("CentralDataExtractorDAOImpl : updateGoodsIssueWithReadByFranchisee(): END updated count:["
					+ qry.executeUpdate() + "]");
		} catch (Exception obj) {
			logger.error("CentralDataExtractorDAOImpl::updateGoodsIssueWithReadByFranchisee::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		}

		finally {
			session.close();
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getAllFranchiseeCodes()
	 */
	@Override
	public List<String> getAllFranchiseeCodes() {
		LOGGER.debug("CentralDataExtractorDAOImpl : getAllFranchiseeCodes(): START");
		List<String> frCodes = null;
		frCodes = getHibernateTemplate().findByNamedQuery(
		"getFranchiseesForFrModule");
		LOGGER.debug("CentralDataExtractorDAOImpl : getAllFranchiseeCodes(): END");
		return frCodes;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getGoodsIssueDataForFrModule(List, Integer)
	 */
	@Override
	public List<GoodsIssueDO> getGoodsIssueDataForFrModule(
			List<String> FrCodeList, Integer maxSize) throws CGSystemException {
		String query = CentralDataExtractorConstant.QRY_GET_GOODS_ISSUE_DTLS_FOR_FR_MODULE;

		Session session = null;
		List<GoodsIssueDO> goodsIssueDoList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qury = session.getNamedQuery(query);
			qury.setParameterList(CentralDataExtractorConstant.FRANCHISEE_CODE, FrCodeList);
			qury.setMaxResults(maxSize);
			goodsIssueDoList = qury.list();
		} catch (Exception e) {
			logger.error("CentralDataExtractorDAOImpl::getGoodsIssueDataForFrModule::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.close();
		}

		return goodsIssueDoList;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getGoodsCanclDataForFrModule(List, Integer)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<GoodscCancellationDO> getGoodsCanclDataForFrModule(
			List<String> FrCodeList, Integer maxGoodsCanclSize)
			throws CGSystemException {
		LOGGER.debug("CentralDataExtractorDAOImpl : getGoodsCanclDataByBranchCode(): START");
		String queryGoodsCancl = CentralDataExtractorConstant.QRY_GET_GOODS_CANCEL_DTLS_FOR_FR_MODULE;
		Session session = null;
		List<GoodscCancellationDO> dataExtrctGoodsCanclDOList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qury = session.getNamedQuery(queryGoodsCancl);
			qury.setParameterList(CentralDataExtractorConstant.FRANCHISEE_CODE, FrCodeList);
			qury.setMaxResults(maxGoodsCanclSize);
			dataExtrctGoodsCanclDOList = qury.list();
		} catch (Exception e) {
			logger.error("CentralDataExtractorDAOImpl::getGoodsCanclDataForFrModule::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.close();
		}


		LOGGER.debug("CentralDataExtractorDAOImpl : getGoodsCanclDataByBranchCode(): END");
		return dataExtrctGoodsCanclDOList;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getGoodsRenewalDataForFrModule(List, Integer)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<GoodsRenewalDO> getGoodsRenewalDataForFrModule(
			List<String> FrCodeList, Integer maxGoodsRenewalSize)
			throws CGSystemException {
		LOGGER.debug("CentralDataExtractorDAOImpl : getGoodsRenewalDataForFrModule(): START");
		String queryGoodsRenewal = CentralDataExtractorConstant.QRY_GET_GOODS_RENWAL_DTLS_FOR_FR_MODULE;

		Session session = null;
		List<GoodsRenewalDO> dataExtrctGoodsRenewalDOList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qury = session.getNamedQuery(queryGoodsRenewal);
			qury.setParameterList(BusinessConstants.FRANCHISEE_CODE, FrCodeList);
			qury.setMaxResults(maxGoodsRenewalSize);
			dataExtrctGoodsRenewalDOList = qury.list();
		} catch (Exception e) {
			logger.error("CentralDataExtractorDAOImpl::getGoodsRenewalDataForFrModule::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.debug("CentralDataExtractorDAOImpl : getGoodsRenewalDataForFrModule(): END");
		return dataExtrctGoodsRenewalDOList;
	}

	public void updateUserReadByLocal(
			List<UserDO> userDOList) {
		LOGGER.debug("CentralDataExtractorDAOImpl : updateGoodsRenewalWithReadByLocal(): START");

		if (userDOList != null && userDOList.size() > 0) {
			for (UserDO userDO : userDOList) {
				/** Set the READ_BY_LOCAL status */
				userDO.setReadByLocal(CentralDataExtractorConstant.READ_BY_LOCAL_SUCCESS);
				userDO.setLastTransModifiedDate(DateFormatterUtil.getCurrentDate());
			}

			getHibernateTemplate().saveOrUpdateAll(userDOList);
		}
		LOGGER.debug("CentralDataExtractorDAOImpl : updateGoodsRenewalWithReadByLocal(): END");

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDO> getUserDetailsByOfficeCode(String officeCode,
			Integer maxSize) throws CGSystemException {
		LOGGER.debug("CentralDataExtractorDAOImpl : getBookingDetailsByOfficeCode(): START");
		String query = CentralDataExtractorConstant.GET_USERS_BY_OFFICE;
		Session session = null;
		List<UserDO> userDOList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();

			Query qury = session.getNamedQuery(query);
			qury.setMaxResults(maxSize);
			userDOList = qury.list();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		LOGGER.debug("CentralDataExtractorDAOImpl : getBookingDetailsByCn(): Total count :"
				+ ((userDOList != null && !userDOList.isEmpty()) ? userDOList
						.size() : "0"));

		LOGGER.debug("CentralDataExtractorDAOImpl : getBookingDetailsByCn(): END");
		return userDOList;

	}
	
	public List<String> getOfficeDataSyncDetails()
			throws CGSystemException {
		LOGGER.debug("CentralDataExtractorDAOImpl : getOfficeWiseDataSync(): START");
		Session session = null;
		String query = "getOfficeDataSyncByOffice";
		List<String> dataOfficeDataSyncDOList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qury = session.getNamedQuery(query);
			dataOfficeDataSyncDOList = qury.list();
		} catch (Exception e) {
			throw new CGSystemException(e);
		} finally {
			session.close();
		}

		LOGGER.debug("CentralDataExtractorDAOImpl : getExtractedDataByBranchCode(): END");

		return dataOfficeDataSyncDOList;
	}
	
	
	public List<CGBaseEntity> getUnSyncData(String namedQuery, Integer maxRow) {
		List<CGBaseEntity> unSyncList =null;
		Session session =null;
		HibernateTemplate template = getHibernateTemplate();
		try {
			if(maxRow ==  null) {
				maxRow = GET_MAX_ROW_COUNT;
			}
			 session = template.getSessionFactory().openSession();
			 Query q= session.getNamedQuery(namedQuery);
			 q.setMaxResults(maxRow);
			 unSyncList =  q.list();
			q = null;
		} catch (Exception e) {
			logger.error("BranchToCentralDBSyncDAOImpl::getUnSyncData::Exception::" + e.getMessage());
		}
		finally{
			if(session!=null){
				session.close();
			}
			template = null;
		}
		return unSyncList;
	}
	
	public void updateUnSyncData(CGBaseEntity baseEntity) {
		try{
			getHibernateTemplate().update(baseEntity);	
		}catch(Exception e){
			logger.error("BranchToCentralDBSyncDAOImpl::updateUnSyncData::Exception::" + e.getMessage());
			
		}
		
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BookingDO> getBookingDetailsByFranchiseeCode(List<String> frCodeList,
			Integer maxSize) throws CGSystemException {
		LOGGER.debug("CentralDataExtractorDAOImpl : getBookingDetailsByFranchiseeCode(): START");
		String query = CentralDataExtractorConstant.GET_BOOKINGDATA_BY_FRANCHISEE;
		Session session = null;
		List<BookingDO> bookingDOList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();

			Query qury = session.getNamedQuery(query);
			qury.setParameterList("franchiseeCode", frCodeList);
			qury.setMaxResults(maxSize);
			bookingDOList = qury.list();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		LOGGER.debug("CentralDataExtractorDAOImpl : getBookingDetailsByFranchiseeCode(): Total count :"
				+ ((bookingDOList != null && !bookingDOList.isEmpty()) ? bookingDOList
						.size() : "0"));

		LOGGER.debug("CentralDataExtractorDAOImpl : getBookingDetailsByFranchiseeCode(): END");
		return bookingDOList;

	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ManifestDO> getManifestDetailsByFranchiseeCode(List<String> frCodeList,
			Integer maxSize) throws CGSystemException {
		LOGGER.debug("CentralDataExtractorDAOImpl : getManifestDetailsByFranchiseeCode(): START");
		String query = CentralDataExtractorConstant.GET_MANIFEST_DATA_BY_FRANCHISEE;
		Session session = null;
		List<ManifestDO> manifestDOList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qury = session.getNamedQuery(query);
			qury.setParameterList("franchiseeCode", frCodeList);
			qury.setMaxResults(maxSize);
			manifestDOList = qury.list();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		LOGGER.debug("CentralDataExtractorDAOImpl : getManifestDetailsByFranchiseeCode(): Total count :"
				+ ((manifestDOList != null && !manifestDOList.isEmpty()) ? manifestDOList
						.size() : "0"));

		LOGGER.debug("CentralDataExtractorDAOImpl : getManifestDetailsByFranchiseeCode(): END");
		return manifestDOList;

	}
	
	@Override
	public Boolean updateFrSyncFlagInBookingForFranchisee(List<Integer> bookingExIdList)
	throws CGSystemException {
		Session session = null;
		Boolean result = Boolean.FALSE;
		LOGGER.debug("CentralDataExtractorDAOImpl : updateFrSyncFlagInBookingForFranchisee(): START with params"
				+ bookingExIdList.toString());
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qry = session.getNamedQuery("updateFrSyncFlagForBooking");
			qry.setParameterList("bookingIdList", bookingExIdList);
			qry.setString("frSync",
					CentralDataExtractorConstant.FR_SYNC_SUCCESS);
			result = Boolean.TRUE;
			LOGGER.debug("CentralDataExtractorDAOImpl : updateFrSyncFlagInBookingForFranchisee(): END updated count:["
					+ qry.executeUpdate() + "]");
		} catch (Exception obj) {
			logger.error("CentralDataExtractorDAOImpl::updateFrSyncFlagInBookingForFranchisee::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		}

		finally {
			session.close();
		}

		return result;
	}

	@Override
	public List<DeliveryDO> getDeliveryDetailsByFranchiseeCode(
			List<String> frCodeList, Integer maxDeliveryRecords)
			throws CGSystemException {
		LOGGER.debug("CentralDataExtractorDAOImpl : getDeliveryDetailsByFranchiseeCode(): START");
		String query = CentralDataExtractorConstant.GET_DELIVERYDATA_BY_FRANCHISEE;
		Session session = null;
		List<DeliveryDO> deliveryDOList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qury = session.getNamedQuery(query);
			qury.setParameterList("franchiseeCode", frCodeList);
			qury.setMaxResults(maxDeliveryRecords);
			deliveryDOList = qury.list();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		LOGGER.debug("CentralDataExtractorDAOImpl : getDeliveryDetailsByFranchiseeCode(): Total count :"
				+ ((deliveryDOList != null && !deliveryDOList.isEmpty()) ? deliveryDOList
						.size() : "0"));

		LOGGER.debug("CentralDataExtractorDAOImpl : getDeliveryDetailsByFranchiseeCode(): END");
		return deliveryDOList;

	}
	
	@Override
	public Boolean updateFrSyncFlagInDeliveryForFranchisee(List<Integer> deliveryExIdList)
	throws CGSystemException {
		Session session = null;
		Boolean result = Boolean.FALSE;
		LOGGER.debug("CentralDataExtractorDAOImpl : updateFrSyncFlagInDeliveryForFranchisee(): START with params"
				+ deliveryExIdList.toString());
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qry = session.getNamedQuery("updateFrSyncFlagForDelivery");
			qry.setParameterList("deliveryIdList", deliveryExIdList);
			qry.setString("frSync",
					CentralDataExtractorConstant.FR_SYNC_SUCCESS);
			result = Boolean.TRUE;
			LOGGER.debug("CentralDataExtractorDAOImpl : updateFrSyncFlagInDeliveryForFranchisee(): END updated count:["
					+ qry.executeUpdate() + "]");
		} catch (Exception obj) {
			logger.error("CentralDataExtractorDAOImpl::updateFrSyncFlagInDeliveryForFranchisee::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		}

		finally {
			session.close();
		}

		return result;
	}

	
	@Override
	public Boolean updateFrSyncFlagInManifestForFranchisee(List<Integer> manifestExIdList)
	throws CGSystemException {
		Session session = null;
		Boolean result = Boolean.FALSE;
		LOGGER.debug("CentralDataExtractorDAOImpl : updateFrSyncFlagInManifestForFranchisee(): START with params"
				+ manifestExIdList.toString());
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qry = session.getNamedQuery("updateFrSyncFlagForManifest");
			qry.setParameterList("manifestIdList", manifestExIdList);
			qry.setString("frSync",
					CentralDataExtractorConstant.FR_SYNC_SUCCESS);
			result = Boolean.TRUE;
			LOGGER.debug("CentralDataExtractorDAOImpl : updateFrSyncFlagInManifestForFranchisee(): END updated count:["
					+ qry.executeUpdate() + "]");
		} catch (Exception obj) {
			logger.error("CentralDataExtractorDAOImpl::updateFrSyncFlagInManifestForFranchisee::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		}

		finally {
			session.close();
		}

		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.CentralDataExtractorDAO#getDispatchDataByBranchCode(String, Integer)
	 */
	@Override
	public List<DispatchDO> getDispatchDataByFranchiseeCode(List<String> frCodeList,
			Integer maxSize) throws CGSystemException {
		LOGGER.debug("CentralDataExtractorDAOImpl : getDispatchDataByBranchCode(): START");
		String queryDispatchData = "getDispatchDataByFranchiseeCode";
		Session session = null;
		List<DispatchDO> dispatchDataList = null;

		try {
			
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qury = session.getNamedQuery(queryDispatchData);
			qury.setParameterList("franchiseeCode", frCodeList);
			qury.setMaxResults(maxSize);
			dispatchDataList = qury.list();
		} catch (Exception e) {
			logger.error("CentralDataExtractorDAOImpl::getDispatchDataByBranchCode::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.close();
		}

		LOGGER.debug("CentralDataExtractorDAOImpl : getDispatchDataByBranchCode(): END");
		return dispatchDataList;
	}
	
	@Override
	public Boolean updateFrSyncFlagInDispatchForFranchisee(List<Integer> dispatchExIdList)
	throws CGSystemException {
		Session session = null;
		Boolean result = Boolean.FALSE;
		LOGGER.debug("CentralDataExtractorDAOImpl : updateFrSyncFlagInDispatchForFranchisee(): START with params"
				+ dispatchExIdList.toString());
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qry = session.getNamedQuery("updateFrSyncFlagForDispatch");
			qry.setParameterList("dispatchIdList", dispatchExIdList);
			qry.setString("frSync",
					CentralDataExtractorConstant.FR_SYNC_SUCCESS);
			result = Boolean.TRUE;
			LOGGER.debug("CentralDataExtractorDAOImpl : updateFrSyncFlagInDispatchForFranchisee(): END updated count:["
					+ qry.executeUpdate() + "]");
		} catch (Exception obj) {
			logger.error("CentralDataExtractorDAOImpl::updateFrSyncFlagInDispatchForFranchisee::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		}

		finally {
			session.close();
		}

		return result;
	}
	
	public List<String> getOfficeDataSyncByFranchisee()
		throws CGSystemException {
		LOGGER.debug("CentralDataExtractorDAOImpl : getOfficeWiseDataSync(): START");
		Session session = null;
		String query = "getOfficeDataSyncByFranchisee";
		List<String> dataOfficeDataSyncDOList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qury = session.getNamedQuery(query);
			dataOfficeDataSyncDOList = qury.list();
		} catch (Exception e) {
			throw new CGSystemException(e);
		} finally {
			session.close();
		}

		LOGGER.debug("CentralDataExtractorDAOImpl : getExtractedDataByBranchCode(): END");

		return dataOfficeDataSyncDOList;
	}
}
