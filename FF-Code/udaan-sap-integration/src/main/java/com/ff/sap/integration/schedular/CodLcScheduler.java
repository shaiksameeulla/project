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
import com.ff.domain.mec.SAPLiabilityEntriesDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.miscellaneous.service.MiscellaneousSAPIntegrationService;
import com.ff.sap.integration.to.SAPLiabilityEntriesTO;
import com.firstflight.fi.csdtosap.codlcconsignmentNew.DTCSDCODLCConsignment;
import com.firstflight.fi.csdtosap.codlcconsignmentNew.SICSDCODLCConsignmentOut;

/**
 * @author cbhure
 *
 */
public class CodLcScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPLiabilityEntriesScheduler.class);
	private SICSDCODLCConsignmentOut client; 
	public MiscellaneousSAPIntegrationService miscSAPService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("CODLC STAGING :: CodLcScheduler :: executeInternal :: Start");
		
		DTCSDCODLCConsignment codLDtcsdcodlcConsignment = null;
		DTCSDCODLCConsignment.CODLCConsignment clc = null;
		
		SAPLiabilityEntriesTO sapLiEntriesTO = new SAPLiabilityEntriesTO();
		sapLiEntriesTO.setSapStatus(SAPIntegrationConstants.SAP_STATUS);
		sapLiEntriesTO.setMaxCheck(SAPIntegrationConstants.MAX_CHECK);
		List<DTCSDCODLCConsignment.CODLCConsignment> elements =  null;
		List<SAPLiabilityEntriesDO> sapLiabilityEntriesList = null;
		try {
			codLDtcsdcodlcConsignment = new DTCSDCODLCConsignment();
			sapLiabilityEntriesList = miscSAPService.findLiabilityEntriesDtlsFromStaging(sapLiEntriesTO);
			if(!StringUtil.isEmptyColletion(sapLiabilityEntriesList)){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for(SAPLiabilityEntriesDO sapCODLCDO : sapLiabilityEntriesList){
					elements = codLDtcsdcodlcConsignment.getCODLCConsignment();
					clc = new DTCSDCODLCConsignment.CODLCConsignment();
					
					if(!StringUtil.isStringEmpty(sapCODLCDO.getCustNo())){
						clc.setCustomerCode(sapCODLCDO.getCustNo());
					}
					logger.debug("CODLC STAGING :: COD LC Cust No ---->"+clc.getCustomerCode());
					
					if(!StringUtil.isNull(sapCODLCDO.getBookingDate())){
						GregorianCalendar gregCalenderDdate = new GregorianCalendar();
						gregCalenderDdate.setTime(sapCODLCDO.getBookingDate());
						try {
							XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
							clc.setBookingDate(xmlGregCalDate);
						} catch (DatatypeConfigurationException e) {
							logger.debug("CODLC STAGING :: CodLcScheduler :: executeInternal :: error",e);
						}
					}
					logger.debug("CODLC STAGING :: COD LC Interface Booking Date ----->"+clc.getBookingDate());
					
					if(!StringUtil.isStringEmpty(sapCODLCDO.getBookingOfcRHOCode())){
						clc.setBookingOfficeRHOCode(sapCODLCDO.getBookingOfcRHOCode());
					}
					logger.debug("CODLC STAGING :: COD LC Booking Office RHO Code ---->"+clc.getBookingOfficeRHOCode());
					
					if(!StringUtil.isStringEmpty(sapCODLCDO.getConsgNo())){
						clc.setConsignmentNo(sapCODLCDO.getConsgNo());
					}
					logger.debug("CODLC STAGING :: COD LC Consg No ---->"+clc.getConsignmentNo());
					
					if(!StringUtil.isEmptyDouble(sapCODLCDO.getCodValue())){
						clc.setCODValue(String.valueOf(sapCODLCDO.getCodValue()));
					}
					logger.debug("CODLC STAGING :: COD Value ---->"+clc.getCODValue());
					
					if(!StringUtil.isEmptyDouble(sapCODLCDO.getLcValue())){
						clc.setLCValue(String.valueOf(sapCODLCDO.getLcValue()));
					}
					logger.debug("CODLC STAGING :: Lc Value ---->"+clc.getLCValue());
					
					if(!StringUtil.isEmptyDouble(sapCODLCDO.getBaAmount())){
						clc.setBAAmount(String.valueOf(sapCODLCDO.getBaAmount()));
					}
					logger.debug("CODLC STAGING :: BA Value ---->"+clc.getBAAmount());
					
					if(!StringUtil.isStringEmpty(sapCODLCDO.getDestRHO())){
						clc.setDestinationRHO(sapCODLCDO.getDestRHO());
					}
					logger.debug("CODLC STAGING :: Dest RHO ---->"+clc.getDestinationRHO());
					
					if(!StringUtil.isStringEmpty(sapCODLCDO.getStatusFlag())){
						clc.setStatusFlag(sapCODLCDO.getStatusFlag());
					}
					logger.debug("CODLC STAGING :: Status Flag ---->"+clc.getStatusFlag());
					
					if(!StringUtil.isNull(sapCODLCDO.getRtoDate())){
						GregorianCalendar gregCalenderDdate = new GregorianCalendar();
						gregCalenderDdate.setTime(sapCODLCDO.getRtoDate());
						try {
							XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
							clc.setRTODate(xmlGregCalDate);
						} catch (DatatypeConfigurationException e) {
							logger.debug("CODLC STAGING :: CodLcScheduler :: executeInternal :: error",e);
						}
					}
					logger.debug("CODLC STAGING :: COD LC RTO Date ----->"+clc.getRTODate());
					
					if(!StringUtil.isNull(sapCODLCDO.getRtoDrsDate())){
						GregorianCalendar gregCalenderDdate = new GregorianCalendar();
						gregCalenderDdate.setTime(sapCODLCDO.getRtoDrsDate());
						try {
							XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
							clc.setRTODRSUpdateDate(xmlGregCalDate);
						} catch (DatatypeConfigurationException e) {
							logger.debug("CODLC STAGING :: CodLcScheduler :: executeInternal :: error",e);
						}
					}
					logger.debug("CODLC STAGING :: COD LC Interface RTO DRS Date ----->"+clc.getRTODRSUpdateDate());
					
					if(!StringUtil.isNull(sapCODLCDO.getConsigneeDate())){
						GregorianCalendar gregCalenderDdate = new GregorianCalendar();
						gregCalenderDdate.setTime(sapCODLCDO.getConsigneeDate());
						try {
							XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
							clc.setConsigneeDate(xmlGregCalDate); 
							
						} catch (DatatypeConfigurationException e) {
							logger.debug("CODLC STAGING :: CodLcScheduler :: executeInternal :: error",e);
						}
					}
					logger.debug("CODLC STAGING :: COD LC Interface Consignee Date ----->"+clc.getConsigneeDate());
					Date today = Calendar.getInstance().getTime();        
					String dateStamp = df.format(today);
					clc.setTimestamp(dateStamp);
					elements.add(clc);
				}
			}
		} catch (CGSystemException | CGBusinessException e) { 
			logger.debug("CODLC STAGING :: CodLcScheduler :: executeInternal :: error",e);
		}
		if(!StringUtil.isEmptyList(codLDtcsdcodlcConsignment.getCODLCConsignment())){
			String sapStatus = null;
			String exception = null;
			try{
				client.siCODCODLCConsignmentOut(codLDtcsdcodlcConsignment);
				sapStatus = SAPIntegrationConstants.SAP_STATUS_C;
			}catch(Exception e){
				sapStatus = SAPIntegrationConstants.SAP_STATUS; 
				if(!StringUtil.isStringEmpty(e.getCause().getCause().getMessage())){
					exception = e.getCause().getCause().getMessage();
				}
				logger.debug("CODLC STAGING :: EXPENSE :: Error is ",e);
			}
			finally{
				try {
					miscSAPService.updateCODLCStagingStatusFlag(sapStatus,sapLiabilityEntriesList,exception);
				} catch (CGSystemException e) {
					logger.debug("CODLC STAGING :: CodLcScheduler :: executeInternal :: error",e);
				}
			}
		}
		logger.debug("CODLC STAGING :: CodLcScheduler :: executeInternal :: End");
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(SICSDCODLCConsignmentOut client) {
		this.client = client;
	}

	/**
	 * @param miscSAPService the miscSAPService to set
	 */
	public void setMiscSAPService(MiscellaneousSAPIntegrationService miscSAPService) {
		this.miscSAPService = miscSAPService;
	}
}

