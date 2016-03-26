/**
 * 
 */
package com.ff.sap.integration.vendor.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.ff.sap.integration.to.SAPVendorTO;

/**
 * @author cbhure
 *
 */
public interface VendorMasterSAPIntegrationService {
	
	public boolean saveVendorDetails(List<SAPVendorTO> vendors,boolean iterateOneByOne) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;

}
