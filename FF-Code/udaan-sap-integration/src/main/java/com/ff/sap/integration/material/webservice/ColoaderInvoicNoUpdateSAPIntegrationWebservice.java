package com.ff.sap.integration.material.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.ff.sap.integration.to.SAPColoaderTO;

@WebService
public interface ColoaderInvoicNoUpdateSAPIntegrationWebservice {

	public boolean updateColoaderInvoiceNo(@WebParam(name="coloaderInvoiceNumber")List<SAPColoaderTO> coloaderInvoiceNumber);
}
