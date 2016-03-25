/**
 * 
 */
package com.ff.coloading;

import org.apache.commons.lang.StringUtils;

/**
 * @author Indrajeet Sawarkar
 * 
 */
public class TrainDetailsTO  implements Comparable<TrainDetailsTO>{

	private Integer id;
	private String trainNo;
	private Double minChargeableRate;
	private Double ratePerKG;
	private Double otherChargesPerKG;
	private char storeStatus = 'T';
	private Integer createdBy ;
	private String createdDate;

	

	public String getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public Double getMinChargeableRate() {
		return minChargeableRate;
	}

	public void setMinChargeableRate(Double minChargeableRate) {
		this.minChargeableRate = minChargeableRate;
	}

	public Double getRatePerKG() {
		return ratePerKG;
	}

	public void setRatePerKG(Double ratePerKG) {
		this.ratePerKG = ratePerKG;
	}

	public Double getOtherChargesPerKG() {
		return otherChargesPerKG;
	}

	public void setOtherChargesPerKG(Double otherChargesPerKG) {
		this.otherChargesPerKG = otherChargesPerKG;
	}

	public char getStoreStatus() {
		return storeStatus;
	}

	public void setStoreStatus(char storeStatus) {
		this.storeStatus = storeStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((trainNo == null) ? 0 : trainNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrainDetailsTO other = (TrainDetailsTO) obj;
		if (trainNo == null) {
			if (other.trainNo != null)
				return false;
		} else if (!trainNo.equals(other.trainNo))
			return false;
		return true;
	}

	@Override
	public int compareTo(TrainDetailsTO detailsTO) {
		int returnVal = 0;
		if (StringUtils.isNotEmpty(trainNo)) {
			returnVal = this.trainNo.compareTo(detailsTO.getTrainNo());
		}
		return returnVal;
	}

	

	
	
}
