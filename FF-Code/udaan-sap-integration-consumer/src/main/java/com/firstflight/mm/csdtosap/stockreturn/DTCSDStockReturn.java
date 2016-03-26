
package com.firstflight.mm.csdtosap.stockreturn;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DT_CSD_StockReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DT_CSD_StockReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StockReturn" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="DocumentDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="MaterialCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="LoggedInPlant" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="StkTransNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="IssueDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="CustNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="StockIssueNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "DT_CSD_StockReturn", propOrder = {
    "stockReturn"
})
public class DTCSDStockReturn {

    @XmlElement(name = "StockReturn")
    protected List<DTCSDStockReturn.StockReturn> stockReturn;

    /**
     * Gets the value of the stockReturn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stockReturn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStockReturn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DTCSDStockReturn.StockReturn }
     * 
     * 
     */
    public List<DTCSDStockReturn.StockReturn> getStockReturn() {
        if (stockReturn == null) {
            stockReturn = new ArrayList<DTCSDStockReturn.StockReturn>();
        }
        return this.stockReturn;
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
     *         &lt;element name="DocumentDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="MaterialCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="LoggedInPlant" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="StkTransNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="IssueDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="CustNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="StockIssueNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "documentDate",
        "quantity",
        "materialCode",
        "loggedInPlant",
        "stkTransNo",
        "issueDate",
        "custNo",
        "timestamp",
        "stockIssueNo"
    })
    public static class StockReturn {

        @XmlElement(name = "DocumentDate")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar documentDate;
        @XmlElement(name = "Quantity")
        protected BigDecimal quantity;
        @XmlElement(name = "MaterialCode")
        protected String materialCode;
        @XmlElement(name = "LoggedInPlant")
        protected String loggedInPlant;
        @XmlElement(name = "StkTransNo")
        protected String stkTransNo;
        @XmlElement(name = "IssueDate")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar issueDate;
        @XmlElement(name = "CustNo")
        protected String custNo;
        @XmlElement(name = "Timestamp")
        protected String timestamp;

        @XmlElement(name = "StockIssueNo")
        protected String stockIssueNo;

		/**
         * Gets the value of the documentDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDocumentDate() {
            return documentDate;
        }

        /**
         * Sets the value of the documentDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDocumentDate(XMLGregorianCalendar value) {
            this.documentDate = value;
        }

        /**
         * Gets the value of the quantity property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getQuantity() {
            return quantity;
        }

        /**
         * Sets the value of the quantity property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setQuantity(BigDecimal value) {
            this.quantity = value;
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
         * Gets the value of the stkTransNo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStkTransNo() {
            return stkTransNo;
        }

        /**
         * Sets the value of the stkTransNo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStkTransNo(String value) {
            this.stkTransNo = value;
        }

        /**
         * Gets the value of the issueDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getIssueDate() {
            return issueDate;
        }

        /**
         * Sets the value of the issueDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setIssueDate(XMLGregorianCalendar value) {
            this.issueDate = value;
        }

        /**
         * Gets the value of the custNo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustNo() {
            return custNo;
        }

        /**
         * Sets the value of the custNo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustNo(String value) {
            this.custNo = value;
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

		/**
         * Gets the value of the stockIssueNo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStockIssueNo() {
            return stockIssueNo;
        }

		/**
         * Sets the value of the stockIssueNo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStockIssueNo(String value) {
            this.stockIssueNo = value;
        }

    }

}
