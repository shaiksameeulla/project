package com.ff.tracking;

import java.util.List;

import com.ff.loadmanagement.LoadMovementTO;
import com.ff.loadmanagement.ManifestTO;

public class TrackingGatepassTO extends TrackingBaseTO {

	private static final long serialVersionUID = 1L;

	private LoadMovementTO loadMovementTO;
	private List<ManifestTO> manifestTOs;
	private ManifestTO manifestTO;
	private List<String> remarks;
	private String remark;
	private int bagsDispatch;
	private int bagsReceive;
	private String dispatchDate;
	private String receiveDate;
	private String dispatchWt;
	private String receiveWt;
	/**
	 * @return the bagsDispatch
	 */
	public int getBagsDispatch() {
		return bagsDispatch;
	}

	/**
	 * @param bagsDispatch the bagsDispatch to set
	 */
	public void setBagsDispatch(int bagsDispatch) {
		this.bagsDispatch = bagsDispatch;
	}

	/**
	 * @return the bagsReceive
	 */
	public int getBagsReceive() {
		return bagsReceive;
	}

	/**
	 * @param bagsReceive the bagsReceive to set
	 */
	public void setBagsReceive(int bagsReceive) {
		this.bagsReceive = bagsReceive;
	}

	/**
	 * @return the loadMovementTO
	 */
	public LoadMovementTO getLoadMovementTO() {
		return loadMovementTO;
	}

	/**
	 * @param loadMovementTO the loadMovementTO to set
	 */
	public void setLoadMovementTO(LoadMovementTO loadMovementTO) {
		this.loadMovementTO = loadMovementTO;
	}

	/**
	 * @return the manifestTOs
	 */
	public List<ManifestTO> getManifestTOs() {
		return manifestTOs;
	}

	/**
	 * @param manifestTOs the manifestTOs to set
	 */
	public void setManifestTOs(List<ManifestTO> manifestTOs) {
		this.manifestTOs = manifestTOs;
	}

	/**
	 * @return the remarks
	 */
	public List<String> getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(List<String> remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the manifestTO
	 */
	public ManifestTO getManifestTO() {
		return manifestTO;
	}

	/**
	 * @param manifestTO the manifestTO to set
	 */
	public void setManifestTO(ManifestTO manifestTO) {
		this.manifestTO = manifestTO;
	}

	/**
	 * @return the dispatchDate
	 */
	public String getDispatchDate() {
		return dispatchDate;
	}

	/**
	 * @param dispatchDate the dispatchDate to set
	 */
	public void setDispatchDate(String dispatchDate) {
		this.dispatchDate = dispatchDate;
	}

	/**
	 * @return the receiveDate
	 */
	public String getReceiveDate() {
		return receiveDate;
	}

	/**
	 * @param receiveDate the receiveDate to set
	 */
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getDispatchWt() {
		return dispatchWt;
	}

	public void setDispatchWt(String dispatchWt) {
		this.dispatchWt = dispatchWt;
	}

	public String getReceiveWt() {
		return receiveWt;
	}

	public void setReceiveWt(String receiveWt) {
		this.receiveWt = receiveWt;
	}
}
