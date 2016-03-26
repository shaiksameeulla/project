
package com.firstflight.hr.csdtosap.EmpPickUpCommission;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DT_CSD_EmpPickUpCommission complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DT_CSD_EmpPickUpCommission">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PickUpCommission" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Employee_Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Prd_Grp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="PickUp_Count" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Net_Value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Calculated_For" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
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
@XmlType(name = "DT_CSD_EmpPickUpCommission", propOrder = {
    "pickUpCommission"
})
public class DTCSDEmpPickUpCommission {

    @XmlElement(name = "PickUpCommission")
    protected List<DTCSDEmpPickUpCommission.PickUpCommission> pickUpCommission;

    /**
     * Gets the value of the pickUpCommission property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pickUpCommission property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPickUpCommission().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DTCSDEmpPickUpCommission.PickUpCommission }
     * 
     * 
     */
    public List<DTCSDEmpPickUpCommission.PickUpCommission> getPickUpCommission() {
        if (pickUpCommission == null) {
            pickUpCommission = new ArrayList<DTCSDEmpPickUpCommission.PickUpCommission>();
        }
        return this.pickUpCommission;
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
     *         &lt;element name="Employee_Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Prd_Grp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="PickUp_Count" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Net_Value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Calculated_For" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
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
        "employeeCode",
        "prdGrp",
        "pickUpCount",
        "netValue",
        "calculatedFor"
    })
    public static class PickUpCommission {

        @XmlElement(name = "Employee_Code")
        protected String employeeCode;
        @XmlElement(name = "Prd_Grp")
        protected String prdGrp;
        @XmlElement(name = "PickUp_Count")
        protected String pickUpCount;
        @XmlElement(name = "Net_Value")
        protected String netValue;
        @XmlElement(name = "Calculated_For")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar calculatedFor;

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
         * Gets the value of the prdGrp property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPrdGrp() {
            return prdGrp;
        }

        /**
         * Sets the value of the prdGrp property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPrdGrp(String value) {
            this.prdGrp = value;
        }

        /**
         * Gets the value of the pickUpCount property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPickUpCount() {
            return pickUpCount;
        }

        /**
         * Sets the value of the pickUpCount property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPickUpCount(String value) {
            this.pickUpCount = value;
        }

        /**
         * Gets the value of the netValue property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNetValue() {
            return netValue;
        }

        /**
         * Sets the value of the netValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNetValue(String value) {
            this.netValue = value;
        }

        /**
         * Gets the value of the calculatedFor property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getCalculatedFor() {
            return calculatedFor;
        }

        /**
         * Sets the value of the calculatedFor property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setCalculatedFor(XMLGregorianCalendar value) {
            this.calculatedFor = value;
        }

    }

}
