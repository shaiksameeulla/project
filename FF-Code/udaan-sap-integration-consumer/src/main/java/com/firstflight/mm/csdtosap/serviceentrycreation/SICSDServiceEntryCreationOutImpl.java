
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.firstflight.mm.csdtosap.serviceentrycreation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class was generated by Apache CXF 2.7.1
 * 2013-12-27T16:21:18.300+05:30
 * Generated source version: 2.7.1
 * 
 */

@javax.jws.WebService(
                      serviceName = "SI_CSD_ServiceEntryCreation_OutService",
                      portName = "SI_CSD_ServiceEntryCreation_OutPort",
                      targetNamespace = "http://FirstFlight.com/MM/CSDToSAP/ServiceEntryCreation",
                      wsdlLocation = "http://pidev.firstflight.net:50000/sap/xi/engine?type=entry&version=3.0&Sender.Service=CSD_DEV&Interface=http%3A%2F%2FFirstFlight.com%2FMM%2FCSDToSAP%2FServiceEntryCreation%5ESI_CSD_ServiceEntryCreation_Out&QualityOfService=ExactlyOnce&sap-user=cg-csd&sap-password=interface",
                      endpointInterface = "com.firstflight.mm.csdtosap.serviceentrycreation.SICSDServiceEntryCreationOut")
                      
public class SICSDServiceEntryCreationOutImpl implements SICSDServiceEntryCreationOut {

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(SICSDServiceEntryCreationOutImpl.class);

    /* (non-Javadoc)
     * @see com.firstflight.mm.csdtosap.serviceentrycreation.SICSDServiceEntryCreationOut#siCSDServiceEntryCreationOut(com.firstflight.mm.csdtosap.serviceentrycreation.DTCSDServiceEntryCreation  mtCSDServiceEntryCreation )*
     */
    public void siCSDServiceEntryCreationOut(DTCSDServiceEntryCreation mtCSDServiceEntryCreation) { 
    	LOGGER.info("Executing operation siCSDServiceEntryCreationOut");
    	  LOGGER.debug("mtCSDServiceEntryCreation::"+mtCSDServiceEntryCreation);
        try {
        } catch (java.lang.Exception ex) {
        	LOGGER.error("SICSDServiceEntryCreationOutImpl::siCSDServiceEntryCreationOut::error::",ex);
            throw new RuntimeException(ex);
        }
    }

}
