/**
 * 
 */
package com.ff.sap.integration.vendor.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.ff.sap.integration.to.SAPVendorTO;

/**
 * @author cbhure
 *
 */
@WebService
public interface VendorMasterSAPIntegrationWebservice {

	public boolean saveVendorDetails(@WebParam(name="vendors")List<SAPVendorTO> vendors);
}
