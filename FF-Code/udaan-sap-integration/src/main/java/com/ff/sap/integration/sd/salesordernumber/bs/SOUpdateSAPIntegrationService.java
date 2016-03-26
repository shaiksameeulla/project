/**
 * 
 */
package com.ff.sap.integration.sd.salesordernumber.bs;

import java.util.List;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.sap.integration.to.SAPSalesOrderTO;

/**
 * @author cbhure
 *
 */
public interface SOUpdateSAPIntegrationService {
	
	public boolean updateSalesOrderNumber(List<SAPSalesOrderTO> salesOrder) throws CGBusinessException, CGSystemException;
	
	public List<CGBaseDO> saveOrUpdateBillNumberStatus(List<SAPSalesOrderTO> salesOrder) throws CGBusinessException, CGSystemException;
	
}
