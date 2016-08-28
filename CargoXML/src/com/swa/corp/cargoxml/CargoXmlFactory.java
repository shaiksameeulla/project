package com.swa.corp.cargoxml;

public  class CargoXmlFactory{
	private CargoXmlFactory(){

	}

	public static CargoXMLTransformer	generateXmlFactory(String transformer,String messageType){
		AbstractCargoXmlFactory factory=null;
		switch(transformer){
		case "INBOUND":
			factory= CargoXMLOutboundFactroy.getInstance();
			
			break;
		case "OUTBOUND":
			factory= CargoXMLInboundFactroy.getInstance();
			break;
			
		}
		
		return factory.generateTransformer(messageType);
	}

}

