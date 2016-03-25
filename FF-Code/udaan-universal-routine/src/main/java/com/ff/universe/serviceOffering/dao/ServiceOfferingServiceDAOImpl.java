package com.ff.universe.serviceOffering.dao;

/**
 * 
 */

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.locationSearch.LocationSearchDO;
import com.ff.domain.manifest.LoadLotDO;
import com.ff.domain.serviceOffering.BookingTypeProductMapDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.ConsignmentTypeConfigDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.IdentityProofTypeDO;
import com.ff.domain.serviceOffering.InsuranceConfigDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.domain.serviceOffering.PaymentModeDO;
import com.ff.domain.serviceOffering.PrivilegeCardDO;
import com.ff.domain.serviceOffering.PrivilegeCardTransactionDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.serviceOffering.ProductGroupServiceabilityDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.domain.serviceOffering.RelationDO;
import com.ff.domain.serviceOffering.RemarksDO;
import com.ff.domain.serviceability.PincodeBranchServiceabilityCityNameDO;
import com.ff.domain.serviceability.PincodeBranchServiceabilityDO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeConfigTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.universe.booking.constant.UniversalBookingConstants;
import com.ff.universe.serviceOffering.constant.UniversalServiceOfferingConstants;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;

/**
 * @author uchauhan
 * 
 */
public class ServiceOfferingServiceDAOImpl extends CGBaseDAO implements
		ServiceOfferingServiceDAO {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ServiceOfferingServiceDAOImpl.class);

	@Override
	public List<ConsignmentTypeDO> getConsignmentType()
			throws CGSystemException {
		List<ConsignmentTypeDO> consignmentTypeDOs = null;

		try {
			LOGGER.debug("ServiceOfferingServiceDAOImpl::getConsignmentType----------->end:::::::");
			String queryName = "getConsignmentType";
			consignmentTypeDOs = getHibernateTemplate().findByNamedQuery(
					queryName);

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getConsignmentType",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ServiceOfferingServiceDAOImpl::getConsignmentType----------->end:::::::");
		return consignmentTypeDOs;
		// TODO Auto-generated method stub

	}

	@Override
	public List<PaymentModeDO> getPaymentMode() throws CGSystemException {

		try {
			List<PaymentModeDO> paymentModeDOs = null;
			String queryName = "getPaymentDetails";
			paymentModeDOs = getHibernateTemplate().findByNamedQuery(queryName);
			return paymentModeDOs;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getPaymentMode", e);
			throw new CGSystemException(e);
		}
	}

	public PaymentModeDO getPaymentMode(Integer paymentId)
			throws CGSystemException {
		List<PaymentModeDO> paymentModeDOs = null;
		PaymentModeDO paymentMode = null;
		try {

			String queryName = "getPaymentDetailsById";
			paymentModeDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "paymentId",
							paymentId);
			if (paymentModeDOs != null && !paymentModeDOs.isEmpty()) {
				paymentMode = paymentModeDOs.get(0);

			}
			return paymentMode;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getPaymentMode", e);
			throw new CGSystemException(e);
		}
	}

	@Override
	public List<CNContentDO> getContentValues() throws CGSystemException {

		try {
			List<CNContentDO> cnContentDOs = null;
			String queryName = UniversalBookingConstants.GET_CONTENT_VALUES;
			cnContentDOs = getHibernateTemplate().findByNamedQuery(queryName);
			return cnContentDOs;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getPaymentMode", e);
			throw new CGSystemException(e);
		}
	}

	@Override
	public List<CNPaperWorksDO> getPaperWorks(
			CNPaperWorksTO paperWorkValidationTO) throws CGSystemException {
		try {
			List<CNPaperWorksDO> cnPaperWorksDOs = null;
			String pincode = paperWorkValidationTO.getPincode();
			String queryName = UniversalBookingConstants.GET_PAPER_WORKS;
			cnPaperWorksDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "pincode",
							pincode);
			return cnPaperWorksDOs;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getPaymentMode", e);
			throw new CGSystemException(e);
		}
	}

	@Override
	public CNPaperWorksDO getPaperWorkByPincode(String pincode,
			String paperWorkName) throws CGSystemException {
		List<CNPaperWorksDO> cnPaperWorksDOs = null;
		CNPaperWorksDO paperworkDO = null;
		try {
			String queryName = UniversalBookingConstants.GET_PAPER_WORK_BY_PIN;
			String[] params = { "pincode", "paperWorkName" };
			Object[] values = { pincode, paperWorkName };
			cnPaperWorksDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);
			if (!StringUtil.isEmptyList(cnPaperWorksDOs))
				paperworkDO = cnPaperWorksDOs.get(0);

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getPaymentMode", e);
			throw new CGSystemException(e);
		}
		return paperworkDO;
	}

	@Override
	public CNContentDO getCNContentByName(String cnContentName)
			throws CGSystemException {
		CNContentDO content = null;
		Session session = null;
		try {
			LOGGER.debug("ServiceOfferingServiceDAOImpl::getCNContentByName----------->Start:::::::");
			session = createSession();
			content = (CNContentDO) session.createCriteria(CNContentDO.class)
					.add(Restrictions.eq("cnContentName", cnContentName))
					.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getCNContentByName",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ServiceOfferingServiceDAOImpl::getCNContentByName----------->END:::::::");
		return content;
	}

	public ProductDO getProductByConsgSeries(String consgSeries)
			throws CGSystemException {
		ProductDO product = null;
		Session session = null;
		try {
			if (StringUtil.isStringEmpty(consgSeries)) {
				return product;
			}

			if (Character.isDigit(consgSeries.charAt(0)))
				consgSeries = "N";

			session = createSession();
			product = (ProductDO) session.createCriteria(ProductDO.class)
					.add(Restrictions.eq("consgSeries", consgSeries))
					.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getProductByConsgSeries",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return product;
	}

	public List<BookingTypeProductMapDO> getAllBookingProductMapping(
			String bookingType) throws CGSystemException {
		List<BookingTypeProductMapDO> products = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = createSession();
			criteria = session.createCriteria(BookingTypeProductMapDO.class);
			if (StringUtils.isNotEmpty(bookingType)) {
				criteria.createAlias("bookingType", "bookingType");
				criteria.add(Restrictions.eq("bookingType.bookingType",
						bookingType));
			}
			products = (List<BookingTypeProductMapDO>) criteria.list();

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getAllProducts", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return products;
	}

	@SuppressWarnings("unchecked")
	public BookingTypeProductMapDO getBookingProductMapping(String bookingType)
			throws CGSystemException {
		List<BookingTypeProductMapDO> bookingTypeProductMapDOs = null;
		BookingTypeProductMapDO bookingTypeProductMapDO = null;
		try {
			bookingTypeProductMapDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UniversalBookingConstants.QRY_GET_BOOKING_PRODUCT_MAPPINGS,
							UniversalBookingConstants.PARAM_BOOKING_TYPE,
							bookingType);
			bookingTypeProductMapDO = !StringUtil
					.isEmptyList(bookingTypeProductMapDOs) ? bookingTypeProductMapDOs
					.get(0) : null;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getProductByConsgSeries",
					e);
			throw new CGSystemException(e);
		}
		return bookingTypeProductMapDO;
	}

	public boolean isProductServicedByBooking(String bookingType,
			String congSeries) throws CGSystemException {
		long count = 0;
		boolean isProductServiced = Boolean.FALSE;
		try {
			String[] params = { UniversalBookingConstants.PARAM_BOOKING_TYPE,
					UniversalBookingConstants.PARAM_CONSG_SERIES };
			String[] values = { bookingType, congSeries };
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UniversalBookingConstants.QRY_IS_PRODUCT_SERVICED_BY_BOOKING,
							params, values).get(0);
			if (count > 0)
				isProductServiced = Boolean.TRUE;

		} catch (Exception e) {
			LOGGER.error("Error occured in BookingCommonDAOImpl :: isConsgBooked()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return isProductServiced;
	}

	public boolean isNormalProductServicedByBooking(String bookingType,
			String prodCode) throws CGSystemException {
		long count = 0;
		boolean isProductServiced = Boolean.FALSE;
		try {
			String[] params = { UniversalBookingConstants.PARAM_BOOKING_TYPE,
					UniversalBookingConstants.QRY_PARAM_PROD_CODE };
			String[] values = { bookingType, prodCode };
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UniversalBookingConstants.QRY_IS_NORMAL_PRODUCT_SERVICED_BY_BOOKING,
							params, values).get(0);
			if (count > 0)
				isProductServiced = Boolean.TRUE;

		} catch (Exception e) {
			LOGGER.error("Error occured in BookingCommonDAOImpl :: isNormalProductServicedByBooking()..:"
					+ e.getMessage());
		}
		return isProductServiced;
	}

	@SuppressWarnings("unchecked")
	public PrivilegeCardDO getPrivilegeCardDtls(String privilegeCardNo)
			throws CGSystemException {
		PrivilegeCardDO privgCardDtls = null;
		Session session = null;
		try {
			session = createSession();
			privgCardDtls = (PrivilegeCardDO) session
					.createCriteria(PrivilegeCardDO.class)
					.add(Restrictions.eq("privilegeCardNo", privilegeCardNo))
					.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getProductByConsgSeries",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return privgCardDtls;
	}

	@SuppressWarnings("unchecked")
	public List<PrivilegeCardTransactionDO> getPrivilegeCardTransDtls(
			String privilegeCardNo) throws CGSystemException {
		List<PrivilegeCardTransactionDO> privgCardTransDOs = null;
		try {
			privgCardTransDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UniversalBookingConstants.QRY_GET_PRIVILEGE_CARD_TRANS_DTLS,
							UniversalBookingConstants.PARAM_PRIVILEGE_CARD_NO,
							privilegeCardNo);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getPrivilegeCardTransDtls",
					e);
			throw new CGSystemException(e);
		}
		return privgCardTransDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InsuredByDO> getInsuredBy() throws CGSystemException {
		List<InsuredByDO> insuranceConfigDOs = null;
		try {
			String queryName = UniversalBookingConstants.GET_INSURED_BY;
			insuranceConfigDOs = getHibernateTemplate().findByNamedQuery(
					queryName);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getInsuarnceBy", e);
			throw new CGSystemException(e);
		}
		return insuranceConfigDOs;
	}

	@Override
	public List<InsuranceConfigDO> getInsuarnceConfigDtls(Double declaredValue,
			String bookingType) throws CGSystemException {
		List<InsuranceConfigDO> insurenceConfigDtls = null;
		try {
			String queryName = UniversalBookingConstants.GET_INSURENCE_CONFIG_DTLS;
			String params[] = { "bookingType", "declaredValue" };
			Object[] values = { bookingType, declaredValue };
			insurenceConfigDtls = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getInsuarnceConfigDtls",
					e);
			throw new CGSystemException(e);
		}
		return insurenceConfigDtls;
	}

	@Override
	public InsuranceConfigDO validateInsuarnceConfigDtls(Double declaredValue,
			String bookingType, Integer insuredById) throws CGSystemException {
		List<InsuranceConfigDO> insurenceConfigDtls = null;
		InsuranceConfigDO insurenceConfigDO = null;
		try {
			String params[] = { "bookingType", "declaredValue", "insuredById" };
			Object[] values = { bookingType, declaredValue, insuredById };
			insurenceConfigDtls = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							"validatensurenceConfigDtls", params, values);
			if (!StringUtil.isEmptyList(insurenceConfigDtls))
				insurenceConfigDO = insurenceConfigDtls.get(0);

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getInsuarnceConfigDtls",
					e);
			throw new CGSystemException(e);
		}
		return insurenceConfigDO;
	}

	public ConsignmentTypeConfigDO getConsgTypeConfigDtls(
			ConsignmentTypeConfigTO consgTypeConfigTO) throws CGSystemException {
		ConsignmentTypeConfigDO consgTypeConfigDO = null;
		Session session = null;
		try {
			session = createSession();
			consgTypeConfigDO = (ConsignmentTypeConfigDO) session
					.createCriteria(ConsignmentTypeConfigDO.class)
					.createAlias("consignmentId", "consgType")
					.add(Restrictions.eq("consgType.consignmentCode",
							consgTypeConfigTO.getDocType())).uniqueResult();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getConsgTypeConfigDtls",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return consgTypeConfigDO;
	}

	@Override
	public List<ConsignmentTypeConfigDO> getConsgTypeConfigDtlsByConsgConfigType(
			ConsignmentTypeConfigTO consgTypeConfigTO) throws CGSystemException {
		List<ConsignmentTypeConfigDO> consgTypeConfigDO = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = createSession();
			criteria = session.createCriteria(ConsignmentTypeConfigDO.class);
			if (!StringUtil.isStringEmpty(consgTypeConfigTO.getDocType())) {
				criteria.createAlias("consignmentId", "consgType").add(
						Restrictions.eq("consgType.consignmentCode",
								consgTypeConfigTO.getDocType()));
			}
			consgTypeConfigDO = criteria.list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getConsgTypeConfigDtlsByConsgConfigType",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return consgTypeConfigDO;
	}

	public List<ConsignmentTypeDO> getConsignmentTypes(
			ConsignmentTypeDO consgTypeDO) throws CGSystemException {
		List<ConsignmentTypeDO> consignmentTypeDOs = null;
		Session session = null;
		Criteria creteria = null;
		try {
			session = createSession();
			creteria = session.createCriteria(ConsignmentTypeDO.class);
			if (StringUtils.isNotEmpty(consgTypeDO.getConsignmentCode()))
				creteria.add(Restrictions.eq("consignmentCode",
						consgTypeDO.getConsignmentCode()));
			else if (StringUtils.isNotEmpty(consgTypeDO.getConsignmentName()))
				creteria.add(Restrictions.eq("consignmentName",
						consgTypeDO.getConsignmentName()));
			consignmentTypeDOs = (List<ConsignmentTypeDO>) creteria.list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getConsignmentTypes",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return consignmentTypeDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public InsuredByDO getInsuredByNameOrCode(String insuredByName,
			String insuredByCode, Integer insuredById) throws CGSystemException {
		InsuredByDO insuredBy = null;
		Session session = null;
		Criteria creteria = null;
		try {
			session = createSession();
			creteria = session.createCriteria(InsuredByDO.class);
			if (!StringUtil.isNull(insuredByName)
					&& StringUtils.isNotEmpty(insuredByName))
				creteria.add(Restrictions.eq("insuredByDesc", insuredByName));
			if (!StringUtil.isNull(insuredByCode)
					&& StringUtils.isNotEmpty(insuredByCode))
				creteria.add(Restrictions.eq("insuredByCode", insuredByCode));
			if (!StringUtil.isEmptyInteger(insuredById))
				creteria.add(Restrictions.eq("insuredById", insuredById));
			insuredBy = (InsuredByDO) creteria.uniqueResult();
		} catch (Exception e) {
			LOGGER.error("ERROR : ServiceOfferingServiceDAOImpl.getInsuredBy",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return insuredBy;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ConsignmentTypeDO getConsgType(ConsignmentTypeTO consignmentTypeTO)
			throws CGSystemException {
		ConsignmentTypeDO consignmentTypeDO = null;
		Session session = null;
		Criteria creteria = null;
		try {
			session = createSession();
			creteria = session.createCriteria(ConsignmentTypeDO.class);
			if (StringUtils.isNotBlank(consignmentTypeTO.getConsignmentCode())) {
				creteria.add(Restrictions.eq("consignmentCode",
						consignmentTypeTO.getConsignmentCode()));
			} else if (StringUtils.isNotBlank(consignmentTypeTO
					.getConsignmentName())) {
				creteria.add(Restrictions.eq("consignmentName",
						consignmentTypeTO.getConsignmentName()));
			}
			List<ConsignmentTypeDO> consignmentTypeDOs = (List<ConsignmentTypeDO>) creteria
					.list();

			if (!StringUtil.isEmptyColletion(consignmentTypeDOs)) {
				consignmentTypeDO = consignmentTypeDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : ServiceOfferingServiceDAOImpl.getConsgType",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return consignmentTypeDO;
	}

	@Override
	public List<?> getStandardTypesAsMap(String typeName)
			throws CGSystemException {
		List<?> numberList = null;
		Object[] values = null;
		if (!StringUtil.isStringEmpty(typeName)
				&& typeName.contains(FrameworkConstants.CHARACTER_COMMA)) {
			values = typeName.split(FrameworkConstants.CHARACTER_COMMA);
		} else {
			values = new Object[] { typeName };
		}

		Session session = null;
		try {
			session = createSession();
			Query query = session
					.getNamedQuery(StockUniveralConstants.GET_STOCK_STD_TYPE_BY_TYPE_NAME_MAP);
			query.setParameterList(StockUniveralConstants.QRY_PARAM_TYPE_NAME,
					values);
			numberList = query.list();
		} finally {
			closeSession(session);
		}

		return numberList;
	}

	@Override
	public List<LoadLotDO> getLoadNo() throws CGSystemException {
		List<LoadLotDO> loadLotDOList = null;
		try {
			loadLotDOList = getHibernateTemplate()
					.findByNamedQuery("getLoadNo");
		} catch (Exception e) {
			LOGGER.error("ServiceOfferingServiceDAOImpl :: getLoadNo()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return loadLotDOList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.serviceOffering.dao.ServiceOfferingServiceDAO#
	 * getReasonsByReasonType(com.ff.to.serviceofferings.ReasonTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReasonDO> getReasonsByReasonType(ReasonTO reasonTO)
			throws CGSystemException {
		List<ReasonDO> reasonDOs = null;
		try {
			reasonDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UniversalServiceOfferingConstants.QRY_GET_REASONS_BY_REASON_TYPE,
							UniversalServiceOfferingConstants.PARAM_REASON_TYPE,
							reasonTO.getReasonType());
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getReasonsByReasonType",
					e);
			throw new CGSystemException(e);
		}
		return reasonDOs;
	}

	/**
	 * Gets the all relations.
	 * 
	 * @param relationDO
	 *            the relation do
	 * @return the all relations
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RelationDO> getAllRelations(RelationDO relationDO)
			throws CGSystemException {
		List<RelationDO> reasonDOs = null;
		Session session = null;
		Criteria creteria = null;
		try {
			session = createSession();
			creteria = session.createCriteria(RelationDO.class, "relation");
			if (!StringUtil.isEmptyInteger(relationDO.getRelationId())) {
				creteria.add(Restrictions.eq("relation.relationId",
						relationDO.getRelationId()));
			}
			if (!StringUtil.isStringEmpty(relationDO.getRelationCode())) {
				creteria.add(Restrictions.eq("relation.relationCode",
						relationDO.getRelationCode()));
			}
			creteria.add(Restrictions.eq("relation.isActive",
					UniversalServiceOfferingConstants.IS_ACTIVE_YES));
			reasonDOs = creteria.list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl:::getAllRelations :::Exception",
					e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		return reasonDOs;
	}

	/**
	 * Gets the all identity proofs.
	 * 
	 * @param idProofDO
	 *            the id proof do
	 * @return the all identity proofs
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IdentityProofTypeDO> getAllIdentityProofs(
			IdentityProofTypeDO idProofDO) throws CGSystemException {
		List<IdentityProofTypeDO> idProofTypeDOlist = null;
		Session session = null;
		Criteria creteria = null;
		try {
			session = createSession();
			creteria = session.createCriteria(IdentityProofTypeDO.class,
					"idproof");
			if (!StringUtil.isEmptyInteger(idProofDO.getIdentityProofTypeId())) {
				creteria.add(Restrictions.eq("idproof.identityProofTypeId",
						idProofDO.getIdentityProofTypeId()));
			}
			if (!StringUtil.isStringEmpty(idProofDO.getIdentityProofTypeCode())) {
				creteria.add(Restrictions.eq("idproof.identityProofTypeCode",
						idProofDO.getIdentityProofTypeCode()));
			}
			creteria.add(Restrictions.eq("idproof.isActive",
					UniversalServiceOfferingConstants.IS_ACTIVE_YES));
			idProofTypeDOlist = creteria.list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl:::getAllIdentityProofs :::Exception",
					e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		return idProofTypeDOlist;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PaymentModeDO> getPaymentModeDtls(String processCode)
			throws CGSystemException {
		List<PaymentModeDO> paymentModeDOs = null;
		try {
			String queryName = "getPaymentModeDtls";
			String params[] = { "processCode", "status" };
			Object values[] = { processCode, "A" };
			paymentModeDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getPaymentModeDtls",
					e);
			throw new CGSystemException(e);
		}
		return paymentModeDOs;
	}

	@Override
	@SuppressWarnings("unchecked")
	public PaymentModeDO getPaymentMode(String processCode, String payModeCode)
			throws CGSystemException {
		List<PaymentModeDO> paymentModeDOs = null;
		PaymentModeDO paymentModeDO = null;
		try {
			String queryName = "getPaymentMode";
			String params[] = { "processCode", "status", "payModeCode" };
			Object values[] = { processCode, "A", payModeCode };
			paymentModeDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);
			paymentModeDO = !CGCollectionUtils.isEmpty(paymentModeDOs) ? paymentModeDOs
					.get(0) : null;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getPaymentModeDtls",
					e);
			throw new CGSystemException(e);
		}
		return paymentModeDO;
	}

	@Override
	public CNContentDO getCNContentByCodeOrId(Integer contentId,
			String contentCode) throws CGSystemException {
		CNContentDO content = null;
		Session session = null;
		Criteria creteria = null;
		try {
			session = createSession();
			creteria = session.createCriteria(CNContentDO.class);
			if (!StringUtil.isEmptyInteger(contentId))
				creteria.add(Restrictions.eq("cnContentId", contentId));
			else if (StringUtils.isNotEmpty(contentCode))
				creteria.add(Restrictions.eq("cnContentCode", contentCode));
			content = (CNContentDO) creteria.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getCNContentByCodeOrId",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		return content;
	}

	public CNPaperWorksDO getPaperWorkByCodeOrId(Integer paperWorkId,
			String paperWorkCode) throws CGSystemException {
		CNPaperWorksDO paperwork = null;
		Session session = null;
		Criteria creteria = null;
		try {
			session = createSession();
			creteria = session.createCriteria(CNPaperWorksDO.class);
			if (!StringUtil.isEmptyInteger(paperWorkId))
				creteria.add(Restrictions.eq("cnPaperWorkId", paperWorkId));
			else if (StringUtils.isNotEmpty(paperWorkCode))
				creteria.add(Restrictions.eq("cnPaperWorkCode", paperWorkCode));
			paperwork = (CNPaperWorksDO) creteria.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getPaperWorkByCodeOrId",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return paperwork;
	}

	@Override
	public List<ProductDO> getAllProducts() throws CGSystemException {
		List<ProductDO> products = null;
		Session session = null;
		try {
			session = createSession();
			/*
			 * products = (List<ProductDO>) session
			 * .createCriteria(ProductDO.class).list();
			 */
			products = session.createCriteria(ProductDO.class)
					.addOrder(Order.asc("productName")).list();

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getAllProducts", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return products;
	}

	@Override
	public List<ProductDO> getProductByProduct(ProductDO productDO)
			throws CGSystemException {
		List<ProductDO> products = null;
		Session session = null;
		Criteria creteria = null;
		try {
			session = createSession();
			creteria = session.createCriteria(ProductDO.class);
			if (!StringUtil.isEmptyInteger(productDO.getProductId())) {
				creteria.add(Restrictions.eq("productId",
						productDO.getProductId()));
			}
			if (!StringUtil.isStringEmpty(productDO.getProductCode())) {
				creteria.add(Restrictions.eq("productCode",
						productDO.getProductCode()));
			}
			if (!StringUtil.isStringEmpty(productDO.getConsgSeries())) {
				creteria.add(Restrictions.eq("consgSeries",
						productDO.getConsgSeries()));
			}
			creteria.add(Restrictions.eq("status", "A"));
			products = creteria.list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getAllProducts", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return products;
	}

	@Override
	public List<ProductGroupServiceabilityDO> getAllProductGroup()
			throws CGSystemException {
		List<ProductGroupServiceabilityDO> serviceabilityDOs = null;
		Session session = null;
		try {
			session = createSession();
			serviceabilityDOs = (List<ProductGroupServiceabilityDO>) session
					.createCriteria(ProductGroupServiceabilityDO.class).list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getAllProducts", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return serviceabilityDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RemarksDO> getRemarksByType(String remarkType)
			throws CGSystemException {
		LOGGER.trace("ServiceOfferingServiceDAOImpl::getRemarksByType :: START");
		List<RemarksDO> remarksDOs = null;
		Session session = null;
		Criteria creteria = null;
		try {
			session = createSession();
			creteria = session.createCriteria(RemarksDO.class);
			creteria.add(Restrictions.eq("remarkType", remarkType));
			creteria.add(Restrictions.eq("status",
					CommonConstants.REMARKS_STATUS_ACTIVE));
			remarksDOs = creteria.list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getRemarksByType", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("ServiceOfferingServiceDAOImpl::getRemarksByType :: END");
		return remarksDOs;
	}

	@Override
	public PrivilegeCardTransactionDO getprivilegeCardDtls(String consgNo)
			throws CGSystemException {
		LOGGER.trace("ServiceOfferingServiceDAOImpl::getprivilegeCardDtls :: START");
		PrivilegeCardTransactionDO transactionDO = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = createSession();
			criteria = session.createCriteria(PrivilegeCardTransactionDO.class);
			criteria.add(Restrictions.eq("consgNumber", consgNo));
			transactionDO = (PrivilegeCardTransactionDO) criteria
					.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getprivilegeCardDtls",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("ServiceOfferingServiceDAOImpl::getprivilegeCardDtls :: END");
		return transactionDO;
	}

	@Override
	public ProductDO getProductByProductId(Integer productId)
			throws CGSystemException {
		LOGGER.debug("ServiceOfferingServiceDAOImpl :: getProductByProductId() :: Start --------> ::::::");
		ProductDO productDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(ProductDO.class, "product");
			cr.add(Restrictions.eq("product.productId", productId));
			List<ProductDO> productDOs = (List<ProductDO>) cr.list();
			if (!StringUtil.isEmptyColletion(productDOs)) {
				productDO = productDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ServiceOfferingServiceDAOImpl :: getProductByProductId()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ServiceOfferingServiceDAOImpl: getProductByProductId(): END");
		return productDO;
	}

	@Override
	public List<PincodeBranchServiceabilityDO> getAllServicingOfficebyPincode(
			String pincode) throws CGSystemException {
		List<PincodeBranchServiceabilityDO> pincodeBranchServiceabilityDOs = null;
		try {
			String queryName = "getPincodeServicingBranch";
			String params = "pincode";
			// pincodeBranchServiceabilityDOs = getHibernateTemplate()
			// .findByNamedQueryAndNamedParam(queryName, params, values);
			pincodeBranchServiceabilityDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, pincode);
			// pincodeBranchServiceabilityDO =
			// !CGCollectionUtils.isEmpty(pincodeBranchServiceabilityDOs) ?
			// pincodeBranchServiceabilityDOs
			// .get(0) : null;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getAllServicingOfficebyPincode",
					e);
			throw new CGSystemException(e);
		}
		return pincodeBranchServiceabilityDOs;
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<PincodeBranchServiceabilityCityNameDO> getAllServicingOfficebyPincodeCity(
			String pincode) throws CGSystemException {
	
		LOGGER.debug("ServiceOfferingServiceDAOImpl::getAllServicingOfficebyPincodeCity::START::" + System.currentTimeMillis());
		
		Session session = null;
		List<PincodeBranchServiceabilityCityNameDO> list = null;
		
		try {
			session = createSession();
			
			Query query = session.createSQLQuery(UniversalServiceOfferingConstants.QRY_SEARCH_PINCODE_INFO)
			.addScalar("pincode", StandardBasicTypes.STRING)
			.addScalar("cityName", StandardBasicTypes.STRING)
			.addScalar("stateName", StandardBasicTypes.STRING)
			.addScalar("regionName", StandardBasicTypes.STRING)
			.addScalar("officeName", StandardBasicTypes.STRING)
			.addScalar("address1", StandardBasicTypes.STRING)
			.addScalar("address2", StandardBasicTypes.STRING)
			.addScalar("email", StandardBasicTypes.STRING)
			.addScalar("phone", StandardBasicTypes.STRING)
			.addScalar("servStatus", StandardBasicTypes.STRING)
			.setResultTransformer(
					Transformers.aliasToBean(PincodeBranchServiceabilityCityNameDO.class));
			
			list = query.setParameter("pincode", pincode).list();
			LOGGER.debug("ServiceOfferingServiceDAOImpl::getAllServicingOfficebyPincodeCity::LIST SIZE = " + list.size());
			
		} catch (Exception exception) {
			LOGGER.error("ERROR : ServiceOfferingServiceDAOImpl.getAllServicingOfficebyPincodeCity",exception);
		} finally {
			closeSession(session);
		}
		
		LOGGER.debug("ServiceOfferingServiceDAOImpl::getAllServicingOfficebyPincodeCity::END::" + System.currentTimeMillis());
					
		//List list = null;
		/*Query query = this
				.getSession()
				.createSQLQuery(
						UniversalServiceOfferingConstants.QRY_SEARCH_PINCODE_INFO)
				.addScalar("pincode", StandardBasicTypes.STRING)
				.addScalar("cityName", StandardBasicTypes.STRING)
				.addScalar("stateName", StandardBasicTypes.STRING)
				.addScalar("regionName", StandardBasicTypes.STRING)
				.addScalar("officeName", StandardBasicTypes.STRING)
				.addScalar("address1", StandardBasicTypes.STRING)
				.addScalar("address2", StandardBasicTypes.STRING)
				.addScalar("email", StandardBasicTypes.STRING)
				.addScalar("phone", StandardBasicTypes.STRING)
				.addScalar("servStatus", StandardBasicTypes.STRING)
				.setResultTransformer(
						Transformers.aliasToBean(PincodeBranchServiceabilityCityNameDO.class));

		List list = query.setParameter("pincode", pincode).list();
		
		LOGGER.trace("ServiceOfferingServiceDAOImpl::getServicingLocationDetails::LIST SIZE = " + list.size());
		LOGGER.debug("ServiceOfferingServiceDAOImpl::getServicingLocationDetails::END------------>");*/
		return list;
	}
	
	@Override
	public List<PincodeBranchServiceabilityDO> getAllServicingOfficebyBranch(
			String officeCode) throws CGSystemException {
		List<PincodeBranchServiceabilityDO> pincodeBranchServiceabilityDOs = null;
		try {
			String queryName = "getBranchServicingPincode";
			String params = "officeCode";
			// pincodeBranchServiceabilityDOs = getHibernateTemplate()
			// .findByNamedQueryAndNamedParam(queryName, params, values);
			pincodeBranchServiceabilityDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params,
							officeCode);
			// pincodeBranchServiceabilityDO =
			// !CGCollectionUtils.isEmpty(pincodeBranchServiceabilityDOs) ?
			// pincodeBranchServiceabilityDOs
			// .get(0) : null;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getAllServicingOfficebyBranch",
					e);
			throw new CGSystemException(e);
		}
		return pincodeBranchServiceabilityDOs;
	}

	@Override
	public List<PincodeBranchServiceabilityDO> getAllPincodesByOfficeId(
			Integer officeId) throws CGSystemException {
		List<PincodeBranchServiceabilityDO> pincodeBranchServiceabilityDOs = null;
		try {
			String queryName = "getPincodeDetails";
			String params = "officeId";
			pincodeBranchServiceabilityDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, officeId);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getAllPincodesByOfficeId",
					e);
			throw new CGSystemException(e);
		}
		return pincodeBranchServiceabilityDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getServicingLocationSearch() throws CGSystemException {

		LOGGER.debug("ServiceOfferingServiceDAOImpl::getServicingLocationSearch::START--------------->");
		List<String> locationList = null;
		Session session = null;

		try {
			String queryName = "getPincodeAndCityListForLocationSearch";
			session = getHibernateTemplate().getSessionFactory().openSession();
			locationList = session.getNamedQuery(queryName).list();
			
			LOGGER.trace("ServiceOfferingServiceDAOImpl::getServicingLocationSearch::locationList Size = " + locationList.size());
			
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getServicingLocationSearch",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ServiceOfferingServiceDAOImpl::getServicingLocationSearch::START--------------->");
		return locationList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LocationSearchDO> getServicingLocationDetails(String location,
			String cityName, OfficeTO loggedInOffice) throws CGSystemException {

		LOGGER.debug("ServiceOfferingServiceDAOImpl::getServicingLocationDetails::START------------>");
		LOGGER.trace("ServiceOfferingServiceDAOImpl::getServicingLocationDetails::PARAMETERS::location = "
				+ location
				+ "::cityName = "
				+ cityName
				+ "::loggedInOffice = "
				+ loggedInOffice);

		List<LocationSearchDO> list = null;
		Session session = null;
		
		try {
			session = createSession();
			Query query = session.createSQLQuery(UniversalServiceOfferingConstants.QRY_LOCATION_SEARCH_INFO)
					.addScalar("pincodeMapped", StandardBasicTypes.STRING)
					.addScalar("productMapped", StandardBasicTypes.STRING)
					.addScalar("officeName", StandardBasicTypes.STRING)
					.setResultTransformer(
							Transformers.aliasToBean(LocationSearchDO.class));
			list = query.setParameter("location", location)
					.setParameter("cityName", cityName)
					.setParameter("loggedInOffice", loggedInOffice.getOfficeId())
					.list();
			
			LOGGER.trace("ServiceOfferingServiceDAOImpl::getServicingLocationDetails::LIST SIZE = "
					+ list.size());
			
		} catch (Exception exception) {
			LOGGER.error("ERROR : ServiceOfferingServiceDAOImpl.getAllServicingOfficebyPincodeCity",exception);
			throw new CGSystemException(exception);
		} finally {
			closeSession(session);
		}
		
		/*
		Query query = this
				.getSession()
				.createSQLQuery(
						UniversalServiceOfferingConstants.QRY_LOCATION_SEARCH_INFO)
				.addScalar("pincodeMapped", StandardBasicTypes.STRING)
				.addScalar("productMapped", StandardBasicTypes.STRING)
				.addScalar("officeName", StandardBasicTypes.STRING)
				.setResultTransformer(
						Transformers.aliasToBean(LocationSearchDO.class));

		list = query.setParameter("location", location)
				.setParameter("cityName", cityName)
				.setParameter("loggedInOffice", loggedInOffice.getOfficeId())
				.list();*/

		LOGGER.debug("ServiceOfferingServiceDAOImpl::getServicingLocationDetails::END------------>");
		
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ConsignmentTypeDO getConsignmentTypeByCode(String consignmentTypeCode)
			throws CGSystemException {
		LOGGER.debug("ServiceOfferingServiceDAOImpl :: getConsignmentTypeByCode() :: Start --------> ::::::");
		ConsignmentTypeDO cTypeDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(ConsignmentTypeDO.class, "consignmentTypeDO");
			cr.add(Restrictions.eq("consignmentTypeDO.consignmentCode", consignmentTypeCode));
			List<ConsignmentTypeDO> cTypeDOs = (List<ConsignmentTypeDO>) cr.list();
			if (!StringUtil.isEmptyColletion(cTypeDOs)) {
				cTypeDO = cTypeDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ServiceOfferingServiceDAOImpl :: getConsignmentTypeByCode()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ServiceOfferingServiceDAOImpl: getConsignmentTypeByCode(): END");
		return cTypeDO;
	
	}

}
