package com.cg.lbs.bcun.to;

/**
 * @author mohammal
 * Jan 15, 2013
 * 
 */
public class BcunConfigPropertyTO  implements Comparable {
	private String beanId;
	private String doName;
	private String dataFormater;
	private String process;
	private String namedQuery;
	private Integer maxRowCount;
	private String officesFinder;
	private String propKey;
	private int sequence;
	private String category;
	public String getBeanId() {
		return beanId;
	}

	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	public String getDoName() {
		return doName;
	}

	public void setDoName(String doName) {
		this.doName = doName;
	}

	public String getDataFormater() {
		return dataFormater;
	}

	public void setDataFormater(String dataFormater) {
		this.dataFormater = dataFormater;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getNamedQuery() {
		return namedQuery;
	}

	public void setNamedQuery(String namedQuery) {
		this.namedQuery = namedQuery;
	}

	public Integer getMaxRowCount() {
		return maxRowCount;
	}

	public void setMaxRowCount(Integer maxRowCount) {
		this.maxRowCount = maxRowCount;
	}

	public String getOfficesFinder() {
		return officesFinder;
	}

	public void setOfficesFinder(String officesFinder) {
		this.officesFinder = officesFinder;
	}

	public String getPropKey() {
		return propKey;
	}

	public void setPropKey(String propKey) {
		this.propKey = propKey;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	@Override
	public int compareTo(Object other) {
		BcunConfigPropertyTO propTo = (BcunConfigPropertyTO)other;
		int result = this.sequence == propTo.sequence ? 0 : 
			this.sequence > propTo.sequence ? 1 : -1;
		return result;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
}
