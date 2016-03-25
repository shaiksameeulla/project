package com.ff.to.billing;
import java.util.ArrayList;
import java.util.List;

public class BulkCustModificationTO extends CustModificationTO {

	/**
	 * defalut eclipse generated Id.
	 */
	private static final long serialVersionUID = 1L;
	private String cnModSelMode; // "S" - Series / "M" - Multiple
	private String startConsgNo;
	private String endConsgNo;
	private String consgNumbers;	
	private List<String> validConsgList;
	private List<String> inValidConsgList  = new ArrayList<String>();
	private List<List<String>> inValidConsgWithErrorDesc = new ArrayList<List<String>>();
	public String getCnModSelMode() {
		return cnModSelMode;
	}
	public void setCnModSelMode(String cnModSelMode) {
		this.cnModSelMode = cnModSelMode;
	}
	public String getStartConsgNo() {
		return startConsgNo;
	}
	public void setStartConsgNo(String startConsgNo) {
		this.startConsgNo = startConsgNo;
	}
	public String getEndConsgNo() {
		return endConsgNo;
	}
	public void setEndConsgNo(String endConsgNo) {
		this.endConsgNo = endConsgNo;
	}
	public String getConsgNumbers() {
		return consgNumbers;
	}
	public void setConsgNumbers(String consgNumbers) {
		this.consgNumbers = consgNumbers;
	}
	public List<String> getValidConsgList() {
		return validConsgList;
	}
	public void setValidConsgList(List<String> validConsgList) {
		this.validConsgList = validConsgList;
	}
	public List<String> getInValidConsgList() {
		return inValidConsgList;
	}
	public void setInValidConsgList(List<String> inValidConsgList) {
		this.inValidConsgList = inValidConsgList;
	}
	public List<List<String>> getInValidConsgWithErrorDesc() {
		return inValidConsgWithErrorDesc;
	}
	public void setInValidConsgWithErrorDesc(
			List<List<String>> inValidConsgWithErrorDesc) {
		this.inValidConsgWithErrorDesc = inValidConsgWithErrorDesc;
	}
	
}
