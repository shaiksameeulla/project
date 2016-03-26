package com.ff.sap.integration.schedular;

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
import com.ff.domain.stockmanagement.operations.cancel.SAPStockCancellationDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.material.stock.StockSAPIntegrationService;
import com.ff.sap.integration.to.SAPStockCancellationTO;
import com.firstflight.mm.csdtosap.goodscancellation.DTCSDGoodsCancellation;
import com.firstflight.mm.csdtosap.goodscancellation.SICSDGoodsCancellationOut;

/**
 * @author cbhure
 *
 */
public class SAPStockCancellationScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPStockCancellationScheduler.class);
	private SICSDGoodsCancellationOut client; 
	public StockSAPIntegrationService stockSAPIntServiceForCancellation;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("STOCKCANCELLATION :: SAPStockCancellationScheduler::executeInternal::start=======>");
		DTCSDGoodsCancellation stockCancellation = null;
		DTCSDGoodsCancellation.GoodsCancellation sc = null;
		
		SAPStockCancellationTO stockCancellationTO = new SAPStockCancellationTO();
		stockCancellationTO.setSapStatus(SAPIntegrationConstants.SAP_STATUS);
		stockCancellationTO.setMaxCheck(SAPIntegrationConstants.MAX_CHECK);
		List<SAPStockCancellationDO> sapStkCancelaltionDOList =  null;
		List<DTCSDGoodsCancellation.GoodsCancellation> elements = null;
		try {
			stockCancellation = new DTCSDGoodsCancellation();
			sapStkCancelaltionDOList = stockSAPIntServiceForCancellation.findCancellationDtlsForSAPIntegration(stockCancellationTO);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			for(SAPStockCancellationDO stkCancelDO : sapStkCancelaltionDOList){
				elements =  stockCancellation.getGoodsCancellation();
				sc = new DTCSDGoodsCancellation.GoodsCancellation();
				
				if(!StringUtil.isNull(stkCancelDO.getTransactionCreateDate())){
					GregorianCalendar gregCalenderDdate = new GregorianCalendar();
					gregCalenderDdate.setTime(stkCancelDO.getTransactionCreateDate());
					try {
						XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
						sc.setDocDate(xmlGregCalDate);
					} catch (DatatypeConfigurationException e) {
						logger.error("STOCKCANCELLATION :: SAPStockCancellationScheduler::executeInternal::error::",e);
					}
					logger.debug("Document Date (Trax Creation Date) ----------->"+sc.getDocDate());
				}
				
				if(!StringUtil.isNull(stkCancelDO.getCancelledDate())){
					GregorianCalendar gregCalenderDdate = new GregorianCalendar();
					gregCalenderDdate.setTime(stkCancelDO.getCancelledDate());
					try {
						XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
						sc.setPostingDate(xmlGregCalDate);
					} catch (DatatypeConfigurationException e) {
						logger.error("STOCKCANCELLATION :: SAPStockCancellationScheduler::executeInternal::error::",e);
					}
					logger.debug("Posting Date (Cancellation Date) ----------->"+sc.getPostingDate());
				}
				
				if(!StringUtil.isStringEmpty(stkCancelDO.getReason())){
					sc.setReason(stkCancelDO.getReason());
				}
				logger.debug("Reason --------------->"+sc.getReason());
				
				if(!StringUtil.isNull(stkCancelDO.getItemCode())){
					sc.setMaterialCode(stkCancelDO.getItemCode());
				}
				logger.debug("Item Code (Material Code)--------------->"+sc.getMaterialCode());
				
				if(!StringUtil.isNull(stkCancelDO.getCancellationNumber())){
					sc.setStkCanDocNo(stkCancelDO.getCancellationNumber());
				}
				logger.debug("Cancellation Doc No--------------->"+sc.getStkCanDocNo());
				
				if(!StringUtil.isEmptyInteger(stkCancelDO.getQuantity())){
					sc.setQuantity(String.valueOf(stkCancelDO.getQuantity()));
				}
				logger.debug("QTY--------------->"+sc.getQuantity());
				
				if(!StringUtil.isNull(stkCancelDO.getCancellationOfcCode())){
					sc.setLoggedInPlant(stkCancelDO.getCancellationOfcCode());
				}
				logger.debug("Looged In Plant (Office Code)--------------->"+sc.getLoggedInPlant());
				
				Date today = Calendar.getInstance().getTime();        
				String dateStamp = df.format(today);
				sc.setTimestamp(dateStamp);
				elements.add(sc);
			}
		} catch (CGSystemException | CGBusinessException e) {
			logger.error("STOCKCANCELLATION :: ERROR IN :: SAPStockCancellationScheduler ",e);
		}
		
		if(!StringUtil.isEmptyList(stockCancellation.getGoodsCancellation())){
			String sapStatus = null;
			String exception = null;
			try{
				client.siCSDGoodsCancellationOut(stockCancellation);
				sapStatus = "C"; 
			}catch(Exception e){
				sapStatus = "N"; 
				exception = e.getMessage();
				logger.debug("Error is "+e);
			}
			finally{
				try {
					stockSAPIntServiceForCancellation.updateStkCancelStagingStatusFlag(sapStatus,sapStkCancelaltionDOList,exception);
				} catch (CGSystemException e) {
					logger.error("STOCKCANCELLATION :: ERROR IN :: SAPStockCancellationScheduler ",e);
				}
			}
		}
		logger.debug("STOCKCANCELLATION :: SAPStockCancellationScheduler::executeInternal::after webservice call=======>");
		logger.debug("STOCKCANCELLATION :: SAPStockCancellationScheduler::executeInternal::end=======>");
	}
	
	/**
	 * @param client the client to set
	 */
	public void setClient(SICSDGoodsCancellationOut client) {
		this.client = client;
	}

	/**
	 * @param stockSAPIntServiceForCancellation the stockSAPIntServiceForCancellation to set
	 */
	public void setStockSAPIntServiceForCancellation(
			StockSAPIntegrationService stockSAPIntServiceForCancellation) {
		this.stockSAPIntServiceForCancellation = stockSAPIntServiceForCancellation;
	}
}

