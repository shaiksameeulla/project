package com.ff.sap.integration.material.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.email.EmailSenderUtil;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.business.CSDSAPLoadMovementVendorDO;
import com.ff.domain.business.SAPVendorDO;
import com.ff.domain.stockmanagement.masters.CSDSAPItemDO;
import com.ff.domain.stockmanagement.masters.ItemTypeDO;
import com.ff.domain.stockmanagement.masters.SAPItemDO;
import com.ff.domain.umc.UserDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.dao.SAPIntegrationDAO;
import com.ff.sap.integration.to.SAPMaterialTO;
import com.ff.sap.integration.vendor.bs.VendorMasterSAPTransactionServiceImpl;
import com.ff.universe.stockmanagement.dao.StockUniversalDAO;

public class MaterialMasterSAPTransactionServiceImpl implements MaterialMasterSAPTransactionService{
	Logger logger = Logger.getLogger(MaterialMasterSAPTransactionServiceImpl.class);
	private SAPIntegrationDAO integrationDAO;
	private StockUniversalDAO stockUniversalDAO;
	private EmailSenderUtil emailSenderUtil;
	
	
	
	/**
	 * @param integrationDAO the integrationDAO to set
	 */
	public void setIntegrationDAO(SAPIntegrationDAO integrationDAO) {
		this.integrationDAO = integrationDAO;
	}

	/**
	 * @param stockUniversalDAO the stockUniversalDAO to set
	 */
	public void setStockUniversalDAO(StockUniversalDAO stockUniversalDAO) {
		this.stockUniversalDAO = stockUniversalDAO;
	}

	/**
	 * @param emailSenderUtil the emailSenderUtil to set
	 */
	public void setEmailSenderUtil(EmailSenderUtil emailSenderUtil) {
		this.emailSenderUtil = emailSenderUtil;
	}

	public List<CSDSAPItemDO> getUpdateMaterialDOFromTO(List<SAPMaterialTO> materials) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException{
		logger.debug("ITEM :: MaterialMasterSAPIntegrationServiceImpl :: getUpdateMaterialDOFromTO :: Start");
		List<CSDSAPItemDO> baseList = null;
		String userName = "SAP_USER"; 
		//DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//String dateStamp = df.format(today);
		if(materials != null && !materials.isEmpty()) {			
			baseList = new ArrayList<CSDSAPItemDO>(materials.size());
			logger.debug("ITEM :: SAP ITEM SIZE -------------------->"+baseList.size());
			CSDSAPItemDO itemDO = null;
			for(SAPMaterialTO materialTO : materials) {
				if(!StringUtil.isStringEmpty(materialTO.getItemCode())){
					logger.debug("ITEM :: Material TO Item CODE -------------->"+materialTO.getItemCode());
					String itemCode = materialTO.getItemCode();
					itemDO = stockUniversalDAO.getItemDtlsByCode(itemCode);
					if(!StringUtil.isNull(itemDO)){
						logger.debug("ITEM :: Ttem Type Code Updated ------->"+itemDO.getItemCode());
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
						logger.debug("ITEM :: Update base List Size-------------------->"+baseList.size());
					}
				}
			}
		}
		logger.debug("ITEM :: MaterialMasterSAPIntegrationServiceImpl :: getUpdateMaterialDOFromTO :: End");
		return baseList;
	}
	
private CSDSAPItemDO convertItemTOtoDO(SAPMaterialTO materialTo,boolean isUpdate, CSDSAPItemDO itmDO) throws CGBusinessException, CGSystemException {
		
		logger.debug("ITEM ::  MaterialMasterSAPIntegrationServiceImpl :: convertItemTOtoDO :: Start");
		CSDSAPItemDO itemDO = new CSDSAPItemDO();
		
		if(isUpdate && (!StringUtil.isNull(itmDO)
				&& (!StringUtil.isEmptyInteger(itmDO.getItemId())))){ 
			itemDO.setItemId(itmDO.getItemId());
		}else{
			itemDO.setItemId(!StringUtil.isEmptyInteger(materialTo.getItemId())?materialTo.getItemId() :null);
			logger.debug("ITEM ::  Item ID ----------------->"+itemDO.getItemId());
		}
		logger.debug("ITEM ::  Item ID----------------->"+itemDO.getItemId());
		
		if(!StringUtil.isStringEmpty(materialTo.getItemCode())){
			itemDO.setItemCode(materialTo.getItemCode());
		}
		logger.debug("ITEM ::  itemDO getItemCode----------------->"+itemDO.getItemCode());
		if(!StringUtil.isStringEmpty(materialTo.getItemName())){
			itemDO.setItemName(materialTo.getItemName());
		}
		logger.debug("ITEM ::  itemDO getItemName----------------->"+itemDO.getItemName());
		if(!StringUtil.isStringEmpty(materialTo.getItemSeries())
				&& materialTo.getItemSeries().contains("N")){
			itemDO.setItemSeries(null);
		}else{
			itemDO.setItemSeries(materialTo.getItemSeries());
		}
		logger.debug("ITEM ::  itemDO getItemSeries----------------->"+itemDO.getItemSeries());
		if(!StringUtil.isStringEmpty(materialTo.getIsActive())){
			itemDO.setIsActive(materialTo.getIsActive());
		}
		logger.debug("ITEM ::  itemDO getIsActive----------------->"+itemDO.getIsActive());
		if(!StringUtil.isStringEmpty(materialTo.getDescription())){
			itemDO.setDescription(materialTo.getDescription());
		}
		logger.debug("ITEM ::  itemDO getDescription----------------->"+itemDO.getDescription());
		if(!StringUtil.isStringEmpty(materialTo.getUom())){
			itemDO.setUom(materialTo.getUom());
		}
		logger.debug("ITEM ::  itemDO getUom----------------->"+itemDO.getUom());
		if(!StringUtil.isEmptyInteger(materialTo.getSeriesLength())){
			itemDO.setSeriesLength(materialTo.getSeriesLength());
		}
		logger.debug("ITEM ::  itemDO getSeriesLength----------------->"+itemDO.getSeriesLength());
		
		ItemTypeDO itemTypeDO = null;
		if(!StringUtil.isStringEmpty(materialTo.getItemTypeCode())){
			logger.debug("ITEM ::  Item Type Code From TO ------------------> "+materialTo.getItemTypeCode());
			String itemTypeCode = materialTo.getItemTypeCode();
			itemTypeDO = stockUniversalDAO.getItemTypeByCode(itemTypeCode);
			if(!StringUtil.isNull(itemTypeDO)){
				itemDO.setItemTypeDO(itemTypeDO);
				logger.debug("ITEM ::  itemDO Item Type ID ----------------->"+itemDO.getItemTypeDO().getItemTypeId());
			}
		}
		itemDO.setSapStatusInBound("C");
		logger.debug("ITEM ::  MaterialMasterSAPIntegrationServiceImpl :: convertItemTOtoDO :: End");
		return itemDO;
	}
	
	@Override
	public boolean updateDetails(List<CSDSAPItemDO> updateMaterialDOs) throws CGSystemException {
		logger.error("ITEM :: MaterialMasterSAPTransactionServiceImpl :: updateDetails :: START");
		boolean isUpdate = false;
		try{
			isUpdate = integrationDAO.updateDetailsOfMaterial(updateMaterialDOs);
		}catch(Exception e){
			logger.error("ITEM :: Exception IN :: MaterialMasterSAPTransactionServiceImpl :: updateDetails :: ",e);
			 throw new CGSystemException(e);
		}
		logger.error("ITEM :: MaterialMasterSAPTransactionServiceImpl :: updateDetails :: End");
		return isUpdate;
	}
	
	public boolean saveDetailsOneByOne(List<CSDSAPItemDO> updateMaterialDOs) {
		logger.debug("ITEM :: MaterialMasterSAPIntegrationServiceImpl :: saveDetailsOneByOne :: START");
		List<CSDSAPItemDO> errorVendorDOs = null;
		List<SAPItemDO> stagingItemDOs = null;
		SAPItemDO stagingItemDO = null;
		boolean  isSaved = false;
		try{
			//new method created 
			errorVendorDOs = integrationDAO.saveDetailsOneByOneForMaterials(updateMaterialDOs);
			if(!StringUtil.isEmptyColletion(errorVendorDOs)){
				for(CSDSAPItemDO errorRecord : errorVendorDOs){
					stagingItemDO = new SAPItemDO();
					stagingItemDO = convertErrorRecordsToStagingDO(errorRecord);
					stagingItemDOs.add(stagingItemDO);
				}
				if(!StringUtil.isEmptyColletion(stagingItemDOs)){
					isSaved = integrationDAO.saveMaterialErrorRecords(stagingItemDOs);
					if(isSaved){
						//If Saved Successfully into staging table trigger mail to SAP
						sendEmail(stagingItemDOs);
					}
				}
			}
		}catch(Exception e){
			logger.error("ITEM :: Exception IN :: MaterialMasterSAPIntegrationServiceImpl :: saveDetailsOneByOne :: ",e);
		}
		logger.debug("ITEM :: MaterialMasterSAPIntegrationServiceImpl :: saveDetailsOneByOne :: END");
		return isSaved;
	}
		
		private SAPItemDO convertErrorRecordsToStagingDO(CSDSAPItemDO errorRecord) throws CGSystemException {
			
			logger.debug("ITEM ::  MaterialMasterSAPIntegrationServiceImpl :: convertErrorRecordsToStagingDO :: Start");
			
			//SAPVendorDO stagingVendorDO = new SAPVendorDO();
			SAPItemDO stagingItemDO = new SAPItemDO();
			if((!StringUtil.isNull(errorRecord)
					&& (!StringUtil.isEmptyInteger(errorRecord.getItemId())))){ 
				stagingItemDO.setId(errorRecord.getItemId().longValue());
			}/*else{
			stagingVendorDO.setId(!StringUtil.isEmptyInteger(errorRecord.getVendorId())?errorRecord.getVendorId() :null);
			}*/
			logger.debug("ITEM ::  Staging Item ID ----------------->"+stagingItemDO.getId());
			
			if(!StringUtil.isStringEmpty(errorRecord.getItemCode())){
				stagingItemDO.setItemCode(errorRecord.getItemCode());
			}
			logger.debug("ITEM ::  Staging Item Code----------------->"+stagingItemDO.getItemCode());
			
			if(!StringUtil.isStringEmpty(errorRecord.getItemName())){
				stagingItemDO.setItemName(errorRecord.getItemName());
			}
			logger.debug("ITEM ::  Item Name ----------------->"+stagingItemDO.getItemName());
			
			if(!StringUtil.isStringEmpty(errorRecord.getUom())){
				stagingItemDO.setUom(errorRecord.getUom());
			}
			logger.debug("ITEM ::  Uom ----------------->"+stagingItemDO.getUom());
			
			if(!StringUtil.isStringEmpty(errorRecord.getUom())){
				stagingItemDO.setUom(errorRecord.getUom());
			}
			logger.debug("ITEM ::  UomDescription ----------------->"+stagingItemDO.getUom());
			
			if(!StringUtil.isStringEmpty(errorRecord.getItemSeries())){
				stagingItemDO.setItemSeries(errorRecord.getItemSeries());
			}
			logger.debug("ITEM ::  ItemSeries ----------------->"+stagingItemDO.getItemSeries());
			
			if(!StringUtil.isEmptyInteger(errorRecord.getSeriesLength())){
				stagingItemDO.setSeriesLength(errorRecord.getSeriesLength());
			}
			logger.debug("ITEM ::  SeriesLength ----------------->"+stagingItemDO.getSeriesLength());
			
			/*if(!StringUtil.isNull(errorRecord.getPurchaseDate())){
				stagingItemDO.setPurchaseDate(errorRecord.getPurchaseDate());
			}
			logger.debug("ITEM ::  PurchaseDate ----------------->"+stagingItemDO.getPurchaseDate());*/
			
			if(!StringUtil.isStringEmpty(errorRecord.getIsActive())){
				stagingItemDO.setCurStatus(errorRecord.getIsActive().charAt(0));
			}
			logger.debug("ITEM ::  IsActive ----------------->"+stagingItemDO.getCurStatus());
			
			if(!StringUtil.isNull(errorRecord.getItemTypeDO())
					&& !StringUtil.isStringEmpty(errorRecord.getItemTypeDO().getItemTypeCode())){
				stagingItemDO.setItemTypeCode(errorRecord.getItemTypeDO().getItemTypeCode());
			}
			logger.debug("ITEM ::  ItemTypeDO ----------------->"+stagingItemDO.getItemTypeCode());
			
			if(!StringUtil.isNull(errorRecord.getItemTypeDO())
					&& !StringUtil.isStringEmpty(errorRecord.getItemTypeDO().getItemTypeName())){
				stagingItemDO.setItemTypeName(errorRecord.getItemTypeDO().getItemTypeName());
			}
			logger.debug("ITEM ::  ItemTypeDO ----------------->"+stagingItemDO.getItemTypeName());
			
			if(!StringUtil.isStringEmpty(errorRecord.getDescription())){
				stagingItemDO.setDescription(errorRecord.getDescription());
			}
			logger.debug("ITEM ::  Description ----------------->"+stagingItemDO.getDescription());
			
			/*if(!StringUtil.isNull(errorRecord.getIsSeriesVerifier())){
				stagingItemDO.setIsSeriesVerifier(errorRecord.getIsSeriesVerifier());
			}
			logger.debug("ITEM ::  IsSeriesVerifier ----------------->"+stagingItemDO.getIsSeriesVerifier());*/
			
			//Add RHO CODE
			logger.debug("ITEM ::  MaterialMasterSAPIntegrationServiceImpl :: convertErrorRecordsToStagingDO :: End");
			return stagingItemDO;
		}
		
		
		public List<CSDSAPItemDO> getDOFromTO(List<SAPMaterialTO> materials) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException {
			logger.debug("ITEM :: MaterialMasterSAPTransactionServiceImpl :: getDOFromTO :: Start");
			List<CSDSAPItemDO> baseList = null;
			if(materials != null && !materials.isEmpty()) {
				baseList = new ArrayList<CSDSAPItemDO>(materials.size());
				logger.debug("ITEM :: SAP ITEM LIST SIZE -------------------->"+baseList.size());
				CSDSAPItemDO itemDO = null;
				for(SAPMaterialTO materialTO : materials) {
					if(!StringUtil.isStringEmpty(materialTO.getItemCode())){
						logger.debug("ITEM :: Material TO Item CODE -------------->"+materialTO.getItemCode());
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
							logger.debug("ITEM :: Save base List Size-------------------->"+baseList.size());
						}
					}
				}
			}
			logger.debug("ITEM :: MaterialMasterSAPTransactionServiceImpl :: getDOFromTO :: End");
			return baseList;
		}
		
		private void sendEmail(List<SAPItemDO> stagingItemDOs) throws CGBusinessException, CGSystemException {
			logger.debug("ITEM :: MaterialMasterSAPTransactionServiceImpl :: sendEmail :: Start");
			try {
				List<MailSenderTO> mailerList = new ArrayList<>();
				//Prepare Subject and add it into Mail Body
				StringBuilder plainMailBody = prepareMailBody(stagingItemDOs);
				//String subject="your complaint with reference number "+serviceRequestFollowupDO.getServiceRequestDO().getServiceRequestNo()+" has been followup";
				//subject=subject+" for consignment/Booking ref no: "+serviceRequestFollowupDO.getServiceRequestDO().getBookingNo();
				//StringBuilder plainMailBody = getMailBody(subject);
				prepareCallerMailAddress(mailerList,plainMailBody);
				//prepareExecutiveMail(serviceRequestFollowupDO, mailerList);
				for(MailSenderTO senderTO:mailerList){
					emailSenderUtil.sendEmail(senderTO);
				}
			} catch (Exception e) {
				logger.error("ITEM :: ServiceRequestForServiceReqServiceImpl::saveOrUpdateServiceReqDtls ::sendMail",e);
			}
			logger.debug("ITEM :: MaterialMasterSAPTransactionServiceImpl :: sendEmail :: End");
		}
		
		private StringBuilder prepareMailBody(List<SAPItemDO> stagingItemDOs){
			logger.debug("MaterialMasterSAPTransactionServiceImpl :: prepareMailBody :: Start");
			StringBuilder plainMailBody = new StringBuilder();
			plainMailBody.append("<html><body> Dear Sir/madam");
			plainMailBody.append("<br/><br/>Error came while processing Vendor Records to CSD Database");
			if(!StringUtil.isEmptyColletion(stagingItemDOs)){
				for(SAPItemDO stagingItemDO : stagingItemDOs){
					if(!StringUtil.isStringEmpty(stagingItemDO.getItemCode())){
						plainMailBody.append("<br/><br/>Vendor Code : "+stagingItemDO.getItemCode());
					}
					if(!StringUtil.isStringEmpty(stagingItemDO.getException())){
						plainMailBody.append("<br/><br/>Exception : "+stagingItemDO.getException());
					}
				}
			}
			plainMailBody.append("<BR><BR> Regarads,<BR> FFCL IT support");
			logger.debug("MaterialMasterSAPTransactionServiceImpl :: prepareMailBody :: End");
			return plainMailBody;
		}
		
		private void prepareCallerMailAddress(List<MailSenderTO> mailerList,StringBuilder plainMailBody) {
			logger.debug("MaterialMasterSAPTransactionServiceImpl :: prepareCallerMailAddress :: Start");
			MailSenderTO callerSenderTO=new MailSenderTO();
			callerSenderTO.setTo(new String[]{SAPIntegrationConstants.EMAILD_ID});
			//callerSenderTO.setMailSubject(subject);
			callerSenderTO.setPlainMailBody(plainMailBody.toString());
			mailerList.add(callerSenderTO);
			logger.debug("MaterialMasterSAPTransactionServiceImpl :: prepareCallerMailAddress :: End");
		}

		@Override
		public boolean saveDetailsForMaterial(List<CSDSAPItemDO> materialDOs)throws CGSystemException {
			logger.error("ITEM :: MaterialMasterSAPTransactionServiceImpl :: saveDetailsForMaterial :: START");
			boolean isUpdate = false;
			try{
				isUpdate = integrationDAO.saveDetailsForMaterial(materialDOs);
			}catch(Exception e){
				logger.error("ITEM :: Exception IN :: MaterialMasterSAPTransactionServiceImpl :: saveDetailsForMaterial :: ",e);
				 throw new CGSystemException(e);
			}
			logger.error("ITEM :: MaterialMasterSAPTransactionServiceImpl :: saveDetailsForMaterial :: END");
			return isUpdate;
		}
	

}
