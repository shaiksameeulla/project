
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.firstflight.mm.csdtosap.stocktransfers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class was generated by Apache CXF 2.7.1
 * 2013-04-16T15:50:21.053+05:30
 * Generated source version: 2.7.1
 * 
 */

@javax.jws.WebService(
                      serviceName = "SI_CSD_StockTransfer_OutService",
                      portName = "SI_CSD_StockTransfer_OutPort",
                      targetNamespace = "http://FirstFlight.com/MM/CSDToSAP/StockTransfer",
                      wsdlLocation = "http://172.16.10.106:50000/sap/xi/engine?type=entry&version=3.0&Sender.Service=CSD-DEV&Interface=http%3A%2F%2FFirstFlight.com%2FMM%2FCSDToSAP%2FStockTransfer%5ESI_CSD_StockTransfer_Out&QualityOfService=ExactlyOnce&sap-user=cg-pi&sap-password=gouri1",
                      endpointInterface = "com.firstflight.mm.csdtosap.stocktransfer.SICSDStockTransferOut")
                      
public class SICSDStockTransferOutImpl implements SICSDStockTransferOut {

	/** The logger. */
	private final static Logger LOG = LoggerFactory
			.getLogger(SICSDStockTransferOutImpl.class);

    /* (non-Javadoc)
     * @see com.firstflight.mm.csdtosap.stocktransfer.SICSDStockTransferOut#siCSDStockTransferOut(com.firstflight.mm.csdtosap.stocktransfer.DTCSDStockTransfer  mtCSDStockTransfer )*
     */
    public void siCSDStockTransferOut(DTCSDStockTransfer mtCSDStockTransfer) { 
        LOG.info("Executing operation siCSDStockTransferOut");
        LOG.debug("mtCSDStockTransfer::"+mtCSDStockTransfer);
        try {
        } catch (java.lang.Exception ex) {
        	LOG.error("SICSDStockTransferOutImpl::siCSDStockTransferOut::error::",ex);
            throw new RuntimeException(ex);
        }
    }

}
