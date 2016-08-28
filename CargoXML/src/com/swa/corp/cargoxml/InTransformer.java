/**
 * 
 */
package com.swa.corp.cargoxml;

/**
 * @author mohammes
 *
 */
public abstract  class InTransformer implements CargoXMLTransformer {

	@Override
	final public String convertTOXML(Object message) {
		// TODO Auto-generated method stub
		return null;
	}

	public abstract Object convertFromXML(String message);

	

}
