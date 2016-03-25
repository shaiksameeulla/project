package com.cg.lbs.bcun.to;

public class BcunFileSequenceTO implements Comparable {
	private int sequenceNumber;
	private String fileName;
	
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public int compareTo(Object o) {
		int result = 0;
		BcunFileSequenceTO fileTo  = (BcunFileSequenceTO)o;
		result = this.sequenceNumber == fileTo.sequenceNumber?0 : this.sequenceNumber > fileTo.sequenceNumber ? 1 : -1;  
		return result;
	}
}
