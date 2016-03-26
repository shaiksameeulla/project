/**
 * 
 */
package com.ff.sap.integration.plantmaster.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.sap.integration.to.SAPOfficeTO;

/**
 * @author cbhure
 *
 */
public interface PlantMasterSAPIntegrationService {

	public boolean saveOfficeDetails(List<SAPOfficeTO> officeTO,boolean iterateOneByOne) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException;
	
}
