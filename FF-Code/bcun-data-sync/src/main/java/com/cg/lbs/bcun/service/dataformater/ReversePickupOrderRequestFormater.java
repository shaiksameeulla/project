/**
 * 
 */
package com.cg.lbs.bcun.service.dataformater;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.constant.BcunDataFormaterConstants;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.pickup.BcunReversePickupOrderBranchMappingDO;
import com.ff.domain.pickup.BcunReversePickupOrderDetailDO;
import com.ff.domain.pickup.BcunReversePickupOrderHeaderDO;

/**
 * @author uchauhan
 * 
 */
public class ReversePickupOrderRequestFormater extends AbstractDataFormater {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReversePickupOrderRequestFormater.class);
	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		return formatUpdateData(baseDO, bcunService);
	}

	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		LOGGER.info("ReversePickupOrderRequestFormater:: Outbound :: START");
		BcunReversePickupOrderBranchMappingDO mappingDO = (BcunReversePickupOrderBranchMappingDO) baseDO;
		BcunReversePickupOrderDetailDO orderDetailDO = mappingDO.getPickupOrderDetail();
		BcunReversePickupOrderHeaderDO revHeaderDO = mappingDO.getPickupOrderDetail().getHeaderId();
		String orderNum = mappingDO.getPickupOrderDetail().getOrderNumber();
		try {
			Integer revPickupBranchMapId = null;			
			//set header id
			revHeaderDO.setRequestHeaderID(getRevPickupHeaderId(bcunService, revHeaderDO));
			orderDetailDO.setHeaderId(revHeaderDO);
			//set detail id
			orderDetailDO.setDetailId(getRevPickupDetailId(bcunService, mappingDO));
			//set branch map id
			revPickupBranchMapId = getRevPickupBranchMapId(bcunService,mappingDO);
			mappingDO.setId(revPickupBranchMapId);
			if(!StringUtil.isEmptyInteger(revPickupBranchMapId)){
				List<CGBaseDO> baseList = getRevPickupBranchesByOrderNo(bcunService, mappingDO);
//			**********************************************************
					// Get the Branch Office ID
					// Get the Confirmation Status
					String confirmationStatus = mappingDO.getBranchOrderRequestStatus();
					Integer officeConfirmedIn = mappingDO.getOrderAssignedBranch();
					boolean malicious = false;
					// If the Confirmation Status = 'A' (Accepted)
					if (StringUtils.equalsIgnoreCase(confirmationStatus, "A")) {
						// Determine whether the same order is confirmed in Another
						// Branch
						// Traverse the list of Confirmation statuses for the
						// different
						// Branches in the Central
						for (CGBaseDO tempBaseDO : baseList) {
							// get each element
							BcunReversePickupOrderBranchMappingDO bcunRevPickupOrderBranchMapDO = (BcunReversePickupOrderBranchMappingDO) tempBaseDO;
							// if the Branch in input is not the same as Branch in
							// this
							// element
							if (bcunRevPickupOrderBranchMapDO
									.getOrderAssignedBranch().intValue() != officeConfirmedIn
									.intValue()) {
								// If the pickup order is already confirmed in
								// another
								// branch
								if (StringUtils
										.equalsIgnoreCase(
												bcunRevPickupOrderBranchMapDO
														.getBranchOrderRequestStatus(),
												"A")) {
									malicious = true;
								} else {
									// Else Mark the Pickup Orders as Declined
									bcunRevPickupOrderBranchMapDO
											.setBranchOrderRequestStatus("D");
									// Transmit the data
									bcunRevPickupOrderBranchMapDO
											.setDtToBranch("N");
								}
							}
						}
						if (malicious) {
							mappingDO.setBranchOrderRequestStatus("M");
						}
						bcunService.saveOrUpdateTransferedEntities(baseList);
					}
				}
//				**********************************************************
				
				
		} catch (Exception e) {
			LOGGER.error(
					"ReversePickupOrderRequestFormater:: Outbound:: Exception for ORDER NUMBER :[" + orderNum + "]", e);
			throw e;
		}
		LOGGER.info("ReversePickupOrderRequestFormater:: Outbound :: END");
		return mappingDO;
	}
	
	private Integer getRevPickupHeaderId(BcunDatasyncService bcunService,
			BcunReversePickupOrderHeaderDO revHeaderDO){
		String[] params = { "originatingOffice", "requestDate", "deliveryOffice", "customer" };
		Object[] values = { revHeaderDO.getOriginatingOffice(),revHeaderDO.getRequestDate(),revHeaderDO.getDeliveryOffice(),revHeaderDO.getCustomer() };
		Integer revPickupHeaderId = bcunService.getUniqueId("getRevPickupHeaderId", params, values);
		LOGGER.debug("ReversePickupOrderRequestFormater:: getRevPickupHeaderId :: [ORIGIN OFFICE - REQUEST DATE - DELIVERY OFFICE - CUSTOMER] :" + values +"----->"+revPickupHeaderId);
		return revPickupHeaderId;
	}
	private Integer getRevPickupDetailId(BcunDatasyncService bcunService,
			BcunReversePickupOrderBranchMappingDO mappingDO){
		String[] params = { "orderNumber" };
		Object[] values = { mappingDO.getPickupOrderDetail().getOrderNumber() };
		Integer revPickupDetailId = bcunService.getUniqueId(BcunDataFormaterConstants.QRY_GET_REVERSE_PICKUP_REQUEST_DETAIL_ID, params, values);
		LOGGER.debug("ReversePickupOrderRequestFormater:: getRevPickupDetailId :: [ORDER NUMBER] :" + values +"----->"+revPickupDetailId);
		return revPickupDetailId;
	}
	private Integer getRevPickupBranchMapId(BcunDatasyncService bcunService,
			BcunReversePickupOrderBranchMappingDO mappingDO){
		String[] params = { "orderNumber","assignedBranchId" };
		Object[] values = { mappingDO.getPickupOrderDetail().getOrderNumber(), mappingDO.getOrderAssignedBranch()};
		Integer revPickupBranchMapId = bcunService.getUniqueId("getRevPickupBranchMapId", params, values);
		LOGGER.debug("ReversePickupOrderRequestFormater:: getRevPickupBranchMapId :: [ORDER NUMBER - ASSIGNED BRANCH ID] :" + values +"----->"+revPickupBranchMapId);
		return revPickupBranchMapId;
	}
	private List<CGBaseDO> getRevPickupBranchesByOrderNo(BcunDatasyncService bcunService,
			BcunReversePickupOrderBranchMappingDO mappingDO){
		String[] params = { "ordernum"};
		Object[] values = { mappingDO.getPickupOrderDetail().getOrderNumber()};
		@SuppressWarnings("unchecked")
		List<CGBaseDO> baseList = (List<CGBaseDO>) bcunService.getDataByNamedQueryAndNamedParam("getRevPickupOrdersByOrderNo", params, values);
		return baseList;
	}
}
