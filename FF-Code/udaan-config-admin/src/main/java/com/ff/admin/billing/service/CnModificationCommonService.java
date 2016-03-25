package com.ff.admin.billing.service;

import java.lang.reflect.InvocationTargetException;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.consignment.ConsignmentTO;
import com.ff.to.billing.CustModificationAliasTO;
import com.ff.to.billing.CustModificationTO;
import com.ff.to.rate.ProductToBeValidatedInputTO;

public interface CnModificationCommonService {
	
	public ProductToBeValidatedInputTO setProductContractInputTO(CustModificationTO custModificationTO) 
			throws CGBusinessException, CGSystemException;
	
	public CustModificationAliasTO validateConsignment2Modify(String consgNo) throws CGSystemException, CGBusinessException;
	
	public boolean saveOrUpdateConsignmentModification(ConsignmentTO consgTO, CustModificationTO custModificationTO) throws CGBusinessException,CGSystemException, 
			IllegalAccessException, InvocationTargetException, NoSuchMethodException;

}
