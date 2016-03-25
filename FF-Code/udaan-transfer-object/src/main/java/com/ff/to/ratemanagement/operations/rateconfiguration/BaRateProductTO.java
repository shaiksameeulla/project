/**
 * 
 */
package com.ff.to.ratemanagement.operations.rateconfiguration;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author prmeher
 * 
 */
public class BaRateProductTO extends CGBaseTO {

	private static final long serialVersionUID = 4297632470282697699L;
	private Integer baProductHeaderId;
	private Integer rateProductCategory;
	private String servicedOn;
	private List<BaSlabRateTO> baSlabRateList;
	private List<BaSpecialDestinationRateTO> baSpecialDestinationRateTOList;
	private List<SpecialDestinationSlabsTO> specialSlabsList;
	private String isSave;

	private List<BaRateWeightSlabTO> baRateWeightSlabTOList;

	/**
	 * @return the baRateWeightSlabTOList
	 */
	public List<BaRateWeightSlabTO> getBaRateWeightSlabTOList() {
		return baRateWeightSlabTOList;
	}

	/**
	 * @param baRateWeightSlabTOList
	 *            the baRateWeightSlabTOList to set
	 */
	public void setBaRateWeightSlabTOList(
			List<BaRateWeightSlabTO> baRateWeightSlabTOList) {
		this.baRateWeightSlabTOList = baRateWeightSlabTOList;
	}

	/**
	 * @return the isSave
	 */
	public String getIsSave() {
		return isSave;
	}

	/**
	 * @param isSave
	 *            the isSave to set
	 */
	public void setIsSave(String isSave) {
		this.isSave = isSave;
	}

	/**
	 * @return the baProductHeaderId
	 */
	public Integer getBaProductHeaderId() {
		return baProductHeaderId;
	}

	/**
	 * @return the specialSlabsList
	 */
	public List<SpecialDestinationSlabsTO> getSpecialSlabsList() {
		return specialSlabsList;
	}

	/**
	 * @param specialSlabsList
	 *            the specialSlabsList to set
	 */
	public void setSpecialSlabsList(
			List<SpecialDestinationSlabsTO> specialSlabsList) {
		this.specialSlabsList = specialSlabsList;
	}

	/**
	 * @param baProductHeaderId
	 *            the baProductHeaderId to set
	 */
	public void setBaProductHeaderId(Integer baProductHeaderId) {
		this.baProductHeaderId = baProductHeaderId;
	}

	/**
	 * @return the rateProductCategory
	 */
	public Integer getRateProductCategory() {
		return rateProductCategory;
	}

	/**
	 * @param rateProductCategory
	 *            the rateProductCategory to set
	 */
	public void setRateProductCategory(Integer rateProductCategory) {
		this.rateProductCategory = rateProductCategory;
	}

	/**
	 * @return the servicedOn
	 */
	public String getServicedOn() {
		return servicedOn;
	}

	/**
	 * @param servicedOn
	 *            the servicedOn to set
	 */
	public void setServicedOn(String servicedOn) {
		this.servicedOn = servicedOn;
	}

	/**
	 * @return the baSlabRateList
	 */
	public List<BaSlabRateTO> getBaSlabRateList() {
		return baSlabRateList;
	}

	/**
	 * @param baSlabRateList
	 *            the baSlabRateList to set
	 */
	public void setBaSlabRateList(List<BaSlabRateTO> baSlabRateList) {
		this.baSlabRateList = baSlabRateList;
	}

	/**
	 * @return the baSpecialDestinationRateTOList
	 */
	public List<BaSpecialDestinationRateTO> getBaSpecialDestinationRateTOList() {
		return baSpecialDestinationRateTOList;
	}

	/**
	 * @param baSpecialDestinationRateTOList
	 *            the baSpecialDestinationRateTOList to set
	 */
	public void setBaSpecialDestinationRateTOList(
			List<BaSpecialDestinationRateTO> baSpecialDestinationRateTOList) {
		this.baSpecialDestinationRateTOList = baSpecialDestinationRateTOList;
	}

}
