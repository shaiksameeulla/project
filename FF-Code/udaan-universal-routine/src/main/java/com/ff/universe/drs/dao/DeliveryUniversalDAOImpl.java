/**
 * 
 */
package com.ff.universe.drs.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.delivery.DrsCollectionIntegrationWrapperDO;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.universe.manifest.constant.ManifestUniversalConstants;

/**
 * @author mohammes
 * 
 */
public class DeliveryUniversalDAOImpl extends CGBaseDAO implements
DeliveryUniversalDAO {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DeliveryUniversalDAOImpl.class);

	/**
	 * getConsignmentStatusFromDelivery : get Delivery status of the Given
	 * consignment number and consg number should exist in the consignment table
	 * 
	 * @param consignment
	 * @return
	 * @throws CGSystemException
	 */
	@Override
	public String getConsignmentStatusFromDelivery(final String consignment)
			throws CGSystemException {
		List<String> result ;
		String status = null;
		final String queryName = UniversalDeliveryContants.QRY_CONSG_STATUS_FROM_DELIVERY;
		final Session session=createSession();
		try {
			Query qry = session.getNamedQuery(queryName);
			qry.setString(UniversalDeliveryContants.QRY_PARAM_CONSG, consignment);
			qry.setString(UniversalDeliveryContants.QRY_PARAM_RECORD_STATUS,UniversalDeliveryContants.RECORD_STATUS_ACTIVE);

			qry.setMaxResults(1);
			result = qry.list();
		} catch (Exception e) {
			LOGGER.error("DeliveryUniversalDAOImpl::getConsignmentStatusFromDelivery :: Exception"+e.getLocalizedMessage());
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}
		if (!CGCollectionUtils.isEmpty(result)) {
			status = result.get(0);
		}
		return status;
	}
	@Override
	public String isConsignmentExistInDeliveryByParentConsg(final String consignment)
			throws CGSystemException {
		List<String> result ;
		String status = null;
		final String queryName = UniversalDeliveryContants.QRY_IS_CONSG_EXIST_IN_DELIVERY_BY_PARENT_CN;
		final Session session=createSession();
		try {
			Query qry = session.getNamedQuery(queryName);
			qry.setString(UniversalDeliveryContants.QRY_PARAM_CONSG, consignment);
			qry.setString(UniversalDeliveryContants.QRY_PARAM_RECORD_STATUS,UniversalDeliveryContants.RECORD_STATUS_ACTIVE);

			qry.setMaxResults(1);
			result = qry.list();
		} catch (Exception e) {
			LOGGER.error("DeliveryUniversalDAOImpl::isConsignmentExistInDeliveryByParentConsg :: Exception"+e.getLocalizedMessage());
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}
		if (!CGCollectionUtils.isEmpty(result)) {
			status = result.get(0);
		}
		return status;
	}

	/**
	 * Gets the latest date for consignment for Active consignments here we
	 * ignore Discarded consignments.
	 * 
	 * @param consignment
	 *            the consignment
	 * @return the latest date for consignment
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	@Override
	public Date getLatestDateForConsignment(final String consignment)
			throws CGSystemException {
		List<Date> result;
		Date currentDate = null;
		String queryName = UniversalDeliveryContants.QRY_LATEST_DATE_CONSGN_NUMBER;
		Session session=createSession();
		try {
			Query qry = session.getNamedQuery(queryName);
			qry.setString(UniversalDeliveryContants.QRY_PARAM_CONSG, consignment);
			qry.setString(UniversalDeliveryContants.QRY_PARAM_RECORD_STATUS,UniversalDeliveryContants.RECORD_STATUS_ACTIVE);

			qry.setMaxResults(1);
			result = qry.list();
		} catch (Exception e) {
			LOGGER.error("DeliveryUniversalDAOImpl::getLatestDateForConsignment :: Exception"+e.getLocalizedMessage());
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}
		if (!CGCollectionUtils.isEmpty(result)) {
			currentDate = result.get(0);
		}
		return currentDate;
	}

	/**
	 * Gets the consignment dtls from booking. Procedure: it checks given consg
	 * number in booking table with logged in office, if it's available it
	 * checks in consg table But Actal Named Qry in deliveryDetails.hbm.xml
	 * 
	 * @param inputTo
	 *            the input to
	 * @return the consignment dtls from booking
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	@Override
	public ConsignmentDO getDoxConsgDtlsFromBooking(AbstractDeliveryTO inputTo)
			throws CGSystemException {
		List<ConsignmentDO> consDOList;
		ConsignmentDO consDO = null;
		String queryName = UniversalDeliveryContants.QRY_DOX_CONSG_FRM_BKNG;
		String paramNames[] = { UniversalDeliveryContants.QRY_PARAM_CONSG,
				UniversalDeliveryContants.QRY_PARAM_LOGGED_IN_OFFICE_ID };
		Object values[] = { inputTo.getConsignmentNumber(),
				inputTo.getLoginOfficeId() };

		consDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, paramNames, values);
		if (!CGCollectionUtils.isEmpty(consDOList)) {
			consDO = consDOList.get(0);
		}
		return consDO;
	}

	/**
	 * Gets the ppx consg dtls from booking.
	 *
	 * @param inputTo the input to
	 * @return the ppx consg dtls from booking
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public ChildConsignmentDO getPpxConsgDtlsFromBooking(AbstractDeliveryTO inputTo)
			throws CGSystemException {
		List<ChildConsignmentDO> consDOList;
		ChildConsignmentDO consDO = null;
		String queryName = UniversalDeliveryContants.QRY_PPX_CONSG_FRM_BKNG;
		String paramNames[] = { UniversalDeliveryContants.QRY_PARAM_CONSG,
				UniversalDeliveryContants.QRY_PARAM_LOGGED_IN_OFFICE_ID };
		Object values[] = { inputTo.getConsignmentNumber(),
				inputTo.getLoginOfficeId() };

		consDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, paramNames, values);
		if (!CGCollectionUtils.isEmpty(consDOList)) {
			consDO = consDOList.get(0);
		}
		return consDO;
	}

	/**
	 * Gets the in manifest consgn dtls for drs. Procedure: check whether
	 * DOX/Parent cn is in manifested or not,if it's in-manifested get the
	 * consgnment details
	 * 
	 * @param consgNumber
	 *            the consg number
	 * @param manifestStatus
	 *            the manifest status
	 * @return the in manifest consgn dtls for drs
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	@Override
	public ConsignmentDO getManifestedConsgnDtls(String consgNumber,
			String manifestStatus) throws CGSystemException {
		List<ConsignmentDO> manifestedConsg;
		String[] params = { UniversalDeliveryContants.QRY_PARAM_CONSG,
				ManifestUniversalConstants.MANIFEST_TYPE };
		Object[] values = { consgNumber, manifestStatus };
		manifestedConsg = getHibernateTemplate().findByNamedQueryAndNamedParam(
				UniversalDeliveryContants.QRY_DOX_MANIFEST_CONSGN_DTLS_FOR_DRS,
				params, values);
		return !CGCollectionUtils.isEmpty(manifestedConsg) ? manifestedConsg
				.get(0) : null;
	}
	
	/**
	 * Gets the manifested consgn dtls for third party drs parent cn.
	 *
	 * @param consgNumber the consg number
	 * @param manifestStatus the manifest status
	 * @param manifestNumber the manifest number
	 * @return the manifested consgn dtls for third party drs parent cn
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public ConsignmentDO getManifestedConsgnDtlsForThirdPartyDrsParentCn(String consgNumber,
			String manifestStatus,String manifestNumber) throws CGSystemException {
		List<ConsignmentDO> manifestedConsg;
		String[] params = { UniversalDeliveryContants.QRY_PARAM_CONSG,
				ManifestUniversalConstants.MANIFEST_TYPE ,UniversalDeliveryContants.QRY_PARAM_MNFST_NUMBER};
		Object[] values = { consgNumber, manifestStatus ,manifestNumber};
		manifestedConsg = getHibernateTemplate().findByNamedQueryAndNamedParam(
				UniversalDeliveryContants.QRY_CN_DTLS_FOR_THIRD_PARTY_MANIFEST_DTLS_PARENT_CN,
				params, values);
		return !CGCollectionUtils.isEmpty(manifestedConsg) ? manifestedConsg
				.get(0) : null;
	}
	
	/**
	 * Gets the manifested consgn dtls for third party drs child cn.
	 *
	 * @param consgNumber the consg number
	 * @param manifestStatus the manifest status
	 * @param manifestNumber the manifest number
	 * @return the manifested consgn dtls for third party drs child cn
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public String  getManifestedConsgnDtlsForThirdPartyDrsChildCn(String consgNumber,
			String manifestStatus,String manifestNumber) throws CGSystemException {
		List<String> manifestedConsg;
		String[] params = { UniversalDeliveryContants.QRY_PARAM_CONSG,
				ManifestUniversalConstants.MANIFEST_TYPE,UniversalDeliveryContants.QRY_PARAM_MNFST_NUMBER };
		Object[] values = { consgNumber, manifestStatus ,manifestNumber};
		manifestedConsg = getHibernateTemplate().findByNamedQueryAndNamedParam(
				UniversalDeliveryContants.QRY_CN_DTLS_FOR_THIRD_PARTY_MANIFEST_DTLS_CHILD_CN,
				params, values);
		return !CGCollectionUtils.isEmpty(manifestedConsg) ? manifestedConsg
				.get(0) : null;
	}

	/**
	 * Gets the manifested comail dtls.
	 *
	 * @param consgNumber the consg number
	 * @param manifestStatus the manifest status
	 * @return the manifested comail dtls
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public ComailDO getManifestedComailDtls(String consgNumber,
			String manifestStatus) throws CGSystemException {
		List<ComailDO> manifestedComail;
		String[] params = { UniversalDeliveryContants.QRY_PARAM_CONSG,
				ManifestUniversalConstants.MANIFEST_TYPE };
		Object[] values = { consgNumber, manifestStatus };
		manifestedComail = getHibernateTemplate().findByNamedQueryAndNamedParam(
				UniversalDeliveryContants.QRY_DOX_MANIFEST_COMAIL_DTLS_FOR_DRS,
				params, values);
		return !CGCollectionUtils.isEmpty(manifestedComail) ? manifestedComail
				.get(0) : null;
	}

	/**
	 * Checks if is valid comail number.
	 *
	 * @param consgNumber the consg number
	 * @return true, if is valid comail number
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public boolean isValidComailNumber(String consgNumber) throws CGSystemException {
		List<String> manifestedComail;
		String[] params = { UniversalDeliveryContants.QRY_PARAM_CONSG
		};
		Object[] values = { consgNumber };
		manifestedComail = getHibernateTemplate().findByNamedQueryAndNamedParam(
				UniversalDeliveryContants.QRY_IS_COMAIL_VALID,
				params, values);
		return !CGCollectionUtils.isEmpty(manifestedComail) ? true
				: false;
	}


	/**
	 * Gets the in Delivered consingnments details
	 * @param consgNumber
	 *            the consg number
	 @throws CGSystemException
	 *             the cG system exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DeliveryDetailsDO getDeliverdConsgDtls(String consignment,
			Integer orgOfficeId) throws CGSystemException {
		DeliveryDetailsDO deliveryDtls = null;
		Session session = null;
		List<DeliveryDetailsDO> deliveryDtlsDOs = null;
		try {
			String[] params = { UniversalDeliveryContants.QRY_PARAM_CONSG,
					UniversalDeliveryContants.QRY_PARAM_DELIVERY_STATUS,
					UniversalDeliveryContants.CONSG_ORIGIN_OFF_ID };
			Object[] values = { consignment,
					UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED,
					orgOfficeId };
			deliveryDtlsDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UniversalDeliveryContants.QRY_GET_DELIVERED_CN_DTLS,
							params, values);
			deliveryDtls = !StringUtil.isEmptyList(deliveryDtlsDOs) ? deliveryDtlsDOs
					.get(0) : null;
		} catch (Exception e) {
			LOGGER.error("Error occured in DeliveryUniversalDAOImpl :: getDeliverdConsgDtls()..:"
					+ e.getMessage());
		} finally {
			closeSession(session);
		}
		return deliveryDtls;
	}

	/**
	 * getAttemptCountForConsignment : it gives no of consg occurence in the table
	 * @param consgNumber
	 * @return
	 * @throws CGSystemException
	 */
	@Override
	public Integer getAttemptCountForConsignment(String consgNumber) throws CGSystemException {
		Integer attempCount = null;
		List<Long> attempCounts = null;
		String queryName = UniversalDeliveryContants.QRY_FOR_ATTEMPT_NUMEBR;
		String paramNames[] = { UniversalDeliveryContants.QRY_PARAM_CONSG,
				UniversalDeliveryContants.QRY_PARAM_RECORD_STATUS };
		Object values[] = { consgNumber,
				UniversalDeliveryContants.RECORD_STATUS_ACTIVE };

		attempCounts = getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, paramNames, values);
		if(!CGCollectionUtils.isEmpty(attempCounts)){
			attempCount =attempCounts.get(0).intValue()+1; 
		}else{
			attempCount=1;
		}
		return attempCount;

	}





	/**
	 * Gets the parent consg dtls for drs. :: validate against Consgment table and it doesnot constains any child elements
	 *
	 * @param consgNumber the consg number
	 * @return the parent consg dtls for drs
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public ConsignmentDO getParentConsgDtlsForDrs(String consgNumber)throws CGSystemException {
		ConsignmentDO consgDetails = null;
		List<ConsignmentDO> consgDtlsList;
		String queryName = UniversalDeliveryContants.QRY_FOR_PARENT_CONSG_DTLS;
		String paramNames[] = { UniversalDeliveryContants.QRY_PARAM_CONSG };
		Object values[] = { consgNumber};

		consgDtlsList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, paramNames, values);
		if(!CGCollectionUtils.isEmpty(consgDtlsList)){
			consgDetails =consgDtlsList.get(0); 
		}
		return consgDetails;

	}
	@Override
	public String getConsignmentTypeByConsgNumber(String consgNumber)throws CGSystemException {
		List<String> consgDtlsList;
		String queryName = UniversalDeliveryContants.QRY_GET_CONSIGNMENT_TYPE_FOR_CONSG;
		 queryName = UniversalDeliveryContants.QRY_GET_CONSIGNMENT_TYPE_FOR_PARENT_CONSG;
		String paramNames[] = { UniversalDeliveryContants.QRY_PARAM_CONSG };
		Object values[] = { consgNumber};

		consgDtlsList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, paramNames, values);
		if(CGCollectionUtils.isEmpty(consgDtlsList)){
			consgDtlsList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UniversalDeliveryContants.QRY_GET_CONSIGNMENT_TYPE_FOR_CHILD_CONSG, paramNames, values);
		}
		return !CGCollectionUtils.isEmpty(consgDtlsList)?consgDtlsList.get(0):null;

	}
	
	@Override
	public String getConsignmentStatusFromConsg(String consgNumber)throws CGSystemException {
		List<String> consgDtlsList;
		String queryName = UniversalDeliveryContants.QRY_GET_CONSIGNMENT_STATUS_FROM_CONSG;
		String paramNames[] = { UniversalDeliveryContants.QRY_PARAM_CONSG };
		Object values[] = { consgNumber};

		consgDtlsList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, paramNames, values);
		return !CGCollectionUtils.isEmpty(consgDtlsList)?consgDtlsList.get(0):null;

	}
	
	

	/**
	 * Gets the child consg dtls for drs. ::validating against child table and child table 
	 * should have associated with the consg table
	 *
	 * @param consgNumber the consg number
	 * @return the child consg dtls for drs
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public ChildConsignmentDO getChildConsgDtlsForDrs(String consgNumber)throws CGSystemException {
		ChildConsignmentDO consgDetails = null;
		List<ChildConsignmentDO> consgDtlsList ;
		String queryName = UniversalDeliveryContants.QRY_FOR_CHILD_CONSG_DTLS;
		String paramNames[] = { UniversalDeliveryContants.QRY_PARAM_CONSG };
		Object values[] = { consgNumber};

		consgDtlsList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, paramNames, values);
		if(!CGCollectionUtils.isEmpty(consgDtlsList)){
			consgDetails =consgDtlsList.get(0); 
		}
		return consgDetails;

	}
	@Override
	public Double getOctroiAmountForDrs(String consgNumber)throws CGSystemException {
		Double octroiCharges = null;
		List<Double> consgDtlsList ;
		String queryName = UniversalDeliveryContants.QRY_FOR_OCTROI_AMOUNT_BY_PARENT_CN;
		String paramNames[] = { UniversalDeliveryContants.QRY_PARAM_CONSG  ,"octroiBourneBy"};
		Object values[] = { consgNumber,CommonConstants.OCTROI_BOURNE_BY_CONSIGNOR};

		consgDtlsList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, paramNames, values);
		if(!CGCollectionUtils.isEmpty(consgDtlsList) && !StringUtil.isEmptyDouble(consgDtlsList.get(0))){
			octroiCharges =consgDtlsList.get(0); 
		}else{
			queryName=UniversalDeliveryContants.QRY_FOR_OCTROI_AMOUNT_BY_CHILD_CN;
			consgDtlsList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, paramNames, values);
		}
		if(!CGCollectionUtils.isEmpty(consgDtlsList) && !StringUtil.isEmptyDouble(consgDtlsList.get(0))){
			octroiCharges =consgDtlsList.get(0);
		}
		return octroiCharges;

	}

	/**
	 * Checks if is consg having child cns. it checks whether given Consg has child consg as well.
	 * if child cn exists it returns given consg otherwise null.
	 *
	 * @param consgNumber the consg number
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public String isConsgHavingChildCns(String consgNumber)throws CGSystemException {
		String consgDetails = null;
		List<String> consgList ;
		String queryName = UniversalDeliveryContants.QRY_FOR_IS_CONSG_HAVING_CHILD_CNS;
		String paramNames[] = { UniversalDeliveryContants.QRY_PARAM_CONSG };
		Object values[] = { consgNumber};
		consgList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, paramNames, values);
		if(!CGCollectionUtils.isEmpty(consgList)){
			consgDetails =consgList.get(0); 
		}
		return consgDetails;

	}
	@Override
	public String isChildCns(String consgNumber)throws CGSystemException {
		String consgDetails = null;
		List<String> consgList ;
		String queryName = UniversalDeliveryContants.QRY_FOR_IS_CHILD_CNS;
		String paramNames[] = { UniversalDeliveryContants.QRY_PARAM_CONSG };
		Object values[] = { consgNumber};
		consgList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, paramNames, values);
		if(!CGCollectionUtils.isEmpty(consgList)){
			consgDetails =consgList.get(0); 
		}
		return consgDetails;

	}


	/**
	 * Gets the mnfst child consgn dtls for drs for ppx.:it returns parent consg number
	 *
	 * @param consgNumber the consg number
	 * @param manifestStatus the manifest status
	 * @return the mnfst child consgn dtls for drs for ppx
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public String getMnfstChildConsgnDtlsForDrsForPPX(String consgNumber,
			String manifestStatus) throws CGSystemException {
		List<String> manifestedConsg;
		String[] params = { UniversalDeliveryContants.QRY_PARAM_CONSG,
				ManifestUniversalConstants.MANIFEST_TYPE };
		Object[] values = { consgNumber, manifestStatus };
		manifestedConsg = getHibernateTemplate().findByNamedQueryAndNamedParam(
				UniversalDeliveryContants.QRY_PPX_MANIFEST_CONSGN_FOR_DRS,
				params, values);
		return !CGCollectionUtils.isEmpty(manifestedConsg) ? manifestedConsg
				.get(0) : null;
	}
	@Override
	public Date getManifestedDateByConsgNumber(AbstractDeliveryTO dlvInputTO) throws CGSystemException {
		List<Date> manifestedDate=null;
		String[] params = { UniversalDeliveryContants.QRY_PARAM_CONSG,
				ManifestUniversalConstants.MANIFEST_TYPE, UniversalDeliveryContants.QRY_PARAM_PROCESS_CODE,ManifestUniversalConstants.OPERATING_OFFICE};
		Object[] values = { dlvInputTO.getConsignmentNumber(), dlvInputTO.getManifestDirection(),dlvInputTO.getProcessCode(),dlvInputTO.getLoginOfficeId() };
		manifestedDate = getHibernateTemplate().findByNamedQueryAndNamedParam(
				dlvInputTO.getQueryName(),
				params, values);
		return !CGCollectionUtils.isEmpty(manifestedDate) ? manifestedDate
				.get(0) : null;
	}
	
	@Override
	public Date getManifestedDateByConsgNumberAndManifestNumber(AbstractDeliveryTO dlvInputTO) throws CGSystemException {
		List<Date> manifestedDate;
		String[] params = { UniversalDeliveryContants.QRY_PARAM_CONSG,
				ManifestUniversalConstants.MANIFEST_TYPE, UniversalDeliveryContants.QRY_PARAM_PROCESS_CODE,UniversalDeliveryContants.QRY_PARAM_MNFST_NUMBER,ManifestUniversalConstants.OPERATING_OFFICE};
		Object[] values = { dlvInputTO.getConsignmentNumber(), dlvInputTO.getManifestDirection(),dlvInputTO.getProcessCode() ,dlvInputTO.getDrsNumber(),dlvInputTO.getLoginOfficeId()};
		manifestedDate = getHibernateTemplate().findByNamedQueryAndNamedParam(
				dlvInputTO.getQueryName(),
				params, values);
		return !CGCollectionUtils.isEmpty(manifestedDate) ? manifestedDate
				.get(0) : null;
	}

	@Override
	public Date getManifestedDateByComailNumber(String consgNumber,
			String manifestStatus,String processCode) throws CGSystemException {
		List<Date> manifestedDate ;
		String[] params = { UniversalDeliveryContants.QRY_PARAM_CONSG,
				ManifestUniversalConstants.MANIFEST_TYPE, UniversalDeliveryContants.QRY_PARAM_PROCESS_CODE};
		Object[] values = { consgNumber, manifestStatus,processCode };
		manifestedDate = getHibernateTemplate().findByNamedQueryAndNamedParam(
				UniversalDeliveryContants.QRY_FOR_MANFST_DATE_FOR_COMAIL,
				params, values);
		return !CGCollectionUtils.isEmpty(manifestedDate) ? manifestedDate
				.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DeliveryDetailsDO getDrsDtlsByConsgNo(String consignmentNumber)
			throws CGSystemException {
		LOGGER.debug("DeliveryUniversalDAOImpl :: getDrsDtlsByConsgNo() :: Start --------> ::::::");
		DeliveryDetailsDO deliveryDetailsDO = null;
		try {
			List<DeliveryDetailsDO> deliveryDetailsDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UniversalDeliveryContants.QRY_GET_DRS_DTLS_BY_CONSG_NO,
							UniversalDeliveryContants.PARAM_CONSG_NO,
							consignmentNumber);

			deliveryDetailsDO = !StringUtil.isEmptyList(deliveryDetailsDOs) ? deliveryDetailsDOs.get(0) : null;	

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::DeliveryUniversalDAOImpl::getDrsDtlsByConsgNo() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("DeliveryUniversalDAOImpl :: getDrsDtlsByConsgNo() :: End --------> ::::::");
		return deliveryDetailsDO;
	}

	/**
	 * validateManifestedOfficeByLoggedInOffice
	 * @return
	 * @throws CGSystemException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getInManifestedConsignmentNumberByOffice(AbstractDeliveryTO dlvInputTO) throws CGSystemException {
		List<String> manifestedNumber;
		String[] params = { UniversalDeliveryContants.QRY_PARAM_CONSG,
				ManifestUniversalConstants.MANIFEST_TYPE, UniversalDeliveryContants.QRY_PARAM_PROCESS_CODE,UniversalDeliveryContants.QRY_PARAM_LOGGED_IN_OFFICE_ID};
		Object[] values = { dlvInputTO.getConsignmentNumber(), dlvInputTO.getManifestDirection(),dlvInputTO.getProcessCode() ,dlvInputTO.getLoginOfficeId()};
		manifestedNumber = getHibernateTemplate().findByNamedQueryAndNamedParam(
				dlvInputTO.getQueryName(),
				params, values);
		return !CGCollectionUtils.isEmpty(manifestedNumber) ? manifestedNumber
				.get(0) : null;
	}
	
	@Override
	public String getOutManifestedConsignmentNumberByOfficeForThirdPartyManifest(AbstractDeliveryTO dlvInputTO) throws CGSystemException {
		List<String> manifestedNumber;
		String[] params = { UniversalDeliveryContants.QRY_PARAM_CONSG,
				ManifestUniversalConstants.MANIFEST_TYPE, UniversalDeliveryContants.QRY_PARAM_PROCESS_CODE,UniversalDeliveryContants.QRY_PARAM_LOGGED_IN_OFFICE_ID, UniversalDeliveryContants.QRY_PARAM_MNFST_NUMBER};
		Object[] values = { dlvInputTO.getConsignmentNumber(), dlvInputTO.getManifestDirection(),dlvInputTO.getProcessCode() ,dlvInputTO.getLoginOfficeId(),dlvInputTO.getDrsNumber()};
		manifestedNumber = getHibernateTemplate().findByNamedQueryAndNamedParam(
				dlvInputTO.getQueryName(),
				params, values);
		return !CGCollectionUtils.isEmpty(manifestedNumber) ? manifestedNumber
				.get(0) : null;
	}

	/**
	 * Gets the delivery details for collection.
	 *
	 * @return the delivery details for collection
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public List<DrsCollectionIntegrationWrapperDO> getDeliveryDetailsForCollection(String qryName)throws CGSystemException{
		List<DrsCollectionIntegrationWrapperDO> detailsDOList=null;
		//int defaultMaxFetch=getHibernateTemplate().getMaxResults();
		long startMilliseconds=System.currentTimeMillis();
		Session session= null;
		try {
			/*String[] params = { UniversalDeliveryContants.QRY_PARAM_DELIVERY_STATUS,
					UniversalDeliveryContants.QRY_PARAM_COLLECTION_STATUS, UniversalDeliveryContants.QRY_PARAM_TODAY_DATE,UniversalDeliveryContants.QRY_PARAM_YESTERDAY_DATE ,UniversalDeliveryContants.QRY_PARAM_SCREEN_CODE};
			Object[] values = { UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED, UniversalDeliveryContants.COLLECTION_STATUS_FLAG_NO,DateUtil.appendLastHourToDate(DateUtil.getCurrentDateWithoutTime()) ,DateUtil.trimTimeFromDate(DateUtil.getPreviousDate(60)),UniversalDeliveryContants.RTO_COD_SCREEN_CODE};
			detailsDOList=  getHibernateTemplate().findByNamedQueryAndNamedParam(qryName,
					params, values);*/
		session=createSession();
		Query query=session.getNamedQuery(qryName);
		query.setString(UniversalDeliveryContants.QRY_PARAM_DELIVERY_STATUS, UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED);
		query.setString(UniversalDeliveryContants.QRY_PARAM_COLLECTION_STATUS, UniversalDeliveryContants.COLLECTION_STATUS_FLAG_NO);
		query.setTimestamp(UniversalDeliveryContants.QRY_PARAM_TODAY_DATE, DateUtil.appendLastHourToDate(DateUtil.getCurrentDateWithoutTime()));
		query.setTimestamp(UniversalDeliveryContants.QRY_PARAM_YESTERDAY_DATE, DateUtil.trimTimeFromDate(DateUtil.getPreviousDate(8)));
		//query.setString(UniversalDeliveryContants.QRY_PARAM_SCREEN_CODE, UniversalDeliveryContants.RTO_COD_SCREEN_CODE);
		if(qryName.endsWith("ration")){
			query.setMaxResults(500);//setting max results to 800 per this session only
		}else{
			query.setMaxResults(30);//setting max results to 400 per this session only
		}
		detailsDOList=query.list();
		} catch (Exception e) {
			LOGGER.error("DeliveryUniversalDAOImpl::getDeliveryDetailsForCollection() ::Exception ", e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		long endMilliSeconds=System.currentTimeMillis();
		long diff=endMilliSeconds-startMilliseconds;
		String time=DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff);
		String loggs=
				"DeliveryUniversalDAOImpl::getDeliveryDetailsForCollection::END------------>::::::: For Query["+qryName+"]endMilliSeconds:["+endMilliSeconds+"] Difference"+(diff) +" Difference IN HH:MM:SS format ::"+time;
		LOGGER.debug(loggs);
	
		
		return detailsDOList;
	}

	/**
	 * Save collection dtls by drs.
	 *
	 * @param collectionDo the collection do
	 * @return the boolean
	 */
	@Override
	public Boolean saveCollectionDtlsByDrs(CollectionDO collectionDo){
		boolean result=false;
		getHibernateTemplate().save(collectionDo);
		result=true;
		return result;
	}
	@Override
	public Boolean saveCollectionDtlsByDrs(List<CollectionDO> collectionDo){
		boolean result=false;
		getHibernateTemplate().saveOrUpdateAll(collectionDo);
		result=true;
		return result;
	}

	/**
	 * Update drs for collection integration.
	 *
	 * @param drsDtsDO the drs dts do
	 * @return the boolean
	 * @throws CGSystemException 
	 */
	@Override
	public Boolean updateDrsForCollectionIntegration(DrsCollectionIntegrationWrapperDO drsDtsDO) throws CGSystemException{
		boolean result=false;
		int noOfRecords=0;
		Session session= openTransactionalSession();
		try {
			Query query=session.getNamedQuery(UniversalDeliveryContants.QRY_UPDATE_DRS_COLLECTION);
			query.setLong(UniversalDeliveryContants.QRY_PARAM_DELIVERY_DTL_ID, drsDtsDO.getDeliveryDtlsId());
			query.setString(UniversalDeliveryContants.QRY_PARAM_COLLECTION_STATUS, drsDtsDO.getCollectionStatus());
			noOfRecords=query.executeUpdate();
			LOGGER.info("DeliveryUniversalDAOImpl::updateDrsForCollectionIntegration() ::No Of Records update :"+noOfRecords);
		} catch (Exception e) {
			LOGGER.error("DeliveryUniversalDAOImpl::updateDrsForCollectionIntegration() ::Exception ", e);
			throw new CGSystemException(e);
		}finally {
			closeTransactionalSession(session);
		}
		result=true;
		return result;
	}
	@Override
	public Boolean updateCollectionStatusForAllDRSChildConsignments(DrsCollectionIntegrationWrapperDO drsDtsDO) throws CGSystemException{
		boolean result=false;
		int noOfRecords=0;
		Session session= openTransactionalSession();
		try {
			Query query=session.getNamedQuery(UniversalDeliveryContants.QRY_UPDATE_ALL_CHILD_CN_FOR_COLLECTION);
			query.setLong(UniversalDeliveryContants.QRY_PARAM_CONSG_ID, drsDtsDO.getConsgId());
			query.setString(UniversalDeliveryContants.QRY_PARAM_COLLECTION_STATUS, drsDtsDO.getCollectionStatus());
			query.setString(UniversalDeliveryContants.QRY_PARAM_DRS_STATUS, "D");
			noOfRecords=query.executeUpdate();
			LOGGER.info("DeliveryUniversalDAOImpl::updateAllChildConsignmentsForIntegration() ::No Of Records update :"+noOfRecords);
		} catch (Exception e) {
			LOGGER.error("DeliveryUniversalDAOImpl::updateDrsForCollectionIntegration() ::Exception ", e);
			throw new CGSystemException(e);
		}finally {
			closeTransactionalSession(session);
		}
		result=true;
		return result;
	}
	
	@Override
	public String getConsignmentStatusFromConsgForParentCn(String consgNumber)throws CGSystemException{
		List<String> detailsDOList=null;
		try {
			String[] params = { UniversalDeliveryContants.QRY_PARAM_CONSG};
			Object[] values = {consgNumber};
			detailsDOList=  getHibernateTemplate().findByNamedQueryAndNamedParam(
					UniversalDeliveryContants.QRY_GET_CN_STATUS_FROM_CONSG_FOR_PARENT,
					params, values);
		} catch (Exception e) {
			LOGGER.error("DeliveryUniversalDAOImpl::getConsignmentStatusFromConsgForParentCn() ::Exception ", e);
			throw new CGSystemException(e);
		}
		
		return !CGCollectionUtils.isEmpty(detailsDOList)?detailsDOList.get(0):null;
	}
	@Override
	public String getConsignmentStatusFromConsgForChildCn(String consgNumber)throws CGSystemException{
		List<String> detailsDOList=null;
		try {
			String[] params = { UniversalDeliveryContants.QRY_PARAM_CONSG};
			Object[] values = {consgNumber};
			detailsDOList=  getHibernateTemplate().findByNamedQueryAndNamedParam(
					UniversalDeliveryContants.QRY_GET_CN_STATUS_FROM_CONSG_FOR_CHILD,
					params, values);
		} catch (Exception e) {
			LOGGER.error("DeliveryUniversalDAOImpl::getConsignmentStatusFromConsgForChildCn() ::Exception ", e);
			throw new CGSystemException(e);
		}
		
		return !CGCollectionUtils.isEmpty(detailsDOList)?detailsDOList.get(0):null;
	}
	
	@Override
	public List<?> getManifestedTypeByConsignmentAndLoggedInoffice(AbstractDeliveryTO dlvInputTO) throws CGSystemException {
		List<?> result=null;
		String[] params = { UniversalDeliveryContants.QRY_PARAM_CONSG,
				UniversalDeliveryContants.QRY_PARAM_LOGGED_IN_OFFICE_ID};
		Object[] values = { dlvInputTO.getConsignmentNumber(), dlvInputTO.getLoginOfficeId()};
		String qryName=UniversalDeliveryContants.QRY_GET_MANIFESTED_TYPE_FOR_CONSG_BY_LOGIN_OFFICE;
				result= getHibernateTemplate().findByNamedQueryAndNamedParam(
						qryName,
				params, values);
		if(CGCollectionUtils.isEmpty(result)){
			qryName=UniversalDeliveryContants.QRY_GET_MANIFESTED_TYPE_FOR_CONSG_BY_LOG_IN_OFFICE_FOR_CHILD;
			result= getHibernateTemplate().findByNamedQueryAndNamedParam(
					qryName,params, values);
		}
		return result;
	}
	@Override
	public List<?> getManifestedTypeByConsignmentAndLoggedInofficeAndManifestNumber(AbstractDeliveryTO dlvInputTO) throws CGSystemException {
		List<?> result=null;
		String[] params = { UniversalDeliveryContants.QRY_PARAM_CONSG,
				UniversalDeliveryContants.QRY_PARAM_LOGGED_IN_OFFICE_ID};
		Object[] values = { dlvInputTO.getConsignmentNumber(), dlvInputTO.getLoginOfficeId()};
		String qryName=UniversalDeliveryContants.QRY_GET_MANIFESTED_TYPE_FOR_CONSG_BY_LOGIN_OFFICE;
				result= getHibernateTemplate().findByNamedQueryAndNamedParam(
						qryName,
				params, values);
		if(CGCollectionUtils.isEmpty(result)){
			qryName=UniversalDeliveryContants.QRY_GET_MANIFESTED_TYPE_FOR_CONSG_BY_LOG_IN_OFFICE_FOR_CHILD;
			result= getHibernateTemplate().findByNamedQueryAndNamedParam(
					qryName,params, values);
		}
		return result;
	}
	
	@Override
	public Boolean isCollectionEntryAlreadyExistForDRS(Integer consgId)throws CGSystemException {
		Boolean alreadyExist = false;
		List<Integer> consgDtlsList ;
		String queryName = UniversalDeliveryContants.QRY_FOR_IS_COLLECTION_ALREADY_POSTED;
		String paramNames[] = { UniversalDeliveryContants.QRY_PARAM_CONSG_ID };
		Object values[] = { consgId};

		consgDtlsList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, paramNames, values);
		if(!CGCollectionUtils.isEmpty(consgDtlsList) && !StringUtil.isEmptyInteger(consgDtlsList.get(0))){
			alreadyExist=true;
		}
		return alreadyExist;

	}
	@Override
	public Integer getOfficeIdByOfficeCode(String  officeCode)throws CGSystemException {
		Integer officeId = null;
		List<Integer> officeIdList=null ;
		String queryName = "getOfcIdByOfcCode";
		String paramNames[] = { "ofcCode" };
		Object values[] = { officeCode};

		officeIdList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, paramNames, values);
		if(!CGCollectionUtils.isEmpty(officeIdList) && !StringUtil.isEmptyInteger(officeIdList.get(0))){
			officeId=officeIdList.get(0);
		}
		return officeId;

	}
	@Override
	public String getOfficeCodeByOfficeId(Integer  officeId)throws CGSystemException {
		String officeCode = null;
		List<String> officeCodeList=null ;
		String queryName = "getDeliveredOfficeCodeByOfficeId";
		String paramNames[] = { "officeId" };
		Object values[] = { officeId};

		officeCodeList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, paramNames, values);
		if(!CGCollectionUtils.isEmpty(officeCodeList)){
			officeCode=officeCodeList.get(0);
		}
		return officeCode;

	}
	@Override
	public Integer getDeliveredOfficeByDeliveryDtl(Long  deliveryDtlsId)throws CGSystemException {
		Integer officeId = null;
		List<Integer> officeIdList=null ;
		String queryName = "getDeliveredOfficeIdForCollection";
		String paramNames[] = { "deliveryDetailId" };
		Object values[] = { deliveryDtlsId};

		officeIdList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, paramNames, values);
		if(!CGCollectionUtils.isEmpty(officeIdList) && !StringUtil.isEmptyInteger(officeIdList.get(0))){
			officeId=officeIdList.get(0);
		}
		return officeId;

	}
	
	
}
