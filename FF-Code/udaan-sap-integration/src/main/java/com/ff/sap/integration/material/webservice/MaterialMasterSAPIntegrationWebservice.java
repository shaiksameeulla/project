package com.ff.sap.integration.material.webservice;

import java.util.List;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.ff.sap.integration.to.SAPMaterialTO;

@WebService
public interface MaterialMasterSAPIntegrationWebservice {

	public boolean saveMaterialDetails(@WebParam(name="materials")List<SAPMaterialTO> materials);
}
