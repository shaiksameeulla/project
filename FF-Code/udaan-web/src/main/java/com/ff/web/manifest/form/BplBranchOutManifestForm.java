package com.ff.web.manifest.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.manifest.BPLOutManifestDoxTO;
import com.ff.manifest.BplBranchOutManifestTO;

public class BplBranchOutManifestForm extends CGBaseForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6489816742371989325L;

	public BplBranchOutManifestForm() {
		setTo(new BplBranchOutManifestTO());
	}
	
}
