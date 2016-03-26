/**
 * 
 */
package com.ff.sap.integration.sd.customer.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.ff.sap.integration.to.SAPCustomerTO;
import com.ff.sap.integration.to.SAPErrorTO;

/**
 * @author cbhure
 *
 */
@WebService
public interface CustomerMasterSAPIntegrationWebService {

	public void saveCustomerDetails(@WebParam(name="customer")List<SAPCustomerTO> customer);
	public void getPendingCustomersAndSaveCustDetails(List<SAPErrorTO> sapErroTOlist, String sapInboundStatus);
}
