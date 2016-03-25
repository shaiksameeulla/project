/**
 * 
 */
package com.cg.lbs.bcun.service.dataformater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.cg.lbs.bcun.constant.BcunDataFormaterConstants;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.pickup.ReversePickupOrderBranchMappingDO;
import com.ff.domain.pickup.ReversePickupOrderDetailDO;
import com.ff.domain.pickup.ReversePickupOrderHeaderDO;

/**
 * @author uchauhan
 * 
 */
public class ReversePickupOrderRequestInboundFormater extends AbstractDataFormater {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReversePickupOrderRequestInboundFormater.class);
	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		return formatUpdateData(baseDO, bcunService);
	}

	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		LOGGER.info("ReversePickupOrderRequestInboundFormater:: Inbound :: START");
		ReversePickupOrderHeaderDO headerDO = (ReversePickupOrderHeaderDO) baseDO;
		try {
			//set header id
			Integer revPickupHeaderId = getRevPickupHeaderId(bcunService, headerDO);			
			headerDO.setRequestHeaderID(revPickupHeaderId);
			
			//set detail id
			for (ReversePickupOrderDetailDO detailDO : headerDO.getDetailsDO()) {
				detailDO.getPickupOrderHeader().setRequestHeaderID(revPickupHeaderId);
				detailDO.setDetailId(getRevPickupOrderDetailId(bcunService, detailDO));
				
				for (ReversePickupOrderBranchMappingDO mappingDO : detailDO.getBranchesAssignedDO()) {
					mappingDO.setPickupOrderDetail(detailDO);
					mappingDO.setId(getRevPickupBranchMapId(bcunService, mappingDO));
				}
			}				
		} catch (Exception e) {
			LOGGER.error(
					"ReversePickupOrderRequestInboundFormater:: Inbound :: Exception [ORIGIN OFFICE - REQUEST DATE - DELIVERY OFFICE - CUSTOMER] :[" 
							+ headerDO.getOriginatingOffice()+" - "+headerDO.getRequestDate()+" - "+headerDO.getDeliveryOffice()+" - "+headerDO.getCustomer() + "]", e);
			throw e;
		}
		LOGGER.info("ReversePickupOrderRequestInboundFormater:: Inbound :: END");
		return headerDO;
	}
	
	private Integer getRevPickupHeaderId(BcunDatasyncService bcunService,
			ReversePickupOrderHeaderDO headerDO){
		String[] params = { "originatingOffice", "requestDate", "deliveryOffice", "customer" };
		Object[] values = { headerDO.getOriginatingOffice(),headerDO.getRequestDate(),headerDO.getDeliveryOffice(),headerDO.getCustomer() };
		Integer revPickupHeaderId = bcunService.getUniqueId("getRevPickupHeaderId", params, values);
		LOGGER.debug("ReversePickupOrderRequestFormater:: getRevPickupHeaderId :: [ORIGIN OFFICE - REQUEST DATE - DELIVERY OFFICE - CUSTOMER] :" + values +"----->"+revPickupHeaderId);
		return revPickupHeaderId;
	}
	private Integer getRevPickupOrderDetailId(BcunDatasyncService bcunService,
			ReversePickupOrderDetailDO detailDO){
		String[] params = { "orderNumber" };
		Object[] values = {detailDO.getOrderNumber() };
		Integer revPickupDetailId = bcunService.getUniqueId(BcunDataFormaterConstants.QRY_GET_REVERSE_PICKUP_REQUEST_DETAIL_ID, params, values);
		LOGGER.debug("ReversePickupOrderRequestFormater:: getRevPickupOrderDetailId :: [ORDER NUMBER] :" + values +"----->"+revPickupDetailId);
		return revPickupDetailId;
	}
	private Integer getRevPickupBranchMapId(BcunDatasyncService bcunService,
			ReversePickupOrderBranchMappingDO mappingDO){
		String[] params = { "orderNumber","assignedBranchId" };
		Object[] values = { mappingDO.getPickupOrderDetail().getOrderNumber(), mappingDO.getOrderAssignedBranch()};
		Integer revPickupBranchMapId = bcunService.getUniqueId("getRevPickupBranchMapId", params, values);
		LOGGER.debug("ReversePickupOrderRequestFormater:: getRevPickupBranchMapId :: [ORDER NUMBER - ASSIGNED BRANCH ID] :" + values +"----->"+revPickupBranchMapId);
		return revPickupBranchMapId;
	}	
}
