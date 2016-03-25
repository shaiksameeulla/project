package com.ff.admin.mec.collection.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.mec.collection.BulkCollectionValidationTO;

public class BulkCollectionValidationForm extends CGBaseForm {
	private static final long serialVersionUID = 1L;
	
	public BulkCollectionValidationForm() {
		BulkCollectionValidationTO bulkCollectionValidationTo = new BulkCollectionValidationTO();
		setTo(bulkCollectionValidationTo);
	}
}
