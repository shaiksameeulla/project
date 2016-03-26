package com.ff.sap.integration.material.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.ff.sap.integration.to.SAPColoaderTO;
import com.ff.sap.integration.to.SAPMaterialTO;

public interface MaterialMasterSAPIntegrationService {
	
	public boolean saveMaterialsDetails(List<SAPMaterialTO> materials,boolean iterateOneByOne) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;

	public boolean updateColoaderInvoiceNo(List<SAPColoaderTO> coloaderInvoiceNumber) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;

}
