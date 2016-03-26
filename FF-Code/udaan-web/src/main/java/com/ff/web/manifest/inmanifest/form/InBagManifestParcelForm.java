/**
 * 
 */
package com.ff.web.manifest.inmanifest.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.manifest.inmanifest.InBagManifestParcelTO;

// TODO: Auto-generated Javadoc
/**
 * The Class InBagManifestParcelForm.
 *
 * @author narmdr
 */
public class InBagManifestParcelForm extends CGBaseForm{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 61692840632038899L;

	/**
	 * Instantiates a new in bag manifest parcel form.
	 */
	public InBagManifestParcelForm() {
		setTo(new InBagManifestParcelTO());
	}
}
