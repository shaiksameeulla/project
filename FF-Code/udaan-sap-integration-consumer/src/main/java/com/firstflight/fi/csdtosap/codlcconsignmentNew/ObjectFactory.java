
package com.firstflight.fi.csdtosap.codlcconsignmentNew;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.firstflight.fi.csdtosap.codlcconsignment package. 
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

    private final static QName _MTCSDCODLCConsignment_QNAME = new QName("http://FirstFlight.com/FI/CSDToSAP/CODLCConsignment", "MT_CSD_CODLCConsignment");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.firstflight.fi.csdtosap.codlcconsignment
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DTCSDCODLCConsignment }
     * 
     */
    public DTCSDCODLCConsignment createDTCSDCODLCConsignment() {
        return new DTCSDCODLCConsignment();
    }

    /**
     * Create an instance of {@link DTCSDCODLCConsignment.CODLCConsignment }
     * 
     */
    public DTCSDCODLCConsignment.CODLCConsignment createDTCSDCODLCConsignmentCODLCConsignment() {
        return new DTCSDCODLCConsignment.CODLCConsignment();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTCSDCODLCConsignment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://FirstFlight.com/FI/CSDToSAP/CODLCConsignment", name = "MT_CSD_CODLCConsignment")
    public JAXBElement<DTCSDCODLCConsignment> createMTCSDCODLCConsignment(DTCSDCODLCConsignment value) {
        return new JAXBElement<DTCSDCODLCConsignment>(_MTCSDCODLCConsignment_QNAME, DTCSDCODLCConsignment.class, null, value);
    }

}
