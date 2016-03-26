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
import com.ff.domain.mec.SAPLiabilityPaymentDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.miscellaneous.service.MiscellaneousSAPIntegrationService;
import com.ff.sap.integration.to.SAPLiabilityPaymentTO;
import com.firstflight.fi.csdtosap.codlcliabilityRegion.DTCSDCODLCLiability;
import com.firstflight.fi.csdtosap.codlcliabilityRegion.SICSDCODLCLiabilityOut;

/**
 * @author CBHURE
 *
 */

public class SAPLiabilityPaymentScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPLiabilityPaymentScheduler.class);
	private SICSDCODLCLiabilityOut client; 
	public MiscellaneousSAPIntegrationService miscSAPService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("LIABILITY PAYMENT :: SAPLiabilityPaymentScheduler::executeInternal::start=======>");
		
		DTCSDCODLCLiability codLCLiaibility = null;
		DTCSDCODLCLiability.CODLCLiability cl = null;
		SAPLiabilityPaymentTO liabilityPaytTO = new SAPLiabilityPaymentTO();
		liabilityPaytTO.setSapStatus(SAPIntegrationConstants.SAP_STATUS);
		liabilityPaytTO.setMaxCheck(SAPIntegrationConstants.MAX_CHECK);
		List<SAPLiabilityPaymentDO> sapLiabilityPaymentDOList = null;
		List<DTCSDCODLCLiability.CODLCLiability> elements = null;
		try {
			codLCLiaibility = new DTCSDCODLCLiability();
			sapLiabilityPaymentDOList = miscSAPService.findLiabilityPaymentDtlsForSAPIntegration(liabilityPaytTO);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(SAPLiabilityPaymentDO liabilityPayDO : sapLiabilityPaymentDOList){
				elements =  codLCLiaibility.getCODLCLiability();
				cl = new DTCSDCODLCLiability.CODLCLiability();
					
				if(!StringUtil.isNull(liabilityPayDO.getCustCode())){ 
					cl.setCustomerCode(liabilityPayDO.getCustCode());
				}
				logger.debug("Liability Interface Customer Code ---------->"+cl.getCustomerCode());
				
				if(!StringUtil.isStringEmpty(liabilityPayDO.getTxNumber())){ 
					cl.setTransactionNo(liabilityPayDO.getTxNumber());
				}
				logger.debug("Liability Interface TX Number ---------->"+cl.getTransactionNo());
				
				if(!StringUtil.isNull(liabilityPayDO.getCreationDate())){ 
					GregorianCalendar gregCalenderDdate = new GregorianCalendar();
					gregCalenderDdate.setTime(liabilityPayDO.getCreationDate());
					try {
						XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
						cl.setCreatedDate(xmlGregCalDate);
					} catch (DatatypeConfigurationException e) {
						logger.error("LIABILITY PAYMENT :: SAPLiabilityPaymentScheduler::executeInternal::error::",e);
					}
				}
				logger.debug("Liability Interface Created Date ---------->"+cl.getCreatedDate());
				
				if(!StringUtil.isNull(liabilityPayDO.getChequeDate())){
					GregorianCalendar gregCalenderDdate = new GregorianCalendar();
					gregCalenderDdate.setTime(liabilityPayDO.getChequeDate());
					try {
						XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
						cl.setChequeDate(xmlGregCalDate);
					} catch (DatatypeConfigurationException e) {
						logger.error("LIABILITY PAYMENT :: SAPLiabilityPaymentScheduler::executeInternal::error::",e);
					}
				}
				logger.debug("Liability Interface Chqque Date ---------->"+cl.getChequeDate());
				
				if(!StringUtil.isStringEmpty(liabilityPayDO.getChequeNo())){
					cl.setChequeNo(liabilityPayDO.getChequeNo());
				}
				logger.debug("Liability Interface Cheque No ---------->"+cl.getChequeNo());
				
				if(!StringUtil.isNull(liabilityPayDO.getChequeBankName())){
					cl.setChequeBankName(liabilityPayDO.getChequeBankName());
				}
				logger.debug("Liability Interface CHQ Bank Name---------->"+cl.getChequeBankName());
				
				if(!StringUtil.isNull(liabilityPayDO.getBankGLCode())){
					cl.setBankGLCode(liabilityPayDO.getBankGLCode());
				}
				logger.debug("Liability Interface Bank GL Code Code ---------->"+cl.getBankGLCode());
				
				if(!StringUtil.isEmptyDouble(liabilityPayDO.getAmount())){ 
					cl.setAmount(String.valueOf(liabilityPayDO.getAmount()));
				}
				logger.debug("Liability Interface Amount---------->"+cl.getAmount());
				
				if(!StringUtil.isNull(liabilityPayDO.getRegionCode())){ 
					cl.setRegion(liabilityPayDO.getRegionCode());
				}
				logger.debug("Liability Interface Region ---------->"+cl.getRegion());
				
				Date today = Calendar.getInstance().getTime();        
				String dateStamp = df.format(today);
				cl.setTimestamp(dateStamp);
				elements.add(cl);
			}
		} catch (CGSystemException | CGBusinessException e) {
			logger.error("LIABILITY PAYMENT :: SAPLiabilityPaymentScheduler :: ",e);
		}
		if(!StringUtil.isEmptyList(codLCLiaibility.getCODLCLiability())){
			String sapStatus = null;
			String exception = null;
			try{
				client.siCSDCODLCLiabilityOut(codLCLiaibility);
				sapStatus = "C"; 
			}catch(Exception e){
				sapStatus = "N"; 
				if(!StringUtil.isStringEmpty(e.getCause().getCause().getMessage())){
					exception = e.getCause().getCause().getMessage();
				}
				logger.debug("LIABILITY PAYMENT :: Error is "+e);
			}
			finally{
				try {
					miscSAPService.updateLiabilityPaymentStagingStatusFlag(sapStatus,sapLiabilityPaymentDOList,exception);
				} catch (CGSystemException e) {
					logger.error("LIABILITY PAYMENT :: SAPLiabilityPaymentScheduler::executeInternal::error::",e);
				}
			}
		}
		logger.debug("LIABILITY PAYMENT :: SAPLiabilityPaymentScheduler::executeInternal::after webservice call=======>");
		logger.debug("LIABILITY PAYMENT :: SAPLiabilityPaymentScheduler::executeInternal::end=======>");
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
	public void setClient(SICSDCODLCLiabilityOut client) {
		this.client = client;
	}
}

