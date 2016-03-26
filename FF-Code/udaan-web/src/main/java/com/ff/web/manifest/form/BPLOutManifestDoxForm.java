package com.ff.web.manifest.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.manifest.BPLOutManifestDoxTO;

// TODO: Auto-generated Javadoc
/**
 * The Class BPLOutManifestDoxForm.
 */
public class BPLOutManifestDoxForm extends CGBaseForm {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3780482612836416333L;
	
	/** The to. */
	public BPLOutManifestDoxTO to=null;
	
	/* (non-Javadoc)
	 * @see com.capgemini.lbs.framework.form.CGBaseForm#getTo()
	 */
	public BPLOutManifestDoxTO getTo() {
		return to;
	}

	/**
	 * Sets the to.
	 *
	 * @param to the new to
	 */
	public void setTo(BPLOutManifestDoxTO to) {
		this.to = to;
	}

	/**
	 * Instantiates a new bPL out manifest dox form.
	 */
	public BPLOutManifestDoxForm() {
		setTo(new BPLOutManifestDoxTO());
	}
}
