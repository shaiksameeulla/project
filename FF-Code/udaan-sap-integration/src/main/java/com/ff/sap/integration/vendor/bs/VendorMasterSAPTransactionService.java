/**
 * 
 */
package com.ff.sap.integration.vendor.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.business.CSDSAPLoadMovementVendorDO;
import com.ff.sap.integration.to.SAPVendorTO;

/**
 * @author cbhure
 *
 */
public interface VendorMasterSAPTransactionService {
	
	/*public boolean saveVendorDetails(List<SAPVendorTO> vendors, boolean iterateOneByOne) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;*/

	public List<CSDSAPLoadMovementVendorDO> getUpdateVendorDOFromTO(List<SAPVendorTO> vendors) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException;

	public boolean saveOrUpdateVendorDetails(List<CSDSAPLoadMovementVendorDO> updateVendorDOs) throws CGSystemException;

	public boolean saveDetailsOneByOne(List<CSDSAPLoadMovementVendorDO> updateVendorDOs);

	public List<CSDSAPLoadMovementVendorDO> getVendorDOFromTO(List<SAPVendorTO> vendors) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException;

	public boolean updateVendorOfficeMapped(List<SAPVendorTO> vendors) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException;

	public boolean updateVendorDetails(List<CSDSAPLoadMovementVendorDO> updateVendorDOs) throws CGSystemException;

}
