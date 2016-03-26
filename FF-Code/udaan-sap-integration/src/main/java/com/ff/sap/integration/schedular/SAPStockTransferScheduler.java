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
import com.ff.domain.stockmanagement.operations.issue.SAPStockIssueDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.material.stock.StockSAPIntegrationService;
import com.ff.sap.integration.to.SAPStockIssueTO;
import com.firstflight.mm.csdtosap.stktransfer.DTCSDStockTransfer;
import com.firstflight.mm.csdtosap.stktransfer.SICSDStockTransferOut;

/**
 * @author cbhure
 *
 */
public class SAPStockTransferScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPStockTransferScheduler.class);
	private SICSDStockTransferOut client; 
	public StockSAPIntegrationService stockSAPIntServiceForStockIssue;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("STOCKISSUE :: SAPStockTransferScheduler::executeInternal::start=======>");
		DTCSDStockTransfer stockTransfer = null; 
		DTCSDStockTransfer.StockTransfer stkTransfer = null;
		
		SAPStockIssueTO stkIssueTO = new SAPStockIssueTO();
		stkIssueTO.setSapStatus(SAPIntegrationConstants.SAP_STATUS);
		stkIssueTO.setMaxCheck(SAPIntegrationConstants.MAX_CHECK);
		
		List<SAPStockIssueDO> sapStockIssueDOList = null; 
		List<DTCSDStockTransfer.StockTransfer> elements =  null;
		try {
			stockTransfer = new DTCSDStockTransfer();
			sapStockIssueDOList = stockSAPIntServiceForStockIssue.findStockIssueDtlsForSAPIntegration(stkIssueTO);
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(SAPStockIssueDO stockIssue : sapStockIssueDOList){
				if(!StringUtil.isNull(stockIssue)){
					elements =  stockTransfer.getStockTransfer();
					stkTransfer = new DTCSDStockTransfer.StockTransfer();
					if(!StringUtil.isNull(BigInteger.valueOf(stockIssue.getRowNumber()))){
						stkTransfer.setROWNUMBER(BigInteger.valueOf(stockIssue.getRowNumber()));
					}
					logger.debug("Row Number--------------->"+stkTransfer.getROWNUMBER());
					
					if(!StringUtil.isNull(stockIssue.getIssueDate())){
						GregorianCalendar gregCalenderDdate = new GregorianCalendar();
						gregCalenderDdate.setTime(stockIssue.getIssueDate());
						try {
							XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
							stkTransfer.setISSUEDATE(xmlGregCalDate);
						} catch (DatatypeConfigurationException e) {
							logger.error("STOCKISSUE :: SAPStockTransferScheduler::executeInternal::error::",e);
						}
					}
					
					logger.debug("Issue Date In GregorianCalendar Format ----------->"+stkTransfer.getISSUEDATE());
					
					if(!StringUtil.isNull(stockIssue.getRequisitionNumber())){
						stkTransfer.setREQUISITIONNUMBER(stockIssue.getRequisitionNumber());
					}
					logger.debug("RequisitionNumber--------------->"+stkTransfer.getREQUISITIONNUMBER());
					
					if(!StringUtil.isStringEmpty(stockIssue.getItemCode())){
						stkTransfer.setITEMCODE(stockIssue.getItemCode());
					}
					logger.debug("Item Code--------------->"+stkTransfer.getITEMCODE());
					
					if(!StringUtil.isStringEmpty(stockIssue.getItemTypeCode())){
						stkTransfer.setITEMTYPECODE(stockIssue.getItemTypeCode());
					}
					logger.debug("Item Type Code--------------->"+stkTransfer.getITEMTYPECODE());
					
					if(!StringUtil.isNull(stockIssue.getDescription())){
						stkTransfer.setDESCRIPTION(stockIssue.getDescription());
					}
					logger.debug("Description--------------->"+stkTransfer.getDESCRIPTION());
					
					if(!StringUtil.isNull(stockIssue.getUom())){
						stkTransfer.setUNITOFMEASURE(stockIssue.getUom());
					}
					logger.debug("UOM--------------->"+stkTransfer.getUNITOFMEASURE());
					
					if(!StringUtil.isNull(BigInteger.valueOf(stockIssue.getIssuedQty()))){
						stkTransfer.setISSUEDQUANTITY(BigInteger.valueOf(stockIssue.getIssuedQty()));
					}
					logger.debug("Issued Qty--------------->"+stkTransfer.getISSUEDQUANTITY());
					
					if(!StringUtil.isNull(stockIssue.getIssueNumber())){
						stkTransfer.setISSUENUMBER(stockIssue.getIssueNumber());
					}
					logger.debug("Issue Number--------------->"+stkTransfer.getISSUENUMBER());
					
					if(!StringUtil.isNull(stockIssue.getIssuedToofficeCode())){
						stkTransfer.setISSUEDTOOFFICE(stockIssue.getIssuedToofficeCode());
					}
					logger.debug("Issued To OFFICE CODE Branch --------------->"+stkTransfer.getISSUEDTOOFFICE());
					
					if(!StringUtil.isNull(stockIssue.getIssuedOfficeCode())){
						stkTransfer.setISSUEOFFICEID(String.valueOf(stockIssue.getIssuedOfficeCode()));
					}
					logger.debug("Issued OFFICE ID as Logged in Plant --------------->"+stkTransfer.getISSUEOFFICEID());
					
					if(!StringUtil.isStringEmpty(stockIssue.getCustCode())){
						stkTransfer.setBANO(stockIssue.getCustCode());
					}
					logger.debug("Stk Issue Interface Cust Code --------------->"+stkTransfer.getBANO());
					
					Date today = Calendar.getInstance().getTime();        
					String dateStamp = df.format(today);
					stkTransfer.setTimestamp(dateStamp);
					elements.add(stkTransfer);
				}
			}
		}catch (CGSystemException | CGBusinessException e) {
			logger.error("STOCKISSUE :: Exception in SAPStockTransferScheduler",e);
		}
		if(!StringUtil.isEmptyList(stockTransfer.getStockTransfer())){
			String sapStatus = null;
			String exception = null;
			try{
				client.siCSDStockTransferOut(stockTransfer);
				sapStatus = "C"; 
			}catch(Exception e){
				sapStatus = "N"; 
				exception = e.getMessage();
				logger.error("STOCKISSUE :: SAPStockTransferScheduler::executeInternal::error::",e);
			}
			finally{
				try {
					stockSAPIntServiceForStockIssue.updateStockIssueStagingStatusFlag(sapStatus,exception,sapStockIssueDOList);
				} catch (CGSystemException e) {
					logger.error("STOCKISSUE :: SAPStockTransferScheduler::executeInternal::error::",e);
				}
			}
		}
			
		logger.debug("STOCKISSUE :: SAPStockTransferScheduler::executeInternal::after webservice call=======>");
		logger.debug("STOCKISSUE :: SAPStockTransferScheduler::executeInternal::end=======>");
	}
	
	/**
	 * @param client the client to set
	 */
	public void setClient(SICSDStockTransferOut client) {
		this.client = client;
	}

	/**
	 * @param stockSAPIntServiceForStockIssue the stockSAPIntServiceForStockIssue to set
	 */
	public void setStockSAPIntServiceForStockIssue(
			StockSAPIntegrationService stockSAPIntServiceForStockIssue) {
		this.stockSAPIntServiceForStockIssue = stockSAPIntServiceForStockIssue;
	}

}
