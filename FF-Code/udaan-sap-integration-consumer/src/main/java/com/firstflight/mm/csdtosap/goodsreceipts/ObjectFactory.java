
package com.firstflight.mm.csdtosap.goodsreceipts;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.firstflight.mm.csdtosap.goodsreceipt package. 
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

    private final static QName _MTCSDGoodsReceipt_QNAME = new QName("http://FirstFlight.com/MM/CSDToSAP/GoodsReceipt", "MT_CSD_GoodsReceipt");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.firstflight.mm.csdtosap.goodsreceipt
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DTCSDGoodsReceipt }
     * 
     */
    public DTCSDGoodsReceipt createDTCSDGoodsReceipt() {
        return new DTCSDGoodsReceipt();
    }

    /**
     * Create an instance of {@link DTCSDGoodsReceipt.GoodsReceipt }
     * 
     */
    public DTCSDGoodsReceipt.GoodsReceipt createDTCSDGoodsReceiptGoodsReceipt() {
        return new DTCSDGoodsReceipt.GoodsReceipt();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTCSDGoodsReceipt }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://FirstFlight.com/MM/CSDToSAP/GoodsReceipt", name = "MT_CSD_GoodsReceipt")
    public JAXBElement<DTCSDGoodsReceipt> createMTCSDGoodsReceipt(DTCSDGoodsReceipt value) {
        return new JAXBElement<DTCSDGoodsReceipt>(_MTCSDGoodsReceipt_QNAME, DTCSDGoodsReceipt.class, null, value);
    }

}
