package com.ff.domain.coloading;


public class ColoadingTrainContractRateDetailsDO extends CGBaseColaodingDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String trainNo;
	private ColoadingTrainContractDO coloadingTrainContract;
	private Double minChargeableRate;
	private double ratePerKg;
	private Double otherChargesPerKg;
	
	public String getTrainNo() {
		return this.trainNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	

	public ColoadingTrainContractDO getColoadingTrainContract() {
		return coloadingTrainContract;
	}

	public void setColoadingTrainContract(
			ColoadingTrainContractDO coloadingTrainContract) {
		this.coloadingTrainContract = coloadingTrainContract;
	}

	public Double getMinChargeableRate() {
		return this.minChargeableRate;
	}

	public void setMinChargeableRate(Double minChargeableRate) {
		this.minChargeableRate = minChargeableRate;
	}

	public double getRatePerKg() {
		return this.ratePerKg;
	}

	public void setRatePerKg(double ratePerKg) {
		this.ratePerKg = ratePerKg;
	}

	public Double getOtherChargesPerKg() {
		return this.otherChargesPerKg;
	}

	public void setOtherChargesPerKg(Double otherChargesPerKg) {
		this.otherChargesPerKg = otherChargesPerKg;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		ColoadingTrainContractRateDetailsDO other = (ColoadingTrainContractRateDetailsDO) obj;
		if (trainNo == null) {
			if (other.trainNo != null)
				return false;
		} else if (!trainNo.equals(other.trainNo))
			return false;
		return true;
	}

	

}
