/**
 * 
 */
package com.ff.tracking;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author uchauhan
 *
 */
public class TrackingBaseTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6754308697840071160L; 
	
	private String type;
	private String consgNumber;
	private String manifestNumber;
	private String number;
	private String incompleteData;  
	private List<ProcessMapTO> processMapTO;
	
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @return the manifestNumber
	 */
	public String getManifestNumber() {
		return manifestNumber;
	}
	/**
	 * @param manifestNumber the manifestNumber to set
	 */
	public void setManifestNumber(String manifestNumber) {
		this.manifestNumber = manifestNumber;
	}
	/**
	 * @return the processMapTO
	 */
	public List<ProcessMapTO> getProcessMapTO() {
		return processMapTO;
	}
	/**
	 * @param processMapTO the processMapTO to set
	 */
	public void setProcessMapTO(List<ProcessMapTO> processMapTO) {
		this.processMapTO = processMapTO;
	}
	

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * @return the incompleteData
	 */
	public String getIncompleteData() {
		return incompleteData;
	}
	/**
	 * @param incompleteData the incompleteData to set
	 */
	public void setIncompleteData(String incompleteData) {
		this.incompleteData = incompleteData;
	}
}
