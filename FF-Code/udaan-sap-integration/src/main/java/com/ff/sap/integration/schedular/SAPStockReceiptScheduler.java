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
import com.ff.domain.stockmanagement.operations.receipt.SAPStockReceiptDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.material.stock.StockSAPIntegrationService;
import com.ff.sap.integration.to.SAPStockReceiptTO;
import com.firstflight.mm.csdtosap.goodsreceipts.DTCSDGoodsReceipt;
import com.firstflight.mm.csdtosap.goodsreceipts.SICSDGoodsReceiptOut;

/**
 * @author cbhure
 *
 */
public class SAPStockReceiptScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPStockReceiptScheduler.class);
	private SICSDGoodsReceiptOut client; 
	public StockSAPIntegrationService stockSAPIntServiceForReceipt;
	
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("STOCKACKNOWLEDGEMENT :: SAPStockReceiptScheduler :: executeInternal :: Start ");
		DTCSDGoodsReceipt stockReceipt = null;
		DTCSDGoodsReceipt.GoodsReceipt stkReceipt = null; 
		
		SAPStockReceiptTO receiptTO = new SAPStockReceiptTO();
		receiptTO.setSapStatus(SAPIntegrationConstants.SAP_STATUS);
		receiptTO.setMaxCheck(SAPIntegrationConstants.MAX_CHECK);
		List<DTCSDGoodsReceipt.GoodsReceipt> elements =  null;
		List<SAPStockReceiptDO> sapStockReceiptList = null;
		
		try {
			stockReceipt = new DTCSDGoodsReceipt();
			sapStockReceiptList = stockSAPIntServiceForReceipt.findStockReceiptDtlsForSAPIntegration(receiptTO);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(SAPStockReceiptDO stkReceiptDO : sapStockReceiptList){
				elements =  stockReceipt.getGoodsReceipt();
				stkReceipt = new DTCSDGoodsReceipt.GoodsReceipt();
				if(!StringUtil.isNull(BigInteger.valueOf(stkReceiptDO.getRowNumber()))){
					stkReceipt.setROWNUMBER(BigInteger.valueOf(stkReceiptDO.getRowNumber())); 
				}
				logger.debug("Row Number--------------->"+stkReceipt.getROWNUMBER());
				
				if(!StringUtil.isNull(stkReceiptDO.getReceivedDate())){
					GregorianCalendar gregCalenderDdate = new GregorianCalendar();
					gregCalenderDdate.setTime(stkReceiptDO.getReceivedDate());
					try {
						XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
						stkReceipt.setRECEIVEDDATE(xmlGregCalDate);
					} catch (DatatypeConfigurationException e) {
						logger.error("STOCKACKNOWLEDGEMENT :: SAPStockReceiptScheduler :: executeInternal :: error::",e);
					}
				}
				logger.debug("Received Date GregorianCalendar Format ----------->"+stkReceipt.getRECEIVEDDATE());
				
				if(!StringUtil.isNull(stkReceiptDO.getRequisitionNumber())){
					stkReceipt.setREQUISITIONNUMBER(stkReceiptDO.getRequisitionNumber());
				}
				logger.debug("RequisitionNumber--------------->"+stkReceipt.getREQUISITIONNUMBER());
				
				if(!StringUtil.isNull(stkReceiptDO.getItemCode())){
					stkReceipt.setITEMCODE(stkReceiptDO.getItemCode());
				}
				logger.debug("Item Code--------------->"+stkReceipt.getITEMCODE());
				
				if(!StringUtil.isNull(stkReceiptDO.getItemTypeCode())){
					stkReceipt.setITEMTYPECODE(stkReceiptDO.getItemTypeCode());
				}
				logger.debug("Item Type Code--------------->"+stkReceipt.getITEMTYPECODE());
				
				if(!StringUtil.isNull(stkReceiptDO.getDescription())){
					stkReceipt.setDESCRIPTION(stkReceiptDO.getDescription());
				}
				logger.debug("Description--------------->"+stkReceipt.getDESCRIPTION());
				
				if(!StringUtil.isNull(stkReceiptDO.getUom())){
					stkReceipt.setUNITOFMEASURE(stkReceiptDO.getUom());
				}
				logger.debug("UOM--------------->"+stkReceipt.getUNITOFMEASURE());
				
				if(!StringUtil.isNull(BigInteger.valueOf(stkReceiptDO.getReceivedQty()))){
					stkReceipt.setRECEIVEDQUANTITY(BigInteger.valueOf(stkReceiptDO.getReceivedQty()));
				}
				logger.debug("Received Qty--------------->"+stkReceipt.getRECEIVEDQUANTITY());
				
				if(!StringUtil.isNull(stkReceiptDO.getAckNumber())){
					stkReceipt.setACKNOWLEDGEMENTNUMBER(stkReceiptDO.getAckNumber());
				}
				logger.debug("Ack Number --------------->"+stkReceipt.getACKNOWLEDGEMENTNUMBER());
				
				if(!StringUtil.isNull(stkReceiptDO.getIssueNumber())){
					stkReceipt.setStockIssueNo(stkReceiptDO.getIssueNumber());
				}
				logger.debug("Issue Number --------------->"+stkReceipt.getStockIssueNo());
				
				Date today = Calendar.getInstance().getTime();        
				String dateStamp = df.format(today);
				stkReceipt.setTimestamp(dateStamp);
				elements.add(stkReceipt);
			}
		} catch (CGSystemException | CGBusinessException e) { 
			logger.error("Exception IN :: SAPStockReceiptScheduler :: findStockReceiptDtlsForSAPIntegration :: ",e);
		} 
		if(!StringUtil.isEmptyList(stockReceipt.getGoodsReceipt())){
			String sapStatus = null;
			String exception = null;
			try{
				client.siCSDGoodsReceiptOut(stockReceipt);
				sapStatus = "C"; 
			}catch(Exception e){
				sapStatus = "N"; 
				exception = e.getMessage();
			}
			finally{
				try {
					stockSAPIntServiceForReceipt.updateStkReceiptStagingStatusFlag(sapStatus,sapStockReceiptList,exception);
				} catch (CGSystemException e) {
					logger.error("STOCKACKNOWLEDGEMENT :: SAPStockReceiptScheduler :: executeInternal :: error::",e);
				}
			}
		}
		logger.debug("STOCKACKNOWLEDGEMENT :: SAPStockTransferScheduler::executeInternal::after webservice call=======>");
		logger.debug("STOCKACKNOWLEDGEMENT :: SAPStockTransferScheduler::executeInternal::end=======>");
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(SICSDGoodsReceiptOut client) {
		this.client = client;
	}

	/**
	 * @param stockSAPIntServiceForReceipt the stockSAPIntServiceForReceipt to set
	 */
	public void setStockSAPIntServiceForReceipt(
			StockSAPIntegrationService stockSAPIntServiceForReceipt) {
		this.stockSAPIntServiceForReceipt = stockSAPIntServiceForReceipt;
	}

	
	
}
