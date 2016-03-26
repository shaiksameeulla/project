/**
 * 
 */
package com.ff.sap.integration.sd.customer.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.ratemanagement.masters.SAPCustomerDO;
import com.ff.sap.integration.to.SAPErrorTO;

/**
 * @author cbhure
 * 
 */
public interface CustomerMasterSAPIntegrationService {

	public void saveCustomerDetails(SAPCustomerDO sapCustDO) throws CGBusinessException,CGSystemException;

	/**
	 * To save SAP CUSTOMER DO List to staging table
	 * 
	 * @param sapCustomerDOs
	 * @throws CGBusinessException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws CGSystemException
	 */
	public void saveSapCustomerDOs(List<SAPCustomerDO> sapCustomerDOs)
			throws CGSystemException;

	/**
	 * To save SAP CUSTOMER DO to staging table
	 * 
	 * @param sapCustomerDOs
	 * @throws CGBusinessException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws CGSystemException
	 */
	public void saveSapCustomerDO(SAPCustomerDO sapCustomerDO)
			throws CGBusinessException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, CGSystemException;

	/**
	 * To get pending SAP Customer list whose DT_SAP_INBOUND flag is N
	 * 
	 * @return pendingSapCustDOs
	 * @throws CGBusinessException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws CGSystemException
	 */
	public List<SAPCustomerDO> getPendingSapCustomerList(String sapInboundStatus)
			throws CGBusinessException,CGSystemException;

	/**
	 * To search already saved SAP customer details
	 * 
	 * @param sapCustomerDOs
	 * @throws CGBusinessException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws CGSystemException
	 */
	public void searchAlreadySavedSAPCustDtls(List<SAPCustomerDO> sapCustomerDOs)
			throws CGBusinessException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, CGSystemException;

	/**
	 * To send SAP Integration Error Mail
	 * 
	 * @param sapErroTOlist
	 * @param templateMane
	 * @param subName
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public void sendSapIntgErrorMail(List<SAPErrorTO> sapErroTOlist,
			String templateMane, String subName) throws CGBusinessException,
			CGSystemException;

}
