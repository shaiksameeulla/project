package com.swa.corp.cargoxml;

public  class CargoXMLInboundFactroy extends AbstractCargoXmlFactory {
	private CargoXMLInboundFactroy(){
		
	}
	
	private static class SingletonHolder {    
        public static final CargoXMLInboundFactroy instance = new CargoXMLInboundFactroy();
    }    

	public static CargoXMLInboundFactroy getInstance() {    
        return SingletonHolder.instance;    
    } 
	CargoXMLTransformer transformer=null;
	@Override
	CargoXMLTransformer generateTransformer(String messageType) {
		switch(messageType){
			case "XFFM":
				break;
			case "XCSN":
				transformer=new XCSNInTransformer();
		}
		return transformer;
	}

	
	
}
