package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunPaperworkPincodeMapDO extends CGMasterDO {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -771554666595283411L;
			
	private Integer paperworksPincodeMapId;
	private Integer pincodeId;
	private Integer cnPaperWorkId;
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
	public Integer getPincodeId() {
		return pincodeId;
	}
	/**
	 * @param pincodeId the pincodeId to set
	 */
	public void setPincodeId(Integer pincodeId) {
		this.pincodeId = pincodeId;
	}
	/**
	 * @return the cnPaperWorkId
	 */
	public Integer getCnPaperWorkId() {
		return cnPaperWorkId;
	}
	/**
	 * @param cnPaperWorkId the cnPaperWorkId to set
	 */
	public void setCnPaperWorkId(Integer cnPaperWorkId) {
		this.cnPaperWorkId = cnPaperWorkId;
	}
	
	
}
