package com.ff.domain.mec;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * @author hkansagr
 */

public class BcunGLMasterDO extends CGMasterDO {

	private static final long serialVersionUID = 1L;

	/** primary key */
	private Integer glId;
	
	/** The GL code. */
	private String glCode;
	
	/** The GL description. */
	private String glDesc;
	
	/** FK with payment mode  */
	private Integer paymentModeId;
	
	/** The nature of GL. */
	private String nature;
	
	/** The given GL is Employee GL. */
	private String isEmpGL;
	
	/** The given GL is Co-loader GL. */
	private String isCoLoaderGL;
	
	/** The given GL is Octroi GL. */
	private String isOctroiGL;

	/** The given GL is Bank GL. */
	private String isBankGL;
		
	
	/**
	 * @return the isBankGL
	 */
	public String getIsBankGL() {
		return isBankGL;
	}

	/**
	 * @param isBankGL the isBankGL to set
	 */
	public void setIsBankGL(String isBankGL) {
		this.isBankGL = isBankGL;
	}

	/**
	 * @return the glId
	 */
	public Integer getGlId() {
		return glId;
	}

	/**
	 * @param glId the glId to set
	 */
	public void setGlId(Integer glId) {
		this.glId = glId;
	}

	/**
	 * @return the glCode
	 */
	public String getGlCode() {
		return glCode;
	}

	/**
	 * @param glCode the glCode to set
	 */
	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}

	/**
	 * @return the glDesc
	 */
	public String getGlDesc() {
		return glDesc;
	}

	/**
	 * @param glDesc the glDesc to set
	 */
	public void setGlDesc(String glDesc) {
		this.glDesc = glDesc;
	}

	
	/**
	 * @return the paymentModeId
	 */
	public Integer getPaymentModeId() {
		return paymentModeId;
	}

	/**
	 * @param paymentModeId the paymentModeId to set
	 */
	public void setPaymentModeId(Integer paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	/**
	 * @return the nature
	 */
	public String getNature() {
		return nature;
	}

	/**
	 * @param nature the nature to set
	 */
	public void setNature(String nature) {
		this.nature = nature;
	}

	/**
	 * @return the isEmpGL
	 */
	public String getIsEmpGL() {
		return isEmpGL;
	}

	/**
	 * @param isEmpGL the isEmpGL to set
	 */
	public void setIsEmpGL(String isEmpGL) {
		this.isEmpGL = isEmpGL;
	}

	/**
	 * @return the isCoLoaderGL
	 */
	public String getIsCoLoaderGL() {
		return isCoLoaderGL;
	}

	/**
	 * @param isCoLoaderGL the isCoLoaderGL to set
	 */
	public void setIsCoLoaderGL(String isCoLoaderGL) {
		this.isCoLoaderGL = isCoLoaderGL;
	}

	/**
	 * @return the isOctroiGL
	 */
	public String getIsOctroiGL() {
		return isOctroiGL;
	}

	/**
	 * @param isOctroiGL the isOctroiGL to set
	 */
	public void setIsOctroiGL(String isOctroiGL) {
		this.isOctroiGL = isOctroiGL;
	}
	
}
