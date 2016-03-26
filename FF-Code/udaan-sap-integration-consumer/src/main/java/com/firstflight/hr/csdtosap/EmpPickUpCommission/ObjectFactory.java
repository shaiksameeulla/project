
package com.firstflight.hr.csdtosap.EmpPickUpCommission;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.firstflight.hr.csdtosap.emppickupcommission package. 
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

    private final static QName _MTCSDEmpPickUpCommission_QNAME = new QName("http://FirstFlight.com/HR/CSDToSAP/EmpPickUpCommission", "MT_CSD_EmpPickUpCommission");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.firstflight.hr.csdtosap.emppickupcommission
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DTCSDEmpPickUpCommission }
     * 
     */
    public DTCSDEmpPickUpCommission createDTCSDEmpPickUpCommission() {
        return new DTCSDEmpPickUpCommission();
    }

    /**
     * Create an instance of {@link DTCSDEmpPickUpCommission.PickUpCommission }
     * 
     */
    public DTCSDEmpPickUpCommission.PickUpCommission createDTCSDEmpPickUpCommissionPickUpCommission() {
        return new DTCSDEmpPickUpCommission.PickUpCommission();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTCSDEmpPickUpCommission }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://FirstFlight.com/HR/CSDToSAP/EmpPickUpCommission", name = "MT_CSD_EmpPickUpCommission")
    public JAXBElement<DTCSDEmpPickUpCommission> createMTCSDEmpPickUpCommission(DTCSDEmpPickUpCommission value) {
        return new JAXBElement<DTCSDEmpPickUpCommission>(_MTCSDEmpPickUpCommission_QNAME, DTCSDEmpPickUpCommission.class, null, value);
    }

}
