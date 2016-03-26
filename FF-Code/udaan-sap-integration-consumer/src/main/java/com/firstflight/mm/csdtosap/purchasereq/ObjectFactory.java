
package com.firstflight.mm.csdtosap.purchasereq;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.firstflight.mm.csdtosap.purchaserequisition package. 
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

    private final static QName _MTCSDPurchaseRequisition_QNAME = new QName("http://FirstFlight.com/MM/CSDToSAP/PurchaseRequisition", "MT_CSD_PurchaseRequisition");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.firstflight.mm.csdtosap.purchaserequisition
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DTCSDPurchaseRequisition }
     * 
     */
    public DTCSDPurchaseRequisition createDTCSDPurchaseRequisition() {
        return new DTCSDPurchaseRequisition();
    }

    /**
     * Create an instance of {@link DTCSDPurchaseRequisition.PurchaseRequisition }
     * 
     */
    public DTCSDPurchaseRequisition.PurchaseRequisition createDTCSDPurchaseRequisitionPurchaseRequisition() {
        return new DTCSDPurchaseRequisition.PurchaseRequisition();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTCSDPurchaseRequisition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://FirstFlight.com/MM/CSDToSAP/PurchaseRequisition", name = "MT_CSD_PurchaseRequisition")
    public JAXBElement<DTCSDPurchaseRequisition> createMTCSDPurchaseRequisition(DTCSDPurchaseRequisition value) {
        return new JAXBElement<DTCSDPurchaseRequisition>(_MTCSDPurchaseRequisition_QNAME, DTCSDPurchaseRequisition.class, null, value);
    }

}
