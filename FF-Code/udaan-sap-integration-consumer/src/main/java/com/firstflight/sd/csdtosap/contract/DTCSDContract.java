
package com.firstflight.sd.csdtosap.contract;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DT_CSD_Contract complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DT_CSD_Contract">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Contract" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="SAPStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ContractNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="CustomerNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="SalesOfficeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="SalesPersonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="IndustryTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="CustomerGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DistChannel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="GroupKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="CustName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Address1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Address2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Address3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="City" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Pincode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="State" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="PrimaryContact" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="PersonName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="ContactNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="Extention" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="Mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="Fax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="SecondaryContact" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="PersonName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="ContactNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="Extention" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="Mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="Fax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="BillingCycle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="PaymentTerms" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="PlantCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="TANNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="SalesDistrict" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="PANNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="LegacyCustCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "DT_CSD_Contract", propOrder = {
    "contract"
})
public class DTCSDContract {

    @XmlElement(name = "Contract")
    protected List<DTCSDContract.Contract> contract;

    /**
     * Gets the value of the contract property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contract property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContract().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DTCSDContract.Contract }
     * 
     * 
     */
    public List<DTCSDContract.Contract> getContract() {
        if (contract == null) {
            contract = new ArrayList<DTCSDContract.Contract>();
        }
        return this.contract;
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
     *         &lt;element name="ContractNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="CustomerNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="SalesOfficeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="SalesPersonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="IndustryTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="CustomerGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DistChannel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="GroupKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="CustName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Address1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Address2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Address3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="City" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Pincode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="State" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="PrimaryContact" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="PersonName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="ContactNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Extention" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Fax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="SecondaryContact" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="PersonName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="ContactNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Extention" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Fax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="BillingCycle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="PaymentTerms" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="PlantCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="TANNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="SalesDistrict" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="PANNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="LegacyCustCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "contractNo",
        "customerNo",
        "salesOfficeCode",
        "salesPersonCode",
        "industryTypeCode",
        "customerGroup",
        "distChannel",
        "groupKey",
        "custName",
        "address1",
        "address2",
        "address3",
        "city",
        "pincode",
        "state",
        "primaryContact",
        "secondaryContact",
        "billingCycle",
        "paymentTerms",
        "plantCode",
        "tanNo",
        "salesDistrict",
        "panNo",
        "status",
        "legacyCustCode"
    })
    public static class Contract {

        @XmlElement(name = "Timestamp")
        protected String timestamp;
        @XmlElement(name = "SAPStatus")
        protected String sapStatus;
        @XmlElement(name = "ContractNo")
        protected String contractNo;
        @XmlElement(name = "CustomerNo")
        protected String customerNo;
        @XmlElement(name = "SalesOfficeCode")
        protected String salesOfficeCode;
        @XmlElement(name = "SalesPersonCode")
        protected String salesPersonCode;
        @XmlElement(name = "IndustryTypeCode")
        protected String industryTypeCode;
        @XmlElement(name = "CustomerGroup")
        protected String customerGroup;
        @XmlElement(name = "DistChannel")
        protected String distChannel;
        @XmlElement(name = "GroupKey")
        protected String groupKey;
        @XmlElement(name = "CustName")
        protected String custName;
        @XmlElement(name = "Address1")
        protected String address1;
        @XmlElement(name = "Address2")
        protected String address2;
        @XmlElement(name = "Address3")
        protected String address3;
        @XmlElement(name = "City")
        protected String city;
        @XmlElement(name = "Pincode")
        protected String pincode;
        @XmlElement(name = "State")
        protected String state;
        @XmlElement(name = "PrimaryContact")
        protected DTCSDContract.Contract.PrimaryContact primaryContact;
        @XmlElement(name = "SecondaryContact")
        protected DTCSDContract.Contract.SecondaryContact secondaryContact;
        @XmlElement(name = "BillingCycle")
        protected String billingCycle;
        @XmlElement(name = "PaymentTerms")
        protected String paymentTerms;
        @XmlElement(name = "PlantCode")
        protected String plantCode;
        @XmlElement(name = "TANNo")
        protected String tanNo;
        @XmlElement(name = "SalesDistrict")
        protected String salesDistrict;
        @XmlElement(name = "PANNo")
        protected String panNo;
        @XmlElement(name = "Status")
        protected String status;

        @XmlElement(name = "LegacyCustCode")
        protected String legacyCustCode;

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
         * Gets the value of the contractNo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContractNo() {
            return contractNo;
        }

        /**
         * Sets the value of the contractNo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContractNo(String value) {
            this.contractNo = value;
        }

        /**
         * Gets the value of the customerNo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomerNo() {
            return customerNo;
        }

        /**
         * Sets the value of the customerNo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomerNo(String value) {
            this.customerNo = value;
        }

        /**
         * Gets the value of the salesOfficeCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSalesOfficeCode() {
            return salesOfficeCode;
        }

        /**
         * Sets the value of the salesOfficeCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSalesOfficeCode(String value) {
            this.salesOfficeCode = value;
        }

        /**
         * Gets the value of the salesPersonCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSalesPersonCode() {
            return salesPersonCode;
        }

        /**
         * Sets the value of the salesPersonCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSalesPersonCode(String value) {
            this.salesPersonCode = value;
        }

        /**
         * Gets the value of the industryTypeCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIndustryTypeCode() {
            return industryTypeCode;
        }

        /**
         * Sets the value of the industryTypeCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIndustryTypeCode(String value) {
            this.industryTypeCode = value;
        }

        /**
         * Gets the value of the customerGroup property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomerGroup() {
            return customerGroup;
        }

        /**
         * Sets the value of the customerGroup property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomerGroup(String value) {
            this.customerGroup = value;
        }

        /**
         * Gets the value of the distChannel property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDistChannel() {
            return distChannel;
        }

        /**
         * Sets the value of the distChannel property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDistChannel(String value) {
            this.distChannel = value;
        }

        /**
         * Gets the value of the groupKey property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getGroupKey() {
            return groupKey;
        }

        /**
         * Sets the value of the groupKey property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setGroupKey(String value) {
            this.groupKey = value;
        }

        /**
         * Gets the value of the custName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustName() {
            return custName;
        }

        /**
         * Sets the value of the custName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustName(String value) {
            this.custName = value;
        }

        /**
         * Gets the value of the address1 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAddress1() {
            return address1;
        }

        /**
         * Sets the value of the address1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAddress1(String value) {
            this.address1 = value;
        }

        /**
         * Gets the value of the address2 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAddress2() {
            return address2;
        }

        /**
         * Sets the value of the address2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAddress2(String value) {
            this.address2 = value;
        }

        /**
         * Gets the value of the address3 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAddress3() {
            return address3;
        }

        /**
         * Sets the value of the address3 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAddress3(String value) {
            this.address3 = value;
        }

        /**
         * Gets the value of the city property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCity() {
            return city;
        }

        /**
         * Sets the value of the city property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCity(String value) {
            this.city = value;
        }

        /**
         * Gets the value of the pincode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPincode() {
            return pincode;
        }

        /**
         * Sets the value of the pincode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPincode(String value) {
            this.pincode = value;
        }

        /**
         * Gets the value of the state property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getState() {
            return state;
        }

        /**
         * Sets the value of the state property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setState(String value) {
            this.state = value;
        }

        /**
         * Gets the value of the primaryContact property.
         * 
         * @return
         *     possible object is
         *     {@link DTCSDContract.Contract.PrimaryContact }
         *     
         */
        public DTCSDContract.Contract.PrimaryContact getPrimaryContact() {
            return primaryContact;
        }

        /**
         * Sets the value of the primaryContact property.
         * 
         * @param value
         *     allowed object is
         *     {@link DTCSDContract.Contract.PrimaryContact }
         *     
         */
        public void setPrimaryContact(DTCSDContract.Contract.PrimaryContact value) {
            this.primaryContact = value;
        }

        /**
         * Gets the value of the secondaryContact property.
         * 
         * @return
         *     possible object is
         *     {@link DTCSDContract.Contract.SecondaryContact }
         *     
         */
        public DTCSDContract.Contract.SecondaryContact getSecondaryContact() {
            return secondaryContact;
        }

        /**
         * Sets the value of the secondaryContact property.
         * 
         * @param value
         *     allowed object is
         *     {@link DTCSDContract.Contract.SecondaryContact }
         *     
         */
        public void setSecondaryContact(DTCSDContract.Contract.SecondaryContact value) {
            this.secondaryContact = value;
        }

        /**
         * Gets the value of the billingCycle property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBillingCycle() {
            return billingCycle;
        }

        /**
         * Sets the value of the billingCycle property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBillingCycle(String value) {
            this.billingCycle = value;
        }

        /**
         * Gets the value of the paymentTerms property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPaymentTerms() {
            return paymentTerms;
        }

        /**
         * Sets the value of the paymentTerms property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPaymentTerms(String value) {
            this.paymentTerms = value;
        }

        /**
         * Gets the value of the plantCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPlantCode() {
            return plantCode;
        }

        /**
         * Sets the value of the plantCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPlantCode(String value) {
            this.plantCode = value;
        }

        /**
         * Gets the value of the tanNo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTANNo() {
            return tanNo;
        }

        /**
         * Sets the value of the tanNo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTANNo(String value) {
            this.tanNo = value;
        }

        /**
         * Gets the value of the salesDistrict property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSalesDistrict() {
            return salesDistrict;
        }

        /**
         * Sets the value of the salesDistrict property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSalesDistrict(String value) {
            this.salesDistrict = value;
        }

        /**
         * Gets the value of the panNo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPANNo() {
            return panNo;
        }

        /**
         * Sets the value of the panNo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPANNo(String value) {
            this.panNo = value;
        }

        /**
         * Gets the value of the status property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatus() {
            return status;
        }

        /**
         * Sets the value of the status property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatus(String value) {
            this.status = value;
        }


        /**
         * Gets the value of the legacyCustCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLegacyCustCode() {
            return legacyCustCode;
        }

		/**
         * Sets the value of the legacyCustCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLegacyCustCode(String value) {
            this.legacyCustCode = value;
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
         *         &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="PersonName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="ContactNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="Extention" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="Mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="Fax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            "title",
            "personName",
            "email",
            "contactNo",
            "extention",
            "mobile",
            "fax"
        })
        public static class PrimaryContact {

            @XmlElement(name = "Title")
            protected String title;
            @XmlElement(name = "PersonName")
            protected String personName;
            @XmlElement(name = "Email")
            protected String email;
            @XmlElement(name = "ContactNo")
            protected String contactNo;
            @XmlElement(name = "Extention")
            protected String extention;
            @XmlElement(name = "Mobile")
            protected String mobile;
            @XmlElement(name = "Fax")
            protected String fax;

            /**
             * Gets the value of the title property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTitle() {
                return title;
            }

            /**
             * Sets the value of the title property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTitle(String value) {
                this.title = value;
            }

            /**
             * Gets the value of the personName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPersonName() {
                return personName;
            }

            /**
             * Sets the value of the personName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPersonName(String value) {
                this.personName = value;
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
             * Gets the value of the contactNo property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getContactNo() {
                return contactNo;
            }

            /**
             * Sets the value of the contactNo property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setContactNo(String value) {
                this.contactNo = value;
            }

            /**
             * Gets the value of the extention property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getExtention() {
                return extention;
            }

            /**
             * Sets the value of the extention property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setExtention(String value) {
                this.extention = value;
            }

            /**
             * Gets the value of the mobile property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMobile() {
                return mobile;
            }

            /**
             * Sets the value of the mobile property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMobile(String value) {
                this.mobile = value;
            }

            /**
             * Gets the value of the fax property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFax() {
                return fax;
            }

            /**
             * Sets the value of the fax property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFax(String value) {
                this.fax = value;
            }

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
         *         &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="PersonName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="ContactNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="Extention" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="Mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="Fax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            "title",
            "personName",
            "email",
            "contactNo",
            "extention",
            "mobile",
            "fax"
        })
        public static class SecondaryContact {

            @XmlElement(name = "Title")
            protected String title;
            @XmlElement(name = "PersonName")
            protected String personName;
            @XmlElement(name = "Email")
            protected String email;
            @XmlElement(name = "ContactNo")
            protected String contactNo;
            @XmlElement(name = "Extention")
            protected String extention;
            @XmlElement(name = "Mobile")
            protected String mobile;
            @XmlElement(name = "Fax")
            protected String fax;

            /**
             * Gets the value of the title property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTitle() {
                return title;
            }

            /**
             * Sets the value of the title property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTitle(String value) {
                this.title = value;
            }

            /**
             * Gets the value of the personName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPersonName() {
                return personName;
            }

            /**
             * Sets the value of the personName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPersonName(String value) {
                this.personName = value;
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
             * Gets the value of the contactNo property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getContactNo() {
                return contactNo;
            }

            /**
             * Sets the value of the contactNo property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setContactNo(String value) {
                this.contactNo = value;
            }

            /**
             * Gets the value of the extention property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getExtention() {
                return extention;
            }

            /**
             * Sets the value of the extention property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setExtention(String value) {
                this.extention = value;
            }

            /**
             * Gets the value of the mobile property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMobile() {
                return mobile;
            }

            /**
             * Sets the value of the mobile property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMobile(String value) {
                this.mobile = value;
            }

            /**
             * Gets the value of the fax property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFax() {
                return fax;
            }

            /**
             * Sets the value of the fax property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFax(String value) {
                this.fax = value;
            }

        }

    }

}
