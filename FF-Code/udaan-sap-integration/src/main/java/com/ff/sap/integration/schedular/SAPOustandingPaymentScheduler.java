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
import com.ff.domain.mec.SAPOutstandingPaymentDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.miscellaneous.service.MiscellaneousSAPIntegrationService;
import com.ff.sap.integration.to.SAPOutstandingPaymentTO;
import com.firstflight.fi.csdtosap.outstandingreport.DTCSDCustomerOutstandingReport;
import com.firstflight.fi.csdtosap.outstandingreport.SICSDCustomerOutstandingReportOut;

/**
 * Customer Outstanding Payment Report 
 * EMP ID - 47892
 * @author CBHURE
 *
 */

public class SAPOustandingPaymentScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPLiabilityPaymentScheduler.class);
	private SICSDCustomerOutstandingReportOut client; 
	public MiscellaneousSAPIntegrationService miscSAPService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("OUTSTANDING REPORT :: SAPOustandingPaymentScheduler :: executeInternal :: start=======>");
		
		
		DTCSDCustomerOutstandingReport custOutstandingReport = null;
		DTCSDCustomerOutstandingReport.CustomerOutstandingReport cop = null;
		SAPOutstandingPaymentTO outPaymentTO = new SAPOutstandingPaymentTO();
		outPaymentTO.setSapStatus(SAPIntegrationConstants.SAP_STATUS);
		
		List<SAPOutstandingPaymentDO> sapOutPaymentDOs= null;
		List<DTCSDCustomerOutstandingReport.CustomerOutstandingReport> elements = null;
		
		try {
			custOutstandingReport = new DTCSDCustomerOutstandingReport();
			sapOutPaymentDOs = miscSAPService.findOutstandingPaymentDtls(outPaymentTO);
			if(!StringUtil.isEmptyColletion(sapOutPaymentDOs)){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for(SAPOutstandingPaymentDO outStandingPayDO : sapOutPaymentDOs){
					elements = custOutstandingReport.getCustomerOutstandingReport(); 
					cop = new DTCSDCustomerOutstandingReport.CustomerOutstandingReport();
					if(!StringUtil.isNull(outStandingPayDO.getBillUpto())){ 
						GregorianCalendar gregCalenderDdate = new GregorianCalendar();
						gregCalenderDdate.setTime(outStandingPayDO.getBillUpto());
						try {
							XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
							cop.setBillsUpTo(xmlGregCalDate);
						} catch (DatatypeConfigurationException e) {
							logger.error("SAPOustandingPaymentScheduler :: setBillsUptoDate :: Exception",e);
						}
					}
					logger.debug("Customer OutStanding Payment Interface Bills upto Date ---------->"+cop.getBillsUpTo());
					
					if(!StringUtil.isNull(outStandingPayDO.getPaymentUpto())){ 
						GregorianCalendar gregCalenderDdate = new GregorianCalendar();
						gregCalenderDdate.setTime(outStandingPayDO.getPaymentUpto());
						try {
							XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
							cop.setPaymentsUpTo(xmlGregCalDate);
						} catch (DatatypeConfigurationException e) {
							logger.error("SAPOustandingPaymentScheduler :: setPaymentUptoDate :: Exception",e);
						}
					}
					logger.debug("Customer OutStanding Payment Interface Payment upto Date ---------->"+cop.getPaymentsUpTo());
					
					
					if(!StringUtil.isStringEmpty(outStandingPayDO.getCcemail())){ 
						cop.setEmail(outStandingPayDO.getCcemail());
					}
					logger.debug("COP Email ---------->"+cop.getEmail());
					
					if(!StringUtil.isStringEmpty(outStandingPayDO.getCustomerCode())){ 
						cop.setCustomerNO(outStandingPayDO.getCustomerCode());
					}
					logger.debug("COP Cust No ---------->"+cop.getCustomerNO());
					
					if(!StringUtil.isStringEmpty(outStandingPayDO.getEmpCode())){ 
						cop.setEmployeeCode(outStandingPayDO.getEmpCode());
					}
					logger.debug("COP Emp code ---------->"+cop.getEmployeeCode());
					
					if(!StringUtil.isStringEmpty(outStandingPayDO.getOfficeCode())){ 
						cop.setProfitCentre(outStandingPayDO.getOfficeCode());
					}
					logger.debug("COP Ptofit Centre(Office Code) ---------->"+cop.getProfitCentre());
					
					Date today = Calendar.getInstance().getTime();        
					String dateStamp = df.format(today);
					cop.setSAPTimestamp(dateStamp);
					elements.add(cop);
				}
			}
		} catch (CGSystemException | CGBusinessException e) {
			logger.error("OUTSTANDING REPORT :: Exception IN :: SAPOustandingPaymentScheduler :: ",e);
		}
		if(!StringUtil.isEmptyList(custOutstandingReport.getCustomerOutstandingReport())){
			String sapStatus = null;
			String exception = null;
			try{
				client.siCSDCustomerOutstandingReportOut(custOutstandingReport);
				sapStatus = "C"; 
			}catch(Exception e){
				sapStatus = "N"; 
				exception = e.getMessage();
				logger.debug("OUTSTANDING REPORT :: Error is ",e);
			}
			finally{
				try {
					miscSAPService.updateOutStandingPaymentStagingStatusFlag(sapStatus,sapOutPaymentDOs,exception);
				} catch (CGSystemException e) {
					logger.error("OUTSTANDING REPORT :: Exception IN :: updateOutStandingPaymentStagingStatusFlag :: ",e);
				}
			}
		}
		logger.debug("OUTSTANDING REPORT :: SAPOustandingPaymentScheduler :: executeInternal :: after webservice call=======>");
		logger.debug("OUTSTANDING REPORT :: SAPOustandingPaymentScheduler :: executeInternal :: end=======>");
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(SICSDCustomerOutstandingReportOut client) {
		this.client = client;
	}

	/**
	 * @param miscSAPService the miscSAPService to set
	 */
	public void setMiscSAPService(MiscellaneousSAPIntegrationService miscSAPService) {
		this.miscSAPService = miscSAPService;
	}

	
	
}

