package com.ff.sap.integration.schedular;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.stockmanagement.operations.requisition.SAPStockRequisitionDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.material.stock.StockSAPIntegrationService;
import com.ff.sap.integration.to.SAPStockRequisitionTO;
import com.firstflight.mm.csdtosap.purchasereq.DTCSDPurchaseRequisition;
import com.firstflight.mm.csdtosap.purchasereq.SICSDPurchaseRequisitionOut;

/**
 * @author cbhure
 *
 */
public class SAPPurchaseRequisitionScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPPurchaseRequisitionScheduler.class);
	private SICSDPurchaseRequisitionOut client; 
	public StockSAPIntegrationService stockSAPIntService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("STOCKREQUISION :: SAPPurchaseRequisitionScheduler :: executeInternal :: Start");
		DTCSDPurchaseRequisition purchaseRequisition = null;
		DTCSDPurchaseRequisition.PurchaseRequisition pr = null;
		
		SAPStockRequisitionTO stockRequisitionTo = new SAPStockRequisitionTO();
		stockRequisitionTo.setSapStatus(SAPIntegrationConstants.SAP_STATUS);
		stockRequisitionTo.setMaxCheck(SAPIntegrationConstants.MAX_CHECK);
		List<DTCSDPurchaseRequisition.PurchaseRequisition> elements =  null;
		
		List<SAPStockRequisitionDO> sapStkReqDoList = null;
		try {
			purchaseRequisition = new DTCSDPurchaseRequisition();
			sapStkReqDoList = stockSAPIntService.findRequisitionDtlsForSAPIntegration(stockRequisitionTo);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			for(SAPStockRequisitionDO sapStkReqDO : sapStkReqDoList){
				elements =  purchaseRequisition.getPurchaseRequisition();
				pr = new DTCSDPurchaseRequisition.PurchaseRequisition();
				if(!StringUtil.isNull(sapStkReqDO)){ 
					
					if(!StringUtil.isNull(sapStkReqDO.getRequisitionNumber())){
						pr.setREQUISITIONNUMBER(sapStkReqDO.getRequisitionNumber());
					}
					logger.debug("STOCKREQUISION :: RequisitionNumber--------------->"+pr.getREQUISITIONNUMBER());
					
					if(!StringUtil.isNull(sapStkReqDO.getOfficeCode())){
						pr.setREQUISITIONOFFICECODE(sapStkReqDO.getOfficeCode());
					}
					logger.debug("STOCKREQUISION :: REQUISITION OFFICE CODE--------------->"+pr.getREQUISITIONOFFICECODE());
					
					if(!StringUtil.isNull(BigInteger.valueOf(sapStkReqDO.getRowNumber()))){
						pr.setROWNUMBER(BigInteger.valueOf(sapStkReqDO.getRowNumber()));
					}
					logger.debug("STOCKREQUISION :: Row Number--------------->"+pr.getROWNUMBER());
					
					if(!StringUtil.isNull(String.valueOf(sapStkReqDO.getApprovedQty()))){
						pr.setAPPROVEDQUANTITY(String.valueOf(sapStkReqDO.getApprovedQty()));
					}
					logger.debug("STOCKREQUISION :: App QTY--------------->"+pr.getAPPROVEDQUANTITY());
					
					if(!StringUtil.isNull(sapStkReqDO.getDescription())){
						pr.setDESCRIPTION(sapStkReqDO.getDescription());
					}
					logger.debug("STOCKREQUISION :: Description--------------->"+pr.getDESCRIPTION());
					
					if(!StringUtil.isNull(sapStkReqDO.getItemCode())){
						pr.setITEMCODE(sapStkReqDO.getItemCode());
					}
					logger.debug("STOCKREQUISION :: Item Code--------------->"+pr.getITEMCODE());
					
					if(!StringUtil.isNull(sapStkReqDO.getItemTypeCode())){
						pr.setITEMTYPECODE(sapStkReqDO.getItemTypeCode());
					}
					logger.debug("STOCKREQUISION :: Item Type Code--------------->"+pr.getITEMTYPECODE());
					
					if(!StringUtil.isNull(sapStkReqDO.getUom())){
						pr.setUOM(sapStkReqDO.getUom());
					}
					logger.debug("STOCKREQUISION :: UOM--------------->"+pr.getUOM());
					
					if(!StringUtil.isNull(sapStkReqDO.getTxCreatedDate())){
						GregorianCalendar gregCalenderDdate = new GregorianCalendar();
						gregCalenderDdate.setTime(sapStkReqDO.getTxCreatedDate());
						try {
							XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
							pr.setREQCREATEDDATETIME(xmlGregCalDate);
						} catch (DatatypeConfigurationException e) {
							logger.debug("STOCKREQUISION :: SAPPurchaseRequisitionScheduler :: executeInternal :: error::",e);
						}
					}
					logger.debug("STOCKREQUISION :: REQ CREATED DATE TIME--------------->"+pr.getREQCREATEDDATETIME());
					
					if(!StringUtil.isNull(sapStkReqDO.getProcurementType())){
						pr.setProcurementType(sapStkReqDO.getProcurementType());
					}
					logger.debug("STOCKREQUISION :: Procurement Type--------------->"+pr.getProcurementType());
					
					if(!StringUtil.isNull(sapStkReqDO.getSeriesStartsWith())){
						pr.setSerierStartsWith(sapStkReqDO.getSeriesStartsWith());
					}
					logger.debug("STOCKREQUISION :: Series Starts With --------------->"+pr.getSerierStartsWith());
					
					if(!StringUtil.isNull(sapStkReqDO.getPrConsolidated())){
						pr.setISPRCONSOLIDATED(sapStkReqDO.getPrConsolidated());
					}
					logger.debug("STOCKREQUISION :: PR Consolidated--------------->"+pr.getISPRCONSOLIDATED());
					
					Date today = Calendar.getInstance().getTime();        
					String dateStamp = df.format(today);
					pr.setTimestamp(dateStamp);
					elements.add(pr);
				}
			}
		} catch (CGSystemException | CGBusinessException e) {
			logger.error("STOCKREQUISION :: Error In :: SAPPurchaseRequisitionScheduler :: executeInternal",e);
		}
		if(!StringUtil.isEmptyList(purchaseRequisition.getPurchaseRequisition())){
			String sapStatus = null;
			String exception = null;
			try{
				client.siCSDPurchaseRequisitionOut(purchaseRequisition);
				sapStatus = "C"; 
			}catch(Exception e){
				sapStatus = "N";;
				exception = e.getMessage();
				logger.error("STOCKREQUISION :: Error In :: SAPPurchaseRequisitionScheduler :: executeInternal",e);
			}
			finally{
				try {
					stockSAPIntService.updateStkRequisitionStagingStatusFlag(sapStatus,sapStkReqDoList,exception);
				} catch (CGSystemException e) {
					logger.error("STOCKREQUISION :: Error In :: updateStkRequisitionStagingStatusFlag :: executeInternal",e);
				}
			}
		}
		logger.debug("STOCKREQUISION :: SAPPurchaseRequisitionScheduler :: executeInternal :: After webservice call");
		logger.debug("STOCKREQUISION :: SAPPurchaseRequisitionScheduler :: executeInternal :: End");
	}
	
	public void setClient(SICSDPurchaseRequisitionOut client) {
		this.client = client;
	}
	/**
	 * @param stockSAPIntService the stockSAPIntService to set
	 */
	public void setStockSAPIntService(StockSAPIntegrationService stockSAPIntService) {
		this.stockSAPIntService = stockSAPIntService;
	}
}

