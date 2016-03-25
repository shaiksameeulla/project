package com.ff.transport;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author narmdr
 *
 */
public class TrainTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;
	
	private Integer trainId;
	private String trainCode;
	private String trainName;
	private String trainNumber;	

	public Integer getTrainId() {
		return trainId;
	}
	public void setTrainId(Integer trainId) {
		this.trainId = trainId;
	}
	public String getTrainCode() {
		return trainCode;
	}
	public void setTrainCode(String trainCode) {
		this.trainCode = trainCode;
	}
	public String getTrainName() {
		return trainName;
	}
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	public String getTrainNumber() {
		return trainNumber;
	}
	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}
}
