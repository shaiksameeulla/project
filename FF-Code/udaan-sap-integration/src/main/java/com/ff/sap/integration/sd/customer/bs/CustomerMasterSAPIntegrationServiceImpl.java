/**
 * 
 */
package com.ff.sap.integration.sd.customer.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.domain.ratemanagement.masters.SAPCustomerDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.dao.SAPIntegrationDAO;
import com.ff.sap.integration.to.SAPErrorTO;

/**
 * @author cbhure
 * 
 */
public class CustomerMasterSAPIntegrationServiceImpl implements
		CustomerMasterSAPIntegrationService {

	private final static Logger LOGGER = Logger
			.getLogger(CustomerMasterSAPIntegrationServiceImpl.class);

	private SAPIntegrationDAO integrationDAO;
	private CustomerMasterSAPTransactionService customerSAPTransactionService;

	/**
	 * @param integrationDAO
	 *            the integrationDAO to set
	 */
	public void setIntegrationDAO(SAPIntegrationDAO integrationDAO) {
		this.integrationDAO = integrationDAO;
	}

	/**
	 * @param customerSAPTransactionService
	 *            the customerSAPTransactionService to set
	 */
	public void setCustomerSAPTransactionService(
			CustomerMasterSAPTransactionService customerSAPTransactionService) {
		this.customerSAPTransactionService = customerSAPTransactionService;
	}

	@Override
	public void saveCustomerDetails(SAPCustomerDO sapCustDO) throws CGBusinessException,CGSystemException {
		LOGGER.debug("CustomerMasterSAPIntegrationServiceImpl :: saveCustomerDetails :: START ");
		try {
			if(customerSAPTransactionService.isCustomerContractual(sapCustDO)){
				// update contract related tables
				customerSAPTransactionService.saveContractualCustomer(sapCustDO);
			}
			else{
				// update the other respective tables
				customerSAPTransactionService.saveNonContractualCustomer(sapCustDO);
			}
			saveSapCustomerDO(setMandatoryFields(sapCustDO));
		}
		catch(CGBusinessException e){
			LOGGER.error("Exception In :: CustomerMasterSAPIntegrationServiceImpl :: saveCustomerDetails",e);
			throw e;
		}
		catch(CGSystemException e){
			LOGGER.error("Exception In :: CustomerMasterSAPIntegrationServiceImpl :: saveCustomerDetails",e);
			throw e;
		}
		catch(Exception e) {
			LOGGER.error("Exception In :: CustomerMasterSAPIntegrationServiceImpl :: saveCustomerDetails",e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("CustomerMasterSAPIntegrationServiceImpl :: saveCustomerDetails :: END");
	}

	private SAPCustomerDO setMandatoryFields(SAPCustomerDO sapCustDO){
		LOGGER.debug("CustomerMasterSAPIntegrationServiceImpl :: setMandatoryFields() :: START");
		sapCustDO.setIsError(CommonConstants.NO);
		sapCustDO.setException(null);
		sapCustDO.setSapInbound(SAPIntegrationConstants.SAP_STATUS_C);
		sapCustDO.setUpdateDate(DateUtil.getCurrentDate());
		sapCustDO.setCsdTimeStmap(DateUtil.getCurrentDate());
		LOGGER.debug("CustomerMasterSAPIntegrationServiceImpl :: setMandatoryFields() :: END");
		return sapCustDO;
	}
	
	@Override
	public void saveSapCustomerDOs(List<SAPCustomerDO> sapCustomerDOs)
			throws CGSystemException {
		LOGGER.debug("CustomerMasterSAPIntegrationServiceImpl :: saveSapCustomerDOs() :: START");
		integrationDAO.saveSapCustomerDOs(sapCustomerDOs);
		LOGGER.debug("CustomerMasterSAPIntegrationServiceImpl :: saveSapCustomerDOs() :: END");
	}

	@Override
	public void saveSapCustomerDO(SAPCustomerDO sapCustomerDO)
			throws CGBusinessException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, CGSystemException {
		LOGGER.debug("CustomerMasterSAPIntegrationServiceImpl :: saveSapCustomerDO() :: START");
		integrationDAO.saveSapCustomerDO(sapCustomerDO);
		LOGGER.debug("CustomerMasterSAPIntegrationServiceImpl :: saveSapCustomerDO() :: END");
	}

	@Override
	public List<SAPCustomerDO> getPendingSapCustomerList(String sapInboundStatus)
			throws CGBusinessException,CGSystemException {
		return integrationDAO.getPendingSapCustomerList(sapInboundStatus);
	}

	@Override
	public void searchAlreadySavedSAPCustDtls(List<SAPCustomerDO> sapCustomerDOs)
			throws CGBusinessException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, CGSystemException {
		integrationDAO.searchAlreadySavedSAPCustDtls(sapCustomerDOs);
	}

	@Override
	public void sendSapIntgErrorMail(List<SAPErrorTO> sapErroTOlist,
			String templateName, String subName) throws CGBusinessException,
			CGSystemException {
		integrationDAO.sendSapIntgErrorMail(sapErroTOlist, templateName,
				subName);
	}

}
