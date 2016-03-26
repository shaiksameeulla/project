package com.firstflight.fi.csdtosap.expentries;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.7.1
 * 2013-06-13T16:22:24.882+05:30
 * Generated source version: 2.7.1
 * 
 */
@WebService(targetNamespace = "http://FirstFlight.com/FI/CSDToSAP/ExpenseEntries", name = "SI_CSD_ExpenseEntries_Out")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface SICSDExpenseEntriesOut {

    @Oneway
    @WebMethod(operationName = "SI_CSD_ExpenseEntries_Out", action = "http://sap.com/xi/WebService/soap1.1")
    public void siCSDExpenseEntriesOut(
        @WebParam(partName = "MT_CSD_ExpenseEntries", name = "MT_CSD_ExpenseEntries", targetNamespace = "http://FirstFlight.com/FI/CSDToSAP/ExpenseEntries")
        DTCSDExpenseEntries mtCSDExpenseEntries
    );
}
