package com.ff.web.manifest.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.manifest.BranchOutManifestDoxTO;

/**
 * @author nihsingh
 *
 */
public class BranchOutManifestDoxForm extends CGBaseForm {
	
	private static final long serialVersionUID = 7492857921810952795L;
	
	/**
	 * 
	 */
	public BranchOutManifestDoxForm() {
		setTo(new BranchOutManifestDoxTO());
	}
}
