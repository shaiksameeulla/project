
package com.firstflight.hr.csdtosap.emppickupdelivery;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DT_CSD_EmpPickUpDelivery complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DT_CSD_EmpPickUpDelivery">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EmpPickUpDelivery" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="EMPLOYEE_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="PRODUCT_GROUP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="CALCULATED_FOR_PERIOD" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="DELIVERED_COUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DELIVERED_DAY_1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DELIVERED_DAY_2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DELIVERED_DAY_3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DELIVERED_DAY_4_BEYOND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="TRANS_CREATED_DATE" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
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
@XmlType(name = "DT_CSD_EmpPickUpDelivery", propOrder = {
    "empPickUpDelivery"
})
public class DTCSDEmpPickUpDelivery {

    @XmlElement(name = "EmpPickUpDelivery")
    protected List<DTCSDEmpPickUpDelivery.EmpPickUpDelivery> empPickUpDelivery;

    /**
     * Gets the value of the empPickUpDelivery property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the empPickUpDelivery property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEmpPickUpDelivery().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DTCSDEmpPickUpDelivery.EmpPickUpDelivery }
     * 
     * 
     */
    public List<DTCSDEmpPickUpDelivery.EmpPickUpDelivery> getEmpPickUpDelivery() {
        if (empPickUpDelivery == null) {
            empPickUpDelivery = new ArrayList<DTCSDEmpPickUpDelivery.EmpPickUpDelivery>();
        }
        return this.empPickUpDelivery;
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
     *         &lt;element name="EMPLOYEE_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="PRODUCT_GROUP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="CALCULATED_FOR_PERIOD" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="DELIVERED_COUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DELIVERED_DAY_1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DELIVERED_DAY_2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DELIVERED_DAY_3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DELIVERED_DAY_4_BEYOND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="TRANS_CREATED_DATE" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
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
        "employeecode",
        "productgroup",
        "calculatedforperiod",
        "deliveredcount",
        "deliveredday1",
        "deliveredday2",
        "deliveredday3",
        "deliveredday4BEYOND",
        "transcreateddate"
    })
    public static class EmpPickUpDelivery {

        @XmlElement(name = "EMPLOYEE_CODE")
        protected String employeecode;
        @XmlElement(name = "PRODUCT_GROUP")
        protected String productgroup;
        @XmlElement(name = "CALCULATED_FOR_PERIOD")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar calculatedforperiod;
        @XmlElement(name = "DELIVERED_COUNT")
        protected String deliveredcount;
        @XmlElement(name = "DELIVERED_DAY_1")
        protected String deliveredday1;
        @XmlElement(name = "DELIVERED_DAY_2")
        protected String deliveredday2;
        @XmlElement(name = "DELIVERED_DAY_3")
        protected String deliveredday3;
        @XmlElement(name = "DELIVERED_DAY_4_BEYOND")
        protected String deliveredday4BEYOND;
        @XmlElement(name = "TRANS_CREATED_DATE")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar transcreateddate;

        /**
         * Gets the value of the employeecode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEMPLOYEECODE() {
            return employeecode;
        }

        /**
         * Sets the value of the employeecode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEMPLOYEECODE(String value) {
            this.employeecode = value;
        }

        /**
         * Gets the value of the productgroup property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPRODUCTGROUP() {
            return productgroup;
        }

        /**
         * Sets the value of the productgroup property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPRODUCTGROUP(String value) {
            this.productgroup = value;
        }

        /**
         * Gets the value of the calculatedforperiod property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getCALCULATEDFORPERIOD() {
            return calculatedforperiod;
        }

        /**
         * Sets the value of the calculatedforperiod property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setCALCULATEDFORPERIOD(XMLGregorianCalendar value) {
            this.calculatedforperiod = value;
        }

        /**
         * Gets the value of the deliveredcount property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDELIVEREDCOUNT() {
            return deliveredcount;
        }

        /**
         * Sets the value of the deliveredcount property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDELIVEREDCOUNT(String value) {
            this.deliveredcount = value;
        }

        /**
         * Gets the value of the deliveredday1 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDELIVEREDDAY1() {
            return deliveredday1;
        }

        /**
         * Sets the value of the deliveredday1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDELIVEREDDAY1(String value) {
            this.deliveredday1 = value;
        }

        /**
         * Gets the value of the deliveredday2 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDELIVEREDDAY2() {
            return deliveredday2;
        }

        /**
         * Sets the value of the deliveredday2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDELIVEREDDAY2(String value) {
            this.deliveredday2 = value;
        }

        /**
         * Gets the value of the deliveredday3 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDELIVEREDDAY3() {
            return deliveredday3;
        }

        /**
         * Sets the value of the deliveredday3 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDELIVEREDDAY3(String value) {
            this.deliveredday3 = value;
        }

        /**
         * Gets the value of the deliveredday4BEYOND property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDELIVEREDDAY4BEYOND() {
            return deliveredday4BEYOND;
        }

        /**
         * Sets the value of the deliveredday4BEYOND property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDELIVEREDDAY4BEYOND(String value) {
            this.deliveredday4BEYOND = value;
        }

        /**
         * Gets the value of the transcreateddate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getTRANSCREATEDDATE() {
            return transcreateddate;
        }

        /**
         * Sets the value of the transcreateddate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setTRANSCREATEDDATE(XMLGregorianCalendar value) {
            this.transcreateddate = value;
        }

    }

}
