/**
 * 
 */
package com.ff.to.ratemanagement.operations.rateconfiguration;

import java.util.List;

import com.ff.to.ratemanagement.operations.rateconfiguration.BaSpecialDestinationRateTO;

/**
 * @author prmeher
 *
 */
public class SpecialDestinationSlabsTO {

	private String pincode;
	private Integer stateId;
	
	private List<BaSpecialDestinationRateTO> specialSlabs;
	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}
	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	/**
	 * @return the specialSlabs
	 */
	public List<BaSpecialDestinationRateTO> getSpecialSlabs() {
		return specialSlabs;
	}
	/**
	 * @param specialSlabs the specialSlabs to set
	 */
	public void setSpecialSlabs(List<BaSpecialDestinationRateTO> specialSlabs) {
		this.specialSlabs = specialSlabs;
	}
	public Integer getStateId() {
		return stateId;
	}
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	
	
}
