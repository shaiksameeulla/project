
package com.firstflight.fi.csdtosap.outstandingreport;

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
 * 2013-09-19T12:35:34.724+05:30
 * Generated source version: 2.7.1
 * 
 */
public final class SICSDCustomerOutstandingReportOut_SICSDCustomerOutstandingReportOutPort_Client {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(SICSDCustomerOutstandingReportOut_SICSDCustomerOutstandingReportOutPort_Client.class);

    private static final QName SERVICE_NAME = new QName("http://FirstFlight.com/FI/CSDToSAP/CustomerOutstandingReport", "SI_CSD_CustomerOutstandingReport_OutService");

    private SICSDCustomerOutstandingReportOut_SICSDCustomerOutstandingReportOutPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = SICSDCustomerOutstandingReportOutService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
            	LOGGER.error("SICSDCustomerOutstandingReportOut_SICSDCustomerOutstandingReportOutPort_Client::main::error::",e);
            }
        }
      
        SICSDCustomerOutstandingReportOutService ss = new SICSDCustomerOutstandingReportOutService(wsdlURL, SERVICE_NAME);
        SICSDCustomerOutstandingReportOut port = ss.getSICSDCustomerOutstandingReportOutPort();  
        
        {
        	LOGGER.debug("Invoking siCSDCustomerOutstandingReportOut...");
        com.firstflight.fi.csdtosap.outstandingreport.DTCSDCustomerOutstandingReport _siCSDCustomerOutstandingReportOut_mtCSDCustomerOutstandingReport = new com.firstflight.fi.csdtosap.outstandingreport.DTCSDCustomerOutstandingReport();
        java.util.List<com.firstflight.fi.csdtosap.outstandingreport.DTCSDCustomerOutstandingReport.CustomerOutstandingReport> _siCSDCustomerOutstandingReportOut_mtCSDCustomerOutstandingReportCustomerOutstandingReport = new java.util.ArrayList<com.firstflight.fi.csdtosap.outstandingreport.DTCSDCustomerOutstandingReport.CustomerOutstandingReport>();
        com.firstflight.fi.csdtosap.outstandingreport.DTCSDCustomerOutstandingReport.CustomerOutstandingReport _siCSDCustomerOutstandingReportOut_mtCSDCustomerOutstandingReportCustomerOutstandingReportVal1 = new com.firstflight.fi.csdtosap.outstandingreport.DTCSDCustomerOutstandingReport.CustomerOutstandingReport();
        _siCSDCustomerOutstandingReportOut_mtCSDCustomerOutstandingReportCustomerOutstandingReportVal1.setBillsUpTo(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2013-09-19T12:35:34.818+05:30"));
        _siCSDCustomerOutstandingReportOut_mtCSDCustomerOutstandingReportCustomerOutstandingReportVal1.setPaymentsUpTo(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2013-09-19T12:35:34.818+05:30"));
        _siCSDCustomerOutstandingReportOut_mtCSDCustomerOutstandingReportCustomerOutstandingReportVal1.setCustomerNO("CustomerNO865104913");
        _siCSDCustomerOutstandingReportOut_mtCSDCustomerOutstandingReportCustomerOutstandingReportVal1.setProfitCentre("ProfitCentre-1252140768");
        _siCSDCustomerOutstandingReportOut_mtCSDCustomerOutstandingReportCustomerOutstandingReportVal1.setEmployeeCode("EmployeeCode1291866247");
        _siCSDCustomerOutstandingReportOut_mtCSDCustomerOutstandingReportCustomerOutstandingReportVal1.setEmail("Email536841121");
        _siCSDCustomerOutstandingReportOut_mtCSDCustomerOutstandingReportCustomerOutstandingReportVal1.setSAPTimestamp("SAPTimestamp-1844194431");
        _siCSDCustomerOutstandingReportOut_mtCSDCustomerOutstandingReportCustomerOutstandingReport.add(_siCSDCustomerOutstandingReportOut_mtCSDCustomerOutstandingReportCustomerOutstandingReportVal1);
        _siCSDCustomerOutstandingReportOut_mtCSDCustomerOutstandingReport.getCustomerOutstandingReport().addAll(_siCSDCustomerOutstandingReportOut_mtCSDCustomerOutstandingReportCustomerOutstandingReport);
        port.siCSDCustomerOutstandingReportOut(_siCSDCustomerOutstandingReportOut_mtCSDCustomerOutstandingReport);


        }

        System.exit(0);
    }

}
