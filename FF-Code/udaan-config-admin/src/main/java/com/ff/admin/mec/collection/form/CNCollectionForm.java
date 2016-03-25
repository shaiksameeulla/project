/**
 * 
 */
package com.ff.admin.mec.collection.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.mec.collection.CNCollectionTO;

/**
 * @author prmeher
 *
 */
public class CNCollectionForm  extends CGBaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CNCollectionForm() {
		CNCollectionTO cnCollectionTO = new CNCollectionTO();
		setTo(cnCollectionTO);
	}
}
