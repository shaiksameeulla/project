
package com.firstflight.fi.csdtosap.codlcconsignmentNew;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DT_CSD_CODLCConsignment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DT_CSD_CODLCConsignment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CODLCConsignment" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="SAPStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="CustomerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="BookingDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="BookingOfficeRHOCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ConsignmentNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="CODValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="BAAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="LCValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DestinationRHO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="StatusFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="RTODRSUpdateDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="RTODate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="ConsigneeDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
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
@XmlType(name = "DT_CSD_CODLCConsignment", propOrder = {
    "codlcConsignment"
})
public class DTCSDCODLCConsignment {

    @XmlElement(name = "CODLCConsignment")
    protected List<DTCSDCODLCConsignment.CODLCConsignment> codlcConsignment;

    /**
     * Gets the value of the codlcConsignment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the codlcConsignment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCODLCConsignment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DTCSDCODLCConsignment.CODLCConsignment }
     * 
     * 
     */
    public List<DTCSDCODLCConsignment.CODLCConsignment> getCODLCConsignment() {
        if (codlcConsignment == null) {
            codlcConsignment = new ArrayList<DTCSDCODLCConsignment.CODLCConsignment>();
        }
        return this.codlcConsignment;
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
     *         &lt;element name="CustomerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="BookingDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="BookingOfficeRHOCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ConsignmentNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="CODValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="BAAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="LCValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DestinationRHO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="StatusFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="RTODRSUpdateDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="RTODate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="ConsigneeDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
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
        "customerCode",
        "bookingDate",
        "bookingOfficeRHOCode",
        "consignmentNo",
        "codValue",
        "baAmount",
        "lcValue",
        "destinationRHO",
        "statusFlag",
        "rtodrsUpdateDate",
        "rtoDate",
        "consigneeDate"
    })
    public static class CODLCConsignment {

        @XmlElement(name = "Timestamp")
        protected String timestamp;
        @XmlElement(name = "SAPStatus")
        protected String sapStatus;
        @XmlElement(name = "CustomerCode")
        protected String customerCode;
        @XmlElement(name = "BookingDate")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar bookingDate;
        @XmlElement(name = "BookingOfficeRHOCode")
        protected String bookingOfficeRHOCode;
        @XmlElement(name = "ConsignmentNo")
        protected String consignmentNo;
        @XmlElement(name = "CODValue")
        protected String codValue;
        @XmlElement(name = "BAAmount")
        protected String baAmount;
        @XmlElement(name = "LCValue")
        protected String lcValue;
        @XmlElement(name = "DestinationRHO")
        protected String destinationRHO;
        @XmlElement(name = "StatusFlag")
        protected String statusFlag;
        @XmlElement(name = "RTODRSUpdateDate")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar rtodrsUpdateDate;
        @XmlElement(name = "RTODate")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar rtoDate;
        @XmlElement(name = "ConsigneeDate")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar consigneeDate;

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
         * Gets the value of the bookingDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getBookingDate() {
            return bookingDate;
        }

        /**
         * Sets the value of the bookingDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setBookingDate(XMLGregorianCalendar value) {
            this.bookingDate = value;
        }

        /**
         * Gets the value of the bookingOfficeRHOCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBookingOfficeRHOCode() {
            return bookingOfficeRHOCode;
        }

        /**
         * Sets the value of the bookingOfficeRHOCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBookingOfficeRHOCode(String value) {
            this.bookingOfficeRHOCode = value;
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
         * Gets the value of the codValue property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCODValue() {
            return codValue;
        }

        /**
         * Sets the value of the codValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCODValue(String value) {
            this.codValue = value;
        }

        /**
         * Gets the value of the baAmount property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBAAmount() {
            return baAmount;
        }

        /**
         * Sets the value of the baAmount property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBAAmount(String value) {
            this.baAmount = value;
        }

        /**
         * Gets the value of the lcValue property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLCValue() {
            return lcValue;
        }

        /**
         * Sets the value of the lcValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLCValue(String value) {
            this.lcValue = value;
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
         * Gets the value of the statusFlag property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatusFlag() {
            return statusFlag;
        }

        /**
         * Sets the value of the statusFlag property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatusFlag(String value) {
            this.statusFlag = value;
        }

        /**
         * Gets the value of the rtodrsUpdateDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getRTODRSUpdateDate() {
            return rtodrsUpdateDate;
        }

        /**
         * Sets the value of the rtodrsUpdateDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setRTODRSUpdateDate(XMLGregorianCalendar value) {
            this.rtodrsUpdateDate = value;
        }

        /**
         * Gets the value of the rtoDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getRTODate() {
            return rtoDate;
        }

        /**
         * Sets the value of the rtoDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setRTODate(XMLGregorianCalendar value) {
            this.rtoDate = value;
        }

        /**
         * Gets the value of the consigneeDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getConsigneeDate() {
            return consigneeDate;
        }

        /**
         * Sets the value of the consigneeDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setConsigneeDate(XMLGregorianCalendar value) {
            this.consigneeDate = value;
        }

    }

}
