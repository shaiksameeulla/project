
package com.firstflight.fi.csdtosap.codlcliability;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DT_CSD_CODLCLiability complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DT_CSD_CODLCLiability">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CODLCLiability" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TransactionNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="CreatedDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="CustomerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ChequeDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="ChequeNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ChequeBankName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="BankGLCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Amount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="SAPStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "DT_CSD_CODLCLiability", propOrder = {
    "codlcLiability"
})
public class DTCSDCODLCLiability {

    @XmlElement(name = "CODLCLiability")
    protected List<DTCSDCODLCLiability.CODLCLiability> codlcLiability;

    /**
     * Gets the value of the codlcLiability property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the codlcLiability property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCODLCLiability().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DTCSDCODLCLiability.CODLCLiability }
     * 
     * 
     */
    public List<DTCSDCODLCLiability.CODLCLiability> getCODLCLiability() {
        if (codlcLiability == null) {
            codlcLiability = new ArrayList<DTCSDCODLCLiability.CODLCLiability>();
        }
        return this.codlcLiability;
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
     *         &lt;element name="TransactionNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="CreatedDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="CustomerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ChequeDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="ChequeNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ChequeBankName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="BankGLCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Amount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="SAPStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "transactionNo",
        "createdDate",
        "customerCode",
        "chequeDate",
        "chequeNo",
        "chequeBankName",
        "bankGLCode",
        "amount",
        "sapStatus",
        "timestamp"
    })
    public static class CODLCLiability {

        @XmlElement(name = "TransactionNo")
        protected String transactionNo;
        @XmlElement(name = "CreatedDate")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar createdDate;
        @XmlElement(name = "CustomerCode")
        protected String customerCode;
        @XmlElement(name = "ChequeDate")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar chequeDate;
        @XmlElement(name = "ChequeNo")
        protected String chequeNo;
        @XmlElement(name = "ChequeBankName")
        protected String chequeBankName;
        @XmlElement(name = "BankGLCode")
        protected String bankGLCode;
        @XmlElement(name = "Amount")
        protected String amount;
        @XmlElement(name = "SAPStatus")
        protected String sapStatus;
        @XmlElement(name = "Timestamp")
        protected String timestamp;

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
         * Gets the value of the chequeBankName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getChequeBankName() {
            return chequeBankName;
        }

        /**
         * Sets the value of the chequeBankName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setChequeBankName(String value) {
            this.chequeBankName = value;
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
         * Gets the value of the amount property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAmount() {
            return amount;
        }

        /**
         * Sets the value of the amount property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAmount(String value) {
            this.amount = value;
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

    }

}
