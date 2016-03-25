package com.cg.lbs.bcun.service.dataformater;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.GlobalErrorCodeConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.constant.BcunDataFormaterConstants;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.pickup.BcunPickupRunsheetDetailDO;
import com.ff.domain.pickup.BcunPickupRunsheetHeaderDO;
import com.ff.domain.pickup.BcunRunsheetAssignmentDetailDO;
import com.ff.domain.pickup.BcunRunsheetAssignmentHeaderDO;

public class GenerateAndUpdateRunsheetFormatter extends AbstractDataFormater{
	private static final Logger LOGGER = LoggerFactory.getLogger(GenerateAndUpdateRunsheetFormatter.class);
	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) throws CGBusinessException{
		return formatUpdateData(baseDO, bcunService);
	}

	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) throws CGBusinessException {
		LOGGER.debug("GenerateAndUpdateRunsheetFormatter:: START");
		BcunPickupRunsheetHeaderDO headerDO = (BcunPickupRunsheetHeaderDO)baseDO;
		try{
			//get latest branch data to be processed
			String[] params = { BcunDataFormaterConstants.QRY_PARAM_RUNSHEET_NUNBER};
			Object[] values = { headerDO.getRunsheetNo() };
			@SuppressWarnings("unchecked")
			List<CGBaseDO> centralRunsheets = (List<CGBaseDO>) bcunService.getDataByNamedQueryAndNamedParam("getRunsheetHeader", params, values);
			
			BcunPickupRunsheetHeaderDO bcunPickupRunsheetHeaderDO = null;
			Integer runsheetHeaderId = null;
			boolean isDataToProcess = Boolean.FALSE;
			StringBuilder logTxt = new StringBuilder();
			logTxt.append("GenerateAndUpdateRunsheetFormatter:: getRunsheetHeader :: [RUNSHEET NO - ID ] :");
			logTxt.append(headerDO.getRunsheetNo());
			logTxt.append("-----> ");
			
			if(!StringUtil.isEmptyColletion(centralRunsheets)){
				bcunPickupRunsheetHeaderDO = (BcunPickupRunsheetHeaderDO)(centralRunsheets.get(0));
				runsheetHeaderId = bcunPickupRunsheetHeaderDO.getRunsheetHeaderId();
				logTxt.append(runsheetHeaderId);
			}else{
				isDataToProcess = Boolean.TRUE;
			}
			LOGGER.debug(logTxt.toString());
			if(isDataToProcess){
				//Setting Run sheet Header Id
				headerDO.setRunsheetHeaderId(runsheetHeaderId);
				
				//Setting Assignment Header Id
				BcunRunsheetAssignmentHeaderDO runsheetAssignmentHeaderDo = null;
				//Save assignment data which is dependent data for pickup run sheet if it is not available.
				try {
					LOGGER.debug("GenerateAndUpdateRunsheetFormatter::  Process Runsheet Assignment");
					CreateRunsheetAssignmentFormatter createRunsheetAssignmentFormatter = new CreateRunsheetAssignmentFormatter();
					CGBaseDO assignmentDO = createRunsheetAssignmentFormatter.formatInsertData(headerDO.getPickupAssignmentHeader(), bcunService);
					bcunService.persistOrUpdateTransferedEntity(assignmentDO);
					
					BcunRunsheetAssignmentHeaderDO runsheetAssignmentDO = (BcunRunsheetAssignmentHeaderDO) assignmentDO;
					headerDO.setPickupAssignmentHeader(runsheetAssignmentDO);
					runsheetAssignmentHeaderDo = runsheetAssignmentDO; // assign derived assignment header to runsheetAssignmentHeaderDo
				} catch (Exception e) {
					LOGGER.error("GenerateAndUpdateRunsheetFormatter::  Save Runsheet Assignment Header :: [CREATED_AT_OFFICE - CREATED_FOR_OFFICE - EMPLOYEE - PICKUP_ASSIGNMENT_TYPE - CREATED_DATE] ::["
							+headerDO.getPickupAssignmentHeader().getOfficeCreatedAt()+" - "+headerDO.getPickupAssignmentHeader().getOfficeCreatedFor()+" - "+headerDO.getPickupAssignmentHeader().getEmployeeFieldStaff()+" - "
							+headerDO.getPickupAssignmentHeader().getPickupAssignmentType()+" - "+headerDO.getPickupAssignmentHeader().getCreatedDate()+"]",e);
					throw e;
				}
				headerDO.setPickupAssignmentHeader(runsheetAssignmentHeaderDo);
				
				Set<BcunPickupRunsheetDetailDO> runsheetDetails = headerDO.getRunsheetDetails();
				for (BcunPickupRunsheetDetailDO pickupRunsheetDetailDO : runsheetDetails) {
					pickupRunsheetDetailDO.getRunsheetHeaderDtls().setRunsheetHeaderId(headerDO.getRunsheetHeaderId());
					
					//Setting Assignment Detail Id
					BcunRunsheetAssignmentDetailDO pickupAssignmentDtlDO = getAssignmentDetail(bcunService,pickupRunsheetDetailDO.getPickupAssignmentDtls(),runsheetAssignmentHeaderDo);
					if (!StringUtil.isNull(pickupAssignmentDtlDO)) {
						pickupRunsheetDetailDO.setPickupAssignmentDtls(pickupAssignmentDtlDO);
						
						//Setting Run sheet Detail Id
						String[] params2 = { BcunDataFormaterConstants.QRY_PARAM_RUNSHEET_NUNBER, BcunDataFormaterConstants.QRY_PARAM_ASSIGNMENT_DTL_ID};
						Object[] values2 = { headerDO.getRunsheetNo(), pickupAssignmentDtlDO.getPickupAssignmentDtlId() };
						
						Integer runsheetDetailId = bcunService.getUniqueId(BcunDataFormaterConstants.QRY_GET_RUNSHEET_DETAIL_ID, params2, values2);
						pickupRunsheetDetailDO.setRunsheetDetailId(runsheetDetailId);
					}				
				}
			}else {
				headerDO = bcunPickupRunsheetHeaderDO;
			}
		}catch(HibernateObjectRetrievalFailureException e){
			LOGGER.error("GenerateAndUpdateRunsheetFormatter:: Exception for RUNSHEET NUMBER :["+headerDO.getRunsheetNo()+"]",e);
			throw new CGBusinessException(GlobalErrorCodeConstants.BUSSINESS_VIOLATION_ERROR);
		}catch(Exception e){
			LOGGER.error("GenerateAndUpdateRunsheetFormatter:: Exception for RUNSHEET NUMBER :["+headerDO.getRunsheetNo()+"]",e);
			throw e;
		}
		LOGGER.debug("GenerateAndUpdateRunsheetFormatter:: END");
		return headerDO;
	}

	private BcunRunsheetAssignmentDetailDO getAssignmentDetail(BcunDatasyncService bcunService,
			BcunRunsheetAssignmentDetailDO assignmentDetailDO, BcunRunsheetAssignmentHeaderDO assignmentHeaderDO) throws CGBusinessException {
		StringBuilder assignDtlKey = getUniqueKey4AssignmentDtl(assignmentDetailDO, assignmentHeaderDO);
		String assignDtlUniqueKey = assignDtlKey.toString();
		 
		Set<BcunRunsheetAssignmentDetailDO> detailDOs = assignmentHeaderDO.getAssignmentDetails();
		for (BcunRunsheetAssignmentDetailDO dtlDO : detailDOs) {
			StringBuilder dtlKey = getUniqueKey4AssignmentDtl(dtlDO, assignmentHeaderDO);
			String dtlUniqueKey = dtlKey.toString();
			if(StringUtils.equalsIgnoreCase(assignDtlUniqueKey, dtlUniqueKey)){
				assignmentDetailDO = dtlDO;
				break;
			}
		}
		
		return assignmentDetailDO;
	}

	private StringBuilder getUniqueKey4AssignmentDtl(
			BcunRunsheetAssignmentDetailDO assignmentDetailDO,
			BcunRunsheetAssignmentHeaderDO assignmentHeaderDO) {
		StringBuilder assignDtlKey = new StringBuilder();
		assignDtlKey.append(assignmentHeaderDO.getPickupAssignmentHeaderId());
		assignDtlKey.append(CommonConstants.HYPHEN);
		if(!StringUtil.isNull(assignmentDetailDO.getReversePickupCustomer()))
			assignDtlKey.append(assignmentDetailDO.getReversePickupCustomer().getOrderNumber());
		else
			assignDtlKey.append(CommonConstants.EMPTY_STRING);
		assignDtlKey.append(CommonConstants.HYPHEN);
		assignDtlKey.append(assignmentDetailDO.getPickupDlvLocation());
		return assignDtlKey;
	}
}
