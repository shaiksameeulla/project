package com.ff.web.pickup.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.pickup.ReversePickupOrderDetailDO;
import com.ff.domain.ratemanagement.masters.ContractPaymentBillingLocationDO;
import com.ff.pickup.PickupOrderDetailsTO;
import com.ff.web.pickup.constants.PickupManagementConstants;

public class PickupGatewayDAOImpl extends CGBaseDAO implements PickupGatewayDAO {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PickupGatewayDAOImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public ReversePickupOrderDetailDO getReversePickupOrderDetail(PickupOrderDetailsTO detailTO)
			throws CGSystemException {
		LOGGER.trace("PickupGatewayDAOImpl :: getReversePickupOrderDetail() :: Start --------> ::::::");
		List<ReversePickupOrderDetailDO> detailsDO = null;
		ReversePickupOrderDetailDO detailDO= null;
		try{
			if(!StringUtil.isEmptyInteger(detailTO.getDetailId())){
				detailsDO = getHibernateTemplate().findByNamedQueryAndNamedParam("getPickupOrderDetailById","detailId",detailTO.getDetailId());
			}
			else if(StringUtils.isNotEmpty(detailTO.getOrderNumber())){
				detailsDO = getHibernateTemplate().findByNamedQueryAndNamedParam("getPickupOrderDetail","orderNum",detailTO.getOrderNumber());
			}			
			if(!StringUtil.isEmptyList(detailsDO)){
				detailDO = detailsDO.get(0);
			}
		}catch(Exception e ){
			LOGGER.error("ERROR::PickupGatewayDAOImpl - getReversePickupOrderDetail() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PickupGatewayDAOImpl :: getReversePickupOrderDetail() :: End --------> ::::::");
		return detailDO;
	}
	@SuppressWarnings("unchecked")
	@Override
	public ContractPaymentBillingLocationDO getContractPayBillingLocationDtlsBypickupLocation(
			Integer pickupDlvLocId) throws CGSystemException {
		LOGGER.debug("PickupGatewayDAOImpl :: getContractPayBillingLocationDtlsBypickupLocation :: Start");
		List<ContractPaymentBillingLocationDO> contractPaymentBillingLocationDOs = null;
		ContractPaymentBillingLocationDO contractPaymentBillingLocationDO = null;
		try{
			contractPaymentBillingLocationDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					PickupManagementConstants.QRY_GET_CONTRACT_PAY_BILLING_LOCATION_DTLS,
					PickupManagementConstants.PICKUP_DELIVERY_LOCATION, pickupDlvLocId);
			
			if(!StringUtil.isEmptyColletion(contractPaymentBillingLocationDOs)){
				contractPaymentBillingLocationDO = contractPaymentBillingLocationDOs.get(0);
			}
		}catch(Exception e){
			LOGGER.error("ERROR : PickupGatewayDAOImpl.getContractPayBillingLocationDtlsBypickupLocation",e);
			throw new CGSystemException(e);	
		}
		LOGGER.debug("PickupGatewayDAOImpl :: getContractPayBillingLocationDtlsBypickupLocation :: End");
		return contractPaymentBillingLocationDO;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getShippedToCodeByLocationId(
			Integer pickupDlvLocId) throws CGSystemException {
		LOGGER.debug("PickupGatewayDAOImpl :: getShippedToCodeByLocationId :: Start");
		String shippedToCode = null;
		Session session = null;
		try{
			session = createSession();
			Query qry = session.getNamedQuery("getShippedToCodeByLocationId");
			qry.setInteger(PickupManagementConstants.PICKUP_DELIVERY_LOCATION, pickupDlvLocId);
			qry.setMaxResults(1);
			List<String> shippedToCodes = qry.list();
			
			shippedToCode = !StringUtil.isEmptyList(shippedToCodes) ? shippedToCodes.get(0) : null;
		}catch(Exception e){
			LOGGER.error("ERROR : PickupGatewayDAOImpl.getShippedToCodeByLocationId for PickupDeliveryLocationID :: "+pickupDlvLocId,e);
			throw new CGSystemException(e);	
		}finally{
			session.close();
		}
		LOGGER.debug("PickupGatewayDAOImpl :: getShippedToCodeByLocationId :: End");
		return shippedToCode;
	}
	@Override
	public String getLatestShipToCodeByCustomer(Integer officeId,
			Integer customerId) throws CGSystemException {
		LOGGER.debug("PickupGatewayDAOImpl :: getLatestShipToCodeByCustomer :: Start");
		String shippedToCode = null;
		Session session = null;
		try{
			session = createSession();
			Query query = session.createSQLQuery(PickupManagementConstants.QRY_GET_LATEST_SHIP_TO_CODE_BY_CUSTOMER);			
			query.setInteger("officeId", officeId);
			query.setInteger("customerId", customerId);
			List<String> shippedToCodes = query.list();
			
			shippedToCode = !StringUtil.isEmptyList(shippedToCodes) ? shippedToCodes.get(0) : null;
		}catch(Exception e){
			LOGGER.error("ERROR : PickupGatewayDAOImpl.getLatestShipToCodeByCustomer for Customer :: "+customerId +"Office : "+officeId,e);
			throw new CGSystemException(e);	
		}finally{
			closeSession(session);
		}
		LOGGER.debug("PickupGatewayDAOImpl :: getLatestShipToCodeByCustomer :: End");
		return shippedToCode;
	}
}
