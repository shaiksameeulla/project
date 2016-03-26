package com.ff.sap.integration.schedular;

import java.math.BigDecimal;
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
import com.ff.domain.stockmanagement.operations.stockreturn.SAPStockReturnDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.material.stock.StockSAPIntegrationService;
import com.ff.sap.integration.to.SAPStockReturnTO;
import com.firstflight.mm.csdtosap.stockreturn.DTCSDStockReturn;
import com.firstflight.mm.csdtosap.stockreturn.SICSDStockReturnOut;

/**
 * @author CBHURE
 *
 */
public class SAPStockReturnScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPExpenseScheduler.class);
	private SICSDStockReturnOut client; 
	public StockSAPIntegrationService stockSAPIntegrationServiceForStkReturn;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("STOCKRETURN :: SAPStockReturnScheduler::executeInternal::start=======>");
		DTCSDStockReturn stkReturn = null;
		DTCSDStockReturn.StockReturn sr = null;
		
		SAPStockReturnTO sapStkRetTo = new SAPStockReturnTO();
		sapStkRetTo.setSapStatus(SAPIntegrationConstants.SAP_STATUS);
		sapStkRetTo.setMaxCheck(SAPIntegrationConstants.MAX_CHECK);
		List<DTCSDStockReturn.StockReturn> elements =  null;
		List<SAPStockReturnDO> sapstkReturnDOList = null;
		try {
			stkReturn = new DTCSDStockReturn();
			sapstkReturnDOList = stockSAPIntegrationServiceForStkReturn.findStockReturnForSAPIntegration(sapStkRetTo);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			for(SAPStockReturnDO sapStkRetunDO : sapstkReturnDOList){
				if(!StringUtil.isNull(sapStkRetunDO)){
					elements =  stkReturn.getStockReturn();
					sr = new DTCSDStockReturn.StockReturn();
					
					if(!StringUtil.isEmptyInteger(sapStkRetunDO.getReturnQty())){
						sr.setQuantity(BigDecimal.valueOf(sapStkRetunDO.getReturnQty()));
					}
					if(!StringUtil.isStringEmpty(sapStkRetunDO.getReturnNumber())){
						sr.setStkTransNo(sapStkRetunDO.getReturnNumber());
					}
					if(!StringUtil.isStringEmpty(sapStkRetunDO.getReturningOfcCode())){
						sr.setLoggedInPlant(sapStkRetunDO.getReturningOfcCode());
					}
					if(!StringUtil.isStringEmpty(sapStkRetunDO.getItemCode())){
						sr.setMaterialCode(sapStkRetunDO.getItemCode());
					}
					if(!StringUtil.isStringEmpty(sapStkRetunDO.getIssueNumber())){
						sr.setStockIssueNo(sapStkRetunDO.getIssueNumber());
					}
					if(!StringUtil.isNull(sapStkRetunDO.getReturnDate())){
						GregorianCalendar gregCalenderDdate = new GregorianCalendar();
						gregCalenderDdate.setTime(sapStkRetunDO.getReturnDate());
						try {
							XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
							sr.setDocumentDate(xmlGregCalDate);
						} catch (DatatypeConfigurationException e) {
							logger.error("STOCKRETURN :: SAPStockReturnScheduler::executeInternal::error",e);
						}
					}
					
					if(!StringUtil.isNull(sapStkRetunDO.getIssueDate())){
						GregorianCalendar gregCalenderDdate = new GregorianCalendar();
						gregCalenderDdate.setTime(sapStkRetunDO.getIssueDate());
						try {
							XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
							sr.setIssueDate(xmlGregCalDate);
						} catch (DatatypeConfigurationException e) {
							logger.error("STOCKRETURN :: SAPStockReturnScheduler::executeInternal::error",e);
						}
					}
					//Cust Code Pending
					Date today = Calendar.getInstance().getTime();        
					String dateStamp = df.format(today);
					sr.setTimestamp(dateStamp);
					elements.add(sr);
				}
			}
		} catch (CGSystemException | CGBusinessException e) {
			logger.error("STOCKRETURN :: Exception in SAPStockReturnScheduler :: ",e);
		}
	
		if(!StringUtil.isEmptyList(stkReturn.getStockReturn())){
			String sapStatus = null;
			String exception = null;
			try{
				client.siCSDStockReturnOut(stkReturn);
				sapStatus = "C"; 
			}catch(Exception e){
				sapStatus = "N"; 
				exception = e.getMessage();
				logger.debug("Error is "+e);
			}
			finally{
				try {
					stockSAPIntegrationServiceForStkReturn.updateStkReturnStagingStatusFlag(sapStatus,sapstkReturnDOList,exception);
				} catch (CGSystemException e) {
					logger.error("STOCKRETURN :: SAPStockReturnScheduler::executeInternal::error",e);
				}
			}
		}
		logger.debug("STOCKRETURN :: SAPStockReturnScheduler::executeInternal::after webservice call=======>");
		logger.debug("STOCKRETURN :: SAPStockReturnScheduler::executeInternal::end=======>");
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(SICSDStockReturnOut client) {
		this.client = client;
	}

	/**
	 * @param stockSAPIntegrationServiceForStkReturn the stockSAPIntegrationServiceForStkReturn to set
	 */
	public void setStockSAPIntegrationServiceForStkReturn(
			StockSAPIntegrationService stockSAPIntegrationServiceForStkReturn) {
		this.stockSAPIntegrationServiceForStkReturn = stockSAPIntegrationServiceForStkReturn;
	}

	
	
	
}
