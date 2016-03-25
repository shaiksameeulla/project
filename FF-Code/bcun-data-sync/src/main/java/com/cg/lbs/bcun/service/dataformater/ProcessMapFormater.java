package com.cg.lbs.bcun.service.dataformater;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.constant.BcunDataFormaterConstants;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.tracking.ProcessMapDO;

public class ProcessMapFormater extends AbstractDataFormater {

	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {

		ProcessMapDO  headerDO = (ProcessMapDO) baseDO;
		
		if(!StringUtil.isNull(headerDO)){
			headerDO.setProcessMapId(null);
		}
		
		return headerDO;
	}

	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		// TODO Auto-generated method stub
		ProcessMapDO  headerDO = (ProcessMapDO) baseDO;
		
		if(!StringUtil.isNull(headerDO)){
			String[] params = { "consgNumber" };
			Object[] values = { headerDO.getConsgNumber() };
			
			Integer processMapId = bcunService.getUniqueId(BcunDataFormaterConstants.QRY_GET_PROCESS_MAP_ID_BY_CONSIG_NUM, params, values);
			
			if(!StringUtil.isEmptyInteger(processMapId)){
				headerDO.setProcessMapId(processMapId);	
			}
			
			
				
		}
		return headerDO;
	}

}
