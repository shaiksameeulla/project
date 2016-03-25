/*
 * @author mohammes
 */
package com.capgemini.lbs.framework.form;

import org.apache.struts.action.ActionForm;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class CGBaseForm.
 */
@SuppressWarnings("serial")
public abstract class CGBaseForm extends ActionForm {

	/** The base to. */
	private CGBaseTO to;
	
	/**
	 * @return the to
	 */
	public CGBaseTO getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(CGBaseTO to) {
		
		this.to = to;
	}
	
	private String nodeId;
	/**
	 * @return
	 */
	public String getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
}
