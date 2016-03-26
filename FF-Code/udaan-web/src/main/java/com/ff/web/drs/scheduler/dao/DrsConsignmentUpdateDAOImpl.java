/**
 * 
 */
package com.ff.web.drs.scheduler.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;

/**
 * @author mohammes
 *
 */
public class DrsConsignmentUpdateDAOImpl extends CGBaseDAO implements DrsConsignmentUpdateDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DrsConsignmentUpdateDAOImpl.class);
	/**
	 * DeliveryDetailsDO
	 * @return list DeliveryDetailsDO
	 * @throws CGSystemException
	 */
	@Override
	public List<DeliveryDetailsDO> getDeliveredParentConsgDtls()
			throws CGSystemException {
/*		String queryName = UniversalDeliveryContants.QRY_DRS_DTLS_BY_STATUS;
		String paramNames[] = { UniversalDeliveryContants.QRY_PARAM_DELIVERY_STATUS};
		Object values[] = { UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED};*/
		
		Session session=openTransactionalSession();
		List<DeliveryDetailsDO> deliveredParentCn=null;
		try {
			Query qry = session.getNamedQuery(UniversalDeliveryContants.QRY_DRS_DTLS_BY_STATUS);
			qry.setString(UniversalDeliveryContants.QRY_PARAM_DRS_DELIVERED_STATUS, UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED);
			qry.setParameterList(UniversalDeliveryContants.QRY_PARAM_CN_DELIVERED_STATUS, new String[]{ UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED,CommonConstants.CONSIGNMENT_STATUS_RTO_DRS});
			deliveredParentCn = qry.list();
		} catch (Exception e) {
			LOGGER.error("DrsConsignmentUpdateDAOImpl::getDeliveredParentConsgDtls :: Exception"+e.getLocalizedMessage());
		throw new CGSystemException(e);
		}
		finally{
			closeTransactionalSession(session);
		}
		return deliveredParentCn;
	}
	
	/**
	 * Gets the delivered child consg dtls.
	 *
	 * @return the delivered child consg dtls
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public List<DeliveryDetailsDO> getDeliveredChildConsgDtls()
			throws CGSystemException {
		Session session=openTransactionalSession();
		List<DeliveryDetailsDO> deliveredChildCn=null;
		try {
			Query qry = session.getNamedQuery(UniversalDeliveryContants.QRY_DRS_DTLS_CHILD_CN_BY_STATUS);
			qry.setString(UniversalDeliveryContants.QRY_PARAM_DRS_DELIVERED_STATUS, UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED);
			qry.setParameterList(UniversalDeliveryContants.QRY_PARAM_CN_DELIVERED_STATUS, new String[]{ UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED,CommonConstants.CONSIGNMENT_STATUS_RTO_DRS});
			qry.setMaxResults(5);
			deliveredChildCn = qry.list();
		} catch (Exception e) {
			LOGGER.error("DrsConsignmentUpdateDAOImpl::getDeliveredChildConsgDtls :: Exception"+e.getLocalizedMessage());
		throw new CGSystemException(e);
		}
		finally{
			closeTransactionalSession(session);
		}
		return deliveredChildCn;
	}
	
	/**
	 * Update consg details.
	 *
	 * @param consgDO the consg do
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public void updateConsgDetails(List<DeliveryDetailsDO> consgDO)
			throws CGSystemException {
		Session session=null;
		//Transaction tx=null;
		List<String> parentCnlist= new ArrayList<>(consgDO.size());
		try {
			session = openTransactionalSession();
			//tx =session.beginTransaction();
			//Query qry = session.getNamedQuery(UniversalDeliveryContants.QRY_UPDATE_CONSG);
			for(DeliveryDetailsDO deliverydtlsDO :consgDO){
				String consgNumber=(deliverydtlsDO.getConsignmentDO()!=null?deliverydtlsDO.getConsignmentDO().getConsgNo():deliverydtlsDO.getConsignmentNumber());
				if(!parentCnlist.contains(consgNumber)){
					parentCnlist.add(consgNumber);
					/*String screenCode=deliverydtlsDO.getDeliveryDO().getDrsScreenCode();
					if(!StringUtil.isStringEmpty(screenCode) && screenCode.equalsIgnoreCase(DrsConstants.RTO_COD_SCREEN_CODE)){
						qry.setString(UniversalDeliveryContants.QRY_PARAM_DELIVERY_STATUS,CommonConstants.CONSIGNMENT_STATUS_RTO_DRS);
						deliverydtlsDO.getConsignmentDO().setConsgStatus(CommonConstants.CONSIGNMENT_STATUS_RTO_DRS);
					}else{
						qry.setString(UniversalDeliveryContants.QRY_PARAM_DELIVERY_STATUS,deliverydtlsDO.getDeliveryStatus());
						deliverydtlsDO.getConsignmentDO().setConsgStatus(deliverydtlsDO.getDeliveryStatus());
					}
					deliverydtlsDO.getConsignmentDO().setDeliveryDateTime(deliverydtlsDO.getDeliveryDate());
					deliverydtlsDO.getConsignmentDO().setRecvNameOrCompName(!StringUtil.isStringEmpty(deliverydtlsDO.getReceiverName()) ? deliverydtlsDO.getReceiverName():deliverydtlsDO.getCompanySealSign());
					qry.setTimestamp(UniversalDeliveryContants.QRY_PARAM_DLV_TIME, deliverydtlsDO.getDeliveryDate());
					qry.setString(UniversalDeliveryContants.QRY_PARAM_CONSG,consgNumber);
					int result=qry.executeUpdate();
					LOGGER.trace("DrsConsignmentUpdateDAOImpl::updateConsgDetails :: Update status : ConsgNum:["+deliverydtlsDO.getConsignmentNumber()+"] updateStatus :["+(result>0?"Updated":"not Updated")+"]");
				*/
					
					/**
					 * code commented and added new Code to handle Billing flags at central, these changes confirmed by Somesh/gouri
					 */
					deliverydtlsDO.setConsignmentUpdateFlag(CommonConstants.CONSIGNMENT_STATUS_UPDATED_YES);
					deliverydtlsDO.getDeliveryDO().setDtToCentral(CommonConstants.NO);
				}
			}
			//tx.commit();

		} catch (Exception e) {
			//tx.rollback();
			LOGGER.error("DrsConsignmentUpdateDAOImpl::updateConsgDetails ::Exception ",e);
			throw new CGSystemException(e);
		}finally{
			closeTransactionalSession(session);
		}


	}
	
	@Override
	public void updateConsgDetailsWithPincode(List<ConsignmentDO> consgDO)
			throws CGSystemException {
		Session session=null;
		//Transaction tx=null;
		try {
			session = openTransactionalSession();
			Query qry = session.getNamedQuery("updateConsgDestinforBillingFromDRS");
			for(ConsignmentDO consg :consgDO){
				qry.setInteger("pincodeId",consg.getDestPincodeId().getPincodeId());
				qry.setInteger("consgId",consg.getConsgId());
				int result=qry.executeUpdate();
				LOGGER.trace("DrsConsignmentUpdateDAOImpl::updateConsgDetails :: Update status : ConsgNum:["+consg.getConsgNo()+"] updateStatus :["+(result>0?"Updated":"not Updated")+"]");
			}

		} catch (Exception e) {
			//tx.rollback();
			LOGGER.error("DrsConsignmentUpdateDAOImpl::updateConsgDetails ::Exception ",e);
			throw new CGSystemException(e);
		}finally{
			closeTransactionalSession(session);
		}


	}
	@Override
	public Integer getGetTranshipBytranscityAndDrsCity(Integer deliveryCity,Integer actualDestinationCity)
			throws CGSystemException {
		List<Integer> deliveredChildCn=null;
		try{
		 deliveredChildCn=getHibernateTemplate().findByNamedQueryAndNamedParam("getServicedByIdByTransCityAndDrsCity", new String[]{"transshipmentCityId","servicedCityId"}, new Object[]{deliveryCity,actualDestinationCity});
		} catch (Exception e) {
			LOGGER.error("DrsConsignmentUpdateDAOImpl::getDeliveredChildConsgDtls :: Exception",e);
		throw new CGSystemException(e);
		}
		
		return !CGCollectionUtils.isEmpty(deliveredChildCn)? deliveredChildCn.get(0):null;
	}
	@Override
	public PincodeDO getPincodeByCityId(Integer deliveryCity)
			throws CGSystemException {
		List<PincodeDO> deliveredChildCn=null;
		try{
		 deliveredChildCn=getHibernateTemplate().findByNamedQueryAndNamedParam("getPincodeByCityId", new String[]{"cityId"}, new Object[]{deliveryCity});
		} catch (Exception e) {
			LOGGER.error("DrsConsignmentUpdateDAOImpl::getDeliveredChildConsgDtls :: Exception",e);
		throw new CGSystemException(e);
		}
		
		return !CGCollectionUtils.isEmpty(deliveredChildCn)? deliveredChildCn.get(0):null;
	}
}
