/**
 * 
 */
package com.ff.admin.mec.collection.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.mec.collection.ValidateCollectionEntryTO;

/**
 * @author prmeher
 *
 */
public class ValidateCollectionForm extends CGBaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ValidateCollectionForm() {
		ValidateCollectionEntryTO validateCollectionTO = new ValidateCollectionEntryTO();
		setTo(validateCollectionTO);
	}

}
