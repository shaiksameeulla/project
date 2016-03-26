
package com.firstflight.fi.csdtosap.codlcconsignmentNew;

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
 * 2014-03-18T12:47:51.632+05:30
 * Generated source version: 2.7.1
 * 
 */
public final class SICSDCODLCConsignmentOut_SICSDCODLCConsignmentOutPort_Client {

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(SICSDCODLCConsignmentOut_SICSDCODLCConsignmentOutPort_Client.class);
   
	private static final QName SERVICE_NAME = new QName("http://FirstFlight.com/FI/CSDToSAP/CODLCConsignment", "SI_CSD_CODLCConsignment_OutService");

    private SICSDCODLCConsignmentOut_SICSDCODLCConsignmentOutPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = SICSDCODLCConsignmentOutService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
            	LOGGER.error("SICSDCODLCConsignmentOut_SICSDCODLCConsignmentOutPort_Client::main::error::",e);
            }
        }
      
        SICSDCODLCConsignmentOutService ss = new SICSDCODLCConsignmentOutService(wsdlURL, SERVICE_NAME);
        SICSDCODLCConsignmentOut port = ss.getSICSDCODLCConsignmentOutPort();  
        
        {
        	LOGGER.debug("Invoking siCODCODLCConsignmentOut...");
        com.firstflight.fi.csdtosap.codlcconsignmentNew.DTCSDCODLCConsignment _siCODCODLCConsignmentOut_mtCSDCODLCConsignment = new com.firstflight.fi.csdtosap.codlcconsignmentNew.DTCSDCODLCConsignment();
        java.util.List<com.firstflight.fi.csdtosap.codlcconsignmentNew.DTCSDCODLCConsignment.CODLCConsignment> _siCODCODLCConsignmentOut_mtCSDCODLCConsignmentCODLCConsignment = new java.util.ArrayList<com.firstflight.fi.csdtosap.codlcconsignmentNew.DTCSDCODLCConsignment.CODLCConsignment>();
        com.firstflight.fi.csdtosap.codlcconsignmentNew.DTCSDCODLCConsignment.CODLCConsignment _siCODCODLCConsignmentOut_mtCSDCODLCConsignmentCODLCConsignmentVal1 = new com.firstflight.fi.csdtosap.codlcconsignmentNew.DTCSDCODLCConsignment.CODLCConsignment();
        _siCODCODLCConsignmentOut_mtCSDCODLCConsignmentCODLCConsignmentVal1.setTimestamp("Timestamp518898489");
        _siCODCODLCConsignmentOut_mtCSDCODLCConsignmentCODLCConsignmentVal1.setSAPStatus("SAPStatus-534436554");
        _siCODCODLCConsignmentOut_mtCSDCODLCConsignmentCODLCConsignmentVal1.setCustomerCode("CustomerCode455207718");
        _siCODCODLCConsignmentOut_mtCSDCODLCConsignmentCODLCConsignmentVal1.setBookingDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-03-18T12:47:51.760+05:30"));
        _siCODCODLCConsignmentOut_mtCSDCODLCConsignmentCODLCConsignmentVal1.setBookingOfficeRHOCode("BookingOfficeRHOCode685239300");
        _siCODCODLCConsignmentOut_mtCSDCODLCConsignmentCODLCConsignmentVal1.setConsignmentNo("ConsignmentNo1596721478");
        _siCODCODLCConsignmentOut_mtCSDCODLCConsignmentCODLCConsignmentVal1.setCODValue("CODValue-367573910");
        _siCODCODLCConsignmentOut_mtCSDCODLCConsignmentCODLCConsignmentVal1.setBAAmount("BAAmount-1345561029");
        _siCODCODLCConsignmentOut_mtCSDCODLCConsignmentCODLCConsignmentVal1.setLCValue("LCValue-532203010");
        _siCODCODLCConsignmentOut_mtCSDCODLCConsignmentCODLCConsignmentVal1.setDestinationRHO("DestinationRHO1183163027");
        _siCODCODLCConsignmentOut_mtCSDCODLCConsignmentCODLCConsignmentVal1.setStatusFlag("StatusFlag-1191728668");
        _siCODCODLCConsignmentOut_mtCSDCODLCConsignmentCODLCConsignmentVal1.setRTODRSUpdateDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-03-18T12:47:51.789+05:30"));
        _siCODCODLCConsignmentOut_mtCSDCODLCConsignmentCODLCConsignmentVal1.setRTODate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-03-18T12:47:51.790+05:30"));
        _siCODCODLCConsignmentOut_mtCSDCODLCConsignmentCODLCConsignmentVal1.setConsigneeDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-03-18T12:47:51.790+05:30"));
        _siCODCODLCConsignmentOut_mtCSDCODLCConsignmentCODLCConsignment.add(_siCODCODLCConsignmentOut_mtCSDCODLCConsignmentCODLCConsignmentVal1);
        _siCODCODLCConsignmentOut_mtCSDCODLCConsignment.getCODLCConsignment().addAll(_siCODCODLCConsignmentOut_mtCSDCODLCConsignmentCODLCConsignment);
        port.siCODCODLCConsignmentOut(_siCODCODLCConsignmentOut_mtCSDCODLCConsignment);


        }

        System.exit(0);
    }

}
