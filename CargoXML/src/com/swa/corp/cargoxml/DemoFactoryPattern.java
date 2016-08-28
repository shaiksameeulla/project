package com.swa.corp.cargoxml;

public class DemoFactoryPattern {
	public static void main(String s[]){
		
		CargoXMLTransformer transformer=CargoXmlFactory.generateXmlFactory("OUTBOUND","XFZB");
		transformer.convertTOXML("message");
		
	}

}
