package com.ff.sap.integration.sd.customer.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.ratemanagement.masters.CSDSAPCustomerDO;
import com.ff.domain.ratemanagement.masters.SAPCustomerDO;
import com.ff.sap.integration.to.SAPCustomerTO;

public interface CustomerMasterSAPTransactionService {

	public CSDSAPCustomerDO getAndSetNonContractualCustomer(SAPCustomerDO sapCustDO) throws CGSystemException, CGBusinessException;

	public void saveOrupdateDetailsForCustNew(CSDSAPCustomerDO updateCustDO) throws CGSystemException;

	public boolean saveDetailsOneByOne(List<CSDSAPCustomerDO> updateCustDO);

	public List<CSDSAPCustomerDO> getCustDOFromTO(List<SAPCustomerTO> customer) 
			throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException;

	public boolean saveCustomerDetails(List<CSDSAPCustomerDO> custDO) throws CGSystemException;

	public void saveContractualCustomer(SAPCustomerDO sapCustDO) throws CGSystemException, CGBusinessException;
	
	public void saveNonContractualCustomer(SAPCustomerDO sapCustDO) throws CGSystemException, CGBusinessException;
	
	public boolean isCustomerContractual(SAPCustomerDO sapCustDO) throws CGSystemException, CGBusinessException;
	
}
