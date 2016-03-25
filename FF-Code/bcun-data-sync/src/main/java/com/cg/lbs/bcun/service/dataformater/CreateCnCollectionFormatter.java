package com.cg.lbs.bcun.service.dataformater;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.GlobalErrorCodeConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.mec.collection.CollectionDtlsDO;

/**
 * The Class CreateCnCollectionFormatter.
 *
 * @author narmdr
 */
public class CreateCnCollectionFormatter extends AbstractDataFormater {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CreateCnCollectionFormatter.class);

	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) throws CGBusinessException {
		LOGGER.debug("CreateCnCollectionFormatter::formatInsertData::START------------>:::::::");
		CollectionDO collectionDO = (CollectionDO) baseDO;
		collectionDO.setCollectionId(null);
		
		/**
		 * 1. check if Transaction number exist in the  collection entries, if  exist and validated then error out this record
		 * */
		
		List<String> statusList=(List<String>) bcunService.getAnonymusTypeDataByNamedQueryAndNamedParam("getCollectionStatusByTxnNumber", new String[]{"txnNo"}, new Object[]{collectionDO.getTxnNo()});
		if(!CGCollectionUtils.isEmpty(statusList) && !StringUtil.isStringEmpty(statusList.get(0)) && statusList.get(0).equalsIgnoreCase("V")){
			LOGGER.error("CreateCnCollectionFormatter:: formatInsertData::  Collection details are already been processed (moving to error), Collection Tx no :["+collectionDO.getTxnNo()+"]");
			throw new CGBusinessException(GlobalErrorCodeConstants.BUSSINESS_VIOLATION_ERROR);
		}
		
		for(CollectionDtlsDO collectionDtls:collectionDO.getCollectionDtls()){
			collectionDtls.setCollectionDO(collectionDO);
			collectionDtls.setEntryId(null);
			ConsignmentDO consgDO=null;
			
			/**
			 * 2. check if consignment exist in the given collection entries, if not exist error out this record
			 * */
			if(collectionDtls.getConsgDO()==null){
				LOGGER.error("CreateCnCollectionFormatter:: formatInsertData::  Collection details does not have consignment dtls (moving to error), Collection Tx no :["+collectionDO.getTxnNo()+"]");
				throw new CGBusinessException(GlobalErrorCodeConstants.BUSSINESS_VIOLATION_ERROR);

			}
			

			/**
			 * 3. check if consignment is already available in DB, if not exist then insert in DB
			 * */
			List<Integer> consgidList=(List<Integer>) bcunService.getAnonymusTypeDataByNamedQueryAndNamedParam("getMasterConsignmentId", new String[]{"consgNumber"}, new Object[]{collectionDtls.getConsgDO().getConsgNo()});
			if(!CGCollectionUtils.isEmpty(consgidList) && !StringUtil.isEmptyInteger(consgidList.get(0))){
				consgDO=new ConsignmentDO();
				consgDO.setConsgId(consgidList.get(0));
				LOGGER.info("CreateCnCollectionFormatter::formatInsertData::Consignment already exist in DB");
			}else{
				consgDO = BcunManifestUtils.getConsignment(bcunService, collectionDtls.getConsgDO());
				if(StringUtil.isEmptyInteger(consgDO.getConsgId())){
					bcunService.persistOrUpdateTransferedEntity(consgDO);
				}else{
					LOGGER.info("CreateCnCollectionFormatter::formatInsertData::Consignment already exist in DB");
				}
			}

			collectionDtls.setConsgDO(consgDO);
		}
		LOGGER.debug("CreateCnCollectionFormatter::formatInsertData::END------------>:::::::");
		return collectionDO;
	}
	
	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) throws CGBusinessException {
		LOGGER.debug("CreateCnCollectionFormatter::formatUpdateData::START------------>:::::::");
		CollectionDO collectionDo = (CollectionDO) formatInsertData(baseDO, bcunService);
		LOGGER.debug("CreateCnCollectionFormatter::formatUpdateData::END------------>:::::::");
		return collectionDo;
	}

}