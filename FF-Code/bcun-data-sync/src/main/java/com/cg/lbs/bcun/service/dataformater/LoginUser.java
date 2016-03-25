package com.cg.lbs.bcun.service.dataformater;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.umc.EmployeeUserDO;

public class LoginUser extends AbstractDataFormater {

	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO, BcunDatasyncService bcunService) {
		EmployeeUserDO userDo = (EmployeeUserDO)baseDO;
		userDo.setUserId(null);
		 return userDo;
	}

	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		// TODO Auto-generated method stub
		return null;
	}

}
