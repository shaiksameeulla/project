package com.ff.domain.booking;

import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;

/**
 * @author nkattung
 *
 */
public class BookingParcelDO extends BookingDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -842946619955333635L;

	private CNContentDO cnContent;
	private CNPaperWorksDO cnPaperWork;
	private Double declaredValue;
	private Double volumetricWeight;
	private Double height;
	private Double length;
	private Double breath;
	private String others;

	public CNContentDO getCnContent() {
		return cnContent;
	}

	public void setCnContent(CNContentDO cnContent) {
		this.cnContent = cnContent;
	}

	public CNPaperWorksDO getCnPaperWork() {
		return cnPaperWork;
	}

	public void setCnPaperWork(CNPaperWorksDO cnPaperWork) {
		this.cnPaperWork = cnPaperWork;
	}

	public Double getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}

	public Double getVolumetricWeight() {
		return volumetricWeight;
	}

	public void setVolumetricWeight(Double volumetricWeight) {
		this.volumetricWeight = volumetricWeight;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getBreath() {
		return breath;
	}

	public void setBreath(Double breath) {
		this.breath = breath;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

}
