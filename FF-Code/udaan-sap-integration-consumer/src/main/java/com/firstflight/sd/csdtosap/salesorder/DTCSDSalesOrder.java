
package com.firstflight.sd.csdtosap.salesorder;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DT_CSD_SalesOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DT_CSD_SalesOrder">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SalesOrder" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="BookingDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="CustShipTo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DistributionChannel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="CustSoldTo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="BookingOffice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DestinationBranch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="TrnNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Material" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="RTO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Fright" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="FuelSurcharge" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="RiskSurcharge" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ParcelHandlingCharges" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="AirportHandlingCharges" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DocumentHandlingCharges" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ValueofMaterial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="CODCharges" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ToPayCharges" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="LCCharges" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Others" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ServiceTax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="EducationCess" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="HighEduCess" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="StateTax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="SurchargeonStateTax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="GrandTotal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "DT_CSD_SalesOrder", propOrder = {
    "salesOrder"
})
public class DTCSDSalesOrder {

    @XmlElement(name = "SalesOrder")
    protected List<DTCSDSalesOrder.SalesOrder> salesOrder;

    /**
     * Gets the value of the salesOrder property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the salesOrder property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSalesOrder().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DTCSDSalesOrder.SalesOrder }
     * 
     * 
     */
    public List<DTCSDSalesOrder.SalesOrder> getSalesOrder() {
        if (salesOrder == null) {
            salesOrder = new ArrayList<DTCSDSalesOrder.SalesOrder>();
        }
        return this.salesOrder;
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
     *         &lt;element name="BookingDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="CustShipTo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DistributionChannel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="CustSoldTo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="BookingOffice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DestinationBranch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="TrnNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Material" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="RTO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Fright" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="FuelSurcharge" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="RiskSurcharge" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ParcelHandlingCharges" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="AirportHandlingCharges" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DocumentHandlingCharges" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ValueofMaterial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="CODCharges" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ToPayCharges" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="LCCharges" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Others" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ServiceTax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="EducationCess" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="HighEduCess" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="StateTax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="SurchargeonStateTax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="GrandTotal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "bookingDate",
        "custShipTo",
        "distributionChannel",
        "custSoldTo",
        "bookingOffice",
        "destinationBranch",
        "trnNo",
        "material",
        "rto",
        "quantity",
        "fright",
        "fuelSurcharge",
        "riskSurcharge",
        "parcelHandlingCharges",
        "airportHandlingCharges",
        "documentHandlingCharges",
        "valueofMaterial",
        "codCharges",
        "toPayCharges",
        "lcCharges",
        "others",
        "serviceTax",
        "educationCess",
        "highEduCess",
        "stateTax",
        "surchargeonStateTax",
        "grandTotal",
        "timestamp"
    })
    public static class SalesOrder {

        @XmlElement(name = "BookingDate")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar bookingDate;
        @XmlElement(name = "CustShipTo")
        protected String custShipTo;
        @XmlElement(name = "DistributionChannel")
        protected String distributionChannel;
        @XmlElement(name = "CustSoldTo")
        protected String custSoldTo;
        @XmlElement(name = "BookingOffice")
        protected String bookingOffice;
        @XmlElement(name = "DestinationBranch")
        protected String destinationBranch;
        @XmlElement(name = "TrnNo")
        protected String trnNo;
        @XmlElement(name = "Material")
        protected String material;
        @XmlElement(name = "RTO")
        protected String rto;
        @XmlElement(name = "Quantity")
        protected String quantity;
        @XmlElement(name = "Fright")
        protected String fright;
        @XmlElement(name = "FuelSurcharge")
        protected String fuelSurcharge;
        @XmlElement(name = "RiskSurcharge")
        protected String riskSurcharge;
        @XmlElement(name = "ParcelHandlingCharges")
        protected String parcelHandlingCharges;
        @XmlElement(name = "AirportHandlingCharges")
        protected String airportHandlingCharges;
        @XmlElement(name = "DocumentHandlingCharges")
        protected String documentHandlingCharges;
        @XmlElement(name = "ValueofMaterial")
        protected String valueofMaterial;
        @XmlElement(name = "CODCharges")
        protected String codCharges;
        @XmlElement(name = "ToPayCharges")
        protected String toPayCharges;
        @XmlElement(name = "LCCharges")
        protected String lcCharges;
        @XmlElement(name = "Others")
        protected String others;
        @XmlElement(name = "ServiceTax")
        protected String serviceTax;
        @XmlElement(name = "EducationCess")
        protected String educationCess;
        @XmlElement(name = "HighEduCess")
        protected String highEduCess;
        @XmlElement(name = "StateTax")
        protected String stateTax;
        @XmlElement(name = "SurchargeonStateTax")
        protected String surchargeonStateTax;
        @XmlElement(name = "GrandTotal")
        protected String grandTotal;
        @XmlElement(name = "Timestamp")
        protected String timestamp;

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
         * Gets the value of the custShipTo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustShipTo() {
            return custShipTo;
        }

        /**
         * Sets the value of the custShipTo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustShipTo(String value) {
            this.custShipTo = value;
        }

        /**
         * Gets the value of the distributionChannel property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDistributionChannel() {
            return distributionChannel;
        }

        /**
         * Sets the value of the distributionChannel property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDistributionChannel(String value) {
            this.distributionChannel = value;
        }

        /**
         * Gets the value of the custSoldTo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustSoldTo() {
            return custSoldTo;
        }

        /**
         * Sets the value of the custSoldTo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustSoldTo(String value) {
            this.custSoldTo = value;
        }

        /**
         * Gets the value of the bookingOffice property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBookingOffice() {
            return bookingOffice;
        }

        /**
         * Sets the value of the bookingOffice property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBookingOffice(String value) {
            this.bookingOffice = value;
        }

        /**
         * Gets the value of the destinationBranch property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDestinationBranch() {
            return destinationBranch;
        }

        /**
         * Sets the value of the destinationBranch property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDestinationBranch(String value) {
            this.destinationBranch = value;
        }

        /**
         * Gets the value of the trnNo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTrnNo() {
            return trnNo;
        }

        /**
         * Sets the value of the trnNo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTrnNo(String value) {
            this.trnNo = value;
        }

        /**
         * Gets the value of the material property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMaterial() {
            return material;
        }

        /**
         * Sets the value of the material property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMaterial(String value) {
            this.material = value;
        }

        /**
         * Gets the value of the rto property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRTO() {
            return rto;
        }

        /**
         * Sets the value of the rto property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRTO(String value) {
            this.rto = value;
        }

        /**
         * Gets the value of the quantity property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getQuantity() {
            return quantity;
        }

        /**
         * Sets the value of the quantity property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setQuantity(String value) {
            this.quantity = value;
        }

        /**
         * Gets the value of the fright property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFright() {
            return fright;
        }

        /**
         * Sets the value of the fright property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFright(String value) {
            this.fright = value;
        }

        /**
         * Gets the value of the fuelSurcharge property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFuelSurcharge() {
            return fuelSurcharge;
        }

        /**
         * Sets the value of the fuelSurcharge property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFuelSurcharge(String value) {
            this.fuelSurcharge = value;
        }

        /**
         * Gets the value of the riskSurcharge property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRiskSurcharge() {
            return riskSurcharge;
        }

        /**
         * Sets the value of the riskSurcharge property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRiskSurcharge(String value) {
            this.riskSurcharge = value;
        }

        /**
         * Gets the value of the parcelHandlingCharges property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getParcelHandlingCharges() {
            return parcelHandlingCharges;
        }

        /**
         * Sets the value of the parcelHandlingCharges property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setParcelHandlingCharges(String value) {
            this.parcelHandlingCharges = value;
        }

        /**
         * Gets the value of the airportHandlingCharges property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAirportHandlingCharges() {
            return airportHandlingCharges;
        }

        /**
         * Sets the value of the airportHandlingCharges property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAirportHandlingCharges(String value) {
            this.airportHandlingCharges = value;
        }

        /**
         * Gets the value of the documentHandlingCharges property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDocumentHandlingCharges() {
            return documentHandlingCharges;
        }

        /**
         * Sets the value of the documentHandlingCharges property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDocumentHandlingCharges(String value) {
            this.documentHandlingCharges = value;
        }

        /**
         * Gets the value of the valueofMaterial property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValueofMaterial() {
            return valueofMaterial;
        }

        /**
         * Sets the value of the valueofMaterial property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValueofMaterial(String value) {
            this.valueofMaterial = value;
        }

        /**
         * Gets the value of the codCharges property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCODCharges() {
            return codCharges;
        }

        /**
         * Sets the value of the codCharges property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCODCharges(String value) {
            this.codCharges = value;
        }

        /**
         * Gets the value of the toPayCharges property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getToPayCharges() {
            return toPayCharges;
        }

        /**
         * Sets the value of the toPayCharges property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setToPayCharges(String value) {
            this.toPayCharges = value;
        }

        /**
         * Gets the value of the lcCharges property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLCCharges() {
            return lcCharges;
        }

        /**
         * Sets the value of the lcCharges property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLCCharges(String value) {
            this.lcCharges = value;
        }

        /**
         * Gets the value of the others property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOthers() {
            return others;
        }

        /**
         * Sets the value of the others property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOthers(String value) {
            this.others = value;
        }

        /**
         * Gets the value of the serviceTax property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getServiceTax() {
            return serviceTax;
        }

        /**
         * Sets the value of the serviceTax property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setServiceTax(String value) {
            this.serviceTax = value;
        }

        /**
         * Gets the value of the educationCess property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEducationCess() {
            return educationCess;
        }

        /**
         * Sets the value of the educationCess property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEducationCess(String value) {
            this.educationCess = value;
        }

        /**
         * Gets the value of the highEduCess property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHighEduCess() {
            return highEduCess;
        }

        /**
         * Sets the value of the highEduCess property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHighEduCess(String value) {
            this.highEduCess = value;
        }

        /**
         * Gets the value of the stateTax property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStateTax() {
            return stateTax;
        }

        /**
         * Sets the value of the stateTax property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStateTax(String value) {
            this.stateTax = value;
        }

        /**
         * Gets the value of the surchargeonStateTax property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSurchargeonStateTax() {
            return surchargeonStateTax;
        }

        /**
         * Sets the value of the surchargeonStateTax property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSurchargeonStateTax(String value) {
            this.surchargeonStateTax = value;
        }

        /**
         * Gets the value of the grandTotal property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getGrandTotal() {
            return grandTotal;
        }

        /**
         * Sets the value of the grandTotal property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setGrandTotal(String value) {
            this.grandTotal = value;
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
