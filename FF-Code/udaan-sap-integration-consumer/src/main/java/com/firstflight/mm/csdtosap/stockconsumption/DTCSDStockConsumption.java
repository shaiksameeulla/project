package com.firstflight.mm.csdtosap.stockconsumption;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for DT_CSD_StockConsumption complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="DT_CSD_StockConsumption">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StockConsumption" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="BookingDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="Material" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="BookingDocNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="LoggedInPlant" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="CustomerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="TransactionNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "DT_CSD_StockConsumption", propOrder = { "stockConsumption" })
public class DTCSDStockConsumption {

	@XmlElement(name = "StockConsumption")
	protected List<DTCSDStockConsumption.StockConsumption> stockConsumption;

	/**
	 * Gets the value of the stockConsumption property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the stockConsumption property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getStockConsumption().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link DTCSDStockConsumption.StockConsumption }
	 * 
	 * 
	 */
	public List<DTCSDStockConsumption.StockConsumption> getStockConsumption() {
		if (stockConsumption == null) {
			stockConsumption = new ArrayList<DTCSDStockConsumption.StockConsumption>();
		}
		return this.stockConsumption;
	}

	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="BookingDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
	 *         &lt;element name="Material" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
	 *         &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
	 *         &lt;element name="BookingDocNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
	 *         &lt;element name="LoggedInPlant" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
	 *         &lt;element name="CustomerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
	 *         &lt;element name="TransactionNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "bookingDate", "material", "quantity",
			"bookingDocNo", "loggedInPlant", "customerCode", "transactionNo" })
	public static class StockConsumption {

		@XmlElement(name = "BookingDate")
		@XmlSchemaType(name = "date")
		protected XMLGregorianCalendar bookingDate;
		@XmlElement(name = "Material")
		protected String material;
		@XmlElement(name = "Quantity")
		protected String quantity;
		@XmlElement(name = "BookingDocNo")
		protected String bookingDocNo;
		@XmlElement(name = "LoggedInPlant")
		protected String loggedInPlant;
		@XmlElement(name = "CustomerCode")
		protected String customerCode;
		@XmlElement(name = "TransactionNo")
		protected String transactionNo;

		/**
		 * Gets the value of the bookingDate property.
		 * 
		 * @return possible object is {@link XMLGregorianCalendar }
		 * 
		 */
		public XMLGregorianCalendar getBookingDate() {
			return bookingDate;
		}

		/**
		 * Sets the value of the bookingDate property.
		 * 
		 * @param value
		 *            allowed object is {@link XMLGregorianCalendar }
		 * 
		 */
		public void setBookingDate(XMLGregorianCalendar value) {
			this.bookingDate = value;
		}

		/**
		 * Gets the value of the material property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getMaterial() {
			return material;
		}

		/**
		 * Sets the value of the material property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setMaterial(String value) {
			this.material = value;
		}

		/**
		 * Gets the value of the quantity property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getQuantity() {
			return quantity;
		}

		/**
		 * Sets the value of the quantity property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setQuantity(String value) {
			this.quantity = value;
		}

		/**
		 * Gets the value of the bookingDocNo property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getBookingDocNo() {
			return bookingDocNo;
		}

		/**
		 * Sets the value of the bookingDocNo property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setBookingDocNo(String value) {
			this.bookingDocNo = value;
		}

		/**
		 * Gets the value of the loggedInPlant property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getLoggedInPlant() {
			return loggedInPlant;
		}

		/**
		 * Sets the value of the loggedInPlant property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setLoggedInPlant(String value) {
			this.loggedInPlant = value;
		}

		/**
		 * Gets the value of the customerCode property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getCustomerCode() {
			return customerCode;
		}

		/**
		 * Sets the value of the customerCode property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setCustomerCode(String value) {
			this.customerCode = value;
		}

		/**
		 * Gets the value of the transactionNo property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getTransactionNo() {
			return transactionNo;
		}

		/**
		 * Sets the value of the transactionNo property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setTransactionNo(String value) {
			this.transactionNo = value;
		}

	}

}
