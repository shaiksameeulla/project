package com.cg.lbs.bcun.to;

import java.io.Serializable;

public class QueueDataContentTO implements Serializable  {
	
	private String jsonText;
	private String doName;
	private String fileName;
	
	public String getJsonText() {
		return jsonText;
	}
	public void setJsonText(String jsonText) {
		this.jsonText = jsonText;
	}
	public String getDoName() {
		return doName;
	}
	public void setDoName(String doName) {
		this.doName = doName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
