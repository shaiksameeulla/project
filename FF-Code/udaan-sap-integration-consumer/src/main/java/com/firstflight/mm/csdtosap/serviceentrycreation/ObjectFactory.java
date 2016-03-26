
package com.firstflight.mm.csdtosap.serviceentrycreation;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.firstflight.mm.csdtosap.serviceentrycreation package. 
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

    private final static QName _MTCSDServiceEntryCreation_QNAME = new QName("http://FirstFlight.com/MM/CSDToSAP/ServiceEntryCreation", "MT_CSD_ServiceEntryCreation");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.firstflight.mm.csdtosap.serviceentrycreation
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DTCSDServiceEntryCreation }
     * 
     */
    public DTCSDServiceEntryCreation createDTCSDServiceEntryCreation() {
        return new DTCSDServiceEntryCreation();
    }

    /**
     * Create an instance of {@link DTCSDServiceEntryCreation.ServiceEntryCreation }
     * 
     */
    public DTCSDServiceEntryCreation.ServiceEntryCreation createDTCSDServiceEntryCreationServiceEntryCreation() {
        return new DTCSDServiceEntryCreation.ServiceEntryCreation();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTCSDServiceEntryCreation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://FirstFlight.com/MM/CSDToSAP/ServiceEntryCreation", name = "MT_CSD_ServiceEntryCreation")
    public JAXBElement<DTCSDServiceEntryCreation> createMTCSDServiceEntryCreation(DTCSDServiceEntryCreation value) {
        return new JAXBElement<DTCSDServiceEntryCreation>(_MTCSDServiceEntryCreation_QNAME, DTCSDServiceEntryCreation.class, null, value);
    }

}
