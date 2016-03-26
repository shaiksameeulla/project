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
import com.ff.domain.stockmanagement.operations.transfer.SAPStockTransferDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.material.stock.StockSAPIntegrationService;
import com.ff.sap.integration.to.SAPStockTransferTO;
import com.firstflight.mm.csdtosap.stockreturnfromba.DTCSDStockReturnFromBA;
import com.firstflight.mm.csdtosap.stockreturnfromba.SICSDStockReturnFromBAOut;

/**
 * @author CBHURE
 *
 */
public class SAPStockReturnFromBAToBranchScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPExpenseScheduler.class);
	private SICSDStockReturnFromBAOut client; 
	public StockSAPIntegrationService stockSAPIntegrationServiceForStkReturnBA;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("STOCKTRANSFER :: SAPStockReturnFromBAToBranchScheduler :: executeInternal :: start=======>");
		DTCSDStockReturnFromBA stkReturnBA = null;
		DTCSDStockReturnFromBA.StockReturnFromBA srBa = null;
		
		SAPStockTransferTO sapStkRetTo = new SAPStockTransferTO();
		sapStkRetTo.setSapStatus(SAPIntegrationConstants.SAP_STATUS);
		sapStkRetTo.setMaxCheck(SAPIntegrationConstants.MAX_CHECK);
		List<DTCSDStockReturnFromBA.StockReturnFromBA> elements =  null;
		
		List<SAPStockTransferDO> sapstkTransferDOList = null;
		
		try {
			stkReturnBA = new DTCSDStockReturnFromBA();
			sapstkTransferDOList = stockSAPIntegrationServiceForStkReturnBA.findStockTransferForSAPIntegration(sapStkRetTo);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			for(SAPStockTransferDO sapStkRetunDO : sapstkTransferDOList){
				if(!StringUtil.isNull(sapStkRetunDO)){
					elements =  stkReturnBA.getStockReturnFromBA();
					srBa = new DTCSDStockReturnFromBA.StockReturnFromBA();
					
					if(!StringUtil.isStringEmpty(sapStkRetunDO.getBaNo())){
						srBa.setFROMBACODE(sapStkRetunDO.getBaNo());
					}
					if(!StringUtil.isStringEmpty(sapStkRetunDO.getItemCode())){
						srBa.setITEMCODE(sapStkRetunDO.getItemCode());
					}
					if(!StringUtil.isNull(sapStkRetunDO.getReturnDate())){
						GregorianCalendar gregCalenderDdate = new GregorianCalendar();
						gregCalenderDdate.setTime(sapStkRetunDO.getReturnDate());
						try {
							XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
							srBa.setTRANSCREATEDDATETIME(xmlGregCalDate);
						} catch (DatatypeConfigurationException e) {
							logger.error("STOCKTRANSFER :: SAPStockReturnFromBAToBranchScheduler :: executeInternal :: error",e);
						}
					}
					if(!StringUtil.isStringEmpty(sapStkRetunDO.getReturnNumber())){
						srBa.setTRANSFERNUMBER(sapStkRetunDO.getReturnNumber());
					}
					if(!StringUtil.isStringEmpty(sapStkRetunDO.getReturnOfficeCode())){
						srBa.setTOBRANCH(sapStkRetunDO.getReturnOfficeCode());
					}
					if(!StringUtil.isEmptyInteger(sapStkRetunDO.getReturnQty())){
						srBa.setQUANTITY(BigDecimal.valueOf(sapStkRetunDO.getReturnQty()));
					}
					logger.debug("Transfer BA Returning Qty---------------------------------->"+srBa.getQUANTITY());
					
					if(!StringUtil.isStringEmpty(sapStkRetunDO.getIssueNumber())){
						srBa.setStockIssueNo(sapStkRetunDO.getIssueNumber());
					}
					logger.debug("Transfer BA Issue Number---------------------------------->"+srBa.getStockIssueNo());
					Date today = Calendar.getInstance().getTime();        
					String dateStamp = df.format(today);
					srBa.setSAPTimestamp(dateStamp);
					elements.add(srBa);
				}
			}
		} catch (CGSystemException | CGBusinessException e) {
			logger.error("STOCKTRANSFER :: SAPStockReturnFromBAToBranchScheduler ",e);
		}
	
		if(!StringUtil.isEmptyList(stkReturnBA.getStockReturnFromBA())){
			String sapStatus = null;
			String exception = null;
			try{
				client.siCSDStockReturnFromBAOut(stkReturnBA);
				sapStatus = "C"; 
			}catch(Exception e){
				sapStatus = "N"; 
				exception = e.getMessage();
				logger.debug("Error is "+e);
			}
			finally{
				try {
					stockSAPIntegrationServiceForStkReturnBA.updateStkTransferStagingStatusFlag(sapStatus,sapstkTransferDOList,exception);
				} catch (CGSystemException e) {
					logger.error("STOCKTRANSFER :: SAPStockReturnFromBAToBranchScheduler :: updateStkTransferStagingStatusFlag",e);
				}
			}
		}
		logger.debug("SAPStockReturnFromBAToBranchScheduler :: executeInternal::after webservice call=======>");
		logger.debug("SAPStockReturnFromBAToBranchScheduler :: executeInternal :: end=======>");
	}

	/**
	 * @param stockSAPIntegrationServiceForStkReturnBA the stockSAPIntegrationServiceForStkReturnBA to set
	 */
	public void setStockSAPIntegrationServiceForStkReturnBA(
			StockSAPIntegrationService stockSAPIntegrationServiceForStkReturnBA) {
		this.stockSAPIntegrationServiceForStkReturnBA = stockSAPIntegrationServiceForStkReturnBA;
	}


	/**
	 * @param client the client to set
	 */
	public void setClient(SICSDStockReturnFromBAOut client) {
		this.client = client;
	}

	
	
}
