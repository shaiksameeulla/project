/**
 * 
 */
package com.ff.web.drs.blkpending.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.drs.pending.PendingDrsHeaderTO;

/**
 * @author mohammes
 *
 */
public class BulkCnPendingDrsForm extends CGBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -361269122330564744L;

	/** The to. */
	private PendingDrsHeaderTO to;
	
	
	/**
	 * Instantiates a new pending drs from.
	 */
	public BulkCnPendingDrsForm() {
		super();
		to = new PendingDrsHeaderTO();
		setTo(to);

	}
}
