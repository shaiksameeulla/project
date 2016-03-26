
package com.firstflight.mm.csdtosap.goodscancellation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DT_CSD_GoodsCancellation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DT_CSD_GoodsCancellation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GoodsCancellation" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="SAPStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DocDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="Reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="MaterialCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="StkCanDocNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="LoggedInPlant" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="PostingDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
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
@XmlType(name = "DT_CSD_GoodsCancellation", propOrder = {
    "goodsCancellation"
})
public class DTCSDGoodsCancellation {

    @XmlElement(name = "GoodsCancellation")
    protected List<DTCSDGoodsCancellation.GoodsCancellation> goodsCancellation;

    /**
     * Gets the value of the goodsCancellation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the goodsCancellation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGoodsCancellation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DTCSDGoodsCancellation.GoodsCancellation }
     * 
     * 
     */
    public List<DTCSDGoodsCancellation.GoodsCancellation> getGoodsCancellation() {
        if (goodsCancellation == null) {
            goodsCancellation = new ArrayList<DTCSDGoodsCancellation.GoodsCancellation>();
        }
        return this.goodsCancellation;
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
     *         &lt;element name="DocDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="Reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="MaterialCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="StkCanDocNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="LoggedInPlant" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="PostingDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
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
        "docDate",
        "reason",
        "materialCode",
        "quantity",
        "stkCanDocNo",
        "loggedInPlant",
        "postingDate"
    })
    public static class GoodsCancellation {

        @XmlElement(name = "Timestamp")
        protected String timestamp;
        @XmlElement(name = "SAPStatus")
        protected String sapStatus;
        @XmlElement(name = "DocDate")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar docDate;
        @XmlElement(name = "Reason")
        protected String reason;
        @XmlElement(name = "MaterialCode")
        protected String materialCode;
        @XmlElement(name = "Quantity")
        protected String quantity;
        @XmlElement(name = "StkCanDocNo")
        protected String stkCanDocNo;
        @XmlElement(name = "LoggedInPlant")
        protected String loggedInPlant;
        @XmlElement(name = "PostingDate")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar postingDate;

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
         * Gets the value of the docDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDocDate() {
            return docDate;
        }

        /**
         * Sets the value of the docDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDocDate(XMLGregorianCalendar value) {
            this.docDate = value;
        }

        /**
         * Gets the value of the reason property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getReason() {
            return reason;
        }

        /**
         * Sets the value of the reason property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setReason(String value) {
            this.reason = value;
        }

        /**
         * Gets the value of the materialCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMaterialCode() {
            return materialCode;
        }

        /**
         * Sets the value of the materialCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMaterialCode(String value) {
            this.materialCode = value;
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
         * Gets the value of the stkCanDocNo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStkCanDocNo() {
            return stkCanDocNo;
        }

        /**
         * Sets the value of the stkCanDocNo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStkCanDocNo(String value) {
            this.stkCanDocNo = value;
        }

        /**
         * Gets the value of the loggedInPlant property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLoggedInPlant() {
            return loggedInPlant;
        }

        /**
         * Sets the value of the loggedInPlant property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLoggedInPlant(String value) {
            this.loggedInPlant = value;
        }

        /**
         * Gets the value of the postingDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getPostingDate() {
            return postingDate;
        }

        /**
         * Sets the value of the postingDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setPostingDate(XMLGregorianCalendar value) {
            this.postingDate = value;
        }

    }

}
