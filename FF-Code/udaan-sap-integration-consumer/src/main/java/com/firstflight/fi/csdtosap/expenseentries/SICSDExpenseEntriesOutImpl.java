
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.firstflight.fi.csdtosap.expenseentries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class was generated by Apache CXF 2.7.1
 * 2014-08-06T15:54:45.793+05:30
 * Generated source version: 2.7.1
 * 
 */

@javax.jws.WebService(
                      serviceName = "SI_CSD_ExpenseEntries_OutService",
                      portName = "SI_CSD_ExpenseEntries_OutPort",
                      targetNamespace = "http://FirstFlight.com/FI/CSDToSAP/ExpenseEntries",
                      wsdlLocation = "http://pidev.firstflight.net:50000/sap/xi/engine?type=entry&version=3.0&Sender.Service=CSD_DEV&Interface=http%3A%2F%2FFirstFlight.com%2FFI%2FCSDToSAP%2FExpenseEntries%5ESI_CSD_ExpenseEntries_Out&QualityOfService=ExactlyOnce&sap-user=cg-csd&sap-password=interface",
                      endpointInterface = "com.firstflight.fi.csdtosap.expenseentries.SICSDExpenseEntriesOut")
                      
public class SICSDExpenseEntriesOutImpl implements SICSDExpenseEntriesOut {

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(SICSDExpenseEntriesOutImpl.class);

    /* (non-Javadoc)
     * @see com.firstflight.fi.csdtosap.expenseentries.SICSDExpenseEntriesOut#siCSDExpenseEntriesOut(com.firstflight.fi.csdtosap.expenseentries.DTCSDExpenseEntries  mtCSDExpenseEntries )*
     */
    public void siCSDExpenseEntriesOut(DTCSDExpenseEntries mtCSDExpenseEntries) { 
    	LOGGER.info("Executing operation siCSDExpenseEntriesOut");
    	LOGGER.debug("mtCSDExpenseEntries="+mtCSDExpenseEntries);
        try {
        } catch (java.lang.Exception ex) {
        	LOGGER.error("SICSDExpenseEntriesOutImpl::siCSDExpenseEntriesOut::error::",ex);
            throw new RuntimeException(ex);
        }
    }

}
