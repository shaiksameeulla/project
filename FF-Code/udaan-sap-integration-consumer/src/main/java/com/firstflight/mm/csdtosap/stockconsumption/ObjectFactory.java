package com.firstflight.mm.csdtosap.stockconsumption;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the
 * com.firstflight.mm.csdtosap.stockconsumption package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _MTCSDStockConsumption_QNAME = new QName(
			"http://FirstFlight.com/MM/CSDToSAP/StockConsumption",
			"MT_CSD_StockConsumption");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package:
	 * com.firstflight.mm.csdtosap.stockconsumption
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link DTCSDStockConsumption }
	 * 
	 */
	public DTCSDStockConsumption createDTCSDStockConsumption() {
		return new DTCSDStockConsumption();
	}

	/**
	 * Create an instance of {@link DTCSDStockConsumption.StockConsumption }
	 * 
	 */
	public DTCSDStockConsumption.StockConsumption createDTCSDStockConsumptionStockConsumption() {
		return new DTCSDStockConsumption.StockConsumption();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link DTCSDStockConsumption }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://FirstFlight.com/MM/CSDToSAP/StockConsumption", name = "MT_CSD_StockConsumption")
	public JAXBElement<DTCSDStockConsumption> createMTCSDStockConsumption(
			DTCSDStockConsumption value) {
		return new JAXBElement<DTCSDStockConsumption>(
				_MTCSDStockConsumption_QNAME, DTCSDStockConsumption.class,
				null, value);
	}

}
