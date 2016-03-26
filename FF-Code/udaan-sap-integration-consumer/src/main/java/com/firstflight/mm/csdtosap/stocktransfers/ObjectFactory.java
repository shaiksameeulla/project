
package com.firstflight.mm.csdtosap.stocktransfers;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.firstflight.mm.csdtosap.stocktransfer package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _MTCSDStockTransfer_QNAME = new QName("http://FirstFlight.com/MM/CSDToSAP/StockTransfer", "MT_CSD_StockTransfer");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.firstflight.mm.csdtosap.stocktransfer
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DTCSDStockTransfer }
     * 
     */
    public DTCSDStockTransfer createDTCSDStockTransfer() {
        return new DTCSDStockTransfer();
    }

    /**
     * Create an instance of {@link DTCSDStockTransfer.StockTransfer }
     * 
     */
    public DTCSDStockTransfer.StockTransfer createDTCSDStockTransferStockTransfer() {
        return new DTCSDStockTransfer.StockTransfer();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTCSDStockTransfer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://FirstFlight.com/MM/CSDToSAP/StockTransfer", name = "MT_CSD_StockTransfer")
    public JAXBElement<DTCSDStockTransfer> createMTCSDStockTransfer(DTCSDStockTransfer value) {
        return new JAXBElement<DTCSDStockTransfer>(_MTCSDStockTransfer_QNAME, DTCSDStockTransfer.class, null, value);
    }

}
