package com.ff.admin.complaints.form;

import org.apache.struts.upload.FormFile;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.complaints.ServiceRequestPaperworkTO;

public class PaperworkForm extends CGBaseForm{


	private static final long serialVersionUID = 1L;
	
	private FormFile file;

	public PaperworkForm() {
		setTo(new ServiceRequestPaperworkTO());
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

