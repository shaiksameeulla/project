
package com.firstflight.mm.csdtosap.serviceentrysheetcocourier;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DT_CSD_ServiceEntrySheetCoCourier complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DT_CSD_ServiceEntrySheetCoCourier">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ServiceEntrySheetCoCourier" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Vendor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="PickUpOrDeliveryFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="CountOfConsignments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ProductCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Plant" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ServiceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Weight" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="TransactionNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DoxNonDoxType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DT_CSD_ServiceEntrySheetCoCourier", propOrder = {
    "serviceEntrySheetCoCourier"
})
public class DTCSDServiceEntrySheetCoCourier {

    @XmlElement(name = "ServiceEntrySheetCoCourier")
    protected List<DTCSDServiceEntrySheetCoCourier.ServiceEntrySheetCoCourier> serviceEntrySheetCoCourier;

    /**
     * Gets the value of the serviceEntrySheetCoCourier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serviceEntrySheetCoCourier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServiceEntrySheetCoCourier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DTCSDServiceEntrySheetCoCourier.ServiceEntrySheetCoCourier }
     * 
     * 
     */
    public List<DTCSDServiceEntrySheetCoCourier.ServiceEntrySheetCoCourier> getServiceEntrySheetCoCourier() {
        if (serviceEntrySheetCoCourier == null) {
            serviceEntrySheetCoCourier = new ArrayList<DTCSDServiceEntrySheetCoCourier.ServiceEntrySheetCoCourier>();
        }
        return this.serviceEntrySheetCoCourier;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Vendor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="PickUpOrDeliveryFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="CountOfConsignments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ProductCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Plant" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ServiceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Weight" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="TransactionNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DoxNonDoxType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "vendor",
        "date",
        "pickUpOrDeliveryFlag",
        "countOfConsignments",
        "productCode",
        "plant",
        "serviceType",
        "weight",
        "transactionNo",
        "doxNonDoxType"
    })
    public static class ServiceEntrySheetCoCourier {

        @XmlElement(name = "Vendor")
        protected String vendor;
        @XmlElement(name = "Date")
        protected String date;
        @XmlElement(name = "PickUpOrDeliveryFlag")
        protected String pickUpOrDeliveryFlag;
        @XmlElement(name = "CountOfConsignments")
        protected String countOfConsignments;
        @XmlElement(name = "ProductCode")
        protected String productCode;
        @XmlElement(name = "Plant")
        protected String plant;
        @XmlElement(name = "ServiceType")
        protected String serviceType;
        @XmlElement(name = "Weight")
        protected String weight;
        @XmlElement(name = "TransactionNo")
        protected String transactionNo;
        @XmlElement(name = "DoxNonDoxType")
        protected String doxNonDoxType;

        /**
         * Gets the value of the vendor property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getVendor() {
            return vendor;
        }

        /**
         * Sets the value of the vendor property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setVendor(String value) {
            this.vendor = value;
        }

        /**
         * Gets the value of the date property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDate() {
            return date;
        }

        /**
         * Sets the value of the date property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDate(String value) {
            this.date = value;
        }

        /**
         * Gets the value of the pickUpOrDeliveryFlag property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPickUpOrDeliveryFlag() {
            return pickUpOrDeliveryFlag;
        }

        /**
         * Sets the value of the pickUpOrDeliveryFlag property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPickUpOrDeliveryFlag(String value) {
            this.pickUpOrDeliveryFlag = value;
        }

        /**
         * Gets the value of the countOfConsignments property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCountOfConsignments() {
            return countOfConsignments;
        }

        /**
         * Sets the value of the countOfConsignments property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCountOfConsignments(String value) {
            this.countOfConsignments = value;
        }

        /**
         * Gets the value of the productCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProductCode() {
            return productCode;
        }

        /**
         * Sets the value of the productCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProductCode(String value) {
            this.productCode = value;
        }

        /**
         * Gets the value of the plant property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPlant() {
            return plant;
        }

        /**
         * Sets the value of the plant property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPlant(String value) {
            this.plant = value;
        }

        /**
         * Gets the value of the serviceType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getServiceType() {
            return serviceType;
        }

        /**
         * Sets the value of the serviceType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setServiceType(String value) {
            this.serviceType = value;
        }

        /**
         * Gets the value of the weight property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWeight() {
            return weight;
        }

        /**
         * Sets the value of the weight property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWeight(String value) {
            this.weight = value;
        }

        /**
         * Gets the value of the transactionNo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTransactionNo() {
            return transactionNo;
        }

        /**
         * Sets the value of the transactionNo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTransactionNo(String value) {
            this.transactionNo = value;
        }

        /**
         * Gets the value of the doxNonDoxType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDoxNonDoxType() {
            return doxNonDoxType;
        }

        /**
         * Sets the value of the doxNonDoxType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDoxNonDoxType(String value) {
            this.doxNonDoxType = value;
        }

    }

}
