package com.swa.corp.cargoxml;

public class XFZBOutTransformer extends OutTransformer {

	@Override
	public String convertTOXML(Object message) {
		System.out.println("message"+message);
		return "outbound";
	}

	

}
