package com.ff.admin.tracking.multipleTracking.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.tracking.TrackingBulkImportTO;

public class MultipleTrackingForm extends CGBaseForm {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4764968002239346873L;
	/** The to. */
	private TrackingBulkImportTO bulkTOs = null;
	
	/**
	 * Instantiates a new stock transfer form.
	 */
	
	public MultipleTrackingForm() {
		super();
		bulkTOs = new TrackingBulkImportTO();
		setTo(bulkTOs);
	}

}
