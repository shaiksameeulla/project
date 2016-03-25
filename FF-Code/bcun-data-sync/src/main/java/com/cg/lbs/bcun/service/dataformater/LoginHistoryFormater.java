package com.cg.lbs.bcun.service.dataformater;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.umc.LogInOutDetlDO;

public class LoginHistoryFormater extends AbstractDataFormater {

	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO, BcunDatasyncService bcunService) {
		LogInOutDetlDO loginHistoryDo = (LogInOutDetlDO)baseDO;
		loginHistoryDo.setLogInOutId(null);
		 return loginHistoryDo;
	}

	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		// TODO Auto-generated method stub
		return null;
	}


}
