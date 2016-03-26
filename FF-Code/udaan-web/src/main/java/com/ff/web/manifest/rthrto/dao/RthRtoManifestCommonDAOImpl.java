package com.ff.web.manifest.rthrto.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ConsignmentReturnDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.rthrto.ConsignmentValidationTO;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;
import com.ff.web.manifest.rthrto.constants.RthRtoManifestConstatnts;

// TODO: Auto-generated Javadoc
/**
 * The Class RthRtoManifestCommonDAOImpl.
 */
public class RthRtoManifestCommonDAOImpl extends CGBaseDAO implements
		RthRtoManifestCommonDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RthRtoManifestCommonDAO.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.rthrto.dao.RthRtoManifestCommonDAO#saveOrUpdateManifest
	 * (com.ff.domain.manifest.ManifestDO)
	 */
	public ManifestDO saveOrUpdateManifest(ManifestDO manifestDO)
			throws CGSystemException {
		LOGGER.trace("RthRtoManifestCommonDAOImpl::saveOrUpdateManifest::START------------>:::::::");
		try {
			getHibernateTemplate().merge(manifestDO);

		} catch (Exception e) {
			LOGGER.error("Error occured in RthRtoManifestCommonDAOImpl :: saveOrUpdateManifest()..:"
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("RthRtoManifestCommonDAOImpl::saveOrUpdateManifest::END------------>:::::::");
		return manifestDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.dao.OutManifestDoxDAO#saveOrUpdateManifestProcess
	 * (java.util.List)
	 */
	/*public ManifestProcessDO saveOrUpdateManifestProcess(
			ManifestProcessDO manifestProcessDO) throws CGSystemException {
		LOGGER.trace("RthRtoManifestCommonDAOImpl::saveOrUpdateManifestProcess::START------------>:::::::");
		try {
			getHibernateTemplate().saveOrUpdate(manifestProcessDO);
		} catch (Exception e) {
			LOGGER.error("Error occured in RthRtoManifestCommonDAOImpl :: saveOrUpdateManifestProcess()..:"
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("RthRtoManifestCommonDAOImpl::saveOrUpdateManifestProcess::END------------>:::::::");
		return manifestProcessDO;
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.rthrto.dao.RthRtoManifestCommonDAO#
	 * searchRTOHManifestDetails(com.ff.manifest.ManifestInputs)
	 */
	public ManifestDO searchRTOHManifestDetails(ManifestInputs manifestTO)
			throws CGSystemException {
		LOGGER.trace("RthRtoManifestCommonDAOImpl::searchRTOHManifestDetails::START------------>:::::::");
		ManifestDO manifestDO = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = createSession();
			criteria = session.createCriteria(ManifestDO.class);
			criteria.add(Restrictions.eq("manifestNo",
					manifestTO.getManifestNumber()));
			if (!StringUtil.isEmptyInteger(manifestTO.getLoginOfficeId())) {
				criteria.createAlias("originOffice", "officeDO");
				criteria.add(Restrictions.eq("officeDO.officeId",
						manifestTO.getLoginOfficeId()));
			}
			if (!StringUtil.isStringEmpty(manifestTO.getManifestType())) {
				criteria.add(Restrictions.eq("manifestType",
						manifestTO.getManifestType()));
			}
			if (!StringUtil.isStringEmpty(manifestTO.getDocType())) {
				criteria.createAlias("manifestLoadContent", "loadContent");
				criteria.add(Restrictions.eq("loadContent.consignmentName",
						manifestTO.getDocType()));
			}
			/*if (!StringUtil.isStringEmpty(manifestTO.getManifestDirection())) {
				criteria.add(Restrictions.eq("manifestDirection",
						manifestTO.getManifestDirection()));
			}*/
			manifestDO = (ManifestDO) criteria.uniqueResult();
			if (!StringUtil.isNull(manifestDO)) {
				Hibernate.initialize(manifestDO.getConsignments());
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in RthRtoManifestCommonDAOImpl :: searchRTOHManifestDetails() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("RthRtoManifestCommonDAOImpl::searchRTOHManifestDetails::END------------>:::::::");
		return manifestDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.rthrto.dao.RthRtoManifestCommonDAO#
	 * getConsgReturnByConsgNoOfficeIdReturnType(java.lang.String,
	 * java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ConsignmentReturnDO getConsgReturnByConsgNoOfficeIdReturnType(
			String consignmentNo, Integer originOffice, String returnType)
			throws CGSystemException {
		LOGGER.trace("RthRtoManifestCommonDAOImpl::getConsgReturnByConsgNoOfficeIdReturnType::START------------>:::::::");
		ConsignmentReturnDO consignmentReturnDO = null;
		try {
			List<ConsignmentReturnDO> consignmentReturnDOs = (List<ConsignmentReturnDO>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							RthRtoManifestConstatnts.QRY_GET_CONSIGNMENT_RETURN_BY_CONSG_NO_OFFICE_ID_RETURN_TYPE,
							new String[] {
									RthRtoManifestConstatnts.PARAM_CONSIGNMENT_NUMBER,
									RthRtoManifestConstatnts.PARAM_OFFICE_ID,
									RthRtoManifestConstatnts.PARAM_RETURN_TYPE },
							new Object[] { consignmentNo, originOffice,
									returnType });
			if (!StringUtil.isEmptyList(consignmentReturnDOs)) {
				consignmentReturnDO = consignmentReturnDOs.get(0);
			}

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::RthRtoManifestCommonDAOImpl::getConsgReturnByConsgNoOfficeIdReturnType() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("RthRtoManifestCommonDAOImpl::getConsgReturnByConsgNoOfficeIdReturnType::END------------>:::::::");
		return consignmentReturnDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer isConsgNoValidated(ConsignmentValidationTO consgValidationTO)
			throws CGSystemException {
		LOGGER.trace("RthRtoManifestCommonDAOImpl :: isConsgNoValidated() :: START -------------->:::::::");
		Integer consignmentReturnReasonId = null;
		try {
			String[] paramNames = {"consignmentNo","originOffice","returnType"};
			Object[] values = {consgValidationTO.getConsgNumber(),consgValidationTO.getOriginOffice(),consgValidationTO.getConsgReturnType()};
			List<Integer> consignmentReturnReasonIdsList = getHibernateTemplate().findByNamedQueryAndNamedParam("getConsignmentReturnReason", paramNames, values);
			if (!StringUtil.isEmptyList(consignmentReturnReasonIdsList)) {
				consignmentReturnReasonId = consignmentReturnReasonIdsList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in RthRtoManifestCommonDAOImpl :: isConsgNoValidated() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("RthRtoManifestCommonDAOImpl :: isConsgNoValidated() :: END ---------------->:::::::");
		return consignmentReturnReasonId;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ManifestDO> isRtohNoManifested(ManifestInputs manifestTO)
			throws CGSystemException {
		LOGGER.trace("RthRtoManifestCommonDAOImpl :: isRtohNoManifested() :: START------------>:::::::");
		List<ManifestDO> manifestDOs = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = createSession();
			criteria = session.createCriteria(ManifestDO.class);
			criteria.add(Restrictions.eq("manifestNo",
					manifestTO.getManifestNumber()));
			manifestDOs = criteria.list();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in RthRtoManifestCommonDAOImpl :: isRtohNoManifested() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("RthRtoManifestCommonDAOImpl :: isRtohNoManifested() :: END------------>:::::::");
		return manifestDOs;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public ReasonDO validateConsForBranchOut(String returnType,Integer loginOffcId,String consNo)
			throws CGSystemException {
		LOGGER.trace("RthRtoManifestCommonDAOImpl :: validateConsForBranchOut() :: START -------------->:::::::");
		List<ReasonDO> result = null;
		Session session = null;
		ReasonDO reasonDO= null;
		try {
				session = getHibernateTemplate().getSessionFactory().openSession();
				Query query = session.getNamedQuery("getReasonByConsignmentNoAndOriginOffice");
				//Query query = session.createSQLQuery(sql);
				query.setString("consigNo", consNo);
				query.setString("returnType", returnType);
				query.setInteger("originOffice", loginOffcId);
				result = query.list();
				if(!CGCollectionUtils.isEmpty(result)) {
					reasonDO = result.get(0);
				}
			
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in RthRtoManifestCommonDAOImpl :: validateConsForBranchOut() :: ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("RthRtoManifestCommonDAOImpl :: validateConsForBranchOut() :: END ---------------->:::::::");
		return reasonDO;
	}
	
	public ProductDO getProductDetails(Integer productId)throws CGSystemException{
		ProductDO productDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			//session = createSession();
			session=createSession();
			cr = session.createCriteria(ProductDO.class, "product");
			cr.add(Restrictions.eq("product.productId", productId));
			List<ProductDO> productDOs = (List<ProductDO>)cr.list();
			if(!StringUtil.isEmptyColletion(productDOs)){
				productDO = productDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("RthRtoManifestCommonDAOImpl :: getProduct()::::::" , e);
			throw new CGSystemException(e);
		} finally{
			closeSession(session);
		}
		 LOGGER .debug("RthRtoManifestCommonDAOImpl: getProduct(): END");
		return productDO;
		
		
	}
	
	public ConsignmentBillingRateDO getAlreadyExistConsgRtoRate(ConsignmentDO consingnment,String rateFor)throws CGSystemException{
		LOGGER.debug("RthRtoManifestCommonDAOImpl :: getAlreadyExistConsgRate() :: Start --------> ::::::");
		ConsignmentBillingRateDO consignmentBillingRateDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(ConsignmentBillingRateDO.class, "consignmentBillingRateDO");
			cr.add(Restrictions.eq("consignmentBillingRateDO.consignmentDO", consingnment));
			cr.add(Restrictions.eq("consignmentBillingRateDO.rateCalculatedFor", rateFor));
			List<ConsignmentBillingRateDO> consignmentBillingRateDOs = (List<ConsignmentBillingRateDO>)cr.list();
			if(!StringUtil.isEmptyColletion(consignmentBillingRateDOs)){
				consignmentBillingRateDO = consignmentBillingRateDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("RthRtoManifestCommonDAOImpl :: getAlreadyExistConsgRate()::::::"
					, e);
			throw new CGSystemException(e);
		} finally{
			closeSession(session);
		}
		 LOGGER .debug("BillingCommonDAOImpl: getAlreadyExistConsgRate(): END");
		 return consignmentBillingRateDO;
	}
	
	public Boolean saveOrUpdateConsgRtoRate(List<ConsignmentBillingRateDO> consignmentBillingRate)throws CGSystemException{
		Boolean flag=Boolean.FALSE;
		Session session = null;
		try{
			//session = createSession();;
			getHibernateTemplate().saveOrUpdateAll(consignmentBillingRate);
			flag=Boolean.TRUE;
			//Updating flag billingstatus into consignment table
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::RthRtoManifestCommonDAOImpl::saveOrUpdateConsgRate() :: " , e);
			throw new CGSystemException(e);
		}	finally {
			closeSession(session);
		}
		LOGGER.debug("RthRtoManifestCommonDAOImpl :: saveOrUpdateConsgRate() :: End --------> ::::::");
		return flag;
	}

	@Override
	public boolean isCNStopDelivered(ConsignmentValidationTO consigValidationTO) throws CGSystemException {
		Boolean isCNStopDelivered = Boolean.FALSE;
		Session session = null;
		Criteria criteria = null;
		try {
			session = createSession();
			criteria = session.createCriteria(ConsignmentDO.class);
			criteria.add(Restrictions.eq("consgNo", consigValidationTO.getConsgNumber()));
			criteria.add(Restrictions.eq("consgStatus", CommonConstants.CONSIGNMENT_STATUS_STOPDELV));
			List<ConsignmentDO> consignmentDOs = criteria.list();
			if(!StringUtil.isEmptyList(consignmentDOs)){
				isCNStopDelivered = Boolean.TRUE;
			}
		} catch (Exception e) {
			LOGGER.error("RthRtoManifestCommonDAOImpl :: getAlreadyExistConsgRate()::::::"
					, e);
			throw new CGSystemException(e);
		} finally{
			closeSession(session);
		}
		return isCNStopDelivered;
	}

	@Override
	public Object[] getManifestDtlsByConsgNoOperatingOffice(
			ConsignmentValidationTO consignmentValidationTO)
			throws CGSystemException {
		LOGGER.debug("RthRtoManifestCommonDAOImpl :: getManifestDtlsByConsgNoOperatingOffice() :: Start --------> ::::::");
		Object manifest[] = null;
		Session session = null;
		try {
			session = createSession();
			Query qry = null;
			/* As discussed with Somesh : Recent manifest type should be either In-Manifest or Third party manifest with delivery pending status */
//			if(consignmentValidationTO.getManifestType().equalsIgnoreCase(CommonConstants.MANIFEST_TYPE_RTH)){
				/*In case of RTH manifest after doing RTH a particular office for  a consignment 
				 * and if the same consignment is In-manifested in that office again then it should be considered as fresh in-manifest 
				 * and should be allowed again for RTH or RTO manifest.*/
				qry = session.getNamedQuery(InManifestConstants.QRY_GET_MANIFEST_DTLS_BY_CONSG_NO_OPERATING_OFFICE);
				qry.setInteger("operatingOffice", consignmentValidationTO.getOriginOffice());
				//sub:ERROR DURING MAKE RTO   " Consigment Number : B991Z4142581 is neither inManifested Nor Third Party Manifested "
			/*} else if (consignmentValidationTO.getManifestType().equalsIgnoreCase(CommonConstants.MANIFEST_TYPE_RTO)) {
				In case of RTO manifest operating office should not be chk. Recent manifest should be RTH manifest
				qry = session.getNamedQuery("getManifestDtlsByConsgNoForRTO");*/				
//			}			
			qry.setString("consignmentNo", consignmentValidationTO.getConsgNumber());			

			qry.setMaxResults(1);
			List<Object[]> manifestDtls = qry.list();

			manifest = !StringUtil.isEmptyList(manifestDtls) ? manifestDtls
					.get(0) : null;
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::RthRtoManifestCommonDAOImpl::getManifestDtlsByConsgNoOperatingOffice() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("RthRtoManifestCommonDAOImpl :: getManifestDtlsByConsgNoOperatingOffice() :: End --------> ::::::");
		return manifest;
	}
	
}
