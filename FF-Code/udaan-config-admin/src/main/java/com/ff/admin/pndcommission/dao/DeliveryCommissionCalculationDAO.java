package com.ff.admin.pndcommission.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.pndcommission.DeliveryCommissionCalculationDO;
import com.ff.domain.pndcommission.SAPDeliveryCommissionCalcDO;

/**
 * @author hkansagr
 * 
 */
public interface DeliveryCommissionCalculationDAO {

	/**
	 * To generate or execute delivery commission calculation.
	 * 
	 * @throws CGSystemException
	 */
	void generateDlvCommission() throws CGSystemException;

	/**
	 * To get delivery commission calculation details
	 * 
	 * @return dlvCommCalcDOs
	 * @throws CGSystemException
	 */
	List<DeliveryCommissionCalculationDO> getDlvCommCalcDtls()
			throws CGSystemException;

	/**
	 * To save SAP delivery commission calculation details
	 * 
	 * @param sapDlvCommCalcDO
	 * @throws CGSystemException
	 */
	void saveSAPDlvCommCalcDtls(SAPDeliveryCommissionCalcDO sapDlvCommCalcDO)
			throws CGSystemException;

}
