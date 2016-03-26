/**
 * 
 */
package com.ff.sap.integration.sd.salesordernumber.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.ff.sap.integration.to.SAPSalesOrderTO;

/**
 * @author cbhure
 *
 */
@WebService
public interface SOUpdateSAPIntegrationWebservice {

	public boolean updateSalesOrderNumber(@WebParam(name="salesOrder")List<SAPSalesOrderTO> salesOrder);
	
}
