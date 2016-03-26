package com.ff.web.drs.updation.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.util.DrsUtil;


/**
 * @author mohammes
 * The Class PendingDrsDAOImpl.
 */
public class PendingDrsDAOImpl extends CGBaseDAO implements PendingDrsDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PendingDrsDAOImpl.class);
	
	
	
	/**
	 * Update undelivered drs consg.
	 *
	 * @param deliveryDO the delivery do
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Boolean updateUndeliveredDrsConsg(DeliveryDO deliveryDO) throws CGSystemException {
		Boolean result=Boolean.FALSE;
		Session session = null;
		Transaction tx=null;
		try {
			session = createSession();
			tx =session.beginTransaction();
			prepareDeliveryHeaderQuery(deliveryDO,session);
			for(DeliveryDetailsDO details:deliveryDO.getDeliveryDtlsDO()){
				updateNonDeliveryDetails(details,session);
			}
			tx.commit();
			result=Boolean.TRUE;
		} catch (Exception e) {
			tx.rollback();
			LOGGER.error("PendingDrsDAOImpl::updateUndeliveredDrsConsg ::Exception ",e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		
		return result;
	}
	
	/**
	 * Prepare delivery header query.
	 *
	 * @param deliveryDO the delivery do
	 * @param session the session
	 * @return the int
	 */
	private int prepareDeliveryHeaderQuery(DeliveryDO deliveryDO,Session session) {
		//deliveryDO.setDrsStatus(DrsConstants.DRS_STATUS_UPDATED);
		return DrsUtil.prepareDeliveryHeaderQuery(deliveryDO, session);
	}
	
	/**
	 * Discard delivery details.
	 *
	 * @param deliveryDtlsDO the delivery dtls do
	 * @param session the session
	 */
	private void updateNonDeliveryDetails(DeliveryDetailsDO deliveryDtlsDO,Session session){
		List<DeliveryDetailsDO> detailsDOList=null;
		Query qry = session.getNamedQuery(DrsCommonConstants.QRY_GET_DRS_DTLS_BY_DETAILID);
		qry.setLong(DrsCommonConstants.QRY_PARAM_DELIVERY_DTL_ID, deliveryDtlsDO.getDeliveryDetailId());
		detailsDOList= qry.list();
		if(detailsDOList!=null){
			DeliveryDetailsDO detailsDO=detailsDOList.get(0);
			detailsDO.setDeliveryStatus(deliveryDtlsDO.getDeliveryStatus());
			detailsDO.setReasonDO(deliveryDtlsDO.getReasonDO());
			detailsDO.setMissedCardNumber(deliveryDtlsDO.getMissedCardNumber());
			detailsDO.setDeliveryStatus(deliveryDtlsDO.getDeliveryStatus());
			detailsDO.setDeliveryType(deliveryDtlsDO.getDeliveryType());
			detailsDO.setRemarks(deliveryDtlsDO.getRemarks());
			detailsDO.setTransactionModifiedDate(DateUtil.getCurrentDate());
			session.update(detailsDO);
		}

	}
}
