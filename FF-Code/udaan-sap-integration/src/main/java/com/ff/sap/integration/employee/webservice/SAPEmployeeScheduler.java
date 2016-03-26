package com.ff.sap.integration.employee.webservice;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ff.sap.integration.to.SAPEmployeeTO;

public class SAPEmployeeScheduler extends QuartzJobBean {

	private EmployeeSAPIntegrationWebservice client;
	private static final Logger logger = Logger.getLogger(SAPEmployeeScheduler.class);
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("SAPEmployeeScheduler::executeInternal::start=====>");
		List<SAPEmployeeTO> sapEmpTOList = new ArrayList<SAPEmployeeTO>();
		SAPEmployeeTO testEmpData = new SAPEmployeeTO();
		testEmpData.setEmpCode("A001");
		testEmpData.setFirstName("Anwar");
		testEmpData.setLastName("Khan");
		//testEmpData.setOfficeId(1);
		sapEmpTOList.add(testEmpData);
		boolean serviceResponse = client.saveEmployeesDetails(sapEmpTOList);
		logger.debug("SAPEmployeeScheduler::executeInternal::serviceResponse=====>" + serviceResponse);
		logger.debug("SAPEmployeeScheduler::executeInternal::end=====>");
	}

	public void setClient(EmployeeSAPIntegrationWebservice client) {
		this.client = client;
	}
	
}
