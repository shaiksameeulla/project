
package com.firstflight.sd.csdtosap.contract;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.firstflight.sd.csdtosap.contract package. 
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

    private final static QName _MTCSDContract_QNAME = new QName("http://FirstFlight.com/SD/CSDToSAP/Contract", "MT_CSD_Contract");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.firstflight.sd.csdtosap.contract
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DTCSDContract }
     * 
     */
    public DTCSDContract createDTCSDContract() {
        return new DTCSDContract();
    }

    /**
     * Create an instance of {@link DTCSDContract.Contract }
     * 
     */
    public DTCSDContract.Contract createDTCSDContractContract() {
        return new DTCSDContract.Contract();
    }

    /**
     * Create an instance of {@link DTCSDContract.Contract.PrimaryContact }
     * 
     */
    public DTCSDContract.Contract.PrimaryContact createDTCSDContractContractPrimaryContact() {
        return new DTCSDContract.Contract.PrimaryContact();
    }

    /**
     * Create an instance of {@link DTCSDContract.Contract.SecondaryContact }
     * 
     */
    public DTCSDContract.Contract.SecondaryContact createDTCSDContractContractSecondaryContact() {
        return new DTCSDContract.Contract.SecondaryContact();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTCSDContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://FirstFlight.com/SD/CSDToSAP/Contract", name = "MT_CSD_Contract")
    public JAXBElement<DTCSDContract> createMTCSDContract(DTCSDContract value) {
        return new JAXBElement<DTCSDContract>(_MTCSDContract_QNAME, DTCSDContract.class, null, value);
    }

}
