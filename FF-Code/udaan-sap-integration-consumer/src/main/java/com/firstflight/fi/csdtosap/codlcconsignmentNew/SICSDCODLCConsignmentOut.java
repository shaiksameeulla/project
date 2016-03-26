package com.firstflight.fi.csdtosap.codlcconsignmentNew;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.7.1
 * 2014-03-18T12:47:51.815+05:30
 * Generated source version: 2.7.1
 * 
 */
@WebService(targetNamespace = "http://FirstFlight.com/FI/CSDToSAP/CODLCConsignment", name = "SI_CSD_CODLCConsignment_Out")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface SICSDCODLCConsignmentOut {

    @Oneway
    @WebMethod(operationName = "SI_COD_CODLCConsignment_Out", action = "http://sap.com/xi/WebService/soap1.1")
    public void siCODCODLCConsignmentOut(
        @WebParam(partName = "MT_CSD_CODLCConsignment", name = "MT_CSD_CODLCConsignment", targetNamespace = "http://FirstFlight.com/FI/CSDToSAP/CODLCConsignment")
        DTCSDCODLCConsignment mtCSDCODLCConsignment
    );
}
