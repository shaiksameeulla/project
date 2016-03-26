
package com.firstflight.hr.csdtosap.emppickupdelivery;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class was generated by Apache CXF 2.7.1
 * 2014-04-17T16:36:50.340+05:30
 * Generated source version: 2.7.1
 * 
 */
public final class SICSDEmpPickUpDeliveryOut_SICSDEmpPickUpDeliveryOutPort_Client {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(SICSDEmpPickUpDeliveryOut_SICSDEmpPickUpDeliveryOutPort_Client.class);

    private static final QName SERVICE_NAME = new QName("http://FirstFlight.com/HR/CSDToSAP/EmpPickUpDelivery", "SI_CSD_EmpPickUpDelivery_OutService");

    private SICSDEmpPickUpDeliveryOut_SICSDEmpPickUpDeliveryOutPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = SICSDEmpPickUpDeliveryOutService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
            	LOGGER.error("SICSDEmpPickUpDeliveryOut_SICSDEmpPickUpDeliveryOutPort_Client::main::error::",e);
            }
        }
      
        SICSDEmpPickUpDeliveryOutService ss = new SICSDEmpPickUpDeliveryOutService(wsdlURL, SERVICE_NAME);
        SICSDEmpPickUpDeliveryOut port = ss.getSICSDEmpPickUpDeliveryOutPort();  
        
        {
        	LOGGER.debug("Invoking siCSDEmpPickUpDeliveryOut...");
        com.firstflight.hr.csdtosap.emppickupdelivery.DTCSDEmpPickUpDelivery _siCSDEmpPickUpDeliveryOut_mtCSDEmpPickUpDelivery = new com.firstflight.hr.csdtosap.emppickupdelivery.DTCSDEmpPickUpDelivery();
        java.util.List<com.firstflight.hr.csdtosap.emppickupdelivery.DTCSDEmpPickUpDelivery.EmpPickUpDelivery> _siCSDEmpPickUpDeliveryOut_mtCSDEmpPickUpDeliveryEmpPickUpDelivery = new java.util.ArrayList<com.firstflight.hr.csdtosap.emppickupdelivery.DTCSDEmpPickUpDelivery.EmpPickUpDelivery>();
        com.firstflight.hr.csdtosap.emppickupdelivery.DTCSDEmpPickUpDelivery.EmpPickUpDelivery _siCSDEmpPickUpDeliveryOut_mtCSDEmpPickUpDeliveryEmpPickUpDeliveryVal1 = new com.firstflight.hr.csdtosap.emppickupdelivery.DTCSDEmpPickUpDelivery.EmpPickUpDelivery();
        _siCSDEmpPickUpDeliveryOut_mtCSDEmpPickUpDeliveryEmpPickUpDeliveryVal1.setEMPLOYEECODE("EMPLOYEECODE-143491914");
        _siCSDEmpPickUpDeliveryOut_mtCSDEmpPickUpDeliveryEmpPickUpDeliveryVal1.setPRODUCTGROUP("PRODUCTGROUP1480771937");
        _siCSDEmpPickUpDeliveryOut_mtCSDEmpPickUpDeliveryEmpPickUpDeliveryVal1.setCALCULATEDFORPERIOD(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-04-17T16:36:50.465+05:30"));
        _siCSDEmpPickUpDeliveryOut_mtCSDEmpPickUpDeliveryEmpPickUpDeliveryVal1.setDELIVEREDCOUNT("DELIVEREDCOUNT-1955927045");
        _siCSDEmpPickUpDeliveryOut_mtCSDEmpPickUpDeliveryEmpPickUpDeliveryVal1.setDELIVEREDDAY1("DELIVEREDDAY1-482166014");
        _siCSDEmpPickUpDeliveryOut_mtCSDEmpPickUpDeliveryEmpPickUpDeliveryVal1.setDELIVEREDDAY2("DELIVEREDDAY2-722910094");
        _siCSDEmpPickUpDeliveryOut_mtCSDEmpPickUpDeliveryEmpPickUpDeliveryVal1.setDELIVEREDDAY3("DELIVEREDDAY3-642546583");
        _siCSDEmpPickUpDeliveryOut_mtCSDEmpPickUpDeliveryEmpPickUpDeliveryVal1.setDELIVEREDDAY4BEYOND("DELIVEREDDAY4BEYOND-278031256");
        _siCSDEmpPickUpDeliveryOut_mtCSDEmpPickUpDeliveryEmpPickUpDeliveryVal1.setTRANSCREATEDDATE(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-04-17T16:36:50.465+05:30"));
        _siCSDEmpPickUpDeliveryOut_mtCSDEmpPickUpDeliveryEmpPickUpDelivery.add(_siCSDEmpPickUpDeliveryOut_mtCSDEmpPickUpDeliveryEmpPickUpDeliveryVal1);
        _siCSDEmpPickUpDeliveryOut_mtCSDEmpPickUpDelivery.getEmpPickUpDelivery().addAll(_siCSDEmpPickUpDeliveryOut_mtCSDEmpPickUpDeliveryEmpPickUpDelivery);
        port.siCSDEmpPickUpDeliveryOut(_siCSDEmpPickUpDeliveryOut_mtCSDEmpPickUpDelivery);


        }

        System.exit(0);
    }

}
