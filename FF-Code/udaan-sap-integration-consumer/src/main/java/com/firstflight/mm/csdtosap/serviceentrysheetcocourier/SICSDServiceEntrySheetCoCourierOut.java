package com.firstflight.mm.csdtosap.serviceentrysheetcocourier;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.7.1
 * 2014-01-15T19:21:24.053+05:30
 * Generated source version: 2.7.1
 * 
 */
@WebService(targetNamespace = "http://FirstFlight.com/MM/CSDToSAP/ServiceEntrySheetCoCourier", name = "SI_CSD_ServiceEntrySheetCoCourier_Out")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface SICSDServiceEntrySheetCoCourierOut {

    @Oneway
    @WebMethod(operationName = "SI_CSD_ServiceEntrySheetCoCourier_Out", action = "http://sap.com/xi/WebService/soap1.1")
    public void siCSDServiceEntrySheetCoCourierOut(
        @WebParam(partName = "MT_CSD_ServiceEntrySheetCoCourier", name = "MT_CSD_ServiceEntrySheetCoCourier", targetNamespace = "http://FirstFlight.com/MM/CSDToSAP/ServiceEntrySheetCoCourier")
        DTCSDServiceEntrySheetCoCourier mtCSDServiceEntrySheetCoCourier
    );
}
