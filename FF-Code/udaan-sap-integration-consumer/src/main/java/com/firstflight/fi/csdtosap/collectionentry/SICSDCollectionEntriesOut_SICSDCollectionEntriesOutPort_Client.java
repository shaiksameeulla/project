
package com.firstflight.fi.csdtosap.collectionentry;

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
 * 2013-10-29T11:48:03.021+05:30
 * Generated source version: 2.7.1
 * 
 */
public final class SICSDCollectionEntriesOut_SICSDCollectionEntriesOutPort_Client {
	
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(SICSDCollectionEntriesOut_SICSDCollectionEntriesOutPort_Client.class);

    private static final QName SERVICE_NAME = new QName("http://FirstFlight.com/FI/CSDToSAP/CollectionEntries", "SI_CSD_CollectionEntries_OutService");

    private SICSDCollectionEntriesOut_SICSDCollectionEntriesOutPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = SICSDCollectionEntriesOutService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
            	LOGGER.error("SICSDCollectionEntriesOut_SICSDCollectionEntriesOutPort_Client::main::error::",e);

            }
        }
      
        SICSDCollectionEntriesOutService ss = new SICSDCollectionEntriesOutService(wsdlURL, SERVICE_NAME);
        SICSDCollectionEntriesOut port = ss.getSICSDCollectionEntriesOutPort();  
        
        {
        	LOGGER.debug("Invoking siCSDCollectionEntriesOut...");
        com.firstflight.fi.csdtosap.collectionentry.DTCSDCollectionEntries _siCSDCollectionEntriesOut_mtCSDCollectionEntries = new com.firstflight.fi.csdtosap.collectionentry.DTCSDCollectionEntries();
        java.util.List<com.firstflight.fi.csdtosap.collectionentry.DTCSDCollectionEntries.CollectionEntries> _siCSDCollectionEntriesOut_mtCSDCollectionEntriesCollectionEntries = new java.util.ArrayList<com.firstflight.fi.csdtosap.collectionentry.DTCSDCollectionEntries.CollectionEntries>();
        com.firstflight.fi.csdtosap.collectionentry.DTCSDCollectionEntries.CollectionEntries _siCSDCollectionEntriesOut_mtCSDCollectionEntriesCollectionEntriesVal1 = new com.firstflight.fi.csdtosap.collectionentry.DTCSDCollectionEntries.CollectionEntries();
        _siCSDCollectionEntriesOut_mtCSDCollectionEntriesCollectionEntriesVal1.setCustomerCode("CustomerCode-1626318131");
        _siCSDCollectionEntriesOut_mtCSDCollectionEntriesCollectionEntriesVal1.setTransactionNo("TransactionNo228180150");
        _siCSDCollectionEntriesOut_mtCSDCollectionEntriesCollectionEntriesVal1.setCreatedDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2013-10-29T11:48:03.121+05:30"));
        _siCSDCollectionEntriesOut_mtCSDCollectionEntriesCollectionEntriesVal1.setModeOfPayment("ModeOfPayment-797453537");
        _siCSDCollectionEntriesOut_mtCSDCollectionEntriesCollectionEntriesVal1.setBankGLCode("BankGLCode-2057904854");
        _siCSDCollectionEntriesOut_mtCSDCollectionEntriesCollectionEntriesVal1.setChequeDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2013-10-29T11:48:03.132+05:30"));
        _siCSDCollectionEntriesOut_mtCSDCollectionEntriesCollectionEntriesVal1.setChequeNo("ChequeNo719600912");
        _siCSDCollectionEntriesOut_mtCSDCollectionEntriesCollectionEntriesVal1.setChequeBank("ChequeBank-257208844");
        _siCSDCollectionEntriesOut_mtCSDCollectionEntriesCollectionEntriesVal1.setBranch("Branch132244305");
        _siCSDCollectionEntriesOut_mtCSDCollectionEntriesCollectionEntriesVal1.setSAPStatus("SAPStatus-93429062");
        _siCSDCollectionEntriesOut_mtCSDCollectionEntriesCollectionEntriesVal1.setTimestamp("Timestamp453923962");
        java.util.List<com.firstflight.fi.csdtosap.collectionentry.DTCSDCollectionEntries.CollectionEntries.ItemDetails> _siCSDCollectionEntriesOut_mtCSDCollectionEntriesCollectionEntriesVal1ItemDetails = new java.util.ArrayList<com.firstflight.fi.csdtosap.collectionentry.DTCSDCollectionEntries.CollectionEntries.ItemDetails>();
        _siCSDCollectionEntriesOut_mtCSDCollectionEntriesCollectionEntriesVal1.getItemDetails().addAll(_siCSDCollectionEntriesOut_mtCSDCollectionEntriesCollectionEntriesVal1ItemDetails);
        _siCSDCollectionEntriesOut_mtCSDCollectionEntriesCollectionEntries.add(_siCSDCollectionEntriesOut_mtCSDCollectionEntriesCollectionEntriesVal1);
        _siCSDCollectionEntriesOut_mtCSDCollectionEntries.getCollectionEntries().addAll(_siCSDCollectionEntriesOut_mtCSDCollectionEntriesCollectionEntries);
        port.siCSDCollectionEntriesOut(_siCSDCollectionEntriesOut_mtCSDCollectionEntries);


        }

        System.exit(0);
    }

}
