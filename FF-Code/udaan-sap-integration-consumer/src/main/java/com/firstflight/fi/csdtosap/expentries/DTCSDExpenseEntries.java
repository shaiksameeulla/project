
package com.firstflight.fi.csdtosap.expentries;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DT_CSD_ExpenseEntries complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DT_CSD_ExpenseEntries">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ExpenseEntries" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ReportingRHOCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="BranchCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="TransactionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ExpenseGLCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ModeOfPayment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="BankGLCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Amount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ChequeDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="ChequeNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ChequeBank" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="CreationDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="SAPStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="EmployeeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ConsignmentNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DestinationRHO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ServiceCharge" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ServiceTaxBasic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="EDOnServiceTax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="HEDOnServiceTax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "DT_CSD_ExpenseEntries", propOrder = {
    "expenseEntries"
})
public class DTCSDExpenseEntries {

    @XmlElement(name = "ExpenseEntries")
    protected List<DTCSDExpenseEntries.ExpenseEntries> expenseEntries;

    /**
     * Gets the value of the expenseEntries property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the expenseEntries property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExpenseEntries().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DTCSDExpenseEntries.ExpenseEntries }
     * 
     * 
     */
    public List<DTCSDExpenseEntries.ExpenseEntries> getExpenseEntries() {
        if (expenseEntries == null) {
            expenseEntries = new ArrayList<DTCSDExpenseEntries.ExpenseEntries>();
        }
        return this.expenseEntries;
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
     *         &lt;element name="ReportingRHOCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="BranchCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="TransactionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ExpenseGLCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ModeOfPayment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="BankGLCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Amount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ChequeDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="ChequeNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ChequeBank" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="CreationDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="SAPStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="EmployeeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ConsignmentNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DestinationRHO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ServiceCharge" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ServiceTaxBasic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="EDOnServiceTax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="HEDOnServiceTax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "reportingRHOCode",
        "branchCode",
        "transactionID",
        "expenseGLCode",
        "modeOfPayment",
        "bankGLCode",
        "amount",
        "chequeDate",
        "chequeNo",
        "chequeBank",
        "creationDate",
        "sapStatus",
        "employeeCode",
        "consignmentNo",
        "destinationRHO",
        "serviceCharge",
        "serviceTaxBasic",
        "edOnServiceTax",
        "hedOnServiceTax",
        "timestamp"
    })
    public static class ExpenseEntries {

        @XmlElement(name = "ReportingRHOCode")
        protected String reportingRHOCode;
		@XmlElement(name = "BranchCode")
        protected String branchCode;
        @XmlElement(name = "TransactionID")
        protected String transactionID;
        @XmlElement(name = "ExpenseGLCode")
        protected String expenseGLCode;
        @XmlElement(name = "ModeOfPayment")
        protected String modeOfPayment;
        @XmlElement(name = "BankGLCode")
        protected String bankGLCode;
		@XmlElement(name = "Amount")
        protected String amount;
        @XmlElement(name = "ChequeDate")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar chequeDate;
        @XmlElement(name = "ChequeNo")
        protected String chequeNo;
        @XmlElement(name = "ChequeBank")
        protected String chequeBank;
        @XmlElement(name = "CreationDate")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar creationDate;

        @XmlElement(name = "SAPStatus")
        protected String sapStatus;

		@XmlElement(name = "EmployeeCode")
        protected String employeeCode;
		@XmlElement(name = "ConsignmentNo")
        protected String consignmentNo;
		@XmlElement(name = "DestinationRHO")
        protected String destinationRHO;
		@XmlElement(name = "ServiceCharge")
        protected String serviceCharge;
		@XmlElement(name = "ServiceTaxBasic")
        protected String serviceTaxBasic;
		@XmlElement(name = "EDOnServiceTax")
        protected String edOnServiceTax;
		@XmlElement(name = "HEDOnServiceTax")
        protected String hedOnServiceTax;
		@XmlElement(name = "Timestamp")
        protected String timestamp;

		/**
         * Gets the value of the reportingRHOCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getReportingRHOCode() {
            return reportingRHOCode;
        }

		/**
         * Sets the value of the reportingRHOCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setReportingRHOCode(String value) {
            this.reportingRHOCode = value;
        }

		/**
         * Gets the value of the branchCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBranchCode() {
            return branchCode;
        }

        /**
         * Sets the value of the branchCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBranchCode(String value) {
            this.branchCode = value;
        }

        /**
         * Gets the value of the transactionID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTransactionID() {
            return transactionID;
        }

        /**
         * Sets the value of the transactionID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTransactionID(String value) {
            this.transactionID = value;
        }

        /**
         * Gets the value of the expenseGLCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getExpenseGLCode() {
            return expenseGLCode;
        }

        /**
         * Sets the value of the expenseGLCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setExpenseGLCode(String value) {
            this.expenseGLCode = value;
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
         * Gets the value of the creationDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getCreationDate() {
            return creationDate;
        }

        /**
         * Sets the value of the creationDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setCreationDate(XMLGregorianCalendar value) {
            this.creationDate = value;
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
         * Gets the value of the employeeCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEmployeeCode() {
            return employeeCode;
        }

		/**
         * Sets the value of the employeeCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEmployeeCode(String value) {
            this.employeeCode = value;
        }

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
         * Gets the value of the destinationRHO property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDestinationRHO() {
            return destinationRHO;
        }

		/**
         * Sets the value of the destinationRHO property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDestinationRHO(String value) {
            this.destinationRHO = value;
        }

		/**
         * Gets the value of the serviceCharge property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getServiceCharge() {
            return serviceCharge;
        }

		/**
         * Sets the value of the serviceCharge property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setServiceCharge(String value) {
            this.serviceCharge = value;
        }

		/**
         * Gets the value of the serviceTaxBasic property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getServiceTaxBasic() {
            return serviceTaxBasic;
        }

		/**
         * Sets the value of the serviceTaxBasic property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setServiceTaxBasic(String value) {
            this.serviceTaxBasic = value;
        }

		/**
         * Gets the value of the edOnServiceTax property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEDOnServiceTax() {
            return edOnServiceTax;
        }

		/**
         * Sets the value of the edOnServiceTax property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEDOnServiceTax(String value) {
            this.edOnServiceTax = value;
        }

		/**
         * Gets the value of the hedOnServiceTax property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHEDOnServiceTax() {
            return hedOnServiceTax;
        }

		/**
         * Sets the value of the hedOnServiceTax property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHEDOnServiceTax(String value) {
            this.hedOnServiceTax = value;
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
