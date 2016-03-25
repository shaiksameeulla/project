package com.ff.manifest;

import java.util.List;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.tracking.ProcessTO;

/**
 * The Class OutManifestValidate.
 */
public class OutManifestValidate {
	
	/** The consg number. */
	private String consgNumber;
	
	/** The is cons parcel. */
	private String isConsParcel = CommonConstants.NO;
	
	private String isConsAllowed = CommonConstants.NO;
	
	/** The manifest number. */
	private String manifestNumber;

	/** The cn pincode to. */
	private PincodeTO cnPincodeTO;
	
	/** The dest office. */
	private OfficeTO destOffice;
	
	/** The origin office. */
	private OfficeTO originOffice;
	
	/** The dest city to. */
	private CityTO destCityTO;
	
	private CityTO cnDestCityTO;
	
	/** The manifest product map to. */
	private ManifestProductMapTO manifestProductMapTO;
	
	/** The is consg exists. */
	private String isConsgExists;
	
	/** The is cn manifested. */
	private String isCnManifested;
	
	/** The is valid pincode. */
	private String isValidPincode;
	
	/** The is pincode serviceable. */
	private String isPincodeServiceable=CommonConstants.YES;
	
	/** The is allowed product. */
	private String isAllowedProduct;
	
	/** The is cn manifested. */
	private String isCnPartyShifted=CommonConstants.YES;
	
	/** The error msg. */
	private String errorMsg;
	
	/** The manifest direction. */
	private String manifestDirection;
		
	/** The is cn processed from pickup. */
	private String isCNProcessedFromPickup = CommonConstants.NO;
	
	/** Allowed Consg. Manifested Type i.e. "B"(BMS) or "R"(RTO) */
	private String[] allowedConsgManifestedType;
	
	/** The is pincode serv chk req. */
	private String isPincodeServChkReq=CommonConstants.YES;
	
	/** The transhipment city ids. */
	private List<Integer> transhipmentCityIds;
	
	/** The chk trans city pincode serv. */
	private String chkTransCityPincodeServ=CommonConstants.NO;	
	
	/** The is cons in manifestd. */
	private String isConsInManifestd=CommonConstants.NO;
	
	/** The is cons booked. */
	private String isCNBooked;
	
	private String consignmentSeries;
	
	private ConsignmentTypeTO consignmentTypeTO;
	
	/** The consgTO. */
	private ConsignmentTO consgTO;
	
	/** The consignmentModificationTO. */
	private ConsignmentModificationTO consignmentModificationTO;
	
	/** The Child CN Details. */
	private String childCNDetails;
	
	/** The process Details. */
	private ProcessTO updatedProcessFrom;
	
	/** The manifest process code. */
	private String manifestProcessCode;
	
	/** Is Bulk Booked CN. */
	private String isBulkBookedCN = CommonConstants.NO;
	
	
	/**
	 * @return the isBulkBookedCN
	 */
	public String getIsBulkBookedCN() {
		return isBulkBookedCN;
	}

	/**
	 * @param isBulkBookedCN the isBulkBookedCN to set
	 */
	public void setIsBulkBookedCN(String isBulkBookedCN) {
		this.isBulkBookedCN = isBulkBookedCN;
	}

	/**
	 * @return the manifestProcessCode
	 */
	public String getManifestProcessCode() {
		return manifestProcessCode;
	}

	/**
	 * @param manifestProcessCode the manifestProcessCode to set
	 */
	public void setManifestProcessCode(String manifestProcessCode) {
		this.manifestProcessCode = manifestProcessCode;
	}

	/**
	 * @return the updatedProcessFrom
	 */
	public ProcessTO getUpdatedProcessFrom() {
		return updatedProcessFrom;
	}

	/**
	 * @param updatedProcessFrom the updatedProcessFrom to set
	 */
	public void setUpdatedProcessFrom(ProcessTO updatedProcessFrom) {
		this.updatedProcessFrom = updatedProcessFrom;
	}

	/**
	 * @return the childCNDetails
	 */
	public String getChildCNDetails() {
		return childCNDetails;
	}

	/**
	 * @param childCNDetails the childCNDetails to set
	 */
	public void setChildCNDetails(String childCNDetails) {
		this.childCNDetails = childCNDetails;
	}

	/**
	 * @return the consignmentModificationTO
	 */
	public ConsignmentModificationTO getConsignmentModificationTO() {
		return consignmentModificationTO;
	}

	/**
	 * @param consignmentModificationTO the consignmentModificationTO to set
	 */
	public void setConsignmentModificationTO(
			ConsignmentModificationTO consignmentModificationTO) {
		this.consignmentModificationTO = consignmentModificationTO;
	}

	/**
	 * @return the consgTO
	 */
	public ConsignmentTO getConsgTO() {
		return consgTO;
	}

	/**
	 * @param consgTO the consgTO to set
	 */
	public void setConsgTO(ConsignmentTO consgTO) {
		this.consgTO = consgTO;
	}

	/**
	 * To get allowed consg manifested type.
	 *
	 * @return the allowedConsgManifestedType
	 */
	public String[] getAllowedConsgManifestedType() {
		return allowedConsgManifestedType;
	}

	/**
	 * To set allowed consg manifested type.
	 *
	 * @param allowedConsgManifestedType the allowedConsgManifestedType to set
	 */
	public void setAllowedConsgManifestedType(String[] allowedConsgManifestedType) {
		this.allowedConsgManifestedType = allowedConsgManifestedType;
	}
	
	/**
	 * Gets the manifest direction.
	 *
	 * @return the manifest direction
	 */
	public String getManifestDirection() {
		return manifestDirection;
	}
	
	/**
	 * Sets the manifest direction.
	 *
	 * @param manifestDirection the new manifest direction
	 */
	public void setManifestDirection(String manifestDirection) {
		this.manifestDirection = manifestDirection;
	}
	
	/**
	 * Gets the consg number.
	 *
	 * @return the consg number
	 */
	public String getConsgNumber() {
		return consgNumber;
	}
	
	/**
	 * Sets the consg number.
	 *
	 * @param consgNumber the new consg number
	 */
	public void setConsgNumber(String consgNumber) {
		this.consgNumber = consgNumber;
	}
	
	/**
	 * Gets the manifest number.
	 *
	 * @return the manifest number
	 */
	public String getManifestNumber() {
		return manifestNumber;
	}
	
	/**
	 * Sets the manifest number.
	 *
	 * @param manifestNumber the new manifest number
	 */
	public void setManifestNumber(String manifestNumber) {
		this.manifestNumber = manifestNumber;
	}
	
	/**
	 * Gets the checks if is consg exists.
	 *
	 * @return the checks if is consg exists
	 */
	public String getIsConsgExists() {
		return isConsgExists;
	}
	
	/**
	 * Sets the checks if is consg exists.
	 *
	 * @param isConsgExists the new checks if is consg exists
	 */
	public void setIsConsgExists(String isConsgExists) {
		this.isConsgExists = isConsgExists;
	}
	
	/**
	 * Gets the checks if is cn manifested.
	 *
	 * @return the checks if is cn manifested
	 */
	public String getIsCnManifested() {
		return isCnManifested;
	}
	
	/**
	 * Sets the checks if is cn manifested.
	 *
	 * @param isCnManifested the new checks if is cn manifested
	 */
	public void setIsCnManifested(String isCnManifested) {
		this.isCnManifested = isCnManifested;
	}
	
	/**
	 * Gets the cn pincode to.
	 *
	 * @return the cn pincode to
	 */
	public PincodeTO getCnPincodeTO() {
		return cnPincodeTO;
	}
	
	/**
	 * Sets the cn pincode to.
	 *
	 * @param cnPincodeTO the new cn pincode to
	 */
	public void setCnPincodeTO(PincodeTO cnPincodeTO) {
		this.cnPincodeTO = cnPincodeTO;
	}
	
	/**
	 * Gets the error msg.
	 *
	 * @return the error msg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	
	/**
	 * Sets the error msg.
	 *
	 * @param errorMsg the new error msg
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	/**
	 * Gets the dest office.
	 *
	 * @return the dest office
	 */
	public OfficeTO getDestOffice() {
		return destOffice;
	}
	
	/**
	 * Sets the dest office.
	 *
	 * @param destOffice the new dest office
	 */
	public void setDestOffice(OfficeTO destOffice) {
		this.destOffice = destOffice;
	}
	
	/**
	 * Gets the checks if is valid pincode.
	 *
	 * @return the checks if is valid pincode
	 */
	public String getIsValidPincode() {
		return isValidPincode;
	}
	
	/**
	 * Sets the checks if is valid pincode.
	 *
	 * @param isValidPincode the new checks if is valid pincode
	 */
	public void setIsValidPincode(String isValidPincode) {
		this.isValidPincode = isValidPincode;
	}
	
	/**
	 * Gets the dest city to.
	 *
	 * @return the dest city to
	 */
	public CityTO getDestCityTO() {
		return destCityTO;
	}
	
	/**
	 * Sets the dest city to.
	 *
	 * @param destCityTO the new dest city to
	 */
	public void setDestCityTO(CityTO destCityTO) {
		this.destCityTO = destCityTO;
	}
	
	/**
	 * Gets the manifest product map to.
	 *
	 * @return the manifest product map to
	 */
	public ManifestProductMapTO getManifestProductMapTO() {
		return manifestProductMapTO;
	}
	
	/**
	 * Sets the manifest product map to.
	 *
	 * @param manifestProductMapTO the new manifest product map to
	 */
	public void setManifestProductMapTO(ManifestProductMapTO manifestProductMapTO) {
		this.manifestProductMapTO = manifestProductMapTO;
	}
	
	/**
	 * Gets the checks if is allowed product.
	 *
	 * @return the checks if is allowed product
	 */
	public String getIsAllowedProduct() {
		return isAllowedProduct;
	}
	
	/**
	 * Sets the checks if is allowed product.
	 *
	 * @param isAllowedProduct the new checks if is allowed product
	 */
	public void setIsAllowedProduct(String isAllowedProduct) {
		this.isAllowedProduct = isAllowedProduct;
	}
	
	/**
	 * Gets the origin office.
	 *
	 * @return the origin office
	 */
	public OfficeTO getOriginOffice() {
		return originOffice;
	}
	
	/**
	 * Sets the origin office.
	 *
	 * @param originOffice the new origin office
	 */
	public void setOriginOffice(OfficeTO originOffice) {
		this.originOffice = originOffice;
	}
	
	/**
	 * Gets the checks if is pincode serviceable.
	 *
	 * @return the checks if is pincode serviceable
	 */
	public String getIsPincodeServiceable() {
		return isPincodeServiceable;
	}
	
	/**
	 * Sets the checks if is pincode serviceable.
	 *
	 * @param isPincodeServiceable the new checks if is pincode serviceable
	 */
	public void setIsPincodeServiceable(String isPincodeServiceable) {
		this.isPincodeServiceable = isPincodeServiceable;
	}

	/**
	 * Gets the checks if is cn processed from pickup.
	 *
	 * @return the isCNProcessedFromPickup
	 */
	public String getIsCNProcessedFromPickup() {
		return isCNProcessedFromPickup;
	}

	/**
	 * Sets the checks if is cn processed from pickup.
	 *
	 * @param isCNProcessedFromPickup the isCNProcessedFromPickup to set
	 */
	public void setIsCNProcessedFromPickup(String isCNProcessedFromPickup) {
		this.isCNProcessedFromPickup = isCNProcessedFromPickup;
	}

	/**
	 * Gets the checks if is pincode serv chk req.
	 *
	 * @return the isPincodeServChkReq
	 */
	public String getIsPincodeServChkReq() {
		return isPincodeServChkReq;
	}

	/**
	 * Sets the checks if is pincode serv chk req.
	 *
	 * @param isPincodeServChkReq the isPincodeServChkReq to set
	 */
	public void setIsPincodeServChkReq(String isPincodeServChkReq) {
		this.isPincodeServChkReq = isPincodeServChkReq;
	}

	/**
	 * Gets the transhipment city ids.
	 *
	 * @return the transhipmentCityIds
	 */
	public List<Integer> getTranshipmentCityIds() {
		return transhipmentCityIds;
	}

	/**
	 * Sets the transhipment city ids.
	 *
	 * @param transhipmentCityIds the transhipmentCityIds to set
	 */
	public void setTranshipmentCityIds(List<Integer> transhipmentCityIds) {
		this.transhipmentCityIds = transhipmentCityIds;
	}

	/**
	 * Gets the chk trans city pincode serv.
	 *
	 * @return the chkTransCityPincodeServ
	 */
	public String getChkTransCityPincodeServ() {
		return chkTransCityPincodeServ;
	}

	/**
	 * Sets the chk trans city pincode serv.
	 *
	 * @param chkTransCityPincodeServ the chkTransCityPincodeServ to set
	 */
	public void setChkTransCityPincodeServ(String chkTransCityPincodeServ) {
		this.chkTransCityPincodeServ = chkTransCityPincodeServ;
	}

	/**
	 * @return the isConsParcel
	 */
	public String getIsConsParcel() {
		return isConsParcel;
	}

	/**
	 * @param isConsParcel the isConsParcel to set
	 */
	public void setIsConsParcel(String isConsParcel) {
		this.isConsParcel = isConsParcel;
	}

	public String getIsCnPartyShifted() {
		return isCnPartyShifted;
	}

	public void setIsCnPartyShifted(String isCnPartyShifted) {
		this.isCnPartyShifted = isCnPartyShifted;
	}

	/**
	 * @return the isConsInManifestd
	 */
	public String getIsConsInManifestd() {
		return isConsInManifestd;
	}

	/**
	 * @param isConsInManifestd the isConsInManifestd to set
	 */
	public void setIsConsInManifestd(String isConsInManifestd) {
		this.isConsInManifestd = isConsInManifestd;
	}

	public String getIsCNBooked() {
		return isCNBooked;
	}

	public void setIsCNBooked(String isCNBooked) {
		this.isCNBooked = isCNBooked;
	}

	public String getConsignmentSeries() {
		return consignmentSeries;
	}

	public void setConsignmentSeries(String consignmentSeries) {
		this.consignmentSeries = consignmentSeries;
	}

	public ConsignmentTypeTO getConsignmentTypeTO() {
		return consignmentTypeTO;
	}

	public void setConsignmentTypeTO(ConsignmentTypeTO consignmentTypeTO) {
		this.consignmentTypeTO = consignmentTypeTO;
	}

	public CityTO getCnDestCityTO() {
		return cnDestCityTO;
	}

	public void setCnDestCityTO(CityTO cnDestCityTO) {
		this.cnDestCityTO = cnDestCityTO;
	}

	public String getIsConsAllowed() {
		return isConsAllowed;
	}

	public void setIsConsAllowed(String isConsAllowed) {
		this.isConsAllowed = isConsAllowed;
	}	
	
	
}
