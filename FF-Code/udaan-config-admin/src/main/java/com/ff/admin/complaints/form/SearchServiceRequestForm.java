package com.ff.admin.complaints.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.complaints.SearchServiceRequestHeaderTO;

/**
 * @author mohammes
 *
 */
public class SearchServiceRequestForm extends CGBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7047176644242768470L;
	public SearchServiceRequestHeaderTO to;
	public SearchServiceRequestForm(){
		super();
		to=new SearchServiceRequestHeaderTO();
		setTo(to);
	}
}
