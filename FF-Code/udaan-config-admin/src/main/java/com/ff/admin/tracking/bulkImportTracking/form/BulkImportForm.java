/**
 * 
 */
package com.ff.admin.tracking.bulkImportTracking.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.tracking.TrackingBulkImportTO;

/**
 * @author uchauhan
 *
 */
public class BulkImportForm extends CGBaseForm{
    
    /**
     * 
     */
    private static final long serialVersionUID = -3595664630577315052L;
    
    /** The to. */
	private TrackingBulkImportTO bulkTOs = null;
	
	/**
	 * Instantiates a new stock transfer form.
	 */
	public BulkImportForm() {
		super();
		bulkTOs = new TrackingBulkImportTO();
		setTo(bulkTOs);
	}
	

}
