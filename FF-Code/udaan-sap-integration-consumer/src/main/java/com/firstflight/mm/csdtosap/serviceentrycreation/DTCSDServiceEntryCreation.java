
package com.firstflight.mm.csdtosap.serviceentrycreation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DT_CSD_ServiceEntryCreation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DT_CSD_ServiceEntryCreation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ServiceEntryCreation" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="SLNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="NameOfColoader" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="SubServiceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="WayBill" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="CDNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="FlightNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Destination" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="GrossPrice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="PriceOthers" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="TotalPrice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ServiceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="UOM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Plant" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="TranscationNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="NameOfVendor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="VehicleNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="TripSheetDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="TripSheetNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Rent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="RRNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="TrainNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "DT_CSD_ServiceEntryCreation", propOrder = {
    "serviceEntryCreation"
})
public class DTCSDServiceEntryCreation {

    @XmlElement(name = "ServiceEntryCreation")
    protected List<DTCSDServiceEntryCreation.ServiceEntryCreation> serviceEntryCreation;

    /**
     * Gets the value of the serviceEntryCreation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serviceEntryCreation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServiceEntryCreation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DTCSDServiceEntryCreation.ServiceEntryCreation }
     * 
     * 
     */
    public List<DTCSDServiceEntryCreation.ServiceEntryCreation> getServiceEntryCreation() {
        if (serviceEntryCreation == null) {
            serviceEntryCreation = new ArrayList<DTCSDServiceEntryCreation.ServiceEntryCreation>();
        }
        return this.serviceEntryCreation;
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
     *         &lt;element name="SLNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="NameOfColoader" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="SubServiceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="WayBill" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="CDNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="FlightNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Destination" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="GrossPrice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="PriceOthers" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="TotalPrice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ServiceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="UOM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Plant" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="TranscationNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="NameOfVendor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="VehicleNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="TripSheetDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="TripSheetNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Rent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="RRNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="TrainNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "slno",
        "nameOfColoader",
        "subServiceType",
        "wayBill",
        "cdNo",
        "flightNo",
        "destination",
        "grossPrice",
        "priceOthers",
        "totalPrice",
        "serviceType",
        "uom",
        "quantity",
        "plant",
        "transcationNo",
        "nameOfVendor",
        "vehicleNo",
        "tripSheetDate",
        "tripSheetNo",
        "rent",
        "rrNo",
        "trainNo"
    })
    public static class ServiceEntryCreation {

        @XmlElement(name = "SLNO")
        protected String slno;
        @XmlElement(name = "NameOfColoader")
        protected String nameOfColoader;
        @XmlElement(name = "SubServiceType")
        protected String subServiceType;
        @XmlElement(name = "WayBill")
        protected String wayBill;
        @XmlElement(name = "CDNo")
        protected String cdNo;
        @XmlElement(name = "FlightNo")
        protected String flightNo;
        @XmlElement(name = "Destination")
        protected String destination;
        @XmlElement(name = "GrossPrice")
        protected String grossPrice;
        @XmlElement(name = "PriceOthers")
        protected String priceOthers;
        @XmlElement(name = "TotalPrice")
        protected String totalPrice;
        @XmlElement(name = "ServiceType")
        protected String serviceType;
        @XmlElement(name = "UOM")
        protected String uom;
        @XmlElement(name = "Quantity")
        protected String quantity;
        @XmlElement(name = "Plant")
        protected String plant;
        @XmlElement(name = "TranscationNo")
        protected String transcationNo;
        @XmlElement(name = "NameOfVendor")
        protected String nameOfVendor;
        @XmlElement(name = "VehicleNo")
        protected String vehicleNo;
        @XmlElement(name = "TripSheetDate")
        protected String tripSheetDate;
        @XmlElement(name = "TripSheetNo")
        protected String tripSheetNo;
        @XmlElement(name = "Rent")
        protected String rent;
        @XmlElement(name = "RRNo")
        protected String rrNo;
        @XmlElement(name = "TrainNo")
        protected String trainNo;

        /**
         * Gets the value of the slno property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSLNO() {
            return slno;
        }

        /**
         * Sets the value of the slno property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSLNO(String value) {
            this.slno = value;
        }

        /**
         * Gets the value of the nameOfColoader property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNameOfColoader() {
            return nameOfColoader;
        }

        /**
         * Sets the value of the nameOfColoader property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNameOfColoader(String value) {
            this.nameOfColoader = value;
        }

        /**
         * Gets the value of the subServiceType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSubServiceType() {
            return subServiceType;
        }

        /**
         * Sets the value of the subServiceType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSubServiceType(String value) {
            this.subServiceType = value;
        }

        /**
         * Gets the value of the wayBill property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWayBill() {
            return wayBill;
        }

        /**
         * Sets the value of the wayBill property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWayBill(String value) {
            this.wayBill = value;
        }

        /**
         * Gets the value of the cdNo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCDNo() {
            return cdNo;
        }

        /**
         * Sets the value of the cdNo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCDNo(String value) {
            this.cdNo = value;
        }

        /**
         * Gets the value of the flightNo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFlightNo() {
            return flightNo;
        }

        /**
         * Sets the value of the flightNo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFlightNo(String value) {
            this.flightNo = value;
        }

        /**
         * Gets the value of the destination property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDestination() {
            return destination;
        }

        /**
         * Sets the value of the destination property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDestination(String value) {
            this.destination = value;
        }

        /**
         * Gets the value of the grossPrice property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getGrossPrice() {
            return grossPrice;
        }

        /**
         * Sets the value of the grossPrice property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setGrossPrice(String value) {
            this.grossPrice = value;
        }

        /**
         * Gets the value of the priceOthers property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPriceOthers() {
            return priceOthers;
        }

        /**
         * Sets the value of the priceOthers property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPriceOthers(String value) {
            this.priceOthers = value;
        }

        /**
         * Gets the value of the totalPrice property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTotalPrice() {
            return totalPrice;
        }

        /**
         * Sets the value of the totalPrice property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTotalPrice(String value) {
            this.totalPrice = value;
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
         * Gets the value of the transcationNo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTranscationNo() {
            return transcationNo;
        }

        /**
         * Sets the value of the transcationNo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTranscationNo(String value) {
            this.transcationNo = value;
        }

        /**
         * Gets the value of the nameOfVendor property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNameOfVendor() {
            return nameOfVendor;
        }

        /**
         * Sets the value of the nameOfVendor property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNameOfVendor(String value) {
            this.nameOfVendor = value;
        }

        /**
         * Gets the value of the vehicleNo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getVehicleNo() {
            return vehicleNo;
        }

        /**
         * Sets the value of the vehicleNo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setVehicleNo(String value) {
            this.vehicleNo = value;
        }

        /**
         * Gets the value of the tripSheetDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTripSheetDate() {
            return tripSheetDate;
        }

        /**
         * Sets the value of the tripSheetDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTripSheetDate(String value) {
            this.tripSheetDate = value;
        }

        /**
         * Gets the value of the tripSheetNo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTripSheetNo() {
            return tripSheetNo;
        }

        /**
         * Sets the value of the tripSheetNo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTripSheetNo(String value) {
            this.tripSheetNo = value;
        }

        /**
         * Gets the value of the rent property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRent() {
            return rent;
        }

        /**
         * Sets the value of the rent property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRent(String value) {
            this.rent = value;
        }

        /**
         * Gets the value of the rrNo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRRNo() {
            return rrNo;
        }

        /**
         * Sets the value of the rrNo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRRNo(String value) {
            this.rrNo = value;
        }

        /**
         * Gets the value of the trainNo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTrainNo() {
            return trainNo;
        }

        /**
         * Sets the value of the trainNo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTrainNo(String value) {
            this.trainNo = value;
        }

    }

}
