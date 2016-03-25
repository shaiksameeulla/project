package com.cg.lbs.bcun.service.dataformater;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.GlobalErrorCodeConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.constant.BcunDataFormaterConstants;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.utility.BcunDrsUtil;
import com.ff.domain.delivery.DeliveryDO;

public class BranchDrsFormatter extends AbstractDataFormater {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BranchDrsFormatter.class);
	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService)throws CGBusinessException,CGSystemException {
		return formatUpdateData(baseDO, bcunService);
	}

	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) throws CGBusinessException,CGSystemException{
		LOGGER.info("BranchDrsFormatter:: formatUpdateData:: START");
		DeliveryDO headerDO = (DeliveryDO) baseDO;
		List<Long> drsList=null;
		/*  Step 1:Check if data Exists in the Central server  if Exist then apply logic for merge otherwise throw exception */
		LOGGER.debug("BranchDrsFormatter:: formatUpdateData:: DRS NUMBER :["+headerDO.getDrsNumber()+"]");
		try {
			
			drsList= (List<Long>)bcunService.getAnonymusTypeDataByNamedQueryAndNamedParam("getDeliveryIdByDrsNumber", new String[]{BcunDataFormaterConstants.QRY_PARAM_DRS_NUMBER}, new Object[]{headerDO.getDrsNumber()});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("BranchDrsFormatter:: formatUpdateData:: Exception for DRS NUMBER :["+headerDO.getDrsNumber()+"]",e);
			throw e;
		}
		if(CGCollectionUtils.isEmpty(drsList) || StringUtil.isEmptyLong(drsList.get(0))){
		headerDO.setDtToBranch("R");
		headerDO.setDtToCentral("R");
			//insertDrs(bcunService, headerDO);
		LOGGER.error("BranchDrsFormatter:: formatUpdateData:: NO DRS Exist with this DRS number");
		throw new CGBusinessException(GlobalErrorCodeConstants.BUSSINESS_VIOLATION_ERROR);
		}else{
			
			headerDO=BcunDrsUtil.updateDrsForBranch(bcunService, headerDO);
		}
		LOGGER.info("BranchDrsFormatter:: formatUpdateData:: END");
		return headerDO;
	}

	
	
}
