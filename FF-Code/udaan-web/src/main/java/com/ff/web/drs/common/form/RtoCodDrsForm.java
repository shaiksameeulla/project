/**
 * 
 */
package com.ff.web.drs.common.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.drs.RtoCodDrsTO;

/**
 * The Class RtoCodDrsForm.
 *
 * @author mohammes
 */
public class RtoCodDrsForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
private static final long serialVersionUID = 1503047735484060264L;

	/** The drs to. */
	private RtoCodDrsTO to = null;

	/**
	 * Instantiates a new prepare norm dox drs form.
	 */
	public RtoCodDrsForm() {
		super();
		to = new RtoCodDrsTO();
		setTo(to);

	}
}
