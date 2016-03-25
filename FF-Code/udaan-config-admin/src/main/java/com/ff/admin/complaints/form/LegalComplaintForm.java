package com.ff.admin.complaints.form;

import org.apache.struts.upload.FormFile;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.complaints.LegalComplaintTO;

public class LegalComplaintForm extends CGBaseForm{

private static final long serialVersionUID = 1L;
	
	private FormFile file;

	public LegalComplaintForm() {
		setTo(new LegalComplaintTO());
	}

	/**
	 * @return the file
	 */
	public FormFile getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(FormFile file) {
		this.file = file;
	}
	
	
	
	
	
}
