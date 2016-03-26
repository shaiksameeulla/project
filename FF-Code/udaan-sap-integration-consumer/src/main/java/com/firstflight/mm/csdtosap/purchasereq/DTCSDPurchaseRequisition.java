
package com.firstflight.mm.csdtosap.purchasereq;

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
 * <p>Java class for DT_CSD_PurchaseRequisition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DT_CSD_PurchaseRequisition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PurchaseRequisition" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="SAPStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ROW_NUMBER" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                   &lt;element name="REQUISITION_OFFICE_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="REQ_CREATED_DATE_TIME" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="REQUISITION_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ITEM_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ITEM_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DESCRIPTION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="UOM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="APPROVED_QUANTITY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Procurement_Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Serier_Starts_with" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="IS_PR_CONSOLIDATED" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "DT_CSD_PurchaseRequisition", propOrder = {
    "purchaseRequisition"
})
public class DTCSDPurchaseRequisition {

    @XmlElement(name = "PurchaseRequisition")
    protected List<DTCSDPurchaseRequisition.PurchaseRequisition> purchaseRequisition;

    /**
     * Gets the value of the purchaseRequisition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the purchaseRequisition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPurchaseRequisition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DTCSDPurchaseRequisition.PurchaseRequisition }
     * 
     * 
     */
    public List<DTCSDPurchaseRequisition.PurchaseRequisition> getPurchaseRequisition() {
        if (purchaseRequisition == null) {
            purchaseRequisition = new ArrayList<DTCSDPurchaseRequisition.PurchaseRequisition>();
        }
        return this.purchaseRequisition;
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
     *         &lt;element name="REQUISITION_OFFICE_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="REQ_CREATED_DATE_TIME" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="REQUISITION_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ITEM_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ITEM_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DESCRIPTION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="UOM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="APPROVED_QUANTITY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Procurement_Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Serier_Starts_with" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="IS_PR_CONSOLIDATED" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "requisitionofficecode",
        "reqcreateddatetime",
        "requisitionnumber",
        "itemcode",
        "itemtypecode",
        "description",
        "uom",
        "approvedquantity",
        "procurementType",
        "serierStartsWith",
        "isprconsolidated"
    })
    public static class PurchaseRequisition {

        @XmlElement(name = "Timestamp")
        protected String timestamp;
        @XmlElement(name = "SAPStatus")
        protected String sapStatus;
        @XmlElement(name = "ROW_NUMBER")
        protected BigInteger rownumber;
        @XmlElement(name = "REQUISITION_OFFICE_CODE")
        protected String requisitionofficecode;
        @XmlElement(name = "REQ_CREATED_DATE_TIME")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar reqcreateddatetime;
        @XmlElement(name = "REQUISITION_NUMBER")
        protected String requisitionnumber;
        @XmlElement(name = "ITEM_CODE")
        protected String itemcode;
        @XmlElement(name = "ITEM_TYPE_CODE")
        protected String itemtypecode;
        @XmlElement(name = "DESCRIPTION")
        protected String description;
        @XmlElement(name = "UOM")
        protected String uom;
        @XmlElement(name = "APPROVED_QUANTITY")
        protected String approvedquantity;
        @XmlElement(name = "Procurement_Type")
        protected String procurementType;
        @XmlElement(name = "Serier_Starts_with")
        protected String serierStartsWith;

        @XmlElement(name = "IS_PR_CONSOLIDATED")
        protected String isprconsolidated;

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
         * Gets the value of the requisitionofficecode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getREQUISITIONOFFICECODE() {
            return requisitionofficecode;
        }

        /**
         * Sets the value of the requisitionofficecode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setREQUISITIONOFFICECODE(String value) {
            this.requisitionofficecode = value;
        }

        /**
         * Gets the value of the reqcreateddatetime property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getREQCREATEDDATETIME() {
            return reqcreateddatetime;
        }

        /**
         * Sets the value of the reqcreateddatetime property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setREQCREATEDDATETIME(XMLGregorianCalendar value) {
            this.reqcreateddatetime = value;
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
         * Gets the value of the uom property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUOM() {
            return uom;
        }

        /**
         * Sets the value of the uom property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUOM(String value) {
            this.uom = value;
        }

        /**
         * Gets the value of the approvedquantity property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAPPROVEDQUANTITY() {
            return approvedquantity;
        }

        /**
         * Sets the value of the approvedquantity property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAPPROVEDQUANTITY(String value) {
            this.approvedquantity = value;
        }

        /**
         * Gets the value of the procurementType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProcurementType() {
            return procurementType;
        }

        /**
         * Sets the value of the procurementType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProcurementType(String value) {
            this.procurementType = value;
        }

        /**
         * Gets the value of the serierStartsWith property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSerierStartsWith() {
            return serierStartsWith;
        }

        /**
         * Sets the value of the serierStartsWith property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSerierStartsWith(String value) {
            this.serierStartsWith = value;
        }

		/**
         * Gets the value of the isprconsolidated property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getISPRCONSOLIDATED() {
            return isprconsolidated;
        }

		/**
         * Sets the value of the isprconsolidated property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setISPRCONSOLIDATED(String value) {
            this.isprconsolidated = value;
        }

    }

}
