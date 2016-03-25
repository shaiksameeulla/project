package com.ff.manifest.inmanifest;

import com.ff.geography.PincodeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.InsuredByTO;

/**
 * @author narmdr
 *
 */
public class InBagManifestDetailsParcelTO extends InManifestDetailsTO implements
		Comparable<InBagManifestDetailsParcelTO> {

	private static final long serialVersionUID = 6352045811167571151L;

	private Integer consignmentId;
	private Integer consgOrgOffId;	
	private String consgNumber;
	private Integer numOfPc;
	private String childCn;//cn,wt#cn,wt
	private Double actualWeight;
	private Double finalWeight;
	private Double volWeight;
	private Double length;
	private Double height;
	private Double breadth;
	private String mobileNo;
	private Double declaredValue;
	private String policyNo;
	private String refNo;
	private Double toPayAmt;
	private Double codAmt;//cod/lc amt
	private Integer productId;
	private String lcBankName;
	private Integer consignmentTypeId;
	private Integer consignmentManifestId;
	private Integer priceId;
	private String bookingType;

	private CNContentTO cnContentTO;
	private CNPaperWorksTO cnPaperWorksTO;
	private InsuredByTO insuredByTO;
	private PincodeTO pincodeTO;
	
	private String otherCNContent;

	private Double baAmt;
	
	/**
	 * @return the consignmentId
	 */
	public Integer getConsignmentId() {
		return consignmentId;
	}
	/**
	 * @param consignmentId the consignmentId to set
	 */
	public void setConsignmentId(Integer consignmentId) {
		this.consignmentId = consignmentId;
	}
	/**
	 * @return the consgNumber
	 */
	public String getConsgNumber() {
		return consgNumber;
	}
	/**
	 * @param consgNumber the consgNumber to set
	 */
	public void setConsgNumber(String consgNumber) {
		this.consgNumber = consgNumber;
	}
	/**
	 * @return the numOfPc
	 */
	public Integer getNumOfPc() {
		return numOfPc;
	}
	/**
	 * @param numOfPc the numOfPc to set
	 */
	public void setNumOfPc(Integer numOfPc) {
		this.numOfPc = numOfPc;
	}
	/**
	 * @return the childCn
	 */
	public String getChildCn() {
		return childCn;
	}
	/**
	 * @param childCn the childCn to set
	 */
	public void setChildCn(String childCn) {
		this.childCn = childCn;
	}
	/**
	 * @return the actualWeight
	 */
	public Double getActualWeight() {
		return actualWeight;
	}
	/**
	 * @param actualWeight the actualWeight to set
	 */
	public void setActualWeight(Double actualWeight) {
		this.actualWeight = actualWeight;
	}
	/**
	 * @return the finalWeight
	 */
	public Double getFinalWeight() {
		return finalWeight;
	}
	/**
	 * @param finalWeight the finalWeight to set
	 */
	public void setFinalWeight(Double finalWeight) {
		this.finalWeight = finalWeight;
	}
	/**
	 * @return the volWeight
	 */
	public Double getVolWeight() {
		return volWeight;
	}
	/**
	 * @param volWeight the volWeight to set
	 */
	public void setVolWeight(Double volWeight) {
		this.volWeight = volWeight;
	}
	/**
	 * @return the length
	 */
	public Double getLength() {
		return length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(Double length) {
		this.length = length;
	}
	/**
	 * @return the height
	 */
	public Double getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(Double height) {
		this.height = height;
	}
	/**
	 * @return the breadth
	 */
	public Double getBreadth() {
		return breadth;
	}
	/**
	 * @param breadth the breadth to set
	 */
	public void setBreadth(Double breadth) {
		this.breadth = breadth;
	}
	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}
	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	/**
	 * @return the declaredValue
	 */
	public Double getDeclaredValue() {
		return declaredValue;
	}
	/**
	 * @param declaredValue the declaredValue to set
	 */
	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}
	/**
	 * @return the policyNo
	 */
	public String getPolicyNo() {
		return policyNo;
	}
	/**
	 * @param policyNo the policyNo to set
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	/**
	 * @return the refNo
	 */
	public String getRefNo() {
		return refNo;
	}
	/**
	 * @param refNo the refNo to set
	 */
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	/**
	 * @return the toPayAmt
	 */
	public Double getToPayAmt() {
		return toPayAmt;
	}
	/**
	 * @param toPayAmt the toPayAmt to set
	 */
	public void setToPayAmt(Double toPayAmt) {
		this.toPayAmt = toPayAmt;
	}
	/**
	 * @return the codAmt
	 */
	public Double getCodAmt() {
		return codAmt;
	}
	/**
	 * @param codAmt the codAmt to set
	 */
	public void setCodAmt(Double codAmt) {
		this.codAmt = codAmt;
	}
	/**
	 * @return the cnContentTO
	 */
	public CNContentTO getCnContentTO() {
		return cnContentTO;
	}
	/**
	 * @param cnContentTO the cnContentTO to set
	 */
	public void setCnContentTO(CNContentTO cnContentTO) {
		this.cnContentTO = cnContentTO;
	}
	/**
	 * @return the cnPaperWorksTO
	 */
	public CNPaperWorksTO getCnPaperWorksTO() {
		return cnPaperWorksTO;
	}
	/**
	 * @param cnPaperWorksTO the cnPaperWorksTO to set
	 */
	public void setCnPaperWorksTO(CNPaperWorksTO cnPaperWorksTO) {
		this.cnPaperWorksTO = cnPaperWorksTO;
	}
	/**
	 * @return the insuredByTO
	 */
	public InsuredByTO getInsuredByTO() {
		return insuredByTO;
	}
	/**
	 * @param insuredByTO the insuredByTO to set
	 */
	public void setInsuredByTO(InsuredByTO insuredByTO) {
		this.insuredByTO = insuredByTO;
	}
	/**
	 * @return the pincodeTO
	 */
	public PincodeTO getPincodeTO() {
		return pincodeTO;
	}
	/**
	 * @param pincodeTO the pincodeTO to set
	 */
	public void setPincodeTO(PincodeTO pincodeTO) {
		this.pincodeTO = pincodeTO;
	}
	/**
	 * @return the productId
	 */
	public Integer getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	/**
	 * @return the consignmentTypeId
	 */
	public Integer getConsignmentTypeId() {
		return consignmentTypeId;
	}
	/**
	 * @param consignmentTypeId the consignmentTypeId to set
	 */
	public void setConsignmentTypeId(Integer consignmentTypeId) {
		this.consignmentTypeId = consignmentTypeId;
	}
	/**
	 * @return the consignmentManifestId
	 */
	public Integer getConsignmentManifestId() {
		return consignmentManifestId;
	}
	/**
	 * @param consignmentManifestId the consignmentManifestId to set
	 */
	public void setConsignmentManifestId(Integer consignmentManifestId) {
		this.consignmentManifestId = consignmentManifestId;
	}
	/**
	 * @return the priceId
	 */
	public Integer getPriceId() {
		return priceId;
	}
	/**
	 * @param priceId the priceId to set
	 */
	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}
	/**
	 * @return the bookingType
	 */
	public String getBookingType() {
		return bookingType;
	}
	/**
	 * @param bookingType the bookingType to set
	 */
	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}
	
	@Override
	public int compareTo(InBagManifestDetailsParcelTO detailsTO) {
		return this.getConsignmentId().compareTo(detailsTO.getConsignmentId());
	}
	/**
	 * @return the consgOrgOffId
	 */
	public Integer getConsgOrgOffId() {
		return consgOrgOffId;
	}
	/**
	 * @param consgOrgOffId the consgOrgOffId to set
	 */
	public void setConsgOrgOffId(Integer consgOrgOffId) {
		this.consgOrgOffId = consgOrgOffId;
	}
	/**
	 * @return the otherCNContent
	 */
	public String getOtherCNContent() {
		return otherCNContent;
	}
	/**
	 * @param otherCNContent the otherCNContent to set
	 */
	public void setOtherCNContent(String otherCNContent) {
		this.otherCNContent = otherCNContent;
	}
	/**
	 * @return the lcBankName
	 */
	public String getLcBankName() {
		return lcBankName;
	}
	/**
	 * @param lcBankName the lcBankName to set
	 */
	public void setLcBankName(String lcBankName) {
		this.lcBankName = lcBankName;
	}
	/**
	 * @return the baAmt
	 */
	public Double getBaAmt() {
		return baAmt;
	}
	/**
	 * @param baAmt the baAmt to set
	 */
	public void setBaAmt(Double baAmt) {
		this.baAmt = baAmt;
	}
	
	
	//private Double chargeableWeight;
	//private String otherCNContent;
	
	/*private Integer destPincodeId;
	private String destinationPincode;*/
	
	/*private String cnContents;
	private Integer cnContentId;
	private String cnContentCode;
	private String cnContentName;*/
	//private String cnContentOther;
	
	/*private Integer paperWorkId;
	private String cnPaperWork;
	private String paperRefNum;*/
	//private Integer insuredById;


	/*private Integer numOfPieces;
	private Double weight;
	private Double volWeight;
	private Double chargebaleWeight;

	private Integer moblieNumbers;
	private String content ;
	private String contentIds ;
	private Integer declaredValue ;
	private Integer paperWork ;
	private Integer paperWorkIds ;
	
	private String insuredBy ;
	private String policyNumbers ;
	private String refrenceNumbers ;
	private String remarks ;
	
	private Integer codAmount ;
	private Integer toPayAmt ;*/
}
