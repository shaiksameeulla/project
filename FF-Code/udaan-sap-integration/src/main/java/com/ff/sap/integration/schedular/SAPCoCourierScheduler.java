package com.ff.sap.integration.schedular;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.coloading.SAPCocourierDO;
import com.ff.domain.coloading.SAPColoaderRatesDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.material.stock.StockSAPIntegrationService;
import com.ff.sap.integration.to.SAPCoCourierTO;
import com.firstflight.mm.csdtosap.serviceentrysheetcocourier.DTCSDServiceEntrySheetCoCourier;
import com.firstflight.mm.csdtosap.serviceentrysheetcocourier.SICSDServiceEntrySheetCoCourierOut;

/**
 * @author cbhure
 *
 */
public class SAPCoCourierScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPCoCourierScheduler.class);
	public StockSAPIntegrationService cocourierSAPService;
	private SICSDServiceEntrySheetCoCourierOut client;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("COCOURIER :: SAPCoCourierScheduler :: executeInternal :: Start");
		String sapStatus = null;
		String exception = null;
		DTCSDServiceEntrySheetCoCourier coCourier = null;
		DTCSDServiceEntrySheetCoCourier.ServiceEntrySheetCoCourier coc = null;
		
		SAPCoCourierTO sapCoCourierTO = new SAPCoCourierTO();
		sapCoCourierTO.setMaxCheck(SAPIntegrationConstants.MAX_CHECK);
		sapCoCourierTO.setSapStatus(SAPIntegrationConstants.SAP_STATUS);
		List<DTCSDServiceEntrySheetCoCourier.ServiceEntrySheetCoCourier> celements = null;
		List<SAPCocourierDO> sapCocourierDOList = null;
		
		List<SAPColoaderRatesDO> sapColoaderList = null;
		try {
			coCourier = new DTCSDServiceEntrySheetCoCourier();
			sapCocourierDOList = cocourierSAPService.findCocourierDtls(sapCoCourierTO);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			for(SAPCocourierDO sapCocourierDO : sapCocourierDOList){
				celements =  coCourier.getServiceEntrySheetCoCourier();
				coc = new DTCSDServiceEntrySheetCoCourier.ServiceEntrySheetCoCourier();
				if(!StringUtil.isNull(sapCocourierDO)){ 
					
					if(!StringUtil.isNull(sapCocourierDO.getDate())){
						coc.setDate(DateUtil.getDDMMYYYYDateToString(sapCocourierDO.getDate()));
					}
					logger.debug("Date--------------->"+coc.getDate());
					
					if(!StringUtil.isEmptyLong(sapCocourierDO.getId())){
						coc.setTransactionNo(String.valueOf(sapCocourierDO.getId()));
					}
					logger.debug("Transaction Number -------------->"+coc.getTransactionNo());
					
					if(!StringUtil.isStringEmpty(sapCocourierDO.getVendorCode())){
						coc.setVendor(sapCocourierDO.getVendorCode());
					}
					logger.debug("Name Of Vendor--------------->"+coc.getVendor());
					
					if(!StringUtil.isStringEmpty(sapCocourierDO.getServiceType())){
						coc.setServiceType(sapCocourierDO.getServiceType());
					}
					logger.debug("Service Type--------------->"+coc.getServiceType());
					
					if(!StringUtil.isStringEmpty(sapCocourierDO.getConsgType())){
						coc.setDoxNonDoxType(sapCocourierDO.getConsgType());
					}
					logger.debug("Consg Type--------------->"+coc.getDoxNonDoxType());
					
					if(!StringUtil.isNull(sapCocourierDO.getQty())){
						coc.setCountOfConsignments(String.valueOf(sapCocourierDO.getQty()));
					}
					logger.debug("COC ---------->"+coc.getCountOfConsignments());
					
					if(!StringUtil.isStringEmpty(sapCocourierDO.getProductCode())){
						coc.setProductCode(sapCocourierDO.getProductCode());
					}
					logger.debug("Service Type--------------->"+coc.getProductCode());
					
					if(!StringUtil.isStringEmpty(sapCocourierDO.getOffCode())){
						coc.setPlant(sapCocourierDO.getOffCode());
					}
					logger.debug("Ofc Code --------------->"+coc.getPlant());
					
					if(!StringUtil.isEmptyDouble(sapCocourierDO.getWeight())){
						coc.setWeight(String.valueOf(sapCocourierDO.getWeight()));
					}
					logger.debug("Weight ---------->"+coc.getWeight());
					
					if(!StringUtil.isStringEmpty(sapCocourierDO.getStatus())){
						coc.setPickUpOrDeliveryFlag(String.valueOf(sapCocourierDO.getStatus()));
					}
					logger.debug("Delivery Status ---------->"+coc.getWeight());
					
					celements.add(coc);
				}
			}
		}catch (CGBusinessException e) {
			logger.error("COCOURIER :: Error In :: SAPCoCourierScheduler :: executeInternal",e);
		}
		if(!StringUtil.isEmptyList(coCourier.getServiceEntrySheetCoCourier())){
			try{
				client.siCSDServiceEntrySheetCoCourierOut(coCourier);
				sapStatus = "C"; 
			}catch(Exception e){
				sapStatus = "N";
				exception = e.getMessage();
				logger.error("COCOURIER :: Error In :: SAPCoCourierScheduler :: executeInternal",e);
			}
			finally{
				try {
					cocourierSAPService.updateCocourierStagingStatusFlag(sapStatus,sapCocourierDOList,exception);
				} catch (CGSystemException e) {
					logger.error("COCOURIER :: Error In :: updateStkRequisitionStagingStatusFlag :: executeInternal",e);
				}
			}
		}
		logger.debug("COCOURIER :: SAPCoCourierScheduler :: executeInternal :: After webservice call");
		logger.debug("COCOURIER :: SAPCoCourierScheduler :: executeInternal :: End");
	}

	/**
	 * @param cocourierSAPService the cocourierSAPService to set
	 */
	public void setCocourierSAPService(
			StockSAPIntegrationService cocourierSAPService) {
		this.cocourierSAPService = cocourierSAPService;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(SICSDServiceEntrySheetCoCourierOut client) {
		this.client = client;
	}

	
	
}

