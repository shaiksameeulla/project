package com.ff.domain.consignment;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.tracking.ProcessDO;

public class ConsignmentDOXDO extends CGFactDO {

	private static final long serialVersionUID = -1277125712275929468L;

	private Integer consgId;
	private String consgNo;
	private PincodeDO destPincodeId;
	private Double finalWeight;
	private ProcessDO updatedProcess;
	private ConsignmentTypeDO consgType;
	private Double actualWeight;
	private ConsigneeConsignorDO consignee;
	private String consgStatus = "B";
	private Double lcAmount;
	private String lcBankName;
	private Double codAmt;

	/**
	 * @return the codAmt
	 */
	public Double getCodAmt() {
		return codAmt;
	}

	/**
	 * @param codAmt
	 *            the codAmt to set
	 */
	public void setCodAmt(Double codAmt) {
		this.codAmt = codAmt;
	}

	/**
	 * @return the lcAmount
	 */
	public Double getLcAmount() {
		return lcAmount;
	}

	/**
	 * @param lcAmount
	 *            the lcAmount to set
	 */
	public void setLcAmount(Double lcAmount) {
		this.lcAmount = lcAmount;
	}

	/**
	 * @return the lcBankName
	 */
	public String getLcBankName() {
		return lcBankName;
	}

	/**
	 * @param lcBankName
	 *            the lcBankName to set
	 */
	public void setLcBankName(String lcBankName) {
		this.lcBankName = lcBankName;
	}

	public Integer getConsgId() {
		return consgId;
	}

	public void setConsgId(Integer consgId) {
		this.consgId = consgId;
	}

	public String getConsgNo() {
		return consgNo;
	}

	public void setConsgNo(String consgNo) {
		this.consgNo = consgNo;
	}

	public PincodeDO getDestPincodeId() {
		return destPincodeId;
	}

	public void setDestPincodeId(PincodeDO destPincodeId) {
		this.destPincodeId = destPincodeId;
	}

	public Double getFinalWeight() {
		return finalWeight;
	}

	public void setFinalWeight(Double finalWeight) {
		this.finalWeight = finalWeight;
	}

	public ProcessDO getUpdatedProcess() {
		return updatedProcess;
	}

	public void setUpdatedProcess(ProcessDO updatedProcess) {
		this.updatedProcess = updatedProcess;
	}

	public ConsignmentTypeDO getConsgType() {
		return consgType;
	}

	public void setConsgType(ConsignmentTypeDO consgType) {
		this.consgType = consgType;
	}

	public Double getActualWeight() {
		return actualWeight;
	}

	public void setActualWeight(Double actualWeight) {
		this.actualWeight = actualWeight;
	}

	public ConsigneeConsignorDO getConsignee() {
		return consignee;
	}

	public void setConsignee(ConsigneeConsignorDO consignee) {
		this.consignee = consignee;
	}

	public String getConsgStatus() {
		return consgStatus;
	}

	public void setConsgStatus(String consgStatus) {
		this.consgStatus = consgStatus;
	}
}
