
package com.firstflight.hr.csdtosap.emppickndelivercount;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DT_CSD_EmpPickNDeliverCount complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DT_CSD_EmpPickNDeliverCount">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EMP_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FROM_DATE" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="TO_DATE" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="RECORD_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COUNT" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DT_CSD_EmpPickNDeliverCount", propOrder = {
    "empnumber",
    "fromdate",
    "todate",
    "recordtype",
    "count"
})
public class DTCSDEmpPickNDeliverCount {

    @XmlElement(name = "EMP_NUMBER")
    protected String empnumber;
    @XmlElement(name = "FROM_DATE")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fromdate;
    @XmlElement(name = "TO_DATE")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar todate;
    @XmlElement(name = "RECORD_TYPE")
    protected String recordtype;
    @XmlElement(name = "COUNT")
    protected BigInteger count;

    /**
     * Gets the value of the empnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMPNUMBER() {
        return empnumber;
    }

    /**
     * Sets the value of the empnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMPNUMBER(String value) {
        this.empnumber = value;
    }

    /**
     * Gets the value of the fromdate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFROMDATE() {
        return fromdate;
    }

    /**
     * Sets the value of the fromdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFROMDATE(XMLGregorianCalendar value) {
        this.fromdate = value;
    }

    /**
     * Gets the value of the todate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTODATE() {
        return todate;
    }

    /**
     * Sets the value of the todate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTODATE(XMLGregorianCalendar value) {
        this.todate = value;
    }

    /**
     * Gets the value of the recordtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRECORDTYPE() {
        return recordtype;
    }

    /**
     * Sets the value of the recordtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRECORDTYPE(String value) {
        this.recordtype = value;
    }

    /**
     * Gets the value of the count property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCOUNT() {
        return count;
    }

    /**
     * Sets the value of the count property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCOUNT(BigInteger value) {
        this.count = value;
    }

}
