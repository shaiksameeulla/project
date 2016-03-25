package com.ff.manifest.inmanifest;

/**
 * @author narmdr
 *
 */
public class LessExcessBaggageReportTO extends InManifestTO {
	
	private static final long serialVersionUID = 5855279451388019836L;

	private String headerManifestNumber;
	/*private String consgNumber;
	private String manifestNumber;*/
	private String status;//E-Excess L-less
	private boolean isExcess;
	private boolean isLess;
	
	/**
	 * @return the headerManifestNumber
	 */
	public String getHeaderManifestNumber() {
		return headerManifestNumber;
	}
	/**
	 * @param headerManifestNumber the headerManifestNumber to set
	 */
	public void setHeaderManifestNumber(String headerManifestNumber) {
		this.headerManifestNumber = headerManifestNumber;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the isExcess
	 */
	public boolean getIsExcess() {
		return isExcess;
	}
	/**
	 * @param isExcess the isExcess to set
	 */
	public void setIsExcess(boolean isExcess) {
		this.isExcess = isExcess;
	}
	/**
	 * @return the isLess
	 */
	public boolean getIsLess() {
		return isLess;
	}
	/**
	 * @param isLess the isLess to set
	 */
	public void setIsLess(boolean isLess) {
		this.isLess = isLess;
	}
	
}
