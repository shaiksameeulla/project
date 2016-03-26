/**
 * 
 */
package com.ff.web.drs.list.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.drs.list.ListDrsHeaderTO;

/**
 * @author mohammes
 *
 */
public class ListDrsForm extends CGBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6095802029549257954L;
	
	/** The to. */
	private ListDrsHeaderTO to = null;

	
	/**
	 * Instantiates a new list drs form.
	 */
	public ListDrsForm() {
		super();
		to = new ListDrsHeaderTO();
		setTo(to);

	}
}
