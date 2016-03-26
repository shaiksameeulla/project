package com.ff.sap.integration.pickup.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.pickup.PickUpCommissionCalculationDO;
import com.ff.domain.pickup.SAPPickUpComissionCalculationStagingDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.pickup.dao.PickUpCalculationCommissionDao;

public class PickUpCalculationCommissionServiceImpl implements PickUpCalculationCommissionService {

	
	Logger logger = Logger.getLogger(PickUpCalculationCommissionServiceImpl.class);
	
	private PickUpCalculationCommissionDao pickUpCalculationCommissionDAO;
	
	
	/**
	 * @param pickUpCalculationCommissionDAO the pickUpCalculationCommissionDAO to set
	 */
	public void setPickUpCalculationCommissionDAO(
			PickUpCalculationCommissionDao pickUpCalculationCommissionDAO) {
		this.pickUpCalculationCommissionDAO = pickUpCalculationCommissionDAO;
	}



	@Override
	public List<SAPPickUpComissionCalculationStagingDO> findPickUpCommissionCountForSAPIntegration() throws CGSystemException, CGBusinessException {
		logger.debug("PickUpCalculationCommissionServiceImpl :: findPickUpCommissionCountForSAPIntegration :: Start");
		ConfigurableParamsDO configParamDO = null;
		Long totalRecordsInTxnTable;
		Long initialCount;
		Long maxDataCountLimit = null;
		List<PickUpCommissionCalculationDO> pickUpcalculnCommsinDOList = null;
		List<SAPPickUpComissionCalculationStagingDO> sapPickUpCommissionCalStagingDOList = null;
		boolean isSaved = false;
		List<PickUpCommissionCalculationDO> pickUpCalculnCommisnNewList = null;
		PickUpCommissionCalculationDO pickUpCalnNewDO = null;
		boolean isUpdated = false;
		List<SAPPickUpComissionCalculationStagingDO> sapPickUpCommissionCalcultnDOList = null;
		try{
			
			//get max data constant from table - ff_d_config_param = 10
			configParamDO = pickUpCalculationCommissionDAO.getMaxDataCount(SAPIntegrationConstants.MAX_CHECK);
			if(!StringUtil.isNull(configParamDO)){
				String maxDataCount  = configParamDO.getParamValue();
				maxDataCountLimit = Long.valueOf(maxDataCount);
			}
			
			//Find out total record count from Transaction table = 150
			totalRecordsInTxnTable =  pickUpCalculationCommissionDAO.getTotalRecordCount("N");
			
			
			if(!StringUtil.isEmptyLong(totalRecordsInTxnTable)){
				//Add for loop - 
				for(initialCount = 1l; initialCount <= totalRecordsInTxnTable; initialCount=initialCount+maxDataCountLimit){
					
					pickUpcalculnCommsinDOList = new ArrayList<PickUpCommissionCalculationDO>();
					
					//1 Step - Fecthing data from CSD Table
//					
					pickUpcalculnCommsinDOList = pickUpCalculationCommissionDAO.getPickUpDtlsFromTransactionTable("N",maxDataCountLimit);
					
					
					//2 Step - Save CSD Table Data to Interface Staging Table
					sapPickUpCommissionCalStagingDOList = new ArrayList<SAPPickUpComissionCalculationStagingDO>();
						if(!StringUtil.isNull(pickUpcalculnCommsinDOList) &&
							!StringUtil.isEmptyColletion(pickUpcalculnCommsinDOList)){ 
							sapPickUpCommissionCalStagingDOList = convertPickUpCommissionCountCSDDOToStagingDO(pickUpcalculnCommsinDOList); 
							isSaved =  pickUpCalculationCommissionDAO.savePickUpCommissionStagingData(sapPickUpCommissionCalStagingDOList);
							 
							 
							// 2 A
								//Updating status and time stamp in CSD Table if Data successfully saved t o Staging Table 
								//if flag = true status = C and Time stamp = current time
								//if flag = false status = N and time Stamp = current Time
								
								/*if(isSaved){
									pickUpCalculnCommisnNewList = new ArrayList<PickUpCommissionCalculationDO>();
									for(PickUpCommissionCalculationDO pickUpCalculatnCommissnDO : pickUpcalculnCommsinDOList){
										pickUpCalnNewDO = new PickUpCommissionCalculationDO();
										pickUpCalnNewDO.setEmployee_id(pickUpCalculatnCommissnDO.getEmployee_id());
										logger.debug("Pick Up calculatn Commision employee ID ------>"+pickUpCalnNewDO.getEmployee_id());
										Date dateTime = Calendar.getInstance().getTime();
										pickUpCalnNewDO.setSapTimeStamp(dateTime);
										logger.debug("Date Stamp ------>"+pickUpCalnNewDO.getSapTimeStamp());
										pickUpCalnNewDO.setDtSapOutbound("C");
										logger.debug("SAP Status ------>"+pickUpCalnNewDO.getSapTimeStamp());
										pickUpCalculnCommisnNewList.add(pickUpCalnNewDO);
									}
									isUpdated = pickUpCalculationCommissionDAO.updateDateTimeAndStatusFlag(pickUpCalculnCommisnNewList);
								}else{
									pickUpCalculnCommisnNewList = new ArrayList<PickUpCommissionCalculationDO>();
									if(!StringUtil.isEmptyColletion(pickUpcalculnCommsinDOList)){
									for(PickUpCommissionCalculationDO pickUpCalculatnDO : pickUpcalculnCommsinDOList){
										pickUpCalnNewDO = new PickUpCommissionCalculationDO();
										pickUpCalnNewDO.setEmployee_id(pickUpCalculatnDO.getEmployee_id());
										logger.debug("Pick Up calculatn Commision employee ID ------>"+pickUpCalnNewDO.getEmployee_id());
										Date dateTime = Calendar.getInstance().getTime();
										pickUpCalnNewDO.setSapTimeStamp(dateTime);
										logger.debug("Date Stamp ------>"+pickUpCalnNewDO.getSapTimeStamp());
										pickUpCalnNewDO.setDtSapOutbound("N");
										logger.debug("SAP Status ------>"+pickUpCalnNewDO.getSapTimeStamp());
										pickUpCalculnCommisnNewList.add(pickUpCalnNewDO);
									}
									}
									pickUpCalculationCommissionDAO.updateDateTimeAndStatusFlag(pickUpCalculnCommisnNewList);
							}*/
							 
								//3 Step - Fecthing data from Staging Table
								sapPickUpCommissionCalcultnDOList = new ArrayList<SAPPickUpComissionCalculationStagingDO>();
								//if(isUpdated){
									sapPickUpCommissionCalcultnDOList = pickUpCalculationCommissionDAO.findPickUpCommissionDtlsFromStaging("N",maxDataCountLimit);
								//} 
					
						}
					}// end of for loop
				}// end of if 
			else{
				sapPickUpCommissionCalcultnDOList = new ArrayList<SAPPickUpComissionCalculationStagingDO>();
				sapPickUpCommissionCalcultnDOList = pickUpCalculationCommissionDAO.findPickUpCommissionDtlsFromStaging("N",maxDataCountLimit);
			}
			
		}catch(Exception e){
			logger.error("Exception IN :: PickUpCalculationCommissionServiceImpl :: findPickUpCommissionCountForSAPIntegration",e);
			throw new CGBusinessException(e);
		}
		logger.debug("PickUpCalculationCommissionServiceImpl :: findPickUpCommissionCountForSAPIntegration :: End");
		return sapPickUpCommissionCalcultnDOList;
	}
	
	
		private List<SAPPickUpComissionCalculationStagingDO> convertPickUpCommissionCountCSDDOToStagingDO(List<PickUpCommissionCalculationDO> pickUpcalculnCommsinDOList) {
			
			logger.debug("PickUpCalculationCommissionServiceImpl :: convertPickUpCommissionCountCSDDOToStagingDO :: Start");
			List<SAPPickUpComissionCalculationStagingDO> sapPickUpCommissionCalculationStagingDOList = new ArrayList<SAPPickUpComissionCalculationStagingDO>();
			SAPPickUpComissionCalculationStagingDO sapPickUpCommCalStagingDo = null;
			for(PickUpCommissionCalculationDO pickUpCommisnCalcultnDO: pickUpcalculnCommsinDOList){
				sapPickUpCommCalStagingDo = new SAPPickUpComissionCalculationStagingDO();
				
				if(!StringUtil.isNull(pickUpCommisnCalcultnDO.getEmployee_id())){
					EmployeeDO empDo = pickUpCalculationCommissionDAO.getEmployeeCode(pickUpCommisnCalcultnDO.getEmployee_id());
					
					sapPickUpCommCalStagingDo.setEmpCode(empDo.getEmpCode());
				}
				logger.debug("Employee Code--------------->"+sapPickUpCommCalStagingDo.getEmpCode());
				
				if(!StringUtil.isNull(pickUpCommisnCalcultnDO.getProduct_grup())){
					
					sapPickUpCommCalStagingDo.setProduct_grup(pickUpCommisnCalcultnDO.getProduct_grup());
				}
				logger.debug("Product group ID--------------->"+sapPickUpCommCalStagingDo.getProduct_grup());
				
				if(!StringUtil.isNull(pickUpCommisnCalcultnDO.getPickupCount())){
					sapPickUpCommCalStagingDo.setPickupCount(pickUpCommisnCalcultnDO.getPickupCount());
				}
				logger.debug("Pick up Count--------------->"+sapPickUpCommCalStagingDo.getPickupCount());
				
				if(!StringUtil.isNull(pickUpCommisnCalcultnDO.getNetValue())){
					sapPickUpCommCalStagingDo.setNetValue(pickUpCommisnCalcultnDO.getNetValue());
				}
				logger.debug("Net Value--------------->"+sapPickUpCommCalStagingDo.getNetValue());
				
				if(!StringUtil.isNull(pickUpCommisnCalcultnDO.getCalculatedFor())){
					sapPickUpCommCalStagingDo.setCalculatedFor(pickUpCommisnCalcultnDO.getCalculatedFor());
				}
				logger.debug("Calculated for-------------->"+sapPickUpCommCalStagingDo.getCalculatedFor());
				
				if(!StringUtil.isNull(pickUpCommisnCalcultnDO.getCreatedBy())){
					
					sapPickUpCommCalStagingDo.setCreatedBy(pickUpCommisnCalcultnDO.getCreatedBy());
				}
				logger.debug("Created by--------------->"+sapPickUpCommCalStagingDo.getCreatedBy());
				
				if(!StringUtil.isNull(pickUpCommisnCalcultnDO.getDtSapOutbound())){
					
					sapPickUpCommCalStagingDo.setDtSapOutbound(pickUpCommisnCalcultnDO.getDtSapOutbound());
				}
				logger.debug("Dt Sap Outbound--------------->"+sapPickUpCommCalStagingDo.getDtSapOutbound());
				
				if(!StringUtil.isNull(pickUpCommisnCalcultnDO.getSapTimeStamp())){
					sapPickUpCommCalStagingDo.setSapTimeStamp(pickUpCommisnCalcultnDO.getSapTimeStamp());
				}
				logger.debug("SAP Timestamp--------------->"+sapPickUpCommCalStagingDo.getSapTimeStamp());
				
				
				sapPickUpCommissionCalculationStagingDOList.add(sapPickUpCommCalStagingDo);
			}
			logger.debug("PickUpCalculationCommissionServiceImpl :: convertPickUpCommissionCountCSDDOToStagingDO :: End");
			
			return sapPickUpCommissionCalculationStagingDOList;
		}
		
		
		public void updatePickUpStagingFlag(String status,List<SAPPickUpComissionCalculationStagingDO> sapPickUpCommisnStagingDOList,String exception) {
			
			try {
				
				pickUpCalculationCommissionDAO.updatePickUpStagingFlag(status,sapPickUpCommisnStagingDOList,exception);
			} catch (Exception e) {
				logger.error("PickUpCalculationCommissionServiceImpl :: updatePickUpStagingFlag :: error",e);
			}
		}
		
		
}
