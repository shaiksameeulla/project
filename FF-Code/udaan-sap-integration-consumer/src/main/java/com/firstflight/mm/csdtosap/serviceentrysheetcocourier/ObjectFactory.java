
package com.firstflight.mm.csdtosap.serviceentrysheetcocourier;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.firstflight.mm.csdtosap.serviceentrysheetcocourier package. 
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

    private final static QName _MTCSDServiceEntrySheetCoCourier_QNAME = new QName("http://FirstFlight.com/MM/CSDToSAP/ServiceEntrySheetCoCourier", "MT_CSD_ServiceEntrySheetCoCourier");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.firstflight.mm.csdtosap.serviceentrysheetcocourier
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DTCSDServiceEntrySheetCoCourier }
     * 
     */
    public DTCSDServiceEntrySheetCoCourier createDTCSDServiceEntrySheetCoCourier() {
        return new DTCSDServiceEntrySheetCoCourier();
    }

    /**
     * Create an instance of {@link DTCSDServiceEntrySheetCoCourier.ServiceEntrySheetCoCourier }
     * 
     */
    public DTCSDServiceEntrySheetCoCourier.ServiceEntrySheetCoCourier createDTCSDServiceEntrySheetCoCourierServiceEntrySheetCoCourier() {
        return new DTCSDServiceEntrySheetCoCourier.ServiceEntrySheetCoCourier();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTCSDServiceEntrySheetCoCourier }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://FirstFlight.com/MM/CSDToSAP/ServiceEntrySheetCoCourier", name = "MT_CSD_ServiceEntrySheetCoCourier")
    public JAXBElement<DTCSDServiceEntrySheetCoCourier> createMTCSDServiceEntrySheetCoCourier(DTCSDServiceEntrySheetCoCourier value) {
        return new JAXBElement<DTCSDServiceEntrySheetCoCourier>(_MTCSDServiceEntrySheetCoCourier_QNAME, DTCSDServiceEntrySheetCoCourier.class, null, value);
    }

}
