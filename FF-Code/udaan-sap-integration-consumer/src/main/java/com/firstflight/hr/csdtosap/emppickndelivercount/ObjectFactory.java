
package com.firstflight.hr.csdtosap.emppickndelivercount;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.firstflight.hr.csdtosap.emppickndelivercount package. 
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

    private final static QName _MTCSDEmpPickNDeliverCount_QNAME = new QName("http://FirstFlight.com/HR/CSDToSAP/EmpPickNDeliverCount", "MT_CSD_EmpPickNDeliverCount");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.firstflight.hr.csdtosap.emppickndelivercount
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DTCSDEmpPickNDeliverCount }
     * 
     */
    public DTCSDEmpPickNDeliverCount createDTCSDEmpPickNDeliverCount() {
        return new DTCSDEmpPickNDeliverCount();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTCSDEmpPickNDeliverCount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://FirstFlight.com/HR/CSDToSAP/EmpPickNDeliverCount", name = "MT_CSD_EmpPickNDeliverCount")
    public JAXBElement<DTCSDEmpPickNDeliverCount> createMTCSDEmpPickNDeliverCount(DTCSDEmpPickNDeliverCount value) {
        return new JAXBElement<DTCSDEmpPickNDeliverCount>(_MTCSDEmpPickNDeliverCount_QNAME, DTCSDEmpPickNDeliverCount.class, null, value);
    }

}
