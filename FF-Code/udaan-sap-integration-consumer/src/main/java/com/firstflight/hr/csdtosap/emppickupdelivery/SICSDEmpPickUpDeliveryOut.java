package com.firstflight.hr.csdtosap.emppickupdelivery;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.7.1
 * 2014-04-17T16:36:50.512+05:30
 * Generated source version: 2.7.1
 * 
 */
@WebService(targetNamespace = "http://FirstFlight.com/HR/CSDToSAP/EmpPickUpDelivery", name = "SI_CSD_EmpPickUpDelivery_Out")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface SICSDEmpPickUpDeliveryOut {

    @Oneway
    @WebMethod(operationName = "SI_CSD_EmpPickUpDelivery_Out", action = "http://sap.com/xi/WebService/soap1.1")
    public void siCSDEmpPickUpDeliveryOut(
        @WebParam(partName = "MT_CSD_EmpPickUpDelivery", name = "MT_CSD_EmpPickUpDelivery", targetNamespace = "http://FirstFlight.com/HR/CSDToSAP/EmpPickUpDelivery")
        DTCSDEmpPickUpDelivery mtCSDEmpPickUpDelivery
    );
}
