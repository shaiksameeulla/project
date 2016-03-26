
package com.firstflight.fi.csdtosap.outstandingreport;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DT_CSD_CustomerOutstandingReport complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DT_CSD_CustomerOutstandingReport">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CustomerOutstandingReport" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="BillsUpTo" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="PaymentsUpTo" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="CustomerNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ProfitCentre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="EmployeeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="SAPTimestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "DT_CSD_CustomerOutstandingReport", propOrder = {
    "customerOutstandingReport"
})
public class DTCSDCustomerOutstandingReport {

    @XmlElement(name = "CustomerOutstandingReport")
    protected List<DTCSDCustomerOutstandingReport.CustomerOutstandingReport> customerOutstandingReport;

    /**
     * Gets the value of the customerOutstandingReport property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customerOutstandingReport property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomerOutstandingReport().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DTCSDCustomerOutstandingReport.CustomerOutstandingReport }
     * 
     * 
     */
    public List<DTCSDCustomerOutstandingReport.CustomerOutstandingReport> getCustomerOutstandingReport() {
        if (customerOutstandingReport == null) {
            customerOutstandingReport = new ArrayList<DTCSDCustomerOutstandingReport.CustomerOutstandingReport>();
        }
        return this.customerOutstandingReport;
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
     *         &lt;element name="BillsUpTo" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="PaymentsUpTo" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="CustomerNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ProfitCentre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="EmployeeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="SAPTimestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "billsUpTo",
        "paymentsUpTo",
        "customerNO",
        "profitCentre",
        "employeeCode",
        "email",
        "sapTimestamp"
    })
    public static class CustomerOutstandingReport {

        @XmlElement(name = "BillsUpTo")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar billsUpTo;
        @XmlElement(name = "PaymentsUpTo")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar paymentsUpTo;
        @XmlElement(name = "CustomerNO")
        protected String customerNO;
        @XmlElement(name = "ProfitCentre")
        protected String profitCentre;
        @XmlElement(name = "EmployeeCode")
        protected String employeeCode;
        @XmlElement(name = "Email")
        protected String email;
        @XmlElement(name = "SAPTimestamp")
        protected String sapTimestamp;

        /**
         * Gets the value of the billsUpTo property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getBillsUpTo() {
            return billsUpTo;
        }

        /**
         * Sets the value of the billsUpTo property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setBillsUpTo(XMLGregorianCalendar value) {
            this.billsUpTo = value;
        }

        /**
         * Gets the value of the paymentsUpTo property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getPaymentsUpTo() {
            return paymentsUpTo;
        }

        /**
         * Sets the value of the paymentsUpTo property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setPaymentsUpTo(XMLGregorianCalendar value) {
            this.paymentsUpTo = value;
        }

        /**
         * Gets the value of the customerNO property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomerNO() {
            return customerNO;
        }

        /**
         * Sets the value of the customerNO property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomerNO(String value) {
            this.customerNO = value;
        }

        /**
         * Gets the value of the profitCentre property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProfitCentre() {
            return profitCentre;
        }

        /**
         * Sets the value of the profitCentre property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProfitCentre(String value) {
            this.profitCentre = value;
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
         * Gets the value of the email property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEmail() {
            return email;
        }

        /**
         * Sets the value of the email property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEmail(String value) {
            this.email = value;
        }

        /**
         * Gets the value of the sapTimestamp property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSAPTimestamp() {
            return sapTimestamp;
        }

        /**
         * Sets the value of the sapTimestamp property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSAPTimestamp(String value) {
            this.sapTimestamp = value;
        }

    }

}
