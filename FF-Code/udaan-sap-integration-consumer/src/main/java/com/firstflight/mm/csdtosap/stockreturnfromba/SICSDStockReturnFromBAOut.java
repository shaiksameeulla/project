package com.firstflight.mm.csdtosap.stockreturnfromba;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.7.1
 * 2013-09-03T12:29:18.066+05:30
 * Generated source version: 2.7.1
 * 
 */
@WebService(targetNamespace = "http://FirstFlight.com/MM/CSDToSAP/StockReturnFromBA", name = "SI_CSD_StockReturnFromBA_Out")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface SICSDStockReturnFromBAOut {

    @Oneway
    @WebMethod(operationName = "SI_CSD_StockReturnFromBA_Out", action = "http://sap.com/xi/WebService/soap1.1")
    public void siCSDStockReturnFromBAOut(
        @WebParam(partName = "MT_CSD_StockReturnFromBA", name = "MT_CSD_StockReturnFromBA", targetNamespace = "http://FirstFlight.com/MM/CSDToSAP/StockReturnFromBA")
        DTCSDStockReturnFromBA mtCSDStockReturnFromBA
    );
}
