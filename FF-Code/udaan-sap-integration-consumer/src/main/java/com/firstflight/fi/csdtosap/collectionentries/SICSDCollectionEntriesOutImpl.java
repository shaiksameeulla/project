
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.firstflight.fi.csdtosap.collectionentries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class was generated by Apache CXF 2.7.1
 * 2013-05-09T19:23:37.746+05:30
 * Generated source version: 2.7.1
 * 
 */

@javax.jws.WebService(
                      serviceName = "SI_CSD_CollectionEntries_OutService",
                      portName = "SI_CSD_CollectionEntries_OutPort",
                      targetNamespace = "http://FirstFlight.com/FI/CSDToSAP/CollectionEntries",
                      wsdlLocation = "http://pidev.firstflight.net:50000/sap/xi/engine?type=entry&version=3.0&Sender.Service=CSD_DEV&Interface=http%3A%2F%2FFirstFlight.com%2FFI%2FCSDToSAP%2FCollectionEntries%5ESI_CSD_CollectionEntries_Out&QualityOfService=ExactlyOnce&sap-user=cg-pi&sap-password=gouri1",
                      endpointInterface = "com.firstflight.fi.csdtosap.collectionentries.SICSDCollectionEntriesOut")
                      
public class SICSDCollectionEntriesOutImpl implements SICSDCollectionEntriesOut {

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(SICSDCollectionEntriesOutImpl.class);

    /* (non-Javadoc)
     * @see com.firstflight.fi.csdtosap.collectionentries.SICSDCollectionEntriesOut#siCSDCollectionEntriesOut(com.firstflight.fi.csdtosap.collectionentries.DTCSDCollectionEntries  mtCSDCollectionEntries )*
     */
    public void siCSDCollectionEntriesOut(DTCSDCollectionEntries mtCSDCollectionEntries) { 
    	LOGGER.info("Executing operation siCSDCollectionEntriesOut");
        LOGGER.debug("mtCSDCollectionEntries=="+mtCSDCollectionEntries);
        try {
        } catch (java.lang.Exception ex) {
        	LOGGER.error("SICSDCollectionEntriesOutImpl::siCSDCollectionEntriesOut::error::",ex);
            throw new RuntimeException(ex);
        }
    }

}
