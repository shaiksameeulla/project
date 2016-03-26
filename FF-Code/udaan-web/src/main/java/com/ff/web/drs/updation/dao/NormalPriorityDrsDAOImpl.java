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

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.util.DrsUtil;

/**
 * @author mohammes
 *
 */
public class NormalPriorityDrsDAOImpl extends CGBaseDAO implements
		NormalPriorityDrsDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(NormalPriorityDrsDAOImpl.class);
	
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
			LOGGER.error("NormalPriorityDrsDAOImpl::updateDeliveredDrs ::Exception ",e);
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
			if(!StringUtil.isStringEmpty(deliveryDtlsDO.getReceiverName())){
			detailsDO.setReceiverName(deliveryDtlsDO.getReceiverName());
			detailsDO.setContactNumber(deliveryDtlsDO.getContactNumber());
			}
			if(!StringUtil.isStringEmpty(deliveryDtlsDO.getCompanySealSign())){
			detailsDO.setCompanySealSign(deliveryDtlsDO.getCompanySealSign());
			}
			detailsDO.setDeliveryStatus(deliveryDtlsDO.getDeliveryStatus());
			detailsDO.setDeliveryType(deliveryDtlsDO.getDeliveryType());
			if(!StringUtil.isNull(deliveryDtlsDO.getRelationDO())){
				detailsDO.setRelationDO(deliveryDtlsDO.getRelationDO());
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
	

}
