package com.ff.universe.consignment.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.consignment.ConsignmentDOXDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.universe.booking.constant.UniversalBookingConstants;
import com.ff.universe.constant.UniversalErrorConstants;

public class ConsignmentCommonDAOImpl extends CGBaseDAO implements
		ConsignmentCommonDAO {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ConsignmentCommonDAOImpl.class);

	/*
	 * @Override public CNPricingDetailsDO getConsgPrincingDtls(String
	 * consgNumner) throws CGSystemException { CNPricingDetailsDO cnPricingDtls
	 * = null; Session session = null; try { session = createSession(); try {
	 * cnPricingDtls = (CNPricingDetailsDO) session
	 * .createCriteria(CNPricingDetailsDO.class) .createAlias("consignment",
	 * "consg") .add(Restrictions.eq(
	 * UniversalBookingConstants.QRY_PARAM_CONSG1, consgNumner)).uniqueResult();
	 * } catch (Exception e) {
	 * 
	 * } finally { closeSession(session); } } catch (Exception e) {
	 * LOGGER.error(
	 * "Error occured in BookingUniversalDAOImpl :: getConsgPrincingDtls()..:" +
	 * e.getMessage()); } return cnPricingDtls; }
	 */

	@Override
	public ConsignmentDO getConsingmentDtls(String consgNumner)
			throws CGSystemException {
		ConsignmentDO consg = null;
		Session session = null;
		try {
			session = createSession();
			consg = (ConsignmentDO) session
					.createCriteria(ConsignmentDO.class)
					.add(Restrictions.eq(
							UniversalBookingConstants.QRY_PARAM_CONSG2,
							consgNumner)).uniqueResult();
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingUniversalDAOImpl :: getConsingmentDtls()..:"
					+ e.getMessage());
			throw new CGSystemException(e);

		} finally {
			closeSession(session);
		}
		return consg;
	}

	@Override
	public List<ConsignmentDO> getConsingmentDtls(ConsignmentDO inputDO)
			throws CGSystemException {
		List<ConsignmentDO> outDOList = null;
		Session session = null;
		try {
			session = createSession();
			Criteria criteria = session.createCriteria(ConsignmentDO.class,
					"consigDo");
			if (!StringUtil.isEmptyInteger(inputDO.getConsgId())) {
				criteria.add(Restrictions.eq("consigDo.consgId",
						inputDO.getConsgId()));
			}
			if (!StringUtil.isStringEmpty(inputDO.getConsgNo())) {
				criteria.add(Restrictions.eq("consigDo.consgNo",
						inputDO.getConsgNo()));
			}
			if (!StringUtil.isEmptyInteger(inputDO.getOrgOffId())) {
				criteria.add(Restrictions.eq("consigDo.orgOffId",
						inputDO.getOrgOffId()));
			}
			if (inputDO.getConsgType() != null) {
				/** Sub query for Consignment type */
				DetachedCriteria subQuery = DetachedCriteria.forClass(
						ConsignmentTypeDO.class, "typeDO");
				subQuery.add(Restrictions.eq("typeDO.consignmentCode", inputDO
						.getConsgType().getConsignmentCode()));
				subQuery.add(Restrictions.eq("typeDO.consignmentName", inputDO
						.getConsgType().getConsignmentName()));
				subQuery.setProjection(Projections
						.property("typeDO.consignmentId"));
				criteria.add(Property.forName(
						"consigDo.consgType.consignmentId").in(subQuery));
			}
			outDOList = criteria.list();
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingUniversalDAOImpl :: getConsingmentDtls()..:"
					+ e.getMessage());
			throw new CGSystemException(e);

		} finally {
			closeSession(session);
		}
		return outDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ConsignmentDO getConsignmentDetails(ConsignmentTO consignmentTO)
			throws CGSystemException {
		LOGGER.debug("ConsignmentCommonDAOImpl :: getConsignmentDetails() :: Start --------> ::::::");
		ConsignmentDO consignmentDO = null;
		try {
			List<ConsignmentDO> consignmentDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UniversalBookingConstants.QRY_GET_CONSIGNMENT_DETAILS,
							new String[] { "consgNumber", "consgTypeCode", },
							new Object[] {
									consignmentTO.getConsgNo(),
									consignmentTO.getTypeTO()
											.getConsignmentCode() });
			if (!StringUtil.isEmptyColletion(consignmentDOs)) {
				consignmentDO = consignmentDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ConsignmentCommonDAOImpl::getConsignmentDetails() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ConsignmentCommonDAOImpl :: getConsignmentDetails() :: End --------> ::::::");
		return consignmentDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getConsignmentIdByConsgNo(ConsignmentTO consignmentTO)
			throws CGSystemException {
		LOGGER.debug("ConsignmentCommonDAOImpl :: getConsignmentIdByConsgNo() :: Start --------> ::::::");
		Integer consgId = null;
		try {
			List<Integer> consgIds = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UniversalBookingConstants.QRY_GET_CONSIGNMENT_ID_BY_CONSG_NO,
							"consgNumber", consignmentTO.getConsgNo());
			consgId = !StringUtil.isEmptyList(consgIds) ? consgIds.get(0)
					: null;

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ConsignmentCommonDAOImpl::getConsignmentIdByConsgNo() :: "
					+ e);
			// throw new CGSystemException(e);
			throw new CGSystemException(
					UniversalErrorConstants.CONSG_NOT_FOUND, e);
		}
		LOGGER.debug("ConsignmentCommonDAOImpl :: getConsignmentIdByConsgNo() :: End --------> ::::::");
		return consgId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getChildConsgIdByConsgNo(String consgNumber)
			throws CGSystemException {
		LOGGER.debug("ConsignmentCommonDAOImpl :: getChildConsgIdByConsgNo() :: Start --------> ::::::");
		Integer consgId = null;
		try {
			List<Integer> consgIds = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UniversalBookingConstants.QRY_GET_CHILD_CONSIGNMENT_ID_BY_CONSG_NO,
							"consgNumber", consgNumber);
			consgId = !StringUtil.isEmptyList(consgIds) ? consgIds.get(0)
					: null;

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ConsignmentCommonDAOImpl::getChildConsgIdByConsgNo() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ConsignmentCommonDAOImpl :: getChildConsgIdByConsgNo() :: End --------> ::::::");
		return consgId;
	}

	@Override
	public boolean updateConsignmentStatus(String consignmentStatus,
			String processCode, List<String> consgNumbers)
			throws CGSystemException {
		LOGGER.debug("ConsignmentCommonDAOImpl :: updateConsignmentStatus() :: Start --------> ::::::");
		boolean isUpdated = Boolean.FALSE;
		Session session = null;
		try {
			session = openTransactionalSession();
			Query query = session
					.getNamedQuery(UniversalBookingConstants.QRY_UPDATE_CONSIGNMENT_STATUS);
			query.setString(
					UniversalBookingConstants.QRY_PARAM_CONSIGNMENT_STATUS,
					consignmentStatus);
			query.setString(UniversalBookingConstants.QRY_PARAM_PROCESS_CODE,
					processCode);
			query.setParameterList(
					UniversalBookingConstants.QRY_PARAM_CONSIGNMENT_NOS,
					consgNumbers);
			query.executeUpdate();
			isUpdated = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ConsignmentCommonDAOImpl::updateConsignmentStatus() :: "
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
		}
		LOGGER.debug("ConsignmentCommonDAOImpl :: updateConsignmentStatus() :: End --------> ::::::");
		return isUpdated;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsignmentDO> getBookedConsignmentsByCustIdDateRange(
			final Integer customerId, Date startDate, Date endDate)
			throws CGSystemException {
		LOGGER.debug("ConsignmentCommonDAOImpl :: getBookedConsignmentsByCustIdDateRange() :: Start --------> ::::::");
		List<ConsignmentDO> consignmentDOs = null;
		try {
			consignmentDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UniversalBookingConstants.QRY_GET_BOOKED_CONSIGNMENTS_BY_DATE_RANGE,
							new String[] {
									UniversalBookingConstants.PARAM_CUSTOMER_ID,
									UniversalBookingConstants.PARAM_START_DATE,
									UniversalBookingConstants.PARAM_END_DATE },
							new Object[] { customerId, startDate, endDate });

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ConsignmentCommonDAOImpl::getBookedConsignmentsByCustIdDateRange() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ConsignmentCommonDAOImpl :: getBookedConsignmentsByCustIdDateRange() :: End --------> ::::::");
		return consignmentDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsignmentDO> getBookedTransferredConsignmentsByCustIdDateRange(
			final Integer customerId, Date startDate, Date endDate)
			throws CGSystemException {
		LOGGER.debug("ConsignmentCommonDAOImpl :: getBookedTransferredConsignmentsByCustIdDateRange() :: Start --------> ::::::");
		List<ConsignmentDO> consignmentDOs = null;
		try {
			consignmentDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UniversalBookingConstants.QRY_GET_BOOKED_TRANSFERRED_CONSIGNMENTS_BY_DATE_RANGE,
							new String[] {
									UniversalBookingConstants.PARAM_CUSTOMER_ID,
									UniversalBookingConstants.PARAM_START_DATE,
									UniversalBookingConstants.PARAM_END_DATE },
							new Object[] { customerId, startDate, endDate });

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ConsignmentCommonDAOImpl::getBookedTransferredConsignmentsByCustIdDateRange() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ConsignmentCommonDAOImpl :: getBookedTransferredConsignmentsByCustIdDateRange() :: End --------> ::::::");
		return consignmentDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ConsignmentDO getConsignmentById(Integer consignmentId)
			throws CGSystemException {
		LOGGER.debug("ConsignmentCommonDAOImpl :: getConsignmentById() :: Start --------> ::::::");
		ConsignmentDO consignmentDO = null;
		try {
			List<ConsignmentDO> consignmentDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getConsignmentById",
							"consgId", consignmentId);
			consignmentDO = !StringUtil.isEmptyList(consignmentDOs) ? consignmentDOs
					.get(0) : null;

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ConsignmentCommonDAOImpl::getConsignmentById() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ConsignmentCommonDAOImpl :: getConsignmentById() :: End --------> ::::::");
		return consignmentDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ConsignmentDOXDO getConsingmentByConsgNo(String consgNo)
			throws CGSystemException {
		LOGGER.debug("ConsignmentCommonDAOImpl :: getConsingmentByConsgNo() :: START --------> ::::::");
		ConsignmentDOXDO consgUpdateDO = null;
		Session session = null;
		try {
			session = openTransactionalSession();
			Criteria criteria = session.createCriteria(ConsignmentDOXDO.class);
			criteria.add(Restrictions.eq("consgNo", consgNo));
			List<ConsignmentDOXDO> consigUpdateDOs = (List<ConsignmentDOXDO>) criteria
					.list();
			if (!StringUtil.isEmptyColletion(consigUpdateDOs)
					&& consigUpdateDOs.size() > 0) {
				consgUpdateDO = consigUpdateDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurs in ConsignmentCommonDAOImpl :: getConsingmentByConsgNo() ::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
		}
		LOGGER.debug("ConsignmentCommonDAOImpl :: getConsingmentByConsgNo() :: END --------> ::::::");
		return consgUpdateDO;
	}

	@Override
	public List<ConsignmentDO> saveOrUpdateConsignments(
			List<ConsignmentDO> consignments) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ConsignmentDAOImpl::saveOrUpdateConsignments::START------------>:::::::");
		try {
			getHibernateTemplate().saveOrUpdateAll(consignments);
		} catch (Exception ex) {
			LOGGER.error(
					"ERROR : ConsignmentDAOImpl.saveOrUpdateConsignments()", ex);
			throw new CGSystemException(ex);
		}
		LOGGER.trace("ConsignmentDAOImpl::saveOrUpdateConsignments::END------------>:::::::");
		return consignments;
	}

	@Override
	public boolean isConsgNoManifestedForBooking(final String consigNum)
			throws CGSystemException {
		LOGGER.debug("ConsignmentDAOImpl :: isConsgNoManifestedForBooking() :: Start --------> ::::::");

		boolean isCNManifested = Boolean.FALSE;
		try {
			long count = (long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UniversalBookingConstants.QRY_IS_CONSGNO_MANIFESTED_FOR_BOOKING,
							new String[] { "consignmentNo" },
							new Object[] { consigNum }).get(0);

			if (count > 0)
				isCNManifested = Boolean.TRUE;

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ConsignmentDAOImpl::isConsgNoManifestedForBooking() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ConsignmentDAOImpl :: isConsgNoManifestedForBooking() :: End --------> ::::::");
		return isCNManifested;

	}

	@Override
	public void updateConsignment(ConsignmentDO consignmentDO)
			throws CGSystemException {
		LOGGER.debug("ConsignmentCommonDAOImpl :: updateConsignment() :: START ::::");
		try {
			getHibernateTemplate().merge(consignmentDO);
		} catch (Exception e) {
			LOGGER.error("ConsignmentCommonDAOImpl :: updateConsignment() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ConsignmentCommonDAOImpl :: updateConsignment() :: END ::::");
	}

	@SuppressWarnings("unchecked")
	@Override
	public ConsignmentDO getConsgDtlsByBookingRefNo(String bookingRefNo)
			throws CGSystemException {
		LOGGER.trace("ConsignmentCommonDAOImpl :: getConsgDtlsByBookingRefNo() :: START");
		ConsignmentDO consgDO = null;
		try {
			String[] params = { UniversalBookingConstants.QRY_PARAM_BOOKING_REF_NO };
			Object[] values = { bookingRefNo };
			String queryName = UniversalBookingConstants.QRY_GET_CONSG_DTLS_BY_BOOKING_REF_NO;
			List<ConsignmentDO> consgDOs = (List<ConsignmentDO>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);
			if (!CGCollectionUtils.isEmpty(consgDOs)) {
				consgDO = consgDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Error occured in ConsignmentCommonDAOImpl :: getConsgDtlsByBookingRefNo() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("ConsignmentCommonDAOImpl :: getConsgDtlsByBookingRefNo() :: END");
		return consgDO;
	}
	
	@Override
	public String getConsigneeMobileNumberByConsgNo(String consgNo,String bookingRefNumber)
			throws CGSystemException {
		LOGGER.trace("ConsignmentCommonDAOImpl :: getConsigneeMobileNumberByConsgNo() :: START");
		String mobileNumber = null;
		try {
			String[] params = { UniversalBookingConstants.QRY_PARAM_BOOKING_REF_NO,UniversalBookingConstants.QRY_PARAM_CONSG };
			Object[] values = { bookingRefNumber,consgNo };
			String queryName = UniversalBookingConstants.QRY_GET_CONSIGNEE_MOBILE_NUMBER;
			List<String> mobileNumberList =  getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);
			if (!CGCollectionUtils.isEmpty(mobileNumberList)) {
				mobileNumber = mobileNumberList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Error occured in getConsigneeMobileNumberByConsgNo :: getConsgDtlsByBookingRefNo() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("ConsignmentCommonDAOImpl :: getConsigneeMobileNumberByConsgNo() :: END");
		return mobileNumber;
	}
	@Override
	public String getConsignorMobileNumberByConsgNo(String consgNo,String bookingRefNumber)
			throws CGSystemException {
		LOGGER.trace("ConsignmentCommonDAOImpl :: getConsignorMobileNumberByConsgNo() :: START");
		String mobileNumber = null;
		try {
			String[] params = { UniversalBookingConstants.QRY_PARAM_BOOKING_REF_NO,UniversalBookingConstants.QRY_PARAM_CONSG };
			Object[] values = { bookingRefNumber,consgNo };
			String queryName = UniversalBookingConstants.QRY_GET_CONSIGNOR_MOBILE_NUMBER;
			List<String> mobileNumberList =  getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);
			if (!CGCollectionUtils.isEmpty(mobileNumberList)) {
				mobileNumber = mobileNumberList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Error occured in ConsignmentCommonDAOImpl :: getConsignorMobileNumberByConsgNo() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("ConsignmentCommonDAOImpl :: getConsignorMobileNumberByConsgNo() :: END");
		return mobileNumber;
	}
	

	@Override
	public void createConsignment(ConsignmentDO consignmentDO)
			throws CGSystemException {
		getHibernateTemplate().merge(consignmentDO);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Date getConsignmentDeliveryDate(String consigNo)
			throws CGSystemException {
		LOGGER.debug("ConsignmentCommonDAOImpl :: getConsignmentDeliveryDate()..::::  START ");
		Session session = null;
		Date consigDeliveryDate = null;
		try {
			session = createSession();

			Query query = session
					.createQuery("SELECT c.deliveredDate FROM ConsignmentDO c WHERE c.consgNo =:consgNo");
			query.setString("consgNo", consigNo);
			List consignments = query.list();
			consigDeliveryDate = !StringUtil.isEmptyList(consignments) ? (Date) query.list().get(0) : null;
		} catch (Exception e) {
			LOGGER.error("Error occured in ConsignmentCommonDAOImpl :: getConsignmentDeliveryDate()..:"
					+ e.getMessage());
			throw new CGSystemException(e);

		} finally {
			closeSession(session);
		}
		LOGGER.debug("ConsignmentCommonDAOImpl :: getConsignmentDeliveryDate()..::::  END ");
		return consigDeliveryDate;
	}

}
