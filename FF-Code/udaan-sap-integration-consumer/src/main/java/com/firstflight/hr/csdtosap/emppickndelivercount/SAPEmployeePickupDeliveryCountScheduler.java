package com.firstflight.hr.csdtosap.emppickndelivercount;

import java.math.BigInteger;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SAPEmployeePickupDeliveryCountScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPEmployeePickupDeliveryCountScheduler.class);
	private SICSDEmpPickNDeliverCountOut client; 
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("SAPEmployeePickupDeliveryCountScheduler::executeInternal::start=======>");
		DTCSDEmpPickNDeliverCount empPickupDelCountTO = new DTCSDEmpPickNDeliverCount();
		empPickupDelCountTO.setEMPNUMBER("A0112");
		/*empPickupDelCountTO.setSTARTDATE("2013-11-10");
		empPickupDelCountTO.setENDDATE("2013-11-15");
		empPickupDelCountTO.setPICKUPCOUNT("15");
		empPickupDelCountTO.setDELIVERCOUNT("12");*/
		 GregorianCalendar gcal = new GregorianCalendar();
	      XMLGregorianCalendar xgcal;
		try {
			xgcal = DatatypeFactory.newInstance()
			        .newXMLGregorianCalendar(gcal);
			empPickupDelCountTO.setTODATE(xgcal);
			empPickupDelCountTO.setFROMDATE(xgcal);
			empPickupDelCountTO.setCOUNT(new BigInteger("5"));
			empPickupDelCountTO.setRECORDTYPE("XYZ");
		} catch (DatatypeConfigurationException e) {
			logger.error("SAPEmployeePickupDeliveryCountScheduler::executeInternal::error::",e);
		}
		
		
		logger.debug("SAPEmployeePickupDeliveryCountScheduler::executeInternal::before making webservice call=======>");
		client.siCSDEmpPickNDeliverCountIn(empPickupDelCountTO);
		logger.debug("SAPEmployeePickupDeliveryCountScheduler::executeInternal::after webservice call=======>");
		logger.debug("SAPEmployeePickupDeliveryCountScheduler::executeInternal::end=======>");
	}
	
	public void setClient(SICSDEmpPickNDeliverCountOut client) {
		this.client = client;
	}
	
}
