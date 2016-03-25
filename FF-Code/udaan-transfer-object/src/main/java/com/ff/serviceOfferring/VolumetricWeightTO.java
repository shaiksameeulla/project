/**
 * 
 */
package com.ff.serviceOfferring;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author nkattung
 * 
 */
public class VolumetricWeightTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1966461774521556099L;
	private Double height;
	private Double length;
	private Double breadth;
	private Double volWeight;

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getBreadth() {
		return breadth;
	}

	public void setBreadth(Double breadth) {
		this.breadth = breadth;
	}

	public Double getVolWeight() {
		return volWeight;
	}

	public void setVolWeight(Double volWeight) {
		this.volWeight = volWeight;
	}

}
