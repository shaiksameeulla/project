package com.ff.sap.integration.pickup.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.pickup.PickUpCommissionCalculationDO;
import com.ff.domain.pickup.SAPPickUpComissionCalculationStagingDO;
import com.ff.domain.stockmanagement.operations.requisition.SAPStockRequisitionDO;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionItemDtlsDO;
import com.ff.sap.integration.to.SAPStockRequisitionTO;

public interface PickUpCalculationCommissionDao {

	Long getTotalRecordCount(String sapStatus) throws CGSystemException;

	ConfigurableParamsDO getMaxDataCount(String paramName)throws CGSystemException;
	
	List<PickUpCommissionCalculationDO> getPickUpDtlsFromTransactionTable(String sapStatus, Long maxDataCountLimit) throws CGSystemException;

	boolean savePickUpCommissionStagingData(List<SAPPickUpComissionCalculationStagingDO> sapPickUpCommissionCalculatnStagingDOList)throws CGSystemException;
	
	public List<SAPPickUpComissionCalculationStagingDO> findPickUpCommissionDtlsFromStaging(String sapStatus, Long maxDataCountLimit) throws CGSystemException ;

//	boolean updateDateTimeAndStatusFlag(List<PickUpCommissionCalculationDO> pickUpCommissionCalculatnDoList)throws CGSystemException;

	public EmployeeDO getEmployeeCode(Integer employeeId);
	

	public void updatePickUpStagingFlag(String status,List<SAPPickUpComissionCalculationStagingDO> sapPickUpCommisnStagingDOList,String exception) throws CGSystemException;

}
