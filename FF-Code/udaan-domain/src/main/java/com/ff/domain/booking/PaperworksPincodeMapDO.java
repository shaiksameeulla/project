package com.ff.domain.booking;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;

public class PaperworksPincodeMapDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -771554666595283411L;
			
	private Integer paperworksPincodeMapId;
	private BookingTypeDO bookingTypeId;
	private PincodeDO pincodeId;
	private CNPaperWorksDO cnPaperWorkId;
	private double declaredValue;
	private String isRefNoMandatory;
	private String isPaperworkMandatory;
	
	public Integer getPaperworksPincodeMapId() {
		return paperworksPincodeMapId;
	}
	public void setPaperworksPincodeMapId(Integer paperworksPincodeMapId) {
		this.paperworksPincodeMapId = paperworksPincodeMapId;
	}
	public BookingTypeDO getBookingTypeId() {
		return bookingTypeId;
	}
	public void setBookingTypeId(BookingTypeDO bookingTypeId) {
		this.bookingTypeId = bookingTypeId;
	}
	public PincodeDO getPincodeId() {
		return pincodeId;
	}
	public void setPincodeId(PincodeDO pincodeId) {
		this.pincodeId = pincodeId;
	}
	public CNPaperWorksDO getCnPaperWorkId() {
		return cnPaperWorkId;
	}
	public void setCnPaperWorkId(CNPaperWorksDO cnPaperWorkId) {
		this.cnPaperWorkId = cnPaperWorkId;
	}
	public double getDeclaredValue() {
		return declaredValue;
	}
	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}
	public String getIsRefNoMandatory() {
		return isRefNoMandatory;
	}
	public void setIsRefNoMandatory(String isRefNoMandatory) {
		this.isRefNoMandatory = isRefNoMandatory;
	}
	public String getIsPaperworkMandatory() {
		return isPaperworkMandatory;
	}
	public void setIsPaperworkMandatory(String isPaperworkMandatory) {
		this.isPaperworkMandatory = isPaperworkMandatory;
	}
}
