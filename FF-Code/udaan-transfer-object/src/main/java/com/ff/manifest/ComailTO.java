package com.ff.manifest;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.OfficeTO;

public class ComailTO extends CGBaseTO implements Comparable<ComailTO>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9116149575144730089L;

	/** The co mail id. */
	private Integer coMailId;
	
	/** The co mail no. */
	private String  coMailNo;
	
	/** The manifest id. */
	private Integer manifestId;
	
	/** The manifest weight. */
	private Double manifestWeight;
	
	/** The origin office. */
	private Integer originOffice;
	
	/** The destination office. */
	private Integer destinationOffice;
	//Added for tracking
	private OfficeTO destOffice;
	
	public Integer getCoMailId() {
		return coMailId;
	}

	public void setCoMailId(Integer coMailId) {
		this.coMailId = coMailId;
	}

	public String getCoMailNo() {
		return coMailNo;
	}

	public void setCoMailNo(String coMailNo) {
		this.coMailNo = coMailNo;
	}

	public Integer getManifestId() {
		return manifestId;
	}

	public void setManifestId(Integer manifestId) {
		this.manifestId = manifestId;
	}

	public Double getManifestWeight() {
		return manifestWeight;
	}

	public void setManifestWeight(Double manifestWeight) {
		this.manifestWeight = manifestWeight;
	}

	public Integer getOriginOffice() {
		return originOffice;
	}

	public void setOriginOffice(Integer originOffice) {
		this.originOffice = originOffice;
	}

	public Integer getDestinationOffice() {
		return destinationOffice;
	}

	public void setDestinationOffice(Integer destinationOffice) {
		this.destinationOffice = destinationOffice;
	}

	public OfficeTO getDestOffice() {
		return destOffice;
	}

	public void setDestOffice(OfficeTO destOffice) {
		this.destOffice = destOffice;
	}
	/*The sequencing of embedded elements in OGM / BPL are getting changed in tracking*/
	@Override
	public int compareTo(ComailTO arg0) {
		int returnVal = 0;
		if (StringUtils.isNotEmpty(this.coMailNo)) {
			returnVal = this.coMailNo.compareTo(arg0.coMailNo);
		}
		return returnVal;
	}
}
