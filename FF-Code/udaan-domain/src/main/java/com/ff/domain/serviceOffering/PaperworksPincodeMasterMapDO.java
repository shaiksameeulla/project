package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.geography.PincodeMasterDO;

public class PaperworksPincodeMasterMapDO extends CGFactDO{

	private Integer paperworksPincodeMapId;
	private PincodeMasterDO pincodeId;
	private CNPaperWorksDO cnPaperWorkId;
	private String status = "A";
	/**
	 * @return the paperworksPincodeMapId
	 */
	public Integer getPaperworksPincodeMapId() {
		return paperworksPincodeMapId;
	}
	/**
	 * @param paperworksPincodeMapId the paperworksPincodeMapId to set
	 */
	public void setPaperworksPincodeMapId(Integer paperworksPincodeMapId) {
		this.paperworksPincodeMapId = paperworksPincodeMapId;
	}
	/**
	 * @return the pincodeId
	 */
	public PincodeMasterDO getPincodeId() {
		return pincodeId;
	}
	/**
	 * @param pincodeId the pincodeId to set
	 */
	public void setPincodeId(PincodeMasterDO pincodeId) {
		this.pincodeId = pincodeId;
	}
	/**
	 * @return the cnPaperWorkId
	 */
	public CNPaperWorksDO getCnPaperWorkId() {
		return cnPaperWorkId;
	}
	/**
	 * @param cnPaperWorkId the cnPaperWorkId to set
	 */
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
	
	
	
}
