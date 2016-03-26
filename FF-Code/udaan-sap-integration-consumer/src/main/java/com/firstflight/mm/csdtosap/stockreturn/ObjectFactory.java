
package com.firstflight.mm.csdtosap.stockreturn;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.firstflight.mm.csdtosap.stockreturn package. 
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

    private final static QName _MTCSDStockReturn_QNAME = new QName("http://FirstFlight.com/MM/CSDToSAP/StockReturn", "MT_CSD_StockReturn");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.firstflight.mm.csdtosap.stockreturn
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DTCSDStockReturn }
     * 
     */
    public DTCSDStockReturn createDTCSDStockReturn() {
        return new DTCSDStockReturn();
    }

    /**
     * Create an instance of {@link DTCSDStockReturn.StockReturn }
     * 
     */
    public DTCSDStockReturn.StockReturn createDTCSDStockReturnStockReturn() {
        return new DTCSDStockReturn.StockReturn();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTCSDStockReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://FirstFlight.com/MM/CSDToSAP/StockReturn", name = "MT_CSD_StockReturn")
    public JAXBElement<DTCSDStockReturn> createMTCSDStockReturn(DTCSDStockReturn value) {
        return new JAXBElement<DTCSDStockReturn>(_MTCSDStockReturn_QNAME, DTCSDStockReturn.class, null, value);
    }

}
