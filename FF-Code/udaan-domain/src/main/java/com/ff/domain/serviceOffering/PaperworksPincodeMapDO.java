package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.geography.PincodeDO;

public class PaperworksPincodeMapDO extends CGMasterDO {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -771554666595283411L;
			
	private Integer paperworksPincodeMapId;
	private PincodeDO pincodeId;
	private CNPaperWorksDO cnPaperWorkId;
	private String status = "A";
	
	
	public Integer getPaperworksPincodeMapId() {
		return paperworksPincodeMapId;
	}
	public void setPaperworksPincodeMapId(Integer paperworksPincodeMapId) {
		this.paperworksPincodeMapId = paperworksPincodeMapId;
	}
	
	
	public CNPaperWorksDO getCnPaperWorkId() {
		return cnPaperWorkId;
	}
	public void setCnPaperWorkId(CNPaperWorksDO cnPaperWorkId) {
		this.cnPaperWorkId = cnPaperWorkId;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the pincodeId
	 */
	public PincodeDO getPincodeId() {
		return pincodeId;
	}
	/**
	 * @param pincodeId the pincodeId to set
	 */
	public void setPincodeId(PincodeDO pincodeId) {
		this.pincodeId = pincodeId;
	}
	
	
	
	
}
