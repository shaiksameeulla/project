/**
 * 
 */
package com.ff.web.drs.updation.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.booking.BulkBookingVendorDtlsDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.universe.manifest.constant.ManifestUniversalConstants;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.util.DrsUtil;

/**
 * @author mohammes
 *
 */
public class RtoCodDrsDAOImpl extends CGBaseDAO implements
RtoCodDrsDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RtoCodDrsDAOImpl.class);

	/**
	 * Update delivered drs for dox.
	 *
	 * @param deliveryDo the delivery do
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public	Boolean updateDeliveredDrs(DeliveryDO deliveryDo)throws CGBusinessException, CGSystemException{
		Boolean result=Boolean.FALSE;
		Session session = null;
		Transaction tx=null;
		try {
			session = createSession();
			tx =session.beginTransaction();
			updateDeliveryHeader(deliveryDo,session);
			Date transactionModifiedDate=Calendar.getInstance().getTime();
			if(!CGCollectionUtils.isEmpty(deliveryDo.getDeliveryDtlsDO())){
				for(DeliveryDetailsDO details:deliveryDo.getDeliveryDtlsDO()){
					details.setTransactionModifiedDate(transactionModifiedDate);
					updateDeliveredDetails(details,session);
				}
			}
			tx.commit();
			result=Boolean.TRUE;
		} catch (Exception e) {
			tx.rollback();
			LOGGER.error("RtoCodDrsDAOImpl::modifyDrs ::Exception ",e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}

		return result;
	}

	/**
	 * Update delivered details.
	 *
	 * @param deliveryDtlsDO the delivery dtls do
	 * @param session the session
	 */
	private void updateDeliveredDetails(DeliveryDetailsDO deliveryDtlsDO,Session session){
		List<DeliveryDetailsDO> detailsDOList=null;
		Date transactionModifiedDate=deliveryDtlsDO.getTransactionModifiedDate();
		Query qry = session.getNamedQuery(DrsCommonConstants.QRY_GET_DRS_DTLS_BY_DETAILID);
		qry.setLong(DrsCommonConstants.QRY_PARAM_DELIVERY_DTL_ID, deliveryDtlsDO.getDeliveryDetailId());
		detailsDOList= qry.list();
		if(!CGCollectionUtils.isEmpty(detailsDOList)){
			DeliveryDetailsDO detailsDO=detailsDOList.get(0);
			detailsDO.setDeliveryStatus(deliveryDtlsDO.getDeliveryStatus());
			detailsDO.setDeliveryDate(deliveryDtlsDO.getDeliveryDate());
			
			if(!StringUtil.isNull(deliveryDtlsDO.getReasonDO())){
				detailsDO.setReasonDO(deliveryDtlsDO.getReasonDO());
				detailsDO.setRemarks(deliveryDtlsDO.getRemarks());
			}
			detailsDO.setTransactionModifiedDate(transactionModifiedDate);
			session.update(detailsDO);
		}
	}

	/**
	 * Prepare delivery header query.
	 *
	 * @param deliveryDo the delivery do
	 * @param session the session
	 * @return the int
	 */
	private int updateDeliveryHeader(DeliveryDO deliveryDo,Session session){
		return DrsUtil.prepareDeliveryHeaderQuery(deliveryDo, session);
	}

	/**
	 * Gets the manifested consg details.
	 *
	 * @param consgNumber the consg number
	 * @param manifestStatus the manifest status
	 * @param originCityId the origin city id
	 * @return the manifested consg details
	 * @throws CGSystemException 
	 */
	@Override
	public  ConsignmentDO getManifestedConsgDetails(String consgNumber,String manifestStatus,Integer originCityId) throws CGSystemException{
		String qryName=DrsCommonConstants.QRY_MNFSTED_CONSG_DTLS_FOR_RTO_DRS;
		List<ConsignmentDO> manifestedConsg;
		Session session=createSession();
		try {
			Query qry = session.getNamedQuery(qryName);
			qry.setParameterList(ManifestUniversalConstants.MANIFEST_TYPE, manifestStatus.split(FrameworkConstants.CHARACTER_COMMA));
			qry.setString(UniversalDeliveryContants.QRY_PARAM_CONSG, consgNumber);
			qry.setInteger(DrsCommonConstants.QRY_PARAM_OFFICEID, originCityId);
			manifestedConsg= qry.list();
		}catch(Exception e){
			LOGGER.error("RtoCodDrsDAOImpl::getManifestedChildConsgDetails ::Exception ",e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		
		return !CGCollectionUtils.isEmpty(manifestedConsg) ? manifestedConsg
				.get(0) : null;

	}
	
	/**
	 * Gets the manifested child consg details.
	 *
	 * @param consgNumber the consg number
	 * @param manifestStatus the manifest status
	 * @param originCityId the origin city id
	 * @return the manifested child consg details
	 * @throws CGSystemException 
	 */
	@Override
	public  String getManifestedChildConsgDetails(String consgNumber,String manifestStatus,Integer originCityId) throws CGSystemException{
		String qryName=DrsCommonConstants.QRY_MNFSTED_CHILD_CONSG_DTLS_FOR_RTO_DRS;
		List<String> manifestedConsg;
		Session session=createSession();
		try {
			Query qry = session.getNamedQuery(qryName);
			qry.setParameterList(ManifestUniversalConstants.MANIFEST_TYPE, manifestStatus.split(FrameworkConstants.CHARACTER_COMMA));
			qry.setString(UniversalDeliveryContants.QRY_PARAM_CONSG, consgNumber);
			qry.setInteger(DrsCommonConstants.QRY_PARAM_OFFICEID, originCityId);
			manifestedConsg= qry.list();
		}catch(Exception e){
			LOGGER.error("RtoCodDrsDAOImpl::getManifestedChildConsgDetails ::Exception ",e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		return !CGCollectionUtils.isEmpty(manifestedConsg) ? manifestedConsg
				.get(0) : null;

	}
	
	/**
	 * Gets the customer dtls by consg num frm bkng.
	 *
	 * @param consgNumber the consg number
	 * @return the customer dtls by consg num frm bkng
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public  CustomerDO getCustomerDtlsByConsgNumFrmBkng(String consgNumber) throws CGSystemException{
		List<CustomerDO> customerDtls;
		String qryName=DrsCommonConstants.QRY_BOOKING_CUSTOMER_DTLS;
		String params[]={UniversalDeliveryContants.QRY_PARAM_CONSG};
		Object value[] = {consgNumber};
		customerDtls=getHibernateTemplate().findByNamedQueryAndNamedParam(qryName,params, value);
		return !CGCollectionUtils.isEmpty(customerDtls) ? customerDtls
				.get(0) : null;

	}
	
	/**
	 * Gets the venodr dtls by consg num frm bkng.
	 *
	 * @param consgNumber the consg number
	 * @return the venodr dtls by consg num frm bkng
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public  BulkBookingVendorDtlsDO getVendorDtlsByConsgNumFrmBkng(String consgNumber) throws CGSystemException{
		List<BulkBookingVendorDtlsDO> vendorDtls;
		String qryName=DrsCommonConstants.QRY_BOOKING_VENDOR_DTLS;
		String params[]={UniversalDeliveryContants.QRY_PARAM_CONSG};
		Object value[] = {consgNumber};
		vendorDtls=getHibernateTemplate().findByNamedQueryAndNamedParam(qryName,params, value);
		return !CGCollectionUtils.isEmpty(vendorDtls) ? vendorDtls
				.get(0) : null;

	}
	
}
