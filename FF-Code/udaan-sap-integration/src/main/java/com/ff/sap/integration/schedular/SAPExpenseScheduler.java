package com.ff.sap.integration.schedular;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.ff.domain.mec.expense.ExpenseDO;
import com.ff.domain.mec.expense.SAPExpenseDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.miscellaneous.service.MiscellaneousSAPIntegrationService;
import com.ff.sap.integration.to.SAPExpenseTO;
import com.firstflight.fi.csdtosap.expenseentries.DTCSDExpenseEntries;
import com.firstflight.fi.csdtosap.expenseentries.SICSDExpenseEntriesOut;

/**
 * @author CBHURE
 *
 */
public class SAPExpenseScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPExpenseScheduler.class);
	private SICSDExpenseEntriesOut client; 
	public MiscellaneousSAPIntegrationService miscSAPService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("EXPENSE :: SAPExpenseScheduler::executeInternal::start=======>");
		
		//Fetching All Reporting RHO Code from office
		List<ExpenseDO> expenseOfcRHOCodeList = new ArrayList<ExpenseDO>();
		try {
			expenseOfcRHOCodeList = miscSAPService.getAllExpenseOfficeRHO();
		} catch (CGSystemException | CGBusinessException ce) {
			logger.error("EXPENSE :: SAPExpenseScheduler::executeInternal::error",ce);
		}
		logger.debug("Reporting RHO List Size--------------->"+expenseOfcRHOCodeList.size());
		DTCSDExpenseEntries expenseEntries = null;
		DTCSDExpenseEntries.ExpenseEntries ee = null;
		
		SAPExpenseTO expenseTo = new SAPExpenseTO();
		expenseTo.setSapStatus(SAPIntegrationConstants.SAP_STATUS);
		expenseTo.setStatus(SAPIntegrationConstants.EXP_STATUS);
		expenseTo.setMaxCheck(SAPIntegrationConstants.MAX_CHECK);
		List<DTCSDExpenseEntries.ExpenseEntries> elements =  null;
		
		List<SAPExpenseDO> sapExpenseDOList = null;
		
		//For First RHO Code getting all expense records 
		
		for(ExpenseDO expReportingRHO : expenseOfcRHOCodeList){
				if(!StringUtil.isNull(expReportingRHO)){
					
					if(!StringUtil.isEmptyInteger(expReportingRHO.getExpenseOfficeRho())){
						expenseTo.setReportingRHOID(expReportingRHO.getExpenseOfficeRho());
					}
					logger.debug("Reporting RHO ID -------------->"+expenseTo.getReportingRHOID());
					
					try {
						expenseEntries = new DTCSDExpenseEntries();
						sapExpenseDOList = miscSAPService.findExpenseDtlsForSAPIntegration(expenseTo);
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						
						for(SAPExpenseDO sapExpenseDO : sapExpenseDOList){
							if(!StringUtil.isNull(sapExpenseDO)){
								elements =  expenseEntries.getExpenseEntries();
								ee = new DTCSDExpenseEntries.ExpenseEntries();
								if(!StringUtil.isEmptyDouble(sapExpenseDO.getTotalExpense())){
									ee.setAmount(String.valueOf(sapExpenseDO.getTotalExpense()));
								}
								if(!StringUtil.isStringEmpty(sapExpenseDO.getBankGLCode())){
									ee.setBankGLCode(sapExpenseDO.getBankGLCode());
								}
								if(!StringUtil.isStringEmpty(sapExpenseDO.getBankName())){
									ee.setChequeBank(sapExpenseDO.getBankName());
								}
								if(!StringUtil.isStringEmpty(sapExpenseDO.getOfficeCode())){
									ee.setBranchCode(sapExpenseDO.getOfficeCode());
								}
								
								if(!StringUtil.isNull(sapExpenseDO.getChequeDate())){
									GregorianCalendar gregCalenderDdate = new GregorianCalendar();
									gregCalenderDdate.setTime(sapExpenseDO.getChequeDate());
									try {
										XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
										ee.setChequeDate(xmlGregCalDate);
									} catch (DatatypeConfigurationException e) {
										logger.error("EXPENSE :: SAPExpenseScheduler::executeInternal::error::",e);
									}
								}
								
								if(!StringUtil.isStringEmpty(sapExpenseDO.getChequeNo())){
									ee.setChequeNo(sapExpenseDO.getChequeNo());
								}
								
								if(!StringUtil.isNull(sapExpenseDO.getPostingDate())){
									GregorianCalendar gregCalenderDdate = new GregorianCalendar();
									gregCalenderDdate.setTime(sapExpenseDO.getPostingDate());
									try {
										XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
										ee.setCreationDate(xmlGregCalDate);
									} catch (DatatypeConfigurationException e) {
										logger.error("EXPENSE :: SAPExpenseScheduler::executeInternal::error::",e);
									}
								}
								logger.debug("Creation Date "+ee.getCreationDate());
								
								if(!StringUtil.isStringEmpty(sapExpenseDO.getExpenseGLCode())){
									ee.setExpenseGLCode(sapExpenseDO.getExpenseGLCode());
								}
								if(!StringUtil.isStringEmpty(sapExpenseDO.getPaymentCode())){
									ee.setModeOfPayment(sapExpenseDO.getPaymentCode());
								}
								if(!StringUtil.isStringEmpty(sapExpenseDO.getTxNumber())){
									ee.setTransactionID(sapExpenseDO.getTxNumber());
								}
								if(!StringUtil.isStringEmpty(sapExpenseDO.getReportingRhoCode())){
									ee.setReportingRHOCode(sapExpenseDO.getReportingRhoCode());
								}
								if(!StringUtil.isEmptyDouble(sapExpenseDO.getServiceChanrge())){
									ee.setServiceCharge(String.valueOf(sapExpenseDO.getServiceChanrge()));
								}
								if(!StringUtil.isEmptyDouble(sapExpenseDO.getServiceTaxBasic())){
									ee.setServiceTaxBasic(String.valueOf(sapExpenseDO.getServiceTaxBasic()));
								}
								if(!StringUtil.isEmptyDouble(sapExpenseDO.getEdOnServiceTax())){
									ee.setEDOnServiceTax(String.valueOf(sapExpenseDO.getEdOnServiceTax()));
								}
								if(!StringUtil.isEmptyDouble(sapExpenseDO.getHedOnServiceTax())){
									ee.setHEDOnServiceTax(String.valueOf(sapExpenseDO.getHedOnServiceTax()));
								}
								if(!StringUtil.isStringEmpty(sapExpenseDO.getEmpCode())){
									ee.setEmployeeCode(sapExpenseDO.getEmpCode());
								}
								if(!StringUtil.isStringEmpty(sapExpenseDO.getConsgNo())){
									ee.setConsignmentNo(sapExpenseDO.getConsgNo());
								}
								if(!StringUtil.isStringEmpty(sapExpenseDO.getDestinationRHO())){
									ee.setDestinationRHO(sapExpenseDO.getDestinationRHO());
								}
								if(!StringUtil.isStringEmpty(sapExpenseDO.getRemark())){
									ee.setRemarks(sapExpenseDO.getRemark());
								}
								Date today = Calendar.getInstance().getTime();        
								String dateStamp = df.format(today);
								ee.setTimestamp(dateStamp);
								
								// Added glIndicator
								ee.setGLIndicator(sapExpenseDO.getGlIndicator());
								
								elements.add(ee);
								logger.debug("Elements Size -------------->"+elements.size());
							}
						}
					} catch (CGSystemException | CGBusinessException e) {
						logger.error("EXPENSE :: SAPExpenseScheduler::executeInternal::error::",e);
					}
				
					if(!StringUtil.isEmptyList(expenseEntries.getExpenseEntries())){
						String sapStatus = null;
						String exception = null;
						try{
							client.siCSDExpenseEntriesOut(expenseEntries);
							sapStatus = "C"; 
						}catch(Exception e){
							sapStatus = "N"; 
							if(!StringUtil.isStringEmpty(e.getCause().getCause().getMessage())){
								exception = e.getCause().getCause().getMessage();
							}
							logger.debug("EXPENSE :: Error is "+e);
						}
						finally{
							try {
								miscSAPService.updateExpenseStagingStatusFlag(sapStatus,sapExpenseDOList,exception);
							} catch (CGSystemException e) {
								logger.error("EXPENSE :: SAPExpenseScheduler::executeInternal::error::",e);
							}
						}
					}
					logger.debug("EXPENSE :: REPORTING RHO Ends------> "+expenseTo.getReportingRHOID());
			}//end of if
				logger.debug("EXPENSE :: FOr loop Count");
		}//end of for loop
		logger.debug("EXPENSE :: SAPExpenseScheduler::executeInternal::after webservice call=======>");
		logger.debug("EXPENSE :: SAPExpenseScheduler::executeInternal::end=======>");
	}

	/**
	 * @param miscSAPService the miscSAPService to set
	 */
	public void setMiscSAPService(MiscellaneousSAPIntegrationService miscSAPService) {
		this.miscSAPService = miscSAPService;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(SICSDExpenseEntriesOut client) {
		this.client = client;
	}
	
}
