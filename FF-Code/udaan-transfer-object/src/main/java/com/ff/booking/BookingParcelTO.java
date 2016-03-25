package com.ff.booking;

import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;

/**
 * The Class BookingParcelTO.
 */
public class BookingParcelTO extends BookingGridTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1977865728171489955L;
	
	/** The vol weight. */
	private Double volWeight;
	
	/** The height. */
	private Double height;
	
	/** The length. */
	private Double length;
	
	/** The breath. */
	private Double breath;
	
	/** The content. */
	private String content;
	
	/** The if others. */
	private String ifOthers;
	
	/** The pieces. */
	//private Integer pieces;
	
	/** The cn paper works. */
	private CNPaperWorksTO cnPaperWorks;
	
	/** The cn contents. */
	private CNContentTO cnContents;
	
	/** The vol weight dtls. */
	private String volWeightDtls;

	/** The other cn content. */
	private String otherCNContent;
	
	/** The paper work ref no. */
	private String paperWorkRefNo;
	
	/** The cn content id. */
	private Integer cnContentId;
	
	/** The cn paperwork id. */
	private Integer cnPaperworkId;
	
	/** The insured by id. */
	private Integer insuredById;
	
	/** The insured by. */
	private String insuredBy;
	
	/** The policy no. */
	private String policyNo;
	
	/** The dlv time map id. */
	private Integer dlvTimeMapId;
	
	/** The Reference no. */
	private String ReferenceNo;
	
	/** The declared value. */
	private Double declaredValue;
	
	/** The paper work. */
	private String paperWork;
	
	// for tracking
	/** The Insuarance Amount. */
	private Double insAmount;
	private String officeName;
	
	private String stateName;

	// UI
	/** The count. */
	private int count;
	
	/** The num of pieces. */
	private Integer[] numOfPieces = new Integer[count];
	
	/** The cn content codes. */
	private String[] cnContentCodes = new String[count];
	
	/** The policy nos. */
	private String[] policyNos = new String[count];
	
	/** The cn content names. */
	private String[] cnContentNames = new String[count];
	
	/** The cn content others. */
	private String[] cnContentOthers = new String[count];
	
	/** The cn paper work codes. */
	private String[] cnPaperWorkCodes = new String[count];
	
	/** The cn paper work names. */
	private String[] cnPaperWorkNames = new String[count];
	
	/** The insuarance nos. */
	private Integer[] insuaranceNos = new Integer[count];
	
	/** The amounts. */
	private Double[] amounts = new Double[count];
	
	/** The cn content ids. */
	private Integer[] cnContentIds = new Integer[count];
	
	/** The cn paper work ids. */
	private Integer[] cnPaperWorkIds = new Integer[count];
	
	/** The paper ref num. */
	private String[] paperRefNum = new String[count];

	/* (non-Javadoc)
	 * @see com.ff.booking.BookingTO#getVolWeight()
	 */
	public Double getVolWeight() {
		return volWeight;
	}

	/* (non-Javadoc)
	 * @see com.ff.booking.BookingTO#setVolWeight(java.lang.Double)
	 */
	public void setVolWeight(Double volWeight) {
		this.volWeight = volWeight;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public Double getHeight() {
		return height;
	}

	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
	public void setHeight(Double height) {
		this.height = height;
	}

	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public Double getLength() {
		return length;
	}

	/**
	 * Sets the length.
	 *
	 * @param length the new length
	 */
	public void setLength(Double length) {
		this.length = length;
	}

	/**
	 * Gets the breath.
	 *
	 * @return the breath
	 */
	public Double getBreath() {
		return breath;
	}

	/**
	 * Sets the breath.
	 *
	 * @param breath the new breath
	 */
	public void setBreath(Double breath) {
		this.breath = breath;
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Gets the if others.
	 *
	 * @return the if others
	 */
	public String getIfOthers() {
		return ifOthers;
	}

	/**
	 * Sets the if others.
	 *
	 * @param ifOthers the new if others
	 */
	public void setIfOthers(String ifOthers) {
		this.ifOthers = ifOthers;
	}

	/**
	 * Gets the pieces.
	 *
	 * @return the pieces
	 *//*
	public Integer getPieces() {
		return pieces;
	}

	*//**
	 * Sets the pieces.
	 *
	 * @param pieces the new pieces
	 *//*
	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}
*/
	/**
	 * Gets the cn paper works.
	 *
	 * @return the cn paper works
	 */
	public CNPaperWorksTO getCnPaperWorks() {
		return cnPaperWorks;
	}

	/**
	 * Sets the cn paper works.
	 *
	 * @param cnPaperWorks the new cn paper works
	 */
	public void setCnPaperWorks(CNPaperWorksTO cnPaperWorks) {
		this.cnPaperWorks = cnPaperWorks;
	}

	/**
	 * Gets the cn contents.
	 *
	 * @return the cn contents
	 */
	public CNContentTO getCnContents() {
		return cnContents;
	}

	/**
	 * Sets the cn contents.
	 *
	 * @param cnContents the new cn contents
	 */
	public void setCnContents(CNContentTO cnContents) {
		this.cnContents = cnContents;
	}

	/**
	 * Gets the vol weight dtls.
	 *
	 * @return the vol weight dtls
	 */
	public String getVolWeightDtls() {
		return volWeightDtls;
	}

	/**
	 * Sets the vol weight dtls.
	 *
	 * @param volWeightDtls the new vol weight dtls
	 */
	public void setVolWeightDtls(String volWeightDtls) {
		this.volWeightDtls = volWeightDtls;
	}

	/**
	 * Gets the other cn content.
	 *
	 * @return the other cn content
	 */
	public String getOtherCNContent() {
		return otherCNContent;
	}

	/**
	 * Sets the other cn content.
	 *
	 * @param otherCNContent the new other cn content
	 */
	public void setOtherCNContent(String otherCNContent) {
		this.otherCNContent = otherCNContent;
	}

	/**
	 * Gets the paper work ref no.
	 *
	 * @return the paper work ref no
	 */
	public String getPaperWorkRefNo() {
		return paperWorkRefNo;
	}

	/**
	 * Sets the paper work ref no.
	 *
	 * @param paperWorkRefNo the new paper work ref no
	 */
	public void setPaperWorkRefNo(String paperWorkRefNo) {
		this.paperWorkRefNo = paperWorkRefNo;
	}

	/**
	 * Gets the cn content id.
	 *
	 * @return the cn content id
	 */
	public Integer getCnContentId() {
		return cnContentId;
	}

	/**
	 * Sets the cn content id.
	 *
	 * @param cnContentId the new cn content id
	 */
	public void setCnContentId(Integer cnContentId) {
		this.cnContentId = cnContentId;
	}

	/**
	 * Gets the cn paperwork id.
	 *
	 * @return the cn paperwork id
	 */
	public Integer getCnPaperworkId() {
		return cnPaperworkId;
	}

	/**
	 * Sets the cn paperwork id.
	 *
	 * @param cnPaperworkId the new cn paperwork id
	 */
	public void setCnPaperworkId(Integer cnPaperworkId) {
		this.cnPaperworkId = cnPaperworkId;
	}

	/**
	 * Gets the insured by id.
	 *
	 * @return the insured by id
	 */
	public Integer getInsuredById() {
		return insuredById;
	}

	/**
	 * Sets the insured by id.
	 *
	 * @param insuredById the new insured by id
	 */
	public void setInsuredById(Integer insuredById) {
		this.insuredById = insuredById;
	}

	/**
	 * Gets the policy no.
	 *
	 * @return the policy no
	 */
	public String getPolicyNo() {
		return policyNo;
	}

	/**
	 * Sets the policy no.
	 *
	 * @param policyNo the new policy no
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * Gets the dlv time map id.
	 *
	 * @return the dlv time map id
	 */
	public Integer getDlvTimeMapId() {
		return dlvTimeMapId;
	}

	/**
	 * Sets the dlv time map id.
	 *
	 * @param dlvTimeMapId the new dlv time map id
	 */
	public void setDlvTimeMapId(Integer dlvTimeMapId) {
		this.dlvTimeMapId = dlvTimeMapId;
	}

	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Sets the count.
	 *
	 * @param count the new count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * Gets the num of pieces.
	 *
	 * @return the num of pieces
	 */
	public Integer[] getNumOfPieces() {
		return numOfPieces;
	}

	/**
	 * Sets the num of pieces.
	 *
	 * @param numOfPieces the new num of pieces
	 */
	public void setNumOfPieces(Integer[] numOfPieces) {
		this.numOfPieces = numOfPieces;
	}

	/**
	 * Gets the cn content codes.
	 *
	 * @return the cn content codes
	 */
	public String[] getCnContentCodes() {
		return cnContentCodes;
	}

	/**
	 * Sets the cn content codes.
	 *
	 * @param cnContentCodes the new cn content codes
	 */
	public void setCnContentCodes(String[] cnContentCodes) {
		this.cnContentCodes = cnContentCodes;
	}

	/**
	 * Gets the policy nos.
	 *
	 * @return the policy nos
	 */
	public String[] getPolicyNos() {
		return policyNos;
	}

	/**
	 * Sets the policy nos.
	 *
	 * @param policyNos the new policy nos
	 */
	public void setPolicyNos(String[] policyNos) {
		this.policyNos = policyNos;
	}

	/**
	 * Gets the cn content names.
	 *
	 * @return the cn content names
	 */
	public String[] getCnContentNames() {
		return cnContentNames;
	}

	/**
	 * Sets the cn content names.
	 *
	 * @param cnContentNames the new cn content names
	 */
	public void setCnContentNames(String[] cnContentNames) {
		this.cnContentNames = cnContentNames;
	}

	/**
	 * Gets the cn content others.
	 *
	 * @return the cn content others
	 */
	public String[] getCnContentOthers() {
		return cnContentOthers;
	}

	/**
	 * Sets the cn content others.
	 *
	 * @param cnContentOthers the new cn content others
	 */
	public void setCnContentOthers(String[] cnContentOthers) {
		this.cnContentOthers = cnContentOthers;
	}

	/**
	 * Gets the cn paper work codes.
	 *
	 * @return the cn paper work codes
	 */
	public String[] getCnPaperWorkCodes() {
		return cnPaperWorkCodes;
	}

	/**
	 * Sets the cn paper work codes.
	 *
	 * @param cnPaperWorkCodes the new cn paper work codes
	 */
	public void setCnPaperWorkCodes(String[] cnPaperWorkCodes) {
		this.cnPaperWorkCodes = cnPaperWorkCodes;
	}

	/**
	 * Gets the cn paper work names.
	 *
	 * @return the cn paper work names
	 */
	public String[] getCnPaperWorkNames() {
		return cnPaperWorkNames;
	}

	/**
	 * Sets the cn paper work names.
	 *
	 * @param cnPaperWorkNames the new cn paper work names
	 */
	public void setCnPaperWorkNames(String[] cnPaperWorkNames) {
		this.cnPaperWorkNames = cnPaperWorkNames;
	}

	/**
	 * Gets the insuarance nos.
	 *
	 * @return the insuarance nos
	 */
	public Integer[] getInsuaranceNos() {
		return insuaranceNos;
	}

	/**
	 * Sets the insuarance nos.
	 *
	 * @param insuaranceNos the new insuarance nos
	 */
	public void setInsuaranceNos(Integer[] insuaranceNos) {
		this.insuaranceNos = insuaranceNos;
	}

	
	public Double[] getAmounts() {
		return amounts;
	}

	public void setAmounts(Double[] amounts) {
		this.amounts = amounts;
	}

	/**
	 * Gets the cn content ids.
	 *
	 * @return the cn content ids
	 */
	public Integer[] getCnContentIds() {
		return cnContentIds;
	}

	/**
	 * Sets the cn content ids.
	 *
	 * @param cnContentIds the new cn content ids
	 */
	public void setCnContentIds(Integer[] cnContentIds) {
		this.cnContentIds = cnContentIds;
	}

	/**
	 * Gets the cn paper work ids.
	 *
	 * @return the cn paper work ids
	 */
	public Integer[] getCnPaperWorkIds() {
		return cnPaperWorkIds;
	}

	/**
	 * Sets the cn paper work ids.
	 *
	 * @param cnPaperWorkIds the new cn paper work ids
	 */
	public void setCnPaperWorkIds(Integer[] cnPaperWorkIds) {
		this.cnPaperWorkIds = cnPaperWorkIds;
	}

	/**
	 * Gets the paper ref num.
	 *
	 * @return the paper ref num
	 */
	public String[] getPaperRefNum() {
		return paperRefNum;
	}

	/**
	 * Sets the paper ref num.
	 *
	 * @param paperRefNum the new paper ref num
	 */
	public void setPaperRefNum(String[] paperRefNum) {
		this.paperRefNum = paperRefNum;
	}

	/**
	 * Gets the insured by.
	 *
	 * @return the insured by
	 */
	public String getInsuredBy() {
		return insuredBy;
	}

	/**
	 * Sets the insured by.
	 *
	 * @param insuredBy the new insured by
	 */
	public void setInsuredBy(String insuredBy) {
		this.insuredBy = insuredBy;
	}

	/**
	 * Gets the reference no.
	 *
	 * @return the reference no
	 */
	public String getReferenceNo() {
		return ReferenceNo;
	}

	/**
	 * Sets the reference no.
	 *
	 * @param referenceNo the new reference no
	 */
	public void setReferenceNo(String referenceNo) {
		ReferenceNo = referenceNo;
	}

	/**
	 * Gets the declared value.
	 *
	 * @return the declared value
	 */
	public Double getDeclaredValue() {
		return declaredValue;
	}

	/**
	 * Sets the declared value.
	 *
	 * @param declaredValue the new declared value
	 */
	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}

	/**
	 * Gets the paper work.
	 *
	 * @return the paper work
	 */
	public String getPaperWork() {
		return paperWork;
	}

	/**
	 * Sets the paper work.
	 *
	 * @param paperWork the new paper work
	 */
	public void setPaperWork(String paperWork) {
		this.paperWork = paperWork;
	}

	/**
	 * @return the insAmount
	 */
	public Double getInsAmount() {
		return insAmount;
	}

	/**
	 * @param insAmount the insAmount to set
	 */
	public void setInsAmount(Double insAmount) {
		this.insAmount = insAmount;
	}

	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

}
