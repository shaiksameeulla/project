
package com.firstflight.mm.csdtosap.goodscancellation;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.firstflight.mm.csdtosap.goodscancellation package. 
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

    private final static QName _MTCSDGoodsCancellation_QNAME = new QName("http://FirstFlight.com/MM/CSDToSAP/GoodsCancellation", "MT_CSD_GoodsCancellation");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.firstflight.mm.csdtosap.goodscancellation
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DTCSDGoodsCancellation }
     * 
     */
    public DTCSDGoodsCancellation createDTCSDGoodsCancellation() {
        return new DTCSDGoodsCancellation();
    }

    /**
     * Create an instance of {@link DTCSDGoodsCancellation.GoodsCancellation }
     * 
     */
    public DTCSDGoodsCancellation.GoodsCancellation createDTCSDGoodsCancellationGoodsCancellation() {
        return new DTCSDGoodsCancellation.GoodsCancellation();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTCSDGoodsCancellation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://FirstFlight.com/MM/CSDToSAP/GoodsCancellation", name = "MT_CSD_GoodsCancellation")
    public JAXBElement<DTCSDGoodsCancellation> createMTCSDGoodsCancellation(DTCSDGoodsCancellation value) {
        return new JAXBElement<DTCSDGoodsCancellation>(_MTCSDGoodsCancellation_QNAME, DTCSDGoodsCancellation.class, null, value);
    }

}
