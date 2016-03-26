/**
 * 
 */
package com.ff.web.drs.common.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.drs.ManualDrsTO;

/**
 * @author mohammes
 *
 */
public class ManualDrsForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1503047735484060264L;

		/** The drs to. */
		private ManualDrsTO to = null;

		/**
		 * Instantiates a new prepare norm dox drs form.
		 */
		public ManualDrsForm() {
			super();
			to = new ManualDrsTO();
			setTo(to);

		}
}
