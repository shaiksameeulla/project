
package com.firstflight.sd.csdtosap.salesorder;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.firstflight.sd.csdtosap.salesorder package. 
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

    private final static QName _MTCSDSalesOrder_QNAME = new QName("http://FirstFlight.com/SD/CSDToSAP/SalesOrder", "MT_CSD_SalesOrder");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.firstflight.sd.csdtosap.salesorder
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DTCSDSalesOrder }
     * 
     */
    public DTCSDSalesOrder createDTCSDSalesOrder() {
        return new DTCSDSalesOrder();
    }

    /**
     * Create an instance of {@link DTCSDSalesOrder.SalesOrder }
     * 
     */
    public DTCSDSalesOrder.SalesOrder createDTCSDSalesOrderSalesOrder() {
        return new DTCSDSalesOrder.SalesOrder();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTCSDSalesOrder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://FirstFlight.com/SD/CSDToSAP/SalesOrder", name = "MT_CSD_SalesOrder")
    public JAXBElement<DTCSDSalesOrder> createMTCSDSalesOrder(DTCSDSalesOrder value) {
        return new JAXBElement<DTCSDSalesOrder>(_MTCSDSalesOrder_QNAME, DTCSDSalesOrder.class, null, value);
    }

}
