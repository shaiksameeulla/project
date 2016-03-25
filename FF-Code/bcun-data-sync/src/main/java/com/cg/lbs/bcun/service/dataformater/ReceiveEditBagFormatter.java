package com.cg.lbs.bcun.service.dataformater;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.loadmanagement.BcunLoadConnectedDO;

/**
 * The Class ReceiveEditBagFormatter.
 * 
 * @author narmdr
 */
public class ReceiveEditBagFormatter extends AbstractDataFormater {

	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		BcunLoadConnectedDO headerDO = (BcunLoadConnectedDO)baseDO;
		//setDefaultValue(headerDO);
		formatUpdateData(baseDO, bcunService);
		return headerDO;
	}

//	private static void setDefaultValue(CGBaseDO baseDO) {
//		baseDO.setDtToCentral(CommonConstants.YES);
//	}


	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		BcunLoadConnectedDO headerDO = (BcunLoadConnectedDO)baseDO;
		//setDefaultValue(headerDO);
		
		Integer loadMovementId = null;
		Integer loadConnectedId = null;

		//plz uncomment once fixed comilation issue
		/*if(headerDO.getLoadMovementDO()!=null){
			String[] receiveParams = {"receiveNumber","movementDirection","receiveType","destOfficeId"};
			Object[] receiveValues = { headerDO.getLoadMovementDO().getReceiveNumber(),headerDO.getLoadMovementDO().getMovementDirection(),headerDO.getLoadMovementDO().getReceiveType(),headerDO.getLoadMovementDO().getDestOfficeId()};
			//header details
			loadMovementId = bcunService.getUniqueId(BcunDataFormaterConstants.QRY_LOADMOVEMENT_FOR_RECEIVE_OUTSTATION_HEADER_DTLS, receiveParams, receiveValues);
			if (!StringUtil.isEmptyInteger(loadMovementId)){
				headerDO.getLoadMovementDO().setLoadMovementId(loadMovementId);
			}else{
				headerDO.setLoadMovementDO(null);
			}
		}*/
		
		String[] receiveParams = {"loadMovementId", "manifestNumber"};
		Object[] receiveValues = {loadMovementId, headerDO.getManifestDO().getManifestNo()};
		loadConnectedId = bcunService.getUniqueId("getLoadConnId4EditBag", receiveParams, receiveValues);
		headerDO.setLoadConnectedId(loadConnectedId);
		
		Integer manifestId = getManifestId4LoadConnected(headerDO, bcunService);
		if(!StringUtil.isEmptyInteger(manifestId)){
			headerDO.getManifestDO().setManifestId(manifestId);			
		}else{
			headerDO.setManifestDO(null);
		}
		
		return headerDO;
	}

	private Integer getManifestId4LoadConnected(
			BcunLoadConnectedDO loadConnectedDO, BcunDatasyncService bcunService) {
		Integer manifestId = null;
		if(loadConnectedDO.getManifestDO()==null){
			return manifestId;			
		}

		String[] inParams = { "manifestNo", "destOfficeId", "manifestType" };
		String[] outParams = { "manifestNo", "originOfficeId", "manifestType" };
		
		if(loadConnectedDO.getManifestDO().getManifestType().equals(CommonConstants.MANIFEST_TYPE_OUT)){
			Object[] outValues = {loadConnectedDO.getManifestDO().getManifestNo(),loadConnectedDO.getManifestDO().getOriginOfficeId(), loadConnectedDO.getManifestDO().getManifestType()};
			manifestId = bcunService.getUniqueId("getManifestIdByNoTypeOrgOffId", outParams, outValues);

		}else{//In manifest
			Object[] inValues  ={loadConnectedDO.getManifestDO().getManifestNo(),loadConnectedDO.getManifestDO().getDestOfficeId(),loadConnectedDO.getManifestDO().getManifestType()};
			manifestId = bcunService.getUniqueId("getManifestIdByNoTypeDestOffId", inParams, inValues);
		}
		
		return manifestId;
	}
}