package com.ff.admin.complaints.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.complaints.ServiceRequestFilters;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.complaints.ServiceRelatedDetailsDO;
import com.ff.domain.complaints.ServiceRequestComplaintTypeDO;
import com.ff.domain.complaints.ServiceRequestCustTypeDO;
import com.ff.domain.complaints.ServiceRequestDO;
import com.ff.domain.complaints.ServiceRequestQueryTypeDO;
import com.ff.domain.complaints.ServiceRequestStatusDO;
import com.ff.domain.complaints.ServiceRequestTransfertoDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.umc.EmployeeUserDO;

public class ComplaintsCommonDAOImpl extends CGBaseDAO implements
		ComplaintsCommonDAO {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ComplaintsCommonDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerTypeDO> getCustomerTypeList() throws CGSystemException {
		LOGGER.debug("ComplaintsCommonDAOImpl :: getCustomerTypeList() :: Start --------> ::::::");
		List<CustomerTypeDO> customerDOList = null;
		Session session = null;
		session = createSession();
		try {
			customerDOList = (List<CustomerTypeDO>) session.createCriteria(
					CustomerTypeDO.class).list();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ComplaintsCommonDAOImpl::getCustomerTypeList() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ComplaintsCommonDAOImpl :: getCustomerTypeList() :: End --------> ::::::");
		return customerDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceRequestCustTypeDO> getCustomerTypeListForComplaints()
			throws CGSystemException {
		LOGGER.debug("ComplaintsCommonDAOImpl::getCustomerTypeListForComplaints()  :: Start --------> ::::::");
		List<ServiceRequestCustTypeDO> customerDOList = null;
		Session session = null;
		session = createSession();
		try {
			customerDOList = (List<ServiceRequestCustTypeDO>) session
					.createCriteria(ServiceRequestCustTypeDO.class).list();
		} catch (Exception e) {
			LOGGER.error("ComplaintsCommonDAOImpl::getCustomerTypeListForComplaints() ::Exception "
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ComplaintsCommonDAOImpl::getCustomerTypeListForComplaints() :: End --------> ::::::");
		return customerDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceRequestQueryTypeDO> getServiceRequestQueryTypeDetails(ServiceRequestQueryTypeDO queryTypeDO)
			throws CGSystemException {
		LOGGER.debug("ComplaintsCommonDAOImpl::getServiceRequestQueryTypeDetails()  :: Start --------> ::::::");
		List<ServiceRequestQueryTypeDO> customerDOList = null;
		Session session = null;
		session = createSession();
		Criteria criteria=null;
		try {
			criteria = session
					.createCriteria(ServiceRequestQueryTypeDO.class);
			
			if(!StringUtil.isNull(queryTypeDO)){
				if(!StringUtil.isStringEmpty(queryTypeDO.getQueryTypeCode())){
					criteria.add(Restrictions.eq("queryTypeCode", queryTypeDO.getQueryTypeCode()));
				}

				if(!StringUtil.isStringEmpty(queryTypeDO.getQueryTypeName())){
					criteria.add(Restrictions.eq("queryTypeName", queryTypeDO.getQueryTypeName()));
				}
				if(!StringUtil.isStringEmpty(queryTypeDO.getQueryType())){
					if(queryTypeDO.getQueryType().contains(FrameworkConstants.CHARACTER_COMMA)){
						criteria.add(Restrictions.in("queryType", queryTypeDO.getQueryType().split(FrameworkConstants.CHARACTER_COMMA)));
					}else{
						criteria.add(Restrictions.eq("queryType", queryTypeDO.getQueryType()));
					}
				}


			}
			
			customerDOList= criteria.list();
		} catch (Exception e) {
			LOGGER.error("ComplaintsCommonDAOImpl::getServiceRequestQueryTypeDetails() ::Exception "
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ComplaintsCommonDAOImpl::getServiceRequestQueryTypeDetails() :: End --------> ::::::");
		return customerDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceRequestComplaintTypeDO> getComplaintTypeDetails()
			throws CGSystemException {
		LOGGER.debug("ComplaintsCommonDAOImpl::getComplaintTypeDetails()  :: Start --------> ::::::");
		List<ServiceRequestComplaintTypeDO> customerDOList = null;
		Session session = null;
		session = createSession();
		try {
			customerDOList = (List<ServiceRequestComplaintTypeDO>) session
					.createCriteria(ServiceRequestComplaintTypeDO.class).list();
		} catch (Exception e) {
			LOGGER.error("ComplaintsCommonDAOImpl::getComplaintTypeDetails() ::Exception "
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ComplaintsCommonDAOImpl::getComplaintTypeDetails() :: End --------> ::::::");
		return customerDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeDO> getEmployeeDetailsByDesignationType(
			String designationType) throws CGSystemException {
		LOGGER.debug("ComplaintsCommonDAOImpl :: getEmployeeDetailsByDesignationType() :: Start --------> ::::::");
		List<EmployeeDO> employeeDOList = null;
		try {
			employeeDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ComplaintsCommonConstants.QRY_GET_EMPLOYEES_DTLS_BY_DESIGNATION_TYPE,
							new String[] { ComplaintsCommonConstants.DESIGNATION },
							new Object[] { designationType });
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ComplaintsCommonDAOImpl::getEmployeeDetailsByDesignationType() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("ComplaintsCommonDAOImpl :: getEmployeeDetailsByDesignationType() :: End --------> ::::::");
		return employeeDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeDO> getEmployeeDetailsByUserRoleAndOffice(
			String designationType, Integer officeId) throws CGSystemException {
		LOGGER.debug("ComplaintsCommonDAOImpl :: getEmployeeDetailsByDesignationType() :: Start --------> ::::::");
		List<EmployeeDO> employeeDOList = null;
		try {
			employeeDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ComplaintsCommonConstants.QRY_GET_EMPLOYEES_DTLS_BY_ROLE_OFFICE,
							new String[] {
									ComplaintsCommonConstants.QRY_PARAM_ROLE,
									ComplaintsCommonConstants.OFFICEID },
							new Object[] { designationType, officeId });
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ComplaintsCommonDAOImpl::getEmployeeDetailsByDesignationType() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("ComplaintsCommonDAOImpl :: getEmployeeDetailsByDesignationType() :: End --------> ::::::");
		return employeeDOList;
	}
	@Override
	public List<EmployeeDO> getEmployeeDetailsByUserRoleAndOffice(
			String designationType, List<Integer> officeIdList) throws CGSystemException {
		LOGGER.debug("ComplaintsCommonDAOImpl :: getEmployeeDetailsByUserRoleAndOffice() :: Start --------> ::::::");
		List<EmployeeDO> employeeDOList = null;
		Session session=null;
		try {
			session = createSession();
			Query qry = session
					.getNamedQuery(ComplaintsCommonConstants.QRY_GET_EMPLOYEES_DTLS_BY_ROLE_OFFICE);
			qry.setString(ComplaintsCommonConstants.QRY_PARAM_ROLE,designationType);
			qry.setParameterList(ComplaintsCommonConstants.OFFICEID, officeIdList);
			employeeDOList=qry.list();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ComplaintsCommonDAOImpl::getEmployeeDetailsByUserRoleAndOffice() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		LOGGER.debug("ComplaintsCommonDAOImpl :: getEmployeeDetailsByUserRoleAndOffice() :: End --------> ::::::");
		return employeeDOList;
	}

	@Override
	public Boolean saveOrUpdateComplaints(
			List<ServiceRequestDO> serviceRequestDOs) throws CGSystemException {
		LOGGER.debug("ComplaintsCommonDAOImpl :: saveOrUpdateComplaints() :: Start --------> ::::::");
		Boolean flag = Boolean.FALSE;
		try {
			for (ServiceRequestDO serviceRequestDO : serviceRequestDOs) {
				getHibernateTemplate().saveOrUpdate(serviceRequestDO);
			}
			flag = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ComplaintsCommonDAOImpl::saveOrUpdateComplaints() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ComplaintsCommonDAOImpl :: saveOrUpdateComplaints() :: End --------> ::::::");
		return flag;
	}

	@Override
	public Boolean saveOrUpdateComplaints(ServiceRequestDO serviceRequestDO)
			throws CGSystemException {
		LOGGER.debug("ComplaintsCommonDAOImpl :: saveOrUpdateComplaints() :: Start --------> ::::::");
		Boolean flag = Boolean.FALSE;
		try {
			getHibernateTemplate().saveOrUpdate(serviceRequestDO);
			flag = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ComplaintsCommonDAOImpl::saveOrUpdateComplaints() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ComplaintsCommonDAOImpl :: saveOrUpdateComplaints() :: End --------> ::::::");
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean updateServiceRequest(ServiceRequestDO serviceRequestDO)
			throws CGSystemException {
		LOGGER.debug("ComplaintsCommonDAOImpl :: saveOrUpdateComplaints() :: Start --------> ::::::");
		Boolean flag = Boolean.FALSE;
		Session session = null;
		List<ServiceRequestDO> serviceRequestList = null;
		ServiceRequestDO updateReqestDO = null;
		Transaction tx = null;
		try {
			session = createSession();
			tx = session.beginTransaction();
			Query qry = session
					.getNamedQuery(ComplaintsCommonConstants.QRY_GET_SERVICE_REQUEST_BY_SERVICE_REQUEST_NO);
			qry.setString(ComplaintsCommonConstants.SERVICE_REQUEST_NO,
					serviceRequestDO.getServiceRequestNo());
			serviceRequestList = qry.list();
			if (!CGCollectionUtils.isEmpty(serviceRequestList)) {
				updateReqestDO = serviceRequestList.get(0);
				updateReqestDO.setAssignedTo(serviceRequestDO.getAssignedTo());
				if(!serviceRequestDO.isForTransferFunctionality()){
					updateReqestDO.setServiceRequestStatusDO(serviceRequestDO
							.getServiceRequestStatusDO());
					updateReqestDO.setUpdatedBy(serviceRequestDO.getUpdatedBy());
					updateReqestDO.setUpdateDate(DateUtil.getCurrentDate());
					updateReqestDO.setRemark(serviceRequestDO.getRemark());
					/** linked Service reqno should not allow to edit*/
					/*if (!StringUtil.isStringEmpty(serviceRequestDO
						.getLinkedServiceReqNo())) {
					updateReqestDO.setLinkedServiceReqNo(serviceRequestDO
							.getLinkedServiceReqNo());
				}*/
				}
				session.update(updateReqestDO);
				serviceRequestDO=updateReqestDO;
			}
			tx.commit();
			flag = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ComplaintsCommonDAOImpl::saveOrUpdateComplaints() :: "
					+ e);
			if (tx != null) {
				tx.rollback();
			}
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ComplaintsCommonDAOImpl :: saveOrUpdateComplaints() :: End --------> ::::::");
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceRequestDO> getServiceRequestDetails(
			ServiceRequestFilters requestFilters) throws CGSystemException {
		LOGGER.debug("ComplaintsCommonDAOImpl::getServiceRequestDetails::START------------>:::::::");
		Session session = null;
		Criteria criteria = null;
		List<ServiceRequestDO> serviceRequestDOs = null;
		try {
			session = createSession();
			criteria = session.createCriteria(ServiceRequestDO.class,
					"serviceReq");

			if (!StringUtil.isEmptyInteger(requestFilters.getUserId())) {
				criteria.createAlias("userDO", "userDO");
				criteria.add(Restrictions.eq("userDO.userId",
						requestFilters.getUserId()));
			}

			if (StringUtils.isNotEmpty(requestFilters.getReqStatus())) {
				criteria.add(Restrictions.eq("serviceReq.status",
						requestFilters.getReqStatus()));
			}

			if (StringUtils.isNotEmpty(requestFilters.getConsignmentNo())) {
				criteria.createAlias("consignmentDO", "consignmentDO");
				criteria.add(Restrictions.eq("consignmentDO.consgNo",
						requestFilters.getConsignmentNo()));
			}
			if (StringUtils.isNotEmpty(requestFilters.getCallerPhone())) {
				criteria.add(Restrictions.eq("serviceReq.callerPhone",
						requestFilters.getCallerPhone()));
			}
			/*
			 * if(StringUtils.isNotEmpty(requestFilters.getCallerMobile())){
			 * criteria.add(Restrictions.eq("serviceReq.",
			 * requestFilters.getCallerMobile())); }
			 */
			if (StringUtils.isNotEmpty(requestFilters.getCallerEmail())) {
				criteria.add(Restrictions.eq("serviceReq.callerEmail",
						requestFilters.getCallerEmail()));
			}
			if (StringUtils.isNotEmpty(requestFilters.getReferenceNo())) {
				criteria.add(Restrictions.eq("serviceReq.referenceNo",
						requestFilters.getReferenceNo()));
			}

			serviceRequestDOs = criteria.list();
		} catch (Exception e) {
			LOGGER.error("Error occured in ComplaintsCommonDAOImpl :: getServiceRequestDetails() ::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ComplaintsCommonDAOImpl::getServiceRequestDetails::END------------>:::::::");
		return serviceRequestDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceRequestDO> searchServiceRequestDtls(
			ServiceRequestFilters requestFilters) throws CGSystemException {
		LOGGER.debug("ComplaintsCommonDAOImpl::searchServiceRequestDtls::START------------>:::::::");
		Session session = null;
		Criteria criteria = null;
		List<ServiceRequestDO> serviceRequestDOs = null;
		try {
			session = createSession();
			criteria = session.createCriteria(ServiceRequestDO.class,
					"serviceReq");
			prepareProjectionList(requestFilters, criteria);
			if (!StringUtil.isStringEmpty(requestFilters
					.getServiceRequestNumber())) {
				criteria.add(Restrictions
						.disjunction()
						.add(Restrictions.eq("serviceReq.serviceRequestNo",
								requestFilters.getServiceRequestNumber()))
						.add(Restrictions.eq("serviceReq.linkedServiceReqNo",
								requestFilters.getServiceRequestNumber())));
			}
			if (!StringUtil.isStringEmpty(requestFilters.getCallerPhone())) {
				criteria.add(Restrictions.eq("serviceReq.callerPhone",
						requestFilters.getCallerPhone()));
			}
			/******* for Booking reference number */
			if (!StringUtil.isStringEmpty(requestFilters
					.getBookingReferenceNumber())) {
				criteria.add(Restrictions
						.conjunction()
						.add(Restrictions.eq("serviceReq.bookingNo",
								requestFilters.getBookingReferenceNumber()))
						.add(Restrictions
								.eq("serviceReq.bookingNoType",
										ComplaintsCommonConstants.COMPLAINT_BOOKING_NO_TYPE_REF)));
			}
			/******* for Consignment number */
			if (!StringUtil.isStringEmpty(requestFilters.getConsignmentNo())) {
				criteria.add(Restrictions
						.conjunction()
						.add(Restrictions.eq("serviceReq.bookingNo",
								requestFilters.getConsignmentNo()))
						.add(Restrictions
								.eq("serviceReq.bookingNoType",
										ComplaintsCommonConstants.COMPLAINT_BOOKING_NO_TYPE_CN)));
			}
			criteria.addOrder(Order.desc("serviceReq.createdDate"));
			serviceRequestDOs = criteria.list();
		} catch (Exception e) {
			LOGGER.error(
					"ComplaintsCommonDAOImpl::searchServiceRequestDtls::Exception------------>:::::::",
					e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ComplaintsCommonDAOImpl::searchServiceRequestDtls::END------------>:::::::");
		return serviceRequestDOs;
	}

	private void prepareProjectionList(ServiceRequestFilters requestFilters,
			Criteria criteria) {
		if(!StringUtil.isNull(requestFilters.getIsProjectionRequired())&&requestFilters.getIsProjectionRequired()){
			criteria.setProjection(Projections.projectionList()
					.add(Projections.alias(Projections.property("serviceReq.serviceRequestNo"), "serviceRequestNo"))
					.add(Projections.alias(Projections.property("serviceReq.linkedServiceReqNo"), "linkedServiceReqNo"))
					.add(Projections.alias(Projections.property("serviceReq.callerPhone"), "callerPhone"))
					.add(Projections.alias(Projections.property("serviceReq.bookingNo"), "bookingNo"))
					.add(Projections.alias(Projections.property("serviceReq.bookingNoType"), "bookingNoType"))
					.add(Projections.alias(Projections.property("serviceReq.serviceRequestStatusDO"), "serviceRequestStatusDO"))
					.add(Projections.alias(Projections.property("serviceReq.serviceRequestId"), "serviceRequestId")));
			criteria.setResultTransformer(Transformers.aliasToBean(ServiceRequestDO.class));
		}
	}

	@SuppressWarnings("unchecked")
	public List<ProductDO> getProductList() throws CGSystemException {
		LOGGER.debug("ComplaintsCommonDAOImpl :: getProductList() :: Start --------> ::::::");
		List<ProductDO> productDOList = null;
		Session session = null;
		session = createSession();
		try {
			productDOList = (List<ProductDO>) session.createCriteria(
					ProductDO.class).list();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ComplaintsCommonDAOImpl::getProductList() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ComplaintsCommonDAOImpl :: getProductList() :: End --------> ::::::");
		return productDOList;
	}
	@Override
	public List<ProductDO> getProductList(String consignmentSeries) throws CGSystemException {
		LOGGER.debug("ComplaintsCommonDAOImpl :: getProductList() :: Start --------> ::::::");
		List<ProductDO> productDOList = null;
		Session session = null;
		Criteria criteria=null;
		session = createSession();
		try {
			criteria =  session.createCriteria(
					ProductDO.class);
			if(!StringUtil.isStringEmpty(consignmentSeries)){
				if(consignmentSeries.contains(",")){
					criteria.add(Restrictions.in("consgSeries", consignmentSeries.split(",")));
				}else{
					criteria.add(Restrictions.eq("consgSeries", consignmentSeries));
				}
			
			}
			productDOList=criteria.list();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ComplaintsCommonDAOImpl::getProductList() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ComplaintsCommonDAOImpl :: getProductList() :: End --------> ::::::");
		return productDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PincodeDO> getPincode(final String pincode)
			throws CGSystemException {
		LOGGER.debug("ComplaintsCommonDAOImpl :: getPincode() :: Start --------> ::::::");
		List<PincodeDO> pincodeDOList = null;
		Session session = null;
		session = createSession();
		try {
			pincodeDOList = (List<PincodeDO>) session
					.createCriteria(PincodeDO.class)
					.add(Restrictions.eq("pincode", pincode)).list();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ComplaintsCommonDAOImpl::getPincode() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ComplaintsCommonDAOImpl :: getPincode() :: End --------> ::::::");
		return pincodeDOList;
	}

	@Override
	public void saveOrUpdateServiceRelDtls(
			ServiceRelatedDetailsDO serviceRelatedDetailsDO)
			throws CGSystemException {
		LOGGER.debug("ComplaintsCommonDAOImpl :: saveOrUpdateComplaints() :: Start --------> ::::::");
		try {
			getHibernateTemplate().saveOrUpdate(serviceRelatedDetailsDO);
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ComplaintsCommonDAOImpl::saveOrUpdateComplaints() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ComplaintsCommonDAOImpl :: saveOrUpdateComplaints() :: End --------> ::::::");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceRequestStatusDO> getServiceRequestStatus()
			throws CGSystemException {
		LOGGER.debug("ComplaintsCommonDAOImpl :: getBacklineSummaryStatus() :: Start --------> ::::::");
		List<ServiceRequestStatusDO> statusDOList = null;
		Session session = null;
		session = createSession();
		try {
			statusDOList = (List<ServiceRequestStatusDO>) session
					.createCriteria(ServiceRequestStatusDO.class).list();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ComplaintsCommonDAOImpl::getBacklineSummaryStatus() :: "
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		LOGGER.debug("ComplaintsCommonDAOImpl :: getBacklineSummaryStatus() :: End --------> ::::::");
		return statusDOList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomerDO getCustomerDtlsByCustId(Integer customerId)
			throws CGSystemException {
		LOGGER.trace("ComplaintsCommonDAOImpl :: getCustomerDtlsByCustId() :: START");
		CustomerDO customerDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(CustomerDO.class);
			cr.add(Restrictions.eq("customerId", customerId));
			List<CustomerDO> customerDOs = cr.list();
			if (!CGCollectionUtils.isEmpty(customerDOs)) {
				customerDO = customerDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ComplaintsCommonDAOImpl :: getCustomerDtlsByCustId() :: ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("ComplaintsCommonDAOImpl :: getCustomerDtlsByCustId() :: END");
		return customerDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceRequestTransfertoDO> getTransfettoDetails()
			throws CGSystemException {
		LOGGER.debug("ComplaintsCommonDAOImpl :: getTransfettoDetails() :: Start --------> ::::::");
		List<ServiceRequestTransfertoDO> transfertoDOList = null;
		Session session = null;
		try {
			session = createSession();
			transfertoDOList = (List<ServiceRequestTransfertoDO>) session
					.createCriteria(ServiceRequestTransfertoDO.class).list();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ComplaintsCommonDAOImpl::getTransfettoDetails() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ComplaintsCommonDAOImpl :: getTransfettoDetails() :: End --------> ::::::");
		return transfertoDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getMaxNumberFromProcess(
			SequenceGeneratorConfigTO seqConfigTo, String queryName)
			throws CGSystemException {
		List<String> numberList = null;
		String params[] = { StockCommonConstants.QRY_PARAM_OFFICEID,
				StockCommonConstants.QRY_PARAM_PREFIX,
				StockCommonConstants.QRY_PARAM_NUMBER_LENGTH };
		Object value[] = {
				seqConfigTo.getRequestingBranchId(),
				seqConfigTo.getProcessRequesting()
						+ seqConfigTo.getRequestingBranchCode()
						+ FrameworkConstants.CHARACTER_PERCENTILE,
				seqConfigTo.getLengthOfNumber() };
		numberList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, params, value);
		return !StringUtil.isEmptyList(numberList) ? numberList.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public EmployeeUserDO getEmployeeUser(Integer userId)
			throws CGSystemException {
		LOGGER.trace("ComplaintsCommonDAOImpl :: getEmployeeUser() :: Start --------> ::::::");
		List<EmployeeUserDO> result = null;
		try {
			result = getHibernateTemplate().findByNamedQueryAndNamedParam(
					ComplaintsCommonConstants.QRY_GET_EMP_USERDO_BY_USERID,
					ComplaintsCommonConstants.USER_ID, userId);
			LOGGER.trace("ComplaintsCommonDAOImpl :: getEmployeeUser() :: END --------> ::::::");
			if (result.isEmpty()) {
				return null;
			} else if (result.size() > 1) {
				return null;
			} else {
				EmployeeUserDO domain = result.get(0);
				return domain;
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : ComplaintsCommonDAOImpl.getEmployeeUser", e);
			throw new CGSystemException(e);
		}
	}
	
	@Override
	public List<Integer> getAllOfficeIdByCityId(Integer cityId)
			throws CGSystemException {
		LOGGER.trace("ComplaintsCommonDAOImpl :: getAllOfficeIdByCity() :: Start --------> ::::::");
		List<Integer> result = null;
		try {
			result = getHibernateTemplate().findByNamedQueryAndNamedParam(
					ComplaintsCommonConstants.QRY_GET_ALL_OFFICE_ID_BY_CITY,
					ComplaintsCommonConstants.CITY_ID, cityId);
			LOGGER.trace("ComplaintsCommonDAOImpl :: getAllOfficeIdByCityId() :: END --------> ::::::");
		} catch (Exception e) {
			LOGGER.error("ERROR : ComplaintsCommonDAOImpl.getAllOfficeIdByCityId", e);
			throw new CGSystemException(e);
		}
		return result;
	}
	
	

}
