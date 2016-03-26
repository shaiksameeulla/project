
package com.firstflight.fi.csdtosap.codlcliabilityRegion;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.firstflight.fi.csdtosap.codlcliability package. 
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

    private final static QName _MTCSDCODLCLiability_QNAME = new QName("http://FirstFlight.com/FI/CSDToSAP/CODLCLiability", "MT_CSD_CODLCLiability");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.firstflight.fi.csdtosap.codlcliability
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DTCSDCODLCLiability }
     * 
     */
    public DTCSDCODLCLiability createDTCSDCODLCLiability() {
        return new DTCSDCODLCLiability();
    }

    /**
     * Create an instance of {@link DTCSDCODLCLiability.CODLCLiability }
     * 
     */
    public DTCSDCODLCLiability.CODLCLiability createDTCSDCODLCLiabilityCODLCLiability() {
        return new DTCSDCODLCLiability.CODLCLiability();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTCSDCODLCLiability }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://FirstFlight.com/FI/CSDToSAP/CODLCLiability", name = "MT_CSD_CODLCLiability")
    public JAXBElement<DTCSDCODLCLiability> createMTCSDCODLCLiability(DTCSDCODLCLiability value) {
        return new JAXBElement<DTCSDCODLCLiability>(_MTCSDCODLCLiability_QNAME, DTCSDCODLCLiability.class, null, value);
    }

}
