/**
 * 
 */
package com.ff.web.drs.updation.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.drs.pending.PendingDrsHeaderTO;

/**
 * @author mohammes
 *
 */
public class PendingDrsForm extends CGBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -361269122330564744L;

	/** The to. */
	private PendingDrsHeaderTO to;
	
	
	/**
	 * Instantiates a new pending drs from.
	 */
	public PendingDrsForm() {
		super();
		to = new PendingDrsHeaderTO();
		setTo(to);

	}
}
