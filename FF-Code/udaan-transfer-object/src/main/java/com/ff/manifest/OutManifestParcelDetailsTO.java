package com.ff.manifest;

import java.util.List;

import com.ff.serviceOfferring.CNPaperWorksTO;

/**
 * The Class OutManifestParcelDetailsTO.
 */
public class OutManifestParcelDetailsTO extends
		BranchOutManifestParcelDetailsTO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1423785089197126170L;

	// To be used for specifically OutManifestParcel
	/** The booking type. */
	private String bookingType;
	
	/** The mobile no. */
	private String mobileNo;
	
	/** The consignee id. */
	private Integer consigneeId;
	
	/** The act weight. */
	private Double actWeight;
	
	/** The insured by id. */
	private Integer insuredById;
	
	/** The policy no. */
	private String policyNo;
	
	/** The cust ref no. */
	private String custRefNo;
	/* volumetric weight details */
	/** The volumetric weight. */
	private Double volumetricWeight;
	
	/** The length. */
	private Double length;
	
	/** The breadth. */
	private Double breadth;
	
	/** The height. */
	private Double height;
	
	/** The allow prod series. */
	private String allowProdSeries;
	private Integer manifestLoggedInOffId;
	private List<CNPaperWorksTO> cnPaperWorksTOList;
	

	/**
	 * @return the cnPaperWorksTOList
	 */
	public List<CNPaperWorksTO> getCnPaperWorksTOList() {
		return cnPaperWorksTOList;
	}

	/**
	 * @param cnPaperWorksTOList the cnPaperWorksTOList to set
	 */
	public void setCnPaperWorksTOList(List<CNPaperWorksTO> cnPaperWorksTOList) {
		this.cnPaperWorksTOList = cnPaperWorksTOList;
	}

	/**
	 * Gets the booking type.
	 *
	 * @return the booking type
	 */
	public String getBookingType() {
		return bookingType;
	}

	/**
	 * Sets the booking type.
	 *
	 * @param bookingType the new booking type
	 */
	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
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
	 * Gets the breadth.
	 *
	 * @return the breadth
	 */
	public Double getBreadth() {
		return breadth;
	}

	/**
	 * Sets the breadth.
	 *
	 * @param breadth the new breadth
	 */
	public void setBreadth(Double breadth) {
		this.breadth = breadth;
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
	 * Gets the act weight.
	 *
	 * @return the act weight
	 */
	public Double getActWeight() {
		return actWeight;
	}

	/**
	 * Sets the act weight.
	 *
	 * @param actWeight the new act weight
	 */
	public void setActWeight(Double actWeight) {
		this.actWeight = actWeight;
	}

	/**
	 * Gets the volumetric weight.
	 *
	 * @return the volumetric weight
	 */
	public Double getVolumetricWeight() {
		return volumetricWeight;
	}

	/**
	 * Sets the volumetric weight.
	 *
	 * @param volumetricWeight the new volumetric weight
	 */
	public void setVolumetricWeight(Double volumetricWeight) {
		this.volumetricWeight = volumetricWeight;
	}

	/**
	 * Gets the mobile no.
	 *
	 * @return the mobile no
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Sets the mobile no.
	 *
	 * @param mobileNo the new mobile no
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * Gets the consignee id.
	 *
	 * @return the consignee id
	 */
	public Integer getConsigneeId() {
		return consigneeId;
	}

	/**
	 * Sets the consignee id.
	 *
	 * @param consigneeId the new consignee id
	 */
	public void setConsigneeId(Integer consigneeId) {
		this.consigneeId = consigneeId;
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
	 * Gets the cust ref no.
	 *
	 * @return the cust ref no
	 */
	public String getCustRefNo() {
		return custRefNo;
	}

	/**
	 * Sets the cust ref no.
	 *
	 * @param custRefNo the new cust ref no
	 */
	public void setCustRefNo(String custRefNo) {
		this.custRefNo = custRefNo;
	}

	public String getAllowProdSeries() {
		return allowProdSeries;
	}

	public void setAllowProdSeries(String allowProdSeries) {
		this.allowProdSeries = allowProdSeries;
	}

	public Integer getManifestLoggedInOffId() {
		return manifestLoggedInOffId;
	}

	public void setManifestLoggedInOffId(Integer manifestLoggedInOffId) {
		this.manifestLoggedInOffId = manifestLoggedInOffId;
	}

}
