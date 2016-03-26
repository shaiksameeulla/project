
package com.firstflight.mm.csdtosap.stockreturnfromba;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.firstflight.mm.csdtosap.stockreturnfromba package. 
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

    private final static QName _MTCSDStockReturnFromBA_QNAME = new QName("http://FirstFlight.com/MM/CSDToSAP/StockReturnFromBA", "MT_CSD_StockReturnFromBA");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.firstflight.mm.csdtosap.stockreturnfromba
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DTCSDStockReturnFromBA }
     * 
     */
    public DTCSDStockReturnFromBA createDTCSDStockReturnFromBA() {
        return new DTCSDStockReturnFromBA();
    }

    /**
     * Create an instance of {@link DTCSDStockReturnFromBA.StockReturnFromBA }
     * 
     */
    public DTCSDStockReturnFromBA.StockReturnFromBA createDTCSDStockReturnFromBAStockReturnFromBA() {
        return new DTCSDStockReturnFromBA.StockReturnFromBA();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTCSDStockReturnFromBA }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://FirstFlight.com/MM/CSDToSAP/StockReturnFromBA", name = "MT_CSD_StockReturnFromBA")
    public JAXBElement<DTCSDStockReturnFromBA> createMTCSDStockReturnFromBA(DTCSDStockReturnFromBA value) {
        return new JAXBElement<DTCSDStockReturnFromBA>(_MTCSDStockReturnFromBA_QNAME, DTCSDStockReturnFromBA.class, null, value);
    }

}
