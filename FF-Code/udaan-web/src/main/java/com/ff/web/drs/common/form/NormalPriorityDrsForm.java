/**
 * 
 */
package com.ff.web.drs.common.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.drs.NormalPriorityDrsTO;

/**
 * The Class PrepareNormDoxDrsForm.
 *
 * @author mohammes
 */
public class NormalPriorityDrsForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
private static final long serialVersionUID = 1503047735484060264L;

	/** The drs to. */
	private NormalPriorityDrsTO to = null;

	/**
	 * Instantiates a new prepare norm dox drs form.
	 */
	public NormalPriorityDrsForm() {
		super();
		to = new NormalPriorityDrsTO();
		setTo(to);

	}
}
