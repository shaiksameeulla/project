/**
 * 
 */
package com.ff.tracking;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateUtil;

/**
 * @author uchauhan
 *
 */
public class ProcessMapTO extends CGBaseTO implements Comparable<ProcessMapTO>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 219240070732810727L;
	private String artifactType ;//Either C-Consgment OR M-manifest
	private String processNumber ;// 
	private String consgNo;
	private String manifestNo;
	private Integer processOrder;// get From Process table
	private Date date;//Transaction date
	private List<TrackingParameterTO> parameterTOs ;
	private Integer operatingLevel;
	
	// for display 
	private String consignmentPath;
	private String manifestType;
	private String dateAndTime;
	
	public String getArtifactType() {
		return artifactType;
	}
	public String getProcessNumber() {
		return processNumber;
	}
	public String getConsgNo() {
		return consgNo;
	}
	public String getManifestNo() {
		return manifestNo;
	}
	public Integer getProcessOrder() {
		return processOrder;
	}
	public Date getDate() {
		return date;
	}
	public List<TrackingParameterTO> getParameterTOs() {
		return parameterTOs;
	}
	public Integer getOperatingLevel() {
		return operatingLevel;
	}
	public String getConsignmentPath() {
		return consignmentPath;
	}
	public String getManifestType() {
		return manifestType;
	}
	public String getDateAndTime() {
		return dateAndTime;
	}
	public void setArtifactType(String artifactType) {
		this.artifactType = artifactType;
	}
	public void setProcessNumber(String processNumber) {
		this.processNumber = processNumber;
	}
	public void setConsgNo(String consgNo) {
		this.consgNo = consgNo;
	}
	public void setManifestNo(String manifestNo) {
		this.manifestNo = manifestNo;
	}
	public void setProcessOrder(Integer processOrder) {
		this.processOrder = processOrder;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setParameterTOs(List<TrackingParameterTO> parameterTOs) {
		this.parameterTOs = parameterTOs;
	}
	public void setOperatingLevel(Integer operatingLevel) {
		this.operatingLevel = operatingLevel;
	}
	public void setConsignmentPath(String consignmentPath) {
		this.consignmentPath = consignmentPath;
	}
	public void setManifestType(String manifestType) {
		this.manifestType = manifestType;
	}
	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	@Override
	public int compareTo(ProcessMapTO to) {
		Date d1 = DateUtil.parseStringDateToDDMMYYYYHHMMFormat(this.dateAndTime);
		Date d2 = DateUtil.parseStringDateToDDMMYYYYHHMMFormat(to.dateAndTime);
		int value = d1.compareTo(d2);
		return value;
	}
}
