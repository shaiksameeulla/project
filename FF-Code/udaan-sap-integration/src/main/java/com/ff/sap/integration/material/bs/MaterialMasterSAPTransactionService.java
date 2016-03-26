package com.ff.sap.integration.material.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.stockmanagement.masters.CSDSAPItemDO;
import com.ff.sap.integration.to.SAPMaterialTO;

public interface MaterialMasterSAPTransactionService {

	public List<CSDSAPItemDO> getUpdateMaterialDOFromTO(List<SAPMaterialTO> materials) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException;

	public boolean updateDetails(List<CSDSAPItemDO> updateMaterialDOs) throws CGSystemException;

	public boolean saveDetailsOneByOne(List<CSDSAPItemDO> updateMaterialDOs);

	public List<CSDSAPItemDO> getDOFromTO(List<SAPMaterialTO> materials) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException;

	public boolean saveDetailsForMaterial(List<CSDSAPItemDO> materialDOs) throws CGSystemException;

}
