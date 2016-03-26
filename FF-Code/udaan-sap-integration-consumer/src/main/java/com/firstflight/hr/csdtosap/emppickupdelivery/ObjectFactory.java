
package com.firstflight.hr.csdtosap.emppickupdelivery;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.firstflight.hr.csdtosap.emppickupdelivery package. 
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

    private final static QName _MTCSDEmpPickUpDelivery_QNAME = new QName("http://FirstFlight.com/HR/CSDToSAP/EmpPickUpDelivery", "MT_CSD_EmpPickUpDelivery");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.firstflight.hr.csdtosap.emppickupdelivery
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DTCSDEmpPickUpDelivery }
     * 
     */
    public DTCSDEmpPickUpDelivery createDTCSDEmpPickUpDelivery() {
        return new DTCSDEmpPickUpDelivery();
    }

    /**
     * Create an instance of {@link DTCSDEmpPickUpDelivery.EmpPickUpDelivery }
     * 
     */
    public DTCSDEmpPickUpDelivery.EmpPickUpDelivery createDTCSDEmpPickUpDeliveryEmpPickUpDelivery() {
        return new DTCSDEmpPickUpDelivery.EmpPickUpDelivery();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTCSDEmpPickUpDelivery }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://FirstFlight.com/HR/CSDToSAP/EmpPickUpDelivery", name = "MT_CSD_EmpPickUpDelivery")
    public JAXBElement<DTCSDEmpPickUpDelivery> createMTCSDEmpPickUpDelivery(DTCSDEmpPickUpDelivery value) {
        return new JAXBElement<DTCSDEmpPickUpDelivery>(_MTCSDEmpPickUpDelivery_QNAME, DTCSDEmpPickUpDelivery.class, null, value);
    }

}
