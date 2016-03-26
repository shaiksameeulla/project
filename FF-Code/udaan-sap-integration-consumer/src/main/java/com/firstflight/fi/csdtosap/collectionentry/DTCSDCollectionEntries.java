
package com.firstflight.fi.csdtosap.collectionentry;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DT_CSD_CollectionEntries complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DT_CSD_CollectionEntries">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CollectionEntries" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CustomerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="TransactionNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="CreatedDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="ModeOfPayment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="BankGLCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ChequeDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="ChequeNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ChequeBank" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Branch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="SAPStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ItemDetails" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ConsignmentNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="CollectionAgainst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="BillNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="ReceivedAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="TDSAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="BilledAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="Deduction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="Remarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="ReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
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
@XmlType(name = "DT_CSD_CollectionEntries", propOrder = {
    "collectionEntries"
})
public class DTCSDCollectionEntries {

    @XmlElement(name = "CollectionEntries")
    protected List<DTCSDCollectionEntries.CollectionEntries> collectionEntries;

    /**
     * Gets the value of the collectionEntries property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the collectionEntries property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCollectionEntries().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DTCSDCollectionEntries.CollectionEntries }
     * 
     * 
     */
    public List<DTCSDCollectionEntries.CollectionEntries> getCollectionEntries() {
        if (collectionEntries == null) {
            collectionEntries = new ArrayList<DTCSDCollectionEntries.CollectionEntries>();
        }
        return this.collectionEntries;
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
     *         &lt;element name="CustomerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="TransactionNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="CreatedDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="ModeOfPayment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="BankGLCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ChequeDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="ChequeNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ChequeBank" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Branch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="SAPStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ItemDetails" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ConsignmentNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="CollectionAgainst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="BillNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="ReceivedAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="TDSAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="BilledAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Deduction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Remarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="ReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    @XmlType(name = "", propOrder = {
        "customerCode",
        "transactionNo",
        "createdDate",
        "modeOfPayment",
        "bankGLCode",
        "chequeDate",
        "chequeNo",
        "chequeBank",
        "branch",
        "sapStatus",
        "timestamp",
        "itemDetails"
    })
    public static class CollectionEntries {

        @XmlElement(name = "CustomerCode")
        protected String customerCode;
        @XmlElement(name = "TransactionNo")
        protected String transactionNo;
        @XmlElement(name = "CreatedDate")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar createdDate;
        @XmlElement(name = "ModeOfPayment")
        protected String modeOfPayment;
        @XmlElement(name = "BankGLCode")
        protected String bankGLCode;
        @XmlElement(name = "ChequeDate")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar chequeDate;
        @XmlElement(name = "ChequeNo")
        protected String chequeNo;
        @XmlElement(name = "ChequeBank")
        protected String chequeBank;
        @XmlElement(name = "Branch")
        protected String branch;
        @XmlElement(name = "SAPStatus")
        protected String sapStatus;
        @XmlElement(name = "Timestamp")
        protected String timestamp;
        @XmlElement(name = "ItemDetails")
		public List<DTCSDCollectionEntries.CollectionEntries.ItemDetails> itemDetails;

        /**
         * Gets the value of the customerCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomerCode() {
            return customerCode;
        }

        /**
         * Sets the value of the customerCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomerCode(String value) {
            this.customerCode = value;
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
         * Gets the value of the createdDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getCreatedDate() {
            return createdDate;
        }

        /**
         * Sets the value of the createdDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setCreatedDate(XMLGregorianCalendar value) {
            this.createdDate = value;
        }

        /**
         * Gets the value of the modeOfPayment property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getModeOfPayment() {
            return modeOfPayment;
        }

        /**
         * Sets the value of the modeOfPayment property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setModeOfPayment(String value) {
            this.modeOfPayment = value;
        }

        /**
         * Gets the value of the bankGLCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBankGLCode() {
            return bankGLCode;
        }

        /**
         * Sets the value of the bankGLCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBankGLCode(String value) {
            this.bankGLCode = value;
        }

        /**
         * Gets the value of the chequeDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getChequeDate() {
            return chequeDate;
        }

        /**
         * Sets the value of the chequeDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setChequeDate(XMLGregorianCalendar value) {
            this.chequeDate = value;
        }

        /**
         * Gets the value of the chequeNo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getChequeNo() {
            return chequeNo;
        }

        /**
         * Sets the value of the chequeNo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setChequeNo(String value) {
            this.chequeNo = value;
        }

        /**
         * Gets the value of the chequeBank property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getChequeBank() {
            return chequeBank;
        }

        /**
         * Sets the value of the chequeBank property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setChequeBank(String value) {
            this.chequeBank = value;
        }

        /**
         * Gets the value of the branch property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBranch() {
            return branch;
        }

        /**
         * Sets the value of the branch property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBranch(String value) {
            this.branch = value;
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
         * Gets the value of the itemDetails property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the itemDetails property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getItemDetails().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DTCSDCollectionEntries.CollectionEntries.ItemDetails }
         * 
         * 
         */
        public List<DTCSDCollectionEntries.CollectionEntries.ItemDetails> getItemDetails() {
            if (itemDetails == null) {
                itemDetails = new ArrayList<DTCSDCollectionEntries.CollectionEntries.ItemDetails>();
            }
            return this.itemDetails;
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
         *         &lt;element name="ConsignmentNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="CollectionAgainst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="BillNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="ReceivedAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="TDSAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="BilledAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="Deduction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="Remarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="ReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            "consignmentNo",
            "collectionAgainst",
            "billNo",
            "receivedAmount",
            "tdsAmount",
            "billedAmount",
            "deduction",
            "remarks",
            "reasonCode"
        })
        public static class ItemDetails {

            @XmlElement(name = "ConsignmentNo")
            protected String consignmentNo;
            @XmlElement(name = "CollectionAgainst")
            protected String collectionAgainst;
            @XmlElement(name = "BillNo")
            protected String billNo;
            @XmlElement(name = "ReceivedAmount")
            protected String receivedAmount;
            @XmlElement(name = "TDSAmount")
            protected String tdsAmount;
            @XmlElement(name = "BilledAmount")
            protected String billedAmount;
            @XmlElement(name = "Deduction")
            protected String deduction;
            @XmlElement(name = "Remarks")
            protected String remarks;
            @XmlElement(name = "ReasonCode")
            protected String reasonCode;

            /**
             * Gets the value of the consignmentNo property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getConsignmentNo() {
                return consignmentNo;
            }

            /**
             * Sets the value of the consignmentNo property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setConsignmentNo(String value) {
                this.consignmentNo = value;
            }

            /**
             * Gets the value of the collectionAgainst property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCollectionAgainst() {
                return collectionAgainst;
            }

            /**
             * Sets the value of the collectionAgainst property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCollectionAgainst(String value) {
                this.collectionAgainst = value;
            }

            /**
             * Gets the value of the billNo property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBillNo() {
                return billNo;
            }

            /**
             * Sets the value of the billNo property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBillNo(String value) {
                this.billNo = value;
            }

            /**
             * Gets the value of the receivedAmount property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getReceivedAmount() {
                return receivedAmount;
            }

            /**
             * Sets the value of the receivedAmount property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setReceivedAmount(String value) {
                this.receivedAmount = value;
            }

            /**
             * Gets the value of the tdsAmount property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTDSAmount() {
                return tdsAmount;
            }

            /**
             * Sets the value of the tdsAmount property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTDSAmount(String value) {
                this.tdsAmount = value;
            }

            /**
             * Gets the value of the billedAmount property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBilledAmount() {
                return billedAmount;
            }

            /**
             * Sets the value of the billedAmount property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBilledAmount(String value) {
                this.billedAmount = value;
            }

            /**
             * Gets the value of the deduction property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDeduction() {
                return deduction;
            }

            /**
             * Sets the value of the deduction property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDeduction(String value) {
                this.deduction = value;
            }

            /**
             * Gets the value of the remarks property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRemarks() {
                return remarks;
            }

            /**
             * Sets the value of the remarks property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRemarks(String value) {
                this.remarks = value;
            }

            /**
             * Gets the value of the reasonCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getReasonCode() {
                return reasonCode;
            }

            /**
             * Sets the value of the reasonCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setReasonCode(String value) {
                this.reasonCode = value;
            }

        }

    }

}
