/**
 * 
 */
package com.swa.corp.cargoxml;

/**
 * @author mohammes
 *
 */
public abstract class OutTransformer implements CargoXMLTransformer {

	public abstract String convertTOXML(Object message);

	@Override
	final public Object convertFromXML(String message) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
