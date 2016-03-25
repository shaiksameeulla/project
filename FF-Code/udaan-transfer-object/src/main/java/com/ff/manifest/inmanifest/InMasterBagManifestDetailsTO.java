/**
 * 
 */
package com.ff.manifest.inmanifest;

import com.capgemini.lbs.framework.utils.StringUtil;

/**
 * @author nkattung
 * 
 */
public class InMasterBagManifestDetailsTO extends InManifestDetailsTO implements Comparable<InMasterBagManifestDetailsTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3269180890567438993L;
	private String bplNumber;
	private String bagLackNo;
	private String bplProcessCode;
	private Integer bplProcessId;
	private String docType;
	private Integer position;

	//for print 
	private Integer noOfConsg;
	/**
	 * @return the bplProcessCode
	 */
	public String getBplProcessCode() {
		return bplProcessCode;
	}

	/**
	 * @param bplProcessCode the bplProcessCode to set
	 */
	public void setBplProcessCode(String bplProcessCode) {
		this.bplProcessCode = bplProcessCode;
	}

	/**
	 * @return the bplProcessId
	 */
	public Integer getBplProcessId() {
		return bplProcessId;
	}

	/**
	 * @param bplProcessId the bplProcessId to set
	 */
	public void setBplProcessId(Integer bplProcessId) {
		this.bplProcessId = bplProcessId;
	}

	public String getBplNumber() {
		return bplNumber;
	}

	public void setBplNumber(String bplNumber) {
		this.bplNumber = bplNumber;
	}

	public String getBagLackNo() {
		return bagLackNo;
	}

	public void setBagLackNo(String bagLackNo) {
		this.bagLackNo = bagLackNo;
	}

	/**
	 * @return the docType
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * @param docType the docType to set
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	/**
	 * @return the noOfConsg
	 */
	public Integer getNoOfConsg() {
		return noOfConsg;
	}

	/**
	 * @param noOfConsg the noOfConsg to set
	 */
	public void setNoOfConsg(Integer noOfConsg) {
		this.noOfConsg = noOfConsg;
	}

	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}

	@Override
	public int compareTo(InMasterBagManifestDetailsTO arg0) {
		int returnVal = 0;
		if(!StringUtil.isEmptyInteger(position) &&
				!StringUtil.isEmptyInteger(arg0.getPosition())){
			returnVal = position.compareTo(arg0.position);
		}
		return returnVal;
	}
	
}
