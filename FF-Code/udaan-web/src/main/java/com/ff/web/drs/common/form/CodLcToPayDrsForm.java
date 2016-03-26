package com.ff.web.drs.common.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.drs.CodLcDrsTO;

/**
 * @author nihsingh
 *
 */
public class CodLcToPayDrsForm extends CGBaseForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The drs to. */
	private CodLcDrsTO to = null;
	
	/**
	 * Instantiates a new prepare Cod and LC series dox drs form.
	 */
	public CodLcToPayDrsForm() {
		super();
		to = new CodLcDrsTO();
		setTo(to);
	}
	
}
