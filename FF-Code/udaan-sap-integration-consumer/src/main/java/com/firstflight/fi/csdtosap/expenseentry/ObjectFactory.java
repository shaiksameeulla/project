
package com.firstflight.fi.csdtosap.expenseentry;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.firstflight.fi.csdtosap.expenseentries package. 
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

    private final static QName _MTCSDExpenseEntries_QNAME = new QName("http://FirstFlight.com/FI/CSDToSAP/ExpenseEntries", "MT_CSD_ExpenseEntries");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.firstflight.fi.csdtosap.expenseentries
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DTCSDExpenseEntries }
     * 
     */
    public DTCSDExpenseEntries createDTCSDExpenseEntries() {
        return new DTCSDExpenseEntries();
    }

    /**
     * Create an instance of {@link DTCSDExpenseEntries.ExpenseEntries }
     * 
     */
    public DTCSDExpenseEntries.ExpenseEntries createDTCSDExpenseEntriesExpenseEntries() {
        return new DTCSDExpenseEntries.ExpenseEntries();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTCSDExpenseEntries }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://FirstFlight.com/FI/CSDToSAP/ExpenseEntries", name = "MT_CSD_ExpenseEntries")
    public JAXBElement<DTCSDExpenseEntries> createMTCSDExpenseEntries(DTCSDExpenseEntries value) {
        return new JAXBElement<DTCSDExpenseEntries>(_MTCSDExpenseEntries_QNAME, DTCSDExpenseEntries.class, null, value);
    }

}
