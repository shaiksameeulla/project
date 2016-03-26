/**
 * 
 */
package com.ff.web.manifest.inmanifest.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.manifest.inmanifest.InBagManifestDoxTO;

// TODO: Auto-generated Javadoc
/**
 * The Class InBagManifestForm.
 *
 * @author narmdr
 */
public class InBagManifestForm extends CGBaseForm{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6367160431202452140L;

	/**
	 * Instantiates a new in bag manifest form.
	 */
	public InBagManifestForm() {
		setTo(new InBagManifestDoxTO());
	}
}
