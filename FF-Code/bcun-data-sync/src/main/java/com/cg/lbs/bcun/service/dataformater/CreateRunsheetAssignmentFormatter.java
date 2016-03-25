package com.cg.lbs.bcun.service.dataformater;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.GlobalErrorCodeConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.constant.BcunDataFormaterConstants;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.pickup.BcunRunsheetAssignmentDetailDO;
import com.ff.domain.pickup.BcunRunsheetAssignmentHeaderDO;
import com.ff.domain.pickup.ReversePickupForAssignmentDetailDO;
import com.ff.domain.pickup.ReversePickupOrderDetailDO;
import com.ff.domain.pickup.ReversePickupOrderHeaderDO;

public class CreateRunsheetAssignmentFormatter extends AbstractDataFormater{
	private final static Logger LOGGER = LoggerFactory.getLogger(CreateRunsheetAssignmentFormatter.class);
	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) throws CGBusinessException {
		return formatUpdateData(baseDO, bcunService);
	}

	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) throws CGBusinessException {
		LOGGER.debug("CreateRunsheetAssignmentFormatter:: START------------->");
		BcunRunsheetAssignmentHeaderDO headerDO = (BcunRunsheetAssignmentHeaderDO)baseDO;
		
		try{
			Integer assignmentHeaderId = null;
			boolean isDataToProcess = Boolean.FALSE;
			BcunRunsheetAssignmentHeaderDO bcunRunsheetAssignmentHeaderDO = null;
			
			//get latest branch data to be processed
			String[] params = { BcunDataFormaterConstants.QRY_PARAM_CREATED_AT_OFFICE_ID,BcunDataFormaterConstants.QRY_PARAM_CREATED_FOR_OFFICE_ID,
									BcunDataFormaterConstants.QRY_PARAM_EMPLOYEE_FIELD_STAFF_ID, BcunDataFormaterConstants.QRY_PARAM_PICKUP_ASSIGNMENT_TYPE, BcunDataFormaterConstants.QRY_PARAM_PICKUP_ASSIGNMENT_CREATED_DATE };
			Object[] values = { headerDO.getOfficeCreatedAt(), headerDO.getOfficeCreatedFor(),headerDO.getEmployeeFieldStaff(), headerDO.getPickupAssignmentType(), headerDO.getCreatedDate() };
			@SuppressWarnings("unchecked")
			List<CGBaseDO> baseList = (List<CGBaseDO>) bcunService.getDataByNamedQueryAndNamedParam("getAssignmentHeader", params, values);
						
			if(!StringUtil.isEmptyColletion(baseList)){
				//central data
				bcunRunsheetAssignmentHeaderDO = (BcunRunsheetAssignmentHeaderDO)(baseList.get(0));
				assignmentHeaderId = bcunRunsheetAssignmentHeaderDO.getPickupAssignmentHeaderId();
				//Compare central data with branch data whether its already updated or need to update now
				if((bcunRunsheetAssignmentHeaderDO.getUpdatedDate()!=null && headerDO.getUpdatedDate() != null)
						&&(bcunRunsheetAssignmentHeaderDO.getUpdatedDate()).compareTo(headerDO.getUpdatedDate())<0){
					isDataToProcess = Boolean.TRUE;
				}else if(StringUtils.equalsIgnoreCase(headerDO.getRunsheetAssignmentStatus(), BcunDataFormaterConstants.PICKUP_ASSIGNMENT_GENERATED)){
					isDataToProcess = Boolean.TRUE;
				}				
			}else{
				//New entry
				isDataToProcess = Boolean.TRUE;
			}
			LOGGER.debug("CreateRunsheetAssignmentFormatter:: getAssignmentHeader :: [CREATED_AT_OFFICE - CREATED_FOR_OFFICE - EMPLOYEE - PICKUP_ASSIGNMENT_TYPE - CREATED_DATE] : [" 
						+ headerDO.getOfficeCreatedAt()+", "+ headerDO.getOfficeCreatedFor()+", "+headerDO.getEmployeeFieldStaff()+", "+headerDO.getPickupAssignmentType()+", "+ headerDO.getCreatedDate()+ "] -----> "+assignmentHeaderId);
			if(isDataToProcess){
				//Setting Assignment Header Id
				headerDO.setPickupAssignmentHeaderId(assignmentHeaderId);
			} else {
				headerDO = bcunRunsheetAssignmentHeaderDO;			}
			
				//Process assignment details
				Set<BcunRunsheetAssignmentDetailDO> mappedAssignmentDtailDOs = null;
				if(!StringUtil.isEmptyColletion(headerDO.getAssignmentDetails())){
					mappedAssignmentDtailDOs = new HashSet<BcunRunsheetAssignmentDetailDO>();
					for(BcunRunsheetAssignmentDetailDO detailDO : headerDO.getAssignmentDetails()){
						Integer assignmentDetailId=null;
						Integer pickupDlvLocationId=null;
						String orderNo=null;

						detailDO.setPickupAssignmentHeader(headerDO);
						//If the pickup type is R - reverse then only reverse pickup order detail will be required.
						if(StringUtils.equalsIgnoreCase(detailDO.getPickupType(), BcunDataFormaterConstants.PICKUP_TYPE_REVERSE)){
							Integer revPickupRequestDtlId=null;
							//Setting Reverse Pickup Detail Id
							ReversePickupForAssignmentDetailDO revPickupRequestDtlDO = detailDO.getReversePickupCustomer();
							if(!StringUtil.isNull(revPickupRequestDtlDO)){
								orderNo = revPickupRequestDtlDO.getOrderNumber();
								
								String[] params3 = { BcunDataFormaterConstants.QRY_PARAM_ORDER_NUMBER};
								Object[] values3 = { orderNo };	
								
								revPickupRequestDtlId = bcunService.getUniqueId(BcunDataFormaterConstants.QRY_GET_REVERSE_PICKUP_REQUEST_DETAIL_ID, params3, values3);
								if(!StringUtil.isEmptyInteger(revPickupRequestDtlId)){
									revPickupRequestDtlDO.setDetailId(revPickupRequestDtlId);
									detailDO.setReversePickupCustomer(revPickupRequestDtlDO);
								}else{
									LOGGER.debug("CreateRunsheetAssignmentFormatter:: Reprocess : ORDER NUMBER : ["+orderNo+"] :: Not found for Reverse Pickup.");
									
									ReversePickupOrderHeaderDO reversePickupOrderHeaderDO = revPickupRequestDtlDO.getPickupOrderHeader();
									ReversePickupOrderRequestInboundFormater pickupOrderRequestInboundFormater = new ReversePickupOrderRequestInboundFormater();
									CGBaseDO cgBaseRevPickupOrderHeaderDO = pickupOrderRequestInboundFormater.formatInsertData(reversePickupOrderHeaderDO, bcunService);
									List<CGBaseDO> revPickupOrderHeaderList = new ArrayList<CGBaseDO>();
									revPickupOrderHeaderList.add(cgBaseRevPickupOrderHeaderDO);
									bcunService.saveOrUpdateTransferedEntities(revPickupOrderHeaderList);
										
									ReversePickupOrderHeaderDO revPkupHeaderDO = (ReversePickupOrderHeaderDO) cgBaseRevPickupOrderHeaderDO;
									for (ReversePickupOrderDetailDO orderDetailDO : revPkupHeaderDO.getDetailsDO()) {
										if (StringUtils.equalsIgnoreCase(orderNo, orderDetailDO.getOrderNumber())) {
											revPickupRequestDtlId = orderDetailDO.getDetailId();
											revPickupRequestDtlDO.setDetailId(revPickupRequestDtlId);
											detailDO.setReversePickupCustomer(revPickupRequestDtlDO);
											break;
										}
									}
								}
							}
						}else{
							pickupDlvLocationId = detailDO.getPickupDlvLocation();
						}
						//Setting Assignment Detail Id
						if(!StringUtil.isEmptyInteger(assignmentHeaderId)){
							String[] params2 = { BcunDataFormaterConstants.QRY_PARAM_ORDER_NUMBER, BcunDataFormaterConstants.QRY_PARAM_PICKUP_LOCATION_ID, BcunDataFormaterConstants.QRY_PARAM_PICKUP_ASSIGNMENT_HEADER_ID};
							Object[] values2 = {orderNo, pickupDlvLocationId, assignmentHeaderId};
							LOGGER.debug("CreateRunsheetAssignmentFormatter:: Assignment detail Id Key :: [ASSIGNMENT HEADER ID - PICKUP LOCATION - ORDER NUMBER]: ["+assignmentHeaderId +" - "+pickupDlvLocationId+" - "+ orderNo+"]");
							assignmentDetailId = bcunService.getUniqueId(BcunDataFormaterConstants.QRY_GET_ASSIGNMENT_DETAIL_ID, params2, values2);
						}
						detailDO.setPickupAssignmentDtlId(assignmentDetailId);
						mappedAssignmentDtailDOs.add(detailDO);
					}
					headerDO.getAssignmentDetails().addAll(mappedAssignmentDtailDOs);
				}
			
				if(StringUtil.isEmptyColletion(headerDO.getAssignmentDetails())){
					LOGGER.debug("ERROR : CreateRunsheetAssignmentFormatter::  If Assignment details are not available Don't save Runsheet Assignment data");
					throw new CGBusinessException(GlobalErrorCodeConstants.BUSSINESS_VIOLATION_ERROR);
				}
			
		}catch(Exception e){
			LOGGER.error("CreateRunsheetAssignmentFormatter:: [CREATED_AT_OFFICE - CREATED_FOR_OFFICE - EMPLOYEE - PICKUP_ASSIGNMENT_TYPE - CREATED_DATE] ::["
					+headerDO.getOfficeCreatedAt()+" - "+headerDO.getOfficeCreatedFor()+" - "+headerDO.getEmployeeFieldStaff()+" - "+headerDO.getPickupAssignmentType()+" - "+headerDO.getCreatedDate()+"]",e);
			throw e;
		}
		LOGGER.debug("CreateRunsheetAssignmentFormatter:: END------------->");
		return headerDO;
	}
}
