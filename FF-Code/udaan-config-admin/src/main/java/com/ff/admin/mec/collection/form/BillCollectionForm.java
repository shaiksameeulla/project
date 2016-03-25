/**
 * 
 */
package com.ff.admin.mec.collection.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.mec.collection.BillCollectionTO;

/**
 * @author prmeher
 *
 */
public class BillCollectionForm extends CGBaseForm  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param 
	 */
	public BillCollectionForm() {
		BillCollectionTO billCollectionTO = new BillCollectionTO();
		setTo(billCollectionTO);
	}

}
