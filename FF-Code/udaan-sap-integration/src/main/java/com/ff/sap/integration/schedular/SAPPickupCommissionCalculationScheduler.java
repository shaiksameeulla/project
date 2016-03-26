package com.ff.sap.integration.schedular;

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
import com.ff.domain.pickup.SAPPickUpComissionCalculationStagingDO;
import com.ff.sap.integration.pickup.service.PickUpCalculationCommissionService;
import com.firstflight.hr.csdtosap.EmpPickUpCommission.DTCSDEmpPickUpCommission;
import com.firstflight.hr.csdtosap.EmpPickUpCommission.SICSDEmpPickUpCommissionOut;

public class SAPPickupCommissionCalculationScheduler extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPPickupCommissionCalculationScheduler.class);
	public PickUpCalculationCommissionService pickUpCommissionService;
	public SICSDEmpPickUpCommissionOut client;
	
	
	/**
	 * @param client the client to set
	 */
	public void setClient(SICSDEmpPickUpCommissionOut client) {
		this.client = client;
	}

	

	/**
	 * @param pickUpCommissionService the pickUpCommissionService to set
	 */
	public void setPickUpCommissionService(
			PickUpCalculationCommissionService pickUpCommissionService) {
		this.pickUpCommissionService = pickUpCommissionService;
	}



	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		logger.debug("SAPPickupCommissionCalculationScheduler :: executeInternal :: Start");
		DTCSDEmpPickUpCommission dtCsdEmpPickUpComsn = null; 
		DTCSDEmpPickUpCommission.PickUpCommission pc = null;
		List<DTCSDEmpPickUpCommission.PickUpCommission> elements = null;
		List<SAPPickUpComissionCalculationStagingDO>  pickUpComissionCalculationStagingDOList = null;
		try{
			
			pickUpComissionCalculationStagingDOList = pickUpCommissionService.findPickUpCommissionCountForSAPIntegration();	
			
			dtCsdEmpPickUpComsn = new DTCSDEmpPickUpCommission();
			for(SAPPickUpComissionCalculationStagingDO sapPickUpComissionCalculationStagingDO : pickUpComissionCalculationStagingDOList){
				elements = dtCsdEmpPickUpComsn.getPickUpCommission();
				pc = new DTCSDEmpPickUpCommission.PickUpCommission(); 
				
				if(!StringUtil.isStringEmpty(sapPickUpComissionCalculationStagingDO.getEmpCode())){
					pc.setEmployeeCode(sapPickUpComissionCalculationStagingDO.getEmpCode());
				}
				logger.debug("employee code--------------->"+pc.getEmployeeCode());
				
				if(!StringUtil.isNull(sapPickUpComissionCalculationStagingDO.getCalculatedFor())){
					GregorianCalendar gregCalenderDdate = new GregorianCalendar();
					gregCalenderDdate.setTime(sapPickUpComissionCalculationStagingDO.getCalculatedFor());
					try {
						XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
						pc.setCalculatedFor(xmlGregCalDate);
					} catch (DatatypeConfigurationException e) {
						logger.debug("SAPPickupCommissionCalculationScheduler :: executeInternal :: error::",e);
					}
				}
				logger.debug("Calculated for--------------->"+pc.getCalculatedFor());
				
				if(!StringUtil.isNull(sapPickUpComissionCalculationStagingDO.getNetValue())){
					pc.setNetValue(String.valueOf(sapPickUpComissionCalculationStagingDO.getNetValue()));
				}
				logger.debug("Net value --------------->"+pc.getNetValue());
				
				if(!StringUtil.isNull(sapPickUpComissionCalculationStagingDO.getPickupCount())){
					pc.setPickUpCount(String.valueOf(sapPickUpComissionCalculationStagingDO.getPickupCount()));
				}
				logger.debug("Pick up count--------------->"+pc.getPickUpCount());
				
				if(!StringUtil.isNull(sapPickUpComissionCalculationStagingDO.getProduct_grup())){
					pc.setPrdGrp(sapPickUpComissionCalculationStagingDO.getProduct_grup());
				}
				logger.debug("Product grup--------------->"+pc.getPrdGrp());
				
				elements.add(pc);
				
			}
			
		}catch (CGSystemException | CGBusinessException e) {
			logger.debug("SAPPickupCommissionCalculationScheduler :: executeInternal :: error::",e);
		}
		
		if(!StringUtil.isEmptyList(dtCsdEmpPickUpComsn.getPickUpCommission())){
			String sapStatus = null;
			String exception = null;
			try{
				client.siCSDEmpPickUpCommissionOut(dtCsdEmpPickUpComsn);
				sapStatus = "C"; 
			}catch(Exception e){
				sapStatus = "N"; 
				logger.debug("SAPPickupCommissionCalculationScheduler :: executeInternal :: error::",e);
				exception = e.getMessage();
				logger.debug("Error is "+e);
			}
			finally{
				try {
					pickUpCommissionService.updatePickUpStagingFlag(sapStatus,pickUpComissionCalculationStagingDOList,exception);
				} catch (Exception e) {
					logger.debug("SAPPickupCommissionCalculationScheduler :: executeInternal :: error::",e);
				}
			}
		}
		logger.debug("SAPStockCancellationScheduler::executeInternal::after webservice call=======>");
		logger.debug("SAPStockCancellationScheduler::executeInternal::end=======>");
	}

}
