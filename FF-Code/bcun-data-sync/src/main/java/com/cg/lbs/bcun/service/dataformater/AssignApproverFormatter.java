package com.cg.lbs.bcun.service.dataformater;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.umc.BcunAssignApproverDO;

public class AssignApproverFormatter extends AbstractDataFormater {

	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		// TODO Auto-generated method stub
		BcunAssignApproverDO headerDO = (BcunAssignApproverDO) baseDO;
		headerDO.setAssignApproverId(null);
		
		return headerDO;
	}

	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		// TODO Auto-generated method stub
		return null;
	}

}
