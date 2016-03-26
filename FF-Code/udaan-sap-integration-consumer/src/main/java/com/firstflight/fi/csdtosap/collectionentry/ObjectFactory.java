
package com.firstflight.fi.csdtosap.collectionentry;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.firstflight.fi.csdtosap.collectionentries package. 
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

    private final static QName _MTCSDCollectionEntries_QNAME = new QName("http://FirstFlight.com/FI/CSDToSAP/CollectionEntries", "MT_CSD_CollectionEntries");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.firstflight.fi.csdtosap.collectionentries
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DTCSDCollectionEntries }
     * 
     */
    public DTCSDCollectionEntries createDTCSDCollectionEntries() {
        return new DTCSDCollectionEntries();
    }

    /**
     * Create an instance of {@link DTCSDCollectionEntries.CollectionEntries }
     * 
     */
    public DTCSDCollectionEntries.CollectionEntries createDTCSDCollectionEntriesCollectionEntries() {
        return new DTCSDCollectionEntries.CollectionEntries();
    }

    /**
     * Create an instance of {@link DTCSDCollectionEntries.CollectionEntries.ItemDetails }
     * 
     */
    public DTCSDCollectionEntries.CollectionEntries.ItemDetails createDTCSDCollectionEntriesCollectionEntriesItemDetails() {
        return new DTCSDCollectionEntries.CollectionEntries.ItemDetails();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTCSDCollectionEntries }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://FirstFlight.com/FI/CSDToSAP/CollectionEntries", name = "MT_CSD_CollectionEntries")
    public JAXBElement<DTCSDCollectionEntries> createMTCSDCollectionEntries(DTCSDCollectionEntries value) {
        return new JAXBElement<DTCSDCollectionEntries>(_MTCSDCollectionEntries_QNAME, DTCSDCollectionEntries.class, null, value);
    }

}
