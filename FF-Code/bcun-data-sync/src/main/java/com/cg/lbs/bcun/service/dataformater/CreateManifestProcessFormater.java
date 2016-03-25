package com.cg.lbs.bcun.service.dataformater;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.cg.lbs.bcun.service.BcunDatasyncService;

public class CreateManifestProcessFormater extends AbstractDataFormater {

	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO, BcunDatasyncService bcunService) {
		formatUpdateData(baseDO, bcunService);
		return baseDO;
	}

	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		/*ManifestProcessDO manifestProcessDO = (ManifestProcessDO) baseDO;
		getAndSetManifestProcess(bcunService, manifestProcessDO);
		return manifestProcessDO;*/
		return baseDO;
	}

/*	private void getAndSetManifestProcess(BcunDatasyncService bcunService,
			ManifestProcessDO manifestProcessDO) {
		String[] params = { "manifestNo", "manifestDirection",
				"manifestProcessCode", "originOfficeId", "destOfficeId" };
		Object[] values = { manifestProcessDO.getManifestNo(),
				manifestProcessDO.getManifestDirection(),
				manifestProcessDO.getManifestProcessCode(),
				manifestProcessDO.getOriginOfficeId(),
				manifestProcessDO.getDestOfficeId() };

		Integer manifestProcessId = bcunService.getUniqueId(
				"getManifestProcessId", params, values);
		manifestProcessDO.setManifestProcessId(manifestProcessId);
	}*/

}
