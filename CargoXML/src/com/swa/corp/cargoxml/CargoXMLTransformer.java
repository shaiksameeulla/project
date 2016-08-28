package com.swa.corp.cargoxml;

public interface CargoXMLTransformer {

	String convertTOXML(Object message);
	Object convertFromXML(String message);
}
