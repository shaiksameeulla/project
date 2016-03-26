
package com.firstflight.mm.csdtosap.stocktransfers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DT_CSD_StockTransfer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DT_CSD_StockTransfer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StockTransfer" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ROW_NUMBER" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                   &lt;element name="ISSUE_DATE" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="REQUISITION_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ITEM_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ITEM_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DESCRIPTION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="UNIT_OF_MEASURE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ISSUED_QUANTITY" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                   &lt;element name="ISSUE_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ISSUED_TO_OFFICE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ISSUE_OFFICE_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "DT_CSD_StockTransfer", propOrder = {
    "stockTransfer"
})
public class DTCSDStockTransfer {

    @XmlElement(name = "StockTransfer")
    protected List<DTCSDStockTransfer.StockTransfer> stockTransfer;

    /**
     * Gets the value of the stockTransfer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stockTransfer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStockTransfer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DTCSDStockTransfer.StockTransfer }
     * 
     * 
     */
    public List<DTCSDStockTransfer.StockTransfer> getStockTransfer() {
        if (stockTransfer == null) {
            stockTransfer = new ArrayList<DTCSDStockTransfer.StockTransfer>();
        }
        return this.stockTransfer;
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
     *         &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ROW_NUMBER" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *         &lt;element name="ISSUE_DATE" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="REQUISITION_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ITEM_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ITEM_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DESCRIPTION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="UNIT_OF_MEASURE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ISSUED_QUANTITY" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *         &lt;element name="ISSUE_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ISSUED_TO_OFFICE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ISSUE_OFFICE_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "timestamp",
        "rownumber",
        "issuedate",
        "requisitionnumber",
        "itemtypecode",
        "itemcode",
        "description",
        "unitofmeasure",
        "issuedquantity",
        "issuenumber",
        "issuedtooffice",
        "issueofficeid"
    })
    public static class StockTransfer {

        @XmlElement(name = "Timestamp", required = true)
        protected String timestamp;
        @XmlElement(name = "ROW_NUMBER")
        protected BigInteger rownumber;
        @XmlElement(name = "ISSUE_DATE")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar issuedate;
        @XmlElement(name = "REQUISITION_NUMBER")
        protected String requisitionnumber;
        @XmlElement(name = "ITEM_TYPE_CODE")
        protected String itemtypecode;
        @XmlElement(name = "ITEM_CODE")
        protected String itemcode;
        @XmlElement(name = "DESCRIPTION")
        protected String description;
        @XmlElement(name = "UNIT_OF_MEASURE")
        protected String unitofmeasure;
        @XmlElement(name = "ISSUED_QUANTITY")
        protected BigInteger issuedquantity;
        @XmlElement(name = "ISSUE_NUMBER")
        protected String issuenumber;
        @XmlElement(name = "ISSUED_TO_OFFICE")
        protected String issuedtooffice;
        @XmlElement(name = "ISSUE_OFFICE_ID")
        protected String issueofficeid;

        /**
         * Gets the value of the timestamp property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTimestamp() {
            return timestamp;
        }

        /**
         * Sets the value of the timestamp property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTimestamp(String value) {
            this.timestamp = value;
        }

        /**
         * Gets the value of the rownumber property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getROWNUMBER() {
            return rownumber;
        }

        /**
         * Sets the value of the rownumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setROWNUMBER(BigInteger value) {
            this.rownumber = value;
        }

        /**
         * Gets the value of the issuedate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getISSUEDATE() {
            return issuedate;
        }

        /**
         * Sets the value of the issuedate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setISSUEDATE(XMLGregorianCalendar value) {
            this.issuedate = value;
        }

        /**
         * Gets the value of the requisitionnumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getREQUISITIONNUMBER() {
            return requisitionnumber;
        }

        /**
         * Sets the value of the requisitionnumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setREQUISITIONNUMBER(String value) {
            this.requisitionnumber = value;
        }

        /**
         * Gets the value of the itemtypecode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getITEMTYPECODE() {
            return itemtypecode;
        }

        /**
         * Sets the value of the itemtypecode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setITEMTYPECODE(String value) {
            this.itemtypecode = value;
        }

        /**
         * Gets the value of the itemcode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getITEMCODE() {
            return itemcode;
        }

        /**
         * Sets the value of the itemcode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setITEMCODE(String value) {
            this.itemcode = value;
        }

        /**
         * Gets the value of the description property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDESCRIPTION() {
            return description;
        }

        /**
         * Sets the value of the description property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDESCRIPTION(String value) {
            this.description = value;
        }

        /**
         * Gets the value of the unitofmeasure property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUNITOFMEASURE() {
            return unitofmeasure;
        }

        /**
         * Sets the value of the unitofmeasure property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUNITOFMEASURE(String value) {
            this.unitofmeasure = value;
        }

        /**
         * Gets the value of the issuedquantity property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getISSUEDQUANTITY() {
            return issuedquantity;
        }

        /**
         * Sets the value of the issuedquantity property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setISSUEDQUANTITY(BigInteger value) {
            this.issuedquantity = value;
        }

        /**
         * Gets the value of the issuenumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getISSUENUMBER() {
            return issuenumber;
        }

        /**
         * Sets the value of the issuenumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setISSUENUMBER(String value) {
            this.issuenumber = value;
        }

        /**
         * Gets the value of the issuedtooffice property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getISSUEDTOOFFICE() {
            return issuedtooffice;
        }

        /**
         * Sets the value of the issuedtooffice property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setISSUEDTOOFFICE(String value) {
            this.issuedtooffice = value;
        }

        /**
         * Gets the value of the issueofficeid property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getISSUEOFFICEID() {
            return issueofficeid;
        }

        /**
         * Sets the value of the issueofficeid property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setISSUEOFFICEID(String value) {
            this.issueofficeid = value;
        }

    }

}
