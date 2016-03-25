package com.ff.admin.billing.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.billing.constants.BillingConstants;
import com.ff.business.CustomerTypeTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingModifiedDO;
import com.ff.domain.booking.BookingTypeDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.consignment.ConsignmentModifiedDO;
import com.ff.domain.mec.SAPLiabilityEntriesDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.to.billing.CustModificationAliasTO;

public class CnModificationCommonDAOImpl extends CGBaseDAO implements CnModificationCommonDAO {
	private final static Logger LOGGER = LoggerFactory.getLogger(CnModificationCommonDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public String getBookingTypeByCustcode(String customer_code)
			throws CGSystemException {
		LOGGER.debug("CustModificationDAOImpl: getBookingTypeByCustcode(): START");
		String queryName = null;
		Session session = null;
		queryName = BillingConstants.QRY_BOOKINGTYPE_NEW_CUSTCODE;
		String custType = null;
		List<CustomerTypeTO> customerTypeCode = null;
		try {
			session = createSession();
			customerTypeCode = session
					.createSQLQuery(queryName)
					.addScalar("customerTypeCode")
					.setParameter(BillingConstants.NEW_CUST_CODE, customer_code)
					.setResultTransformer(
							Transformers.aliasToBean(CustomerTypeTO.class))
					.list();
			if (!StringUtil.isNull(customerTypeCode)
					&& customerTypeCode.size() > 0) {
				custType = customerTypeCode.get(0).getCustomerTypeCode();
			}
		} catch (Exception e) {
			logger.error(
					"CustModificationDAOImpl::getBookingTypeByCustcode :: Exception",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("CustModificationDAOImpl: getBookingTypeByCustcode(): End");
		return custType;
	}

	@Override
	public ConsignmentDO getConsignmentDetails(String consgNo)
			throws CGSystemException {
		LOGGER .debug("CustModificationDAOImpl: getConsgDetailsForModification(): START");
		ConsignmentDO consgDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(ConsignmentDO.class, "consignment");
			cr.add(Restrictions.eq("consignment.consgNo", consgNo));
			@SuppressWarnings("unchecked")
			List<ConsignmentDO> consgDOs = (List<ConsignmentDO>) cr.list();
			if (!StringUtil.isEmptyColletion(consgDOs)) {
				consgDO = consgDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("CustModificationDAOImpl :: getConsgDetailsForModification()::::::", e);
			throw new CGSystemException(e);
		} finally{
			closeSession(session);
		}
		LOGGER .debug("CustModificationDAOImpl: getConsgDetailsForModification(): END");
		return consgDO;
	}

	@Override
	public BookingDO getBookingDeatils(String consgNo) throws CGSystemException {
		LOGGER .debug("CustModificationDAOImpl: getConsgBookingDeatilsForModification(): START");
		BookingDO bookingDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session =createSession();
			cr = session.createCriteria(BookingDO.class, "booking");
			cr.add(Restrictions.eq("booking.consgNumber", consgNo));
			@SuppressWarnings("unchecked")
			List<BookingDO> bookingDOs = (List<BookingDO>)cr.list();
			if(!StringUtil.isEmptyColletion(bookingDOs)){
				bookingDO = bookingDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("CustModificationDAOImpl :: getConsgBookingDeatilsForModification()::::::", e);
			throw new CGSystemException(e);
		} finally{
			closeSession(session);
		}
		LOGGER .debug("CustModificationDAOImpl: getConsgBookingDeatilsForModification(): END");
		return bookingDO;
	}
	
	@SuppressWarnings("unchecked")
	public CustModificationAliasTO getCnModificationValidationDetails(String consgNo)throws CGSystemException{
		LOGGER.debug("CustModificationDAOImpl :: getCnModificationValidationDetails() :: Start --------> ::::::");
		List<CustModificationAliasTO> custModiAliasTOs = null;
		CustModificationAliasTO custModiAliasTO=null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			custModiAliasTOs = session.createSQLQuery(BillingConstants.CUST_MODIFICATION_VALIDATION_DETAIS)
					.addScalar("consignmentNo")
					.addScalar("isConsgDelivered")
					.addScalar("billingStatus")
					.addScalar("bookDetails")
					.addScalar("bkgDate")
					.addScalar("isExcessConsg")
					.addScalar("custCheck")
					.addScalar("expenseCheck")
					.addScalar("collectionCheck")
					.addScalar("liabilityCheck")
					.addScalar("liabilitySapCheck")
//					.addScalar("isDelivered")
					.addScalar("custId")
					.addScalar("custCode")
					.addScalar("shipToCode")
					.addScalar("custName")
					.addScalar("custTypeCode")
					.addScalar("totalConsignmentWeight")
					.addScalar("declaredvalue")
					.addScalar("officeId")
					.addScalar("cityId",IntegerType.INSTANCE)
					.setParameter("consgNo", consgNo)
					.setResultTransformer(Transformers.aliasToBean(CustModificationAliasTO.class)).list();

			if(!CGCollectionUtils.isEmpty(custModiAliasTOs)){
				custModiAliasTO = custModiAliasTOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : CustModificationDAOImpl.getCnModificationValidationDetails", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		LOGGER.debug("CustModificationDAOImpl :: getCnModificationValidationDetails() :: End --------> ::::::");
		return custModiAliasTO;
	}	
	
	@Override
	public boolean saveOrUpdateCustModification(ConsignmentDO consignmentDO,
			BookingModifiedDO bookingModifiedDO,
			ConsignmentModifiedDO consignmentModifiedDO,
			BookingDO bookDo, SAPLiabilityEntriesDO sapLiabilityEntriesDO) throws CGSystemException {
		LOGGER.debug("CustModificationDAOImpl::saveOrUpdateCustModification:: Start======>");
		boolean isSaved = false;
		Session session = null;
		Transaction tx  = null;
		try {
			session = createSession();
			tx = session.beginTransaction();
			
			session.merge(consignmentDO);
			
			if (!StringUtil.isNull(bookingModifiedDO)) {
				session.merge(bookingModifiedDO);
			}
			if (!StringUtil.isNull(consignmentModifiedDO)) {
				session.merge(consignmentModifiedDO);
			}
			if (!StringUtil.isNull(bookDo)) {
				session.merge(bookDo);
			}
			if (!StringUtil.isNull(sapLiabilityEntriesDO)) {
				session.merge(sapLiabilityEntriesDO);
			}
			tx.commit();
			isSaved = true;
		} catch (Exception e) {
			tx.rollback();
			LOGGER.error("Exception In :: CustModificationDAOImpl :: saveOrUpdateCustModification ::", e);
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}
		LOGGER.debug("CustModificationDAOImpl::saveOrUpdateCustModification:: end======>");
		return isSaved;
	}
	@Override
	public BookingTypeDO getBookingTypeDO(String bookingType)
			throws CGSystemException {
		LOGGER .debug("CustModificationDAOImpl: getBookingTypeDO(): Start");
		Session session=null;
		Criteria criteria=null;
		BookingTypeDO bookingtypeDO=null;
		try {
			session=createSession();
			criteria = session.createCriteria(BookingTypeDO.class, "bookingtypedo");
			criteria.add(Restrictions.eq("bookingtypedo.bookingType", bookingType));
			@SuppressWarnings("unchecked")
			List<BookingTypeDO> booktypeDOs = (List<BookingTypeDO>)criteria.list();
			if(!StringUtil.isEmptyColletion(booktypeDOs)){
				bookingtypeDO = booktypeDOs.get(0);
			}
		
		}catch (Exception e) {
			logger.error("CustModificationDAOImpl::getBookingTypeDO :: Exception",e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		LOGGER .debug("CustModificationDAOImpl: getBookingTypeDO(): End");
		
		return bookingtypeDO;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SAPLiabilityEntriesDO checkSapLiabilityEntriesDetails(String consgNo)throws CGSystemException{
		LOGGER .debug("CustModificationDAOImpl: checkSapLiabilityEntriesDetails(): START");
		SAPLiabilityEntriesDO sapLiabilityDO=null;
		Session session = null;
		Criteria cr = null;
		try {
			//session = getHibernateTemplate().getSessionFactory().openSession();
			session=getHibernateTemplate().getSessionFactory().openSession();
			cr = session.createCriteria(SAPLiabilityEntriesDO.class, "sapLiabilityDO");
			cr.add(Restrictions.eq("sapLiabilityDO.consgNo", consgNo));
			cr.add(Restrictions.eq("sapLiabilityDO.statusFlag", "B"));
			List<SAPLiabilityEntriesDO> sapLiabilityDOs = (List<SAPLiabilityEntriesDO>)cr.list();
			if(!StringUtil.isEmptyColletion(sapLiabilityDOs)){
				sapLiabilityDO = sapLiabilityDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("CustModificationDAOImpl :: checkSapLiabilityEntriesDetails()::::::", e);
			throw new CGSystemException(e);
		} finally{
			closeSession(session);
		}
		LOGGER .debug("CustModificationDAOImpl: checkSapLiabilityEntriesDetails(): End");
		return sapLiabilityDO;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ProductDO getProductDetails(Integer productId)throws CGSystemException{
		ProductDO productDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			cr = session.createCriteria(ProductDO.class, "product");
			cr.add(Restrictions.eq("product.productId", productId));
			List<ProductDO> productDOs = (List<ProductDO>) cr.list();
			if (!StringUtil.isEmptyColletion(productDOs)) {
				productDO = productDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("CustModificationDAOImpl :: getProductDetails()::::::", e);
			throw new CGSystemException(e);
		} finally{
			closeSession(session);
		}
		LOGGER .debug("CustModificationDAOImpl: getProductDetails(): END");
		return productDO;
	}
	
	@Override
	public CustomerDO getCustomerByIdOrCode(Integer customerId,
			String customerCode) throws CGSystemException {
		Criteria customerCriteria = null;
		Session session = null;
		CustomerDO customer = null;
		try {
			session = createSession();
			customerCriteria = session.createCriteria(CustomerDO.class);
			if (!StringUtil.isEmptyInteger(customerId))
				customerCriteria.add(Restrictions.eq("customerId", customerId));
			if (StringUtils.isNotEmpty(customerCode))
				customerCriteria.add(Restrictions.eq("customerCode",
						customerCode));
			customer = (CustomerDO) customerCriteria.uniqueResult();
		} catch (Exception e) {
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		return customer;
	}
}
