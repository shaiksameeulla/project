package com.ff.to.rate;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class RateCalculationOutputTO extends CGBaseTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7489181214848783895L;
	private List<RateComponentTO> components;

	/**
	 * @return the components
	 */
	public List<RateComponentTO> getComponents() {
		return components;
	}

	/**
	 * @param components the components to set
	 */
	public void setComponents(List<RateComponentTO> components) {
		this.components = components;
	}
	
	
}
