package com.swa.corp.cargoxml;

public class XCSNInTransformer extends InTransformer {


	@Override
	public Object convertFromXML(String message) {
		System.out.println("Outbound"+message);
		return "message";
	}

}
