
package com.firstflight.mm.csdtosap.goodsreceipts;

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
 * <p>Java class for DT_CSD_GoodsReceipt complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DT_CSD_GoodsReceipt">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GoodsReceipt" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="SAPStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ROW_NUMBER" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                   &lt;element name="RECEIVED_DATE" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="REQUISITION_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ITEM_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ITEM_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DESCRIPTION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="UNIT_OF_MEASURE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="RECEIVED_QUANTITY" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                   &lt;element name="ACKNOWLEDGEMENT_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="StockIssueNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "DT_CSD_GoodsReceipt", propOrder = {
    "goodsReceipt"
})
public class DTCSDGoodsReceipt {

    @XmlElement(name = "GoodsReceipt")
    protected List<DTCSDGoodsReceipt.GoodsReceipt> goodsReceipt;

    /**
     * Gets the value of the goodsReceipt property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the goodsReceipt property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGoodsReceipt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DTCSDGoodsReceipt.GoodsReceipt }
     * 
     * 
     */
    public List<DTCSDGoodsReceipt.GoodsReceipt> getGoodsReceipt() {
        if (goodsReceipt == null) {
            goodsReceipt = new ArrayList<DTCSDGoodsReceipt.GoodsReceipt>();
        }
        return this.goodsReceipt;
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
     *         &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="SAPStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ROW_NUMBER" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *         &lt;element name="RECEIVED_DATE" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="REQUISITION_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ITEM_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ITEM_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DESCRIPTION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="UNIT_OF_MEASURE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="RECEIVED_QUANTITY" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *         &lt;element name="ACKNOWLEDGEMENT_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="StockIssueNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "sapStatus",
        "rownumber",
        "receiveddate",
        "requisitionnumber",
        "itemtypecode",
        "itemcode",
        "description",
        "unitofmeasure",
        "receivedquantity",
        "acknowledgementnumber",
        "stockIssueNo"
    })
    public static class GoodsReceipt {

        @XmlElement(name = "Timestamp")
        protected String timestamp;
        @XmlElement(name = "SAPStatus")
        protected String sapStatus;
        @XmlElement(name = "ROW_NUMBER")
        protected BigInteger rownumber;
        @XmlElement(name = "RECEIVED_DATE")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar receiveddate;
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
        @XmlElement(name = "RECEIVED_QUANTITY")
        protected BigInteger receivedquantity;
        @XmlElement(name = "ACKNOWLEDGEMENT_NUMBER")
        protected String acknowledgementnumber;

        @XmlElement(name = "StockIssueNo")
        protected String stockIssueNo;

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
         * Gets the value of the sapStatus property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSAPStatus() {
            return sapStatus;
        }

        /**
         * Sets the value of the sapStatus property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSAPStatus(String value) {
            this.sapStatus = value;
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
         * Gets the value of the receiveddate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getRECEIVEDDATE() {
            return receiveddate;
        }

        /**
         * Sets the value of the receiveddate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setRECEIVEDDATE(XMLGregorianCalendar value) {
            this.receiveddate = value;
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
         * Gets the value of the receivedquantity property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getRECEIVEDQUANTITY() {
            return receivedquantity;
        }

        /**
         * Sets the value of the receivedquantity property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setRECEIVEDQUANTITY(BigInteger value) {
            this.receivedquantity = value;
        }

        /**
         * Gets the value of the acknowledgementnumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getACKNOWLEDGEMENTNUMBER() {
            return acknowledgementnumber;
        }

        /**
         * Sets the value of the acknowledgementnumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setACKNOWLEDGEMENTNUMBER(String value) {
            this.acknowledgementnumber = value;
        }

		/**
         * Gets the value of the stockIssueNo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStockIssueNo() {
            return stockIssueNo;
        }

		/**
         * Sets the value of the stockIssueNo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStockIssueNo(String value) {
            this.stockIssueNo = value;
        }

    }

}
