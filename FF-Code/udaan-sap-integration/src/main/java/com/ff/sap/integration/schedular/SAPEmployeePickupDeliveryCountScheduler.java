package com.ff.sap.integration.schedular;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SAPEmployeePickupDeliveryCountScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPEmployeePickupDeliveryCountScheduler.class);
	/*private SICSDEmpPickNDeliverCountOut client; 
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("SAPEmployeePickupDeliveryCountScheduler::executeInternal::start=======>");
		DTCSDEmpPickNDeliverCount empPickupDelCountTO = new DTCSDEmpPickNDeliverCount();
		empPickupDelCountTO.setEMPNUMBER("A0112");
		GregorianCalendar gcal = new GregorianCalendar();
		XMLGregorianCalendar xgcal;
		try {
			xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
			empPickupDelCountTO.setSTARTDATE("xgcal");
			empPickupDelCountTO.setENDDATE("xgcal");
			empPickupDelCountTO.setPICKUPCOUNT("C4");
			empPickupDelCountTO.setDELIVERCOUNT("C1");
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
		}
		logger.debug("SAPEmployeePickupDeliveryCountScheduler::executeInternal::before making webservice call=======>");
		client.siCSDEmpPickNDeliverCountIn(empPickupDelCountTO);
		logger.debug("SAPEmployeePickupDeliveryCountScheduler::executeInternal::after webservice call=======>");
		logger.debug("SAPEmployeePickupDeliveryCountScheduler::executeInternal::end=======>");
	}
	
	public void setClient(SICSDEmpPickNDeliverCountOut client) {
		this.client = client;
	}*/

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		
	}
	
}

