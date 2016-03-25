package com.ff.domain.mec;

/**
 * @author khassan
 *
 */
public class SAPReportDO {
	private Long Id;
	private String reportName;
	private String reportDescription;
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportDescription() {
		return reportDescription;
	}
	public void setReportDescription(String reportDescription) {
		this.reportDescription = reportDescription;
	}
	
	
	

}
