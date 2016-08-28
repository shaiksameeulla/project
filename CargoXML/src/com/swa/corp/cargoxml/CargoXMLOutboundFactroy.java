package com.swa.corp.cargoxml;

public  class CargoXMLOutboundFactroy extends AbstractCargoXmlFactory {

	private CargoXMLOutboundFactroy(){

	}
	private static class SingletonHolder {    
        public static final CargoXMLOutboundFactroy instance = new CargoXMLOutboundFactroy();
    }    

    public static CargoXMLOutboundFactroy getInstance() {    
        return SingletonHolder.instance;    
    }    
	
	
	CargoXMLTransformer transformer=null;
	CargoXMLTransformer generateTransformer(String messageType) {
		switch(messageType){
		case "XFFM":
			break;
		case "XFZB":
			transformer=new XFZBOutTransformer();
		}
		return transformer;
	}

}
