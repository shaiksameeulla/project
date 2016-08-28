package com.swa.corp.cargoxml;

public abstract class AbstractCargoXmlFactory {

	abstract CargoXMLTransformer generateTransformer(String messageType);

}
