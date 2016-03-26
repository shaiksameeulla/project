package com.ff.sap.integration.material.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.coloading.CSDSAPColoaderInvoiceDO;
import com.ff.domain.coloading.ColoaderRatesDO;
import com.ff.domain.stockmanagement.masters.CSDSAPItemDO;
import com.ff.domain.stockmanagement.masters.ItemTypeDO;
import com.ff.domain.stockmanagement.masters.SAPItemDO;
import com.ff.domain.umc.UserDO;
import com.ff.sap.integration.dao.SAPIntegrationDAO;
import com.ff.sap.integration.to.SAPColoaderTO;
import com.ff.sap.integration.to.SAPMaterialTO;
import com.ff.universe.stockmanagement.dao.StockUniversalDAO;

public class MaterialMasterSAPIntegrationServiceImpl implements MaterialMasterSAPIntegrationService {

	Logger logger = Logger.getLogger(MaterialMasterSAPIntegrationServiceImpl.class);
	private SAPIntegrationDAO integrationDAO;
	private StockUniversalDAO stockUniversalDAO;
	private MaterialMasterSAPTransactionService materialSAPTransactionService;

	
	
	/**
	 * @param materialSAPTransactionService the materialSAPTransactionService to set
	 */
	public void setMaterialSAPTransactionService(
			MaterialMasterSAPTransactionService materialSAPTransactionService) {
		this.materialSAPTransactionService = materialSAPTransactionService;
	}

	@Override
	public boolean saveMaterialsDetails(List<SAPMaterialTO> materials,boolean iterateOneByOne) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException { 

		logger.debug("ITEM ::  MaterialMasterSAPIntegrationServiceImpl :: saveMaterialsDetails :: Start");
		boolean isSaved = false;
		boolean isStagingSaved = false;
		boolean isUpdate = false;
		String sapStatus = null;
		List<CSDSAPItemDO> materialDOs;
		List<CSDSAPItemDO> updateMaterialDOs;
		List<CSDSAPItemDO> sapMaterialDOs;
	
		try {
			/*//1.Always Insert PI Data into Staging Table
			sapMaterialDOs = getStagingDOFromTO(materials);
			if(!CGCollectionUtils.isEmpty(sapMaterialDOs)){
				isStagingSaved = integrationDAO.saveDetailsForMaterial(sapMaterialDOs);
			} */
			/*if(isStagingSaved){*/
				// 2. first check data if present - Update
				//sapStatus = "C";
			//If Batch Insert/Update fails then try to insert/update records one by one 
			// and error records will be stored in staging table ff_f_sap_vendor and email will be trigger 
			//for error records
			//fisrt chk data if present - Update
				updateMaterialDOs = materialSAPTransactionService.getUpdateMaterialDOFromTO(materials);
				logger.debug("ITEM ::  Update Material DO Size"+updateMaterialDOs.size());
				//having doubt
				if(!StringUtil.isNull(updateMaterialDOs) &&(!StringUtil.isEmptyColletion(updateMaterialDOs)) && !iterateOneByOne){
					logger.debug("ITEM ::  Inside Update Method !!!!0000");
					isUpdate = materialSAPTransactionService.updateDetails(updateMaterialDOs);
					//2.A - Update Complete Status in Staging Table
//					integrationDAO.updateMaterialStagingStatus(sapStatus,updateMaterialDOs);
				}else if(!isUpdate && !StringUtil.isNull(updateMaterialDOs)){
					//not complete
					isSaved = materialSAPTransactionService.saveDetailsOneByOne(updateMaterialDOs);
				}
				//If Batch Insert/Update fails then try to insert/update records one by one 
				// and error records will be stored in staging table ff_f_sap_vendor and email will be trigger 
				//for error records
				// 3. Item Type Code New - Save
				materialDOs = materialSAPTransactionService.getDOFromTO(materials);
				if(!StringUtil.isNull(materialDOs)
						&&(!StringUtil.isEmptyColletion(materialDOs)) && !iterateOneByOne){
					isSaved = materialSAPTransactionService.saveDetailsForMaterial(materialDOs);
					//3.A - Update Complete Status in Staging Table
					//integrationDAO.updateMaterialStagingStatus(sapStatus,materialDOs);
				}else if(!isSaved && !StringUtil.isNull(materialDOs)){
					isSaved = materialSAPTransactionService.saveDetailsOneByOne(materialDOs);
				}
			/*}else{
				//4. If its failure then set SAP Status as N
				sapStatus = "N";
				integrationDAO.updateMaterialStagingStatus(sapStatus,sapMaterialDOs);
			}*/
		} catch (CGSystemException e) {
			logger.error("ITEM :: Exception In :: MaterialMasterSAPIntegrationServiceImpl :: saveMaterialsDetails");
		}
		logger.debug("ITEM ::  MaterialMasterSAPIntegrationServiceImpl :: saveMaterialsDetails :: End");
		return isSaved;
	}

	private List<CGBaseDO> getStagingDOFromTO(List<SAPMaterialTO> materials) throws CGBusinessException {
		logger.debug("MaterialMasterSAPIntegrationServiceImpl :: getStagingDOFromTO :: Start");
		List<CGBaseDO> baseList = null;
		if(materials != null && !materials.isEmpty()) {
			baseList = new ArrayList<CGBaseDO>(materials.size());
			SAPItemDO sapItemDO = null;
			for(SAPMaterialTO materialTO : materials) {
				if(!StringUtil.isNull(materialTO)){
					sapItemDO = new SAPItemDO();
					sapItemDO = convertSAPItemTypeTOtoDO(materialTO);
					baseList.add(sapItemDO);
					logger.debug("Save base List Size-------------------->"+baseList.size());
				}
			}
		}
		logger.debug("MaterialMasterSAPIntegrationServiceImpl :: getStagingDOFromTO :: End");
		return baseList;
	}

	private List<CGBaseDO> getUpdateMaterialDOFromTO(List<SAPMaterialTO> materials) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException{
		logger.debug("MaterialMasterSAPIntegrationServiceImpl :: getUpdateMaterialDOFromTO :: Start");
		List<CGBaseDO> baseList = null;
		String userName = "SAP_USER"; 
		//DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//String dateStamp = df.format(today);
		if(materials != null && !materials.isEmpty()) {			
			baseList = new ArrayList<CGBaseDO>(materials.size());
			CSDSAPItemDO itemDO = null;
			for(SAPMaterialTO materialTO : materials) {
				if(!StringUtil.isStringEmpty(materialTO.getItemCode())){
					logger.debug("Material TO Item CODE -------------->"+materialTO.getItemCode());
					String itemCode = materialTO.getItemCode();
					itemDO = stockUniversalDAO.getItemDtlsByCode(itemCode);
					if(!StringUtil.isNull(itemDO)){
						logger.debug("Ttem Type Code Updated ------->"+itemDO.getItemCode());
						//ItemTypeDO itmTypeDO = null;
						CSDSAPItemDO itmDO = null;
						boolean isUpdate = true;
						//itmTypeDO = convertItemTypeTOtoDO(materialTO,isUpdate,itemDO.getItemTypeDO());
						itmDO = convertItemTOtoDO(materialTO,isUpdate,itemDO);
						UserDO userDO = integrationDAO.getSAPUserDtls(userName);
						if(!StringUtil.isNull(userDO)
								&& !StringUtil.isEmptyInteger(userDO.getUserId())){
							itmDO.setCreatedBy(userDO.getUserId());
							itemDO.setUpdatedBy(userDO.getUserId());
							Date today = Calendar.getInstance().getTime();
							itemDO.setCreatedDate(today);
							itemDO.setUpdatedDate(today);
						}
						//itmDO.setItemTypeDO(itmTypeDO);
						baseList.add(itmDO);
						logger.debug("Update base List Size-------------------->"+baseList.size());
					}
				}
			}
		}
		logger.debug("MaterialMasterSAPIntegrationServiceImpl :: getUpdateMaterialDOFromTO :: End");
		return baseList;
	}

	private List<CGBaseDO> getDOFromTO(List<SAPMaterialTO> materials) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException {
	logger.debug("MaterialMasterSAPIntegrationServiceImpl :: getDOFromTO :: Start");
	List<CGBaseDO> baseList = null;
	if(materials != null && !materials.isEmpty()) {
		baseList = new ArrayList<CGBaseDO>(materials.size());
		CSDSAPItemDO itemDO = null;
		for(SAPMaterialTO materialTO : materials) {
			if(!StringUtil.isStringEmpty(materialTO.getItemCode())){
				logger.debug("Material TO Item CODE -------------->"+materialTO.getItemCode());
				String itemCode = materialTO.getItemCode();
				itemDO = stockUniversalDAO.getItemDtlsByCode(itemCode);
				if(StringUtil.isNull(itemDO)){
					CSDSAPItemDO itmDO = null;
					//ItemTypeDO itmTypeDO = null;
					boolean isUpdate = false;
					//itmTypeDO = convertItemTypeTOtoDO(materialTO,isUpdate,itmTypeDO);
					itmDO = convertItemTOtoDO(materialTO,isUpdate,itemDO);
					//itmDO.setItemTypeDO(itmTypeDO);
					baseList.add(itmDO);
					logger.debug("Save base List Size-------------------->"+baseList.size());
				}
			}
		}
	}
	logger.debug("MaterialMasterSAPIntegrationServiceImpl :: getDOFromTO :: End");
	return baseList;
	}

	private SAPItemDO convertSAPItemTypeTOtoDO(SAPMaterialTO materialTo) throws CGBusinessException{
		
		logger.debug("MaterialMasterSAPIntegrationServiceImpl :: convertSAPItemTypeTOtoDO :: Start");
		SAPItemDO sapItemDO = new SAPItemDO();
		//DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		sapItemDO.setId(Long.valueOf(!StringUtil.isEmptyInteger(materialTo.getItemId())?materialTo.getItemId() :null));
		if(!StringUtil.isStringEmpty(materialTo.getItemTypeCode())){
			sapItemDO.setItemTypeCode(materialTo.getItemTypeCode());
		}
		logger.debug("item Type CODE----------------->"+sapItemDO.getItemTypeCode());
		
		if(!StringUtil.isStringEmpty(materialTo.getItemTypeName())){
			sapItemDO.setItemTypeName(materialTo.getItemTypeName());
		}
		logger.debug("item Type Name ----------------->"+sapItemDO.getItemTypeName());
		
		if(!StringUtil.isStringEmpty(materialTo.getItemSeries())){
			sapItemDO.setItemHasSeries('Y');
		}else{
			sapItemDO.setItemHasSeries('N');
		}
		logger.debug("Item Has Series ----------------->"+sapItemDO.getItemHasSeries());
		
		if(!StringUtil.isStringEmpty(materialTo.getIsActive())){
			sapItemDO.setCurStatus(materialTo.getIsActive().charAt(0)); 
		}
		logger.debug("Is Active ----------------->"+sapItemDO.getCurStatus());
		
		if(!StringUtil.isStringEmpty(materialTo.getItemCode())){
			sapItemDO.setItemCode(materialTo.getItemCode());
		}
		logger.debug("itemDO getItemCode----------------->"+sapItemDO.getItemCode());
		if(!StringUtil.isStringEmpty(materialTo.getItemName())){
			sapItemDO.setItemName(materialTo.getItemTypeName());
		}
		logger.debug("itemDO getItemName----------------->"+sapItemDO.getItemName());
		if(!StringUtil.isStringEmpty(materialTo.getItemSeries())){
			sapItemDO.setItemSeries(materialTo.getItemSeries());
		}
		logger.debug("itemDO getItemSeries----------------->"+sapItemDO.getItemSeries());
		if(!StringUtil.isStringEmpty(materialTo.getDescription())){
			sapItemDO.setDescription(materialTo.getDescription());
		}
		logger.debug("itemDO getDescription----------------->"+sapItemDO.getDescription());
		if(!StringUtil.isStringEmpty(materialTo.getUom())){
			sapItemDO.setUom(materialTo.getUom());
		}
		logger.debug("itemDO getUom----------------->"+sapItemDO.getUom());
		if(!StringUtil.isEmptyInteger(materialTo.getSeriesLength())){
			sapItemDO.setSeriesLength(materialTo.getSeriesLength());
		}
		logger.debug("itemDO getSeriesLength----------------->"+sapItemDO.getSeriesLength());
		
		sapItemDO.setSapStatusInBound("N");
		Date today = Calendar.getInstance().getTime();        
		sapItemDO.setSapTimestamp(today);
		
		/*if(!StringUtil.isStringEmpty(error)){
			sapItemDO.setIsError('Y');
			sapItemDO.setErrorDesc(error);
		}else{
			sapItemDO.setIsError('N');
			sapItemDO.setErrorDesc(null);
		}
		logger.debug("Error Descp ----------------->"+error);*/
		logger.debug("MaterialMasterSAPIntegrationServiceImpl :: convertSAPItemTypeTOtoDO :: End");
		return sapItemDO;
	
	}

	/*private ItemTypeDO convertItemTypeTOtoDO(SAPMaterialTO materialTo,boolean isUpdate, ItemTypeDO itmTypeDO) throws CGBusinessException {
	
		ItemTypeDO itemTypeDO = new ItemTypeDO();
		
		if(isUpdate && (!StringUtil.isNull(itmTypeDO)
				&& (!StringUtil.isEmptyInteger(itmTypeDO.getItemTypeId())))){ 
			itemTypeDO.setItemTypeId(itmTypeDO.getItemTypeId());
		}else{
			itemTypeDO.setItemTypeId(!StringUtil.isEmptyInteger(materialTo.getItemTypeId())?materialTo.getItemTypeId() :null);
			logger.debug("Item Type ID ----------------->"+itemTypeDO.getItemTypeId());
		}
		
		if(!StringUtil.isStringEmpty(materialTo.getItemTypeCode())){
			itemTypeDO.setItemTypeCode(materialTo.getItemTypeCode());
		}
		logger.debug("item Type CODE----------------->"+itemTypeDO.getItemTypeCode());
		if(!StringUtil.isStringEmpty(materialTo.getItemTypeName())){
			itemTypeDO.setItemTypeName(materialTo.getItemTypeName());
		}
		logger.debug("item Type Name ----------------->"+itemTypeDO.getItemTypeName());
		if(!StringUtil.isStringEmpty(materialTo.getItemSeries())){
			itemTypeDO.setItemHasSeries("Y");
		}else{
			itemTypeDO.setItemHasSeries("N");
		}
		logger.debug("Item Has Series ----------------->"+itemTypeDO.getItemHasSeries());
		if(!StringUtil.isStringEmpty(materialTo.getIsActive())){
			itemTypeDO.setIsActive(materialTo.getIsActive());
		}
		logger.debug("Is Active ----------------->"+itemTypeDO.getIsActive());
		return itemTypeDO;
	}*/

	private CSDSAPItemDO convertItemTOtoDO(SAPMaterialTO materialTo,boolean isUpdate, CSDSAPItemDO itmDO) throws CGBusinessException, CGSystemException {
		
		logger.debug("MaterialMasterSAPIntegrationServiceImpl :: convertItemTOtoDO :: Start");
		CSDSAPItemDO itemDO = new CSDSAPItemDO();
		
		if(isUpdate && (!StringUtil.isNull(itmDO)
				&& (!StringUtil.isEmptyInteger(itmDO.getItemId())))){ 
			itemDO.setItemId(itmDO.getItemId());
		}else{
			itemDO.setItemId(!StringUtil.isEmptyInteger(materialTo.getItemId())?materialTo.getItemId() :null);
			logger.debug("Item ID ----------------->"+itemDO.getItemId());
		}
		logger.debug("Item ID----------------->"+itemDO.getItemId());
		
		if(!StringUtil.isStringEmpty(materialTo.getItemCode())){
			itemDO.setItemCode(materialTo.getItemCode());
		}
		logger.debug("itemDO getItemCode----------------->"+itemDO.getItemCode());
		if(!StringUtil.isStringEmpty(materialTo.getItemName())){
			itemDO.setItemName(materialTo.getItemName());
		}
		logger.debug("itemDO getItemName----------------->"+itemDO.getItemName());
		if(!StringUtil.isStringEmpty(materialTo.getItemSeries())
				&& materialTo.getItemSeries().contains("N")){
			itemDO.setItemSeries(null);
		}else{
			itemDO.setItemSeries(materialTo.getItemSeries());
		}
		logger.debug("itemDO getItemSeries----------------->"+itemDO.getItemSeries());
		if(!StringUtil.isStringEmpty(materialTo.getIsActive())){
			itemDO.setIsActive(materialTo.getIsActive());
		}
		logger.debug("itemDO getIsActive----------------->"+itemDO.getIsActive());
		if(!StringUtil.isStringEmpty(materialTo.getDescription())){
			itemDO.setDescription(materialTo.getDescription());
		}
		logger.debug("itemDO getDescription----------------->"+itemDO.getDescription());
		if(!StringUtil.isStringEmpty(materialTo.getUom())){
			itemDO.setUom(materialTo.getUom());
		}
		logger.debug("itemDO getUom----------------->"+itemDO.getUom());
		if(!StringUtil.isEmptyInteger(materialTo.getSeriesLength())){
			itemDO.setSeriesLength(materialTo.getSeriesLength());
		}
		logger.debug("itemDO getSeriesLength----------------->"+itemDO.getSeriesLength());
		
		ItemTypeDO itemTypeDO = null;
		if(!StringUtil.isStringEmpty(materialTo.getItemTypeCode())){
			logger.debug("Item Type Code From TO ------------------> "+materialTo.getItemTypeCode());
			String itemTypeCode = materialTo.getItemTypeCode();
			itemTypeDO = stockUniversalDAO.getItemTypeByCode(itemTypeCode);
			if(!StringUtil.isNull(itemTypeDO)){
				itemDO.setItemTypeDO(itemTypeDO);
				logger.debug("itemDO Item Type ID ----------------->"+itemDO.getItemTypeDO().getItemTypeId());
			}
		}
		itemDO.setSapStatusInBound("C");
		logger.debug("MaterialMasterSAPIntegrationServiceImpl :: convertItemTOtoDO :: End");
		return itemDO;
	}

public void setIntegrationDAO(SAPIntegrationDAO integrationDAO) {
this.integrationDAO = integrationDAO;
}
/**
* @param stockUniversalDAO the stockUniversalDAO to set
*/
public void setStockUniversalDAO(StockUniversalDAO stockUniversalDAO) {
this.stockUniversalDAO = stockUniversalDAO;
}

@Override
public boolean updateColoaderInvoiceNo(List<SAPColoaderTO> coloaderInvoiceNumber)
		throws CGBusinessException, IllegalAccessException,
		InvocationTargetException, NoSuchMethodException {
	logger.debug("COLOADERINNVOICE :: MaterialMasterSAPIntegrationServiceImpl :: updateColoaderInvoiceNo :: Start");
	boolean isUpdate = false;
	List<CSDSAPColoaderInvoiceDO> saveColoaderDOs;
	List<CSDSAPColoaderInvoiceDO> updateColoaderDOs;
	try {
		//Update Coloader Invoice Number
		
		//isUpdate = updateColoaderInvoice(coloaderInvoiceNumber);
		updateColoaderDOs = getUpdateColoaderInvoiceDO(coloaderInvoiceNumber);
		if(!StringUtil.isNull(updateColoaderDOs)){
			isUpdate = integrationDAO.SaveOrUpdateStagingColoaderInvoiceNo(updateColoaderDOs);
		}
		
		saveColoaderDOs = getColoaderInvoiceDetails(coloaderInvoiceNumber);
		if(!StringUtil.isNull(saveColoaderDOs)){
			isUpdate = integrationDAO.SaveOrUpdateStagingColoaderInvoiceNo(saveColoaderDOs);
		}
		//updateColoaderDOs = coloaderSAPTransactionService.
		//Update Coloader Invoice Number 
		
		isUpdate = updateColoaderInvoice(coloaderInvoiceNumber);
	} catch (Exception e/*CGSystemException e*/) {
		logger.error("COLOADERINNVOICE :: Exception IN :: MaterialMasterSAPIntegrationServiceImpl :: updateColoaderInvoiceNo ::",e);
	}
	logger.debug("MaterialMasterSAPIntegrationServiceImpl :: updateColoaderInvoiceNo :: End");
	return isUpdate;
}

private List<CSDSAPColoaderInvoiceDO> getColoaderInvoiceDetails(
		List<SAPColoaderTO> coloaderInvoiceNumber) throws CGSystemException {
	logger.debug("COLOADERINNVOICE ::  MaterialMasterSAPIntegrationServiceImpl :: getColoaderInvoiceDetails :: Start");
	List<CSDSAPColoaderInvoiceDO> saveColoaderDOs = null;
	if(coloaderInvoiceNumber != null && !coloaderInvoiceNumber.isEmpty()) {			
		CSDSAPColoaderInvoiceDO  saveColoaderDO = null;
		for(SAPColoaderTO coloaderTO : coloaderInvoiceNumber) {
			if(!StringUtil.isStringEmpty(coloaderTO.getInvoiceNo())
					&& !StringUtil.isEmptyLong(coloaderTO.getTransactionId())){
				logger.debug("COLOADERINNVOICE ::  Co loader Invoice No -------------->"+coloaderTO.getInvoiceNo());
				logger.debug("COLOADERINNVOICE ::  Co loader Transaction ID -------------->"+coloaderTO.getTransactionId());
				Integer id = coloaderTO.getTransactionId().intValue();
				saveColoaderDO = stockUniversalDAO.getStagingColoaderDtlsByID(id); 
				if(StringUtil.isNull(saveColoaderDO)){
					CSDSAPColoaderInvoiceDO  stagingColoaderDO = null;
					boolean isUpdate = false;
					stagingColoaderDO = convertColoaderInvoiceTOtoDO(coloaderTO,isUpdate,saveColoaderDO);
					saveColoaderDOs.add(stagingColoaderDO);
				}
			}
		}
	}
	logger.debug("COLOADERINNVOICE ::  MaterialMasterSAPIntegrationServiceImpl :: getColoaderInvoiceDetails :: End");
	return saveColoaderDOs;
}

private CSDSAPColoaderInvoiceDO convertColoaderInvoiceTOtoDO(
		SAPColoaderTO coloaderTO,boolean isUpdate,CSDSAPColoaderInvoiceDO saveColoaderDO) {
	logger.debug("COLOADERINNVOICE ::  MaterialMasterSAPIntegrationServiceImpl :: convertColoaderInvoiceTOtoDO :: Start");
	CSDSAPColoaderInvoiceDO stagingColoaderInvDO = new CSDSAPColoaderInvoiceDO();
	
	if(isUpdate && (!StringUtil.isNull(saveColoaderDO)
			&& (!StringUtil.isEmptyInteger(saveColoaderDO.getId())))){ 
		stagingColoaderInvDO.setId(saveColoaderDO.getId());
	}else{
		stagingColoaderInvDO.setId(!StringUtil.isEmptyInteger(coloaderTO.getId())?coloaderTO.getId() :null);
		logger.debug("COLOADERINNVOICE ::  ID ----------------->"+stagingColoaderInvDO.getId());
	}
	logger.debug("COLOADERINNVOICE ::  ID----------------->"+stagingColoaderInvDO.getId());
	
	if(!StringUtil.isStringEmpty(coloaderTO.getInvoiceNo())){
		stagingColoaderInvDO.setInvoiceNo(coloaderTO.getInvoiceNo());
	}
	logger.debug("COLOADERINNVOICE ::  InvoiceNo----------------->"+stagingColoaderInvDO.getInvoiceNo());
	
	if(!StringUtil.isEmptyLong(coloaderTO.getTransactionId())){
		stagingColoaderInvDO.setTransactionNo(coloaderTO.getTransactionId().intValue());
	}
	logger.debug("COLOADERINNVOICE ::  TransactionId---------------->"+stagingColoaderInvDO.getTransactionNo());
	
	if(!isUpdate){
		stagingColoaderInvDO.setSapStatus("N");
	}
	Date today = Calendar.getInstance().getTime();        
	stagingColoaderInvDO.setSapTimestamp(today);
	logger.debug("COLOADERINNVOICE ::  MaterialMasterSAPIntegrationServiceImpl :: convertColoaderInvoiceTOtoDO :: end");
	return stagingColoaderInvDO;
	
}

private List<CSDSAPColoaderInvoiceDO> getUpdateColoaderInvoiceDO(List<SAPColoaderTO> coloaderInvoiceNumber) throws CGSystemException {
	logger.debug("COLOADERINNVOICE :: MaterialMasterSAPIntegrationServiceImpl :: getUpdateColoaderInvoiceDO :: Start");
	List<CSDSAPColoaderInvoiceDO> updateColoaderDOs = null;
	if(coloaderInvoiceNumber != null && !coloaderInvoiceNumber.isEmpty()) {			
		CSDSAPColoaderInvoiceDO  updateColoaderDO = null;
		for(SAPColoaderTO coloaderTO : coloaderInvoiceNumber) {
			if(!StringUtil.isStringEmpty(coloaderTO.getInvoiceNo())
					&& !StringUtil.isEmptyLong(coloaderTO.getTransactionId())){
				logger.debug("COLOADERINNVOICE ::  Co loader Invoice No -------------->"+coloaderTO.getInvoiceNo());
				logger.debug("COLOADERINNVOICE ::  Co loader Transaction ID -------------->"+coloaderTO.getTransactionId());
				Integer id = coloaderTO.getTransactionId().intValue();
				updateColoaderDO = stockUniversalDAO.getStagingColoaderDtlsByID(id); 
				if(!StringUtil.isNull(updateColoaderDO)){
					CSDSAPColoaderInvoiceDO  stagingColoaderDO = null;
					boolean isUpdate = true;
					stagingColoaderDO = convertColoaderInvoiceTOtoDO(coloaderTO,isUpdate,updateColoaderDO);
					updateColoaderDOs.add(updateColoaderDO);
				}
			}
		}
	}
	logger.debug("COLOADERINNVOICE ::  MaterialMasterSAPIntegrationServiceImpl :: getUpdateColoaderInvoiceDO :: End");
	return updateColoaderDOs;
}

private boolean updateColoaderInvoice(List<SAPColoaderTO> coloaderInvoiceNumber) throws CGSystemException { 
	logger.debug("COLOADERINNVOICE :: MaterialMasterSAPIntegrationServiceImpl :: updateColoaderInvoice :: Start");
	boolean isUpdate = false;
	List<CSDSAPColoaderInvoiceDO> stagingColoaderInvDOs = null;
	CSDSAPColoaderInvoiceDO stagingColoaderInvDO = null;
	ColoaderRatesDO  coloaderRatesDO = null;
		try{
		String sapStatus = "N";
		stagingColoaderInvDOs = stockUniversalDAO.getStagingColoaderDtls(sapStatus);
		if(!StringUtil.isEmptyColletion(stagingColoaderInvDOs)){
			for(CSDSAPColoaderInvoiceDO stagingInvoiceDO : stagingColoaderInvDOs){
				Integer id = stagingInvoiceDO.getTransactionNo();
				coloaderRatesDO = stockUniversalDAO.getColoaderDtlsByID(id);
				if(!StringUtil.isNull(coloaderRatesDO)){
					isUpdate = integrationDAO.updateColoaderInvoiceNumber(coloaderRatesDO);
					
					if(isUpdate){
						//coloaderRatesDO.setSapStatus("C");
						isUpdate = integrationDAO.updateInvoiceStagingStatus(coloaderRatesDO);
						
					}
				}
				
			}
		}
	}catch(Exception e){
		logger.debug("COLOADERINNVOICE ::  MaterialMasterSAPIntegrationServiceImpl :: updateColoaderInvoice :: end");
	}
	
	/*if(coloaderInvoiceNumber != null && !coloaderInvoiceNumber.isEmpty()) {			
		ColoaderRatesDO  coloaderRatesDO = null;
		for(SAPColoaderTO coloaderTO : coloaderInvoiceNumber) {
			if(!StringUtil.isStringEmpty(coloaderTO.getInvoiceNo())
					&& !StringUtil.isEmptyLong(coloaderTO.getTransactionId())){
				logger.debug("Co loader Invoice No -------------->"+coloaderTO.getInvoiceNo());
				logger.debug("Co loader Transaction ID -------------->"+coloaderTO.getTransactionId());
				
				Integer id = coloaderTO.getTransactionId().intValue();
				coloaderRatesDO = stockUniversalDAO.getColoaderDtlsByID(id);
				
				if(!StringUtil.isNull(coloaderRatesDO)){
					isUpdate = integrationDAO.updateColoaderInvoiceNumber(coloaderRatesDO,coloaderTO);
				}
			}
		}
	}*/
	logger.debug("COLOADERINNVOICE ::  MaterialMasterSAPIntegrationServiceImpl :: updateColoaderInvoice :: End");
	return isUpdate;
}


}
