/*
 * @author soagarwa
 */
package com.capgemini.lbs.framework.utils;

import net.sf.json.JSONSerializer;

/**
 * The Class CGJasonConverter.
 *
 * @author soagarwa
 */
public class CGJasonConverter {

	/** The serializer. */
	public static JSONSerializer serializer;

	/**
	 * Instantiates a new CG jason converter.
	 */
	private CGJasonConverter() {
		/* Created to defeat instantiation. */

	}

	/* Will create a singleton object of JSONSerializer */
	/**
	 * Gets the json object.
	 *
	 * @return the json object
	 */
	public static JSONSerializer getJsonObject() {
		if (serializer == null) {
			serializer = new JSONSerializer();
		}
		return serializer;
	}
}
