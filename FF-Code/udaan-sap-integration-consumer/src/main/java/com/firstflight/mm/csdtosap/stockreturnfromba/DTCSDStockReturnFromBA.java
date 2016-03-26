
package com.firstflight.mm.csdtosap.stockreturnfromba;

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
 * <p>Java class for DT_CSD_StockReturnFromBA complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DT_CSD_StockReturnFromBA">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StockReturnFromBA" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="FROM_BA_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ITEM_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="TRANS_CREATED_DATE_TIME" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="TRANSFER_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="TO_BRANCH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="QUANTITY" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="SAP_Timestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "DT_CSD_StockReturnFromBA", propOrder = {
    "stockReturnFromBA"
})
public class DTCSDStockReturnFromBA {

    @XmlElement(name = "StockReturnFromBA")
    protected List<DTCSDStockReturnFromBA.StockReturnFromBA> stockReturnFromBA;

    /**
     * Gets the value of the stockReturnFromBA property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stockReturnFromBA property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStockReturnFromBA().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DTCSDStockReturnFromBA.StockReturnFromBA }
     * 
     * 
     */
    public List<DTCSDStockReturnFromBA.StockReturnFromBA> getStockReturnFromBA() {
        if (stockReturnFromBA == null) {
            stockReturnFromBA = new ArrayList<DTCSDStockReturnFromBA.StockReturnFromBA>();
        }
        return this.stockReturnFromBA;
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
     *         &lt;element name="FROM_BA_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ITEM_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="TRANS_CREATED_DATE_TIME" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="TRANSFER_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="TO_BRANCH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="QUANTITY" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="SAP_Timestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "frombacode",
        "itemcode",
        "transcreateddatetime",
        "transfernumber",
        "tobranch",
        "quantity",
        "sapTimestamp",
        "stockIssueNo"
    })
    public static class StockReturnFromBA {

        @XmlElement(name = "FROM_BA_CODE")
        protected String frombacode;
        @XmlElement(name = "ITEM_CODE")
        protected String itemcode;
        @XmlElement(name = "TRANS_CREATED_DATE_TIME")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar transcreateddatetime;
        @XmlElement(name = "TRANSFER_NUMBER")
        protected String transfernumber;
        @XmlElement(name = "TO_BRANCH")
        protected String tobranch;
        @XmlElement(name = "QUANTITY")
        protected BigDecimal quantity;
        @XmlElement(name = "SAP_Timestamp")
        protected String sapTimestamp;

        @XmlElement(name = "StockIssueNo")
        protected String stockIssueNo;

		/**
         * Gets the value of the frombacode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFROMBACODE() {
            return frombacode;
        }

        /**
         * Sets the value of the frombacode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFROMBACODE(String value) {
            this.frombacode = value;
        }

        /**
         * Gets the value of the itemcode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getITEMCODE() {
            return itemcode;
        }

        /**
         * Sets the value of the itemcode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setITEMCODE(String value) {
            this.itemcode = value;
        }

        /**
         * Gets the value of the transcreateddatetime property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getTRANSCREATEDDATETIME() {
            return transcreateddatetime;
        }

        /**
         * Sets the value of the transcreateddatetime property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setTRANSCREATEDDATETIME(XMLGregorianCalendar value) {
            this.transcreateddatetime = value;
        }

        /**
         * Gets the value of the transfernumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTRANSFERNUMBER() {
            return transfernumber;
        }

        /**
         * Sets the value of the transfernumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTRANSFERNUMBER(String value) {
            this.transfernumber = value;
        }

        /**
         * Gets the value of the tobranch property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTOBRANCH() {
            return tobranch;
        }

        /**
         * Sets the value of the tobranch property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTOBRANCH(String value) {
            this.tobranch = value;
        }

        /**
         * Gets the value of the quantity property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getQUANTITY() {
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
        public void setQUANTITY(BigDecimal value) {
            this.quantity = value;
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
